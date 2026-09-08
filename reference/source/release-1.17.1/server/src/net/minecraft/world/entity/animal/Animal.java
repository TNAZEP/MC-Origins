package net.minecraft.world.entity.animal;

import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public abstract class Animal extends AgeableMob {
   static final int PARENT_AGE_AFTER_BREEDING = 6000;
   private int inLove;
   private UUID loveCause;

   protected Animal(EntityType<? extends Animal> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
      this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
   }

   @Override
   protected void customServerAiStep() {
      if (this.getAge() != 0) {
         this.inLove = 0;
      }

      super.customServerAiStep();
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (this.getAge() != 0) {
         this.inLove = 0;
      }

      if (this.inLove > 0) {
         --this.inLove;
         if (this.inLove % 10 == 0) {
            double â˜ƒ = this.random.nextGaussian() * 0.02;
            double â˜ƒx = this.random.nextGaussian() * 0.02;
            double â˜ƒxx = this.random.nextGaussian() * 0.02;
            this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), â˜ƒ, â˜ƒx, â˜ƒxx);
         }
      }
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         this.inLove = 0;
         return super.hurt(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : â˜ƒ.getBrightness(â˜ƒ) - 0.5F;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("InLove", this.inLove);
      if (this.loveCause != null) {
         â˜ƒ.putUUID("LoveCause", this.loveCause);
      }
   }

   @Override
   public double getMyRidingOffset() {
      return 0.14;
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.inLove = â˜ƒ.getInt("InLove");
      this.loveCause = â˜ƒ.hasUUID("LoveCause") ? â˜ƒ.getUUID("LoveCause") : null;
   }

   public static boolean checkAnimalSpawnRules(EntityType<? extends Animal> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).is(Blocks.GRASS_BLOCK) && â˜ƒ.getRawBrightness(â˜ƒ, 0) > 8;
   }

   @Override
   public int getAmbientSoundInterval() {
      return 120;
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return false;
   }

   @Override
   protected int getExperienceReward(Player var1) {
      return 1 + this.level.random.nextInt(3);
   }

   public boolean isFood(ItemStack var1) {
      return â˜ƒ.is(Items.WHEAT);
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (this.isFood(â˜ƒ)) {
         int â˜ƒx = this.getAge();
         if (!this.level.isClientSide && â˜ƒx == 0 && this.canFallInLove()) {
            this.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
            this.setInLove(â˜ƒ);
            this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
            return InteractionResult.SUCCESS;
         }

         if (this.isBaby()) {
            this.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
            this.ageUp((int)((float)(-â˜ƒx / 20) * 0.1F), true);
            this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }

         if (this.level.isClientSide) {
            return InteractionResult.CONSUME;
         }
      }

      return super.mobInteract(â˜ƒ, â˜ƒ);
   }

   protected void usePlayerItem(Player var1, InteractionHand var2, ItemStack var3) {
      if (!â˜ƒ.getAbilities().instabuild) {
         â˜ƒ.shrink(1);
      }
   }

   public boolean canFallInLove() {
      return this.inLove <= 0;
   }

   public void setInLove(@Nullable Player var1) {
      this.inLove = 600;
      if (â˜ƒ != null) {
         this.loveCause = â˜ƒ.getUUID();
      }

      this.level.broadcastEntityEvent(this, (byte)18);
   }

   public void setInLoveTime(int var1) {
      this.inLove = â˜ƒ;
   }

   public int getInLoveTime() {
      return this.inLove;
   }

   @Nullable
   public ServerPlayer getLoveCause() {
      if (this.loveCause == null) {
         return null;
      } else {
         Player â˜ƒ = this.level.getPlayerByUUID(this.loveCause);
         return â˜ƒ instanceof ServerPlayer ? (ServerPlayer)â˜ƒ : null;
      }
   }

   public boolean isInLove() {
      return this.inLove > 0;
   }

   public void resetLove() {
      this.inLove = 0;
   }

   public boolean canMate(Animal var1) {
      if (â˜ƒ == this) {
         return false;
      } else if (â˜ƒ.getClass() != this.getClass()) {
         return false;
      } else {
         return this.isInLove() && â˜ƒ.isInLove();
      }
   }

   public void spawnChildFromBreeding(ServerLevel var1, Animal var2) {
      AgeableMob â˜ƒ = this.getBreedOffspring(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         ServerPlayer â˜ƒx = this.getLoveCause();
         if (â˜ƒx == null && â˜ƒ.getLoveCause() != null) {
            â˜ƒx = â˜ƒ.getLoveCause();
         }

         if (â˜ƒx != null) {
            â˜ƒx.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(â˜ƒx, this, â˜ƒ, â˜ƒ);
         }

         this.setAge(6000);
         â˜ƒ.setAge(6000);
         this.resetLove();
         â˜ƒ.resetLove();
         â˜ƒ.setBaby(true);
         â˜ƒ.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
         â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
         â˜ƒ.broadcastEntityEvent(this, (byte)18);
         if (â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            â˜ƒ.addFreshEntity(new ExperienceOrb(â˜ƒ, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
         }
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 18) {
         for(int â˜ƒ = 0; â˜ƒ < 7; ++â˜ƒ) {
            double â˜ƒx = this.random.nextGaussian() * 0.02;
            double â˜ƒxx = this.random.nextGaussian() * 0.02;
            double â˜ƒxxx = this.random.nextGaussian() * 0.02;
            this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }
}
