package net.minecraft.world.entity.animal;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Pufferfish extends AbstractFish {
   private static final EntityDataAccessor<Integer> PUFF_STATE = SynchedEntityData.defineId(Pufferfish.class, EntityDataSerializers.INT);
   int inflateCounter;
   int deflateTimer;
   private static final Predicate<LivingEntity> SCARY_MOB = var0 -> {
      if (var0 instanceof Player && ((Player)var0).isCreative()) {
         return false;
      } else {
         return var0.getType() == EntityType.AXOLOTL || var0.getMobType() != MobType.WATER;
      }
   };
   static final TargetingConditions targetingConditions = TargetingConditions.forNonCombat()
      .ignoreInvisibilityTesting()
      .ignoreLineOfSight()
      .selector(SCARY_MOB);
   public static final int STATE_SMALL = 0;
   public static final int STATE_MID = 1;
   public static final int STATE_FULL = 2;

   public Pufferfish(EntityType<? extends Pufferfish> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.refreshDimensions();
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(PUFF_STATE, 0);
   }

   public int getPuffState() {
      return this.entityData.get(PUFF_STATE);
   }

   public void setPuffState(int var1) {
      this.entityData.set(PUFF_STATE, â˜ƒ);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (PUFF_STATE.equals(â˜ƒ)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("PuffState", this.getPuffState());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setPuffState(â˜ƒ.getInt("PuffState"));
   }

   @Override
   public ItemStack getBucketItemStack() {
      return new ItemStack(Items.PUFFERFISH_BUCKET);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new Pufferfish.PufferfishPuffGoal(this));
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
         if (this.inflateCounter > 0) {
            if (this.getPuffState() == 0) {
               this.playSound(SoundEvents.PUFFER_FISH_BLOW_UP, this.getSoundVolume(), this.getVoicePitch());
               this.setPuffState(1);
            } else if (this.inflateCounter > 40 && this.getPuffState() == 1) {
               this.playSound(SoundEvents.PUFFER_FISH_BLOW_UP, this.getSoundVolume(), this.getVoicePitch());
               this.setPuffState(2);
            }

            ++this.inflateCounter;
         } else if (this.getPuffState() != 0) {
            if (this.deflateTimer > 60 && this.getPuffState() == 2) {
               this.playSound(SoundEvents.PUFFER_FISH_BLOW_OUT, this.getSoundVolume(), this.getVoicePitch());
               this.setPuffState(1);
            } else if (this.deflateTimer > 100 && this.getPuffState() == 1) {
               this.playSound(SoundEvents.PUFFER_FISH_BLOW_OUT, this.getSoundVolume(), this.getVoicePitch());
               this.setPuffState(0);
            }

            ++this.deflateTimer;
         }
      }

      super.tick();
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (this.isAlive() && this.getPuffState() > 0) {
         for(Mob â˜ƒ : this.level.getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(0.3), var1 -> targetingConditions.test(this, var1))) {
            if (â˜ƒ.isAlive()) {
               this.touch(â˜ƒ);
            }
         }
      }
   }

   private void touch(Mob var1) {
      int â˜ƒ = this.getPuffState();
      if (â˜ƒ.hurt(DamageSource.mobAttack(this), (float)(1 + â˜ƒ))) {
         â˜ƒ.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * â˜ƒ, 0), this);
         this.playSound(SoundEvents.PUFFER_FISH_STING, 1.0F, 1.0F);
      }
   }

   @Override
   public void playerTouch(Player var1) {
      int â˜ƒ = this.getPuffState();
      if (â˜ƒ instanceof ServerPlayer && â˜ƒ > 0 && â˜ƒ.hurt(DamageSource.mobAttack(this), (float)(1 + â˜ƒ))) {
         if (!this.isSilent()) {
            ((ServerPlayer)â˜ƒ).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0F));
         }

         â˜ƒ.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * â˜ƒ, 0), this);
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.PUFFER_FISH_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.PUFFER_FISH_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.PUFFER_FISH_HURT;
   }

   @Override
   protected SoundEvent getFlopSound() {
      return SoundEvents.PUFFER_FISH_FLOP;
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      return super.getDimensions(â˜ƒ).scale(getScale(this.getPuffState()));
   }

   private static float getScale(int var0) {
      switch(â˜ƒ) {
         case 0:
            return 0.5F;
         case 1:
            return 0.7F;
         default:
            return 1.0F;
      }
   }

   static class PufferfishPuffGoal extends Goal {
      private final Pufferfish fish;

      public PufferfishPuffGoal(Pufferfish var1) {
         this.fish = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         List<LivingEntity> â˜ƒ = this.fish
            .level
            .getEntitiesOfClass(LivingEntity.class, this.fish.getBoundingBox().inflate(2.0), var1x -> Pufferfish.targetingConditions.test(this.fish, var1x));
         return !â˜ƒ.isEmpty();
      }

      @Override
      public void start() {
         this.fish.inflateCounter = 1;
         this.fish.deflateTimer = 0;
      }

      @Override
      public void stop() {
         this.fish.inflateCounter = 0;
      }
   }
}
