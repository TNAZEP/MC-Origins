import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.security.MessageDigest;
import java.util.Arrays;

/** JDK-only differential harness; each invocation loads one jar without a game launch. */
public final class BetaPacketCodecTest {
    static Method writeString, readString, readPacket, writePacket, getPacket, packetId, packetSize;
    static PrintWriter report;
    static int cases;
    static boolean namedNamespace;

    interface Operation { String run() throws Exception; }

    static String hash(byte[] bytes) throws Exception {
        StringBuilder result = new StringBuilder();
        for (byte b : MessageDigest.getInstance("SHA-256").digest(bytes)) result.append(String.format("%02x", b & 255));
        return bytes.length + ":" + result;
    }

    static byte[] encode(String value) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        data.writeShort(value.length());
        data.writeChars(value);
        return bytes.toByteArray();
    }

    static String repeat(int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, 'x');
        return new String(chars);
    }

    static void record(String name, Operation operation) throws Exception {
        String outcome;
        try { outcome = "OK:" + operation.run(); }
        catch (InvocationTargetException error) {
            Throwable cause = error.getCause();
            outcome = "ERROR:" + cause.getClass().getName() + ":" + cause.getMessage();
        }
        report.println(name + "\t" + outcome);
        cases++;
    }

    static void readCase(String name, byte[] bytes, int maximum) throws Exception {
        record(name, () -> {
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes));
            String value = (String) readString.invoke(null, input, maximum);
            return hash(encode(value)) + ":remaining=" + input.available();
        });
    }

    static void packetCase(String name, byte[] bytes, boolean server) throws Exception {
        record(name, () -> {
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes));
            Object packet = readPacket.invoke(null, input, server);
            if (packet == null) return "null:remaining=" + input.available();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            writePacket.invoke(null, packet, new DataOutputStream(output));
            return "id=" + packetId.invoke(packet) + ":size=" + packetSize.invoke(packet)
                + ":wire=" + hash(output.toByteArray()) + ":remaining=" + input.available();
        });
    }

    static Object field(Object packet, String named, String original) throws Exception {
        return packet.getClass().getField(namedNamespace ? named : original).get(packet);
    }

    static String movementState(Object packet) throws Exception {
        StringBuilder state = new StringBuilder();
        for (String[] names : new String[][] {{"xPosition", "a"}, {"yPosition", "b"},
                {"stance", "d"}, {"zPosition", "c"}}) {
            state.append(Long.toHexString(Double.doubleToRawLongBits((Double) field(packet, names[0], names[1])))).append(',');
        }
        for (String[] names : new String[][] {{"yaw", "e"}, {"pitch", "f"}}) {
            state.append(Integer.toHexString(Float.floatToRawIntBits((Float) field(packet, names[0], names[1])))).append(',');
        }
        return state + ":ground=" + field(packet, "onGround", "g")
            + ":moving=" + field(packet, "moving", "h") + ":rotating=" + field(packet, "rotating", "i");
    }

    static byte[] movementWire(int id, double[] coordinates, float[] angles, int ground) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        out.writeByte(id);
        if (id == 11 || id == 13) for (double value : coordinates) out.writeDouble(value);
        if (id == 12 || id == 13) for (float value : angles) out.writeFloat(value);
        out.writeByte(ground);
        return bytes.toByteArray();
    }

    static void movementCase(String name, byte[] inputBytes, boolean server, int id, boolean expectPacket,
                             boolean expectedGround) throws Exception {
        record(name, () -> {
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(inputBytes));
            Object value = readPacket.invoke(null, input, server);
            if ((value != null) != expectPacket) throw new AssertionError("Unexpected EOF behavior: " + name);
            if (value == null) return "null:remaining=" + input.available();
            if (!packetId.invoke(value).equals(id)
                || !field(value, "moving", "h").equals(id == 11 || id == 13)
                || !field(value, "rotating", "i").equals(id == 12 || id == 13)
                || !field(value, "onGround", "g").equals(expectedGround)) throw new AssertionError("Movement flags/ID changed");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            writePacket.invoke(null, value, new DataOutputStream(bytes));
            return "wire=" + hash(bytes.toByteArray()) + ":size=" + packetSize.invoke(value)
                + ":state=" + movementState(value) + ":remaining=" + input.available();
        });
    }

    static void movementCases() throws Exception {
        double[][] points = {{-12.5, 64, 65.62, 33.25}, {-0.0, 0, -0.0, 0},
            {0.125, -999, -999, -0.25}, {30000000, 127.999, 129.62, -30000000},
            {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.MIN_VALUE}};
        float[][] angles = {{180, -90}, {-0.0F, 0}, {721.25F, -360.5F}, {Float.MAX_VALUE, Float.MIN_VALUE},
            {Float.NaN, Float.POSITIVE_INFINITY}};
        for (int id = 10; id <= 13; id++) {
            for (int i = 0; i < points.length; i++) {
                for (int ground : new int[] {0, 1, 2, 127, 255}) {
                    byte[] wire = movementWire(id, points[i], angles[i], ground);
                    for (boolean server : new boolean[] {false, true}) {
                        movementCase("movement-" + id + "-" + i + "-" + ground + "-" + server,
                            wire, server, id, true, ground != 0);
                    }
                }
            }
            byte[] wire = movementWire(id, points[0], angles[0], 0);
            for (int length = 0; length < wire.length; length++) {
                for (boolean server : new boolean[] {false, true}) {
                    // Beta reads the final ground byte using read(), so -1 becomes true.
                    movementCase("movement-truncated-" + id + "-" + length + "-" + server,
                        Arrays.copyOf(wire, length), server, id, length == wire.length - 1, true);
                }
            }
        }
        // The common Packet13 constructor, using values from the two side-specific call sites.
        for (boolean correction : new boolean[] {false, true}) {
            record("movement-constructor-" + (correction ? "server-correction" : "client-ack"), () -> {
                double feet = 64, eyes = feet + (double) 1.62F;
                Object prototype = getPacket.invoke(null, 13);
                Object packet = prototype.getClass().getConstructor(double.class, double.class, double.class,
                    double.class, float.class, float.class, boolean.class)
                    .newInstance(-12.5, correction ? eyes : feet, correction ? feet : eyes, 33.25, 45F, -20F, false);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                writePacket.invoke(null, packet, new DataOutputStream(out));
                byte[] expected = movementWire(13, new double[] {-12.5, correction ? eyes : feet,
                    correction ? feet : eyes, 33.25}, new float[] {45F, -20F}, 0);
                if (!Arrays.equals(expected, out.toByteArray())) throw new AssertionError("Constructor coordinate order changed");
                return hash(out.toByteArray()) + ":" + movementState(packet);
            });
        }
    }

    static Field accessibleField(Class<?> type, String named, String original) throws Exception {
        Field result = type.getDeclaredField(namedNamespace ? named : original);
        result.setAccessible(true);
        return result;
    }

    static void accountingSnapshot(Class<?> packet, String name) throws Exception {
        record(name, () -> {
            java.util.Map<?, ?> counters = (java.util.Map<?, ?>) accessibleField(packet, "packetStats", "e").get(null);
            java.util.TreeMap<Integer, String> ordered = new java.util.TreeMap<Integer, String>();
            for (java.util.Map.Entry<?, ?> entry : counters.entrySet()) {
                Object counter = entry.getValue();
                ordered.put((Integer) entry.getKey(), accessibleField(counter.getClass(), "totalPackets", "a").get(counter)
                    + "/" + accessibleField(counter.getClass(), "totalBytes", "b").get(counter));
            }
            return "total=" + accessibleField(packet, "totalPacketsCount", "f").get(null) + ":" + ordered;
        });
    }

    static void accountingCases(Class<?> packet) throws Exception {
        accountingSnapshot(packet, "accounting-after-codecs");
        for (int value : new int[] {0, 1, -1, Integer.MAX_VALUE}) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);
            out.writeByte(23); out.writeInt(42); out.writeByte(1);
            out.writeInt(-32); out.writeInt(64); out.writeInt(96); out.writeInt(value);
            if (value > 0) { out.writeShort(1); out.writeShort(-2); out.writeShort(3); }
            packetCase("accounting-vehicle-" + value, bytes.toByteArray(), false);
        }
        for (int size : new int[] {0, 1, 255}) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);
            out.writeByte(131); out.writeShort(1); out.writeShort(2); out.writeByte(size); out.write(new byte[size]);
            packetCase("accounting-map-" + size, bytes.toByteArray(), false);
        }
        accountingSnapshot(packet, "accounting-after-size-quirks");
        packetCase("accounting-invalid", new byte[] {(byte)254}, true);
        packetCase("accounting-eof", new byte[] {3, 0, 1}, true);
        accountingSnapshot(packet, "accounting-after-rejections");
        record("accounting-detached-overflow", () -> {
            java.util.Map<?, ?> counters = (java.util.Map<?, ?>) accessibleField(packet, "packetStats", "e").get(null);
            Class<?> type = counters.values().iterator().next().getClass();
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object counter = constructor.newInstance();
            Field count = accessibleField(type, "totalPackets", "a"), bytes = accessibleField(type, "totalBytes", "b");
            Method add = type.getDeclaredMethod(namedNamespace ? "addPacket" : "a", int.class); add.setAccessible(true);
            add.invoke(counter, -1); add.invoke(counter, Integer.MAX_VALUE);
            if (count.getInt(counter) != 2 || bytes.getLong(counter) != 2147483646L) throw new AssertionError("Counter accumulation");
            count.setInt(counter, Integer.MAX_VALUE); bytes.setLong(counter, Long.MAX_VALUE);
            add.invoke(counter, 1);
            if (count.getInt(counter) != Integer.MIN_VALUE || bytes.getLong(counter) != Long.MIN_VALUE) throw new AssertionError("Counter overflow");
            return count.get(counter) + "/" + bytes.get(counter);
        });
    }

    static void registryCases(Class<?> packet) throws Exception {
        Method add = packet.getDeclaredMethod(namedNamespace ? "addIdClassMapping" : "a",
            int.class, boolean.class, boolean.class, Class.class);
        add.setAccessible(true);
        Object keepAlive = getPacket.invoke(null, 0);
        // Duplicate failures must leave registrations and direction policy unchanged.
        record("registry-duplicate-id", () -> {
            add.invoke(null, 0, false, false, String.class);
            throw new AssertionError("Duplicate ID accepted");
        });
        // Class names differ across namespaces; compare exception type, not its class-name text.
        record("registry-duplicate-class", () -> {
            try { add.invoke(null, 254, false, false, keepAlive.getClass()); }
            catch (InvocationTargetException error) {
                if (!(error.getCause() instanceof IllegalArgumentException)) throw error;
                return error.getCause().getClass().getName();
            }
            throw new AssertionError("Duplicate class accepted");
        });
        for (int id = -1; id <= 256; id++) {
            final int identifier = id;
            record("registry-lookup-" + id, () -> {
                Object value = getPacket.invoke(null, identifier);
                return value == null ? "null" : "id=" + packetId.invoke(value);
            });
        }
        for (int id = 0; id <= 255; id++) {
            for (boolean server : new boolean[] {false, true}) {
                final int identifier = id;
                final boolean direction = server;
                record("registry-direction-" + id + "-" + server, () -> {
                    // No payload: reject at the ID gate or observe the decoder's original EOF behavior.
                    DataInputStream input = new DataInputStream(new ByteArrayInputStream(new byte[] {(byte)identifier}));
                    Object value = readPacket.invoke(null, input, direction);
                    return value == null ? "null" : "id=" + packetId.invoke(value);
                });
            }
        }
    }

    static void watchableCases(ClassLoader loader, String namespace) throws Exception {
        boolean named = namespace.equals("named");
        Class<?> type = loader.loadClass(named ? "net.minecraft.src.WatchableObject"
            : namespace.equals("client") ? "ma" : "ht");
        Constructor<?> constructor = type.getConstructor(int.class, int.class, Object.class);
        Method getId = type.getMethod(named ? "getDataValueId" : "a");
        Method getType = type.getMethod(named ? "getObjectType" : "c");
        Method getObject = type.getMethod(named ? "getObject" : "b");
        Method setObject = type.getMethod(named ? "setObject" : "a", Object.class);
        Method setWatching = type.getMethod(named ? "setWatching" : "a", boolean.class);
        Field watching = type.getDeclaredField(named ? "isWatching" : "d");
        watching.setAccessible(true);
        Method getter = null;
        try { getter = type.getMethod(named ? "getWatching" : "d"); }
        catch (NoSuchMethodException absentOnClient) {
            if (namespace.equals("server")) throw absentOnClient;
        }
        final Method watchingGetter = getter;
        int[][] vectors = {{0, 0}, {1, 31}, {2, -1}, {3, 32}, {4, 127}, {5, 255},
            {6, Integer.MAX_VALUE}, {-1, Integer.MIN_VALUE}, {Integer.MAX_VALUE, -32}};
        for (int i = 0; i < vectors.length; i++) {
            final int objectType = vectors[i][0], id = vectors[i][1];
            Object original = new Object(), replacement = new Object();
            Object value = constructor.newInstance(objectType, id, original);
            for (int step = 0; step < 6; step++) {
                // setObject is a plain reference assignment, not a dirty-flag transition.
                if (step == 1) setWatching.invoke(value, false);
                if (step == 2) setObject.invoke(value, replacement);
                if (step == 3) setObject.invoke(value, new Object[] {null});
                if (step == 4) setWatching.invoke(value, true);
                if (step == 5) setObject.invoke(value, original);
                final Object expected = step < 2 || step == 5 ? original : step == 2 ? replacement : null;
                final boolean expectedWatching = step == 0 || step >= 4;
                record("watchable-" + i + "-" + step, () -> {
                    if (!getId.invoke(value).equals(id) || !getType.invoke(value).equals(objectType)
                        || getObject.invoke(value) != expected || watching.getBoolean(value) != expectedWatching)
                        throw new AssertionError("WatchableObject state/identity changed");
                    if (watchingGetter != null && !watchingGetter.invoke(value).equals(expectedWatching))
                        throw new AssertionError("Dirty getter differs from stored state");
                    return "type=" + objectType + ":id=" + id + ":identity=true:null=" + (expected == null)
                        + ":watching=" + expectedWatching;
                });
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) throw new IllegalArgumentException("jar namespace report");
        boolean named = args[1].equals("named");
        namedNamespace = named;
        String className = named ? "net.minecraft.src.Packet" : args[1].equals("client") ? "ki" : "gt";
        try (URLClassLoader loader = new URLClassLoader(new URL[] {new File(args[0]).toURI().toURL()}, null);
             PrintWriter writer = new PrintWriter(args[2], "UTF-8")) {
            report = writer;
            Class<?> packet = loader.loadClass(className);
            writeString = packet.getMethod(named ? "writeString" : "a", String.class, DataOutputStream.class);
            readString = packet.getMethod(named ? "readString" : "a", DataInputStream.class, int.class);
            readPacket = packet.getMethod(named ? "readPacket" : "a", DataInputStream.class, boolean.class);
            writePacket = packet.getMethod(named ? "writePacket" : "a", packet, DataOutputStream.class);
            getPacket = packet.getMethod(named ? "getNewPacket" : "a", int.class);
            packetId = packet.getMethod(named ? "getPacketId" : args[1].equals("client") ? "c" : "b");
            packetSize = packet.getMethod(named ? "getPacketSize" : "a");
            String[] values = {"", "Origins", "A\u0000\u65e5\u672c\ud83d\ude00", "\ud800", "\udfff",
                repeat(32), repeat(33), repeat(100), repeat(101), repeat(119), repeat(120), repeat(32767), repeat(32768)};
            for (int i = 0; i < values.length; i++) {
                final String value = values[i];
                record("write-string-" + i, () -> {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    writeString.invoke(null, value, new DataOutputStream(bytes));
                    if (!Arrays.equals(bytes.toByteArray(), encode(value))) throw new AssertionError("UTF-16 wire bytes changed");
                    return hash(bytes.toByteArray());
                });
                readCase("read-string-" + i, encode(value), 32767);
            }
            readCase("negative-length", new byte[] {-1, -1}, 32);
            readCase("over-limit", encode(repeat(33)), 32);
            readCase("negative-maximum", encode(""), -1);
            byte[] shortString = encode("A\u65e5");
            for (int length = 0; length < shortString.length; length++) readCase("truncated-string-" + length, Arrays.copyOf(shortString, length), 32);
            for (int id : new int[] {0, 2, 3, 255}) {
                int count = id == 0 ? 1 : values.length;
                for (int i = 0; i < count; i++) {
                    final int identifier = id, index = i;
                    record("construct-" + id + "-" + i, () -> {
                        Object instance = getPacket.invoke(null, identifier);
                        if (identifier != 0) instance = instance.getClass().getConstructor(String.class).newInstance(values[index]);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        writePacket.invoke(null, instance, new DataOutputStream(bytes));
                        return hash(bytes.toByteArray()) + ":size=" + packetSize.invoke(instance);
                    });
                    byte[] payload = id == 0 ? new byte[0] : encode(values[i]);
                    byte[] wire = new byte[payload.length + 1];
                    wire[0] = (byte) id;
                    System.arraycopy(payload, 0, wire, 1, payload.length);
                    for (boolean server : new boolean[] {false, true}) packetCase("packet-" + id + "-" + i + "-" + server, wire, server);
                }
            }
            for (boolean server : new boolean[] {false, true}) {
                packetCase("empty-" + server, new byte[0], server);
                packetCase("unknown-id-" + server, new byte[] {(byte)254}, server);
                for (int id : new int[] {4, 7}) {
                    byte[] wire = new byte[10]; wire[0] = (byte)id;
                    packetCase("direction-" + id + "-" + server, wire, server);
                }
                for (int length = 1; length < 7; length++) {
                    byte[] wire = new byte[] {3, 0, 2, 0, 65, 0, 66};
                    packetCase("truncated-packet-" + length + "-" + server, Arrays.copyOf(wire, length), server);
                }
            }
            movementCases();
            accountingCases(packet);
            registryCases(packet);
            watchableCases(loader, args[1]);
            if (report.checkError()) throw new IOException("Report write failed");
            System.out.println("PASS: completed " + cases + " codec observations");
        }
    }
}
