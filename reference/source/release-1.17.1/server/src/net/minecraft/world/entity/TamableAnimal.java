package net.minecraft.world.entity;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

public abstract class TamableAnimal extends Animal implements OwnableEntity {
   protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(TamableAnimal.class, EntityDataSerializers.BYTE);
   protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(
      TamableAnimal.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private boolean orderedToSit;

   protected TamableAnimal(EntityType<? extends TamableAnimal> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.reassessTameGoals();
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_FLAGS_ID, (byte)0);
      this.entityData.define(DATA_OWNERUUID_ID, Optional.empty());
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.getOwnerUUID() != null) {
         â˜ƒ.putUUID("Owner", this.getOwnerUUID());
      }

      â˜ƒ.putBoolean("Sitting", this.orderedToSit);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      UUID â˜ƒ;
      if (â˜ƒ.hasUUID("Owner")) {
         â˜ƒ = â˜ƒ.getUUID("Owner");
      } else {
         String â˜ƒ = â˜ƒ.getString("Owner");
         â˜ƒ = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), â˜ƒ);
      }

      if (â˜ƒ != null) {
         try {
            this.setOwnerUUID(â˜ƒ);
            this.setTame(true);
         } catch (Throwable var4) {
            this.setTame(false);
         }
      }

      this.orderedToSit = â˜ƒ.getBoolean("Sitting");
      this.setInSittingPose(this.orderedToSit);
   }

   @Override
   public boolean canBeLeashed(Player var1) {
      return !this.isLeashed();
   }

   protected void spawnTamingParticles(boolean var1) {
      ParticleOptions â˜ƒ = ParticleTypes.HEART;
      if (!â˜ƒ) {
         â˜ƒ = ParticleTypes.SMOKE;
      }

      for(int â˜ƒ = 0; â˜ƒ < 7; ++â˜ƒ) {
         double â˜ƒx = this.random.nextGaussian() * 0.02;
         double â˜ƒxx = this.random.nextGaussian() * 0.02;
         double â˜ƒxxx = this.random.nextGaussian() * 0.02;
         this.level.addParticle(â˜ƒ, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 7) {
         this.spawnTamingParticles(true);
      } else if (â˜ƒ == 6) {
         this.spawnTamingParticles(false);
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   public boolean isTame() {
      return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
   }

   public void setTame(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_FLAGS_ID);
      if (â˜ƒ) {
         this.entityData.set(DATA_FLAGS_ID, (byte)(â˜ƒ | 4));
      } else {
         this.entityData.set(DATA_FLAGS_ID, (byte)(â˜ƒ & -5));
      }

      this.reassessTameGoals();
   }

   protected void reassessTameGoals() {
   }

   public boolean isInSittingPose() {
      return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
   }

   public void setInSittingPose(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_FLAGS_ID);
      if (â˜ƒ) {
         this.entityData.set(DATA_FLAGS_ID, (byte)(â˜ƒ | 1));
      } else {
         this.entityData.set(DATA_FLAGS_ID, (byte)(â˜ƒ & -2));
      }
   }

   @Nullable
   @Override
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(DATA_OWNERUUID_ID)).orElse(null);
   }

   public void setOwnerUUID(@Nullable UUID var1) {
      this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(â˜ƒ));
   }

   public void tame(Player var1) {
      this.setTame(true);
      this.setOwnerUUID(â˜ƒ.getUUID());
      if (â˜ƒ instanceof ServerPlayer) {
         CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)â˜ƒ, this);
      }
   }

   @Nullable
   public LivingEntity getOwner() {
      try {
         UUID â˜ƒ = this.getOwnerUUID();
         return â˜ƒ == null ? null : this.level.getPlayerByUUID(â˜ƒ);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   @Override
   public boolean canAttack(LivingEntity var1) {
      return this.isOwnedBy(â˜ƒ) ? false : super.canAttack(â˜ƒ);
   }

   public boolean isOwnedBy(LivingEntity var1) {
      return â˜ƒ == this.getOwner();
   }

   public boolean wantsToAttack(LivingEntity var1, LivingEntity var2) {
      return true;
   }

   @Override
   public Team getTeam() {
      if (this.isTame()) {
         LivingEntity â˜ƒ = this.getOwner();
         if (â˜ƒ != null) {
            return â˜ƒ.getTeam();
         }
      }

      return super.getTeam();
   }

   @Override
   public boolean isAlliedTo(Entity var1) {
      if (this.isTame()) {
         LivingEntity â˜ƒ = this.getOwner();
         if (â˜ƒ == â˜ƒ) {
            return true;
         }

         if (â˜ƒ != null) {
            return â˜ƒ.isAlliedTo(â˜ƒ);
         }
      }

      return super.isAlliedTo(â˜ƒ);
   }

   @Override
   public void die(DamageSource var1) {
      if (!this.level.isClientSide && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
         this.getOwner().sendMessage(this.getCombatTracker().getDeathMessage(), Util.NIL_UUID);
      }

      super.die(â˜ƒ);
   }

   public boolean isOrderedToSit() {
      return this.orderedToSit;
   }

   public void setOrderedToSit(boolean var1) {
      this.orderedToSit = â˜ƒ;
   }
}
