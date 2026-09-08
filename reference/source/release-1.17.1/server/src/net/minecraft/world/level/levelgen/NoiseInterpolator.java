package net.minecraft.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;

public class NoiseInterpolator {
   private double[][] slice0;
   private double[][] slice1;
   private final int cellCountY;
   private final int cellCountZ;
   private final int cellNoiseMinY;
   private final NoiseInterpolator.NoiseColumnFiller noiseColumnFiller;
   private double noise000;
   private double noise001;
   private double noise100;
   private double noise101;
   private double noise010;
   private double noise011;
   private double noise110;
   private double noise111;
   private double valueXZ00;
   private double valueXZ10;
   private double valueXZ01;
   private double valueXZ11;
   private double valueZ0;
   private double valueZ1;
   private final int firstCellXInChunk;
   private final int firstCellZInChunk;

   public NoiseInterpolator(int var1, int var2, int var3, ChunkPos var4, int var5, NoiseInterpolator.NoiseColumnFiller var6) {
      this.cellCountY = â˜ƒ;
      this.cellCountZ = â˜ƒ;
      this.cellNoiseMinY = â˜ƒ;
      this.noiseColumnFiller = â˜ƒ;
      this.slice0 = allocateSlice(â˜ƒ, â˜ƒ);
      this.slice1 = allocateSlice(â˜ƒ, â˜ƒ);
      this.firstCellXInChunk = â˜ƒ.x * â˜ƒ;
      this.firstCellZInChunk = â˜ƒ.z * â˜ƒ;
   }

   private static double[][] allocateSlice(int var0, int var1) {
      int â˜ƒ = â˜ƒ + 1;
      int â˜ƒx = â˜ƒ + 1;
      double[][] â˜ƒxx = new double[â˜ƒ][â˜ƒx];

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
         â˜ƒxx[â˜ƒxxx] = new double[â˜ƒx];
      }

      return â˜ƒxx;
   }

   public void initializeForFirstCellX() {
      this.fillSlice(this.slice0, this.firstCellXInChunk);
   }

   public void advanceCellX(int var1) {
      this.fillSlice(this.slice1, this.firstCellXInChunk + â˜ƒ + 1);
   }

   private void fillSlice(double[][] var1, int var2) {
      for(int â˜ƒ = 0; â˜ƒ < this.cellCountZ + 1; ++â˜ƒ) {
         int â˜ƒx = this.firstCellZInChunk + â˜ƒ;
         this.noiseColumnFiller.fillNoiseColumn(â˜ƒ[â˜ƒ], â˜ƒ, â˜ƒx, this.cellNoiseMinY, this.cellCountY);
      }
   }

   public void selectCellYZ(int var1, int var2) {
      this.noise000 = this.slice0[â˜ƒ][â˜ƒ];
      this.noise001 = this.slice0[â˜ƒ + 1][â˜ƒ];
      this.noise100 = this.slice1[â˜ƒ][â˜ƒ];
      this.noise101 = this.slice1[â˜ƒ + 1][â˜ƒ];
      this.noise010 = this.slice0[â˜ƒ][â˜ƒ + 1];
      this.noise011 = this.slice0[â˜ƒ + 1][â˜ƒ + 1];
      this.noise110 = this.slice1[â˜ƒ][â˜ƒ + 1];
      this.noise111 = this.slice1[â˜ƒ + 1][â˜ƒ + 1];
   }

   public void updateForY(double var1) {
      this.valueXZ00 = Mth.lerp(â˜ƒ, this.noise000, this.noise010);
      this.valueXZ10 = Mth.lerp(â˜ƒ, this.noise100, this.noise110);
      this.valueXZ01 = Mth.lerp(â˜ƒ, this.noise001, this.noise011);
      this.valueXZ11 = Mth.lerp(â˜ƒ, this.noise101, this.noise111);
   }

   public void updateForX(double var1) {
      this.valueZ0 = Mth.lerp(â˜ƒ, this.valueXZ00, this.valueXZ10);
      this.valueZ1 = Mth.lerp(â˜ƒ, this.valueXZ01, this.valueXZ11);
   }

   public double calculateValue(double var1) {
      return Mth.lerp(â˜ƒ, this.valueZ0, this.valueZ1);
   }

   public void swapSlices() {
      double[][] â˜ƒ = this.slice0;
      this.slice0 = this.slice1;
      this.slice1 = â˜ƒ;
   }

   @FunctionalInterface
   public interface NoiseColumnFiller {
      void fillNoiseColumn(double[] var1, int var2, int var3, int var4, int var5);
   }
}
