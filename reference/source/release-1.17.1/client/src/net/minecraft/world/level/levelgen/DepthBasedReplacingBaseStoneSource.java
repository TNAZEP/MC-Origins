package net.minecraft.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class DepthBasedReplacingBaseStoneSource implements BaseStoneSource {
   private static final int ALWAYS_REPLACE_BELOW_Y = -8;
   private static final int NEVER_REPLACE_ABOVE_Y = 0;
   private final WorldgenRandom random;
   private final long seed;
   private final BlockState normalBlock;
   private final BlockState replacementBlock;
   private final NoiseGeneratorSettings settings;

   public DepthBasedReplacingBaseStoneSource(long var1, BlockState var3, BlockState var4, NoiseGeneratorSettings var5) {
      this.random = new WorldgenRandom(â˜ƒ);
      this.seed = â˜ƒ;
      this.normalBlock = â˜ƒ;
      this.replacementBlock = â˜ƒ;
      this.settings = â˜ƒ;
   }

   @Override
   public BlockState getBaseBlock(int var1, int var2, int var3) {
      if (!this.settings.isDeepslateEnabled()) {
         return this.normalBlock;
      } else if (â˜ƒ < -8) {
         return this.replacementBlock;
      } else if (â˜ƒ > 0) {
         return this.normalBlock;
      } else {
         double â˜ƒ = Mth.map((double)â˜ƒ, -8.0, 0.0, 1.0, 0.0);
         this.random.setBaseStoneSeed(this.seed, â˜ƒ, â˜ƒ, â˜ƒ);
         return (double)this.random.nextFloat() < â˜ƒ ? this.replacementBlock : this.normalBlock;
      }
   }
}
