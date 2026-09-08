package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

public class BadlandsSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   protected static final int MAX_CLAY_DEPTH = 15;
   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.defaultBlockState();
   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.defaultBlockState();
   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.defaultBlockState();
   private static final BlockState YELLOW_TERRACOTTA = Blocks.YELLOW_TERRACOTTA.defaultBlockState();
   private static final BlockState BROWN_TERRACOTTA = Blocks.BROWN_TERRACOTTA.defaultBlockState();
   private static final BlockState RED_TERRACOTTA = Blocks.RED_TERRACOTTA.defaultBlockState();
   private static final BlockState LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState();
   protected BlockState[] clayBands;
   protected long seed;
   protected PerlinSimplexNoise pillarNoise;
   protected PerlinSimplexNoise pillarRoofNoise;
   protected PerlinSimplexNoise clayBandsOffsetNoise;

   public BadlandsSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
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
                  if (â˜ƒxxxxxxxxxxxxx >= â˜ƒ - 1) {
                     if (â˜ƒxxxxxxxxxxxxx <= â˜ƒ + 3 + â˜ƒxxxxxxx) {
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
                  } else {
                     â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxx, false);
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
                        â˜ƒ.setBlockState(â˜ƒxxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
                     }
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

   @Override
   public void initNoise(long var1) {
      if (this.seed != â˜ƒ || this.clayBands == null) {
         this.generateBands(â˜ƒ);
      }

      if (this.seed != â˜ƒ || this.pillarNoise == null || this.pillarRoofNoise == null) {
         WorldgenRandom â˜ƒ = new WorldgenRandom(â˜ƒ);
         this.pillarNoise = new PerlinSimplexNoise(â˜ƒ, IntStream.rangeClosed(-3, 0));
         this.pillarRoofNoise = new PerlinSimplexNoise(â˜ƒ, ImmutableList.of(0));
      }

      this.seed = â˜ƒ;
   }

   protected void generateBands(long var1) {
      this.clayBands = new BlockState[64];
      Arrays.fill(this.clayBands, TERRACOTTA);
      WorldgenRandom â˜ƒ = new WorldgenRandom(â˜ƒ);
      this.clayBandsOffsetNoise = new PerlinSimplexNoise(â˜ƒ, ImmutableList.of(0));

      for(int â˜ƒx = 0; â˜ƒx < 64; ++â˜ƒx) {
         â˜ƒx += â˜ƒ.nextInt(5) + 1;
         if (â˜ƒx < 64) {
            this.clayBands[â˜ƒx] = ORANGE_TERRACOTTA;
         }
      }

      int â˜ƒx = â˜ƒ.nextInt(4) + 2;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         int â˜ƒxxx = â˜ƒ.nextInt(3) + 1;
         int â˜ƒxxxx = â˜ƒ.nextInt(64);

         for(int â˜ƒxxxxx = 0; â˜ƒxxxx + â˜ƒxxxxx < 64 && â˜ƒxxxxx < â˜ƒxxx; ++â˜ƒxxxxx) {
            this.clayBands[â˜ƒxxxx + â˜ƒxxxxx] = YELLOW_TERRACOTTA;
         }
      }

      int â˜ƒxx = â˜ƒ.nextInt(4) + 2;

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
         int â˜ƒxxxx = â˜ƒ.nextInt(3) + 2;
         int â˜ƒxxxxx = â˜ƒ.nextInt(64);

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxx + â˜ƒxxxxxx < 64 && â˜ƒxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxx) {
            this.clayBands[â˜ƒxxxxx + â˜ƒxxxxxx] = BROWN_TERRACOTTA;
         }
      }

      int â˜ƒxxx = â˜ƒ.nextInt(4) + 2;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx; ++â˜ƒxxxx) {
         int â˜ƒxxxxx = â˜ƒ.nextInt(3) + 1;
         int â˜ƒxxxxxx = â˜ƒ.nextInt(64);

         for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxx + â˜ƒxxxxxxx < 64 && â˜ƒxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxx) {
            this.clayBands[â˜ƒxxxxxx + â˜ƒxxxxxxx] = RED_TERRACOTTA;
         }
      }

      int â˜ƒxxxx = â˜ƒ.nextInt(3) + 3;
      int â˜ƒxxxxx = 0;

      for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxx) {
         int â˜ƒxxxxxxx = 1;
         â˜ƒxxxxx += â˜ƒ.nextInt(16) + 4;

         for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxx + â˜ƒxxxxxxxx < 64 && â˜ƒxxxxxxxx < 1; ++â˜ƒxxxxxxxx) {
            this.clayBands[â˜ƒxxxxx + â˜ƒxxxxxxxx] = WHITE_TERRACOTTA;
            if (â˜ƒxxxxx + â˜ƒxxxxxxxx > 1 && â˜ƒ.nextBoolean()) {
               this.clayBands[â˜ƒxxxxx + â˜ƒxxxxxxxx - 1] = LIGHT_GRAY_TERRACOTTA;
            }

            if (â˜ƒxxxxx + â˜ƒxxxxxxxx < 63 && â˜ƒ.nextBoolean()) {
               this.clayBands[â˜ƒxxxxx + â˜ƒxxxxxxxx + 1] = LIGHT_GRAY_TERRACOTTA;
            }
         }
      }
   }

   protected BlockState getBand(int var1, int var2, int var3) {
      int â˜ƒ = (int)Math.round(this.clayBandsOffsetNoise.getValue((double)â˜ƒ / 512.0, (double)â˜ƒ / 512.0, false) * 2.0);
      return this.clayBands[(â˜ƒ + â˜ƒ + 64) % 64];
   }
}
