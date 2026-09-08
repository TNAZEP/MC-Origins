package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class ErodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.defaultBlockState();
   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.defaultBlockState();
   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.defaultBlockState();

   public ErodedBadlandsSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
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
      double â˜ƒ = 0.0;
      double â˜ƒx = Math.min(Math.abs(â˜ƒ), this.pillarNoise.getValue((double)â˜ƒ * 0.25, (double)â˜ƒ * 0.25, false) * 15.0);
      if (â˜ƒx > 0.0) {
         double â˜ƒxx = 0.001953125;
         double â˜ƒxxx = Math.abs(this.pillarRoofNoise.getValue((double)â˜ƒ * 0.001953125, (double)â˜ƒ * 0.001953125, false));
         â˜ƒ = â˜ƒx * â˜ƒx * 2.5;
         double â˜ƒxxxx = Math.ceil(â˜ƒxxx * 50.0) + 14.0;
         if (â˜ƒ > â˜ƒxxxx) {
            â˜ƒ = â˜ƒxxxx;
         }

         â˜ƒ += 64.0;
      }

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
      BlockPos.MutableBlockPos â˜ƒxxxxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxxxxxxx = Math.max(â˜ƒ, (int)â˜ƒ + 1); â˜ƒxxxxxxxxxxxx >= â˜ƒ; --â˜ƒxxxxxxxxxxxx) {
         â˜ƒxxxxxxxxxxx.set(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒx);
         if (â˜ƒ.getBlockState(â˜ƒxxxxxxxxxxx).isAir() && â˜ƒxxxxxxxxxxxx < (int)â˜ƒ) {
            â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxx, â˜ƒ, false);
         }

         BlockState â˜ƒxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxxxx);
         if (â˜ƒxxxxxxxxxxxxx.isAir()) {
            â˜ƒxxxxxxxxx = -1;
         } else if (â˜ƒxxxxxxxxxxxxx.is(â˜ƒ.getBlock())) {
            if (â˜ƒxxxxxxxxx == -1) {
               â˜ƒxxxxxxxxxx = false;
               if (â˜ƒxxxxxxx <= 0) {
                  â˜ƒxx = Blocks.AIR.defaultBlockState();
                  â˜ƒxxxxxx = â˜ƒ;
               } else if (â˜ƒxxxxxxxxxxxx >= â˜ƒ - 4 && â˜ƒxxxxxxxxxxxx <= â˜ƒ + 1) {
                  â˜ƒxx = WHITE_TERRACOTTA;
                  â˜ƒxxxxxx = â˜ƒxxxx;
               }

               if (â˜ƒxxxxxxxxxxxx < â˜ƒ && (â˜ƒxx == null || â˜ƒxx.isAir())) {
                  â˜ƒxx = â˜ƒ;
               }

               â˜ƒxxxxxxxxx = â˜ƒxxxxxxx + Math.max(0, â˜ƒxxxxxxxxxxxx - â˜ƒ);
               if (â˜ƒxxxxxxxxxxxx >= â˜ƒ - 1) {
                  if (â˜ƒxxxxxxxxxxxx > â˜ƒ + 3 + â˜ƒxxxxxxx) {
                     BlockState â˜ƒxxxxxxxxxxxxx;
                     if (â˜ƒxxxxxxxxxxxx < 64 || â˜ƒxxxxxxxxxxxx > 127) {
                        â˜ƒxxxxxxxxxxxxx = ORANGE_TERRACOTTA;
                     } else if (â˜ƒxxxxxxxx) {
                        â˜ƒxxxxxxxxxxxxx = TERRACOTTA;
                     } else {
                        â˜ƒxxxxxxxxxxxxx = this.getBand(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒ);
                     }

                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, false);
                  } else {
                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxx, â˜ƒxxxxx, false);
                     â˜ƒxxxxxxxxxx = true;
                  }
               } else {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxx, false);
                  if (â˜ƒxxxxxx.is(Blocks.WHITE_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.ORANGE_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.MAGENTA_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.LIGHT_BLUE_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.YELLOW_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.LIME_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.PINK_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.GRAY_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.LIGHT_GRAY_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.CYAN_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.PURPLE_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.BLUE_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.BROWN_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.GREEN_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.RED_TERRACOTTA)
                     || â˜ƒxxxxxx.is(Blocks.BLACK_TERRACOTTA)) {
                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
                  }
               }
            } else if (â˜ƒxxxxxxxxx > 0) {
               --â˜ƒxxxxxxxxx;
               if (â˜ƒxxxxxxxxxx) {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
               } else {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxx, this.getBand(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒ), false);
               }
            }
         }
      }
   }
}
