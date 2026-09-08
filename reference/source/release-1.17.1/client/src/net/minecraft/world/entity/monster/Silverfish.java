package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Silverfish extends Monster {
   private Silverfish.SilverfishWakeUpFriendsGoal friendsGoal;

   public Silverfish(EntityType<? extends Silverfish> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      this.friendsGoal = new Silverfish.SilverfishWakeUpFriendsGoal(this);
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(3, this.friendsGoal);
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(5, new Silverfish.SilverfishMergeWithStoneGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   @Override
   public double getMyRidingOffset() {
      return 0.1;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.13F;
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 8.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 1.0);
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.EVENTS;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.SILVERFISH_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.SILVERFISH_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.SILVERFISH_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         if ((â˜ƒ instanceof EntityDamageSource || â˜ƒ == DamageSource.MAGIC) && this.friendsGoal != null) {
            this.friendsGoal.notifyHurt();
         }

         return super.hurt(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void tick() {
      this.yBodyRot = this.getYRot();
      super.tick();
   }

   @Override
   public void setYBodyRot(float var1) {
      this.setYRot(â˜ƒ);
      super.setYBodyRot(â˜ƒ);
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      return InfestedBlock.isCompatibleHostBlock(â˜ƒ.getBlockState(â˜ƒ.below())) ? 10.0F : super.getWalkTargetValue(â˜ƒ, â˜ƒ);
   }

   public static boolean checkSliverfishSpawnRules(EntityType<Silverfish> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      if (checkAnyLightMonsterSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         Player â˜ƒ = â˜ƒ.getNearestPlayer((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, 5.0, true);
         return â˜ƒ == null;
      } else {
         return false;
      }
   }

   @Override
   public MobType getMobType() {
      return MobType.ARTHROPOD;
   }

   static class SilverfishMergeWithStoneGoal extends RandomStrollGoal {
      private Direction selectedDirection;
      private boolean doMerge;

      public SilverfishMergeWithStoneGoal(Silverfish var1) {
         super(â˜ƒ, 1.0, 10);
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         if (this.mob.getTarget() != null) {
            return false;
         } else if (!this.mob.getNavigation().isDone()) {
            return false;
         } else {
            Random â˜ƒ = this.mob.getRandom();
            if (this.mob.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && â˜ƒ.nextInt(10) == 0) {
               this.selectedDirection = Direction.getRandom(â˜ƒ);
               BlockPos â˜ƒx = new BlockPos(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).relative(this.selectedDirection);
               BlockState â˜ƒxx = this.mob.level.getBlockState(â˜ƒx);
               if (InfestedBlock.isCompatibleHostBlock(â˜ƒxx)) {
                  this.doMerge = true;
                  return true;
               }
            }

            this.doMerge = false;
            return super.canUse();
         }
      }

      @Override
      public boolean canContinueToUse() {
         return this.doMerge ? false : super.canContinueToUse();
      }

      @Override
      public void start() {
         if (!this.doMerge) {
            super.start();
         } else {
            LevelAccessor â˜ƒ = this.mob.level;
            BlockPos â˜ƒx = new BlockPos(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).relative(this.selectedDirection);
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            if (InfestedBlock.isCompatibleHostBlock(â˜ƒxx)) {
               â˜ƒ.setBlock(â˜ƒx, InfestedBlock.infestedStateByHost(â˜ƒxx), 3);
               this.mob.spawnAnim();
               this.mob.discard();
            }
         }
      }
   }

   static class SilverfishWakeUpFriendsGoal extends Goal {
      private final Silverfish silverfish;
      private int lookForFriends;

      public SilverfishWakeUpFriendsGoal(Silverfish var1) {
         this.silverfish = â˜ƒ;
      }

      public void notifyHurt() {
         if (this.lookForFriends == 0) {
            this.lookForFriends = 20;
         }
      }

      @Override
      public boolean canUse() {
         return this.lookForFriends > 0;
      }

      @Override
      public void tick() {
         --this.lookForFriends;
         if (this.lookForFriends <= 0) {
            Level â˜ƒ = this.silverfish.level;
            Random â˜ƒx = this.silverfish.getRandom();
            BlockPos â˜ƒxx = this.silverfish.blockPosition();

            for(int â˜ƒxxx = 0; â˜ƒxxx <= 5 && â˜ƒxxx >= -5; â˜ƒxxx = (â˜ƒxxx <= 0 ? 1 : 0) - â˜ƒxxx) {
               for(int â˜ƒxxxx = 0; â˜ƒxxxx <= 10 && â˜ƒxxxx >= -10; â˜ƒxxxx = (â˜ƒxxxx <= 0 ? 1 : 0) - â˜ƒxxxx) {
                  for(int â˜ƒxxxxx = 0; â˜ƒxxxxx <= 10 && â˜ƒxxxxx >= -10; â˜ƒxxxxx = (â˜ƒxxxxx <= 0 ? 1 : 0) - â˜ƒxxxxx) {
                     BlockPos â˜ƒxxxxxx = â˜ƒxx.offset(â˜ƒxxxx, â˜ƒxxx, â˜ƒxxxxx);
                     BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
                     Block â˜ƒxxxxxxxx = â˜ƒxxxxxxx.getBlock();
                     if (â˜ƒxxxxxxxx instanceof InfestedBlock) {
                        if (â˜ƒ.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                           â˜ƒ.destroyBlock(â˜ƒxxxxxx, true, this.silverfish);
                        } else {
                           â˜ƒ.setBlock(â˜ƒxxxxxx, ((InfestedBlock)â˜ƒxxxxxxxx).hostStateByInfested(â˜ƒ.getBlockState(â˜ƒxxxxxx)), 3);
                        }

                        if (â˜ƒx.nextBoolean()) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
