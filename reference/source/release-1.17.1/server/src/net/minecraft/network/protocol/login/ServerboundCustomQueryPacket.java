package net.minecraft.network.protocol.login;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundCustomQueryPacket implements Packet<ServerLoginPacketListener> {
   private static final int MAX_PAYLOAD_SIZE = 1048576;
   private final int transactionId;
   private final FriendlyByteBuf data;

   public ServerboundCustomQueryPacket(int var1, @Nullable FriendlyByteBuf var2) {
      this.transactionId = â˜ƒ;
      this.data = â˜ƒ;
   }

   public ServerboundCustomQueryPacket(FriendlyByteBuf var1) {
      this.transactionId = â˜ƒ.readVarInt();
      if (â˜ƒ.readBoolean()) {
         int â˜ƒ = â˜ƒ.readableBytes();
         if (â˜ƒ < 0 || â˜ƒ > 1048576) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
         }

         this.data = new FriendlyByteBuf(â˜ƒ.readBytes(â˜ƒ));
      } else {
         this.data = null;
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.transactionId);
      if (this.data != null) {
         â˜ƒ.writeBoolean(true);
         â˜ƒ.writeBytes(this.data.copy());
      } else {
         â˜ƒ.writeBoolean(false);
      }
   }

   public void handle(ServerLoginPacketListener var1) {
      â˜ƒ.handleCustomQueryPacket(this);
   }

   public int getTransactionId() {
      return this.transactionId;
   }

   public FriendlyByteBuf getData() {
      return this.data;
   }
}
