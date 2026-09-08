package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;

public class Barrier extends Particle {
   protected Barrier(World var1, double var2, double var4, double var6, IItemProvider var8) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.func_187117_a(Minecraft.func_71410_x().func_175599_af().func_175037_a().func_199934_a(☃));
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.field_187129_i = 0.0;
      this.field_187130_j = 0.0;
      this.field_187131_k = 0.0;
      this.field_70545_g = 0.0F;
      this.field_70547_e = 80;
      this.field_190017_n = false;
   }

   @Override
   public int func_70537_b() {
      return 1;
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = this.field_187119_C.func_94209_e();
      float ☃x = this.field_187119_C.func_94212_f();
      float ☃xx = this.field_187119_C.func_94206_g();
      float ☃xxx = this.field_187119_C.func_94210_h();
      float ☃xxxx = 0.5F;
      float ☃xxxxx = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)☃ - field_70556_an);
      float ☃xxxxxx = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)☃ - field_70554_ao);
      float ☃xxxxxxx = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)☃ - field_70555_ap);
      int ☃xxxxxxxx = this.func_189214_a(☃);
      int ☃xxxxxxxxx = ☃xxxxxxxx >> 16 & 65535;
      int ☃xxxxxxxxxx = ☃xxxxxxxx & 65535;
      ☃.func_181662_b((double)(☃xxxxx - ☃ * 0.5F - ☃ * 0.5F), (double)(☃xxxxxx - ☃ * 0.5F), (double)(☃xxxxxxx - ☃ * 0.5F - ☃ * 0.5F))
         .func_187315_a((double)☃x, (double)☃xxx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
         .func_187314_a(☃xxxxxxxxx, ☃xxxxxxxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)(☃xxxxx - ☃ * 0.5F + ☃ * 0.5F), (double)(☃xxxxxx + ☃ * 0.5F), (double)(☃xxxxxxx - ☃ * 0.5F + ☃ * 0.5F))
         .func_187315_a((double)☃x, (double)☃xx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
         .func_187314_a(☃xxxxxxxxx, ☃xxxxxxxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)(☃xxxxx + ☃ * 0.5F + ☃ * 0.5F), (double)(☃xxxxxx + ☃ * 0.5F), (double)(☃xxxxxxx + ☃ * 0.5F + ☃ * 0.5F))
         .func_187315_a((double)☃, (double)☃xx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
         .func_187314_a(☃xxxxxxxxx, ☃xxxxxxxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)(☃xxxxx + ☃ * 0.5F - ☃ * 0.5F), (double)(☃xxxxxx - ☃ * 0.5F), (double)(☃xxxxxxx + ☃ * 0.5F - ☃ * 0.5F))
         .func_187315_a((double)☃, (double)☃xxx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
         .func_187314_a(☃xxxxxxxxx, ☃xxxxxxxxxx)
         .func_181675_d();
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new Barrier(☃, ☃, ☃, ☃, Blocks.field_180401_cv.func_199767_j());
      }
   }
}
