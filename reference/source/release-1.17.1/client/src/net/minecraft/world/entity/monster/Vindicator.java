package net.minecraft.world.entity.monster;

import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Vindicator extends AbstractIllager {
   private static final String TAG_JOHNNY = "Johnny";
   static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = var0 -> var0 == Difficulty.NORMAL || var0 == Difficulty.HARD;
   boolean isJohnny;

   public Vindicator(EntityType<? extends Vindicator> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Vindicator.VindicatorBreakDoorGoal(this));
      this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
      this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
      this.goalSelector.addGoal(4, new Vindicator.VindicatorMeleeAttackGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(4, new Vindicator.VindicatorJohnnyAttackGoal(this));
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
   }

   @Override
   protected void customServerAiStep() {
      if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
         boolean â˜ƒ = ((ServerLevel)this.level).isRaided(this.blockPosition());
         ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(â˜ƒ);
      }

      super.customServerAiStep();
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.35F)
         .add(Attributes.FOLLOW_RANGE, 12.0)
         .add(Attributes.MAX_HEALTH, 24.0)
         .add(Attributes.ATTACK_DAMAGE, 5.0);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.isJohnny) {
         â˜ƒ.putBoolean("Johnny", true);
      }
   }

   @Override
   public AbstractIllager.IllagerArmPose getArmPose() {
      if (this.isAggressive()) {
         return AbstractIllager.IllagerArmPose.ATTACKING;
      } else {
         return this.isCelebrating() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.CROSSED;
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("Johnny", 99)) {
         this.isJohnny = â˜ƒ.getBoolean("Johnny");
      }
   }

   @Override
   public SoundEvent getCelebrateSound() {
      return SoundEvents.VINDICATOR_CELEBRATE;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      SpawnGroupData â˜ƒ = super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
      this.populateDefaultEquipmentSlots(â˜ƒ);
      this.populateDefaultEquipmentEnchantments(â˜ƒ);
      return â˜ƒ;
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      if (this.getCurrentRaid() == null) {
         this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
      }
   }

   @Override
   public boolean isAlliedTo(Entity var1) {
      if (super.isAlliedTo(â˜ƒ)) {
         return true;
      } else if (â˜ƒ instanceof LivingEntity && ((LivingEntity)â˜ƒ).getMobType() == MobType.ILLAGER) {
         return this.getTeam() == null && â˜ƒ.getTeam() == null;
      } else {
         return false;
      }
   }

   @Override
   public void setCustomName(@Nullable Component var1) {
      super.setCustomName(â˜ƒ);
      if (!this.isJohnny && â˜ƒ != null && â˜ƒ.getString().equals("Johnny")) {
         this.isJohnny = true;
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.VINDICATOR_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.VINDICATOR_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.VINDICATOR_HURT;
   }

   @Override
   public void applyRaidBuffs(int var1, boolean var2) {
      ItemStack â˜ƒ = new ItemStack(Items.IRON_AXE);
      Raid â˜ƒx = this.getCurrentRaid();
      int â˜ƒxx = 1;
      if (â˜ƒ > â˜ƒx.getNumGroups(Difficulty.NORMAL)) {
         â˜ƒxx = 2;
      }

      boolean â˜ƒ = this.random.nextFloat() <= â˜ƒx.getEnchantOdds();
      if (â˜ƒ) {
         Map<Enchantment, Integer> â˜ƒx = Maps.newHashMap();
         â˜ƒx.put(Enchantments.SHARPNESS, â˜ƒxx);
         EnchantmentHelper.setEnchantments(â˜ƒx, â˜ƒ);
      }

      this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ);
   }

   static class VindicatorBreakDoorGoal extends BreakDoorGoal {
      public VindicatorBreakDoorGoal(Mob var1) {
         super(â˜ƒ, 6, Vindicator.DOOR_BREAKING_PREDICATE);
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canContinueToUse() {
         Vindicator â˜ƒ = (Vindicator)this.mob;
         return â˜ƒ.hasActiveRaid() && super.canContinueToUse();
      }

      @Override
      public boolean canUse() {
         Vindicator â˜ƒ = (Vindicator)this.mob;
         return â˜ƒ.hasActiveRaid() && â˜ƒ.random.nextInt(10) == 0 && super.canUse();
      }

      @Override
      public void start() {
         super.start();
         this.mob.setNoActionTime(0);
      }
   }

   static class VindicatorJohnnyAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
      public VindicatorJohnnyAttackGoal(Vindicator var1) {
         super(â˜ƒ, LivingEntity.class, 0, true, true, LivingEntity::attackable);
      }

      @Override
      public boolean canUse() {
         return ((Vindicator)this.mob).isJohnny && super.canUse();
      }

      @Override
      public void start() {
         super.start();
         this.mob.setNoActionTime(0);
      }
   }

   class VindicatorMeleeAttackGoal extends MeleeAttackGoal {
      public VindicatorMeleeAttackGoal(Vindicator var2) {
         super(â˜ƒ, 1.0, false);
      }

      @Override
      protected double getAttackReachSqr(LivingEntity var1) {
         if (this.mob.getVehicle() instanceof Ravager) {
            float â˜ƒ = this.mob.getVehicle().getBbWidth() - 0.1F;
            return (double)(â˜ƒ * 2.0F * â˜ƒ * 2.0F + â˜ƒ.getBbWidth());
         } else {
            return super.getAttackReachSqr(â˜ƒ);
         }
      }
   }
}
