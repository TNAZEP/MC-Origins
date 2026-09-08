package net.minecraft.world.entity.monster;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Evoker extends SpellcasterIllager {
   private Sheep wololoTarget;

   public Evoker(EntityType<? extends Evoker> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.xpReward = 10;
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Evoker.EvokerCastingSpellGoal());
      this.goalSelector.addGoal(2, new AvoidEntityGoal(this, Player.class, 8.0F, 0.6, 1.0));
      this.goalSelector.addGoal(4, new Evoker.EvokerSummonSpellGoal());
      this.goalSelector.addGoal(5, new Evoker.EvokerAttackSpellGoal());
      this.goalSelector.addGoal(6, new Evoker.EvokerWololoSpellGoal());
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 12.0).add(Attributes.MAX_HEALTH, 24.0);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
   }

   @Override
   public SoundEvent getCelebrateSound() {
      return SoundEvents.EVOKER_CELEBRATE;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
   }

   @Override
   public boolean isAlliedTo(Entity var1) {
      if (â˜ƒ == null) {
         return false;
      } else if (â˜ƒ == this) {
         return true;
      } else if (super.isAlliedTo(â˜ƒ)) {
         return true;
      } else if (â˜ƒ instanceof Vex) {
         return this.isAlliedTo(((Vex)â˜ƒ).getOwner());
      } else if (â˜ƒ instanceof LivingEntity && ((LivingEntity)â˜ƒ).getMobType() == MobType.ILLAGER) {
         return this.getTeam() == null && â˜ƒ.getTeam() == null;
      } else {
         return false;
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.EVOKER_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.EVOKER_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.EVOKER_HURT;
   }

   void setWololoTarget(@Nullable Sheep var1) {
      this.wololoTarget = â˜ƒ;
   }

   @Nullable
   Sheep getWololoTarget() {
      return this.wololoTarget;
   }

   @Override
   protected SoundEvent getCastingSoundEvent() {
      return SoundEvents.EVOKER_CAST_SPELL;
   }

   @Override
   public void applyRaidBuffs(int var1, boolean var2) {
   }

   class EvokerAttackSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
      @Override
      protected int getCastingTime() {
         return 40;
      }

      @Override
      protected int getCastingInterval() {
         return 100;
      }

      @Override
      protected void performSpellCasting() {
         LivingEntity â˜ƒ = Evoker.this.getTarget();
         double â˜ƒx = Math.min(â˜ƒ.getY(), Evoker.this.getY());
         double â˜ƒxx = Math.max(â˜ƒ.getY(), Evoker.this.getY()) + 1.0;
         float â˜ƒxxx = (float)Mth.atan2(â˜ƒ.getZ() - Evoker.this.getZ(), â˜ƒ.getX() - Evoker.this.getX());
         if (Evoker.this.distanceToSqr(â˜ƒ) < 9.0) {
            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 5; ++â˜ƒxxxx) {
               float â˜ƒxxxxx = â˜ƒxxx + (float)â˜ƒxxxx * (float) Math.PI * 0.4F;
               this.createSpellEntity(
                  Evoker.this.getX() + (double)Mth.cos(â˜ƒxxxxx) * 1.5, Evoker.this.getZ() + (double)Mth.sin(â˜ƒxxxxx) * 1.5, â˜ƒx, â˜ƒxx, â˜ƒxxxxx, 0
               );
            }

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 8; ++â˜ƒxxxx) {
               float â˜ƒxxxxx = â˜ƒxxx + (float)â˜ƒxxxx * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
               this.createSpellEntity(
                  Evoker.this.getX() + (double)Mth.cos(â˜ƒxxxxx) * 2.5, Evoker.this.getZ() + (double)Mth.sin(â˜ƒxxxxx) * 2.5, â˜ƒx, â˜ƒxx, â˜ƒxxxxx, 3
               );
            }
         } else {
            for(int â˜ƒ = 0; â˜ƒ < 16; ++â˜ƒ) {
               double â˜ƒx = 1.25 * (double)(â˜ƒ + 1);
               int â˜ƒxx = 1 * â˜ƒ;
               this.createSpellEntity(
                  Evoker.this.getX() + (double)Mth.cos(â˜ƒxxx) * â˜ƒx, Evoker.this.getZ() + (double)Mth.sin(â˜ƒxxx) * â˜ƒx, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxx
               );
            }
         }
      }

      private void createSpellEntity(double var1, double var3, double var5, double var7, float var9, int var10) {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
         boolean â˜ƒx = false;
         double â˜ƒxx = 0.0;

         do {
            BlockPos â˜ƒxxx = â˜ƒ.below();
            BlockState â˜ƒxxxx = Evoker.this.level.getBlockState(â˜ƒxxx);
            if (â˜ƒxxxx.isFaceSturdy(Evoker.this.level, â˜ƒxxx, Direction.UP)) {
               if (!Evoker.this.level.isEmptyBlock(â˜ƒ)) {
                  BlockState â˜ƒxxxxx = Evoker.this.level.getBlockState(â˜ƒ);
                  VoxelShape â˜ƒxxxxxx = â˜ƒxxxxx.getCollisionShape(Evoker.this.level, â˜ƒ);
                  if (!â˜ƒxxxxxx.isEmpty()) {
                     â˜ƒxx = â˜ƒxxxxxx.max(Direction.Axis.Y);
                  }
               }

               â˜ƒx = true;
               break;
            }

            â˜ƒ = â˜ƒ.below();
         } while(â˜ƒ.getY() >= Mth.floor(â˜ƒ) - 1);

         if (â˜ƒx) {
            Evoker.this.level.addFreshEntity(new EvokerFangs(Evoker.this.level, â˜ƒ, (double)â˜ƒ.getY() + â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, Evoker.this));
         }
      }

      @Override
      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.EVOKER_PREPARE_ATTACK;
      }

      @Override
      protected SpellcasterIllager.IllagerSpell getSpell() {
         return SpellcasterIllager.IllagerSpell.FANGS;
      }
   }

   class EvokerCastingSpellGoal extends SpellcasterIllager.SpellcasterCastingSpellGoal {
      @Override
      public void tick() {
         if (Evoker.this.getTarget() != null) {
            Evoker.this.getLookControl().setLookAt(Evoker.this.getTarget(), (float)Evoker.this.getMaxHeadYRot(), (float)Evoker.this.getMaxHeadXRot());
         } else if (Evoker.this.getWololoTarget() != null) {
            Evoker.this.getLookControl().setLookAt(Evoker.this.getWololoTarget(), (float)Evoker.this.getMaxHeadYRot(), (float)Evoker.this.getMaxHeadXRot());
         }
      }
   }

   class EvokerSummonSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
      private final TargetingConditions vexCountTargeting = TargetingConditions.forNonCombat().range(16.0).ignoreLineOfSight().ignoreInvisibilityTesting();

      @Override
      public boolean canUse() {
         if (!super.canUse()) {
            return false;
         } else {
            int â˜ƒ = Evoker.this.level.getNearbyEntities(Vex.class, this.vexCountTargeting, Evoker.this, Evoker.this.getBoundingBox().inflate(16.0)).size();
            return Evoker.this.random.nextInt(8) + 1 > â˜ƒ;
         }
      }

      @Override
      protected int getCastingTime() {
         return 100;
      }

      @Override
      protected int getCastingInterval() {
         return 340;
      }

      @Override
      protected void performSpellCasting() {
         ServerLevel â˜ƒ = (ServerLevel)Evoker.this.level;

         for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
            BlockPos â˜ƒxx = Evoker.this.blockPosition().offset(-2 + Evoker.this.random.nextInt(5), 1, -2 + Evoker.this.random.nextInt(5));
            Vex â˜ƒxxx = EntityType.VEX.create(Evoker.this.level);
            â˜ƒxxx.moveTo(â˜ƒxx, 0.0F, 0.0F);
            â˜ƒxxx.finalizeSpawn(â˜ƒ, Evoker.this.level.getCurrentDifficultyAt(â˜ƒxx), MobSpawnType.MOB_SUMMONED, null, null);
            â˜ƒxxx.setOwner(Evoker.this);
            â˜ƒxxx.setBoundOrigin(â˜ƒxx);
            â˜ƒxxx.setLimitedLife(20 * (30 + Evoker.this.random.nextInt(90)));
            â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxx);
         }
      }

      @Override
      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.EVOKER_PREPARE_SUMMON;
      }

      @Override
      protected SpellcasterIllager.IllagerSpell getSpell() {
         return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
      }
   }

   public class EvokerWololoSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
      private final TargetingConditions wololoTargeting = TargetingConditions.forNonCombat()
         .range(16.0)
         .selector(var0 -> ((Sheep)var0).getColor() == DyeColor.BLUE);

      @Override
      public boolean canUse() {
         if (Evoker.this.getTarget() != null) {
            return false;
         } else if (Evoker.this.isCastingSpell()) {
            return false;
         } else if (Evoker.this.tickCount < this.nextAttackTickCount) {
            return false;
         } else if (!Evoker.this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
         } else {
            List<Sheep> â˜ƒ = Evoker.this.level
               .getNearbyEntities(Sheep.class, this.wololoTargeting, Evoker.this, Evoker.this.getBoundingBox().inflate(16.0, 4.0, 16.0));
            if (â˜ƒ.isEmpty()) {
               return false;
            } else {
               Evoker.this.setWololoTarget((Sheep)â˜ƒ.get(Evoker.this.random.nextInt(â˜ƒ.size())));
               return true;
            }
         }
      }

      @Override
      public boolean canContinueToUse() {
         return Evoker.this.getWololoTarget() != null && this.attackWarmupDelay > 0;
      }

      @Override
      public void stop() {
         super.stop();
         Evoker.this.setWololoTarget(null);
      }

      @Override
      protected void performSpellCasting() {
         Sheep â˜ƒ = Evoker.this.getWololoTarget();
         if (â˜ƒ != null && â˜ƒ.isAlive()) {
            â˜ƒ.setColor(DyeColor.RED);
         }
      }

      @Override
      protected int getCastWarmupTime() {
         return 40;
      }

      @Override
      protected int getCastingTime() {
         return 60;
      }

      @Override
      protected int getCastingInterval() {
         return 140;
      }

      @Override
      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.EVOKER_PREPARE_WOLOLO;
      }

      @Override
      protected SpellcasterIllager.IllagerSpell getSpell() {
         return SpellcasterIllager.IllagerSpell.WOLOLO;
      }
   }
}
