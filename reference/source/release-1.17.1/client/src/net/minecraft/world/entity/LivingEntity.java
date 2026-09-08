package net.minecraft.world.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

public abstract class LivingEntity extends Entity {
   private static final UUID SPEED_MODIFIER_SPRINTING_UUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final UUID SPEED_MODIFIER_SOUL_SPEED_UUID = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
   private static final UUID SPEED_MODIFIER_POWDER_SNOW_UUID = UUID.fromString("1eaf83ff-7207-4596-b37a-d7a07b3ec4ce");
   private static final AttributeModifier SPEED_MODIFIER_SPRINTING = new AttributeModifier(
      SPEED_MODIFIER_SPRINTING_UUID, "Sprinting speed boost", 0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL
   );
   public static final int HAND_SLOTS = 2;
   public static final int ARMOR_SLOTS = 4;
   public static final int EQUIPMENT_SLOT_OFFSET = 98;
   public static final int ARMOR_SLOT_OFFSET = 100;
   public static final int SWING_DURATION = 6;
   public static final int PLAYER_HURT_EXPERIENCE_TIME = 100;
   private static final int DAMAGE_SOURCE_TIMEOUT = 40;
   public static final double MIN_MOVEMENT_DISTANCE = 0.003;
   public static final double DEFAULT_BASE_GRAVITY = 0.08;
   public static final int DEATH_DURATION = 20;
   private static final int WAIT_TICKS_BEFORE_ITEM_USE_EFFECTS = 7;
   private static final int TICKS_PER_ELYTRA_FREE_FALL_EVENT = 10;
   private static final int FREE_FALL_EVENTS_PER_ELYTRA_BREAK = 2;
   public static final int USE_ITEM_INTERVAL = 4;
   private static final double MAX_LINE_OF_SIGHT_TEST_RANGE = 128.0;
   protected static final int LIVING_ENTITY_FLAG_IS_USING = 1;
   protected static final int LIVING_ENTITY_FLAG_OFF_HAND = 2;
   protected static final int LIVING_ENTITY_FLAG_SPIN_ATTACK = 4;
   protected static final EntityDataAccessor<Byte> DATA_LIVING_ENTITY_FLAGS = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Float> DATA_HEALTH_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DATA_EFFECT_COLOR_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_EFFECT_AMBIENCE_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> DATA_ARROW_COUNT_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_STINGER_COUNT_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Optional<BlockPos>> SLEEPING_POS_ID = SynchedEntityData.defineId(
      LivingEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   protected static final float DEFAULT_EYE_HEIGHT = 1.74F;
   protected static final EntityDimensions SLEEPING_DIMENSIONS = EntityDimensions.fixed(0.2F, 0.2F);
   public static final float EXTRA_RENDER_CULLING_SIZE_WITH_BIG_HAT = 0.5F;
   private final AttributeMap attributes;
   private final CombatTracker combatTracker = new CombatTracker(this);
   private final Map<MobEffect, MobEffectInstance> activeEffects = Maps.<MobEffect, MobEffectInstance>newHashMap();
   private final NonNullList<ItemStack> lastHandItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);
   private final NonNullList<ItemStack> lastArmorItemStacks = NonNullList.withSize(4, ItemStack.EMPTY);
   public boolean swinging;
   private boolean discardFriction = false;
   public InteractionHand swingingArm;
   public int swingTime;
   public int removeArrowTime;
   public int removeStingerTime;
   public int hurtTime;
   public int hurtDuration;
   public float hurtDir;
   public int deathTime;
   public float oAttackAnim;
   public float attackAnim;
   protected int attackStrengthTicker;
   public float animationSpeedOld;
   public float animationSpeed;
   public float animationPosition;
   public final int invulnerableDuration = 20;
   public final float timeOffs;
   public final float rotA;
   public float yBodyRot;
   public float yBodyRotO;
   public float yHeadRot;
   public float yHeadRotO;
   public float flyingSpeed = 0.02F;
   @Nullable
   protected Player lastHurtByPlayer;
   protected int lastHurtByPlayerTime;
   protected boolean dead;
   protected int noActionTime;
   protected float oRun;
   protected float run;
   protected float animStep;
   protected float animStepO;
   protected float rotOffs;
   protected int deathScore;
   protected float lastHurt;
   protected boolean jumping;
   public float xxa;
   public float yya;
   public float zza;
   protected int lerpSteps;
   protected double lerpX;
   protected double lerpY;
   protected double lerpZ;
   protected double lerpYRot;
   protected double lerpXRot;
   protected double lyHeadRot;
   protected int lerpHeadSteps;
   private boolean effectsDirty = true;
   @Nullable
   private LivingEntity lastHurtByMob;
   private int lastHurtByMobTimestamp;
   private LivingEntity lastHurtMob;
   private int lastHurtMobTimestamp;
   private float speed;
   private int noJumpDelay;
   private float absorptionAmount;
   protected ItemStack useItem = ItemStack.EMPTY;
   protected int useItemRemaining;
   protected int fallFlyTicks;
   private BlockPos lastPos;
   private Optional<BlockPos> lastClimbablePos = Optional.empty();
   @Nullable
   private DamageSource lastDamageSource;
   private long lastDamageStamp;
   protected int autoSpinAttackTicks;
   private float swimAmount;
   private float swimAmountO;
   protected Brain<?> brain;

   protected LivingEntity(EntityType<? extends LivingEntity> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.attributes = new AttributeMap(DefaultAttributes.getSupplier(â˜ƒ));
      this.setHealth(this.getMaxHealth());
      this.blocksBuilding = true;
      this.rotA = (float)((Math.random() + 1.0) * 0.01F);
      this.reapplyPosition();
      this.timeOffs = (float)Math.random() * 12398.0F;
      this.setYRot((float)(Math.random() * (float) (Math.PI * 2)));
      this.yHeadRot = this.getYRot();
      this.maxUpStep = 0.6F;
      NbtOps â˜ƒ = NbtOps.INSTANCE;
      this.brain = this.makeBrain(new Dynamic<>(â˜ƒ, â˜ƒ.createMap(ImmutableMap.of(â˜ƒ.createString("memories"), â˜ƒ.emptyMap()))));
   }

   public Brain<?> getBrain() {
      return this.brain;
   }

   protected Brain.Provider<?> brainProvider() {
      return Brain.provider(ImmutableList.of(), ImmutableList.of());
   }

   protected Brain<?> makeBrain(Dynamic<?> var1) {
      return this.brainProvider().makeBrain(â˜ƒ);
   }

   @Override
   public void kill() {
      this.hurt(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
   }

   public boolean canAttackType(EntityType<?> var1) {
      return true;
   }

   @Override
   protected void defineSynchedData() {
      this.entityData.define(DATA_LIVING_ENTITY_FLAGS, (byte)0);
      this.entityData.define(DATA_EFFECT_COLOR_ID, 0);
      this.entityData.define(DATA_EFFECT_AMBIENCE_ID, false);
      this.entityData.define(DATA_ARROW_COUNT_ID, 0);
      this.entityData.define(DATA_STINGER_COUNT_ID, 0);
      this.entityData.define(DATA_HEALTH_ID, 1.0F);
      this.entityData.define(SLEEPING_POS_ID, Optional.empty());
   }

   public static AttributeSupplier.Builder createLivingAttributes() {
      return AttributeSupplier.builder()
         .add(Attributes.MAX_HEALTH)
         .add(Attributes.KNOCKBACK_RESISTANCE)
         .add(Attributes.MOVEMENT_SPEED)
         .add(Attributes.ARMOR)
         .add(Attributes.ARMOR_TOUGHNESS);
   }

   @Override
   protected void checkFallDamage(double var1, boolean var3, BlockState var4, BlockPos var5) {
      if (!this.isInWater()) {
         this.updateInWaterStateAndDoWaterCurrentPushing();
      }

      if (!this.level.isClientSide && â˜ƒ && this.fallDistance > 0.0F) {
         this.removeSoulSpeed();
         this.tryAddSoulSpeed();
      }

      if (!this.level.isClientSide && this.fallDistance > 3.0F && â˜ƒ) {
         float â˜ƒ = (float)Mth.ceil(this.fallDistance - 3.0F);
         if (!â˜ƒ.isAir()) {
            double â˜ƒx = Math.min((double)(0.2F + â˜ƒ / 15.0F), 2.5);
            int â˜ƒxx = (int)(150.0 * â˜ƒx);
            ((ServerLevel)this.level)
               .sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, â˜ƒ), this.getX(), this.getY(), this.getZ(), â˜ƒxx, 0.0, 0.0, 0.0, 0.15F);
         }
      }

