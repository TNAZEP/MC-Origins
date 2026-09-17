import java.io.*;
import java.nio.file.*;
import net.minecraft.src.*;

/** Save metadata and region-file reopen check in a fresh disposable directory. */
public final class WorldSaveSmokeTest {
    public static final class SavedCounter extends MapDataBase {
        int value;
        public SavedCounter(String name) { super(name); }
        public void readFromNBT(NBTTagCompound tag) { value = tag.getInteger("value"); }
        public void writeToNBT(NBTTagCompound tag) { tag.setInteger("value", value); }
    }

    public static void main(String[] args) throws Exception {
        java.nio.file.Path directory = Files.createTempDirectory(Paths.get("run/smoke"), "world-save-");
        WorldInfo world = new WorldInfo(8675309L, "Origins smoke");
        world.setSpawn(-10, 64, 20);
        world.setWorldTime(1234);
        world.setRaining(true);
        NBTTagCompound player = new NBTTagCompound();
        player.setInteger("Dimension", -1);
        NBTTagCompound saved = world.getNBTTagCompoundWithPlayer(player);
        File regionPath = directory.resolve("r.0.0.mcr").toFile();
        RegionFile region = new RegionFile(regionPath);
        try (DataOutputStream output = region.getChunkDataOutputStream(0, 0)) {
            CompressedStreamTools.write(saved, output);
        }
        region.close();
        region = new RegionFile(regionPath);
        try (DataInputStream input = region.getChunkDataInputStream(0, 0)) {
            WorldInfo loaded = new WorldInfo(CompressedStreamTools.read(input));
            if (loaded.getRandomSeed() != 8675309L || loaded.getSpawnX() != -10
                || loaded.getSpawnY() != 64 || loaded.getSpawnZ() != 20
                || loaded.getWorldTime() != 1234 || !loaded.getRaining()
                || loaded.getDimension() != -1 || !loaded.getWorldName().equals("Origins smoke"))
                throw new AssertionError("World metadata failed region save/reopen");
        } finally {
            region.close();
        }
        MapFileAccess files = name -> directory.resolve(name + ".dat").toFile();
        MapStorage storage = new MapStorage(files);
        SavedCounter counter = new SavedCounter("map_0");
        counter.value = 37;
        counter.markDirty();
        storage.setData("map_0", counter);
        storage.saveAllData();
        if (counter.isDirty() || storage.getUniqueDataId("map") != 0)
            throw new AssertionError("Map save/first ID");
        MapStorage reopened = new MapStorage(files);
        SavedCounter restored = (SavedCounter)reopened.loadData(SavedCounter.class, "map_0");
        if (restored == null || restored.value != 37 || reopened.getUniqueDataId("map") != 1)
            throw new AssertionError("Map data/ID counter reopen");
        System.out.println("PASS: world metadata, region, map data and ID counter save/reopen in " + directory);
    }
}
