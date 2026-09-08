package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundRenameItemPacket implements Packet<ServerGamePacketListener> {
   private final String name;

   public ServerboundRenameItemPacket(String var1) {
      this.name = â˜ƒ;
   }

   public ServerboundRenameItemPacket(FriendlyByteBuf var1) {
      this.name = â˜ƒ.readUtf();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.name);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleRenameItem(this);
   }

   public String getName() {
      return this.name;
   }
}
