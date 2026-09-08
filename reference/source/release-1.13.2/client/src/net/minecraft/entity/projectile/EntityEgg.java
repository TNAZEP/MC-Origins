package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEgg extends EntityThrowable {
   public EntityEgg(World var1) {
      super(EntityType.field_200751_aq, ☃);
   }

   public EntityEgg(World var1, EntityLivingBase var2) {
      super(EntityType.field_200751_aq, ☃, ☃);
   }

   public EntityEgg(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200751_aq, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 3) {
         double ☃ = 0.08;

         for(int ☃x = 0; ☃x < 8; ++☃x) {
            this.field_70170_p
               .func_195594_a(
                  new ItemParticleData(Particles.field_197591_B, new ItemStack(Items.field_151110_aK)),
                  this.field_70165_t,
                  this.field_70163_u,
                  this.field_70161_v,
                  ((double)this.field_70146_Z.nextFloat() - 0.5) * 0.08,
                  ((double)this.field_70146_Z.nextFloat() - 0.5) * 0.08,
                  ((double)this.field_70146_Z.nextFloat() - 0.5) * 0.08
               );
         }
      }
   }

   @Override
   protected void func_70184_a(RayTraceResult var1) {
      if (☃.field_72308_g != null) {
         ☃.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), 0.0F);
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70146_Z.nextInt(8) == 0) {
            int ☃ = 1;
            if (this.field_70146_Z.nextInt(32) == 0) {
               ☃ = 4;
            }

            for(int ☃ = 0; ☃ < ☃; ++☃) {
               EntityChicken ☃x = new EntityChicken(this.field_70170_p);
               ☃x.func_70873_a(-24000);
               ☃x.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
               this.field_70170_p.func_72838_d(☃x);
            }
         }

         this.field_70170_p.func_72960_a(this, (byte)3);
         this.func_70106_y();
      }
   }
}
