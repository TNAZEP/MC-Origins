package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class NetherForestSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();
   protected long seed;
   private PerlinNoise decorationNoise;

   public NetherForestSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
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
      double â˜ƒxxx = this.decorationNoise.getValue((double)â˜ƒ * 0.1, (double)â˜ƒ, (double)â˜ƒ * 0.1);
      boolean â˜ƒxxxx = â˜ƒxxx > 0.15 + â˜ƒ.nextDouble() * 0.35;
      double â˜ƒxxxxx = this.decorationNoise.getValue((double)â˜ƒ * 0.1, 109.0, (double)â˜ƒ * 0.1);
      boolean â˜ƒxxxxxx = â˜ƒxxxxx > 0.25 + â˜ƒ.nextDouble() * 0.9;
      int â˜ƒxxxxxxx = (int)(â˜ƒ / 3.0 + 3.0 + â˜ƒ.nextDouble() * 0.25);
      BlockPos.MutableBlockPos â˜ƒxxxxxxxx = new BlockPos.MutableBlockPos();
      int â˜ƒxxxxxxxxx = -1;
      BlockState â˜ƒxxxxxxxxxx = â˜ƒ.getUnderMaterial();

      for(int â˜ƒxxxxxxxxxxx = 127; â˜ƒxxxxxxxxxxx >= â˜ƒ; --â˜ƒxxxxxxxxxxx) {
         â˜ƒxxxxxxxx.set(â˜ƒx, â˜ƒxxxxxxxxxxx, â˜ƒxx);
         BlockState â˜ƒxxxxxxxxxxxx = â˜ƒ.getTopMaterial();
         BlockState â˜ƒxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxx);
         if (â˜ƒxxxxxxxxxxxxx.isAir()) {
            â˜ƒxxxxxxxxx = -1;
         } else if (â˜ƒxxxxxxxxxxxxx.is(â˜ƒ.getBlock())) {
            if (â˜ƒxxxxxxxxx == -1) {
               boolean â˜ƒxxxxxxxxxxxx = false;
               if (â˜ƒxxxxxxx <= 0) {
                  â˜ƒxxxxxxxxxxxx = true;
                  â˜ƒxxxxxxxxxx = â˜ƒ.getUnderMaterial();
               }

               if (â˜ƒxxxx) {
                  â˜ƒxxxxxxxxxxxx = â˜ƒ.getUnderMaterial();
               } else if (â˜ƒxxxxxx) {
                  â˜ƒxxxxxxxxxxxx = â˜ƒ.getUnderwaterMaterial();
               }

               if (â˜ƒxxxxxxxxxxx < â˜ƒ && â˜ƒxxxxxxxxxxxx) {
                  â˜ƒxxxxxxxxxxxx = â˜ƒ;
               }

               â˜ƒxxxxxxxxx = â˜ƒxxxxxxx;
               if (â˜ƒxxxxxxxxxxx >= â˜ƒ - 1) {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx, false);
               } else {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, false);
               }
            } else if (â˜ƒxxxxxxxxx > 0) {
               --â˜ƒxxxxxxxxx;
               â˜ƒ.setBlockState(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, false);
            }
         }
      }
   }

   @Override
   public void initNoise(long var1) {
      if (this.seed != â˜ƒ || this.decorationNoise == null) {
         this.decorationNoise = new PerlinNoise(new WorldgenRandom(â˜ƒ), ImmutableList.of(0));
      }

      this.seed = â˜ƒ;
   }
}
