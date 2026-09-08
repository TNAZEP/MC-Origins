package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye extends Entity {
   private double field_70224_b;
   private double field_70225_c;
   private double field_70222_d;
   private int field_70223_e;
   private boolean field_70221_f;

   public EntityEnderEye(World var1) {
      super(EntityType.field_200808_v, ☃);
      this.func_70105_a(0.25F, 0.25F);
   }

   public EntityEnderEye(World var1, double var2, double var4, double var6) {
      this(☃);
      this.field_70223_e = 0;
      this.func_70107_b(☃, ☃, ☃);
   }

   @Override
   protected void func_70088_a() {
   }

   @Override
   public boolean func_70112_a(double var1) {
      double ☃ = this.func_174813_aQ().func_72320_b() * 4.0;
      if (Double.isNaN(☃)) {
         ☃ = 4.0;
      }

      ☃ *= 64.0;
      return ☃ < ☃ * ☃;
   }

   public void func_180465_a(BlockPos var1) {
      double ☃ = (double)☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      double ☃xx = (double)☃.func_177952_p();
      double ☃xxx = ☃ - this.field_70165_t;
      double ☃xxxx = ☃xx - this.field_70161_v;
      float ☃xxxxx = MathHelper.func_76133_a(☃xxx * ☃xxx + ☃xxxx * ☃xxxx);
      if (☃xxxxx > 12.0F) {
         this.field_70224_b = this.field_70165_t + ☃xxx / (double)☃xxxxx * 12.0;
         this.field_70222_d = this.field_70161_v + ☃xxxx / (double)☃xxxxx * 12.0;
         this.field_70225_c = this.field_70163_u + 8.0;
      } else {
         this.field_70224_b = ☃;
         this.field_70225_c = (double)☃x;
         this.field_70222_d = ☃xx;
      }

      this.field_70223_e = 0;
      this.field_70221_f = this.field_70146_Z.nextInt(5) > 0;
   }

   @Override
   public void func_70016_h(double var1, double var3, double var5) {
      this.field_70159_w = ☃;
      this.field_70181_x = ☃;
      this.field_70179_y = ☃;
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float ☃ = MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃);
         this.field_70177_z = (float)(MathHelper.func_181159_b(☃, ☃) * 180.0F / (float)Math.PI);
         this.field_70125_A = (float)(MathHelper.func_181159_b(☃, (double)☃) * 180.0F / (float)Math.PI);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
      }
   }

   @Override
   public void func_70071_h_() {
      this.field_70142_S = this.field_70165_t;
      this.field_70137_T = this.field_70163_u;
      this.field_70136_U = this.field_70161_v;
      super.func_70071_h_();
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
      if (!this.field_70170_p.field_72995_K) {
         double ☃x = this.field_70224_b - this.field_70165_t;
         double ☃xx = this.field_70222_d - this.field_70161_v;
         float ☃xxx = (float)Math.sqrt(☃x * ☃x + ☃xx * ☃xx);
         float ☃xxxx = (float)MathHelper.func_181159_b(☃xx, ☃x);
         double ☃xxxxx = (double)☃ + (double)(☃xxx - ☃) * 0.0025;
         if (☃xxx < 1.0F) {
            ☃xxxxx *= 0.8;
            this.field_70181_x *= 0.8;
         }

         this.field_70159_w = Math.cos((double)☃xxxx) * ☃xxxxx;
         this.field_70179_y = Math.sin((double)☃xxxx) * ☃xxxxx;
         if (this.field_70163_u < this.field_70225_c) {
            this.field_70181_x += (1.0 - this.field_70181_x) * 0.015F;
         } else {
            this.field_70181_x += (-1.0 - this.field_70181_x) * 0.015F;
         }
      }

      float ☃x = 0.25F;
      if (this.func_70090_H()) {
         for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
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
      } else {
         this.field_70170_p
            .func_195594_a(
               Particles.field_197599_J,
               this.field_70165_t - this.field_70159_w * 0.25 + this.field_70146_Z.nextDouble() * 0.6 - 0.3,
               this.field_70163_u - this.field_70181_x * 0.25 - 0.5,
               this.field_70161_v - this.field_70179_y * 0.25 + this.field_70146_Z.nextDouble() * 0.6 - 0.3,
               this.field_70159_w,
               this.field_70181_x,
               this.field_70179_y
            );
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         ++this.field_70223_e;
         if (this.field_70223_e > 80 && !this.field_70170_p.field_72995_K) {
            this.func_184185_a(SoundEvents.field_193777_bb, 1.0F, 1.0F);
            this.func_70106_y();
            if (this.field_70221_f) {
               this.field_70170_p
                  .func_72838_d(
                     new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, new ItemStack(Items.field_151061_bv))
                  );
            } else {
               this.field_70170_p.func_175718_b(2003, new BlockPos(this), 0);
            }
         }
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
   }

   @Override
   public float func_70013_c() {
      return 1.0F;
   }

   @Override
   public int func_70070_b() {
      return 15728880;
   }

   @Override
   public boolean func_70075_an() {
      return false;
   }
}
