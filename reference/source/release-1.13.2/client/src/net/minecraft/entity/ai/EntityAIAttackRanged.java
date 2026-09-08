package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.util.math.MathHelper;

public class EntityAIAttackRanged extends EntityAIBase {
   private final EntityLiving field_75322_b;
   private final IRangedAttackMob field_82641_b;
   private EntityLivingBase field_75323_c;
   private int field_75320_d = -1;
   private final double field_75321_e;
   private int field_75318_f;
   private final int field_96561_g;
   private final int field_75325_h;
   private final float field_96562_i;
   private final float field_82642_h;

   public EntityAIAttackRanged(IRangedAttackMob var1, double var2, int var4, float var5) {
      this(☃, ☃, ☃, ☃, ☃);
   }

   public EntityAIAttackRanged(IRangedAttackMob var1, double var2, int var4, int var5, float var6) {
      if (!(☃ instanceof EntityLivingBase)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.field_82641_b = ☃;
         this.field_75322_b = (EntityLiving)☃;
         this.field_75321_e = ☃;
         this.field_96561_g = ☃;
         this.field_75325_h = ☃;
         this.field_96562_i = ☃;
         this.field_82642_h = ☃ * ☃;
         this.func_75248_a(3);
      }
   }

   @Override
   public boolean func_75250_a() {
      EntityLivingBase ☃ = this.field_75322_b.func_70638_az();
      if (☃ == null) {
         return false;
      } else {
         this.field_75323_c = ☃;
         return true;
      }
   }

   @Override
   public boolean func_75253_b() {
      return this.func_75250_a() || !this.field_75322_b.func_70661_as().func_75500_f();
   }

   @Override
   public void func_75251_c() {
      this.field_75323_c = null;
      this.field_75318_f = 0;
      this.field_75320_d = -1;
   }

   @Override
   public void func_75246_d() {
      double ☃ = this.field_75322_b
         .func_70092_e(this.field_75323_c.field_70165_t, this.field_75323_c.func_174813_aQ().field_72338_b, this.field_75323_c.field_70161_v);
      boolean ☃x = this.field_75322_b.func_70635_at().func_75522_a(this.field_75323_c);
      if (☃x) {
         ++this.field_75318_f;
      } else {
         this.field_75318_f = 0;
      }

      if (!(☃ > (double)this.field_82642_h) && this.field_75318_f >= 20) {
         this.field_75322_b.func_70661_as().func_75499_g();
      } else {
         this.field_75322_b.func_70661_as().func_75497_a(this.field_75323_c, this.field_75321_e);
      }

      this.field_75322_b.func_70671_ap().func_75651_a(this.field_75323_c, 30.0F, 30.0F);
      if (--this.field_75320_d == 0) {
         if (!☃x) {
            return;
         }

         float ☃ = MathHelper.func_76133_a(☃) / this.field_96562_i;
         float var5 = MathHelper.func_76131_a(☃, 0.1F, 1.0F);
         this.field_82641_b.func_82196_d(this.field_75323_c, var5);
         this.field_75320_d = MathHelper.func_76141_d(☃ * (float)(this.field_75325_h - this.field_96561_g) + (float)this.field_96561_g);
      } else if (this.field_75320_d < 0) {
         float ☃ = MathHelper.func_76133_a(☃) / this.field_96562_i;
         this.field_75320_d = MathHelper.func_76141_d(☃ * (float)(this.field_75325_h - this.field_96561_g) + (float)this.field_96561_g);
      }
   }
}
