package net.minecraft.world.entity.monster;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Stray extends AbstractSkeleton {
   public Stray(EntityType<? extends Stray> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public static boolean checkStraySpawnRules(EntityType<Stray> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      BlockPos â˜ƒ = â˜ƒ;

      do {
         â˜ƒ = â˜ƒ.above();
      } while(â˜ƒ.getBlockState(â˜ƒ).is(Blocks.POWDER_SNOW));

      return checkMonsterSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) && (â˜ƒ == MobSpawnType.SPAWNER || â˜ƒ.canSeeSky(â˜ƒ.below()));
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.STRAY_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.STRAY_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.STRAY_DEATH;
   }

   @Override
   SoundEvent getStepSound() {
      return SoundEvents.STRAY_STEP;
   }

   @Override
   protected AbstractArrow getArrow(ItemStack var1, float var2) {
      AbstractArrow â˜ƒ = super.getArrow(â˜ƒ, â˜ƒ);
      if (â˜ƒ instanceof Arrow) {
         ((Arrow)â˜ƒ).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600));
      }

      return â˜ƒ;
   }
}
