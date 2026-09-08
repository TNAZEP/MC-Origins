package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockSnowLayer extends Block {
   public static final IntegerProperty field_176315_a = BlockStateProperties.field_208129_ad;
   protected static final VoxelShape[] field_196508_b = new VoxelShape[]{
      VoxelShapes.func_197880_a(),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   protected BlockSnowLayer(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176315_a, Integer.valueOf(1)));
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      switch(☃) {
         case LAND:
            return ☃.func_177229_b(field_176315_a) < 5;
         case WATER:
            return false;
         case AIR:
            return false;
         default:
            return false;
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return ☃.func_177229_b(field_176315_a) == 8;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196508_b[☃.func_177229_b(field_176315_a)];
   }

   @Override
   public VoxelShape func_196268_f(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196508_b[☃.func_177229_b(field_176315_a) - 1];
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
      Block ☃x = ☃.func_177230_c();
      if (☃x != Blocks.field_150432_aD && ☃x != Blocks.field_150403_cj && ☃x != Blocks.field_180401_cv) {
         BlockFaceShape ☃xx = ☃.func_193401_d(☃, ☃.func_177977_b(), EnumFacing.UP);
         return ☃xx == BlockFaceShape.SOLID || ☃.func_203425_a(BlockTags.field_206952_E) || ☃x == this && ☃.func_177229_b(field_176315_a) == 8;
      } else {
         return false;
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      Integer ☃ = ☃.func_177229_b(field_176315_a);
      if (this.func_149700_E() && EnchantmentHelper.func_77506_a(Enchantments.field_185306_r, ☃) > 0) {
         if (☃ == 8) {
            func_180635_a(☃, ☃, new ItemStack(Blocks.field_196604_cC));
         } else {
            for(int ☃x = 0; ☃x < ☃; ++☃x) {
               func_180635_a(☃, ☃, this.func_180643_i(☃));
            }
         }
      } else {
         func_180635_a(☃, ☃, new ItemStack(Items.field_151126_ay, ☃));
      }

      ☃.func_175698_g(☃);
      ☃.func_71029_a(StatList.field_188065_ae.func_199076_b(this));
      ☃.func_71020_j(0.005F);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_175642_b(EnumLightType.BLOCK, ☃) > 11) {
         ☃.func_196949_c(☃, ☃, 0);
         ☃.func_175698_g(☃);
      }
   }

   @Override
   public boolean func_196253_a(IBlockState var1, BlockItemUseContext var2) {
      int ☃ = ☃.func_177229_b(field_176315_a);
      if (☃.func_195996_i().func_77973_b() != this.func_199767_j() || ☃ >= 8) {
         return ☃ == 1;
      } else if (☃.func_196012_c()) {
         return ☃.func_196000_l() == EnumFacing.UP;
      } else {
         return true;
      }
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a());
      if (☃.func_177230_c() == this) {
         int ☃x = ☃.func_177229_b(field_176315_a);
         return ☃.func_206870_a(field_176315_a, Integer.valueOf(Math.min(8, ☃x + 1)));
      } else {
         return super.func_196258_a(☃);
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176315_a);
   }

   @Override
   protected boolean func_149700_E() {
      return true;
   }
}
