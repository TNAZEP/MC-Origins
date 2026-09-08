package net.minecraft.world.level.levelgen.placement.nether;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

public class CountMultiLayerDecorator extends FeatureDecorator<CountConfiguration> {
   public CountMultiLayerDecorator(Codec<CountConfiguration> var1) {
      super(â˜ƒ);
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, CountConfiguration var3, BlockPos var4) {
      List<BlockPos> â˜ƒ = Lists.<BlockPos>newArrayList();
      int â˜ƒx = 0;

      boolean â˜ƒ;
      do {
         â˜ƒ = false;

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.count().sample(â˜ƒ); ++â˜ƒxx) {
            int â˜ƒxxx = â˜ƒ.nextInt(16) + â˜ƒ.getX();
            int â˜ƒxxxx = â˜ƒ.nextInt(16) + â˜ƒ.getZ();
            int â˜ƒxxxxx = â˜ƒ.getHeight(Heightmap.Types.MOTION_BLOCKING, â˜ƒxxx, â˜ƒxxxx);
            int â˜ƒxxxxxx = findOnGroundYPosition(â˜ƒ, â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxx, â˜ƒx);
            if (â˜ƒxxxxxx != Integer.MAX_VALUE) {
               â˜ƒ.add(new BlockPos(â˜ƒxxx, â˜ƒxxxxxx, â˜ƒxxxx));
               â˜ƒ = true;
            }
         }

         ++â˜ƒx;
      } while(â˜ƒ);

      return â˜ƒ.stream();
   }

   private static int findOnGroundYPosition(DecorationContext var0, int var1, int var2, int var3, int var4) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒx = 0;
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);

      for(int â˜ƒxxx = â˜ƒ; â˜ƒxxx >= â˜ƒ.getMinBuildHeight() + 1; --â˜ƒxxx) {
         â˜ƒ.setY(â˜ƒxxx - 1);
         BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒ);
         if (!isEmpty(â˜ƒxxxx) && isEmpty(â˜ƒxx) && !â˜ƒxxxx.is(Blocks.BEDROCK)) {
            if (â˜ƒx == â˜ƒ) {
               return â˜ƒ.getY() + 1;
            }

            ++â˜ƒx;
         }

         â˜ƒxx = â˜ƒxxxx;
      }

      return Integer.MAX_VALUE;
   }

   private static boolean isEmpty(BlockState var0) {
      return â˜ƒ.isAir() || â˜ƒ.is(Blocks.WATER) || â˜ƒ.is(Blocks.LAVA);
   }
}
