package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.PistonType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class TileEntityPiston extends TileEntity implements ITickable {
   private IBlockState field_200231_a;
   private EnumFacing field_174931_f;
   private boolean field_145875_k;
   private boolean field_145872_l;
   private static final ThreadLocal<EnumFacing> field_190613_i = new ThreadLocal<EnumFacing>() {
      protected EnumFacing initialValue() {
         return null;
      }
   };
   private float field_145873_m;
   private float field_145870_n;
   private long field_211147_k;

   public TileEntityPiston() {
      super(TileEntityType.field_200980_k);
   }

   public TileEntityPiston(IBlockState var1, EnumFacing var2, boolean var3, boolean var4) {
      this();
      this.field_200231_a = ☃;
      this.field_174931_f = ☃;
      this.field_145875_k = ☃;
      this.field_145872_l = ☃;
   }

   @Override
   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public boolean func_145868_b() {
      return this.field_145875_k;
   }

   public EnumFacing func_212363_d() {
      return this.field_174931_f;
   }

   public boolean func_145867_d() {
      return this.field_145872_l;
   }

   public float func_145860_a(float var1) {
      if (☃ > 1.0F) {
         ☃ = 1.0F;
      }

      return this.field_145870_n + (this.field_145873_m - this.field_145870_n) * ☃;
   }

   public float func_174929_b(float var1) {
      return (float)this.field_174931_f.func_82601_c() * this.func_184320_e(this.func_145860_a(☃));
   }

   public float func_174928_c(float var1) {
      return (float)this.field_174931_f.func_96559_d() * this.func_184320_e(this.func_145860_a(☃));
   }

   public float func_174926_d(float var1) {
      return (float)this.field_174931_f.func_82599_e() * this.func_184320_e(this.func_145860_a(☃));
   }

   private float func_184320_e(float var1) {
      return this.field_145875_k ? ☃ - 1.0F : 1.0F - ☃;
   }

   private IBlockState func_190606_j() {
      return !this.func_145868_b() && this.func_145867_d()
         ? Blocks.field_150332_K
            .func_176223_P()
            .func_206870_a(
               BlockPistonExtension.field_176325_b, this.field_200231_a.func_177230_c() == Blocks.field_150320_F ? PistonType.STICKY : PistonType.DEFAULT
            )
            .func_206870_a(BlockPistonExtension.field_176387_N, this.field_200231_a.func_177229_b(BlockPistonBase.field_176387_N))
         : this.field_200231_a;
   }

   private void func_184322_i(float var1) {
      EnumFacing ☃ = this.func_195509_h();
      double ☃x = (double)(☃ - this.field_145873_m);
      VoxelShape ☃xx = this.func_190606_j().func_196952_d(this.field_145850_b, this.func_174877_v());
      if (!☃xx.func_197766_b()) {
         List<AxisAlignedBB> ☃xxx = ☃xx.func_197756_d();
         AxisAlignedBB ☃xxxx = this.func_190607_a(this.func_191515_a(☃xxx));
         List<Entity> ☃xxxxx = this.field_145850_b.func_72839_b(null, this.func_190610_a(☃xxxx, ☃, ☃x).func_111270_a(☃xxxx));
         if (!☃xxxxx.isEmpty()) {
            boolean ☃xxxxxx = this.field_200231_a.func_177230_c() == Blocks.field_180399_cE;

            for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxx.size(); ++☃xxxxxxx) {
               Entity ☃xxxxxxxx = (Entity)☃xxxxx.get(☃xxxxxxx);
               if (☃xxxxxxxx.func_184192_z() != EnumPushReaction.IGNORE) {
                  if (☃xxxxxx) {
                     switch(☃.func_176740_k()) {
                        case X:
                           ☃xxxxxxxx.field_70159_w = (double)☃.func_82601_c();
                           break;
                        case Y:
                           ☃xxxxxxxx.field_70181_x = (double)☃.func_96559_d();
                           break;
                        case Z:
                           ☃xxxxxxxx.field_70179_y = (double)☃.func_82599_e();
                     }
                  }

                  double ☃xxxxxxxxx = 0.0;

                  for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃xxx.size(); ++☃xxxxxxxxxx) {
                     AxisAlignedBB ☃xxxxxxxxxxx = this.func_190610_a(this.func_190607_a((AxisAlignedBB)☃xxx.get(☃xxxxxxxxxx)), ☃, ☃x);
                     AxisAlignedBB ☃xxxxxxxxxxxx = ☃xxxxxxxx.func_174813_aQ();
                     if (☃xxxxxxxxxxx.func_72326_a(☃xxxxxxxxxxxx)) {
                        ☃xxxxxxxxx = Math.max(☃xxxxxxxxx, this.func_190612_a(☃xxxxxxxxxxx, ☃, ☃xxxxxxxxxxxx));
                        if (☃xxxxxxxxx >= ☃x) {
                           break;
                        }
                     }
                  }

                  if (!(☃xxxxxxxxx <= 0.0)) {
                     ☃xxxxxxxxx = Math.min(☃xxxxxxxxx, ☃x) + 0.01;
                     field_190613_i.set(☃);
                     ☃xxxxxxxx.func_70091_d(
                        MoverType.PISTON, ☃xxxxxxxxx * (double)☃.func_82601_c(), ☃xxxxxxxxx * (double)☃.func_96559_d(), ☃xxxxxxxxx * (double)☃.func_82599_e()
                     );
                     field_190613_i.set(null);
                     if (!this.field_145875_k && this.field_145872_l) {
                        this.func_190605_a(☃xxxxxxxx, ☃, ☃x);
                     }
                  }
               }
            }
         }
      }
   }

   public EnumFacing func_195509_h() {
      return this.field_145875_k ? this.field_174931_f : this.field_174931_f.func_176734_d();
   }

   private AxisAlignedBB func_191515_a(List<AxisAlignedBB> var1) {
      double ☃ = 0.0;
      double ☃x = 0.0;
      double ☃xx = 0.0;
      double ☃xxx = 1.0;
      double ☃xxxx = 1.0;
      double ☃xxxxx = 1.0;

      for(AxisAlignedBB ☃xxxxxx : ☃) {
         ☃ = Math.min(☃xxxxxx.field_72340_a, ☃);
         ☃x = Math.min(☃xxxxxx.field_72338_b, ☃x);
         ☃xx = Math.min(☃xxxxxx.field_72339_c, ☃xx);
         ☃xxx = Math.max(☃xxxxxx.field_72336_d, ☃xxx);
         ☃xxxx = Math.max(☃xxxxxx.field_72337_e, ☃xxxx);
         ☃xxxxx = Math.max(☃xxxxxx.field_72334_f, ☃xxxxx);
      }

      return new AxisAlignedBB(☃, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
   }

   private double func_190612_a(AxisAlignedBB var1, EnumFacing var2, AxisAlignedBB var3) {
      switch(☃.func_176740_k()) {
         case X:
            return func_190611_b(☃, ☃, ☃);
         case Y:
         default:
            return func_190608_c(☃, ☃, ☃);
         case Z:
            return func_190604_d(☃, ☃, ☃);
      }
   }

   private AxisAlignedBB func_190607_a(AxisAlignedBB var1) {
      double ☃ = (double)this.func_184320_e(this.field_145873_m);
      return ☃.func_72317_d(
         (double)this.field_174879_c.func_177958_n() + ☃ * (double)this.field_174931_f.func_82601_c(),
         (double)this.field_174879_c.func_177956_o() + ☃ * (double)this.field_174931_f.func_96559_d(),
         (double)this.field_174879_c.func_177952_p() + ☃ * (double)this.field_174931_f.func_82599_e()
      );
   }

   private AxisAlignedBB func_190610_a(AxisAlignedBB var1, EnumFacing var2, double var3) {
      double ☃ = ☃ * (double)☃.func_176743_c().func_179524_a();
      double ☃x = Math.min(☃, 0.0);
      double ☃xx = Math.max(☃, 0.0);
      switch(☃) {
         case WEST:
            return new AxisAlignedBB(☃.field_72340_a + ☃x, ☃.field_72338_b, ☃.field_72339_c, ☃.field_72340_a + ☃xx, ☃.field_72337_e, ☃.field_72334_f);
         case EAST:
            return new AxisAlignedBB(☃.field_72336_d + ☃x, ☃.field_72338_b, ☃.field_72339_c, ☃.field_72336_d + ☃xx, ☃.field_72337_e, ☃.field_72334_f);
         case DOWN:
            return new AxisAlignedBB(☃.field_72340_a, ☃.field_72338_b + ☃x, ☃.field_72339_c, ☃.field_72336_d, ☃.field_72338_b + ☃xx, ☃.field_72334_f);
         case UP:
         default:
            return new AxisAlignedBB(☃.field_72340_a, ☃.field_72337_e + ☃x, ☃.field_72339_c, ☃.field_72336_d, ☃.field_72337_e + ☃xx, ☃.field_72334_f);
         case NORTH:
            return new AxisAlignedBB(☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c + ☃x, ☃.field_72336_d, ☃.field_72337_e, ☃.field_72339_c + ☃xx);
         case SOUTH:
            return new AxisAlignedBB(☃.field_72340_a, ☃.field_72338_b, ☃.field_72334_f + ☃x, ☃.field_72336_d, ☃.field_72337_e, ☃.field_72334_f + ☃xx);
      }
   }

   private void func_190605_a(Entity var1, EnumFacing var2, double var3) {
      AxisAlignedBB ☃ = ☃.func_174813_aQ();
      AxisAlignedBB ☃x = VoxelShapes.func_197868_b().func_197752_a().func_186670_a(this.field_174879_c);
      if (☃.func_72326_a(☃x)) {
         EnumFacing ☃xx = ☃.func_176734_d();
         double ☃xxx = this.func_190612_a(☃x, ☃xx, ☃) + 0.01;
         double ☃xxxx = this.func_190612_a(☃x, ☃xx, ☃.func_191500_a(☃x)) + 0.01;
         if (Math.abs(☃xxx - ☃xxxx) < 0.01) {
            ☃xxx = Math.min(☃xxx, ☃) + 0.01;
            field_190613_i.set(☃);
            ☃.func_70091_d(MoverType.PISTON, ☃xxx * (double)☃xx.func_82601_c(), ☃xxx * (double)☃xx.func_96559_d(), ☃xxx * (double)☃xx.func_82599_e());
            field_190613_i.set(null);
         }
      }
   }

   private static double func_190611_b(AxisAlignedBB var0, EnumFacing var1, AxisAlignedBB var2) {
      return ☃.func_176743_c() == EnumFacing.AxisDirection.POSITIVE ? ☃.field_72336_d - ☃.field_72340_a : ☃.field_72336_d - ☃.field_72340_a;
   }

   private static double func_190608_c(AxisAlignedBB var0, EnumFacing var1, AxisAlignedBB var2) {
      return ☃.func_176743_c() == EnumFacing.AxisDirection.POSITIVE ? ☃.field_72337_e - ☃.field_72338_b : ☃.field_72337_e - ☃.field_72338_b;
   }

   private static double func_190604_d(AxisAlignedBB var0, EnumFacing var1, AxisAlignedBB var2) {
      return ☃.func_176743_c() == EnumFacing.AxisDirection.POSITIVE ? ☃.field_72334_f - ☃.field_72339_c : ☃.field_72334_f - ☃.field_72339_c;
   }

   public IBlockState func_200230_i() {
      return this.field_200231_a;
   }

   public void func_145866_f() {
      if (this.field_145870_n < 1.0F && this.field_145850_b != null) {
         this.field_145873_m = 1.0F;
         this.field_145870_n = this.field_145873_m;
         this.field_145850_b.func_175713_t(this.field_174879_c);
         this.func_145843_s();
         if (this.field_145850_b.func_180495_p(this.field_174879_c).func_177230_c() == Blocks.field_196603_bb) {
            IBlockState ☃;
            if (this.field_145872_l) {
               ☃ = Blocks.field_150350_a.func_176223_P();
            } else {
               ☃ = Block.func_199770_b(this.field_200231_a, this.field_145850_b, this.field_174879_c);
            }

            this.field_145850_b.func_180501_a(this.field_174879_c, ☃, 3);
            this.field_145850_b.func_190524_a(this.field_174879_c, ☃.func_177230_c(), this.field_174879_c);
         }
      }
   }

   @Override
   public void func_73660_a() {
      this.field_211147_k = this.field_145850_b.func_82737_E();
      this.field_145870_n = this.field_145873_m;
      if (this.field_145870_n >= 1.0F) {
         this.field_145850_b.func_175713_t(this.field_174879_c);
         this.func_145843_s();
         if (this.field_200231_a != null && this.field_145850_b.func_180495_p(this.field_174879_c).func_177230_c() == Blocks.field_196603_bb) {
            IBlockState ☃ = Block.func_199770_b(this.field_200231_a, this.field_145850_b, this.field_174879_c);
            if (☃.func_196958_f()) {
               this.field_145850_b.func_180501_a(this.field_174879_c, this.field_200231_a, 84);
               Block.func_196263_a(this.field_200231_a, ☃, this.field_145850_b, this.field_174879_c, 3);
            } else {
               if (☃.func_196959_b(BlockStateProperties.field_208198_y) && ☃.func_177229_b(BlockStateProperties.field_208198_y)) {
                  ☃ = ☃.func_206870_a(BlockStateProperties.field_208198_y, Boolean.valueOf(false));
               }

               this.field_145850_b.func_180501_a(this.field_174879_c, ☃, 67);
               this.field_145850_b.func_190524_a(this.field_174879_c, ☃.func_177230_c(), this.field_174879_c);
            }
         }
      } else {
         float ☃ = this.field_145873_m + 0.5F;
         this.func_184322_i(☃);
         this.field_145873_m = ☃;
         if (this.field_145873_m >= 1.0F) {
            this.field_145873_m = 1.0F;
         }
      }
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_200231_a = NBTUtil.func_190008_d(☃.func_74775_l("blockState"));
      this.field_174931_f = EnumFacing.func_82600_a(☃.func_74762_e("facing"));
      this.field_145873_m = ☃.func_74760_g("progress");
      this.field_145870_n = this.field_145873_m;
      this.field_145875_k = ☃.func_74767_n("extending");
      this.field_145872_l = ☃.func_74767_n("source");
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      ☃.func_74782_a("blockState", NBTUtil.func_190009_a(this.field_200231_a));
      ☃.func_74768_a("facing", this.field_174931_f.func_176745_a());
      ☃.func_74776_a("progress", this.field_145870_n);
      ☃.func_74757_a("extending", this.field_145875_k);
      ☃.func_74757_a("source", this.field_145872_l);
      return ☃;
   }

   public VoxelShape func_195508_a(IBlockReader var1, BlockPos var2) {
      VoxelShape ☃;
      if (!this.field_145875_k && this.field_145872_l) {
         ☃ = this.field_200231_a.func_206870_a(BlockPistonBase.field_176320_b, Boolean.valueOf(true)).func_196952_d(☃, ☃);
      } else {
         ☃ = VoxelShapes.func_197880_a();
      }

      EnumFacing ☃ = (EnumFacing)field_190613_i.get();
      if ((double)this.field_145873_m < 1.0 && ☃ == this.func_195509_h()) {
         return ☃;
      } else {
         IBlockState ☃;
         if (this.func_145867_d()) {
            ☃ = Blocks.field_150332_K
               .func_176223_P()
               .func_206870_a(BlockPistonExtension.field_176387_N, this.field_174931_f)
               .func_206870_a(BlockPistonExtension.field_176327_M, Boolean.valueOf(this.field_145875_k != 1.0F - this.field_145873_m < 4.0F));
         } else {
            ☃ = this.field_200231_a;
         }

         float ☃ = this.func_184320_e(this.field_145873_m);
         double ☃x = (double)((float)this.field_174931_f.func_82601_c() * ☃);
         double ☃xx = (double)((float)this.field_174931_f.func_96559_d() * ☃);
         double ☃xxx = (double)((float)this.field_174931_f.func_82599_e() * ☃);
         return VoxelShapes.func_197872_a(☃, ☃.func_196952_d(☃, ☃).func_197751_a(☃x, ☃xx, ☃xxx));
      }
   }

   public long func_211146_k() {
      return this.field_211147_k;
   }
}
