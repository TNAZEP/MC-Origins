package net.minecraft.block;

import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockStairs extends Block implements IBucketPickupHandler, ILiquidContainer {
   public static final DirectionProperty field_176309_a = BlockHorizontal.field_185512_D;
   public static final EnumProperty<Half> field_176308_b = BlockStateProperties.field_208164_Q;
   public static final EnumProperty<StairsShape> field_176310_M = BlockStateProperties.field_208146_au;
   public static final BooleanProperty field_204513_t = BlockStateProperties.field_208198_y;
   protected static final VoxelShape field_185712_d = BlockSlab.field_196507_c;
   protected static final VoxelShape field_185719_G = BlockSlab.field_196506_b;
   protected static final VoxelShape field_196512_A = Block.func_208617_a(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
   protected static final VoxelShape field_196513_B = Block.func_208617_a(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
   protected static final VoxelShape field_196514_C = Block.func_208617_a(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
   protected static final VoxelShape field_196515_D = Block.func_208617_a(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
   protected static final VoxelShape field_196516_E = Block.func_208617_a(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
   protected static final VoxelShape field_196517_F = Block.func_208617_a(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape field_196518_G = Block.func_208617_a(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
   protected static final VoxelShape field_196519_H = Block.func_208617_a(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape[] field_196520_I = func_199779_a(field_185712_d, field_196512_A, field_196516_E, field_196513_B, field_196517_F);
   protected static final VoxelShape[] field_196521_J = func_199779_a(field_185719_G, field_196514_C, field_196518_G, field_196515_D, field_196519_H);
   private static final int[] field_196522_K = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
   private final Block field_150149_b;
   private final IBlockState field_150151_M;

   private static VoxelShape[] func_199779_a(VoxelShape var0, VoxelShape var1, VoxelShape var2, VoxelShape var3, VoxelShape var4) {
      return (VoxelShape[])IntStream.range(0, 16).mapToObj(var5 -> func_199781_a(var5, ☃, ☃, ☃, ☃, ☃)).toArray(var0x -> new VoxelShape[var0x]);
   }

   private static VoxelShape func_199781_a(int var0, VoxelShape var1, VoxelShape var2, VoxelShape var3, VoxelShape var4, VoxelShape var5) {
      VoxelShape ☃ = ☃;
      if ((☃ & 1) != 0) {
         ☃ = VoxelShapes.func_197872_a(☃, ☃);
      }

      if ((☃ & 2) != 0) {
         ☃ = VoxelShapes.func_197872_a(☃, ☃);
      }

      if ((☃ & 4) != 0) {
         ☃ = VoxelShapes.func_197872_a(☃, ☃);
      }

      if ((☃ & 8) != 0) {
         ☃ = VoxelShapes.func_197872_a(☃, ☃);
      }

      return ☃;
   }

   protected BlockStairs(IBlockState var1, Block.Properties var2) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176309_a, EnumFacing.NORTH)
            .func_206870_a(field_176308_b, Half.BOTTOM)
            .func_206870_a(field_176310_M, StairsShape.STRAIGHT)
            .func_206870_a(field_204513_t, Boolean.valueOf(false))
      );
      this.field_150149_b = ☃.func_177230_c();
      this.field_150151_M = ☃;
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_201572_C();
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return (☃.func_177229_b(field_176308_b) == Half.TOP ? field_196520_I : field_196521_J)[field_196522_K[this.func_196511_x(☃)]];
   }

   private int func_196511_x(IBlockState var1) {
      return ((StairsShape)☃.func_177229_b(field_176310_M)).ordinal() * 4 + ((EnumFacing)☃.func_177229_b(field_176309_a)).func_176736_b();
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      if (☃.func_176740_k() == EnumFacing.Axis.Y) {
         return ☃ == EnumFacing.UP == (☃.func_177229_b(field_176308_b) == Half.TOP) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
      } else {
         StairsShape ☃ = ☃.func_177229_b(field_176310_M);
         if (☃ != StairsShape.OUTER_LEFT && ☃ != StairsShape.OUTER_RIGHT) {
            EnumFacing ☃x = ☃.func_177229_b(field_176309_a);
            switch(☃) {
               case STRAIGHT:
                  return ☃x == ☃ ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
               case INNER_LEFT:
                  return ☃x != ☃ && ☃x != ☃.func_176746_e() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
               case INNER_RIGHT:
                  return ☃x != ☃ && ☃x != ☃.func_176735_f() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
               default:
                  return BlockFaceShape.UNDEFINED;
            }
         } else {
            return BlockFaceShape.UNDEFINED;
         }
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      this.field_150149_b.func_180655_c(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196270_a(IBlockState var1, World var2, BlockPos var3, EntityPlayer var4) {
      this.field_150151_M.func_196942_a(☃, ☃, ☃);
   }

   @Override
   public void func_176206_d(IWorld var1, BlockPos var2, IBlockState var3) {
      this.field_150149_b.func_176206_d(☃, ☃, ☃);
   }

   @Override
   public int func_185484_c(IBlockState var1, IWorldReader var2, BlockPos var3) {
      return this.field_150151_M.func_185889_a(☃, ☃);
   }

   @Override
   public float func_149638_a() {
      return this.field_150149_b.func_149638_a();
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return this.field_150149_b.func_180664_k();
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return this.field_150149_b.func_149738_a(☃);
   }

   @Override
   public boolean func_149703_v() {
      return this.field_150149_b.func_149703_v();
   }

   @Override
   public boolean func_200293_a(IBlockState var1) {
      return this.field_150149_b.func_200293_a(☃);
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         this.field_150151_M.func_189546_a(☃, ☃, Blocks.field_150350_a, ☃);
         this.field_150149_b.func_196259_b(this.field_150151_M, ☃, ☃, ☃);
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         this.field_150151_M.func_196947_b(☃, ☃, ☃, ☃);
      }
   }

   @Override
   public void func_176199_a(World var1, BlockPos var2, Entity var3) {
      this.field_150149_b.func_176199_a(☃, ☃, ☃);
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      this.field_150149_b.func_196267_b(☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      return this.field_150151_M.func_196943_a(☃, ☃, ☃, ☃, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
   }

   @Override
   public void func_180652_a(World var1, BlockPos var2, Explosion var3) {
      this.field_150149_b.func_180652_a(☃, ☃, ☃);
   }

   @Override
   public boolean func_185481_k(IBlockState var1) {
      return ☃.func_177229_b(field_176308_b) == Half.TOP;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      EnumFacing ☃ = ☃.func_196000_l();
      IFluidState ☃x = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      IBlockState ☃xx = this.func_176223_P()
         .func_206870_a(field_176309_a, ☃.func_195992_f())
         .func_206870_a(field_176308_b, ☃ != EnumFacing.DOWN && (☃ == EnumFacing.UP || !((double)☃.func_195993_n() > 0.5)) ? Half.BOTTOM : Half.TOP)
         .func_206870_a(field_204513_t, Boolean.valueOf(☃x.func_206886_c() == Fluids.field_204546_a));
      return ☃xx.func_206870_a(field_176310_M, func_208064_n(☃xx, ☃.func_195991_k(), ☃.func_195995_a()));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_204513_t)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      return ☃.func_176740_k().func_176722_c() ? ☃.func_206870_a(field_176310_M, func_208064_n(☃, ☃, ☃)) : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   private static StairsShape func_208064_n(IBlockState var0, IBlockReader var1, BlockPos var2) {
      EnumFacing ☃ = ☃.func_177229_b(field_176309_a);
      IBlockState ☃x = ☃.func_180495_p(☃.func_177972_a(☃));
      if (func_185709_i(☃x) && ☃.func_177229_b(field_176308_b) == ☃x.func_177229_b(field_176308_b)) {
         EnumFacing ☃xx = ☃x.func_177229_b(field_176309_a);
         if (☃xx.func_176740_k() != ((EnumFacing)☃.func_177229_b(field_176309_a)).func_176740_k() && func_185704_d(☃, ☃, ☃, ☃xx.func_176734_d())) {
            if (☃xx == ☃.func_176735_f()) {
               return StairsShape.OUTER_LEFT;
            }

            return StairsShape.OUTER_RIGHT;
         }
      }

      IBlockState ☃ = ☃.func_180495_p(☃.func_177972_a(☃.func_176734_d()));
      if (func_185709_i(☃) && ☃.func_177229_b(field_176308_b) == ☃.func_177229_b(field_176308_b)) {
         EnumFacing ☃x = ☃.func_177229_b(field_176309_a);
         if (☃x.func_176740_k() != ((EnumFacing)☃.func_177229_b(field_176309_a)).func_176740_k() && func_185704_d(☃, ☃, ☃, ☃x)) {
            if (☃x == ☃.func_176735_f()) {
               return StairsShape.INNER_LEFT;
            }

            return StairsShape.INNER_RIGHT;
         }
      }

      return StairsShape.STRAIGHT;
   }

   private static boolean func_185704_d(IBlockState var0, IBlockReader var1, BlockPos var2, EnumFacing var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177972_a(☃));
      return !func_185709_i(☃)
         || ☃.func_177229_b(field_176309_a) != ☃.func_177229_b(field_176309_a)
         || ☃.func_177229_b(field_176308_b) != ☃.func_177229_b(field_176308_b);
   }

   public static boolean func_185709_i(IBlockState var0) {
      return ☃.func_177230_c() instanceof BlockStairs;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176309_a, ☃.func_185831_a(☃.func_177229_b(field_176309_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      EnumFacing ☃ = ☃.func_177229_b(field_176309_a);
      StairsShape ☃x = ☃.func_177229_b(field_176310_M);
      switch(☃) {
         case LEFT_RIGHT:
            if (☃.func_176740_k() == EnumFacing.Axis.Z) {
               switch(☃x) {
                  case INNER_LEFT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180).func_206870_a(field_176310_M, StairsShape.INNER_RIGHT);
                  case INNER_RIGHT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180).func_206870_a(field_176310_M, StairsShape.INNER_LEFT);
                  case OUTER_LEFT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180).func_206870_a(field_176310_M, StairsShape.OUTER_RIGHT);
                  case OUTER_RIGHT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180).func_206870_a(field_176310_M, StairsShape.OUTER_LEFT);
                  default:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180);
               }
            }
            break;
         case FRONT_BACK:
            if (☃.func_176740_k() == EnumFacing.Axis.X) {
               switch(☃x) {
                  case STRAIGHT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180);
                  case INNER_LEFT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180).func_206870_a(field_176310_M, StairsShape.INNER_LEFT);
                  case INNER_RIGHT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180).func_206870_a(field_176310_M, StairsShape.INNER_RIGHT);
                  case OUTER_LEFT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180).func_206870_a(field_176310_M, StairsShape.OUTER_RIGHT);
                  case OUTER_RIGHT:
                     return ☃.func_185907_a(Rotation.CLOCKWISE_180).func_206870_a(field_176310_M, StairsShape.OUTER_LEFT);
               }
            }
      }

      return super.func_185471_a(☃, ☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176309_a, field_176308_b, field_176310_M, field_204513_t);
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_204513_t)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_204513_t, Boolean.valueOf(false)), 3);
         return Fluids.field_204546_a;
      } else {
         return Fluids.field_204541_a;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return ☃.func_177229_b(field_204513_t) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(☃);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return !☃.func_177229_b(field_204513_t) && ☃ == Fluids.field_204546_a;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      if (!☃.func_177229_b(field_204513_t) && ☃.func_206886_c() == Fluids.field_204546_a) {
         if (!☃.func_201670_d()) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_204513_t, Boolean.valueOf(true)), 3);
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
}
