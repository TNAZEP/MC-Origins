package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAISit extends EntityAIBase {
   private final EntityTameable field_75272_a;
   private boolean field_75271_b;

   public EntityAISit(EntityTameable var1) {
      this.field_75272_a = ☃;
      this.func_75248_a(5);
   }

   @Override
   public boolean func_75250_a() {
      if (!this.field_75272_a.func_70909_n()) {
         return false;
      } else if (this.field_75272_a.func_203005_aq()) {
         return false;
      } else if (!this.field_75272_a.field_70122_E) {
         return false;
      } else {
         EntityLivingBase ☃ = this.field_75272_a.func_70902_q();
         if (☃ == null) {
            return true;
         } else {
            return this.field_75272_a.func_70068_e(☃) < 144.0 && ☃.func_70643_av() != null ? false : this.field_75271_b;
         }
      }
   }

   @Override
   public void func_75249_e() {
      this.field_75272_a.func_70661_as().func_75499_g();
      this.field_75272_a.func_70904_g(true);
   }

   @Override
   public void func_75251_c() {
      this.field_75272_a.func_70904_g(false);
   }

   public void func_75270_a(boolean var1) {
      this.field_75271_b = ☃;
   }
}
