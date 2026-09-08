package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockPistonMoving extends BlockContainer {
   public static final DirectionProperty field_196344_a = BlockPistonExtension.field_176387_N;
   public static final EnumProperty<PistonType> field_196345_b = BlockPistonExtension.field_176325_b;

   public BlockPistonMoving(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_196344_a, EnumFacing.NORTH).func_206870_a(field_196345_b, PistonType.DEFAULT));
   }

   @Nullable
   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return null;
   }

   public static TileEntity func_196343_a(IBlockState var0, EnumFacing var1, boolean var2, boolean var3) {
      return new TileEntityPiston(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityPiston) {
            ((TileEntityPiston)☃).func_145866_f();
         } else {
            super.func_196243_a(☃, ☃, ☃, ☃, ☃);
         }
      }
   }

   @Override
   public void func_176206_d(IWorld var1, BlockPos var2, IBlockState var3) {
      BlockPos ☃ = ☃.func_177972_a(((EnumFacing)☃.func_177229_b(field_196344_a)).func_176734_d());
      IBlockState ☃x = ☃.func_180495_p(☃);
      if (☃x.func_177230_c() instanceof BlockPistonBase && ☃x.func_177229_b(BlockPistonBase.field_176320_b)) {
         ☃.func_175698_g(☃);
      }
   }

   @Override
   public boolean func_200124_e(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (!☃.field_72995_K && ☃.func_175625_s(☃) == null) {
         ☃.func_175698_g(☃);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      if (!☃.field_72995_K) {
         TileEntityPiston ☃ = this.func_196342_a(☃, ☃);
         if (☃ != null) {
            ☃.func_200230_i().func_196949_c(☃, ☃, 0);
         }
      }
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return VoxelShapes.func_197880_a();
   }

   @Override
   public VoxelShape func_196268_f(IBlockState var1, IBlockReader var2, BlockPos var3) {
      TileEntityPiston ☃ = this.func_196342_a(☃, ☃);
      return ☃ != null ? ☃.func_195508_a(☃, ☃) : VoxelShapes.func_197880_a();
   }

   @Nullable
   private TileEntityPiston func_196342_a(IBlockReader var1, BlockPos var2) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      return ☃ instanceof TileEntityPiston ? (TileEntityPiston)☃ : null;
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return ItemStack.field_190927_a;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_196344_a, ☃.func_185831_a(☃.func_177229_b(field_196344_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_196344_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196344_a, field_196345_b);
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
