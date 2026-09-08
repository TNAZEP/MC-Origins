package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockFurnace extends BlockContainer {
   public static final DirectionProperty field_176447_a = BlockHorizontal.field_185512_D;
   public static final BooleanProperty field_196325_b = BlockRedstoneTorch.field_196528_a;

   protected BlockFurnace(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176447_a, EnumFacing.NORTH).func_206870_a(field_196325_b, Boolean.valueOf(false))
      );
   }

   @Override
   public int func_149750_m(IBlockState var1) {
      return ☃.func_177229_b(field_196325_b) ? super.func_149750_m(☃) : 0;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityFurnace) {
            ☃.func_71007_a((TileEntityFurnace)☃);
            ☃.func_195066_a(StatList.field_188061_aa);
         }

         return true;
      }
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityFurnace();
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_176447_a, ☃.func_195992_f().func_176734_d());
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (☃.func_82837_s()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityFurnace) {
            ((TileEntityFurnace)☃).func_200225_a(☃.func_200301_q());
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityFurnace) {
            InventoryHelper.func_180175_a(☃, ☃, (TileEntityFurnace)☃);
            ☃.func_175666_e(☃, this);
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
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_177229_b(field_196325_b)) {
         double ☃ = (double)☃.func_177958_n() + 0.5;
         double ☃x = (double)☃.func_177956_o();
         double ☃xx = (double)☃.func_177952_p() + 0.5;
         if (☃.nextDouble() < 0.1) {
            ☃.func_184134_a(☃, ☃x, ☃xx, SoundEvents.field_187652_bv, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         }

         EnumFacing ☃ = ☃.func_177229_b(field_176447_a);
         EnumFacing.Axis ☃x = ☃.func_176740_k();
         double ☃xx = 0.52;
         double ☃xxx = ☃.nextDouble() * 0.6 - 0.3;
         double ☃xxxx = ☃x == EnumFacing.Axis.X ? (double)☃.func_82601_c() * 0.52 : ☃xxx;
         double ☃xxxxx = ☃.nextDouble() * 6.0 / 16.0;
         double ☃xxxxxx = ☃x == EnumFacing.Axis.Z ? (double)☃.func_82599_e() * 0.52 : ☃xxx;
         ☃.func_195594_a(Particles.field_197601_L, ☃ + ☃xxxx, ☃x + ☃xxxxx, ☃xx + ☃xxxxxx, 0.0, 0.0, 0.0);
         ☃.func_195594_a(Particles.field_197631_x, ☃ + ☃xxxx, ☃x + ☃xxxxx, ☃xx + ☃xxxxxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176447_a, ☃.func_185831_a(☃.func_177229_b(field_176447_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176447_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176447_a, field_196325_b);
   }
}
