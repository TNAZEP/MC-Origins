package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockTallGrass extends BlockBush implements IGrowable {
   protected static final VoxelShape field_196389_a = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   protected BlockTallGrass(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196389_a;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return ☃.field_73012_v.nextInt(8) == 0 ? Items.field_151014_N : Items.field_190931_a;
   }

   @Override
   public int func_196251_a(IBlockState var1, int var2, World var3, BlockPos var4, Random var5) {
      return 1 + ☃.nextInt(☃ * 2 + 1);
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      if (!☃.field_72995_K && ☃.func_77973_b() == Items.field_151097_aZ) {
         ☃.func_71029_a(StatList.field_188065_ae.func_199076_b(this));
         ☃.func_71020_j(0.005F);
         func_180635_a(☃, ☃, new ItemStack(this));
      } else {
         super.func_180657_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_176473_a(IBlockReader var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockDoublePlant ☃ = (BlockDoublePlant)(this == Blocks.field_196554_aH ? Blocks.field_196805_gi : Blocks.field_196804_gh);
      if (☃.func_176223_P().func_196955_c(☃, ☃) && ☃.func_175623_d(☃.func_177984_a())) {
         ☃.func_196390_a(☃, ☃, 2);
      }
   }

   @Override
   public Block.EnumOffsetType func_176218_Q() {
      return Block.EnumOffsetType.XYZ;
   }
}
