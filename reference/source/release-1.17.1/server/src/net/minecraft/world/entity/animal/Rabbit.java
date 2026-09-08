package net.minecraft.world.entity.animal;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Rabbit extends Animal {
   public static final double STROLL_SPEED_MOD = 0.6;
   public static final double BREED_SPEED_MOD = 0.8;
   public static final double FOLLOW_SPEED_MOD = 1.0;
   public static final double FLEE_SPEED_MOD = 2.2;
   public static final double ATTACK_SPEED_MOD = 1.4;
   private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Rabbit.class, EntityDataSerializers.INT);
   public static final int TYPE_BROWN = 0;
   public static final int TYPE_WHITE = 1;
   public static final int TYPE_BLACK = 2;
   public static final int TYPE_WHITE_SPLOTCHED = 3;
   public static final int TYPE_GOLD = 4;
   public static final int TYPE_SALT = 5;
   public static final int TYPE_EVIL = 99;
   private static final ResourceLocation KILLER_BUNNY = new ResourceLocation("killer_bunny");
   public static final int EVIL_ATTACK_POWER = 8;
   public static final int EVIL_ARMOR_VALUE = 8;
   private static final int MORE_CARROTS_DELAY = 40;
   private int jumpTicks;
   private int jumpDuration;
   private boolean wasOnGround;
   private int jumpDelayTicks;
   int moreCarrotTicks;

   public Rabbit(EntityType<? extends Rabbit> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.jumpControl = new Rabbit.RabbitJumpControl(this);
      this.moveControl = new Rabbit.RabbitMoveControl(this);
      this.setSpeedModifier(0.0);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Rabbit.RabbitPanicGoal(this, 2.2));
      this.goalSelector.addGoal(2, new BreedGoal(this, 0.8));
      this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
      this.goalSelector.addGoal(4, new Rabbit.RabbitAvoidEntityGoal(this, Player.class, 8.0F, 2.2, 2.2));
      this.goalSelector.addGoal(4, new Rabbit.RabbitAvoidEntityGoal(this, Wolf.class, 10.0F, 2.2, 2.2));
      this.goalSelector.addGoal(4, new Rabbit.RabbitAvoidEntityGoal(this, Monster.class, 4.0F, 2.2, 2.2));
      this.goalSelector.addGoal(5, new Rabbit.RaidGardenGoal(this));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
   }

   @Override
   protected float getJumpPower() {
      if (!this.horizontalCollision && (!this.moveControl.hasWanted() || !(this.moveControl.getWantedY() > this.getY() + 0.5))) {
         Path â˜ƒ = this.navigation.getPath();
         if (â˜ƒ != null && !â˜ƒ.isDone()) {
            Vec3 â˜ƒx = â˜ƒ.getNextEntityPos(this);
            if (â˜ƒx.y > this.getY() + 0.5) {
               return 0.5F;
            }
         }

         return this.moveControl.getSpeedModifier() <= 0.6 ? 0.2F : 0.3F;
      } else {
         return 0.5F;
      }
   }

   @Override
   protected void jumpFromGround() {
      super.jumpFromGround();
      double â˜ƒ = this.moveControl.getSpeedModifier();
      if (â˜ƒ > 0.0) {
         double â˜ƒx = this.getDeltaMovement().horizontalDistanceSqr();
         if (â˜ƒx < 0.01) {
            this.moveRelative(0.1F, new Vec3(0.0, 0.0, 1.0));
         }
      }

      if (!this.level.isClientSide) {
         this.level.broadcastEntityEvent(this, (byte)1);
      }
   }

   public float getJumpCompletion(float var1) {
      return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + â˜ƒ) / (float)this.jumpDuration;
   }

   public void setSpeedModifier(double var1) {
      this.getNavigation().setSpeedModifier(â˜ƒ);
      this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), â˜ƒ);
   }

   @Override
   public void setJumping(boolean var1) {
      super.setJumping(â˜ƒ);
      if (â˜ƒ) {
         this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
      }
   }

   public void startJumping() {
      this.setJumping(true);
      this.jumpDuration = 10;
      this.jumpTicks = 0;
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_TYPE_ID, 0);
   }

   @Override
   public void customServerAiStep() {
      if (this.jumpDelayTicks > 0) {
         --this.jumpDelayTicks;
      }

      if (this.moreCarrotTicks > 0) {
         this.moreCarrotTicks -= this.random.nextInt(3);
         if (this.moreCarrotTicks < 0) {
            this.moreCarrotTicks = 0;
         }
      }

      if (this.onGround) {
         if (!this.wasOnGround) {
            this.setJumping(false);
            this.checkLandingDelay();
         }

         if (this.getRabbitType() == 99 && this.jumpDelayTicks == 0) {
            LivingEntity â˜ƒ = this.getTarget();
            if (â˜ƒ != null && this.distanceToSqr(â˜ƒ) < 16.0) {
               this.facePoint(â˜ƒ.getX(), â˜ƒ.getZ());
               this.moveControl.setWantedPosition(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), this.moveControl.getSpeedModifier());
               this.startJumping();
               this.wasOnGround = true;
            }
         }

         Rabbit.RabbitJumpControl â˜ƒ = (Rabbit.RabbitJumpControl)this.jumpControl;
         if (!â˜ƒ.wantJump()) {
            if (this.moveControl.hasWanted() && this.jumpDelayTicks == 0) {
               Path â˜ƒx = this.navigation.getPath();
               Vec3 â˜ƒxx = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
               if (â˜ƒx != null && !â˜ƒx.isDone()) {
                  â˜ƒxx = â˜ƒx.getNextEntityPos(this);
               }

               this.facePoint(â˜ƒxx.x, â˜ƒxx.z);
               this.startJumping();
            }
         } else if (!â˜ƒ.canJump()) {
            this.enableJumpControl();
         }
      }

      this.wasOnGround = this.onGround;
   }

   @Override
   public boolean canSpawnSprintParticle() {
      return false;
   }

   private void facePoint(double var1, double var3) {
      this.setYRot((float)(Mth.atan2(â˜ƒ - this.getZ(), â˜ƒ - this.getX()) * 180.0F / (float)Math.PI) - 90.0F);
   }

   private void enableJumpControl() {
      ((Rabbit.RabbitJumpControl)this.jumpControl).setCanJump(true);
   }

   private void disableJumpControl() {
      ((Rabbit.RabbitJumpControl)this.jumpControl).setCanJump(false);
   }

   private void setLandingDelay() {
      if (this.moveControl.getSpeedModifier() < 2.2) {
         this.jumpDelayTicks = 10;
      } else {
         this.jumpDelayTicks = 1;
      }
   }

   private void checkLandingDelay() {
      this.setLandingDelay();
      this.disableJumpControl();
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (this.jumpTicks != this.jumpDuration) {
         ++this.jumpTicks;
      } else if (this.jumpDuration != 0) {
         this.jumpTicks = 0;
         this.jumpDuration = 0;
         this.setJumping(false);
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.MOVEMENT_SPEED, 0.3F);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("RabbitType", this.getRabbitType());
      â˜ƒ.putInt("MoreCarrotTicks", this.moreCarrotTicks);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setRabbitType(â˜ƒ.getInt("RabbitType"));
      this.moreCarrotTicks = â˜ƒ.getInt("MoreCarrotTicks");
   }

   protected SoundEvent getJumpSound() {
      return SoundEvents.RABBIT_JUMP;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.RABBIT_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.RABBIT_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.RABBIT_DEATH;
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      if (this.getRabbitType() == 99) {
         this.playSound(SoundEvents.RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
         return â˜ƒ.hurt(DamageSource.mobAttack(this), 8.0F);
      } else {
         return â˜ƒ.hurt(DamageSource.mobAttack(this), 3.0F);
      }
   }

   @Override
   public SoundSource getSoundSource() {
      return this.getRabbitType() == 99 ? SoundSource.HOSTILE : SoundSource.NEUTRAL;
   }

   private static boolean isTemptingItem(ItemStack var0) {
      return â˜ƒ.is(Items.CARROT) || â˜ƒ.is(Items.GOLDEN_CARROT) || â˜ƒ.is(Blocks.DANDELION.asItem());
   }

   public Rabbit getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      Rabbit â˜ƒ = EntityType.RABBIT.create(â˜ƒ);
      int â˜ƒx = this.getRandomRabbitType(â˜ƒ);
      if (this.random.nextInt(20) != 0) {
         if (â˜ƒ instanceof Rabbit && this.random.nextBoolean()) {
            â˜ƒx = ((Rabbit)â˜ƒ).getRabbitType();
         } else {
            â˜ƒx = this.getRabbitType();
         }
      }

      â˜ƒ.setRabbitType(â˜ƒx);
      return â˜ƒ;
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return isTemptingItem(â˜ƒ);
   }

   public int getRabbitType() {
      return this.entityData.get(DATA_TYPE_ID);
   }

   public void setRabbitType(int var1) {
      if (â˜ƒ == 99) {
         this.getAttribute(Attributes.ARMOR).setBaseValue(8.0);
         this.goalSelector.addGoal(4, new Rabbit.EvilRabbitAttackGoal(this));
         this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
         this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
         this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Wolf.class, true));
         if (!this.hasCustomName()) {
            this.setCustomName(new TranslatableComponent(Util.makeDescriptionId("entity", KILLER_BUNNY)));
         }
      }

      this.entityData.set(DATA_TYPE_ID, â˜ƒ);
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      int â˜ƒ = this.getRandomRabbitType(â˜ƒ);
      if (â˜ƒ instanceof Rabbit.RabbitGroupData) {
         â˜ƒ = ((Rabbit.RabbitGroupData)â˜ƒ).rabbitType;
      } else {
         â˜ƒ = new Rabbit.RabbitGroupData(â˜ƒ);
      }

      this.setRabbitType(â˜ƒ);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private int getRandomRabbitType(LevelAccessor var1) {
      Biome â˜ƒ = â˜ƒ.getBiome(this.blockPosition());
      int â˜ƒx = this.random.nextInt(100);
      if (â˜ƒ.getPrecipitation() == Biome.Precipitation.SNOW) {
         return â˜ƒx < 80 ? 1 : 3;
      } else if (â˜ƒ.getBiomeCategory() == Biome.BiomeCategory.DESERT) {
         return 4;
      } else {
         return â˜ƒx < 50 ? 0 : (â˜ƒx < 90 ? 5 : 2);
      }
   }

   public static boolean checkRabbitSpawnRules(EntityType<Rabbit> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      return (â˜ƒ.is(Blocks.GRASS_BLOCK) || â˜ƒ.is(Blocks.SNOW) || â˜ƒ.is(Blocks.SAND)) && â˜ƒ.getRawBrightness(â˜ƒ, 0) > 8;
   }

   boolean wantsMoreFood() {
      return this.moreCarrotTicks == 0;
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 1) {
         this.spawnSprintParticle();
         this.jumpDuration = 10;
         this.jumpTicks = 0;
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   @Override
   public Vec3 getLeashOffset() {
      return new Vec3(0.0, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
   }

   static class EvilRabbitAttackGoal extends MeleeAttackGoal {
      public EvilRabbitAttackGoal(Rabbit var1) {
         super(â˜ƒ, 1.4, true);
      }

      @Override
      protected double getAttackReachSqr(LivingEntity var1) {
         return (double)(4.0F + â˜ƒ.getBbWidth());
      }
   }

   static class RabbitAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final Rabbit rabbit;

      public RabbitAvoidEntityGoal(Rabbit var1, Class<T> var2, float var3, double var4, double var6) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.rabbit = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return this.rabbit.getRabbitType() != 99 && super.canUse();
      }
   }

   public static class RabbitGroupData extends AgeableMob.AgeableMobGroupData {
      public final int rabbitType;

      public RabbitGroupData(int var1) {
         super(1.0F);
         this.rabbitType = â˜ƒ;
      }
   }

   public class RabbitJumpControl extends JumpControl {
      private final Rabbit rabbit;
      private boolean canJump;

      public RabbitJumpControl(Rabbit var2) {
         super(â˜ƒ);
         this.rabbit = â˜ƒ;
      }

      public boolean wantJump() {
         return this.jump;
      }

      public boolean canJump() {
         return this.canJump;
      }

      public void setCanJump(boolean var1) {
         this.canJump = â˜ƒ;
      }

      @Override
      public void tick() {
         if (this.jump) {
            this.rabbit.startJumping();
            this.jump = false;
         }
      }
   }

   static class RabbitMoveControl extends MoveControl {
      private final Rabbit rabbit;
      private double nextJumpSpeed;

      public RabbitMoveControl(Rabbit var1) {
         super(â˜ƒ);
         this.rabbit = â˜ƒ;
      }

      @Override
      public void tick() {
         if (this.rabbit.onGround && !this.rabbit.jumping && !((Rabbit.RabbitJumpControl)this.rabbit.jumpControl).wantJump()) {
            this.rabbit.setSpeedModifier(0.0);
         } else if (this.hasWanted()) {
            this.rabbit.setSpeedModifier(this.nextJumpSpeed);
         }

         super.tick();
      }

      @Override
      public void setWantedPosition(double var1, double var3, double var5, double var7) {
         if (this.rabbit.isInWater()) {
            â˜ƒ = 1.5;
         }

         super.setWantedPosition(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ > 0.0) {
            this.nextJumpSpeed = â˜ƒ;
         }
      }
   }

   static class RabbitPanicGoal extends PanicGoal {
      private final Rabbit rabbit;

      public RabbitPanicGoal(Rabbit var1, double var2) {
         super(â˜ƒ, â˜ƒ);
         this.rabbit = â˜ƒ;
      }

      @Override
      public void tick() {
         super.tick();
         this.rabbit.setSpeedModifier(this.speedModifier);
      }
   }

   static class RaidGardenGoal extends MoveToBlockGoal {
      private final Rabbit rabbit;
      private boolean wantsToRaid;
      private boolean canRaid;

      public RaidGardenGoal(Rabbit var1) {
         super(â˜ƒ, 0.7F, 16);
         this.rabbit = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         if (this.nextStartTick <= 0) {
            if (!this.rabbit.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
               return false;
            }

            this.canRaid = false;
            this.wantsToRaid = this.rabbit.wantsMoreFood();
            this.wantsToRaid = true;
         }

         return super.canUse();
      }

      @Override
      public boolean canContinueToUse() {
         return this.canRaid && super.canContinueToUse();
      }

      @Override
      public void tick() {
         super.tick();
         this.rabbit
            .getLookControl()
            .setLookAt(
               (double)this.blockPos.getX() + 0.5,
               (double)(this.blockPos.getY() + 1),
               (double)this.blockPos.getZ() + 0.5,
               10.0F,
               (float)this.rabbit.getMaxHeadXRot()
            );
         if (this.isReachedTarget()) {
            Level â˜ƒ = this.rabbit.level;
            BlockPos â˜ƒx = this.blockPos.above();
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            Block â˜ƒxxx = â˜ƒxx.getBlock();
            if (this.canRaid && â˜ƒxxx instanceof CarrotBlock) {
               int â˜ƒxxxx = â˜ƒxx.getValue(CarrotBlock.AGE);
               if (â˜ƒxxxx == 0) {
                  â˜ƒ.setBlock(â˜ƒx, Blocks.AIR.defaultBlockState(), 2);
                  â˜ƒ.destroyBlock(â˜ƒx, true, this.rabbit);
               } else {
                  â˜ƒ.setBlock(â˜ƒx, â˜ƒxx.setValue(CarrotBlock.AGE, Integer.valueOf(â˜ƒxxxx - 1)), 2);
                  â˜ƒ.levelEvent(2001, â˜ƒx, Block.getId(â˜ƒxx));
               }

               this.rabbit.moreCarrotTicks = 40;
            }

            this.canRaid = false;
            this.nextStartTick = 10;
         }
      }

      @Override
      protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         if (â˜ƒ.is(Blocks.FARMLAND) && this.wantsToRaid && !this.canRaid) {
            â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.above());
            if (â˜ƒ.getBlock() instanceof CarrotBlock && ((CarrotBlock)â˜ƒ.getBlock()).isMaxAge(â˜ƒ)) {
               this.canRaid = true;
               return true;
            }
         }

         return false;
      }
   }
}
