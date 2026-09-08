package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.Particles;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockEnderChest extends BlockContainer implements IBucketPickupHandler, ILiquidContainer {
   public static final DirectionProperty field_176437_a = BlockHorizontal.field_185512_D;
   public static final BooleanProperty field_204615_b = BlockStateProperties.field_208198_y;
   protected static final VoxelShape field_196324_b = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);

   protected BlockEnderChest(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176437_a, EnumFacing.NORTH).func_206870_a(field_204615_b, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196324_b;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_190946_v(IBlockState var1) {
      return true;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Blocks.field_150343_Z;
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 8;
   }

   @Override
   protected boolean func_149700_E() {
      return true;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IFluidState ☃ = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      return this.func_176223_P()
         .func_206870_a(field_176437_a, ☃.func_195992_f().func_176734_d())
         .func_206870_a(field_204615_b, Boolean.valueOf(☃.func_206886_c() == Fluids.field_204546_a));
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      InventoryEnderChest ☃ = ☃.func_71005_bN();
      TileEntity ☃x = ☃.func_175625_s(☃);
      if (☃ == null || !(☃x instanceof TileEntityEnderChest)) {
         return true;
      } else if (☃.func_180495_p(☃.func_177984_a()).func_185915_l()) {
         return true;
      } else if (☃.field_72995_K) {
         return true;
      } else {
         ☃.func_146031_a((TileEntityEnderChest)☃x);
         ☃.func_71007_a(☃);
         ☃.func_195066_a(StatList.field_188090_X);
         return true;
      }
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityEnderChest();
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      for(int ☃ = 0; ☃ < 3; ++☃) {
         int ☃x = ☃.nextInt(2) * 2 - 1;
         int ☃xx = ☃.nextInt(2) * 2 - 1;
         double ☃xxx = (double)☃.func_177958_n() + 0.5 + 0.25 * (double)☃x;
         double ☃xxxx = (double)((float)☃.func_177956_o() + ☃.nextFloat());
         double ☃xxxxx = (double)☃.func_177952_p() + 0.5 + 0.25 * (double)☃xx;
         double ☃xxxxxx = (double)(☃.nextFloat() * (float)☃x);
         double ☃xxxxxxx = ((double)☃.nextFloat() - 0.5) * 0.125;
         double ☃xxxxxxxx = (double)(☃.nextFloat() * (float)☃xx);
         ☃.func_195594_a(Particles.field_197599_J, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx);
      }
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176437_a, ☃.func_185831_a(☃.func_177229_b(field_176437_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176437_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176437_a, field_204615_b);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_204615_b)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_204615_b, Boolean.valueOf(false)), 3);
         return Fluids.field_204546_a;
      } else {
         return Fluids.field_204541_a;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return ☃.func_177229_b(field_204615_b) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(☃);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return !☃.func_177229_b(field_204615_b) && ☃ == Fluids.field_204546_a;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      if (!☃.func_177229_b(field_204615_b) && ☃.func_206886_c() == Fluids.field_204546_a) {
         if (!☃.func_201670_d()) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_204615_b, Boolean.valueOf(true)), 3);
            ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_204615_b)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
