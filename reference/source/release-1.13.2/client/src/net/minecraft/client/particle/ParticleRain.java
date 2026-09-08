package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleRain extends Particle {
   protected ParticleRain(World var1, double var2, double var4, double var6) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_187129_i *= 0.3F;
      this.field_187130_j = Math.random() * 0.2F + 0.1F;
      this.field_187131_k *= 0.3F;
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.func_70536_a(19 + this.field_187136_p.nextInt(4));
      this.func_187115_a(0.01F, 0.01F);
      this.field_70545_g = 0.06F;
      this.field_70547_e = (int)(8.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.field_187130_j -= (double)this.field_70545_g;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.98F;
      this.field_187130_j *= 0.98F;
      this.field_187131_k *= 0.98F;
      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }

      if (this.field_187132_l) {
         if (Math.random() < 0.5) {
            this.func_187112_i();
         }

         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }

      BlockPos ☃ = new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h);
      IBlockState ☃x = this.field_187122_b.func_180495_p(☃);
      Material ☃xx = ☃x.func_185904_a();
      IFluidState ☃xxx = this.field_187122_b.func_204610_c(☃);
      if (!☃xxx.func_206888_e() || ☃xx.func_76220_a()) {
         double ☃xxxx;
         if (☃xxx.func_206885_f() > 0.0F) {
            ☃xxxx = (double)☃xxx.func_206885_f();
         } else {
            ☃xxxx = ☃x.func_196952_d(this.field_187122_b, ☃)
               .func_197760_b(EnumFacing.Axis.Y, this.field_187126_f - Math.floor(this.field_187126_f), this.field_187128_h - Math.floor(this.field_187128_h));
         }

         double ☃xxxx = (double)MathHelper.func_76128_c(this.field_187127_g) + ☃xxxx;
         if (this.field_187127_g < ☃xxxx) {
            this.func_187112_i();
         }
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleRain(☃, ☃, ☃, ☃);
      }
   }
}
