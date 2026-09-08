package net.minecraft.network.protocol.login;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundHelloPacket implements Packet<ServerLoginPacketListener> {
   private final GameProfile gameProfile;

   public ServerboundHelloPacket(GameProfile var1) {
      this.gameProfile = â˜ƒ;
   }

   public ServerboundHelloPacket(FriendlyByteBuf var1) {
      this.gameProfile = new GameProfile(null, â˜ƒ.readUtf(16));
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.gameProfile.getName());
   }

   public void handle(ServerLoginPacketListener var1) {
      â˜ƒ.handleHello(this);
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }
}
