package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VinesFeature extends Feature<NoneFeatureConfiguration> {
   public VinesFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      â˜ƒ.config();
      if (!â˜ƒ.isEmptyBlock(â˜ƒx)) {
         return false;
      } else {
         for(Direction â˜ƒ : Direction.values()) {
            if (â˜ƒ != Direction.DOWN && VineBlock.isAcceptableNeighbour(â˜ƒ, â˜ƒx.relative(â˜ƒ), â˜ƒ)) {
               â˜ƒ.setBlock(â˜ƒx, Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(â˜ƒ), Boolean.valueOf(true)), 2);
               return true;
            }
         }

         return false;
      }
   }
}
