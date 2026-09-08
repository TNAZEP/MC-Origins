package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundEntityTagQuery implements Packet<ServerGamePacketListener> {
   private final int transactionId;
   private final int entityId;

   public ServerboundEntityTagQuery(int var1, int var2) {
      this.transactionId = â˜ƒ;
      this.entityId = â˜ƒ;
   }

   public ServerboundEntityTagQuery(FriendlyByteBuf var1) {
      this.transactionId = â˜ƒ.readVarInt();
      this.entityId = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.transactionId);
      â˜ƒ.writeVarInt(this.entityId);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleEntityTagQuery(this);
   }

   public int getTransactionId() {
      return this.transactionId;
   }

   public int getEntityId() {
      return this.entityId;
   }
}
