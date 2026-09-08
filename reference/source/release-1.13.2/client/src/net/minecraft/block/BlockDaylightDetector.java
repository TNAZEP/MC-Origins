package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockDaylightDetector extends BlockContainer {
   public static final IntegerProperty field_176436_a = BlockStateProperties.field_208136_ak;
   public static final BooleanProperty field_196320_b = BlockStateProperties.field_208188_o;
   protected static final VoxelShape field_196321_c = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

   public BlockDaylightDetector(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176436_a, Integer.valueOf(0)).func_206870_a(field_196320_b, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196321_c;
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_176436_a);
   }

   public static void func_196319_d(IBlockState var0, World var1, BlockPos var2) {
      if (☃.field_73011_w.func_191066_m()) {
         int ☃ = ☃.func_175642_b(EnumLightType.SKY, ☃) - ☃.func_175657_ab();
         float ☃x = ☃.func_72929_e(1.0F);
         boolean ☃xx = ☃.func_177229_b(field_196320_b);
         if (☃xx) {
            ☃ = 15 - ☃;
         } else if (☃ > 0) {
            float ☃ = ☃x < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            ☃x += (☃ - ☃x) * 0.2F;
            ☃ = Math.round((float)☃ * MathHelper.func_76134_b(☃x));
         }

         ☃ = MathHelper.func_76125_a(☃, 0, 15);
         if (☃.func_177229_b(field_176436_a) != ☃) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_176436_a, Integer.valueOf(☃)), 3);
         }
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.func_175142_cm()) {
         if (☃.field_72995_K) {
            return true;
         } else {
            IBlockState ☃ = ☃.func_177231_a(field_196320_b);
            ☃.func_180501_a(☃, ☃, 4);
            func_196319_d(☃, ☃, ☃);
            return true;
         }
      } else {
         return super.func_196250_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityDaylightDetector();
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176436_a, field_196320_b);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }
}
