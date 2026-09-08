package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CanyonWorldCarver extends WorldCarver<CanyonCarverConfiguration> {
   private static final Logger LOGGER = LogManager.getLogger();

   public CanyonWorldCarver(Codec<CanyonCarverConfiguration> var1) {
      super(â˜ƒ);
   }

   public boolean isStartChunk(CanyonCarverConfiguration var1, Random var2) {
      return â˜ƒ.nextFloat() <= â˜ƒ.probability;
   }

   public boolean carve(
      CarvingContext var1,
      CanyonCarverConfiguration var2,
      ChunkAccess var3,
      Function<BlockPos, Biome> var4,
      Random var5,
      Aquifer var6,
      ChunkPos var7,
      BitSet var8
   ) {
      int â˜ƒ = (this.getRange() * 2 - 1) * 16;
      double â˜ƒx = (double)â˜ƒ.getBlockX(â˜ƒ.nextInt(16));
      int â˜ƒxx = â˜ƒ.y.sample(â˜ƒ, â˜ƒ);
      double â˜ƒxxx = (double)â˜ƒ.getBlockZ(â˜ƒ.nextInt(16));
      float â˜ƒxxxx = â˜ƒ.nextFloat() * (float) (Math.PI * 2);
      float â˜ƒxxxxx = â˜ƒ.verticalRotation.sample(â˜ƒ);
      double â˜ƒxxxxxx = (double)â˜ƒ.yScale.sample(â˜ƒ);
      float â˜ƒxxxxxxx = â˜ƒ.shape.thickness.sample(â˜ƒ);
      int â˜ƒxxxxxxxx = (int)((float)â˜ƒ * â˜ƒ.shape.distanceFactor.sample(â˜ƒ));
      int â˜ƒxxxxxxxxx = 0;
      this.doCarve(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.nextLong(), â˜ƒ, â˜ƒx, (double)â˜ƒxx, â˜ƒxxx, â˜ƒxxxxxxx, â˜ƒxxxx, â˜ƒxxxxx, 0, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒ);
      return true;
   }

   private void doCarve(
      CarvingContext var1,
      CanyonCarverConfiguration var2,
      ChunkAccess var3,
      Function<BlockPos, Biome> var4,
      long var5,
      Aquifer var7,
      double var8,
      double var10,
      double var12,
      float var14,
      float var15,
      float var16,
      int var17,
      int var18,
      double var19,
      BitSet var21
   ) {
      Random â˜ƒ = new Random(â˜ƒ);
      float[] â˜ƒx = this.initWidthFactors(â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒxx = 0.0F;
      float â˜ƒxxx = 0.0F;

      for(int â˜ƒxxxx = â˜ƒ; â˜ƒxxxx < â˜ƒ; ++â˜ƒxxxx) {
         double â˜ƒxxxxx = 1.5 + (double)(Mth.sin((float)â˜ƒxxxx * (float) Math.PI / (float)â˜ƒ) * â˜ƒ);
         double â˜ƒxxxxxx = â˜ƒxxxxx * â˜ƒ;
         â˜ƒxxxxx *= (double)â˜ƒ.shape.horizontalRadiusFactor.sample(â˜ƒ);
         â˜ƒxxxxxx = this.updateVerticalRadius(â˜ƒ, â˜ƒ, â˜ƒxxxxxx, (float)â˜ƒ, (float)â˜ƒxxxx);
         float â˜ƒxxxxxxx = Mth.cos(â˜ƒ);
         float â˜ƒxxxxxxxx = Mth.sin(â˜ƒ);
         â˜ƒ += (double)(Mth.cos(â˜ƒ) * â˜ƒxxxxxxx);
         â˜ƒ += (double)â˜ƒxxxxxxxx;
         â˜ƒ += (double)(Mth.sin(â˜ƒ) * â˜ƒxxxxxxx);
         â˜ƒ *= 0.7F;
         â˜ƒ += â˜ƒxxx * 0.05F;
         â˜ƒ += â˜ƒxx * 0.05F;
         â˜ƒxxx *= 0.8F;
         â˜ƒxx *= 0.5F;
         â˜ƒxxx += (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * â˜ƒ.nextFloat() * 2.0F;
         â˜ƒxx += (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * â˜ƒ.nextFloat() * 4.0F;
         if (â˜ƒ.nextInt(4) != 0) {
            if (!canReach(â˜ƒ.getPos(), â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒ, â˜ƒ)) {
               return;
            }

            this.carveEllipsoid(
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒxxxxx,
               â˜ƒxxxxxx,
               â˜ƒ,
               (var2x, var3x, var5x, var7x, var9) -> this.shouldSkip(var2x, â˜ƒ, var3x, var5x, var7x, var9)
            );
         }
      }
   }

   private float[] initWidthFactors(CarvingContext var1, CanyonCarverConfiguration var2, Random var3) {
      int â˜ƒ = â˜ƒ.getGenDepth();
      float[] â˜ƒx = new float[â˜ƒ];
      float â˜ƒxx = 1.0F;

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
         if (â˜ƒxxx == 0 || â˜ƒ.nextInt(â˜ƒ.shape.widthSmoothness) == 0) {
            â˜ƒxx = 1.0F + â˜ƒ.nextFloat() * â˜ƒ.nextFloat();
         }

         â˜ƒx[â˜ƒxxx] = â˜ƒxx * â˜ƒxx;
      }

      return â˜ƒx;
   }

   private double updateVerticalRadius(CanyonCarverConfiguration var1, Random var2, double var3, float var5, float var6) {
      float â˜ƒ = 1.0F - Mth.abs(0.5F - â˜ƒ / â˜ƒ) * 2.0F;
      float â˜ƒx = â˜ƒ.shape.verticalRadiusDefaultFactor + â˜ƒ.shape.verticalRadiusCenterFactor * â˜ƒ;
      return (double)â˜ƒx * â˜ƒ * (double)Mth.randomBetween(â˜ƒ, 0.75F, 1.0F);
   }

   private boolean shouldSkip(CarvingContext var1, float[] var2, double var3, double var5, double var7, int var9) {
      int â˜ƒ = â˜ƒ - â˜ƒ.getMinGenY();
      return (â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) * (double)â˜ƒ[â˜ƒ - 1] + â˜ƒ * â˜ƒ / 6.0 >= 1.0;
   }
}
