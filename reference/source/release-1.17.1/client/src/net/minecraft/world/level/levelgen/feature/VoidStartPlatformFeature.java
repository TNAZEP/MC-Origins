package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VoidStartPlatformFeature extends Feature<NoneFeatureConfiguration> {
   private static final BlockPos PLATFORM_OFFSET = new BlockPos(8, 3, 8);
   private static final ChunkPos PLATFORM_ORIGIN_CHUNK = new ChunkPos(PLATFORM_OFFSET);
   private static final int PLATFORM_RADIUS = 16;
   private static final int PLATFORM_RADIUS_CHUNKS = 1;

   public VoidStartPlatformFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   private static int checkerboardDistance(int var0, int var1, int var2, int var3) {
      return Math.max(Math.abs(â˜ƒ - â˜ƒ), Math.abs(â˜ƒ - â˜ƒ));
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      ChunkPos â˜ƒx = new ChunkPos(â˜ƒ.origin());
      if (checkerboardDistance(â˜ƒx.x, â˜ƒx.z, PLATFORM_ORIGIN_CHUNK.x, PLATFORM_ORIGIN_CHUNK.z) > 1) {
         return true;
      } else {
         BlockPos â˜ƒ = PLATFORM_OFFSET.atY(â˜ƒ.origin().getY() + PLATFORM_OFFSET.getY());
         BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxx = â˜ƒx.getMinBlockZ(); â˜ƒxx <= â˜ƒx.getMaxBlockZ(); ++â˜ƒxx) {
            for(int â˜ƒxxx = â˜ƒx.getMinBlockX(); â˜ƒxxx <= â˜ƒx.getMaxBlockX(); ++â˜ƒxxx) {
               if (checkerboardDistance(â˜ƒ.getX(), â˜ƒ.getZ(), â˜ƒxxx, â˜ƒxx) <= 16) {
                  â˜ƒx.set(â˜ƒxxx, â˜ƒ.getY(), â˜ƒxx);
                  if (â˜ƒx.equals(â˜ƒ)) {
                     â˜ƒ.setBlock(â˜ƒx, Blocks.COBBLESTONE.defaultBlockState(), 2);
                  } else {
                     â˜ƒ.setBlock(â˜ƒx, Blocks.STONE.defaultBlockState(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
