package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.MathHelper;

public class EntityFlyHelper extends EntityMoveHelper {
   public EntityFlyHelper(EntityLiving var1) {
      super(☃);
   }

   @Override
   public void func_75641_c() {
      if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
         this.field_188491_h = EntityMoveHelper.Action.WAIT;
         this.field_75648_a.func_189654_d(true);
         double ☃ = this.field_75646_b - this.field_75648_a.field_70165_t;
         double ☃x = this.field_75647_c - this.field_75648_a.field_70163_u;
         double ☃xx = this.field_75644_d - this.field_75648_a.field_70161_v;
         double ☃xxx = ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
         if (☃xxx < 2.5000003E-7F) {
            this.field_75648_a.func_70657_f(0.0F);
            this.field_75648_a.func_191989_p(0.0F);
            return;
         }

         float ☃x = (float)(MathHelper.func_181159_b(☃xx, ☃) * 180.0F / (float)Math.PI) - 90.0F;
         this.field_75648_a.field_70177_z = this.func_75639_a(this.field_75648_a.field_70177_z, ☃x, 10.0F);
         float ☃;
         if (this.field_75648_a.field_70122_E) {
            ☃ = (float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
         } else {
            ☃ = (float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_193334_e).func_111126_e());
         }

         this.field_75648_a.func_70659_e(☃);
         double ☃ = (double)MathHelper.func_76133_a(☃ * ☃ + ☃xx * ☃xx);
         float ☃x = (float)(-(MathHelper.func_181159_b(☃x, ☃) * 180.0F / (float)Math.PI));
         this.field_75648_a.field_70125_A = this.func_75639_a(this.field_75648_a.field_70125_A, ☃x, 10.0F);
         this.field_75648_a.func_70657_f(☃x > 0.0 ? ☃ : -☃);
      } else {
         this.field_75648_a.func_189654_d(false);
         this.field_75648_a.func_70657_f(0.0F);
         this.field_75648_a.func_191989_p(0.0F);
      }
   }
}
