package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class NetherSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();
   private static final BlockState GRAVEL = Blocks.GRAVEL.defaultBlockState();
   private static final BlockState SOUL_SAND = Blocks.SOUL_SAND.defaultBlockState();
   protected long seed;
   protected PerlinNoise decorationNoise;

   public NetherSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
      super(â˜ƒ);
   }

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
      int â˜ƒ = â˜ƒ;
      int â˜ƒx = â˜ƒ & 15;
      int â˜ƒxx = â˜ƒ & 15;
      double â˜ƒxxx = 0.03125;
      boolean â˜ƒxxxx = this.decorationNoise.getValue((double)â˜ƒ * 0.03125, (double)â˜ƒ * 0.03125, 0.0) * 75.0 + â˜ƒ.nextDouble() > 0.0;
      boolean â˜ƒxxxxx = this.decorationNoise.getValue((double)â˜ƒ * 0.03125, 109.0, (double)â˜ƒ * 0.03125) * 75.0 + â˜ƒ.nextDouble() > 0.0;
      int â˜ƒxxxxxx = (int)(â˜ƒ / 3.0 + 3.0 + â˜ƒ.nextDouble() * 0.25);
      BlockPos.MutableBlockPos â˜ƒxxxxxxx = new BlockPos.MutableBlockPos();
      int â˜ƒxxxxxxxx = -1;
      BlockState â˜ƒxxxxxxxxx = â˜ƒ.getTopMaterial();
      BlockState â˜ƒxxxxxxxxxx = â˜ƒ.getUnderMaterial();

      for(int â˜ƒxxxxxxxxxxx = 127; â˜ƒxxxxxxxxxxx >= â˜ƒ; --â˜ƒxxxxxxxxxxx) {
         â˜ƒxxxxxxx.set(â˜ƒx, â˜ƒxxxxxxxxxxx, â˜ƒxx);
         BlockState â˜ƒxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxx);
         if (â˜ƒxxxxxxxxxxxx.isAir()) {
            â˜ƒxxxxxxxx = -1;
         } else if (â˜ƒxxxxxxxxxxxx.is(â˜ƒ.getBlock())) {
            if (â˜ƒxxxxxxxx == -1) {
               boolean â˜ƒxxxxxxxxxxxx = false;
               if (â˜ƒxxxxxx <= 0) {
                  â˜ƒxxxxxxxxxxxx = true;
                  â˜ƒxxxxxxxxxx = â˜ƒ.getUnderMaterial();
               } else if (â˜ƒxxxxxxxxxxx >= â˜ƒ - 4 && â˜ƒxxxxxxxxxxx <= â˜ƒ + 1) {
                  â˜ƒxxxxxxxxx = â˜ƒ.getTopMaterial();
                  â˜ƒxxxxxxxxxx = â˜ƒ.getUnderMaterial();
                  if (â˜ƒxxxxx) {
                     â˜ƒxxxxxxxxx = GRAVEL;
                     â˜ƒxxxxxxxxxx = â˜ƒ.getUnderMaterial();
                  }

                  if (â˜ƒxxxx) {
                     â˜ƒxxxxxxxxx = SOUL_SAND;
                     â˜ƒxxxxxxxxxx = SOUL_SAND;
                  }
               }

               if (â˜ƒxxxxxxxxxxx < â˜ƒ && â˜ƒxxxxxxxxxxxx) {
                  â˜ƒxxxxxxxxx = â˜ƒ;
               }

               â˜ƒxxxxxxxx = â˜ƒxxxxxx;
               if (â˜ƒxxxxxxxxxxx >= â˜ƒ - 1) {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxx, â˜ƒxxxxxxxxx, false);
               } else {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxx, false);
               }
            } else if (â˜ƒxxxxxxxx > 0) {
               --â˜ƒxxxxxxxx;
               â˜ƒ.setBlockState(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxx, false);
            }
         }
      }
   }

   @Override
   public void initNoise(long var1) {
      if (this.seed != â˜ƒ || this.decorationNoise == null) {
         this.decorationNoise = new PerlinNoise(new WorldgenRandom(â˜ƒ), IntStream.rangeClosed(-3, 0));
      }

      this.seed = â˜ƒ;
   }
}
