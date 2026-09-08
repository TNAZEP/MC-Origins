package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.Vec3d;

public class EntityAIPlay extends EntityAIBase {
   private final EntityVillager field_75262_a;
   private EntityLivingBase field_75260_b;
   private final double field_75261_c;
   private int field_75259_d;

   public EntityAIPlay(EntityVillager var1, double var2) {
      this.field_75262_a = ☃;
      this.field_75261_c = ☃;
      this.func_75248_a(1);
   }

   @Override
   public boolean func_75250_a() {
      if (this.field_75262_a.func_70874_b() >= 0) {
         return false;
      } else if (this.field_75262_a.func_70681_au().nextInt(400) != 0) {
         return false;
      } else {
         List<EntityVillager> ☃ = this.field_75262_a
            .field_70170_p
            .func_72872_a(EntityVillager.class, this.field_75262_a.func_174813_aQ().func_72314_b(6.0, 3.0, 6.0));
         double ☃x = Double.MAX_VALUE;

         for(EntityVillager ☃xx : ☃) {
            if (☃xx != this.field_75262_a && !☃xx.func_70945_p() && ☃xx.func_70874_b() < 0) {
               double ☃xxx = ☃xx.func_70068_e(this.field_75262_a);
               if (!(☃xxx > ☃x)) {
                  ☃x = ☃xxx;
                  this.field_75260_b = ☃xx;
               }
            }
         }

         if (this.field_75260_b == null) {
            Vec3d ☃xx = RandomPositionGenerator.func_75463_a(this.field_75262_a, 16, 3);
            if (☃xx == null) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public boolean func_75253_b() {
      return this.field_75259_d > 0;
   }

   @Override
   public void func_75249_e() {
      if (this.field_75260_b != null) {
         this.field_75262_a.func_70939_f(true);
      }

      this.field_75259_d = 1000;
   }

   @Override
   public void func_75251_c() {
      this.field_75262_a.func_70939_f(false);
      this.field_75260_b = null;
   }

   @Override
   public void func_75246_d() {
      --this.field_75259_d;
      if (this.field_75260_b != null) {
         if (this.field_75262_a.func_70068_e(this.field_75260_b) > 4.0) {
            this.field_75262_a.func_70661_as().func_75497_a(this.field_75260_b, this.field_75261_c);
         }
      } else if (this.field_75262_a.func_70661_as().func_75500_f()) {
         Vec3d ☃ = RandomPositionGenerator.func_75463_a(this.field_75262_a, 16, 3);
         if (☃ == null) {
            return;
         }

         this.field_75262_a.func_70661_as().func_75492_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, this.field_75261_c);
      }
   }
}
