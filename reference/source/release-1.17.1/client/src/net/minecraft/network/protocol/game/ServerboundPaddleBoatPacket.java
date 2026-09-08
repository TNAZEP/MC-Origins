package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundPaddleBoatPacket implements Packet<ServerGamePacketListener> {
   private final boolean left;
   private final boolean right;

   public ServerboundPaddleBoatPacket(boolean var1, boolean var2) {
      this.left = â˜ƒ;
      this.right = â˜ƒ;
   }

   public ServerboundPaddleBoatPacket(FriendlyByteBuf var1) {
      this.left = â˜ƒ.readBoolean();
      this.right = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBoolean(this.left);
      â˜ƒ.writeBoolean(this.right);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handlePaddleBoat(this);
   }

   public boolean getLeft() {
      return this.left;
   }

   public boolean getRight() {
      return this.right;
   }
}
