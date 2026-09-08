package net.minecraft.world.level.levelgen;

public interface RandomSource {
   void setSeed(long var1);

   int nextInt();

   int nextInt(int var1);

   long nextLong();

   boolean nextBoolean();

   float nextFloat();

   double nextDouble();

   double nextGaussian();

   default void consumeCount(int var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         this.nextInt();
      }
   }
}
