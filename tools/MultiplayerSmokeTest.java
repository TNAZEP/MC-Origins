package net.minecraft.src;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.nio.file.Path;
import java.util.concurrent.*;
import java.util.function.Predicate;

/** Two real protocol connections to a separate dedicated process, in a disposable world. */
public final class MultiplayerSmokeTest {
    static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
    static final class Client implements AutoCloseable {
        final Socket socket;
        final DataOutputStream output;
        final BlockingQueue<Packet> packets = new LinkedBlockingQueue<>();
        volatile Throwable failure;
        double x, y, z;
        Client(int port, String name) throws Exception {
            socket = new Socket("127.0.0.1", port);
            output = new DataOutputStream(socket.getOutputStream());
            Thread reader = new Thread(() -> {
                try {
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    while (!socket.isClosed()) {
                        Packet p = Packet.readPacket(in, false);
                        if (p == null) throw new EOFException();
                        if (p instanceof Packet255KickDisconnect)
                            throw new IOException(((Packet255KickDisconnect)p).reason);
                        packets.add(p);
                    }
                } catch (Throwable error) { if (!socket.isClosed()) failure = error; }
            }, "probe-" + name);
            reader.setDaemon(true);
            reader.start();
            send(new Packet2Handshake(name));
            await(p -> p instanceof Packet2Handshake, "handshake");
            send(new Packet1Login(name, 14));
            await(p -> p instanceof Packet1Login, "login");
            Packet13PlayerLookMove position = (Packet13PlayerLookMove)await(
                p -> p instanceof Packet13PlayerLookMove, "initial position");
            x = position.xPosition;
            y = position.stance; // Server sends eye Y first, feet Y second.
            z = position.zPosition;
            move(x, z);
        }
        synchronized void send(Packet p) throws IOException {
            Packet.writePacket(p, output); output.flush();
        }
        void move(double nextX, double nextZ) throws IOException {
            x = nextX; z = nextZ;
            send(new Packet13PlayerLookMove(x, y, y + 1.62, z, 0, 0, true));
        }
        Packet await(Predicate<Packet> match, String purpose) throws Exception {
            long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(25);
            while (System.nanoTime() < end) {
                if (failure != null) throw new IOException(purpose, failure);
                Packet p = packets.poll(100, TimeUnit.MILLISECONDS);
                if (p != null && match.test(p)) return p;
            }
            throw new AssertionError("Timed out: " + purpose);
        }
        public void close() throws IOException { socket.close(); }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("java8 server.jar");
        Path root = Files.createTempDirectory(Paths.get("run/smoke"), "multiplayer-").toAbsolutePath();
        System.out.println("Scenario directory: " + root);
        SaveOldDir save = new SaveOldDir(root.toFile(), "world", true);
        World world = new World(save, "world", 8675309L);
        world.setSpawnPoint(new ChunkCoordinates(0, 100, 0));
        for (int x = 0; x < 8; x++) for (int z = 0; z < 8; z++)
            world.setBlockWithNotify(x, 99, z, Block.stone.blockID);
        world.setBlockWithNotify(4, 100, 3, Block.dirt.blockID);
        world.setBlockWithNotify(4, 100, 4, Block.chest.blockID);
        ((TileEntityChest)world.getBlockTileEntity(4, 100, 4)).setInventorySlotContents(0, new ItemStack(Item.diamond, 7));
        for (int i = 0; i < 2; i++) {
            EntityPlayer p = new EntityPlayer(world) {};
            p.username = "Probe" + i;
            p.setPosition(2.5, 100, 2.5 + i);
            p.inventory.mainInventory[0] = new ItemStack(Block.cobblestone, 8);
            save.writePlayerData(p);
        }
        world.saveWorld(true, null); save.func_22093_e();
        int port;
        try (ServerSocket reserve = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
            port = reserve.getLocalPort();
        }
        Files.write(root.resolve("ops.txt"), "Probe0\nProbe1\n".getBytes("UTF-8"));
        Files.write(root.resolve("server.properties"), ("server-ip=127.0.0.1\nserver-port=" + port
            + "\nonline-mode=false\nlevel-name=world\nspawn-monsters=false\nspawn-animals=false\n").getBytes("UTF-8"));
        Path log = root.resolve("server.log");
        Process server = new ProcessBuilder(args[0], "-Xmx512m", "-Djava.awt.headless=true", "-jar",
            new File(args[1]).getAbsolutePath(), "nogui").directory(root.toFile())
            .redirectErrorStream(true).redirectOutput(log.toFile()).start();
        PrintWriter console = new PrintWriter(server.getOutputStream(), true);
        try {
            long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(90);
            while (!new String(Files.readAllBytes(log), "UTF-8").contains("Done (")) {
                check(server.isAlive() && System.nanoTime() < end, "Server not ready: " + log);
                Thread.sleep(100);
            }
            double ax, az, bx, bz;
            try (Client a = new Client(port, "Probe0"); Client b = new Client(port, "Probe1")) {
                a.send(new Packet3Chat("origins-two-player"));
                b.await(p -> p instanceof Packet3Chat && ((Packet3Chat)p).message.contains("origins-two-player"), "peer chat");
                a.move(a.x + .25, a.z); b.move(b.x + .25, b.z);
                ax = a.x; az = a.z; bx = b.x; bz = b.z;
                a.send(new Packet15Place(4, 99, 2, 1, new ItemStack(Block.cobblestone, 8)));
                a.await(p -> p instanceof Packet53BlockChange && ((Packet53BlockChange)p).xPosition == 4
                    && ((Packet53BlockChange)p).yPosition == 100 && ((Packet53BlockChange)p).zPosition == 2
                    && ((Packet53BlockChange)p).type == Block.cobblestone.blockID, "block placement");
                b.send(new Packet14BlockDig(0, 4, 100, 3, 1));
                Thread.sleep(1200);
                b.send(new Packet14BlockDig(2, 4, 100, 3, 1));
                b.send(new Packet15Place(4, 100, 4, 1, new ItemStack(Block.cobblestone, 8)));
                Packet100OpenWindow window = (Packet100OpenWindow)b.await(p -> p instanceof Packet100OpenWindow, "chest interaction");
                check(window.slotsCount == 27, "Chest window size");
                b.await(p -> p instanceof Packet104WindowItems && ((Packet104WindowItems)p).windowId == window.windowId
                    && ((Packet104WindowItems)p).itemStack[0] != null
                    && ((Packet104WindowItems)p).itemStack[0].stackSize == 7, "chest inventory");
                b.send(new Packet101CloseWindow(window.windowId));
                console.println("save-all");
                Thread.sleep(500);
            }
            Thread.sleep(500);
            try (Client a = new Client(port, "Probe0"); Client b = new Client(port, "Probe1")) {
                check(Math.abs(a.x - ax) < .01 && Math.abs(a.z - az) < .01, "First player movement/reconnect");
                check(Math.abs(b.x - bx) < .01 && Math.abs(b.z - bz) < .01, "Second player movement/reconnect");
            }
        } finally {
            console.println("stop");
            if (!server.waitFor(30, TimeUnit.SECONDS)) { server.destroyForcibly(); throw new AssertionError("Server did not stop"); }
        }
        check(server.exitValue() == 0, "Server exit");
        SaveOldDir restoredSave = new SaveOldDir(root.toFile(), "world", true);
        World restored = new World(restoredSave, "world", 1L);
        check(restored.getBlockId(4, 100, 2) == Block.cobblestone.blockID, "Placed block not saved");
        check(restored.getBlockId(4, 100, 3) == 0, "Dug block not saved");
        EntityPlayer player = new EntityPlayer(restored) {};
        player.username = "Probe0"; restoredSave.readPlayerData(player);
        check(player.inventory.mainInventory[0].stackSize == 7, "Placement inventory consumption not saved");
        restoredSave.func_22093_e();
        System.out.println("PASS: two protocol clients login, chat, move, place/dig, open chest, save, disconnect/reconnect; persisted blocks and inventory; clean server stop");
    }
}
