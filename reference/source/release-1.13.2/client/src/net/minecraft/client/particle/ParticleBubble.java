package net.minecraft.client.particle;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleBubble extends Particle {
   protected ParticleBubble(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.func_70536_a(32);
      this.func_187115_a(0.02F, 0.02F);
      this.field_70544_f *= this.field_187136_p.nextFloat() * 0.6F + 0.2F;
      this.field_187129_i = ☃ * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.field_187130_j = ☃ * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.field_187131_k = ☃ * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.field_70547_e = (int)(8.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.field_187130_j += 0.002;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.85F;
      this.field_187130_j *= 0.85F;
      this.field_187131_k *= 0.85F;
      if (!this.field_187122_b
         .func_204610_c(new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h))
         .func_206884_a(FluidTags.field_206959_a)) {
         this.func_187112_i();
      }

      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleBubble(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
