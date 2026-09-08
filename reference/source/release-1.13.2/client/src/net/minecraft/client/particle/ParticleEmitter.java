package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;

public class ParticleEmitter extends Particle {
   private final Entity field_174851_a;
   private int field_174852_ax;
   private final int field_174850_ay;
   private final IParticleData field_174849_az;

   public ParticleEmitter(World var1, Entity var2, IParticleData var3) {
      this(☃, ☃, ☃, 3);
   }

   public ParticleEmitter(World var1, Entity var2, IParticleData var3, int var4) {
      super(
         ☃,
         ☃.field_70165_t,
         ☃.func_174813_aQ().field_72338_b + (double)(☃.field_70131_O / 2.0F),
         ☃.field_70161_v,
         ☃.field_70159_w,
         ☃.field_70181_x,
         ☃.field_70179_y
      );
      this.field_174851_a = ☃;
      this.field_174850_ay = ☃;
      this.field_174849_az = ☃;
      this.func_189213_a();
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   @Override
   public void func_189213_a() {
      for(int ☃ = 0; ☃ < 16; ++☃) {
         double ☃x = (double)(this.field_187136_p.nextFloat() * 2.0F - 1.0F);
         double ☃xx = (double)(this.field_187136_p.nextFloat() * 2.0F - 1.0F);
         double ☃xxx = (double)(this.field_187136_p.nextFloat() * 2.0F - 1.0F);
         if (!(☃x * ☃x + ☃xx * ☃xx + ☃xxx * ☃xxx > 1.0)) {
            double ☃xxxx = this.field_174851_a.field_70165_t + ☃x * (double)this.field_174851_a.field_70130_N / 4.0;
            double ☃xxxxx = this.field_174851_a.func_174813_aQ().field_72338_b
               + (double)(this.field_174851_a.field_70131_O / 2.0F)
               + ☃xx * (double)this.field_174851_a.field_70131_O / 4.0;
            double ☃xxxxxx = this.field_174851_a.field_70161_v + ☃xxx * (double)this.field_174851_a.field_70130_N / 4.0;
            this.field_187122_b.func_195590_a(this.field_174849_az, false, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃x, ☃xx + 0.2, ☃xxx);
         }
      }

      ++this.field_174852_ax;
      if (this.field_174852_ax >= this.field_174850_ay) {
         this.func_187112_i();
      }
   }

   @Override
   public int func_70537_b() {
      return 3;
   }
}
