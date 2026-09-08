package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.passive.EntityAnimal;

public class EntityAIFollowParent extends EntityAIBase {
   private final EntityAnimal field_75348_a;
   private EntityAnimal field_75346_b;
   private final double field_75347_c;
   private int field_75345_d;

   public EntityAIFollowParent(EntityAnimal var1, double var2) {
      this.field_75348_a = ☃;
      this.field_75347_c = ☃;
   }

   @Override
   public boolean func_75250_a() {
      if (this.field_75348_a.func_70874_b() >= 0) {
         return false;
      } else {
         List<EntityAnimal> ☃ = this.field_75348_a
            .field_70170_p
            .func_72872_a(this.field_75348_a.getClass(), this.field_75348_a.func_174813_aQ().func_72314_b(8.0, 4.0, 8.0));
         EntityAnimal ☃x = null;
         double ☃xx = Double.MAX_VALUE;

         for(EntityAnimal ☃xxx : ☃) {
            if (☃xxx.func_70874_b() >= 0) {
               double ☃xxxx = this.field_75348_a.func_70068_e(☃xxx);
               if (!(☃xxxx > ☃xx)) {
                  ☃xx = ☃xxxx;
                  ☃x = ☃xxx;
               }
            }
         }

         if (☃x == null) {
            return false;
         } else if (☃xx < 9.0) {
            return false;
         } else {
            this.field_75346_b = ☃x;
            return true;
         }
      }
   }

   @Override
   public boolean func_75253_b() {
      if (this.field_75348_a.func_70874_b() >= 0) {
         return false;
      } else if (!this.field_75346_b.func_70089_S()) {
         return false;
      } else {
         double ☃ = this.field_75348_a.func_70068_e(this.field_75346_b);
         return !(☃ < 9.0) && !(☃ > 256.0);
      }
   }

   @Override
   public void func_75249_e() {
      this.field_75345_d = 0;
   }

   @Override
   public void func_75251_c() {
      this.field_75346_b = null;
   }

   @Override
   public void func_75246_d() {
      if (--this.field_75345_d <= 0) {
         this.field_75345_d = 10;
         this.field_75348_a.func_70661_as().func_75497_a(this.field_75346_b, this.field_75347_c);
      }
   }
}
