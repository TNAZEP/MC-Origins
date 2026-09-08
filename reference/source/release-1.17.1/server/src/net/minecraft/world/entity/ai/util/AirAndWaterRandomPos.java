package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class AirAndWaterRandomPos {
   @Nullable
   public static Vec3 getPos(PathfinderMob var0, int var1, int var2, int var3, double var4, double var6, double var8) {
      boolean â˜ƒ = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return RandomPos.generateRandomPos(â˜ƒ, () -> generateRandomPos(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   @Nullable
   public static BlockPos generateRandomPos(PathfinderMob var0, int var1, int var2, int var3, double var4, double var6, double var8, boolean var10) {
      BlockPos â˜ƒ = RandomPos.generateRandomDirectionWithinRadians(â˜ƒ.getRandom(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ == null) {
         return null;
      } else {
         BlockPos â˜ƒ = RandomPos.generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ.getRandom(), â˜ƒ);
         if (!GoalUtils.isOutsideLimits(â˜ƒ, â˜ƒ) && !GoalUtils.isRestricted(â˜ƒ, â˜ƒ, â˜ƒ)) {
            â˜ƒ = RandomPos.moveUpOutOfSolid(â˜ƒ, â˜ƒ.level.getMaxBuildHeight(), var1x -> GoalUtils.isSolid(â˜ƒ, var1x));
            return GoalUtils.hasMalus(â˜ƒ, â˜ƒ) ? null : â˜ƒ;
         } else {
            return null;
         }
      }
   }
}
