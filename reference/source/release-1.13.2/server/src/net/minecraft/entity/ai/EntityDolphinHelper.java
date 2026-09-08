package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.MathHelper;

public class EntityDolphinHelper extends EntityLookHelper {
   private final int field_205139_h;

   public EntityDolphinHelper(EntityLiving var1, int var2) {
      super(☃);
      this.field_205139_h = ☃;
   }

   @Override
   public void func_75649_a() {
      if (this.field_75655_d) {
         this.field_75655_d = false;
         double ☃ = this.field_75656_e - this.field_75659_a.field_70165_t;
         double ☃x = this.field_75653_f - (this.field_75659_a.field_70163_u + (double)this.field_75659_a.func_70047_e());
         double ☃xx = this.field_75654_g - this.field_75659_a.field_70161_v;
         double ☃xxx = (double)MathHelper.func_76133_a(☃ * ☃ + ☃xx * ☃xx);
         float ☃xxxx = (float)(MathHelper.func_181159_b(☃xx, ☃) * 180.0F / (float)Math.PI) - 90.0F + 20.0F;
         float ☃xxxxx = (float)(-(MathHelper.func_181159_b(☃x, ☃xxx) * 180.0F / (float)Math.PI)) + 10.0F;
         this.field_75659_a.field_70125_A = this.func_75652_a(this.field_75659_a.field_70125_A, ☃xxxxx, this.field_75658_c);
         this.field_75659_a.field_70759_as = this.func_75652_a(this.field_75659_a.field_70759_as, ☃xxxx, this.field_75657_b);
      } else {
         if (this.field_75659_a.func_70661_as().func_75500_f()) {
            this.field_75659_a.field_70125_A = this.func_75652_a(this.field_75659_a.field_70125_A, 0.0F, 5.0F);
         }

         this.field_75659_a.field_70759_as = this.func_75652_a(this.field_75659_a.field_70759_as, this.field_75659_a.field_70761_aq, this.field_75657_b);
      }

      float ☃ = MathHelper.func_76142_g(this.field_75659_a.field_70759_as - this.field_75659_a.field_70761_aq);
      if (☃ < (float)(-this.field_205139_h)) {
         this.field_75659_a.field_70761_aq -= 4.0F;
      } else if (☃ > (float)this.field_205139_h) {
         this.field_75659_a.field_70761_aq += 4.0F;
      }
   }
}
