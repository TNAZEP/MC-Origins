package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.material.Material;

public class IcebergFeature extends Feature<BlockStateConfiguration> {
   public IcebergFeature(Codec<BlockStateConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<BlockStateConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      â˜ƒ = new BlockPos(â˜ƒ.getX(), â˜ƒ.chunkGenerator().getSeaLevel(), â˜ƒ.getZ());
      Random â˜ƒxx = â˜ƒ.random();
      boolean â˜ƒxxx = â˜ƒxx.nextDouble() > 0.7;
      BlockState â˜ƒxxxx = â˜ƒ.config().state;
      double â˜ƒxxxxx = â˜ƒxx.nextDouble() * 2.0 * Math.PI;
      int â˜ƒxxxxxx = 11 - â˜ƒxx.nextInt(5);
      int â˜ƒxxxxxxx = 3 + â˜ƒxx.nextInt(3);
      boolean â˜ƒxxxxxxxx = â˜ƒxx.nextDouble() > 0.7;
      int â˜ƒxxxxxxxxx = 11;
      int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx ? â˜ƒxx.nextInt(6) + 6 : â˜ƒxx.nextInt(15) + 3;
      if (!â˜ƒxxxxxxxx && â˜ƒxx.nextDouble() > 0.9) {
         â˜ƒxxxxxxxxxx += â˜ƒxx.nextInt(19) + 7;
      }

      int â˜ƒ = Math.min(â˜ƒxxxxxxxxxx + â˜ƒxx.nextInt(11), 18);
      int â˜ƒx = Math.min(â˜ƒxxxxxxxxxx + â˜ƒxx.nextInt(7) - â˜ƒxx.nextInt(5), 11);
      int â˜ƒxx = â˜ƒxxxxxxxx ? â˜ƒxxxxxx : 11;

      for(int â˜ƒxxx = -â˜ƒxx; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = -â˜ƒxx; â˜ƒxxxx < â˜ƒxx; ++â˜ƒxxxx) {
            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxxxxxxxx; ++â˜ƒxxxxx) {
               int â˜ƒxxxxxx = â˜ƒxxxxxxxx
                  ? this.heightDependentRadiusEllipse(â˜ƒxxxxx, â˜ƒxxxxxxxxxx, â˜ƒx)
                  : this.heightDependentRadiusRound(â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxxxxxx, â˜ƒx);
               if (â˜ƒxxxxxxxx || â˜ƒxxx < â˜ƒxxxxxx) {
                  this.generateIcebergBlock(
                     â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒxxxxxxxxxx, â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx
                  );
               }
            }
         }
      }

      this.smooth(â˜ƒx, â˜ƒ, â˜ƒx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxx);

      for(int â˜ƒxxx = -â˜ƒxx; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = -â˜ƒxx; â˜ƒxxxx < â˜ƒxx; ++â˜ƒxxxx) {
            for(int â˜ƒxxxxx = -1; â˜ƒxxxxx > -â˜ƒ; --â˜ƒxxxxx) {
               int â˜ƒxxxxxx = â˜ƒxxxxxxxx ? Mth.ceil((float)â˜ƒxx * (1.0F - (float)Math.pow((double)â˜ƒxxxxx, 2.0) / ((float)â˜ƒ * 8.0F))) : â˜ƒxx;
               int â˜ƒxxxxxxx = this.heightDependentRadiusSteep(â˜ƒxx, -â˜ƒxxxxx, â˜ƒ, â˜ƒx);
               if (â˜ƒxxx < â˜ƒxxxxxxx) {
                  this.generateIcebergBlock(
                     â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx
                  );
               }
            }
         }
      }

      boolean â˜ƒxxx = â˜ƒxxxxxxxx ? â˜ƒxx.nextDouble() > 0.1 : â˜ƒxx.nextDouble() > 0.7;
      if (â˜ƒxxx) {
         this.generateCutOut(â˜ƒxx, â˜ƒx, â˜ƒx, â˜ƒxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx);
      }

      return true;
   }

   private void generateCutOut(Random var1, LevelAccessor var2, int var3, int var4, BlockPos var5, boolean var6, int var7, double var8, int var10) {
      int â˜ƒ = â˜ƒ.nextBoolean() ? -1 : 1;
      int â˜ƒx = â˜ƒ.nextBoolean() ? -1 : 1;
      int â˜ƒxx = â˜ƒ.nextInt(Math.max(â˜ƒ / 2 - 2, 1));
      if (â˜ƒ.nextBoolean()) {
         â˜ƒxx = â˜ƒ / 2 + 1 - â˜ƒ.nextInt(Math.max(â˜ƒ - â˜ƒ / 2 - 1, 1));
      }

      int â˜ƒ = â˜ƒ.nextInt(Math.max(â˜ƒ / 2 - 2, 1));
      if (â˜ƒ.nextBoolean()) {
         â˜ƒ = â˜ƒ / 2 + 1 - â˜ƒ.nextInt(Math.max(â˜ƒ - â˜ƒ / 2 - 1, 1));
      }

      if (â˜ƒ) {
         â˜ƒxx = â˜ƒ = â˜ƒ.nextInt(Math.max(â˜ƒ - 5, 1));
      }

      BlockPos â˜ƒ = new BlockPos(â˜ƒ * â˜ƒxx, 0, â˜ƒx * â˜ƒ);
      double â˜ƒx = â˜ƒ ? â˜ƒ + (Math.PI / 2) : â˜ƒ.nextDouble() * 2.0 * Math.PI;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ - 3; ++â˜ƒxx) {
         int â˜ƒxxx = this.heightDependentRadiusRound(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ);
         this.carve(â˜ƒxxx, â˜ƒxx, â˜ƒ, â˜ƒ, false, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      for(int â˜ƒxx = -1; â˜ƒxx > -â˜ƒ + â˜ƒ.nextInt(5); --â˜ƒxx) {
         int â˜ƒxxx = this.heightDependentRadiusSteep(â˜ƒ, -â˜ƒxx, â˜ƒ, â˜ƒ);
         this.carve(â˜ƒxxx, â˜ƒxx, â˜ƒ, â˜ƒ, true, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void carve(int var1, int var2, BlockPos var3, LevelAccessor var4, boolean var5, double var6, BlockPos var8, int var9, int var10) {
      int â˜ƒ = â˜ƒ + 1 + â˜ƒ / 3;
      int â˜ƒx = Math.min(â˜ƒ - 3, 3) + â˜ƒ / 2 - 1;

      for(int â˜ƒxx = -â˜ƒ; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         for(int â˜ƒxxx = -â˜ƒ; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
            double â˜ƒxxxx = this.signedDistanceEllipse(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
            if (â˜ƒxxxx < 0.0) {
               BlockPos â˜ƒxxxxx = â˜ƒ.offset(â˜ƒxx, â˜ƒ, â˜ƒxxx);
               BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
               if (isIcebergState(â˜ƒxxxxxx) || â˜ƒxxxxxx.is(Blocks.SNOW_BLOCK)) {
                  if (â˜ƒ) {
                     this.setBlock(â˜ƒ, â˜ƒxxxxx, Blocks.WATER.defaultBlockState());
                  } else {
                     this.setBlock(â˜ƒ, â˜ƒxxxxx, Blocks.AIR.defaultBlockState());
                     this.removeFloatingSnowLayer(â˜ƒ, â˜ƒxxxxx);
                  }
               }
            }
         }
      }
   }

   private void removeFloatingSnowLayer(LevelAccessor var1, BlockPos var2) {
      if (â˜ƒ.getBlockState(â˜ƒ.above()).is(Blocks.SNOW)) {
         this.setBlock(â˜ƒ, â˜ƒ.above(), Blocks.AIR.defaultBlockState());
      }
   }

   private void generateIcebergBlock(
      LevelAccessor var1,
      Random var2,
      BlockPos var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      boolean var10,
      int var11,
      double var12,
      boolean var14,
      BlockState var15
   ) {
      double â˜ƒ = â˜ƒ
         ? this.signedDistanceEllipse(â˜ƒ, â˜ƒ, BlockPos.ZERO, â˜ƒ, this.getEllipseC(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ)
         : this.signedDistanceCircle(â˜ƒ, â˜ƒ, BlockPos.ZERO, â˜ƒ, â˜ƒ);
      if (â˜ƒ < 0.0) {
         BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ, â˜ƒ, â˜ƒ);
         double â˜ƒxx = â˜ƒ ? -0.5 : (double)(-6 - â˜ƒ.nextInt(3));
         if (â˜ƒ > â˜ƒxx && â˜ƒ.nextDouble() > 0.9) {
            return;
         }

         this.setIcebergBlock(â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void setIcebergBlock(BlockPos var1, LevelAccessor var2, Random var3, int var4, int var5, boolean var6, boolean var7, BlockState var8) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒ.getMaterial() == Material.AIR || â˜ƒ.is(Blocks.SNOW_BLOCK) || â˜ƒ.is(Blocks.ICE) || â˜ƒ.is(Blocks.WATER)) {
         boolean â˜ƒx = !â˜ƒ || â˜ƒ.nextDouble() > 0.05;
         int â˜ƒxx = â˜ƒ ? 3 : 2;
         if (â˜ƒ && !â˜ƒ.is(Blocks.WATER) && (double)â˜ƒ <= (double)â˜ƒ.nextInt(Math.max(1, â˜ƒ / â˜ƒxx)) + (double)â˜ƒ * 0.6 && â˜ƒx) {
            this.setBlock(â˜ƒ, â˜ƒ, Blocks.SNOW_BLOCK.defaultBlockState());
         } else {
            this.setBlock(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private int getEllipseC(int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ;
      if (â˜ƒ > 0 && â˜ƒ - â˜ƒ <= 3) {
         â˜ƒ = â˜ƒ - (4 - (â˜ƒ - â˜ƒ));
      }

      return â˜ƒ;
   }

   private double signedDistanceCircle(int var1, int var2, BlockPos var3, int var4, Random var5) {
      float â˜ƒ = 10.0F * Mth.clamp(â˜ƒ.nextFloat(), 0.2F, 0.8F) / (float)â˜ƒ;
      return (double)â˜ƒ + Math.pow((double)(â˜ƒ - â˜ƒ.getX()), 2.0) + Math.pow((double)(â˜ƒ - â˜ƒ.getZ()), 2.0) - Math.pow((double)â˜ƒ, 2.0);
   }

   private double signedDistanceEllipse(int var1, int var2, BlockPos var3, int var4, int var5, double var6) {
      return Math.pow(((double)(â˜ƒ - â˜ƒ.getX()) * Math.cos(â˜ƒ) - (double)(â˜ƒ - â˜ƒ.getZ()) * Math.sin(â˜ƒ)) / (double)â˜ƒ, 2.0)
         + Math.pow(((double)(â˜ƒ - â˜ƒ.getX()) * Math.sin(â˜ƒ) + (double)(â˜ƒ - â˜ƒ.getZ()) * Math.cos(â˜ƒ)) / (double)â˜ƒ, 2.0)
         - 1.0;
   }

   private int heightDependentRadiusRound(Random var1, int var2, int var3, int var4) {
      float â˜ƒ = 3.5F - â˜ƒ.nextFloat();
      float â˜ƒx = (1.0F - (float)Math.pow((double)â˜ƒ, 2.0) / ((float)â˜ƒ * â˜ƒ)) * (float)â˜ƒ;
      if (â˜ƒ > 15 + â˜ƒ.nextInt(5)) {
         int â˜ƒxx = â˜ƒ < 3 + â˜ƒ.nextInt(6) ? â˜ƒ / 2 : â˜ƒ;
         â˜ƒx = (1.0F - (float)â˜ƒxx / ((float)â˜ƒ * â˜ƒ * 0.4F)) * (float)â˜ƒ;
      }

      return Mth.ceil(â˜ƒx / 2.0F);
   }

   private int heightDependentRadiusEllipse(int var1, int var2, int var3) {
      float â˜ƒ = 1.0F;
      float â˜ƒx = (1.0F - (float)Math.pow((double)â˜ƒ, 2.0) / ((float)â˜ƒ * 1.0F)) * (float)â˜ƒ;
      return Mth.ceil(â˜ƒx / 2.0F);
   }

   private int heightDependentRadiusSteep(Random var1, int var2, int var3, int var4) {
      float â˜ƒ = 1.0F + â˜ƒ.nextFloat() / 2.0F;
      float â˜ƒx = (1.0F - (float)â˜ƒ / ((float)â˜ƒ * â˜ƒ)) * (float)â˜ƒ;
      return Mth.ceil(â˜ƒx / 2.0F);
   }

   private static boolean isIcebergState(BlockState var0) {
      return â˜ƒ.is(Blocks.PACKED_ICE) || â˜ƒ.is(Blocks.SNOW_BLOCK) || â˜ƒ.is(Blocks.BLUE_ICE);
   }

   private boolean belowIsAir(BlockGetter var1, BlockPos var2) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).getMaterial() == Material.AIR;
   }

   private void smooth(LevelAccessor var1, BlockPos var2, int var3, int var4, boolean var5, int var6) {
      int â˜ƒ = â˜ƒ ? â˜ƒ : â˜ƒ / 2;

      for(int â˜ƒx = -â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
         for(int â˜ƒxx = -â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx <= â˜ƒ; ++â˜ƒxxx) {
               BlockPos â˜ƒxxxx = â˜ƒ.offset(â˜ƒx, â˜ƒxxx, â˜ƒxx);
               BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
               if (isIcebergState(â˜ƒxxxxx) || â˜ƒxxxxx.is(Blocks.SNOW)) {
                  if (this.belowIsAir(â˜ƒ, â˜ƒxxxx)) {
                     this.setBlock(â˜ƒ, â˜ƒxxxx, Blocks.AIR.defaultBlockState());
                     this.setBlock(â˜ƒ, â˜ƒxxxx.above(), Blocks.AIR.defaultBlockState());
                  } else if (isIcebergState(â˜ƒxxxxx)) {
                     BlockState[] â˜ƒxxxxxx = new BlockState[]{
                        â˜ƒ.getBlockState(â˜ƒxxxx.west()),
                        â˜ƒ.getBlockState(â˜ƒxxxx.east()),
                        â˜ƒ.getBlockState(â˜ƒxxxx.north()),
                        â˜ƒ.getBlockState(â˜ƒxxxx.south())
                     };
                     int â˜ƒxxxxxxx = 0;

                     for(BlockState â˜ƒxxxxxxxx : â˜ƒxxxxxx) {
                        if (!isIcebergState(â˜ƒxxxxxxxx)) {
                           ++â˜ƒxxxxxxx;
                        }
                     }

                     if (â˜ƒxxxxxxx >= 3) {
                        this.setBlock(â˜ƒ, â˜ƒxxxx, Blocks.AIR.defaultBlockState());
                     }
                  }
               }
            }
         }
      }
   }
}
