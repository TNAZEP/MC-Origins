package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.math.Vec3d;

public class EntityAILlamaFollowCaravan extends EntityAIBase {
   public EntityLlama field_190859_a;
   private double field_190860_b;
   private int field_190861_c;

   public EntityAILlamaFollowCaravan(EntityLlama var1, double var2) {
      this.field_190859_a = ☃;
      this.field_190860_b = ☃;
      this.func_75248_a(1);
   }

   @Override
   public boolean func_75250_a() {
      if (!this.field_190859_a.func_110167_bD() && !this.field_190859_a.func_190718_dR()) {
         List<EntityLlama> ☃ = this.field_190859_a
            .field_70170_p
            .func_72872_a(this.field_190859_a.getClass(), this.field_190859_a.func_174813_aQ().func_72314_b(9.0, 4.0, 9.0));
         EntityLlama ☃x = null;
         double ☃xx = Double.MAX_VALUE;

         for(EntityLlama ☃xxx : ☃) {
            if (☃xxx.func_190718_dR() && !☃xxx.func_190712_dQ()) {
               double ☃xxxx = this.field_190859_a.func_70068_e(☃xxx);
               if (!(☃xxxx > ☃xx)) {
                  ☃xx = ☃xxxx;
                  ☃x = ☃xxx;
               }
            }
         }

         if (☃x == null) {
            for(EntityLlama ☃xxx : ☃) {
               if (☃xxx.func_110167_bD() && !☃xxx.func_190712_dQ()) {
                  double ☃xxxx = this.field_190859_a.func_70068_e(☃xxx);
                  if (!(☃xxxx > ☃xx)) {
                     ☃xx = ☃xxxx;
                     ☃x = ☃xxx;
                  }
               }
            }
         }

         if (☃x == null) {
            return false;
         } else if (☃xx < 4.0) {
            return false;
         } else if (!☃x.func_110167_bD() && !this.func_190858_a(☃x, 1)) {
            return false;
         } else {
            this.field_190859_a.func_190715_a(☃x);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean func_75253_b() {
      if (this.field_190859_a.func_190718_dR() && this.field_190859_a.func_190716_dS().func_70089_S() && this.func_190858_a(this.field_190859_a, 0)) {
         double ☃ = this.field_190859_a.func_70068_e(this.field_190859_a.func_190716_dS());
         if (☃ > 676.0) {
            if (this.field_190860_b <= 3.0) {
               this.field_190860_b *= 1.2;
               this.field_190861_c = 40;
               return true;
            }

            if (this.field_190861_c == 0) {
               return false;
            }
         }

         if (this.field_190861_c > 0) {
            --this.field_190861_c;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void func_75251_c() {
      this.field_190859_a.func_190709_dP();
      this.field_190860_b = 2.1;
   }

   @Override
   public void func_75246_d() {
      if (this.field_190859_a.func_190718_dR()) {
         EntityLlama ☃ = this.field_190859_a.func_190716_dS();
         double ☃x = (double)this.field_190859_a.func_70032_d(☃);
         float ☃xx = 2.0F;
         Vec3d ☃xxx = new Vec3d(
               ☃.field_70165_t - this.field_190859_a.field_70165_t,
               ☃.field_70163_u - this.field_190859_a.field_70163_u,
               ☃.field_70161_v - this.field_190859_a.field_70161_v
            )
            .func_72432_b()
            .func_186678_a(Math.max(☃x - 2.0, 0.0));
         this.field_190859_a
            .func_70661_as()
            .func_75492_a(
               this.field_190859_a.field_70165_t + ☃xxx.field_72450_a,
               this.field_190859_a.field_70163_u + ☃xxx.field_72448_b,
               this.field_190859_a.field_70161_v + ☃xxx.field_72449_c,
               this.field_190860_b
            );
      }
   }

   private boolean func_190858_a(EntityLlama var1, int var2) {
      if (☃ > 8) {
         return false;
      } else if (☃.func_190718_dR()) {
         return ☃.func_190716_dS().func_110167_bD() ? true : this.func_190858_a(☃.func_190716_dS(), ++☃);
      } else {
         return false;
      }
   }
}
