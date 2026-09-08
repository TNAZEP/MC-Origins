package net.minecraft.world.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.util.RewindableStream;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListenerRegistrar;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Entity implements Nameable, EntityAccess, CommandSource {
   protected static final Logger LOGGER = LogManager.getLogger();
   public static final String ID_TAG = "id";
   public static final String PASSENGERS_TAG = "Passengers";
   private static final AtomicInteger ENTITY_COUNTER = new AtomicInteger();
   private static final List<ItemStack> EMPTY_LIST = Collections.emptyList();
   public static final int BOARDING_COOLDOWN = 60;
   public static final int TOTAL_AIR_SUPPLY = 300;
   public static final int MAX_ENTITY_TAG_COUNT = 1024;
   public static final double DELTA_AFFECTED_BY_BLOCKS_BELOW = 0.5000001;
   public static final float BREATHING_DISTANCE_BELOW_EYES = 0.11111111F;
   public static final int BASE_TICKS_REQUIRED_TO_FREEZE = 140;
   public static final int FREEZE_HURT_FREQUENCY = 40;
   private static final AABB INITIAL_AABB = new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static final double WATER_FLOW_SCALE = 0.014;
   private static final double LAVA_FAST_FLOW_SCALE = 0.007;
   private static final double LAVA_SLOW_FLOW_SCALE = 0.0023333333333333335;
   public static final String UUID_TAG = "UUID";
   private static double viewScale = 1.0;
   private final EntityType<?> type;
   private int id = ENTITY_COUNTER.incrementAndGet();
   public boolean blocksBuilding;
   private ImmutableList<Entity> passengers = ImmutableList.of();
   protected int boardingCooldown;
   @Nullable
   private Entity vehicle;
   public Level level;
   public double xo;
   public double yo;
   public double zo;
   private Vec3 position;
   private BlockPos blockPosition;
   private Vec3 deltaMovement = Vec3.ZERO;
   private float yRot;
   private float xRot;
   public float yRotO;
   public float xRotO;
   private AABB bb = INITIAL_AABB;
   protected boolean onGround;
   public boolean horizontalCollision;
   public boolean verticalCollision;
   public boolean hurtMarked;
   protected Vec3 stuckSpeedMultiplier = Vec3.ZERO;
   @Nullable
   private Entity.RemovalReason removalReason;
   public static final float DEFAULT_BB_WIDTH = 0.6F;
   public static final float DEFAULT_BB_HEIGHT = 1.8F;
   public float walkDistO;
   public float walkDist;
   public float moveDist;
   public float flyDist;
   public float fallDistance;
   private float nextStep = 1.0F;
   public double xOld;
   public double yOld;
   public double zOld;
   public float maxUpStep;
   public boolean noPhysics;
   protected final Random random = new Random();
   public int tickCount;
   private int remainingFireTicks = -this.getFireImmuneTicks();
   protected boolean wasTouchingWater;
   protected Object2DoubleMap<Tag<Fluid>> fluidHeight = new Object2DoubleArrayMap<>(2);
   protected boolean wasEyeInWater;
   @Nullable
   protected Tag<Fluid> fluidOnEyes;
   public int invulnerableTime;
   protected boolean firstTick = true;
   protected final SynchedEntityData entityData;
   protected static final EntityDataAccessor<Byte> DATA_SHARED_FLAGS_ID = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BYTE);
   protected static final int FLAG_ONFIRE = 0;
   private static final int FLAG_SHIFT_KEY_DOWN = 1;
   private static final int FLAG_SPRINTING = 3;
   private static final int FLAG_SWIMMING = 4;
   private static final int FLAG_INVISIBLE = 5;
   protected static final int FLAG_GLOWING = 6;
   protected static final int FLAG_FALL_FLYING = 7;
   private static final EntityDataAccessor<Integer> DATA_AIR_SUPPLY_ID = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Optional<Component>> DATA_CUSTOM_NAME = SynchedEntityData.defineId(
      Entity.class, EntityDataSerializers.OPTIONAL_COMPONENT
   );
   private static final EntityDataAccessor<Boolean> DATA_CUSTOM_NAME_VISIBLE = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_SILENT = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_NO_GRAVITY = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Pose> DATA_POSE = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.POSE);
   private static final EntityDataAccessor<Integer> DATA_TICKS_FROZEN = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);
   private EntityInLevelCallback levelCallback = EntityInLevelCallback.NULL;
   private Vec3 packetCoordinates;
   public boolean noCulling;
   public boolean hasImpulse;
   private int portalCooldown;
   protected boolean isInsidePortal;
   protected int portalTime;
   protected BlockPos portalEntrancePos;
   private boolean invulnerable;
   protected UUID uuid = Mth.createInsecureUUID(this.random);
   protected String stringUUID = this.uuid.toString();
   private boolean hasGlowingTag;
   private final Set<String> tags = Sets.newHashSet();
   private final double[] pistonDeltas = new double[]{0.0, 0.0, 0.0};
   private long pistonDeltasGameTime;
   private EntityDimensions dimensions;
   private float eyeHeight;
   public boolean isInPowderSnow;
   public boolean wasInPowderSnow;
   public boolean wasOnFire;
   private float crystalSoundIntensity;
   private int lastCrystalSoundPlayTick;
   private boolean hasVisualFire;

   public Entity(EntityType<?> var1, Level var2) {
      this.type = â˜ƒ;
      this.level = â˜ƒ;
      this.dimensions = â˜ƒ.getDimensions();
      this.position = Vec3.ZERO;
      this.blockPosition = BlockPos.ZERO;
      this.packetCoordinates = Vec3.ZERO;
      this.entityData = new SynchedEntityData(this);
      this.entityData.define(DATA_SHARED_FLAGS_ID, (byte)0);
      this.entityData.define(DATA_AIR_SUPPLY_ID, this.getMaxAirSupply());
      this.entityData.define(DATA_CUSTOM_NAME_VISIBLE, false);
      this.entityData.define(DATA_CUSTOM_NAME, Optional.empty());
      this.entityData.define(DATA_SILENT, false);
      this.entityData.define(DATA_NO_GRAVITY, false);
      this.entityData.define(DATA_POSE, Pose.STANDING);
      this.entityData.define(DATA_TICKS_FROZEN, 0);
      this.defineSynchedData();
      this.setPos(0.0, 0.0, 0.0);
      this.eyeHeight = this.getEyeHeight(Pose.STANDING, this.dimensions);
   }

   public boolean isColliding(BlockPos var1, BlockState var2) {
      VoxelShape â˜ƒ = â˜ƒ.getCollisionShape(this.level, â˜ƒ, CollisionContext.of(this));
      VoxelShape â˜ƒx = â˜ƒ.move((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
      return Shapes.joinIsNotEmpty(â˜ƒx, Shapes.create(this.getBoundingBox()), BooleanOp.AND);
   }

   public int getTeamColor() {
      Team â˜ƒ = this.getTeam();
      return â˜ƒ != null && â˜ƒ.getColor().getColor() != null ? â˜ƒ.getColor().getColor() : 16777215;
   }

   public boolean isSpectator() {
      return false;
   }

   public final void unRide() {
      if (this.isVehicle()) {
         this.ejectPassengers();
      }

      if (this.isPassenger()) {
         this.stopRiding();
      }
   }

   public void setPacketCoordinates(double var1, double var3, double var5) {
      this.setPacketCoordinates(new Vec3(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void setPacketCoordinates(Vec3 var1) {
      this.packetCoordinates = â˜ƒ;
   }

   public Vec3 getPacketCoordinates() {
      return this.packetCoordinates;
   }

   public EntityType<?> getType() {
      return this.type;
   }

   @Override
   public int getId() {
      return this.id;
   }

   public void setId(int var1) {
      this.id = â˜ƒ;
   }

   public Set<String> getTags() {
      return this.tags;
   }

   public boolean addTag(String var1) {
      return this.tags.size() >= 1024 ? false : this.tags.add(â˜ƒ);
   }

   public boolean removeTag(String var1) {
      return this.tags.remove(â˜ƒ);
   }

   public void kill() {
      this.remove(Entity.RemovalReason.KILLED);
   }

   public final void discard() {
      this.remove(Entity.RemovalReason.DISCARDED);
   }

   protected abstract void defineSynchedData();

   public SynchedEntityData getEntityData() {
      return this.entityData;
   }

   public boolean equals(Object var1) {
      if (â˜ƒ instanceof Entity) {
         return ((Entity)â˜ƒ).id == this.id;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.id;
   }

   public void remove(Entity.RemovalReason var1) {
      this.setRemoved(â˜ƒ);
      if (â˜ƒ == Entity.RemovalReason.KILLED) {
         this.gameEvent(GameEvent.ENTITY_KILLED);
      }
   }

   public void onClientRemoval() {
   }

   public void setPose(Pose var1) {
      this.entityData.set(DATA_POSE, â˜ƒ);
   }

   public Pose getPose() {
      return this.entityData.get(DATA_POSE);
   }

   public boolean closerThan(Entity var1, double var2) {
      double â˜ƒ = â˜ƒ.position.x - this.position.x;
      double â˜ƒx = â˜ƒ.position.y - this.position.y;
      double â˜ƒxx = â˜ƒ.position.z - this.position.z;
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx < â˜ƒ * â˜ƒ;
   }

   protected void setRot(float var1, float var2) {
      this.setYRot(â˜ƒ % 360.0F);
      this.setXRot(â˜ƒ % 360.0F);
   }

   public final void setPos(Vec3 var1) {
      this.setPos(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
   }

   public void setPos(double var1, double var3, double var5) {
      this.setPosRaw(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setBoundingBox(this.makeBoundingBox());
   }

   protected AABB makeBoundingBox() {
      return this.dimensions.makeBoundingBox(this.position);
   }

   protected void reapplyPosition() {
      this.setPos(this.position.x, this.position.y, this.position.z);
   }

   public void turn(double var1, double var3) {
      float â˜ƒ = (float)â˜ƒ * 0.15F;
      float â˜ƒx = (float)â˜ƒ * 0.15F;
      this.setXRot(this.getXRot() + â˜ƒ);
      this.setYRot(this.getYRot() + â˜ƒx);
      this.setXRot(Mth.clamp(this.getXRot(), -90.0F, 90.0F));
      this.xRotO += â˜ƒ;
      this.yRotO += â˜ƒx;
      this.xRotO = Mth.clamp(this.xRotO, -90.0F, 90.0F);
      if (this.vehicle != null) {
         this.vehicle.onPassengerTurned(this);
      }
   }

   public void tick() {
      this.baseTick();
   }

   public void baseTick() {
      this.level.getProfiler().push("entityBaseTick");
      if (this.isPassenger() && this.getVehicle().isRemoved()) {
         this.stopRiding();
      }

      if (this.boardingCooldown > 0) {
         --this.boardingCooldown;
      }

      this.walkDistO = this.walkDist;
      this.xRotO = this.getXRot();
      this.yRotO = this.getYRot();
      this.handleNetherPortal();
      if (this.canSpawnSprintParticle()) {
         this.spawnSprintParticle();
      }

      this.wasInPowderSnow = this.isInPowderSnow;
      this.isInPowderSnow = false;
      this.updateInWaterStateAndDoFluidPushing();
      this.updateFluidOnEyes();
      this.updateSwimming();
      if (this.level.isClientSide) {
         this.clearFire();
      } else if (this.remainingFireTicks > 0) {
         if (this.fireImmune()) {
            this.setRemainingFireTicks(this.remainingFireTicks - 4);
            if (this.remainingFireTicks < 0) {
               this.clearFire();
            }
         } else {
            if (this.remainingFireTicks % 20 == 0 && !this.isInLava()) {
               this.hurt(DamageSource.ON_FIRE, 1.0F);
            }

            this.setRemainingFireTicks(this.remainingFireTicks - 1);
         }

         if (this.getTicksFrozen() > 0) {
            this.setTicksFrozen(0);
            this.level.levelEvent(null, 1009, this.blockPosition, 1);
         }
      }

      if (this.isInLava()) {
         this.lavaHurt();
         this.fallDistance *= 0.5F;
      }

      this.checkOutOfWorld();
      if (!this.level.isClientSide) {
         this.setSharedFlagOnFire(this.remainingFireTicks > 0);
      }

      this.firstTick = false;
      this.level.getProfiler().pop();
   }

   public void setSharedFlagOnFire(boolean var1) {
      this.setSharedFlag(0, â˜ƒ || this.hasVisualFire);
   }

   public void checkOutOfWorld() {
      if (this.getY() < (double)(this.level.getMinBuildHeight() - 64)) {
         this.outOfWorld();
      }
   }

   public void setPortalCooldown() {
      this.portalCooldown = this.getDimensionChangingDelay();
   }

   public boolean isOnPortalCooldown() {
      return this.portalCooldown > 0;
   }

   protected void processPortalCooldown() {
      if (this.isOnPortalCooldown()) {
         --this.portalCooldown;
      }
   }

   public int getPortalWaitTime() {
      return 0;
   }

   public void lavaHurt() {
      if (!this.fireImmune()) {
         this.setSecondsOnFire(15);
         if (this.hurt(DamageSource.LAVA, 4.0F)) {
            this.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
         }
      }
   }

   public void setSecondsOnFire(int var1) {
      int â˜ƒ = â˜ƒ * 20;
      if (this instanceof LivingEntity) {
         â˜ƒ = ProtectionEnchantment.getFireAfterDampener((LivingEntity)this, â˜ƒ);
      }

      if (this.remainingFireTicks < â˜ƒ) {
         this.setRemainingFireTicks(â˜ƒ);
      }
   }

   public void setRemainingFireTicks(int var1) {
      this.remainingFireTicks = â˜ƒ;
   }

   public int getRemainingFireTicks() {
      return this.remainingFireTicks;
   }

   public void clearFire() {
      this.setRemainingFireTicks(0);
   }

   protected void outOfWorld() {
      this.discard();
   }

   public boolean isFree(double var1, double var3, double var5) {
      return this.isFree(this.getBoundingBox().move(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   private boolean isFree(AABB var1) {
      return this.level.noCollision(this, â˜ƒ) && !this.level.containsAnyLiquid(â˜ƒ);
   }

   public void setOnGround(boolean var1) {
      this.onGround = â˜ƒ;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public void move(MoverType var1, Vec3 var2) {
      if (this.noPhysics) {
         this.setPos(this.getX() + â˜ƒ.x, this.getY() + â˜ƒ.y, this.getZ() + â˜ƒ.z);
      } else {
         this.wasOnFire = this.isOnFire();
         if (â˜ƒ == MoverType.PISTON) {
            â˜ƒ = this.limitPistonMovement(â˜ƒ);
            if (â˜ƒ.equals(Vec3.ZERO)) {
               return;
            }
         }

         this.level.getProfiler().push("move");
         if (this.stuckSpeedMultiplier.lengthSqr() > 1.0E-7) {
            â˜ƒ = â˜ƒ.multiply(this.stuckSpeedMultiplier);
            this.stuckSpeedMultiplier = Vec3.ZERO;
            this.setDeltaMovement(Vec3.ZERO);
         }

         â˜ƒ = this.maybeBackOffFromEdge(â˜ƒ, â˜ƒ);
         Vec3 â˜ƒ = this.collide(â˜ƒ);
         if (â˜ƒ.lengthSqr() > 1.0E-7) {
            this.setPos(this.getX() + â˜ƒ.x, this.getY() + â˜ƒ.y, this.getZ() + â˜ƒ.z);
         }

         this.level.getProfiler().pop();
         this.level.getProfiler().push("rest");
         this.horizontalCollision = !Mth.equal(â˜ƒ.x, â˜ƒ.x) || !Mth.equal(â˜ƒ.z, â˜ƒ.z);
         this.verticalCollision = â˜ƒ.y != â˜ƒ.y;
         this.onGround = this.verticalCollision && â˜ƒ.y < 0.0;
         BlockPos â˜ƒ = this.getOnPos();
         BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
         this.checkFallDamage(â˜ƒ.y, this.onGround, â˜ƒx, â˜ƒ);
         if (this.isRemoved()) {
            this.level.getProfiler().pop();
         } else {
            Vec3 â˜ƒ = this.getDeltaMovement();
            if (â˜ƒ.x != â˜ƒ.x) {
               this.setDeltaMovement(0.0, â˜ƒ.y, â˜ƒ.z);
            }

            if (â˜ƒ.z != â˜ƒ.z) {
               this.setDeltaMovement(â˜ƒ.x, â˜ƒ.y, 0.0);
            }

            Block â˜ƒ = â˜ƒx.getBlock();
            if (â˜ƒ.y != â˜ƒ.y) {
               â˜ƒ.updateEntityAfterFallOn(this.level, this);
            }

            if (this.onGround && !this.isSteppingCarefully()) {
               â˜ƒ.stepOn(this.level, â˜ƒ, â˜ƒx, this);
            }

            Entity.MovementEmission â˜ƒ = this.getMovementEmission();
            if (â˜ƒ.emitsAnything() && !this.isPassenger()) {
               double â˜ƒx = â˜ƒ.x;
               double â˜ƒxx = â˜ƒ.y;
               double â˜ƒxxx = â˜ƒ.z;
               this.flyDist = (float)((double)this.flyDist + â˜ƒ.length() * 0.6);
               if (!â˜ƒx.is(BlockTags.CLIMBABLE) && !â˜ƒx.is(Blocks.POWDER_SNOW)) {
                  â˜ƒxx = 0.0;
               }

               this.walkDist += (float)â˜ƒ.horizontalDistance() * 0.6F;
               this.moveDist += (float)Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx) * 0.6F;
               if (this.moveDist > this.nextStep && !â˜ƒx.isAir()) {
                  this.nextStep = this.nextStep();
                  if (this.isInWater()) {
                     if (â˜ƒ.emitsSounds()) {
                        Entity â˜ƒx = this.isVehicle() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
                        float â˜ƒxx = â˜ƒx == this ? 0.35F : 0.4F;
                        Vec3 â˜ƒxxx = â˜ƒx.getDeltaMovement();
                        float â˜ƒxxxx = Math.min(1.0F, (float)Math.sqrt(â˜ƒxxx.x * â˜ƒxxx.x * 0.2F + â˜ƒxxx.y * â˜ƒxxx.y + â˜ƒxxx.z * â˜ƒxxx.z * 0.2F) * â˜ƒxx);
                        this.playSwimSound(â˜ƒxxxx);
                     }

                     if (â˜ƒ.emitsEvents()) {
                        this.gameEvent(GameEvent.SWIM);
                     }
                  } else {
                     if (â˜ƒ.emitsSounds()) {
                        this.playAmethystStepSound(â˜ƒx);
                        this.playStepSound(â˜ƒ, â˜ƒx);
                     }

                     if (â˜ƒ.emitsEvents() && !â˜ƒx.is(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) {
                        this.gameEvent(GameEvent.STEP);
                     }
                  }
               } else if (â˜ƒx.isAir()) {
                  this.processFlappingMovement();
               }
            }

            this.tryCheckInsideBlocks();
            float â˜ƒ = this.getBlockSpeedFactor();
            this.setDeltaMovement(this.getDeltaMovement().multiply((double)â˜ƒ, 1.0, (double)â˜ƒ));
            if (this.level.getBlockStatesIfLoaded(this.getBoundingBox().deflate(1.0E-6)).noneMatch(var0 -> var0.is(BlockTags.FIRE) || var0.is(Blocks.LAVA))) {
               if (this.remainingFireTicks <= 0) {
                  this.setRemainingFireTicks(-this.getFireImmuneTicks());
               }

               if (this.wasOnFire && (this.isInPowderSnow || this.isInWaterRainOrBubble())) {
                  this.playEntityOnFireExtinguishedSound();
               }
            }

            if (this.isOnFire() && (this.isInPowderSnow || this.isInWaterRainOrBubble())) {
               this.setRemainingFireTicks(-this.getFireImmuneTicks());
            }

            this.level.getProfiler().pop();
         }
      }
   }

   protected void tryCheckInsideBlocks() {
      try {
         this.checkInsideBlocks();
      } catch (Throwable var4) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var4, "Checking entity block collision");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Entity being checked for collision");
         this.fillCrashReportCategory(â˜ƒx);
         throw new ReportedException(â˜ƒ);
      }
   }

   protected void playEntityOnFireExtinguishedSound() {
      this.playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
   }

   protected void processFlappingMovement() {
      if (this.isFlapping()) {
         this.onFlap();
         if (this.getMovementEmission().emitsEvents()) {
            this.gameEvent(GameEvent.FLAP);
         }
      }
   }

   public BlockPos getOnPos() {
      int â˜ƒ = Mth.floor(this.position.x);
      int â˜ƒx = Mth.floor(this.position.y - 0.2F);
      int â˜ƒxx = Mth.floor(this.position.z);
      BlockPos â˜ƒxxx = new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
      if (this.level.getBlockState(â˜ƒxxx).isAir()) {
         BlockPos â˜ƒxxxx = â˜ƒxxx.below();
         BlockState â˜ƒxxxxx = this.level.getBlockState(â˜ƒxxxx);
         if (â˜ƒxxxxx.is(BlockTags.FENCES) || â˜ƒxxxxx.is(BlockTags.WALLS) || â˜ƒxxxxx.getBlock() instanceof FenceGateBlock) {
            return â˜ƒxxxx;
         }
      }

      return â˜ƒxxx;
   }

   protected float getBlockJumpFactor() {
      float â˜ƒ = this.level.getBlockState(this.blockPosition()).getBlock().getJumpFactor();
      float â˜ƒx = this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getJumpFactor();
      return (double)â˜ƒ == 1.0 ? â˜ƒx : â˜ƒ;
   }

   protected float getBlockSpeedFactor() {
      BlockState â˜ƒ = this.level.getBlockState(this.blockPosition());
      float â˜ƒx = â˜ƒ.getBlock().getSpeedFactor();
      if (!â˜ƒ.is(Blocks.WATER) && !â˜ƒ.is(Blocks.BUBBLE_COLUMN)) {
         return (double)â˜ƒx == 1.0 ? this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getSpeedFactor() : â˜ƒx;
      } else {
         return â˜ƒx;
      }
   }

   protected BlockPos getBlockPosBelowThatAffectsMyMovement() {
      return new BlockPos(this.position.x, this.getBoundingBox().minY - 0.5000001, this.position.z);
   }

   protected Vec3 maybeBackOffFromEdge(Vec3 var1, MoverType var2) {
      return â˜ƒ;
   }

   protected Vec3 limitPistonMovement(Vec3 var1) {
      if (â˜ƒ.lengthSqr() <= 1.0E-7) {
         return â˜ƒ;
      } else {
         long â˜ƒ = this.level.getGameTime();
         if (â˜ƒ != this.pistonDeltasGameTime) {
            Arrays.fill(this.pistonDeltas, 0.0);
            this.pistonDeltasGameTime = â˜ƒ;
         }

         if (â˜ƒ.x != 0.0) {
            double â˜ƒ = this.applyPistonMovementRestriction(Direction.Axis.X, â˜ƒ.x);
            return Math.abs(â˜ƒ) <= 1.0E-5F ? Vec3.ZERO : new Vec3(â˜ƒ, 0.0, 0.0);
         } else if (â˜ƒ.y != 0.0) {
            double â˜ƒ = this.applyPistonMovementRestriction(Direction.Axis.Y, â˜ƒ.y);
            return Math.abs(â˜ƒ) <= 1.0E-5F ? Vec3.ZERO : new Vec3(0.0, â˜ƒ, 0.0);
         } else if (â˜ƒ.z != 0.0) {
            double â˜ƒ = this.applyPistonMovementRestriction(Direction.Axis.Z, â˜ƒ.z);
            return Math.abs(â˜ƒ) <= 1.0E-5F ? Vec3.ZERO : new Vec3(0.0, 0.0, â˜ƒ);
         } else {
            return Vec3.ZERO;
         }
      }
   }

   private double applyPistonMovementRestriction(Direction.Axis var1, double var2) {
      int â˜ƒ = â˜ƒ.ordinal();
      double â˜ƒx = Mth.clamp(â˜ƒ + this.pistonDeltas[â˜ƒ], -0.51, 0.51);
      â˜ƒ = â˜ƒx - this.pistonDeltas[â˜ƒ];
      this.pistonDeltas[â˜ƒ] = â˜ƒx;
      return â˜ƒ;
   }

   private Vec3 collide(Vec3 var1) {
      AABB â˜ƒ = this.getBoundingBox();
      CollisionContext â˜ƒx = CollisionContext.of(this);
      VoxelShape â˜ƒxx = this.level.getWorldBorder().getCollisionShape();
      Stream<VoxelShape> â˜ƒxxx = Shapes.joinIsNotEmpty(â˜ƒxx, Shapes.create(â˜ƒ.deflate(1.0E-7)), BooleanOp.AND) ? Stream.empty() : Stream.of(â˜ƒxx);
      Stream<VoxelShape> â˜ƒxxxx = this.level.getEntityCollisions(this, â˜ƒ.expandTowards(â˜ƒ), var0 -> true);
      RewindableStream<VoxelShape> â˜ƒxxxxx = new RewindableStream<>(Stream.concat(â˜ƒxxxx, â˜ƒxxx));
      Vec3 â˜ƒxxxxxx = â˜ƒ.lengthSqr() == 0.0 ? â˜ƒ : collideBoundingBoxHeuristically(this, â˜ƒ, â˜ƒ, this.level, â˜ƒx, â˜ƒxxxxx);
      boolean â˜ƒxxxxxxx = â˜ƒ.x != â˜ƒxxxxxx.x;
      boolean â˜ƒxxxxxxxx = â˜ƒ.y != â˜ƒxxxxxx.y;
      boolean â˜ƒxxxxxxxxx = â˜ƒ.z != â˜ƒxxxxxx.z;
      boolean â˜ƒxxxxxxxxxx = this.onGround || â˜ƒxxxxxxxx && â˜ƒ.y < 0.0;
      if (this.maxUpStep > 0.0F && â˜ƒxxxxxxxxxx && (â˜ƒxxxxxxx || â˜ƒxxxxxxxxx)) {
         Vec3 â˜ƒxxxxxxxxxxx = collideBoundingBoxHeuristically(this, new Vec3(â˜ƒ.x, (double)this.maxUpStep, â˜ƒ.z), â˜ƒ, this.level, â˜ƒx, â˜ƒxxxxx);
         Vec3 â˜ƒxxxxxxxxxxxx = collideBoundingBoxHeuristically(
            this, new Vec3(0.0, (double)this.maxUpStep, 0.0), â˜ƒ.expandTowards(â˜ƒ.x, 0.0, â˜ƒ.z), this.level, â˜ƒx, â˜ƒxxxxx
         );
         if (â˜ƒxxxxxxxxxxxx.y < (double)this.maxUpStep) {
            Vec3 â˜ƒxxxxxxxxxxxxx = collideBoundingBoxHeuristically(this, new Vec3(â˜ƒ.x, 0.0, â˜ƒ.z), â˜ƒ.move(â˜ƒxxxxxxxxxxxx), this.level, â˜ƒx, â˜ƒxxxxx)
               .add(â˜ƒxxxxxxxxxxxx);
            if (â˜ƒxxxxxxxxxxxxx.horizontalDistanceSqr() > â˜ƒxxxxxxxxxxx.horizontalDistanceSqr()) {
               â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx;
            }
         }

         if (â˜ƒxxxxxxxxxxx.horizontalDistanceSqr() > â˜ƒxxxxxx.horizontalDistanceSqr()) {
            return â˜ƒxxxxxxxxxxx.add(
               collideBoundingBoxHeuristically(this, new Vec3(0.0, -â˜ƒxxxxxxxxxxx.y + â˜ƒ.y, 0.0), â˜ƒ.move(â˜ƒxxxxxxxxxxx), this.level, â˜ƒx, â˜ƒxxxxx)
            );
         }
      }

      return â˜ƒxxxxxx;
   }

   public static Vec3 collideBoundingBoxHeuristically(
      @Nullable Entity var0, Vec3 var1, AABB var2, Level var3, CollisionContext var4, RewindableStream<VoxelShape> var5
   ) {
      boolean â˜ƒ = â˜ƒ.x == 0.0;
      boolean â˜ƒx = â˜ƒ.y == 0.0;
      boolean â˜ƒxx = â˜ƒ.z == 0.0;
      if ((!â˜ƒ || !â˜ƒx) && (!â˜ƒ || !â˜ƒxx) && (!â˜ƒx || !â˜ƒxx)) {
         RewindableStream<VoxelShape> â˜ƒxxx = new RewindableStream<>(Stream.concat(â˜ƒ.getStream(), â˜ƒ.getBlockCollisions(â˜ƒ, â˜ƒ.expandTowards(â˜ƒ))));
         return collideBoundingBoxLegacy(â˜ƒ, â˜ƒ, â˜ƒxxx);
      } else {
         return collideBoundingBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static Vec3 collideBoundingBoxLegacy(Vec3 var0, AABB var1, RewindableStream<VoxelShape> var2) {
      double â˜ƒ = â˜ƒ.x;
      double â˜ƒx = â˜ƒ.y;
      double â˜ƒxx = â˜ƒ.z;
      if (â˜ƒx != 0.0) {
         â˜ƒx = Shapes.collide(Direction.Axis.Y, â˜ƒ, â˜ƒ.getStream(), â˜ƒx);
         if (â˜ƒx != 0.0) {
            â˜ƒ = â˜ƒ.move(0.0, â˜ƒx, 0.0);
         }
      }

      boolean â˜ƒ = Math.abs(â˜ƒ) < Math.abs(â˜ƒxx);
      if (â˜ƒ && â˜ƒxx != 0.0) {
         â˜ƒxx = Shapes.collide(Direction.Axis.Z, â˜ƒ, â˜ƒ.getStream(), â˜ƒxx);
         if (â˜ƒxx != 0.0) {
            â˜ƒ = â˜ƒ.move(0.0, 0.0, â˜ƒxx);
         }
      }

      if (â˜ƒ != 0.0) {
         â˜ƒ = Shapes.collide(Direction.Axis.X, â˜ƒ, â˜ƒ.getStream(), â˜ƒ);
         if (!â˜ƒ && â˜ƒ != 0.0) {
            â˜ƒ = â˜ƒ.move(â˜ƒ, 0.0, 0.0);
         }
      }

      if (!â˜ƒ && â˜ƒxx != 0.0) {
         â˜ƒxx = Shapes.collide(Direction.Axis.Z, â˜ƒ, â˜ƒ.getStream(), â˜ƒxx);
      }

      return new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public static Vec3 collideBoundingBox(Vec3 var0, AABB var1, LevelReader var2, CollisionContext var3, RewindableStream<VoxelShape> var4) {
      double â˜ƒ = â˜ƒ.x;
      double â˜ƒx = â˜ƒ.y;
      double â˜ƒxx = â˜ƒ.z;
      if (â˜ƒx != 0.0) {
         â˜ƒx = Shapes.collide(Direction.Axis.Y, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ.getStream());
         if (â˜ƒx != 0.0) {
            â˜ƒ = â˜ƒ.move(0.0, â˜ƒx, 0.0);
         }
      }

      boolean â˜ƒ = Math.abs(â˜ƒ) < Math.abs(â˜ƒxx);
      if (â˜ƒ && â˜ƒxx != 0.0) {
         â˜ƒxx = Shapes.collide(Direction.Axis.Z, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ.getStream());
         if (â˜ƒxx != 0.0) {
            â˜ƒ = â˜ƒ.move(0.0, 0.0, â˜ƒxx);
         }
      }

      if (â˜ƒ != 0.0) {
         â˜ƒ = Shapes.collide(Direction.Axis.X, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getStream());
         if (!â˜ƒ && â˜ƒ != 0.0) {
            â˜ƒ = â˜ƒ.move(â˜ƒ, 0.0, 0.0);
         }
      }

      if (!â˜ƒ && â˜ƒxx != 0.0) {
         â˜ƒxx = Shapes.collide(Direction.Axis.Z, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ.getStream());
      }

      return new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   protected float nextStep() {
      return (float)((int)this.moveDist + 1);
   }

   protected SoundEvent getSwimSound() {
      return SoundEvents.GENERIC_SWIM;
   }

   protected SoundEvent getSwimSplashSound() {
      return SoundEvents.GENERIC_SPLASH;
   }

   protected SoundEvent getSwimHighSpeedSplashSound() {
      return SoundEvents.GENERIC_SPLASH;
   }

   protected void checkInsideBlocks() {
      AABB â˜ƒ = this.getBoundingBox();
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.minX + 0.001, â˜ƒ.minY + 0.001, â˜ƒ.minZ + 0.001);
      BlockPos â˜ƒxx = new BlockPos(â˜ƒ.maxX - 0.001, â˜ƒ.maxY - 0.001, â˜ƒ.maxZ - 0.001);
      if (this.level.hasChunksAt(â˜ƒx, â˜ƒxx)) {
         BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxxx = â˜ƒx.getX(); â˜ƒxxxx <= â˜ƒxx.getX(); ++â˜ƒxxxx) {
            for(int â˜ƒxxxxx = â˜ƒx.getY(); â˜ƒxxxxx <= â˜ƒxx.getY(); ++â˜ƒxxxxx) {
               for(int â˜ƒxxxxxx = â˜ƒx.getZ(); â˜ƒxxxxxx <= â˜ƒxx.getZ(); ++â˜ƒxxxxxx) {
                  â˜ƒxxx.set(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
                  BlockState â˜ƒxxxxxxx = this.level.getBlockState(â˜ƒxxx);

                  try {
                     â˜ƒxxxxxxx.entityInside(this.level, â˜ƒxxx, this);
                     this.onInsideBlock(â˜ƒxxxxxxx);
                  } catch (Throwable var12) {
                     CrashReport â˜ƒxxxxxxxx = CrashReport.forThrowable(var12, "Colliding entity with block");
                     CrashReportCategory â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx.addCategory("Block being collided with");
                     CrashReportCategory.populateBlockDetails(â˜ƒxxxxxxxxx, this.level, â˜ƒxxx, â˜ƒxxxxxxx);
                     throw new ReportedException(â˜ƒxxxxxxxx);
                  }
               }
            }
         }
      }
   }

   protected void onInsideBlock(BlockState var1) {
   }

   public void gameEvent(GameEvent var1, @Nullable Entity var2, BlockPos var3) {
      this.level.gameEvent(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void gameEvent(GameEvent var1, @Nullable Entity var2) {
      this.gameEvent(â˜ƒ, â˜ƒ, this.blockPosition);
   }

   public void gameEvent(GameEvent var1, BlockPos var2) {
      this.gameEvent(â˜ƒ, this, â˜ƒ);
   }

   public void gameEvent(GameEvent var1) {
      this.gameEvent(â˜ƒ, this.blockPosition);
   }

   protected void playStepSound(BlockPos var1, BlockState var2) {
      if (!â˜ƒ.getMaterial().isLiquid()) {
         BlockState â˜ƒ = this.level.getBlockState(â˜ƒ.above());
         SoundType â˜ƒx = â˜ƒ.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? â˜ƒ.getSoundType() : â˜ƒ.getSoundType();
         this.playSound(â˜ƒx.getStepSound(), â˜ƒx.getVolume() * 0.15F, â˜ƒx.getPitch());
      }
   }

   private void playAmethystStepSound(BlockState var1) {
      if (â˜ƒ.is(BlockTags.CRYSTAL_SOUND_BLOCKS) && this.tickCount >= this.lastCrystalSoundPlayTick + 20) {
         this.crystalSoundIntensity = (float)((double)this.crystalSoundIntensity * Math.pow(0.997F, (double)(this.tickCount - this.lastCrystalSoundPlayTick)));
         this.crystalSoundIntensity = Math.min(1.0F, this.crystalSoundIntensity + 0.07F);
         float â˜ƒ = 0.5F + this.crystalSoundIntensity * this.random.nextFloat() * 1.2F;
         float â˜ƒx = 0.1F + this.crystalSoundIntensity * 1.2F;
         this.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, â˜ƒx, â˜ƒ);
         this.lastCrystalSoundPlayTick = this.tickCount;
      }
   }

   protected void playSwimSound(float var1) {
      this.playSound(this.getSwimSound(), â˜ƒ, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
   }

   protected void onFlap() {
   }

   protected boolean isFlapping() {
      return false;
   }

   public void playSound(SoundEvent var1, float var2, float var3) {
      if (!this.isSilent()) {
         this.level.playSound(null, this.getX(), this.getY(), this.getZ(), â˜ƒ, this.getSoundSource(), â˜ƒ, â˜ƒ);
      }
   }

   public boolean isSilent() {
      return this.entityData.get(DATA_SILENT);
   }

   public void setSilent(boolean var1) {
      this.entityData.set(DATA_SILENT, â˜ƒ);
   }

   public boolean isNoGravity() {
      return this.entityData.get(DATA_NO_GRAVITY);
   }

   public void setNoGravity(boolean var1) {
      this.entityData.set(DATA_NO_GRAVITY, â˜ƒ);
   }

   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.ALL;
   }

   public boolean occludesVibrations() {
      return false;
   }

   protected void checkFallDamage(double var1, boolean var3, BlockState var4, BlockPos var5) {
      if (â˜ƒ) {
         if (this.fallDistance > 0.0F) {
            â˜ƒ.getBlock().fallOn(this.level, â˜ƒ, â˜ƒ, this, this.fallDistance);
            if (!â˜ƒ.is(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) {
               this.gameEvent(GameEvent.HIT_GROUND);
            }
         }

         this.fallDistance = 0.0F;
      } else if (â˜ƒ < 0.0) {
         this.fallDistance = (float)((double)this.fallDistance - â˜ƒ);
      }
   }

   public boolean fireImmune() {
      return this.getType().fireImmune();
   }

   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      if (this.isVehicle()) {
         for(Entity â˜ƒ : this.getPassengers()) {
            â˜ƒ.causeFallDamage(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      return false;
   }

   public boolean isInWater() {
      return this.wasTouchingWater;
   }

   private boolean isInRain() {
      BlockPos â˜ƒ = this.blockPosition();
      return this.level.isRainingAt(â˜ƒ) || this.level.isRainingAt(new BlockPos((double)â˜ƒ.getX(), this.getBoundingBox().maxY, (double)â˜ƒ.getZ()));
   }

   private boolean isInBubbleColumn() {
      return this.level.getBlockState(this.blockPosition()).is(Blocks.BUBBLE_COLUMN);
   }

   public boolean isInWaterOrRain() {
      return this.isInWater() || this.isInRain();
   }

   public boolean isInWaterRainOrBubble() {
      return this.isInWater() || this.isInRain() || this.isInBubbleColumn();
   }

   public boolean isInWaterOrBubble() {
      return this.isInWater() || this.isInBubbleColumn();
   }

   public boolean isUnderWater() {
      return this.wasEyeInWater && this.isInWater();
   }

   public void updateSwimming() {
      if (this.isSwimming()) {
         this.setSwimming(this.isSprinting() && this.isInWater() && !this.isPassenger());
      } else {
         this.setSwimming(this.isSprinting() && this.isUnderWater() && !this.isPassenger() && this.level.getFluidState(this.blockPosition).is(FluidTags.WATER));
      }
   }

   protected boolean updateInWaterStateAndDoFluidPushing() {
      this.fluidHeight.clear();
      this.updateInWaterStateAndDoWaterCurrentPushing();
      double â˜ƒ = this.level.dimensionType().ultraWarm() ? 0.007 : 0.0023333333333333335;
      boolean â˜ƒx = this.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, â˜ƒ);
      return this.isInWater() || â˜ƒx;
   }

   void updateInWaterStateAndDoWaterCurrentPushing() {
      if (this.getVehicle() instanceof Boat) {
         this.wasTouchingWater = false;
      } else if (this.updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014)) {
         if (!this.wasTouchingWater && !this.firstTick) {
            this.doWaterSplashEffect();
         }

         this.fallDistance = 0.0F;
         this.wasTouchingWater = true;
         this.clearFire();
      } else {
         this.wasTouchingWater = false;
      }
   }

   private void updateFluidOnEyes() {
      this.wasEyeInWater = this.isEyeInFluid(FluidTags.WATER);
      this.fluidOnEyes = null;
      double â˜ƒx = this.getEyeY() - 0.11111111F;
      Entity â˜ƒxx = this.getVehicle();
      if (â˜ƒxx instanceof Boat â˜ƒ && !â˜ƒ.isUnderWater() && â˜ƒ.getBoundingBox().maxY >= â˜ƒx && â˜ƒ.getBoundingBox().minY <= â˜ƒx) {
         return;
      }

      BlockPos â˜ƒ = new BlockPos(this.getX(), â˜ƒx, this.getZ());
      FluidState â˜ƒx = this.level.getFluidState(â˜ƒ);

      for(Tag<Fluid> â˜ƒxx : FluidTags.getStaticTags()) {
         if (â˜ƒx.is(â˜ƒxx)) {
            double â˜ƒxxx = (double)((float)â˜ƒ.getY() + â˜ƒx.getHeight(this.level, â˜ƒ));
            if (â˜ƒxxx > â˜ƒx) {
               this.fluidOnEyes = â˜ƒxx;
            }

            return;
         }
      }
   }

   protected void doWaterSplashEffect() {
      Entity â˜ƒ = this.isVehicle() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
      float â˜ƒx = â˜ƒ == this ? 0.2F : 0.9F;
      Vec3 â˜ƒxx = â˜ƒ.getDeltaMovement();
      float â˜ƒxxx = Math.min(1.0F, (float)Math.sqrt(â˜ƒxx.x * â˜ƒxx.x * 0.2F + â˜ƒxx.y * â˜ƒxx.y + â˜ƒxx.z * â˜ƒxx.z * 0.2F) * â˜ƒx);
      if (â˜ƒxxx < 0.25F) {
         this.playSound(this.getSwimSplashSound(), â˜ƒxxx, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
      } else {
         this.playSound(this.getSwimHighSpeedSplashSound(), â˜ƒxxx, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
      }

      float â˜ƒ = (float)Mth.floor(this.getY());

      for(int â˜ƒx = 0; (float)â˜ƒx < 1.0F + this.dimensions.width * 20.0F; ++â˜ƒx) {
         double â˜ƒxx = (this.random.nextDouble() * 2.0 - 1.0) * (double)this.dimensions.width;
         double â˜ƒxxx = (this.random.nextDouble() * 2.0 - 1.0) * (double)this.dimensions.width;
         this.level
            .addParticle(
               ParticleTypes.BUBBLE,
               this.getX() + â˜ƒxx,
               (double)(â˜ƒ + 1.0F),
               this.getZ() + â˜ƒxxx,
               â˜ƒxx.x,
               â˜ƒxx.y - this.random.nextDouble() * 0.2F,
               â˜ƒxx.z
            );
      }

      for(int â˜ƒx = 0; (float)â˜ƒx < 1.0F + this.dimensions.width * 20.0F; ++â˜ƒx) {
         double â˜ƒxx = (this.random.nextDouble() * 2.0 - 1.0) * (double)this.dimensions.width;
         double â˜ƒxxx = (this.random.nextDouble() * 2.0 - 1.0) * (double)this.dimensions.width;
         this.level.addParticle(ParticleTypes.SPLASH, this.getX() + â˜ƒxx, (double)(â˜ƒ + 1.0F), this.getZ() + â˜ƒxxx, â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z);
      }

      this.gameEvent(GameEvent.SPLASH);
   }

   protected BlockState getBlockStateOn() {
      return this.level.getBlockState(this.getOnPos());
   }

   public boolean canSpawnSprintParticle() {
      return this.isSprinting() && !this.isInWater() && !this.isSpectator() && !this.isCrouching() && !this.isInLava() && this.isAlive();
   }

   protected void spawnSprintParticle() {
      int â˜ƒ = Mth.floor(this.getX());
      int â˜ƒx = Mth.floor(this.getY() - 0.2F);
      int â˜ƒxx = Mth.floor(this.getZ());
      BlockPos â˜ƒxxx = new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
      BlockState â˜ƒxxxx = this.level.getBlockState(â˜ƒxxx);
      if (â˜ƒxxxx.getRenderShape() != RenderShape.INVISIBLE) {
         Vec3 â˜ƒxxxxx = this.getDeltaMovement();
         this.level
            .addParticle(
               new BlockParticleOption(ParticleTypes.BLOCK, â˜ƒxxxx),
               this.getX() + (this.random.nextDouble() - 0.5) * (double)this.dimensions.width,
               this.getY() + 0.1,
               this.getZ() + (this.random.nextDouble() - 0.5) * (double)this.dimensions.width,
               â˜ƒxxxxx.x * -4.0,
               1.5,
               â˜ƒxxxxx.z * -4.0
            );
      }
   }

   public boolean isEyeInFluid(Tag<Fluid> var1) {
      return this.fluidOnEyes == â˜ƒ;
   }

   public boolean isInLava() {
      return !this.firstTick && this.fluidHeight.getDouble(FluidTags.LAVA) > 0.0;
   }

   public void moveRelative(float var1, Vec3 var2) {
      Vec3 â˜ƒ = getInputVector(â˜ƒ, â˜ƒ, this.getYRot());
      this.setDeltaMovement(this.getDeltaMovement().add(â˜ƒ));
   }

   private static Vec3 getInputVector(Vec3 var0, float var1, float var2) {
      double â˜ƒ = â˜ƒ.lengthSqr();
      if (â˜ƒ < 1.0E-7) {
         return Vec3.ZERO;
      } else {
         Vec3 â˜ƒ = (â˜ƒ > 1.0 ? â˜ƒ.normalize() : â˜ƒ).scale((double)â˜ƒ);
         float â˜ƒx = Mth.sin(â˜ƒ * (float) (Math.PI / 180.0));
         float â˜ƒxx = Mth.cos(â˜ƒ * (float) (Math.PI / 180.0));
         return new Vec3(â˜ƒ.x * (double)â˜ƒxx - â˜ƒ.z * (double)â˜ƒx, â˜ƒ.y, â˜ƒ.z * (double)â˜ƒxx + â˜ƒ.x * (double)â˜ƒx);
      }
   }

   public float getBrightness() {
      return this.level.hasChunkAt(this.getBlockX(), this.getBlockZ())
         ? this.level.getBrightness(new BlockPos(this.getX(), this.getEyeY(), this.getZ()))
         : 0.0F;
   }

   public void absMoveTo(double var1, double var3, double var5, float var7, float var8) {
      this.absMoveTo(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setYRot(â˜ƒ % 360.0F);
      this.setXRot(Mth.clamp(â˜ƒ, -90.0F, 90.0F) % 360.0F);
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
   }

   public void absMoveTo(double var1, double var3, double var5) {
      double â˜ƒ = Mth.clamp(â˜ƒ, -3.0E7, 3.0E7);
      double â˜ƒx = Mth.clamp(â˜ƒ, -3.0E7, 3.0E7);
      this.xo = â˜ƒ;
      this.yo = â˜ƒ;
      this.zo = â˜ƒx;
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void moveTo(Vec3 var1) {
      this.moveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public void moveTo(double var1, double var3, double var5) {
      this.moveTo(â˜ƒ, â˜ƒ, â˜ƒ, this.getYRot(), this.getXRot());
   }

   public void moveTo(BlockPos var1, float var2, float var3) {
      this.moveTo((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5, â˜ƒ, â˜ƒ);
   }

   public void moveTo(double var1, double var3, double var5, float var7, float var8) {
      this.setPosRaw(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setYRot(â˜ƒ);
      this.setXRot(â˜ƒ);
      this.setOldPosAndRot();
      this.reapplyPosition();
   }

   public final void setOldPosAndRot() {
      double â˜ƒ = this.getX();
      double â˜ƒx = this.getY();
      double â˜ƒxx = this.getZ();
      this.xo = â˜ƒ;
      this.yo = â˜ƒx;
      this.zo = â˜ƒxx;
      this.xOld = â˜ƒ;
      this.yOld = â˜ƒx;
      this.zOld = â˜ƒxx;
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
   }

   public float distanceTo(Entity var1) {
      float â˜ƒ = (float)(this.getX() - â˜ƒ.getX());
      float â˜ƒx = (float)(this.getY() - â˜ƒ.getY());
      float â˜ƒxx = (float)(this.getZ() - â˜ƒ.getZ());
      return Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx);
   }

   public double distanceToSqr(double var1, double var3, double var5) {
      double â˜ƒ = this.getX() - â˜ƒ;
      double â˜ƒx = this.getY() - â˜ƒ;
      double â˜ƒxx = this.getZ() - â˜ƒ;
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
   }

   public double distanceToSqr(Entity var1) {
      return this.distanceToSqr(â˜ƒ.position());
   }

   public double distanceToSqr(Vec3 var1) {
      double â˜ƒ = this.getX() - â˜ƒ.x;
      double â˜ƒx = this.getY() - â˜ƒ.y;
      double â˜ƒxx = this.getZ() - â˜ƒ.z;
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
   }

   public void playerTouch(Player var1) {
   }

   public void push(Entity var1) {
      if (!this.isPassengerOfSameVehicle(â˜ƒ)) {
         if (!â˜ƒ.noPhysics && !this.noPhysics) {
            double â˜ƒ = â˜ƒ.getX() - this.getX();
            double â˜ƒx = â˜ƒ.getZ() - this.getZ();
            double â˜ƒxx = Mth.absMax(â˜ƒ, â˜ƒx);
            if (â˜ƒxx >= 0.01F) {
               â˜ƒxx = Math.sqrt(â˜ƒxx);
               â˜ƒ /= â˜ƒxx;
               â˜ƒx /= â˜ƒxx;
               double â˜ƒxxx = 1.0 / â˜ƒxx;
               if (â˜ƒxxx > 1.0) {
                  â˜ƒxxx = 1.0;
               }

               â˜ƒ *= â˜ƒxxx;
               â˜ƒx *= â˜ƒxxx;
               â˜ƒ *= 0.05F;
               â˜ƒx *= 0.05F;
               if (!this.isVehicle()) {
                  this.push(-â˜ƒ, 0.0, -â˜ƒx);
               }

               if (!â˜ƒ.isVehicle()) {
                  â˜ƒ.push(â˜ƒ, 0.0, â˜ƒx);
               }
            }
         }
      }
   }

   public void push(double var1, double var3, double var5) {
      this.setDeltaMovement(this.getDeltaMovement().add(â˜ƒ, â˜ƒ, â˜ƒ));
      this.hasImpulse = true;
   }

   protected void markHurt() {
      this.hurtMarked = true;
   }

   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         this.markHurt();
         return false;
      }
   }

   public final Vec3 getViewVector(float var1) {
      return this.calculateViewVector(this.getViewXRot(â˜ƒ), this.getViewYRot(â˜ƒ));
   }

   public float getViewXRot(float var1) {
      return â˜ƒ == 1.0F ? this.getXRot() : Mth.lerp(â˜ƒ, this.xRotO, this.getXRot());
   }

   public float getViewYRot(float var1) {
      return â˜ƒ == 1.0F ? this.getYRot() : Mth.lerp(â˜ƒ, this.yRotO, this.getYRot());
   }

   protected final Vec3 calculateViewVector(float var1, float var2) {
      float â˜ƒ = â˜ƒ * (float) (Math.PI / 180.0);
      float â˜ƒx = -â˜ƒ * (float) (Math.PI / 180.0);
      float â˜ƒxx = Mth.cos(â˜ƒx);
      float â˜ƒxxx = Mth.sin(â˜ƒx);
      float â˜ƒxxxx = Mth.cos(â˜ƒ);
      float â˜ƒxxxxx = Mth.sin(â˜ƒ);
      return new Vec3((double)(â˜ƒxxx * â˜ƒxxxx), (double)(-â˜ƒxxxxx), (double)(â˜ƒxx * â˜ƒxxxx));
   }

   public final Vec3 getUpVector(float var1) {
      return this.calculateUpVector(this.getViewXRot(â˜ƒ), this.getViewYRot(â˜ƒ));
   }

   protected final Vec3 calculateUpVector(float var1, float var2) {
      return this.calculateViewVector(â˜ƒ - 90.0F, â˜ƒ);
   }

   public final Vec3 getEyePosition() {
      return new Vec3(this.getX(), this.getEyeY(), this.getZ());
   }

   public final Vec3 getEyePosition(float var1) {
      double â˜ƒ = Mth.lerp((double)â˜ƒ, this.xo, this.getX());
      double â˜ƒx = Mth.lerp((double)â˜ƒ, this.yo, this.getY()) + (double)this.getEyeHeight();
      double â˜ƒxx = Mth.lerp((double)â˜ƒ, this.zo, this.getZ());
      return new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public Vec3 getLightProbePosition(float var1) {
      return this.getEyePosition(â˜ƒ);
   }

   public final Vec3 getPosition(float var1) {
      double â˜ƒ = Mth.lerp((double)â˜ƒ, this.xo, this.getX());
      double â˜ƒx = Mth.lerp((double)â˜ƒ, this.yo, this.getY());
      double â˜ƒxx = Mth.lerp((double)â˜ƒ, this.zo, this.getZ());
      return new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public HitResult pick(double var1, float var3, boolean var4) {
      Vec3 â˜ƒ = this.getEyePosition(â˜ƒ);
      Vec3 â˜ƒx = this.getViewVector(â˜ƒ);
      Vec3 â˜ƒxx = â˜ƒ.add(â˜ƒx.x * â˜ƒ, â˜ƒx.y * â˜ƒ, â˜ƒx.z * â˜ƒ);
      return this.level.clip(new ClipContext(â˜ƒ, â˜ƒxx, ClipContext.Block.OUTLINE, â˜ƒ ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, this));
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public void awardKillScore(Entity var1, int var2, DamageSource var3) {
      if (â˜ƒ instanceof ServerPlayer) {
         CriteriaTriggers.ENTITY_KILLED_PLAYER.trigger((ServerPlayer)â˜ƒ, this, â˜ƒ);
      }
   }

   public boolean shouldRender(double var1, double var3, double var5) {
      double â˜ƒ = this.getX() - â˜ƒ;
      double â˜ƒx = this.getY() - â˜ƒ;
      double â˜ƒxx = this.getZ() - â˜ƒ;
      double â˜ƒxxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
      return this.shouldRenderAtSqrDistance(â˜ƒxxx);
   }

   public boolean shouldRenderAtSqrDistance(double var1) {
      double â˜ƒ = this.getBoundingBox().getSize();
      if (Double.isNaN(â˜ƒ)) {
         â˜ƒ = 1.0;
      }

      â˜ƒ *= 64.0 * viewScale;
      return â˜ƒ < â˜ƒ * â˜ƒ;
   }

   public boolean saveAsPassenger(CompoundTag var1) {
      if (this.removalReason != null && !this.removalReason.shouldSave()) {
         return false;
      } else {
         String â˜ƒ = this.getEncodeId();
         if (â˜ƒ == null) {
            return false;
         } else {
            â˜ƒ.putString("id", â˜ƒ);
            this.saveWithoutId(â˜ƒ);
            return true;
         }
      }
   }

   public boolean save(CompoundTag var1) {
      return this.isPassenger() ? false : this.saveAsPassenger(â˜ƒ);
   }

   public CompoundTag saveWithoutId(CompoundTag var1) {
      try {
         if (this.vehicle != null) {
            â˜ƒ.put("Pos", this.newDoubleList(this.vehicle.getX(), this.getY(), this.vehicle.getZ()));
         } else {
            â˜ƒ.put("Pos", this.newDoubleList(this.getX(), this.getY(), this.getZ()));
         }

         Vec3 â˜ƒ = this.getDeltaMovement();
         â˜ƒ.put("Motion", this.newDoubleList(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z));
         â˜ƒ.put("Rotation", this.newFloatList(this.getYRot(), this.getXRot()));
         â˜ƒ.putFloat("FallDistance", this.fallDistance);
         â˜ƒ.putShort("Fire", (short)this.remainingFireTicks);
         â˜ƒ.putShort("Air", (short)this.getAirSupply());
         â˜ƒ.putBoolean("OnGround", this.onGround);
         â˜ƒ.putBoolean("Invulnerable", this.invulnerable);
         â˜ƒ.putInt("PortalCooldown", this.portalCooldown);
         â˜ƒ.putUUID("UUID", this.getUUID());
         Component â˜ƒx = this.getCustomName();
         if (â˜ƒx != null) {
            â˜ƒ.putString("CustomName", Component.Serializer.toJson(â˜ƒx));
         }

         if (this.isCustomNameVisible()) {
            â˜ƒ.putBoolean("CustomNameVisible", this.isCustomNameVisible());
         }

         if (this.isSilent()) {
            â˜ƒ.putBoolean("Silent", this.isSilent());
         }

         if (this.isNoGravity()) {
            â˜ƒ.putBoolean("NoGravity", this.isNoGravity());
         }

         if (this.hasGlowingTag) {
            â˜ƒ.putBoolean("Glowing", true);
         }

         int â˜ƒ = this.getTicksFrozen();
         if (â˜ƒ > 0) {
            â˜ƒ.putInt("TicksFrozen", this.getTicksFrozen());
         }

         if (this.hasVisualFire) {
            â˜ƒ.putBoolean("HasVisualFire", this.hasVisualFire);
         }

         if (!this.tags.isEmpty()) {
            ListTag â˜ƒ = new ListTag();

            for(String â˜ƒx : this.tags) {
               â˜ƒ.add(StringTag.valueOf(â˜ƒx));
            }

            â˜ƒ.put("Tags", â˜ƒ);
         }

         this.addAdditionalSaveData(â˜ƒ);
         if (this.isVehicle()) {
            ListTag â˜ƒ = new ListTag();

            for(Entity â˜ƒx : this.getPassengers()) {
               CompoundTag â˜ƒxx = new CompoundTag();
               if (â˜ƒx.saveAsPassenger(â˜ƒxx)) {
                  â˜ƒ.add(â˜ƒxx);
               }
            }

            if (!â˜ƒ.isEmpty()) {
               â˜ƒ.put("Passengers", â˜ƒ);
            }
         }

         return â˜ƒ;
      } catch (Throwable var9) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var9, "Saving entity NBT");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Entity being saved");
         this.fillCrashReportCategory(â˜ƒx);
         throw new ReportedException(â˜ƒ);
      }
   }

   public void load(CompoundTag var1) {
      try {
         ListTag â˜ƒ = â˜ƒ.getList("Pos", 6);
         ListTag â˜ƒx = â˜ƒ.getList("Motion", 6);
         ListTag â˜ƒxx = â˜ƒ.getList("Rotation", 5);
         double â˜ƒxxx = â˜ƒx.getDouble(0);
         double â˜ƒxxxx = â˜ƒx.getDouble(1);
         double â˜ƒxxxxx = â˜ƒx.getDouble(2);
         this.setDeltaMovement(Math.abs(â˜ƒxxx) > 10.0 ? 0.0 : â˜ƒxxx, Math.abs(â˜ƒxxxx) > 10.0 ? 0.0 : â˜ƒxxxx, Math.abs(â˜ƒxxxxx) > 10.0 ? 0.0 : â˜ƒxxxxx);
         this.setPosRaw(â˜ƒ.getDouble(0), Mth.clamp(â˜ƒ.getDouble(1), -2.0E7, 2.0E7), â˜ƒ.getDouble(2));
         this.setYRot(â˜ƒxx.getFloat(0));
         this.setXRot(â˜ƒxx.getFloat(1));
         this.setOldPosAndRot();
         this.setYHeadRot(this.getYRot());
         this.setYBodyRot(this.getYRot());
         this.fallDistance = â˜ƒ.getFloat("FallDistance");
         this.remainingFireTicks = â˜ƒ.getShort("Fire");
         if (â˜ƒ.contains("Air")) {
            this.setAirSupply(â˜ƒ.getShort("Air"));
         }

         this.onGround = â˜ƒ.getBoolean("OnGround");
         this.invulnerable = â˜ƒ.getBoolean("Invulnerable");
         this.portalCooldown = â˜ƒ.getInt("PortalCooldown");
         if (â˜ƒ.hasUUID("UUID")) {
            this.uuid = â˜ƒ.getUUID("UUID");
            this.stringUUID = this.uuid.toString();
         }

         if (!Double.isFinite(this.getX()) || !Double.isFinite(this.getY()) || !Double.isFinite(this.getZ())) {
            throw new IllegalStateException("Entity has invalid position");
         } else if (Double.isFinite((double)this.getYRot()) && Double.isFinite((double)this.getXRot())) {
            this.reapplyPosition();
            this.setRot(this.getYRot(), this.getXRot());
            if (â˜ƒ.contains("CustomName", 8)) {
               String â˜ƒ = â˜ƒ.getString("CustomName");

               try {
                  this.setCustomName(Component.Serializer.fromJson(â˜ƒ));
               } catch (Exception var14) {
                  LOGGER.warn("Failed to parse entity custom name {}", â˜ƒ, var14);
               }
            }

            this.setCustomNameVisible(â˜ƒ.getBoolean("CustomNameVisible"));
            this.setSilent(â˜ƒ.getBoolean("Silent"));
            this.setNoGravity(â˜ƒ.getBoolean("NoGravity"));
            this.setGlowingTag(â˜ƒ.getBoolean("Glowing"));
            this.setTicksFrozen(â˜ƒ.getInt("TicksFrozen"));
            this.hasVisualFire = â˜ƒ.getBoolean("HasVisualFire");
            if (â˜ƒ.contains("Tags", 9)) {
               this.tags.clear();
               ListTag â˜ƒ = â˜ƒ.getList("Tags", 8);
               int â˜ƒx = Math.min(â˜ƒ.size(), 1024);

               for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
                  this.tags.add(â˜ƒ.getString(â˜ƒxx));
               }
            }

            this.readAdditionalSaveData(â˜ƒ);
            if (this.repositionEntityAfterLoad()) {
               this.reapplyPosition();
            }
         } else {
            throw new IllegalStateException("Entity has invalid rotation");
         }
      } catch (Throwable var15) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var15, "Loading entity NBT");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Entity being loaded");
         this.fillCrashReportCategory(â˜ƒx);
         throw new ReportedException(â˜ƒ);
      }
   }

   protected boolean repositionEntityAfterLoad() {
      return true;
   }

   @Nullable
   protected final String getEncodeId() {
      EntityType<?> â˜ƒ = this.getType();
      ResourceLocation â˜ƒx = EntityType.getKey(â˜ƒ);
      return â˜ƒ.canSerialize() && â˜ƒx != null ? â˜ƒx.toString() : null;
   }

   protected abstract void readAdditionalSaveData(CompoundTag var1);

   protected abstract void addAdditionalSaveData(CompoundTag var1);

   protected ListTag newDoubleList(double... var1) {
      ListTag â˜ƒ = new ListTag();

      for(double â˜ƒx : â˜ƒ) {
         â˜ƒ.add(DoubleTag.valueOf(â˜ƒx));
      }

      return â˜ƒ;
   }

   protected ListTag newFloatList(float... var1) {
      ListTag â˜ƒ = new ListTag();

      for(float â˜ƒx : â˜ƒ) {
         â˜ƒ.add(FloatTag.valueOf(â˜ƒx));
      }

      return â˜ƒ;
   }

   @Nullable
   public ItemEntity spawnAtLocation(ItemLike var1) {
      return this.spawnAtLocation(â˜ƒ, 0);
   }

   @Nullable
   public ItemEntity spawnAtLocation(ItemLike var1, int var2) {
      return this.spawnAtLocation(new ItemStack(â˜ƒ), (float)â˜ƒ);
   }

   @Nullable
   public ItemEntity spawnAtLocation(ItemStack var1) {
      return this.spawnAtLocation(â˜ƒ, 0.0F);
   }

   @Nullable
   public ItemEntity spawnAtLocation(ItemStack var1, float var2) {
      if (â˜ƒ.isEmpty()) {
         return null;
      } else if (this.level.isClientSide) {
         return null;
      } else {
         ItemEntity â˜ƒ = new ItemEntity(this.level, this.getX(), this.getY() + (double)â˜ƒ, this.getZ(), â˜ƒ);
         â˜ƒ.setDefaultPickUpDelay();
         this.level.addFreshEntity(â˜ƒ);
         return â˜ƒ;
      }
   }

   public boolean isAlive() {
      return !this.isRemoved();
   }

   public boolean isInWall() {
      if (this.noPhysics) {
         return false;
      } else {
         float â˜ƒ = this.dimensions.width * 0.8F;
         AABB â˜ƒx = AABB.ofSize(this.getEyePosition(), (double)â˜ƒ, 1.0E-6, (double)â˜ƒ);
         return this.level.getBlockCollisions(this, â˜ƒx, (var1x, var2x) -> var1x.isSuffocating(this.level, var2x)).findAny().isPresent();
      }
   }

   public InteractionResult interact(Player var1, InteractionHand var2) {
      return InteractionResult.PASS;
   }

   public boolean canCollideWith(Entity var1) {
      return â˜ƒ.canBeCollidedWith() && !this.isPassengerOfSameVehicle(â˜ƒ);
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public void rideTick() {
      this.setDeltaMovement(Vec3.ZERO);
      this.tick();
      if (this.isPassenger()) {
         this.getVehicle().positionRider(this);
      }
   }

   public void positionRider(Entity var1) {
      this.positionRider(â˜ƒ, Entity::setPos);
   }

   private void positionRider(Entity var1, Entity.MoveFunction var2) {
      if (this.hasPassenger(â˜ƒ)) {
         double â˜ƒ = this.getY() + this.getPassengersRidingOffset() + â˜ƒ.getMyRidingOffset();
         â˜ƒ.accept(â˜ƒ, this.getX(), â˜ƒ, this.getZ());
      }
   }

   public void onPassengerTurned(Entity var1) {
   }

   public double getMyRidingOffset() {
      return 0.0;
   }

   public double getPassengersRidingOffset() {
      return (double)this.dimensions.height * 0.75;
   }

   public boolean startRiding(Entity var1) {
      return this.startRiding(â˜ƒ, false);
   }

   public boolean showVehicleHealth() {
      return this instanceof LivingEntity;
   }

   public boolean startRiding(Entity var1, boolean var2) {
      if (â˜ƒ == this.vehicle) {
         return false;
      } else {
         for(Entity â˜ƒ = â˜ƒ; â˜ƒ.vehicle != null; â˜ƒ = â˜ƒ.vehicle) {
            if (â˜ƒ.vehicle == this) {
               return false;
            }
         }

         if (â˜ƒ || this.canRide(â˜ƒ) && â˜ƒ.canAddPassenger(this)) {
            if (this.isPassenger()) {
               this.stopRiding();
            }

            this.setPose(Pose.STANDING);
            this.vehicle = â˜ƒ;
            this.vehicle.addPassenger(this);
            â˜ƒ.getIndirectPassengersStream()
               .filter(var0 -> var0 instanceof ServerPlayer)
               .forEach(var0 -> CriteriaTriggers.START_RIDING_TRIGGER.trigger((ServerPlayer)var0));
            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean canRide(Entity var1) {
      return !this.isShiftKeyDown() && this.boardingCooldown <= 0;
   }

   protected boolean canEnterPose(Pose var1) {
      return this.level.noCollision(this, this.getBoundingBoxForPose(â˜ƒ).deflate(1.0E-7));
   }

   public void ejectPassengers() {
      for(int â˜ƒ = this.passengers.size() - 1; â˜ƒ >= 0; --â˜ƒ) {
         ((Entity)this.passengers.get(â˜ƒ)).stopRiding();
      }
   }

   public void removeVehicle() {
      if (this.vehicle != null) {
         Entity â˜ƒ = this.vehicle;
         this.vehicle = null;
         â˜ƒ.removePassenger(this);
      }
   }

   public void stopRiding() {
      this.removeVehicle();
   }

   protected void addPassenger(Entity var1) {
      if (â˜ƒ.getVehicle() != this) {
         throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
      } else {
         if (this.passengers.isEmpty()) {
            this.passengers = ImmutableList.of(â˜ƒ);
         } else {
            List<Entity> â˜ƒ = Lists.<Entity>newArrayList(this.passengers);
            if (!this.level.isClientSide && â˜ƒ instanceof Player && !(this.getControllingPassenger() instanceof Player)) {
               â˜ƒ.add(0, â˜ƒ);
            } else {
               â˜ƒ.add(â˜ƒ);
            }

            this.passengers = ImmutableList.copyOf(â˜ƒ);
         }
      }
   }

   protected void removePassenger(Entity var1) {
      if (â˜ƒ.getVehicle() == this) {
         throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
      } else {
         if (this.passengers.size() == 1 && this.passengers.get(0) == â˜ƒ) {
            this.passengers = ImmutableList.of();
         } else {
            this.passengers = (ImmutableList)this.passengers.stream().filter(var1x -> var1x != â˜ƒ).collect(ImmutableList.toImmutableList());
         }

         â˜ƒ.boardingCooldown = 60;
      }
   }

   protected boolean canAddPassenger(Entity var1) {
      return this.passengers.isEmpty();
   }

   public void lerpTo(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setRot(â˜ƒ, â˜ƒ);
   }

   public void lerpHeadTo(float var1, int var2) {
      this.setYHeadRot(â˜ƒ);
   }

   public float getPickRadius() {
      return 0.0F;
   }

   public Vec3 getLookAngle() {
      return this.calculateViewVector(this.getXRot(), this.getYRot());
   }

   public Vec2 getRotationVector() {
      return new Vec2(this.getXRot(), this.getYRot());
   }

   public Vec3 getForward() {
      return Vec3.directionFromRotation(this.getRotationVector());
   }

   public void handleInsidePortal(BlockPos var1) {
      if (this.isOnPortalCooldown()) {
         this.setPortalCooldown();
      } else {
         if (!this.level.isClientSide && !â˜ƒ.equals(this.portalEntrancePos)) {
            this.portalEntrancePos = â˜ƒ.immutable();
         }

         this.isInsidePortal = true;
      }
   }

   protected void handleNetherPortal() {
      if (this.level instanceof ServerLevel) {
         int â˜ƒ = this.getPortalWaitTime();
         ServerLevel â˜ƒx = (ServerLevel)this.level;
         if (this.isInsidePortal) {
            MinecraftServer â˜ƒxx = â˜ƒx.getServer();
            ResourceKey<Level> â˜ƒxxx = this.level.dimension() == Level.NETHER ? Level.OVERWORLD : Level.NETHER;
            ServerLevel â˜ƒxxxx = â˜ƒxx.getLevel(â˜ƒxxx);
            if (â˜ƒxxxx != null && â˜ƒxx.isNetherEnabled() && !this.isPassenger() && this.portalTime++ >= â˜ƒ) {
               this.level.getProfiler().push("portal");
               this.portalTime = â˜ƒ;
               this.setPortalCooldown();
               this.changeDimension(â˜ƒxxxx);
               this.level.getProfiler().pop();
            }

            this.isInsidePortal = false;
         } else {
            if (this.portalTime > 0) {
               this.portalTime -= 4;
            }

            if (this.portalTime < 0) {
               this.portalTime = 0;
            }
         }

         this.processPortalCooldown();
      }
   }

   public int getDimensionChangingDelay() {
      return 300;
   }

   public void lerpMotion(double var1, double var3, double var5) {
      this.setDeltaMovement(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void handleEntityEvent(byte var1) {
      switch(â˜ƒ) {
         case 53:
            HoneyBlock.showSlideParticles(this);
      }
   }

   public void animateHurt() {
   }

   public Iterable<ItemStack> getHandSlots() {
      return EMPTY_LIST;
   }

   public Iterable<ItemStack> getArmorSlots() {
      return EMPTY_LIST;
   }

   public Iterable<ItemStack> getAllSlots() {
      return Iterables.concat(this.getHandSlots(), this.getArmorSlots());
   }

   public void setItemSlot(EquipmentSlot var1, ItemStack var2) {
   }

   public boolean isOnFire() {
      boolean â˜ƒ = this.level != null && this.level.isClientSide;
      return !this.fireImmune() && (this.remainingFireTicks > 0 || â˜ƒ && this.getSharedFlag(0));
   }

   public boolean isPassenger() {
      return this.getVehicle() != null;
   }

   public boolean isVehicle() {
      return !this.passengers.isEmpty();
   }

   public boolean rideableUnderWater() {
      return true;
   }

   public void setShiftKeyDown(boolean var1) {
      this.setSharedFlag(1, â˜ƒ);
   }

   public boolean isShiftKeyDown() {
      return this.getSharedFlag(1);
   }

   public boolean isSteppingCarefully() {
      return this.isShiftKeyDown();
   }

   public boolean isSuppressingBounce() {
      return this.isShiftKeyDown();
   }

   public boolean isDiscrete() {
      return this.isShiftKeyDown();
   }

   public boolean isDescending() {
      return this.isShiftKeyDown();
   }

   public boolean isCrouching() {
      return this.getPose() == Pose.CROUCHING;
   }

   public boolean isSprinting() {
      return this.getSharedFlag(3);
   }

   public void setSprinting(boolean var1) {
      this.setSharedFlag(3, â˜ƒ);
   }

   public boolean isSwimming() {
      return this.getSharedFlag(4);
   }

   public boolean isVisuallySwimming() {
      return this.getPose() == Pose.SWIMMING;
   }

   public boolean isVisuallyCrawling() {
      return this.isVisuallySwimming() && !this.isInWater();
   }

   public void setSwimming(boolean var1) {
      this.setSharedFlag(4, â˜ƒ);
   }

   public final boolean hasGlowingTag() {
      return this.hasGlowingTag;
   }

   public final void setGlowingTag(boolean var1) {
      this.hasGlowingTag = â˜ƒ;
      this.setSharedFlag(6, this.isCurrentlyGlowing());
   }

   public boolean isCurrentlyGlowing() {
      return this.level.isClientSide() ? this.getSharedFlag(6) : this.hasGlowingTag;
   }

   public boolean isInvisible() {
      return this.getSharedFlag(5);
   }

   public boolean isInvisibleTo(Player var1) {
      if (â˜ƒ.isSpectator()) {
         return false;
      } else {
         Team â˜ƒ = this.getTeam();
         return â˜ƒ != null && â˜ƒ != null && â˜ƒ.getTeam() == â˜ƒ && â˜ƒ.canSeeFriendlyInvisibles() ? false : this.isInvisible();
      }
   }

   @Nullable
   public GameEventListenerRegistrar getGameEventListenerRegistrar() {
      return null;
   }

   @Nullable
   public Team getTeam() {
      return this.level.getScoreboard().getPlayersTeam(this.getScoreboardName());
   }

   public boolean isAlliedTo(Entity var1) {
      return this.isAlliedTo(â˜ƒ.getTeam());
   }

   public boolean isAlliedTo(Team var1) {
      return this.getTeam() != null ? this.getTeam().isAlliedTo(â˜ƒ) : false;
   }

   public void setInvisible(boolean var1) {
      this.setSharedFlag(5, â˜ƒ);
   }

   protected boolean getSharedFlag(int var1) {
      return (this.entityData.get(DATA_SHARED_FLAGS_ID) & 1 << â˜ƒ) != 0;
   }

   protected void setSharedFlag(int var1, boolean var2) {
      byte â˜ƒ = this.entityData.get(DATA_SHARED_FLAGS_ID);
      if (â˜ƒ) {
         this.entityData.set(DATA_SHARED_FLAGS_ID, (byte)(â˜ƒ | 1 << â˜ƒ));
      } else {
         this.entityData.set(DATA_SHARED_FLAGS_ID, (byte)(â˜ƒ & ~(1 << â˜ƒ)));
      }
   }

   public int getMaxAirSupply() {
      return 300;
   }

   public int getAirSupply() {
      return this.entityData.get(DATA_AIR_SUPPLY_ID);
   }

   public void setAirSupply(int var1) {
      this.entityData.set(DATA_AIR_SUPPLY_ID, â˜ƒ);
   }

   public int getTicksFrozen() {
      return this.entityData.get(DATA_TICKS_FROZEN);
   }

   public void setTicksFrozen(int var1) {
      this.entityData.set(DATA_TICKS_FROZEN, â˜ƒ);
   }

   public float getPercentFrozen() {
      int â˜ƒ = this.getTicksRequiredToFreeze();
      return (float)Math.min(this.getTicksFrozen(), â˜ƒ) / (float)â˜ƒ;
   }

   public boolean isFullyFrozen() {
      return this.getTicksFrozen() >= this.getTicksRequiredToFreeze();
   }

   public int getTicksRequiredToFreeze() {
      return 140;
   }

   public void thunderHit(ServerLevel var1, LightningBolt var2) {
      this.setRemainingFireTicks(this.remainingFireTicks + 1);
      if (this.remainingFireTicks == 0) {
         this.setSecondsOnFire(8);
      }

      this.hurt(DamageSource.LIGHTNING_BOLT, 5.0F);
   }

   public void onAboveBubbleCol(boolean var1) {
      Vec3 â˜ƒx = this.getDeltaMovement();
      double â˜ƒ;
      if (â˜ƒ) {
         â˜ƒ = Math.max(-0.9, â˜ƒx.y - 0.03);
      } else {
         â˜ƒ = Math.min(1.8, â˜ƒx.y + 0.1);
      }

      this.setDeltaMovement(â˜ƒx.x, â˜ƒ, â˜ƒx.z);
   }

   public void onInsideBubbleColumn(boolean var1) {
      Vec3 â˜ƒx = this.getDeltaMovement();
      double â˜ƒ;
      if (â˜ƒ) {
         â˜ƒ = Math.max(-0.3, â˜ƒx.y - 0.03);
      } else {
         â˜ƒ = Math.min(0.7, â˜ƒx.y + 0.06);
      }

      this.setDeltaMovement(â˜ƒx.x, â˜ƒ, â˜ƒx.z);
      this.fallDistance = 0.0F;
   }

   public void killed(ServerLevel var1, LivingEntity var2) {
   }

   protected void moveTowardsClosestSpace(double var1, double var3, double var5) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      Vec3 â˜ƒx = new Vec3(â˜ƒ - (double)â˜ƒ.getX(), â˜ƒ - (double)â˜ƒ.getY(), â˜ƒ - (double)â˜ƒ.getZ());
      BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();
      Direction â˜ƒxxx = Direction.UP;
      double â˜ƒxxxx = Double.MAX_VALUE;

      for(Direction â˜ƒxxxxx : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP}) {
         â˜ƒxx.setWithOffset(â˜ƒ, â˜ƒxxxxx);
         if (!this.level.getBlockState(â˜ƒxx).isCollisionShapeFullBlock(this.level, â˜ƒxx)) {
            double â˜ƒxxxxxx = â˜ƒx.get(â˜ƒxxxxx.getAxis());
            double â˜ƒxxxxxxx = â˜ƒxxxxx.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 1.0 - â˜ƒxxxxxx : â˜ƒxxxxxx;
            if (â˜ƒxxxxxxx < â˜ƒxxxx) {
               â˜ƒxxxx = â˜ƒxxxxxxx;
               â˜ƒxxx = â˜ƒxxxxx;
            }
         }
      }

      float â˜ƒxxxxx = this.random.nextFloat() * 0.2F + 0.1F;
      float â˜ƒxxxxxx = (float)â˜ƒxxx.getAxisDirection().getStep();
      Vec3 â˜ƒxxxxxxx = this.getDeltaMovement().scale(0.75);
      if (â˜ƒxxx.getAxis() == Direction.Axis.X) {
         this.setDeltaMovement((double)(â˜ƒxxxxxx * â˜ƒxxxxx), â˜ƒxxxxxxx.y, â˜ƒxxxxxxx.z);
      } else if (â˜ƒxxx.getAxis() == Direction.Axis.Y) {
         this.setDeltaMovement(â˜ƒxxxxxxx.x, (double)(â˜ƒxxxxxx * â˜ƒxxxxx), â˜ƒxxxxxxx.z);
      } else if (â˜ƒxxx.getAxis() == Direction.Axis.Z) {
         this.setDeltaMovement(â˜ƒxxxxxxx.x, â˜ƒxxxxxxx.y, (double)(â˜ƒxxxxxx * â˜ƒxxxxx));
      }
   }

   public void makeStuckInBlock(BlockState var1, Vec3 var2) {
      this.fallDistance = 0.0F;
      this.stuckSpeedMultiplier = â˜ƒ;
   }

   private static Component removeAction(Component var0) {
      MutableComponent â˜ƒ = â˜ƒ.plainCopy().setStyle(â˜ƒ.getStyle().withClickEvent(null));

      for(Component â˜ƒx : â˜ƒ.getSiblings()) {
         â˜ƒ.append(removeAction(â˜ƒx));
      }

      return â˜ƒ;
   }

   @Override
   public Component getName() {
      Component â˜ƒ = this.getCustomName();
      return â˜ƒ != null ? removeAction(â˜ƒ) : this.getTypeName();
   }

   protected Component getTypeName() {
      return this.type.getDescription();
   }

   public boolean is(Entity var1) {
      return this == â˜ƒ;
   }

   public float getYHeadRot() {
      return 0.0F;
   }

   public void setYHeadRot(float var1) {
   }

   public void setYBodyRot(float var1) {
   }

   public boolean isAttackable() {
      return true;
   }

   public boolean skipAttackInteraction(Entity var1) {
      return false;
   }

   public String toString() {
      return String.format(
         Locale.ROOT,
         "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
         this.getClass().getSimpleName(),
         this.getName().getString(),
         this.id,
         this.level == null ? "~NULL~" : this.level.toString(),
         this.getX(),
         this.getY(),
         this.getZ()
      );
   }

   public boolean isInvulnerableTo(DamageSource var1) {
      return this.isRemoved() || this.invulnerable && â˜ƒ != DamageSource.OUT_OF_WORLD && !â˜ƒ.isCreativePlayer();
   }

   public boolean isInvulnerable() {
      return this.invulnerable;
   }

   public void setInvulnerable(boolean var1) {
      this.invulnerable = â˜ƒ;
   }

   public void copyPosition(Entity var1) {
      this.moveTo(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getYRot(), â˜ƒ.getXRot());
   }

   public void restoreFrom(Entity var1) {
      CompoundTag â˜ƒ = â˜ƒ.saveWithoutId(new CompoundTag());
      â˜ƒ.remove("Dimension");
      this.load(â˜ƒ);
      this.portalCooldown = â˜ƒ.portalCooldown;
      this.portalEntrancePos = â˜ƒ.portalEntrancePos;
   }

   @Nullable
   public Entity changeDimension(ServerLevel var1) {
      if (this.level instanceof ServerLevel && !this.isRemoved()) {
         this.level.getProfiler().push("changeDimension");
         this.unRide();
         this.level.getProfiler().push("reposition");
         PortalInfo â˜ƒ = this.findDimensionEntryPoint(â˜ƒ);
         if (â˜ƒ == null) {
            return null;
         } else {
            this.level.getProfiler().popPush("reloading");
            Entity â˜ƒ = this.getType().create(â˜ƒ);
            if (â˜ƒ != null) {
               â˜ƒ.restoreFrom(this);
               â˜ƒ.moveTo(â˜ƒ.pos.x, â˜ƒ.pos.y, â˜ƒ.pos.z, â˜ƒ.yRot, â˜ƒ.getXRot());
               â˜ƒ.setDeltaMovement(â˜ƒ.speed);
               â˜ƒ.addDuringTeleport(â˜ƒ);
               if (â˜ƒ.dimension() == Level.END) {
                  ServerLevel.makeObsidianPlatform(â˜ƒ);
               }
            }

            this.removeAfterChangingDimensions();
            this.level.getProfiler().pop();
            ((ServerLevel)this.level).resetEmptyTime();
            â˜ƒ.resetEmptyTime();
            this.level.getProfiler().pop();
            return â˜ƒ;
         }
      } else {
         return null;
      }
   }

   protected void removeAfterChangingDimensions() {
      this.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
   }

   @Nullable
   protected PortalInfo findDimensionEntryPoint(ServerLevel var1) {
      boolean â˜ƒ = this.level.dimension() == Level.END && â˜ƒ.dimension() == Level.OVERWORLD;
      boolean â˜ƒx = â˜ƒ.dimension() == Level.END;
      if (!â˜ƒ && !â˜ƒx) {
         boolean â˜ƒxx = â˜ƒ.dimension() == Level.NETHER;
         if (this.level.dimension() != Level.NETHER && !â˜ƒxx) {
            return null;
         } else {
            WorldBorder â˜ƒxx = â˜ƒ.getWorldBorder();
            double â˜ƒxxx = Math.max(-2.9999872E7, â˜ƒxx.getMinX() + 16.0);
            double â˜ƒxxxx = Math.max(-2.9999872E7, â˜ƒxx.getMinZ() + 16.0);
            double â˜ƒxxxxx = Math.min(2.9999872E7, â˜ƒxx.getMaxX() - 16.0);
            double â˜ƒxxxxxx = Math.min(2.9999872E7, â˜ƒxx.getMaxZ() - 16.0);
            double â˜ƒxxxxxxx = DimensionType.getTeleportationScale(this.level.dimensionType(), â˜ƒ.dimensionType());
            BlockPos â˜ƒxxxxxxxx = new BlockPos(
               Mth.clamp(this.getX() * â˜ƒxxxxxxx, â˜ƒxxx, â˜ƒxxxxx), this.getY(), Mth.clamp(this.getZ() * â˜ƒxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxx)
            );
            return (PortalInfo)this.getExitPortal(â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxx)
               .map(
                  var2x -> {
                     BlockState â˜ƒxx = this.level.getBlockState(this.portalEntrancePos);
                     Direction.Axis â˜ƒ;
                     Vec3 â˜ƒx;
                     if (â˜ƒxx.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                        â˜ƒ = â˜ƒxx.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                        BlockUtil.FoundRectangle â˜ƒxxx = BlockUtil.getLargestRectangleAround(
                           this.portalEntrancePos, â˜ƒ, 21, Direction.Axis.Y, 21, var2xx -> this.level.getBlockState(var2xx) == â˜ƒ
                        );
                        â˜ƒx = this.getRelativePortalPosition(â˜ƒ, â˜ƒxxx);
                     } else {
                        â˜ƒ = Direction.Axis.X;
                        â˜ƒx = new Vec3(0.5, 0.0, 0.0);
                     }
      
                     return PortalShape.createPortalInfo(
                        â˜ƒ, var2x, â˜ƒ, â˜ƒx, this.getDimensions(this.getPose()), this.getDeltaMovement(), this.getYRot(), this.getXRot()
                     );
                  }
               )
               .orElse(null);
         }
      } else {
         BlockPos â˜ƒ;
         if (â˜ƒx) {
            â˜ƒ = ServerLevel.END_SPAWN_POINT;
         } else {
            â˜ƒ = â˜ƒ.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, â˜ƒ.getSharedSpawnPos());
         }

         return new PortalInfo(
            new Vec3((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5), this.getDeltaMovement(), this.getYRot(), this.getXRot()
         );
      }
   }

   protected Vec3 getRelativePortalPosition(Direction.Axis var1, BlockUtil.FoundRectangle var2) {
      return PortalShape.getRelativePosition(â˜ƒ, â˜ƒ, this.position(), this.getDimensions(this.getPose()));
   }

   protected Optional<BlockUtil.FoundRectangle> getExitPortal(ServerLevel var1, BlockPos var2, boolean var3) {
      return â˜ƒ.getPortalForcer().findPortalAround(â˜ƒ, â˜ƒ);
   }

   public boolean canChangeDimensions() {
      return true;
   }

   public float getBlockExplosionResistance(Explosion var1, BlockGetter var2, BlockPos var3, BlockState var4, FluidState var5, float var6) {
      return â˜ƒ;
   }

   public boolean shouldBlockExplode(Explosion var1, BlockGetter var2, BlockPos var3, BlockState var4, float var5) {
      return true;
   }

   public int getMaxFallDistance() {
      return 3;
   }

   public boolean isIgnoringBlockTriggers() {
      return false;
   }

   public void fillCrashReportCategory(CrashReportCategory var1) {
      â˜ƒ.setDetail("Entity Type", (CrashReportDetail<String>)(() -> EntityType.getKey(this.getType()) + " (" + this.getClass().getCanonicalName() + ")"));
      â˜ƒ.setDetail("Entity ID", this.id);
      â˜ƒ.setDetail("Entity Name", (CrashReportDetail<String>)(() -> this.getName().getString()));
      â˜ƒ.setDetail("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.getX(), this.getY(), this.getZ()));
      â˜ƒ.setDetail(
         "Entity's Block location", CrashReportCategory.formatLocation(this.level, Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ()))
      );
      Vec3 â˜ƒ = this.getDeltaMovement();
      â˜ƒ.setDetail("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", â˜ƒ.x, â˜ƒ.y, â˜ƒ.z));
      â˜ƒ.setDetail("Entity's Passengers", (CrashReportDetail<String>)(() -> this.getPassengers().toString()));
      â˜ƒ.setDetail("Entity's Vehicle", (CrashReportDetail<String>)(() -> String.valueOf(this.getVehicle())));
   }

   public boolean displayFireAnimation() {
      return this.isOnFire() && !this.isSpectator();
   }

   public void setUUID(UUID var1) {
      this.uuid = â˜ƒ;
      this.stringUUID = this.uuid.toString();
   }

   @Override
   public UUID getUUID() {
      return this.uuid;
   }

   public String getStringUUID() {
      return this.stringUUID;
   }

   public String getScoreboardName() {
      return this.stringUUID;
   }

   public boolean isPushedByFluid() {
      return true;
   }

   public static double getViewScale() {
      return viewScale;
   }

   public static void setViewScale(double var0) {
      viewScale = â˜ƒ;
   }

   @Override
   public Component getDisplayName() {
      return PlayerTeam.formatNameForTeam(this.getTeam(), this.getName())
         .withStyle(var1 -> var1.withHoverEvent(this.createHoverEvent()).withInsertion(this.getStringUUID()));
   }

   public void setCustomName(@Nullable Component var1) {
      this.entityData.set(DATA_CUSTOM_NAME, Optional.ofNullable(â˜ƒ));
   }

   @Nullable
   @Override
   public Component getCustomName() {
      return (Component)((Optional)this.entityData.get(DATA_CUSTOM_NAME)).orElse(null);
   }

   @Override
   public boolean hasCustomName() {
      return ((Optional)this.entityData.get(DATA_CUSTOM_NAME)).isPresent();
   }

   public void setCustomNameVisible(boolean var1) {
      this.entityData.set(DATA_CUSTOM_NAME_VISIBLE, â˜ƒ);
   }

   public boolean isCustomNameVisible() {
      return this.entityData.get(DATA_CUSTOM_NAME_VISIBLE);
   }

   public final void teleportToWithTicket(double var1, double var3, double var5) {
      if (this.level instanceof ServerLevel) {
         ChunkPos â˜ƒ = new ChunkPos(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
         ((ServerLevel)this.level).getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, â˜ƒ, 0, this.getId());
         this.level.getChunk(â˜ƒ.x, â˜ƒ.z);
         this.teleportTo(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void dismountTo(double var1, double var3, double var5) {
      this.teleportTo(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void teleportTo(double var1, double var3, double var5) {
      if (this.level instanceof ServerLevel) {
         this.moveTo(â˜ƒ, â˜ƒ, â˜ƒ, this.getYRot(), this.getXRot());
         this.getSelfAndPassengers().forEach(var0 -> {
            for(Entity â˜ƒ : var0.passengers) {
               var0.positionRider(â˜ƒ, Entity::moveTo);
            }
         });
      }
   }

   public boolean shouldShowName() {
      return this.isCustomNameVisible();
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_POSE.equals(â˜ƒ)) {
         this.refreshDimensions();
      }
   }

   public void refreshDimensions() {
      EntityDimensions â˜ƒ = this.dimensions;
      Pose â˜ƒx = this.getPose();
      EntityDimensions â˜ƒxx = this.getDimensions(â˜ƒx);
      this.dimensions = â˜ƒxx;
      this.eyeHeight = this.getEyeHeight(â˜ƒx, â˜ƒxx);
      this.reapplyPosition();
      boolean â˜ƒxxx = (double)â˜ƒxx.width <= 4.0 && (double)â˜ƒxx.height <= 4.0;
      if (!this.level.isClientSide
         && !this.firstTick
         && !this.noPhysics
         && â˜ƒxxx
         && (â˜ƒxx.width > â˜ƒ.width || â˜ƒxx.height > â˜ƒ.height)
         && !(this instanceof Player)) {
         Vec3 â˜ƒxxxx = this.position().add(0.0, (double)â˜ƒ.height / 2.0, 0.0);
         double â˜ƒxxxxx = (double)Math.max(0.0F, â˜ƒxx.width - â˜ƒ.width) + 1.0E-6;
         double â˜ƒxxxxxx = (double)Math.max(0.0F, â˜ƒxx.height - â˜ƒ.height) + 1.0E-6;
         VoxelShape â˜ƒxxxxxxx = Shapes.create(AABB.ofSize(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxx));
         this.level
            .findFreePosition(this, â˜ƒxxxxxxx, â˜ƒxxxx, (double)â˜ƒxx.width, (double)â˜ƒxx.height, (double)â˜ƒxx.width)
            .ifPresent(var2x -> this.setPos(var2x.add(0.0, (double)(-â˜ƒ.height) / 2.0, 0.0)));
      }
   }

   public Direction getDirection() {
      return Direction.fromYRot((double)this.getYRot());
   }

   public Direction getMotionDirection() {
      return this.getDirection();
   }

   protected HoverEvent createHoverEvent() {
      return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new HoverEvent.EntityTooltipInfo(this.getType(), this.getUUID(), this.getName()));
   }

   public boolean broadcastToPlayer(ServerPlayer var1) {
      return true;
   }

   @Override
   public final AABB getBoundingBox() {
      return this.bb;
   }

   public AABB getBoundingBoxForCulling() {
      return this.getBoundingBox();
   }

   protected AABB getBoundingBoxForPose(Pose var1) {
      EntityDimensions â˜ƒ = this.getDimensions(â˜ƒ);
      float â˜ƒx = â˜ƒ.width / 2.0F;
      Vec3 â˜ƒxx = new Vec3(this.getX() - (double)â˜ƒx, this.getY(), this.getZ() - (double)â˜ƒx);
      Vec3 â˜ƒxxx = new Vec3(this.getX() + (double)â˜ƒx, this.getY() + (double)â˜ƒ.height, this.getZ() + (double)â˜ƒx);
      return new AABB(â˜ƒxx, â˜ƒxxx);
   }

   public final void setBoundingBox(AABB var1) {
      this.bb = â˜ƒ;
   }

   protected float getEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * 0.85F;
   }

   public float getEyeHeight(Pose var1) {
      return this.getEyeHeight(â˜ƒ, this.getDimensions(â˜ƒ));
   }

   public final float getEyeHeight() {
      return this.eyeHeight;
   }

   public Vec3 getLeashOffset() {
      return new Vec3(0.0, (double)this.getEyeHeight(), (double)(this.getBbWidth() * 0.4F));
   }

   public SlotAccess getSlot(int var1) {
      return SlotAccess.NULL;
   }

   @Override
   public void sendMessage(Component var1, UUID var2) {
   }

   public Level getCommandSenderWorld() {
      return this.level;
   }

   @Nullable
   public MinecraftServer getServer() {
      return this.level.getServer();
   }

   public InteractionResult interactAt(Player var1, Vec3 var2, InteractionHand var3) {
      return InteractionResult.PASS;
   }

   public boolean ignoreExplosion() {
      return false;
   }

   public void doEnchantDamageEffects(LivingEntity var1, Entity var2) {
      if (â˜ƒ instanceof LivingEntity) {
         EnchantmentHelper.doPostHurtEffects((LivingEntity)â˜ƒ, â˜ƒ);
      }

      EnchantmentHelper.doPostDamageEffects(â˜ƒ, â˜ƒ);
   }

   public void startSeenByPlayer(ServerPlayer var1) {
   }

   public void stopSeenByPlayer(ServerPlayer var1) {
   }

   public float rotate(Rotation var1) {
      float â˜ƒ = Mth.wrapDegrees(this.getYRot());
      switch(â˜ƒ) {
         case CLOCKWISE_180:
            return â˜ƒ + 180.0F;
         case COUNTERCLOCKWISE_90:
            return â˜ƒ + 270.0F;
         case CLOCKWISE_90:
            return â˜ƒ + 90.0F;
         default:
            return â˜ƒ;
      }
   }

   public float mirror(Mirror var1) {
      float â˜ƒ = Mth.wrapDegrees(this.getYRot());
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            return -â˜ƒ;
         case FRONT_BACK:
            return 180.0F - â˜ƒ;
         default:
            return â˜ƒ;
      }
   }

   public boolean onlyOpCanSetNbt() {
      return false;
   }

   @Nullable
   public Entity getControllingPassenger() {
      return null;
   }

   public final List<Entity> getPassengers() {
      return this.passengers;
   }

   @Nullable
   public Entity getFirstPassenger() {
      return this.passengers.isEmpty() ? null : (Entity)this.passengers.get(0);
   }

   public boolean hasPassenger(Entity var1) {
      return this.passengers.contains(â˜ƒ);
   }

   public boolean hasPassenger(Predicate<Entity> var1) {
      for(Entity â˜ƒ : this.passengers) {
         if (â˜ƒ.test(â˜ƒ)) {
            return true;
         }
      }

      return false;
   }

   private Stream<Entity> getIndirectPassengersStream() {
      return this.passengers.stream().flatMap(Entity::getSelfAndPassengers);
   }

   @Override
   public Stream<Entity> getSelfAndPassengers() {
      return Stream.concat(Stream.of(this), this.getIndirectPassengersStream());
   }

   @Override
   public Stream<Entity> getPassengersAndSelf() {
      return Stream.concat(this.passengers.stream().flatMap(Entity::getPassengersAndSelf), Stream.of(this));
   }

   public Iterable<Entity> getIndirectPassengers() {
      return () -> this.getIndirectPassengersStream().iterator();
   }

   public boolean hasExactlyOnePlayerPassenger() {
      return this.getIndirectPassengersStream().filter(var0 -> var0 instanceof Player).count() == 1L;
   }

   public Entity getRootVehicle() {
      Entity â˜ƒ = this;

      while(â˜ƒ.isPassenger()) {
         â˜ƒ = â˜ƒ.getVehicle();
      }

      return â˜ƒ;
   }

   public boolean isPassengerOfSameVehicle(Entity var1) {
      return this.getRootVehicle() == â˜ƒ.getRootVehicle();
   }

   public boolean hasIndirectPassenger(Entity var1) {
      return this.getIndirectPassengersStream().anyMatch(var1x -> var1x == â˜ƒ);
   }

   public boolean isControlledByLocalInstance() {
      Entity â˜ƒ = this.getControllingPassenger();
      if (â˜ƒ instanceof Player) {
         return ((Player)â˜ƒ).isLocalPlayer();
      } else {
         return !this.level.isClientSide;
      }
   }

   protected static Vec3 getCollisionHorizontalEscapeVector(double var0, double var2, float var4) {
      double â˜ƒ = (â˜ƒ + â˜ƒ + 1.0E-5F) / 2.0;
      float â˜ƒx = -Mth.sin(â˜ƒ * (float) (Math.PI / 180.0));
      float â˜ƒxx = Mth.cos(â˜ƒ * (float) (Math.PI / 180.0));
      float â˜ƒxxx = Math.max(Math.abs(â˜ƒx), Math.abs(â˜ƒxx));
      return new Vec3((double)â˜ƒx * â˜ƒ / (double)â˜ƒxxx, 0.0, (double)â˜ƒxx * â˜ƒ / (double)â˜ƒxxx);
   }

   public Vec3 getDismountLocationForPassenger(LivingEntity var1) {
      return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
   }

   @Nullable
   public Entity getVehicle() {
      return this.vehicle;
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.NORMAL;
   }

   public SoundSource getSoundSource() {
      return SoundSource.NEUTRAL;
   }

   protected int getFireImmuneTicks() {
      return 1;
   }

   public CommandSourceStack createCommandSourceStack() {
      return new CommandSourceStack(
         this,
         this.position(),
         this.getRotationVector(),
         this.level instanceof ServerLevel ? (ServerLevel)this.level : null,
         this.getPermissionLevel(),
         this.getName().getString(),
         this.getDisplayName(),
         this.level.getServer(),
         this
      );
   }

   protected int getPermissionLevel() {
      return 0;
   }

   public boolean hasPermissions(int var1) {
      return this.getPermissionLevel() >= â˜ƒ;
   }

   @Override
   public boolean acceptsSuccess() {
      return this.level.getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK);
   }

   @Override
   public boolean acceptsFailure() {
      return true;
   }

   @Override
   public boolean shouldInformAdmins() {
      return true;
   }

   public void lookAt(EntityAnchorArgument.Anchor var1, Vec3 var2) {
      Vec3 â˜ƒ = â˜ƒ.apply(this);
      double â˜ƒx = â˜ƒ.x - â˜ƒ.x;
      double â˜ƒxx = â˜ƒ.y - â˜ƒ.y;
      double â˜ƒxxx = â˜ƒ.z - â˜ƒ.z;
      double â˜ƒxxxx = Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxxx * â˜ƒxxx);
      this.setXRot(Mth.wrapDegrees((float)(-(Mth.atan2(â˜ƒxx, â˜ƒxxxx) * 180.0F / (float)Math.PI))));
      this.setYRot(Mth.wrapDegrees((float)(Mth.atan2(â˜ƒxxx, â˜ƒx) * 180.0F / (float)Math.PI) - 90.0F));
      this.setYHeadRot(this.getYRot());
      this.xRotO = this.getXRot();
      this.yRotO = this.getYRot();
   }

   public boolean updateFluidHeightAndDoFluidPushing(Tag<Fluid> var1, double var2) {
      if (this.touchingUnloadedChunk()) {
         return false;
      } else {
         AABB â˜ƒ = this.getBoundingBox().deflate(0.001);
         int â˜ƒx = Mth.floor(â˜ƒ.minX);
         int â˜ƒxx = Mth.ceil(â˜ƒ.maxX);
         int â˜ƒxxx = Mth.floor(â˜ƒ.minY);
         int â˜ƒxxxx = Mth.ceil(â˜ƒ.maxY);
         int â˜ƒxxxxx = Mth.floor(â˜ƒ.minZ);
         int â˜ƒxxxxxx = Mth.ceil(â˜ƒ.maxZ);
         double â˜ƒxxxxxxx = 0.0;
         boolean â˜ƒxxxxxxxx = this.isPushedByFluid();
         boolean â˜ƒxxxxxxxxx = false;
         Vec3 â˜ƒxxxxxxxxxx = Vec3.ZERO;
         int â˜ƒxxxxxxxxxxx = 0;
         BlockPos.MutableBlockPos â˜ƒxxxxxxxxxxxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxxxxxxxxxxxx = â˜ƒx; â˜ƒxxxxxxxxxxxxx < â˜ƒxx; ++â˜ƒxxxxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxx; â˜ƒxxxxxxxxxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxx; â˜ƒxxxxxxxxxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxxxxxxxxxx) {
                  â˜ƒxxxxxxxxxxxx.set(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
                  FluidState â˜ƒxxxxxxxxxxxxxxxx = this.level.getFluidState(â˜ƒxxxxxxxxxxxx);
                  if (â˜ƒxxxxxxxxxxxxxxxx.is(â˜ƒ)) {
                     double â˜ƒxxxxxxxxxxxxxxxxx = (double)((float)â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxx.getHeight(this.level, â˜ƒxxxxxxxxxxxx));
                     if (â˜ƒxxxxxxxxxxxxxxxxx >= â˜ƒ.minY) {
                        â˜ƒxxxxxxxxx = true;
                        â˜ƒxxxxxxx = Math.max(â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒ.minY, â˜ƒxxxxxxx);
                        if (â˜ƒxxxxxxxx) {
                           Vec3 â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx.getFlow(this.level, â˜ƒxxxxxxxxxxxx);
                           if (â˜ƒxxxxxxx < 0.4) {
                              â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx.scale(â˜ƒxxxxxxx);
                           }

                           â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxx.add(â˜ƒxxxxxxxxxxxxxxxxxx);
                           ++â˜ƒxxxxxxxxxxx;
                        }
                     }
                  }
               }
            }
         }

         if (â˜ƒxxxxxxxxxx.length() > 0.0) {
            if (â˜ƒxxxxxxxxxxx > 0) {
               â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxx.scale(1.0 / (double)â˜ƒxxxxxxxxxxx);
            }

            if (!(this instanceof Player)) {
               â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxx.normalize();
            }

            Vec3 â˜ƒxxxxxxxxxxxxx = this.getDeltaMovement();
            â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxx.scale(â˜ƒ * 1.0);
            double â˜ƒxxxxxxxxxxxxxx = 0.003;
            if (Math.abs(â˜ƒxxxxxxxxxxxxx.x) < 0.003 && Math.abs(â˜ƒxxxxxxxxxxxxx.z) < 0.003 && â˜ƒxxxxxxxxxx.length() < 0.0045000000000000005) {
               â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxx.normalize().scale(0.0045000000000000005);
            }

            this.setDeltaMovement(this.getDeltaMovement().add(â˜ƒxxxxxxxxxx));
         }

         this.fluidHeight.put(â˜ƒ, â˜ƒxxxxxxx);
         return â˜ƒxxxxxxxxx;
      }
   }

   public boolean touchingUnloadedChunk() {
      AABB â˜ƒ = this.getBoundingBox().inflate(1.0);
      int â˜ƒx = Mth.floor(â˜ƒ.minX);
      int â˜ƒxx = Mth.ceil(â˜ƒ.maxX);
      int â˜ƒxxx = Mth.floor(â˜ƒ.minZ);
      int â˜ƒxxxx = Mth.ceil(â˜ƒ.maxZ);
      return !this.level.hasChunksAt(â˜ƒx, â˜ƒxxx, â˜ƒxx, â˜ƒxxxx);
   }

   public double getFluidHeight(Tag<Fluid> var1) {
      return this.fluidHeight.getDouble(â˜ƒ);
   }

   public double getFluidJumpThreshold() {
      return (double)this.getEyeHeight() < 0.4 ? 0.0 : 0.4;
   }

   public final float getBbWidth() {
      return this.dimensions.width;
   }

   public final float getBbHeight() {
      return this.dimensions.height;
   }

   public abstract Packet<?> getAddEntityPacket();

   public EntityDimensions getDimensions(Pose var1) {
      return this.type.getDimensions();
   }

   public Vec3 position() {
      return this.position;
   }

   @Override
   public BlockPos blockPosition() {
      return this.blockPosition;
   }

   public BlockState getFeetBlockState() {
      return this.level.getBlockState(this.blockPosition());
   }

   public BlockPos eyeBlockPosition() {
      return new BlockPos(this.getEyePosition(1.0F));
   }

   public ChunkPos chunkPosition() {
      return new ChunkPos(this.blockPosition);
   }

   public Vec3 getDeltaMovement() {
      return this.deltaMovement;
   }

   public void setDeltaMovement(Vec3 var1) {
      this.deltaMovement = â˜ƒ;
   }

   public void setDeltaMovement(double var1, double var3, double var5) {
      this.setDeltaMovement(new Vec3(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public final int getBlockX() {
      return this.blockPosition.getX();
   }

   public final double getX() {
      return this.position.x;
   }

   public double getX(double var1) {
      return this.position.x + (double)this.getBbWidth() * â˜ƒ;
   }

   public double getRandomX(double var1) {
      return this.getX((2.0 * this.random.nextDouble() - 1.0) * â˜ƒ);
   }

   public final int getBlockY() {
      return this.blockPosition.getY();
   }

   public final double getY() {
      return this.position.y;
   }

   public double getY(double var1) {
      return this.position.y + (double)this.getBbHeight() * â˜ƒ;
   }

   public double getRandomY() {
      return this.getY(this.random.nextDouble());
   }

   public double getEyeY() {
      return this.position.y + (double)this.eyeHeight;
   }

   public final int getBlockZ() {
      return this.blockPosition.getZ();
   }

   public final double getZ() {
      return this.position.z;
   }

   public double getZ(double var1) {
      return this.position.z + (double)this.getBbWidth() * â˜ƒ;
   }

   public double getRandomZ(double var1) {
      return this.getZ((2.0 * this.random.nextDouble() - 1.0) * â˜ƒ);
   }

   public final void setPosRaw(double var1, double var3, double var5) {
      if (this.position.x != â˜ƒ || this.position.y != â˜ƒ || this.position.z != â˜ƒ) {
         this.position = new Vec3(â˜ƒ, â˜ƒ, â˜ƒ);
         int â˜ƒ = Mth.floor(â˜ƒ);
         int â˜ƒx = Mth.floor(â˜ƒ);
         int â˜ƒxx = Mth.floor(â˜ƒ);
         if (â˜ƒ != this.blockPosition.getX() || â˜ƒx != this.blockPosition.getY() || â˜ƒxx != this.blockPosition.getZ()) {
            this.blockPosition = new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
         }

         this.levelCallback.onMove();
         GameEventListenerRegistrar â˜ƒ = this.getGameEventListenerRegistrar();
         if (â˜ƒ != null) {
            â˜ƒ.onListenerMove(this.level);
         }
      }
   }

   public void checkDespawn() {
   }

   public Vec3 getRopeHoldPosition(float var1) {
      return this.getPosition(â˜ƒ).add(0.0, (double)this.eyeHeight * 0.7, 0.0);
   }

   public void recreateFromPacket(ClientboundAddEntityPacket var1) {
      int â˜ƒ = â˜ƒ.getId();
      double â˜ƒx = â˜ƒ.getX();
      double â˜ƒxx = â˜ƒ.getY();
      double â˜ƒxxx = â˜ƒ.getZ();
      this.setPacketCoordinates(â˜ƒx, â˜ƒxx, â˜ƒxxx);
      this.moveTo(â˜ƒx, â˜ƒxx, â˜ƒxxx);
      this.setXRot((float)(â˜ƒ.getxRot() * 360) / 256.0F);
      this.setYRot((float)(â˜ƒ.getyRot() * 360) / 256.0F);
      this.setId(â˜ƒ);
      this.setUUID(â˜ƒ.getUUID());
   }

   @Nullable
   public ItemStack getPickResult() {
      return null;
   }

   public void setIsInPowderSnow(boolean var1) {
      this.isInPowderSnow = â˜ƒ;
   }

   public boolean canFreeze() {
      return !EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES.contains(this.getType());
   }

   public float getYRot() {
      return this.yRot;
   }

   public void setYRot(float var1) {
      if (!Float.isFinite(â˜ƒ)) {
         Util.logAndPauseIfInIde("Invalid entity rotation: " + â˜ƒ + ", discarding.");
      } else {
         this.yRot = â˜ƒ;
      }
   }

   public float getXRot() {
      return this.xRot;
   }

   public void setXRot(float var1) {
      if (!Float.isFinite(â˜ƒ)) {
         Util.logAndPauseIfInIde("Invalid entity rotation: " + â˜ƒ + ", discarding.");
      } else {
         this.xRot = â˜ƒ;
      }
   }

   public final boolean isRemoved() {
      return this.removalReason != null;
   }

   @Nullable
   public Entity.RemovalReason getRemovalReason() {
      return this.removalReason;
   }

   @Override
   public final void setRemoved(Entity.RemovalReason var1) {
      if (this.removalReason == null) {
         this.removalReason = â˜ƒ;
      }

      if (this.removalReason.shouldDestroy()) {
         this.stopRiding();
      }

      this.getPassengers().forEach(Entity::stopRiding);
      this.levelCallback.onRemove(â˜ƒ);
   }

   protected void unsetRemoved() {
      this.removalReason = null;
   }

   @Override
   public void setLevelCallback(EntityInLevelCallback var1) {
      this.levelCallback = â˜ƒ;
   }

   @Override
   public boolean shouldBeSaved() {
      if (this.removalReason != null && !this.removalReason.shouldSave()) {
         return false;
      } else if (this.isPassenger()) {
         return false;
      } else {
         return !this.isVehicle() || !this.hasExactlyOnePlayerPassenger();
      }
   }

   @Override
   public boolean isAlwaysTicking() {
      return false;
   }

   public boolean mayInteract(Level var1, BlockPos var2) {
      return true;
   }

   @FunctionalInterface
   public interface MoveFunction {
      void accept(Entity var1, double var2, double var4, double var6);
   }

   public static enum MovementEmission {
      NONE(false, false),
      SOUNDS(true, false),
      EVENTS(false, true),
      ALL(true, true);

      final boolean sounds;
      final boolean events;

      private MovementEmission(boolean var3, boolean var4) {
         this.sounds = â˜ƒ;
         this.events = â˜ƒ;
      }

      public boolean emitsAnything() {
         return this.events || this.sounds;
      }

      public boolean emitsEvents() {
         return this.events;
      }

      public boolean emitsSounds() {
         return this.sounds;
      }
   }

   public static enum RemovalReason {
      KILLED(true, false),
      DISCARDED(true, false),
      UNLOADED_TO_CHUNK(false, true),
      UNLOADED_WITH_PLAYER(false, false),
      CHANGED_DIMENSION(false, false);

      private final boolean destroy;
      private final boolean save;

      private RemovalReason(boolean var3, boolean var4) {
         this.destroy = â˜ƒ;
         this.save = â˜ƒ;
      }

      public boolean shouldDestroy() {
         return this.destroy;
      }

      public boolean shouldSave() {
         return this.save;
      }
   }
}
