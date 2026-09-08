package net.minecraft.world.entity.animal;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.CatLieOnBedGoal;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;

public class Cat extends TamableAnimal {
   public static final double TEMPT_SPEED_MOD = 0.6;
   public static final double WALK_SPEED_MOD = 0.8;
   public static final double SPRINT_SPEED_MOD = 1.33;
   private static final Ingredient TEMPT_INGREDIENT = Ingredient.of(Items.COD, Items.SALMON);
   private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.INT);
   public static final int TYPE_TABBY = 0;
   public static final int TYPE_BLACK = 1;
   public static final int TYPE_RED = 2;
   public static final int TYPE_SIAMESE = 3;
   public static final int TYPE_BRITISH = 4;
   public static final int TYPE_CALICO = 5;
   public static final int TYPE_PERSIAN = 6;
   public static final int TYPE_RAGDOLL = 7;
   public static final int TYPE_WHITE = 8;
   public static final int TYPE_JELLIE = 9;
   public static final int TYPE_ALL_BLACK = 10;
   private static final int NUMBER_OF_CAT_TYPES = 11;
   private static final int NUMBER_OF_CAT_TYPES_EXCEPT_ALL_BLACK = 10;
   public static final Map<Integer, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), var0 -> {
      var0.put(0, new ResourceLocation("textures/entity/cat/tabby.png"));
      var0.put(1, new ResourceLocation("textures/entity/cat/black.png"));
      var0.put(2, new ResourceLocation("textures/entity/cat/red.png"));
      var0.put(3, new ResourceLocation("textures/entity/cat/siamese.png"));
      var0.put(4, new ResourceLocation("textures/entity/cat/british_shorthair.png"));
      var0.put(5, new ResourceLocation("textures/entity/cat/calico.png"));
      var0.put(6, new ResourceLocation("textures/entity/cat/persian.png"));
      var0.put(7, new ResourceLocation("textures/entity/cat/ragdoll.png"));
      var0.put(8, new ResourceLocation("textures/entity/cat/white.png"));
      var0.put(9, new ResourceLocation("textures/entity/cat/jellie.png"));
      var0.put(10, new ResourceLocation("textures/entity/cat/all_black.png"));
   });
   private Cat.CatAvoidEntityGoal<Player> avoidPlayersGoal;
   private TemptGoal temptGoal;
   private float lieDownAmount;
   private float lieDownAmountO;
   private float lieDownAmountTail;
   private float lieDownAmountOTail;
   private float relaxStateOneAmount;
   private float relaxStateOneAmountO;

   public Cat(EntityType<? extends Cat> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getResourceLocation() {
      return (ResourceLocation)TEXTURE_BY_TYPE.getOrDefault(this.getCatType(), (ResourceLocation)TEXTURE_BY_TYPE.get(0));
   }

   @Override
   protected void registerGoals() {
      this.temptGoal = new Cat.CatTemptGoal(this, 0.6, TEMPT_INGREDIENT, true);
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
      this.goalSelector.addGoal(2, new Cat.CatRelaxOnOwnerGoal(this));
      this.goalSelector.addGoal(3, this.temptGoal);
      this.goalSelector.addGoal(5, new CatLieOnBedGoal(this, 1.1, 8));
      this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, false));
      this.goalSelector.addGoal(7, new CatSitOnBlockGoal(this, 0.8));
      this.goalSelector.addGoal(8, new LeapAtTargetGoal(this, 0.3F));
      this.goalSelector.addGoal(9, new OcelotAttackGoal(this));
      this.goalSelector.addGoal(10, new BreedGoal(this, 0.8));
      this.goalSelector.addGoal(11, new WaterAvoidingRandomStrollGoal(this, 0.8, 1.0000001E-5F));
      this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 10.0F));
      this.targetSelector.addGoal(1, new NonTameRandomTargetGoal(this, Rabbit.class, false, null));
      this.targetSelector.addGoal(1, new NonTameRandomTargetGoal(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
   }

   public int getCatType() {
      return this.entityData.get(DATA_TYPE_ID);
   }

   public void setCatType(int var1) {
      if (â˜ƒ < 0 || â˜ƒ >= 11) {
         â˜ƒ = this.random.nextInt(10);
      }

      this.entityData.set(DATA_TYPE_ID, â˜ƒ);
   }

   public void setLying(boolean var1) {
      this.entityData.set(IS_LYING, â˜ƒ);
   }

   public boolean isLying() {
      return this.entityData.get(IS_LYING);
   }

   public void setRelaxStateOne(boolean var1) {
      this.entityData.set(RELAX_STATE_ONE, â˜ƒ);
   }

   public boolean isRelaxStateOne() {
      return this.entityData.get(RELAX_STATE_ONE);
   }

   public DyeColor getCollarColor() {
      return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
   }

   public void setCollarColor(DyeColor var1) {
      this.entityData.set(DATA_COLLAR_COLOR, â˜ƒ.getId());
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_TYPE_ID, 1);
      this.entityData.define(IS_LYING, false);
      this.entityData.define(RELAX_STATE_ONE, false);
      this.entityData.define(DATA_COLLAR_COLOR, DyeColor.RED.getId());
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("CatType", this.getCatType());
      â˜ƒ.putByte("CollarColor", (byte)this.getCollarColor().getId());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setCatType(â˜ƒ.getInt("CatType"));
      if (â˜ƒ.contains("CollarColor", 99)) {
         this.setCollarColor(DyeColor.byId(â˜ƒ.getInt("CollarColor")));
      }
   }

   @Override
   public void customServerAiStep() {
      if (this.getMoveControl().hasWanted()) {
         double â˜ƒ = this.getMoveControl().getSpeedModifier();
         if (â˜ƒ == 0.6) {
            this.setPose(Pose.CROUCHING);
            this.setSprinting(false);
         } else if (â˜ƒ == 1.33) {
            this.setPose(Pose.STANDING);
            this.setSprinting(true);
         } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
         }
      } else {
         this.setPose(Pose.STANDING);
         this.setSprinting(false);
      }
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      if (this.isTame()) {
         if (this.isInLove()) {
            return SoundEvents.CAT_PURR;
         } else {
            return this.random.nextInt(4) == 0 ? SoundEvents.CAT_PURREOW : SoundEvents.CAT_AMBIENT;
         }
      } else {
         return SoundEvents.CAT_STRAY_AMBIENT;
      }
   }

   @Override
   public int getAmbientSoundInterval() {
      return 120;
   }

   public void hiss() {
      this.playSound(SoundEvents.CAT_HISS, this.getSoundVolume(), this.getVoicePitch());
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.CAT_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.CAT_DEATH;
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 3.0);
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      return false;
   }

   @Override
   protected void usePlayerItem(Player var1, InteractionHand var2, ItemStack var3) {
      if (this.isFood(â˜ƒ)) {
         this.playSound(SoundEvents.CAT_EAT, 1.0F, 1.0F);
      }

      super.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private float getAttackDamage() {
      return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      return â˜ƒ.hurt(DamageSource.mobAttack(this), this.getAttackDamage());
   }

   @Override
   public void tick() {
      super.tick();
      if (this.temptGoal != null && this.temptGoal.isRunning() && !this.isTame() && this.tickCount % 100 == 0) {
         this.playSound(SoundEvents.CAT_BEG_FOR_FOOD, 1.0F, 1.0F);
      }

      this.handleLieDown();
   }

   private void handleLieDown() {
      if ((this.isLying() || this.isRelaxStateOne()) && this.tickCount % 5 == 0) {
         this.playSound(SoundEvents.CAT_PURR, 0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), 1.0F);
      }

      this.updateLieDownAmount();
      this.updateRelaxStateOneAmount();
   }

   private void updateLieDownAmount() {
      this.lieDownAmountO = this.lieDownAmount;
      this.lieDownAmountOTail = this.lieDownAmountTail;
      if (this.isLying()) {
         this.lieDownAmount = Math.min(1.0F, this.lieDownAmount + 0.15F);
         this.lieDownAmountTail = Math.min(1.0F, this.lieDownAmountTail + 0.08F);
      } else {
         this.lieDownAmount = Math.max(0.0F, this.lieDownAmount - 0.22F);
         this.lieDownAmountTail = Math.max(0.0F, this.lieDownAmountTail - 0.13F);
      }
   }

   private void updateRelaxStateOneAmount() {
      this.relaxStateOneAmountO = this.relaxStateOneAmount;
      if (this.isRelaxStateOne()) {
         this.relaxStateOneAmount = Math.min(1.0F, this.relaxStateOneAmount + 0.1F);
      } else {
         this.relaxStateOneAmount = Math.max(0.0F, this.relaxStateOneAmount - 0.13F);
      }
   }

   public float getLieDownAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.lieDownAmountO, this.lieDownAmount);
   }

   public float getLieDownAmountTail(float var1) {
      return Mth.lerp(â˜ƒ, this.lieDownAmountOTail, this.lieDownAmountTail);
   }

   public float getRelaxStateOneAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.relaxStateOneAmountO, this.relaxStateOneAmount);
   }

   public Cat getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      Cat â˜ƒ = EntityType.CAT.create(â˜ƒ);
      if (â˜ƒ instanceof Cat) {
         if (this.random.nextBoolean()) {
            â˜ƒ.setCatType(this.getCatType());
         } else {
            â˜ƒ.setCatType(((Cat)â˜ƒ).getCatType());
         }

         if (this.isTame()) {
            â˜ƒ.setOwnerUUID(this.getOwnerUUID());
            â˜ƒ.setTame(true);
            if (this.random.nextBoolean()) {
               â˜ƒ.setCollarColor(this.getCollarColor());
            } else {
               â˜ƒ.setCollarColor(((Cat)â˜ƒ).getCollarColor());
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   public boolean canMate(Animal var1) {
      if (!this.isTame()) {
         return false;
      } else if (!(â˜ƒ instanceof Cat)) {
         return false;
      } else {
         Cat â˜ƒ = (Cat)â˜ƒ;
         return â˜ƒ.isTame() && super.canMate(â˜ƒ);
      }
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      â˜ƒ = super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.getMoonBrightness() > 0.9F) {
         this.setCatType(this.random.nextInt(11));
      } else {
         this.setCatType(this.random.nextInt(10));
      }

      Level â˜ƒ = â˜ƒ.getLevel();
      if (â˜ƒ instanceof ServerLevel
         && ((ServerLevel)â˜ƒ).structureFeatureManager().getStructureAt(this.blockPosition(), true, StructureFeature.SWAMP_HUT).isValid()) {
         this.setCatType(10);
         this.setPersistenceRequired();
      }

      return â˜ƒ;
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      Item â˜ƒx = â˜ƒ.getItem();
      if (this.level.isClientSide) {
         if (this.isTame() && this.isOwnedBy(â˜ƒ)) {
            return InteractionResult.SUCCESS;
         } else {
            return !this.isFood(â˜ƒ) || !(this.getHealth() < this.getMaxHealth()) && this.isTame() ? InteractionResult.PASS : InteractionResult.SUCCESS;
         }
      } else {
         if (this.isTame()) {
            if (this.isOwnedBy(â˜ƒ)) {
               if (!(â˜ƒx instanceof DyeItem)) {
                  if (â˜ƒx.isEdible() && this.isFood(â˜ƒ) && this.getHealth() < this.getMaxHealth()) {
                     this.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
                     this.heal((float)â˜ƒx.getFoodProperties().getNutrition());
                     return InteractionResult.CONSUME;
                  }

                  InteractionResult â˜ƒ = super.mobInteract(â˜ƒ, â˜ƒ);
                  if (!â˜ƒ.consumesAction() || this.isBaby()) {
                     this.setOrderedToSit(!this.isOrderedToSit());
                  }

                  return â˜ƒ;
               }

               DyeColor â˜ƒ = ((DyeItem)â˜ƒx).getDyeColor();
               if (â˜ƒ != this.getCollarColor()) {
                  this.setCollarColor(â˜ƒ);
                  if (!â˜ƒ.getAbilities().instabuild) {
                     â˜ƒ.shrink(1);
                  }

                  this.setPersistenceRequired();
                  return InteractionResult.CONSUME;
               }
            }
         } else if (this.isFood(â˜ƒ)) {
            this.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
            if (this.random.nextInt(3) == 0) {
               this.tame(â˜ƒ);
               this.setOrderedToSit(true);
               this.level.broadcastEntityEvent(this, (byte)7);
            } else {
               this.level.broadcastEntityEvent(this, (byte)6);
            }

            this.setPersistenceRequired();
            return InteractionResult.CONSUME;
         }

         InteractionResult â˜ƒ = super.mobInteract(â˜ƒ, â˜ƒ);
         if (â˜ƒ.consumesAction()) {
            this.setPersistenceRequired();
         }

         return â˜ƒ;
      }
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return TEMPT_INGREDIENT.test(â˜ƒ);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * 0.5F;
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return !this.isTame() && this.tickCount > 2400;
   }

   @Override
   protected void reassessTameGoals() {
      if (this.avoidPlayersGoal == null) {
         this.avoidPlayersGoal = new Cat.CatAvoidEntityGoal<>(this, Player.class, 16.0F, 0.8, 1.33);
      }

      this.goalSelector.removeGoal(this.avoidPlayersGoal);
      if (!this.isTame()) {
         this.goalSelector.addGoal(4, this.avoidPlayersGoal);
      }
   }

   @Override
   public boolean isSteppingCarefully() {
      return this.getPose() == Pose.CROUCHING || super.isSteppingCarefully();
   }

   static class CatAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final Cat cat;

      public CatAvoidEntityGoal(Cat var1, Class<T> var2, float var3, double var4, double var6) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
         this.cat = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return !this.cat.isTame() && super.canUse();
      }

      @Override
      public boolean canContinueToUse() {
         return !this.cat.isTame() && super.canContinueToUse();
      }
   }

   static class CatRelaxOnOwnerGoal extends Goal {
      private final Cat cat;
      private Player ownerPlayer;
      private BlockPos goalPos;
      private int onBedTicks;

      public CatRelaxOnOwnerGoal(Cat var1) {
         this.cat = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         if (!this.cat.isTame()) {
            return false;
         } else if (this.cat.isOrderedToSit()) {
            return false;
         } else {
            LivingEntity â˜ƒ = this.cat.getOwner();
            if (â˜ƒ instanceof Player) {
               this.ownerPlayer = (Player)â˜ƒ;
               if (!â˜ƒ.isSleeping()) {
                  return false;
               }

               if (this.cat.distanceToSqr(this.ownerPlayer) > 100.0) {
                  return false;
               }

               BlockPos â˜ƒx = this.ownerPlayer.blockPosition();
               BlockState â˜ƒxx = this.cat.level.getBlockState(â˜ƒx);
               if (â˜ƒxx.is(BlockTags.BEDS)) {
                  this.goalPos = (BlockPos)â˜ƒxx.getOptionalValue(BedBlock.FACING)
                     .map(var1x -> â˜ƒ.relative(var1x.getOpposite()))
                     .orElseGet(() -> new BlockPos(â˜ƒ));
                  return !this.spaceIsOccupied();
               }
            }

            return false;
         }
      }

      private boolean spaceIsOccupied() {
         for(Cat â˜ƒ : this.cat.level.getEntitiesOfClass(Cat.class, new AABB(this.goalPos).inflate(2.0))) {
            if (â˜ƒ != this.cat && (â˜ƒ.isLying() || â˜ƒ.isRelaxStateOne())) {
               return true;
            }
         }

         return false;
      }

      @Override
      public boolean canContinueToUse() {
         return this.cat.isTame()
            && !this.cat.isOrderedToSit()
            && this.ownerPlayer != null
            && this.ownerPlayer.isSleeping()
            && this.goalPos != null
            && !this.spaceIsOccupied();
      }

      @Override
      public void start() {
         if (this.goalPos != null) {
            this.cat.setInSittingPose(false);
            this.cat.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), 1.1F);
         }
      }

      @Override
      public void stop() {
         this.cat.setLying(false);
         float â˜ƒ = this.cat.level.getTimeOfDay(1.0F);
         if (this.ownerPlayer.getSleepTimer() >= 100 && (double)â˜ƒ > 0.77 && (double)â˜ƒ < 0.8 && (double)this.cat.level.getRandom().nextFloat() < 0.7) {
            this.giveMorningGift();
         }

         this.onBedTicks = 0;
         this.cat.setRelaxStateOne(false);
         this.cat.getNavigation().stop();
      }

      private void giveMorningGift() {
         Random â˜ƒ = this.cat.getRandom();
         BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();
         â˜ƒx.set(this.cat.blockPosition());
         this.cat
            .randomTeleport(
               (double)(â˜ƒx.getX() + â˜ƒ.nextInt(11) - 5), (double)(â˜ƒx.getY() + â˜ƒ.nextInt(5) - 2), (double)(â˜ƒx.getZ() + â˜ƒ.nextInt(11) - 5), false
            );
         â˜ƒx.set(this.cat.blockPosition());
         LootTable â˜ƒxx = this.cat.level.getServer().getLootTables().get(BuiltInLootTables.CAT_MORNING_GIFT);
         LootContext.Builder â˜ƒxxx = new LootContext.Builder((ServerLevel)this.cat.level)
            .withParameter(LootContextParams.ORIGIN, this.cat.position())
            .withParameter(LootContextParams.THIS_ENTITY, this.cat)
            .withRandom(â˜ƒ);

         for(ItemStack â˜ƒxxxx : â˜ƒxx.getRandomItems(â˜ƒxxx.create(LootContextParamSets.GIFT))) {
            this.cat
               .level
               .addFreshEntity(
                  new ItemEntity(
                     this.cat.level,
                     (double)â˜ƒx.getX() - (double)Mth.sin(this.cat.yBodyRot * (float) (Math.PI / 180.0)),
                     (double)â˜ƒx.getY(),
                     (double)â˜ƒx.getZ() + (double)Mth.cos(this.cat.yBodyRot * (float) (Math.PI / 180.0)),
                     â˜ƒxxxx
                  )
               );
         }
      }

      @Override
      public void tick() {
         if (this.ownerPlayer != null && this.goalPos != null) {
            this.cat.setInSittingPose(false);
            this.cat.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), 1.1F);
            if (this.cat.distanceToSqr(this.ownerPlayer) < 2.5) {
               ++this.onBedTicks;
               if (this.onBedTicks > 16) {
                  this.cat.setLying(true);
                  this.cat.setRelaxStateOne(false);
               } else {
                  this.cat.lookAt(this.ownerPlayer, 45.0F, 45.0F);
                  this.cat.setRelaxStateOne(true);
               }
            } else {
               this.cat.setLying(false);
            }
         }
      }
   }

   static class CatTemptGoal extends TemptGoal {
      @Nullable
      private Player selectedPlayer;
      private final Cat cat;

      public CatTemptGoal(Cat var1, double var2, Ingredient var4, boolean var5) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.cat = â˜ƒ;
      }

      @Override
      public void tick() {
         super.tick();
         if (this.selectedPlayer == null && this.mob.getRandom().nextInt(600) == 0) {
            this.selectedPlayer = this.player;
         } else if (this.mob.getRandom().nextInt(500) == 0) {
            this.selectedPlayer = null;
         }
      }

      @Override
      protected boolean canScare() {
         return this.selectedPlayer != null && this.selectedPlayer.equals(this.player) ? false : super.canScare();
      }

      @Override
      public boolean canUse() {
         return super.canUse() && !this.cat.isTame();
      }
   }
}
