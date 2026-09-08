package net.minecraft.block;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BlockFourWay extends Block implements IBucketPickupHandler, ILiquidContainer {
   public static final BooleanProperty field_196409_a = BlockSixWay.field_196488_a;
   public static final BooleanProperty field_196411_b = BlockSixWay.field_196490_b;
   public static final BooleanProperty field_196413_c = BlockSixWay.field_196492_c;
   public static final BooleanProperty field_196414_y = BlockSixWay.field_196495_y;
   public static final BooleanProperty field_204514_u = BlockStateProperties.field_208198_y;
   protected static final Map<EnumFacing, BooleanProperty> field_196415_z = (Map<EnumFacing, BooleanProperty>)BlockSixWay.field_196491_B
      .entrySet()
      .stream()
      .filter(var0 -> ((EnumFacing)var0.getKey()).func_176740_k().func_176722_c())
      .collect(Util.func_199749_a());
   protected final VoxelShape[] field_196410_A;
   protected final VoxelShape[] field_196412_B;

   protected BlockFourWay(float var1, float var2, float var3, float var4, float var5, Block.Properties var6) {
      super(☃);
      this.field_196410_A = this.func_196408_a(☃, ☃, ☃, 0.0F, ☃);
      this.field_196412_B = this.func_196408_a(☃, ☃, ☃, 0.0F, ☃);
   }

   protected VoxelShape[] func_196408_a(float var1, float var2, float var3, float var4, float var5) {
      float ☃ = 8.0F - ☃;
      float ☃x = 8.0F + ☃;
      float ☃xx = 8.0F - ☃;
      float ☃xxx = 8.0F + ☃;
      VoxelShape ☃xxxx = Block.func_208617_a((double)☃, 0.0, (double)☃, (double)☃x, (double)☃, (double)☃x);
      VoxelShape ☃xxxxx = Block.func_208617_a((double)☃xx, (double)☃, 0.0, (double)☃xxx, (double)☃, (double)☃xxx);
      VoxelShape ☃xxxxxx = Block.func_208617_a((double)☃xx, (double)☃, (double)☃xx, (double)☃xxx, (double)☃, 16.0);
      VoxelShape ☃xxxxxxx = Block.func_208617_a(0.0, (double)☃, (double)☃xx, (double)☃xxx, (double)☃, (double)☃xxx);
      VoxelShape ☃xxxxxxxx = Block.func_208617_a((double)☃xx, (double)☃, (double)☃xx, 16.0, (double)☃, (double)☃xxx);
      VoxelShape ☃xxxxxxxxx = VoxelShapes.func_197872_a(☃xxxxx, ☃xxxxxxxx);
      VoxelShape ☃xxxxxxxxxx = VoxelShapes.func_197872_a(☃xxxxxx, ☃xxxxxxx);
      VoxelShape[] ☃xxxxxxxxxxx = new VoxelShape[]{
         VoxelShapes.func_197880_a(),
         ☃xxxxxx,
         ☃xxxxxxx,
         ☃xxxxxxxxxx,
         ☃xxxxx,
         VoxelShapes.func_197872_a(☃xxxxxx, ☃xxxxx),
         VoxelShapes.func_197872_a(☃xxxxxxx, ☃xxxxx),
         VoxelShapes.func_197872_a(☃xxxxxxxxxx, ☃xxxxx),
         ☃xxxxxxxx,
         VoxelShapes.func_197872_a(☃xxxxxx, ☃xxxxxxxx),
         VoxelShapes.func_197872_a(☃xxxxxxx, ☃xxxxxxxx),
         VoxelShapes.func_197872_a(☃xxxxxxxxxx, ☃xxxxxxxx),
         ☃xxxxxxxxx,
         VoxelShapes.func_197872_a(☃xxxxxx, ☃xxxxxxxxx),
         VoxelShapes.func_197872_a(☃xxxxxxx, ☃xxxxxxxxx),
         VoxelShapes.func_197872_a(☃xxxxxxxxxx, ☃xxxxxxxxx)
      };

      for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx < 16; ++☃xxxxxxxxxxxx) {
         ☃xxxxxxxxxxx[☃xxxxxxxxxxxx] = VoxelShapes.func_197872_a(☃xxxx, ☃xxxxxxxxxxx[☃xxxxxxxxxxxx]);
      }

      return ☃xxxxxxxxxxx;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return this.field_196412_B[this.func_196406_i(☃)];
   }

   @Override
   public VoxelShape func_196268_f(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return this.field_196410_A[this.func_196406_i(☃)];
   }

   private static int func_196407_a(EnumFacing var0) {
      return 1 << ☃.func_176736_b();
   }

   protected int func_196406_i(IBlockState var1) {
      int ☃ = 0;
      if (☃.func_177229_b(field_196409_a)) {
         ☃ |= func_196407_a(EnumFacing.NORTH);
      }

      if (☃.func_177229_b(field_196411_b)) {
         ☃ |= func_196407_a(EnumFacing.EAST);
      }

      if (☃.func_177229_b(field_196413_c)) {
         ☃ |= func_196407_a(EnumFacing.SOUTH);
      }

      if (☃.func_177229_b(field_196414_y)) {
         ☃ |= func_196407_a(EnumFacing.WEST);
      }

      return ☃;
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_204514_u)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_204514_u, Boolean.valueOf(false)), 3);
         return Fluids.field_204546_a;
      } else {
         return Fluids.field_204541_a;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return ☃.func_177229_b(field_204514_u) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(☃);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return !☃.func_177229_b(field_204514_u) && ☃ == Fluids.field_204546_a;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      if (!☃.func_177229_b(field_204514_u) && ☃.func_206886_c() == Fluids.field_204546_a) {
         if (!☃.func_201670_d()) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_204514_u, Boolean.valueOf(true)), 3);
            ☃.func_205219_F_().func_205360_a(☃, ☃.func_206886_c(), ☃.func_206886_c().func_205569_a(☃));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      switch(☃) {
         case CLOCKWISE_180:
            return ☃.func_206870_a(field_196409_a, ☃.func_177229_b(field_196413_c))
               .func_206870_a(field_196411_b, ☃.func_177229_b(field_196414_y))
               .func_206870_a(field_196413_c, ☃.func_177229_b(field_196409_a))
               .func_206870_a(field_196414_y, ☃.func_177229_b(field_196411_b));
         case COUNTERCLOCKWISE_90:
            return ☃.func_206870_a(field_196409_a, ☃.func_177229_b(field_196411_b))
               .func_206870_a(field_196411_b, ☃.func_177229_b(field_196413_c))
               .func_206870_a(field_196413_c, ☃.func_177229_b(field_196414_y))
               .func_206870_a(field_196414_y, ☃.func_177229_b(field_196409_a));
         case CLOCKWISE_90:
            return ☃.func_206870_a(field_196409_a, ☃.func_177229_b(field_196414_y))
               .func_206870_a(field_196411_b, ☃.func_177229_b(field_196409_a))
               .func_206870_a(field_196413_c, ☃.func_177229_b(field_196411_b))
               .func_206870_a(field_196414_y, ☃.func_177229_b(field_196413_c));
         default:
            return ☃;
      }
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      switch(☃) {
         case LEFT_RIGHT:
            return ☃.func_206870_a(field_196409_a, ☃.func_177229_b(field_196413_c)).func_206870_a(field_196413_c, ☃.func_177229_b(field_196409_a));
         case FRONT_BACK:
            return ☃.func_206870_a(field_196411_b, ☃.func_177229_b(field_196414_y)).func_206870_a(field_196414_y, ☃.func_177229_b(field_196411_b));
         default:
            return super.func_185471_a(☃, ☃);
      }
   }
}
