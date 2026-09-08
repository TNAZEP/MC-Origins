package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class AnimalPanic extends Behavior<PathfinderMob> {
   private static final int PANIC_MIN_DURATION = 100;
   private static final int PANIC_MAX_DURATION = 120;
   private static final int PANIC_DISTANCE_HORIZANTAL = 5;
   private static final int PANIC_DISTANCE_VERTICAL = 4;
   private final float speedMultiplier;

   public AnimalPanic(float var1) {
      super(ImmutableMap.of(MemoryModuleType.HURT_BY, MemoryStatus.VALUE_PRESENT), 100, 120);
      this.speedMultiplier = â˜ƒ;
   }

   protected boolean canStillUse(ServerLevel var1, PathfinderMob var2, long var3) {
      return true;
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
   }

   protected void tick(ServerLevel var1, PathfinderMob var2, long var3) {
      if (â˜ƒ.getNavigation().isDone()) {
         Vec3 â˜ƒ = LandRandomPos.getPos(â˜ƒ, 5, 4);
         if (â˜ƒ != null) {
            â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒ, this.speedMultiplier, 0));
         }
      }
   }
}
