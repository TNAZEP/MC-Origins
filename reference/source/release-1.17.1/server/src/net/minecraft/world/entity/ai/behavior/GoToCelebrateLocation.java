package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class GoToCelebrateLocation<E extends Mob> extends Behavior<E> {
   private final int closeEnoughDist;
   private final float speedModifier;

   public GoToCelebrateLocation(int var1, float var2) {
      super(
         ImmutableMap.of(
            MemoryModuleType.CELEBRATE_LOCATION,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED
         )
      );
      this.closeEnoughDist = â˜ƒ;
      this.speedModifier = â˜ƒ;
   }

   protected void start(ServerLevel var1, Mob var2, long var3) {
      BlockPos â˜ƒ = getCelebrateLocation(â˜ƒ);
      boolean â˜ƒx = â˜ƒ.closerThan(â˜ƒ.blockPosition(), (double)this.closeEnoughDist);
      if (!â˜ƒx) {
         BehaviorUtils.setWalkAndLookTargetMemories(â˜ƒ, getNearbyPos(â˜ƒ, â˜ƒ), this.speedModifier, this.closeEnoughDist);
      }
   }

   private static BlockPos getNearbyPos(Mob var0, BlockPos var1) {
      Random â˜ƒ = â˜ƒ.level.random;
      return â˜ƒ.offset(getRandomOffset(â˜ƒ), 0, getRandomOffset(â˜ƒ));
   }

   private static int getRandomOffset(Random var0) {
      return â˜ƒ.nextInt(3) - 1;
   }

   private static BlockPos getCelebrateLocation(Mob var0) {
      return (BlockPos)â˜ƒ.getBrain().getMemory(MemoryModuleType.CELEBRATE_LOCATION).get();
   }
}
