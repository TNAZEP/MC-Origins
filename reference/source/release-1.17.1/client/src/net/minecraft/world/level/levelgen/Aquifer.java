package net.minecraft.world.level.levelgen;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public interface Aquifer {
   int ALWAYS_LAVA_AT_OR_BELOW_Y_INDEX = 9;
   int ALWAYS_USE_SEA_LEVEL_WHEN_ABOVE = 30;

   static Aquifer create(
      ChunkPos var0, NormalNoise var1, NormalNoise var2, NormalNoise var3, NoiseGeneratorSettings var4, NoiseSampler var5, int var6, int var7
   ) {
      return new Aquifer.NoiseBasedAquifer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   static Aquifer createDisabled(final int var0, final BlockState var1) {
      return new Aquifer() {
         @Override
         public BlockState computeState(BaseStoneSource var1x, int var2, int var3, int var4, double var5) {
            if (â˜ƒ > 0.0) {
               return â˜ƒ.getBaseBlock(â˜ƒ, â˜ƒ, â˜ƒ);
            } else {
               return â˜ƒ >= â˜ƒ ? Blocks.AIR.defaultBlockState() : â˜ƒ;
            }
         }

         @Override
         public boolean shouldScheduleFluidUpdate() {
            return false;
         }
      };
   }

   BlockState computeState(BaseStoneSource var1, int var2, int var3, int var4, double var5);

   boolean shouldScheduleFluidUpdate();

   public static class NoiseBasedAquifer implements Aquifer {
      private static final int X_RANGE = 10;
      private static final int Y_RANGE = 9;
      private static final int Z_RANGE = 10;
      private static final int X_SEPARATION = 6;
      private static final int Y_SEPARATION = 3;
      private static final int Z_SEPARATION = 6;
      private static final int X_SPACING = 16;
      private static final int Y_SPACING = 12;
      private static final int Z_SPACING = 16;
      private final NormalNoise barrierNoise;
      private final NormalNoise waterLevelNoise;
      private final NormalNoise lavaNoise;
      private final NoiseGeneratorSettings noiseGeneratorSettings;
      private final Aquifer.NoiseBasedAquifer.AquiferStatus[] aquiferCache;
      private final long[] aquiferLocationCache;
      private boolean shouldScheduleFluidUpdate;
      private final NoiseSampler sampler;
      private final int minGridX;
      private final int minGridY;
      private final int minGridZ;
      private final int gridSizeX;
      private final int gridSizeZ;

      NoiseBasedAquifer(ChunkPos var1, NormalNoise var2, NormalNoise var3, NormalNoise var4, NoiseGeneratorSettings var5, NoiseSampler var6, int var7, int var8) {
         this.barrierNoise = â˜ƒ;
         this.waterLevelNoise = â˜ƒ;
         this.lavaNoise = â˜ƒ;
         this.noiseGeneratorSettings = â˜ƒ;
         this.sampler = â˜ƒ;
         this.minGridX = this.gridX(â˜ƒ.getMinBlockX()) - 1;
         int â˜ƒ = this.gridX(â˜ƒ.getMaxBlockX()) + 1;
         this.gridSizeX = â˜ƒ - this.minGridX + 1;
         this.minGridY = this.gridY(â˜ƒ) - 1;
         int â˜ƒx = this.gridY(â˜ƒ + â˜ƒ) + 1;
         int â˜ƒxx = â˜ƒx - this.minGridY + 1;
         this.minGridZ = this.gridZ(â˜ƒ.getMinBlockZ()) - 1;
         int â˜ƒxxx = this.gridZ(â˜ƒ.getMaxBlockZ()) + 1;
         this.gridSizeZ = â˜ƒxxx - this.minGridZ + 1;
         int â˜ƒxxxx = this.gridSizeX * â˜ƒxx * this.gridSizeZ;
         this.aquiferCache = new Aquifer.NoiseBasedAquifer.AquiferStatus[â˜ƒxxxx];
         this.aquiferLocationCache = new long[â˜ƒxxxx];
         Arrays.fill(this.aquiferLocationCache, Long.MAX_VALUE);
      }

      private int getIndex(int var1, int var2, int var3) {
         int â˜ƒ = â˜ƒ - this.minGridX;
         int â˜ƒx = â˜ƒ - this.minGridY;
         int â˜ƒxx = â˜ƒ - this.minGridZ;
         return (â˜ƒx * this.gridSizeZ + â˜ƒxx) * this.gridSizeX + â˜ƒ;
      }

      @Override
      public BlockState computeState(BaseStoneSource var1, int var2, int var3, int var4, double var5) {
         if (â˜ƒ <= 0.0) {
            double â˜ƒ;
            BlockState â˜ƒx;
            boolean â˜ƒxx;
            if (this.isLavaLevel(â˜ƒ)) {
               â˜ƒx = Blocks.LAVA.defaultBlockState();
               â˜ƒ = 0.0;
               â˜ƒxx = false;
            } else {
               int â˜ƒ = Math.floorDiv(â˜ƒ - 5, 16);
               int â˜ƒx = Math.floorDiv(â˜ƒ + 1, 12);
               int â˜ƒxx = Math.floorDiv(â˜ƒ - 5, 16);
               int â˜ƒxxx = Integer.MAX_VALUE;
               int â˜ƒxxxx = Integer.MAX_VALUE;
               int â˜ƒxxxxx = Integer.MAX_VALUE;
               long â˜ƒxxxxxx = 0L;
               long â˜ƒxxxxxxx = 0L;
               long â˜ƒxxxxxxxx = 0L;

               for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx <= 1; ++â˜ƒxxxxxxxxx) {
                  for(int â˜ƒxxxxxxxxxx = -1; â˜ƒxxxxxxxxxx <= 1; ++â˜ƒxxxxxxxxxx) {
                     for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx <= 1; ++â˜ƒxxxxxxxxxxx) {
                        int â˜ƒxxxxxxxxxxxxx = â˜ƒ + â˜ƒxxxxxxxxx;
                        int â˜ƒxxxxxxxxxxxxxx = â˜ƒx + â˜ƒxxxxxxxxxx;
                        int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxx + â˜ƒxxxxxxxxxxx;
                        int â˜ƒxxxxxxxxxxxxxxxx = this.getIndex(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
                        long â˜ƒxxxxxxxxxxxxxxxxx = this.aquiferLocationCache[â˜ƒxxxxxxxxxxxxxxxx];
                        long â˜ƒxxxxxxxxxxxx;
                        if (â˜ƒxxxxxxxxxxxxxxxxx != Long.MAX_VALUE) {
                           â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx;
                        } else {
                           WorldgenRandom â˜ƒxxxxxxxxxxxx = new WorldgenRandom(Mth.getSeed(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx * 3, â˜ƒxxxxxxxxxxxxxxx) + 1L);
                           â˜ƒxxxxxxxxxxxx = BlockPos.asLong(
                              â˜ƒxxxxxxxxxxxxx * 16 + â˜ƒxxxxxxxxxxxx.nextInt(10),
                              â˜ƒxxxxxxxxxxxxxx * 12 + â˜ƒxxxxxxxxxxxx.nextInt(9),
                              â˜ƒxxxxxxxxxxxxxxx * 16 + â˜ƒxxxxxxxxxxxx.nextInt(10)
                           );
                           this.aquiferLocationCache[â˜ƒxxxxxxxxxxxxxxxx] = â˜ƒxxxxxxxxxxxx;
                        }

                        int â˜ƒxxxxxxxxxxxx = BlockPos.getX(â˜ƒxxxxxxxxxxxx) - â˜ƒ;
                        int â˜ƒxxxxxxxxxxxxx = BlockPos.getY(â˜ƒxxxxxxxxxxxx) - â˜ƒ;
                        int â˜ƒxxxxxxxxxxxxxx = BlockPos.getZ(â˜ƒxxxxxxxxxxxx) - â˜ƒ;
                        int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxx
                           + â˜ƒxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxx
                           + â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx;
                        if (â˜ƒxxx >= â˜ƒxxxxxxxxxxxxxxx) {
                           â˜ƒxxxxxxxx = â˜ƒxxxxxxx;
                           â˜ƒxxxxxxx = â˜ƒxxxxxx;
                           â˜ƒxxxxxx = â˜ƒxxxxxxxxxxxx;
                           â˜ƒxxxxx = â˜ƒxxxx;
                           â˜ƒxxxx = â˜ƒxxx;
                           â˜ƒxxx = â˜ƒxxxxxxxxxxxxxxx;
                        } else if (â˜ƒxxxx >= â˜ƒxxxxxxxxxxxxxxx) {
                           â˜ƒxxxxxxxx = â˜ƒxxxxxxx;
                           â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxx;
                           â˜ƒxxxxx = â˜ƒxxxx;
                           â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxx;
                        } else if (â˜ƒxxxxx >= â˜ƒxxxxxxxxxxxxxxx) {
                           â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxxx;
                           â˜ƒxxxxx = â˜ƒxxxxxxxxxxxxxxx;
                        }
                     }
                  }
               }

               Aquifer.NoiseBasedAquifer.AquiferStatus â˜ƒxxxxxxxxx = this.getAquiferStatus(â˜ƒxxxxxx);
               Aquifer.NoiseBasedAquifer.AquiferStatus â˜ƒxxxxxxxxxx = this.getAquiferStatus(â˜ƒxxxxxxx);
               Aquifer.NoiseBasedAquifer.AquiferStatus â˜ƒxxxxxxxxxxx = this.getAquiferStatus(â˜ƒxxxxxxxx);
               double â˜ƒxxxxxxxxxxxx = this.similarity(â˜ƒxxx, â˜ƒxxxx);
               double â˜ƒxxxxxxxxxxxxx = this.similarity(â˜ƒxxx, â˜ƒxxxxx);
               double â˜ƒxxxxxxxxxxxxxx = this.similarity(â˜ƒxxxx, â˜ƒxxxxx);
               â˜ƒxx = â˜ƒxxxxxxxxxxxx > 0.0;
               if (â˜ƒxxxxxxxxx.fluidLevel >= â˜ƒ && â˜ƒxxxxxxxxx.fluidType.is(Blocks.WATER) && this.isLavaLevel(â˜ƒ - 1)) {
                  â˜ƒ = 1.0;
               } else if (â˜ƒxxxxxxxxxxxx > -1.0) {
                  double â˜ƒxxxxxxxxx = 1.0 + (this.barrierNoise.getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ) + 0.05) / 4.0;
                  double â˜ƒxxxxxxxxxx = this.calculatePressure(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
                  double â˜ƒxxxxxxxxxxx = this.calculatePressure(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxx);
                  double â˜ƒxxxxxxxxxxxx = this.calculatePressure(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx);
                  double â˜ƒxxxxxxxxxxxxx = Math.max(0.0, â˜ƒxxxxxxxxxxxx);
                  double â˜ƒxxxxxxxxxxxxxx = Math.max(0.0, â˜ƒxxxxxxxxxxxxx);
                  double â˜ƒxxxxxxxxxxxxxxx = Math.max(0.0, â˜ƒxxxxxxxxxxxxxx);
                  double â˜ƒxxxxxxxxxxxxxxxx = 2.0
                     * â˜ƒxxxxxxxxxxxxx
                     * Math.max(â˜ƒxxxxxxxxxx, Math.max(â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx));
                  â˜ƒ = Math.max(0.0, â˜ƒxxxxxxxxxxxxxxxx);
               } else {
                  â˜ƒ = 0.0;
               }

               â˜ƒx = â˜ƒ >= â˜ƒxxxxxxxxx.fluidLevel ? Blocks.AIR.defaultBlockState() : â˜ƒxxxxxxxxx.fluidType;
            }

            if (â˜ƒ + â˜ƒ <= 0.0) {
               this.shouldScheduleFluidUpdate = â˜ƒxx;
               return â˜ƒx;
            }
         }

         this.shouldScheduleFluidUpdate = false;
         return â˜ƒ.getBaseBlock(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean shouldScheduleFluidUpdate() {
         return this.shouldScheduleFluidUpdate;
      }

      private boolean isLavaLevel(int var1) {
         return â˜ƒ - this.noiseGeneratorSettings.noiseSettings().minY() <= 9;
      }

      private double similarity(int var1, int var2) {
         double â˜ƒ = 25.0;
         return 1.0 - (double)Math.abs(â˜ƒ - â˜ƒ) / 25.0;
      }

      private double calculatePressure(int var1, double var2, Aquifer.NoiseBasedAquifer.AquiferStatus var4, Aquifer.NoiseBasedAquifer.AquiferStatus var5) {
         if (â˜ƒ <= â˜ƒ.fluidLevel && â˜ƒ <= â˜ƒ.fluidLevel && â˜ƒ.fluidType != â˜ƒ.fluidType) {
            return 1.0;
         } else {
            int â˜ƒ = Math.abs(â˜ƒ.fluidLevel - â˜ƒ.fluidLevel);
            double â˜ƒx = 0.5 * (double)(â˜ƒ.fluidLevel + â˜ƒ.fluidLevel);
            double â˜ƒxx = Math.abs(â˜ƒx - (double)â˜ƒ - 0.5);
            return 0.5 * (double)â˜ƒ * â˜ƒ - â˜ƒxx;
         }
      }

      private int gridX(int var1) {
         return Math.floorDiv(â˜ƒ, 16);
      }

      private int gridY(int var1) {
         return Math.floorDiv(â˜ƒ, 12);
      }

      private int gridZ(int var1) {
         return Math.floorDiv(â˜ƒ, 16);
      }

      private Aquifer.NoiseBasedAquifer.AquiferStatus getAquiferStatus(long var1) {
         int â˜ƒ = BlockPos.getX(â˜ƒ);
         int â˜ƒx = BlockPos.getY(â˜ƒ);
         int â˜ƒxx = BlockPos.getZ(â˜ƒ);
         int â˜ƒxxx = this.gridX(â˜ƒ);
         int â˜ƒxxxx = this.gridY(â˜ƒx);
         int â˜ƒxxxxx = this.gridZ(â˜ƒxx);
         int â˜ƒxxxxxx = this.getIndex(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         Aquifer.NoiseBasedAquifer.AquiferStatus â˜ƒxxxxxxx = this.aquiferCache[â˜ƒxxxxxx];
         if (â˜ƒxxxxxxx != null) {
            return â˜ƒxxxxxxx;
         } else {
            Aquifer.NoiseBasedAquifer.AquiferStatus â˜ƒ = this.computeAquifer(â˜ƒ, â˜ƒx, â˜ƒxx);
            this.aquiferCache[â˜ƒxxxxxx] = â˜ƒ;
            return â˜ƒ;
         }
      }

      private Aquifer.NoiseBasedAquifer.AquiferStatus computeAquifer(int var1, int var2, int var3) {
         int â˜ƒ = this.noiseGeneratorSettings.seaLevel();
         if (â˜ƒ > 30) {
            return new Aquifer.NoiseBasedAquifer.AquiferStatus(â˜ƒ, Blocks.WATER.defaultBlockState());
         } else {
            int â˜ƒ = 64;
            int â˜ƒx = -10;
            int â˜ƒxx = 40;
            double â˜ƒxxx = this.waterLevelNoise.getValue((double)Math.floorDiv(â˜ƒ, 64), (double)Math.floorDiv(â˜ƒ, 40) / 1.4, (double)Math.floorDiv(â˜ƒ, 64))
                  * 30.0
               + -10.0;
            boolean â˜ƒxxxx = false;
            if (Math.abs(â˜ƒxxx) > 8.0) {
               â˜ƒxxx *= 4.0;
            }

            int â˜ƒ = Math.floorDiv(â˜ƒ, 40) * 40 + 20;
            int â˜ƒx = â˜ƒ + Mth.floor(â˜ƒxxx);
            if (â˜ƒ == -20) {
               double â˜ƒxx = this.lavaNoise.getValue((double)Math.floorDiv(â˜ƒ, 64), (double)Math.floorDiv(â˜ƒ, 40) / 1.4, (double)Math.floorDiv(â˜ƒ, 64));
               â˜ƒxxxx = Math.abs(â˜ƒxx) > 0.22F;
            }

            return new Aquifer.NoiseBasedAquifer.AquiferStatus(Math.min(56, â˜ƒx), â˜ƒxxxx ? Blocks.LAVA.defaultBlockState() : Blocks.WATER.defaultBlockState());
         }
      }

      static final class AquiferStatus {
         final int fluidLevel;
         final BlockState fluidType;

         public AquiferStatus(int var1, BlockState var2) {
            this.fluidLevel = â˜ƒ;
            this.fluidType = â˜ƒ;
         }
      }
   }
}
