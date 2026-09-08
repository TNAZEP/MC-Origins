package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

public class IcePatchFeature extends BaseDiskFeature {
   public IcePatchFeature(Codec<DiskConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<DiskConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      ChunkGenerator â˜ƒx = â˜ƒ.chunkGenerator();
      Random â˜ƒxx = â˜ƒ.random();
      DiskConfiguration â˜ƒxxx = â˜ƒ.config();
      BlockPos â˜ƒxxxx = â˜ƒ.origin();

      while(â˜ƒ.isEmptyBlock(â˜ƒxxxx) && â˜ƒxxxx.getY() > â˜ƒ.getMinBuildHeight() + 2) {
         â˜ƒxxxx = â˜ƒxxxx.below();
      }

      return !â˜ƒ.getBlockState(â˜ƒxxxx).is(Blocks.SNOW_BLOCK) ? false : super.place(new FeaturePlaceContext<>(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxx, â˜ƒxxx));
   }
}
