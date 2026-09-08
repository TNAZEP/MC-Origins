package net.minecraft.world.entity.vehicle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractMinecart extends Entity {
   private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DATA_ID_DISPLAY_BLOCK = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_ID_DISPLAY_OFFSET = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_ID_CUSTOM_DISPLAY = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.BOOLEAN);
   private static final ImmutableMap<Pose, ImmutableList<Integer>> POSE_DISMOUNT_HEIGHTS = ImmutableMap.of(
      Pose.STANDING, ImmutableList.of(0, 1, -1), Pose.CROUCHING, ImmutableList.of(0, 1, -1), Pose.SWIMMING, ImmutableList.of(0, 1)
   );
   protected static final float WATER_SLOWDOWN_FACTOR = 0.95F;
   private boolean flipped;
   private static final Map<RailShape, Pair<Vec3i, Vec3i>> EXITS = Util.make(Maps.newEnumMap(RailShape.class), var0 -> {
      Vec3i â˜ƒ = Direction.WEST.getNormal();
      Vec3i â˜ƒx = Direction.EAST.getNormal();
      Vec3i â˜ƒxx = Direction.NORTH.getNormal();
      Vec3i â˜ƒxxx = Direction.SOUTH.getNormal();
      Vec3i â˜ƒxxxx = â˜ƒ.below();
      Vec3i â˜ƒxxxxx = â˜ƒx.below();
      Vec3i â˜ƒxxxxxx = â˜ƒxx.below();
      Vec3i â˜ƒxxxxxxx = â˜ƒxxx.below();
      var0.put(RailShape.NORTH_SOUTH, Pair.of(â˜ƒxx, â˜ƒxxx));
      var0.put(RailShape.EAST_WEST, Pair.of(â˜ƒ, â˜ƒx));
      var0.put(RailShape.ASCENDING_EAST, Pair.of(â˜ƒxxxx, â˜ƒx));
      var0.put(RailShape.ASCENDING_WEST, Pair.of(â˜ƒ, â˜ƒxxxxx));
      var0.put(RailShape.ASCENDING_NORTH, Pair.of(â˜ƒxx, â˜ƒxxxxxxx));
      var0.put(RailShape.ASCENDING_SOUTH, Pair.of(â˜ƒxxxxxx, â˜ƒxxx));
      var0.put(RailShape.SOUTH_EAST, Pair.of(â˜ƒxxx, â˜ƒx));
      var0.put(RailShape.SOUTH_WEST, Pair.of(â˜ƒxxx, â˜ƒ));
      var0.put(RailShape.NORTH_WEST, Pair.of(â˜ƒxx, â˜ƒ));
      var0.put(RailShape.NORTH_EAST, Pair.of(â˜ƒxx, â˜ƒx));
   });
   private int lSteps;
   private double lx;
   private double ly;
   private double lz;
   private double lyr;
   private double lxr;
   private double lxd;
   private double lyd;
   private double lzd;

   protected AbstractMinecart(EntityType<?> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.blocksBuilding = true;
   }

   protected AbstractMinecart(EntityType<?> var1, Level var2, double var3, double var5, double var7) {
      this(â˜ƒ, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.xo = â˜ƒ;
      this.yo = â˜ƒ;
      this.zo = â˜ƒ;
   }

   public static AbstractMinecart createMinecart(Level var0, double var1, double var3, double var5, AbstractMinecart.Type var7) {
      if (â˜ƒ == AbstractMinecart.Type.CHEST) {
         return new MinecartChest(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == AbstractMinecart.Type.FURNACE) {
         return new MinecartFurnace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == AbstractMinecart.Type.TNT) {
         return new MinecartTNT(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == AbstractMinecart.Type.SPAWNER) {
         return new MinecartSpawner(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (â˜ƒ == AbstractMinecart.Type.HOPPER) {
         return new MinecartHopper(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return (AbstractMinecart)(â˜ƒ == AbstractMinecart.Type.COMMAND_BLOCK ? new MinecartCommandBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : new Minecart(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.EVENTS;
   }

   @Override
   protected void defineSynchedData() {
      this.entityData.define(DATA_ID_HURT, 0);
      this.entityData.define(DATA_ID_HURTDIR, 1);
      this.entityData.define(DATA_ID_DAMAGE, 0.0F);
      this.entityData.define(DATA_ID_DISPLAY_BLOCK, Block.getId(Blocks.AIR.defaultBlockState()));
      this.entityData.define(DATA_ID_DISPLAY_OFFSET, 6);
      this.entityData.define(DATA_ID_CUSTOM_DISPLAY, false);
   }

   @Override
   public boolean canCollideWith(Entity var1) {
      return Boat.canVehicleCollide(this, â˜ƒ);
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
      return 0.0;
   }

   @Override
   public Vec3 getDismountLocationForPassenger(LivingEntity var1) {
      Direction â˜ƒ = this.getMotionDirection();
      if (â˜ƒ.getAxis() == Direction.Axis.Y) {
         return super.getDismountLocationForPassenger(â˜ƒ);
      } else {
         int[][] â˜ƒ = DismountHelper.offsetsForDirection(â˜ƒ);
         BlockPos â˜ƒx = this.blockPosition();
         BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();
         ImmutableList<Pose> â˜ƒxxx = â˜ƒ.getDismountPoses();

         for(Pose â˜ƒxxxx : â˜ƒxxx) {
            EntityDimensions â˜ƒxxxxx = â˜ƒ.getDimensions(â˜ƒxxxx);
            float â˜ƒxxxxxx = Math.min(â˜ƒxxxxx.width, 1.0F) / 2.0F;

            for(int â˜ƒxxxxxxx : (ImmutableList)POSE_DISMOUNT_HEIGHTS.get(â˜ƒxxxx)) {
               for(int[] â˜ƒxxxxxxxx : â˜ƒ) {
                  â˜ƒxx.set(â˜ƒx.getX() + â˜ƒxxxxxxxx[0], â˜ƒx.getY() + â˜ƒxxxxxxx, â˜ƒx.getZ() + â˜ƒxxxxxxxx[1]);
                  double â˜ƒxxxxxxxxx = this.level
                     .getBlockFloorHeight(DismountHelper.nonClimbableShape(this.level, â˜ƒxx), () -> DismountHelper.nonClimbableShape(this.level, â˜ƒ.below()));
                  if (DismountHelper.isBlockFloorValid(â˜ƒxxxxxxxxx)) {
                     AABB â˜ƒxxxxxxxxxx = new AABB(
                        (double)(-â˜ƒxxxxxx), 0.0, (double)(-â˜ƒxxxxxx), (double)â˜ƒxxxxxx, (double)â˜ƒxxxxx.height, (double)â˜ƒxxxxxx
                     );
                     Vec3 â˜ƒxxxxxxxxxxx = Vec3.upFromBottomCenterOf(â˜ƒxx, â˜ƒxxxxxxxxx);
                     if (DismountHelper.canDismountTo(this.level, â˜ƒ, â˜ƒxxxxxxxxxx.move(â˜ƒxxxxxxxxxxx))) {
                        â˜ƒ.setPose(â˜ƒxxxx);
                        return â˜ƒxxxxxxxxxxx;
                     }
                  }
               }
            }
         }

         double â˜ƒxxxx = this.getBoundingBox().maxY;
         â˜ƒxx.set((double)â˜ƒx.getX(), â˜ƒxxxx, (double)â˜ƒx.getZ());

         for(Pose â˜ƒxxxxx : â˜ƒxxx) {
            double â˜ƒxxxxxx = (double)â˜ƒ.getDimensions(â˜ƒxxxxx).height;
            int â˜ƒxxxxxxx = Mth.ceil(â˜ƒxxxx - (double)â˜ƒxx.getY() + â˜ƒxxxxxx);
            double â˜ƒxxxxxxxx = DismountHelper.findCeilingFrom(
               â˜ƒxx, â˜ƒxxxxxxx, var1x -> this.level.getBlockState(var1x).getCollisionShape(this.level, var1x)
            );
            if (â˜ƒxxxx + â˜ƒxxxxxx <= â˜ƒxxxxxxxx) {
               â˜ƒ.setPose(â˜ƒxxxxx);
               break;
            }
         }

         return super.getDismountLocationForPassenger(â˜ƒ);
      }
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.level.isClientSide || this.isRemoved()) {
         return true;
      } else if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         this.setHurtDir(-this.getHurtDir());
         this.setHurtTime(10);
         this.markHurt();
         this.setDamage(this.getDamage() + â˜ƒ * 10.0F);
         this.gameEvent(GameEvent.ENTITY_DAMAGED, â˜ƒ.getEntity());
         boolean â˜ƒ = â˜ƒ.getEntity() instanceof Player && ((Player)â˜ƒ.getEntity()).getAbilities().instabuild;
         if (â˜ƒ || this.getDamage() > 40.0F) {
            this.ejectPassengers();
            if (â˜ƒ && !this.hasCustomName()) {
               this.discard();
            } else {
               this.destroy(â˜ƒ);
            }
         }

         return true;
      }
   }

   @Override
   protected float getBlockSpeedFactor() {
      BlockState â˜ƒ = this.level.getBlockState(this.blockPosition());
      return â˜ƒ.is(BlockTags.RAILS) ? 1.0F : super.getBlockSpeedFactor();
   }

   public void destroy(DamageSource var1) {
      this.remove(Entity.RemovalReason.KILLED);
      if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
         ItemStack â˜ƒ = new ItemStack(Items.MINECART);
         if (this.hasCustomName()) {
            â˜ƒ.setHoverName(this.getCustomName());
         }

         this.spawnAtLocation(â˜ƒ);
      }
   }

   @Override
   public void animateHurt() {
      this.setHurtDir(-this.getHurtDir());
      this.setHurtTime(10);
      this.setDamage(this.getDamage() + this.getDamage() * 10.0F);
   }

   @Override
   public boolean isPickable() {
      return !this.isRemoved();
   }

   private static Pair<Vec3i, Vec3i> exits(RailShape var0) {
      return (Pair<Vec3i, Vec3i>)EXITS.get(â˜ƒ);
   }

   @Override
   public Direction getMotionDirection() {
      return this.flipped ? this.getDirection().getOpposite().getClockWise() : this.getDirection().getClockWise();
   }

   @Override
   public void tick() {
      if (this.getHurtTime() > 0) {
         this.setHurtTime(this.getHurtTime() - 1);
      }

      if (this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      this.checkOutOfWorld();
      this.handleNetherPortal();
      if (this.level.isClientSide) {
         if (this.lSteps > 0) {
            double â˜ƒ = this.getX() + (this.lx - this.getX()) / (double)this.lSteps;
            double â˜ƒx = this.getY() + (this.ly - this.getY()) / (double)this.lSteps;
            double â˜ƒxx = this.getZ() + (this.lz - this.getZ()) / (double)this.lSteps;
            double â˜ƒxxx = Mth.wrapDegrees(this.lyr - (double)this.getYRot());
            this.setYRot(this.getYRot() + (float)â˜ƒxxx / (float)this.lSteps);
            this.setXRot(this.getXRot() + (float)(this.lxr - (double)this.getXRot()) / (float)this.lSteps);
            --this.lSteps;
            this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
            this.setRot(this.getYRot(), this.getXRot());
         } else {
            this.reapplyPosition();
            this.setRot(this.getYRot(), this.getXRot());
         }
      } else {
         if (!this.isNoGravity()) {
            double â˜ƒ = this.isInWater() ? -0.005 : -0.04;
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, â˜ƒ, 0.0));
         }

         int â˜ƒ = Mth.floor(this.getX());
         int â˜ƒx = Mth.floor(this.getY());
         int â˜ƒxx = Mth.floor(this.getZ());
         if (this.level.getBlockState(new BlockPos(â˜ƒ, â˜ƒx - 1, â˜ƒxx)).is(BlockTags.RAILS)) {
            --â˜ƒx;
         }

         BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
         BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
         if (BaseRailBlock.isRail(â˜ƒx)) {
            this.moveAlongTrack(â˜ƒ, â˜ƒx);
            if (â˜ƒx.is(Blocks.ACTIVATOR_RAIL)) {
               this.activateMinecart(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒx.getValue(PoweredRailBlock.POWERED));
            }
         } else {
            this.comeOffTrack();
         }

         this.checkInsideBlocks();
         this.setXRot(0.0F);
         double â˜ƒ = this.xo - this.getX();
         double â˜ƒx = this.zo - this.getZ();
         if (â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx > 0.001) {
            this.setYRot((float)(Mth.atan2(â˜ƒx, â˜ƒ) * 180.0 / Math.PI));
            if (this.flipped) {
               this.setYRot(this.getYRot() + 180.0F);
            }
         }

         double â˜ƒ = (double)Mth.wrapDegrees(this.getYRot() - this.yRotO);
         if (â˜ƒ < -170.0 || â˜ƒ >= 170.0) {
            this.setYRot(this.getYRot() + 180.0F);
            this.flipped = !this.flipped;
         }

         this.setRot(this.getYRot(), this.getXRot());
         if (this.getMinecartType() == AbstractMinecart.Type.RIDEABLE && this.getDeltaMovement().horizontalDistanceSqr() > 0.01) {
            List<Entity> â˜ƒ = this.level.getEntities(this, this.getBoundingBox().inflate(0.2F, 0.0, 0.2F), EntitySelector.pushableBy(this));
            if (!â˜ƒ.isEmpty()) {
               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
                  Entity â˜ƒxx = (Entity)â˜ƒ.get(â˜ƒx);
                  if (!(â˜ƒxx instanceof Player)
                     && !(â˜ƒxx instanceof IronGolem)
                     && !(â˜ƒxx instanceof AbstractMinecart)
                     && !this.isVehicle()
                     && !â˜ƒxx.isPassenger()) {
                     â˜ƒxx.startRiding(this);
                  } else {
                     â˜ƒxx.push(this);
                  }
               }
            }
         } else {
            for(Entity â˜ƒ : this.level.getEntities(this, this.getBoundingBox().inflate(0.2F, 0.0, 0.2F))) {
               if (!this.hasPassenger(â˜ƒ) && â˜ƒ.isPushable() && â˜ƒ instanceof AbstractMinecart) {
                  â˜ƒ.push(this);
               }
            }
         }

         this.updateInWaterStateAndDoFluidPushing();
         if (this.isInLava()) {
            this.lavaHurt();
            this.fallDistance *= 0.5F;
         }

         this.firstTick = false;
      }
   }

   protected double getMaxSpeed() {
      return (this.isInWater() ? 4.0 : 8.0) / 20.0;
   }

   public void activateMinecart(int var1, int var2, int var3, boolean var4) {
   }

   protected void comeOffTrack() {
      double â˜ƒ = this.getMaxSpeed();
      Vec3 â˜ƒx = this.getDeltaMovement();
      this.setDeltaMovement(Mth.clamp(â˜ƒx.x, -â˜ƒ, â˜ƒ), â˜ƒx.y, Mth.clamp(â˜ƒx.z, -â˜ƒ, â˜ƒ));
      if (this.onGround) {
         this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
      }

      this.move(MoverType.SELF, this.getDeltaMovement());
      if (!this.onGround) {
         this.setDeltaMovement(this.getDeltaMovement().scale(0.95));
      }
   }

   protected void moveAlongTrack(BlockPos var1, BlockState var2) {
      this.fallDistance = 0.0F;
      double â˜ƒ = this.getX();
      double â˜ƒx = this.getY();
      double â˜ƒxx = this.getZ();
      Vec3 â˜ƒxxx = this.getPos(â˜ƒ, â˜ƒx, â˜ƒxx);
      â˜ƒx = (double)â˜ƒ.getY();
      boolean â˜ƒxxxx = false;
      boolean â˜ƒxxxxx = false;
      if (â˜ƒ.is(Blocks.POWERED_RAIL)) {
         â˜ƒxxxx = â˜ƒ.getValue(PoweredRailBlock.POWERED);
         â˜ƒxxxxx = !â˜ƒxxxx;
      }

      double â˜ƒ = 0.0078125;
      if (this.isInWater()) {
         â˜ƒ *= 0.2;
      }

      Vec3 â˜ƒ = this.getDeltaMovement();
      RailShape â˜ƒx = â˜ƒ.getValue(((BaseRailBlock)â˜ƒ.getBlock()).getShapeProperty());
      switch(â˜ƒx) {
         case ASCENDING_EAST:
            this.setDeltaMovement(â˜ƒ.add(-â˜ƒ, 0.0, 0.0));
            ++â˜ƒx;
            break;
         case ASCENDING_WEST:
            this.setDeltaMovement(â˜ƒ.add(â˜ƒ, 0.0, 0.0));
            ++â˜ƒx;
            break;
         case ASCENDING_NORTH:
            this.setDeltaMovement(â˜ƒ.add(0.0, 0.0, â˜ƒ));
            ++â˜ƒx;
            break;
         case ASCENDING_SOUTH:
            this.setDeltaMovement(â˜ƒ.add(0.0, 0.0, -â˜ƒ));
            ++â˜ƒx;
      }

      â˜ƒ = this.getDeltaMovement();
      Pair<Vec3i, Vec3i> â˜ƒ = exits(â˜ƒx);
      Vec3i â˜ƒx = â˜ƒ.getFirst();
      Vec3i â˜ƒxx = â˜ƒ.getSecond();
      double â˜ƒxxx = (double)(â˜ƒxx.getX() - â˜ƒx.getX());
      double â˜ƒxxxx = (double)(â˜ƒxx.getZ() - â˜ƒx.getZ());
      double â˜ƒxxxxx = Math.sqrt(â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx);
      double â˜ƒxxxxxx = â˜ƒ.x * â˜ƒxxx + â˜ƒ.z * â˜ƒxxxx;
      if (â˜ƒxxxxxx < 0.0) {
         â˜ƒxxx = -â˜ƒxxx;
         â˜ƒxxxx = -â˜ƒxxxx;
      }

      double â˜ƒ = Math.min(2.0, â˜ƒ.horizontalDistance());
      â˜ƒ = new Vec3(â˜ƒ * â˜ƒxxx / â˜ƒxxxxx, â˜ƒ.y, â˜ƒ * â˜ƒxxxx / â˜ƒxxxxx);
      this.setDeltaMovement(â˜ƒ);
      Entity â˜ƒx = this.getFirstPassenger();
      if (â˜ƒx instanceof Player) {
         Vec3 â˜ƒxx = â˜ƒx.getDeltaMovement();
         double â˜ƒxxx = â˜ƒxx.horizontalDistanceSqr();
         double â˜ƒxxxx = this.getDeltaMovement().horizontalDistanceSqr();
         if (â˜ƒxxx > 1.0E-4 && â˜ƒxxxx < 0.01) {
            this.setDeltaMovement(this.getDeltaMovement().add(â˜ƒxx.x * 0.1, 0.0, â˜ƒxx.z * 0.1));
            â˜ƒxxxxx = false;
         }
      }

      if (â˜ƒxxxxx) {
         double â˜ƒ = this.getDeltaMovement().horizontalDistance();
         if (â˜ƒ < 0.03) {
            this.setDeltaMovement(Vec3.ZERO);
         } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.5, 0.0, 0.5));
         }
      }

      double â˜ƒx = (double)â˜ƒ.getX() + 0.5 + (double)â˜ƒx.getX() * 0.5;
      double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5 + (double)â˜ƒx.getZ() * 0.5;
      double â˜ƒxxx = (double)â˜ƒ.getX() + 0.5 + (double)â˜ƒxx.getX() * 0.5;
      double â˜ƒxxxx = (double)â˜ƒ.getZ() + 0.5 + (double)â˜ƒxx.getZ() * 0.5;
      â˜ƒxxx = â˜ƒxxx - â˜ƒx;
      â˜ƒxxxx = â˜ƒxxxx - â˜ƒxx;
      double â˜ƒ;
      if (â˜ƒxxx == 0.0) {
         â˜ƒ = â˜ƒxx - (double)â˜ƒ.getZ();
      } else if (â˜ƒxxxx == 0.0) {
         â˜ƒ = â˜ƒ - (double)â˜ƒ.getX();
      } else {
         double â˜ƒ = â˜ƒ - â˜ƒx;
         double â˜ƒx = â˜ƒxx - â˜ƒxx;
         â˜ƒ = (â˜ƒ * â˜ƒxxx + â˜ƒx * â˜ƒxxxx) * 2.0;
      }

      â˜ƒ = â˜ƒx + â˜ƒxxx * â˜ƒ;
      â˜ƒxx = â˜ƒxx + â˜ƒxxxx * â˜ƒ;
      this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
      double â˜ƒ = this.isVehicle() ? 0.75 : 1.0;
      double â˜ƒx = this.getMaxSpeed();
      â˜ƒ = this.getDeltaMovement();
      this.move(MoverType.SELF, new Vec3(Mth.clamp(â˜ƒ * â˜ƒ.x, -â˜ƒx, â˜ƒx), 0.0, Mth.clamp(â˜ƒ * â˜ƒ.z, -â˜ƒx, â˜ƒx)));
      if (â˜ƒx.getY() != 0 && Mth.floor(this.getX()) - â˜ƒ.getX() == â˜ƒx.getX() && Mth.floor(this.getZ()) - â˜ƒ.getZ() == â˜ƒx.getZ()) {
         this.setPos(this.getX(), this.getY() + (double)â˜ƒx.getY(), this.getZ());
      } else if (â˜ƒxx.getY() != 0 && Mth.floor(this.getX()) - â˜ƒ.getX() == â˜ƒxx.getX() && Mth.floor(this.getZ()) - â˜ƒ.getZ() == â˜ƒxx.getZ()) {
         this.setPos(this.getX(), this.getY() + (double)â˜ƒxx.getY(), this.getZ());
      }

      this.applyNaturalSlowdown();
      Vec3 â˜ƒ = this.getPos(this.getX(), this.getY(), this.getZ());
      if (â˜ƒ != null && â˜ƒxxx != null) {
         double â˜ƒx = (â˜ƒxxx.y - â˜ƒ.y) * 0.05;
         Vec3 â˜ƒxx = this.getDeltaMovement();
         double â˜ƒxxx = â˜ƒxx.horizontalDistance();
         if (â˜ƒxxx > 0.0) {
            this.setDeltaMovement(â˜ƒxx.multiply((â˜ƒxxx + â˜ƒx) / â˜ƒxxx, 1.0, (â˜ƒxxx + â˜ƒx) / â˜ƒxxx));
         }

         this.setPos(this.getX(), â˜ƒ.y, this.getZ());
      }

      int â˜ƒ = Mth.floor(this.getX());
      int â˜ƒx = Mth.floor(this.getZ());
      if (â˜ƒ != â˜ƒ.getX() || â˜ƒx != â˜ƒ.getZ()) {
         Vec3 â˜ƒxx = this.getDeltaMovement();
         double â˜ƒxxx = â˜ƒxx.horizontalDistance();
         this.setDeltaMovement(â˜ƒxxx * (double)(â˜ƒ - â˜ƒ.getX()), â˜ƒxx.y, â˜ƒxxx * (double)(â˜ƒx - â˜ƒ.getZ()));
      }

      if (â˜ƒxxxx) {
         Vec3 â˜ƒ = this.getDeltaMovement();
         double â˜ƒx = â˜ƒ.horizontalDistance();
         if (â˜ƒx > 0.01) {
            double â˜ƒxx = 0.06;
            this.setDeltaMovement(â˜ƒ.add(â˜ƒ.x / â˜ƒx * 0.06, 0.0, â˜ƒ.z / â˜ƒx * 0.06));
         } else {
            Vec3 â˜ƒ = this.getDeltaMovement();
            double â˜ƒx = â˜ƒ.x;
            double â˜ƒxx = â˜ƒ.z;
            if (â˜ƒx == RailShape.EAST_WEST) {
               if (this.isRedstoneConductor(â˜ƒ.west())) {
                  â˜ƒx = 0.02;
               } else if (this.isRedstoneConductor(â˜ƒ.east())) {
                  â˜ƒx = -0.02;
               }
            } else {
               if (â˜ƒx != RailShape.NORTH_SOUTH) {
                  return;
               }

               if (this.isRedstoneConductor(â˜ƒ.north())) {
                  â˜ƒxx = 0.02;
               } else if (this.isRedstoneConductor(â˜ƒ.south())) {
                  â˜ƒxx = -0.02;
               }
            }

            this.setDeltaMovement(â˜ƒx, â˜ƒ.y, â˜ƒxx);
         }
      }
   }

   private boolean isRedstoneConductor(BlockPos var1) {
      return this.level.getBlockState(â˜ƒ).isRedstoneConductor(this.level, â˜ƒ);
   }

   protected void applyNaturalSlowdown() {
      double â˜ƒ = this.isVehicle() ? 0.997 : 0.96;
      Vec3 â˜ƒx = this.getDeltaMovement();
      â˜ƒx = â˜ƒx.multiply(â˜ƒ, 0.0, â˜ƒ);
      if (this.isInWater()) {
         â˜ƒx = â˜ƒx.scale(0.95F);
      }

      this.setDeltaMovement(â˜ƒx);
   }

   @Nullable
   public Vec3 getPosOffs(double var1, double var3, double var5, double var7) {
      int â˜ƒ = Mth.floor(â˜ƒ);
      int â˜ƒx = Mth.floor(â˜ƒ);
      int â˜ƒxx = Mth.floor(â˜ƒ);
      if (this.level.getBlockState(new BlockPos(â˜ƒ, â˜ƒx - 1, â˜ƒxx)).is(BlockTags.RAILS)) {
         --â˜ƒx;
      }

      BlockState â˜ƒ = this.level.getBlockState(new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx));
      if (BaseRailBlock.isRail(â˜ƒ)) {
         RailShape â˜ƒx = â˜ƒ.getValue(((BaseRailBlock)â˜ƒ.getBlock()).getShapeProperty());
         â˜ƒ = (double)â˜ƒx;
         if (â˜ƒx.isAscending()) {
            â˜ƒ = (double)(â˜ƒx + 1);
         }

         Pair<Vec3i, Vec3i> â˜ƒx = exits(â˜ƒx);
         Vec3i â˜ƒxx = â˜ƒx.getFirst();
         Vec3i â˜ƒxxx = â˜ƒx.getSecond();
         double â˜ƒxxxx = (double)(â˜ƒxxx.getX() - â˜ƒxx.getX());
         double â˜ƒxxxxx = (double)(â˜ƒxxx.getZ() - â˜ƒxx.getZ());
         double â˜ƒxxxxxx = Math.sqrt(â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx);
         â˜ƒxxxx /= â˜ƒxxxxxx;
         â˜ƒxxxxx /= â˜ƒxxxxxx;
         â˜ƒ += â˜ƒxxxx * â˜ƒ;
         â˜ƒ += â˜ƒxxxxx * â˜ƒ;
         if (â˜ƒxx.getY() != 0 && Mth.floor(â˜ƒ) - â˜ƒ == â˜ƒxx.getX() && Mth.floor(â˜ƒ) - â˜ƒxx == â˜ƒxx.getZ()) {
            â˜ƒ += (double)â˜ƒxx.getY();
         } else if (â˜ƒxxx.getY() != 0 && Mth.floor(â˜ƒ) - â˜ƒ == â˜ƒxxx.getX() && Mth.floor(â˜ƒ) - â˜ƒxx == â˜ƒxxx.getZ()) {
            â˜ƒ += (double)â˜ƒxxx.getY();
         }

         return this.getPos(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return null;
      }
   }

   @Nullable
   public Vec3 getPos(double var1, double var3, double var5) {
      int â˜ƒ = Mth.floor(â˜ƒ);
      int â˜ƒx = Mth.floor(â˜ƒ);
      int â˜ƒxx = Mth.floor(â˜ƒ);
      if (this.level.getBlockState(new BlockPos(â˜ƒ, â˜ƒx - 1, â˜ƒxx)).is(BlockTags.RAILS)) {
         --â˜ƒx;
      }

      BlockState â˜ƒ = this.level.getBlockState(new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx));
      if (BaseRailBlock.isRail(â˜ƒ)) {
         RailShape â˜ƒxx = â˜ƒ.getValue(((BaseRailBlock)â˜ƒ.getBlock()).getShapeProperty());
         Pair<Vec3i, Vec3i> â˜ƒxxx = exits(â˜ƒxx);
         Vec3i â˜ƒxxxx = â˜ƒxxx.getFirst();
         Vec3i â˜ƒxxxxx = â˜ƒxxx.getSecond();
         double â˜ƒxxxxxx = (double)â˜ƒ + 0.5 + (double)â˜ƒxxxx.getX() * 0.5;
         double â˜ƒxxxxxxx = (double)â˜ƒx + 0.0625 + (double)â˜ƒxxxx.getY() * 0.5;
         double â˜ƒxxxxxxxx = (double)â˜ƒxx + 0.5 + (double)â˜ƒxxxx.getZ() * 0.5;
         double â˜ƒxxxxxxxxx = (double)â˜ƒ + 0.5 + (double)â˜ƒxxxxx.getX() * 0.5;
         double â˜ƒxxxxxxxxxx = (double)â˜ƒx + 0.0625 + (double)â˜ƒxxxxx.getY() * 0.5;
         double â˜ƒxxxxxxxxxxx = (double)â˜ƒxx + 0.5 + (double)â˜ƒxxxxx.getZ() * 0.5;
         double â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxx - â˜ƒxxxxxx;
         double â˜ƒxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxx - â˜ƒxxxxxxx) * 2.0;
         double â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx - â˜ƒxxxxxxxx;
         double â˜ƒx;
         if (â˜ƒxxxxxxxxxxxx == 0.0) {
            â˜ƒx = â˜ƒ - (double)â˜ƒxx;
         } else if (â˜ƒxxxxxxxxxxxxxx == 0.0) {
            â˜ƒx = â˜ƒ - (double)â˜ƒ;
         } else {
            double â˜ƒx = â˜ƒ - â˜ƒxxxxxx;
            double â˜ƒxx = â˜ƒ - â˜ƒxxxxxxxx;
            â˜ƒx = (â˜ƒx * â˜ƒxxxxxxxxxxxx + â˜ƒxx * â˜ƒxxxxxxxxxxxxxx) * 2.0;
         }

         â˜ƒ = â˜ƒxxxxxx + â˜ƒxxxxxxxxxxxx * â˜ƒx;
         â˜ƒ = â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxxx * â˜ƒx;
         â˜ƒ = â˜ƒxxxxxxxx + â˜ƒxxxxxxxxxxxxxx * â˜ƒx;
         if (â˜ƒxxxxxxxxxxxxx < 0.0) {
            ++â˜ƒ;
         } else if (â˜ƒxxxxxxxxxxxxx > 0.0) {
            â˜ƒ += 0.5;
         }

         return new Vec3(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return null;
      }
   }

   @Override
   public AABB getBoundingBoxForCulling() {
      AABB â˜ƒ = this.getBoundingBox();
      return this.hasCustomDisplay() ? â˜ƒ.inflate((double)Math.abs(this.getDisplayOffset()) / 16.0) : â˜ƒ;
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      if (â˜ƒ.getBoolean("CustomDisplayTile")) {
         this.setDisplayBlockState(NbtUtils.readBlockState(â˜ƒ.getCompound("DisplayState")));
         this.setDisplayOffset(â˜ƒ.getInt("DisplayOffset"));
      }
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      if (this.hasCustomDisplay()) {
         â˜ƒ.putBoolean("CustomDisplayTile", true);
         â˜ƒ.put("DisplayState", NbtUtils.writeBlockState(this.getDisplayBlockState()));
         â˜ƒ.putInt("DisplayOffset", this.getDisplayOffset());
      }
   }

   @Override
   public void push(Entity var1) {
      if (!this.level.isClientSide) {
         if (!â˜ƒ.noPhysics && !this.noPhysics) {
            if (!this.hasPassenger(â˜ƒ)) {
               double â˜ƒ = â˜ƒ.getX() - this.getX();
               double â˜ƒx = â˜ƒ.getZ() - this.getZ();
               double â˜ƒxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx;
               if (â˜ƒxx >= 1.0E-4F) {
                  â˜ƒxx = Math.sqrt(â˜ƒxx);
                  â˜ƒ /= â˜ƒxx;
                  â˜ƒx /= â˜ƒxx;
                  double â˜ƒxxx = 1.0 / â˜ƒxx;
                  if (â˜ƒxxx > 1.0) {
                     â˜ƒxxx = 1.0;
                  }

                  â˜ƒ *= â˜ƒxxx;
                  â˜ƒx *= â˜ƒxxx;
                  â˜ƒ *= 0.1F;
                  â˜ƒx *= 0.1F;
                  â˜ƒ *= 0.5;
                  â˜ƒx *= 0.5;
                  if (â˜ƒ instanceof AbstractMinecart) {
                     double â˜ƒxxx = â˜ƒ.getX() - this.getX();
                     double â˜ƒxxxx = â˜ƒ.getZ() - this.getZ();
                     Vec3 â˜ƒxxxxx = new Vec3(â˜ƒxxx, 0.0, â˜ƒxxxx).normalize();
                     Vec3 â˜ƒxxxxxx = new Vec3(
                           (double)Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)), 0.0, (double)Mth.sin(this.getYRot() * (float) (Math.PI / 180.0))
                        )
                        .normalize();
                     double â˜ƒxxxxxxx = Math.abs(â˜ƒxxxxx.dot(â˜ƒxxxxxx));
                     if (â˜ƒxxxxxxx < 0.8F) {
                        return;
                     }

                     Vec3 â˜ƒxxx = this.getDeltaMovement();
                     Vec3 â˜ƒxxxx = â˜ƒ.getDeltaMovement();
                     if (((AbstractMinecart)â˜ƒ).getMinecartType() == AbstractMinecart.Type.FURNACE && this.getMinecartType() != AbstractMinecart.Type.FURNACE) {
                        this.setDeltaMovement(â˜ƒxxx.multiply(0.2, 1.0, 0.2));
                        this.push(â˜ƒxxxx.x - â˜ƒ, 0.0, â˜ƒxxxx.z - â˜ƒx);
                        â˜ƒ.setDeltaMovement(â˜ƒxxxx.multiply(0.95, 1.0, 0.95));
                     } else if (((AbstractMinecart)â˜ƒ).getMinecartType() != AbstractMinecart.Type.FURNACE
                        && this.getMinecartType() == AbstractMinecart.Type.FURNACE) {
                        â˜ƒ.setDeltaMovement(â˜ƒxxxx.multiply(0.2, 1.0, 0.2));
                        â˜ƒ.push(â˜ƒxxx.x + â˜ƒ, 0.0, â˜ƒxxx.z + â˜ƒx);
                        this.setDeltaMovement(â˜ƒxxx.multiply(0.95, 1.0, 0.95));
                     } else {
                        double â˜ƒxxx = (â˜ƒxxxx.x + â˜ƒxxx.x) / 2.0;
                        double â˜ƒxxxx = (â˜ƒxxxx.z + â˜ƒxxx.z) / 2.0;
                        this.setDeltaMovement(â˜ƒxxx.multiply(0.2, 1.0, 0.2));
                        this.push(â˜ƒxxx - â˜ƒ, 0.0, â˜ƒxxxx - â˜ƒx);
                        â˜ƒ.setDeltaMovement(â˜ƒxxxx.multiply(0.2, 1.0, 0.2));
                        â˜ƒ.push(â˜ƒxxx + â˜ƒ, 0.0, â˜ƒxxxx + â˜ƒx);
                     }
                  } else {
                     this.push(-â˜ƒ, 0.0, -â˜ƒx);
                     â˜ƒ.push(â˜ƒ / 4.0, 0.0, â˜ƒx / 4.0);
                  }
               }
            }
         }
      }
   }

   @Override
   public void lerpTo(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.lx = â˜ƒ;
      this.ly = â˜ƒ;
      this.lz = â˜ƒ;
      this.lyr = (double)â˜ƒ;
      this.lxr = (double)â˜ƒ;
      this.lSteps = â˜ƒ + 2;
      this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
   }

   @Override
   public void lerpMotion(double var1, double var3, double var5) {
      this.lxd = â˜ƒ;
      this.lyd = â˜ƒ;
      this.lzd = â˜ƒ;
      this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
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

   public void setHurtDir(int var1) {
      this.entityData.set(DATA_ID_HURTDIR, â˜ƒ);
   }

   public int getHurtDir() {
      return this.entityData.get(DATA_ID_HURTDIR);
   }

   public abstract AbstractMinecart.Type getMinecartType();

   public BlockState getDisplayBlockState() {
      return !this.hasCustomDisplay() ? this.getDefaultDisplayBlockState() : Block.stateById(this.getEntityData().get(DATA_ID_DISPLAY_BLOCK));
   }

   public BlockState getDefaultDisplayBlockState() {
      return Blocks.AIR.defaultBlockState();
   }

   public int getDisplayOffset() {
      return !this.hasCustomDisplay() ? this.getDefaultDisplayOffset() : this.getEntityData().get(DATA_ID_DISPLAY_OFFSET);
   }

   public int getDefaultDisplayOffset() {
      return 6;
   }

   public void setDisplayBlockState(BlockState var1) {
      this.getEntityData().set(DATA_ID_DISPLAY_BLOCK, Block.getId(â˜ƒ));
      this.setCustomDisplay(true);
   }

   public void setDisplayOffset(int var1) {
      this.getEntityData().set(DATA_ID_DISPLAY_OFFSET, â˜ƒ);
      this.setCustomDisplay(true);
   }

   public boolean hasCustomDisplay() {
      return this.getEntityData().get(DATA_ID_CUSTOM_DISPLAY);
   }

   public void setCustomDisplay(boolean var1) {
      this.getEntityData().set(DATA_ID_CUSTOM_DISPLAY, â˜ƒ);
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }

   @Override
   public ItemStack getPickResult() {
      return new ItemStack(switch(this.getMinecartType()) {
         case FURNACE -> Items.FURNACE_MINECART;
         case CHEST -> Items.CHEST_MINECART;
         case TNT -> Items.TNT_MINECART;
         case HOPPER -> Items.HOPPER_MINECART;
         case COMMAND_BLOCK -> Items.COMMAND_BLOCK_MINECART;
         default -> Items.MINECART;
      });
   }

   public static enum Type {
      RIDEABLE,
      CHEST,
      FURNACE,
      TNT,
      SPAWNER,
      HOPPER,
      COMMAND_BLOCK;
   }
}
