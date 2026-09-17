package net.minecraft.src;

import net.minecraft.client.Minecraft;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/** Runs the real render/tick loop with scripted actions in isolated SP and MP worlds. */
public final class ClientPlaySmokeTest extends Minecraft {
    private final Path evidence;
    private final int port;
    private int stage, ticks;
    private double startX, startZ;
    private int blockX, blockY, blockZ;
    private boolean complete;
    private ClientPlaySmokeTest(Path evidence, int port, Frame frame, Canvas canvas) {
        super(frame, canvas, null, 854, 480, false);
        this.evidence = evidence;
        this.port = port;
        session = new Session("RenderProbe", "-");
        minecraftUri = "www.minecraft.net";
    }
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
    public void displayUnexpectedThrowable(UnexpectedThrowable error) {
        System.err.println("FAIL: " + error.description);
        error.exception.printStackTrace();
        System.exit(1);
    }
    private void capture(String phase) {
        String result = ScreenShotHelper.saveScreenshot(evidence.toFile(), displayWidth, displayHeight);
        check(result.startsWith("Saved"), result);
        System.out.println(phase + ": " + result);
    }
    // The desktop may leave an automated window unfocused. Suppress only this
    // harness's focus-loss menu so the real game loop can execute scripted input.
    public void displayInGameMenu() {
        if (stage == 0) super.displayInGameMenu();
    }
    public void runTick() {
        super.runTick();
        ++ticks;
        if (stage == 0 && ticks > 20) {
            capture("menu");
            gameSettings.renderDistance = 2;
            playerController = new PlayerControllerSP(this);
            displayGuiScreen(null);
            startWorld("smoke", "Origins graphical smoke", 8675309L);
            displayGuiScreen(null);
            stage = 1; ticks = 0;
        } else if (stage == 1 && ticks > 40) {
            check(theWorld != null && !theWorld.multiplayerWorld, "Local world failed");
            startX = thePlayer.posX; startZ = thePlayer.posZ;
            thePlayer.movementInput = new MovementInput();
            thePlayer.movementInput.moveForward = 1;
            stage = 2; ticks = 0;
        } else if (stage == 2 && ticks > 20) {
            thePlayer.movementInput.moveForward = 0;
            System.out.println("Movement: " + startX + "," + startZ + " -> " + thePlayer.posX + "," + thePlayer.posZ + "; screen=" + currentScreen + "; paused=" + isGamePaused);
            check(Math.abs(thePlayer.posX - startX) + Math.abs(thePlayer.posZ - startZ) > .1, "Local movement failed");
            blockX = MathHelper.floor_double(thePlayer.posX) + 2;
            blockY = MathHelper.floor_double(thePlayer.boundingBox.minY);
            blockZ = MathHelper.floor_double(thePlayer.posZ);
            theWorld.setBlockWithNotify(blockX, blockY - 1, blockZ, Block.stone.blockID);
            theWorld.setBlockWithNotify(blockX, blockY, blockZ, 0);
            ItemStack blocks = new ItemStack(Block.cobblestone, 8);
            thePlayer.inventory.mainInventory[0] = blocks;
            thePlayer.inventory.currentItem = 0;
            check(playerController.sendPlaceBlock(thePlayer, theWorld, blocks, blockX, blockY - 1, blockZ, 1), "Controller placement");
            check(playerController.sendBlockRemoved(blockX, blockY, blockZ, 1), "Controller removal");
            check(playerController.sendPlaceBlock(thePlayer, theWorld, blocks, blockX, blockY - 1, blockZ, 1), "Controller replacement");
            stage = 3; ticks = 0;
        } else if (stage == 3 && ticks > 60) {
            capture("local-play");
            changeWorld1(null);
            startWorld("smoke", "Origins graphical smoke", 1L);
            displayGuiScreen(null);
            check(theWorld.getBlockId(blockX, blockY, blockZ) == Block.cobblestone.blockID, "Graphical world reload lost placed block");
            stage = 4; ticks = 0;
        } else if (stage == 4 && ticks > 40) {
            capture("local-reloaded");
            displayGuiScreen(new GuiConnecting(this, "127.0.0.1", port));
            stage = 5; ticks = 0;
        } else if ((stage == 5 || stage == 7) && theWorld != null && theWorld.multiplayerWorld && currentScreen == null && ticks > 100) {
            capture(stage == 5 ? "multiplayer" : "multiplayer-reconnected");
            getSendQueue().disconnect();
            changeWorld1(null);
            if (stage == 5) { stage = 6; ticks = 0; displayGuiScreen(new GuiMainMenu()); }
            else {
                complete = true;
                System.out.println("PASS: graphical menu, local movement/controller block interaction, save/reload, dedicated connection and reconnect");
                shutdown();
            }
        } else if (stage == 6 && ticks > 30) {
            displayGuiScreen(new GuiConnecting(this, "127.0.0.1", port));
            stage = 7; ticks = 0;
        }
    }
    public void shutdownMinecraftApplet() {
        if (!complete) { System.err.println("FAIL: graphical scenario interrupted at stage " + stage); System.exit(1); }
        super.shutdownMinecraftApplet();
        if (!complete) System.exit(1);
    }
    public static void main(String[] args) {
        try { launch(args); } catch (Throwable error) { error.printStackTrace(); System.exit(1); }
    }
    private static void launch(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("java8 server.jar");
        Path root = Files.createTempDirectory(Paths.get("run/smoke"), "graphical-").toAbsolutePath();
        System.out.println("Scenario directory: " + root);
        System.setProperty("user.home", root.resolve("client").toString());
        Files.createDirectories(root.resolve("client"));
        Path serverRoot = Files.createDirectory(root.resolve("server"));
        int port;
        try (ServerSocket reserve = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) { port = reserve.getLocalPort(); }
        Files.write(serverRoot.resolve("server.properties"), ("server-ip=127.0.0.1\nserver-port=" + port
            + "\nonline-mode=false\nlevel-name=world\nlevel-seed=8675309\n").getBytes("UTF-8"));
        Path log = serverRoot.resolve("server.log");
        Process server = new ProcessBuilder(args[0], "-Xmx512m", "-Djava.awt.headless=true", "-jar",
            new File(args[1]).getAbsolutePath(), "nogui").directory(serverRoot.toFile())
            .redirectErrorStream(true).redirectOutput(log.toFile()).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            new PrintWriter(server.getOutputStream(), true).println("stop");
            try { if (!server.waitFor(30, TimeUnit.SECONDS)) server.destroyForcibly(); }
            catch (InterruptedException e) { server.destroyForcibly(); }
        }));
        Thread watchdog = new Thread(() -> {
            try { Thread.sleep(180000); } catch (InterruptedException e) { return; }
            System.err.println("FAIL: graphical scenario timed out"); System.exit(1);
        });
        watchdog.setDaemon(true); watchdog.start();
        long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(90);
        while (!new String(Files.readAllBytes(log), "UTF-8").contains("Done (")) {
            check(server.isAlive() && System.nanoTime() < end, "Server startup"); Thread.sleep(100);
        }
        String resources = System.getProperty("origins.smokeResources");
        if (resources != null) {
            Path source = Paths.get(resources);
            Path target = root.resolve("client/.minecraft/resources");
            try (java.util.stream.Stream<Path> paths = Files.walk(source)) {
                for (Path input : (Iterable<Path>)paths::iterator) {
                    Path dest = target.resolve(source.relativize(input));
                    if (Files.isDirectory(input)) Files.createDirectories(dest);
                    else Files.copy(input, dest);
                }
            }
        }
        Frame frame = new Frame(OriginsVersion.DISPLAY_NAME);
        Canvas canvas = new Canvas();
        frame.setLayout(new BorderLayout());
        frame.add(canvas, "Center");
        canvas.setPreferredSize(new Dimension(854, 480));
        frame.pack(); frame.setLocationRelativeTo(null); frame.setVisible(true);
        new ClientPlaySmokeTest(root, port, frame, canvas).run();
    }
}
