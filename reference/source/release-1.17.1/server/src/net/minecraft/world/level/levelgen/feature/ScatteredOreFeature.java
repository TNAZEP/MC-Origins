package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class ScatteredOreFeature extends Feature<OreConfiguration> {
   private static final int MAX_DIST_FROM_ORIGIN = 7;

   ScatteredOreFeature(Codec<OreConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<OreConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      Random â˜ƒx = â˜ƒ.random();
      OreConfiguration â˜ƒxx = â˜ƒ.config();
      BlockPos â˜ƒxxx = â˜ƒ.origin();
      int â˜ƒxxxx = â˜ƒx.nextInt(â˜ƒxx.size + 1);
      BlockPos.MutableBlockPos â˜ƒxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxx) {
         this.offsetTargetPos(â˜ƒxxxxx, â˜ƒx, â˜ƒxxx, Math.min(â˜ƒxxxxxx, 7));
         BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);

         for(OreConfiguration.TargetBlockState â˜ƒxxxxxxxx : â˜ƒxx.targetStates) {
            if (OreFeature.canPlaceOre(â˜ƒxxxxxxx, â˜ƒ::getBlockState, â˜ƒx, â˜ƒxx, â˜ƒxxxxxxxx, â˜ƒxxxxx)) {
               â˜ƒ.setBlock(â˜ƒxxxxx, â˜ƒxxxxxxxx.state, 2);
               break;
            }
         }
      }

      return true;
   }

   private void offsetTargetPos(BlockPos.MutableBlockPos var1, Random var2, BlockPos var3, int var4) {
      int â˜ƒ = this.getRandomPlacementInOneAxisRelativeToOrigin(â˜ƒ, â˜ƒ);
      int â˜ƒx = this.getRandomPlacementInOneAxisRelativeToOrigin(â˜ƒ, â˜ƒ);
      int â˜ƒxx = this.getRandomPlacementInOneAxisRelativeToOrigin(â˜ƒ, â˜ƒ);
      â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   private int getRandomPlacementInOneAxisRelativeToOrigin(Random var1, int var2) {
      return Math.round((â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * (float)â˜ƒ);
   }
}
