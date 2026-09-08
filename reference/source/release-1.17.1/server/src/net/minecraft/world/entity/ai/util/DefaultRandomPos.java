package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class DefaultRandomPos {
   @Nullable
   public static Vec3 getPos(PathfinderMob var0, int var1, int var2) {
      boolean â˜ƒ = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return RandomPos.generateRandomPos(â˜ƒ, () -> {
         BlockPos â˜ƒ = RandomPos.generateRandomDirection(â˜ƒ.getRandom(), â˜ƒ, â˜ƒ);
         return generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      });
   }

   @Nullable
   public static Vec3 getPosTowards(PathfinderMob var0, int var1, int var2, Vec3 var3, double var4) {
      Vec3 â˜ƒ = â˜ƒ.subtract(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      boolean â˜ƒx = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return RandomPos.generateRandomPos(â˜ƒ, () -> {
         BlockPos â˜ƒ = RandomPos.generateRandomDirectionWithinRadians(â˜ƒ.getRandom(), â˜ƒ, â˜ƒ, 0, â˜ƒ.x, â˜ƒ.z, â˜ƒ);
         return â˜ƒ == null ? null : generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      });
   }

   @Nullable
   public static Vec3 getPosAway(PathfinderMob var0, int var1, int var2, Vec3 var3) {
      Vec3 â˜ƒ = â˜ƒ.position().subtract(â˜ƒ);
      boolean â˜ƒx = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return RandomPos.generateRandomPos(â˜ƒ, () -> {
         BlockPos â˜ƒ = RandomPos.generateRandomDirectionWithinRadians(â˜ƒ.getRandom(), â˜ƒ, â˜ƒ, 0, â˜ƒ.x, â˜ƒ.z, (float) (Math.PI / 2));
         return â˜ƒ == null ? null : generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      });
   }

   @Nullable
   private static BlockPos generateRandomPosTowardDirection(PathfinderMob var0, int var1, boolean var2, BlockPos var3) {
      BlockPos â˜ƒ = RandomPos.generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ.getRandom(), â˜ƒ);
      return !GoalUtils.isOutsideLimits(â˜ƒ, â˜ƒ)
            && !GoalUtils.isRestricted(â˜ƒ, â˜ƒ, â˜ƒ)
            && !GoalUtils.isNotStable(â˜ƒ.getNavigation(), â˜ƒ)
            && !GoalUtils.hasMalus(â˜ƒ, â˜ƒ)
         ? â˜ƒ
         : null;
   }
}
