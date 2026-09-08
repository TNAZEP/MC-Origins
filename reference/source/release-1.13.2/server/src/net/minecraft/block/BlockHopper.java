package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockHopper extends BlockContainer {
   public static final DirectionProperty field_176430_a = BlockStateProperties.field_208156_I;
   public static final BooleanProperty field_176429_b = BlockStateProperties.field_208180_g;
   private static final VoxelShape field_196328_c = Block.func_208617_a(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape field_196339_z = Block.func_208617_a(4.0, 4.0, 4.0, 12.0, 10.0, 12.0);
   private static final VoxelShape field_199607_z = VoxelShapes.func_197872_a(field_196339_z, field_196328_c);
   private static final VoxelShape field_196326_A = VoxelShapes.func_197878_a(field_199607_z, IHopper.field_200101_a, IBooleanFunction.ONLY_FIRST);
   private static final VoxelShape field_196333_G = VoxelShapes.func_197872_a(field_196326_A, Block.func_208617_a(6.0, 0.0, 6.0, 10.0, 4.0, 10.0));
   private static final VoxelShape field_196334_H = VoxelShapes.func_197872_a(field_196326_A, Block.func_208617_a(12.0, 4.0, 6.0, 16.0, 8.0, 10.0));
   private static final VoxelShape field_196335_I = VoxelShapes.func_197872_a(field_196326_A, Block.func_208617_a(6.0, 4.0, 0.0, 10.0, 8.0, 4.0));
   private static final VoxelShape field_196336_J = VoxelShapes.func_197872_a(field_196326_A, Block.func_208617_a(6.0, 4.0, 12.0, 10.0, 8.0, 16.0));
   private static final VoxelShape field_196337_K = VoxelShapes.func_197872_a(field_196326_A, Block.func_208617_a(0.0, 4.0, 6.0, 4.0, 8.0, 10.0));
   private static final VoxelShape field_199602_G = IHopper.field_200101_a;
   private static final VoxelShape field_199603_H = VoxelShapes.func_197872_a(IHopper.field_200101_a, Block.func_208617_a(12.0, 8.0, 6.0, 16.0, 10.0, 10.0));
   private static final VoxelShape field_199604_I = VoxelShapes.func_197872_a(IHopper.field_200101_a, Block.func_208617_a(6.0, 8.0, 0.0, 10.0, 10.0, 4.0));
   private static final VoxelShape field_199605_J = VoxelShapes.func_197872_a(IHopper.field_200101_a, Block.func_208617_a(6.0, 8.0, 12.0, 10.0, 10.0, 16.0));
   private static final VoxelShape field_199606_K = VoxelShapes.func_197872_a(IHopper.field_200101_a, Block.func_208617_a(0.0, 8.0, 6.0, 4.0, 10.0, 10.0));

   public BlockHopper(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176430_a, EnumFacing.DOWN).func_206870_a(field_176429_b, Boolean.valueOf(true))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      switch((EnumFacing)☃.func_177229_b(field_176430_a)) {
         case DOWN:
            return field_196333_G;
         case NORTH:
            return field_196335_I;
         case SOUTH:
            return field_196336_J;
         case WEST:
            return field_196337_K;
         case EAST:
            return field_196334_H;
         default:
            return field_196326_A;
      }
   }

   @Override
   public VoxelShape func_199600_g(IBlockState var1, IBlockReader var2, BlockPos var3) {
      switch((EnumFacing)☃.func_177229_b(field_176430_a)) {
         case DOWN:
            return field_199602_G;
         case NORTH:
            return field_199604_I;
         case SOUTH:
            return field_199605_J;
         case WEST:
            return field_199606_K;
         case EAST:
            return field_199603_H;
         default:
            return IHopper.field_200101_a;
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      EnumFacing ☃ = ☃.func_196000_l().func_176734_d();
      return this.func_176223_P()
         .func_206870_a(field_176430_a, ☃.func_176740_k() == EnumFacing.Axis.Y ? EnumFacing.DOWN : ☃)
         .func_206870_a(field_176429_b, Boolean.valueOf(true));
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityHopper();
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (☃.func_82837_s()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityHopper) {
            ((TileEntityHopper)☃).func_200226_a(☃.func_200301_q());
         }
      }
   }

   @Override
   public boolean func_185481_k(IBlockState var1) {
      return true;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         this.func_176427_e(☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityHopper) {
            ☃.func_71007_a((TileEntityHopper)☃);
            ☃.func_195066_a(StatList.field_188084_R);
         }

         return true;
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      this.func_176427_e(☃, ☃, ☃);
   }

   private void func_176427_e(World var1, BlockPos var2, IBlockState var3) {
      boolean ☃ = !☃.func_175640_z(☃);
      if (☃ != ☃.func_177229_b(field_176429_b)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176429_b, Boolean.valueOf(☃)), 4);
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityHopper) {
            InventoryHelper.func_180175_a(☃, ☃, (TileEntityHopper)☃);
            ☃.func_175666_e(☃, this);
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      return Container.func_178144_a(☃.func_175625_s(☃));
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176430_a, ☃.func_185831_a(☃.func_177229_b(field_176430_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176430_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176430_a, field_176429_b);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.UP ? BlockFaceShape.BOWL : BlockFaceShape.UNDEFINED;
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntityHopper) {
         ((TileEntityHopper)☃).func_200113_a(☃);
      }
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
