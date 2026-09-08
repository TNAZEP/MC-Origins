package net.minecraft.world.entity;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public interface NeutralMob {
   String TAG_ANGER_TIME = "AngerTime";
   String TAG_ANGRY_AT = "AngryAt";

   int getRemainingPersistentAngerTime();

   void setRemainingPersistentAngerTime(int var1);

   @Nullable
   UUID getPersistentAngerTarget();

   void setPersistentAngerTarget(@Nullable UUID var1);

   void startPersistentAngerTimer();

   default void addPersistentAngerSaveData(CompoundTag var1) {
      â˜ƒ.putInt("AngerTime", this.getRemainingPersistentAngerTime());
      if (this.getPersistentAngerTarget() != null) {
         â˜ƒ.putUUID("AngryAt", this.getPersistentAngerTarget());
      }
   }

   default void readPersistentAngerSaveData(Level var1, CompoundTag var2) {
      this.setRemainingPersistentAngerTime(â˜ƒ.getInt("AngerTime"));
      if (â˜ƒ instanceof ServerLevel) {
         if (!â˜ƒ.hasUUID("AngryAt")) {
            this.setPersistentAngerTarget(null);
         } else {
            UUID â˜ƒ = â˜ƒ.getUUID("AngryAt");
            this.setPersistentAngerTarget(â˜ƒ);
            Entity â˜ƒx = ((ServerLevel)â˜ƒ).getEntity(â˜ƒ);
            if (â˜ƒx != null) {
               if (â˜ƒx instanceof Mob) {
                  this.setLastHurtByMob((Mob)â˜ƒx);
               }

               if (â˜ƒx.getType() == EntityType.PLAYER) {
                  this.setLastHurtByPlayer((Player)â˜ƒx);
               }
            }
         }
      }
   }

   default void updatePersistentAnger(ServerLevel var1, boolean var2) {
      LivingEntity â˜ƒ = this.getTarget();
      UUID â˜ƒx = this.getPersistentAngerTarget();
      if ((â˜ƒ == null || â˜ƒ.isDeadOrDying()) && â˜ƒx != null && â˜ƒ.getEntity(â˜ƒx) instanceof Mob) {
         this.stopBeingAngry();
      } else {
         if (â˜ƒ != null && !Objects.equals(â˜ƒx, â˜ƒ.getUUID())) {
            this.setPersistentAngerTarget(â˜ƒ.getUUID());
            this.startPersistentAngerTimer();
         }

         if (this.getRemainingPersistentAngerTime() > 0 && (â˜ƒ == null || â˜ƒ.getType() != EntityType.PLAYER || !â˜ƒ)) {
            this.setRemainingPersistentAngerTime(this.getRemainingPersistentAngerTime() - 1);
            if (this.getRemainingPersistentAngerTime() == 0) {
               this.stopBeingAngry();
            }
         }
      }
   }

   default boolean isAngryAt(LivingEntity var1) {
      if (!this.canAttack(â˜ƒ)) {
         return false;
      } else {
         return â˜ƒ.getType() == EntityType.PLAYER && this.isAngryAtAllPlayers(â˜ƒ.level) ? true : â˜ƒ.getUUID().equals(this.getPersistentAngerTarget());
      }
   }

   default boolean isAngryAtAllPlayers(Level var1) {
      return â˜ƒ.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER) && this.isAngry() && this.getPersistentAngerTarget() == null;
   }

   default boolean isAngry() {
      return this.getRemainingPersistentAngerTime() > 0;
   }

   default void playerDied(Player var1) {
      if (â˜ƒ.level.getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
         if (â˜ƒ.getUUID().equals(this.getPersistentAngerTarget())) {
            this.stopBeingAngry();
         }
      }
   }

   default void forgetCurrentTargetAndRefreshUniversalAnger() {
      this.stopBeingAngry();
      this.startPersistentAngerTimer();
   }

   default void stopBeingAngry() {
      this.setLastHurtByMob(null);
      this.setPersistentAngerTarget(null);
      this.setTarget(null);
      this.setRemainingPersistentAngerTime(0);
   }

   @Nullable
   LivingEntity getLastHurtByMob();

   void setLastHurtByMob(@Nullable LivingEntity var1);

   void setLastHurtByPlayer(@Nullable Player var1);

   void setTarget(@Nullable LivingEntity var1);

   boolean canAttack(LivingEntity var1);

   @Nullable
   LivingEntity getTarget();
}
