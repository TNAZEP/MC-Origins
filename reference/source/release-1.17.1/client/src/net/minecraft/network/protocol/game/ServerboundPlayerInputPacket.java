package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundPlayerInputPacket implements Packet<ServerGamePacketListener> {
   private static final int FLAG_JUMPING = 1;
   private static final int FLAG_SHIFT_KEY_DOWN = 2;
   private final float xxa;
   private final float zza;
   private final boolean isJumping;
   private final boolean isShiftKeyDown;

   public ServerboundPlayerInputPacket(float var1, float var2, boolean var3, boolean var4) {
      this.xxa = â˜ƒ;
      this.zza = â˜ƒ;
      this.isJumping = â˜ƒ;
      this.isShiftKeyDown = â˜ƒ;
   }

   public ServerboundPlayerInputPacket(FriendlyByteBuf var1) {
      this.xxa = â˜ƒ.readFloat();
      this.zza = â˜ƒ.readFloat();
      byte â˜ƒ = â˜ƒ.readByte();
      this.isJumping = (â˜ƒ & 1) > 0;
      this.isShiftKeyDown = (â˜ƒ & 2) > 0;
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeFloat(this.xxa);
      â˜ƒ.writeFloat(this.zza);
      byte â˜ƒ = 0;
      if (this.isJumping) {
         â˜ƒ = (byte)(â˜ƒ | 1);
      }

      if (this.isShiftKeyDown) {
         â˜ƒ = (byte)(â˜ƒ | 2);
      }

      â˜ƒ.writeByte(â˜ƒ);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handlePlayerInput(this);
   }

   public float getXxa() {
      return this.xxa;
   }

   public float getZza() {
      return this.zza;
   }

   public boolean isJumping() {
      return this.isJumping;
   }

   public boolean isShiftKeyDown() {
      return this.isShiftKeyDown;
   }
}
