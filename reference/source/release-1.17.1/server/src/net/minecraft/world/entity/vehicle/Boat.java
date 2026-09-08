package net.minecraft.world.entity.vehicle;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Boat extends Entity {
   private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_LEFT = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_RIGHT = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> DATA_ID_BUBBLE_TIME = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
   public static final int PADDLE_LEFT = 0;
   public static final int PADDLE_RIGHT = 1;
   private static final int TIME_TO_EJECT = 60;
   private static final double PADDLE_SPEED = (float) (Math.PI / 8);
   public static final double PADDLE_SOUND_TIME = (float) (Math.PI / 4);
   public static final int BUBBLE_TIME = 60;
   private final float[] paddlePositions = new float[2];
   private float invFriction;
   private float outOfControlTicks;
   private float deltaRotation;
   private int lerpSteps;
   private double lerpX;
   private double lerpY;
   private double lerpZ;
   private double lerpYRot;
   private double lerpXRot;
   private boolean inputLeft;
   private boolean inputRight;
   private boolean inputUp;
   private boolean inputDown;
   private double waterLevel;
   private float landFriction;
   private Boat.Status status;
   private Boat.Status oldStatus;
   private double lastYd;
   private boolean isAboveBubbleColumn;
   private boolean bubbleColumnDirectionIsDown;
   private float bubbleMultiplier;
   private float bubbleAngle;
   private float bubbleAngleO;

   public Boat(EntityType<? extends Boat> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.blocksBuilding = true;
   }

   public Boat(Level var1, double var2, double var4, double var6) {
      this(EntityType.BOAT, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.xo = â˜ƒ;
      this.yo = â˜ƒ;
      this.zo = â˜ƒ;
   }

   @Override
   protected float getEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height;
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   @Override
   protected void defineSynchedData() {
      this.entityData.define(DATA_ID_HURT, 0);
      this.entityData.define(DATA_ID_HURTDIR, 1);
      this.entityData.define(DATA_ID_DAMAGE, 0.0F);
      this.entityData.define(DATA_ID_TYPE, Boat.Type.OAK.ordinal());
      this.entityData.define(DATA_ID_PADDLE_LEFT, false);
      this.entityData.define(DATA_ID_PADDLE_RIGHT, false);
      this.entityData.define(DATA_ID_BUBBLE_TIME, 0);
   }

   @Override
   public boolean canCollideWith(Entity var1) {
      return canVehicleCollide(this, â˜ƒ);
   }

   public static boolean canVehicleCollide(Entity var0, Entity var1) {
      return (â˜ƒ.canBeCollidedWith() || â˜ƒ.isPushable()) && !â˜ƒ.isPassengerOfSameVehicle(â˜ƒ);
   }

   @Override
   public boolean canBeCollidedWith() {
      return true;
   }

   @Override
   public boolean isPushable() {
      return true;
   }

   @Override
   protected Vec3 getRelativePortalPosition(Direction.Axis var1, BlockUtil.FoundRectangle var2) {
      return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(â˜ƒ, â˜ƒ));
   }

   @Override
   public double getPassengersRidingOffset() {
      return -0.1;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (!this.level.isClientSide && !this.isRemoved()) {
         this.setHurtDir(-this.getHurtDir());
         this.setHurtTime(10);
         this.setDamage(this.getDamage() + â˜ƒ * 10.0F);
         this.markHurt();
         this.gameEvent(GameEvent.ENTITY_DAMAGED, â˜ƒ.getEntity());
         boolean â˜ƒ = â˜ƒ.getEntity() instanceof Player && ((Player)â˜ƒ.getEntity()).getAbilities().instabuild;
         if (â˜ƒ || this.getDamage() > 40.0F) {
            if (!â˜ƒ && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
               this.spawnAtLocation(this.getDropItem());
            }

            this.discard();
         }

         return true;
      } else {
         return true;
      }
   }

   @Override
   public void onAboveBubbleCol(boolean var1) {
      if (!this.level.isClientSide) {
         this.isAboveBubbleColumn = true;
         this.bubbleColumnDirectionIsDown = â˜ƒ;
         if (this.getBubbleTime() == 0) {
            this.setBubbleTime(60);
         }
      }

      this.level
         .addParticle(
            ParticleTypes.SPLASH,
            this.getX() + (double)this.random.nextFloat(),
            this.getY() + 0.7,
            this.getZ() + (double)this.random.nextFloat(),
            0.0,
            0.0,
            0.0
         );
      if (this.random.nextInt(20) == 0) {
         this.level
            .playLocalSound(
               this.getX(), this.getY(), this.getZ(), this.getSwimSplashSound(), this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat(), false
            );
      }

      this.gameEvent(GameEvent.SPLASH, this.getControllingPassenger());
   }

   @Override
   public void push(Entity var1) {
      if (â˜ƒ instanceof Boat) {
         if (â˜ƒ.getBoundingBox().minY < this.getBoundingBox().maxY) {
            super.push(â˜ƒ);
         }
      } else if (â˜ƒ.getBoundingBox().minY <= this.getBoundingBox().minY) {
         super.push(â˜ƒ);
      }
   }

   public Item getDropItem() {
      switch(this.getBoatType()) {
         case OAK:
         default:
            return Items.OAK_BOAT;
         case SPRUCE:
            return Items.SPRUCE_BOAT;
         case BIRCH:
            return Items.BIRCH_BOAT;
         case JUNGLE:
            return Items.JUNGLE_BOAT;
         case ACACIA:
            return Items.ACACIA_BOAT;
         case DARK_OAK:
            return Items.DARK_OAK_BOAT;
      }
   }

   @Override
   public void animateHurt() {
      this.setHurtDir(-this.getHurtDir());
      this.setHurtTime(10);
      this.setDamage(this.getDamage() * 11.0F);
   }

   @Override
   public boolean isPickable() {
      return !this.isRemoved();
   }

   @Override
   public void lerpTo(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.lerpX = â˜ƒ;
      this.lerpY = â˜ƒ;
      this.lerpZ = â˜ƒ;
      this.lerpYRot = (double)â˜ƒ;
      this.lerpXRot = (double)â˜ƒ;
      this.lerpSteps = 10;
   }

   @Override
   public Direction getMotionDirection() {
      return this.getDirection().getClockWise();
   }

   @Override
   public void tick() {
      this.oldStatus = this.status;
      this.status = this.getStatus();
      if (this.status != Boat.Status.UNDER_WATER && this.status != Boat.Status.UNDER_FLOWING_WATER) {
         this.outOfControlTicks = 0.0F;
      } else {
         ++this.outOfControlTicks;
      }

      if (!this.level.isClientSide && this.outOfControlTicks >= 60.0F) {
         this.ejectPassengers();
      }

      if (this.getHurtTime() > 0) {
         this.setHurtTime(this.getHurtTime() - 1);
      }

      if (this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      super.tick();
      this.tickLerp();
      if (this.isControlledByLocalInstance()) {
         if (!(this.getFirstPassenger() instanceof Player)) {
            this.setPaddleState(false, false);
         }

         this.floatBoat();
         if (this.level.isClientSide) {
            this.controlBoat();
            this.level.sendPacketToServer(new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
         }

         this.move(MoverType.SELF, this.getDeltaMovement());
      } else {
         this.setDeltaMovement(Vec3.ZERO);
      }

      this.tickBubbleColumn();

      for(int â˜ƒ = 0; â˜ƒ <= 1; ++â˜ƒ) {
         if (this.getPaddleState(â˜ƒ)) {
            if (!this.isSilent()
               && (double)(this.paddlePositions[â˜ƒ] % (float) (Math.PI * 2)) <= (float) (Math.PI / 4)
               && ((double)this.paddlePositions[â˜ƒ] + (float) (Math.PI / 8)) % (float) (Math.PI * 2) >= (float) (Math.PI / 4)) {
               SoundEvent â˜ƒx = this.getPaddleSound();
               if (â˜ƒx != null) {
                  Vec3 â˜ƒxx = this.getViewVector(1.0F);
                  double â˜ƒxxx = â˜ƒ == 1 ? -â˜ƒxx.z : â˜ƒxx.z;
                  double â˜ƒxxxx = â˜ƒ == 1 ? â˜ƒxx.x : -â˜ƒxx.x;
                  this.level
                     .playSound(
                        null,
                        this.getX() + â˜ƒxxx,
                        this.getY(),
                        this.getZ() + â˜ƒxxxx,
                        â˜ƒx,
                        this.getSoundSource(),
                        1.0F,
                        0.8F + 0.4F * this.random.nextFloat()
                     );
                  this.level
                     .gameEvent(this.getControllingPassenger(), GameEvent.SPLASH, new BlockPos(this.getX() + â˜ƒxxx, this.getY(), this.getZ() + â˜ƒxxxx));
               }
            }

            this.paddlePositions[â˜ƒ] = (float)((double)this.paddlePositions[â˜ƒ] + (float) (Math.PI / 8));
         } else {
            this.paddlePositions[â˜ƒ] = 0.0F;
         }
      }

      this.checkInsideBlocks();
      List<Entity> â˜ƒ = this.level.getEntities(this, this.getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(this));
      if (!â˜ƒ.isEmpty()) {
         boolean â˜ƒx = !this.level.isClientSide && !(this.getControllingPassenger() instanceof Player);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
            Entity â˜ƒxxx = (Entity)â˜ƒ.get(â˜ƒxx);
            if (!â˜ƒxxx.hasPassenger(this)) {
               if (â˜ƒx
                  && this.getPassengers().size() < 2
                  && !â˜ƒxxx.isPassenger()
                  && â˜ƒxxx.getBbWidth() < this.getBbWidth()
                  && â˜ƒxxx instanceof LivingEntity
                  && !(â˜ƒxxx instanceof WaterAnimal)
                  && !(â˜ƒxxx instanceof Player)) {
                  â˜ƒxxx.startRiding(this);
               } else {
                  this.push(â˜ƒxxx);
               }
            }
         }
      }
   }

   private void tickBubbleColumn() {
      if (this.level.isClientSide) {
         int â˜ƒ = this.getBubbleTime();
         if (â˜ƒ > 0) {
            this.bubbleMultiplier += 0.05F;
         } else {
            this.bubbleMultiplier -= 0.1F;
         }

         this.bubbleMultiplier = Mth.clamp(this.bubbleMultiplier, 0.0F, 1.0F);
         this.bubbleAngleO = this.bubbleAngle;
         this.bubbleAngle = 10.0F * (float)Math.sin((double)(0.5F * (float)this.level.getGameTime())) * this.bubbleMultiplier;
      } else {
         if (!this.isAboveBubbleColumn) {
            this.setBubbleTime(0);
         }

         int â˜ƒ = this.getBubbleTime();
         if (â˜ƒ > 0) {
            this.setBubbleTime(--â˜ƒ);
            int â˜ƒx = 60 - â˜ƒ - 1;
            if (â˜ƒx > 0 && â˜ƒ == 0) {
               this.setBubbleTime(0);
               Vec3 â˜ƒxx = this.getDeltaMovement();
               if (this.bubbleColumnDirectionIsDown) {
                  this.setDeltaMovement(â˜ƒxx.add(0.0, -0.7, 0.0));
                  this.ejectPassengers();
               } else {
                  this.setDeltaMovement(â˜ƒxx.x, this.hasPassenger(var0 -> var0 instanceof Player) ? 2.7 : 0.6, â˜ƒxx.z);
               }
            }

            this.isAboveBubbleColumn = false;
         }
      }
   }

   @Nullable
   protected SoundEvent getPaddleSound() {
      switch(this.getStatus()) {
         case IN_WATER:
         case UNDER_WATER:
         case UNDER_FLOWING_WATER:
            return SoundEvents.BOAT_PADDLE_WATER;
         case ON_LAND:
            return SoundEvents.BOAT_PADDLE_LAND;
         case IN_AIR:
         default:
            return null;
      }
   }

   private void tickLerp() {
      if (this.isControlledByLocalInstance()) {
         this.lerpSteps = 0;
         this.setPacketCoordinates(this.getX(), this.getY(), this.getZ());
      }

      if (this.lerpSteps > 0) {
         double â˜ƒ = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
         double â˜ƒx = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
         double â˜ƒxx = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
         double â˜ƒxxx = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
         this.setYRot(this.getYRot() + (float)â˜ƒxxx / (float)this.lerpSteps);
         this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
         --this.lerpSteps;
         this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
         this.setRot(this.getYRot(), this.getXRot());
      }
   }

   public void setPaddleState(boolean var1, boolean var2) {
      this.entityData.set(DATA_ID_PADDLE_LEFT, â˜ƒ);
      this.entityData.set(DATA_ID_PADDLE_RIGHT, â˜ƒ);
   }

   public float getRowingTime(int var1, float var2) {
      return this.getPaddleState(â˜ƒ)
         ? (float)Mth.clampedLerp((double)this.paddlePositions[â˜ƒ] - (float) (Math.PI / 8), (double)this.paddlePositions[â˜ƒ], (double)â˜ƒ)
         : 0.0F;
   }

   private Boat.Status getStatus() {
      Boat.Status â˜ƒ = this.isUnderwater();
      if (â˜ƒ != null) {
         this.waterLevel = this.getBoundingBox().maxY;
         return â˜ƒ;
      } else if (this.checkInWater()) {
         return Boat.Status.IN_WATER;
      } else {
         float â˜ƒ = this.getGroundFriction();
         if (â˜ƒ > 0.0F) {
            this.landFriction = â˜ƒ;
            return Boat.Status.ON_LAND;
         } else {
            return Boat.Status.IN_AIR;
         }
      }
   }

   public float getWaterLevelAbove() {
      AABB â˜ƒ = this.getBoundingBox();
      int â˜ƒx = Mth.floor(â˜ƒ.minX);
      int â˜ƒxx = Mth.ceil(â˜ƒ.maxX);
      int â˜ƒxxx = Mth.floor(â˜ƒ.maxY);
      int â˜ƒxxxx = Mth.ceil(â˜ƒ.maxY - this.lastYd);
      int â˜ƒxxxxx = Mth.floor(â˜ƒ.minZ);
      int â˜ƒxxxxxx = Mth.ceil(â˜ƒ.maxZ);
      BlockPos.MutableBlockPos â˜ƒxxxxxxx = new BlockPos.MutableBlockPos();

      label39:
      for(int â˜ƒxxxxxxxx = â˜ƒxxx; â˜ƒxxxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxxxx) {
         float â˜ƒxxxxxxxxx = 0.0F;

         for(int â˜ƒxxxxxxxxxx = â˜ƒx; â˜ƒxxxxxxxxxx < â˜ƒxx; ++â˜ƒxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxx = â˜ƒxxxxx; â˜ƒxxxxxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxxxxxx) {
               â˜ƒxxxxxxx.set(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxx);
               FluidState â˜ƒxxxxxxxxxxxx = this.level.getFluidState(â˜ƒxxxxxxx);
               if (â˜ƒxxxxxxxxxxxx.is(FluidTags.WATER)) {
                  â˜ƒxxxxxxxxx = Math.max(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxxx.getHeight(this.level, â˜ƒxxxxxxx));
               }

               if (â˜ƒxxxxxxxxx >= 1.0F) {
                  continue label39;
               }
            }
         }

         if (â˜ƒxxxxxxxxx < 1.0F) {
            return (float)â˜ƒxxxxxxx.getY() + â˜ƒxxxxxxxxx;
         }
      }

      return (float)(â˜ƒxxxx + 1);
   }

   public float getGroundFriction() {
      AABB â˜ƒ = this.getBoundingBox();
      AABB â˜ƒx = new AABB(â˜ƒ.minX, â˜ƒ.minY - 0.001, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.minY, â˜ƒ.maxZ);
      int â˜ƒxx = Mth.floor(â˜ƒx.minX) - 1;
      int â˜ƒxxx = Mth.ceil(â˜ƒx.maxX) + 1;
      int â˜ƒxxxx = Mth.floor(â˜ƒx.minY) - 1;
      int â˜ƒxxxxx = Mth.ceil(â˜ƒx.maxY) + 1;
      int â˜ƒxxxxxx = Mth.floor(â˜ƒx.minZ) - 1;
      int â˜ƒxxxxxxx = Mth.ceil(â˜ƒx.maxZ) + 1;
      VoxelShape â˜ƒxxxxxxxx = Shapes.create(â˜ƒx);
      float â˜ƒxxxxxxxxx = 0.0F;
      int â˜ƒxxxxxxxxxx = 0;
      BlockPos.MutableBlockPos â˜ƒxxxxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxxxxxxx = â˜ƒxx; â˜ƒxxxxxxxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxx; â˜ƒxxxxxxxxxxxxx < â˜ƒxxxxxxx; ++â˜ƒxxxxxxxxxxxxx) {
            int â˜ƒxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxxx != â˜ƒxx && â˜ƒxxxxxxxxxxxx != â˜ƒxxx - 1 ? 0 : 1)
               + (â˜ƒxxxxxxxxxxxxx != â˜ƒxxxxxx && â˜ƒxxxxxxxxxxxxx != â˜ƒxxxxxxx - 1 ? 0 : 1);
            if (â˜ƒxxxxxxxxxxxxxx != 2) {
               for(int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxx; â˜ƒxxxxxxxxxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxxxxxxxxxx) {
                  if (â˜ƒxxxxxxxxxxxxxx <= 0 || â˜ƒxxxxxxxxxxxxxxx != â˜ƒxxxx && â˜ƒxxxxxxxxxxxxxxx != â˜ƒxxxxx - 1) {
                     â˜ƒxxxxxxxxxxx.set(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx);
                     BlockState â˜ƒxxxxxxxxxxxxxxxx = this.level.getBlockState(â˜ƒxxxxxxxxxxx);
                     if (!(â˜ƒxxxxxxxxxxxxxxxx.getBlock() instanceof WaterlilyBlock)
                        && Shapes.joinIsNotEmpty(
                           â˜ƒxxxxxxxxxxxxxxxx.getCollisionShape(this.level, â˜ƒxxxxxxxxxxx)
                              .move((double)â˜ƒxxxxxxxxxxxx, (double)â˜ƒxxxxxxxxxxxxxxx, (double)â˜ƒxxxxxxxxxxxxx),
                           â˜ƒxxxxxxxx,
                           BooleanOp.AND
                        )) {
                        â˜ƒxxxxxxxxx += â˜ƒxxxxxxxxxxxxxxxx.getBlock().getFriction();
                        ++â˜ƒxxxxxxxxxx;
                     }
                  }
               }
            }
         }
      }

      return â˜ƒxxxxxxxxx / (float)â˜ƒxxxxxxxxxx;
   }

   private boolean checkInWater() {
      AABB â˜ƒ = this.getBoundingBox();
      int â˜ƒx = Mth.floor(â˜ƒ.minX);
      int â˜ƒxx = Mth.ceil(â˜ƒ.maxX);
      int â˜ƒxxx = Mth.floor(â˜ƒ.minY);
      int â˜ƒxxxx = Mth.ceil(â˜ƒ.minY + 0.001);
      int â˜ƒxxxxx = Mth.floor(â˜ƒ.minZ);
      int â˜ƒxxxxxx = Mth.ceil(â˜ƒ.maxZ);
      boolean â˜ƒxxxxxxx = false;
      this.waterLevel = -Double.MAX_VALUE;
      BlockPos.MutableBlockPos â˜ƒxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxxxx = â˜ƒx; â˜ƒxxxxxxxxx < â˜ƒxx; ++â˜ƒxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxx = â˜ƒxxx; â˜ƒxxxxxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxx = â˜ƒxxxxx; â˜ƒxxxxxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxxxxxx) {
               â˜ƒxxxxxxxx.set(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx);
               FluidState â˜ƒxxxxxxxxxxxx = this.level.getFluidState(â˜ƒxxxxxxxx);
               if (â˜ƒxxxxxxxxxxxx.is(FluidTags.WATER)) {
                  float â˜ƒxxxxxxxxxxxxx = (float)â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxxx.getHeight(this.level, â˜ƒxxxxxxxx);
                  this.waterLevel = Math.max((double)â˜ƒxxxxxxxxxxxxx, this.waterLevel);
                  â˜ƒxxxxxxx |= â˜ƒ.minY < (double)â˜ƒxxxxxxxxxxxxx;
               }
            }
         }
      }

      return â˜ƒxxxxxxx;
   }

   @Nullable
   private Boat.Status isUnderwater() {
      AABB â˜ƒ = this.getBoundingBox();
      double â˜ƒx = â˜ƒ.maxY + 0.001;
      int â˜ƒxx = Mth.floor(â˜ƒ.minX);
      int â˜ƒxxx = Mth.ceil(â˜ƒ.maxX);
      int â˜ƒxxxx = Mth.floor(â˜ƒ.maxY);
      int â˜ƒxxxxx = Mth.ceil(â˜ƒx);
      int â˜ƒxxxxxx = Mth.floor(â˜ƒ.minZ);
      int â˜ƒxxxxxxx = Mth.ceil(â˜ƒ.maxZ);
      boolean â˜ƒxxxxxxxx = false;
      BlockPos.MutableBlockPos â˜ƒxxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxxxxx = â˜ƒxx; â˜ƒxxxxxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxx = â˜ƒxxxx; â˜ƒxxxxxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxx; â˜ƒxxxxxxxxxxxx < â˜ƒxxxxxxx; ++â˜ƒxxxxxxxxxxxx) {
               â˜ƒxxxxxxxxx.set(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx);
               FluidState â˜ƒxxxxxxxxxxxxx = this.level.getFluidState(â˜ƒxxxxxxxxx);
               if (â˜ƒxxxxxxxxxxxxx.is(FluidTags.WATER) && â˜ƒx < (double)((float)â˜ƒxxxxxxxxx.getY() + â˜ƒxxxxxxxxxxxxx.getHeight(this.level, â˜ƒxxxxxxxxx))) {
                  if (!â˜ƒxxxxxxxxxxxxx.isSource()) {
                     return Boat.Status.UNDER_FLOWING_WATER;
                  }

                  â˜ƒxxxxxxxx = true;
               }
            }
         }
      }

      return â˜ƒxxxxxxxx ? Boat.Status.UNDER_WATER : null;
   }

   private void floatBoat() {
      double â˜ƒ = -0.04F;
      double â˜ƒx = this.isNoGravity() ? 0.0 : -0.04F;
      double â˜ƒxx = 0.0;
      this.invFriction = 0.05F;
      if (this.oldStatus == Boat.Status.IN_AIR && this.status != Boat.Status.IN_AIR && this.status != Boat.Status.ON_LAND) {
         this.waterLevel = this.getY(1.0);
         this.setPos(this.getX(), (double)(this.getWaterLevelAbove() - this.getBbHeight()) + 0.101, this.getZ());
         this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.0, 1.0));
         this.lastYd = 0.0;
         this.status = Boat.Status.IN_WATER;
      } else {
         if (this.status == Boat.Status.IN_WATER) {
            â˜ƒxx = (this.waterLevel - this.getY()) / (double)this.getBbHeight();
            this.invFriction = 0.9F;
         } else if (this.status == Boat.Status.UNDER_FLOWING_WATER) {
            â˜ƒx = -7.0E-4;
            this.invFriction = 0.9F;
         } else if (this.status == Boat.Status.UNDER_WATER) {
            â˜ƒxx = 0.01F;
            this.invFriction = 0.45F;
         } else if (this.status == Boat.Status.IN_AIR) {
            this.invFriction = 0.9F;
         } else if (this.status == Boat.Status.ON_LAND) {
            this.invFriction = this.landFriction;
            if (this.getControllingPassenger() instanceof Player) {
               this.landFriction /= 2.0F;
            }
         }

         Vec3 â˜ƒ = this.getDeltaMovement();
         this.setDeltaMovement(â˜ƒ.x * (double)this.invFriction, â˜ƒ.y + â˜ƒx, â˜ƒ.z * (double)this.invFriction);
         this.deltaRotation *= this.invFriction;
         if (â˜ƒxx > 0.0) {
            Vec3 â˜ƒx = this.getDeltaMovement();
            this.setDeltaMovement(â˜ƒx.x, (â˜ƒx.y + â˜ƒxx * 0.06153846016296973) * 0.75, â˜ƒx.z);
         }
      }
   }

   private void controlBoat() {
      if (this.isVehicle()) {
         float â˜ƒ = 0.0F;
         if (this.inputLeft) {
            --this.deltaRotation;
         }

         if (this.inputRight) {
            ++this.deltaRotation;
         }

         if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
            â˜ƒ += 0.005F;
         }

         this.setYRot(this.getYRot() + this.deltaRotation);
         if (this.inputUp) {
            â˜ƒ += 0.04F;
         }

         if (this.inputDown) {
            â˜ƒ -= 0.005F;
         }

         this.setDeltaMovement(
            this.getDeltaMovement()
               .add(
                  (double)(Mth.sin(-this.getYRot() * (float) (Math.PI / 180.0)) * â˜ƒ),
                  0.0,
                  (double)(Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * â˜ƒ)
               )
         );
         this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
      }
   }

   @Override
   public void positionRider(Entity var1) {
      if (this.hasPassenger(â˜ƒ)) {
         float â˜ƒ = 0.0F;
         float â˜ƒx = (float)((this.isRemoved() ? 0.01F : this.getPassengersRidingOffset()) + â˜ƒ.getMyRidingOffset());
         if (this.getPassengers().size() > 1) {
            int â˜ƒxx = this.getPassengers().indexOf(â˜ƒ);
            if (â˜ƒxx == 0) {
               â˜ƒ = 0.2F;
            } else {
               â˜ƒ = -0.6F;
            }

            if (â˜ƒ instanceof Animal) {
               â˜ƒ = (float)((double)â˜ƒ + 0.2);
            }
         }

         Vec3 â˜ƒ = new Vec3((double)â˜ƒ, 0.0, 0.0).yRot(-this.getYRot() * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
         â˜ƒ.setPos(this.getX() + â˜ƒ.x, this.getY() + (double)â˜ƒx, this.getZ() + â˜ƒ.z);
         â˜ƒ.setYRot(â˜ƒ.getYRot() + this.deltaRotation);
         â˜ƒ.setYHeadRot(â˜ƒ.getYHeadRot() + this.deltaRotation);
         this.clampRotation(â˜ƒ);
         if (â˜ƒ instanceof Animal && this.getPassengers().size() > 1) {
            int â˜ƒx = â˜ƒ.getId() % 2 == 0 ? 90 : 270;
            â˜ƒ.setYBodyRot(((Animal)â˜ƒ).yBodyRot + (float)â˜ƒx);
            â˜ƒ.setYHeadRot(â˜ƒ.getYHeadRot() + (float)â˜ƒx);
         }
      }
   }

   @Override
   public Vec3 getDismountLocationForPassenger(LivingEntity var1) {
      Vec3 â˜ƒ = getCollisionHorizontalEscapeVector((double)(this.getBbWidth() * Mth.SQRT_OF_TWO), (double)â˜ƒ.getBbWidth(), â˜ƒ.getYRot());
      double â˜ƒx = this.getX() + â˜ƒ.x;
      double â˜ƒxx = this.getZ() + â˜ƒ.z;
      BlockPos â˜ƒxxx = new BlockPos(â˜ƒx, this.getBoundingBox().maxY, â˜ƒxx);
      BlockPos â˜ƒxxxx = â˜ƒxxx.below();
      if (!this.level.isWaterAt(â˜ƒxxxx)) {
         List<Vec3> â˜ƒxxxxx = Lists.<Vec3>newArrayList();
         double â˜ƒxxxxxx = this.level.getBlockFloorHeight(â˜ƒxxx);
         if (DismountHelper.isBlockFloorValid(â˜ƒxxxxxx)) {
            â˜ƒxxxxx.add(new Vec3(â˜ƒx, (double)â˜ƒxxx.getY() + â˜ƒxxxxxx, â˜ƒxx));
         }

         double â˜ƒxxxxx = this.level.getBlockFloorHeight(â˜ƒxxxx);
         if (DismountHelper.isBlockFloorValid(â˜ƒxxxxx)) {
            â˜ƒxxxxx.add(new Vec3(â˜ƒx, (double)â˜ƒxxxx.getY() + â˜ƒxxxxx, â˜ƒxx));
         }

         for(Pose â˜ƒxxxxx : â˜ƒ.getDismountPoses()) {
            for(Vec3 â˜ƒxxxxxx : â˜ƒxxxxx) {
               if (DismountHelper.canDismountTo(this.level, â˜ƒxxxxxx, â˜ƒ, â˜ƒxxxxx)) {
                  â˜ƒ.setPose(â˜ƒxxxxx);
                  return â˜ƒxxxxxx;
               }
            }
         }
      }

      return super.getDismountLocationForPassenger(â˜ƒ);
   }

   protected void clampRotation(Entity var1) {
      â˜ƒ.setYBodyRot(this.getYRot());
      float â˜ƒ = Mth.wrapDegrees(â˜ƒ.getYRot() - this.getYRot());
      float â˜ƒx = Mth.clamp(â˜ƒ, -105.0F, 105.0F);
      â˜ƒ.yRotO += â˜ƒx - â˜ƒ;
      â˜ƒ.setYRot(â˜ƒ.getYRot() + â˜ƒx - â˜ƒ);
      â˜ƒ.setYHeadRot(â˜ƒ.getYRot());
   }

   @Override
   public void onPassengerTurned(Entity var1) {
      this.clampRotation(â˜ƒ);
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putString("Type", this.getBoatType().getName());
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      if (â˜ƒ.contains("Type", 8)) {
         this.setType(Boat.Type.byName(â˜ƒ.getString("Type")));
      }
   }

   @Override
   public InteractionResult interact(Player var1, InteractionHand var2) {
      if (â˜ƒ.isSecondaryUseActive()) {
         return InteractionResult.PASS;
      } else if (this.outOfControlTicks < 60.0F) {
         if (!this.level.isClientSide) {
            return â˜ƒ.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
         } else {
            return InteractionResult.SUCCESS;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   protected void checkFallDamage(double var1, boolean var3, BlockState var4, BlockPos var5) {
      this.lastYd = this.getDeltaMovement().y;
      if (!this.isPassenger()) {
         if (â˜ƒ) {
            if (this.fallDistance > 3.0F) {
               if (this.status != Boat.Status.ON_LAND) {
                  this.fallDistance = 0.0F;
                  return;
               }

               this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
               if (!this.level.isClientSide && !this.isRemoved()) {
                  this.kill();
                  if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                     for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
                        this.spawnAtLocation(this.getBoatType().getPlanks());
                     }

                     for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
                        this.spawnAtLocation(Items.STICK);
                     }
                  }
               }
            }

            this.fallDistance = 0.0F;
         } else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && â˜ƒ < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - â˜ƒ);
         }
      }
   }

   public boolean getPaddleState(int var1) {
      return this.entityData.get(â˜ƒ == 0 ? DATA_ID_PADDLE_LEFT : DATA_ID_PADDLE_RIGHT) && this.getControllingPassenger() != null;
   }

   public void setDamage(float var1) {
      this.entityData.set(DATA_ID_DAMAGE, â˜ƒ);
   }

   public float getDamage() {
      return this.entityData.get(DATA_ID_DAMAGE);
   }

   public void setHurtTime(int var1) {
      this.entityData.set(DATA_ID_HURT, â˜ƒ);
   }

   public int getHurtTime() {
      return this.entityData.get(DATA_ID_HURT);
   }

   private void setBubbleTime(int var1) {
      this.entityData.set(DATA_ID_BUBBLE_TIME, â˜ƒ);
   }

   private int getBubbleTime() {
      return this.entityData.get(DATA_ID_BUBBLE_TIME);
   }

   public float getBubbleAngle(float var1) {
      return Mth.lerp(â˜ƒ, this.bubbleAngleO, this.bubbleAngle);
   }

   public void setHurtDir(int var1) {
      this.entityData.set(DATA_ID_HURTDIR, â˜ƒ);
   }

   public int getHurtDir() {
      return this.entityData.get(DATA_ID_HURTDIR);
   }

   public void setType(Boat.Type var1) {
      this.entityData.set(DATA_ID_TYPE, â˜ƒ.ordinal());
   }

   public Boat.Type getBoatType() {
      return Boat.Type.byId(this.entityData.get(DATA_ID_TYPE));
   }

   @Override
   protected boolean canAddPassenger(Entity var1) {
      return this.getPassengers().size() < 2 && !this.isEyeInFluid(FluidTags.WATER);
   }

   @Nullable
   @Override
   public Entity getControllingPassenger() {
      return this.getFirstPassenger();
   }

   public void setInput(boolean var1, boolean var2, boolean var3, boolean var4) {
      this.inputLeft = â˜ƒ;
      this.inputRight = â˜ƒ;
      this.inputUp = â˜ƒ;
      this.inputDown = â˜ƒ;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }

   @Override
   public boolean isUnderWater() {
      return this.status == Boat.Status.UNDER_WATER || this.status == Boat.Status.UNDER_FLOWING_WATER;
   }

   @Override
   public ItemStack getPickResult() {
      return new ItemStack(this.getDropItem());
   }

   public static enum Status {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;
   }

   public static enum Type {
      OAK(Blocks.OAK_PLANKS, "oak"),
      SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
      BIRCH(Blocks.BIRCH_PLANKS, "birch"),
      JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
      ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
      DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak");

      private final String name;
      private final Block planks;

      private Type(Block var3, String var4) {
         this.name = â˜ƒ;
         this.planks = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public Block getPlanks() {
         return this.planks;
      }

      public String toString() {
         return this.name;
      }

      public static Boat.Type byId(int var0) {
         Boat.Type[] â˜ƒ = values();
         if (â˜ƒ < 0 || â˜ƒ >= â˜ƒ.length) {
            â˜ƒ = 0;
         }

         return â˜ƒ[â˜ƒ];
      }

      public static Boat.Type byName(String var0) {
         Boat.Type[] â˜ƒ = values();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
            if (â˜ƒ[â˜ƒx].getName().equals(â˜ƒ)) {
               return â˜ƒ[â˜ƒx];
            }
         }

         return â˜ƒ[0];
      }
   }
}
