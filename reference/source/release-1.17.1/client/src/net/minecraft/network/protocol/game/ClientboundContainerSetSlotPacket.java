package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class ClientboundContainerSetSlotPacket implements Packet<ClientGamePacketListener> {
   public static final int CARRIED_ITEM = -1;
   public static final int PLAYER_INVENTORY = -2;
   private final int containerId;
   private final int stateId;
   private final int slot;
   private final ItemStack itemStack;

   public ClientboundContainerSetSlotPacket(int var1, int var2, int var3, ItemStack var4) {
      this.containerId = â˜ƒ;
      this.stateId = â˜ƒ;
      this.slot = â˜ƒ;
      this.itemStack = â˜ƒ.copy();
   }

   public ClientboundContainerSetSlotPacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readByte();
      this.stateId = â˜ƒ.readVarInt();
      this.slot = â˜ƒ.readShort();
      this.itemStack = â˜ƒ.readItem();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
      â˜ƒ.writeVarInt(this.stateId);
      â˜ƒ.writeShort(this.slot);
      â˜ƒ.writeItem(this.itemStack);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleContainerSetSlot(this);
   }

   public int getContainerId() {
      return this.containerId;
   }

   public int getSlot() {
      return this.slot;
   }

   public ItemStack getItem() {
      return this.itemStack;
   }

   public int getStateId() {
      return this.stateId;
   }
}
