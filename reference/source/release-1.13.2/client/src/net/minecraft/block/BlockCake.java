package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockCake extends Block {
   public static final IntegerProperty field_176589_a = BlockStateProperties.field_208173_Z;
   protected static final VoxelShape[] field_196402_b = new VoxelShape[]{
      Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.func_208617_a(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.func_208617_a(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.func_208617_a(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.func_208617_a(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.func_208617_a(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.func_208617_a(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)
   };

   protected BlockCake(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176589_a, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196402_b[☃.func_177229_b(field_176589_a)];
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (!☃.field_72995_K) {
         return this.func_180682_b(☃, ☃, ☃, ☃);
      } else {
         ItemStack ☃ = ☃.func_184586_b(☃);
         return this.func_180682_b(☃, ☃, ☃, ☃) || ☃.func_190926_b();
      }
   }

   private boolean func_180682_b(IWorld var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (!☃.func_71043_e(false)) {
         return false;
      } else {
         ☃.func_195066_a(StatList.field_188076_J);
         ☃.func_71024_bL().func_75122_a(2, 0.1F);
         int ☃ = ☃.func_177229_b(field_176589_a);
         if (☃ < 6) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_176589_a, Integer.valueOf(☃ + 1)), 3);
         } else {
            ☃.func_175698_g(☃);
         }

         return true;
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃ == EnumFacing.DOWN && !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return ☃.func_180495_p(☃.func_177977_b()).func_185904_a().func_76220_a();
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176589_a);
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      return (7 - ☃.func_177229_b(field_176589_a)) * 2;
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
