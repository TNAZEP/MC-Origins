import java.io.*;
import net.minecraft.src.*;

/** Practical save-data check; runs against the shared classes without launching the client. */
public final class SharedNbtSmokeTest {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("existing level.dat path required");
        NBTTagCompound level = CompressedStreamTools.readCompressed(new FileInputStream(args[0]));
        NBTTagCompound data = level.getCompoundTag("Data");
        check(data.hasKey("RandomSeed") && data.hasKey("SpawnX"), "Missing world metadata");

        NBTTagCompound player = new NBTTagCompound();
        player.setString("Name", "Origins player");
        player.setShort("Health", (short)20);
        player.setByteArray("Flags", new byte[] {1, 2, 3});
        NBTTagCompound item = new NBTTagCompound();
        item.setShort("id", (short)1);
        item.setByte("Count", (byte)64);
        item.setShort("Damage", (short)0);
        NBTTagList inventory = new NBTTagList();
        inventory.setTag(item);
        player.setTag("Inventory", inventory);
        level.setCompoundTag("TestPlayer", player);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        CompressedStreamTools.writeGzippedCompoundToOutputStream(level, bytes);
        NBTTagCompound loaded = CompressedStreamTools.readCompressed(new ByteArrayInputStream(bytes.toByteArray()));
        NBTTagCompound loadedData = loaded.getCompoundTag("Data");
        check(loadedData.getLong("RandomSeed") == data.getLong("RandomSeed"), "World seed lost");
        for (String axis : new String[] {"SpawnX", "SpawnY", "SpawnZ"})
            check(loadedData.getInteger(axis) == data.getInteger(axis), "Spawn coordinate lost: " + axis);
        NBTTagCompound loadedPlayer = loaded.getCompoundTag("TestPlayer");
        check(loadedPlayer.getString("Name").equals("Origins player"), "Player name lost");
        check(loadedPlayer.getShort("Health") == 20, "Player health lost");
        check(java.util.Arrays.equals(loadedPlayer.getByteArray("Flags"), new byte[] {1, 2, 3}), "Byte array lost");
        NBTTagList loadedInventory = loadedPlayer.getTagList("Inventory");
        check(loadedInventory.tagCount() == 1, "Inventory list lost");
        NBTTagCompound loadedItem = (NBTTagCompound)loadedInventory.tagAt(0);
        check(loadedItem.getShort("id") == 1 && loadedItem.getByte("Count") == 64
            && loadedItem.getShort("Damage") == 0, "Inventory item changed");
        // Region chunk storage uses the uncompressed DataInput/DataOutput API.
        bytes.reset();
        CompressedStreamTools.write(loaded, new DataOutputStream(bytes));
        check(CompressedStreamTools.read(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())))
            .getCompoundTag("Data").getLong("RandomSeed") == data.getLong("RandomSeed"), "Raw NBT read/write failed");
        System.out.println("PASS: existing level data, compressed/raw NBT and nested inventory read/write");
    }
}
