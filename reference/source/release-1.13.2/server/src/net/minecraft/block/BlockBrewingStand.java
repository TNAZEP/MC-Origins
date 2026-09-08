package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockBrewingStand extends BlockContainer {
   public static final BooleanProperty[] field_176451_a = new BooleanProperty[]{
      BlockStateProperties.field_208184_k, BlockStateProperties.field_208185_l, BlockStateProperties.field_208186_m
   };
   protected static final VoxelShape field_196308_b = VoxelShapes.func_197872_a(
      Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 2.0, 15.0), Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 14.0, 9.0)
   );

   public BlockBrewingStand(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176451_a[0], Boolean.valueOf(false))
            .func_206870_a(field_176451_a[1], Boolean.valueOf(false))
            .func_206870_a(field_176451_a[2], Boolean.valueOf(false))
      );
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityBrewingStand();
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196308_b;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityBrewingStand) {
            ☃.func_71007_a((TileEntityBrewingStand)☃);
            ☃.func_195066_a(StatList.field_188081_O);
         }

         return true;
      }
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (☃.func_82837_s()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityBrewingStand) {
            ((TileEntityBrewingStand)☃).func_200224_a(☃.func_200301_q());
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityBrewingStand) {
            InventoryHelper.func_180175_a(☃, ☃, (TileEntityBrewingStand)☃);
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
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
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176451_a[0], field_176451_a[1], field_176451_a[2]);
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
