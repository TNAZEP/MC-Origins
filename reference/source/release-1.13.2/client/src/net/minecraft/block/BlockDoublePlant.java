package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockDoublePlant extends BlockBush {
   public static final EnumProperty<DoubleBlockHalf> field_176492_b = BlockStateProperties.field_208163_P;

   public BlockDoublePlant(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176492_b, DoubleBlockHalf.LOWER));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      DoubleBlockHalf ☃ = ☃.func_177229_b(field_176492_b);
      if (☃.func_176740_k() != EnumFacing.Axis.Y
         || ☃ == DoubleBlockHalf.LOWER != (☃ == EnumFacing.UP)
         || ☃.func_177230_c() == this && ☃.func_177229_b(field_176492_b) != ☃) {
         return ☃ == DoubleBlockHalf.LOWER && ☃ == EnumFacing.DOWN && !☃.func_196955_c(☃, ☃)
            ? Blocks.field_150350_a.func_176223_P()
            : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      } else {
         return Blocks.field_150350_a.func_176223_P();
      }
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      BlockPos ☃ = ☃.func_195995_a();
      return ☃.func_177956_o() < 255 && ☃.func_195991_k().func_180495_p(☃.func_177984_a()).func_196953_a(☃) ? super.func_196258_a(☃) : null;
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      ☃.func_180501_a(☃.func_177984_a(), this.func_176223_P().func_206870_a(field_176492_b, DoubleBlockHalf.UPPER), 3);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      if (☃.func_177229_b(field_176492_b) != DoubleBlockHalf.UPPER) {
         return super.func_196260_a(☃, ☃, ☃);
      } else {
         IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
         return ☃.func_177230_c() == this && ☃.func_177229_b(field_176492_b) == DoubleBlockHalf.LOWER;
      }
   }

   public void func_196390_a(IWorld var1, BlockPos var2, int var3) {
      ☃.func_180501_a(☃, this.func_176223_P().func_206870_a(field_176492_b, DoubleBlockHalf.LOWER), ☃);
      ☃.func_180501_a(☃.func_177984_a(), this.func_176223_P().func_206870_a(field_176492_b, DoubleBlockHalf.UPPER), ☃);
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      super.func_180657_a(☃, ☃, ☃, Blocks.field_150350_a.func_176223_P(), ☃, ☃);
   }

   @Override
   public void func_176208_a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      DoubleBlockHalf ☃ = ☃.func_177229_b(field_176492_b);
      boolean ☃x = ☃ == DoubleBlockHalf.LOWER;
      BlockPos ☃xx = ☃x ? ☃.func_177984_a() : ☃.func_177977_b();
      IBlockState ☃xxx = ☃.func_180495_p(☃xx);
      if (☃xxx.func_177230_c() == this && ☃xxx.func_177229_b(field_176492_b) != ☃) {
         ☃.func_180501_a(☃xx, Blocks.field_150350_a.func_176223_P(), 35);
         ☃.func_180498_a(☃, 2001, ☃xx, Block.func_196246_j(☃xxx));
         if (!☃.field_72995_K && !☃.func_184812_l_()) {
            if (☃x) {
               this.func_196391_a(☃, ☃, ☃, ☃.func_184614_ca());
            } else {
               this.func_196391_a(☃xxx, ☃, ☃xx, ☃.func_184614_ca());
            }
         }
      }

      super.func_176208_a(☃, ☃, ☃, ☃);
   }

   protected void func_196391_a(IBlockState var1, World var2, BlockPos var3, ItemStack var4) {
      ☃.func_196949_c(☃, ☃, 0);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return (IItemProvider)(☃.func_177229_b(field_176492_b) == DoubleBlockHalf.LOWER ? super.func_199769_a(☃, ☃, ☃, ☃) : Items.field_190931_a);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176492_b);
   }

   @Override
   public Block.EnumOffsetType func_176218_Q() {
      return Block.EnumOffsetType.XZ;
   }

   @Override
   public long func_209900_a(IBlockState var1, BlockPos var2) {
      return MathHelper.func_180187_c(
         ☃.func_177958_n(), ☃.func_177979_c(☃.func_177229_b(field_176492_b) == DoubleBlockHalf.LOWER ? 0 : 1).func_177956_o(), ☃.func_177952_p()
      );
   }
}
