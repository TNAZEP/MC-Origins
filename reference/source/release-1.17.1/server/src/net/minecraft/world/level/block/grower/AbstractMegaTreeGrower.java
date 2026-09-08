package net.minecraft.world.level.block.grower;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public abstract class AbstractMegaTreeGrower extends AbstractTreeGrower {
   @Override
   public boolean growTree(ServerLevel var1, ChunkGenerator var2, BlockPos var3, BlockState var4, Random var5) {
      for(int â˜ƒ = 0; â˜ƒ >= -1; --â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx >= -1; --â˜ƒx) {
            if (isTwoByTwoSapling(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx)) {
               return this.placeMega(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            }
         }
      }

      return super.growTree(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   protected abstract ConfiguredFeature<TreeConfiguration, ?> getConfiguredMegaFeature(Random var1);

   public boolean placeMega(ServerLevel var1, ChunkGenerator var2, BlockPos var3, BlockState var4, Random var5, int var6, int var7) {
      ConfiguredFeature<TreeConfiguration, ?> â˜ƒ = this.getConfiguredMegaFeature(â˜ƒ);
      if (â˜ƒ == null) {
         return false;
      } else {
         BlockState â˜ƒ = Blocks.AIR.defaultBlockState();
         â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ, 0, â˜ƒ), â˜ƒ, 4);
         â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ + 1, 0, â˜ƒ), â˜ƒ, 4);
         â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ, 0, â˜ƒ + 1), â˜ƒ, 4);
         â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ + 1, 0, â˜ƒ + 1), â˜ƒ, 4);
         if (â˜ƒ.place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.offset(â˜ƒ, 0, â˜ƒ))) {
            return true;
         } else {
            â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ, 0, â˜ƒ), â˜ƒ, 4);
            â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ + 1, 0, â˜ƒ), â˜ƒ, 4);
            â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ, 0, â˜ƒ + 1), â˜ƒ, 4);
            â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ + 1, 0, â˜ƒ + 1), â˜ƒ, 4);
            return false;
         }
      }
   }

   public static boolean isTwoByTwoSapling(BlockState var0, BlockGetter var1, BlockPos var2, int var3, int var4) {
      Block â˜ƒ = â˜ƒ.getBlock();
      return â˜ƒ.getBlockState(â˜ƒ.offset(â˜ƒ, 0, â˜ƒ)).is(â˜ƒ)
         && â˜ƒ.getBlockState(â˜ƒ.offset(â˜ƒ + 1, 0, â˜ƒ)).is(â˜ƒ)
         && â˜ƒ.getBlockState(â˜ƒ.offset(â˜ƒ, 0, â˜ƒ + 1)).is(â˜ƒ)
         && â˜ƒ.getBlockState(â˜ƒ.offset(â˜ƒ + 1, 0, â˜ƒ + 1)).is(â˜ƒ);
   }
}
