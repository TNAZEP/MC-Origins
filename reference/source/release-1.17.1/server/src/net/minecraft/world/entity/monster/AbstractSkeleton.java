package net.minecraft.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSkeleton extends Monster implements RangedAttackMob {
   private final RangedBowAttackGoal<AbstractSkeleton> bowGoal = new RangedBowAttackGoal<>(this, 1.0, 20, 15.0F);
   private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2, false) {
      @Override
      public void stop() {
         super.stop();
         AbstractSkeleton.this.setAggressive(false);
      }

      @Override
      public void start() {
         super.start();
         AbstractSkeleton.this.setAggressive(true);
      }
   };

   protected AbstractSkeleton(EntityType<? extends AbstractSkeleton> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.reassessWeaponGoal();
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(2, new RestrictSunGoal(this));
      this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Wolf.class, 6.0F, 1.0, 1.2));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(this.getStepSound(), 0.15F, 1.0F);
   }

   abstract SoundEvent getStepSound();

   @Override
   public MobType getMobType() {
      return MobType.UNDEAD;
   }

   @Override
   public void aiStep() {
      boolean â˜ƒ = this.isSunBurnTick();
      if (â˜ƒ) {
         ItemStack â˜ƒx = this.getItemBySlot(EquipmentSlot.HEAD);
         if (!â˜ƒx.isEmpty()) {
            if (â˜ƒx.isDamageableItem()) {
               â˜ƒx.setDamageValue(â˜ƒx.getDamageValue() + this.random.nextInt(2));
               if (â˜ƒx.getDamageValue() >= â˜ƒx.getMaxDamage()) {
                  this.broadcastBreakEvent(EquipmentSlot.HEAD);
                  this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
               }
            }

            â˜ƒ = false;
         }

         if (â˜ƒ) {
            this.setSecondsOnFire(8);
         }
      }

      super.aiStep();
   }

   @Override
   public void rideTick() {
      super.rideTick();
      if (this.getVehicle() instanceof PathfinderMob â˜ƒ) {
         this.yBodyRot = â˜ƒ.yBodyRot;
      }
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      super.populateDefaultEquipmentSlots(â˜ƒ);
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      â˜ƒ = super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.populateDefaultEquipmentSlots(â˜ƒ);
      this.populateDefaultEquipmentEnchantments(â˜ƒ);
      this.reassessWeaponGoal();
      this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * â˜ƒ.getSpecialMultiplier());
      if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
         LocalDate â˜ƒ = LocalDate.now();
         int â˜ƒx = â˜ƒ.get(ChronoField.DAY_OF_MONTH);
         int â˜ƒxx = â˜ƒ.get(ChronoField.MONTH_OF_YEAR);
         if (â˜ƒxx == 10 && â˜ƒx == 31 && this.random.nextFloat() < 0.25F) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
            this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
         }
      }

      return â˜ƒ;
   }

   public void reassessWeaponGoal() {
      if (this.level != null && !this.level.isClientSide) {
         this.goalSelector.removeGoal(this.meleeGoal);
         this.goalSelector.removeGoal(this.bowGoal);
         ItemStack â˜ƒ = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
         if (â˜ƒ.is(Items.BOW)) {
            int â˜ƒx = 20;
            if (this.level.getDifficulty() != Difficulty.HARD) {
               â˜ƒx = 40;
            }

            this.bowGoal.setMinAttackInterval(â˜ƒx);
            this.goalSelector.addGoal(4, this.bowGoal);
         } else {
            this.goalSelector.addGoal(4, this.meleeGoal);
         }
      }
   }

   @Override
   public void performRangedAttack(LivingEntity var1, float var2) {
      ItemStack â˜ƒ = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW)));
      AbstractArrow â˜ƒx = this.getArrow(â˜ƒ, â˜ƒ);
      double â˜ƒxx = â˜ƒ.getX() - this.getX();
      double â˜ƒxxx = â˜ƒ.getY(0.3333333333333333) - â˜ƒx.getY();
      double â˜ƒxxxx = â˜ƒ.getZ() - this.getZ();
      double â˜ƒxxxxx = Math.sqrt(â˜ƒxx * â˜ƒxx + â˜ƒxxxx * â˜ƒxxxx);
      â˜ƒx.shoot(â˜ƒxx, â˜ƒxxx + â˜ƒxxxxx * 0.2F, â˜ƒxxxx, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
      this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.level.addFreshEntity(â˜ƒx);
   }

   protected AbstractArrow getArrow(ItemStack var1, float var2) {
      return ProjectileUtil.getMobArrow(this, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canFireProjectileWeapon(ProjectileWeaponItem var1) {
      return â˜ƒ == Items.BOW;
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.reassessWeaponGoal();
   }

   @Override
   public void setItemSlot(EquipmentSlot var1, ItemStack var2) {
      super.setItemSlot(â˜ƒ, â˜ƒ);
      if (!this.level.isClientSide) {
         this.reassessWeaponGoal();
      }
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 1.74F;
   }

   @Override
   public double getMyRidingOffset() {
      return -0.6;
   }

   public boolean isShaking() {
      return this.isFullyFrozen();
   }
}
