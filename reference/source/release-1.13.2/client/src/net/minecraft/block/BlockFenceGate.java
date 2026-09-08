package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockFenceGate extends BlockHorizontal {
   public static final BooleanProperty field_176466_a = BlockStateProperties.field_208193_t;
   public static final BooleanProperty field_176465_b = BlockStateProperties.field_208194_u;
   public static final BooleanProperty field_176467_M = BlockStateProperties.field_208189_p;
   protected static final VoxelShape field_185541_d = Block.func_208617_a(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape field_185542_e = Block.func_208617_a(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
   protected static final VoxelShape field_185543_f = Block.func_208617_a(0.0, 0.0, 6.0, 16.0, 13.0, 10.0);
   protected static final VoxelShape field_185544_g = Block.func_208617_a(6.0, 0.0, 0.0, 10.0, 13.0, 16.0);
   protected static final VoxelShape field_208068_x = Block.func_208617_a(0.0, 0.0, 6.0, 16.0, 24.0, 10.0);
   protected static final VoxelShape field_185540_C = Block.func_208617_a(6.0, 0.0, 0.0, 10.0, 24.0, 16.0);
   protected static final VoxelShape field_208069_z = VoxelShapes.func_197872_a(
      Block.func_208617_a(0.0, 5.0, 7.0, 2.0, 16.0, 9.0), Block.func_208617_a(14.0, 5.0, 7.0, 16.0, 16.0, 9.0)
   );
   protected static final VoxelShape field_185539_B = VoxelShapes.func_197872_a(
      Block.func_208617_a(7.0, 5.0, 0.0, 9.0, 16.0, 2.0), Block.func_208617_a(7.0, 5.0, 14.0, 9.0, 16.0, 16.0)
   );
   protected static final VoxelShape field_208066_B = VoxelShapes.func_197872_a(
      Block.func_208617_a(0.0, 2.0, 7.0, 2.0, 13.0, 9.0), Block.func_208617_a(14.0, 2.0, 7.0, 16.0, 13.0, 9.0)
   );
   protected static final VoxelShape field_208067_C = VoxelShapes.func_197872_a(
      Block.func_208617_a(7.0, 2.0, 0.0, 9.0, 13.0, 2.0), Block.func_208617_a(7.0, 2.0, 14.0, 9.0, 13.0, 16.0)
   );

   public BlockFenceGate(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176466_a, Boolean.valueOf(false))
            .func_206870_a(field_176465_b, Boolean.valueOf(false))
            .func_206870_a(field_176467_M, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      if (☃.func_177229_b(field_176467_M)) {
         return ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k() == EnumFacing.Axis.X ? field_185544_g : field_185543_f;
      } else {
         return ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k() == EnumFacing.Axis.X ? field_185542_e : field_185541_d;
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      EnumFacing.Axis ☃ = ☃.func_176740_k();
      if (((EnumFacing)☃.func_177229_b(field_185512_D)).func_176746_e().func_176740_k() != ☃) {
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      } else {
         boolean ☃ = this.func_196380_i(☃) || this.func_196380_i(☃.func_180495_p(☃.func_177972_a(☃.func_176734_d())));
         return ☃.func_206870_a(field_176467_M, Boolean.valueOf(☃));
      }
   }

   @Override
   public VoxelShape func_196268_f(IBlockState var1, IBlockReader var2, BlockPos var3) {
      if (☃.func_177229_b(field_176466_a)) {
         return VoxelShapes.func_197880_a();
      } else {
         return ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k() == EnumFacing.Axis.Z ? field_208068_x : field_185540_C;
      }
   }

   @Override
   public VoxelShape func_196247_c(IBlockState var1, IBlockReader var2, BlockPos var3) {
      if (☃.func_177229_b(field_176467_M)) {
         return ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k() == EnumFacing.Axis.X ? field_208067_C : field_208066_B;
      } else {
         return ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k() == EnumFacing.Axis.X ? field_185539_B : field_208069_z;
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      switch(☃) {
         case LAND:
            return ☃.func_177229_b(field_176466_a);
         case WATER:
            return false;
         case AIR:
            return ☃.func_177229_b(field_176466_a);
         default:
            return false;
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      boolean ☃xx = ☃.func_175640_z(☃x);
      EnumFacing ☃xxx = ☃.func_195992_f();
      EnumFacing.Axis ☃xxxx = ☃xxx.func_176740_k();
      boolean ☃xxxxx = ☃xxxx == EnumFacing.Axis.Z
            && (this.func_196380_i(☃.func_180495_p(☃x.func_177976_e())) || this.func_196380_i(☃.func_180495_p(☃x.func_177974_f())))
         || ☃xxxx == EnumFacing.Axis.X && (this.func_196380_i(☃.func_180495_p(☃x.func_177978_c())) || this.func_196380_i(☃.func_180495_p(☃x.func_177968_d())));
      return this.func_176223_P()
         .func_206870_a(field_185512_D, ☃xxx)
         .func_206870_a(field_176466_a, Boolean.valueOf(☃xx))
         .func_206870_a(field_176465_b, Boolean.valueOf(☃xx))
         .func_206870_a(field_176467_M, Boolean.valueOf(☃xxxxx));
   }

   private boolean func_196380_i(IBlockState var1) {
      return ☃.func_177230_c() == Blocks.field_150463_bK || ☃.func_177230_c() == Blocks.field_196723_eg;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.func_177229_b(field_176466_a)) {
         ☃ = ☃.func_206870_a(field_176466_a, Boolean.valueOf(false));
         ☃.func_180501_a(☃, ☃, 10);
      } else {
         EnumFacing ☃ = ☃.func_174811_aO();
         if (☃.func_177229_b(field_185512_D) == ☃.func_176734_d()) {
            ☃ = ☃.func_206870_a(field_185512_D, ☃);
         }

         ☃ = ☃.func_206870_a(field_176466_a, Boolean.valueOf(true));
         ☃.func_180501_a(☃, ☃, 10);
      }

      ☃.func_180498_a(☃, ☃.func_177229_b(field_176466_a) ? 1008 : 1014, ☃, 0);
      return true;
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (!☃.field_72995_K) {
         boolean ☃ = ☃.func_175640_z(☃);
         if (☃.func_177229_b(field_176465_b) != ☃) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_176465_b, Boolean.valueOf(☃)).func_206870_a(field_176466_a, Boolean.valueOf(☃)), 2);
            if (☃.func_177229_b(field_176466_a) != ☃) {
               ☃.func_180498_a(null, ☃ ? 1008 : 1014, ☃, 0);
            }
         }
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185512_D, field_176466_a, field_176465_b, field_176467_M);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      if (☃ != EnumFacing.UP && ☃ != EnumFacing.DOWN) {
         return ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k() == ☃.func_176746_e().func_176740_k()
            ? BlockFaceShape.MIDDLE_POLE
            : BlockFaceShape.UNDEFINED;
      } else {
         return BlockFaceShape.UNDEFINED;
      }
   }
}
