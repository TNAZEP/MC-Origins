package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class StrollToPoi extends Behavior<PathfinderMob> {
   private final MemoryModuleType<GlobalPos> memoryType;
   private final int closeEnoughDist;
   private final int maxDistanceFromPoi;
   private final float speedModifier;
   private long nextOkStartTime;

   public StrollToPoi(MemoryModuleType<GlobalPos> var1, float var2, int var3, int var4) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, â˜ƒ, MemoryStatus.VALUE_PRESENT));
      this.memoryType = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.closeEnoughDist = â˜ƒ;
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
         Brain<?> â˜ƒ = â˜ƒ.getBrain();
         Optional<GlobalPos> â˜ƒx = â˜ƒ.getMemory(this.memoryType);
         â˜ƒx.ifPresent(var2x -> â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(var2x.pos(), this.speedModifier, this.closeEnoughDist)));
         this.nextOkStartTime = â˜ƒ + 80L;
      }
   }
}
