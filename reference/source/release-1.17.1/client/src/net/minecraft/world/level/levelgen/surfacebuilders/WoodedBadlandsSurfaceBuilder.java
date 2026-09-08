package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class WoodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.defaultBlockState();
   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.defaultBlockState();
   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.defaultBlockState();

   public WoodedBadlandsSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public void apply(
      Random var1,
      ChunkAccess var2,
      Biome var3,
      int var4,
      int var5,
      int var6,
      double var7,
      BlockState var9,
      BlockState var10,
      int var11,
      int var12,
      long var13,
      SurfaceBuilderBaseConfiguration var15
   ) {
      int â˜ƒ = â˜ƒ & 15;
      int â˜ƒx = â˜ƒ & 15;
      BlockState â˜ƒxx = WHITE_TERRACOTTA;
      SurfaceBuilderConfiguration â˜ƒxxx = â˜ƒ.getGenerationSettings().getSurfaceBuilderConfig();
      BlockState â˜ƒxxxx = â˜ƒxxx.getUnderMaterial();
      BlockState â˜ƒxxxxx = â˜ƒxxx.getTopMaterial();
      BlockState â˜ƒxxxxxx = â˜ƒxxxx;
      int â˜ƒxxxxxxx = (int)(â˜ƒ / 3.0 + 3.0 + â˜ƒ.nextDouble() * 0.25);
      boolean â˜ƒxxxxxxxx = Math.cos(â˜ƒ / 3.0 * Math.PI) > 0.0;
      int â˜ƒxxxxxxxxx = -1;
      boolean â˜ƒxxxxxxxxxx = false;
      int â˜ƒxxxxxxxxxxx = 0;
      BlockPos.MutableBlockPos â˜ƒxxxxxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxxxxxxxx = â˜ƒ; â˜ƒxxxxxxxxxxxxx >= â˜ƒ; --â˜ƒxxxxxxxxxxxxx) {
         if (â˜ƒxxxxxxxxxxx < 15) {
            â˜ƒxxxxxxxxxxxx.set(â˜ƒ, â˜ƒxxxxxxxxxxxxx, â˜ƒx);
            BlockState â˜ƒxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxxxxx);
            if (â˜ƒxxxxxxxxxxxxxx.isAir()) {
               â˜ƒxxxxxxxxx = -1;
            } else if (â˜ƒxxxxxxxxxxxxxx.is(â˜ƒ.getBlock())) {
               if (â˜ƒxxxxxxxxx == -1) {
                  â˜ƒxxxxxxxxxx = false;
                  if (â˜ƒxxxxxxx <= 0) {
                     â˜ƒxx = Blocks.AIR.defaultBlockState();
                     â˜ƒxxxxxx = â˜ƒ;
                  } else if (â˜ƒxxxxxxxxxxxxx >= â˜ƒ - 4 && â˜ƒxxxxxxxxxxxxx <= â˜ƒ + 1) {
                     â˜ƒxx = WHITE_TERRACOTTA;
                     â˜ƒxxxxxx = â˜ƒxxxx;
                  }

                  if (â˜ƒxxxxxxxxxxxxx < â˜ƒ && (â˜ƒxx == null || â˜ƒxx.isAir())) {
                     â˜ƒxx = â˜ƒ;
                  }

                  â˜ƒxxxxxxxxx = â˜ƒxxxxxxx + Math.max(0, â˜ƒxxxxxxxxxxxxx - â˜ƒ);
                  if (â˜ƒxxxxxxxxxxxxx < â˜ƒ - 1) {
                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxx, false);
                     if (â˜ƒxxxxxx == WHITE_TERRACOTTA) {
                        â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
                     }
                  } else if (â˜ƒxxxxxxxxxxxxx > 86 + â˜ƒxxxxxxx * 2) {
                     if (â˜ƒxxxxxxxx) {
                        â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, Blocks.COARSE_DIRT.defaultBlockState(), false);
                     } else {
                        â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, Blocks.GRASS_BLOCK.defaultBlockState(), false);
                     }
                  } else if (â˜ƒxxxxxxxxxxxxx <= â˜ƒ + 3 + â˜ƒxxxxxxx) {
                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxx, false);
                     â˜ƒxxxxxxxxxx = true;
                  } else {
                     BlockState â˜ƒxxxxxxxxxxxxxx;
                     if (â˜ƒxxxxxxxxxxxxx < 64 || â˜ƒxxxxxxxxxxxxx > 127) {
                        â˜ƒxxxxxxxxxxxxxx = ORANGE_TERRACOTTA;
                     } else if (â˜ƒxxxxxxxx) {
                        â˜ƒxxxxxxxxxxxxxx = TERRACOTTA;
                     } else {
                        â˜ƒxxxxxxxxxxxxxx = this.getBand(â˜ƒ, â˜ƒxxxxxxxxxxxxx, â˜ƒ);
                     }

                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, false);
                  }
               } else if (â˜ƒxxxxxxxxx > 0) {
                  --â˜ƒxxxxxxxxx;
                  if (â˜ƒxxxxxxxxxx) {
                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
                  } else {
                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, this.getBand(â˜ƒ, â˜ƒxxxxxxxxxxxxx, â˜ƒ), false);
                  }
               }

               ++â˜ƒxxxxxxxxxxx;
            }
         }
      }
   }
}
