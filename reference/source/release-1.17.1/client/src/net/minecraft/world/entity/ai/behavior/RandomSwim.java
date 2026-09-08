package net.minecraft.world.entity.ai.behavior;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class RandomSwim extends RandomStroll {
   public static final int[][] XY_DISTANCE_TIERS = new int[][]{{1, 1}, {3, 3}, {5, 5}, {6, 5}, {7, 7}, {10, 7}};

   public RandomSwim(float var1) {
      super(â˜ƒ);
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      return â˜ƒ.isInWaterOrBubble();
   }

   @Nullable
   @Override
   protected Vec3 getTargetPos(PathfinderMob var1) {
      Vec3 â˜ƒ = null;
      Vec3 â˜ƒx = null;

      for(int[] â˜ƒxx : XY_DISTANCE_TIERS) {
         if (â˜ƒ == null) {
            â˜ƒx = BehaviorUtils.getRandomSwimmablePos(â˜ƒ, â˜ƒxx[0], â˜ƒxx[1]);
         } else {
            â˜ƒx = â˜ƒ.position().add(â˜ƒ.position().vectorTo(â˜ƒ).normalize().multiply((double)â˜ƒxx[0], (double)â˜ƒxx[1], (double)â˜ƒxx[0]));
         }

         if (â˜ƒx == null || â˜ƒ.level.getFluidState(new BlockPos(â˜ƒx)).isEmpty()) {
            return â˜ƒ;
         }

         â˜ƒ = â˜ƒx;
      }

      return â˜ƒx;
   }
}
