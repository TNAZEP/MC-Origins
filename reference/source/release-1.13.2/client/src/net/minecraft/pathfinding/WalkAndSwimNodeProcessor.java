package net.minecraft.pathfinding;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class WalkAndSwimNodeProcessor extends WalkNodeProcessor {
   private float field_203247_k;
   private float field_203248_l;

   @Override
   public void func_186315_a(IBlockReader var1, EntityLiving var2) {
      super.func_186315_a(☃, ☃);
      ☃.func_184644_a(PathNodeType.WATER, 0.0F);
      this.field_203247_k = ☃.func_184643_a(PathNodeType.WALKABLE);
      ☃.func_184644_a(PathNodeType.WALKABLE, 6.0F);
      this.field_203248_l = ☃.func_184643_a(PathNodeType.WATER_BORDER);
      ☃.func_184644_a(PathNodeType.WATER_BORDER, 4.0F);
   }

   @Override
   public void func_176163_a() {
      this.field_186326_b.func_184644_a(PathNodeType.WALKABLE, this.field_203247_k);
      this.field_186326_b.func_184644_a(PathNodeType.WATER_BORDER, this.field_203248_l);
      super.func_176163_a();
   }

   @Override
   public PathPoint func_186318_b() {
      return this.func_176159_a(
         MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72340_a),
         MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72338_b + 0.5),
         MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72339_c)
      );
   }

   @Override
   public PathPoint func_186325_a(double var1, double var3, double var5) {
      return this.func_176159_a(MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃ + 0.5), MathHelper.func_76128_c(☃));
   }

   @Override
   public int func_186320_a(PathPoint[] var1, PathPoint var2, PathPoint var3, float var4) {
      int ☃ = 0;
      int ☃x = 1;
      BlockPos ☃xx = new BlockPos(☃.field_75839_a, ☃.field_75837_b, ☃.field_75838_c);
      double ☃xxx = this.func_203246_a(☃xx);
      PathPoint ☃xxxx = this.func_203245_a(☃.field_75839_a, ☃.field_75837_b, ☃.field_75838_c + 1, 1, ☃xxx);
      PathPoint ☃xxxxx = this.func_203245_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c, 1, ☃xxx);
      PathPoint ☃xxxxxx = this.func_203245_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c, 1, ☃xxx);
      PathPoint ☃xxxxxxx = this.func_203245_a(☃.field_75839_a, ☃.field_75837_b, ☃.field_75838_c - 1, 1, ☃xxx);
      PathPoint ☃xxxxxxxx = this.func_203245_a(☃.field_75839_a, ☃.field_75837_b + 1, ☃.field_75838_c, 0, ☃xxx);
      PathPoint ☃xxxxxxxxx = this.func_203245_a(☃.field_75839_a, ☃.field_75837_b - 1, ☃.field_75838_c, 1, ☃xxx);
      if (☃xxxx != null && !☃xxxx.field_75842_i && ☃xxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxx;
      }

      if (☃xxxxx != null && !☃xxxxx.field_75842_i && ☃xxxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxxx;
      }

      if (☃xxxxxx != null && !☃xxxxxx.field_75842_i && ☃xxxxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxxxx;
      }

      if (☃xxxxxxx != null && !☃xxxxxxx.field_75842_i && ☃xxxxxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxxxxx;
      }

      if (☃xxxxxxxx != null && !☃xxxxxxxx.field_75842_i && ☃xxxxxxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxxxxxx;
      }

      if (☃xxxxxxxxx != null && !☃xxxxxxxxx.field_75842_i && ☃xxxxxxxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxxxxxxx;
      }

      boolean ☃ = ☃xxxxxxx == null || ☃xxxxxxx.field_186287_m == PathNodeType.OPEN || ☃xxxxxxx.field_186286_l != 0.0F;
      boolean ☃x = ☃xxxx == null || ☃xxxx.field_186287_m == PathNodeType.OPEN || ☃xxxx.field_186286_l != 0.0F;
      boolean ☃xx = ☃xxxxxx == null || ☃xxxxxx.field_186287_m == PathNodeType.OPEN || ☃xxxxxx.field_186286_l != 0.0F;
      boolean ☃xxx = ☃xxxxx == null || ☃xxxxx.field_186287_m == PathNodeType.OPEN || ☃xxxxx.field_186286_l != 0.0F;
      if (☃ && ☃xxx) {
         PathPoint ☃xxxx = this.func_203245_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c - 1, 1, ☃xxx);
         if (☃xxxx != null && !☃xxxx.field_75842_i && ☃xxxx.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃xxxx;
         }
      }

      if (☃ && ☃xx) {
         PathPoint ☃ = this.func_203245_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c - 1, 1, ☃xxx);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃x && ☃xxx) {
         PathPoint ☃ = this.func_203245_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c + 1, 1, ☃xxx);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃x && ☃xx) {
         PathPoint ☃ = this.func_203245_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c + 1, 1, ☃xxx);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      return ☃;
   }

   private double func_203246_a(BlockPos var1) {
      if (!this.field_186326_b.func_70090_H()) {
         BlockPos ☃ = ☃.func_177977_b();
         VoxelShape ☃x = this.field_176169_a.func_180495_p(☃).func_196952_d(this.field_176169_a, ☃);
         return (double)☃.func_177956_o() + (☃x.func_197766_b() ? 0.0 : ☃x.func_197758_c(EnumFacing.Axis.Y));
      } else {
         return (double)☃.func_177956_o() + 0.5;
      }
   }

   @Nullable
   private PathPoint func_203245_a(int var1, int var2, int var3, int var4, double var5) {
      PathPoint ☃ = null;
      BlockPos ☃x = new BlockPos(☃, ☃, ☃);
      double ☃xx = this.func_203246_a(☃x);
      if (☃xx - ☃ > 1.125) {
         return null;
      } else {
         PathNodeType ☃ = this.func_186319_a(
            this.field_176169_a, ☃, ☃, ☃, this.field_186326_b, this.field_176168_c, this.field_176165_d, this.field_176166_e, false, false
         );
         float ☃x = this.field_186326_b.func_184643_a(☃);
         double ☃xx = (double)this.field_186326_b.field_70130_N / 2.0;
         if (☃x >= 0.0F) {
            ☃ = this.func_176159_a(☃, ☃, ☃);
            ☃.field_186287_m = ☃;
            ☃.field_186286_l = Math.max(☃.field_186286_l, ☃x);
         }

         if (☃ != PathNodeType.WATER && ☃ != PathNodeType.WALKABLE) {
            if (☃ == null && ☃ > 0 && ☃ != PathNodeType.FENCE && ☃ != PathNodeType.TRAPDOOR) {
               ☃ = this.func_203245_a(☃, ☃ + 1, ☃, ☃ - 1, ☃);
            }

            if (☃ == PathNodeType.OPEN) {
               AxisAlignedBB ☃ = new AxisAlignedBB(
                  (double)☃ - ☃xx + 0.5,
                  (double)☃ + 0.001,
                  (double)☃ - ☃xx + 0.5,
                  (double)☃ + ☃xx + 0.5,
                  (double)((float)☃ + this.field_186326_b.field_70131_O),
                  (double)☃ + ☃xx + 0.5
               );
               if (!this.field_186326_b.field_70170_p.func_195586_b(null, ☃)) {
                  return null;
               }

               PathNodeType ☃ = this.func_186319_a(
                  this.field_176169_a, ☃, ☃ - 1, ☃, this.field_186326_b, this.field_176168_c, this.field_176165_d, this.field_176166_e, false, false
               );
               if (☃ == PathNodeType.BLOCKED) {
                  ☃ = this.func_176159_a(☃, ☃, ☃);
                  ☃.field_186287_m = PathNodeType.WALKABLE;
                  ☃.field_186286_l = Math.max(☃.field_186286_l, ☃x);
                  return ☃;
               }

               if (☃ == PathNodeType.WATER) {
                  ☃ = this.func_176159_a(☃, ☃, ☃);
                  ☃.field_186287_m = PathNodeType.WATER;
                  ☃.field_186286_l = Math.max(☃.field_186286_l, ☃x);
                  return ☃;
               }

               int ☃ = 0;

               while(☃ > 0 && ☃ == PathNodeType.OPEN) {
                  --☃;
                  if (☃++ >= this.field_186326_b.func_82143_as()) {
                     return null;
                  }

                  ☃ = this.func_186319_a(
                     this.field_176169_a, ☃, ☃, ☃, this.field_186326_b, this.field_176168_c, this.field_176165_d, this.field_176166_e, false, false
                  );
                  ☃x = this.field_186326_b.func_184643_a(☃);
                  if (☃ != PathNodeType.OPEN && ☃x >= 0.0F) {
                     ☃ = this.func_176159_a(☃, ☃, ☃);
                     ☃.field_186287_m = ☃;
                     ☃.field_186286_l = Math.max(☃.field_186286_l, ☃x);
                     break;
                  }

                  if (☃x < 0.0F) {
                     return null;
                  }
               }
            }

            return ☃;
         } else {
            if (☃ < this.field_186326_b.field_70170_p.func_181545_F() - 10 && ☃ != null) {
               ++☃.field_186286_l;
            }

            return ☃;
         }
      }
   }

   @Override
   public PathNodeType func_193577_a(
      IBlockReader var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      boolean var8,
      boolean var9,
      EnumSet<PathNodeType> var10,
      PathNodeType var11,
      BlockPos var12
   ) {
      for(int ☃ = 0; ☃ < ☃; ++☃) {
         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
               int ☃xxx = ☃ + ☃;
               int ☃xxxx = ☃x + ☃;
               int ☃xxxxx = ☃xx + ☃;
               PathNodeType ☃xxxxxx = this.func_186330_a(☃, ☃xxx, ☃xxxx, ☃xxxxx);
               if (☃xxxxxx == PathNodeType.RAIL
                  && !(☃.func_180495_p(☃).func_177230_c() instanceof BlockRailBase)
                  && !(☃.func_180495_p(☃.func_177977_b()).func_177230_c() instanceof BlockRailBase)) {
                  ☃xxxxxx = PathNodeType.FENCE;
               }

               if (☃xxxxxx == PathNodeType.DOOR_OPEN || ☃xxxxxx == PathNodeType.DOOR_WOOD_CLOSED || ☃xxxxxx == PathNodeType.DOOR_IRON_CLOSED) {
                  ☃xxxxxx = PathNodeType.BLOCKED;
               }

               if (☃ == 0 && ☃x == 0 && ☃xx == 0) {
                  ☃ = ☃xxxxxx;
               }

               ☃.add(☃xxxxxx);
            }
         }
      }

      return ☃;
   }

   @Override
   public PathNodeType func_186330_a(IBlockReader var1, int var2, int var3, int var4) {
      PathNodeType ☃ = this.func_189553_b(☃, ☃, ☃, ☃);
      if (☃ == PathNodeType.WATER) {
         for(EnumFacing ☃x : EnumFacing.values()) {
            PathNodeType ☃xx = this.func_189553_b(☃, ☃ + ☃x.func_82601_c(), ☃ + ☃x.func_96559_d(), ☃ + ☃x.func_82599_e());
            if (☃xx == PathNodeType.BLOCKED) {
               return PathNodeType.WATER_BORDER;
            }
         }

         return PathNodeType.WATER;
      } else {
         if (☃ == PathNodeType.OPEN && ☃ >= 1) {
            Block ☃ = ☃.func_180495_p(new BlockPos(☃, ☃ - 1, ☃)).func_177230_c();
            PathNodeType ☃x = this.func_189553_b(☃, ☃, ☃ - 1, ☃);
            if (☃x != PathNodeType.WALKABLE && ☃x != PathNodeType.OPEN && ☃x != PathNodeType.LAVA) {
               ☃ = PathNodeType.WALKABLE;
            } else {
               ☃ = PathNodeType.OPEN;
            }

            if (☃x == PathNodeType.DAMAGE_FIRE || ☃ == Blocks.field_196814_hQ) {
               ☃ = PathNodeType.DAMAGE_FIRE;
            }

            if (☃x == PathNodeType.DAMAGE_CACTUS) {
               ☃ = PathNodeType.DAMAGE_CACTUS;
            }
         }

         return this.func_193578_a(☃, ☃, ☃, ☃, ☃);
      }
   }
}
