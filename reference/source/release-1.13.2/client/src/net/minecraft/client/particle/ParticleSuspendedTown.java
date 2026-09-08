package net.minecraft.client.particle;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticleSuspendedTown extends Particle {
   protected ParticleSuspendedTown(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      float ☃ = this.field_187136_p.nextFloat() * 0.1F + 0.2F;
      this.field_70552_h = ☃;
      this.field_70553_i = ☃;
      this.field_70551_j = ☃;
      this.func_70536_a(0);
      this.func_187115_a(0.02F, 0.02F);
      this.field_70544_f *= this.field_187136_p.nextFloat() * 0.6F + 0.5F;
      this.field_187129_i *= 0.02F;
      this.field_187130_j *= 0.02F;
      this.field_187131_k *= 0.02F;
      this.field_70547_e = (int)(20.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void func_187110_a(double var1, double var3, double var5) {
      this.func_187108_a(this.func_187116_l().func_72317_d(☃, ☃, ☃));
      this.func_187118_j();
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.99;
      this.field_187130_j *= 0.99;
      this.field_187131_k *= 0.99;
      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }
   }

   public static class DolphinSpeedFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleSuspendedTown(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         ☃.func_70538_b(0.3F, 0.5F, 1.0F);
         ☃.func_82338_g(1.0F - ☃.field_73012_v.nextFloat() * 0.7F);
         ☃.func_187114_a(☃.func_206254_h() / 2);
         return ☃;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleSuspendedTown(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public static class HappyVillagerFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleSuspendedTown(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         ☃.func_70536_a(82);
         ☃.func_70538_b(1.0F, 1.0F, 1.0F);
         return ☃;
      }
   }
}
