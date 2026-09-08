package net.minecraft.world.entity.animal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Panda extends Animal {
   private static final EntityDataAccessor<Integer> UNHAPPY_COUNTER = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> SNEEZE_COUNTER = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> EAT_COUNTER = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Byte> MAIN_GENE_ID = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Byte> HIDDEN_GENE_ID = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.BYTE);
   static final TargetingConditions BREED_TARGETING = TargetingConditions.forNonCombat().range(8.0);
   private static final int FLAG_SNEEZE = 2;
   private static final int FLAG_ROLL = 4;
   private static final int FLAG_SIT = 8;
   private static final int FLAG_ON_BACK = 16;
   private static final int EAT_TICK_INTERVAL = 5;
   public static final int TOTAL_ROLL_STEPS = 32;
   private static final int TOTAL_UNHAPPY_TIME = 32;
   boolean gotBamboo;
   boolean didBite;
   public int rollCounter;
   private Vec3 rollDelta;
   private float sitAmount;
   private float sitAmountO;
   private float onBackAmount;
   private float onBackAmountO;
   private float rollAmount;
   private float rollAmountO;
   Panda.PandaLookAtPlayerGoal lookAtPlayerGoal;
   static final Predicate<ItemEntity> PANDA_ITEMS = var0 -> {
      ItemStack â˜ƒ = var0.getItem();
      return (â˜ƒ.is(Blocks.BAMBOO.asItem()) || â˜ƒ.is(Blocks.CAKE.asItem())) && var0.isAlive() && !var0.hasPickUpDelay();
   };

   public Panda(EntityType<? extends Panda> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.moveControl = new Panda.PandaMoveControl(this);
      if (!this.isBaby()) {
         this.setCanPickUpLoot(true);
      }
   }

   @Override
   public boolean canTakeItem(ItemStack var1) {
      EquipmentSlot â˜ƒ = Mob.getEquipmentSlotForItem(â˜ƒ);
      if (!this.getItemBySlot(â˜ƒ).isEmpty()) {
         return false;
      } else {
         return â˜ƒ == EquipmentSlot.MAINHAND && super.canTakeItem(â˜ƒ);
      }
   }

   public int getUnhappyCounter() {
      return this.entityData.get(UNHAPPY_COUNTER);
   }

   public void setUnhappyCounter(int var1) {
      this.entityData.set(UNHAPPY_COUNTER, â˜ƒ);
   }

   public boolean isSneezing() {
      return this.getFlag(2);
   }

   public boolean isSitting() {
      return this.getFlag(8);
   }

   public void sit(boolean var1) {
      this.setFlag(8, â˜ƒ);
   }

   public boolean isOnBack() {
      return this.getFlag(16);
   }

   public void setOnBack(boolean var1) {
      this.setFlag(16, â˜ƒ);
   }

   public boolean isEating() {
      return this.entityData.get(EAT_COUNTER) > 0;
   }

   public void eat(boolean var1) {
      this.entityData.set(EAT_COUNTER, â˜ƒ ? 1 : 0);
   }

   private int getEatCounter() {
      return this.entityData.get(EAT_COUNTER);
   }

   private void setEatCounter(int var1) {
      this.entityData.set(EAT_COUNTER, â˜ƒ);
   }

   public void sneeze(boolean var1) {
      this.setFlag(2, â˜ƒ);
      if (!â˜ƒ) {
         this.setSneezeCounter(0);
      }
   }

   public int getSneezeCounter() {
      return this.entityData.get(SNEEZE_COUNTER);
   }

   public void setSneezeCounter(int var1) {
      this.entityData.set(SNEEZE_COUNTER, â˜ƒ);
   }

   public Panda.Gene getMainGene() {
      return Panda.Gene.byId(this.entityData.get(MAIN_GENE_ID));
   }

   public void setMainGene(Panda.Gene var1) {
      if (â˜ƒ.getId() > 6) {
         â˜ƒ = Panda.Gene.getRandom(this.random);
      }

      this.entityData.set(MAIN_GENE_ID, (byte)â˜ƒ.getId());
   }

   public Panda.Gene getHiddenGene() {
      return Panda.Gene.byId(this.entityData.get(HIDDEN_GENE_ID));
   }

   public void setHiddenGene(Panda.Gene var1) {
      if (â˜ƒ.getId() > 6) {
         â˜ƒ = Panda.Gene.getRandom(this.random);
      }

      this.entityData.set(HIDDEN_GENE_ID, (byte)â˜ƒ.getId());
   }

   public boolean isRolling() {
      return this.getFlag(4);
   }

   public void roll(boolean var1) {
      this.setFlag(4, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(UNHAPPY_COUNTER, 0);
      this.entityData.define(SNEEZE_COUNTER, 0);
      this.entityData.define(MAIN_GENE_ID, (byte)0);
      this.entityData.define(HIDDEN_GENE_ID, (byte)0);
      this.entityData.define(DATA_ID_FLAGS, (byte)0);
      this.entityData.define(EAT_COUNTER, 0);
   }

   private boolean getFlag(int var1) {
      return (this.entityData.get(DATA_ID_FLAGS) & â˜ƒ) != 0;
   }

   private void setFlag(int var1, boolean var2) {
      byte â˜ƒ = this.entityData.get(DATA_ID_FLAGS);
      if (â˜ƒ) {
         this.entityData.set(DATA_ID_FLAGS, (byte)(â˜ƒ | â˜ƒ));
      } else {
         this.entityData.set(DATA_ID_FLAGS, (byte)(â˜ƒ & ~â˜ƒ));
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putString("MainGene", this.getMainGene().getName());
      â˜ƒ.putString("HiddenGene", this.getHiddenGene().getName());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setMainGene(Panda.Gene.byName(â˜ƒ.getString("MainGene")));
      this.setHiddenGene(Panda.Gene.byName(â˜ƒ.getString("HiddenGene")));
   }

   @Nullable
   @Override
   public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      Panda â˜ƒ = EntityType.PANDA.create(â˜ƒ);
      if (â˜ƒ instanceof Panda) {
         â˜ƒ.setGeneFromParents(this, (Panda)â˜ƒ);
      }

      â˜ƒ.setAttributes();
      return â˜ƒ;
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(2, new Panda.PandaPanicGoal(this, 2.0));
      this.goalSelector.addGoal(2, new Panda.PandaBreedGoal(this, 1.0));
      this.goalSelector.addGoal(3, new Panda.PandaAttackGoal(this, 1.2F, true));
      this.goalSelector.addGoal(4, new TemptGoal(this, 1.0, Ingredient.of(Blocks.BAMBOO.asItem()), false));
      this.goalSelector.addGoal(6, new Panda.PandaAvoidGoal(this, Player.class, 8.0F, 2.0, 2.0));
      this.goalSelector.addGoal(6, new Panda.PandaAvoidGoal(this, Monster.class, 4.0F, 2.0, 2.0));
      this.goalSelector.addGoal(7, new Panda.PandaSitGoal());
      this.goalSelector.addGoal(8, new Panda.PandaLieOnBackGoal(this));
      this.goalSelector.addGoal(8, new Panda.PandaSneezeGoal(this));
      this.lookAtPlayerGoal = new Panda.PandaLookAtPlayerGoal(this, Player.class, 6.0F);
      this.goalSelector.addGoal(9, this.lookAtPlayerGoal);
      this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(12, new Panda.PandaRollGoal(this));
      this.goalSelector.addGoal(13, new FollowParentGoal(this, 1.25));
      this.goalSelector.addGoal(14, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.targetSelector.addGoal(1, new Panda.PandaHurtByTargetGoal(this).setAlertOthers(new Class[0]));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.15F).add(Attributes.ATTACK_DAMAGE, 6.0);
   }

   public Panda.Gene getVariant() {
      return Panda.Gene.getVariantFromGenes(this.getMainGene(), this.getHiddenGene());
   }

   public boolean isLazy() {
      return this.getVariant() == Panda.Gene.LAZY;
   }

   public boolean isWorried() {
      return this.getVariant() == Panda.Gene.WORRIED;
   }

   public boolean isPlayful() {
      return this.getVariant() == Panda.Gene.PLAYFUL;
   }

   public boolean isBrown() {
      return this.getVariant() == Panda.Gene.BROWN;
   }

   public boolean isWeak() {
      return this.getVariant() == Panda.Gene.WEAK;
   }

   @Override
   public boolean isAggressive() {
      return this.getVariant() == Panda.Gene.AGGRESSIVE;
   }

   @Override
   public boolean canBeLeashed(Player var1) {
      return false;
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      this.playSound(SoundEvents.PANDA_BITE, 1.0F, 1.0F);
      if (!this.isAggressive()) {
         this.didBite = true;
      }

      return super.doHurtTarget(â˜ƒ);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isWorried()) {
         if (this.level.isThundering() && !this.isInWater()) {
            this.sit(true);
            this.eat(false);
         } else if (!this.isEating()) {
            this.sit(false);
         }
      }

      if (this.getTarget() == null) {
         this.gotBamboo = false;
         this.didBite = false;
      }

      if (this.getUnhappyCounter() > 0) {
         if (this.getTarget() != null) {
            this.lookAt(this.getTarget(), 90.0F, 90.0F);
         }

         if (this.getUnhappyCounter() == 29 || this.getUnhappyCounter() == 14) {
            this.playSound(SoundEvents.PANDA_CANT_BREED, 1.0F, 1.0F);
         }

         this.setUnhappyCounter(this.getUnhappyCounter() - 1);
      }

      if (this.isSneezing()) {
         this.setSneezeCounter(this.getSneezeCounter() + 1);
         if (this.getSneezeCounter() > 20) {
            this.sneeze(false);
            this.afterSneeze();
         } else if (this.getSneezeCounter() == 1) {
            this.playSound(SoundEvents.PANDA_PRE_SNEEZE, 1.0F, 1.0F);
         }
      }

      if (this.isRolling()) {
         this.handleRoll();
      } else {
         this.rollCounter = 0;
      }

      if (this.isSitting()) {
         this.setXRot(0.0F);
      }

      this.updateSitAmount();
      this.handleEating();
      this.updateOnBackAnimation();
      this.updateRollAmount();
   }

   public boolean isScared() {
      return this.isWorried() && this.level.isThundering();
   }

   private void handleEating() {
      if (!this.isEating() && this.isSitting() && !this.isScared() && !this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && this.random.nextInt(80) == 1) {
         this.eat(true);
      } else if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() || !this.isSitting()) {
         this.eat(false);
      }

      if (this.isEating()) {
         this.addEatingParticles();
         if (!this.level.isClientSide && this.getEatCounter() > 80 && this.random.nextInt(20) == 1) {
            if (this.getEatCounter() > 100 && this.isFoodOrCake(this.getItemBySlot(EquipmentSlot.MAINHAND))) {
               if (!this.level.isClientSide) {
                  this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                  this.gameEvent(GameEvent.EAT, this.eyeBlockPosition());
               }

               this.sit(false);
            }

            this.eat(false);
            return;
         }

         this.setEatCounter(this.getEatCounter() + 1);
      }
   }

   private void addEatingParticles() {
      if (this.getEatCounter() % 5 == 0) {
         this.playSound(SoundEvents.PANDA_EAT, 0.5F + 0.5F * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

         for(int â˜ƒ = 0; â˜ƒ < 6; ++â˜ƒ) {
            Vec3 â˜ƒx = new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double)this.random.nextFloat() - 0.5) * 0.1);
            â˜ƒx = â˜ƒx.xRot(-this.getXRot() * (float) (Math.PI / 180.0));
            â˜ƒx = â˜ƒx.yRot(-this.getYRot() * (float) (Math.PI / 180.0));
            double â˜ƒxx = (double)(-this.random.nextFloat()) * 0.6 - 0.3;
            Vec3 â˜ƒxxx = new Vec3(((double)this.random.nextFloat() - 0.5) * 0.8, â˜ƒxx, 1.0 + ((double)this.random.nextFloat() - 0.5) * 0.4);
            â˜ƒxxx = â˜ƒxxx.yRot(-this.yBodyRot * (float) (Math.PI / 180.0));
            â˜ƒxxx = â˜ƒxxx.add(this.getX(), this.getEyeY() + 1.0, this.getZ());
            this.level
               .addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, this.getItemBySlot(EquipmentSlot.MAINHAND)),
                  â˜ƒxxx.x,
                  â˜ƒxxx.y,
                  â˜ƒxxx.z,
                  â˜ƒx.x,
                  â˜ƒx.y + 0.05,
                  â˜ƒx.z
               );
         }
      }
   }

   private void updateSitAmount() {
      this.sitAmountO = this.sitAmount;
      if (this.isSitting()) {
         this.sitAmount = Math.min(1.0F, this.sitAmount + 0.15F);
      } else {
         this.sitAmount = Math.max(0.0F, this.sitAmount - 0.19F);
      }
   }

   private void updateOnBackAnimation() {
      this.onBackAmountO = this.onBackAmount;
      if (this.isOnBack()) {
         this.onBackAmount = Math.min(1.0F, this.onBackAmount + 0.15F);
      } else {
         this.onBackAmount = Math.max(0.0F, this.onBackAmount - 0.19F);
      }
   }

   private void updateRollAmount() {
      this.rollAmountO = this.rollAmount;
      if (this.isRolling()) {
         this.rollAmount = Math.min(1.0F, this.rollAmount + 0.15F);
      } else {
         this.rollAmount = Math.max(0.0F, this.rollAmount - 0.19F);
      }
   }

   public float getSitAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.sitAmountO, this.sitAmount);
   }

   public float getLieOnBackAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.onBackAmountO, this.onBackAmount);
   }

   public float getRollAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.rollAmountO, this.rollAmount);
   }

   private void handleRoll() {
      ++this.rollCounter;
      if (this.rollCounter > 32) {
         this.roll(false);
      } else {
         if (!this.level.isClientSide) {
            Vec3 â˜ƒ = this.getDeltaMovement();
            if (this.rollCounter == 1) {
               float â˜ƒx = this.getYRot() * (float) (Math.PI / 180.0);
               float â˜ƒxx = this.isBaby() ? 0.1F : 0.2F;
               this.rollDelta = new Vec3(â˜ƒ.x + (double)(-Mth.sin(â˜ƒx) * â˜ƒxx), 0.0, â˜ƒ.z + (double)(Mth.cos(â˜ƒx) * â˜ƒxx));
               this.setDeltaMovement(this.rollDelta.add(0.0, 0.27, 0.0));
            } else if ((float)this.rollCounter != 7.0F && (float)this.rollCounter != 15.0F && (float)this.rollCounter != 23.0F) {
               this.setDeltaMovement(this.rollDelta.x, â˜ƒ.y, this.rollDelta.z);
            } else {
               this.setDeltaMovement(0.0, this.onGround ? 0.27 : â˜ƒ.y, 0.0);
            }
         }
      }
   }

   private void afterSneeze() {
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.level
         .addParticle(
            ParticleTypes.SNEEZE,
            this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5 * (double)Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0)),
            this.getEyeY() - 0.1F,
            this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5 * (double)Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0)),
            â˜ƒ.x,
            0.0,
            â˜ƒ.z
         );
      this.playSound(SoundEvents.PANDA_SNEEZE, 1.0F, 1.0F);

      for(Panda â˜ƒx : this.level.getEntitiesOfClass(Panda.class, this.getBoundingBox().inflate(10.0))) {
         if (!â˜ƒx.isBaby() && â˜ƒx.onGround && !â˜ƒx.isInWater() && â˜ƒx.canPerformAction()) {
            â˜ƒx.jumpFromGround();
         }
      }

      if (!this.level.isClientSide() && this.random.nextInt(700) == 0 && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
         this.spawnAtLocation(Items.SLIME_BALL);
      }
   }

   @Override
   protected void pickUpItem(ItemEntity var1) {
      if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && PANDA_ITEMS.test(â˜ƒ)) {
         this.onItemPickup(â˜ƒ);
         ItemStack â˜ƒ = â˜ƒ.getItem();
         this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ);
         this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
         this.take(â˜ƒ, â˜ƒ.getCount());
         â˜ƒ.discard();
      }
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      this.sit(false);
      return super.hurt(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.setMainGene(Panda.Gene.getRandom(this.random));
      this.setHiddenGene(Panda.Gene.getRandom(this.random));
      this.setAttributes();
      if (â˜ƒ == null) {
         â˜ƒ = new AgeableMob.AgeableMobGroupData(0.2F);
      }

      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setGeneFromParents(Panda var1, @Nullable Panda var2) {
      if (â˜ƒ == null) {
         if (this.random.nextBoolean()) {
            this.setMainGene(â˜ƒ.getOneOfGenesRandomly());
            this.setHiddenGene(Panda.Gene.getRandom(this.random));
         } else {
            this.setMainGene(Panda.Gene.getRandom(this.random));
            this.setHiddenGene(â˜ƒ.getOneOfGenesRandomly());
         }
      } else if (this.random.nextBoolean()) {
         this.setMainGene(â˜ƒ.getOneOfGenesRandomly());
         this.setHiddenGene(â˜ƒ.getOneOfGenesRandomly());
      } else {
         this.setMainGene(â˜ƒ.getOneOfGenesRandomly());
         this.setHiddenGene(â˜ƒ.getOneOfGenesRandomly());
      }

      if (this.random.nextInt(32) == 0) {
         this.setMainGene(Panda.Gene.getRandom(this.random));
      }

      if (this.random.nextInt(32) == 0) {
         this.setHiddenGene(Panda.Gene.getRandom(this.random));
      }
   }

   private Panda.Gene getOneOfGenesRandomly() {
      return this.random.nextBoolean() ? this.getMainGene() : this.getHiddenGene();
   }

   public void setAttributes() {
      if (this.isWeak()) {
         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0);
      }

      if (this.isLazy()) {
         this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.07F);
      }
   }

   void tryToSit() {
      if (!this.isInWater()) {
         this.setZza(0.0F);
         this.getNavigation().stop();
         this.sit(true);
      }
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (this.isScared()) {
         return InteractionResult.PASS;
      } else if (this.isOnBack()) {
         this.setOnBack(false);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else if (this.isFood(â˜ƒ)) {
         if (this.getTarget() != null) {
            this.gotBamboo = true;
         }

         if (this.isBaby()) {
            this.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
            this.ageUp((int)((float)(-this.getAge() / 20) * 0.1F), true);
            this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
         } else if (!this.level.isClientSide && this.getAge() == 0 && this.canFallInLove()) {
            this.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
            this.setInLove(â˜ƒ);
            this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
         } else {
            if (this.level.isClientSide || this.isSitting() || this.isInWater()) {
               return InteractionResult.PASS;
            }

            this.tryToSit();
            this.eat(true);
            ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!â˜ƒ.isEmpty() && !â˜ƒ.getAbilities().instabuild) {
               this.spawnAtLocation(â˜ƒ);
            }

            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(â˜ƒ.getItem(), 1));
            this.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      if (this.isAggressive()) {
         return SoundEvents.PANDA_AGGRESSIVE_AMBIENT;
      } else {
         return this.isWorried() ? SoundEvents.PANDA_WORRIED_AMBIENT : SoundEvents.PANDA_AMBIENT;
      }
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(SoundEvents.PANDA_STEP, 0.15F, 1.0F);
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return â˜ƒ.is(Blocks.BAMBOO.asItem());
   }

   private boolean isFoodOrCake(ItemStack var1) {
      return this.isFood(â˜ƒ) || â˜ƒ.is(Blocks.CAKE.asItem());
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.PANDA_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.PANDA_HURT;
   }

   public boolean canPerformAction() {
      return !this.isOnBack() && !this.isScared() && !this.isEating() && !this.isRolling() && !this.isSitting();
   }

   public static enum Gene {
      NORMAL(0, "normal", false),
      LAZY(1, "lazy", false),
      WORRIED(2, "worried", false),
      PLAYFUL(3, "playful", false),
      BROWN(4, "brown", true),
      WEAK(5, "weak", true),
      AGGRESSIVE(6, "aggressive", false);

      private static final Panda.Gene[] BY_ID = (Panda.Gene[])Arrays.stream(values())
         .sorted(Comparator.comparingInt(Panda.Gene::getId))
         .toArray(var0 -> new Panda.Gene[var0]);
      private static final int MAX_GENE = 6;
      private final int id;
      private final String name;
      private final boolean isRecessive;

      private Gene(int var3, String var4, boolean var5) {
         this.id = â˜ƒ;
         this.name = â˜ƒ;
         this.isRecessive = â˜ƒ;
      }

      public int getId() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      public boolean isRecessive() {
         return this.isRecessive;
      }

      static Panda.Gene getVariantFromGenes(Panda.Gene var0, Panda.Gene var1) {
         if (â˜ƒ.isRecessive()) {
            return â˜ƒ == â˜ƒ ? â˜ƒ : NORMAL;
         } else {
            return â˜ƒ;
         }
      }

      public static Panda.Gene byId(int var0) {
         if (â˜ƒ < 0 || â˜ƒ >= BY_ID.length) {
            â˜ƒ = 0;
         }

         return BY_ID[â˜ƒ];
      }

      public static Panda.Gene byName(String var0) {
         for(Panda.Gene â˜ƒ : values()) {
            if (â˜ƒ.name.equals(â˜ƒ)) {
               return â˜ƒ;
            }
         }

         return NORMAL;
      }

      public static Panda.Gene getRandom(Random var0) {
         int â˜ƒ = â˜ƒ.nextInt(16);
         if (â˜ƒ == 0) {
            return LAZY;
         } else if (â˜ƒ == 1) {
            return WORRIED;
         } else if (â˜ƒ == 2) {
            return PLAYFUL;
         } else if (â˜ƒ == 4) {
            return AGGRESSIVE;
         } else if (â˜ƒ < 9) {
            return WEAK;
         } else {
            return â˜ƒ < 11 ? BROWN : NORMAL;
         }
      }
   }

   static class PandaAttackGoal extends MeleeAttackGoal {
      private final Panda panda;

      public PandaAttackGoal(Panda var1, double var2, boolean var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.panda = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return this.panda.canPerformAction() && super.canUse();
      }
   }

   static class PandaAvoidGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final Panda panda;

      public PandaAvoidGoal(Panda var1, Class<T> var2, float var3, double var4, double var6) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, EntitySelector.NO_SPECTATORS::test);
         this.panda = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return this.panda.isWorried() && this.panda.canPerformAction() && super.canUse();
      }
   }

   class PandaBreedGoal extends BreedGoal {
      private final Panda panda;
      private int unhappyCooldown;

      public PandaBreedGoal(Panda var2, double var3) {
         super(â˜ƒ, â˜ƒ);
         this.panda = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         if (!super.canUse() || this.panda.getUnhappyCounter() != 0) {
            return false;
         } else if (!this.canFindBamboo()) {
            if (this.unhappyCooldown <= this.panda.tickCount) {
               this.panda.setUnhappyCounter(32);
               this.unhappyCooldown = this.panda.tickCount + 600;
               if (this.panda.isEffectiveAi()) {
                  Player â˜ƒ = this.level.getNearestPlayer(Panda.BREED_TARGETING, this.panda);
                  this.panda.lookAtPlayerGoal.setTarget(â˜ƒ);
               }
            }

            return false;
         } else {
            return true;
         }
      }

      private boolean canFindBamboo() {
         BlockPos â˜ƒ = this.panda.blockPosition();
         BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxx = 0; â˜ƒxx < 3; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < 8; ++â˜ƒxxx) {
               for(int â˜ƒxxxx = 0; â˜ƒxxxx <= â˜ƒxxx; â˜ƒxxxx = â˜ƒxxxx > 0 ? -â˜ƒxxxx : 1 - â˜ƒxxxx) {
                  for(int â˜ƒxxxxx = â˜ƒxxxx < â˜ƒxxx && â˜ƒxxxx > -â˜ƒxxx ? â˜ƒxxx : 0; â˜ƒxxxxx <= â˜ƒxxx; â˜ƒxxxxx = â˜ƒxxxxx > 0 ? -â˜ƒxxxxx : 1 - â˜ƒxxxxx) {
                     â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxxxx, â˜ƒxx, â˜ƒxxxxx);
                     if (this.level.getBlockState(â˜ƒx).is(Blocks.BAMBOO)) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   static class PandaHurtByTargetGoal extends HurtByTargetGoal {
      private final Panda panda;

      public PandaHurtByTargetGoal(Panda var1, Class<?>... var2) {
         super(â˜ƒ, â˜ƒ);
         this.panda = â˜ƒ;
      }

      @Override
      public boolean canContinueToUse() {
         if (!this.panda.gotBamboo && !this.panda.didBite) {
            return super.canContinueToUse();
         } else {
            this.panda.setTarget(null);
            return false;
         }
      }

      @Override
      protected void alertOther(Mob var1, LivingEntity var2) {
         if (â˜ƒ instanceof Panda && ((Panda)â˜ƒ).isAggressive()) {
            â˜ƒ.setTarget(â˜ƒ);
         }
      }
   }

   static class PandaLieOnBackGoal extends Goal {
      private final Panda panda;
      private int cooldown;

      public PandaLieOnBackGoal(Panda var1) {
         this.panda = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return this.cooldown < this.panda.tickCount && this.panda.isLazy() && this.panda.canPerformAction() && this.panda.random.nextInt(400) == 1;
      }

      @Override
      public boolean canContinueToUse() {
         if (!this.panda.isInWater() && (this.panda.isLazy() || this.panda.random.nextInt(600) != 1)) {
            return this.panda.random.nextInt(2000) != 1;
         } else {
            return false;
         }
      }

      @Override
      public void start() {
         this.panda.setOnBack(true);
         this.cooldown = 0;
      }

      @Override
      public void stop() {
         this.panda.setOnBack(false);
         this.cooldown = this.panda.tickCount + 200;
      }
   }

   static class PandaLookAtPlayerGoal extends LookAtPlayerGoal {
      private final Panda panda;

      public PandaLookAtPlayerGoal(Panda var1, Class<? extends LivingEntity> var2, float var3) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.panda = â˜ƒ;
      }

      public void setTarget(LivingEntity var1) {
         this.lookAt = â˜ƒ;
      }

      @Override
      public boolean canContinueToUse() {
         return this.lookAt != null && super.canContinueToUse();
      }

      @Override
      public boolean canUse() {
         if (this.mob.getRandom().nextFloat() >= this.probability) {
            return false;
         } else {
            if (this.lookAt == null) {
               if (this.lookAtType == Player.class) {
                  this.lookAt = this.mob.level.getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
               } else {
                  this.lookAt = this.mob
                     .level
                     .getNearestEntity(
                        this.mob
                           .level
                           .getEntitiesOfClass(
                              this.lookAtType, this.mob.getBoundingBox().inflate((double)this.lookDistance, 3.0, (double)this.lookDistance), var0 -> true
                           ),
                        this.lookAtContext,
                        this.mob,
                        this.mob.getX(),
                        this.mob.getEyeY(),
                        this.mob.getZ()
                     );
               }
            }

            return this.panda.canPerformAction() && this.lookAt != null;
         }
      }

      @Override
      public void tick() {
         if (this.lookAt != null) {
            super.tick();
         }
      }
   }

   static class PandaMoveControl extends MoveControl {
      private final Panda panda;

      public PandaMoveControl(Panda var1) {
         super(â˜ƒ);
         this.panda = â˜ƒ;
      }

      @Override
      public void tick() {
         if (this.panda.canPerformAction()) {
            super.tick();
         }
      }
   }

   static class PandaPanicGoal extends PanicGoal {
      private final Panda panda;

      public PandaPanicGoal(Panda var1, double var2) {
         super(â˜ƒ, â˜ƒ);
         this.panda = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         if (!this.panda.isOnFire()) {
            return false;
         } else {
            BlockPos â˜ƒ = this.lookForWater(this.mob.level, this.mob, 5, 4);
            if (â˜ƒ != null) {
               this.posX = (double)â˜ƒ.getX();
               this.posY = (double)â˜ƒ.getY();
               this.posZ = (double)â˜ƒ.getZ();
               return true;
            } else {
               return this.findRandomPosition();
            }
         }
      }

      @Override
      public boolean canContinueToUse() {
         if (this.panda.isSitting()) {
            this.panda.getNavigation().stop();
            return false;
         } else {
            return super.canContinueToUse();
         }
      }
   }

   static class PandaRollGoal extends Goal {
      private final Panda panda;

      public PandaRollGoal(Panda var1) {
         this.panda = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         if ((this.panda.isBaby() || this.panda.isPlayful()) && this.panda.onGround) {
            if (!this.panda.canPerformAction()) {
               return false;
            } else {
               float â˜ƒ = this.panda.getYRot() * (float) (Math.PI / 180.0);
               int â˜ƒx = 0;
               int â˜ƒxx = 0;
               float â˜ƒxxx = -Mth.sin(â˜ƒ);
               float â˜ƒxxxx = Mth.cos(â˜ƒ);
               if ((double)Math.abs(â˜ƒxxx) > 0.5) {
                  â˜ƒx = (int)((float)â˜ƒx + â˜ƒxxx / Math.abs(â˜ƒxxx));
               }

               if ((double)Math.abs(â˜ƒxxxx) > 0.5) {
                  â˜ƒxx = (int)((float)â˜ƒxx + â˜ƒxxxx / Math.abs(â˜ƒxxxx));
               }

               if (this.panda.level.getBlockState(this.panda.blockPosition().offset(â˜ƒx, -1, â˜ƒxx)).isAir()) {
                  return true;
               } else if (this.panda.isPlayful() && this.panda.random.nextInt(60) == 1) {
                  return true;
               } else {
                  return this.panda.random.nextInt(500) == 1;
               }
            }
         } else {
            return false;
         }
      }

      @Override
      public boolean canContinueToUse() {
         return false;
      }

      @Override
      public void start() {
         this.panda.roll(true);
      }

      @Override
      public boolean isInterruptable() {
         return false;
      }
   }

   class PandaSitGoal extends Goal {
      private int cooldown;

      public PandaSitGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         if (this.cooldown <= Panda.this.tickCount
            && !Panda.this.isBaby()
            && !Panda.this.isInWater()
            && Panda.this.canPerformAction()
            && Panda.this.getUnhappyCounter() <= 0) {
            List<ItemEntity> â˜ƒ = Panda.this.level.getEntitiesOfClass(ItemEntity.class, Panda.this.getBoundingBox().inflate(6.0, 6.0, 6.0), Panda.PANDA_ITEMS);
            return !â˜ƒ.isEmpty() || !Panda.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
         } else {
            return false;
         }
      }

      @Override
      public boolean canContinueToUse() {
         if (!Panda.this.isInWater() && (Panda.this.isLazy() || Panda.this.random.nextInt(600) != 1)) {
            return Panda.this.random.nextInt(2000) != 1;
         } else {
            return false;
         }
      }

      @Override
      public void tick() {
         if (!Panda.this.isSitting() && !Panda.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            Panda.this.tryToSit();
         }
      }

      @Override
      public void start() {
         List<ItemEntity> â˜ƒ = Panda.this.level.getEntitiesOfClass(ItemEntity.class, Panda.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Panda.PANDA_ITEMS);
         if (!â˜ƒ.isEmpty() && Panda.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            Panda.this.getNavigation().moveTo((Entity)â˜ƒ.get(0), 1.2F);
         } else if (!Panda.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            Panda.this.tryToSit();
         }

         this.cooldown = 0;
      }

      @Override
      public void stop() {
         ItemStack â˜ƒ = Panda.this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (!â˜ƒ.isEmpty()) {
            Panda.this.spawnAtLocation(â˜ƒ);
            Panda.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            int â˜ƒx = Panda.this.isLazy() ? Panda.this.random.nextInt(50) + 10 : Panda.this.random.nextInt(150) + 10;
            this.cooldown = Panda.this.tickCount + â˜ƒx * 20;
         }

         Panda.this.sit(false);
      }
   }

   static class PandaSneezeGoal extends Goal {
      private final Panda panda;

      public PandaSneezeGoal(Panda var1) {
         this.panda = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         if (this.panda.isBaby() && this.panda.canPerformAction()) {
            if (this.panda.isWeak() && this.panda.random.nextInt(500) == 1) {
               return true;
            } else {
               return this.panda.random.nextInt(6000) == 1;
            }
         } else {
            return false;
         }
      }

      @Override
      public boolean canContinueToUse() {
         return false;
      }

      @Override
      public void start() {
         this.panda.sneeze(true);
      }
   }
}
