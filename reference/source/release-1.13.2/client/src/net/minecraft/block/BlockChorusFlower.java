package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockChorusFlower extends Block {
   public static final IntegerProperty field_185607_a = BlockStateProperties.field_208169_V;
   private final BlockChorusPlant field_196405_b;

   protected BlockChorusFlower(BlockChorusPlant var1, Block.Properties var2) {
      super(☃);
      this.field_196405_b = ☃;
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_185607_a, Integer.valueOf(0)));
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.func_196955_c(☃, ☃)) {
         ☃.func_175655_b(☃, true);
      } else {
         BlockPos ☃ = ☃.func_177984_a();
         if (☃.func_175623_d(☃) && ☃.func_177956_o() < 256) {
            int ☃x = ☃.func_177229_b(field_185607_a);
            if (☃x < 5) {
               boolean ☃xx = false;
               boolean ☃xxx = false;
               IBlockState ☃xxxx = ☃.func_180495_p(☃.func_177977_b());
               Block ☃xxxxx = ☃xxxx.func_177230_c();
               if (☃xxxxx == Blocks.field_150377_bs) {
                  ☃xx = true;
               } else if (☃xxxxx == this.field_196405_b) {
                  int ☃xx = 1;

                  for(int ☃xxx = 0; ☃xxx < 4; ++☃xxx) {
                     Block ☃xxxx = ☃.func_180495_p(☃.func_177979_c(☃xx + 1)).func_177230_c();
                     if (☃xxxx != this.field_196405_b) {
                        if (☃xxxx == Blocks.field_150377_bs) {
                           ☃xxx = true;
                        }
                        break;
                     }

                     ++☃xx;
                  }

                  if (☃xx < 2 || ☃xx <= ☃.nextInt(☃xxx ? 5 : 4)) {
                     ☃xx = true;
                  }
               } else if (☃xxxx.func_196958_f()) {
                  ☃xx = true;
               }

               if (☃xx && func_185604_a(☃, ☃, null) && ☃.func_175623_d(☃.func_177981_b(2))) {
                  ☃.func_180501_a(☃, this.field_196405_b.func_196497_a(☃, ☃), 2);
                  this.func_185602_a(☃, ☃, ☃x);
               } else if (☃x < 4) {
                  int ☃xx = ☃.nextInt(4);
                  if (☃xxx) {
                     ++☃xx;
                  }

                  boolean ☃xx = false;

                  for(int ☃xxx = 0; ☃xxx < ☃xx; ++☃xxx) {
                     EnumFacing ☃xxxx = EnumFacing.Plane.HORIZONTAL.func_179518_a(☃);
                     BlockPos ☃xxxxx = ☃.func_177972_a(☃xxxx);
                     if (☃.func_175623_d(☃xxxxx) && ☃.func_175623_d(☃xxxxx.func_177977_b()) && func_185604_a(☃, ☃xxxxx, ☃xxxx.func_176734_d())) {
                        this.func_185602_a(☃, ☃xxxxx, ☃x + 1);
                        ☃xx = true;
                     }
                  }

                  if (☃xx) {
                     ☃.func_180501_a(☃, this.field_196405_b.func_196497_a(☃, ☃), 2);
                  } else {
                     this.func_185605_c(☃, ☃);
                  }
               } else {
                  this.func_185605_c(☃, ☃);
               }
            }
         }
      }
   }

   private void func_185602_a(World var1, BlockPos var2, int var3) {
      ☃.func_180501_a(☃, this.func_176223_P().func_206870_a(field_185607_a, Integer.valueOf(☃)), 2);
      ☃.func_175718_b(1033, ☃, 0);
   }

   private void func_185605_c(World var1, BlockPos var2) {
      ☃.func_180501_a(☃, this.func_176223_P().func_206870_a(field_185607_a, Integer.valueOf(5)), 2);
      ☃.func_175718_b(1034, ☃, 0);
   }

   private static boolean func_185604_a(IWorldReaderBase var0, BlockPos var1, @Nullable EnumFacing var2) {
      for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
         if (☃ != ☃ && !☃.func_175623_d(☃.func_177972_a(☃))) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃ != EnumFacing.UP && !☃.func_196955_c(☃, ☃)) {
         ☃.func_205220_G_().func_205360_a(☃, this, 1);
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
      Block ☃x = ☃.func_177230_c();
      if (☃x != this.field_196405_b && ☃x != Blocks.field_150377_bs) {
         if (!☃.func_196958_f()) {
            return false;
         } else {
            boolean ☃xx = false;

            for(EnumFacing ☃xxx : EnumFacing.Plane.HORIZONTAL) {
               IBlockState ☃xxxx = ☃.func_180495_p(☃.func_177972_a(☃xxx));
               if (☃xxxx.func_177230_c() == this.field_196405_b) {
                  if (☃xx) {
                     return false;
                  }

                  ☃xx = true;
               } else if (!☃xxxx.func_196958_f()) {
                  return false;
               }
            }

            return ☃xx;
         }
      } else {
         return true;
      }
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      super.func_180657_a(☃, ☃, ☃, ☃, ☃, ☃);
      func_180635_a(☃, ☃, new ItemStack(this));
   }

   @Override
   protected ItemStack func_180643_i(IBlockState var1) {
      return ItemStack.field_190927_a;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185607_a);
   }

   public static void func_185603_a(IWorld var0, BlockPos var1, Random var2, int var3) {
      ☃.func_180501_a(☃, ((BlockChorusPlant)Blocks.field_185765_cR).func_196497_a(☃, ☃), 2);
      func_185601_a(☃, ☃, ☃, ☃, ☃, 0);
   }

   private static void func_185601_a(IWorld var0, BlockPos var1, Random var2, BlockPos var3, int var4, int var5) {
      BlockChorusPlant ☃ = (BlockChorusPlant)Blocks.field_185765_cR;
      int ☃x = ☃.nextInt(4) + 1;
      if (☃ == 0) {
         ++☃x;
      }

      for(int ☃ = 0; ☃ < ☃x; ++☃) {
         BlockPos ☃x = ☃.func_177981_b(☃ + 1);
         if (!func_185604_a(☃, ☃x, null)) {
            return;
         }

         ☃.func_180501_a(☃x, ☃.func_196497_a(☃, ☃x), 2);
         ☃.func_180501_a(☃x.func_177977_b(), ☃.func_196497_a(☃, ☃x.func_177977_b()), 2);
      }

      boolean ☃ = false;
      if (☃ < 4) {
         int ☃x = ☃.nextInt(4);
         if (☃ == 0) {
            ++☃x;
         }

         for(int ☃x = 0; ☃x < ☃x; ++☃x) {
            EnumFacing ☃xx = EnumFacing.Plane.HORIZONTAL.func_179518_a(☃);
            BlockPos ☃xxx = ☃.func_177981_b(☃x).func_177972_a(☃xx);
            if (Math.abs(☃xxx.func_177958_n() - ☃.func_177958_n()) < ☃
               && Math.abs(☃xxx.func_177952_p() - ☃.func_177952_p()) < ☃
               && ☃.func_175623_d(☃xxx)
               && ☃.func_175623_d(☃xxx.func_177977_b())
               && func_185604_a(☃, ☃xxx, ☃xx.func_176734_d())) {
               ☃ = true;
               ☃.func_180501_a(☃xxx, ☃.func_196497_a(☃, ☃xxx), 2);
               ☃.func_180501_a(☃xxx.func_177972_a(☃xx.func_176734_d()), ☃.func_196497_a(☃, ☃xxx.func_177972_a(☃xx.func_176734_d())), 2);
               func_185601_a(☃, ☃xxx, ☃, ☃, ☃, ☃ + 1);
            }
         }
      }

      if (!☃) {
         ☃.func_180501_a(☃.func_177981_b(☃x), Blocks.field_185766_cS.func_176223_P().func_206870_a(field_185607_a, Integer.valueOf(5)), 2);
      }
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
