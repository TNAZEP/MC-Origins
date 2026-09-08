package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SnowAndFreezeFeature extends Feature<NoneFeatureConfiguration> {
   public SnowAndFreezeFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16; ++â˜ƒxxxxx) {
            int â˜ƒxxxxxx = â˜ƒx.getX() + â˜ƒxxxx;
            int â˜ƒxxxxxxx = â˜ƒx.getZ() + â˜ƒxxxxx;
            int â˜ƒxxxxxxxx = â˜ƒ.getHeight(Heightmap.Types.MOTION_BLOCKING, â˜ƒxxxxxx, â˜ƒxxxxxxx);
            â˜ƒxx.set(â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx);
            â˜ƒxxx.set(â˜ƒxx).move(Direction.DOWN, 1);
            Biome â˜ƒxxxxxxxxx = â˜ƒ.getBiome(â˜ƒxx);
            if (â˜ƒxxxxxxxxx.shouldFreeze(â˜ƒ, â˜ƒxxx, false)) {
               â˜ƒ.setBlock(â˜ƒxxx, Blocks.ICE.defaultBlockState(), 2);
            }

            if (â˜ƒxxxxxxxxx.shouldSnow(â˜ƒ, â˜ƒxx)) {
               â˜ƒ.setBlock(â˜ƒxx, Blocks.SNOW.defaultBlockState(), 2);
               BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
               if (â˜ƒxxxxxx.hasProperty(SnowyDirtBlock.SNOWY)) {
                  â˜ƒ.setBlock(â˜ƒxxx, â˜ƒxxxxxx.setValue(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
               }
            }
         }
      }

      return true;
   }
}
