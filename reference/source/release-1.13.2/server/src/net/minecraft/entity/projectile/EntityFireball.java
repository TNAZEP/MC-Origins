package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityFireball extends Entity {
   public EntityLivingBase field_70235_a;
   private int field_70236_j;
   private int field_70234_an;
   public double field_70232_b;
   public double field_70233_c;
   public double field_70230_d;

   protected EntityFireball(EntityType<?> var1, World var2, float var3, float var4) {
      super(☃, ☃);
      this.func_70105_a(☃, ☃);
   }

   public EntityFireball(
      EntityType<?> var1, double var2, double var4, double var6, double var8, double var10, double var12, World var14, float var15, float var16
   ) {
      this(☃, ☃, ☃, ☃);
      this.func_70012_b(☃, ☃, ☃, this.field_70177_z, this.field_70125_A);
      this.func_70107_b(☃, ☃, ☃);
      double ☃ = (double)MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃ + ☃ * ☃);
      this.field_70232_b = ☃ / ☃ * 0.1;
      this.field_70233_c = ☃ / ☃ * 0.1;
      this.field_70230_d = ☃ / ☃ * 0.1;
   }

   public EntityFireball(EntityType<?> var1, EntityLivingBase var2, double var3, double var5, double var7, World var9, float var10, float var11) {
      this(☃, ☃, ☃, ☃);
      this.field_70235_a = ☃;
      this.func_70012_b(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70159_w = 0.0;
      this.field_70181_x = 0.0;
      this.field_70179_y = 0.0;
      ☃ += this.field_70146_Z.nextGaussian() * 0.4;
      ☃ += this.field_70146_Z.nextGaussian() * 0.4;
      ☃ += this.field_70146_Z.nextGaussian() * 0.4;
      double ☃ = (double)MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃ + ☃ * ☃);
      this.field_70232_b = ☃ / ☃ * 0.1;
      this.field_70233_c = ☃ / ☃ * 0.1;
      this.field_70230_d = ☃ / ☃ * 0.1;
   }

   @Override
   protected void func_70088_a() {
   }

   @Override
   public void func_70071_h_() {
      if (this.field_70170_p.field_72995_K
         || (this.field_70235_a == null || !this.field_70235_a.field_70128_L) && this.field_70170_p.func_175667_e(new BlockPos(this))) {
         super.func_70071_h_();
         if (this.func_184564_k()) {
            this.func_70015_d(1);
         }

         ++this.field_70234_an;
         RayTraceResult ☃ = ProjectileHelper.func_188802_a(this, true, this.field_70234_an >= 25, this.field_70235_a);
         if (☃ != null) {
            this.func_70227_a(☃);
         }

         this.field_70165_t += this.field_70159_w;
         this.field_70163_u += this.field_70181_x;
         this.field_70161_v += this.field_70179_y;
         ProjectileHelper.func_188803_a(this, 0.2F);
         float ☃ = this.func_82341_c();
         if (this.func_70090_H()) {
            for(int ☃x = 0; ☃x < 4; ++☃x) {
               float ☃xx = 0.25F;
               this.field_70170_p
                  .func_195594_a(
                     Particles.field_197612_e,
                     this.field_70165_t - this.field_70159_w * 0.25,
                     this.field_70163_u - this.field_70181_x * 0.25,
                     this.field_70161_v - this.field_70179_y * 0.25,
                     this.field_70159_w,
                     this.field_70181_x,
                     this.field_70179_y
                  );
            }

            ☃ = 0.8F;
         }

         this.field_70159_w += this.field_70232_b;
         this.field_70181_x += this.field_70233_c;
         this.field_70179_y += this.field_70230_d;
         this.field_70159_w *= (double)☃;
         this.field_70181_x *= (double)☃;
         this.field_70179_y *= (double)☃;
         this.field_70170_p.func_195594_a(this.func_195057_f(), this.field_70165_t, this.field_70163_u + 0.5, this.field_70161_v, 0.0, 0.0, 0.0);
         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      } else {
         this.func_70106_y();
      }
   }

   protected boolean func_184564_k() {
      return true;
   }

   protected IParticleData func_195057_f() {
      return Particles.field_197601_L;
   }

   protected float func_82341_c() {
      return 0.95F;
   }

   protected abstract void func_70227_a(RayTraceResult var1);

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      ☃.func_74782_a("direction", this.func_70087_a(new double[]{this.field_70159_w, this.field_70181_x, this.field_70179_y}));
      ☃.func_74782_a("power", this.func_70087_a(new double[]{this.field_70232_b, this.field_70233_c, this.field_70230_d}));
      ☃.func_74768_a("life", this.field_70236_j);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      if (☃.func_150297_b("power", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("power", 6);
         if (☃.size() == 3) {
            this.field_70232_b = ☃.func_150309_d(0);
            this.field_70233_c = ☃.func_150309_d(1);
            this.field_70230_d = ☃.func_150309_d(2);
         }
      }

      this.field_70236_j = ☃.func_74762_e("life");
      if (☃.func_150297_b("direction", 9) && ☃.func_150295_c("direction", 6).size() == 3) {
         NBTTagList ☃ = ☃.func_150295_c("direction", 6);
         this.field_70159_w = ☃.func_150309_d(0);
         this.field_70181_x = ☃.func_150309_d(1);
         this.field_70179_y = ☃.func_150309_d(2);
      } else {
         this.func_70106_y();
      }
   }

   @Override
   public boolean func_70067_L() {
      return true;
   }

   @Override
   public float func_70111_Y() {
      return 1.0F;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         this.func_70018_K();
         if (☃.func_76346_g() != null) {
            Vec3d ☃ = ☃.func_76346_g().func_70040_Z();
            if (☃ != null) {
               this.field_70159_w = ☃.field_72450_a;
               this.field_70181_x = ☃.field_72448_b;
               this.field_70179_y = ☃.field_72449_c;
               this.field_70232_b = this.field_70159_w * 0.1;
               this.field_70233_c = this.field_70181_x * 0.1;
               this.field_70230_d = this.field_70179_y * 0.1;
            }

            if (☃.func_76346_g() instanceof EntityLivingBase) {
               this.field_70235_a = (EntityLivingBase)☃.func_76346_g();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public float func_70013_c() {
      return 1.0F;
   }
}
