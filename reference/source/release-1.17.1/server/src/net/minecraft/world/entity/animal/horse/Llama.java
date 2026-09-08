package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LlamaFollowCaravanGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Llama extends AbstractChestedHorse implements RangedAttackMob {
   private static final int MAX_STRENGTH = 5;
   private static final int VARIANTS = 4;
   private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT, Blocks.HAY_BLOCK.asItem());
   private static final EntityDataAccessor<Integer> DATA_STRENGTH_ID = SynchedEntityData.defineId(Llama.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_SWAG_ID = SynchedEntityData.defineId(Llama.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(Llama.class, EntityDataSerializers.INT);
   boolean didSpit;
   @Nullable
   private Llama caravanHead;
   @Nullable
   private Llama caravanTail;

   public Llama(EntityType<? extends Llama> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public boolean isTraderLlama() {
      return false;
   }

   private void setStrength(int var1) {
      this.entityData.set(DATA_STRENGTH_ID, Math.max(1, Math.min(5, â˜ƒ)));
   }

   private void setRandomStrength() {
      int â˜ƒ = this.random.nextFloat() < 0.04F ? 5 : 3;
      this.setStrength(1 + this.random.nextInt(â˜ƒ));
   }

   public int getStrength() {
      return this.entityData.get(DATA_STRENGTH_ID);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Variant", this.getVariant());
      â˜ƒ.putInt("Strength", this.getStrength());
      if (!this.inventory.getItem(1).isEmpty()) {
         â˜ƒ.put("DecorItem", this.inventory.getItem(1).save(new CompoundTag()));
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      this.setStrength(â˜ƒ.getInt("Strength"));
      super.readAdditionalSaveData(â˜ƒ);
      this.setVariant(â˜ƒ.getInt("Variant"));
      if (â˜ƒ.contains("DecorItem", 10)) {
         this.inventory.setItem(1, ItemStack.of(â˜ƒ.getCompound("DecorItem")));
      }

      this.updateContainerEquipment();
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2));
      this.goalSelector.addGoal(2, new LlamaFollowCaravanGoal(this, 2.1F));
      this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25, 40, 20.0F));
      this.goalSelector.addGoal(3, new PanicGoal(this, 1.2));
      this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new Llama.LlamaHurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new Llama.LlamaAttackWolfGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return createBaseChestedHorseAttributes().add(Attributes.FOLLOW_RANGE, 40.0);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_STRENGTH_ID, 0);
      this.entityData.define(DATA_SWAG_ID, -1);
      this.entityData.define(DATA_VARIANT_ID, 0);
   }

   public int getVariant() {
      return Mth.clamp(this.entityData.get(DATA_VARIANT_ID), 0, 3);
   }

   public void setVariant(int var1) {
      this.entityData.set(DATA_VARIANT_ID, â˜ƒ);
   }

   @Override
   protected int getInventorySize() {
      return this.hasChest() ? 2 + 3 * this.getInventoryColumns() : super.getInventorySize();
   }

   @Override
   public void positionRider(Entity var1) {
      if (this.hasPassenger(â˜ƒ)) {
         float â˜ƒ = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float â˜ƒx = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         float â˜ƒxx = 0.3F;
         â˜ƒ.setPos(
            this.getX() + (double)(0.3F * â˜ƒx), this.getY() + this.getPassengersRidingOffset() + â˜ƒ.getMyRidingOffset(), this.getZ() - (double)(0.3F * â˜ƒ)
         );
      }
   }

   @Override
   public double getPassengersRidingOffset() {
      return (double)this.getBbHeight() * 0.67;
   }

   @Override
   public boolean canBeControlledByRider() {
      return false;
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return FOOD_ITEMS.test(â˜ƒ);
   }

   @Override
   protected boolean handleEating(Player var1, ItemStack var2) {
      int â˜ƒ = 0;
      int â˜ƒx = 0;
      float â˜ƒxx = 0.0F;
      boolean â˜ƒxxx = false;
      if (â˜ƒ.is(Items.WHEAT)) {
         â˜ƒ = 10;
         â˜ƒx = 3;
         â˜ƒxx = 2.0F;
      } else if (â˜ƒ.is(Blocks.HAY_BLOCK.asItem())) {
         â˜ƒ = 90;
         â˜ƒx = 6;
         â˜ƒxx = 10.0F;
         if (this.isTamed() && this.getAge() == 0 && this.canFallInLove()) {
            â˜ƒxxx = true;
            this.setInLove(â˜ƒ);
         }
      }

      if (this.getHealth() < this.getMaxHealth() && â˜ƒxx > 0.0F) {
         this.heal(â˜ƒxx);
         â˜ƒxxx = true;
      }

      if (this.isBaby() && â˜ƒ > 0) {
         this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
         if (!this.level.isClientSide) {
            this.ageUp(â˜ƒ);
         }

         â˜ƒxxx = true;
      }

      if (â˜ƒx > 0 && (â˜ƒxxx || !this.isTamed()) && this.getTemper() < this.getMaxTemper()) {
         â˜ƒxxx = true;
         if (!this.level.isClientSide) {
            this.modifyTemper(â˜ƒx);
         }
      }

      if (â˜ƒxxx) {
         this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
         if (!this.isSilent()) {
            SoundEvent â˜ƒ = this.getEatingSound();
            if (â˜ƒ != null) {
               this.level
                  .playSound(
                     null,
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     this.getEatingSound(),
                     this.getSoundSource(),
                     1.0F,
                     1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
                  );
            }
         }
      }

      return â˜ƒxxx;
   }

   @Override
   protected boolean isImmobile() {
      return this.isDeadOrDying() || this.isEating();
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.setRandomStrength();
      int â˜ƒ;
      if (â˜ƒ instanceof Llama.LlamaGroupData) {
         â˜ƒ = ((Llama.LlamaGroupData)â˜ƒ).variant;
      } else {
         â˜ƒ = this.random.nextInt(4);
         â˜ƒ = new Llama.LlamaGroupData(â˜ƒ);
      }

      this.setVariant(â˜ƒ);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected SoundEvent getAngrySound() {
      return SoundEvents.LLAMA_ANGRY;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.LLAMA_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.LLAMA_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.LLAMA_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getEatingSound() {
      return SoundEvents.LLAMA_EAT;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(SoundEvents.LLAMA_STEP, 0.15F, 1.0F);
   }

   @Override
   protected void playChestEquipsSound() {
      this.playSound(SoundEvents.LLAMA_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
   }

   @Override
   public void makeMad() {
      SoundEvent â˜ƒ = this.getAngrySound();
      if (â˜ƒ != null) {
         this.playSound(â˜ƒ, this.getSoundVolume(), this.getVoicePitch());
      }
   }

   @Override
   public int getInventoryColumns() {
      return this.getStrength();
   }

   @Override
   public boolean canWearArmor() {
      return true;
   }

   @Override
   public boolean isWearingArmor() {
      return !this.inventory.getItem(1).isEmpty();
   }

   @Override
   public boolean isArmor(ItemStack var1) {
      return â˜ƒ.is(ItemTags.CARPETS);
   }

   @Override
   public boolean isSaddleable() {
      return false;
   }

   @Override
   public void containerChanged(Container var1) {
      DyeColor â˜ƒ = this.getSwag();
      super.containerChanged(â˜ƒ);
      DyeColor â˜ƒx = this.getSwag();
      if (this.tickCount > 20 && â˜ƒx != null && â˜ƒx != â˜ƒ) {
         this.playSound(SoundEvents.LLAMA_SWAG, 0.5F, 1.0F);
      }
   }

   @Override
   protected void updateContainerEquipment() {
      if (!this.level.isClientSide) {
         super.updateContainerEquipment();
         this.setSwag(getDyeColor(this.inventory.getItem(1)));
      }
   }

   private void setSwag(@Nullable DyeColor var1) {
      this.entityData.set(DATA_SWAG_ID, â˜ƒ == null ? -1 : â˜ƒ.getId());
   }

   @Nullable
   private static DyeColor getDyeColor(ItemStack var0) {
      Block â˜ƒ = Block.byItem(â˜ƒ.getItem());
      return â˜ƒ instanceof WoolCarpetBlock ? ((WoolCarpetBlock)â˜ƒ).getColor() : null;
   }

   @Nullable
   public DyeColor getSwag() {
      int â˜ƒ = this.entityData.get(DATA_SWAG_ID);
      return â˜ƒ == -1 ? null : DyeColor.byId(â˜ƒ);
   }

   @Override
   public int getMaxTemper() {
      return 30;
   }

   @Override
   public boolean canMate(Animal var1) {
      return â˜ƒ != this && â˜ƒ instanceof Llama && this.canParent() && ((Llama)â˜ƒ).canParent();
   }

   public Llama getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      Llama â˜ƒ = this.makeBabyLlama();
      this.setOffspringAttributes(â˜ƒ, â˜ƒ);
      Llama â˜ƒx = (Llama)â˜ƒ;
      int â˜ƒxx = this.random.nextInt(Math.max(this.getStrength(), â˜ƒx.getStrength())) + 1;
      if (this.random.nextFloat() < 0.03F) {
         ++â˜ƒxx;
      }

      â˜ƒ.setStrength(â˜ƒxx);
      â˜ƒ.setVariant(this.random.nextBoolean() ? this.getVariant() : â˜ƒx.getVariant());
      return â˜ƒ;
   }

   protected Llama makeBabyLlama() {
      return EntityType.LLAMA.create(this.level);
   }

   private void spit(LivingEntity var1) {
      LlamaSpit â˜ƒ = new LlamaSpit(this.level, this);
      double â˜ƒx = â˜ƒ.getX() - this.getX();
      double â˜ƒxx = â˜ƒ.getY(0.3333333333333333) - â˜ƒ.getY();
      double â˜ƒxxx = â˜ƒ.getZ() - this.getZ();
      double â˜ƒxxxx = Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxxx * â˜ƒxxx) * 0.2F;
      â˜ƒ.shoot(â˜ƒx, â˜ƒxx + â˜ƒxxxx, â˜ƒxxx, 1.5F, 10.0F);
      if (!this.isSilent()) {
         this.level
            .playSound(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               SoundEvents.LLAMA_SPIT,
               this.getSoundSource(),
               1.0F,
               1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
            );
      }

      this.level.addFreshEntity(â˜ƒ);
      this.didSpit = true;
   }

   void setDidSpit(boolean var1) {
      this.didSpit = â˜ƒ;
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      int â˜ƒ = this.calculateFallDamage(â˜ƒ, â˜ƒ);
      if (â˜ƒ <= 0) {
         return false;
      } else {
         if (â˜ƒ >= 6.0F) {
            this.hurt(â˜ƒ, (float)â˜ƒ);
            if (this.isVehicle()) {
               for(Entity â˜ƒ : this.getIndirectPassengers()) {
                  â˜ƒ.hurt(â˜ƒ, (float)â˜ƒ);
               }
            }
         }

         this.playBlockFallSound();
         return true;
      }
   }

   public void leaveCaravan() {
      if (this.caravanHead != null) {
         this.caravanHead.caravanTail = null;
      }

      this.caravanHead = null;
   }

   public void joinCaravan(Llama var1) {
      this.caravanHead = â˜ƒ;
      this.caravanHead.caravanTail = this;
   }

   public boolean hasCaravanTail() {
      return this.caravanTail != null;
   }

   public boolean inCaravan() {
      return this.caravanHead != null;
   }

   @Nullable
   public Llama getCaravanHead() {
      return this.caravanHead;
   }

   @Override
   protected double followLeashSpeed() {
      return 2.0;
   }

   @Override
   protected void followMommy() {
      if (!this.inCaravan() && this.isBaby()) {
         super.followMommy();
      }
   }

   @Override
   public boolean canEatGrass() {
      return false;
   }

   @Override
   public void performRangedAttack(LivingEntity var1, float var2) {
      this.spit(â˜ƒ);
   }

   @Override
   public Vec3 getLeashOffset() {
      return new Vec3(0.0, 0.75 * (double)this.getEyeHeight(), (double)this.getBbWidth() * 0.5);
   }

   static class LlamaAttackWolfGoal extends NearestAttackableTargetGoal<Wolf> {
      public LlamaAttackWolfGoal(Llama var1) {
         super(â˜ƒ, Wolf.class, 16, false, true, var0 -> !((Wolf)var0).isTame());
      }

      @Override
      protected double getFollowDistance() {
         return super.getFollowDistance() * 0.25;
      }
   }

   static class LlamaGroupData extends AgeableMob.AgeableMobGroupData {
      public final int variant;

      LlamaGroupData(int var1) {
         super(true);
         this.variant = â˜ƒ;
      }
   }

   static class LlamaHurtByTargetGoal extends HurtByTargetGoal {
      public LlamaHurtByTargetGoal(Llama var1) {
         super(â˜ƒ);
      }

      @Override
      public boolean canContinueToUse() {
         if (this.mob instanceof Llama â˜ƒ && â˜ƒ.didSpit) {
            â˜ƒ.setDidSpit(false);
            return false;
         }

         return super.canContinueToUse();
      }
   }
}
