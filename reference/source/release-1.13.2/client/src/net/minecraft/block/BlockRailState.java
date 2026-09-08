package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRailState {
   private final World field_196920_a;
   private final BlockPos field_196921_b;
   private final BlockRailBase field_196922_c;
   private IBlockState field_196923_d;
   private final boolean field_208513_e;
   private final List<BlockPos> field_196924_e = Lists.<BlockPos>newArrayList();

   public BlockRailState(World var1, BlockPos var2, IBlockState var3) {
      this.field_196920_a = ☃;
      this.field_196921_b = ☃;
      this.field_196923_d = ☃;
      this.field_196922_c = (BlockRailBase)☃.func_177230_c();
      RailShape ☃ = ☃.func_177229_b(this.field_196922_c.func_176560_l());
      this.field_208513_e = this.field_196922_c.func_208490_b();
      this.func_208509_a(☃);
   }

   public List<BlockPos> func_196907_a() {
      return this.field_196924_e;
   }

   private void func_208509_a(RailShape var1) {
      this.field_196924_e.clear();
      switch(☃) {
         case NORTH_SOUTH:
            this.field_196924_e.add(this.field_196921_b.func_177978_c());
            this.field_196924_e.add(this.field_196921_b.func_177968_d());
            break;
         case EAST_WEST:
            this.field_196924_e.add(this.field_196921_b.func_177976_e());
            this.field_196924_e.add(this.field_196921_b.func_177974_f());
            break;
         case ASCENDING_EAST:
            this.field_196924_e.add(this.field_196921_b.func_177976_e());
            this.field_196924_e.add(this.field_196921_b.func_177974_f().func_177984_a());
            break;
         case ASCENDING_WEST:
            this.field_196924_e.add(this.field_196921_b.func_177976_e().func_177984_a());
            this.field_196924_e.add(this.field_196921_b.func_177974_f());
            break;
         case ASCENDING_NORTH:
            this.field_196924_e.add(this.field_196921_b.func_177978_c().func_177984_a());
            this.field_196924_e.add(this.field_196921_b.func_177968_d());
            break;
         case ASCENDING_SOUTH:
            this.field_196924_e.add(this.field_196921_b.func_177978_c());
            this.field_196924_e.add(this.field_196921_b.func_177968_d().func_177984_a());
            break;
         case SOUTH_EAST:
            this.field_196924_e.add(this.field_196921_b.func_177974_f());
            this.field_196924_e.add(this.field_196921_b.func_177968_d());
            break;
         case SOUTH_WEST:
            this.field_196924_e.add(this.field_196921_b.func_177976_e());
            this.field_196924_e.add(this.field_196921_b.func_177968_d());
            break;
         case NORTH_WEST:
            this.field_196924_e.add(this.field_196921_b.func_177976_e());
            this.field_196924_e.add(this.field_196921_b.func_177978_c());
            break;
         case NORTH_EAST:
            this.field_196924_e.add(this.field_196921_b.func_177974_f());
            this.field_196924_e.add(this.field_196921_b.func_177978_c());
      }
   }

   private void func_196903_f() {
      for(int ☃ = 0; ☃ < this.field_196924_e.size(); ++☃) {
         BlockRailState ☃x = this.func_196908_a((BlockPos)this.field_196924_e.get(☃));
         if (☃x != null && ☃x.func_196919_b(this)) {
            this.field_196924_e.set(☃, ☃x.field_196921_b);
         } else {
            this.field_196924_e.remove(☃--);
         }
      }
   }

   private boolean func_196902_d(BlockPos var1) {
      return BlockRailBase.func_208488_a(this.field_196920_a, ☃)
         || BlockRailBase.func_208488_a(this.field_196920_a, ☃.func_177984_a())
         || BlockRailBase.func_208488_a(this.field_196920_a, ☃.func_177977_b());
   }

   @Nullable
   private BlockRailState func_196908_a(BlockPos var1) {
      IBlockState ☃ = this.field_196920_a.func_180495_p(☃);
      if (BlockRailBase.func_208487_j(☃)) {
         return new BlockRailState(this.field_196920_a, ☃, ☃);
      } else {
         BlockPos var2 = ☃.func_177984_a();
         ☃ = this.field_196920_a.func_180495_p(var2);
         if (BlockRailBase.func_208487_j(☃)) {
            return new BlockRailState(this.field_196920_a, var2, ☃);
         } else {
            var2 = ☃.func_177977_b();
            ☃ = this.field_196920_a.func_180495_p(var2);
            return BlockRailBase.func_208487_j(☃) ? new BlockRailState(this.field_196920_a, var2, ☃) : null;
         }
      }
   }

   private boolean func_196919_b(BlockRailState var1) {
      return this.func_196904_b(☃.field_196921_b);
   }

   private boolean func_196904_b(BlockPos var1) {
      for(int ☃ = 0; ☃ < this.field_196924_e.size(); ++☃) {
         BlockPos ☃x = (BlockPos)this.field_196924_e.get(☃);
         if (☃x.func_177958_n() == ☃.func_177958_n() && ☃x.func_177952_p() == ☃.func_177952_p()) {
            return true;
         }
      }

      return false;
   }

   protected int func_196910_b() {
      int ☃ = 0;

      for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
         if (this.func_196902_d(this.field_196921_b.func_177972_a(☃x))) {
            ++☃;
         }
      }

      return ☃;
   }

   private boolean func_196905_c(BlockRailState var1) {
      return this.func_196919_b(☃) || this.field_196924_e.size() != 2;
   }

   private void func_208510_c(BlockRailState var1) {
      this.field_196924_e.add(☃.field_196921_b);
      BlockPos ☃ = this.field_196921_b.func_177978_c();
      BlockPos ☃x = this.field_196921_b.func_177968_d();
      BlockPos ☃xx = this.field_196921_b.func_177976_e();
      BlockPos ☃xxx = this.field_196921_b.func_177974_f();
      boolean ☃xxxx = this.func_196904_b(☃);
      boolean ☃xxxxx = this.func_196904_b(☃x);
      boolean ☃xxxxxx = this.func_196904_b(☃xx);
      boolean ☃xxxxxxx = this.func_196904_b(☃xxx);
      RailShape ☃xxxxxxxx = null;
      if (☃xxxx || ☃xxxxx) {
         ☃xxxxxxxx = RailShape.NORTH_SOUTH;
      }

      if (☃xxxxxx || ☃xxxxxxx) {
         ☃xxxxxxxx = RailShape.EAST_WEST;
      }

      if (!this.field_208513_e) {
         if (☃xxxxx && ☃xxxxxxx && !☃xxxx && !☃xxxxxx) {
            ☃xxxxxxxx = RailShape.SOUTH_EAST;
         }

         if (☃xxxxx && ☃xxxxxx && !☃xxxx && !☃xxxxxxx) {
            ☃xxxxxxxx = RailShape.SOUTH_WEST;
         }

         if (☃xxxx && ☃xxxxxx && !☃xxxxx && !☃xxxxxxx) {
            ☃xxxxxxxx = RailShape.NORTH_WEST;
         }

         if (☃xxxx && ☃xxxxxxx && !☃xxxxx && !☃xxxxxx) {
            ☃xxxxxxxx = RailShape.NORTH_EAST;
         }
      }

      if (☃xxxxxxxx == RailShape.NORTH_SOUTH) {
         if (BlockRailBase.func_208488_a(this.field_196920_a, ☃.func_177984_a())) {
            ☃xxxxxxxx = RailShape.ASCENDING_NORTH;
         }

         if (BlockRailBase.func_208488_a(this.field_196920_a, ☃x.func_177984_a())) {
            ☃xxxxxxxx = RailShape.ASCENDING_SOUTH;
         }
      }

      if (☃xxxxxxxx == RailShape.EAST_WEST) {
         if (BlockRailBase.func_208488_a(this.field_196920_a, ☃xxx.func_177984_a())) {
            ☃xxxxxxxx = RailShape.ASCENDING_EAST;
         }

         if (BlockRailBase.func_208488_a(this.field_196920_a, ☃xx.func_177984_a())) {
            ☃xxxxxxxx = RailShape.ASCENDING_WEST;
         }
      }

      if (☃xxxxxxxx == null) {
         ☃xxxxxxxx = RailShape.NORTH_SOUTH;
      }

      this.field_196923_d = this.field_196923_d.func_206870_a(this.field_196922_c.func_176560_l(), ☃xxxxxxxx);
      this.field_196920_a.func_180501_a(this.field_196921_b, this.field_196923_d, 3);
   }

   private boolean func_208512_d(BlockPos var1) {
      BlockRailState ☃ = this.func_196908_a(☃);
      if (☃ == null) {
         return false;
      } else {
         ☃.func_196903_f();
         return ☃.func_196905_c(this);
      }
   }

   public BlockRailState func_208511_a(boolean var1, boolean var2) {
      BlockPos ☃ = this.field_196921_b.func_177978_c();
      BlockPos ☃x = this.field_196921_b.func_177968_d();
      BlockPos ☃xx = this.field_196921_b.func_177976_e();
      BlockPos ☃xxx = this.field_196921_b.func_177974_f();
      boolean ☃xxxx = this.func_208512_d(☃);
      boolean ☃xxxxx = this.func_208512_d(☃x);
      boolean ☃xxxxxx = this.func_208512_d(☃xx);
      boolean ☃xxxxxxx = this.func_208512_d(☃xxx);
      RailShape ☃xxxxxxxx = null;
      if ((☃xxxx || ☃xxxxx) && !☃xxxxxx && !☃xxxxxxx) {
         ☃xxxxxxxx = RailShape.NORTH_SOUTH;
      }

      if ((☃xxxxxx || ☃xxxxxxx) && !☃xxxx && !☃xxxxx) {
         ☃xxxxxxxx = RailShape.EAST_WEST;
      }

      if (!this.field_208513_e) {
         if (☃xxxxx && ☃xxxxxxx && !☃xxxx && !☃xxxxxx) {
            ☃xxxxxxxx = RailShape.SOUTH_EAST;
         }

         if (☃xxxxx && ☃xxxxxx && !☃xxxx && !☃xxxxxxx) {
            ☃xxxxxxxx = RailShape.SOUTH_WEST;
         }

         if (☃xxxx && ☃xxxxxx && !☃xxxxx && !☃xxxxxxx) {
            ☃xxxxxxxx = RailShape.NORTH_WEST;
         }

         if (☃xxxx && ☃xxxxxxx && !☃xxxxx && !☃xxxxxx) {
            ☃xxxxxxxx = RailShape.NORTH_EAST;
         }
      }

      if (☃xxxxxxxx == null) {
         if (☃xxxx || ☃xxxxx) {
            ☃xxxxxxxx = RailShape.NORTH_SOUTH;
         }

         if (☃xxxxxx || ☃xxxxxxx) {
            ☃xxxxxxxx = RailShape.EAST_WEST;
         }

         if (!this.field_208513_e) {
            if (☃) {
               if (☃xxxxx && ☃xxxxxxx) {
                  ☃xxxxxxxx = RailShape.SOUTH_EAST;
               }

               if (☃xxxxxx && ☃xxxxx) {
                  ☃xxxxxxxx = RailShape.SOUTH_WEST;
               }

               if (☃xxxxxxx && ☃xxxx) {
                  ☃xxxxxxxx = RailShape.NORTH_EAST;
               }

               if (☃xxxx && ☃xxxxxx) {
                  ☃xxxxxxxx = RailShape.NORTH_WEST;
               }
            } else {
               if (☃xxxx && ☃xxxxxx) {
                  ☃xxxxxxxx = RailShape.NORTH_WEST;
               }

               if (☃xxxxxxx && ☃xxxx) {
                  ☃xxxxxxxx = RailShape.NORTH_EAST;
               }

               if (☃xxxxxx && ☃xxxxx) {
                  ☃xxxxxxxx = RailShape.SOUTH_WEST;
               }

               if (☃xxxxx && ☃xxxxxxx) {
                  ☃xxxxxxxx = RailShape.SOUTH_EAST;
               }
            }
         }
      }

      if (☃xxxxxxxx == RailShape.NORTH_SOUTH) {
         if (BlockRailBase.func_208488_a(this.field_196920_a, ☃.func_177984_a())) {
            ☃xxxxxxxx = RailShape.ASCENDING_NORTH;
         }

         if (BlockRailBase.func_208488_a(this.field_196920_a, ☃x.func_177984_a())) {
            ☃xxxxxxxx = RailShape.ASCENDING_SOUTH;
         }
      }

      if (☃xxxxxxxx == RailShape.EAST_WEST) {
         if (BlockRailBase.func_208488_a(this.field_196920_a, ☃xxx.func_177984_a())) {
            ☃xxxxxxxx = RailShape.ASCENDING_EAST;
         }

         if (BlockRailBase.func_208488_a(this.field_196920_a, ☃xx.func_177984_a())) {
            ☃xxxxxxxx = RailShape.ASCENDING_WEST;
         }
      }

      if (☃xxxxxxxx == null) {
         ☃xxxxxxxx = RailShape.NORTH_SOUTH;
      }

      this.func_208509_a(☃xxxxxxxx);
      this.field_196923_d = this.field_196923_d.func_206870_a(this.field_196922_c.func_176560_l(), ☃xxxxxxxx);
      if (☃ || this.field_196920_a.func_180495_p(this.field_196921_b) != this.field_196923_d) {
         this.field_196920_a.func_180501_a(this.field_196921_b, this.field_196923_d, 3);

         for(int ☃ = 0; ☃ < this.field_196924_e.size(); ++☃) {
            BlockRailState ☃x = this.func_196908_a((BlockPos)this.field_196924_e.get(☃));
            if (☃x != null) {
               ☃x.func_196903_f();
               if (☃x.func_196905_c(this)) {
                  ☃x.func_208510_c(this);
               }
            }
         }
      }

      return this;
   }

   public IBlockState func_196916_c() {
      return this.field_196923_d;
   }
}
