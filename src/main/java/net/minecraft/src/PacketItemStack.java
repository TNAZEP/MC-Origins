package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/** Decoded Beta item payload; acquiring gameplay callbacks belongs to the receiving host. */
public final class PacketItemStack extends ItemStackData<PacketItemStack> {
    public PacketItemStack(int id, int count, int damage) {
        super(id, count, damage);
    }

    protected PacketItemStack createStack(int id, int count, int damage) {
        return new PacketItemStack(id, count, damage);
    }

    public static final DataWatcher.ItemCodec METADATA_CODEC = new DataWatcher.ItemCodec() {
        public Class itemClass() { return PacketItemStack.class; }

        public Object read(DataInputStream input) throws IOException {
            return new PacketItemStack(input.readShort(), input.readByte(), input.readShort());
        }

        public void write(Object value, DataOutputStream output) throws IOException {
            ItemStackData<?> item = (ItemStackData<?>)value;
            output.writeShort(item.itemID);
            output.writeByte(item.stackSize);
            output.writeShort(item.getItemDamage());
        }
    };
}
