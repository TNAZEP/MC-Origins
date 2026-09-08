package net.minecraft.client.particle;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.init.Particles;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleDrip extends Particle {
   private final Fluid field_204502_a;
   private int field_70564_aq;

   protected ParticleDrip(World var1, double var2, double var4, double var6, Fluid var8) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_187129_i = 0.0;
      this.field_187130_j = 0.0;
      this.field_187131_k = 0.0;
      if (☃.func_207185_a(FluidTags.field_206959_a)) {
         this.field_70552_h = 0.0F;
         this.field_70553_i = 0.0F;
         this.field_70551_j = 1.0F;
      } else {
         this.field_70552_h = 1.0F;
         this.field_70553_i = 0.0F;
         this.field_70551_j = 0.0F;
      }

      this.func_70536_a(113);
      this.func_187115_a(0.01F, 0.01F);
      this.field_70545_g = 0.06F;
      this.field_204502_a = ☃;
      this.field_70564_aq = 40;
      this.field_70547_e = (int)(64.0 / (Math.random() * 0.8 + 0.2));
      this.field_187129_i = 0.0;
      this.field_187130_j = 0.0;
      this.field_187131_k = 0.0;
   }

   @Override
   public int func_189214_a(float var1) {
      return this.field_204502_a.func_207185_a(FluidTags.field_206959_a) ? super.func_189214_a(☃) : 257;
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_204502_a.func_207185_a(FluidTags.field_206959_a)) {
         this.field_70552_h = 0.2F;
         this.field_70553_i = 0.3F;
         this.field_70551_j = 1.0F;
      } else {
         this.field_70552_h = 1.0F;
         this.field_70553_i = 16.0F / (float)(40 - this.field_70564_aq + 16);
         this.field_70551_j = 4.0F / (float)(40 - this.field_70564_aq + 8);
      }

      this.field_187130_j -= (double)this.field_70545_g;
      if (this.field_70564_aq-- > 0) {
         this.field_187129_i *= 0.02;
         this.field_187130_j *= 0.02;
         this.field_187131_k *= 0.02;
         this.func_70536_a(113);
      } else {
         this.func_70536_a(112);
      }

      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.98F;
      this.field_187130_j *= 0.98F;
      this.field_187131_k *= 0.98F;
      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }

      if (this.field_187132_l) {
         if (this.field_204502_a.func_207185_a(FluidTags.field_206959_a)) {
            this.func_187112_i();
            this.field_187122_b.func_195594_a(Particles.field_197606_Q, this.field_187126_f, this.field_187127_g, this.field_187128_h, 0.0, 0.0, 0.0);
         } else {
            this.func_70536_a(114);
         }

         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }

      BlockPos ☃ = new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h);
      IFluidState ☃x = this.field_187122_b.func_204610_c(☃);
      if (☃x.func_206886_c() == this.field_204502_a) {
         double ☃xx = (double)((float)MathHelper.func_76128_c(this.field_187127_g) + ☃x.func_206885_f());
         if (this.field_187127_g < ☃xx) {
            this.func_187112_i();
         }
      }
   }

   public static class LavaFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleDrip(☃, ☃, ☃, ☃, Fluids.field_204547_b);
      }
   }

   public static class WaterFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleDrip(☃, ☃, ☃, ☃, Fluids.field_204546_a);
      }
   }
}
