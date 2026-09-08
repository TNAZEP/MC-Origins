package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;

public class NetherVines {
   private static final double BONEMEAL_GROW_PROBABILITY_DECREASE_RATE = 0.826;
   public static final double GROW_PER_TICK_PROBABILITY = 0.1;

   public static boolean isValidGrowthState(BlockState var0) {
      return â˜ƒ.isAir();
   }

   public static int getBlocksToGrowWhenBonemealed(Random var0) {
      double â˜ƒ = 1.0;

      int â˜ƒ;
      for(â˜ƒ = 0; â˜ƒ.nextDouble() < â˜ƒ; ++â˜ƒ) {
         â˜ƒ *= 0.826;
      }

      return â˜ƒ;
   }
}
