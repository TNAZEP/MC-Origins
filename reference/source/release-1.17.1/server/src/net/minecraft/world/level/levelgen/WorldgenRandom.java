package net.minecraft.world.level.levelgen;

import java.util.Random;

public class WorldgenRandom extends Random implements RandomSource {
   private int count;

   public WorldgenRandom() {
   }

   public WorldgenRandom(long var1) {
      super(â˜ƒ);
   }

   public int getCount() {
      return this.count;
   }

   public int next(int var1) {
      ++this.count;
      return super.next(â˜ƒ);
   }

   public long setBaseChunkSeed(int var1, int var2) {
      long â˜ƒ = (long)â˜ƒ * 341873128712L + (long)â˜ƒ * 132897987541L;
      this.setSeed(â˜ƒ);
      return â˜ƒ;
   }

   public long setDecorationSeed(long var1, int var3, int var4) {
      this.setSeed(â˜ƒ);
      long â˜ƒ = this.nextLong() | 1L;
      long â˜ƒx = this.nextLong() | 1L;
      long â˜ƒxx = (long)â˜ƒ * â˜ƒ + (long)â˜ƒ * â˜ƒx ^ â˜ƒ;
      this.setSeed(â˜ƒxx);
      return â˜ƒxx;
   }

   public long setFeatureSeed(long var1, int var3, int var4) {
      long â˜ƒ = â˜ƒ + (long)â˜ƒ + (long)(10000 * â˜ƒ);
      this.setSeed(â˜ƒ);
      return â˜ƒ;
   }

   public long setLargeFeatureSeed(long var1, int var3, int var4) {
      this.setSeed(â˜ƒ);
      long â˜ƒ = this.nextLong();
      long â˜ƒx = this.nextLong();
      long â˜ƒxx = (long)â˜ƒ * â˜ƒ ^ (long)â˜ƒ * â˜ƒx ^ â˜ƒ;
      this.setSeed(â˜ƒxx);
      return â˜ƒxx;
   }

   public long setBaseStoneSeed(long var1, int var3, int var4, int var5) {
      this.setSeed(â˜ƒ);
      long â˜ƒ = this.nextLong();
      long â˜ƒx = this.nextLong();
      long â˜ƒxx = this.nextLong();
      long â˜ƒxxx = (long)â˜ƒ * â˜ƒ ^ (long)â˜ƒ * â˜ƒx ^ (long)â˜ƒ * â˜ƒxx ^ â˜ƒ;
      this.setSeed(â˜ƒxxx);
      return â˜ƒxxx;
   }

   public long setLargeFeatureWithSalt(long var1, int var3, int var4, int var5) {
      long â˜ƒ = (long)â˜ƒ * 341873128712L + (long)â˜ƒ * 132897987541L + â˜ƒ + (long)â˜ƒ;
      this.setSeed(â˜ƒ);
      return â˜ƒ;
   }

   public static Random seedSlimeChunk(int var0, int var1, long var2, long var4) {
      return new Random(â˜ƒ + (long)(â˜ƒ * â˜ƒ * 4987142) + (long)(â˜ƒ * 5947611) + (long)(â˜ƒ * â˜ƒ) * 4392871L + (long)(â˜ƒ * 389711) ^ â˜ƒ);
   }
}
