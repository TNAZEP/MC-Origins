package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIHurtByTarget extends EntityAITarget {
   private final boolean field_75312_a;
   private int field_142052_b;
   private final Class<?>[] field_179447_c;

   public EntityAIHurtByTarget(EntityCreature var1, boolean var2, Class<?>... var3) {
      super(☃, true);
      this.field_75312_a = ☃;
      this.field_179447_c = ☃;
      this.func_75248_a(1);
   }

   @Override
   public boolean func_75250_a() {
      int ☃ = this.field_75299_d.func_142015_aE();
      EntityLivingBase ☃x = this.field_75299_d.func_70643_av();
      return ☃ != this.field_142052_b && ☃x != null && this.func_75296_a(☃x, false);
   }

   @Override
   public void func_75249_e() {
      this.field_75299_d.func_70624_b(this.field_75299_d.func_70643_av());
      this.field_188509_g = this.field_75299_d.func_70638_az();
      this.field_142052_b = this.field_75299_d.func_142015_aE();
      this.field_188510_h = 300;
      if (this.field_75312_a) {
         this.func_190105_f();
      }

      super.func_75249_e();
   }

   protected void func_190105_f() {
      double ☃ = this.func_111175_f();

      for(EntityCreature ☃x : this.field_75299_d
         .field_70170_p
         .func_72872_a(
            this.field_75299_d.getClass(),
            new AxisAlignedBB(
                  this.field_75299_d.field_70165_t,
                  this.field_75299_d.field_70163_u,
                  this.field_75299_d.field_70161_v,
                  this.field_75299_d.field_70165_t + 1.0,
                  this.field_75299_d.field_70163_u + 1.0,
                  this.field_75299_d.field_70161_v + 1.0
               )
               .func_72314_b(☃, 10.0, ☃)
         )) {
         if (this.field_75299_d != ☃x
            && ☃x.func_70638_az() == null
            && (!(this.field_75299_d instanceof EntityTameable) || ((EntityTameable)this.field_75299_d).func_70902_q() == ((EntityTameable)☃x).func_70902_q())
            && !☃x.func_184191_r(this.field_75299_d.func_70643_av())) {
            boolean ☃xx = false;

            for(Class<?> ☃xxx : this.field_179447_c) {
               if (☃x.getClass() == ☃xxx) {
                  ☃xx = true;
                  break;
               }
            }

            if (!☃xx) {
               this.func_179446_a(☃x, this.field_75299_d.func_70643_av());
            }
         }
      }
   }

   protected void func_179446_a(EntityCreature var1, EntityLivingBase var2) {
      ☃.func_70624_b(☃);
   }
}
