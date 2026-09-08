package net.minecraft.world.level.levelgen;

import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.Mth;
import net.minecraft.util.ThreadingDetector;

public class SimpleRandomSource implements RandomSource {
   private static final int MODULUS_BITS = 48;
   private static final long MODULUS_MASK = 281474976710655L;
   private static final long MULTIPLIER = 25214903917L;
   private static final long INCREMENT = 11L;
   private static final float FLOAT_MULTIPLIER = 5.9604645E-8F;
   private static final double DOUBLE_MULTIPLIER = 1.110223E-16F;
   private final AtomicLong seed = new AtomicLong();
   private double nextNextGaussian;
   private boolean haveNextNextGaussian;

   public SimpleRandomSource(long var1) {
      this.setSeed(â˜ƒ);
   }

   @Override
   public void setSeed(long var1) {
      if (!this.seed.compareAndSet(this.seed.get(), (â˜ƒ ^ 25214903917L) & 281474976710655L)) {
         throw ThreadingDetector.makeThreadingException("SimpleRandomSource", null);
      }
   }

   private int next(int var1) {
      long â˜ƒ = this.seed.get();
      long â˜ƒx = â˜ƒ * 25214903917L + 11L & 281474976710655L;
      if (!this.seed.compareAndSet(â˜ƒ, â˜ƒx)) {
         throw ThreadingDetector.makeThreadingException("SimpleRandomSource", null);
      } else {
         return (int)(â˜ƒx >> 48 - â˜ƒ);
      }
   }

   @Override
   public int nextInt() {
      return this.next(32);
   }

   @Override
   public int nextInt(int var1) {
      if (â˜ƒ <= 0) {
         throw new IllegalArgumentException("Bound must be positive");
      } else if ((â˜ƒ & â˜ƒ - 1) == 0) {
         return (int)((long)â˜ƒ * (long)this.next(31) >> 31);
      } else {
         int â˜ƒ;
         int â˜ƒ;
         do {
            â˜ƒ = this.next(31);
            â˜ƒ = â˜ƒ % â˜ƒ;
         } while(â˜ƒ - â˜ƒ + (â˜ƒ - 1) < 0);

         return â˜ƒ;
      }
   }

   @Override
   public long nextLong() {
      int â˜ƒ = this.next(32);
      int â˜ƒx = this.next(32);
      long â˜ƒxx = (long)â˜ƒ << 32;
      return â˜ƒxx + (long)â˜ƒx;
   }

   @Override
   public boolean nextBoolean() {
      return this.next(1) != 0;
   }

   @Override
   public float nextFloat() {
      return (float)this.next(24) * 5.9604645E-8F;
   }

   @Override
   public double nextDouble() {
      int â˜ƒ = this.next(26);
      int â˜ƒx = this.next(27);
      long â˜ƒxx = ((long)â˜ƒ << 27) + (long)â˜ƒx;
      return (double)â˜ƒxx * 1.110223E-16F;
   }

   @Override
   public double nextGaussian() {
      if (this.haveNextNextGaussian) {
         this.haveNextNextGaussian = false;
         return this.nextNextGaussian;
      } else {
         double â˜ƒ;
         double â˜ƒ;
         double â˜ƒ;
         do {
            â˜ƒ = 2.0 * this.nextDouble() - 1.0;
            â˜ƒ = 2.0 * this.nextDouble() - 1.0;
            â˜ƒ = Mth.square(â˜ƒ) + Mth.square(â˜ƒ);
         } while(â˜ƒ >= 1.0 || â˜ƒ == 0.0);

         double â˜ƒ = Math.sqrt(-2.0 * Math.log(â˜ƒ) / â˜ƒ);
         this.nextNextGaussian = â˜ƒ * â˜ƒ;
         this.haveNextNextGaussian = true;
         return â˜ƒ * â˜ƒ;
      }
   }
}
