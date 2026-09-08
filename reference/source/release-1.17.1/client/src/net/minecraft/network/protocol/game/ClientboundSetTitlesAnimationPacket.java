package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetTitlesAnimationPacket implements Packet<ClientGamePacketListener> {
   private final int fadeIn;
   private final int stay;
   private final int fadeOut;

   public ClientboundSetTitlesAnimationPacket(int var1, int var2, int var3) {
      this.fadeIn = â˜ƒ;
      this.stay = â˜ƒ;
      this.fadeOut = â˜ƒ;
   }

   public ClientboundSetTitlesAnimationPacket(FriendlyByteBuf var1) {
      this.fadeIn = â˜ƒ.readInt();
      this.stay = â˜ƒ.readInt();
      this.fadeOut = â˜ƒ.readInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.fadeIn);
      â˜ƒ.writeInt(this.stay);
      â˜ƒ.writeInt(this.fadeOut);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.setTitlesAnimation(this);
   }

   public int getFadeIn() {
      return this.fadeIn;
   }

   public int getStay() {
      return this.stay;
   }

   public int getFadeOut() {
      return this.fadeOut;
   }
}
