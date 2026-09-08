package net.minecraft.entity.projectile;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityThrowable extends Entity implements IProjectile {
   private int field_145788_c = -1;
   private int field_145786_d = -1;
   private int field_145787_e = -1;
   protected boolean field_174854_a;
   public int field_70191_b;
   protected EntityLivingBase field_70192_c;
   private UUID field_200218_h;
   public Entity field_184539_c;
   private int field_184540_av;

   protected EntityThrowable(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_70105_a(0.25F, 0.25F);
   }

   protected EntityThrowable(EntityType<?> var1, double var2, double var4, double var6, World var8) {
      this(☃, ☃);
      this.func_70107_b(☃, ☃, ☃);
   }

   protected EntityThrowable(EntityType<?> var1, EntityLivingBase var2, World var3) {
      this(☃, ☃.field_70165_t, ☃.field_70163_u + (double)☃.func_70047_e() - 0.1F, ☃.field_70161_v, ☃);
      this.field_70192_c = ☃;
      this.field_200218_h = ☃.func_110124_au();
   }

   @Override
   protected void func_70088_a() {
   }

   public void func_184538_a(Entity var1, float var2, float var3, float var4, float var5, float var6) {
      float ☃ = -MathHelper.func_76126_a(☃ * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃ * (float) (Math.PI / 180.0));
      float ☃x = -MathHelper.func_76126_a((☃ + ☃) * (float) (Math.PI / 180.0));
      float ☃xx = MathHelper.func_76134_b(☃ * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃ * (float) (Math.PI / 180.0));
      this.func_70186_c((double)☃, (double)☃x, (double)☃xx, ☃, ☃);
      this.field_70159_w += ☃.field_70159_w;
      this.field_70179_y += ☃.field_70179_y;
      if (!☃.field_70122_E) {
         this.field_70181_x += ☃.field_70181_x;
      }
   }

   @Override
   public void func_70186_c(double var1, double var3, double var5, float var7, float var8) {
      float ☃ = MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃ + ☃ * ☃);
      ☃ /= (double)☃;
      ☃ /= (double)☃;
      ☃ /= (double)☃;
      ☃ += this.field_70146_Z.nextGaussian() * 0.0075F * (double)☃;
      ☃ += this.field_70146_Z.nextGaussian() * 0.0075F * (double)☃;
      ☃ += this.field_70146_Z.nextGaussian() * 0.0075F * (double)☃;
      ☃ *= (double)☃;
      ☃ *= (double)☃;
      ☃ *= (double)☃;
      this.field_70159_w = ☃;
      this.field_70181_x = ☃;
      this.field_70179_y = ☃;
      float ☃x = MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃);
      this.field_70177_z = (float)(MathHelper.func_181159_b(☃, ☃) * 180.0F / (float)Math.PI);
      this.field_70125_A = (float)(MathHelper.func_181159_b(☃, (double)☃x) * 180.0F / (float)Math.PI);
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
   }

   @Override
   public void func_70071_h_() {
      this.field_70142_S = this.field_70165_t;
      this.field_70137_T = this.field_70163_u;
      this.field_70136_U = this.field_70161_v;
      super.func_70071_h_();
      if (this.field_70191_b > 0) {
         --this.field_70191_b;
      }

      if (this.field_174854_a) {
         this.field_174854_a = false;
         this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
         this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
         this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
      }

      Vec3d ☃ = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      Vec3d ☃x = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      RayTraceResult ☃xx = this.field_70170_p.func_72933_a(☃, ☃x);
      ☃ = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      ☃x = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (☃xx != null) {
         ☃x = new Vec3d(☃xx.field_72307_f.field_72450_a, ☃xx.field_72307_f.field_72448_b, ☃xx.field_72307_f.field_72449_c);
      }

      Entity ☃ = null;
      List<Entity> ☃x = this.field_70170_p
         .func_72839_b(this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0));
      double ☃xx = 0.0;
      boolean ☃xxx = false;

      for(int ☃xxxx = 0; ☃xxxx < ☃x.size(); ++☃xxxx) {
         Entity ☃xxxxx = (Entity)☃x.get(☃xxxx);
         if (☃xxxxx.func_70067_L()) {
            if (☃xxxxx == this.field_184539_c) {
               ☃xxx = true;
            } else if (this.field_70192_c != null && this.field_70173_aa < 2 && this.field_184539_c == null) {
               this.field_184539_c = ☃xxxxx;
               ☃xxx = true;
            } else {
               ☃xxx = false;
               AxisAlignedBB ☃xxxxxx = ☃xxxxx.func_174813_aQ().func_186662_g(0.3F);
               RayTraceResult ☃xxxxxxx = ☃xxxxxx.func_72327_a(☃, ☃x);
               if (☃xxxxxxx != null) {
                  double ☃xxxxxxxx = ☃.func_72436_e(☃xxxxxxx.field_72307_f);
                  if (☃xxxxxxxx < ☃xx || ☃xx == 0.0) {
                     ☃ = ☃xxxxx;
                     ☃xx = ☃xxxxxxxx;
                  }
               }
            }
         }
      }

      if (this.field_184539_c != null) {
         if (☃xxx) {
            this.field_184540_av = 2;
         } else if (this.field_184540_av-- <= 0) {
            this.field_184539_c = null;
         }
      }

      if (☃ != null) {
         ☃xx = new RayTraceResult(☃);
      }

      if (☃xx != null) {
         if (☃xx.field_72313_a == RayTraceResult.Type.BLOCK && this.field_70170_p.func_180495_p(☃xx.func_178782_a()).func_177230_c() == Blocks.field_150427_aO) {
            this.func_181015_d(☃xx.func_178782_a());
         } else {
            this.func_70184_a(☃xx);
         }
      }

      this.field_70165_t += this.field_70159_w;
      this.field_70163_u += this.field_70181_x;
      this.field_70161_v += this.field_70179_y;
      float ☃xxxx = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 180.0F / (float)Math.PI);
      this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)☃xxxx) * 180.0F / (float)Math.PI);

      while(this.field_70125_A - this.field_70127_C < -180.0F) {
         this.field_70127_C -= 360.0F;
      }

      while(this.field_70125_A - this.field_70127_C >= 180.0F) {
         this.field_70127_C += 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B < -180.0F) {
         this.field_70126_B -= 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B >= 180.0F) {
         this.field_70126_B += 360.0F;
      }

      this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
      this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
      float ☃xxxxx = 0.99F;
      float ☃xxxxxx = this.func_70185_h();
      if (this.func_70090_H()) {
         for(int ☃xxxxxxx = 0; ☃xxxxxxx < 4; ++☃xxxxxxx) {
            float ☃xxxxxxxx = 0.25F;
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

         ☃xxxxx = 0.8F;
      }

      this.field_70159_w *= (double)☃xxxxx;
      this.field_70181_x *= (double)☃xxxxx;
      this.field_70179_y *= (double)☃xxxxx;
      if (!this.func_189652_ae()) {
         this.field_70181_x -= (double)☃xxxxxx;
      }

      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   protected float func_70185_h() {
      return 0.03F;
   }

   protected abstract void func_70184_a(RayTraceResult var1);

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      ☃.func_74768_a("xTile", this.field_145788_c);
      ☃.func_74768_a("yTile", this.field_145786_d);
      ☃.func_74768_a("zTile", this.field_145787_e);
      ☃.func_74774_a("shake", (byte)this.field_70191_b);
      ☃.func_74774_a("inGround", (byte)(this.field_174854_a ? 1 : 0));
      if (this.field_200218_h != null) {
         ☃.func_74782_a("owner", NBTUtil.func_186862_a(this.field_200218_h));
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      this.field_145788_c = ☃.func_74762_e("xTile");
      this.field_145786_d = ☃.func_74762_e("yTile");
      this.field_145787_e = ☃.func_74762_e("zTile");
      this.field_70191_b = ☃.func_74771_c("shake") & 255;
      this.field_174854_a = ☃.func_74771_c("inGround") == 1;
      this.field_70192_c = null;
      if (☃.func_150297_b("owner", 10)) {
         this.field_200218_h = NBTUtil.func_186860_b(☃.func_74775_l("owner"));
      }
   }

   @Nullable
   public EntityLivingBase func_85052_h() {
      if (this.field_70192_c == null && this.field_200218_h != null && this.field_70170_p instanceof WorldServer) {
         Entity ☃ = ((WorldServer)this.field_70170_p).func_175733_a(this.field_200218_h);
         if (☃ instanceof EntityLivingBase) {
            this.field_70192_c = (EntityLivingBase)☃;
         } else {
            this.field_200218_h = null;
         }
      }

      return this.field_70192_c;
   }
}
