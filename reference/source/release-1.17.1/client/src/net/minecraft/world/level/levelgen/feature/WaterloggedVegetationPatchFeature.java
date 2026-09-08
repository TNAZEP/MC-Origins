package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class WaterloggedVegetationPatchFeature extends VegetationPatchFeature {
   public WaterloggedVegetationPatchFeature(Codec<VegetationPatchConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   protected Set<BlockPos> placeGroundPatch(
      WorldGenLevel var1, VegetationPatchConfiguration var2, Random var3, BlockPos var4, Predicate<BlockState> var5, int var6, int var7
   ) {
      Set<BlockPos> â˜ƒ = super.placeGroundPatch(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      Set<BlockPos> â˜ƒx = new HashSet();
      BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

      for(BlockPos â˜ƒxxx : â˜ƒ) {
         if (!isExposed(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxx)) {
            â˜ƒx.add(â˜ƒxxx);
         }
      }

      for(BlockPos â˜ƒxxx : â˜ƒx) {
         â˜ƒ.setBlock(â˜ƒxxx, Blocks.WATER.defaultBlockState(), 2);
      }

      return â˜ƒx;
   }

   private static boolean isExposed(WorldGenLevel var0, Set<BlockPos> var1, BlockPos var2, BlockPos.MutableBlockPos var3) {
      return isExposedDirection(â˜ƒ, â˜ƒ, â˜ƒ, Direction.NORTH)
         || isExposedDirection(â˜ƒ, â˜ƒ, â˜ƒ, Direction.EAST)
         || isExposedDirection(â˜ƒ, â˜ƒ, â˜ƒ, Direction.SOUTH)
         || isExposedDirection(â˜ƒ, â˜ƒ, â˜ƒ, Direction.WEST)
         || isExposedDirection(â˜ƒ, â˜ƒ, â˜ƒ, Direction.DOWN);
   }

   private static boolean isExposedDirection(WorldGenLevel var0, BlockPos var1, BlockPos.MutableBlockPos var2, Direction var3) {
      â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ);
      return !â˜ƒ.getBlockState(â˜ƒ).isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ.getOpposite());
   }

   @Override
   protected boolean placeVegetation(WorldGenLevel var1, VegetationPatchConfiguration var2, ChunkGenerator var3, Random var4, BlockPos var5) {
      if (super.placeVegetation(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.below())) {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         if (â˜ƒ.hasProperty(BlockStateProperties.WATERLOGGED) && !â˜ƒ.getValue(BlockStateProperties.WATERLOGGED)) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)), 2);
         }

         return true;
      } else {
         return false;
      }
   }
}
