package net.minecraft.network.protocol.game;

import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class ClientboundContainerSetContentPacket implements Packet<ClientGamePacketListener> {
   private final int containerId;
   private final int stateId;
   private final List<ItemStack> items;
   private final ItemStack carriedItem;

   public ClientboundContainerSetContentPacket(int var1, int var2, NonNullList<ItemStack> var3, ItemStack var4) {
      this.containerId = â˜ƒ;
      this.stateId = â˜ƒ;
      this.items = NonNullList.<ItemStack>withSize(â˜ƒ.size(), ItemStack.EMPTY);

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         this.items.set(â˜ƒ, â˜ƒ.get(â˜ƒ).copy());
      }

      this.carriedItem = â˜ƒ.copy();
   }

   public ClientboundContainerSetContentPacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readUnsignedByte();
      this.stateId = â˜ƒ.readVarInt();
      this.items = â˜ƒ.readCollection(NonNullList::createWithCapacity, FriendlyByteBuf::readItem);
      this.carriedItem = â˜ƒ.readItem();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
      â˜ƒ.writeVarInt(this.stateId);
      â˜ƒ.writeCollection(this.items, FriendlyByteBuf::writeItem);
      â˜ƒ.writeItem(this.carriedItem);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleContainerContent(this);
   }

   public int getContainerId() {
      return this.containerId;
   }

   public List<ItemStack> getItems() {
      return this.items;
   }

   public ItemStack getCarriedItem() {
      return this.carriedItem;
   }

   public int getStateId() {
      return this.stateId;
   }
}
