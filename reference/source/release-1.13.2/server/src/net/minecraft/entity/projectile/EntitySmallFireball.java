package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySmallFireball extends EntityFireball {
   public EntitySmallFireball(World var1) {
      super(EntityType.field_200744_aj, ☃, 0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(EntityType.field_200744_aj, ☃, ☃, ☃, ☃, ☃, 0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(EntityType.field_200744_aj, ☃, ☃, ☃, ☃, ☃, ☃, ☃, 0.3125F, 0.3125F);
   }

   @Override
   protected void func_70227_a(RayTraceResult var1) {
      if (!this.field_70170_p.field_72995_K) {
         if (☃.field_72308_g != null) {
            if (!☃.field_72308_g.func_70045_F()) {
               ☃.field_72308_g.func_70015_d(5);
               boolean ☃ = ☃.field_72308_g.func_70097_a(DamageSource.func_76362_a(this, this.field_70235_a), 5.0F);
               if (☃) {
                  this.func_174815_a(this.field_70235_a, ☃.field_72308_g);
               }
            }
         } else {
            boolean ☃ = true;
            if (this.field_70235_a != null && this.field_70235_a instanceof EntityLiving) {
               ☃ = this.field_70170_p.func_82736_K().func_82766_b("mobGriefing");
            }

            if (☃) {
               BlockPos ☃ = ☃.func_178782_a().func_177972_a(☃.field_178784_b);
               if (this.field_70170_p.func_175623_d(☃)) {
                  this.field_70170_p.func_175656_a(☃, Blocks.field_150480_ab.func_176223_P());
               }
            }
         }

         this.func_70106_y();
      }
   }

   @Override
   public boolean func_70067_L() {
      return false;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      return false;
   }
}
