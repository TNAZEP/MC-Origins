package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockFarmland extends Block {
   public static final IntegerProperty field_176531_a = BlockStateProperties.field_208133_ah;
   protected static final VoxelShape field_196432_b = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

   protected BlockFarmland(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176531_a, Integer.valueOf(0)));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃ == EnumFacing.UP && !☃.func_196955_c(☃, ☃)) {
         ☃.func_205220_G_().func_205360_a(☃, this, 1);
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177984_a());
      return !☃.func_185904_a().func_76220_a() || ☃.func_177230_c() instanceof BlockFenceGate;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return !this.func_176223_P().func_196955_c(☃.func_195991_k(), ☃.func_195995_a()) ? Blocks.field_150346_d.func_176223_P() : super.func_196258_a(☃);
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_201572_C();
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196432_b;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.func_196955_c(☃, ☃)) {
         func_199610_d(☃, ☃, ☃);
      } else {
         int ☃ = ☃.func_177229_b(field_176531_a);
         if (!func_176530_e(☃, ☃) && !☃.func_175727_C(☃.func_177984_a())) {
            if (☃ > 0) {
               ☃.func_180501_a(☃, ☃.func_206870_a(field_176531_a, Integer.valueOf(☃ - 1)), 2);
            } else if (!func_176529_d(☃, ☃)) {
               func_199610_d(☃, ☃, ☃);
            }
         } else if (☃ < 7) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_176531_a, Integer.valueOf(7)), 2);
         }
      }
   }

   @Override
   public void func_180658_a(World var1, BlockPos var2, Entity var3, float var4) {
      if (!☃.field_72995_K
         && ☃.field_73012_v.nextFloat() < ☃ - 0.5F
         && ☃ instanceof EntityLivingBase
         && (☃ instanceof EntityPlayer || ☃.func_82736_K().func_82766_b("mobGriefing"))
         && ☃.field_70130_N * ☃.field_70130_N * ☃.field_70131_O > 0.512F) {
         func_199610_d(☃.func_180495_p(☃), ☃, ☃);
      }

      super.func_180658_a(☃, ☃, ☃, ☃);
   }

   public static void func_199610_d(IBlockState var0, World var1, BlockPos var2) {
      ☃.func_175656_a(☃, func_199601_a(☃, Blocks.field_150346_d.func_176223_P(), ☃, ☃));
   }

   private static boolean func_176529_d(IBlockReader var0, BlockPos var1) {
      Block ☃ = ☃.func_180495_p(☃.func_177984_a()).func_177230_c();
      return ☃ instanceof BlockCrops || ☃ instanceof BlockStem || ☃ instanceof BlockAttachedStem;
   }

   private static boolean func_176530_e(IWorldReaderBase var0, BlockPos var1) {
      for(BlockPos.MutableBlockPos ☃ : BlockPos.func_177975_b(☃.func_177982_a(-4, 0, -4), ☃.func_177982_a(4, 1, 4))) {
         if (☃.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Blocks.field_150346_d;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176531_a);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
