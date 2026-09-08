package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class MoveToSkySeeingSpot extends Behavior<LivingEntity> {
   private final float speedModifier;

   public MoveToSkySeeingSpot(float var1) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
      this.speedModifier = â˜ƒ;
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Optional<Vec3> â˜ƒ = Optional.ofNullable(this.getOutdoorPosition(â˜ƒ, â˜ƒ));
      if (â˜ƒ.isPresent()) {
         â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, â˜ƒ.map(var1x -> new WalkTarget(var1x, this.speedModifier, 0)));
      }
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      return !â˜ƒ.canSeeSky(â˜ƒ.blockPosition());
   }

   @Nullable
   private Vec3 getOutdoorPosition(ServerLevel var1, LivingEntity var2) {
      Random â˜ƒ = â˜ƒ.getRandom();
      BlockPos â˜ƒx = â˜ƒ.blockPosition();

      for(int â˜ƒxx = 0; â˜ƒxx < 10; ++â˜ƒxx) {
         BlockPos â˜ƒxxx = â˜ƒx.offset(â˜ƒ.nextInt(20) - 10, â˜ƒ.nextInt(6) - 3, â˜ƒ.nextInt(20) - 10);
         if (hasNoBlocksAbove(â˜ƒ, â˜ƒ, â˜ƒxxx)) {
            return Vec3.atBottomCenterOf(â˜ƒxxx);
         }
      }

      return null;
   }

   public static boolean hasNoBlocksAbove(ServerLevel var0, LivingEntity var1, BlockPos var2) {
      return â˜ƒ.canSeeSky(â˜ƒ) && (double)â˜ƒ.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, â˜ƒ).getY() <= â˜ƒ.getY();
   }
}
