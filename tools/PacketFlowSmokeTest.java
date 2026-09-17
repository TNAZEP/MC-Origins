package net.minecraft.src;

import java.io.*;
import java.util.Arrays;

/** Exercise shared packet values and the receiving host's gameplay conversions. */
public final class PacketFlowSmokeTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static Packet transfer(Packet outgoing, boolean server) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Packet.writePacket(outgoing, new DataOutputStream(bytes));
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        Packet received = Packet.readPacket(input, server);
        check(received != null && input.available() == 0, "packet framing");
        return received;
    }

    public static void main(String[] args) throws Exception {
        Class.forName("net.minecraft.src.StatList");
        ItemStack stack = new ItemStack(1, 12, 2);
        Packet103SetSlot slot = new Packet103SetSlot(0, 9, stack);
        stack.stackSize = 4;
        Packet103SetSlot received = (Packet103SetSlot)transfer(slot, false);
        ItemStack restored = ItemStack.fromPacket(received.myItemStack);
        check(restored.stackSize == 12 && restored.getItemDamage() == 2 && restored.getItem() != null,
            "slot snapshot or gameplay conversion");
        Packet104WindowItems items = (Packet104WindowItems)transfer(
            new Packet104WindowItems(0, Arrays.asList(stack, null, restored)), false);
        ItemStack[] inventory = ItemStack.fromPacket(items.itemStack);
        check(inventory.length == 3 && inventory[1] == null && inventory[2].stackSize == 12,
            "inventory/null slot conversion");
        Packet15Place place = (Packet15Place)transfer(new Packet15Place(1, 64, -2, 3, stack), true);
        check(place.zPosition == -2 && ItemStack.areItemStacksEqual(place.itemStack, stack), "place item");
        Packet102WindowClick click = (Packet102WindowClick)transfer(
            new Packet102WindowClick(0, 9, 1, true, stack, (short)17), true);
        check(click.action == 17 && click.field_27050_f && ItemStack.areItemStacksEqual(click.itemStack, stack),
            "click transaction");
        DataWatcher watcher = new DataWatcher(ItemStackMetadataCodec.INSTANCE);
        watcher.addObject(3, stack);
        watcher.updateObject(3, restored);
        Packet40EntityMetadata metadata = (Packet40EntityMetadata)transfer(new Packet40EntityMetadata(42, watcher), false);
        DataWatcher target = new DataWatcher(ItemStackMetadataCodec.INSTANCE);
        target.addObject(3, stack);
        target.updateWatchedObjectsFromList(metadata.func_21047_b());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        target.writeWatchableObjects(new DataOutputStream(bytes)); // Side codec requires an actual ItemStack.
        check(!target.hasObjectChanged(), "received metadata dirtied server state");
        EntityPig pig = new EntityPig(null);
        pig.setPosition(2.5D, 64, -1.5D);
        Packet24MobSpawn mob = (Packet24MobSpawn)transfer(PacketFactory.createPacket24MobSpawn(pig), false);
        check(mob.xPosition == 80 && mob.zPosition == -48 && mob.getMetadata() != null, "mob producer");
        final boolean[] handled = {false};
        received.processPacket(new NetHandler() {
            public boolean isServerHandler() { return false; }
            public void func_20088_a(Packet103SetSlot packet) { handled[0] = packet.windowId == 0; }
        });
        check(handled[0], "typed handler dispatch");
        System.out.println("PASS: packet framing, item snapshots, host inventory conversion, metadata, mob producer and dispatch");
    }
}
