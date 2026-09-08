package net.minecraft.world.entity.monster;

import com.mojang.math.Vector3f;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Shulker extends AbstractGolem implements Enemy {
   private static final UUID COVERED_ARMOR_MODIFIER_UUID = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
   private static final AttributeModifier COVERED_ARMOR_MODIFIER = new AttributeModifier(
      COVERED_ARMOR_MODIFIER_UUID, "Covered armor bonus", 20.0, AttributeModifier.Operation.ADDITION
   );
   protected static final EntityDataAccessor<Direction> DATA_ATTACH_FACE_ID = SynchedEntityData.defineId(Shulker.class, EntityDataSerializers.DIRECTION);
   protected static final EntityDataAccessor<Byte> DATA_PEEK_ID = SynchedEntityData.defineId(Shulker.class, EntityDataSerializers.BYTE);
   protected static final EntityDataAccessor<Byte> DATA_COLOR_ID = SynchedEntityData.defineId(Shulker.class, EntityDataSerializers.BYTE);
   private static final int TELEPORT_STEPS = 6;
   private static final byte NO_COLOR = 16;
   private static final byte DEFAULT_COLOR = 16;
   private static final int MAX_TELEPORT_DISTANCE = 8;
   private static final int OTHER_SHULKER_SCAN_RADIUS = 8;
   private static final int OTHER_SHULKER_LIMIT = 5;
   private static final float PEEK_PER_TICK = 0.05F;
   static final Vector3f FORWARD = Util.make(() -> {
      Vec3i â˜ƒ = Direction.SOUTH.getNormal();
      return new Vector3f((float)â˜ƒ.getX(), (float)â˜ƒ.getY(), (float)â˜ƒ.getZ());
   });
   private float currentPeekAmountO;
   private float currentPeekAmount;
   @Nullable
   private BlockPos clientOldAttachPosition;
   private int clientSideTeleportInterpolation;
   private static final float MAX_LID_OPEN = 1.0F;

   public Shulker(EntityType<? extends Shulker> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.xpReward = 5;
      this.lookControl = new Shulker.ShulkerLookControl(this);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F, 0.02F, true));
      this.goalSelector.addGoal(4, new Shulker.ShulkerAttackGoal());
      this.goalSelector.addGoal(7, new Shulker.ShulkerPeekGoal());
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, this.getClass()).setAlertOthers());
      this.targetSelector.addGoal(2, new Shulker.ShulkerNearestAttackGoal(this));
      this.targetSelector.addGoal(3, new Shulker.ShulkerDefenseAttackGoal(this));
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.SHULKER_AMBIENT;
   }

   @Override
   public void playAmbientSound() {
      if (!this.isClosed()) {
         super.playAmbientSound();
      }
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.SHULKER_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return this.isClosed() ? SoundEvents.SHULKER_HURT_CLOSED : SoundEvents.SHULKER_HURT;
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_ATTACH_FACE_ID, Direction.DOWN);
      this.entityData.define(DATA_PEEK_ID, (byte)0);
      this.entityData.define(DATA_COLOR_ID, (byte)16);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0);
   }

   @Override
   protected BodyRotationControl createBodyControl() {
      return new Shulker.ShulkerBodyRotationControl(this);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setAttachFace(Direction.from3DDataValue(â˜ƒ.getByte("AttachFace")));
      this.entityData.set(DATA_PEEK_ID, â˜ƒ.getByte("Peek"));
      if (â˜ƒ.contains("Color", 99)) {
         this.entityData.set(DATA_COLOR_ID, â˜ƒ.getByte("Color"));
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putByte("AttachFace", (byte)this.getAttachFace().get3DDataValue());
      â˜ƒ.putByte("Peek", this.entityData.get(DATA_PEEK_ID));
      â˜ƒ.putByte("Color", this.entityData.get(DATA_COLOR_ID));
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level.isClientSide && !this.isPassenger() && !this.canStayAt(this.blockPosition(), this.getAttachFace())) {
         this.findNewAttachment();
      }

      if (this.updatePeekAmount()) {
         this.onPeekAmountChange();
      }

      if (this.level.isClientSide) {
         if (this.clientSideTeleportInterpolation > 0) {
            --this.clientSideTeleportInterpolation;
         } else {
            this.clientOldAttachPosition = null;
         }
      }
   }

   private void findNewAttachment() {
      Direction â˜ƒ = this.findAttachableSurface(this.blockPosition());
      if (â˜ƒ != null) {
         this.setAttachFace(â˜ƒ);
      } else {
         this.teleportSomewhere();
      }
   }

   @Override
   protected AABB makeBoundingBox() {
      float â˜ƒ = getPhysicalPeek(this.currentPeekAmount);
      Direction â˜ƒx = this.getAttachFace().getOpposite();
      float â˜ƒxx = this.getType().getWidth() / 2.0F;
      return getProgressAabb(â˜ƒx, â˜ƒ).move(this.getX() - (double)â˜ƒxx, this.getY(), this.getZ() - (double)â˜ƒxx);
   }

   private static float getPhysicalPeek(float var0) {
      return 0.5F - Mth.sin((0.5F + â˜ƒ) * (float) Math.PI) * 0.5F;
   }

   private boolean updatePeekAmount() {
      this.currentPeekAmountO = this.currentPeekAmount;
      float â˜ƒ = (float)this.getRawPeekAmount() * 0.01F;
      if (this.currentPeekAmount == â˜ƒ) {
         return false;
      } else {
         if (this.currentPeekAmount > â˜ƒ) {
            this.currentPeekAmount = Mth.clamp(this.currentPeekAmount - 0.05F, â˜ƒ, 1.0F);
         } else {
            this.currentPeekAmount = Mth.clamp(this.currentPeekAmount + 0.05F, 0.0F, â˜ƒ);
         }

         return true;
      }
   }

   private void onPeekAmountChange() {
      this.reapplyPosition();
      float â˜ƒ = getPhysicalPeek(this.currentPeekAmount);
      float â˜ƒx = getPhysicalPeek(this.currentPeekAmountO);
      Direction â˜ƒxx = this.getAttachFace().getOpposite();
      float â˜ƒxxx = â˜ƒ - â˜ƒx;
      if (!(â˜ƒxxx <= 0.0F)) {
         for(Entity â˜ƒxxxx : this.level
            .getEntities(
               this,
               getProgressDeltaAabb(â˜ƒxx, â˜ƒx, â˜ƒ).move(this.getX() - 0.5, this.getY(), this.getZ() - 0.5),
               EntitySelector.NO_SPECTATORS.and(var1x -> !var1x.isPassengerOfSameVehicle(this))
            )) {
            if (!(â˜ƒxxxx instanceof Shulker) && !â˜ƒxxxx.noPhysics) {
               â˜ƒxxxx.move(
                  MoverType.SHULKER,
                  new Vec3((double)(â˜ƒxxx * (float)â˜ƒxx.getStepX()), (double)(â˜ƒxxx * (float)â˜ƒxx.getStepY()), (double)(â˜ƒxxx * (float)â˜ƒxx.getStepZ()))
               );
            }
         }
      }
   }

   public static AABB getProgressAabb(Direction var0, float var1) {
      return getProgressDeltaAabb(â˜ƒ, -1.0F, â˜ƒ);
   }

   public static AABB getProgressDeltaAabb(Direction var0, float var1, float var2) {
      double â˜ƒ = (double)Math.max(â˜ƒ, â˜ƒ);
      double â˜ƒx = (double)Math.min(â˜ƒ, â˜ƒ);
      return new AABB(BlockPos.ZERO)
         .expandTowards((double)â˜ƒ.getStepX() * â˜ƒ, (double)â˜ƒ.getStepY() * â˜ƒ, (double)â˜ƒ.getStepZ() * â˜ƒ)
         .contract((double)(-â˜ƒ.getStepX()) * (1.0 + â˜ƒx), (double)(-â˜ƒ.getStepY()) * (1.0 + â˜ƒx), (double)(-â˜ƒ.getStepZ()) * (1.0 + â˜ƒx));
   }

   @Override
   public double getMyRidingOffset() {
      EntityType<?> â˜ƒ = this.getVehicle().getType();
      return â˜ƒ != EntityType.BOAT && â˜ƒ != EntityType.MINECART ? super.getMyRidingOffset() : 0.1875 - this.getVehicle().getPassengersRidingOffset();
   }

   @Override
   public boolean startRiding(Entity var1, boolean var2) {
      if (this.level.isClientSide()) {
         this.clientOldAttachPosition = null;
         this.clientSideTeleportInterpolation = 0;
      }

      this.setAttachFace(Direction.DOWN);
      return super.startRiding(â˜ƒ, â˜ƒ);
   }

   @Override
   public void stopRiding() {
      super.stopRiding();
      if (this.level.isClientSide) {
         this.clientOldAttachPosition = this.blockPosition();
      }

      this.yBodyRotO = 0.0F;
      this.yBodyRot = 0.0F;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.setYRot(0.0F);
      this.yHeadRot = this.getYRot();
      this.setOldPosAndRot();
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void move(MoverType var1, Vec3 var2) {
      if (â˜ƒ == MoverType.SHULKER_BOX) {
         this.teleportSomewhere();
      } else {
         super.move(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public Vec3 getDeltaMovement() {
      return Vec3.ZERO;
   }

   @Override
   public void setDeltaMovement(Vec3 var1) {
   }

   @Override
   public void setPos(double var1, double var3, double var5) {
      BlockPos â˜ƒ = this.blockPosition();
      if (this.isPassenger()) {
         super.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         super.setPos((double)Mth.floor(â˜ƒ) + 0.5, (double)Mth.floor(â˜ƒ + 0.5), (double)Mth.floor(â˜ƒ) + 0.5);
      }

      if (this.tickCount != 0) {
         BlockPos â˜ƒ = this.blockPosition();
         if (!â˜ƒ.equals(â˜ƒ)) {
            this.entityData.set(DATA_PEEK_ID, (byte)0);
            this.hasImpulse = true;
            if (this.level.isClientSide && !this.isPassenger() && !â˜ƒ.equals(this.clientOldAttachPosition)) {
               this.clientOldAttachPosition = â˜ƒ;
               this.clientSideTeleportInterpolation = 6;
               this.xOld = this.getX();
               this.yOld = this.getY();
               this.zOld = this.getZ();
            }
         }
      }
   }

   @Nullable
   protected Direction findAttachableSurface(BlockPos var1) {
      for(Direction â˜ƒ : Direction.values()) {
         if (this.canStayAt(â˜ƒ, â˜ƒ)) {
            return â˜ƒ;
         }
      }

      return null;
   }

   boolean canStayAt(BlockPos var1, Direction var2) {
      if (this.isPositionBlocked(â˜ƒ)) {
         return false;
      } else {
         Direction â˜ƒ = â˜ƒ.getOpposite();
         if (!this.level.loadedAndEntityCanStandOnFace(â˜ƒ.relative(â˜ƒ), this, â˜ƒ)) {
            return false;
         } else {
            AABB â˜ƒ = getProgressAabb(â˜ƒ, 1.0F).move(â˜ƒ).deflate(1.0E-6);
            return this.level.noCollision(this, â˜ƒ);
         }
      }
   }

   private boolean isPositionBlocked(BlockPos var1) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
      if (â˜ƒ.isAir()) {
         return false;
      } else {
         boolean â˜ƒ = â˜ƒ.is(Blocks.MOVING_PISTON) && â˜ƒ.equals(this.blockPosition());
         return !â˜ƒ;
      }
   }

   protected boolean teleportSomewhere() {
      if (!this.isNoAi() && this.isAlive()) {
         BlockPos â˜ƒ = this.blockPosition();

         for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
            BlockPos â˜ƒxx = â˜ƒ.offset(
               Mth.randomBetweenInclusive(this.random, -8, 8), Mth.randomBetweenInclusive(this.random, -8, 8), Mth.randomBetweenInclusive(this.random, -8, 8)
            );
            if (â˜ƒxx.getY() > this.level.getMinBuildHeight()
               && this.level.isEmptyBlock(â˜ƒxx)
               && this.level.getWorldBorder().isWithinBounds(â˜ƒxx)
               && this.level.noCollision(this, new AABB(â˜ƒxx).deflate(1.0E-6))) {
               Direction â˜ƒxxx = this.findAttachableSurface(â˜ƒxx);
               if (â˜ƒxxx != null) {
                  this.unRide();
                  this.setAttachFace(â˜ƒxxx);
                  this.playSound(SoundEvents.SHULKER_TELEPORT, 1.0F, 1.0F);
                  this.setPos((double)â˜ƒxx.getX() + 0.5, (double)â˜ƒxx.getY(), (double)â˜ƒxx.getZ() + 0.5);
                  this.entityData.set(DATA_PEEK_ID, (byte)0);
                  this.setTarget(null);
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public void lerpTo(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.lerpSteps = 0;
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setRot(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isClosed()) {
         Entity â˜ƒ = â˜ƒ.getDirectEntity();
         if (â˜ƒ instanceof AbstractArrow) {
            return false;
         }
      }

      if (!super.hurt(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         if ((double)this.getHealth() < (double)this.getMaxHealth() * 0.5 && this.random.nextInt(4) == 0) {
            this.teleportSomewhere();
         } else if (â˜ƒ.isProjectile()) {
            Entity â˜ƒ = â˜ƒ.getDirectEntity();
            if (â˜ƒ != null && â˜ƒ.getType() == EntityType.SHULKER_BULLET) {
               this.hitByShulkerBullet();
            }
         }

         return true;
      }
   }

   private boolean isClosed() {
      return this.getRawPeekAmount() == 0;
   }

   private void hitByShulkerBullet() {
      Vec3 â˜ƒ = this.position();
      AABB â˜ƒx = this.getBoundingBox();
      if (!this.isClosed() && this.teleportSomewhere()) {
         int â˜ƒxx = this.level.getEntities(EntityType.SHULKER, â˜ƒx.inflate(8.0), Entity::isAlive).size();
         float â˜ƒxxx = (float)(â˜ƒxx - 1) / 5.0F;
         if (!(this.level.random.nextFloat() < â˜ƒxxx)) {
            Shulker â˜ƒxxxx = EntityType.SHULKER.create(this.level);
            DyeColor â˜ƒxxxxx = this.getColor();
            if (â˜ƒxxxxx != null) {
               â˜ƒxxxx.setColor(â˜ƒxxxxx);
            }

            â˜ƒxxxx.moveTo(â˜ƒ);
            this.level.addFreshEntity(â˜ƒxxxx);
         }
      }
   }

   @Override
   public boolean canBeCollidedWith() {
      return this.isAlive();
   }

   public Direction getAttachFace() {
      return this.entityData.get(DATA_ATTACH_FACE_ID);
   }

   private void setAttachFace(Direction var1) {
      this.entityData.set(DATA_ATTACH_FACE_ID, â˜ƒ);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_ATTACH_FACE_ID.equals(â˜ƒ)) {
         this.setBoundingBox(this.makeBoundingBox());
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   private int getRawPeekAmount() {
      return this.entityData.get(DATA_PEEK_ID);
   }

   void setRawPeekAmount(int var1) {
      if (!this.level.isClientSide) {
         this.getAttribute(Attributes.ARMOR).removeModifier(COVERED_ARMOR_MODIFIER);
         if (â˜ƒ == 0) {
            this.getAttribute(Attributes.ARMOR).addPermanentModifier(COVERED_ARMOR_MODIFIER);
            this.playSound(SoundEvents.SHULKER_CLOSE, 1.0F, 1.0F);
            this.gameEvent(GameEvent.SHULKER_CLOSE);
         } else {
            this.playSound(SoundEvents.SHULKER_OPEN, 1.0F, 1.0F);
            this.gameEvent(GameEvent.SHULKER_OPEN);
         }
      }

      this.entityData.set(DATA_PEEK_ID, (byte)â˜ƒ);
   }

   public float getClientPeekAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.currentPeekAmountO, this.currentPeekAmount);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.5F;
   }

   @Override
   public void recreateFromPacket(ClientboundAddMobPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      this.yBodyRot = 0.0F;
   }

   @Override
   public int getMaxHeadXRot() {
      return 180;
   }

   @Override
   public int getMaxHeadYRot() {
      return 180;
   }

   @Override
   public void push(Entity var1) {
   }

   @Override
   public float getPickRadius() {
      return 0.0F;
   }

   public Optional<Vec3> getRenderPosition(float var1) {
      if (this.clientOldAttachPosition != null && this.clientSideTeleportInterpolation > 0) {
         double â˜ƒ = (double)((float)this.clientSideTeleportInterpolation - â˜ƒ) / 6.0;
         â˜ƒ *= â˜ƒ;
         BlockPos â˜ƒx = this.blockPosition();
         double â˜ƒxx = (double)(â˜ƒx.getX() - this.clientOldAttachPosition.getX()) * â˜ƒ;
         double â˜ƒxxx = (double)(â˜ƒx.getY() - this.clientOldAttachPosition.getY()) * â˜ƒ;
         double â˜ƒxxxx = (double)(â˜ƒx.getZ() - this.clientOldAttachPosition.getZ()) * â˜ƒ;
         return Optional.of(new Vec3(-â˜ƒxx, -â˜ƒxxx, -â˜ƒxxxx));
      } else {
         return Optional.empty();
      }
   }

   private void setColor(DyeColor var1) {
      this.entityData.set(DATA_COLOR_ID, (byte)â˜ƒ.getId());
   }

   @Nullable
   public DyeColor getColor() {
      byte â˜ƒ = this.entityData.get(DATA_COLOR_ID);
      return â˜ƒ != 16 && â˜ƒ <= 15 ? DyeColor.byId(â˜ƒ) : null;
   }

   class ShulkerAttackGoal extends Goal {
      private int attackTime;

      public ShulkerAttackGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         LivingEntity â˜ƒ = Shulker.this.getTarget();
         if (â˜ƒ != null && â˜ƒ.isAlive()) {
            return Shulker.this.level.getDifficulty() != Difficulty.PEACEFUL;
         } else {
            return false;
         }
      }

      @Override
      public void start() {
         this.attackTime = 20;
         Shulker.this.setRawPeekAmount(100);
      }

      @Override
      public void stop() {
         Shulker.this.setRawPeekAmount(0);
      }

      @Override
      public void tick() {
         if (Shulker.this.level.getDifficulty() != Difficulty.PEACEFUL) {
            --this.attackTime;
            LivingEntity â˜ƒ = Shulker.this.getTarget();
            Shulker.this.getLookControl().setLookAt(â˜ƒ, 180.0F, 180.0F);
            double â˜ƒx = Shulker.this.distanceToSqr(â˜ƒ);
            if (â˜ƒx < 400.0) {
               if (this.attackTime <= 0) {
                  this.attackTime = 20 + Shulker.this.random.nextInt(10) * 20 / 2;
                  Shulker.this.level.addFreshEntity(new ShulkerBullet(Shulker.this.level, Shulker.this, â˜ƒ, Shulker.this.getAttachFace().getAxis()));
                  Shulker.this.playSound(SoundEvents.SHULKER_SHOOT, 2.0F, (Shulker.this.random.nextFloat() - Shulker.this.random.nextFloat()) * 0.2F + 1.0F);
               }
            } else {
               Shulker.this.setTarget(null);
            }

            super.tick();
         }
      }
   }

   static class ShulkerBodyRotationControl extends BodyRotationControl {
      public ShulkerBodyRotationControl(Mob var1) {
         super(â˜ƒ);
      }

      @Override
      public void clientTick() {
      }
   }

   static class ShulkerDefenseAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
      public ShulkerDefenseAttackGoal(Shulker var1) {
         super(â˜ƒ, LivingEntity.class, 10, true, false, var0 -> var0 instanceof Enemy);
      }

      @Override
      public boolean canUse() {
         return this.mob.getTeam() == null ? false : super.canUse();
      }

      @Override
      protected AABB getTargetSearchArea(double var1) {
         Direction â˜ƒ = ((Shulker)this.mob).getAttachFace();
         if (â˜ƒ.getAxis() == Direction.Axis.X) {
            return this.mob.getBoundingBox().inflate(4.0, â˜ƒ, â˜ƒ);
         } else {
            return â˜ƒ.getAxis() == Direction.Axis.Z ? this.mob.getBoundingBox().inflate(â˜ƒ, â˜ƒ, 4.0) : this.mob.getBoundingBox().inflate(â˜ƒ, 4.0, â˜ƒ);
         }
      }
   }

   class ShulkerLookControl extends LookControl {
      public ShulkerLookControl(Mob var2) {
         super(â˜ƒ);
      }

      @Override
      protected void clampHeadRotationToBody() {
      }

      @Override
      protected Optional<Float> getYRotD() {
         Direction â˜ƒ = Shulker.this.getAttachFace().getOpposite();
         Vector3f â˜ƒx = Shulker.FORWARD.copy();
         â˜ƒx.transform(â˜ƒ.getRotation());
         Vec3i â˜ƒxx = â˜ƒ.getNormal();
         Vector3f â˜ƒxxx = new Vector3f((float)â˜ƒxx.getX(), (float)â˜ƒxx.getY(), (float)â˜ƒxx.getZ());
         â˜ƒxxx.cross(â˜ƒx);
         double â˜ƒxxxx = this.wantedX - this.mob.getX();
         double â˜ƒxxxxx = this.wantedY - this.mob.getEyeY();
         double â˜ƒxxxxxx = this.wantedZ - this.mob.getZ();
         Vector3f â˜ƒxxxxxxx = new Vector3f((float)â˜ƒxxxx, (float)â˜ƒxxxxx, (float)â˜ƒxxxxxx);
         float â˜ƒxxxxxxxx = â˜ƒxxx.dot(â˜ƒxxxxxxx);
         float â˜ƒxxxxxxxxx = â˜ƒx.dot(â˜ƒxxxxxxx);
         return !(Math.abs(â˜ƒxxxxxxxx) > 1.0E-5F) && !(Math.abs(â˜ƒxxxxxxxxx) > 1.0E-5F)
            ? Optional.empty()
            : Optional.of((float)(Mth.atan2((double)(-â˜ƒxxxxxxxx), (double)â˜ƒxxxxxxxxx) * 180.0F / (float)Math.PI));
      }

      @Override
      protected Optional<Float> getXRotD() {
         return Optional.of(0.0F);
      }
   }

   class ShulkerNearestAttackGoal extends NearestAttackableTargetGoal<Player> {
      public ShulkerNearestAttackGoal(Shulker var2) {
         super(â˜ƒ, Player.class, true);
      }

      @Override
      public boolean canUse() {
         return Shulker.this.level.getDifficulty() == Difficulty.PEACEFUL ? false : super.canUse();
      }

      @Override
      protected AABB getTargetSearchArea(double var1) {
         Direction â˜ƒ = ((Shulker)this.mob).getAttachFace();
         if (â˜ƒ.getAxis() == Direction.Axis.X) {
            return this.mob.getBoundingBox().inflate(4.0, â˜ƒ, â˜ƒ);
         } else {
            return â˜ƒ.getAxis() == Direction.Axis.Z ? this.mob.getBoundingBox().inflate(â˜ƒ, â˜ƒ, 4.0) : this.mob.getBoundingBox().inflate(â˜ƒ, 4.0, â˜ƒ);
         }
      }
   }

   class ShulkerPeekGoal extends Goal {
      private int peekTime;

      @Override
      public boolean canUse() {
         return Shulker.this.getTarget() == null
            && Shulker.this.random.nextInt(40) == 0
            && Shulker.this.canStayAt(Shulker.this.blockPosition(), Shulker.this.getAttachFace());
      }

      @Override
      public boolean canContinueToUse() {
         return Shulker.this.getTarget() == null && this.peekTime > 0;
      }

      @Override
      public void start() {
         this.peekTime = 20 * (1 + Shulker.this.random.nextInt(3));
         Shulker.this.setRawPeekAmount(30);
      }

      @Override
      public void stop() {
         if (Shulker.this.getTarget() == null) {
            Shulker.this.setRawPeekAmount(0);
         }
      }

      @Override
      public void tick() {
         --this.peekTime;
      }
   }
}
