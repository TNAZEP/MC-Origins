package net.minecraft.world.entity.boss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

public class EnderDragonPart extends Entity {
   public final EnderDragon parentMob;
   public final String name;
   private final EntityDimensions size;

   public EnderDragonPart(EnderDragon var1, String var2, float var3, float var4) {
      super(â˜ƒ.getType(), â˜ƒ.level);
      this.size = EntityDimensions.scalable(â˜ƒ, â˜ƒ);
      this.refreshDimensions();
      this.parentMob = â˜ƒ;
      this.name = â˜ƒ;
   }

   @Override
   protected void defineSynchedData() {
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
   }

   @Override
   public boolean isPickable() {
      return true;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      return this.isInvulnerableTo(â˜ƒ) ? false : this.parentMob.hurt(this, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean is(Entity var1) {
      return this == â˜ƒ || this.parentMob == â˜ƒ;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      throw new UnsupportedOperationException();
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      return this.size;
   }

   @Override
   public boolean shouldBeSaved() {
      return false;
   }
}
