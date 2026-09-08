package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRail extends BlockRailBase {
   public static final EnumProperty<RailShape> field_176565_b = BlockStateProperties.field_208165_R;

   protected BlockRail(Block.Properties var1) {
      super(false, ☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176565_b, RailShape.NORTH_SOUTH));
   }

   @Override
   protected void func_189541_b(IBlockState var1, World var2, BlockPos var3, Block var4) {
      if (☃.func_176223_P().func_185897_m() && new BlockRailState(☃, ☃, ☃).func_196910_b() == 3) {
         this.func_208489_a(☃, ☃, ☃, false);
      }
   }

   @Override
   public IProperty<RailShape> func_176560_l() {
      return field_176565_b;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      switch(☃) {
         case CLOCKWISE_180:
            switch((RailShape)☃.func_177229_b(field_176565_b)) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_WEST);
            }
         case COUNTERCLOCKWISE_90:
            switch((RailShape)☃.func_177229_b(field_176565_b)) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_NORTH);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_WEST);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_EAST);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_WEST);
               case NORTH_SOUTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.EAST_WEST);
               case EAST_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_SOUTH);
            }
         case CLOCKWISE_90:
            switch((RailShape)☃.func_177229_b(field_176565_b)) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_NORTH);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_EAST);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_WEST);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_EAST);
               case NORTH_SOUTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.EAST_WEST);
               case EAST_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_SOUTH);
            }
         default:
            return ☃;
      }
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      RailShape ☃ = ☃.func_177229_b(field_176565_b);
      switch(☃) {
         case LEFT_RIGHT:
            switch(☃) {
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_EAST);
               default:
                  return super.func_185471_a(☃, ☃);
            }
         case FRONT_BACK:
            switch(☃) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
               case ASCENDING_SOUTH:
               default:
                  break;
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176565_b, RailShape.NORTH_WEST);
            }
      }

      return super.func_185471_a(☃, ☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176565_b);
   }
}
