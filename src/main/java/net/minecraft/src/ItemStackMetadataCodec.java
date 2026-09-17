package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/** Connects shared metadata to the remaining side-local item model. */
final class ItemStackMetadataCodec implements DataWatcher.ItemCodec {
    static final ItemStackMetadataCodec INSTANCE = new ItemStackMetadataCodec();

    public Object toHost(Object value) { return ItemStack.fromPacket((ItemStackData<?>)value); }

    public Class itemClass() { return ItemStack.class; }

    public Object read(DataInputStream input) throws IOException {
        return new ItemStack(input.readShort(), input.readByte(), input.readShort());
    }

    public void write(Object value, DataOutputStream output) throws IOException {
        ItemStack item = (ItemStack)value;
        output.writeShort(item.getItem().shiftedIndex);
        output.writeByte(item.stackSize);
        output.writeShort(item.getItemDamage());
    }
}
