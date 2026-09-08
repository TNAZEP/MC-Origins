package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.material.Material;

public class FrozenOceanSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   protected static final BlockState PACKED_ICE = Blocks.PACKED_ICE.defaultBlockState();
   protected static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.defaultBlockState();
   private static final BlockState AIR = Blocks.AIR.defaultBlockState();
   private static final BlockState GRAVEL = Blocks.GRAVEL.defaultBlockState();
   private static final BlockState ICE = Blocks.ICE.defaultBlockState();
   private PerlinSimplexNoise icebergNoise;
   private PerlinSimplexNoise icebergRoofNoise;
   private long seed;

   public FrozenOceanSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
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
      double â˜ƒ = 0.0;
      double â˜ƒx = 0.0;
      BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();
      float â˜ƒxxx = â˜ƒ.getTemperature(â˜ƒxx.set(â˜ƒ, 63, â˜ƒ));
      double â˜ƒxxxx = Math.min(Math.abs(â˜ƒ), this.icebergNoise.getValue((double)â˜ƒ * 0.1, (double)â˜ƒ * 0.1, false) * 15.0);
      if (â˜ƒxxxx > 1.8) {
         double â˜ƒxxxxx = 0.09765625;
         double â˜ƒxxxxxx = Math.abs(this.icebergRoofNoise.getValue((double)â˜ƒ * 0.09765625, (double)â˜ƒ * 0.09765625, false));
         â˜ƒ = â˜ƒxxxx * â˜ƒxxxx * 1.2;
         double â˜ƒxxxxxxx = Math.ceil(â˜ƒxxxxxx * 40.0) + 14.0;
         if (â˜ƒ > â˜ƒxxxxxxx) {
            â˜ƒ = â˜ƒxxxxxxx;
         }

         if (â˜ƒxxx > 0.1F) {
            â˜ƒ -= 2.0;
         }

         if (â˜ƒ > 2.0) {
            â˜ƒx = (double)â˜ƒ - â˜ƒ - 7.0;
            â˜ƒ += (double)â˜ƒ;
         } else {
            â˜ƒ = 0.0;
         }
      }

      int â˜ƒ = â˜ƒ & 15;
      int â˜ƒx = â˜ƒ & 15;
      SurfaceBuilderConfiguration â˜ƒxx = â˜ƒ.getGenerationSettings().getSurfaceBuilderConfig();
      BlockState â˜ƒxxx = â˜ƒxx.getUnderMaterial();
      BlockState â˜ƒxxxx = â˜ƒxx.getTopMaterial();
      BlockState â˜ƒxxxxx = â˜ƒxxx;
      BlockState â˜ƒxxxxxx = â˜ƒxxxx;
      int â˜ƒxxxxxxx = (int)(â˜ƒ / 3.0 + 3.0 + â˜ƒ.nextDouble() * 0.25);
      int â˜ƒxxxxxxxx = -1;
      int â˜ƒxxxxxxxxx = 0;
      int â˜ƒxxxxxxxxxx = 2 + â˜ƒ.nextInt(4);
      int â˜ƒxxxxxxxxxxx = â˜ƒ + 18 + â˜ƒ.nextInt(10);

      for(int â˜ƒxxxxxxxxxxxx = Math.max(â˜ƒ, (int)â˜ƒ + 1); â˜ƒxxxxxxxxxxxx >= â˜ƒ; --â˜ƒxxxxxxxxxxxx) {
         â˜ƒxx.set(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒx);
         if (â˜ƒ.getBlockState(â˜ƒxx).isAir() && â˜ƒxxxxxxxxxxxx < (int)â˜ƒ && â˜ƒ.nextDouble() > 0.01) {
            â˜ƒ.setBlockState(â˜ƒxx, PACKED_ICE, false);
         } else if (â˜ƒ.getBlockState(â˜ƒxx).getMaterial() == Material.WATER
            && â˜ƒxxxxxxxxxxxx > (int)â˜ƒx
            && â˜ƒxxxxxxxxxxxx < â˜ƒ
            && â˜ƒx != 0.0
            && â˜ƒ.nextDouble() > 0.15) {
            â˜ƒ.setBlockState(â˜ƒxx, PACKED_ICE, false);
         }

         BlockState â˜ƒxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxx);
         if (â˜ƒxxxxxxxxxxxxx.isAir()) {
            â˜ƒxxxxxxxx = -1;
         } else if (â˜ƒxxxxxxxxxxxxx.is(â˜ƒ.getBlock())) {
            if (â˜ƒxxxxxxxx == -1) {
               if (â˜ƒxxxxxxx <= 0) {
                  â˜ƒxxxxxx = AIR;
                  â˜ƒxxxxx = â˜ƒ;
               } else if (â˜ƒxxxxxxxxxxxx >= â˜ƒ - 4 && â˜ƒxxxxxxxxxxxx <= â˜ƒ + 1) {
                  â˜ƒxxxxxx = â˜ƒxxxx;
                  â˜ƒxxxxx = â˜ƒxxx;
               }

               if (â˜ƒxxxxxxxxxxxx < â˜ƒ && (â˜ƒxxxxxx == null || â˜ƒxxxxxx.isAir())) {
                  if (â˜ƒ.getTemperature(â˜ƒxx.set(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒ)) < 0.15F) {
                     â˜ƒxxxxxx = ICE;
                  } else {
                     â˜ƒxxxxxx = â˜ƒ;
                  }
               }

               â˜ƒxxxxxxxx = â˜ƒxxxxxxx;
               if (â˜ƒxxxxxxxxxxxx >= â˜ƒ - 1) {
                  â˜ƒ.setBlockState(â˜ƒxx, â˜ƒxxxxxx, false);
               } else if (â˜ƒxxxxxxxxxxxx < â˜ƒ - 7 - â˜ƒxxxxxxx) {
                  â˜ƒxxxxxx = AIR;
                  â˜ƒxxxxx = â˜ƒ;
                  â˜ƒ.setBlockState(â˜ƒxx, GRAVEL, false);
               } else {
                  â˜ƒ.setBlockState(â˜ƒxx, â˜ƒxxxxx, false);
               }
            } else if (â˜ƒxxxxxxxx > 0) {
               --â˜ƒxxxxxxxx;
               â˜ƒ.setBlockState(â˜ƒxx, â˜ƒxxxxx, false);
               if (â˜ƒxxxxxxxx == 0 && â˜ƒxxxxx.is(Blocks.SAND) && â˜ƒxxxxxxx > 1) {
                  â˜ƒxxxxxxxx = â˜ƒ.nextInt(4) + Math.max(0, â˜ƒxxxxxxxxxxxx - 63);
                  â˜ƒxxxxx = â˜ƒxxxxx.is(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.defaultBlockState() : Blocks.SANDSTONE.defaultBlockState();
               }
            }
         } else if (â˜ƒxxxxxxxxxxxxx.is(Blocks.PACKED_ICE) && â˜ƒxxxxxxxxx <= â˜ƒxxxxxxxxxx && â˜ƒxxxxxxxxxxxx > â˜ƒxxxxxxxxxxx) {
            â˜ƒ.setBlockState(â˜ƒxx, SNOW_BLOCK, false);
            ++â˜ƒxxxxxxxxx;
         }
      }
   }

   @Override
   public void initNoise(long var1) {
      if (this.seed != â˜ƒ || this.icebergNoise == null || this.icebergRoofNoise == null) {
         WorldgenRandom â˜ƒ = new WorldgenRandom(â˜ƒ);
         this.icebergNoise = new PerlinSimplexNoise(â˜ƒ, IntStream.rangeClosed(-3, 0));
         this.icebergRoofNoise = new PerlinSimplexNoise(â˜ƒ, ImmutableList.of(0));
      }

      this.seed = â˜ƒ;
   }
}
