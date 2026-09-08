package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class EnderMan extends Monster implements NeutralMob {
   private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(
      SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.15F, AttributeModifier.Operation.ADDITION
   );
   private static final int DELAY_BETWEEN_CREEPY_STARE_SOUND = 400;
   private static final int MIN_DEAGGRESSION_TIME = 600;
   private static final EntityDataAccessor<Optional<BlockState>> DATA_CARRY_STATE = SynchedEntityData.defineId(
      EnderMan.class, EntityDataSerializers.BLOCK_STATE
   );
   private static final EntityDataAccessor<Boolean> DATA_CREEPY = SynchedEntityData.defineId(EnderMan.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(EnderMan.class, EntityDataSerializers.BOOLEAN);
   private int lastStareSound = Integer.MIN_VALUE;
   private int targetChangeTime;
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   private UUID persistentAngerTarget;

   public EnderMan(EntityType<? extends EnderMan> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.maxUpStep = 1.0F;
      this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new EnderMan.EndermanFreezeWhenLookedAt(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(10, new EnderMan.EndermanLeaveBlockGoal(this));
      this.goalSelector.addGoal(11, new EnderMan.EndermanTakeBlockGoal(this));
      this.targetSelector.addGoal(1, new EnderMan.EndermanLookForPlayerGoal(this, this::isAngryAt));
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Endermite.class, true, false));
      this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 40.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3F)
         .add(Attributes.ATTACK_DAMAGE, 7.0)
         .add(Attributes.FOLLOW_RANGE, 64.0);
   }

   @Override
   public void setTarget(@Nullable LivingEntity var1) {
      super.setTarget(â˜ƒ);
      AttributeInstance â˜ƒ = this.getAttribute(Attributes.MOVEMENT_SPEED);
      if (â˜ƒ == null) {
         this.targetChangeTime = 0;
         this.entityData.set(DATA_CREEPY, false);
         this.entityData.set(DATA_STARED_AT, false);
         â˜ƒ.removeModifier(SPEED_MODIFIER_ATTACKING);
      } else {
         this.targetChangeTime = this.tickCount;
         this.entityData.set(DATA_CREEPY, true);
         if (!â˜ƒ.hasModifier(SPEED_MODIFIER_ATTACKING)) {
            â˜ƒ.addTransientModifier(SPEED_MODIFIER_ATTACKING);
         }
      }
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_CARRY_STATE, Optional.empty());
      this.entityData.define(DATA_CREEPY, false);
      this.entityData.define(DATA_STARED_AT, false);
   }

   @Override
   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
   }

   @Override
   public void setRemainingPersistentAngerTime(int var1) {
      this.remainingPersistentAngerTime = â˜ƒ;
   }

   @Override
   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   @Override
   public void setPersistentAngerTarget(@Nullable UUID var1) {
      this.persistentAngerTarget = â˜ƒ;
   }

   @Override
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   public void playStareSound() {
      if (this.tickCount >= this.lastStareSound + 400) {
         this.lastStareSound = this.tickCount;
         if (!this.isSilent()) {
            this.level.playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
         }
      }
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_CREEPY.equals(â˜ƒ) && this.hasBeenStaredAt() && this.level.isClientSide) {
         this.playStareSound();
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      BlockState â˜ƒ = this.getCarriedBlock();
      if (â˜ƒ != null) {
         â˜ƒ.put("carriedBlockState", NbtUtils.writeBlockState(â˜ƒ));
      }

      this.addPersistentAngerSaveData(â˜ƒ);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      BlockState â˜ƒ = null;
      if (â˜ƒ.contains("carriedBlockState", 10)) {
         â˜ƒ = NbtUtils.readBlockState(â˜ƒ.getCompound("carriedBlockState"));
         if (â˜ƒ.isAir()) {
            â˜ƒ = null;
         }
      }

      this.setCarriedBlock(â˜ƒ);
      this.readPersistentAngerSaveData(this.level, â˜ƒ);
   }

   boolean isLookingAtMe(Player var1) {
      ItemStack â˜ƒ = â˜ƒ.getInventory().armor.get(3);
      if (â˜ƒ.is(Blocks.CARVED_PUMPKIN.asItem())) {
         return false;
      } else {
         Vec3 â˜ƒ = â˜ƒ.getViewVector(1.0F).normalize();
         Vec3 â˜ƒx = new Vec3(this.getX() - â˜ƒ.getX(), this.getEyeY() - â˜ƒ.getEyeY(), this.getZ() - â˜ƒ.getZ());
         double â˜ƒxx = â˜ƒx.length();
         â˜ƒx = â˜ƒx.normalize();
         double â˜ƒxxx = â˜ƒ.dot(â˜ƒx);
         return â˜ƒxxx > 1.0 - 0.025 / â˜ƒxx ? â˜ƒ.hasLineOfSight(this) : false;
      }
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 2.55F;
   }

   @Override
   public void aiStep() {
      if (this.level.isClientSide) {
         for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
            this.level
               .addParticle(
                  ParticleTypes.PORTAL,
                  this.getRandomX(0.5),
                  this.getRandomY() - 0.25,
                  this.getRandomZ(0.5),
                  (this.random.nextDouble() - 0.5) * 2.0,
                  -this.random.nextDouble(),
                  (this.random.nextDouble() - 0.5) * 2.0
               );
         }
      }

      this.jumping = false;
      if (!this.level.isClientSide) {
         this.updatePersistentAnger((ServerLevel)this.level, true);
      }

      super.aiStep();
   }

   @Override
   public boolean isSensitiveToWater() {
      return true;
   }

   @Override
   protected void customServerAiStep() {
      if (this.level.isDay() && this.tickCount >= this.targetChangeTime + 600) {
         float â˜ƒ = this.getBrightness();
         if (â˜ƒ > 0.5F && this.level.canSeeSky(this.blockPosition()) && this.random.nextFloat() * 30.0F < (â˜ƒ - 0.4F) * 2.0F) {
            this.setTarget(null);
            this.teleport();
         }
      }

      super.customServerAiStep();
   }

   protected boolean teleport() {
      if (!this.level.isClientSide() && this.isAlive()) {
         double â˜ƒ = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
         double â˜ƒx = this.getY() + (double)(this.random.nextInt(64) - 32);
         double â˜ƒxx = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
         return this.teleport(â˜ƒ, â˜ƒx, â˜ƒxx);
      } else {
         return false;
      }
   }

   boolean teleportTowards(Entity var1) {
      Vec3 â˜ƒ = new Vec3(this.getX() - â˜ƒ.getX(), this.getY(0.5) - â˜ƒ.getEyeY(), this.getZ() - â˜ƒ.getZ());
      â˜ƒ = â˜ƒ.normalize();
      double â˜ƒx = 16.0;
      double â˜ƒxx = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - â˜ƒ.x * 16.0;
      double â˜ƒxxx = this.getY() + (double)(this.random.nextInt(16) - 8) - â˜ƒ.y * 16.0;
      double â˜ƒxxxx = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - â˜ƒ.z * 16.0;
      return this.teleport(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   private boolean teleport(double var1, double var3, double var5) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos(â˜ƒ, â˜ƒ, â˜ƒ);

      while(â˜ƒ.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(â˜ƒ).getMaterial().blocksMotion()) {
         â˜ƒ.move(Direction.DOWN);
      }

      BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
      boolean â˜ƒxx = â˜ƒx.getMaterial().blocksMotion();
      boolean â˜ƒxxx = â˜ƒx.getFluidState().is(FluidTags.WATER);
      if (â˜ƒxx && !â˜ƒxxx) {
         boolean â˜ƒxxxx = this.randomTeleport(â˜ƒ, â˜ƒ, â˜ƒ, true);
         if (â˜ƒxxxx && !this.isSilent()) {
            this.level.playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
         }

         return â˜ƒxxxx;
      } else {
         return false;
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.ENDERMAN_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENDERMAN_DEATH;
   }

   @Override
   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
      super.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);
      BlockState â˜ƒ = this.getCarriedBlock();
      if (â˜ƒ != null) {
         this.spawnAtLocation(â˜ƒ.getBlock());
      }
   }

   public void setCarriedBlock(@Nullable BlockState var1) {
      this.entityData.set(DATA_CARRY_STATE, Optional.ofNullable(â˜ƒ));
   }

   @Nullable
   public BlockState getCarriedBlock() {
      return (BlockState)((Optional)this.entityData.get(DATA_CARRY_STATE)).orElse(null);
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (â˜ƒ instanceof IndirectEntityDamageSource) {
         for(int â˜ƒ = 0; â˜ƒ < 64; ++â˜ƒ) {
            if (this.teleport()) {
               return true;
            }
         }

         return false;
      } else {
         boolean â˜ƒ = super.hurt(â˜ƒ, â˜ƒ);
         if (!this.level.isClientSide() && !(â˜ƒ.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
            this.teleport();
         }

         return â˜ƒ;
      }
   }

   public boolean isCreepy() {
      return this.entityData.get(DATA_CREEPY);
   }

   public boolean hasBeenStaredAt() {
      return this.entityData.get(DATA_STARED_AT);
   }

   public void setBeingStaredAt() {
      this.entityData.set(DATA_STARED_AT, true);
   }

   @Override
   public boolean requiresCustomPersistence() {
      return super.requiresCustomPersistence() || this.getCarriedBlock() != null;
   }

   static class EndermanFreezeWhenLookedAt extends Goal {
      private final EnderMan enderman;
      private LivingEntity target;

      public EndermanFreezeWhenLookedAt(EnderMan var1) {
         this.enderman = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         this.target = this.enderman.getTarget();
         if (!(this.target instanceof Player)) {
            return false;
         } else {
            double â˜ƒ = this.target.distanceToSqr(this.enderman);
            return â˜ƒ > 256.0 ? false : this.enderman.isLookingAtMe((Player)this.target);
         }
      }

      @Override
      public void start() {
         this.enderman.getNavigation().stop();
      }

      @Override
      public void tick() {
         this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
      }
   }

   static class EndermanLeaveBlockGoal extends Goal {
      private final EnderMan enderman;

      public EndermanLeaveBlockGoal(EnderMan var1) {
         this.enderman = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         if (this.enderman.getCarriedBlock() == null) {
            return false;
         } else if (!this.enderman.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
         } else {
            return this.enderman.getRandom().nextInt(2000) == 0;
         }
      }

      @Override
      public void tick() {
         Random â˜ƒ = this.enderman.getRandom();
         Level â˜ƒx = this.enderman.level;
         int â˜ƒxx = Mth.floor(this.enderman.getX() - 1.0 + â˜ƒ.nextDouble() * 2.0);
         int â˜ƒxxx = Mth.floor(this.enderman.getY() + â˜ƒ.nextDouble() * 2.0);
         int â˜ƒxxxx = Mth.floor(this.enderman.getZ() - 1.0 + â˜ƒ.nextDouble() * 2.0);
         BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
         BlockState â˜ƒxxxxxx = â˜ƒx.getBlockState(â˜ƒxxxxx);
         BlockPos â˜ƒxxxxxxx = â˜ƒxxxxx.below();
         BlockState â˜ƒxxxxxxxx = â˜ƒx.getBlockState(â˜ƒxxxxxxx);
         BlockState â˜ƒxxxxxxxxx = this.enderman.getCarriedBlock();
         if (â˜ƒxxxxxxxxx != null) {
            â˜ƒxxxxxxxxx = Block.updateFromNeighbourShapes(â˜ƒxxxxxxxxx, this.enderman.level, â˜ƒxxxxx);
            if (this.canPlaceBlock(â˜ƒx, â˜ƒxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx)) {
               â˜ƒx.setBlock(â˜ƒxxxxx, â˜ƒxxxxxxxxx, 3);
               â˜ƒx.gameEvent(this.enderman, GameEvent.BLOCK_PLACE, â˜ƒxxxxx);
               this.enderman.setCarriedBlock(null);
            }
         }
      }

      private boolean canPlaceBlock(Level var1, BlockPos var2, BlockState var3, BlockState var4, BlockState var5, BlockPos var6) {
         return â˜ƒ.isAir()
            && !â˜ƒ.isAir()
            && !â˜ƒ.is(Blocks.BEDROCK)
            && â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)
            && â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)
            && â˜ƒ.getEntities(this.enderman, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(â˜ƒ))).isEmpty();
      }
   }

   static class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
      private final EnderMan enderman;
      private Player pendingTarget;
      private int aggroTime;
      private int teleportTime;
      private final TargetingConditions startAggroTargetConditions;
      private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();

      public EndermanLookForPlayerGoal(EnderMan var1, @Nullable Predicate<LivingEntity> var2) {
         super(â˜ƒ, Player.class, 10, false, false, â˜ƒ);
         this.enderman = â˜ƒ;
         this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(var1x -> â˜ƒ.isLookingAtMe((Player)var1x));
      }

      @Override
      public boolean canUse() {
         this.pendingTarget = this.enderman.level.getNearestPlayer(this.startAggroTargetConditions, this.enderman);
         return this.pendingTarget != null;
      }

      @Override
      public void start() {
         this.aggroTime = 5;
         this.teleportTime = 0;
         this.enderman.setBeingStaredAt();
      }

      @Override
      public void stop() {
         this.pendingTarget = null;
         super.stop();
      }

      @Override
      public boolean canContinueToUse() {
         if (this.pendingTarget != null) {
            if (!this.enderman.isLookingAtMe(this.pendingTarget)) {
               return false;
            } else {
               this.enderman.lookAt(this.pendingTarget, 10.0F, 10.0F);
               return true;
            }
         } else {
            return this.target != null && this.continueAggroTargetConditions.test(this.enderman, this.target) ? true : super.canContinueToUse();
         }
      }

      @Override
      public void tick() {
         if (this.enderman.getTarget() == null) {
            super.setTarget(null);
         }

         if (this.pendingTarget != null) {
            if (--this.aggroTime <= 0) {
               this.target = this.pendingTarget;
               this.pendingTarget = null;
               super.start();
            }
         } else {
            if (this.target != null && !this.enderman.isPassenger()) {
               if (this.enderman.isLookingAtMe((Player)this.target)) {
                  if (this.target.distanceToSqr(this.enderman) < 16.0) {
                     this.enderman.teleport();
                  }

                  this.teleportTime = 0;
               } else if (this.target.distanceToSqr(this.enderman) > 256.0 && this.teleportTime++ >= 30 && this.enderman.teleportTowards(this.target)) {
                  this.teleportTime = 0;
               }
            }

            super.tick();
         }
      }
   }

   static class EndermanTakeBlockGoal extends Goal {
      private final EnderMan enderman;

      public EndermanTakeBlockGoal(EnderMan var1) {
         this.enderman = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         if (this.enderman.getCarriedBlock() != null) {
            return false;
         } else if (!this.enderman.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
         } else {
            return this.enderman.getRandom().nextInt(20) == 0;
         }
      }

      @Override
      public void tick() {
         Random â˜ƒ = this.enderman.getRandom();
         Level â˜ƒx = this.enderman.level;
         int â˜ƒxx = Mth.floor(this.enderman.getX() - 2.0 + â˜ƒ.nextDouble() * 4.0);
         int â˜ƒxxx = Mth.floor(this.enderman.getY() + â˜ƒ.nextDouble() * 3.0);
         int â˜ƒxxxx = Mth.floor(this.enderman.getZ() - 2.0 + â˜ƒ.nextDouble() * 4.0);
         BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
         BlockState â˜ƒxxxxxx = â˜ƒx.getBlockState(â˜ƒxxxxx);
         Vec3 â˜ƒxxxxxxx = new Vec3((double)this.enderman.getBlockX() + 0.5, (double)â˜ƒxxx + 0.5, (double)this.enderman.getBlockZ() + 0.5);
         Vec3 â˜ƒxxxxxxxx = new Vec3((double)â˜ƒxx + 0.5, (double)â˜ƒxxx + 0.5, (double)â˜ƒxxxx + 0.5);
         BlockHitResult â˜ƒxxxxxxxxx = â˜ƒx.clip(new ClipContext(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this.enderman));
         boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.getBlockPos().equals(â˜ƒxxxxx);
         if (â˜ƒxxxxxx.is(BlockTags.ENDERMAN_HOLDABLE) && â˜ƒxxxxxxxxxx) {
            â˜ƒx.removeBlock(â˜ƒxxxxx, false);
            â˜ƒx.gameEvent(this.enderman, GameEvent.BLOCK_DESTROY, â˜ƒxxxxx);
            this.enderman.setCarriedBlock(â˜ƒxxxxxx.getBlock().defaultBlockState());
         }
      }
   }
}
