package net.minecraft.world.entity.monster;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Husk extends Zombie {
   public Husk(EntityType<? extends Husk> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public static boolean checkHuskSpawnRules(EntityType<Husk> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return checkMonsterSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) && (â˜ƒ == MobSpawnType.SPAWNER || â˜ƒ.canSeeSky(â˜ƒ));
   }

   @Override
   protected boolean isSunSensitive() {
      return false;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.HUSK_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.HUSK_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.HUSK_DEATH;
   }

   @Override
   protected SoundEvent getStepSound() {
      return SoundEvents.HUSK_STEP;
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      boolean â˜ƒ = super.doHurtTarget(â˜ƒ);
      if (â˜ƒ && this.getMainHandItem().isEmpty() && â˜ƒ instanceof LivingEntity) {
         float â˜ƒx = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
         ((LivingEntity)â˜ƒ).addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int)â˜ƒx), this);
      }

      return â˜ƒ;
   }

   @Override
   protected boolean convertsInWater() {
      return true;
   }

   @Override
   protected void doUnderWaterConversion() {
      this.convertToZombieType(EntityType.ZOMBIE);
      if (!this.isSilent()) {
         this.level.levelEvent(null, 1041, this.blockPosition(), 0);
      }
   }

   @Override
   protected ItemStack getSkull() {
      return ItemStack.EMPTY;
   }
}
