package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class EntityAILeapAtTarget extends EntityAIBase {
   private final EntityLiving field_75328_a;
   private EntityLivingBase field_75326_b;
   private final float field_75327_c;

   public EntityAILeapAtTarget(EntityLiving var1, float var2) {
      this.field_75328_a = ☃;
      this.field_75327_c = ☃;
      this.func_75248_a(5);
   }

   @Override
   public boolean func_75250_a() {
      this.field_75326_b = this.field_75328_a.func_70638_az();
      if (this.field_75326_b == null) {
         return false;
      } else {
         double ☃ = this.field_75328_a.func_70068_e(this.field_75326_b);
         if (☃ < 4.0 || ☃ > 16.0) {
            return false;
         } else if (!this.field_75328_a.field_70122_E) {
            return false;
         } else {
            return this.field_75328_a.func_70681_au().nextInt(5) == 0;
         }
      }
   }

   @Override
   public boolean func_75253_b() {
      return !this.field_75328_a.field_70122_E;
   }

   @Override
   public void func_75249_e() {
      double ☃ = this.field_75326_b.field_70165_t - this.field_75328_a.field_70165_t;
      double ☃x = this.field_75326_b.field_70161_v - this.field_75328_a.field_70161_v;
      float ☃xx = MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x);
      if ((double)☃xx >= 1.0E-4) {
         this.field_75328_a.field_70159_w += ☃ / (double)☃xx * 0.5 * 0.8F + this.field_75328_a.field_70159_w * 0.2F;
         this.field_75328_a.field_70179_y += ☃x / (double)☃xx * 0.5 * 0.8F + this.field_75328_a.field_70179_y * 0.2F;
      }

      this.field_75328_a.field_70181_x = (double)this.field_75327_c;
   }
}
