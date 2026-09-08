package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public class ServerboundCustomPayloadPacket implements Packet<ServerGamePacketListener> {
   private static final int MAX_PAYLOAD_SIZE = 32767;
   public static final ResourceLocation BRAND = new ResourceLocation("brand");
   private final ResourceLocation identifier;
   private final FriendlyByteBuf data;

   public ServerboundCustomPayloadPacket(ResourceLocation var1, FriendlyByteBuf var2) {
      this.identifier = â˜ƒ;
      this.data = â˜ƒ;
   }

   public ServerboundCustomPayloadPacket(FriendlyByteBuf var1) {
      this.identifier = â˜ƒ.readResourceLocation();
      int â˜ƒ = â˜ƒ.readableBytes();
      if (â˜ƒ >= 0 && â˜ƒ <= 32767) {
         this.data = new FriendlyByteBuf(â˜ƒ.readBytes(â˜ƒ));
      } else {
         throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeResourceLocation(this.identifier);
      â˜ƒ.writeBytes(this.data);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleCustomPayload(this);
      this.data.release();
   }

   public ResourceLocation getIdentifier() {
      return this.identifier;
   }

   public FriendlyByteBuf getData() {
      return this.data;
   }
}
