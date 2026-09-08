package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class EntityLookHelper {
   protected final EntityLiving field_75659_a;
   protected float field_75657_b;
   protected float field_75658_c;
   protected boolean field_75655_d;
   protected double field_75656_e;
   protected double field_75653_f;
   protected double field_75654_g;

   public EntityLookHelper(EntityLiving var1) {
      this.field_75659_a = ☃;
   }

   public void func_75651_a(Entity var1, float var2, float var3) {
      this.field_75656_e = ☃.field_70165_t;
      if (☃ instanceof EntityLivingBase) {
         this.field_75653_f = ☃.field_70163_u + (double)☃.func_70047_e();
      } else {
         this.field_75653_f = (☃.func_174813_aQ().field_72338_b + ☃.func_174813_aQ().field_72337_e) / 2.0;
      }

      this.field_75654_g = ☃.field_70161_v;
      this.field_75657_b = ☃;
      this.field_75658_c = ☃;
      this.field_75655_d = true;
   }

   public void func_75650_a(double var1, double var3, double var5, float var7, float var8) {
      this.field_75656_e = ☃;
      this.field_75653_f = ☃;
      this.field_75654_g = ☃;
      this.field_75657_b = ☃;
      this.field_75658_c = ☃;
      this.field_75655_d = true;
   }

   public void func_75649_a() {
      this.field_75659_a.field_70125_A = 0.0F;
      if (this.field_75655_d) {
         this.field_75655_d = false;
         double ☃ = this.field_75656_e - this.field_75659_a.field_70165_t;
         double ☃x = this.field_75653_f - (this.field_75659_a.field_70163_u + (double)this.field_75659_a.func_70047_e());
         double ☃xx = this.field_75654_g - this.field_75659_a.field_70161_v;
         double ☃xxx = (double)MathHelper.func_76133_a(☃ * ☃ + ☃xx * ☃xx);
         float ☃xxxx = (float)(MathHelper.func_181159_b(☃xx, ☃) * 180.0F / (float)Math.PI) - 90.0F;
         float ☃xxxxx = (float)(-(MathHelper.func_181159_b(☃x, ☃xxx) * 180.0F / (float)Math.PI));
         this.field_75659_a.field_70125_A = this.func_75652_a(this.field_75659_a.field_70125_A, ☃xxxxx, this.field_75658_c);
         this.field_75659_a.field_70759_as = this.func_75652_a(this.field_75659_a.field_70759_as, ☃xxxx, this.field_75657_b);
      } else {
         this.field_75659_a.field_70759_as = this.func_75652_a(this.field_75659_a.field_70759_as, this.field_75659_a.field_70761_aq, 10.0F);
      }

      float ☃ = MathHelper.func_76142_g(this.field_75659_a.field_70759_as - this.field_75659_a.field_70761_aq);
      if (!this.field_75659_a.func_70661_as().func_75500_f()) {
         if (☃ < -75.0F) {
            this.field_75659_a.field_70759_as = this.field_75659_a.field_70761_aq - 75.0F;
         }

         if (☃ > 75.0F) {
            this.field_75659_a.field_70759_as = this.field_75659_a.field_70761_aq + 75.0F;
         }
      }
   }

   protected float func_75652_a(float var1, float var2, float var3) {
      float ☃ = MathHelper.func_76142_g(☃ - ☃);
      if (☃ > ☃) {
         ☃ = ☃;
      }

      if (☃ < -☃) {
         ☃ = -☃;
      }

      return ☃ + ☃;
   }

   public boolean func_180424_b() {
      return this.field_75655_d;
   }

   public double func_180423_e() {
      return this.field_75656_e;
   }

   public double func_180422_f() {
      return this.field_75653_f;
   }

   public double func_180421_g() {
      return this.field_75654_g;
   }
}
