package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRailPowered extends BlockRailBase {
   public static final EnumProperty<RailShape> field_176568_b = BlockStateProperties.field_208166_S;
   public static final BooleanProperty field_176569_M = BlockStateProperties.field_208194_u;

   protected BlockRailPowered(Block.Properties var1) {
      super(true, ☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176568_b, RailShape.NORTH_SOUTH).func_206870_a(field_176569_M, Boolean.valueOf(false))
      );
   }

   protected boolean func_176566_a(World var1, BlockPos var2, IBlockState var3, boolean var4, int var5) {
      if (☃ >= 8) {
         return false;
      } else {
         int ☃ = ☃.func_177958_n();
         int ☃x = ☃.func_177956_o();
         int ☃xx = ☃.func_177952_p();
         boolean ☃xxx = true;
         RailShape ☃xxxx = ☃.func_177229_b(field_176568_b);
         switch(☃xxxx) {
            case NORTH_SOUTH:
               if (☃) {
                  ++☃xx;
               } else {
                  --☃xx;
               }
               break;
            case EAST_WEST:
               if (☃) {
                  --☃;
               } else {
                  ++☃;
               }
               break;
            case ASCENDING_EAST:
               if (☃) {
                  --☃;
               } else {
                  ++☃;
                  ++☃x;
                  ☃xxx = false;
               }

               ☃xxxx = RailShape.EAST_WEST;
               break;
            case ASCENDING_WEST:
               if (☃) {
                  --☃;
                  ++☃x;
                  ☃xxx = false;
               } else {
                  ++☃;
               }

               ☃xxxx = RailShape.EAST_WEST;
               break;
            case ASCENDING_NORTH:
               if (☃) {
                  ++☃xx;
               } else {
                  --☃xx;
                  ++☃x;
                  ☃xxx = false;
               }

               ☃xxxx = RailShape.NORTH_SOUTH;
               break;
            case ASCENDING_SOUTH:
               if (☃) {
                  ++☃xx;
                  ++☃x;
                  ☃xxx = false;
               } else {
                  --☃xx;
               }

               ☃xxxx = RailShape.NORTH_SOUTH;
         }

         if (this.func_208071_a(☃, new BlockPos(☃, ☃x, ☃xx), ☃, ☃, ☃xxxx)) {
            return true;
         } else {
            return ☃xxx && this.func_208071_a(☃, new BlockPos(☃, ☃x - 1, ☃xx), ☃, ☃, ☃xxxx);
         }
      }
   }

   protected boolean func_208071_a(World var1, BlockPos var2, boolean var3, int var4, RailShape var5) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      if (☃.func_177230_c() != this) {
         return false;
      } else {
         RailShape ☃ = ☃.func_177229_b(field_176568_b);
         if (☃ != RailShape.EAST_WEST || ☃ != RailShape.NORTH_SOUTH && ☃ != RailShape.ASCENDING_NORTH && ☃ != RailShape.ASCENDING_SOUTH) {
            if (☃ != RailShape.NORTH_SOUTH || ☃ != RailShape.EAST_WEST && ☃ != RailShape.ASCENDING_EAST && ☃ != RailShape.ASCENDING_WEST) {
               if (!☃.func_177229_b(field_176569_M)) {
                  return false;
               } else {
                  return ☃.func_175640_z(☃) ? true : this.func_176566_a(☃, ☃, ☃, ☃, ☃ + 1);
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   protected void func_189541_b(IBlockState var1, World var2, BlockPos var3, Block var4) {
      boolean ☃ = ☃.func_177229_b(field_176569_M);
      boolean ☃x = ☃.func_175640_z(☃) || this.func_176566_a(☃, ☃, ☃, true, 0) || this.func_176566_a(☃, ☃, ☃, false, 0);
      if (☃x != ☃) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176569_M, Boolean.valueOf(☃x)), 3);
         ☃.func_195593_d(☃.func_177977_b(), this);
         if (((RailShape)☃.func_177229_b(field_176568_b)).func_208092_c()) {
            ☃.func_195593_d(☃.func_177984_a(), this);
         }
      }
   }

   @Override
   public IProperty<RailShape> func_176560_l() {
      return field_176568_b;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      switch(☃) {
         case CLOCKWISE_180:
            switch((RailShape)☃.func_177229_b(field_176568_b)) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_WEST);
            }
         case COUNTERCLOCKWISE_90:
            switch((RailShape)☃.func_177229_b(field_176568_b)) {
               case NORTH_SOUTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.EAST_WEST);
               case EAST_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_SOUTH);
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_NORTH);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_WEST);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_EAST);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_WEST);
            }
         case CLOCKWISE_90:
            switch((RailShape)☃.func_177229_b(field_176568_b)) {
               case NORTH_SOUTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.EAST_WEST);
               case EAST_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_SOUTH);
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_NORTH);
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_EAST);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_WEST);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_EAST);
            }
         default:
            return ☃;
      }
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      RailShape ☃ = ☃.func_177229_b(field_176568_b);
      switch(☃) {
         case LEFT_RIGHT:
            switch(☃) {
               case ASCENDING_NORTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_EAST);
               default:
                  return super.func_185471_a(☃, ☃);
            }
         case FRONT_BACK:
            switch(☃) {
               case ASCENDING_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
               case ASCENDING_SOUTH:
               default:
                  break;
               case SOUTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return ☃.func_206870_a(field_176568_b, RailShape.NORTH_WEST);
            }
      }

      return super.func_185471_a(☃, ☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176568_b, field_176569_M);
   }
}
