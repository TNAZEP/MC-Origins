package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockCrops extends BlockBush implements IGrowable {
   public static final IntegerProperty field_176488_a = BlockStateProperties.field_208170_W;
   private static final VoxelShape[] field_196393_a = new VoxelShape[]{
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   protected BlockCrops(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(this.func_185524_e(), Integer.valueOf(0)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196393_a[☃.func_177229_b(this.func_185524_e())];
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177230_c() == Blocks.field_150458_ak;
   }

   public IntegerProperty func_185524_e() {
      return field_176488_a;
   }

   public int func_185526_g() {
      return 7;
   }

   protected int func_185527_x(IBlockState var1) {
      return ☃.func_177229_b(this.func_185524_e());
   }

   public IBlockState func_185528_e(int var1) {
      return this.func_176223_P().func_206870_a(this.func_185524_e(), Integer.valueOf(☃));
   }

   public boolean func_185525_y(IBlockState var1) {
      return ☃.func_177229_b(this.func_185524_e()) >= this.func_185526_g();
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      super.func_196267_b(☃, ☃, ☃, ☃);
      if (☃.func_201669_a(☃.func_177984_a(), 0) >= 9) {
         int ☃ = this.func_185527_x(☃);
         if (☃ < this.func_185526_g()) {
            float ☃x = func_180672_a(this, ☃, ☃);
            if (☃.nextInt((int)(25.0F / ☃x) + 1) == 0) {
               ☃.func_180501_a(☃, this.func_185528_e(☃ + 1), 2);
            }
         }
      }
   }

   public void func_176487_g(World var1, BlockPos var2, IBlockState var3) {
      int ☃ = this.func_185527_x(☃) + this.func_185529_b(☃);
      int ☃x = this.func_185526_g();
      if (☃ > ☃x) {
         ☃ = ☃x;
      }

      ☃.func_180501_a(☃, this.func_185528_e(☃), 2);
   }

   protected int func_185529_b(World var1) {
      return MathHelper.func_76136_a(☃.field_73012_v, 2, 5);
   }

   protected static float func_180672_a(Block var0, IBlockReader var1, BlockPos var2) {
      float ☃ = 1.0F;
      BlockPos ☃x = ☃.func_177977_b();

      for(int ☃xx = -1; ☃xx <= 1; ++☃xx) {
         for(int ☃xxx = -1; ☃xxx <= 1; ++☃xxx) {
            float ☃xxxx = 0.0F;
            IBlockState ☃xxxxx = ☃.func_180495_p(☃x.func_177982_a(☃xx, 0, ☃xxx));
            if (☃xxxxx.func_177230_c() == Blocks.field_150458_ak) {
               ☃xxxx = 1.0F;
               if (☃xxxxx.func_177229_b(BlockFarmland.field_176531_a) > 0) {
                  ☃xxxx = 3.0F;
               }
            }

            if (☃xx != 0 || ☃xxx != 0) {
               ☃xxxx /= 4.0F;
            }

            ☃ += ☃xxxx;
         }
      }

      BlockPos ☃xx = ☃.func_177978_c();
      BlockPos ☃xxx = ☃.func_177968_d();
      BlockPos ☃xxxx = ☃.func_177976_e();
      BlockPos ☃xxxxx = ☃.func_177974_f();
      boolean ☃xxxxxx = ☃ == ☃.func_180495_p(☃xxxx).func_177230_c() || ☃ == ☃.func_180495_p(☃xxxxx).func_177230_c();
      boolean ☃xxxxxxx = ☃ == ☃.func_180495_p(☃xx).func_177230_c() || ☃ == ☃.func_180495_p(☃xxx).func_177230_c();
      if (☃xxxxxx && ☃xxxxxxx) {
         ☃ /= 2.0F;
      } else {
         boolean ☃xx = ☃ == ☃.func_180495_p(☃xxxx.func_177978_c()).func_177230_c()
            || ☃ == ☃.func_180495_p(☃xxxxx.func_177978_c()).func_177230_c()
            || ☃ == ☃.func_180495_p(☃xxxxx.func_177968_d()).func_177230_c()
            || ☃ == ☃.func_180495_p(☃xxxx.func_177968_d()).func_177230_c();
         if (☃xx) {
            ☃ /= 2.0F;
         }
      }

      return ☃;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return (☃.func_201669_a(☃, 0) >= 8 || ☃.func_175678_i(☃)) && super.func_196260_a(☃, ☃, ☃);
   }

   protected IItemProvider func_199772_f() {
      return Items.field_151014_N;
   }

   protected IItemProvider func_199773_g() {
      return Items.field_151015_O;
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      super.func_196255_a(☃, ☃, ☃, ☃, 0);
      if (!☃.field_72995_K) {
         int ☃ = this.func_185527_x(☃);
         if (☃ >= this.func_185526_g()) {
            int ☃x = 3 + ☃;

            for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
               if (☃.field_73012_v.nextInt(2 * this.func_185526_g()) <= ☃) {
                  func_180635_a(☃, ☃, new ItemStack(this.func_199772_f()));
               }
            }
         }
      }
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return this.func_185525_y(☃) ? this.func_199773_g() : this.func_199772_f();
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return new ItemStack(this.func_199772_f());
   }

   @Override
   public boolean func_176473_a(IBlockReader var1, BlockPos var2, IBlockState var3, boolean var4) {
      return !this.func_185525_y(☃);
   }

   @Override
   public boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.func_176487_g(☃, ☃, ☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176488_a);
   }
}
