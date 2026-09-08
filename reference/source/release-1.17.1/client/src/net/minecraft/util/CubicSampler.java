package net.minecraft.util;

import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.world.phys.Vec3;

public class CubicSampler {
   private static final int GAUSSIAN_SAMPLE_RADIUS = 2;
   private static final int GAUSSIAN_SAMPLE_BREADTH = 6;
   private static final double[] GAUSSIAN_SAMPLE_KERNEL = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

   private CubicSampler() {
   }

   public static Vec3 gaussianSampleVec3(Vec3 var0, CubicSampler.Vec3Fetcher var1) {
      int â˜ƒ = Mth.floor(â˜ƒ.x());
      int â˜ƒx = Mth.floor(â˜ƒ.y());
      int â˜ƒxx = Mth.floor(â˜ƒ.z());
      double â˜ƒxxx = â˜ƒ.x() - (double)â˜ƒ;
      double â˜ƒxxxx = â˜ƒ.y() - (double)â˜ƒx;
      double â˜ƒxxxxx = â˜ƒ.z() - (double)â˜ƒxx;
      double â˜ƒxxxxxx = 0.0;
      Vec3 â˜ƒxxxxxxx = Vec3.ZERO;

      for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 6; ++â˜ƒxxxxxxxx) {
         double â˜ƒxxxxxxxxx = Mth.lerp(â˜ƒxxx, GAUSSIAN_SAMPLE_KERNEL[â˜ƒxxxxxxxx + 1], GAUSSIAN_SAMPLE_KERNEL[â˜ƒxxxxxxxx]);
         int â˜ƒxxxxxxxxxx = â˜ƒ - 2 + â˜ƒxxxxxxxx;

         for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < 6; ++â˜ƒxxxxxxxxxxx) {
            double â˜ƒxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxx, GAUSSIAN_SAMPLE_KERNEL[â˜ƒxxxxxxxxxxx + 1], GAUSSIAN_SAMPLE_KERNEL[â˜ƒxxxxxxxxxxx]);
            int â˜ƒxxxxxxxxxxxxx = â˜ƒx - 2 + â˜ƒxxxxxxxxxxx;

            for(int â˜ƒxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxx < 6; ++â˜ƒxxxxxxxxxxxxxx) {
               double â˜ƒxxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxx, GAUSSIAN_SAMPLE_KERNEL[â˜ƒxxxxxxxxxxxxxx + 1], GAUSSIAN_SAMPLE_KERNEL[â˜ƒxxxxxxxxxxxxxx]);
               int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxx - 2 + â˜ƒxxxxxxxxxxxxxx;
               double â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx * â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx;
               â˜ƒxxxxxx += â˜ƒxxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxx = â˜ƒxxxxxxx.add(â˜ƒ.fetch(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx).scale(â˜ƒxxxxxxxxxxxxxxxxx));
            }
         }
      }

      return â˜ƒxxxxxxx.scale(1.0 / â˜ƒxxxxxx);
   }

   @DontObfuscate
   public interface Vec3Fetcher {
      Vec3 fetch(int var1, int var2, int var3);
   }
}
