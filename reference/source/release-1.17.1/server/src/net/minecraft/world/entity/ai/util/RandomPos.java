package net.minecraft.world.entity.ai.util;

import com.google.common.annotations.VisibleForTesting;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class RandomPos {
   private static final int RANDOM_POS_ATTEMPTS = 10;

   public static BlockPos generateRandomDirection(Random var0, int var1, int var2) {
      int â˜ƒ = â˜ƒ.nextInt(2 * â˜ƒ + 1) - â˜ƒ;
      int â˜ƒx = â˜ƒ.nextInt(2 * â˜ƒ + 1) - â˜ƒ;
      int â˜ƒxx = â˜ƒ.nextInt(2 * â˜ƒ + 1) - â˜ƒ;
      return new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   @Nullable
   public static BlockPos generateRandomDirectionWithinRadians(Random var0, int var1, int var2, int var3, double var4, double var6, double var8) {
      double â˜ƒ = Mth.atan2(â˜ƒ, â˜ƒ) - (float) (Math.PI / 2);
      double â˜ƒx = â˜ƒ + (double)(2.0F * â˜ƒ.nextFloat() - 1.0F) * â˜ƒ;
      double â˜ƒxx = Math.sqrt(â˜ƒ.nextDouble()) * (double)Mth.SQRT_OF_TWO * (double)â˜ƒ;
      double â˜ƒxxx = -â˜ƒxx * Math.sin(â˜ƒx);
      double â˜ƒxxxx = â˜ƒxx * Math.cos(â˜ƒx);
      if (!(Math.abs(â˜ƒxxx) > (double)â˜ƒ) && !(Math.abs(â˜ƒxxxx) > (double)â˜ƒ)) {
         int â˜ƒxxxxx = â˜ƒ.nextInt(2 * â˜ƒ + 1) - â˜ƒ + â˜ƒ;
         return new BlockPos(â˜ƒxxx, (double)â˜ƒxxxxx, â˜ƒxxxx);
      } else {
         return null;
      }
   }

   @VisibleForTesting
   public static BlockPos moveUpOutOfSolid(BlockPos var0, int var1, Predicate<BlockPos> var2) {
      if (!â˜ƒ.test(â˜ƒ)) {
         return â˜ƒ;
      } else {
         BlockPos â˜ƒ = â˜ƒ.above();

         while(â˜ƒ.getY() < â˜ƒ && â˜ƒ.test(â˜ƒ)) {
            â˜ƒ = â˜ƒ.above();
         }

         return â˜ƒ;
      }
   }

   @VisibleForTesting
   public static BlockPos moveUpToAboveSolid(BlockPos var0, int var1, int var2, Predicate<BlockPos> var3) {
      if (â˜ƒ < 0) {
         throw new IllegalArgumentException("aboveSolidAmount was " + â˜ƒ + ", expected >= 0");
      } else if (!â˜ƒ.test(â˜ƒ)) {
         return â˜ƒ;
      } else {
         BlockPos â˜ƒ = â˜ƒ.above();

         while(â˜ƒ.getY() < â˜ƒ && â˜ƒ.test(â˜ƒ)) {
            â˜ƒ = â˜ƒ.above();
         }

         BlockPos â˜ƒ;
         BlockPos â˜ƒ;
         for(â˜ƒ = â˜ƒ; â˜ƒ.getY() < â˜ƒ && â˜ƒ.getY() - â˜ƒ.getY() < â˜ƒ; â˜ƒ = â˜ƒ) {
            â˜ƒ = â˜ƒ.above();
            if (â˜ƒ.test(â˜ƒ)) {
               break;
            }
         }

         return â˜ƒ;
      }
   }

   @Nullable
   public static Vec3 generateRandomPos(PathfinderMob var0, Supplier<BlockPos> var1) {
      return generateRandomPos(â˜ƒ, â˜ƒ::getWalkTargetValue);
   }

   @Nullable
   public static Vec3 generateRandomPos(Supplier<BlockPos> var0, ToDoubleFunction<BlockPos> var1) {
      double â˜ƒ = Double.NEGATIVE_INFINITY;
      BlockPos â˜ƒx = null;

      for(int â˜ƒxx = 0; â˜ƒxx < 10; ++â˜ƒxx) {
         BlockPos â˜ƒxxx = (BlockPos)â˜ƒ.get();
         if (â˜ƒxxx != null) {
            double â˜ƒxxxx = â˜ƒ.applyAsDouble(â˜ƒxxx);
            if (â˜ƒxxxx > â˜ƒ) {
               â˜ƒ = â˜ƒxxxx;
               â˜ƒx = â˜ƒxxx;
            }
         }
      }

      return â˜ƒx != null ? Vec3.atBottomCenterOf(â˜ƒx) : null;
   }

   public static BlockPos generateRandomPosTowardDirection(PathfinderMob var0, int var1, Random var2, BlockPos var3) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ();
      if (â˜ƒ.hasRestriction() && â˜ƒ > 1) {
         BlockPos â˜ƒxx = â˜ƒ.getRestrictCenter();
         if (â˜ƒ.getX() > (double)â˜ƒxx.getX()) {
            â˜ƒ -= â˜ƒ.nextInt(â˜ƒ / 2);
         } else {
            â˜ƒ += â˜ƒ.nextInt(â˜ƒ / 2);
         }

         if (â˜ƒ.getZ() > (double)â˜ƒxx.getZ()) {
            â˜ƒx -= â˜ƒ.nextInt(â˜ƒ / 2);
         } else {
            â˜ƒx += â˜ƒ.nextInt(â˜ƒ / 2);
         }
      }

      return new BlockPos((double)â˜ƒ + â˜ƒ.getX(), (double)â˜ƒ.getY() + â˜ƒ.getY(), (double)â˜ƒx + â˜ƒ.getZ());
   }
}
