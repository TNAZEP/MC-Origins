package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundCommandSuggestionPacket implements Packet<ServerGamePacketListener> {
   private final int id;
   private final String command;

   public ServerboundCommandSuggestionPacket(int var1, String var2) {
      this.id = â˜ƒ;
      this.command = â˜ƒ;
   }

   public ServerboundCommandSuggestionPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.command = â˜ƒ.readUtf(32500);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeUtf(this.command, 32500);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleCustomCommandSuggestions(this);
   }

   public int getId() {
      return this.id;
   }

   public String getCommand() {
      return this.command;
   }
}
