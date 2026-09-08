package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class SetWalkTargetAwayFrom<T> extends Behavior<PathfinderMob> {
   private final MemoryModuleType<T> walkAwayFromMemory;
   private final float speedModifier;
   private final int desiredDistance;
   private final Function<T, Vec3> toPosition;

   public SetWalkTargetAwayFrom(MemoryModuleType<T> var1, float var2, int var3, boolean var4, Function<T, Vec3> var5) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, â˜ƒ ? MemoryStatus.REGISTERED : MemoryStatus.VALUE_ABSENT, â˜ƒ, MemoryStatus.VALUE_PRESENT));
      this.walkAwayFromMemory = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.desiredDistance = â˜ƒ;
      this.toPosition = â˜ƒ;
   }

   public static SetWalkTargetAwayFrom<BlockPos> pos(MemoryModuleType<BlockPos> var0, float var1, int var2, boolean var3) {
      return new SetWalkTargetAwayFrom<>(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Vec3::atBottomCenterOf);
   }

   public static SetWalkTargetAwayFrom<? extends Entity> entity(MemoryModuleType<? extends Entity> var0, float var1, int var2, boolean var3) {
      return new SetWalkTargetAwayFrom<>(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Entity::position);
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      return this.alreadyWalkingAwayFromPosWithSameSpeed(â˜ƒ) ? false : â˜ƒ.position().closerThan(this.getPosToAvoid(â˜ƒ), (double)this.desiredDistance);
   }

   private Vec3 getPosToAvoid(PathfinderMob var1) {
      return (Vec3)this.toPosition.apply(â˜ƒ.getBrain().getMemory(this.walkAwayFromMemory).get());
   }

   private boolean alreadyWalkingAwayFromPosWithSameSpeed(PathfinderMob var1) {
      if (!â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.WALK_TARGET)) {
         return false;
      } else {
         WalkTarget â˜ƒ = (WalkTarget)â˜ƒ.getBrain().getMemory(MemoryModuleType.WALK_TARGET).get();
         if (â˜ƒ.getSpeedModifier() != this.speedModifier) {
            return false;
         } else {
            Vec3 â˜ƒ = â˜ƒ.getTarget().currentPosition().subtract(â˜ƒ.position());
            Vec3 â˜ƒx = this.getPosToAvoid(â˜ƒ).subtract(â˜ƒ.position());
            return â˜ƒ.dot(â˜ƒx) < 0.0;
         }
      }
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      moveAwayFrom(â˜ƒ, this.getPosToAvoid(â˜ƒ), this.speedModifier);
   }

   private static void moveAwayFrom(PathfinderMob var0, Vec3 var1, float var2) {
      for(int â˜ƒ = 0; â˜ƒ < 10; ++â˜ƒ) {
         Vec3 â˜ƒx = LandRandomPos.getPosAway(â˜ƒ, 16, 7, â˜ƒ);
         if (â˜ƒx != null) {
            â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒx, â˜ƒ, 0));
            return;
         }
      }
   }
}
