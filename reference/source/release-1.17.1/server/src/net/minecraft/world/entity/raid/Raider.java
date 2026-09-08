package net.minecraft.world.entity.raid;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PathfindToRaidGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public abstract class Raider extends PatrollingMonster {
   protected static final EntityDataAccessor<Boolean> IS_CELEBRATING = SynchedEntityData.defineId(Raider.class, EntityDataSerializers.BOOLEAN);
   static final Predicate<ItemEntity> ALLOWED_ITEMS = var0 -> !var0.hasPickUpDelay()
         && var0.isAlive()
         && ItemStack.matches(var0.getItem(), Raid.getLeaderBannerInstance());
   @Nullable
   protected Raid raid;
   private int wave;
   private boolean canJoinRaid;
   private int ticksOutsideRaid;

   protected Raider(EntityType<? extends Raider> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new Raider.ObtainRaidLeaderBannerGoal<>(this));
      this.goalSelector.addGoal(3, new PathfindToRaidGoal<>(this));
      this.goalSelector.addGoal(4, new Raider.RaiderMoveThroughVillageGoal(this, 1.05F, 1));
      this.goalSelector.addGoal(5, new Raider.RaiderCelebration(this));
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(IS_CELEBRATING, false);
   }

   public abstract void applyRaidBuffs(int var1, boolean var2);

   public boolean canJoinRaid() {
      return this.canJoinRaid;
   }

   public void setCanJoinRaid(boolean var1) {
      this.canJoinRaid = â˜ƒ;
   }

   @Override
   public void aiStep() {
      if (this.level instanceof ServerLevel && this.isAlive()) {
         Raid â˜ƒ = this.getCurrentRaid();
         if (this.canJoinRaid()) {
            if (â˜ƒ == null) {
               if (this.level.getGameTime() % 20L == 0L) {
                  Raid â˜ƒx = ((ServerLevel)this.level).getRaidAt(this.blockPosition());
                  if (â˜ƒx != null && Raids.canJoinRaid(this, â˜ƒx)) {
                     â˜ƒx.joinRaid(â˜ƒx.getGroupsSpawned(), this, null, true);
                  }
               }
            } else {
               LivingEntity â˜ƒx = this.getTarget();
               if (â˜ƒx != null && (â˜ƒx.getType() == EntityType.PLAYER || â˜ƒx.getType() == EntityType.IRON_GOLEM)) {
                  this.noActionTime = 0;
               }
            }
         }
      }

      super.aiStep();
   }

   @Override
   protected void updateNoActionTime() {
      this.noActionTime += 2;
   }

   @Override
   public void die(DamageSource var1) {
      if (this.level instanceof ServerLevel) {
         Entity â˜ƒ = â˜ƒ.getEntity();
         Raid â˜ƒx = this.getCurrentRaid();
         if (â˜ƒx != null) {
            if (this.isPatrolLeader()) {
               â˜ƒx.removeLeader(this.getWave());
            }

            if (â˜ƒ != null && â˜ƒ.getType() == EntityType.PLAYER) {
               â˜ƒx.addHeroOfTheVillage(â˜ƒ);
            }

            â˜ƒx.removeFromRaid(this, false);
         }

         if (this.isPatrolLeader() && â˜ƒx == null && ((ServerLevel)this.level).getRaidAt(this.blockPosition()) == null) {
            ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.HEAD);
            Player â˜ƒx = null;
            if (â˜ƒ instanceof Player) {
               â˜ƒx = (Player)â˜ƒ;
            } else if (â˜ƒ instanceof Wolf â˜ƒ) {
               LivingEntity â˜ƒx = â˜ƒ.getOwner();
               if (â˜ƒ.isTame() && â˜ƒx instanceof Player) {
                  â˜ƒx = (Player)â˜ƒx;
               }
            }

            if (!â˜ƒ.isEmpty() && ItemStack.matches(â˜ƒ, Raid.getLeaderBannerInstance()) && â˜ƒx != null) {
               MobEffectInstance â˜ƒ = â˜ƒx.getEffect(MobEffects.BAD_OMEN);
               int â˜ƒx = 1;
               if (â˜ƒ != null) {
                  â˜ƒx += â˜ƒ.getAmplifier();
                  â˜ƒx.removeEffectNoUpdate(MobEffects.BAD_OMEN);
               } else {
                  --â˜ƒx;
               }

               â˜ƒx = Mth.clamp(â˜ƒx, 0, 4);
               MobEffectInstance â˜ƒ = new MobEffectInstance(MobEffects.BAD_OMEN, 120000, â˜ƒx, false, false, true);
               if (!this.level.getGameRules().getBoolean(GameRules.RULE_DISABLE_RAIDS)) {
                  â˜ƒx.addEffect(â˜ƒ);
               }
            }
         }
      }

      super.die(â˜ƒ);
   }

   @Override
   public boolean canJoinPatrol() {
      return !this.hasActiveRaid();
   }

   public void setCurrentRaid(@Nullable Raid var1) {
      this.raid = â˜ƒ;
   }

   @Nullable
   public Raid getCurrentRaid() {
      return this.raid;
   }

   public boolean hasActiveRaid() {
      return this.getCurrentRaid() != null && this.getCurrentRaid().isActive();
   }

   public void setWave(int var1) {
      this.wave = â˜ƒ;
   }

   public int getWave() {
      return this.wave;
   }

   public boolean isCelebrating() {
      return this.entityData.get(IS_CELEBRATING);
   }

   public void setCelebrating(boolean var1) {
      this.entityData.set(IS_CELEBRATING, â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Wave", this.wave);
      â˜ƒ.putBoolean("CanJoinRaid", this.canJoinRaid);
      if (this.raid != null) {
         â˜ƒ.putInt("RaidId", this.raid.getId());
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.wave = â˜ƒ.getInt("Wave");
      this.canJoinRaid = â˜ƒ.getBoolean("CanJoinRaid");
      if (â˜ƒ.contains("RaidId", 3)) {
         if (this.level instanceof ServerLevel) {
            this.raid = ((ServerLevel)this.level).getRaids().get(â˜ƒ.getInt("RaidId"));
         }

         if (this.raid != null) {
            this.raid.addWaveMob(this.wave, this, false);
            if (this.isPatrolLeader()) {
               this.raid.setLeader(this.wave, this);
            }
         }
      }
   }

   @Override
   protected void pickUpItem(ItemEntity var1) {
      ItemStack â˜ƒ = â˜ƒ.getItem();
      boolean â˜ƒx = this.hasActiveRaid() && this.getCurrentRaid().getLeader(this.getWave()) != null;
      if (this.hasActiveRaid() && !â˜ƒx && ItemStack.matches(â˜ƒ, Raid.getLeaderBannerInstance())) {
         EquipmentSlot â˜ƒxx = EquipmentSlot.HEAD;
         ItemStack â˜ƒxxx = this.getItemBySlot(â˜ƒxx);
         double â˜ƒxxxx = (double)this.getEquipmentDropChance(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < â˜ƒxxxx) {
            this.spawnAtLocation(â˜ƒxxx);
         }

         this.onItemPickup(â˜ƒ);
         this.setItemSlot(â˜ƒxx, â˜ƒ);
         this.take(â˜ƒ, â˜ƒ.getCount());
         â˜ƒ.discard();
         this.getCurrentRaid().setLeader(this.getWave(), this);
         this.setPatrolLeader(true);
      } else {
         super.pickUpItem(â˜ƒ);
      }
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return this.getCurrentRaid() == null ? super.removeWhenFarAway(â˜ƒ) : false;
   }

   @Override
   public boolean requiresCustomPersistence() {
      return super.requiresCustomPersistence() || this.getCurrentRaid() != null;
   }

   public int getTicksOutsideRaid() {
      return this.ticksOutsideRaid;
   }

   public void setTicksOutsideRaid(int var1) {
      this.ticksOutsideRaid = â˜ƒ;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.hasActiveRaid()) {
         this.getCurrentRaid().updateBossbar();
      }

      return super.hurt(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.setCanJoinRaid(this.getType() != EntityType.WITCH || â˜ƒ != MobSpawnType.NATURAL);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public abstract SoundEvent getCelebrateSound();

   protected class HoldGroundAttackGoal extends Goal {
      private final Raider mob;
      private final float hostileRadiusSqr;
      public final TargetingConditions shoutTargeting = TargetingConditions.forNonCombat().range(8.0).ignoreLineOfSight().ignoreInvisibilityTesting();

      public HoldGroundAttackGoal(AbstractIllager var2, float var3) {
         this.mob = â˜ƒ;
         this.hostileRadiusSqr = â˜ƒ * â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         LivingEntity â˜ƒ = this.mob.getLastHurtByMob();
         return this.mob.getCurrentRaid() == null
            && this.mob.isPatrolling()
            && this.mob.getTarget() != null
            && !this.mob.isAggressive()
            && (â˜ƒ == null || â˜ƒ.getType() != EntityType.PLAYER);
      }

      @Override
      public void start() {
         super.start();
         this.mob.getNavigation().stop();

         for(Raider â˜ƒ : this.mob.level.getNearbyEntities(Raider.class, this.shoutTargeting, this.mob, this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0))) {
            â˜ƒ.setTarget(this.mob.getTarget());
         }
      }

      @Override
      public void stop() {
         super.stop();
         LivingEntity â˜ƒ = this.mob.getTarget();
         if (â˜ƒ != null) {
            for(Raider â˜ƒx : this.mob.level.getNearbyEntities(Raider.class, this.shoutTargeting, this.mob, this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0))) {
               â˜ƒx.setTarget(â˜ƒ);
               â˜ƒx.setAggressive(true);
            }

            this.mob.setAggressive(true);
         }
      }

      @Override
      public void tick() {
         LivingEntity â˜ƒ = this.mob.getTarget();
         if (â˜ƒ != null) {
            if (this.mob.distanceToSqr(â˜ƒ) > (double)this.hostileRadiusSqr) {
               this.mob.getLookControl().setLookAt(â˜ƒ, 30.0F, 30.0F);
               if (this.mob.random.nextInt(50) == 0) {
                  this.mob.playAmbientSound();
               }
            } else {
               this.mob.setAggressive(true);
            }

            super.tick();
         }
      }
   }

   public class ObtainRaidLeaderBannerGoal<T extends Raider> extends Goal {
      private final T mob;

      public ObtainRaidLeaderBannerGoal(T var2) {
         this.mob = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         Raid â˜ƒ = this.mob.getCurrentRaid();
         if (this.mob.hasActiveRaid()
            && !this.mob.getCurrentRaid().isOver()
            && this.mob.canBeLeader()
            && !ItemStack.matches(this.mob.getItemBySlot(EquipmentSlot.HEAD), Raid.getLeaderBannerInstance())) {
            Raider â˜ƒx = â˜ƒ.getLeader(this.mob.getWave());
            if (â˜ƒx == null || !â˜ƒx.isAlive()) {
               List<ItemEntity> â˜ƒxx = this.mob
                  .level
                  .getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(16.0, 8.0, 16.0), Raider.ALLOWED_ITEMS);
               if (!â˜ƒxx.isEmpty()) {
                  return this.mob.getNavigation().moveTo((Entity)â˜ƒxx.get(0), 1.15F);
               }
            }

            return false;
         } else {
            return false;
         }
      }

      @Override
      public void tick() {
         if (this.mob.getNavigation().getTargetPos().closerThan(this.mob.position(), 1.414)) {
            List<ItemEntity> â˜ƒ = this.mob.level.getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(4.0, 4.0, 4.0), Raider.ALLOWED_ITEMS);
            if (!â˜ƒ.isEmpty()) {
               this.mob.pickUpItem((ItemEntity)â˜ƒ.get(0));
            }
         }
      }
   }

   public class RaiderCelebration extends Goal {
      private final Raider mob;

      RaiderCelebration(Raider var2) {
         this.mob = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         Raid â˜ƒ = this.mob.getCurrentRaid();
         return this.mob.isAlive() && this.mob.getTarget() == null && â˜ƒ != null && â˜ƒ.isLoss();
      }

      @Override
      public void start() {
         this.mob.setCelebrating(true);
         super.start();
      }

      @Override
      public void stop() {
         this.mob.setCelebrating(false);
         super.stop();
      }

      @Override
      public void tick() {
         if (!this.mob.isSilent() && this.mob.random.nextInt(100) == 0) {
            Raider.this.playSound(Raider.this.getCelebrateSound(), Raider.this.getSoundVolume(), Raider.this.getVoicePitch());
         }

         if (!this.mob.isPassenger() && this.mob.random.nextInt(50) == 0) {
            this.mob.getJumpControl().jump();
         }

         super.tick();
      }
   }

   static class RaiderMoveThroughVillageGoal extends Goal {
      private final Raider raider;
      private final double speedModifier;
      private BlockPos poiPos;
      private final List<BlockPos> visited = Lists.<BlockPos>newArrayList();
      private final int distanceToPoi;
      private boolean stuck;

      public RaiderMoveThroughVillageGoal(Raider var1, double var2, int var4) {
         this.raider = â˜ƒ;
         this.speedModifier = â˜ƒ;
         this.distanceToPoi = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         this.updateVisited();
         return this.isValidRaid() && this.hasSuitablePoi() && this.raider.getTarget() == null;
      }

      private boolean isValidRaid() {
         return this.raider.hasActiveRaid() && !this.raider.getCurrentRaid().isOver();
      }

      private boolean hasSuitablePoi() {
         ServerLevel â˜ƒ = (ServerLevel)this.raider.level;
         BlockPos â˜ƒx = this.raider.blockPosition();
         Optional<BlockPos> â˜ƒxx = â˜ƒ.getPoiManager()
            .getRandom(var0 -> var0 == PoiType.HOME, this::hasNotVisited, PoiManager.Occupancy.ANY, â˜ƒx, 48, this.raider.random);
         if (!â˜ƒxx.isPresent()) {
            return false;
         } else {
            this.poiPos = ((BlockPos)â˜ƒxx.get()).immutable();
            return true;
         }
      }

      @Override
      public boolean canContinueToUse() {
         if (this.raider.getNavigation().isDone()) {
            return false;
         } else {
            return this.raider.getTarget() == null
               && !this.poiPos.closerThan(this.raider.position(), (double)(this.raider.getBbWidth() + (float)this.distanceToPoi))
               && !this.stuck;
         }
      }

      @Override
      public void stop() {
         if (this.poiPos.closerThan(this.raider.position(), (double)this.distanceToPoi)) {
            this.visited.add(this.poiPos);
         }
      }

      @Override
      public void start() {
         super.start();
         this.raider.setNoActionTime(0);
         this.raider.getNavigation().moveTo((double)this.poiPos.getX(), (double)this.poiPos.getY(), (double)this.poiPos.getZ(), this.speedModifier);
         this.stuck = false;
      }

      @Override
      public void tick() {
         if (this.raider.getNavigation().isDone()) {
            Vec3 â˜ƒ = Vec3.atBottomCenterOf(this.poiPos);
            Vec3 â˜ƒx = DefaultRandomPos.getPosTowards(this.raider, 16, 7, â˜ƒ, (float) (Math.PI / 10));
            if (â˜ƒx == null) {
               â˜ƒx = DefaultRandomPos.getPosTowards(this.raider, 8, 7, â˜ƒ, (float) (Math.PI / 2));
            }

            if (â˜ƒx == null) {
               this.stuck = true;
               return;
            }

            this.raider.getNavigation().moveTo(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z, this.speedModifier);
         }
      }

      private boolean hasNotVisited(BlockPos var1) {
         for(BlockPos â˜ƒ : this.visited) {
            if (Objects.equals(â˜ƒ, â˜ƒ)) {
               return false;
            }
         }

         return true;
      }

      private void updateVisited() {
         if (this.visited.size() > 2) {
            this.visited.remove(0);
         }
      }
   }
}
