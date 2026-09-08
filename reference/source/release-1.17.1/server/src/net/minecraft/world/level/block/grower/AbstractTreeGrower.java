package net.minecraft.world.level.block.grower;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public abstract class AbstractTreeGrower {
   @Nullable
   protected abstract ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random var1, boolean var2);

   public boolean growTree(ServerLevel var1, ChunkGenerator var2, BlockPos var3, BlockState var4, Random var5) {
      ConfiguredFeature<TreeConfiguration, ?> â˜ƒ = this.getConfiguredFeature(â˜ƒ, this.hasFlowers(â˜ƒ, â˜ƒ));
      if (â˜ƒ == null) {
         return false;
      } else {
         â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 4);
         if (â˜ƒ.place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            return true;
         } else {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 4);
            return false;
         }
      }
   }

   private boolean hasFlowers(LevelAccessor var1, BlockPos var2) {
      for(BlockPos â˜ƒ : BlockPos.MutableBlockPos.betweenClosed(â˜ƒ.below().north(2).west(2), â˜ƒ.above().south(2).east(2))) {
         if (â˜ƒ.getBlockState(â˜ƒ).is(BlockTags.FLOWERS)) {
            return true;
         }
      }

      return false;
   }
}
