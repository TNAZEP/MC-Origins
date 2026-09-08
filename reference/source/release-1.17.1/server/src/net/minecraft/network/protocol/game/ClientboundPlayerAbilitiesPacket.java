package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.player.Abilities;

public class ClientboundPlayerAbilitiesPacket implements Packet<ClientGamePacketListener> {
   private static final int FLAG_INVULNERABLE = 1;
   private static final int FLAG_FLYING = 2;
   private static final int FLAG_CAN_FLY = 4;
   private static final int FLAG_INSTABUILD = 8;
   private final boolean invulnerable;
   private final boolean isFlying;
   private final boolean canFly;
   private final boolean instabuild;
   private final float flyingSpeed;
   private final float walkingSpeed;

   public ClientboundPlayerAbilitiesPacket(Abilities var1) {
      this.invulnerable = â˜ƒ.invulnerable;
      this.isFlying = â˜ƒ.flying;
      this.canFly = â˜ƒ.mayfly;
      this.instabuild = â˜ƒ.instabuild;
      this.flyingSpeed = â˜ƒ.getFlyingSpeed();
      this.walkingSpeed = â˜ƒ.getWalkingSpeed();
   }

   public ClientboundPlayerAbilitiesPacket(FriendlyByteBuf var1) {
      byte â˜ƒ = â˜ƒ.readByte();
      this.invulnerable = (â˜ƒ & 1) != 0;
      this.isFlying = (â˜ƒ & 2) != 0;
      this.canFly = (â˜ƒ & 4) != 0;
      this.instabuild = (â˜ƒ & 8) != 0;
      this.flyingSpeed = â˜ƒ.readFloat();
      this.walkingSpeed = â˜ƒ.readFloat();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      byte â˜ƒ = 0;
      if (this.invulnerable) {
         â˜ƒ = (byte)(â˜ƒ | 1);
      }

      if (this.isFlying) {
         â˜ƒ = (byte)(â˜ƒ | 2);
      }

      if (this.canFly) {
         â˜ƒ = (byte)(â˜ƒ | 4);
      }

      if (this.instabuild) {
         â˜ƒ = (byte)(â˜ƒ | 8);
      }

      â˜ƒ.writeByte(â˜ƒ);
      â˜ƒ.writeFloat(this.flyingSpeed);
      â˜ƒ.writeFloat(this.walkingSpeed);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handlePlayerAbilities(this);
   }

   public boolean isInvulnerable() {
      return this.invulnerable;
   }

   public boolean isFlying() {
      return this.isFlying;
   }

   public boolean canFly() {
      return this.canFly;
   }

   public boolean canInstabuild() {
      return this.instabuild;
   }

   public float getFlyingSpeed() {
      return this.flyingSpeed;
   }

   public float getWalkingSpeed() {
      return this.walkingSpeed;
   }
}
