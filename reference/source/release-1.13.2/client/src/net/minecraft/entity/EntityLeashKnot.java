package net.minecraft.entity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {
   public EntityLeashKnot(World var1) {
      super(EntityType.field_200768_H, ☃);
   }

   public EntityLeashKnot(World var1, BlockPos var2) {
      super(EntityType.field_200768_H, ☃, ☃);
      this.func_70107_b((double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o() + 0.5, (double)☃.func_177952_p() + 0.5);
      float ☃ = 0.125F;
      float ☃x = 0.1875F;
      float ☃xx = 0.25F;
      this.func_174826_a(
         new AxisAlignedBB(
            this.field_70165_t - 0.1875,
            this.field_70163_u - 0.25 + 0.125,
            this.field_70161_v - 0.1875,
            this.field_70165_t + 0.1875,
            this.field_70163_u + 0.25 + 0.125,
            this.field_70161_v + 0.1875
         )
      );
      this.field_98038_p = true;
   }

   @Override
   public void func_70107_b(double var1, double var3, double var5) {
      super.func_70107_b((double)MathHelper.func_76128_c(☃) + 0.5, (double)MathHelper.func_76128_c(☃) + 0.5, (double)MathHelper.func_76128_c(☃) + 0.5);
   }

   @Override
   protected void func_174856_o() {
      this.field_70165_t = (double)this.field_174861_a.func_177958_n() + 0.5;
      this.field_70163_u = (double)this.field_174861_a.func_177956_o() + 0.5;
      this.field_70161_v = (double)this.field_174861_a.func_177952_p() + 0.5;
   }

   @Override
   public void func_174859_a(EnumFacing var1) {
   }

   @Override
   public int func_82329_d() {
      return 9;
   }

   @Override
   public int func_82330_g() {
      return 9;
   }

   @Override
   public float func_70047_e() {
      return -0.0625F;
   }

   @Override
   public boolean func_70112_a(double var1) {
      return ☃ < 1024.0;
   }

   @Override
   public void func_110128_b(@Nullable Entity var1) {
      this.func_184185_a(SoundEvents.field_187746_da, 1.0F, 1.0F);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
   }

   @Override
   public boolean func_184230_a(EntityPlayer var1, EnumHand var2) {
      if (this.field_70170_p.field_72995_K) {
         return true;
      } else {
         boolean ☃ = false;
         double ☃x = 7.0;
         List<EntityLiving> ☃xx = this.field_70170_p
            .func_72872_a(
               EntityLiving.class,
               new AxisAlignedBB(
                  this.field_70165_t - 7.0,
                  this.field_70163_u - 7.0,
                  this.field_70161_v - 7.0,
                  this.field_70165_t + 7.0,
                  this.field_70163_u + 7.0,
                  this.field_70161_v + 7.0
               )
            );

         for(EntityLiving ☃xxx : ☃xx) {
            if (☃xxx.func_110167_bD() && ☃xxx.func_110166_bE() == ☃) {
               ☃xxx.func_110162_b(this, true);
               ☃ = true;
            }
         }

         if (!☃) {
            this.func_70106_y();
            if (☃.field_71075_bZ.field_75098_d) {
               for(EntityLiving ☃xxx : ☃xx) {
                  if (☃xxx.func_110167_bD() && ☃xxx.func_110166_bE() == this) {
                     ☃xxx.func_110160_i(true, false);
                  }
               }
            }
         }

         return true;
      }
   }

   @Override
   public boolean func_70518_d() {
      return this.field_70170_p.func_180495_p(this.field_174861_a).func_177230_c() instanceof BlockFence;
   }

   public static EntityLeashKnot func_174862_a(World var0, BlockPos var1) {
      EntityLeashKnot ☃ = new EntityLeashKnot(☃, ☃);
      ☃.func_72838_d(☃);
      ☃.func_184523_o();
      return ☃;
   }

   @Nullable
   public static EntityLeashKnot func_174863_b(World var0, BlockPos var1) {
      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p();

      for(EntityLeashKnot ☃xxx : ☃.func_72872_a(
         EntityLeashKnot.class, new AxisAlignedBB((double)☃ - 1.0, (double)☃x - 1.0, (double)☃xx - 1.0, (double)☃ + 1.0, (double)☃x + 1.0, (double)☃xx + 1.0)
      )) {
         if (☃xxx.func_174857_n().equals(☃)) {
            return ☃xxx;
         }
      }

      return null;
   }

   @Override
   public void func_184523_o() {
      this.func_184185_a(SoundEvents.field_187748_db, 1.0F, 1.0F);
   }
}
