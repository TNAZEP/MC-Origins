package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class SnowGolem extends AbstractGolem implements Shearable, RangedAttackMob {
   private static final EntityDataAccessor<Byte> DATA_PUMPKIN_ID = SynchedEntityData.defineId(SnowGolem.class, EntityDataSerializers.BYTE);
   private static final byte PUMPKIN_FLAG = 16;
   private static final float EYE_HEIGHT = 1.7F;

   public SnowGolem(EntityType<? extends SnowGolem> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25, 20, 10.0F));
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0, 1.0000001E-5F));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Mob.class, 10, true, false, var0 -> var0 instanceof Enemy));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0).add(Attributes.MOVEMENT_SPEED, 0.2F);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_PUMPKIN_ID, (byte)16);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putBoolean("Pumpkin", this.hasPumpkin());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("Pumpkin")) {
         this.setPumpkin(â˜ƒ.getBoolean("Pumpkin"));
      }
   }

   @Override
   public boolean isSensitiveToWater() {
      return true;
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (!this.level.isClientSide) {
         int â˜ƒ = Mth.floor(this.getX());
         int â˜ƒx = Mth.floor(this.getY());
         int â˜ƒxx = Mth.floor(this.getZ());
         if (this.level.getBiome(new BlockPos(â˜ƒ, 0, â˜ƒxx)).getTemperature(new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx)) > 1.0F) {
            this.hurt(DamageSource.ON_FIRE, 1.0F);
         }

         if (!this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
         }

         BlockState â˜ƒ = Blocks.SNOW.defaultBlockState();

         for(int â˜ƒx = 0; â˜ƒx < 4; ++â˜ƒx) {
            â˜ƒ = Mth.floor(this.getX() + (double)((float)(â˜ƒx % 2 * 2 - 1) * 0.25F));
            â˜ƒx = Mth.floor(this.getY());
            â˜ƒxx = Mth.floor(this.getZ() + (double)((float)(â˜ƒx / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos â˜ƒxx = new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
            if (this.level.getBlockState(â˜ƒxx).isAir() && this.level.getBiome(â˜ƒxx).getTemperature(â˜ƒxx) < 0.8F && â˜ƒ.canSurvive(this.level, â˜ƒxx)) {
               this.level.setBlockAndUpdate(â˜ƒxx, â˜ƒ);
            }
         }
      }
   }

   @Override
   public void performRangedAttack(LivingEntity var1, float var2) {
      Snowball â˜ƒ = new Snowball(this.level, this);
      double â˜ƒx = â˜ƒ.getEyeY() - 1.1F;
      double â˜ƒxx = â˜ƒ.getX() - this.getX();
      double â˜ƒxxx = â˜ƒx - â˜ƒ.getY();
      double â˜ƒxxxx = â˜ƒ.getZ() - this.getZ();
      double â˜ƒxxxxx = Math.sqrt(â˜ƒxx * â˜ƒxx + â˜ƒxxxx * â˜ƒxxxx) * 0.2F;
      â˜ƒ.shoot(â˜ƒxx, â˜ƒxxx + â˜ƒxxxxx, â˜ƒxxxx, 1.6F, 12.0F);
      this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.level.addFreshEntity(â˜ƒ);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 1.7F;
   }

   @Override
   protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.SHEARS) && this.readyForShearing()) {
         this.shear(SoundSource.PLAYERS);
         this.gameEvent(GameEvent.SHEAR, â˜ƒ);
         if (!this.level.isClientSide) {
            â˜ƒ.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
         }

         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public void shear(SoundSource var1) {
      this.level.playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, â˜ƒ, 1.0F, 1.0F);
      if (!this.level.isClientSide()) {
         this.setPumpkin(false);
         this.spawnAtLocation(new ItemStack(Items.CARVED_PUMPKIN), 1.7F);
      }
   }

   @Override
   public boolean readyForShearing() {
      return this.isAlive() && this.hasPumpkin();
   }

   public boolean hasPumpkin() {
      return (this.entityData.get(DATA_PUMPKIN_ID) & 16) != 0;
   }

   public void setPumpkin(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_PUMPKIN_ID);
      if (â˜ƒ) {
         this.entityData.set(DATA_PUMPKIN_ID, (byte)(â˜ƒ | 16));
      } else {
         this.entityData.set(DATA_PUMPKIN_ID, (byte)(â˜ƒ & -17));
      }
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.SNOW_GOLEM_AMBIENT;
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.SNOW_GOLEM_HURT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.SNOW_GOLEM_DEATH;
   }

   @Override
   public Vec3 getLeashOffset() {
      return new Vec3(0.0, (double)(0.75F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
   }
}
