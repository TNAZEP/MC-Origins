package net.minecraft.block;

import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockHugeMushroom extends Block {
   public static final BooleanProperty field_196459_a = BlockSixWay.field_196488_a;
   public static final BooleanProperty field_196461_b = BlockSixWay.field_196490_b;
   public static final BooleanProperty field_196463_c = BlockSixWay.field_196492_c;
   public static final BooleanProperty field_196464_y = BlockSixWay.field_196495_y;
   public static final BooleanProperty field_196465_z = BlockSixWay.field_196496_z;
   public static final BooleanProperty field_196460_A = BlockSixWay.field_196489_A;
   private static final Map<EnumFacing, BooleanProperty> field_196462_B = BlockSixWay.field_196491_B;
   @Nullable
   private final Block field_176379_b;

   public BlockHugeMushroom(@Nullable Block var1, Block.Properties var2) {
      super(☃);
      this.field_176379_b = ☃;
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_196459_a, Boolean.valueOf(true))
            .func_206870_a(field_196461_b, Boolean.valueOf(true))
            .func_206870_a(field_196463_c, Boolean.valueOf(true))
            .func_206870_a(field_196464_y, Boolean.valueOf(true))
            .func_206870_a(field_196465_z, Boolean.valueOf(true))
            .func_206870_a(field_196460_A, Boolean.valueOf(true))
      );
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return Math.max(0, ☃.nextInt(9) - 6);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return (IItemProvider)(this.field_176379_b == null ? Items.field_190931_a : this.field_176379_b);
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockReader ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      return this.func_176223_P()
         .func_206870_a(field_196460_A, Boolean.valueOf(this != ☃.func_180495_p(☃x.func_177977_b()).func_177230_c()))
         .func_206870_a(field_196465_z, Boolean.valueOf(this != ☃.func_180495_p(☃x.func_177984_a()).func_177230_c()))
         .func_206870_a(field_196459_a, Boolean.valueOf(this != ☃.func_180495_p(☃x.func_177978_c()).func_177230_c()))
         .func_206870_a(field_196461_b, Boolean.valueOf(this != ☃.func_180495_p(☃x.func_177974_f()).func_177230_c()))
         .func_206870_a(field_196463_c, Boolean.valueOf(this != ☃.func_180495_p(☃x.func_177968_d()).func_177230_c()))
         .func_206870_a(field_196464_y, Boolean.valueOf(this != ☃.func_180495_p(☃x.func_177976_e()).func_177230_c()));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃.func_177230_c() == this ? ☃.func_206870_a((IProperty)field_196462_B.get(☃), Boolean.valueOf(false)) : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a((IProperty)field_196462_B.get(☃.func_185831_a(EnumFacing.NORTH)), ☃.func_177229_b(field_196459_a))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185831_a(EnumFacing.SOUTH)), ☃.func_177229_b(field_196463_c))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185831_a(EnumFacing.EAST)), ☃.func_177229_b(field_196461_b))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185831_a(EnumFacing.WEST)), ☃.func_177229_b(field_196464_y))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185831_a(EnumFacing.UP)), ☃.func_177229_b(field_196465_z))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185831_a(EnumFacing.DOWN)), ☃.func_177229_b(field_196460_A));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_206870_a((IProperty)field_196462_B.get(☃.func_185803_b(EnumFacing.NORTH)), ☃.func_177229_b(field_196459_a))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185803_b(EnumFacing.SOUTH)), ☃.func_177229_b(field_196463_c))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185803_b(EnumFacing.EAST)), ☃.func_177229_b(field_196461_b))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185803_b(EnumFacing.WEST)), ☃.func_177229_b(field_196464_y))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185803_b(EnumFacing.UP)), ☃.func_177229_b(field_196465_z))
         .func_206870_a((IProperty)field_196462_B.get(☃.func_185803_b(EnumFacing.DOWN)), ☃.func_177229_b(field_196460_A));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196465_z, field_196460_A, field_196459_a, field_196461_b, field_196463_c, field_196464_y);
   }
}
