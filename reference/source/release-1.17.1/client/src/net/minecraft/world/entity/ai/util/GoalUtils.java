package net.minecraft.world.entity.ai.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class GoalUtils {
   public static boolean hasGroundPathNavigation(Mob var0) {
      return â˜ƒ.getNavigation() instanceof GroundPathNavigation;
   }

   public static boolean mobRestricted(PathfinderMob var0, int var1) {
      return â˜ƒ.hasRestriction() && â˜ƒ.getRestrictCenter().closerThan(â˜ƒ.position(), (double)(â˜ƒ.getRestrictRadius() + (float)â˜ƒ) + 1.0);
   }

   public static boolean isOutsideLimits(BlockPos var0, PathfinderMob var1) {
      return â˜ƒ.getY() < â˜ƒ.level.getMinBuildHeight() || â˜ƒ.getY() > â˜ƒ.level.getMaxBuildHeight();
   }

   public static boolean isRestricted(boolean var0, PathfinderMob var1, BlockPos var2) {
      return â˜ƒ && !â˜ƒ.isWithinRestriction(â˜ƒ);
   }

   public static boolean isNotStable(PathNavigation var0, BlockPos var1) {
      return !â˜ƒ.isStableDestination(â˜ƒ);
   }

   public static boolean isWater(PathfinderMob var0, BlockPos var1) {
      return â˜ƒ.level.getFluidState(â˜ƒ).is(FluidTags.WATER);
   }

   public static boolean hasMalus(PathfinderMob var0, BlockPos var1) {
      return â˜ƒ.getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic(â˜ƒ.level, â˜ƒ.mutable())) != 0.0F;
   }

   public static boolean isSolid(PathfinderMob var0, BlockPos var1) {
      return â˜ƒ.level.getBlockState(â˜ƒ).getMaterial().isSolid();
   }
}
