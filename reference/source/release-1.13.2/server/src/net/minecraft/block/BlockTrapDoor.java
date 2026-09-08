package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockTrapDoor extends BlockHorizontal implements IBucketPickupHandler, ILiquidContainer {
   public static final BooleanProperty field_176283_b = BlockStateProperties.field_208193_t;
   public static final EnumProperty<Half> field_176285_M = BlockStateProperties.field_208164_Q;
   public static final BooleanProperty field_196381_c = BlockStateProperties.field_208194_u;
   public static final BooleanProperty field_204614_t = BlockStateProperties.field_208198_y;
   protected static final VoxelShape field_185734_d = Block.func_208617_a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final VoxelShape field_185735_e = Block.func_208617_a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185736_f = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape field_185737_g = Block.func_208617_a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185732_B = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   protected static final VoxelShape field_185733_C = Block.func_208617_a(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);

   protected BlockTrapDoor(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_185512_D, EnumFacing.NORTH)
            .func_206870_a(field_176283_b, Boolean.valueOf(false))
            .func_206870_a(field_176285_M, Half.BOTTOM)
            .func_206870_a(field_196381_c, Boolean.valueOf(false))
            .func_206870_a(field_204614_t, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      if (!☃.func_177229_b(field_176283_b)) {
         return ☃.func_177229_b(field_176285_M) == Half.TOP ? field_185733_C : field_185732_B;
      } else {
         switch((EnumFacing)☃.func_177229_b(field_185512_D)) {
            case NORTH:
            default:
               return field_185737_g;
            case SOUTH:
               return field_185736_f;
            case WEST:
               return field_185735_e;
            case EAST:
               return field_185734_d;
         }
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
            return ☃.func_177229_b(field_176283_b);
         case WATER:
            return ☃.func_177229_b(field_204614_t);
         case AIR:
            return ☃.func_177229_b(field_176283_b);
         default:
            return false;
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (this.field_149764_J == Material.field_151573_f) {
         return false;
      } else {
         ☃ = ☃.func_177231_a(field_176283_b);
         ☃.func_180501_a(☃, ☃, 2);
         if (☃.func_177229_b(field_204614_t)) {
            ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
         }

         this.func_185731_a(☃, ☃, ☃, ☃.func_177229_b(field_176283_b));
         return true;
      }
   }

   protected void func_185731_a(@Nullable EntityPlayer var1, World var2, BlockPos var3, boolean var4) {
      if (☃) {
         int ☃ = this.field_149764_J == Material.field_151573_f ? 1037 : 1007;
         ☃.func_180498_a(☃, ☃, ☃, 0);
      } else {
         int ☃ = this.field_149764_J == Material.field_151573_f ? 1036 : 1013;
         ☃.func_180498_a(☃, ☃, ☃, 0);
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (!☃.field_72995_K) {
         boolean ☃ = ☃.func_175640_z(☃);
         if (☃ != ☃.func_177229_b(field_196381_c)) {
            if (☃.func_177229_b(field_176283_b) != ☃) {
               ☃ = ☃.func_206870_a(field_176283_b, Boolean.valueOf(☃));
               this.func_185731_a(null, ☃, ☃, ☃);
            }

            ☃.func_180501_a(☃, ☃.func_206870_a(field_196381_c, Boolean.valueOf(☃)), 2);
            if (☃.func_177229_b(field_204614_t)) {
               ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
            }
         }
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = this.func_176223_P();
      IFluidState ☃x = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      EnumFacing ☃xx = ☃.func_196000_l();
      if (!☃.func_196012_c() && ☃xx.func_176740_k().func_176722_c()) {
         ☃ = ☃.func_206870_a(field_185512_D, ☃xx).func_206870_a(field_176285_M, ☃.func_195993_n() > 0.5F ? Half.TOP : Half.BOTTOM);
      } else {
         ☃ = ☃.func_206870_a(field_185512_D, ☃.func_195992_f().func_176734_d()).func_206870_a(field_176285_M, ☃xx == EnumFacing.UP ? Half.BOTTOM : Half.TOP);
      }

      if (☃.func_195991_k().func_175640_z(☃.func_195995_a())) {
         ☃ = ☃.func_206870_a(field_176283_b, Boolean.valueOf(true)).func_206870_a(field_196381_c, Boolean.valueOf(true));
      }

      return ☃.func_206870_a(field_204614_t, Boolean.valueOf(☃x.func_206886_c() == Fluids.field_204546_a));
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185512_D, field_176283_b, field_176285_M, field_196381_c, field_204614_t);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return (☃ == EnumFacing.UP && ☃.func_177229_b(field_176285_M) == Half.TOP || ☃ == EnumFacing.DOWN && ☃.func_177229_b(field_176285_M) == Half.BOTTOM)
            && !☃.func_177229_b(field_176283_b)
         ? BlockFaceShape.SOLID
         : BlockFaceShape.UNDEFINED;
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_204614_t)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_204614_t, Boolean.valueOf(false)), 3);
         return Fluids.field_204546_a;
      } else {
         return Fluids.field_204541_a;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return ☃.func_177229_b(field_204614_t) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(☃);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return !☃.func_177229_b(field_204614_t) && ☃ == Fluids.field_204546_a;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      if (!☃.func_177229_b(field_204614_t) && ☃.func_206886_c() == Fluids.field_204546_a) {
         if (!☃.func_201670_d()) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_204614_t, Boolean.valueOf(true)), 3);
            ☃.func_205219_F_().func_205360_a(☃, ☃.func_206886_c(), ☃.func_206886_c().func_205569_a(☃));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_204614_t)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }
}
