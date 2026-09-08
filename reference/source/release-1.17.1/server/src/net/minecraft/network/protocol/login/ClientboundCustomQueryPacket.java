package net.minecraft.network.protocol.login;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public class ClientboundCustomQueryPacket implements Packet<ClientLoginPacketListener> {
   private static final int MAX_PAYLOAD_SIZE = 1048576;
   private final int transactionId;
   private final ResourceLocation identifier;
   private final FriendlyByteBuf data;

   public ClientboundCustomQueryPacket(int var1, ResourceLocation var2, FriendlyByteBuf var3) {
      this.transactionId = â˜ƒ;
      this.identifier = â˜ƒ;
      this.data = â˜ƒ;
   }

   public ClientboundCustomQueryPacket(FriendlyByteBuf var1) {
      this.transactionId = â˜ƒ.readVarInt();
      this.identifier = â˜ƒ.readResourceLocation();
      int â˜ƒ = â˜ƒ.readableBytes();
      if (â˜ƒ >= 0 && â˜ƒ <= 1048576) {
         this.data = new FriendlyByteBuf(â˜ƒ.readBytes(â˜ƒ));
      } else {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.transactionId);
      â˜ƒ.writeResourceLocation(this.identifier);
      â˜ƒ.writeBytes(this.data.copy());
   }

   public void handle(ClientLoginPacketListener var1) {
      â˜ƒ.handleCustomQuery(this);
   }

   public int getTransactionId() {
      return this.transactionId;
   }

   public ResourceLocation getIdentifier() {
      return this.identifier;
   }

   public FriendlyByteBuf getData() {
      return this.data;
   }
}
