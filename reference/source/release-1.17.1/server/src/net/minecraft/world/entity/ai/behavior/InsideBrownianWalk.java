package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class InsideBrownianWalk extends Behavior<PathfinderMob> {
   private final float speedModifier;

   public InsideBrownianWalk(float var1) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
      this.speedModifier = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      return !â˜ƒ.canSeeSky(â˜ƒ.blockPosition());
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      List<BlockPos> â˜ƒx = (List)BlockPos.betweenClosedStream(â˜ƒ.offset(-1, -1, -1), â˜ƒ.offset(1, 1, 1))
         .map(BlockPos::immutable)
         .collect(Collectors.toList());
      Collections.shuffle(â˜ƒx);
      Optional<BlockPos> â˜ƒxx = â˜ƒx.stream()
         .filter(var1x -> !â˜ƒ.canSeeSky(var1x))
         .filter(var2x -> â˜ƒ.loadedAndEntityCanStandOn(var2x, â˜ƒ))
         .filter(var2x -> â˜ƒ.noCollision(â˜ƒ))
         .findFirst();
      â˜ƒxx.ifPresent(var2x -> â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(var2x, this.speedModifier, 0)));
   }
}
