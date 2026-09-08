package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.player.Abilities;

public class ServerboundPlayerAbilitiesPacket implements Packet<ServerGamePacketListener> {
   private static final int FLAG_FLYING = 2;
   private final boolean isFlying;

   public ServerboundPlayerAbilitiesPacket(Abilities var1) {
      this.isFlying = â˜ƒ.flying;
   }

   public ServerboundPlayerAbilitiesPacket(FriendlyByteBuf var1) {
      byte â˜ƒ = â˜ƒ.readByte();
      this.isFlying = (â˜ƒ & 2) != 0;
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      byte â˜ƒ = 0;
      if (this.isFlying) {
         â˜ƒ = (byte)(â˜ƒ | 2);
      }

      â˜ƒ.writeByte(â˜ƒ);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handlePlayerAbilities(this);
   }

   public boolean isFlying() {
      return this.isFlying;
   }
}
