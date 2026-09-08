package net.minecraft.entity.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart {
   private int field_94106_a = -1;

   public EntityMinecartTNT(World var1) {
      super(EntityType.field_200778_R, ☃);
   }

   public EntityMinecartTNT(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200778_R, ☃, ☃, ☃, ☃);
   }

   @Override
   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.TNT;
   }

   @Override
   public IBlockState func_180457_u() {
      return Blocks.field_150335_W.func_176223_P();
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_94106_a > 0) {
         --this.field_94106_a;
         this.field_70170_p.func_195594_a(Particles.field_197601_L, this.field_70165_t, this.field_70163_u + 0.5, this.field_70161_v, 0.0, 0.0, 0.0);
      } else if (this.field_94106_a == 0) {
         this.func_94103_c(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      }

      if (this.field_70123_F) {
         double ☃ = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
         if (☃ >= 0.01F) {
            this.func_94103_c(☃);
         }
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      Entity ☃ = ☃.func_76364_f();
      if (☃ instanceof EntityArrow) {
         EntityArrow ☃x = (EntityArrow)☃;
         if (☃x.func_70027_ad()) {
            this.func_94103_c(☃x.field_70159_w * ☃x.field_70159_w + ☃x.field_70181_x * ☃x.field_70181_x + ☃x.field_70179_y * ☃x.field_70179_y);
         }
      }

      return super.func_70097_a(☃, ☃);
   }

   @Override
   public void func_94095_a(DamageSource var1) {
      double ☃ = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
      if (!☃.func_76347_k() && !☃.func_94541_c() && !(☃ >= 0.01F)) {
         super.func_94095_a(☃);
         if (!☃.func_94541_c() && this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
            this.func_199703_a(Blocks.field_150335_W);
         }
      } else {
         if (this.field_94106_a < 0) {
            this.func_94105_c();
            this.field_94106_a = this.field_70146_Z.nextInt(20) + this.field_70146_Z.nextInt(20);
         }
      }
   }

   protected void func_94103_c(double var1) {
      if (!this.field_70170_p.field_72995_K) {
         double ☃ = Math.sqrt(☃);
         if (☃ > 5.0) {
            ☃ = 5.0;
         }

         this.field_70170_p
            .func_72876_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)(4.0 + this.field_70146_Z.nextDouble() * 1.5 * ☃), true);
         this.func_70106_y();
      }
   }

   @Override
   public void func_180430_e(float var1, float var2) {
      if (☃ >= 3.0F) {
         float ☃ = ☃ / 10.0F;
         this.func_94103_c((double)(☃ * ☃));
      }

      super.func_180430_e(☃, ☃);
   }

   @Override
   public void func_96095_a(int var1, int var2, int var3, boolean var4) {
      if (☃ && this.field_94106_a < 0) {
         this.func_94105_c();
      }
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 10) {
         this.func_94105_c();
      } else {
         super.func_70103_a(☃);
      }
   }

   public void func_94105_c() {
      this.field_94106_a = 80;
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72960_a(this, (byte)10);
         if (!this.func_174814_R()) {
            this.field_70170_p
               .func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
         }
      }
   }

   public int func_94104_d() {
      return this.field_94106_a;
   }

   public boolean func_96096_ay() {
      return this.field_94106_a > -1;
   }

   @Override
   public float func_180428_a(Explosion var1, IBlockReader var2, BlockPos var3, IBlockState var4, IFluidState var5, float var6) {
      return !this.func_96096_ay() || !☃.func_203425_a(BlockTags.field_203437_y) && !☃.func_180495_p(☃.func_177984_a()).func_203425_a(BlockTags.field_203437_y)
         ? super.func_180428_a(☃, ☃, ☃, ☃, ☃, ☃)
         : 0.0F;
   }

   @Override
   public boolean func_174816_a(Explosion var1, IBlockReader var2, BlockPos var3, IBlockState var4, float var5) {
      return !this.func_96096_ay() || !☃.func_203425_a(BlockTags.field_203437_y) && !☃.func_180495_p(☃.func_177984_a()).func_203425_a(BlockTags.field_203437_y)
         ? super.func_174816_a(☃, ☃, ☃, ☃, ☃)
         : false;
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_150297_b("TNTFuse", 99)) {
         this.field_94106_a = ☃.func_74762_e("TNTFuse");
      }
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("TNTFuse", this.field_94106_a);
   }
}
