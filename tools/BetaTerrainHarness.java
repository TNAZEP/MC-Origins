import java.io.File;
import java.io.PrintWriter;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.MessageDigest;
import java.util.List;
import java.util.Random;

/** Test-only adapter: no game classes or libraries on the compiler classpath. */
public final class BetaTerrainHarness {
    private static boolean original;
    private static ClassLoader loader;

    private static String name(String named, String obfuscated) {
        return original ? obfuscated : named;
    }

    private static Class<?> type(String named, String obfuscated) throws Exception {
        return loader.loadClass(name("net.minecraft.src." + named, obfuscated));
    }

    // The superclass implements exactly java.util.Random's algorithm. Only observe next(bits).
    static final class TracedRandom extends Random {
        final MessageDigest digest;
        long calls;

        TracedRandom(long seed) throws Exception {
            super(seed);
            digest = MessageDigest.getInstance("SHA-256");
        }

        protected int next(int bits) {
            int value = super.next(bits);
            calls++;
            digest.update((byte) bits);
            for (int shift = 24; shift >= 0; shift -= 8) {
                digest.update((byte) (value >>> shift));
            }
            return value;
        }
    }

    private static void writeRaw(DataOutputStream out, Object instance, Object provider,
                                 int dimension, int x, int z) throws Exception {
        Field generatorField = provider.getClass().getDeclaredField(name("serverChunkGenerator", "d"));
        generatorField.setAccessible(true);
        Object generator = generatorField.get(provider);
        Object chunk = type("IChunkProvider", "bl").getMethod(name("provideChunk", "b"), int.class, int.class)
            .invoke(generator, x, z);
        Class<?> chunkType = type("Chunk", "hi");
        if ((Boolean) chunkType.getField(name("isTerrainPopulated", "n")).get(chunk)
            || (Integer) chunkType.getField(name("xPosition", "j")).get(chunk) != x
            || (Integer) chunkType.getField(name("zPosition", "k")).get(chunk) != z) {
            throw new IllegalStateException("Unexpected raw chunk coordinates/population");
        }
        byte[] blocks = (byte[]) chunkType.getField(name("blocks", "b")).get(chunk);
        Object nibble = chunkType.getField(name("data", "e")).get(chunk);
        byte[] metadata = (byte[]) type("NibbleArray", "ob").getField(name("data", "a")).get(nibble);
        if (blocks.length != 32768 || metadata.length != 16384) throw new IllegalStateException("Raw array size");
        out.writeInt(dimension == 0 ? 0 : -1);
        out.writeInt(x);
        out.writeInt(z);
        out.write(blocks);
        out.write(metadata);

        // Separate explicit 16x16 manager query after generation. Preserve Beta's array order.
        Object manager = type("World", "dj").getMethod(name("getWorldChunkManager", "a")).invoke(instance);
        Class<?> managerType = type("WorldChunkManager", "ph"), biomeType = type("BiomeGenBase", "gs");
        Object[] biomes = (Object[]) managerType.getMethod(name("loadBlockGeneratorData", "a"),
            Array.newInstance(biomeType, 0).getClass(), int.class, int.class, int.class, int.class)
            .invoke(manager, null, x * 16, z * 16, 16, 16);
        String[] biomeFields = {"rainforest", "swampland", "seasonalForest", "forest", "savanna", "shrubland",
            "taiga", "desert", "plains", "iceDesert", "tundra", "hell", "sky"};
        if (biomes.length != 256) throw new IllegalStateException("Biome array size");
        for (Object biome : biomes) {
            int index = 0;
            while (index < biomeFields.length && biomeType.getField(name(biomeFields[index],
                    String.valueOf((char) ('a' + index)))).get(null) != biome) index++;
            if (index == biomeFields.length) throw new IllegalStateException("Unknown biome singleton");
            out.writeByte(index); // Adapter identities, not game registry IDs.
        }
        for (String[] fieldNames : new String[][] {{"temperature", "a"}, {"humidity", "b"}}) {
            double[] values = (double[]) managerType.getField(name(fieldNames[0], fieldNames[1])).get(manager);
            if (values.length != 256) throw new IllegalStateException("Climate array size");
            for (double value : values) out.writeLong(Double.doubleToRawLongBits(value));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 7) throw new IllegalArgumentException("jar namespace output seed rngSeed requests mode");
        boolean raw = args[6].equals("raw");
        if (!raw && !args[6].equals("saved")) throw new IllegalArgumentException("mode");
        original = args[1].equals("original");
        if (!original && !args[1].equals("named")) throw new IllegalArgumentException("namespace");
        File output = new File(args[2]);
        long seed = Long.parseLong(args[3]);
        long rngSeed = Long.parseLong(args[4]);
        List<String> requests = Files.readAllLines(new File(args[5]).toPath(), StandardCharsets.UTF_8);
        if (requests.isEmpty()) throw new IllegalArgumentException("Empty requests");
        try (URLClassLoader isolated = new URLClassLoader(new URL[] {new File(args[0]).toURI().toURL()}, null);
             PrintWriter trace = new PrintWriter(new File(output, "requests.tsv"), "UTF-8");
             DataOutputStream rawOutput = raw ? new DataOutputStream(new BufferedOutputStream(
                 new FileOutputStream(new File(output, "raw-terrain.bin")))) : null) {
            if (raw) {
                rawOutput.writeInt(0x4f524157); // ORAW
                rawOutput.writeInt(1);
                rawOutput.writeInt(requests.size() * 2);
            }
            loader = isolated;
            Class<?> world = type("World", "dj"), serverWorld = type("WorldServer", "dp");
            Class<?> handlerType = type("ISaveHandler", "om");
            Class<?> server = loader.loadClass("net.minecraft.server.MinecraftServer");
            Object handler = type("SaveOldDir", "ie").getConstructor(File.class, String.class, boolean.class)
                .newInstance(output, "world", true);
            Object overworld = serverWorld.getConstructor(server, handlerType, String.class, int.class, long.class)
                .newInstance(null, handler, "world", 0, seed);
            Object nether = type("WorldServerMulti", "eg")
                .getConstructor(server, handlerType, String.class, int.class, long.class, serverWorld)
                .newInstance(null, handler, "world", -1, seed, overworld);
            Class<?> providerType = type("ChunkProviderServer", "he");
            Field loaded = providerType.getDeclaredField(name("field_727_f", "g"));
            loaded.setAccessible(true);
            Method load = providerType.getMethod(name("prepareChunk", "c"), int.class, int.class);
            Method light = world.getMethod(name("updatingLighting", "f"));
            Method time = world.getMethod(name("getWorldTime", "m"));
            Method save = world.getMethod(name("saveWorld", "a"), boolean.class, type("IProgressUpdate", "pj"));
            trace.println("dimension\tx\tz\tworld_rng_next_calls\tlighting_drain_calls");
            Object[] worlds = {overworld, nether};
            for (int dimension = 0; dimension < worlds.length; dimension++) {
                Object instance = worlds[dimension];
                Object provider = serverWorld.getField(name("chunkProviderServer", "C")).get(instance);
                if (!((List<?>) loaded.get(provider)).isEmpty()) throw new IllegalStateException("Constructor loaded chunks before RNG control");
                if (!world.getMethod(name("getRandomSeed", "l")).invoke(instance).equals(seed)) throw new IllegalStateException("Seed mismatch");
                Object initialTime = time.invoke(instance);
                TracedRandom random = new TracedRandom(rngSeed);
                world.getField(name("rand", "r")).set(instance, random);
                // Explicit test schedule, with the lighting drain used by Beta initWorld.
                // Population remains driven by loadChunk's original neighbor-availability rules.
                for (String request : requests) {
                    String[] coordinates = request.split("\t", -1);
                    if (coordinates.length != 2) throw new IllegalArgumentException("Invalid request");
                    int x = Integer.parseInt(coordinates[0]), z = Integer.parseInt(coordinates[1]);
                    if (raw) {
                        writeRaw(rawOutput, instance, provider, dimension, x, z);
                        if (!((List<?>) loaded.get(provider)).isEmpty()) throw new IllegalStateException("Raw generation loaded world chunks");
                        trace.println(dimension + "\t" + x + "\t" + z + "\t" + random.calls + "\t0");
                        continue;
                    }
                    load.invoke(provider, x, z);
                    int drains = 1;
                    while ((Boolean) light.invoke(instance)) {
                        if (++drains > 1000000) throw new IllegalStateException("Lighting did not drain");
                    }
                    trace.println(dimension + "\t" + x + "\t" + z + "\t" + random.calls + "\t" + drains);
                }
                if (!initialTime.equals(time.invoke(instance))) throw new IllegalStateException("World time advanced");
                if (!raw) save.invoke(instance, true, null);
                StringBuilder hash = new StringBuilder();
                for (byte b : random.digest.digest()) hash.append(String.format("%02x", b & 255));
                System.out.println("dimension=" + dimension + " loaded=" + ((List<?>) loaded.get(provider)).size()
                    + " world_rng_calls=" + random.calls + " rng_sha256=" + hash + " ticks=0 time=" + initialTime);
            }
            handlerType.getMethod(name("func_22093_e", "e")).invoke(handler);
            if (trace.checkError()) throw new IllegalStateException("Cannot write request trace");
        }
    }
}
