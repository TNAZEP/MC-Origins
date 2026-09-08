package net.minecraft.world.entity.animal.goat;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class Goat extends Animal {
   public static final EntityDimensions LONG_JUMPING_DIMENSIONS = EntityDimensions.scalable(0.9F, 1.3F).scale(0.7F);
   private static final int ADULT_ATTACK_DAMAGE = 2;
   private static final int BABY_ATTACK_DAMAGE = 1;
   protected static final ImmutableList<SensorType<? extends Sensor<? super Goat>>> SENSOR_TYPES = ImmutableList.of(
      SensorType.NEAREST_LIVING_ENTITIES,
      SensorType.NEAREST_PLAYERS,
      SensorType.NEAREST_ITEMS,
      SensorType.NEAREST_ADULT,
      SensorType.HURT_BY,
      SensorType.GOAT_TEMPTATIONS
   );
   protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
      MemoryModuleType.LOOK_TARGET,
      MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
      MemoryModuleType.WALK_TARGET,
      MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
      MemoryModuleType.PATH,
      MemoryModuleType.ATE_RECENTLY,
      MemoryModuleType.BREED_TARGET,
      MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS,
      MemoryModuleType.LONG_JUMP_MID_JUMP,
      MemoryModuleType.TEMPTING_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_ADULT,
      MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
      MemoryModuleType.IS_TEMPTED,
      MemoryModuleType.RAM_COOLDOWN_TICKS,
      MemoryModuleType.RAM_TARGET
   );
   public static final int GOAT_FALL_DAMAGE_REDUCTION = 10;
   public static final double GOAT_SCREAMING_CHANCE = 0.02;
   private static final EntityDataAccessor<Boolean> DATA_IS_SCREAMING_GOAT = SynchedEntityData.defineId(Goat.class, EntityDataSerializers.BOOLEAN);
   private boolean isLoweringHead;
   private int lowerHeadTick;

   public Goat(EntityType<? extends Goat> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.getNavigation().setCanFloat(true);
   }

   @Override
   protected Brain.Provider<Goat> brainProvider() {
      return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
   }

   @Override
   protected Brain<?> makeBrain(Dynamic<?> var1) {
      return GoatAi.makeBrain(this.brainProvider().makeBrain(â˜ƒ));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.ATTACK_DAMAGE, 2.0);
   }

   @Override
   protected void ageBoundaryReached() {
      if (this.isBaby()) {
         this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.0);
      } else {
         this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0);
      }
   }

   @Override
   protected int calculateFallDamage(float var1, float var2) {
      return super.calculateFallDamage(â˜ƒ, â˜ƒ) - 10;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_AMBIENT : SoundEvents.GOAT_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return this.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_HURT : SoundEvents.GOAT_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return this.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_DEATH : SoundEvents.GOAT_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(SoundEvents.GOAT_STEP, 0.15F, 1.0F);
   }

   protected SoundEvent getMilkingSound() {
      return this.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_MILK : SoundEvents.GOAT_MILK;
   }

   public Goat getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      Goat â˜ƒ = EntityType.GOAT.create(â˜ƒ);
      if (â˜ƒ != null) {
         GoatAi.initMemories(â˜ƒ);
         boolean â˜ƒx = â˜ƒ instanceof Goat && ((Goat)â˜ƒ).isScreamingGoat();
         â˜ƒ.setScreamingGoat(â˜ƒx || â˜ƒ.getRandom().nextDouble() < 0.02);
      }

      return â˜ƒ;
   }

   @Override
   public Brain<Goat> getBrain() {
      return super.getBrain();
   }

   @Override
   protected void customServerAiStep() {
      this.level.getProfiler().push("goatBrain");
      this.getBrain().tick((ServerLevel)this.level, this);
      this.level.getProfiler().pop();
      this.level.getProfiler().push("goatActivityUpdate");
      GoatAi.updateActivity(this);
      this.level.getProfiler().pop();
      super.customServerAiStep();
   }

   @Override
   public int getMaxHeadYRot() {
      return 15;
   }

   @Override
   public void setYHeadRot(float var1) {
      int â˜ƒ = this.getMaxHeadYRot();
      float â˜ƒx = Mth.degreesDifference(this.yBodyRot, â˜ƒ);
      float â˜ƒxx = Mth.clamp(â˜ƒx, (float)(-â˜ƒ), (float)â˜ƒ);
      super.setYHeadRot(this.yBodyRot + â˜ƒxx);
   }

   @Override
   public SoundEvent getEatingSound(ItemStack var1) {
      return this.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_EAT : SoundEvents.GOAT_EAT;
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.BUCKET) && !this.isBaby()) {
         â˜ƒ.playSound(this.getMilkingSound(), 1.0F, 1.0F);
         ItemStack â˜ƒx = ItemUtils.createFilledResult(â˜ƒ, â˜ƒ, Items.MILK_BUCKET.getDefaultInstance());
         â˜ƒ.setItemInHand(â˜ƒ, â˜ƒx);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         InteractionResult â˜ƒ = super.mobInteract(â˜ƒ, â˜ƒ);
         if (â˜ƒ.consumesAction() && this.isFood(â˜ƒ)) {
            this.level.playSound(null, this, this.getEatingSound(â˜ƒ), SoundSource.NEUTRAL, 1.0F, Mth.randomBetween(this.level.random, 0.8F, 1.2F));
         }

         return â˜ƒ;
      }
   }

   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      GoatAi.initMemories(this);
      this.setScreamingGoat(â˜ƒ.getRandom().nextDouble() < 0.02);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void sendDebugPackets() {
      super.sendDebugPackets();
      DebugPackets.sendEntityBrain(this);
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      return â˜ƒ == Pose.LONG_JUMPING ? LONG_JUMPING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putBoolean("IsScreamingGoat", this.isScreamingGoat());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setScreamingGoat(â˜ƒ.getBoolean("IsScreamingGoat"));
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 58) {
         this.isLoweringHead = true;
      } else if (â˜ƒ == 59) {
         this.isLoweringHead = false;
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   @Override
   public void aiStep() {
      if (this.isLoweringHead) {
         ++this.lowerHeadTick;
      } else {
         this.lowerHeadTick -= 2;
      }

      this.lowerHeadTick = Mth.clamp(this.lowerHeadTick, 0, 20);
      super.aiStep();
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_IS_SCREAMING_GOAT, false);
   }

   public boolean isScreamingGoat() {
      return this.entityData.get(DATA_IS_SCREAMING_GOAT);
   }

   public void setScreamingGoat(boolean var1) {
      this.entityData.set(DATA_IS_SCREAMING_GOAT, â˜ƒ);
   }

   public float getRammingXHeadRot() {
      return (float)this.lowerHeadTick / 20.0F * 30.0F * (float) (Math.PI / 180.0);
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      return new Goat.GoatPathNavigation(this, â˜ƒ);
   }

   static class GoatNodeEvaluator extends WalkNodeEvaluator {
      private final BlockPos.MutableBlockPos belowPos = new BlockPos.MutableBlockPos();

      @Override
      public BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4) {
         this.belowPos.set(â˜ƒ, â˜ƒ - 1, â˜ƒ);
         BlockPathTypes â˜ƒ = getBlockPathTypeRaw(â˜ƒ, this.belowPos);
         return â˜ƒ == BlockPathTypes.POWDER_SNOW ? BlockPathTypes.BLOCKED : getBlockPathTypeStatic(â˜ƒ, this.belowPos.move(Direction.UP));
      }
   }

   static class GoatPathNavigation extends GroundPathNavigation {
      GoatPathNavigation(Goat var1, Level var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected PathFinder createPathFinder(int var1) {
         this.nodeEvaluator = new Goat.GoatNodeEvaluator();
         return new PathFinder(this.nodeEvaluator, â˜ƒ);
      }
   }
}
