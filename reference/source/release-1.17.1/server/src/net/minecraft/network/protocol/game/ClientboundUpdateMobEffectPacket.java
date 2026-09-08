package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class ClientboundUpdateMobEffectPacket implements Packet<ClientGamePacketListener> {
   private static final int FLAG_AMBIENT = 1;
   private static final int FLAG_VISIBLE = 2;
   private static final int FLAG_SHOW_ICON = 4;
   private final int entityId;
   private final byte effectId;
   private final byte effectAmplifier;
   private final int effectDurationTicks;
   private final byte flags;

   public ClientboundUpdateMobEffectPacket(int var1, MobEffectInstance var2) {
      this.entityId = â˜ƒ;
      this.effectId = (byte)(MobEffect.getId(â˜ƒ.getEffect()) & 0xFF);
      this.effectAmplifier = (byte)(â˜ƒ.getAmplifier() & 0xFF);
      if (â˜ƒ.getDuration() > 32767) {
         this.effectDurationTicks = 32767;
      } else {
         this.effectDurationTicks = â˜ƒ.getDuration();
      }

      byte â˜ƒ = 0;
      if (â˜ƒ.isAmbient()) {
         â˜ƒ = (byte)(â˜ƒ | 1);
      }

      if (â˜ƒ.isVisible()) {
         â˜ƒ = (byte)(â˜ƒ | 2);
      }

      if (â˜ƒ.showIcon()) {
         â˜ƒ = (byte)(â˜ƒ | 4);
      }

      this.flags = â˜ƒ;
   }

   public ClientboundUpdateMobEffectPacket(FriendlyByteBuf var1) {
      this.entityId = â˜ƒ.readVarInt();
      this.effectId = â˜ƒ.readByte();
      this.effectAmplifier = â˜ƒ.readByte();
      this.effectDurationTicks = â˜ƒ.readVarInt();
      this.flags = â˜ƒ.readByte();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.entityId);
      â˜ƒ.writeByte(this.effectId);
      â˜ƒ.writeByte(this.effectAmplifier);
      â˜ƒ.writeVarInt(this.effectDurationTicks);
      â˜ƒ.writeByte(this.flags);
   }

   public boolean isSuperLongDuration() {
      return this.effectDurationTicks == 32767;
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleUpdateMobEffect(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public byte getEffectId() {
      return this.effectId;
   }

   public byte getEffectAmplifier() {
      return this.effectAmplifier;
   }

   public int getEffectDurationTicks() {
      return this.effectDurationTicks;
   }

   public boolean isEffectVisible() {
      return (this.flags & 2) == 2;
   }

   public boolean isEffectAmbient() {
      return (this.flags & 1) == 1;
   }

   public boolean effectShowsIcon() {
      return (this.flags & 4) == 4;
   }
}
