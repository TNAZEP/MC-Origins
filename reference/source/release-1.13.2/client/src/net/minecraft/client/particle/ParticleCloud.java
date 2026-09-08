package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleCloud extends Particle {
   private final float field_70569_a;

   protected ParticleCloud(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      float ☃ = 2.5F;
      this.field_187129_i *= 0.1F;
      this.field_187130_j *= 0.1F;
      this.field_187131_k *= 0.1F;
      this.field_187129_i += ☃;
      this.field_187130_j += ☃;
      this.field_187131_k += ☃;
      float ☃x = 1.0F - (float)(Math.random() * 0.3F);
      this.field_70552_h = ☃x;
      this.field_70553_i = ☃x;
      this.field_70551_j = ☃x;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= 2.5F;
      this.field_70569_a = this.field_70544_f;
      this.field_70547_e = (int)(8.0 / (Math.random() * 0.8 + 0.3));
      this.field_70547_e = (int)((float)this.field_70547_e * 2.5F);
      this.field_70547_e = Math.max(this.field_70547_e, 1);
      this.field_190017_n = false;
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_70546_d + ☃) / (float)this.field_70547_e * 32.0F;
      ☃ = MathHelper.func_76131_a(☃, 0.0F, 1.0F);
      this.field_70544_f = this.field_70569_a * ☃;
      super.func_180434_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.func_70536_a(7 - this.field_70546_d * 8 / this.field_70547_e);
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.96F;
      this.field_187130_j *= 0.96F;
      this.field_187131_k *= 0.96F;
      EntityPlayer ☃ = this.field_187122_b.func_184137_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, 2.0, false);
      if (☃ != null) {
         AxisAlignedBB ☃x = ☃.func_174813_aQ();
         if (this.field_187127_g > ☃x.field_72338_b) {
            this.field_187127_g += (☃x.field_72338_b - this.field_187127_g) * 0.2;
            this.field_187130_j += (☃.field_70181_x - this.field_187130_j) * 0.2;
            this.func_187109_b(this.field_187126_f, this.field_187127_g, this.field_187128_h);
         }
      }

      if (this.field_187132_l) {
         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleCloud(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
