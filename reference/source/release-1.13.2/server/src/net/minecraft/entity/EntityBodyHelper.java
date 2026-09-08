package net.minecraft.entity;

import net.minecraft.util.math.MathHelper;

public class EntityBodyHelper {
   private final EntityLivingBase field_75668_a;
   private int field_75666_b;
   private float field_75667_c;

   public EntityBodyHelper(EntityLivingBase var1) {
      this.field_75668_a = ☃;
   }

   public void func_75664_a() {
      double ☃ = this.field_75668_a.field_70165_t - this.field_75668_a.field_70169_q;
      double ☃x = this.field_75668_a.field_70161_v - this.field_75668_a.field_70166_s;
      if (☃ * ☃ + ☃x * ☃x > 2.5000003E-7F) {
         this.field_75668_a.field_70761_aq = this.field_75668_a.field_70177_z;
         this.field_75668_a.field_70759_as = this.func_75665_a(this.field_75668_a.field_70761_aq, this.field_75668_a.field_70759_as, 75.0F);
         this.field_75667_c = this.field_75668_a.field_70759_as;
         this.field_75666_b = 0;
      } else {
         if (this.field_75668_a.func_184188_bt().isEmpty() || !(this.field_75668_a.func_184188_bt().get(0) instanceof EntityLiving)) {
            float ☃ = 75.0F;
            if (Math.abs(this.field_75668_a.field_70759_as - this.field_75667_c) > 15.0F) {
               this.field_75666_b = 0;
               this.field_75667_c = this.field_75668_a.field_70759_as;
            } else {
               ++this.field_75666_b;
               int ☃ = 10;
               if (this.field_75666_b > 10) {
                  ☃ = Math.max(1.0F - (float)(this.field_75666_b - 10) / 10.0F, 0.0F) * 75.0F;
               }
            }

            this.field_75668_a.field_70761_aq = this.func_75665_a(this.field_75668_a.field_70759_as, this.field_75668_a.field_70761_aq, ☃);
         }
      }
   }

   private float func_75665_a(float var1, float var2, float var3) {
      float ☃ = MathHelper.func_76142_g(☃ - ☃);
      if (☃ < -☃) {
         ☃ = -☃;
      }

      if (☃ >= ☃) {
         ☃ = ☃;
      }

      return ☃ - ☃;
   }
}
