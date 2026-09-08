package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockFlowerPot extends Block {
   private static final Map<Block, Block> field_196451_b = Maps.<Block, Block>newHashMap();
   protected static final VoxelShape field_196450_a = Block.func_208617_a(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
   private final Block field_196452_c;

   public BlockFlowerPot(Block var1, Block.Properties var2) {
      super(☃);
      this.field_196452_c = ☃;
      field_196451_b.put(☃, this);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196450_a;
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
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      Item ☃x = ☃.func_77973_b();
      Block ☃xx = ☃x instanceof ItemBlock ? (Block)field_196451_b.getOrDefault(((ItemBlock)☃x).func_179223_d(), Blocks.field_150350_a) : Blocks.field_150350_a;
      boolean ☃xxx = ☃xx == Blocks.field_150350_a;
      boolean ☃xxxx = this.field_196452_c == Blocks.field_150350_a;
      if (☃xxx != ☃xxxx) {
         if (☃xxxx) {
            ☃.func_180501_a(☃, ☃xx.func_176223_P(), 3);
            ☃.func_195066_a(StatList.field_188088_V);
            if (!☃.field_71075_bZ.field_75098_d) {
               ☃.func_190918_g(1);
            }
         } else {
            ItemStack ☃xxxxx = new ItemStack(this.field_196452_c);
            if (☃.func_190926_b()) {
               ☃.func_184611_a(☃, ☃xxxxx);
            } else if (!☃.func_191521_c(☃xxxxx)) {
               ☃.func_71019_a(☃xxxxx, false);
            }

            ☃.func_180501_a(☃, Blocks.field_150457_bL.func_176223_P(), 3);
         }
      }

      return true;
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return this.field_196452_c == Blocks.field_150350_a ? super.func_185473_a(☃, ☃, ☃) : new ItemStack(this.field_196452_c);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Blocks.field_150457_bL;
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      super.func_196255_a(☃, ☃, ☃, ☃, ☃);
      if (this.field_196452_c != Blocks.field_150350_a) {
         func_180635_a(☃, ☃, new ItemStack(this.field_196452_c));
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃ == EnumFacing.DOWN && !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
