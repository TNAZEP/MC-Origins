package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundChatPacket implements Packet<ServerGamePacketListener> {
   private static final int MAX_MESSAGE_LENGTH = 256;
   private final String message;

   public ServerboundChatPacket(String var1) {
      if (â˜ƒ.length() > 256) {
         â˜ƒ = â˜ƒ.substring(0, 256);
      }

      this.message = â˜ƒ;
   }

   public ServerboundChatPacket(FriendlyByteBuf var1) {
      this.message = â˜ƒ.readUtf(256);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.message);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleChat(this);
   }

   public String getMessage() {
      return this.message;
   }
}
