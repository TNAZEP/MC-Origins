package net.minecraft.entity.item;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Particles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class EntityEnderPearl extends EntityThrowable {
   private EntityLivingBase field_181555_c;

   public EntityEnderPearl(World var1) {
      super(EntityType.field_200752_ar, ☃);
   }

   public EntityEnderPearl(World var1, EntityLivingBase var2) {
      super(EntityType.field_200752_ar, ☃, ☃);
      this.field_181555_c = ☃;
   }

   public EntityEnderPearl(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200752_ar, ☃, ☃, ☃, ☃);
   }

   @Override
   protected void func_70184_a(RayTraceResult var1) {
      EntityLivingBase ☃ = this.func_85052_h();
      if (☃.field_72308_g != null) {
         if (☃.field_72308_g == this.field_181555_c) {
            return;
         }

         ☃.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, ☃), 0.0F);
      }

      if (☃.field_72313_a == RayTraceResult.Type.BLOCK) {
         BlockPos ☃ = ☃.func_178782_a();
         TileEntity ☃x = this.field_70170_p.func_175625_s(☃);
         if (☃x instanceof TileEntityEndGateway) {
            TileEntityEndGateway ☃xx = (TileEntityEndGateway)☃x;
            if (☃ != null) {
               if (☃ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_192124_d.func_192193_a((EntityPlayerMP)☃, this.field_70170_p.func_180495_p(☃));
               }

               ☃xx.func_195496_a(☃);
               this.func_70106_y();
               return;
            }

            ☃xx.func_195496_a(this);
            return;
         }
      }

      for(int ☃ = 0; ☃ < 32; ++☃) {
         this.field_70170_p
            .func_195594_a(
               Particles.field_197599_J,
               this.field_70165_t,
               this.field_70163_u + this.field_70146_Z.nextDouble() * 2.0,
               this.field_70161_v,
               this.field_70146_Z.nextGaussian(),
               0.0,
               this.field_70146_Z.nextGaussian()
            );
      }

      if (!this.field_70170_p.field_72995_K) {
         if (☃ instanceof EntityPlayerMP) {
            EntityPlayerMP ☃ = (EntityPlayerMP)☃;
            if (☃.field_71135_a.func_147362_b().func_150724_d() && ☃.field_70170_p == this.field_70170_p && !☃.func_70608_bn()) {
               if (this.field_70146_Z.nextFloat() < 0.05F && this.field_70170_p.func_82736_K().func_82766_b("doMobSpawning")) {
                  EntityEndermite ☃x = new EntityEndermite(this.field_70170_p);
                  ☃x.func_175496_a(true);
                  ☃x.func_70012_b(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A);
                  this.field_70170_p.func_72838_d(☃x);
               }

               if (☃.func_184218_aH()) {
                  ☃.func_184210_p();
               }

               ☃.func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
               ☃.field_70143_R = 0.0F;
               ☃.func_70097_a(DamageSource.field_76379_h, 5.0F);
            }
         } else if (☃ != null) {
            ☃.func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            ☃.field_70143_R = 0.0F;
         }

         this.func_70106_y();
      }
   }

   @Override
   public void func_70071_h_() {
      EntityLivingBase ☃ = this.func_85052_h();
      if (☃ != null && ☃ instanceof EntityPlayer && !☃.func_70089_S()) {
         this.func_70106_y();
      } else {
         super.func_70071_h_();
      }
   }

   @Nullable
   @Override
   public Entity func_212321_a(DimensionType var1) {
      if (this.field_70192_c.field_71093_bK != ☃) {
         this.field_70192_c = null;
      }

      return super.func_212321_a(☃);
   }
}
