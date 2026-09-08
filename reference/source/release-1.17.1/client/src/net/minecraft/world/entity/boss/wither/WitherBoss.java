package net.minecraft.world.entity.boss.wither;

import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WitherBoss extends Monster implements PowerableMob, RangedAttackMob {
   private static final EntityDataAccessor<Integer> DATA_TARGET_A = SynchedEntityData.defineId(WitherBoss.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_TARGET_B = SynchedEntityData.defineId(WitherBoss.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_TARGET_C = SynchedEntityData.defineId(WitherBoss.class, EntityDataSerializers.INT);
   private static final List<EntityDataAccessor<Integer>> DATA_TARGETS = ImmutableList.of(DATA_TARGET_A, DATA_TARGET_B, DATA_TARGET_C);
   private static final EntityDataAccessor<Integer> DATA_ID_INV = SynchedEntityData.defineId(WitherBoss.class, EntityDataSerializers.INT);
   private static final int INVULNERABLE_TICKS = 220;
   private final float[] xRotHeads = new float[2];
   private final float[] yRotHeads = new float[2];
   private final float[] xRotOHeads = new float[2];
   private final float[] yRotOHeads = new float[2];
   private final int[] nextHeadUpdate = new int[2];
   private final int[] idleHeadUpdates = new int[2];
   private int destroyBlocksTick;
   private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(
         this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS
      )
      .setDarkenScreen(true);
   private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = var0 -> var0.getMobType() != MobType.UNDEAD && var0.attackable();
   private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forCombat().range(20.0).selector(LIVING_ENTITY_SELECTOR);

   public WitherBoss(EntityType<? extends WitherBoss> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setHealth(this.getMaxHealth());
      this.getNavigation().setCanFloat(true);
      this.xpReward = 50;
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new WitherBoss.WitherDoNothingGoal());
      this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 40, 20.0F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Mob.class, 0, false, false, LIVING_ENTITY_SELECTOR));
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_TARGET_A, 0);
      this.entityData.define(DATA_TARGET_B, 0);
      this.entityData.define(DATA_TARGET_C, 0);
      this.entityData.define(DATA_ID_INV, 0);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Invul", this.getInvulnerableTicks());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setInvulnerableTicks(â˜ƒ.getInt("Invul"));
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }
   }

   @Override
   public void setCustomName(@Nullable Component var1) {
      super.setCustomName(â˜ƒ);
      this.bossEvent.setName(this.getDisplayName());
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.WITHER_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.WITHER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.WITHER_DEATH;
   }

   @Override
   public void aiStep() {
      Vec3 â˜ƒ = this.getDeltaMovement().multiply(1.0, 0.6, 1.0);
      if (!this.level.isClientSide && this.getAlternativeTarget(0) > 0) {
         Entity â˜ƒx = this.level.getEntity(this.getAlternativeTarget(0));
         if (â˜ƒx != null) {
            double â˜ƒxx = â˜ƒ.y;
            if (this.getY() < â˜ƒx.getY() || !this.isPowered() && this.getY() < â˜ƒx.getY() + 5.0) {
               â˜ƒxx = Math.max(0.0, â˜ƒxx);
               â˜ƒxx += 0.3 - â˜ƒxx * 0.6F;
            }

            â˜ƒ = new Vec3(â˜ƒ.x, â˜ƒxx, â˜ƒ.z);
            Vec3 â˜ƒxx = new Vec3(â˜ƒx.getX() - this.getX(), 0.0, â˜ƒx.getZ() - this.getZ());
            if (â˜ƒxx.horizontalDistanceSqr() > 9.0) {
               Vec3 â˜ƒxxx = â˜ƒxx.normalize();
               â˜ƒ = â˜ƒ.add(â˜ƒxxx.x * 0.3 - â˜ƒ.x * 0.6, 0.0, â˜ƒxxx.z * 0.3 - â˜ƒ.z * 0.6);
            }
         }
      }

      this.setDeltaMovement(â˜ƒ);
      if (â˜ƒ.horizontalDistanceSqr() > 0.05) {
         this.setYRot((float)Mth.atan2(â˜ƒ.z, â˜ƒ.x) * (180.0F / (float)Math.PI) - 90.0F);
      }

      super.aiStep();

      for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
         this.yRotOHeads[â˜ƒ] = this.yRotHeads[â˜ƒ];
         this.xRotOHeads[â˜ƒ] = this.xRotHeads[â˜ƒ];
      }

      for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
         int â˜ƒx = this.getAlternativeTarget(â˜ƒ + 1);
         Entity â˜ƒxx = null;
         if (â˜ƒx > 0) {
            â˜ƒxx = this.level.getEntity(â˜ƒx);
         }

         if (â˜ƒxx != null) {
            double â˜ƒx = this.getHeadX(â˜ƒ + 1);
            double â˜ƒxx = this.getHeadY(â˜ƒ + 1);
            double â˜ƒxxx = this.getHeadZ(â˜ƒ + 1);
            double â˜ƒxxxx = â˜ƒxx.getX() - â˜ƒx;
            double â˜ƒxxxxx = â˜ƒxx.getEyeY() - â˜ƒxx;
            double â˜ƒxxxxxx = â˜ƒxx.getZ() - â˜ƒxxx;
            double â˜ƒxxxxxxx = Math.sqrt(â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx);
            float â˜ƒxxxxxxxx = (float)(Mth.atan2(â˜ƒxxxxxx, â˜ƒxxxx) * 180.0F / (float)Math.PI) - 90.0F;
            float â˜ƒxxxxxxxxx = (float)(-(Mth.atan2(â˜ƒxxxxx, â˜ƒxxxxxxx) * 180.0F / (float)Math.PI));
            this.xRotHeads[â˜ƒ] = this.rotlerp(this.xRotHeads[â˜ƒ], â˜ƒxxxxxxxxx, 40.0F);
            this.yRotHeads[â˜ƒ] = this.rotlerp(this.yRotHeads[â˜ƒ], â˜ƒxxxxxxxx, 10.0F);
         } else {
            this.yRotHeads[â˜ƒ] = this.rotlerp(this.yRotHeads[â˜ƒ], this.yBodyRot, 10.0F);
         }
      }

      boolean â˜ƒ = this.isPowered();

      for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
         double â˜ƒxx = this.getHeadX(â˜ƒx);
         double â˜ƒxxx = this.getHeadY(â˜ƒx);
         double â˜ƒxxxx = this.getHeadZ(â˜ƒx);
         this.level
            .addParticle(
               ParticleTypes.SMOKE,
               â˜ƒxx + this.random.nextGaussian() * 0.3F,
               â˜ƒxxx + this.random.nextGaussian() * 0.3F,
               â˜ƒxxxx + this.random.nextGaussian() * 0.3F,
               0.0,
               0.0,
               0.0
            );
         if (â˜ƒ && this.level.random.nextInt(4) == 0) {
            this.level
               .addParticle(
                  ParticleTypes.ENTITY_EFFECT,
                  â˜ƒxx + this.random.nextGaussian() * 0.3F,
                  â˜ƒxxx + this.random.nextGaussian() * 0.3F,
                  â˜ƒxxxx + this.random.nextGaussian() * 0.3F,
                  0.7F,
                  0.7F,
                  0.5
               );
         }
      }

      if (this.getInvulnerableTicks() > 0) {
         for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
            this.level
               .addParticle(
                  ParticleTypes.ENTITY_EFFECT,
                  this.getX() + this.random.nextGaussian(),
                  this.getY() + (double)(this.random.nextFloat() * 3.3F),
                  this.getZ() + this.random.nextGaussian(),
                  0.7F,
                  0.7F,
                  0.9F
               );
         }
      }
   }

   @Override
   protected void customServerAiStep() {
      if (this.getInvulnerableTicks() > 0) {
         int â˜ƒ = this.getInvulnerableTicks() - 1;
         this.bossEvent.setProgress(1.0F - (float)â˜ƒ / 220.0F);
         if (â˜ƒ <= 0) {
            Explosion.BlockInteraction â˜ƒx = this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
               ? Explosion.BlockInteraction.DESTROY
               : Explosion.BlockInteraction.NONE;
            this.level.explode(this, this.getX(), this.getEyeY(), this.getZ(), 7.0F, false, â˜ƒx);
            if (!this.isSilent()) {
               this.level.globalLevelEvent(1023, this.blockPosition(), 0);
            }
         }

         this.setInvulnerableTicks(â˜ƒ);
         if (this.tickCount % 10 == 0) {
            this.heal(10.0F);
         }
      } else {
         super.customServerAiStep();

         for(int â˜ƒ = 1; â˜ƒ < 3; ++â˜ƒ) {
            if (this.tickCount >= this.nextHeadUpdate[â˜ƒ - 1]) {
               this.nextHeadUpdate[â˜ƒ - 1] = this.tickCount + 10 + this.random.nextInt(10);
               if ((this.level.getDifficulty() == Difficulty.NORMAL || this.level.getDifficulty() == Difficulty.HARD) && this.idleHeadUpdates[â˜ƒ - 1]++ > 15) {
                  float â˜ƒx = 10.0F;
                  float â˜ƒxx = 5.0F;
                  double â˜ƒxxx = Mth.nextDouble(this.random, this.getX() - 10.0, this.getX() + 10.0);
                  double â˜ƒxxxx = Mth.nextDouble(this.random, this.getY() - 5.0, this.getY() + 5.0);
                  double â˜ƒxxxxx = Mth.nextDouble(this.random, this.getZ() - 10.0, this.getZ() + 10.0);
                  this.performRangedAttack(â˜ƒ + 1, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, true);
                  this.idleHeadUpdates[â˜ƒ - 1] = 0;
               }

               int â˜ƒx = this.getAlternativeTarget(â˜ƒ);
               if (â˜ƒx > 0) {
                  LivingEntity â˜ƒxx = (LivingEntity)this.level.getEntity(â˜ƒx);
                  if (â˜ƒxx != null && this.canAttack(â˜ƒxx) && !(this.distanceToSqr(â˜ƒxx) > 900.0) && this.hasLineOfSight(â˜ƒxx)) {
                     this.performRangedAttack(â˜ƒ + 1, â˜ƒxx);
                     this.nextHeadUpdate[â˜ƒ - 1] = this.tickCount + 40 + this.random.nextInt(20);
                     this.idleHeadUpdates[â˜ƒ - 1] = 0;
                  } else {
                     this.setAlternativeTarget(â˜ƒ, 0);
                  }
               } else {
                  List<LivingEntity> â˜ƒx = this.level
                     .getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate(20.0, 8.0, 20.0));
                  if (!â˜ƒx.isEmpty()) {
                     LivingEntity â˜ƒxx = (LivingEntity)â˜ƒx.get(this.random.nextInt(â˜ƒx.size()));
                     this.setAlternativeTarget(â˜ƒ, â˜ƒxx.getId());
                  }
               }
            }
         }

         if (this.getTarget() != null) {
            this.setAlternativeTarget(0, this.getTarget().getId());
         } else {
            this.setAlternativeTarget(0, 0);
         }

         if (this.destroyBlocksTick > 0) {
            --this.destroyBlocksTick;
            if (this.destroyBlocksTick == 0 && this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
               int â˜ƒ = Mth.floor(this.getY());
               int â˜ƒx = Mth.floor(this.getX());
               int â˜ƒxx = Mth.floor(this.getZ());
               boolean â˜ƒxxx = false;

               for(int â˜ƒxxxx = -1; â˜ƒxxxx <= 1; ++â˜ƒxxxx) {
                  for(int â˜ƒxxxxx = -1; â˜ƒxxxxx <= 1; ++â˜ƒxxxxx) {
                     for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx <= 3; ++â˜ƒxxxxxx) {
                        int â˜ƒxxxxxxx = â˜ƒx + â˜ƒxxxx;
                        int â˜ƒxxxxxxxx = â˜ƒ + â˜ƒxxxxxx;
                        int â˜ƒxxxxxxxxx = â˜ƒxx + â˜ƒxxxxx;
                        BlockPos â˜ƒxxxxxxxxxx = new BlockPos(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
                        BlockState â˜ƒxxxxxxxxxxx = this.level.getBlockState(â˜ƒxxxxxxxxxx);
                        if (canDestroy(â˜ƒxxxxxxxxxxx)) {
                           â˜ƒxxx = this.level.destroyBlock(â˜ƒxxxxxxxxxx, true, this) || â˜ƒxxx;
                        }
                     }
                  }
               }

               if (â˜ƒxxx) {
                  this.level.levelEvent(null, 1022, this.blockPosition(), 0);
               }
            }
         }

         if (this.tickCount % 20 == 0) {
            this.heal(1.0F);
         }

         this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      }
   }

   public static boolean canDestroy(BlockState var0) {
      return !â˜ƒ.isAir() && !â˜ƒ.is(BlockTags.WITHER_IMMUNE);
   }

   public void makeInvulnerable() {
      this.setInvulnerableTicks(220);
      this.bossEvent.setProgress(0.0F);
      this.setHealth(this.getMaxHealth() / 3.0F);
   }

   @Override
   public void makeStuckInBlock(BlockState var1, Vec3 var2) {
   }

   @Override
   public void startSeenByPlayer(ServerPlayer var1) {
      super.startSeenByPlayer(â˜ƒ);
      this.bossEvent.addPlayer(â˜ƒ);
   }

   @Override
   public void stopSeenByPlayer(ServerPlayer var1) {
      super.stopSeenByPlayer(â˜ƒ);
      this.bossEvent.removePlayer(â˜ƒ);
   }

   private double getHeadX(int var1) {
      if (â˜ƒ <= 0) {
         return this.getX();
      } else {
         float â˜ƒ = (this.yBodyRot + (float)(180 * (â˜ƒ - 1))) * (float) (Math.PI / 180.0);
         float â˜ƒx = Mth.cos(â˜ƒ);
         return this.getX() + (double)â˜ƒx * 1.3;
      }
   }

   private double getHeadY(int var1) {
      return â˜ƒ <= 0 ? this.getY() + 3.0 : this.getY() + 2.2;
   }

   private double getHeadZ(int var1) {
      if (â˜ƒ <= 0) {
         return this.getZ();
      } else {
         float â˜ƒ = (this.yBodyRot + (float)(180 * (â˜ƒ - 1))) * (float) (Math.PI / 180.0);
         float â˜ƒx = Mth.sin(â˜ƒ);
         return this.getZ() + (double)â˜ƒx * 1.3;
      }
   }

   private float rotlerp(float var1, float var2, float var3) {
      float â˜ƒ = Mth.wrapDegrees(â˜ƒ - â˜ƒ);
      if (â˜ƒ > â˜ƒ) {
         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ < -â˜ƒ) {
         â˜ƒ = -â˜ƒ;
      }

      return â˜ƒ + â˜ƒ;
   }

   private void performRangedAttack(int var1, LivingEntity var2) {
      this.performRangedAttack(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY() + (double)â˜ƒ.getEyeHeight() * 0.5, â˜ƒ.getZ(), â˜ƒ == 0 && this.random.nextFloat() < 0.001F);
   }

   private void performRangedAttack(int var1, double var2, double var4, double var6, boolean var8) {
      if (!this.isSilent()) {
         this.level.levelEvent(null, 1024, this.blockPosition(), 0);
      }

      double â˜ƒ = this.getHeadX(â˜ƒ);
      double â˜ƒx = this.getHeadY(â˜ƒ);
      double â˜ƒxx = this.getHeadZ(â˜ƒ);
      double â˜ƒxxx = â˜ƒ - â˜ƒ;
      double â˜ƒxxxx = â˜ƒ - â˜ƒx;
      double â˜ƒxxxxx = â˜ƒ - â˜ƒxx;
      WitherSkull â˜ƒxxxxxx = new WitherSkull(this.level, this, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
      â˜ƒxxxxxx.setOwner(this);
      if (â˜ƒ) {
         â˜ƒxxxxxx.setDangerous(true);
      }

      â˜ƒxxxxxx.setPosRaw(â˜ƒ, â˜ƒx, â˜ƒxx);
      this.level.addFreshEntity(â˜ƒxxxxxx);
   }

   @Override
   public void performRangedAttack(LivingEntity var1, float var2) {
      this.performRangedAttack(0, â˜ƒ);
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (â˜ƒ == DamageSource.DROWN || â˜ƒ.getEntity() instanceof WitherBoss) {
         return false;
      } else if (this.getInvulnerableTicks() > 0 && â˜ƒ != DamageSource.OUT_OF_WORLD) {
         return false;
      } else {
         if (this.isPowered()) {
            Entity â˜ƒ = â˜ƒ.getDirectEntity();
            if (â˜ƒ instanceof AbstractArrow) {
               return false;
            }
         }

         Entity â˜ƒ = â˜ƒ.getEntity();
         if (â˜ƒ != null && !(â˜ƒ instanceof Player) && â˜ƒ instanceof LivingEntity && ((LivingEntity)â˜ƒ).getMobType() == this.getMobType()) {
            return false;
         } else {
            if (this.destroyBlocksTick <= 0) {
               this.destroyBlocksTick = 20;
            }

            for(int â˜ƒ = 0; â˜ƒ < this.idleHeadUpdates.length; ++â˜ƒ) {
               this.idleHeadUpdates[â˜ƒ] += 3;
            }

            return super.hurt(â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
      super.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);
      ItemEntity â˜ƒ = this.spawnAtLocation(Items.NETHER_STAR);
      if (â˜ƒ != null) {
         â˜ƒ.setExtendedLifetime();
      }
   }

   @Override
   public void checkDespawn() {
      if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
         this.discard();
      } else {
         this.noActionTime = 0;
      }
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      return false;
   }

   @Override
   public boolean addEffect(MobEffectInstance var1, @Nullable Entity var2) {
      return false;
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 300.0)
         .add(Attributes.MOVEMENT_SPEED, 0.6F)
         .add(Attributes.FOLLOW_RANGE, 40.0)
         .add(Attributes.ARMOR, 4.0);
   }

   public float getHeadYRot(int var1) {
      return this.yRotHeads[â˜ƒ];
   }

   public float getHeadXRot(int var1) {
      return this.xRotHeads[â˜ƒ];
   }

   public int getInvulnerableTicks() {
      return this.entityData.get(DATA_ID_INV);
   }

   public void setInvulnerableTicks(int var1) {
      this.entityData.set(DATA_ID_INV, â˜ƒ);
   }

   public int getAlternativeTarget(int var1) {
      return this.entityData.get((EntityDataAccessor)DATA_TARGETS.get(â˜ƒ));
   }

   public void setAlternativeTarget(int var1, int var2) {
      this.entityData.set((EntityDataAccessor)DATA_TARGETS.get(â˜ƒ), â˜ƒ);
   }

   @Override
   public boolean isPowered() {
      return this.getHealth() <= this.getMaxHealth() / 2.0F;
   }

   @Override
   public MobType getMobType() {
      return MobType.UNDEAD;
   }

   @Override
   protected boolean canRide(Entity var1) {
      return false;
   }

   @Override
   public boolean canChangeDimensions() {
      return false;
   }

   @Override
   public boolean canBeAffected(MobEffectInstance var1) {
      return â˜ƒ.getEffect() == MobEffects.WITHER ? false : super.canBeAffected(â˜ƒ);
   }

   class WitherDoNothingGoal extends Goal {
      public WitherDoNothingGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         return WitherBoss.this.getInvulnerableTicks() > 0;
      }
   }
}
