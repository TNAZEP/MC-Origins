package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.PistonType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockPistonExtension extends BlockDirectional {
   public static final EnumProperty<PistonType> field_176325_b = BlockStateProperties.field_208144_as;
   public static final BooleanProperty field_176327_M = BlockStateProperties.field_208195_v;
   protected static final VoxelShape field_185635_c = Block.func_208617_a(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185637_d = Block.func_208617_a(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
   protected static final VoxelShape field_185639_e = Block.func_208617_a(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185641_f = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
   protected static final VoxelShape field_185643_g = Block.func_208617_a(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185634_B = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
   protected static final VoxelShape field_185636_C = Block.func_208617_a(6.0, -4.0, 6.0, 10.0, 12.0, 10.0);
   protected static final VoxelShape field_185638_D = Block.func_208617_a(6.0, 4.0, 6.0, 10.0, 20.0, 10.0);
   protected static final VoxelShape field_185640_E = Block.func_208617_a(6.0, 6.0, -4.0, 10.0, 10.0, 12.0);
   protected static final VoxelShape field_185642_F = Block.func_208617_a(6.0, 6.0, 4.0, 10.0, 10.0, 20.0);
   protected static final VoxelShape field_185644_G = Block.func_208617_a(-4.0, 6.0, 6.0, 12.0, 10.0, 10.0);
   protected static final VoxelShape field_185645_I = Block.func_208617_a(4.0, 6.0, 6.0, 20.0, 10.0, 10.0);
   protected static final VoxelShape field_190964_J = Block.func_208617_a(6.0, 0.0, 6.0, 10.0, 12.0, 10.0);
   protected static final VoxelShape field_190965_K = Block.func_208617_a(6.0, 4.0, 6.0, 10.0, 16.0, 10.0);
   protected static final VoxelShape field_190966_L = Block.func_208617_a(6.0, 6.0, 0.0, 10.0, 10.0, 12.0);
   protected static final VoxelShape field_190967_M = Block.func_208617_a(6.0, 6.0, 4.0, 10.0, 10.0, 16.0);
   protected static final VoxelShape field_190968_N = Block.func_208617_a(0.0, 6.0, 6.0, 12.0, 10.0, 10.0);
   protected static final VoxelShape field_190969_O = Block.func_208617_a(4.0, 6.0, 6.0, 16.0, 10.0, 10.0);

   public BlockPistonExtension(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176387_N, EnumFacing.NORTH)
            .func_206870_a(field_176325_b, PistonType.DEFAULT)
            .func_206870_a(field_176327_M, Boolean.valueOf(false))
      );
   }

   private VoxelShape func_196424_i(IBlockState var1) {
      switch((EnumFacing)☃.func_177229_b(field_176387_N)) {
         case DOWN:
         default:
            return field_185634_B;
         case UP:
            return field_185643_g;
         case NORTH:
            return field_185641_f;
         case SOUTH:
            return field_185639_e;
         case WEST:
            return field_185637_d;
         case EAST:
            return field_185635_c;
      }
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return VoxelShapes.func_197872_a(this.func_196424_i(☃), this.func_196425_x(☃));
   }

   private VoxelShape func_196425_x(IBlockState var1) {
      boolean ☃ = ☃.func_177229_b(field_176327_M);
      switch((EnumFacing)☃.func_177229_b(field_176387_N)) {
         case DOWN:
         default:
            return ☃ ? field_190965_K : field_185638_D;
         case UP:
            return ☃ ? field_190964_J : field_185636_C;
         case NORTH:
            return ☃ ? field_190967_M : field_185642_F;
         case SOUTH:
            return ☃ ? field_190966_L : field_185640_E;
         case WEST:
            return ☃ ? field_190969_O : field_185645_I;
         case EAST:
            return ☃ ? field_190968_N : field_185644_G;
      }
   }

   @Override
   public boolean func_185481_k(IBlockState var1) {
      return ☃.func_177229_b(field_176387_N) == EnumFacing.UP;
   }

   @Override
   public void func_176208_a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (!☃.field_72995_K && ☃.field_71075_bZ.field_75098_d) {
         BlockPos ☃ = ☃.func_177972_a(((EnumFacing)☃.func_177229_b(field_176387_N)).func_176734_d());
         Block ☃x = ☃.func_180495_p(☃).func_177230_c();
         if (☃x == Blocks.field_150331_J || ☃x == Blocks.field_150320_F) {
            ☃.func_175698_g(☃);
         }
      }

      super.func_176208_a(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
         EnumFacing ☃ = ((EnumFacing)☃.func_177229_b(field_176387_N)).func_176734_d();
         ☃ = ☃.func_177972_a(☃);
         IBlockState ☃x = ☃.func_180495_p(☃);
         if ((☃x.func_177230_c() == Blocks.field_150331_J || ☃x.func_177230_c() == Blocks.field_150320_F) && ☃x.func_177229_b(BlockPistonBase.field_176320_b)) {
            ☃x.func_196949_c(☃, ☃, 0);
            ☃.func_175698_g(☃);
         }
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃.func_176734_d() == ☃.func_177229_b(field_176387_N) && !☃.func_196955_c(☃, ☃)
         ? Blocks.field_150350_a.func_176223_P()
         : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      Block ☃ = ☃.func_180495_p(☃.func_177972_a(((EnumFacing)☃.func_177229_b(field_176387_N)).func_176734_d())).func_177230_c();
      return ☃ == Blocks.field_150331_J || ☃ == Blocks.field_150320_F || ☃ == Blocks.field_196603_bb;
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (☃.func_196955_c(☃, ☃)) {
         BlockPos ☃ = ☃.func_177972_a(((EnumFacing)☃.func_177229_b(field_176387_N)).func_176734_d());
         ☃.func_180495_p(☃).func_189546_a(☃, ☃, ☃, ☃);
      }
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return new ItemStack(☃.func_177229_b(field_176325_b) == PistonType.STICKY ? Blocks.field_150320_F : Blocks.field_150331_J);
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176387_N, ☃.func_185831_a(☃.func_177229_b(field_176387_N)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176387_N)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176387_N, field_176325_b, field_176327_M);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == ☃.func_177229_b(field_176387_N) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
