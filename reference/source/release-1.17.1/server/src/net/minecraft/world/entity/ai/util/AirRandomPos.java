package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class AirRandomPos {
   @Nullable
   public static Vec3 getPosTowards(PathfinderMob var0, int var1, int var2, int var3, Vec3 var4, double var5) {
      Vec3 â˜ƒ = â˜ƒ.subtract(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      boolean â˜ƒx = GoalUtils.mobRestricted(â˜ƒ, â˜ƒ);
      return RandomPos.generateRandomPos(â˜ƒ, () -> {
         BlockPos â˜ƒ = AirAndWaterRandomPos.generateRandomPos(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.x, â˜ƒ.z, â˜ƒ, â˜ƒ);
         return â˜ƒ != null && !GoalUtils.isWater(â˜ƒ, â˜ƒ) ? â˜ƒ : null;
      });
   }
}
