package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class ServerboundSetCreativeModeSlotPacket implements Packet<ServerGamePacketListener> {
   private final int slotNum;
   private final ItemStack itemStack;

   public ServerboundSetCreativeModeSlotPacket(int var1, ItemStack var2) {
      this.slotNum = â˜ƒ;
      this.itemStack = â˜ƒ.copy();
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSetCreativeModeSlot(this);
   }

   public ServerboundSetCreativeModeSlotPacket(FriendlyByteBuf var1) {
      this.slotNum = â˜ƒ.readShort();
      this.itemStack = â˜ƒ.readItem();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeShort(this.slotNum);
      â˜ƒ.writeItem(this.itemStack);
   }

   public int getSlotNum() {
      return this.slotNum;
   }

   public ItemStack getItem() {
      return this.itemStack;
   }
}
