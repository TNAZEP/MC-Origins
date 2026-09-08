package net.minecraft.entity.projectile;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityLlamaSpit extends Entity implements IProjectile {
   public EntityLlama field_190539_a;
   private NBTTagCompound field_190540_b;

   public EntityLlamaSpit(World var1) {
      super(EntityType.field_200770_J, ☃);
      this.func_70105_a(0.25F, 0.25F);
   }

   public EntityLlamaSpit(World var1, EntityLlama var2) {
      this(☃);
      this.field_190539_a = ☃;
      this.func_70107_b(
         ☃.field_70165_t - (double)(☃.field_70130_N + 1.0F) * 0.5 * (double)MathHelper.func_76126_a(☃.field_70761_aq * (float) (Math.PI / 180.0)),
         ☃.field_70163_u + (double)☃.func_70047_e() - 0.1F,
         ☃.field_70161_v + (double)(☃.field_70130_N + 1.0F) * 0.5 * (double)MathHelper.func_76134_b(☃.field_70761_aq * (float) (Math.PI / 180.0))
      );
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_190540_b != null) {
         this.func_190537_j();
      }

      Vec3d ☃ = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      Vec3d ☃x = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      RayTraceResult ☃xx = this.field_70170_p.func_72933_a(☃, ☃x);
      ☃ = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      ☃x = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (☃xx != null) {
         ☃x = new Vec3d(☃xx.field_72307_f.field_72450_a, ☃xx.field_72307_f.field_72448_b, ☃xx.field_72307_f.field_72449_c);
      }

      Entity ☃ = this.func_190538_a(☃, ☃x);
      if (☃ != null) {
         ☃xx = new RayTraceResult(☃);
      }

      if (☃xx != null) {
         this.func_190536_a(☃xx);
      }

      this.field_70165_t += this.field_70159_w;
      this.field_70163_u += this.field_70181_x;
      this.field_70161_v += this.field_70179_y;
      float ☃ = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 180.0F / (float)Math.PI);
      this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)☃) * 180.0F / (float)Math.PI);

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
      float ☃x = 0.99F;
      float ☃xx = 0.06F;
      if (!this.field_70170_p.func_72875_a(this.func_174813_aQ(), Material.field_151579_a)) {
         this.func_70106_y();
      } else if (this.func_203005_aq()) {
         this.func_70106_y();
      } else {
         this.field_70159_w *= 0.99F;
         this.field_70181_x *= 0.99F;
         this.field_70179_y *= 0.99F;
         if (!this.func_189652_ae()) {
            this.field_70181_x -= 0.06F;
         }

         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      }
   }

   @Nullable
   private Entity func_190538_a(Vec3d var1, Vec3d var2) {
      Entity ☃ = null;
      List<Entity> ☃x = this.field_70170_p
         .func_72839_b(this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0));
      double ☃xx = 0.0;

      for(Entity ☃xxx : ☃x) {
         if (☃xxx != this.field_190539_a) {
            AxisAlignedBB ☃xxxx = ☃xxx.func_174813_aQ().func_186662_g(0.3F);
            RayTraceResult ☃xxxxx = ☃xxxx.func_72327_a(☃, ☃);
            if (☃xxxxx != null) {
               double ☃xxxxxx = ☃.func_72436_e(☃xxxxx.field_72307_f);
               if (☃xxxxxx < ☃xx || ☃xx == 0.0) {
                  ☃ = ☃xxx;
                  ☃xx = ☃xxxxxx;
               }
            }
         }
      }

      return ☃;
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

   public void func_190536_a(RayTraceResult var1) {
      if (☃.field_72308_g != null && this.field_190539_a != null) {
         ☃.field_72308_g.func_70097_a(DamageSource.func_188403_a(this, this.field_190539_a).func_76349_b(), 1.0F);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70106_y();
      }
   }

   @Override
   protected void func_70088_a() {
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      if (☃.func_150297_b("Owner", 10)) {
         this.field_190540_b = ☃.func_74775_l("Owner");
      }
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      if (this.field_190539_a != null) {
         NBTTagCompound ☃ = new NBTTagCompound();
         UUID ☃x = this.field_190539_a.func_110124_au();
         ☃.func_186854_a("OwnerUUID", ☃x);
         ☃.func_74782_a("Owner", ☃);
      }
   }

   private void func_190537_j() {
      if (this.field_190540_b != null && this.field_190540_b.func_186855_b("OwnerUUID")) {
         UUID ☃ = this.field_190540_b.func_186857_a("OwnerUUID");

         for(EntityLlama ☃x : this.field_70170_p.func_72872_a(EntityLlama.class, this.func_174813_aQ().func_186662_g(15.0))) {
            if (☃x.func_110124_au().equals(☃)) {
               this.field_190539_a = ☃x;
               break;
            }
         }
      }

      this.field_190540_b = null;
   }
}
