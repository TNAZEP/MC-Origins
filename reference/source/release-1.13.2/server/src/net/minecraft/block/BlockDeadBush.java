package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockDeadBush extends BlockBush {
   protected static final VoxelShape field_196397_a = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   protected BlockDeadBush(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196397_a;
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      Block ☃ = ☃.func_177230_c();
      return ☃ == Blocks.field_150354_m
         || ☃ == Blocks.field_196611_F
         || ☃ == Blocks.field_150405_ch
         || ☃ == Blocks.field_196777_fo
         || ☃ == Blocks.field_196778_fp
         || ☃ == Blocks.field_196780_fq
         || ☃ == Blocks.field_196782_fr
         || ☃ == Blocks.field_196783_fs
         || ☃ == Blocks.field_196785_ft
         || ☃ == Blocks.field_196787_fu
         || ☃ == Blocks.field_196789_fv
         || ☃ == Blocks.field_196791_fw
         || ☃ == Blocks.field_196793_fx
         || ☃ == Blocks.field_196795_fy
         || ☃ == Blocks.field_196797_fz
         || ☃ == Blocks.field_196719_fA
         || ☃ == Blocks.field_196720_fB
         || ☃ == Blocks.field_196721_fC
         || ☃ == Blocks.field_196722_fD
         || ☃ == Blocks.field_150346_d
         || ☃ == Blocks.field_196660_k
         || ☃ == Blocks.field_196661_l;
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return ☃.nextInt(3);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_151055_y;
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      boolean ☃ = !☃.field_72995_K && ☃.func_77973_b() == Items.field_151097_aZ;
      if (☃) {
         func_180635_a(☃, ☃, new ItemStack(Blocks.field_196555_aI));
      }

      super.func_180657_a(☃, ☃, ☃, ☃ ? Blocks.field_150350_a.func_176223_P() : ☃, ☃, ☃);
   }
}
