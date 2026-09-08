package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleBubblePop extends Particle {
   protected ParticleBubblePop(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.func_70536_a(256);
      this.field_70547_e = 4;
      this.field_70545_g = 0.008F;
      this.field_187129_i = ☃;
      this.field_187130_j = ☃;
      this.field_187131_k = ☃;
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.field_187130_j -= (double)this.field_70545_g;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      } else {
         int ☃ = this.field_70546_d * 5 / this.field_70547_e;
         if (☃ <= 4) {
            this.func_70536_a(256 + ☃);
         }
      }
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = (float)this.field_94054_b / 32.0F;
      float ☃x = ☃ + 0.0624375F;
      float ☃xx = (float)this.field_94055_c / 32.0F;
      float ☃xxx = ☃xx + 0.0624375F;
      float ☃xxxx = 0.1F * this.field_70544_f;
      if (this.field_187119_C != null) {
         ☃ = this.field_187119_C.func_94209_e();
         ☃x = this.field_187119_C.func_94212_f();
         ☃xx = this.field_187119_C.func_94206_g();
         ☃xxx = this.field_187119_C.func_94210_h();
      }

      float ☃ = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)☃ - field_70556_an);
      float ☃x = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)☃ - field_70554_ao);
      float ☃xx = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)☃ - field_70555_ap);
      int ☃xxx = this.func_189214_a(☃);
      int ☃xxxx = ☃xxx >> 16 & 65535;
      int ☃xxxxx = ☃xxx & 65535;
      Vec3d[] ☃xxxxxx = new Vec3d[]{
         new Vec3d((double)(-☃ * ☃xxxx - ☃ * ☃xxxx), (double)(-☃ * ☃xxxx), (double)(-☃ * ☃xxxx - ☃ * ☃xxxx)),
         new Vec3d((double)(-☃ * ☃xxxx + ☃ * ☃xxxx), (double)(☃ * ☃xxxx), (double)(-☃ * ☃xxxx + ☃ * ☃xxxx)),
         new Vec3d((double)(☃ * ☃xxxx + ☃ * ☃xxxx), (double)(☃ * ☃xxxx), (double)(☃ * ☃xxxx + ☃ * ☃xxxx)),
         new Vec3d((double)(☃ * ☃xxxx - ☃ * ☃xxxx), (double)(-☃ * ☃xxxx), (double)(☃ * ☃xxxx - ☃ * ☃xxxx))
      };
      ☃.func_181662_b((double)☃ + ☃xxxxxx[0].field_72450_a, (double)☃x + ☃xxxxxx[0].field_72448_b, (double)☃xx + ☃xxxxxx[0].field_72449_c)
         .func_187315_a((double)☃x, (double)☃xxx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)☃ + ☃xxxxxx[1].field_72450_a, (double)☃x + ☃xxxxxx[1].field_72448_b, (double)☃xx + ☃xxxxxx[1].field_72449_c)
         .func_187315_a((double)☃x, (double)☃xx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)☃ + ☃xxxxxx[2].field_72450_a, (double)☃x + ☃xxxxxx[2].field_72448_b, (double)☃xx + ☃xxxxxx[2].field_72449_c)
         .func_187315_a((double)☃, (double)☃xx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)☃ + ☃xxxxxx[3].field_72450_a, (double)☃x + ☃xxxxxx[3].field_72448_b, (double)☃xx + ☃xxxxxx[3].field_72449_c)
         .func_187315_a((double)☃, (double)☃xxx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
   }

   @Override
   public void func_70536_a(int var1) {
      if (this.func_70537_b() != 0) {
         throw new RuntimeException("Invalid call to Particle.setMiscTex");
      } else {
         this.field_94054_b = 2 * ☃ % 16;
         this.field_94055_c = ☃ / 16;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      @Nullable
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleBubblePop(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
