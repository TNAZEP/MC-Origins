package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;

public class CaveWorldCarver extends WorldCarver<CaveCarverConfiguration> {
   public CaveWorldCarver(Codec<CaveCarverConfiguration> var1) {
      super(â˜ƒ);
   }

   public boolean isStartChunk(CaveCarverConfiguration var1, Random var2) {
      return â˜ƒ.nextFloat() <= â˜ƒ.probability;
   }

   public boolean carve(
      CarvingContext var1,
      CaveCarverConfiguration var2,
      ChunkAccess var3,
      Function<BlockPos, Biome> var4,
      Random var5,
      Aquifer var6,
      ChunkPos var7,
      BitSet var8
   ) {
      int â˜ƒ = SectionPos.sectionToBlockCoord(this.getRange() * 2 - 1);
      int â˜ƒx = â˜ƒ.nextInt(â˜ƒ.nextInt(â˜ƒ.nextInt(this.getCaveBound()) + 1) + 1);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         double â˜ƒxxx = (double)â˜ƒ.getBlockX(â˜ƒ.nextInt(16));
         double â˜ƒxxxx = (double)â˜ƒ.y.sample(â˜ƒ, â˜ƒ);
         double â˜ƒxxxxx = (double)â˜ƒ.getBlockZ(â˜ƒ.nextInt(16));
         double â˜ƒxxxxxx = (double)â˜ƒ.horizontalRadiusMultiplier.sample(â˜ƒ);
         double â˜ƒxxxxxxx = (double)â˜ƒ.verticalRadiusMultiplier.sample(â˜ƒ);
         double â˜ƒxxxxxxxx = (double)â˜ƒ.floorLevel.sample(â˜ƒ);
         WorldCarver.CarveSkipChecker â˜ƒxxxxxxxxx = (var2x, var3x, var5x, var7x, var9x) -> shouldSkip(var3x, var5x, var7x, â˜ƒ);
         int â˜ƒxxxxxxxxxx = 1;
         if (â˜ƒ.nextInt(4) == 0) {
            double â˜ƒxxxxxxxxxxx = (double)â˜ƒ.yScale.sample(â˜ƒ);
            float â˜ƒxxxxxxxxxxxx = 1.0F + â˜ƒ.nextFloat() * 6.0F;
            this.createRoom(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.nextLong(), â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxx);
            â˜ƒxxxxxxxxxx += â˜ƒ.nextInt(4);
         }

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxxxxxxxxxx; ++â˜ƒxxx) {
            float â˜ƒxxxx = â˜ƒ.nextFloat() * (float) (Math.PI * 2);
            float â˜ƒxxxxx = (â˜ƒ.nextFloat() - 0.5F) / 4.0F;
            float â˜ƒxxxxxx = this.getThickness(â˜ƒ);
            int â˜ƒxxxxxxx = â˜ƒ - â˜ƒ.nextInt(â˜ƒ / 4);
            int â˜ƒxxxxxxxx = 0;
            this.createTunnel(
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ.nextLong(),
               â˜ƒ,
               â˜ƒxxx,
               â˜ƒxxxx,
               â˜ƒxxxxx,
               â˜ƒxxxxxx,
               â˜ƒxxxxxxx,
               â˜ƒxxxxxx,
               â˜ƒxxxx,
               â˜ƒxxxxx,
               0,
               â˜ƒxxxxxxx,
               this.getYScale(),
               â˜ƒ,
               â˜ƒxxxxxxxxx
            );
         }
      }

      return true;
   }

   protected int getCaveBound() {
      return 15;
   }

   protected float getThickness(Random var1) {
      float â˜ƒ = â˜ƒ.nextFloat() * 2.0F + â˜ƒ.nextFloat();
      if (â˜ƒ.nextInt(10) == 0) {
         â˜ƒ *= â˜ƒ.nextFloat() * â˜ƒ.nextFloat() * 3.0F + 1.0F;
      }

      return â˜ƒ;
   }

   protected double getYScale() {
      return 1.0;
   }

   protected void createRoom(
      CarvingContext var1,
      CaveCarverConfiguration var2,
      ChunkAccess var3,
      Function<BlockPos, Biome> var4,
      long var5,
      Aquifer var7,
      double var8,
      double var10,
      double var12,
      float var14,
      double var15,
      BitSet var17,
      WorldCarver.CarveSkipChecker var18
   ) {
      double â˜ƒ = 1.5 + (double)(Mth.sin((float) (Math.PI / 2)) * â˜ƒ);
      double â˜ƒx = â˜ƒ * â˜ƒ;
      this.carveEllipsoid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1.0, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
   }

   protected void createTunnel(
      CarvingContext var1,
      CaveCarverConfiguration var2,
      ChunkAccess var3,
      Function<BlockPos, Biome> var4,
      long var5,
      Aquifer var7,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      float var18,
      float var19,
      float var20,
      int var21,
      int var22,
      double var23,
      BitSet var25,
      WorldCarver.CarveSkipChecker var26
   ) {
      Random â˜ƒ = new Random(â˜ƒ);
      int â˜ƒx = â˜ƒ.nextInt(â˜ƒ / 2) + â˜ƒ / 4;
      boolean â˜ƒxx = â˜ƒ.nextInt(6) == 0;
      float â˜ƒxxx = 0.0F;
      float â˜ƒxxxx = 0.0F;

      for(int â˜ƒxxxxx = â˜ƒ; â˜ƒxxxxx < â˜ƒ; ++â˜ƒxxxxx) {
         double â˜ƒxxxxxx = 1.5 + (double)(Mth.sin((float) Math.PI * (float)â˜ƒxxxxx / (float)â˜ƒ) * â˜ƒ);
         double â˜ƒxxxxxxx = â˜ƒxxxxxx * â˜ƒ;
         float â˜ƒxxxxxxxx = Mth.cos(â˜ƒ);
         â˜ƒ += (double)(Mth.cos(â˜ƒ) * â˜ƒxxxxxxxx);
         â˜ƒ += (double)Mth.sin(â˜ƒ);
         â˜ƒ += (double)(Mth.sin(â˜ƒ) * â˜ƒxxxxxxxx);
         â˜ƒ *= â˜ƒxx ? 0.92F : 0.7F;
         â˜ƒ += â˜ƒxxxx * 0.1F;
         â˜ƒ += â˜ƒxxx * 0.1F;
         â˜ƒxxxx *= 0.9F;
         â˜ƒxxx *= 0.75F;
         â˜ƒxxxx += (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * â˜ƒ.nextFloat() * 2.0F;
         â˜ƒxxx += (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * â˜ƒ.nextFloat() * 4.0F;
         if (â˜ƒxxxxx == â˜ƒx && â˜ƒ > 1.0F) {
            this.createTunnel(
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ.nextLong(),
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ.nextFloat() * 0.5F + 0.5F,
               â˜ƒ - (float) (Math.PI / 2),
               â˜ƒ / 3.0F,
               â˜ƒxxxxx,
               â˜ƒ,
               1.0,
               â˜ƒ,
               â˜ƒ
            );
            this.createTunnel(
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ.nextLong(),
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ.nextFloat() * 0.5F + 0.5F,
               â˜ƒ + (float) (Math.PI / 2),
               â˜ƒ / 3.0F,
               â˜ƒxxxxx,
               â˜ƒ,
               1.0,
               â˜ƒ,
               â˜ƒ
            );
            return;
         }

         if (â˜ƒ.nextInt(4) != 0) {
            if (!canReach(â˜ƒ.getPos(), â˜ƒ, â˜ƒ, â˜ƒxxxxx, â˜ƒ, â˜ƒ)) {
               return;
            }

            this.carveEllipsoid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxx * â˜ƒ, â˜ƒxxxxxxx * â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private static boolean shouldSkip(double var0, double var2, double var4, double var6) {
      if (â˜ƒ <= â˜ƒ) {
         return true;
      } else {
         return â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ >= 1.0;
      }
   }
}
