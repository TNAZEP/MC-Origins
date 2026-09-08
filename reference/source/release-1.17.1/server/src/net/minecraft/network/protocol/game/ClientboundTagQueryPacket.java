package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundTagQueryPacket implements Packet<ClientGamePacketListener> {
   private final int transactionId;
   @Nullable
   private final CompoundTag tag;

   public ClientboundTagQueryPacket(int var1, @Nullable CompoundTag var2) {
      this.transactionId = â˜ƒ;
      this.tag = â˜ƒ;
   }

   public ClientboundTagQueryPacket(FriendlyByteBuf var1) {
      this.transactionId = â˜ƒ.readVarInt();
      this.tag = â˜ƒ.readNbt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.transactionId);
      â˜ƒ.writeNbt(this.tag);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleTagQueryPacket(this);
   }

   public int getTransactionId() {
      return this.transactionId;
   }

   @Nullable
   public CompoundTag getTag() {
      return this.tag;
   }

   @Override
   public boolean isSkippable() {
      return true;
   }
}
