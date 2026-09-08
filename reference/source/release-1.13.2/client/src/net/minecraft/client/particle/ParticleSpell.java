package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticleSpell extends Particle {
   private static final Random field_174848_a = new Random();
   private int field_70590_a = 128;

   protected ParticleSpell(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, 0.5 - field_174848_a.nextDouble(), ☃, 0.5 - field_174848_a.nextDouble());
      this.field_187130_j *= 0.2F;
      if (☃ == 0.0 && ☃ == 0.0) {
         this.field_187129_i *= 0.1F;
         this.field_187131_k *= 0.1F;
      }

      this.field_70544_f *= 0.75F;
      this.field_70547_e = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.field_190017_n = false;
   }

   @Override
   public boolean func_187111_c() {
      return true;
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.func_70536_a(this.field_70590_a + 7 - this.field_70546_d * 8 / this.field_70547_e);
      this.field_187130_j += 0.004;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      if (this.field_187127_g == this.field_187124_d) {
         this.field_187129_i *= 1.1;
         this.field_187131_k *= 1.1;
      }

      this.field_187129_i *= 0.96F;
      this.field_187130_j *= 0.96F;
      this.field_187131_k *= 0.96F;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }
   }

   public void func_70589_b(int var1) {
      this.field_70590_a = ☃;
   }

   public static class AmbientMobFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleSpell(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         ☃.func_82338_g(0.15F);
         ☃.func_70538_b((float)☃, (float)☃, (float)☃);
         return ☃;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleSpell(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public static class InstantFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleSpell(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         ((ParticleSpell)☃).func_70589_b(144);
         return ☃;
      }
   }

   public static class MobFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleSpell(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         ☃.func_70538_b((float)☃, (float)☃, (float)☃);
         return ☃;
      }
   }

   public static class WitchFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleSpell(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         ((ParticleSpell)☃).func_70589_b(144);
         float ☃x = ☃.field_73012_v.nextFloat() * 0.5F + 0.35F;
         ☃.func_70538_b(1.0F * ☃x, 0.0F * ☃x, 1.0F * ☃x);
         return ☃;
      }
   }
}
