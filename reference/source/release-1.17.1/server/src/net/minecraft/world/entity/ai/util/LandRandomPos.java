package net.minecraft.world.entity.ai.util;

import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class LandRandomPos {
   @Nullable
   public static Vec3 getPos(PathfinderMob var0, int var1, int var2) {
      return getPos(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ::getWalkTargetValue);
   }

   @Nullable
   public static Vec3 getPos(PathfinderMob var0, int var1, int var2, ToDoubleFunction<BlockPos> var3) {
      boolean â˜ƒ = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return RandomPos.generateRandomPos(() -> {
         BlockPos â˜ƒ = RandomPos.generateRandomDirection(â˜ƒ.getRandom(), â˜ƒ, â˜ƒ);
         BlockPos â˜ƒx = generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return â˜ƒx == null ? null : movePosUpOutOfSolid(â˜ƒ, â˜ƒx);
      }, â˜ƒ);
   }

   @Nullable
   public static Vec3 getPosTowards(PathfinderMob var0, int var1, int var2, Vec3 var3) {
      Vec3 â˜ƒ = â˜ƒ.subtract(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      boolean â˜ƒx = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return getPosInDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
   }

   @Nullable
   public static Vec3 getPosAway(PathfinderMob var0, int var1, int var2, Vec3 var3) {
      Vec3 â˜ƒ = â˜ƒ.position().subtract(â˜ƒ);
      boolean â˜ƒx = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return getPosInDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
   }

   @Nullable
   private static Vec3 getPosInDirection(PathfinderMob var0, int var1, int var2, Vec3 var3, boolean var4) {
      return RandomPos.generateRandomPos(â˜ƒ, () -> {
         BlockPos â˜ƒ = RandomPos.generateRandomDirectionWithinRadians(â˜ƒ.getRandom(), â˜ƒ, â˜ƒ, 0, â˜ƒ.x, â˜ƒ.z, (float) (Math.PI / 2));
         if (â˜ƒ == null) {
            return null;
         } else {
            BlockPos â˜ƒ = generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            return â˜ƒ == null ? null : movePosUpOutOfSolid(â˜ƒ, â˜ƒ);
         }
      });
   }

   @Nullable
   public static BlockPos movePosUpOutOfSolid(PathfinderMob var0, BlockPos var1) {
      â˜ƒ = RandomPos.moveUpOutOfSolid(â˜ƒ, â˜ƒ.level.getMaxBuildHeight(), var1x -> GoalUtils.isSolid(â˜ƒ, var1x));
      return !GoalUtils.isWater(â˜ƒ, â˜ƒ) && !GoalUtils.hasMalus(â˜ƒ, â˜ƒ) ? â˜ƒ : null;
   }

   @Nullable
   public static BlockPos generateRandomPosTowardDirection(PathfinderMob var0, int var1, boolean var2, BlockPos var3) {
      BlockPos â˜ƒ = RandomPos.generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ.getRandom(), â˜ƒ);
      return !GoalUtils.isOutsideLimits(â˜ƒ, â˜ƒ) && !GoalUtils.isRestricted(â˜ƒ, â˜ƒ, â˜ƒ) && !GoalUtils.isNotStable(â˜ƒ.getNavigation(), â˜ƒ) ? â˜ƒ : null;
   }
}
