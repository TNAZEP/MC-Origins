package net.minecraft.world.entity.monster;

import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class Monster extends PathfinderMob implements Enemy {
   protected Monster(EntityType<? extends Monster> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.xpReward = 5;
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   @Override
   public void aiStep() {
      this.updateSwingTime();
      this.updateNoActionTime();
      super.aiStep();
   }

   protected void updateNoActionTime() {
      float â˜ƒ = this.getBrightness();
      if (â˜ƒ > 0.5F) {
         this.noActionTime += 2;
      }
   }

   @Override
   protected boolean shouldDespawnInPeaceful() {
      return true;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.HOSTILE_SWIM;
   }

   @Override
   protected SoundEvent getSwimSplashSound() {
      return SoundEvents.HOSTILE_SPLASH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.HOSTILE_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.HOSTILE_DEATH;
   }

   @Override
   protected SoundEvent getFallDamageSound(int var1) {
      return â˜ƒ > 4 ? SoundEvents.HOSTILE_BIG_FALL : SoundEvents.HOSTILE_SMALL_FALL;
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      return 0.5F - â˜ƒ.getBrightness(â˜ƒ);
   }

   public static boolean isDarkEnoughToSpawn(ServerLevelAccessor var0, BlockPos var1, Random var2) {
      if (â˜ƒ.getBrightness(LightLayer.SKY, â˜ƒ) > â˜ƒ.nextInt(32)) {
         return false;
      } else {
         int â˜ƒ = â˜ƒ.getLevel().isThundering() ? â˜ƒ.getMaxLocalRawBrightness(â˜ƒ, 10) : â˜ƒ.getMaxLocalRawBrightness(â˜ƒ);
         return â˜ƒ <= â˜ƒ.nextInt(8);
      }
   }

   public static boolean checkMonsterSpawnRules(EntityType<? extends Monster> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(â˜ƒ, â˜ƒ, â˜ƒ) && checkMobSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static boolean checkAnyLightMonsterSpawnRules(EntityType<? extends Monster> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static AttributeSupplier.Builder createMonsterAttributes() {
      return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE);
   }

   @Override
   protected boolean shouldDropExperience() {
      return true;
   }

   @Override
   protected boolean shouldDropLoot() {
      return true;
   }

   public boolean isPreventingPlayerRest(Player var1) {
      return true;
   }

   @Override
   public ItemStack getProjectile(ItemStack var1) {
      if (â˜ƒ.getItem() instanceof ProjectileWeaponItem) {
         Predicate<ItemStack> â˜ƒ = ((ProjectileWeaponItem)â˜ƒ.getItem()).getSupportedHeldProjectiles();
         ItemStack â˜ƒx = ProjectileWeaponItem.getHeldProjectile(this, â˜ƒ);
         return â˜ƒx.isEmpty() ? new ItemStack(Items.ARROW) : â˜ƒx;
      } else {
         return ItemStack.EMPTY;
      }
   }
}
