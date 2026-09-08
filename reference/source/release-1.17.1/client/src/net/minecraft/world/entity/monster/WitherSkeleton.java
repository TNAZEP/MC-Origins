package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class WitherSkeleton extends AbstractSkeleton {
   public WitherSkeleton(EntityType<? extends WitherSkeleton> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
   }

   @Override
   protected void registerGoals() {
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractPiglin.class, true));
      super.registerGoals();
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.WITHER_SKELETON_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.WITHER_SKELETON_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.WITHER_SKELETON_DEATH;
   }

   @Override
   SoundEvent getStepSound() {
      return SoundEvents.WITHER_SKELETON_STEP;
   }

   @Override
   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
      super.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);
      Entity â˜ƒx = â˜ƒ.getEntity();
      if (â˜ƒx instanceof Creeper â˜ƒ && â˜ƒ.canDropMobsSkull()) {
         â˜ƒ.increaseDroppedSkulls();
         this.spawnAtLocation(Items.WITHER_SKELETON_SKULL);
      }
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
   }

   @Override
   protected void populateDefaultEquipmentEnchantments(DifficultyInstance var1) {
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      SpawnGroupData â˜ƒ = super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0);
      this.reassessWeaponGoal();
      return â˜ƒ;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 2.1F;
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      if (!super.doHurtTarget(â˜ƒ)) {
         return false;
      } else {
         if (â˜ƒ instanceof LivingEntity) {
            ((LivingEntity)â˜ƒ).addEffect(new MobEffectInstance(MobEffects.WITHER, 200), this);
         }

         return true;
      }
   }

   @Override
   protected AbstractArrow getArrow(ItemStack var1, float var2) {
      AbstractArrow â˜ƒ = super.getArrow(â˜ƒ, â˜ƒ);
      â˜ƒ.setSecondsOnFire(100);
      return â˜ƒ;
   }

   @Override
   public boolean canBeAffected(MobEffectInstance var1) {
      return â˜ƒ.getEffect() == MobEffects.WITHER ? false : super.canBeAffected(â˜ƒ);
   }
}
