package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class HoverRandomPos {
   @Nullable
   public static Vec3 getPos(PathfinderMob var0, int var1, int var2, double var3, double var5, float var7, int var8, int var9) {
      boolean â˜ƒ = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return RandomPos.generateRandomPos(
         â˜ƒ,
         () -> {
            BlockPos â˜ƒ = RandomPos.generateRandomDirectionWithinRadians(â˜ƒ.getRandom(), â˜ƒ, â˜ƒ, 0, â˜ƒ, â˜ƒ, (double)â˜ƒ);
            if (â˜ƒ == null) {
               return null;
            } else {
               BlockPos â˜ƒ = LandRandomPos.generateRandomPosTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               if (â˜ƒ == null) {
                  return null;
               } else {
                  â˜ƒ = RandomPos.moveUpToAboveSolid(
                     â˜ƒ, â˜ƒ.getRandom().nextInt(â˜ƒ - â˜ƒ + 1) + â˜ƒ, â˜ƒ.level.getMaxBuildHeight(), var1x -> GoalUtils.isSolid(â˜ƒ, var1x)
                  );
                  return !GoalUtils.isWater(â˜ƒ, â˜ƒ) && !GoalUtils.hasMalus(â˜ƒ, â˜ƒ) ? â˜ƒ : null;
               }
            }
         }
      );
   }
}
