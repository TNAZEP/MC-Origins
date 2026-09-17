package net.minecraft.src;

import java.io.*;
import java.util.List;

/** Functional metadata synchronization using the actual side's item adapter. */
public final class MetadataSmokeTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    public static void main(String[] args) throws Exception {
        // Both game entry points initialize StatList before the item/block graph.
        Class.forName("net.minecraft.src.StatList");
        DataWatcher source = new DataWatcher(ItemStackMetadataCodec.INSTANCE);
        source.addObject(0, Byte.valueOf((byte)0));
        source.addObject(1, "Origins");
        source.addObject(2, new ChunkCoordinates(10, 64, -20));
        source.addObject(3, new ItemStack(1, 12, 2));
        source.updateObject(0, Byte.valueOf((byte)1));
        check(source.hasObjectChanged(), "Update did not mark metadata dirty");
        List changed = source.getChangedObjects();
        check(!source.hasObjectChanged() && source.getChangedObjects() == null, "Dirty list did not drain");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataWatcher.writeObjectsInListToStream(changed, new DataOutputStream(bytes), ItemStackMetadataCodec.INSTANCE);
        List received = DataWatcher.readWatchableObjects(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())),
            ItemStackMetadataCodec.INSTANCE);
        DataWatcher target = new DataWatcher(ItemStackMetadataCodec.INSTANCE);
        target.addObject(0, Byte.valueOf((byte)0));
        target.addObject(1, "");
        target.updateWatchedObjectsFromList(received);
        check(target.getWatchableObjectByte(0) == 1 && target.getWatchableObjectString(1).equals("Origins"),
            "Received metadata not applied");
        check(!target.hasObjectChanged(), "Receiver incorrectly marked server dirty state");
        boolean itemSeen = false, positionSeen = false;
        for (Object entry : received) {
            WatchableObject value = (WatchableObject)entry;
            if (value.getDataValueId() == 2) {
                positionSeen = new ChunkCoordinates(10, 64, -20).equals(value.getObject());
            }
            if (value.getDataValueId() == 3) {
                ItemStack item = (ItemStack)value.getObject();
                itemSeen = item.itemID == 1 && item.stackSize == 12 && item.getItemDamage() == 2;
            }
        }
        check(itemSeen && positionSeen, "Item/coordinate payload did not survive synchronization");
        bytes.reset();
        source.writeWatchableObjects(new DataOutputStream(bytes));
        check(DataWatcher.readWatchableObjects(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())),
            ItemStackMetadataCodec.INSTANCE).size() == 4, "Full metadata snapshot failed");
        System.out.println("PASS: " + OriginsVersion.DISPLAY_NAME + "; metadata dirty-list, receiver, item and position payloads");
    }
}
