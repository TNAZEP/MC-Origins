package net.minecraft.entity;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public abstract class EntityHanging extends Entity {
   protected static final Predicate<Entity> field_184524_c = var0 -> var0 instanceof EntityHanging;
   private int field_70520_f;
   protected BlockPos field_174861_a;
   @Nullable
   public EnumFacing field_174860_b;

   protected EntityHanging(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_70105_a(0.5F, 0.5F);
   }

   protected EntityHanging(EntityType<?> var1, World var2, BlockPos var3) {
      this(☃, ☃);
      this.field_174861_a = ☃;
   }

   @Override
   protected void func_70088_a() {
   }

   protected void func_174859_a(EnumFacing var1) {
      Validate.notNull(☃);
      Validate.isTrue(☃.func_176740_k().func_176722_c());
      this.field_174860_b = ☃;
      this.field_70177_z = (float)(this.field_174860_b.func_176736_b() * 90);
      this.field_70126_B = this.field_70177_z;
      this.func_174856_o();
   }

   protected void func_174856_o() {
      if (this.field_174860_b != null) {
         double ☃ = (double)this.field_174861_a.func_177958_n() + 0.5;
         double ☃x = (double)this.field_174861_a.func_177956_o() + 0.5;
         double ☃xx = (double)this.field_174861_a.func_177952_p() + 0.5;
         double ☃xxx = 0.46875;
         double ☃xxxx = this.func_190202_a(this.func_82329_d());
         double ☃xxxxx = this.func_190202_a(this.func_82330_g());
         ☃ -= (double)this.field_174860_b.func_82601_c() * 0.46875;
         ☃xx -= (double)this.field_174860_b.func_82599_e() * 0.46875;
         ☃x += ☃xxxxx;
         EnumFacing ☃xxxxxx = this.field_174860_b.func_176735_f();
         ☃ += ☃xxxx * (double)☃xxxxxx.func_82601_c();
         ☃xx += ☃xxxx * (double)☃xxxxxx.func_82599_e();
         this.field_70165_t = ☃;
         this.field_70163_u = ☃x;
         this.field_70161_v = ☃xx;
         double ☃xxxxxxx = (double)this.func_82329_d();
         double ☃xxxxxxxx = (double)this.func_82330_g();
         double ☃xxxxxxxxx = (double)this.func_82329_d();
         if (this.field_174860_b.func_176740_k() == EnumFacing.Axis.Z) {
            ☃xxxxxxxxx = 1.0;
         } else {
            ☃xxxxxxx = 1.0;
         }

         ☃xxxxxxx /= 32.0;
         ☃xxxxxxxx /= 32.0;
         ☃xxxxxxxxx /= 32.0;
         this.func_174826_a(new AxisAlignedBB(☃ - ☃xxxxxxx, ☃x - ☃xxxxxxxx, ☃xx - ☃xxxxxxxxx, ☃ + ☃xxxxxxx, ☃x + ☃xxxxxxxx, ☃xx + ☃xxxxxxxxx));
      }
   }

   private double func_190202_a(int var1) {
      return ☃ % 32 == 0 ? 0.5 : 0.0;
   }

   @Override
   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      if (this.field_70520_f++ == 100 && !this.field_70170_p.field_72995_K) {
         this.field_70520_f = 0;
         if (!this.field_70128_L && !this.func_70518_d()) {
            this.func_70106_y();
            this.func_110128_b(null);
         }
      }
   }

   public boolean func_70518_d() {
      if (!this.field_70170_p.func_195586_b(this, this.func_174813_aQ())) {
         return false;
      } else {
         int ☃ = Math.max(1, this.func_82329_d() / 16);
         int ☃x = Math.max(1, this.func_82330_g() / 16);
         BlockPos ☃xx = this.field_174861_a.func_177972_a(this.field_174860_b.func_176734_d());
         EnumFacing ☃xxx = this.field_174860_b.func_176735_f();
         BlockPos.MutableBlockPos ☃xxxx = new BlockPos.MutableBlockPos();

         for(int ☃xxxxx = 0; ☃xxxxx < ☃; ++☃xxxxx) {
            for(int ☃xxxxxx = 0; ☃xxxxxx < ☃x; ++☃xxxxxx) {
               int ☃xxxxxxx = (☃ - 1) / -2;
               int ☃xxxxxxxx = (☃x - 1) / -2;
               ☃xxxx.func_189533_g(☃xx).func_189534_c(☃xxx, ☃xxxxx + ☃xxxxxxx).func_189534_c(EnumFacing.UP, ☃xxxxxx + ☃xxxxxxxx);
               IBlockState ☃xxxxxxxxx = this.field_70170_p.func_180495_p(☃xxxx);
               if (!☃xxxxxxxxx.func_185904_a().func_76220_a() && !BlockRedstoneDiode.func_185546_B(☃xxxxxxxxx)) {
                  return false;
               }
            }
         }

         return this.field_70170_p.func_175674_a(this, this.func_174813_aQ(), field_184524_c).isEmpty();
      }
   }

   @Override
   public boolean func_70067_L() {
      return true;
   }

   @Override
   public boolean func_85031_j(Entity var1) {
      return ☃ instanceof EntityPlayer ? this.func_70097_a(DamageSource.func_76365_a((EntityPlayer)☃), 0.0F) : false;
   }

   @Override
   public EnumFacing func_174811_aO() {
      return this.field_174860_b;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         if (!this.field_70128_L && !this.field_70170_p.field_72995_K) {
            this.func_70106_y();
            this.func_70018_K();
            this.func_110128_b(☃.func_76346_g());
         }

         return true;
      }
   }

   @Override
   public void func_70091_d(MoverType var1, double var2, double var4, double var6) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L && ☃ * ☃ + ☃ * ☃ + ☃ * ☃ > 0.0) {
         this.func_70106_y();
         this.func_110128_b(null);
      }
   }

   @Override
   public void func_70024_g(double var1, double var3, double var5) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L && ☃ * ☃ + ☃ * ☃ + ☃ * ☃ > 0.0) {
         this.func_70106_y();
         this.func_110128_b(null);
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      ☃.func_74774_a("Facing", (byte)this.field_174860_b.func_176736_b());
      BlockPos ☃ = this.func_174857_n();
      ☃.func_74768_a("TileX", ☃.func_177958_n());
      ☃.func_74768_a("TileY", ☃.func_177956_o());
      ☃.func_74768_a("TileZ", ☃.func_177952_p());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      this.field_174861_a = new BlockPos(☃.func_74762_e("TileX"), ☃.func_74762_e("TileY"), ☃.func_74762_e("TileZ"));
      this.func_174859_a(EnumFacing.func_176731_b(☃.func_74771_c("Facing")));
   }

   public abstract int func_82329_d();

   public abstract int func_82330_g();

   public abstract void func_110128_b(@Nullable Entity var1);

   public abstract void func_184523_o();

   @Override
   public EntityItem func_70099_a(ItemStack var1, float var2) {
      EntityItem ☃ = new EntityItem(
         this.field_70170_p,
         this.field_70165_t + (double)((float)this.field_174860_b.func_82601_c() * 0.15F),
         this.field_70163_u + (double)☃,
         this.field_70161_v + (double)((float)this.field_174860_b.func_82599_e() * 0.15F),
         ☃
      );
      ☃.func_174869_p();
      this.field_70170_p.func_72838_d(☃);
      return ☃;
   }

   @Override
   protected boolean func_142008_O() {
      return false;
   }

   @Override
   public void func_70107_b(double var1, double var3, double var5) {
      this.field_174861_a = new BlockPos(☃, ☃, ☃);
      this.func_174856_o();
      this.field_70160_al = true;
   }

   public BlockPos func_174857_n() {
      return this.field_174861_a;
   }

   @Override
   public float func_184229_a(Rotation var1) {
      if (this.field_174860_b != null && this.field_174860_b.func_176740_k() != EnumFacing.Axis.Y) {
         switch(☃) {
            case CLOCKWISE_180:
               this.field_174860_b = this.field_174860_b.func_176734_d();
               break;
            case COUNTERCLOCKWISE_90:
               this.field_174860_b = this.field_174860_b.func_176735_f();
               break;
            case CLOCKWISE_90:
               this.field_174860_b = this.field_174860_b.func_176746_e();
         }
      }

      float ☃ = MathHelper.func_76142_g(this.field_70177_z);
      switch(☃) {
         case CLOCKWISE_180:
            return ☃ + 180.0F;
         case COUNTERCLOCKWISE_90:
            return ☃ + 90.0F;
         case CLOCKWISE_90:
            return ☃ + 270.0F;
         default:
            return ☃;
      }
   }

   @Override
   public float func_184217_a(Mirror var1) {
      return this.func_184229_a(☃.func_185800_a(this.field_174860_b));
   }

   @Override
   public void func_70077_a(EntityLightningBolt var1) {
   }
}
