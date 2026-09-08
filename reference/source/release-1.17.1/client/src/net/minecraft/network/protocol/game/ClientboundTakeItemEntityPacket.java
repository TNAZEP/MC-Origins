package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundTakeItemEntityPacket implements Packet<ClientGamePacketListener> {
   private final int itemId;
   private final int playerId;
   private final int amount;

   public ClientboundTakeItemEntityPacket(int var1, int var2, int var3) {
      this.itemId = â˜ƒ;
      this.playerId = â˜ƒ;
      this.amount = â˜ƒ;
   }

   public ClientboundTakeItemEntityPacket(FriendlyByteBuf var1) {
      this.itemId = â˜ƒ.readVarInt();
      this.playerId = â˜ƒ.readVarInt();
      this.amount = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.itemId);
      â˜ƒ.writeVarInt(this.playerId);
      â˜ƒ.writeVarInt(this.amount);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleTakeItemEntity(this);
   }

   public int getItemId() {
      return this.itemId;
   }

   public int getPlayerId() {
      return this.playerId;
   }

   public int getAmount() {
      return this.amount;
   }
}