      super.checkFallDamage(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public boolean canBreatheUnderwater() {
      return this.getMobType() == MobType.UNDEAD;
   }

   public float getSwimAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.swimAmountO, this.swimAmount);
   }

   @Override
   public void baseTick() {
      this.oAttackAnim = this.attackAnim;
      if (this.firstTick) {
         this.getSleepingPos().ifPresent(this::setPosToBed);
      }

      if (this.canSpawnSoulSpeedParticle()) {
         this.spawnSoulSpeedParticle();
      }

      super.baseTick();
      this.level.getProfiler().push("livingEntityBaseTick");
      boolean â˜ƒ = this instanceof Player;
      if (this.isAlive()) {
         if (this.isInWall()) {
            this.hurt(DamageSource.IN_WALL, 1.0F);
         } else if (â˜ƒ && !this.level.getWorldBorder().isWithinBounds(this.getBoundingBox())) {
            double â˜ƒx = this.level.getWorldBorder().getDistanceToBorder(this) + this.level.getWorldBorder().getDamageSafeZone();
            if (â˜ƒx < 0.0) {
               double â˜ƒxx = this.level.getWorldBorder().getDamagePerBlock();
               if (â˜ƒxx > 0.0) {
                  this.hurt(DamageSource.IN_WALL, (float)Math.max(1, Mth.floor(-â˜ƒx * â˜ƒxx)));
               }
            }
         }
      }

      if (this.fireImmune() || this.level.isClientSide) {
         this.clearFire();
      }

      boolean â˜ƒ = â˜ƒ && ((Player)this).getAbilities().invulnerable;
      if (this.isAlive()) {
         if (this.isEyeInFluid(FluidTags.WATER) && !this.level.getBlockState(new BlockPos(this.getX(), this.getEyeY(), this.getZ())).is(Blocks.BUBBLE_COLUMN)) {
            if (!this.canBreatheUnderwater() && !MobEffectUtil.hasWaterBreathing(this) && !â˜ƒ) {
               this.setAirSupply(this.decreaseAirSupply(this.getAirSupply()));
               if (this.getAirSupply() == -20) {
                  this.setAirSupply(0);
                  Vec3 â˜ƒx = this.getDeltaMovement();

                  for(int â˜ƒxx = 0; â˜ƒxx < 8; ++â˜ƒxx) {
                     double â˜ƒxxx = this.random.nextDouble() - this.random.nextDouble();
                     double â˜ƒxxxx = this.random.nextDouble() - this.random.nextDouble();
                     double â˜ƒxxxxx = this.random.nextDouble() - this.random.nextDouble();
                     this.level.addParticle(ParticleTypes.BUBBLE, this.getX() + â˜ƒxxx, this.getY() + â˜ƒxxxx, this.getZ() + â˜ƒxxxxx, â˜ƒx.x, â˜ƒx.y, â˜ƒx.z);
                  }

                  this.hurt(DamageSource.DROWN, 2.0F);
               }
            }

            if (!this.level.isClientSide && this.isPassenger() && this.getVehicle() != null && !this.getVehicle().rideableUnderWater()) {
               this.stopRiding();
            }
         } else if (this.getAirSupply() < this.getMaxAirSupply()) {
            this.setAirSupply(this.increaseAirSupply(this.getAirSupply()));
         }

         if (!this.level.isClientSide) {
            BlockPos â˜ƒx = this.blockPosition();
            if (!Objects.equal(this.lastPos, â˜ƒx)) {
               this.lastPos = â˜ƒx;
               this.onChangedBlock(â˜ƒx);
            }
         }
      }

      if (this.isAlive() && (this.isInWaterRainOrBubble() || this.isInPowderSnow)) {
         if (!this.level.isClientSide && this.wasOnFire) {
            this.playEntityOnFireExtinguishedSound();
         }

         this.clearFire();
      }

      if (this.hurtTime > 0) {
         --this.hurtTime;
      }

      if (this.invulnerableTime > 0 && !(this instanceof ServerPlayer)) {
         --this.invulnerableTime;
      }

      if (this.isDeadOrDying()) {
         this.tickDeath();
      }

      if (this.lastHurtByPlayerTime > 0) {
         --this.lastHurtByPlayerTime;
      } else {
         this.lastHurtByPlayer = null;
      }

      if (this.lastHurtMob != null && !this.lastHurtMob.isAlive()) {
         this.lastHurtMob = null;
      }

      if (this.lastHurtByMob != null) {
         if (!this.lastHurtByMob.isAlive()) {
            this.setLastHurtByMob(null);
         } else if (this.tickCount - this.lastHurtByMobTimestamp > 100) {
            this.setLastHurtByMob(null);
         }
      }

      this.tickEffects();
      this.animStepO = this.animStep;
      this.yBodyRotO = this.yBodyRot;
      this.yHeadRotO = this.yHeadRot;
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
      this.level.getProfiler().pop();
   }

   public boolean canSpawnSoulSpeedParticle() {
      return this.tickCount % 5 == 0
         && this.getDeltaMovement().x != 0.0
         && this.getDeltaMovement().z != 0.0
         && !this.isSpectator()
         && EnchantmentHelper.hasSoulSpeed(this)
         && this.onSoulSpeedBlock();
   }

   protected void spawnSoulSpeedParticle() {
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.level
         .addParticle(
            ParticleTypes.SOUL,
            this.getX() + (this.random.nextDouble() - 0.5) * (double)this.getBbWidth(),
            this.getY() + 0.1,
            this.getZ() + (this.random.nextDouble() - 0.5) * (double)this.getBbWidth(),
            â˜ƒ.x * -0.2,
            0.1,
            â˜ƒ.z * -0.2
         );
      float â˜ƒx = this.random.nextFloat() * 0.4F + this.random.nextFloat() > 0.9F ? 0.6F : 0.0F;
      this.playSound(SoundEvents.SOUL_ESCAPE, â˜ƒx, 0.6F + this.random.nextFloat() * 0.4F);
   }

   protected boolean onSoulSpeedBlock() {
      return this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SOUL_SPEED_BLOCKS);
   }

   @Override
   protected float getBlockSpeedFactor() {
      return this.onSoulSpeedBlock() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, this) > 0 ? 1.0F : super.getBlockSpeedFactor();
   }

   protected boolean shouldRemoveSoulSpeed(BlockState var1) {
      return !â˜ƒ.isAir() || this.isFallFlying();
   }

   protected void removeSoulSpeed() {
      AttributeInstance â˜ƒ = this.getAttribute(Attributes.MOVEMENT_SPEED);
      if (â˜ƒ != null) {
         if (â˜ƒ.getModifier(SPEED_MODIFIER_SOUL_SPEED_UUID) != null) {
            â˜ƒ.removeModifier(SPEED_MODIFIER_SOUL_SPEED_UUID);
         }
      }
   }

   protected void tryAddSoulSpeed() {
      if (!this.getBlockStateOn().isAir()) {
         int â˜ƒ = EnchantmentHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, this);
         if (â˜ƒ > 0 && this.onSoulSpeedBlock()) {
            AttributeInstance â˜ƒx = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (â˜ƒx == null) {
               return;
            }

            â˜ƒx.addTransientModifier(
               new AttributeModifier(
                  SPEED_MODIFIER_SOUL_SPEED_UUID, "Soul speed boost", (double)(0.03F * (1.0F + (float)â˜ƒ * 0.35F)), AttributeModifier.Operation.ADDITION
               )
            );
            if (this.getRandom().nextFloat() < 0.04F) {
               ItemStack â˜ƒx = this.getItemBySlot(EquipmentSlot.FEET);
               â˜ƒx.hurtAndBreak(1, this, var0 -> var0.broadcastBreakEvent(EquipmentSlot.FEET));
            }
         }
      }
   }

   protected void removeFrost() {
      AttributeInstance â˜ƒ = this.getAttribute(Attributes.MOVEMENT_SPEED);
      if (â˜ƒ != null) {
         if (â˜ƒ.getModifier(SPEED_MODIFIER_POWDER_SNOW_UUID) != null) {
            â˜ƒ.removeModifier(SPEED_MODIFIER_POWDER_SNOW_UUID);
         }
      }
   }

   protected void tryAddFrost() {
      if (!this.getBlockStateOn().isAir()) {
         int â˜ƒ = this.getTicksFrozen();
         if (â˜ƒ > 0) {
            AttributeInstance â˜ƒx = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (â˜ƒx == null) {
               return;
            }

            float â˜ƒx = -0.05F * this.getPercentFrozen();
            â˜ƒx.addTransientModifier(
               new AttributeModifier(SPEED_MODIFIER_POWDER_SNOW_UUID, "Powder snow slow", (double)â˜ƒx, AttributeModifier.Operation.ADDITION)
            );
         }
      }
   }

   protected void onChangedBlock(BlockPos var1) {
      int â˜ƒ = EnchantmentHelper.getEnchantmentLevel(Enchantments.FROST_WALKER, this);
      if (â˜ƒ > 0) {
         FrostWalkerEnchantment.onEntityMoved(this, this.level, â˜ƒ, â˜ƒ);
      }

      if (this.shouldRemoveSoulSpeed(this.getBlockStateOn())) {
         this.removeSoulSpeed();
      }

      this.tryAddSoulSpeed();
   }

   public boolean isBaby() {
      return false;
   }

   public float getScale() {
      return this.isBaby() ? 0.5F : 1.0F;
   }

   protected boolean isAffectedByFluids() {
      return true;
   }

   @Override
   public boolean rideableUnderWater() {
      return false;
   }

   protected void tickDeath() {
      ++this.deathTime;
      if (this.deathTime == 20 && !this.level.isClientSide()) {
         this.level.broadcastEntityEvent(this, (byte)60);
         this.remove(Entity.RemovalReason.KILLED);
      }
   }

   protected boolean shouldDropExperience() {
      return !this.isBaby();
   }

   protected boolean shouldDropLoot() {
      return !this.isBaby();
   }

   protected int decreaseAirSupply(int var1) {
      int â˜ƒ = EnchantmentHelper.getRespiration(this);
      return â˜ƒ > 0 && this.random.nextInt(â˜ƒ + 1) > 0 ? â˜ƒ : â˜ƒ - 1;
   }

   protected int increaseAirSupply(int var1) {
      return Math.min(â˜ƒ + 4, this.getMaxAirSupply());
   }

   protected int getExperienceReward(Player var1) {
      return 0;
   }

   protected boolean isAlwaysExperienceDropper() {
      return false;
   }

   public Random getRandom() {
      return this.random;
   }

   @Nullable
   public LivingEntity getLastHurtByMob() {
      return this.lastHurtByMob;
   }

   public int getLastHurtByMobTimestamp() {
      return this.lastHurtByMobTimestamp;
   }

   public void setLastHurtByPlayer(@Nullable Player var1) {
      this.lastHurtByPlayer = â˜ƒ;
      this.lastHurtByPlayerTime = this.tickCount;
   }

   public void setLastHurtByMob(@Nullable LivingEntity var1) {
      this.lastHurtByMob = â˜ƒ;
      this.lastHurtByMobTimestamp = this.tickCount;
   }

   @Nullable
   public LivingEntity getLastHurtMob() {
      return this.lastHurtMob;
   }

   public int getLastHurtMobTimestamp() {
      return this.lastHurtMobTimestamp;
   }

   public void setLastHurtMob(Entity var1) {
      if (â˜ƒ instanceof LivingEntity) {
         this.lastHurtMob = (LivingEntity)â˜ƒ;
      } else {
         this.lastHurtMob = null;
      }

      this.lastHurtMobTimestamp = this.tickCount;
   }

   public int getNoActionTime() {
      return this.noActionTime;
   }

   public void setNoActionTime(int var1) {
      this.noActionTime = â˜ƒ;
   }

   public boolean shouldDiscardFriction() {
      return this.discardFriction;
   }

   public void setDiscardFriction(boolean var1) {
      this.discardFriction = â˜ƒ;
   }

   protected void equipEventAndSound(ItemStack var1) {
      SoundEvent â˜ƒ = â˜ƒ.getEquipSound();
      if (!â˜ƒ.isEmpty() && â˜ƒ != null && !this.isSpectator()) {
         this.gameEvent(GameEvent.EQUIP);
         this.playSound(â˜ƒ, 1.0F, 1.0F);
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putFloat("Health", this.getHealth());
      â˜ƒ.putShort("HurtTime", (short)this.hurtTime);
      â˜ƒ.putInt("HurtByTimestamp", this.lastHurtByMobTimestamp);
      â˜ƒ.putShort("DeathTime", (short)this.deathTime);
      â˜ƒ.putFloat("AbsorptionAmount", this.getAbsorptionAmount());
      â˜ƒ.put("Attributes", this.getAttributes().save());
      if (!this.activeEffects.isEmpty()) {
         ListTag â˜ƒ = new ListTag();

         for(MobEffectInstance â˜ƒx : this.activeEffects.values()) {
            â˜ƒ.add(â˜ƒx.save(new CompoundTag()));
         }

         â˜ƒ.put("ActiveEffects", â˜ƒ);
      }

      â˜ƒ.putBoolean("FallFlying", this.isFallFlying());
      this.getSleepingPos().ifPresent(var1x -> {
         â˜ƒ.putInt("SleepingX", var1x.getX());
         â˜ƒ.putInt("SleepingY", var1x.getY());
         â˜ƒ.putInt("SleepingZ", var1x.getZ());
      });
      DataResult<Tag> â˜ƒ = this.brain.serializeStart(NbtOps.INSTANCE);
      â˜ƒ.resultOrPartial(LOGGER::error).ifPresent(var1x -> â˜ƒ.put("Brain", var1x));
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      this.setAbsorptionAmount(â˜ƒ.getFloat("AbsorptionAmount"));
      if (â˜ƒ.contains("Attributes", 9) && this.level != null && !this.level.isClientSide) {
         this.getAttributes().load(â˜ƒ.getList("Attributes", 10));
      }

      if (â˜ƒ.contains("ActiveEffects", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("ActiveEffects", 10);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
            MobEffectInstance â˜ƒxxx = MobEffectInstance.load(â˜ƒxx);
            if (â˜ƒxxx != null) {
               this.activeEffects.put(â˜ƒxxx.getEffect(), â˜ƒxxx);
            }
         }
      }

      if (â˜ƒ.contains("Health", 99)) {
         this.setHealth(â˜ƒ.getFloat("Health"));
      }

      this.hurtTime = â˜ƒ.getShort("HurtTime");
      this.deathTime = â˜ƒ.getShort("DeathTime");
      this.lastHurtByMobTimestamp = â˜ƒ.getInt("HurtByTimestamp");
      if (â˜ƒ.contains("Team", 8)) {
         String â˜ƒ = â˜ƒ.getString("Team");
         PlayerTeam â˜ƒx = this.level.getScoreboard().getPlayerTeam(â˜ƒ);
         boolean â˜ƒxx = â˜ƒx != null && this.level.getScoreboard().addPlayerToTeam(this.getStringUUID(), â˜ƒx);
         if (!â˜ƒxx) {
            LOGGER.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", â˜ƒ);
         }
      }

      if (â˜ƒ.getBoolean("FallFlying")) {
         this.setSharedFlag(7, true);
      }

      if (â˜ƒ.contains("SleepingX", 99) && â˜ƒ.contains("SleepingY", 99) && â˜ƒ.contains("SleepingZ", 99)) {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ.getInt("SleepingX"), â˜ƒ.getInt("SleepingY"), â˜ƒ.getInt("SleepingZ"));
         this.setSleepingPos(â˜ƒ);
         this.entityData.set(DATA_POSE, Pose.SLEEPING);
         if (!this.firstTick) {
            this.setPosToBed(â˜ƒ);
         }
      }

      if (â˜ƒ.contains("Brain", 10)) {
         this.brain = this.makeBrain(new Dynamic<>(NbtOps.INSTANCE, â˜ƒ.get("Brain")));
      }
   }

   protected void tickEffects() {
      Iterator<MobEffect> â˜ƒ = this.activeEffects.keySet().iterator();

      try {
         while(â˜ƒ.hasNext()) {
            MobEffect â˜ƒx = (MobEffect)â˜ƒ.next();
            MobEffectInstance â˜ƒxx = (MobEffectInstance)this.activeEffects.get(â˜ƒx);
            if (!â˜ƒxx.tick(this, () -> this.onEffectUpdated(â˜ƒ, true, null))) {
               if (!this.level.isClientSide) {
                  â˜ƒ.remove();
                  this.onEffectRemoved(â˜ƒxx);
               }
            } else if (â˜ƒxx.getDuration() % 600 == 0) {
               this.onEffectUpdated(â˜ƒxx, false, null);
            }
         }
      } catch (ConcurrentModificationException var11) {
      }

      if (this.effectsDirty) {
         if (!this.level.isClientSide) {
            this.updateInvisibilityStatus();
            this.updateGlowingStatus();
         }

         this.effectsDirty = false;
      }

      int â˜ƒx = this.entityData.get(DATA_EFFECT_COLOR_ID);
      boolean â˜ƒxx = this.entityData.get(DATA_EFFECT_AMBIENCE_ID);
      if (â˜ƒx > 0) {
         boolean â˜ƒxxx;
         if (this.isInvisible()) {
            â˜ƒxxx = this.random.nextInt(15) == 0;
         } else {
            â˜ƒxxx = this.random.nextBoolean();
         }

         if (â˜ƒxx) {
            â˜ƒxxx &= this.random.nextInt(5) == 0;
         }

         if (â˜ƒxxx && â˜ƒx > 0) {
            double â˜ƒxxx = (double)(â˜ƒx >> 16 & 0xFF) / 255.0;
            double â˜ƒxxxx = (double)(â˜ƒx >> 8 & 0xFF) / 255.0;
            double â˜ƒxxxxx = (double)(â˜ƒx >> 0 & 0xFF) / 255.0;
            this.level
               .addParticle(
                  â˜ƒxx ? ParticleTypes.AMBIENT_ENTITY_EFFECT : ParticleTypes.ENTITY_EFFECT,
                  this.getRandomX(0.5),
                  this.getRandomY(),
                  this.getRandomZ(0.5),
                  â˜ƒxxx,
                  â˜ƒxxxx,
                  â˜ƒxxxxx
               );
         }
      }
   }

   protected void updateInvisibilityStatus() {
      if (this.activeEffects.isEmpty()) {
         this.removeEffectParticles();
         this.setInvisible(false);
      } else {
         Collection<MobEffectInstance> â˜ƒ = this.activeEffects.values();
         this.entityData.set(DATA_EFFECT_AMBIENCE_ID, areAllEffectsAmbient(â˜ƒ));
         this.entityData.set(DATA_EFFECT_COLOR_ID, PotionUtils.getColor(â˜ƒ));
         this.setInvisible(this.hasEffect(MobEffects.INVISIBILITY));
      }
   }

   private void updateGlowingStatus() {
      boolean â˜ƒ = this.isCurrentlyGlowing();
      if (this.getSharedFlag(6) != â˜ƒ) {
         this.setSharedFlag(6, â˜ƒ);
      }
   }

   public double getVisibilityPercent(@Nullable Entity var1) {
      double â˜ƒ = 1.0;
      if (this.isDiscrete()) {
         â˜ƒ *= 0.8;
      }

      if (this.isInvisible()) {
         float â˜ƒ = this.getArmorCoverPercentage();
         if (â˜ƒ < 0.1F) {
            â˜ƒ = 0.1F;
         }

         â˜ƒ *= 0.7 * (double)â˜ƒ;
      }

      if (â˜ƒ != null) {
         ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.HEAD);
         EntityType<?> â˜ƒx = â˜ƒ.getType();
         if (â˜ƒx == EntityType.SKELETON && â˜ƒ.is(Items.SKELETON_SKULL)
            || â˜ƒx == EntityType.ZOMBIE && â˜ƒ.is(Items.ZOMBIE_HEAD)
            || â˜ƒx == EntityType.CREEPER && â˜ƒ.is(Items.CREEPER_HEAD)) {
            â˜ƒ *= 0.5;
         }
      }

      return â˜ƒ;
   }

   public boolean canAttack(LivingEntity var1) {
      return â˜ƒ instanceof Player && this.level.getDifficulty() == Difficulty.PEACEFUL ? false : â˜ƒ.canBeSeenAsEnemy();
   }

   public boolean canAttack(LivingEntity var1, TargetingConditions var2) {
      return â˜ƒ.test(this, â˜ƒ);
   }

   public boolean canBeSeenAsEnemy() {
      return !this.isInvulnerable() && this.canBeSeenByAnyone();
   }

   public boolean canBeSeenByAnyone() {
      return !this.isSpectator() && this.isAlive();
   }

   public static boolean areAllEffectsAmbient(Collection<MobEffectInstance> var0) {
      for(MobEffectInstance â˜ƒ : â˜ƒ) {
         if (!â˜ƒ.isAmbient()) {
            return false;
         }
      }

      return true;
   }

   protected void removeEffectParticles() {
      this.entityData.set(DATA_EFFECT_AMBIENCE_ID, false);
      this.entityData.set(DATA_EFFECT_COLOR_ID, 0);
   }

   public boolean removeAllEffects() {
      if (this.level.isClientSide) {
         return false;
      } else {
         Iterator<MobEffectInstance> â˜ƒ = this.activeEffects.values().iterator();

         boolean â˜ƒ;
         for(â˜ƒ = false; â˜ƒ.hasNext(); â˜ƒ = true) {
            this.onEffectRemoved((MobEffectInstance)â˜ƒ.next());
            â˜ƒ.remove();
         }

         return â˜ƒ;
      }
   }

   public Collection<MobEffectInstance> getActiveEffects() {
      return this.activeEffects.values();
   }

   public Map<MobEffect, MobEffectInstance> getActiveEffectsMap() {
      return this.activeEffects;
   }

   public boolean hasEffect(MobEffect var1) {
      return this.activeEffects.containsKey(â˜ƒ);
   }

   @Nullable
   public MobEffectInstance getEffect(MobEffect var1) {
      return (MobEffectInstance)this.activeEffects.get(â˜ƒ);
   }

   public final boolean addEffect(MobEffectInstance var1) {
      return this.addEffect(â˜ƒ, null);
   }

   public boolean addEffect(MobEffectInstance var1, @Nullable Entity var2) {
      if (!this.canBeAffected(â˜ƒ)) {
         return false;
      } else {
         MobEffectInstance â˜ƒ = (MobEffectInstance)this.activeEffects.get(â˜ƒ.getEffect());
         if (â˜ƒ == null) {
            this.activeEffects.put(â˜ƒ.getEffect(), â˜ƒ);
            this.onEffectAdded(â˜ƒ, â˜ƒ);
            return true;
         } else if (â˜ƒ.update(â˜ƒ)) {
            this.onEffectUpdated(â˜ƒ, true, â˜ƒ);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean canBeAffected(MobEffectInstance var1) {
      if (this.getMobType() == MobType.UNDEAD) {
         MobEffect â˜ƒ = â˜ƒ.getEffect();
         if (â˜ƒ == MobEffects.REGENERATION || â˜ƒ == MobEffects.POISON) {
            return false;
         }
      }

      return true;
   }

   public void forceAddEffect(MobEffectInstance var1, @Nullable Entity var2) {
      if (this.canBeAffected(â˜ƒ)) {
         MobEffectInstance â˜ƒ = (MobEffectInstance)this.activeEffects.put(â˜ƒ.getEffect(), â˜ƒ);
         if (â˜ƒ == null) {
            this.onEffectAdded(â˜ƒ, â˜ƒ);
         } else {
            this.onEffectUpdated(â˜ƒ, true, â˜ƒ);
         }
      }
   }

   public boolean isInvertedHealAndHarm() {
      return this.getMobType() == MobType.UNDEAD;
   }

   @Nullable
   public MobEffectInstance removeEffectNoUpdate(@Nullable MobEffect var1) {
      return (MobEffectInstance)this.activeEffects.remove(â˜ƒ);
   }

   public boolean removeEffect(MobEffect var1) {
      MobEffectInstance â˜ƒ = this.removeEffectNoUpdate(â˜ƒ);
      if (â˜ƒ != null) {
         this.onEffectRemoved(â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   protected void onEffectAdded(MobEffectInstance var1, @Nullable Entity var2) {
      this.effectsDirty = true;
      if (!this.level.isClientSide) {
         â˜ƒ.getEffect().addAttributeModifiers(this, this.getAttributes(), â˜ƒ.getAmplifier());
      }
   }

   protected void onEffectUpdated(MobEffectInstance var1, boolean var2, @Nullable Entity var3) {
      this.effectsDirty = true;
      if (â˜ƒ && !this.level.isClientSide) {
         MobEffect â˜ƒ = â˜ƒ.getEffect();
         â˜ƒ.removeAttributeModifiers(this, this.getAttributes(), â˜ƒ.getAmplifier());
         â˜ƒ.addAttributeModifiers(this, this.getAttributes(), â˜ƒ.getAmplifier());
      }
   }

   protected void onEffectRemoved(MobEffectInstance var1) {
      this.effectsDirty = true;
      if (!this.level.isClientSide) {
         â˜ƒ.getEffect().removeAttributeModifiers(this, this.getAttributes(), â˜ƒ.getAmplifier());
      }
   }

   public void heal(float var1) {
      float â˜ƒ = this.getHealth();
      if (â˜ƒ > 0.0F) {
         this.setHealth(â˜ƒ + â˜ƒ);
      }
   }

   public float getHealth() {
      return this.entityData.get(DATA_HEALTH_ID);
   }

   public void setHealth(float var1) {
      this.entityData.set(DATA_HEALTH_ID, Mth.clamp(â˜ƒ, 0.0F, this.getMaxHealth()));
   }

   public boolean isDeadOrDying() {
      return this.getHealth() <= 0.0F;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (this.level.isClientSide) {
         return false;
      } else if (this.isDeadOrDying()) {
         return false;
      } else if (â˜ƒ.isFire() && this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
         return false;
      } else {
         if (this.isSleeping() && !this.level.isClientSide) {
            this.stopSleeping();
         }

         this.noActionTime = 0;
         float â˜ƒ = â˜ƒ;
         boolean â˜ƒx = false;
         float â˜ƒxx = 0.0F;
         if (â˜ƒ > 0.0F && this.isDamageSourceBlocked(â˜ƒ)) {
            this.hurtCurrentlyUsedShield(â˜ƒ);
            â˜ƒxx = â˜ƒ;
            â˜ƒ = 0.0F;
            if (!â˜ƒ.isProjectile()) {
               Entity â˜ƒxxx = â˜ƒ.getDirectEntity();
               if (â˜ƒxxx instanceof LivingEntity) {
                  this.blockUsingShield((LivingEntity)â˜ƒxxx);
               }
            }

            â˜ƒx = true;
         }

         this.animationSpeed = 1.5F;
         boolean â˜ƒ = true;
         if ((float)this.invulnerableTime > 10.0F) {
            if (â˜ƒ <= this.lastHurt) {
               return false;
            }

            this.actuallyHurt(â˜ƒ, â˜ƒ - this.lastHurt);
            this.lastHurt = â˜ƒ;
            â˜ƒ = false;
         } else {
            this.lastHurt = â˜ƒ;
            this.invulnerableTime = 20;
            this.actuallyHurt(â˜ƒ, â˜ƒ);
            this.hurtDuration = 10;
            this.hurtTime = this.hurtDuration;
         }

         if (â˜ƒ.isDamageHelmet() && !this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            this.hurtHelmet(â˜ƒ, â˜ƒ);
            â˜ƒ *= 0.75F;
         }

         this.hurtDir = 0.0F;
         Entity â˜ƒ = â˜ƒ.getEntity();
         if (â˜ƒ != null) {
            if (â˜ƒ instanceof LivingEntity && !â˜ƒ.isNoAggro()) {
               this.setLastHurtByMob((LivingEntity)â˜ƒ);
            }

            if (â˜ƒ instanceof Player) {
               this.lastHurtByPlayerTime = 100;
               this.lastHurtByPlayer = (Player)â˜ƒ;
            } else if (â˜ƒ instanceof Wolf â˜ƒx && â˜ƒx.isTame()) {
               this.lastHurtByPlayerTime = 100;
               LivingEntity â˜ƒxx = â˜ƒx.getOwner();
               if (â˜ƒxx != null && â˜ƒxx.getType() == EntityType.PLAYER) {
                  this.lastHurtByPlayer = (Player)â˜ƒxx;
               } else {
                  this.lastHurtByPlayer = null;
               }
            }
         }

         if (â˜ƒ) {
            if (â˜ƒx) {
               this.level.broadcastEntityEvent(this, (byte)29);
            } else if (â˜ƒ instanceof EntityDamageSource && ((EntityDamageSource)â˜ƒ).isThorns()) {
               this.level.broadcastEntityEvent(this, (byte)33);
            } else {
               byte â˜ƒ;
               if (â˜ƒ == DamageSource.DROWN) {
                  â˜ƒ = 36;
               } else if (â˜ƒ.isFire()) {
                  â˜ƒ = 37;
               } else if (â˜ƒ == DamageSource.SWEET_BERRY_BUSH) {
                  â˜ƒ = 44;
               } else if (â˜ƒ == DamageSource.FREEZE) {
                  â˜ƒ = 57;
               } else {
                  â˜ƒ = 2;
               }

               this.level.broadcastEntityEvent(this, â˜ƒ);
            }

            if (â˜ƒ != DamageSource.DROWN && (!â˜ƒx || â˜ƒ > 0.0F)) {
               this.markHurt();
            }

            if (â˜ƒ != null) {
               double â˜ƒ = â˜ƒ.getX() - this.getX();

               double â˜ƒ;
               for(â˜ƒ = â˜ƒ.getZ() - this.getZ(); â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ < 1.0E-4; â˜ƒ = (Math.random() - Math.random()) * 0.01) {
                  â˜ƒ = (Math.random() - Math.random()) * 0.01;
               }

               this.hurtDir = (float)(Mth.atan2(â˜ƒ, â˜ƒ) * 180.0F / (float)Math.PI - (double)this.getYRot());
               this.knockback(0.4F, â˜ƒ, â˜ƒ);
            } else {
               this.hurtDir = (float)((int)(Math.random() * 2.0) * 180);
            }
         }

         if (this.isDeadOrDying()) {
            if (!this.checkTotemDeathProtection(â˜ƒ)) {
               SoundEvent â˜ƒ = this.getDeathSound();
               if (â˜ƒ && â˜ƒ != null) {
                  this.playSound(â˜ƒ, this.getSoundVolume(), this.getVoicePitch());
               }

               this.die(â˜ƒ);
            }
         } else if (â˜ƒ) {
            this.playHurtSound(â˜ƒ);
         }

         boolean â˜ƒ = !â˜ƒx || â˜ƒ > 0.0F;
         if (â˜ƒ) {
            this.lastDamageSource = â˜ƒ;
            this.lastDamageStamp = this.level.getGameTime();
         }

         if (this instanceof ServerPlayer) {
            CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayer)this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            if (â˜ƒxx > 0.0F && â˜ƒxx < 3.4028235E37F) {
               ((ServerPlayer)this).awardStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(â˜ƒxx * 10.0F));
            }
         }

         if (â˜ƒ instanceof ServerPlayer) {
            CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayer)â˜ƒ, this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
         }

         return â˜ƒ;
      }
   }

   protected void blockUsingShield(LivingEntity var1) {
      â˜ƒ.blockedByShield(this);
   }

   protected void blockedByShield(LivingEntity var1) {
      â˜ƒ.knockback(0.5, â˜ƒ.getX() - this.getX(), â˜ƒ.getZ() - this.getZ());
   }

   private boolean checkTotemDeathProtection(DamageSource var1) {
      if (â˜ƒ.isBypassInvul()) {
         return false;
      } else {
         ItemStack â˜ƒ = null;

         for(InteractionHand â˜ƒx : InteractionHand.values()) {
            ItemStack â˜ƒxx = this.getItemInHand(â˜ƒx);
            if (â˜ƒxx.is(Items.TOTEM_OF_UNDYING)) {
               â˜ƒ = â˜ƒxx.copy();
               â˜ƒxx.shrink(1);
               break;
            }
         }

         if (â˜ƒ != null) {
            if (this instanceof ServerPlayer â˜ƒx) {
               â˜ƒx.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
               CriteriaTriggers.USED_TOTEM.trigger(â˜ƒx, â˜ƒ);
            }

            this.setHealth(1.0F);
            this.removeAllEffects();
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
            this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
            this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
            this.level.broadcastEntityEvent(this, (byte)35);
         }

         return â˜ƒ != null;
      }
   }

   @Nullable
   public DamageSource getLastDamageSource() {
      if (this.level.getGameTime() - this.lastDamageStamp > 40L) {
         this.lastDamageSource = null;
      }

      return this.lastDamageSource;
   }

   protected void playHurtSound(DamageSource var1) {
      SoundEvent â˜ƒ = this.getHurtSound(â˜ƒ);
      if (â˜ƒ != null) {
         this.playSound(â˜ƒ, this.getSoundVolume(), this.getVoicePitch());
      }
   }

   public boolean isDamageSourceBlocked(DamageSource var1) {
      Entity â˜ƒx = â˜ƒ.getDirectEntity();
      boolean â˜ƒxx = false;
      if (â˜ƒx instanceof AbstractArrow â˜ƒ && â˜ƒ.getPierceLevel() > 0) {
         â˜ƒxx = true;
      }

      if (!â˜ƒ.isBypassArmor() && this.isBlocking() && !â˜ƒxx) {
         Vec3 â˜ƒ = â˜ƒ.getSourcePosition();
         if (â˜ƒ != null) {
            Vec3 â˜ƒx = this.getViewVector(1.0F);
            Vec3 â˜ƒxx = â˜ƒ.vectorTo(this.position()).normalize();
            â˜ƒxx = new Vec3(â˜ƒxx.x, 0.0, â˜ƒxx.z);
            if (â˜ƒxx.dot(â˜ƒx) < 0.0) {
               return true;
            }
         }
      }

      return false;
   }

   private void breakItem(ItemStack var1) {
      if (!â˜ƒ.isEmpty()) {
         if (!this.isSilent()) {
            this.level
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  SoundEvents.ITEM_BREAK,
                  this.getSoundSource(),
                  0.8F,
                  0.8F + this.level.random.nextFloat() * 0.4F,
                  false
               );
         }

         this.spawnItemParticles(â˜ƒ, 5);
      }
   }

   public void die(DamageSource var1) {
      if (!this.isRemoved() && !this.dead) {
         Entity â˜ƒ = â˜ƒ.getEntity();
         LivingEntity â˜ƒx = this.getKillCredit();
         if (this.deathScore >= 0 && â˜ƒx != null) {
            â˜ƒx.awardKillScore(this, this.deathScore, â˜ƒ);
         }

         if (this.isSleeping()) {
            this.stopSleeping();
         }

         if (!this.level.isClientSide && this.hasCustomName()) {
            LOGGER.info("Named entity {} died: {}", this, this.getCombatTracker().getDeathMessage().getString());
         }

         this.dead = true;
         this.getCombatTracker().recheckStatus();
         if (this.level instanceof ServerLevel) {
            if (â˜ƒ != null) {
               â˜ƒ.killed((ServerLevel)this.level, this);
            }

            this.dropAllDeathLoot(â˜ƒ);
            this.createWitherRose(â˜ƒx);
         }

         this.level.broadcastEntityEvent(this, (byte)3);
         this.setPose(Pose.DYING);
      }
   }

   protected void createWitherRose(@Nullable LivingEntity var1) {
      if (!this.level.isClientSide) {
         boolean â˜ƒ = false;
         if (â˜ƒ instanceof WitherBoss) {
            if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
               BlockPos â˜ƒx = this.blockPosition();
               BlockState â˜ƒxx = Blocks.WITHER_ROSE.defaultBlockState();
               if (this.level.getBlockState(â˜ƒx).isAir() && â˜ƒxx.canSurvive(this.level, â˜ƒx)) {
                  this.level.setBlock(â˜ƒx, â˜ƒxx, 3);
                  â˜ƒ = true;
               }
            }

            if (!â˜ƒ) {
               ItemEntity â˜ƒx = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.WITHER_ROSE));
               this.level.addFreshEntity(â˜ƒx);
            }
         }
      }
   }

   protected void dropAllDeathLoot(DamageSource var1) {
      Entity â˜ƒx = â˜ƒ.getEntity();
      int â˜ƒ;
      if (â˜ƒx instanceof Player) {
         â˜ƒ = EnchantmentHelper.getMobLooting((LivingEntity)â˜ƒx);
      } else {
         â˜ƒ = 0;
      }

      boolean â˜ƒ = this.lastHurtByPlayerTime > 0;
      if (this.shouldDropLoot() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
         this.dropFromLootTable(â˜ƒ, â˜ƒ);
         this.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      this.dropEquipment();
      this.dropExperience();
   }

   protected void dropEquipment() {
   }

   protected void dropExperience() {
      if (this.level instanceof ServerLevel
         && (
            this.isAlwaysExperienceDropper()
               || this.lastHurtByPlayerTime > 0 && this.shouldDropExperience() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)
         )) {
         ExperienceOrb.award((ServerLevel)this.level, this.position(), this.getExperienceReward(this.lastHurtByPlayer));
      }
   }

   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
   }

   public ResourceLocation getLootTable() {
      return this.getType().getDefaultLootTable();
   }

   protected void dropFromLootTable(DamageSource var1, boolean var2) {
      ResourceLocation â˜ƒ = this.getLootTable();
      LootTable â˜ƒx = this.level.getServer().getLootTables().get(â˜ƒ);
      LootContext.Builder â˜ƒxx = this.createLootContext(â˜ƒ, â˜ƒ);
      â˜ƒx.getRandomItems(â˜ƒxx.create(LootContextParamSets.ENTITY), this::spawnAtLocation);
   }

   protected LootContext.Builder createLootContext(boolean var1, DamageSource var2) {
      LootContext.Builder â˜ƒ = new LootContext.Builder((ServerLevel)this.level)
         .withRandom(this.random)
         .withParameter(LootContextParams.THIS_ENTITY, this)
         .withParameter(LootContextParams.ORIGIN, this.position())
         .withParameter(LootContextParams.DAMAGE_SOURCE, â˜ƒ)
         .withOptionalParameter(LootContextParams.KILLER_ENTITY, â˜ƒ.getEntity())
         .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, â˜ƒ.getDirectEntity());
      if (â˜ƒ && this.lastHurtByPlayer != null) {
         â˜ƒ = â˜ƒ.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
      }

      return â˜ƒ;
   }

   public void knockback(double var1, double var3, double var5) {
      â˜ƒ *= 1.0 - this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
      if (!(â˜ƒ <= 0.0)) {
         this.hasImpulse = true;
         Vec3 â˜ƒ = this.getDeltaMovement();
         Vec3 â˜ƒx = new Vec3(â˜ƒ, 0.0, â˜ƒ).normalize().scale(â˜ƒ);
         this.setDeltaMovement(â˜ƒ.x / 2.0 - â˜ƒx.x, this.onGround ? Math.min(0.4, â˜ƒ.y / 2.0 + â˜ƒ) : â˜ƒ.y, â˜ƒ.z / 2.0 - â˜ƒx.z);
      }
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.GENERIC_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.GENERIC_DEATH;
   }

   protected SoundEvent getFallDamageSound(int var1) {
      return â˜ƒ > 4 ? SoundEvents.GENERIC_BIG_FALL : SoundEvents.GENERIC_SMALL_FALL;
   }

   protected SoundEvent getDrinkingSound(ItemStack var1) {
      return â˜ƒ.getDrinkingSound();
   }

   public SoundEvent getEatingSound(ItemStack var1) {
      return â˜ƒ.getEatingSound();
   }

   @Override
   public void setOnGround(boolean var1) {
      super.setOnGround(â˜ƒ);
      if (â˜ƒ) {
         this.lastClimbablePos = Optional.empty();
      }
   }

   public Optional<BlockPos> getLastClimbablePos() {
      return this.lastClimbablePos;
   }

   public boolean onClimbable() {
      if (this.isSpectator()) {
         return false;
      } else {
         BlockPos â˜ƒ = this.blockPosition();
         BlockState â˜ƒx = this.getFeetBlockState();
         if (â˜ƒx.is(BlockTags.CLIMBABLE)) {
            this.lastClimbablePos = Optional.of(â˜ƒ);
            return true;
         } else if (â˜ƒx.getBlock() instanceof TrapDoorBlock && this.trapdoorUsableAsLadder(â˜ƒ, â˜ƒx)) {
            this.lastClimbablePos = Optional.of(â˜ƒ);
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean trapdoorUsableAsLadder(BlockPos var1, BlockState var2) {
      if (â˜ƒ.getValue(TrapDoorBlock.OPEN)) {
         BlockState â˜ƒ = this.level.getBlockState(â˜ƒ.below());
         if (â˜ƒ.is(Blocks.LADDER) && â˜ƒ.getValue(LadderBlock.FACING) == â˜ƒ.getValue(TrapDoorBlock.FACING)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isAlive() {
      return !this.isRemoved() && this.getHealth() > 0.0F;
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      boolean â˜ƒ = super.causeFallDamage(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒx = this.calculateFallDamage(â˜ƒ, â˜ƒ);
      if (â˜ƒx > 0) {
         this.playSound(this.getFallDamageSound(â˜ƒx), 1.0F, 1.0F);
         this.playBlockFallSound();
         this.hurt(â˜ƒ, (float)â˜ƒx);
         return true;
      } else {
         return â˜ƒ;
      }
   }

   protected int calculateFallDamage(float var1, float var2) {
      MobEffectInstance â˜ƒ = this.getEffect(MobEffects.JUMP);
      float â˜ƒx = â˜ƒ == null ? 0.0F : (float)(â˜ƒ.getAmplifier() + 1);
      return Mth.ceil((â˜ƒ - 3.0F - â˜ƒx) * â˜ƒ);
   }

   protected void playBlockFallSound() {
      if (!this.isSilent()) {
         int â˜ƒ = Mth.floor(this.getX());
         int â˜ƒx = Mth.floor(this.getY() - 0.2F);
         int â˜ƒxx = Mth.floor(this.getZ());
         BlockState â˜ƒxxx = this.level.getBlockState(new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx));
         if (!â˜ƒxxx.isAir()) {
            SoundType â˜ƒxxxx = â˜ƒxxx.getSoundType();
            this.playSound(â˜ƒxxxx.getFallSound(), â˜ƒxxxx.getVolume() * 0.5F, â˜ƒxxxx.getPitch() * 0.75F);
         }
      }
   }

   @Override
   public void animateHurt() {
      this.hurtDuration = 10;
      this.hurtTime = this.hurtDuration;
      this.hurtDir = 0.0F;
   }

   public int getArmorValue() {
      return Mth.floor(this.getAttributeValue(Attributes.ARMOR));
   }

   protected void hurtArmor(DamageSource var1, float var2) {
   }

   protected void hurtHelmet(DamageSource var1, float var2) {
   }

   protected void hurtCurrentlyUsedShield(float var1) {
   }

   protected float getDamageAfterArmorAbsorb(DamageSource var1, float var2) {
      if (!â˜ƒ.isBypassArmor()) {
         this.hurtArmor(â˜ƒ, â˜ƒ);
         â˜ƒ = CombatRules.getDamageAfterAbsorb(â˜ƒ, (float)this.getArmorValue(), (float)this.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
      }

      return â˜ƒ;
   }

   protected float getDamageAfterMagicAbsorb(DamageSource var1, float var2) {
      if (â˜ƒ.isBypassMagic()) {
         return â˜ƒ;
      } else {
         if (this.hasEffect(MobEffects.DAMAGE_RESISTANCE) && â˜ƒ != DamageSource.OUT_OF_WORLD) {
            int â˜ƒ = (this.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5;
            int â˜ƒx = 25 - â˜ƒ;
            float â˜ƒxx = â˜ƒ * (float)â˜ƒx;
            float â˜ƒxxx = â˜ƒ;
            â˜ƒ = Math.max(â˜ƒxx / 25.0F, 0.0F);
            float â˜ƒxxxx = â˜ƒxxx - â˜ƒ;
            if (â˜ƒxxxx > 0.0F && â˜ƒxxxx < 3.4028235E37F) {
               if (this instanceof ServerPlayer) {
                  ((ServerPlayer)this).awardStat(Stats.DAMAGE_RESISTED, Math.round(â˜ƒxxxx * 10.0F));
               } else if (â˜ƒ.getEntity() instanceof ServerPlayer) {
                  ((ServerPlayer)â˜ƒ.getEntity()).awardStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(â˜ƒxxxx * 10.0F));
               }
            }
         }

         if (â˜ƒ <= 0.0F) {
            return 0.0F;
         } else {
            int â˜ƒ = EnchantmentHelper.getDamageProtection(this.getArmorSlots(), â˜ƒ);
            if (â˜ƒ > 0) {
               â˜ƒ = CombatRules.getDamageAfterMagicAbsorb(â˜ƒ, (float)â˜ƒ);
            }

            return â˜ƒ;
         }
      }
   }

   protected void actuallyHurt(DamageSource var1, float var2) {
      if (!this.isInvulnerableTo(â˜ƒ)) {
         â˜ƒ = this.getDamageAfterArmorAbsorb(â˜ƒ, â˜ƒ);
         â˜ƒ = this.getDamageAfterMagicAbsorb(â˜ƒ, â˜ƒ);
         float var8 = Math.max(â˜ƒ - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (â˜ƒ - var8));
         float â˜ƒ = â˜ƒ - var8;
         if (â˜ƒ > 0.0F && â˜ƒ < 3.4028235E37F && â˜ƒ.getEntity() instanceof ServerPlayer) {
            ((ServerPlayer)â˜ƒ.getEntity()).awardStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(â˜ƒ * 10.0F));
         }

         if (var8 != 0.0F) {
            float â˜ƒ = this.getHealth();
            this.setHealth(â˜ƒ - var8);
            this.getCombatTracker().recordDamage(â˜ƒ, â˜ƒ, var8);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - var8);
            this.gameEvent(GameEvent.ENTITY_DAMAGED, â˜ƒ.getEntity());
         }
      }
   }

   public CombatTracker getCombatTracker() {
      return this.combatTracker;
   }

   @Nullable
   public LivingEntity getKillCredit() {
      if (this.combatTracker.getKiller() != null) {
         return this.combatTracker.getKiller();
      } else if (this.lastHurtByPlayer != null) {
         return this.lastHurtByPlayer;
      } else {
         return this.lastHurtByMob != null ? this.lastHurtByMob : null;
      }
   }

   public final float getMaxHealth() {
      return (float)this.getAttributeValue(Attributes.MAX_HEALTH);
   }

   public final int getArrowCount() {
      return this.entityData.get(DATA_ARROW_COUNT_ID);
   }

   public final void setArrowCount(int var1) {
      this.entityData.set(DATA_ARROW_COUNT_ID, â˜ƒ);
   }

   public final int getStingerCount() {
      return this.entityData.get(DATA_STINGER_COUNT_ID);
   }

   public final void setStingerCount(int var1) {
      this.entityData.set(DATA_STINGER_COUNT_ID, â˜ƒ);
   }

   private int getCurrentSwingDuration() {
      if (MobEffectUtil.hasDigSpeed(this)) {
         return 6 - (1 + MobEffectUtil.getDigSpeedAmplification(this));
      } else {
         return this.hasEffect(MobEffects.DIG_SLOWDOWN) ? 6 + (1 + this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 2 : 6;
      }
   }

   public void swing(InteractionHand var1) {
      this.swing(â˜ƒ, false);
   }

   public void swing(InteractionHand var1, boolean var2) {
      if (!this.swinging || this.swingTime >= this.getCurrentSwingDuration() / 2 || this.swingTime < 0) {
         this.swingTime = -1;
         this.swinging = true;
         this.swingingArm = â˜ƒ;
         if (this.level instanceof ServerLevel) {
            ClientboundAnimatePacket â˜ƒ = new ClientboundAnimatePacket(this, â˜ƒ == InteractionHand.MAIN_HAND ? 0 : 3);
            ServerChunkCache â˜ƒx = ((ServerLevel)this.level).getChunkSource();
            if (â˜ƒ) {
               â˜ƒx.broadcastAndSend(this, â˜ƒ);
            } else {
               â˜ƒx.broadcast(this, â˜ƒ);
            }
         }
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      switch(â˜ƒ) {
         case 2:
         case 33:
         case 36:
         case 37:
         case 44:
         case 57:
            this.animationSpeed = 1.5F;
            this.invulnerableTime = 20;
            this.hurtDuration = 10;
            this.hurtTime = this.hurtDuration;
            this.hurtDir = 0.0F;
            if (â˜ƒ == 33) {
               this.playSound(SoundEvents.THORNS_HIT, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            DamageSource â˜ƒ;
            if (â˜ƒ == 37) {
               â˜ƒ = DamageSource.ON_FIRE;
            } else if (â˜ƒ == 36) {
               â˜ƒ = DamageSource.DROWN;
            } else if (â˜ƒ == 44) {
               â˜ƒ = DamageSource.SWEET_BERRY_BUSH;
            } else if (â˜ƒ == 57) {
               â˜ƒ = DamageSource.FREEZE;
            } else {
               â˜ƒ = DamageSource.GENERIC;
            }

            SoundEvent â˜ƒ = this.getHurtSound(â˜ƒ);
            if (â˜ƒ != null) {
               this.playSound(â˜ƒ, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.hurt(DamageSource.GENERIC, 0.0F);
            this.lastDamageSource = â˜ƒ;
            this.lastDamageStamp = this.level.getGameTime();
            break;
         case 3:
            SoundEvent â˜ƒ = this.getDeathSound();
            if (â˜ƒ != null) {
               this.playSound(â˜ƒ, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            if (!(this instanceof Player)) {
               this.setHealth(0.0F);
               this.die(DamageSource.GENERIC);
            }
            break;
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 31:
         case 32:
         case 34:
         case 35:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 45:
         case 53:
         case 56:
         case 58:
         case 59:
         default:
            super.handleEntityEvent(â˜ƒ);
            break;
         case 29:
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.level.random.nextFloat() * 0.4F);
            break;
         case 30:
            this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level.random.nextFloat() * 0.4F);
            break;
         case 46:
            int â˜ƒ = 128;

            for(int â˜ƒx = 0; â˜ƒx < 128; ++â˜ƒx) {
               double â˜ƒxx = (double)â˜ƒx / 127.0;
               float â˜ƒxxx = (this.random.nextFloat() - 0.5F) * 0.2F;
               float â˜ƒxxxx = (this.random.nextFloat() - 0.5F) * 0.2F;
               float â˜ƒxxxxx = (this.random.nextFloat() - 0.5F) * 0.2F;
               double â˜ƒxxxxxx = Mth.lerp(â˜ƒxx, this.xo, this.getX()) + (this.random.nextDouble() - 0.5) * (double)this.getBbWidth() * 2.0;
               double â˜ƒxxxxxxx = Mth.lerp(â˜ƒxx, this.yo, this.getY()) + this.random.nextDouble() * (double)this.getBbHeight();
               double â˜ƒxxxxxxxx = Mth.lerp(â˜ƒxx, this.zo, this.getZ()) + (this.random.nextDouble() - 0.5) * (double)this.getBbWidth() * 2.0;
               this.level.addParticle(ParticleTypes.PORTAL, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, (double)â˜ƒxxx, (double)â˜ƒxxxx, (double)â˜ƒxxxxx);
            }
            break;
         case 47:
            this.breakItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
            break;
         case 48:
            this.breakItem(this.getItemBySlot(EquipmentSlot.OFFHAND));
            break;
         case 49:
            this.breakItem(this.getItemBySlot(EquipmentSlot.HEAD));
            break;
         case 50:
            this.breakItem(this.getItemBySlot(EquipmentSlot.CHEST));
            break;
         case 51:
            this.breakItem(this.getItemBySlot(EquipmentSlot.LEGS));
            break;
         case 52:
            this.breakItem(this.getItemBySlot(EquipmentSlot.FEET));
            break;
         case 54:
            HoneyBlock.showJumpParticles(this);
            break;
         case 55:
            this.swapHandItems();
            break;
         case 60:
            this.makePoofParticles();
      }
   }

   private void makePoofParticles() {
      for(int â˜ƒ = 0; â˜ƒ < 20; ++â˜ƒ) {
         double â˜ƒx = this.random.nextGaussian() * 0.02;
         double â˜ƒxx = this.random.nextGaussian() * 0.02;
         double â˜ƒxxx = this.random.nextGaussian() * 0.02;
         this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }

   private void swapHandItems() {
      ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.OFFHAND);
      this.setItemSlot(EquipmentSlot.OFFHAND, this.getItemBySlot(EquipmentSlot.MAINHAND));
      this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ);
   }

   @Override
   protected void outOfWorld() {
      this.hurt(DamageSource.OUT_OF_WORLD, 4.0F);
   }

   protected void updateSwingTime() {
      int â˜ƒ = this.getCurrentSwingDuration();
      if (this.swinging) {
         ++this.swingTime;
         if (this.swingTime >= â˜ƒ) {
            this.swingTime = 0;
            this.swinging = false;
         }
      } else {
         this.swingTime = 0;
      }

      this.attackAnim = (float)this.swingTime / (float)â˜ƒ;
   }

   @Nullable
   public AttributeInstance getAttribute(Attribute var1) {
      return this.getAttributes().getInstance(â˜ƒ);
   }

   public double getAttributeValue(Attribute var1) {
      return this.getAttributes().getValue(â˜ƒ);
   }

   public double getAttributeBaseValue(Attribute var1) {
      return this.getAttributes().getBaseValue(â˜ƒ);
   }

   public AttributeMap getAttributes() {
      return this.attributes;
   }

   public MobType getMobType() {
      return MobType.UNDEFINED;
   }

   public ItemStack getMainHandItem() {
      return this.getItemBySlot(EquipmentSlot.MAINHAND);
   }

   public ItemStack getOffhandItem() {
      return this.getItemBySlot(EquipmentSlot.OFFHAND);
   }

   public boolean isHolding(Item var1) {
      return this.isHolding(var1x -> var1x.is(â˜ƒ));
   }

   public boolean isHolding(Predicate<ItemStack> var1) {
      return â˜ƒ.test(this.getMainHandItem()) || â˜ƒ.test(this.getOffhandItem());
   }

   public ItemStack getItemInHand(InteractionHand var1) {
      if (â˜ƒ == InteractionHand.MAIN_HAND) {
         return this.getItemBySlot(EquipmentSlot.MAINHAND);
      } else if (â˜ƒ == InteractionHand.OFF_HAND) {
         return this.getItemBySlot(EquipmentSlot.OFFHAND);
      } else {
         throw new IllegalArgumentException("Invalid hand " + â˜ƒ);
      }
   }

   public void setItemInHand(InteractionHand var1, ItemStack var2) {
      if (â˜ƒ == InteractionHand.MAIN_HAND) {
         this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ);
      } else {
         if (â˜ƒ != InteractionHand.OFF_HAND) {
            throw new IllegalArgumentException("Invalid hand " + â˜ƒ);
         }

         this.setItemSlot(EquipmentSlot.OFFHAND, â˜ƒ);
      }
   }

   public boolean hasItemInSlot(EquipmentSlot var1) {
      return !this.getItemBySlot(â˜ƒ).isEmpty();
   }

   @Override
   public abstract Iterable<ItemStack> getArmorSlots();

   public abstract ItemStack getItemBySlot(EquipmentSlot var1);

   @Override
   public abstract void setItemSlot(EquipmentSlot var1, ItemStack var2);

   protected void verifyEquippedItem(ItemStack var1) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null) {
         â˜ƒ.getItem().verifyTagAfterLoad(â˜ƒ);
      }
   }

   public float getArmorCoverPercentage() {
      Iterable<ItemStack> â˜ƒ = this.getArmorSlots();
      int â˜ƒx = 0;
      int â˜ƒxx = 0;

      for(ItemStack â˜ƒxxx : â˜ƒ) {
         if (!â˜ƒxxx.isEmpty()) {
            ++â˜ƒxx;
         }

         ++â˜ƒx;
      }

      return â˜ƒx > 0 ? (float)â˜ƒxx / (float)â˜ƒx : 0.0F;
   }

   @Override
   public void setSprinting(boolean var1) {
      super.setSprinting(â˜ƒ);
      AttributeInstance â˜ƒ = this.getAttribute(Attributes.MOVEMENT_SPEED);
      if (â˜ƒ.getModifier(SPEED_MODIFIER_SPRINTING_UUID) != null) {
         â˜ƒ.removeModifier(SPEED_MODIFIER_SPRINTING);
      }

      if (â˜ƒ) {
         â˜ƒ.addTransientModifier(SPEED_MODIFIER_SPRINTING);
      }
   }

   protected float getSoundVolume() {
      return 1.0F;
   }

   public float getVoicePitch() {
      return this.isBaby()
         ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F
         : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean isImmobile() {
      return this.isDeadOrDying();
   }

   @Override
   public void push(Entity var1) {
      if (!this.isSleeping()) {
         super.push(â˜ƒ);
      }
   }

   private void dismountVehicle(Entity var1) {
      Vec3 â˜ƒ;
      if (this.isRemoved()) {
         â˜ƒ = this.position();
      } else if (!â˜ƒ.isRemoved() && !this.level.getBlockState(â˜ƒ.blockPosition()).is(BlockTags.PORTALS)) {
         â˜ƒ = â˜ƒ.getDismountLocationForPassenger(this);
      } else {
         double â˜ƒ = Math.max(this.getY(), â˜ƒ.getY());
         â˜ƒ = new Vec3(this.getX(), â˜ƒ, this.getZ());
      }

      this.dismountTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   @Override
   public boolean shouldShowName() {
      return this.isCustomNameVisible();
   }

   protected float getJumpPower() {
      return 0.42F * this.getBlockJumpFactor();
   }

   public double getJumpBoostPower() {
      return this.hasEffect(MobEffects.JUMP) ? (double)(0.1F * (float)(this.getEffect(MobEffects.JUMP).getAmplifier() + 1)) : 0.0;
   }

   protected void jumpFromGround() {
      double â˜ƒ = (double)this.getJumpPower() + this.getJumpBoostPower();
      Vec3 â˜ƒx = this.getDeltaMovement();
      this.setDeltaMovement(â˜ƒx.x, â˜ƒ, â˜ƒx.z);
      if (this.isSprinting()) {
         float â˜ƒxx = this.getYRot() * (float) (Math.PI / 180.0);
         this.setDeltaMovement(this.getDeltaMovement().add((double)(-Mth.sin(â˜ƒxx) * 0.2F), 0.0, (double)(Mth.cos(â˜ƒxx) * 0.2F)));
      }

      this.hasImpulse = true;
   }

   protected void goDownInWater() {
      this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04F, 0.0));
   }

   protected void jumpInLiquid(net.minecraft.tags.Tag<Fluid> var1) {
      this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.04F, 0.0));
   }

   protected float getWaterSlowDown() {
      return 0.8F;
   }

   public boolean canStandOnFluid(Fluid var1) {
      return false;
   }

   public void travel(Vec3 var1) {
      if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
         double â˜ƒ = 0.08;
         boolean â˜ƒx = this.getDeltaMovement().y <= 0.0;
         if (â˜ƒx && this.hasEffect(MobEffects.SLOW_FALLING)) {
            â˜ƒ = 0.01;
            this.fallDistance = 0.0F;
         }

         FluidState â˜ƒ = this.level.getFluidState(this.blockPosition());
         if (this.isInWater() && this.isAffectedByFluids() && !this.canStandOnFluid(â˜ƒ.getType())) {
            double â˜ƒx = this.getY();
            float â˜ƒxx = this.isSprinting() ? 0.9F : this.getWaterSlowDown();
            float â˜ƒxxx = 0.02F;
            float â˜ƒxxxx = (float)EnchantmentHelper.getDepthStrider(this);
            if (â˜ƒxxxx > 3.0F) {
               â˜ƒxxxx = 3.0F;
            }

            if (!this.onGround) {
               â˜ƒxxxx *= 0.5F;
            }

            if (â˜ƒxxxx > 0.0F) {
               â˜ƒxx += (0.54600006F - â˜ƒxx) * â˜ƒxxxx / 3.0F;
               â˜ƒxxx += (this.getSpeed() - â˜ƒxxx) * â˜ƒxxxx / 3.0F;
            }

            if (this.hasEffect(MobEffects.DOLPHINS_GRACE)) {
               â˜ƒxx = 0.96F;
            }

            this.moveRelative(â˜ƒxxx, â˜ƒ);
            this.move(MoverType.SELF, this.getDeltaMovement());
            Vec3 â˜ƒx = this.getDeltaMovement();
            if (this.horizontalCollision && this.onClimbable()) {
               â˜ƒx = new Vec3(â˜ƒx.x, 0.2, â˜ƒx.z);
            }

            this.setDeltaMovement(â˜ƒx.multiply((double)â˜ƒxx, 0.8F, (double)â˜ƒxx));
            Vec3 â˜ƒx = this.getFluidFallingAdjustedMovement(â˜ƒ, â˜ƒx, this.getDeltaMovement());
            this.setDeltaMovement(â˜ƒx);
            if (this.horizontalCollision && this.isFree(â˜ƒx.x, â˜ƒx.y + 0.6F - this.getY() + â˜ƒx, â˜ƒx.z)) {
               this.setDeltaMovement(â˜ƒx.x, 0.3F, â˜ƒx.z);
            }
         } else if (this.isInLava() && this.isAffectedByFluids() && !this.canStandOnFluid(â˜ƒ.getType())) {
            double â˜ƒ = this.getY();
            this.moveRelative(0.02F, â˜ƒ);
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
               this.setDeltaMovement(this.getDeltaMovement().multiply(0.5, 0.8F, 0.5));
               Vec3 â˜ƒx = this.getFluidFallingAdjustedMovement(â˜ƒ, â˜ƒx, this.getDeltaMovement());
               this.setDeltaMovement(â˜ƒx);
            } else {
               this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
            }

            if (!this.isNoGravity()) {
               this.setDeltaMovement(this.getDeltaMovement().add(0.0, -â˜ƒ / 4.0, 0.0));
            }

            Vec3 â˜ƒ = this.getDeltaMovement();
            if (this.horizontalCollision && this.isFree(â˜ƒ.x, â˜ƒ.y + 0.6F - this.getY() + â˜ƒ, â˜ƒ.z)) {
               this.setDeltaMovement(â˜ƒ.x, 0.3F, â˜ƒ.z);
            }
         } else if (this.isFallFlying()) {
            Vec3 â˜ƒ = this.getDeltaMovement();
            if (â˜ƒ.y > -0.5) {
               this.fallDistance = 1.0F;
            }

            Vec3 â˜ƒ = this.getLookAngle();
            float â˜ƒx = this.getXRot() * (float) (Math.PI / 180.0);
            double â˜ƒxx = Math.sqrt(â˜ƒ.x * â˜ƒ.x + â˜ƒ.z * â˜ƒ.z);
            double â˜ƒxxx = â˜ƒ.horizontalDistance();
            double â˜ƒxxxx = â˜ƒ.length();
            float â˜ƒxxxxx = Mth.cos(â˜ƒx);
            â˜ƒxxxxx = (float)((double)â˜ƒxxxxx * (double)â˜ƒxxxxx * Math.min(1.0, â˜ƒxxxx / 0.4));
            â˜ƒ = this.getDeltaMovement().add(0.0, â˜ƒ * (-1.0 + (double)â˜ƒxxxxx * 0.75), 0.0);
            if (â˜ƒ.y < 0.0 && â˜ƒxx > 0.0) {
               double â˜ƒxxxxxx = â˜ƒ.y * -0.1 * (double)â˜ƒxxxxx;
               â˜ƒ = â˜ƒ.add(â˜ƒ.x * â˜ƒxxxxxx / â˜ƒxx, â˜ƒxxxxxx, â˜ƒ.z * â˜ƒxxxxxx / â˜ƒxx);
            }

            if (â˜ƒx < 0.0F && â˜ƒxx > 0.0) {
               double â˜ƒ = â˜ƒxxx * (double)(-Mth.sin(â˜ƒx)) * 0.04;
               â˜ƒ = â˜ƒ.add(-â˜ƒ.x * â˜ƒ / â˜ƒxx, â˜ƒ * 3.2, -â˜ƒ.z * â˜ƒ / â˜ƒxx);
            }

            if (â˜ƒxx > 0.0) {
               â˜ƒ = â˜ƒ.add((â˜ƒ.x / â˜ƒxx * â˜ƒxxx - â˜ƒ.x) * 0.1, 0.0, (â˜ƒ.z / â˜ƒxx * â˜ƒxxx - â˜ƒ.z) * 0.1);
            }

            this.setDeltaMovement(â˜ƒ.multiply(0.99F, 0.98F, 0.99F));
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (this.horizontalCollision && !this.level.isClientSide) {
               double â˜ƒ = this.getDeltaMovement().horizontalDistance();
               double â˜ƒx = â˜ƒxxx - â˜ƒ;
               float â˜ƒxx = (float)(â˜ƒx * 10.0 - 3.0);
               if (â˜ƒxx > 0.0F) {
                  this.playSound(this.getFallDamageSound((int)â˜ƒxx), 1.0F, 1.0F);
                  this.hurt(DamageSource.FLY_INTO_WALL, â˜ƒxx);
               }
            }

            if (this.onGround && !this.level.isClientSide) {
               this.setSharedFlag(7, false);
            }
         } else {
            BlockPos â˜ƒ = this.getBlockPosBelowThatAffectsMyMovement();
            float â˜ƒx = this.level.getBlockState(â˜ƒ).getBlock().getFriction();
            float â˜ƒxx = this.onGround ? â˜ƒx * 0.91F : 0.91F;
            Vec3 â˜ƒxxx = this.handleRelativeFrictionAndCalculateMovement(â˜ƒ, â˜ƒx);
            double â˜ƒxxxx = â˜ƒxxx.y;
            if (this.hasEffect(MobEffects.LEVITATION)) {
               â˜ƒxxxx += (0.05 * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - â˜ƒxxx.y) * 0.2;
               this.fallDistance = 0.0F;
            } else if (this.level.isClientSide && !this.level.hasChunkAt(â˜ƒ)) {
               if (this.getY() > (double)this.level.getMinBuildHeight()) {
                  â˜ƒxxxx = -0.1;
               } else {
                  â˜ƒxxxx = 0.0;
               }
            } else if (!this.isNoGravity()) {
               â˜ƒxxxx -= â˜ƒ;
            }

            if (this.shouldDiscardFriction()) {
               this.setDeltaMovement(â˜ƒxxx.x, â˜ƒxxxx, â˜ƒxxx.z);
            } else {
               this.setDeltaMovement(â˜ƒxxx.x * (double)â˜ƒxx, â˜ƒxxxx * 0.98F, â˜ƒxxx.z * (double)â˜ƒxx);
            }
         }
      }

      this.calculateEntityAnimation(this, this instanceof FlyingAnimal);
   }

   public void calculateEntityAnimation(LivingEntity var1, boolean var2) {
      â˜ƒ.animationSpeedOld = â˜ƒ.animationSpeed;
      double â˜ƒ = â˜ƒ.getX() - â˜ƒ.xo;
      double â˜ƒx = â˜ƒ ? â˜ƒ.getY() - â˜ƒ.yo : 0.0;
      double â˜ƒxx = â˜ƒ.getZ() - â˜ƒ.zo;
      float â˜ƒxxx = (float)Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx) * 4.0F;
      if (â˜ƒxxx > 1.0F) {
         â˜ƒxxx = 1.0F;
      }

      â˜ƒ.animationSpeed += (â˜ƒxxx - â˜ƒ.animationSpeed) * 0.4F;
      â˜ƒ.animationPosition += â˜ƒ.animationSpeed;
   }

   public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 var1, float var2) {
      this.moveRelative(this.getFrictionInfluencedSpeed(â˜ƒ), â˜ƒ);
      this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
      this.move(MoverType.SELF, this.getDeltaMovement());
      Vec3 â˜ƒ = this.getDeltaMovement();
      if ((this.horizontalCollision || this.jumping)
         && (this.onClimbable() || this.getFeetBlockState().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
         â˜ƒ = new Vec3(â˜ƒ.x, 0.2, â˜ƒ.z);
      }

      return â˜ƒ;
   }

   public Vec3 getFluidFallingAdjustedMovement(double var1, boolean var3, Vec3 var4) {
      if (!this.isNoGravity() && !this.isSprinting()) {
         double â˜ƒ;
         if (â˜ƒ && Math.abs(â˜ƒ.y - 0.005) >= 0.003 && Math.abs(â˜ƒ.y - â˜ƒ / 16.0) < 0.003) {
            â˜ƒ = -0.003;
         } else {
            â˜ƒ = â˜ƒ.y - â˜ƒ / 16.0;
         }

         return new Vec3(â˜ƒ.x, â˜ƒ, â˜ƒ.z);
      } else {
         return â˜ƒ;
      }
   }

   private Vec3 handleOnClimbable(Vec3 var1) {
      if (this.onClimbable()) {
         this.fallDistance = 0.0F;
         float â˜ƒ = 0.15F;
         double â˜ƒx = Mth.clamp(â˜ƒ.x, -0.15F, 0.15F);
         double â˜ƒxx = Mth.clamp(â˜ƒ.z, -0.15F, 0.15F);
         double â˜ƒxxx = Math.max(â˜ƒ.y, -0.15F);
         if (â˜ƒxxx < 0.0 && !this.getFeetBlockState().is(Blocks.SCAFFOLDING) && this.isSuppressingSlidingDownLadder() && this instanceof Player) {
            â˜ƒxxx = 0.0;
         }

         â˜ƒ = new Vec3(â˜ƒx, â˜ƒxxx, â˜ƒxx);
      }

      return â˜ƒ;
   }

   private float getFrictionInfluencedSpeed(float var1) {
      return this.onGround ? this.getSpeed() * (0.21600002F / (â˜ƒ * â˜ƒ * â˜ƒ)) : this.flyingSpeed;
   }

   public float getSpeed() {
      return this.speed;
   }

   public void setSpeed(float var1) {
      this.speed = â˜ƒ;
   }

   public boolean doHurtTarget(Entity var1) {
      this.setLastHurtMob(â˜ƒ);
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      this.updatingUsingItem();
      this.updateSwimAmount();
      if (!this.level.isClientSide) {
         int â˜ƒ = this.getArrowCount();
         if (â˜ƒ > 0) {
            if (this.removeArrowTime <= 0) {
               this.removeArrowTime = 20 * (30 - â˜ƒ);
            }

            --this.removeArrowTime;
            if (this.removeArrowTime <= 0) {
               this.setArrowCount(â˜ƒ - 1);
            }
         }

         int â˜ƒ = this.getStingerCount();
         if (â˜ƒ > 0) {
            if (this.removeStingerTime <= 0) {
               this.removeStingerTime = 20 * (30 - â˜ƒ);
            }

            --this.removeStingerTime;
            if (this.removeStingerTime <= 0) {
               this.setStingerCount(â˜ƒ - 1);
            }
         }

         this.detectEquipmentUpdates();
         if (this.tickCount % 20 == 0) {
            this.getCombatTracker().recheckStatus();
         }

         if (this.isSleeping() && !this.checkBedExists()) {
            this.stopSleeping();
         }
      }

      this.aiStep();
      double â˜ƒ = this.getX() - this.xo;
      double â˜ƒx = this.getZ() - this.zo;
      float â˜ƒxx = (float)(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx);
      float â˜ƒxxx = this.yBodyRot;
      float â˜ƒxxxx = 0.0F;
      this.oRun = this.run;
      float â˜ƒxxxxx = 0.0F;
      if (â˜ƒxx > 0.0025000002F) {
         â˜ƒxxxxx = 1.0F;
         â˜ƒxxxx = (float)Math.sqrt((double)â˜ƒxx) * 3.0F;
         float â˜ƒxxxxxx = (float)Mth.atan2(â˜ƒx, â˜ƒ) * (180.0F / (float)Math.PI) - 90.0F;
         float â˜ƒxxxxxxx = Mth.abs(Mth.wrapDegrees(this.getYRot()) - â˜ƒxxxxxx);
         if (95.0F < â˜ƒxxxxxxx && â˜ƒxxxxxxx < 265.0F) {
            â˜ƒxxx = â˜ƒxxxxxx - 180.0F;
         } else {
            â˜ƒxxx = â˜ƒxxxxxx;
         }
      }

      if (this.attackAnim > 0.0F) {
         â˜ƒxxx = this.getYRot();
      }

      if (!this.onGround) {
         â˜ƒxxxxx = 0.0F;
      }

      this.run += (â˜ƒxxxxx - this.run) * 0.3F;
      this.level.getProfiler().push("headTurn");
      â˜ƒxxxx = this.tickHeadTurn(â˜ƒxxx, â˜ƒxxxx);
      this.level.getProfiler().pop();
      this.level.getProfiler().push("rangeChecks");

      while(this.getYRot() - this.yRotO < -180.0F) {
         this.yRotO -= 360.0F;
      }

      while(this.getYRot() - this.yRotO >= 180.0F) {
         this.yRotO += 360.0F;
      }

      while(this.yBodyRot - this.yBodyRotO < -180.0F) {
         this.yBodyRotO -= 360.0F;
      }

      while(this.yBodyRot - this.yBodyRotO >= 180.0F) {
         this.yBodyRotO += 360.0F;
      }

      while(this.getXRot() - this.xRotO < -180.0F) {
         this.xRotO -= 360.0F;
      }

      while(this.getXRot() - this.xRotO >= 180.0F) {
         this.xRotO += 360.0F;
      }

      while(this.yHeadRot - this.yHeadRotO < -180.0F) {
         this.yHeadRotO -= 360.0F;
      }

      while(this.yHeadRot - this.yHeadRotO >= 180.0F) {
         this.yHeadRotO += 360.0F;
      }

      this.level.getProfiler().pop();
      this.animStep += â˜ƒxxxx;
      if (this.isFallFlying()) {
         ++this.fallFlyTicks;
      } else {
         this.fallFlyTicks = 0;
      }

      if (this.isSleeping()) {
         this.setXRot(0.0F);
      }
   }

   private void detectEquipmentUpdates() {
      Map<EquipmentSlot, ItemStack> â˜ƒ = this.collectEquipmentChanges();
      if (â˜ƒ != null) {
         this.handleHandSwap(â˜ƒ);
         if (!â˜ƒ.isEmpty()) {
            this.handleEquipmentChanges(â˜ƒ);
         }
      }
   }

   @Nullable
   private Map<EquipmentSlot, ItemStack> collectEquipmentChanges() {
      Map<EquipmentSlot, ItemStack> â˜ƒ = null;

      for(EquipmentSlot â˜ƒx : EquipmentSlot.values()) {
         ItemStack â˜ƒxx;
         switch(â˜ƒx.getType()) {
            case HAND:
               â˜ƒxx = this.getLastHandItem(â˜ƒx);
               break;
            case ARMOR:
               â˜ƒxx = this.getLastArmorItem(â˜ƒx);
               break;
            default:
               continue;
         }

         ItemStack â˜ƒxx = this.getItemBySlot(â˜ƒx);
         if (!ItemStack.matches(â˜ƒxx, â˜ƒxx)) {
            if (â˜ƒ == null) {
               â˜ƒ = Maps.newEnumMap(EquipmentSlot.class);
            }

            â˜ƒ.put(â˜ƒx, â˜ƒxx);
            if (!â˜ƒxx.isEmpty()) {
               this.getAttributes().removeAttributeModifiers(â˜ƒxx.getAttributeModifiers(â˜ƒx));
            }

            if (!â˜ƒxx.isEmpty()) {
               this.getAttributes().addTransientAttributeModifiers(â˜ƒxx.getAttributeModifiers(â˜ƒx));
            }
         }
      }

      return â˜ƒ;
   }

   private void handleHandSwap(Map<EquipmentSlot, ItemStack> var1) {
      ItemStack â˜ƒ = (ItemStack)â˜ƒ.get(EquipmentSlot.MAINHAND);
      ItemStack â˜ƒx = (ItemStack)â˜ƒ.get(EquipmentSlot.OFFHAND);
      if (â˜ƒ != null
         && â˜ƒx != null
         && ItemStack.matches(â˜ƒ, this.getLastHandItem(EquipmentSlot.OFFHAND))
         && ItemStack.matches(â˜ƒx, this.getLastHandItem(EquipmentSlot.MAINHAND))) {
         ((ServerLevel)this.level).getChunkSource().broadcast(this, new ClientboundEntityEventPacket(this, (byte)55));
         â˜ƒ.remove(EquipmentSlot.MAINHAND);
         â˜ƒ.remove(EquipmentSlot.OFFHAND);
         this.setLastHandItem(EquipmentSlot.MAINHAND, â˜ƒ.copy());
         this.setLastHandItem(EquipmentSlot.OFFHAND, â˜ƒx.copy());
      }
   }

   private void handleEquipmentChanges(Map<EquipmentSlot, ItemStack> var1) {
      List<Pair<EquipmentSlot, ItemStack>> â˜ƒ = Lists.<Pair<EquipmentSlot, ItemStack>>newArrayListWithCapacity(â˜ƒ.size());
      â˜ƒ.forEach((var2x, var3) -> {
         ItemStack â˜ƒ = var3.copy();
         â˜ƒ.add(Pair.of(var2x, â˜ƒ));
         switch(var2x.getType()) {
            case HAND:
               this.setLastHandItem(var2x, â˜ƒ);
               break;
            case ARMOR:
               this.setLastArmorItem(var2x, â˜ƒ);
         }
      });
      ((ServerLevel)this.level).getChunkSource().broadcast(this, new ClientboundSetEquipmentPacket(this.getId(), â˜ƒ));
   }

   private ItemStack getLastArmorItem(EquipmentSlot var1) {
      return this.lastArmorItemStacks.get(â˜ƒ.getIndex());
   }

   private void setLastArmorItem(EquipmentSlot var1, ItemStack var2) {
      this.lastArmorItemStacks.set(â˜ƒ.getIndex(), â˜ƒ);
   }

   private ItemStack getLastHandItem(EquipmentSlot var1) {
      return this.lastHandItemStacks.get(â˜ƒ.getIndex());
   }

   private void setLastHandItem(EquipmentSlot var1, ItemStack var2) {
      this.lastHandItemStacks.set(â˜ƒ.getIndex(), â˜ƒ);
   }

   protected float tickHeadTurn(float var1, float var2) {
      float â˜ƒ = Mth.wrapDegrees(â˜ƒ - this.yBodyRot);
      this.yBodyRot += â˜ƒ * 0.3F;
      float â˜ƒx = Mth.wrapDegrees(this.getYRot() - this.yBodyRot);
      boolean â˜ƒxx = â˜ƒx < -90.0F || â˜ƒx >= 90.0F;
      if (â˜ƒx < -75.0F) {
         â˜ƒx = -75.0F;
      }

      if (â˜ƒx >= 75.0F) {
         â˜ƒx = 75.0F;
      }

      this.yBodyRot = this.getYRot() - â˜ƒx;
      if (â˜ƒx * â˜ƒx > 2500.0F) {
         this.yBodyRot += â˜ƒx * 0.2F;
      }

      if (â˜ƒxx) {
         â˜ƒ *= -1.0F;
      }

      return â˜ƒ;
   }

   public void aiStep() {
      if (this.noJumpDelay > 0) {
         --this.noJumpDelay;
      }

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
      } else if (!this.isEffectiveAi()) {
         this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
      }

      if (this.lerpHeadSteps > 0) {
         this.yHeadRot = (float)((double)this.yHeadRot + Mth.wrapDegrees(this.lyHeadRot - (double)this.yHeadRot) / (double)this.lerpHeadSteps);
         --this.lerpHeadSteps;
      }

      Vec3 â˜ƒ = this.getDeltaMovement();
      double â˜ƒx = â˜ƒ.x;
      double â˜ƒxx = â˜ƒ.y;
      double â˜ƒxxx = â˜ƒ.z;
      if (Math.abs(â˜ƒ.x) < 0.003) {
         â˜ƒx = 0.0;
      }

      if (Math.abs(â˜ƒ.y) < 0.003) {
         â˜ƒxx = 0.0;
      }

      if (Math.abs(â˜ƒ.z) < 0.003) {
         â˜ƒxxx = 0.0;
      }

      this.setDeltaMovement(â˜ƒx, â˜ƒxx, â˜ƒxxx);
      this.level.getProfiler().push("ai");
      if (this.isImmobile()) {
         this.jumping = false;
         this.xxa = 0.0F;
         this.zza = 0.0F;
      } else if (this.isEffectiveAi()) {
         this.level.getProfiler().push("newAi");
         this.serverAiStep();
         this.level.getProfiler().pop();
      }

      this.level.getProfiler().pop();
      this.level.getProfiler().push("jump");
      if (this.jumping && this.isAffectedByFluids()) {
         double â˜ƒ;
         if (this.isInLava()) {
            â˜ƒ = this.getFluidHeight(FluidTags.LAVA);
         } else {
            â˜ƒ = this.getFluidHeight(FluidTags.WATER);
         }

         boolean â˜ƒ = this.isInWater() && â˜ƒ > 0.0;
         double â˜ƒx = this.getFluidJumpThreshold();
         if (!â˜ƒ || this.onGround && !(â˜ƒ > â˜ƒx)) {
            if (!this.isInLava() || this.onGround && !(â˜ƒ > â˜ƒx)) {
               if ((this.onGround || â˜ƒ && â˜ƒ <= â˜ƒx) && this.noJumpDelay == 0) {
                  this.jumpFromGround();
                  this.noJumpDelay = 10;
               }
            } else {
               this.jumpInLiquid(FluidTags.LAVA);
            }
         } else {
            this.jumpInLiquid(FluidTags.WATER);
         }
      } else {
         this.noJumpDelay = 0;
      }

      this.level.getProfiler().pop();
      this.level.getProfiler().push("travel");
      this.xxa *= 0.98F;
      this.zza *= 0.98F;
      this.updateFallFlying();
      AABB â˜ƒ = this.getBoundingBox();
      this.travel(new Vec3((double)this.xxa, (double)this.yya, (double)this.zza));
      this.level.getProfiler().pop();
      this.level.getProfiler().push("freezing");
      boolean â˜ƒx = this.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES);
      if (!this.level.isClientSide && !this.isDeadOrDying()) {
         int â˜ƒxx = this.getTicksFrozen();
         if (this.isInPowderSnow && this.canFreeze()) {
            this.setTicksFrozen(Math.min(this.getTicksRequiredToFreeze(), â˜ƒxx + 1));
         } else {
            this.setTicksFrozen(Math.max(0, â˜ƒxx - 2));
         }
      }

      this.removeFrost();
      this.tryAddFrost();
      if (!this.level.isClientSide && this.tickCount % 40 == 0 && this.isFullyFrozen() && this.canFreeze()) {
         int â˜ƒ = â˜ƒx ? 5 : 1;
         this.hurt(DamageSource.FREEZE, (float)â˜ƒ);
      }

      this.level.getProfiler().pop();
      this.level.getProfiler().push("push");
      if (this.autoSpinAttackTicks > 0) {
         --this.autoSpinAttackTicks;
         this.checkAutoSpinAttack(â˜ƒ, this.getBoundingBox());
      }

      this.pushEntities();
      this.level.getProfiler().pop();
      if (!this.level.isClientSide && this.isSensitiveToWater() && this.isInWaterRainOrBubble()) {
         this.hurt(DamageSource.DROWN, 1.0F);
      }
   }

   public boolean isSensitiveToWater() {
      return false;
   }

   private void updateFallFlying() {
      boolean â˜ƒ = this.getSharedFlag(7);
      if (â˜ƒ && !this.onGround && !this.isPassenger() && !this.hasEffect(MobEffects.LEVITATION)) {
         ItemStack â˜ƒx = this.getItemBySlot(EquipmentSlot.CHEST);
         if (â˜ƒx.is(Items.ELYTRA) && ElytraItem.isFlyEnabled(â˜ƒx)) {
            â˜ƒ = true;
            int â˜ƒxx = this.fallFlyTicks + 1;
            if (!this.level.isClientSide && â˜ƒxx % 10 == 0) {
               int â˜ƒxxx = â˜ƒxx / 10;
               if (â˜ƒxxx % 2 == 0) {
                  â˜ƒx.hurtAndBreak(1, this, var0 -> var0.broadcastBreakEvent(EquipmentSlot.CHEST));
               }

               this.gameEvent(GameEvent.ELYTRA_FREE_FALL);
            }
         } else {
            â˜ƒ = false;
         }
      } else {
         â˜ƒ = false;
      }

      if (!this.level.isClientSide) {
         this.setSharedFlag(7, â˜ƒ);
      }
   }

   protected void serverAiStep() {
   }

   protected void pushEntities() {
      List<Entity> â˜ƒ = this.level.getEntities(this, this.getBoundingBox(), EntitySelector.pushableBy(this));
      if (!â˜ƒ.isEmpty()) {
         int â˜ƒx = this.level.getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
         if (â˜ƒx > 0 && â˜ƒ.size() > â˜ƒx - 1 && this.random.nextInt(4) == 0) {
            int â˜ƒxx = 0;

            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ.size(); ++â˜ƒxxx) {
               if (!((Entity)â˜ƒ.get(â˜ƒxxx)).isPassenger()) {
                  ++â˜ƒxx;
               }
            }

            if (â˜ƒxx > â˜ƒx - 1) {
               this.hurt(DamageSource.CRAMMING, 6.0F);
            }
         }

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            Entity â˜ƒxx = (Entity)â˜ƒ.get(â˜ƒx);
            this.doPush(â˜ƒxx);
         }
      }
   }

   protected void checkAutoSpinAttack(AABB var1, AABB var2) {
      AABB â˜ƒ = â˜ƒ.minmax(â˜ƒ);
      List<Entity> â˜ƒx = this.level.getEntities(this, â˜ƒ);
      if (!â˜ƒx.isEmpty()) {
         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            Entity â˜ƒxxx = (Entity)â˜ƒx.get(â˜ƒxx);
            if (â˜ƒxxx instanceof LivingEntity) {
               this.doAutoAttackOnTouch((LivingEntity)â˜ƒxxx);
               this.autoSpinAttackTicks = 0;
               this.setDeltaMovement(this.getDeltaMovement().scale(-0.2));
               break;
            }
         }
      } else if (this.horizontalCollision) {
         this.autoSpinAttackTicks = 0;
      }

      if (!this.level.isClientSide && this.autoSpinAttackTicks <= 0) {
         this.setLivingEntityFlag(4, false);
      }
   }

   protected void doPush(Entity var1) {
      â˜ƒ.push(this);
   }

   protected void doAutoAttackOnTouch(LivingEntity var1) {
   }

   public void startAutoSpinAttack(int var1) {
      this.autoSpinAttackTicks = â˜ƒ;
      if (!this.level.isClientSide) {
         this.setLivingEntityFlag(4, true);
      }
   }

   public boolean isAutoSpinAttack() {
      return (this.entityData.get(DATA_LIVING_ENTITY_FLAGS) & 4) != 0;
   }

   @Override
   public void stopRiding() {
      Entity â˜ƒ = this.getVehicle();
      super.stopRiding();
      if (â˜ƒ != null && â˜ƒ != this.getVehicle() && !this.level.isClientSide) {
         this.dismountVehicle(â˜ƒ);
      }
   }

   @Override
   public void rideTick() {
      super.rideTick();
      this.oRun = this.run;
      this.run = 0.0F;
      this.fallDistance = 0.0F;
   }

   @Override
   public void lerpTo(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.lerpX = â˜ƒ;
      this.lerpY = â˜ƒ;
      this.lerpZ = â˜ƒ;
      this.lerpYRot = (double)â˜ƒ;
      this.lerpXRot = (double)â˜ƒ;
      this.lerpSteps = â˜ƒ;
   }

   @Override
   public void lerpHeadTo(float var1, int var2) {
      this.lyHeadRot = (double)â˜ƒ;
      this.lerpHeadSteps = â˜ƒ;
   }

   public void setJumping(boolean var1) {
      this.jumping = â˜ƒ;
   }

   public void onItemPickup(ItemEntity var1) {
      Player â˜ƒ = â˜ƒ.getThrower() != null ? this.level.getPlayerByUUID(â˜ƒ.getThrower()) : null;
      if (â˜ƒ instanceof ServerPlayer) {
         CriteriaTriggers.ITEM_PICKED_UP_BY_ENTITY.trigger((ServerPlayer)â˜ƒ, â˜ƒ.getItem(), this);
      }
   }

   public void take(Entity var1, int var2) {
      if (!â˜ƒ.isRemoved() && !this.level.isClientSide && (â˜ƒ instanceof ItemEntity || â˜ƒ instanceof AbstractArrow || â˜ƒ instanceof ExperienceOrb)) {
         ((ServerLevel)this.level).getChunkSource().broadcast(â˜ƒ, new ClientboundTakeItemEntityPacket(â˜ƒ.getId(), this.getId(), â˜ƒ));
      }
   }

   public boolean hasLineOfSight(Entity var1) {
      if (â˜ƒ.level != this.level) {
         return false;
      } else {
         Vec3 â˜ƒ = new Vec3(this.getX(), this.getEyeY(), this.getZ());
         Vec3 â˜ƒx = new Vec3(â˜ƒ.getX(), â˜ƒ.getEyeY(), â˜ƒ.getZ());
         if (â˜ƒx.distanceTo(â˜ƒ) > 128.0) {
            return false;
         } else {
            return this.level.clip(new ClipContext(â˜ƒ, â˜ƒx, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
         }
      }
   }

   @Override
   public float getViewYRot(float var1) {
      return â˜ƒ == 1.0F ? this.yHeadRot : Mth.lerp(â˜ƒ, this.yHeadRotO, this.yHeadRot);
   }

   public float getAttackAnim(float var1) {
      float â˜ƒ = this.attackAnim - this.oAttackAnim;
      if (â˜ƒ < 0.0F) {
         ++â˜ƒ;
      }

      return this.oAttackAnim + â˜ƒ * â˜ƒ;
   }

   public boolean isEffectiveAi() {
      return !this.level.isClientSide;
   }

   @Override
   public boolean isPickable() {
      return !this.isRemoved();
   }

   @Override
   public boolean isPushable() {
      return this.isAlive() && !this.isSpectator() && !this.onClimbable();
   }

   @Override
   protected void markHurt() {
      this.hurtMarked = this.random.nextDouble() >= this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
   }

   @Override
   public float getYHeadRot() {
      return this.yHeadRot;
   }

   @Override
   public void setYHeadRot(float var1) {
      this.yHeadRot = â˜ƒ;
   }

   @Override
   public void setYBodyRot(float var1) {
      this.yBodyRot = â˜ƒ;
   }

   @Override
   protected Vec3 getRelativePortalPosition(Direction.Axis var1, BlockUtil.FoundRectangle var2) {
      return resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(â˜ƒ, â˜ƒ));
   }

   public static Vec3 resetForwardDirectionOfRelativePortalPosition(Vec3 var0) {
      return new Vec3(â˜ƒ.x, â˜ƒ.y, 0.0);
   }

   public float getAbsorptionAmount() {
      return this.absorptionAmount;
   }

   public void setAbsorptionAmount(float var1) {
      if (â˜ƒ < 0.0F) {
         â˜ƒ = 0.0F;
      }

      this.absorptionAmount = â˜ƒ;
   }

   public void onEnterCombat() {
   }

   public void onLeaveCombat() {
   }

   protected void updateEffectVisibility() {
      this.effectsDirty = true;
   }

   public abstract HumanoidArm getMainArm();

   public boolean isUsingItem() {
      return (this.entityData.get(DATA_LIVING_ENTITY_FLAGS) & 1) > 0;
   }

   public InteractionHand getUsedItemHand() {
      return (this.entityData.get(DATA_LIVING_ENTITY_FLAGS) & 2) > 0 ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
   }

   private void updatingUsingItem() {
      if (this.isUsingItem()) {
         if (ItemStack.isSameIgnoreDurability(this.getItemInHand(this.getUsedItemHand()), this.useItem)) {
            this.useItem = this.getItemInHand(this.getUsedItemHand());
            this.updateUsingItem(this.useItem);
         } else {
            this.stopUsingItem();
         }
      }
   }

   protected void updateUsingItem(ItemStack var1) {
      â˜ƒ.onUseTick(this.level, this, this.getUseItemRemainingTicks());
      if (this.shouldTriggerItemUseEffects()) {
         this.triggerItemUseEffects(â˜ƒ, 5);
      }

      if (--this.useItemRemaining == 0 && !this.level.isClientSide && !â˜ƒ.useOnRelease()) {
         this.completeUsingItem();
      }
   }

   private boolean shouldTriggerItemUseEffects() {
      int â˜ƒ = this.getUseItemRemainingTicks();
      FoodProperties â˜ƒx = this.useItem.getItem().getFoodProperties();
      boolean â˜ƒxx = â˜ƒx != null && â˜ƒx.isFastFood();
      â˜ƒxx |= â˜ƒ <= this.useItem.getUseDuration() - 7;
      return â˜ƒxx && â˜ƒ % 4 == 0;
   }

   private void updateSwimAmount() {
      this.swimAmountO = this.swimAmount;
      if (this.isVisuallySwimming()) {
         this.swimAmount = Math.min(1.0F, this.swimAmount + 0.09F);
      } else {
         this.swimAmount = Math.max(0.0F, this.swimAmount - 0.09F);
      }
   }

   protected void setLivingEntityFlag(int var1, boolean var2) {
      int â˜ƒ = this.entityData.get(DATA_LIVING_ENTITY_FLAGS);
      if (â˜ƒ) {
         â˜ƒ |= â˜ƒ;
      } else {
         â˜ƒ &= ~â˜ƒ;
      }

      this.entityData.set(DATA_LIVING_ENTITY_FLAGS, (byte)â˜ƒ);
   }

   public void startUsingItem(InteractionHand var1) {
      ItemStack â˜ƒ = this.getItemInHand(â˜ƒ);
      if (!â˜ƒ.isEmpty() && !this.isUsingItem()) {
         this.useItem = â˜ƒ;
         this.useItemRemaining = â˜ƒ.getUseDuration();
         if (!this.level.isClientSide) {
            this.setLivingEntityFlag(1, true);
            this.setLivingEntityFlag(2, â˜ƒ == InteractionHand.OFF_HAND);
         }
      }
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      super.onSyncedDataUpdated(â˜ƒ);
      if (SLEEPING_POS_ID.equals(â˜ƒ)) {
         if (this.level.isClientSide) {
            this.getSleepingPos().ifPresent(this::setPosToBed);
         }
      } else if (DATA_LIVING_ENTITY_FLAGS.equals(â˜ƒ) && this.level.isClientSide) {
         if (this.isUsingItem() && this.useItem.isEmpty()) {
            this.useItem = this.getItemInHand(this.getUsedItemHand());
            if (!this.useItem.isEmpty()) {
               this.useItemRemaining = this.useItem.getUseDuration();
            }
         } else if (!this.isUsingItem() && !this.useItem.isEmpty()) {
            this.useItem = ItemStack.EMPTY;
            this.useItemRemaining = 0;
         }
      }
   }

   @Override
   public void lookAt(EntityAnchorArgument.Anchor var1, Vec3 var2) {
      super.lookAt(â˜ƒ, â˜ƒ);
      this.yHeadRotO = this.yHeadRot;
      this.yBodyRot = this.yHeadRot;
      this.yBodyRotO = this.yBodyRot;
   }

   protected void triggerItemUseEffects(ItemStack var1, int var2) {
      if (!â˜ƒ.isEmpty() && this.isUsingItem()) {
         if (â˜ƒ.getUseAnimation() == UseAnim.DRINK) {
            this.playSound(this.getDrinkingSound(â˜ƒ), 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
         }

         if (â˜ƒ.getUseAnimation() == UseAnim.EAT) {
            this.spawnItemParticles(â˜ƒ, â˜ƒ);
            this.playSound(
               this.getEatingSound(â˜ƒ), 0.5F + 0.5F * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F
            );
         }
      }
   }

   private void spawnItemParticles(ItemStack var1, int var2) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         Vec3 â˜ƒx = new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
         â˜ƒx = â˜ƒx.xRot(-this.getXRot() * (float) (Math.PI / 180.0));
         â˜ƒx = â˜ƒx.yRot(-this.getYRot() * (float) (Math.PI / 180.0));
         double â˜ƒxx = (double)(-this.random.nextFloat()) * 0.6 - 0.3;
         Vec3 â˜ƒxxx = new Vec3(((double)this.random.nextFloat() - 0.5) * 0.3, â˜ƒxx, 0.6);
         â˜ƒxxx = â˜ƒxxx.xRot(-this.getXRot() * (float) (Math.PI / 180.0));
         â˜ƒxxx = â˜ƒxxx.yRot(-this.getYRot() * (float) (Math.PI / 180.0));
         â˜ƒxxx = â˜ƒxxx.add(this.getX(), this.getEyeY(), this.getZ());
         this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, â˜ƒ), â˜ƒxxx.x, â˜ƒxxx.y, â˜ƒxxx.z, â˜ƒx.x, â˜ƒx.y + 0.05, â˜ƒx.z);
      }
   }

   protected void completeUsingItem() {
      InteractionHand â˜ƒ = this.getUsedItemHand();
      if (!this.useItem.equals(this.getItemInHand(â˜ƒ))) {
         this.releaseUsingItem();
      } else {
         if (!this.useItem.isEmpty() && this.isUsingItem()) {
            this.triggerItemUseEffects(this.useItem, 16);
            ItemStack â˜ƒ = this.useItem.finishUsingItem(this.level, this);
            if (â˜ƒ != this.useItem) {
               this.setItemInHand(â˜ƒ, â˜ƒ);
            }

            this.stopUsingItem();
         }
      }
   }

   public ItemStack getUseItem() {
      return this.useItem;
   }

   public int getUseItemRemainingTicks() {
      return this.useItemRemaining;
   }

   public int getTicksUsingItem() {
      return this.isUsingItem() ? this.useItem.getUseDuration() - this.getUseItemRemainingTicks() : 0;
   }

   public void releaseUsingItem() {
      if (!this.useItem.isEmpty()) {
         this.useItem.releaseUsing(this.level, this, this.getUseItemRemainingTicks());
         if (this.useItem.useOnRelease()) {
            this.updatingUsingItem();
         }
      }

      this.stopUsingItem();
   }

   public void stopUsingItem() {
      if (!this.level.isClientSide) {
         this.setLivingEntityFlag(1, false);
      }

      this.useItem = ItemStack.EMPTY;
      this.useItemRemaining = 0;
   }

   public boolean isBlocking() {
      if (this.isUsingItem() && !this.useItem.isEmpty()) {
         Item â˜ƒ = this.useItem.getItem();
         if (â˜ƒ.getUseAnimation(this.useItem) != UseAnim.BLOCK) {
            return false;
         } else {
            return â˜ƒ.getUseDuration(this.useItem) - this.useItemRemaining >= 5;
         }
      } else {
         return false;
      }
   }

   public boolean isSuppressingSlidingDownLadder() {
      return this.isShiftKeyDown();
   }

   public boolean isFallFlying() {
      return this.getSharedFlag(7);
   }

   @Override
   public boolean isVisuallySwimming() {
      return super.isVisuallySwimming() || !this.isFallFlying() && this.getPose() == Pose.FALL_FLYING;
   }

   public int getFallFlyingTicks() {
      return this.fallFlyTicks;
   }

   public boolean randomTeleport(double var1, double var3, double var5, boolean var7) {
      double â˜ƒ = this.getX();
      double â˜ƒx = this.getY();
      double â˜ƒxx = this.getZ();
      double â˜ƒxxx = â˜ƒ;
      boolean â˜ƒxxxx = false;
      BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      Level â˜ƒxxxxxx = this.level;
      if (â˜ƒxxxxxx.hasChunkAt(â˜ƒxxxxx)) {
         boolean â˜ƒxxxxxxx = false;

         while(!â˜ƒxxxxxxx && â˜ƒxxxxx.getY() > â˜ƒxxxxxx.getMinBuildHeight()) {
            BlockPos â˜ƒxxxxxxxx = â˜ƒxxxxx.below();
            BlockState â˜ƒxxxxxxxxx = â˜ƒxxxxxx.getBlockState(â˜ƒxxxxxxxx);
            if (â˜ƒxxxxxxxxx.getMaterial().blocksMotion()) {
               â˜ƒxxxxxxx = true;
            } else {
               --â˜ƒxxx;
               â˜ƒxxxxx = â˜ƒxxxxxxxx;
            }
         }

         if (â˜ƒxxxxxxx) {
            this.teleportTo(â˜ƒ, â˜ƒxxx, â˜ƒ);
            if (â˜ƒxxxxxx.noCollision(this) && !â˜ƒxxxxxx.containsAnyLiquid(this.getBoundingBox())) {
               â˜ƒxxxx = true;
            }
         }
      }

      if (!â˜ƒxxxx) {
         this.teleportTo(â˜ƒ, â˜ƒx, â˜ƒxx);
         return false;
      } else {
         if (â˜ƒ) {
            â˜ƒxxxxxx.broadcastEntityEvent(this, (byte)46);
         }

         if (this instanceof PathfinderMob) {
            ((PathfinderMob)this).getNavigation().stop();
         }

         return true;
      }
   }

   public boolean isAffectedByPotions() {
      return true;
   }

   public boolean attackable() {
      return true;
   }

   public void setRecordPlayingNearby(BlockPos var1, boolean var2) {
   }

   public boolean canTakeItem(ItemStack var1) {
      return false;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddMobPacket(this);
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      return â˜ƒ == Pose.SLEEPING ? SLEEPING_DIMENSIONS : super.getDimensions(â˜ƒ).scale(this.getScale());
   }

   public ImmutableList<Pose> getDismountPoses() {
      return ImmutableList.of(Pose.STANDING);
   }

   public AABB getLocalBoundsForPose(Pose var1) {
      EntityDimensions â˜ƒ = this.getDimensions(â˜ƒ);
      return new AABB((double)(-â˜ƒ.width / 2.0F), 0.0, (double)(-â˜ƒ.width / 2.0F), (double)(â˜ƒ.width / 2.0F), (double)â˜ƒ.height, (double)(â˜ƒ.width / 2.0F));
   }

   public Optional<BlockPos> getSleepingPos() {
      return this.entityData.get(SLEEPING_POS_ID);
   }

   public void setSleepingPos(BlockPos var1) {
      this.entityData.set(SLEEPING_POS_ID, Optional.of(â˜ƒ));
   }

   public void clearSleepingPos() {
      this.entityData.set(SLEEPING_POS_ID, Optional.empty());
   }

   public boolean isSleeping() {
      return this.getSleepingPos().isPresent();
   }

   public void startSleeping(BlockPos var1) {
      if (this.isPassenger()) {
         this.stopRiding();
      }

      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
      if (â˜ƒ.getBlock() instanceof BedBlock) {
         this.level.setBlock(â˜ƒ, â˜ƒ.setValue(BedBlock.OCCUPIED, Boolean.valueOf(true)), 3);
      }

      this.setPose(Pose.SLEEPING);
      this.setPosToBed(â˜ƒ);
      this.setSleepingPos(â˜ƒ);
      this.setDeltaMovement(Vec3.ZERO);
      this.hasImpulse = true;
   }

   private void setPosToBed(BlockPos var1) {
      this.setPos((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.6875, (double)â˜ƒ.getZ() + 0.5);
   }

   private boolean checkBedExists() {
      return this.getSleepingPos().map(var1 -> this.level.getBlockState(var1).getBlock() instanceof BedBlock).orElse(false);
   }

   public void stopSleeping() {
      this.getSleepingPos().filter(this.level::hasChunkAt).ifPresent(var1x -> {
         BlockState â˜ƒ = this.level.getBlockState(var1x);
         if (â˜ƒ.getBlock() instanceof BedBlock) {
            this.level.setBlock(var1x, â˜ƒ.setValue(BedBlock.OCCUPIED, Boolean.valueOf(false)), 3);
            Vec3 â˜ƒx = (Vec3)BedBlock.findStandUpPosition(this.getType(), this.level, var1x, this.getYRot()).orElseGet(() -> {
               BlockPos â˜ƒ = var1x.above();
               return new Vec3((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.1, (double)â˜ƒ.getZ() + 0.5);
            });
            Vec3 â˜ƒxx = Vec3.atBottomCenterOf(var1x).subtract(â˜ƒx).normalize();
            float â˜ƒxxx = (float)Mth.wrapDegrees(Mth.atan2(â˜ƒxx.z, â˜ƒxx.x) * 180.0F / (float)Math.PI - 90.0);
            this.setPos(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z);
            this.setYRot(â˜ƒxxx);
            this.setXRot(0.0F);
         }
      });
      Vec3 â˜ƒ = this.position();
      this.setPose(Pose.STANDING);
      this.setPos(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
      this.clearSleepingPos();
   }

   @Nullable
   public Direction getBedOrientation() {
      BlockPos â˜ƒ = (BlockPos)this.getSleepingPos().orElse(null);
      return â˜ƒ != null ? BedBlock.getBedOrientation(this.level, â˜ƒ) : null;
   }

   @Override
   public boolean isInWall() {
      return !this.isSleeping() && super.isInWall();
   }

   @Override
   protected final float getEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ == Pose.SLEEPING ? 0.2F : this.getStandingEyeHeight(â˜ƒ, â˜ƒ);
   }

   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return super.getEyeHeight(â˜ƒ, â˜ƒ);
   }

   public ItemStack getProjectile(ItemStack var1) {
      return ItemStack.EMPTY;
   }

   public ItemStack eat(Level var1, ItemStack var2) {
      if (â˜ƒ.isEdible()) {
         â˜ƒ.gameEvent(this, GameEvent.EAT, this.eyeBlockPosition());
         â˜ƒ.playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            this.getEatingSound(â˜ƒ),
            SoundSource.NEUTRAL,
            1.0F,
            1.0F + (â˜ƒ.random.nextFloat() - â˜ƒ.random.nextFloat()) * 0.4F
         );
         this.addEatEffect(â˜ƒ, â˜ƒ, this);
         if (!(this instanceof Player) || !((Player)this).getAbilities().instabuild) {
            â˜ƒ.shrink(1);
         }

         this.gameEvent(GameEvent.EAT);
      }

      return â˜ƒ;
   }

   private void addEatEffect(ItemStack var1, Level var2, LivingEntity var3) {
      Item â˜ƒ = â˜ƒ.getItem();
      if (â˜ƒ.isEdible()) {
         for(Pair<MobEffectInstance, Float> â˜ƒx : â˜ƒ.getFoodProperties().getEffects()) {
            if (!â˜ƒ.isClientSide && â˜ƒx.getFirst() != null && â˜ƒ.random.nextFloat() < â˜ƒx.getSecond()) {
               â˜ƒ.addEffect(new MobEffectInstance(â˜ƒx.getFirst()));
            }
         }
      }
   }

   private static byte entityEventForEquipmentBreak(EquipmentSlot var0) {
      switch(â˜ƒ) {
         case MAINHAND:
            return 47;
         case OFFHAND:
            return 48;
         case HEAD:
            return 49;
         case CHEST:
            return 50;
         case FEET:
            return 52;
         case LEGS:
            return 51;
         default:
            return 47;
      }
   }

   public void broadcastBreakEvent(EquipmentSlot var1) {
      this.level.broadcastEntityEvent(this, entityEventForEquipmentBreak(â˜ƒ));
   }

   public void broadcastBreakEvent(InteractionHand var1) {
      this.broadcastBreakEvent(â˜ƒ == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
   }

   @Override
   public AABB getBoundingBoxForCulling() {
      if (this.getItemBySlot(EquipmentSlot.HEAD).is(Items.DRAGON_HEAD)) {
         float â˜ƒ = 0.5F;
         return this.getBoundingBox().inflate(0.5, 0.5, 0.5);
      } else {
         return super.getBoundingBoxForCulling();
      }
   }

   public static EquipmentSlot getEquipmentSlotForItem(ItemStack var0) {
      Item â˜ƒ = â˜ƒ.getItem();
      if (!â˜ƒ.is(Items.CARVED_PUMPKIN) && (!(â˜ƒ instanceof BlockItem) || !(((BlockItem)â˜ƒ).getBlock() instanceof AbstractSkullBlock))) {
         if (â˜ƒ instanceof ArmorItem) {
            return ((ArmorItem)â˜ƒ).getSlot();
         } else if (â˜ƒ.is(Items.ELYTRA)) {
            return EquipmentSlot.CHEST;
         } else {
            return â˜ƒ.is(Items.SHIELD) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
         }
      } else {
         return EquipmentSlot.HEAD;
      }
   }

   private static SlotAccess createEquipmentSlotAccess(LivingEntity var0, EquipmentSlot var1) {
      return â˜ƒ != EquipmentSlot.HEAD && â˜ƒ != EquipmentSlot.MAINHAND && â˜ƒ != EquipmentSlot.OFFHAND
         ? SlotAccess.forEquipmentSlot(â˜ƒ, â˜ƒ, var1x -> var1x.isEmpty() || Mob.getEquipmentSlotForItem(var1x) == â˜ƒ)
         : SlotAccess.forEquipmentSlot(â˜ƒ, â˜ƒ);
   }

   @Nullable
   private static EquipmentSlot getEquipmentSlot(int var0) {
      if (â˜ƒ == 100 + EquipmentSlot.HEAD.getIndex()) {
         return EquipmentSlot.HEAD;
      } else if (â˜ƒ == 100 + EquipmentSlot.CHEST.getIndex()) {
         return EquipmentSlot.CHEST;
      } else if (â˜ƒ == 100 + EquipmentSlot.LEGS.getIndex()) {
         return EquipmentSlot.LEGS;
      } else if (â˜ƒ == 100 + EquipmentSlot.FEET.getIndex()) {
         return EquipmentSlot.FEET;
      } else if (â˜ƒ == 98) {
         return EquipmentSlot.MAINHAND;
      } else {
         return â˜ƒ == 99 ? EquipmentSlot.OFFHAND : null;
      }
   }

   @Override
   public SlotAccess getSlot(int var1) {
      EquipmentSlot â˜ƒ = getEquipmentSlot(â˜ƒ);
      return â˜ƒ != null ? createEquipmentSlotAccess(this, â˜ƒ) : super.getSlot(â˜ƒ);
   }

   @Override
   public boolean canFreeze() {
      if (this.isSpectator()) {
         return false;
      } else {
         boolean â˜ƒ = !this.getItemBySlot(EquipmentSlot.HEAD).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
            && !this.getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
            && !this.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
            && !this.getItemBySlot(EquipmentSlot.FEET).is(ItemTags.FREEZE_IMMUNE_WEARABLES);
         return â˜ƒ && super.canFreeze();
      }
   }

   @Override
   public boolean isCurrentlyGlowing() {
      return !this.level.isClientSide() && this.hasEffect(MobEffects.GLOWING) || super.isCurrentlyGlowing();
   }

   public void recreateFromPacket(ClientboundAddMobPacket var1) {
      double â˜ƒ = â˜ƒ.getX();
      double â˜ƒx = â˜ƒ.getY();
      double â˜ƒxx = â˜ƒ.getZ();
      float â˜ƒxxx = (float)(â˜ƒ.getyRot() * 360) / 256.0F;
      float â˜ƒxxxx = (float)(â˜ƒ.getxRot() * 360) / 256.0F;
      this.setPacketCoordinates(â˜ƒ, â˜ƒx, â˜ƒxx);
      this.yBodyRot = (float)(â˜ƒ.getyHeadRot() * 360) / 256.0F;
      this.yHeadRot = (float)(â˜ƒ.getyHeadRot() * 360) / 256.0F;
      this.setId(â˜ƒ.getId());
      this.setUUID(â˜ƒ.getUUID());
      this.absMoveTo(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      this.setDeltaMovement((double)((float)â˜ƒ.getXd() / 8000.0F), (double)((float)â˜ƒ.getYd() / 8000.0F), (double)((float)â˜ƒ.getZd() / 8000.0F));
   }
}
