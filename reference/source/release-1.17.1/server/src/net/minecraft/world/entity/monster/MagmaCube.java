package net.minecraft.world.entity.monster;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;

public class MagmaCube extends Slime {
   public MagmaCube(EntityType<? extends MagmaCube> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.2F);
   }

   public static boolean checkMagmaCubeSpawnRules(EntityType<MagmaCube> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getDifficulty() != Difficulty.PEACEFUL;
   }

   @Override
   public boolean checkSpawnObstruction(LevelReader var1) {
      return â˜ƒ.isUnobstructed(this) && !â˜ƒ.containsAnyLiquid(this.getBoundingBox());
   }

   @Override
   protected void setSize(int var1, boolean var2) {
      super.setSize(â˜ƒ, â˜ƒ);
      this.getAttribute(Attributes.ARMOR).setBaseValue((double)(â˜ƒ * 3));
   }

   @Override
   public float getBrightness() {
      return 1.0F;
   }

   @Override
   protected ParticleOptions getParticleType() {
      return ParticleTypes.FLAME;
   }

   @Override
   protected ResourceLocation getDefaultLootTable() {
      return this.isTiny() ? BuiltInLootTables.EMPTY : this.getType().getDefaultLootTable();
   }

   @Override
   public boolean isOnFire() {
      return false;
   }

   @Override
   protected int getJumpDelay() {
      return super.getJumpDelay() * 4;
   }

   @Override
   protected void decreaseSquish() {
      this.targetSquish *= 0.9F;
   }

   @Override
   protected void jumpFromGround() {
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.setDeltaMovement(â˜ƒ.x, (double)(this.getJumpPower() + (float)this.getSize() * 0.1F), â˜ƒ.z);
      this.hasImpulse = true;
   }

   @Override
   protected void jumpInLiquid(Tag<Fluid> var1) {
      if (â˜ƒ == FluidTags.LAVA) {
         Vec3 â˜ƒ = this.getDeltaMovement();
         this.setDeltaMovement(â˜ƒ.x, (double)(0.22F + (float)this.getSize() * 0.05F), â˜ƒ.z);
         this.hasImpulse = true;
      } else {
         super.jumpInLiquid(â˜ƒ);
      }
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      return false;
   }

   @Override
   protected boolean isDealsDamage() {
      return this.isEffectiveAi();
   }

   @Override
   protected float getAttackDamage() {
      return super.getAttackDamage() + 2.0F;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return this.isTiny() ? SoundEvents.MAGMA_CUBE_HURT_SMALL : SoundEvents.MAGMA_CUBE_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return this.isTiny() ? SoundEvents.MAGMA_CUBE_DEATH_SMALL : SoundEvents.MAGMA_CUBE_DEATH;
   }

   @Override
   protected SoundEvent getSquishSound() {
      return this.isTiny() ? SoundEvents.MAGMA_CUBE_SQUISH_SMALL : SoundEvents.MAGMA_CUBE_SQUISH;
   }

   @Override
   protected SoundEvent getJumpSound() {
      return SoundEvents.MAGMA_CUBE_JUMP;
   }
}
