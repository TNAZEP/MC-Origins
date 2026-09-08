package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class Vex extends Monster {
   public static final float FLAP_DEGREES_PER_TICK = 45.836624F;
   public static final int TICKS_PER_FLAP = Mth.ceil((float) (Math.PI * 5.0 / 4.0));
   protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Vex.class, EntityDataSerializers.BYTE);
   private static final int FLAG_IS_CHARGING = 1;
   Mob owner;
   @Nullable
   private BlockPos boundOrigin;
   private boolean hasLimitedLife;
   private int limitedLifeTicks;

   public Vex(EntityType<? extends Vex> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.moveControl = new Vex.VexMoveControl(this);
      this.xpReward = 3;
   }

   @Override
   public boolean isFlapping() {
      return this.tickCount % TICKS_PER_FLAP == 0;
   }

   @Override
   public void move(MoverType var1, Vec3 var2) {
      super.move(â˜ƒ, â˜ƒ);
      this.checkInsideBlocks();
   }

   @Override
   public void tick() {
      this.noPhysics = true;
      super.tick();
      this.noPhysics = false;
      this.setNoGravity(true);
      if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
         this.limitedLifeTicks = 20;
         this.hurt(DamageSource.STARVE, 1.0F);
      }
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(4, new Vex.VexChargeAttackGoal());
      this.goalSelector.addGoal(8, new Vex.VexRandomMoveGoal());
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
      this.targetSelector.addGoal(2, new Vex.VexCopyOwnerTargetGoal(this));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0).add(Attributes.ATTACK_DAMAGE, 4.0);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_FLAGS_ID, (byte)0);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("BoundX")) {
         this.boundOrigin = new BlockPos(â˜ƒ.getInt("BoundX"), â˜ƒ.getInt("BoundY"), â˜ƒ.getInt("BoundZ"));
      }

      if (â˜ƒ.contains("LifeTicks")) {
         this.setLimitedLife(â˜ƒ.getInt("LifeTicks"));
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.boundOrigin != null) {
         â˜ƒ.putInt("BoundX", this.boundOrigin.getX());
         â˜ƒ.putInt("BoundY", this.boundOrigin.getY());
         â˜ƒ.putInt("BoundZ", this.boundOrigin.getZ());
      }

      if (this.hasLimitedLife) {
         â˜ƒ.putInt("LifeTicks", this.limitedLifeTicks);
      }
   }

   public Mob getOwner() {
      return this.owner;
   }

   @Nullable
   public BlockPos getBoundOrigin() {
      return this.boundOrigin;
   }

   public void setBoundOrigin(@Nullable BlockPos var1) {
      this.boundOrigin = â˜ƒ;
   }

   private boolean getVexFlag(int var1) {
      int â˜ƒ = this.entityData.get(DATA_FLAGS_ID);
      return (â˜ƒ & â˜ƒ) != 0;
   }

   private void setVexFlag(int var1, boolean var2) {
      int â˜ƒ = this.entityData.get(DATA_FLAGS_ID);
      if (â˜ƒ) {
         â˜ƒ |= â˜ƒ;
      } else {
         â˜ƒ &= ~â˜ƒ;
      }

      this.entityData.set(DATA_FLAGS_ID, (byte)(â˜ƒ & 0xFF));
   }

   public boolean isCharging() {
      return this.getVexFlag(1);
   }

   public void setIsCharging(boolean var1) {
      this.setVexFlag(1, â˜ƒ);
   }

   public void setOwner(Mob var1) {
      this.owner = â˜ƒ;
   }

   public void setLimitedLife(int var1) {
      this.hasLimitedLife = true;
      this.limitedLifeTicks = â˜ƒ;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.VEX_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.VEX_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.VEX_HURT;
   }

   @Override
   public float getBrightness() {
      return 1.0F;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.populateDefaultEquipmentSlots(â˜ƒ);
      this.populateDefaultEquipmentEnchantments(â˜ƒ);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
      this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
   }

   class VexChargeAttackGoal extends Goal {
      public VexChargeAttackGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         if (Vex.this.getTarget() != null && !Vex.this.getMoveControl().hasWanted() && Vex.this.random.nextInt(7) == 0) {
            return Vex.this.distanceToSqr(Vex.this.getTarget()) > 4.0;
         } else {
            return false;
         }
      }

      @Override
      public boolean canContinueToUse() {
         return Vex.this.getMoveControl().hasWanted() && Vex.this.isCharging() && Vex.this.getTarget() != null && Vex.this.getTarget().isAlive();
      }

      @Override
      public void start() {
         LivingEntity â˜ƒ = Vex.this.getTarget();
         Vec3 â˜ƒx = â˜ƒ.getEyePosition();
         Vex.this.moveControl.setWantedPosition(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z, 1.0);
         Vex.this.setIsCharging(true);
         Vex.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
      }

      @Override
      public void stop() {
         Vex.this.setIsCharging(false);
      }

      @Override
      public void tick() {
         LivingEntity â˜ƒ = Vex.this.getTarget();
         if (Vex.this.getBoundingBox().intersects(â˜ƒ.getBoundingBox())) {
            Vex.this.doHurtTarget(â˜ƒ);
            Vex.this.setIsCharging(false);
         } else {
            double â˜ƒ = Vex.this.distanceToSqr(â˜ƒ);
            if (â˜ƒ < 9.0) {
               Vec3 â˜ƒx = â˜ƒ.getEyePosition();
               Vex.this.moveControl.setWantedPosition(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z, 1.0);
            }
         }
      }
   }

   class VexCopyOwnerTargetGoal extends TargetGoal {
      private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

      public VexCopyOwnerTargetGoal(PathfinderMob var2) {
         super(â˜ƒ, false);
      }

      @Override
      public boolean canUse() {
         return Vex.this.owner != null && Vex.this.owner.getTarget() != null && this.canAttack(Vex.this.owner.getTarget(), this.copyOwnerTargeting);
      }

      @Override
      public void start() {
         Vex.this.setTarget(Vex.this.owner.getTarget());
         super.start();
      }
   }

   class VexMoveControl extends MoveControl {
      public VexMoveControl(Vex var2) {
         super(â˜ƒ);
      }

      @Override
      public void tick() {
         if (this.operation == MoveControl.Operation.MOVE_TO) {
            Vec3 â˜ƒ = new Vec3(this.wantedX - Vex.this.getX(), this.wantedY - Vex.this.getY(), this.wantedZ - Vex.this.getZ());
            double â˜ƒx = â˜ƒ.length();
            if (â˜ƒx < Vex.this.getBoundingBox().getSize()) {
               this.operation = MoveControl.Operation.WAIT;
               Vex.this.setDeltaMovement(Vex.this.getDeltaMovement().scale(0.5));
            } else {
               Vex.this.setDeltaMovement(Vex.this.getDeltaMovement().add(â˜ƒ.scale(this.speedModifier * 0.05 / â˜ƒx)));
               if (Vex.this.getTarget() == null) {
                  Vec3 â˜ƒ = Vex.this.getDeltaMovement();
                  Vex.this.setYRot(-((float)Mth.atan2(â˜ƒ.x, â˜ƒ.z)) * (180.0F / (float)Math.PI));
                  Vex.this.yBodyRot = Vex.this.getYRot();
               } else {
                  double â˜ƒ = Vex.this.getTarget().getX() - Vex.this.getX();
                  double â˜ƒx = Vex.this.getTarget().getZ() - Vex.this.getZ();
                  Vex.this.setYRot(-((float)Mth.atan2(â˜ƒ, â˜ƒx)) * (180.0F / (float)Math.PI));
                  Vex.this.yBodyRot = Vex.this.getYRot();
               }
            }
         }
      }
   }

   class VexRandomMoveGoal extends Goal {
      public VexRandomMoveGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         return !Vex.this.getMoveControl().hasWanted() && Vex.this.random.nextInt(7) == 0;
      }

      @Override
      public boolean canContinueToUse() {
         return false;
      }

      @Override
      public void tick() {
         BlockPos â˜ƒ = Vex.this.getBoundOrigin();
         if (â˜ƒ == null) {
            â˜ƒ = Vex.this.blockPosition();
         }

         for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
            BlockPos â˜ƒx = â˜ƒ.offset(Vex.this.random.nextInt(15) - 7, Vex.this.random.nextInt(11) - 5, Vex.this.random.nextInt(15) - 7);
            if (Vex.this.level.isEmptyBlock(â˜ƒx)) {
               Vex.this.moveControl.setWantedPosition((double)â˜ƒx.getX() + 0.5, (double)â˜ƒx.getY() + 0.5, (double)â˜ƒx.getZ() + 0.5, 0.25);
               if (Vex.this.getTarget() == null) {
                  Vex.this.getLookControl().setLookAt((double)â˜ƒx.getX() + 0.5, (double)â˜ƒx.getY() + 0.5, (double)â˜ƒx.getZ() + 0.5, 180.0F, 20.0F);
               }
               break;
            }
         }
      }
   }
}
