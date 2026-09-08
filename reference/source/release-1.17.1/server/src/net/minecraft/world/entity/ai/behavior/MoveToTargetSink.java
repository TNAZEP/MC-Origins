package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MoveToTargetSink extends Behavior<Mob> {
   private static final int MAX_COOLDOWN_BEFORE_RETRYING = 40;
   private int remainingCooldown;
   @Nullable
   private Path path;
   @Nullable
   private BlockPos lastTargetPos;
   private float speedModifier;

   public MoveToTargetSink() {
      this(150, 250);
   }

   public MoveToTargetSink(int var1, int var2) {
      super(
         ImmutableMap.of(
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryStatus.REGISTERED,
            MemoryModuleType.PATH,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_PRESENT
         ),
         â˜ƒ,
         â˜ƒ
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Mob var2) {
      if (this.remainingCooldown > 0) {
         --this.remainingCooldown;
         return false;
      } else {
         Brain<?> â˜ƒ = â˜ƒ.getBrain();
         WalkTarget â˜ƒx = (WalkTarget)â˜ƒ.getMemory(MemoryModuleType.WALK_TARGET).get();
         boolean â˜ƒxx = this.reachedTarget(â˜ƒ, â˜ƒx);
         if (!â˜ƒxx && this.tryComputePath(â˜ƒ, â˜ƒx, â˜ƒ.getGameTime())) {
            this.lastTargetPos = â˜ƒx.getTarget().currentBlockPosition();
            return true;
         } else {
            â˜ƒ.eraseMemory(MemoryModuleType.WALK_TARGET);
            if (â˜ƒxx) {
               â˜ƒ.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            }

            return false;
         }
      }
   }

   protected boolean canStillUse(ServerLevel var1, Mob var2, long var3) {
      if (this.path != null && this.lastTargetPos != null) {
         Optional<WalkTarget> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.WALK_TARGET);
         PathNavigation â˜ƒx = â˜ƒ.getNavigation();
         return !â˜ƒx.isDone() && â˜ƒ.isPresent() && !this.reachedTarget(â˜ƒ, (WalkTarget)â˜ƒ.get());
      } else {
         return false;
      }
   }

   protected void stop(ServerLevel var1, Mob var2, long var3) {
      if (â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.WALK_TARGET)
         && !this.reachedTarget(â˜ƒ, (WalkTarget)â˜ƒ.getBrain().getMemory(MemoryModuleType.WALK_TARGET).get())
         && â˜ƒ.getNavigation().isStuck()) {
         this.remainingCooldown = â˜ƒ.getRandom().nextInt(40);
      }

      â˜ƒ.getNavigation().stop();
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.PATH);
      this.path = null;
   }

   protected void start(ServerLevel var1, Mob var2, long var3) {
      â˜ƒ.getBrain().setMemory(MemoryModuleType.PATH, this.path);
      â˜ƒ.getNavigation().moveTo(this.path, (double)this.speedModifier);
   }

   protected void tick(ServerLevel var1, Mob var2, long var3) {
      Path â˜ƒ = â˜ƒ.getNavigation().getPath();
      Brain<?> â˜ƒx = â˜ƒ.getBrain();
      if (this.path != â˜ƒ) {
         this.path = â˜ƒ;
         â˜ƒx.setMemory(MemoryModuleType.PATH, â˜ƒ);
      }

      if (â˜ƒ != null && this.lastTargetPos != null) {
         WalkTarget â˜ƒ = (WalkTarget)â˜ƒx.getMemory(MemoryModuleType.WALK_TARGET).get();
         if (â˜ƒ.getTarget().currentBlockPosition().distSqr(this.lastTargetPos) > 4.0 && this.tryComputePath(â˜ƒ, â˜ƒ, â˜ƒ.getGameTime())) {
            this.lastTargetPos = â˜ƒ.getTarget().currentBlockPosition();
            this.start(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private boolean tryComputePath(Mob var1, WalkTarget var2, long var3) {
      BlockPos â˜ƒ = â˜ƒ.getTarget().currentBlockPosition();
      this.path = â˜ƒ.getNavigation().createPath(â˜ƒ, 0);
      this.speedModifier = â˜ƒ.getSpeedModifier();
      Brain<?> â˜ƒx = â˜ƒ.getBrain();
      if (this.reachedTarget(â˜ƒ, â˜ƒ)) {
         â˜ƒx.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      } else {
         boolean â˜ƒ = this.path != null && this.path.canReach();
         if (â˜ƒ) {
            â˜ƒx.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
         } else if (!â˜ƒx.hasMemoryValue(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
            â˜ƒx.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, â˜ƒ);
         }

         if (this.path != null) {
            return true;
         }

         Vec3 â˜ƒ = DefaultRandomPos.getPosTowards((PathfinderMob)â˜ƒ, 10, 7, Vec3.atBottomCenterOf(â˜ƒ), (float) (Math.PI / 2));
         if (â˜ƒ != null) {
            this.path = â˜ƒ.getNavigation().createPath(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, 0);
            return this.path != null;
         }
      }

      return false;
   }

   private boolean reachedTarget(Mob var1, WalkTarget var2) {
      return â˜ƒ.getTarget().currentBlockPosition().distManhattan(â˜ƒ.blockPosition()) <= â˜ƒ.getCloseEnoughDist();
   }
}
