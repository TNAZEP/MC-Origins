package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class PrepareRamNearestTarget<E extends PathfinderMob> extends Behavior<E> {
   public static final int TIME_OUT_DURATION = 160;
   private final ToIntFunction<E> getCooldownOnFail;
   private final int minRamDistance;
   private final int maxRamDistance;
   private final float walkSpeed;
   private final TargetingConditions ramTargeting;
   private final int ramPrepareTime;
   private final Function<E, SoundEvent> getPrepareRamSound;
   private Optional<Long> reachedRamPositionTimestamp = Optional.empty();
   private Optional<PrepareRamNearestTarget.RamCandidate> ramCandidate = Optional.empty();

   public PrepareRamNearestTarget(ToIntFunction<E> var1, int var2, int var3, float var4, TargetingConditions var5, int var6, Function<E, SoundEvent> var7) {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.RAM_COOLDOWN_TICKS,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.RAM_TARGET,
            MemoryStatus.VALUE_ABSENT
         ),
         160
      );
      this.getCooldownOnFail = â˜ƒ;
      this.minRamDistance = â˜ƒ;
      this.maxRamDistance = â˜ƒ;
      this.walkSpeed = â˜ƒ;
      this.ramTargeting = â˜ƒ;
      this.ramPrepareTime = â˜ƒ;
      this.getPrepareRamSound = â˜ƒ;
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
         .flatMap(var2x -> var2x.stream().filter(var2xx -> this.ramTargeting.test(â˜ƒ, var2xx)).findFirst())
         .ifPresent(var2x -> this.chooseRamPosition(â˜ƒ, var2x));
   }

   protected void stop(ServerLevel var1, E var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      if (!â˜ƒ.hasMemoryValue(MemoryModuleType.RAM_TARGET)) {
         â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)59);
         â˜ƒ.setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, this.getCooldownOnFail.applyAsInt(â˜ƒ));
      }
   }

   protected boolean canStillUse(ServerLevel var1, PathfinderMob var2, long var3) {
      return this.ramCandidate.isPresent() && ((PrepareRamNearestTarget.RamCandidate)this.ramCandidate.get()).getTarget().isAlive();
   }

   protected void tick(ServerLevel var1, E var2, long var3) {
      if (this.ramCandidate.isPresent()) {
         â˜ƒ.getBrain()
            .setMemory(
               MemoryModuleType.WALK_TARGET,
               new WalkTarget(((PrepareRamNearestTarget.RamCandidate)this.ramCandidate.get()).getStartPosition(), this.walkSpeed, 0)
            );
         â˜ƒ.getBrain()
            .setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(((PrepareRamNearestTarget.RamCandidate)this.ramCandidate.get()).getTarget(), true));
         boolean â˜ƒ = !((PrepareRamNearestTarget.RamCandidate)this.ramCandidate.get())
            .getTarget()
            .blockPosition()
            .equals(((PrepareRamNearestTarget.RamCandidate)this.ramCandidate.get()).getTargetPosition());
         if (â˜ƒ) {
            â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)59);
            â˜ƒ.getNavigation().stop();
            this.chooseRamPosition(â˜ƒ, ((PrepareRamNearestTarget.RamCandidate)this.ramCandidate.get()).target);
         } else {
            BlockPos â˜ƒ = â˜ƒ.blockPosition();
            if (â˜ƒ.equals(((PrepareRamNearestTarget.RamCandidate)this.ramCandidate.get()).getStartPosition())) {
               â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)58);
               if (!this.reachedRamPositionTimestamp.isPresent()) {
                  this.reachedRamPositionTimestamp = Optional.of(â˜ƒ);
               }

               if (â˜ƒ - this.reachedRamPositionTimestamp.get() >= (long)this.ramPrepareTime) {
                  â˜ƒ.getBrain()
                     .setMemory(
                        MemoryModuleType.RAM_TARGET,
                        this.getEdgeOfBlock(â˜ƒ, ((PrepareRamNearestTarget.RamCandidate)this.ramCandidate.get()).getTargetPosition())
                     );
                  â˜ƒ.playSound(null, â˜ƒ, (SoundEvent)this.getPrepareRamSound.apply(â˜ƒ), SoundSource.HOSTILE, 1.0F, â˜ƒ.getVoicePitch());
                  this.ramCandidate = Optional.empty();
               }
            }
         }
      }
   }

   private Vec3 getEdgeOfBlock(BlockPos var1, BlockPos var2) {
      double â˜ƒ = 0.5;
      double â˜ƒx = 0.5 * (double)Mth.sign((double)(â˜ƒ.getX() - â˜ƒ.getX()));
      double â˜ƒxx = 0.5 * (double)Mth.sign((double)(â˜ƒ.getZ() - â˜ƒ.getZ()));
      return Vec3.atBottomCenterOf(â˜ƒ).add(â˜ƒx, 0.0, â˜ƒxx);
   }

   private Optional<BlockPos> calculateRammingStartPosition(PathfinderMob var1, LivingEntity var2) {
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      if (!this.isWalkableBlock(â˜ƒ, â˜ƒ)) {
         return Optional.empty();
      } else {
         List<BlockPos> â˜ƒ = Lists.<BlockPos>newArrayList();
         BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable();

         for(Direction â˜ƒxx : Direction.Plane.HORIZONTAL) {
            â˜ƒx.set(â˜ƒ);

            for(int â˜ƒxxx = 0; â˜ƒxxx < this.maxRamDistance; ++â˜ƒxxx) {
               if (!this.isWalkableBlock(â˜ƒ, â˜ƒx.move(â˜ƒxx))) {
                  â˜ƒx.move(â˜ƒxx.getOpposite());
                  break;
               }
            }

            if (â˜ƒx.distManhattan(â˜ƒ) >= this.minRamDistance) {
               â˜ƒ.add(â˜ƒx.immutable());
            }
         }

         PathNavigation â˜ƒxx = â˜ƒ.getNavigation();
         return â˜ƒ.stream().sorted(Comparator.comparingDouble(â˜ƒ.blockPosition()::distSqr)).filter(var1x -> {
            Path â˜ƒ = â˜ƒ.createPath(var1x, 0);
            return â˜ƒ != null && â˜ƒ.canReach();
         }).findFirst();
      }
   }

   private boolean isWalkableBlock(PathfinderMob var1, BlockPos var2) {
      return â˜ƒ.getNavigation().isStableDestination(â˜ƒ)
         && â˜ƒ.getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic(â˜ƒ.level, â˜ƒ.mutable())) == 0.0F;
   }

   private void chooseRamPosition(PathfinderMob var1, LivingEntity var2) {
      this.reachedRamPositionTimestamp = Optional.empty();
      this.ramCandidate = this.calculateRammingStartPosition(â˜ƒ, â˜ƒ).map(var1x -> new PrepareRamNearestTarget.RamCandidate(var1x, â˜ƒ.blockPosition(), â˜ƒ));
   }

   public static class RamCandidate {
      private final BlockPos startPosition;
      private final BlockPos targetPosition;
      final LivingEntity target;

      public RamCandidate(BlockPos var1, BlockPos var2, LivingEntity var3) {
         this.startPosition = â˜ƒ;
         this.targetPosition = â˜ƒ;
         this.target = â˜ƒ;
      }

      public BlockPos getStartPosition() {
         return this.startPosition;
      }

      public BlockPos getTargetPosition() {
         return this.targetPosition;
      }

      public LivingEntity getTarget() {
         return this.target;
      }
   }
}
