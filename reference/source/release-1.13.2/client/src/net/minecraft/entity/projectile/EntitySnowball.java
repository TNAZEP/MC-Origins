package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Particles;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySnowball extends EntityThrowable {
   public EntitySnowball(World var1) {
      super(EntityType.field_200746_al, ☃);
   }

   public EntitySnowball(World var1, EntityLivingBase var2) {
      super(EntityType.field_200746_al, ☃, ☃);
   }

   public EntitySnowball(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200746_al, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 3) {
         for(int ☃ = 0; ☃ < 8; ++☃) {
            this.field_70170_p.func_195594_a(Particles.field_197593_D, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void func_70184_a(RayTraceResult var1) {
      if (☃.field_72308_g != null) {
         int ☃ = 0;
         if (☃.field_72308_g instanceof EntityBlaze) {
            ☃ = 3;
         }

         ☃.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), (float)☃);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72960_a(this, (byte)3);
         this.func_70106_y();
      }
   }
}
