package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public abstract class PatrollingMonster extends Monster {
   private BlockPos patrolTarget;
   private boolean patrolLeader;
   private boolean patrolling;

   protected PatrollingMonster(EntityType<? extends PatrollingMonster> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(4, new PatrollingMonster.LongDistancePatrolGoal<>(this, 0.7, 0.595));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.patrolTarget != null) {
         â˜ƒ.put("PatrolTarget", NbtUtils.writeBlockPos(this.patrolTarget));
      }

      â˜ƒ.putBoolean("PatrolLeader", this.patrolLeader);
      â˜ƒ.putBoolean("Patrolling", this.patrolling);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("PatrolTarget")) {
         this.patrolTarget = NbtUtils.readBlockPos(â˜ƒ.getCompound("PatrolTarget"));
      }

      this.patrolLeader = â˜ƒ.getBoolean("PatrolLeader");
      this.patrolling = â˜ƒ.getBoolean("Patrolling");
   }

   @Override
   public double getMyRidingOffset() {
      return -0.45;
   }

   public boolean canBeLeader() {
      return true;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      if (â˜ƒ != MobSpawnType.PATROL && â˜ƒ != MobSpawnType.EVENT && â˜ƒ != MobSpawnType.STRUCTURE && this.random.nextFloat() < 0.06F && this.canBeLeader()) {
         this.patrolLeader = true;
      }

      if (this.isPatrolLeader()) {
         this.setItemSlot(EquipmentSlot.HEAD, Raid.getLeaderBannerInstance());
         this.setDropChance(EquipmentSlot.HEAD, 2.0F);
      }

      if (â˜ƒ == MobSpawnType.PATROL) {
         this.patrolling = true;
      }

      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static boolean checkPatrollingMonsterSpawnRules(
      EntityType<? extends PatrollingMonster> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4
   ) {
      return â˜ƒ.getBrightness(LightLayer.BLOCK, â˜ƒ) > 8 ? false : checkAnyLightMonsterSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return !this.patrolling || â˜ƒ > 16384.0;
   }

   public void setPatrolTarget(BlockPos var1) {
      this.patrolTarget = â˜ƒ;
      this.patrolling = true;
   }

   public BlockPos getPatrolTarget() {
      return this.patrolTarget;
   }

   public boolean hasPatrolTarget() {
      return this.patrolTarget != null;
   }

   public void setPatrolLeader(boolean var1) {
      this.patrolLeader = â˜ƒ;
      this.patrolling = true;
   }

   public boolean isPatrolLeader() {
      return this.patrolLeader;
   }

   public boolean canJoinPatrol() {
      return true;
   }

   public void findPatrolTarget() {
      this.patrolTarget = this.blockPosition().offset(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
      this.patrolling = true;
   }

   protected boolean isPatrolling() {
      return this.patrolling;
   }

   protected void setPatrolling(boolean var1) {
      this.patrolling = â˜ƒ;
   }

   public static class LongDistancePatrolGoal<T extends PatrollingMonster> extends Goal {
      private static final int NAVIGATION_FAILED_COOLDOWN = 200;
      private final T mob;
      private final double speedModifier;
      private final double leaderSpeedModifier;
      private long cooldownUntil;

      public LongDistancePatrolGoal(T var1, double var2, double var4) {
         this.mob = â˜ƒ;
         this.speedModifier = â˜ƒ;
         this.leaderSpeedModifier = â˜ƒ;
         this.cooldownUntil = -1L;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         boolean â˜ƒ = this.mob.level.getGameTime() < this.cooldownUntil;
         return this.mob.isPatrolling() && this.mob.getTarget() == null && !this.mob.isVehicle() && this.mob.hasPatrolTarget() && !â˜ƒ;
      }

      @Override
      public void start() {
      }

      @Override
      public void stop() {
      }

      @Override
      public void tick() {
         boolean â˜ƒ = this.mob.isPatrolLeader();
         PathNavigation â˜ƒx = this.mob.getNavigation();
         if (â˜ƒx.isDone()) {
            List<PatrollingMonster> â˜ƒxx = this.findPatrolCompanions();
            if (this.mob.isPatrolling() && â˜ƒxx.isEmpty()) {
               this.mob.setPatrolling(false);
            } else if (â˜ƒ && this.mob.getPatrolTarget().closerThan(this.mob.position(), 10.0)) {
               this.mob.findPatrolTarget();
            } else {
               Vec3 â˜ƒxx = Vec3.atBottomCenterOf(this.mob.getPatrolTarget());
               Vec3 â˜ƒxxx = this.mob.position();
               Vec3 â˜ƒxxxx = â˜ƒxxx.subtract(â˜ƒxx);
               â˜ƒxx = â˜ƒxxxx.yRot(90.0F).scale(0.4).add(â˜ƒxx);
               Vec3 â˜ƒxxxxx = â˜ƒxx.subtract(â˜ƒxxx).normalize().scale(10.0).add(â˜ƒxxx);
               BlockPos â˜ƒxxxxxx = new BlockPos(â˜ƒxxxxx);
               â˜ƒxxxxxx = this.mob.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, â˜ƒxxxxxx);
               if (!â˜ƒx.moveTo(
                  (double)â˜ƒxxxxxx.getX(), (double)â˜ƒxxxxxx.getY(), (double)â˜ƒxxxxxx.getZ(), â˜ƒ ? this.leaderSpeedModifier : this.speedModifier
               )) {
                  this.moveRandomly();
                  this.cooldownUntil = this.mob.level.getGameTime() + 200L;
               } else if (â˜ƒ) {
                  for(PatrollingMonster â˜ƒxx : â˜ƒxx) {
                     â˜ƒxx.setPatrolTarget(â˜ƒxxxxxx);
                  }
               }
            }
         }
      }

      private List<PatrollingMonster> findPatrolCompanions() {
         return this.mob
            .level
            .getEntitiesOfClass(PatrollingMonster.class, this.mob.getBoundingBox().inflate(16.0), var1 -> var1.canJoinPatrol() && !var1.is(this.mob));
      }

      private boolean moveRandomly() {
         Random â˜ƒ = this.mob.getRandom();
         BlockPos â˜ƒx = this.mob
            .level
            .getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.mob.blockPosition().offset(-8 + â˜ƒ.nextInt(16), 0, -8 + â˜ƒ.nextInt(16)));
         return this.mob.getNavigation().moveTo((double)â˜ƒx.getX(), (double)â˜ƒx.getY(), (double)â˜ƒx.getZ(), this.speedModifier);
      }
   }
}
