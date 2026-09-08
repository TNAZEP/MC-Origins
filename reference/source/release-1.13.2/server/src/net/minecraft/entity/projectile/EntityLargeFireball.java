package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball {
   public int field_92057_e = 1;

   public EntityLargeFireball(World var1) {
      super(EntityType.field_200767_G, ☃, 1.0F, 1.0F);
   }

   public EntityLargeFireball(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(EntityType.field_200767_G, ☃, ☃, ☃, ☃, ☃, 1.0F, 1.0F);
   }

   @Override
   protected void func_70227_a(RayTraceResult var1) {
      if (!this.field_70170_p.field_72995_K) {
         if (☃.field_72308_g != null) {
            ☃.field_72308_g.func_70097_a(DamageSource.func_76362_a(this, this.field_70235_a), 6.0F);
            this.func_174815_a(this.field_70235_a, ☃.field_72308_g);
         }

         boolean ☃ = this.field_70170_p.func_82736_K().func_82766_b("mobGriefing");
         this.field_70170_p.func_72885_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)this.field_92057_e, ☃, ☃);
         this.func_70106_y();
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("ExplosionPower", this.field_92057_e);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_150297_b("ExplosionPower", 99)) {
         this.field_92057_e = ☃.func_74762_e("ExplosionPower");
      }
   }
}
