package net.minecraft.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleUtils {
   public static void spawnParticlesOnBlockFaces(Level var0, BlockPos var1, ParticleOptions var2, UniformInt var3) {
      for(Direction â˜ƒ : Direction.values()) {
         int â˜ƒx = â˜ƒ.sample(â˜ƒ.random);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
            spawnParticleOnFace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   public static void spawnParticlesAlongAxis(Direction.Axis var0, Level var1, BlockPos var2, double var3, ParticleOptions var5, UniformInt var6) {
      Vec3 â˜ƒ = Vec3.atCenterOf(â˜ƒ);
      boolean â˜ƒx = â˜ƒ == Direction.Axis.X;
      boolean â˜ƒxx = â˜ƒ == Direction.Axis.Y;
      boolean â˜ƒxxx = â˜ƒ == Direction.Axis.Z;
      int â˜ƒxxxx = â˜ƒ.sample(â˜ƒ.random);

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx; ++â˜ƒxxxxx) {
         double â˜ƒxxxxxx = â˜ƒ.x + Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) * (â˜ƒx ? 0.5 : â˜ƒ);
         double â˜ƒxxxxxxx = â˜ƒ.y + Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) * (â˜ƒxx ? 0.5 : â˜ƒ);
         double â˜ƒxxxxxxxx = â˜ƒ.z + Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) * (â˜ƒxxx ? 0.5 : â˜ƒ);
         double â˜ƒxxxxxxxxx = â˜ƒx ? Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) : 0.0;
         double â˜ƒxxxxxxxxxx = â˜ƒxx ? Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) : 0.0;
         double â˜ƒxxxxxxxxxxx = â˜ƒxxx ? Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) : 0.0;
         â˜ƒ.addParticle(â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx);
      }
   }

   public static void spawnParticleOnFace(Level var0, BlockPos var1, Direction var2, ParticleOptions var3) {
      Vec3 â˜ƒ = Vec3.atCenterOf(â˜ƒ);
      int â˜ƒx = â˜ƒ.getStepX();
      int â˜ƒxx = â˜ƒ.getStepY();
      int â˜ƒxxx = â˜ƒ.getStepZ();
      double â˜ƒxxxx = â˜ƒ.x + (â˜ƒx == 0 ? Mth.nextDouble(â˜ƒ.random, -0.5, 0.5) : (double)â˜ƒx * 0.55);
      double â˜ƒxxxxx = â˜ƒ.y + (â˜ƒxx == 0 ? Mth.nextDouble(â˜ƒ.random, -0.5, 0.5) : (double)â˜ƒxx * 0.55);
      double â˜ƒxxxxxx = â˜ƒ.z + (â˜ƒxxx == 0 ? Mth.nextDouble(â˜ƒ.random, -0.5, 0.5) : (double)â˜ƒxxx * 0.55);
      double â˜ƒxxxxxxx = â˜ƒx == 0 ? Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) : 0.0;
      double â˜ƒxxxxxxxx = â˜ƒxx == 0 ? Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) : 0.0;
      double â˜ƒxxxxxxxxx = â˜ƒxxx == 0 ? Mth.nextDouble(â˜ƒ.random, -1.0, 1.0) : 0.0;
      â˜ƒ.addParticle(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
   }
}
