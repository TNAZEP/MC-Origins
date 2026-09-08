package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class StrollAroundPoi extends Behavior<PathfinderMob> {
   private static final int MIN_TIME_BETWEEN_STROLLS = 180;
   private static final int STROLL_MAX_XZ_DIST = 8;
   private static final int STROLL_MAX_Y_DIST = 6;
   private final MemoryModuleType<GlobalPos> memoryType;
   private long nextOkStartTime;
   private final int maxDistanceFromPoi;
   private final float speedModifier;

   public StrollAroundPoi(MemoryModuleType<GlobalPos> var1, float var2, int var3) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, â˜ƒ, MemoryStatus.VALUE_PRESENT));
      this.memoryType = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.maxDistanceFromPoi = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      Optional<GlobalPos> â˜ƒ = â˜ƒ.getBrain().getMemory(this.memoryType);
      return â˜ƒ.isPresent()
         && â˜ƒ.dimension() == ((GlobalPos)â˜ƒ.get()).dimension()
         && ((GlobalPos)â˜ƒ.get()).pos().closerThan(â˜ƒ.position(), (double)this.maxDistanceFromPoi);
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      if (â˜ƒ > this.nextOkStartTime) {
         Optional<Vec3> â˜ƒ = Optional.ofNullable(LandRandomPos.getPos(â˜ƒ, 8, 6));
         â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, â˜ƒ.map(var1x -> new WalkTarget(var1x, this.speedModifier, 1)));
         this.nextOkStartTime = â˜ƒ + 180L;
      }
   }
}
