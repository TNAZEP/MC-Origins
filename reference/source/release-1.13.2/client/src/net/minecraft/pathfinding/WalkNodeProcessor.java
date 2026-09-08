package net.minecraft.pathfinding;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class WalkNodeProcessor extends NodeProcessor {
   protected float field_176183_h;

   @Override
   public void func_186315_a(IBlockReader var1, EntityLiving var2) {
      super.func_186315_a(☃, ☃);
      this.field_176183_h = ☃.func_184643_a(PathNodeType.WATER);
   }

   @Override
   public void func_176163_a() {
      this.field_186326_b.func_184644_a(PathNodeType.WATER, this.field_176183_h);
      super.func_176163_a();
   }

   @Override
   public PathPoint func_186318_b() {
      int ☃;
      if (this.func_186322_e() && this.field_186326_b.func_70090_H()) {
         ☃ = (int)this.field_186326_b.func_174813_aQ().field_72338_b;
         BlockPos.MutableBlockPos ☃x = new BlockPos.MutableBlockPos(
            MathHelper.func_76128_c(this.field_186326_b.field_70165_t), ☃, MathHelper.func_76128_c(this.field_186326_b.field_70161_v)
         );

         for(Block ☃xx = this.field_176169_a.func_180495_p(☃x).func_177230_c();
            ☃xx == Blocks.field_150355_j;
            ☃xx = this.field_176169_a.func_180495_p(☃x).func_177230_c()
         ) {
            ☃x.func_181079_c(MathHelper.func_76128_c(this.field_186326_b.field_70165_t), ++☃, MathHelper.func_76128_c(this.field_186326_b.field_70161_v));
         }

         --☃;
      } else if (this.field_186326_b.field_70122_E) {
         ☃ = MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72338_b + 0.5);
      } else {
         BlockPos ☃ = new BlockPos(this.field_186326_b);

         while(
            (this.field_176169_a.func_180495_p(☃).func_196958_f() || this.field_176169_a.func_180495_p(☃).func_196957_g(this.field_176169_a, ☃, PathType.LAND))
               && ☃.func_177956_o() > 0
         ) {
            ☃ = ☃.func_177977_b();
         }

         ☃ = ☃.func_177984_a().func_177956_o();
      }

      BlockPos ☃ = new BlockPos(this.field_186326_b);
      PathNodeType ☃x = this.func_186331_a(this.field_186326_b, ☃.func_177958_n(), ☃, ☃.func_177952_p());
      if (this.field_186326_b.func_184643_a(☃x) < 0.0F) {
         Set<BlockPos> ☃xx = Sets.<BlockPos>newHashSet();
         ☃xx.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)☃, this.field_186326_b.func_174813_aQ().field_72339_c));
         ☃xx.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)☃, this.field_186326_b.func_174813_aQ().field_72334_f));
         ☃xx.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)☃, this.field_186326_b.func_174813_aQ().field_72339_c));
         ☃xx.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)☃, this.field_186326_b.func_174813_aQ().field_72334_f));

         for(BlockPos ☃xxx : ☃xx) {
            PathNodeType ☃xxxx = this.func_186329_a(this.field_186326_b, ☃xxx);
            if (this.field_186326_b.func_184643_a(☃xxxx) >= 0.0F) {
               return this.func_176159_a(☃xxx.func_177958_n(), ☃xxx.func_177956_o(), ☃xxx.func_177952_p());
            }
         }
      }

      return this.func_176159_a(☃.func_177958_n(), ☃, ☃.func_177952_p());
   }

   @Override
   public PathPoint func_186325_a(double var1, double var3, double var5) {
      return this.func_176159_a(MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃));
   }

   @Override
   public int func_186320_a(PathPoint[] var1, PathPoint var2, PathPoint var3, float var4) {
      int ☃ = 0;
      int ☃x = 0;
      PathNodeType ☃xx = this.func_186331_a(this.field_186326_b, ☃.field_75839_a, ☃.field_75837_b + 1, ☃.field_75838_c);
      if (this.field_186326_b.func_184643_a(☃xx) >= 0.0F) {
         ☃x = MathHelper.func_76141_d(Math.max(1.0F, this.field_186326_b.field_70138_W));
      }

      double ☃ = func_197682_a(this.field_176169_a, new BlockPos(☃.field_75839_a, ☃.field_75837_b, ☃.field_75838_c));
      PathPoint ☃x = this.func_186332_a(☃.field_75839_a, ☃.field_75837_b, ☃.field_75838_c + 1, ☃x, ☃, EnumFacing.SOUTH);
      PathPoint ☃xx = this.func_186332_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c, ☃x, ☃, EnumFacing.WEST);
      PathPoint ☃xxx = this.func_186332_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c, ☃x, ☃, EnumFacing.EAST);
      PathPoint ☃xxxx = this.func_186332_a(☃.field_75839_a, ☃.field_75837_b, ☃.field_75838_c - 1, ☃x, ☃, EnumFacing.NORTH);
      if (☃x != null && !☃x.field_75842_i && ☃x.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃x;
      }

      if (☃xx != null && !☃xx.field_75842_i && ☃xx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xx;
      }

      if (☃xxx != null && !☃xxx.field_75842_i && ☃xxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxx;
      }

      if (☃xxxx != null && !☃xxxx.field_75842_i && ☃xxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxx;
      }

      boolean ☃ = ☃xxxx == null || ☃xxxx.field_186287_m == PathNodeType.OPEN || ☃xxxx.field_186286_l != 0.0F;
      boolean ☃x = ☃x == null || ☃x.field_186287_m == PathNodeType.OPEN || ☃x.field_186286_l != 0.0F;
      boolean ☃xx = ☃xxx == null || ☃xxx.field_186287_m == PathNodeType.OPEN || ☃xxx.field_186286_l != 0.0F;
      boolean ☃xxx = ☃xx == null || ☃xx.field_186287_m == PathNodeType.OPEN || ☃xx.field_186286_l != 0.0F;
      if (☃ && ☃xxx) {
         PathPoint ☃xxxx = this.func_186332_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c - 1, ☃x, ☃, EnumFacing.NORTH);
         if (☃xxxx != null && !☃xxxx.field_75842_i && ☃xxxx.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃xxxx;
         }
      }

      if (☃ && ☃xx) {
         PathPoint ☃ = this.func_186332_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c - 1, ☃x, ☃, EnumFacing.NORTH);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃x && ☃xxx) {
         PathPoint ☃ = this.func_186332_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c + 1, ☃x, ☃, EnumFacing.SOUTH);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃x && ☃xx) {
         PathPoint ☃ = this.func_186332_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c + 1, ☃x, ☃, EnumFacing.SOUTH);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      return ☃;
   }

   @Nullable
   private PathPoint func_186332_a(int var1, int var2, int var3, int var4, double var5, EnumFacing var7) {
      PathPoint ☃ = null;
      BlockPos ☃x = new BlockPos(☃, ☃, ☃);
      double ☃xx = func_197682_a(this.field_176169_a, ☃x);
      if (☃xx - ☃ > 1.125) {
         return null;
      } else {
         PathNodeType ☃ = this.func_186331_a(this.field_186326_b, ☃, ☃, ☃);
         float ☃x = this.field_186326_b.func_184643_a(☃);
         double ☃xx = (double)this.field_186326_b.field_70130_N / 2.0;
         if (☃x >= 0.0F) {
            ☃ = this.func_176159_a(☃, ☃, ☃);
            ☃.field_186287_m = ☃;
            ☃.field_186286_l = Math.max(☃.field_186286_l, ☃x);
         }

         if (☃ == PathNodeType.WALKABLE) {
            return ☃;
         } else {
            if (☃ == null && ☃ > 0 && ☃ != PathNodeType.FENCE && ☃ != PathNodeType.TRAPDOOR) {
               ☃ = this.func_186332_a(☃, ☃ + 1, ☃, ☃ - 1, ☃, ☃);
               if (☃ != null
                  && (☃.field_186287_m == PathNodeType.OPEN || ☃.field_186287_m == PathNodeType.WALKABLE)
                  && this.field_186326_b.field_70130_N < 1.0F) {
                  double ☃ = (double)(☃ - ☃.func_82601_c()) + 0.5;
                  double ☃x = (double)(☃ - ☃.func_82599_e()) + 0.5;
                  AxisAlignedBB ☃xx = new AxisAlignedBB(
                     ☃ - ☃xx,
                     (double)☃ + 0.001,
                     ☃x - ☃xx,
                     ☃ + ☃xx,
                     (double)this.field_186326_b.field_70131_O + func_197682_a(this.field_176169_a, ☃x.func_177984_a()) - 0.002,
                     ☃x + ☃xx
                  );
                  if (!this.field_186326_b.field_70170_p.func_195586_b(null, ☃xx)) {
                     ☃ = null;
                  }
               }
            }

            if (☃ == PathNodeType.WATER && !this.func_186322_e()) {
               if (this.func_186331_a(this.field_186326_b, ☃, ☃ - 1, ☃) != PathNodeType.WATER) {
                  return ☃;
               }

               while(☃ > 0) {
                  ☃ = this.func_186331_a(this.field_186326_b, ☃, --☃, ☃);
                  if (☃ != PathNodeType.WATER) {
                     return ☃;
                  }

                  ☃ = this.func_176159_a(☃, ☃, ☃);
                  ☃.field_186287_m = ☃;
                  ☃.field_186286_l = Math.max(☃.field_186286_l, this.field_186326_b.func_184643_a(☃));
               }
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

               if (this.field_186326_b.field_70130_N >= 1.0F) {
                  PathNodeType ☃ = this.func_186331_a(this.field_186326_b, ☃, ☃ - 1, ☃);
                  if (☃ == PathNodeType.BLOCKED) {
                     ☃ = this.func_176159_a(☃, ☃, ☃);
                     ☃.field_186287_m = PathNodeType.WALKABLE;
                     ☃.field_186286_l = Math.max(☃.field_186286_l, ☃x);
                     return ☃;
                  }
               }

               int ☃ = 0;

               while(☃ > 0 && ☃ == PathNodeType.OPEN) {
                  --☃;
                  if (☃++ >= this.field_186326_b.func_82143_as()) {
                     return null;
                  }

                  ☃ = this.func_186331_a(this.field_186326_b, ☃, ☃, ☃);
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
         }
      }
   }

   public static double func_197682_a(IBlockReader var0, BlockPos var1) {
      BlockPos ☃ = ☃.func_177977_b();
      VoxelShape ☃x = ☃.func_180495_p(☃).func_196952_d(☃, ☃);
      return (double)☃.func_177956_o() + (☃x.func_197766_b() ? 0.0 : ☃x.func_197758_c(EnumFacing.Axis.Y));
   }

   @Override
   public PathNodeType func_186319_a(
      IBlockReader var1, int var2, int var3, int var4, EntityLiving var5, int var6, int var7, int var8, boolean var9, boolean var10
   ) {
      EnumSet<PathNodeType> ☃ = EnumSet.noneOf(PathNodeType.class);
      PathNodeType ☃x = PathNodeType.BLOCKED;
      double ☃xx = (double)☃.field_70130_N / 2.0;
      BlockPos ☃xxx = new BlockPos(☃);
      ☃x = this.func_193577_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃x, ☃xxx);
      if (☃.contains(PathNodeType.FENCE)) {
         return PathNodeType.FENCE;
      } else {
         PathNodeType ☃ = PathNodeType.BLOCKED;

         for(PathNodeType ☃x : ☃) {
            if (☃.func_184643_a(☃x) < 0.0F) {
               return ☃x;
            }

            if (☃.func_184643_a(☃x) >= ☃.func_184643_a(☃)) {
               ☃ = ☃x;
            }
         }

         return ☃x == PathNodeType.OPEN && ☃.func_184643_a(☃) == 0.0F ? PathNodeType.OPEN : ☃;
      }
   }

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
               if (☃xxxxxx == PathNodeType.DOOR_WOOD_CLOSED && ☃ && ☃) {
                  ☃xxxxxx = PathNodeType.WALKABLE;
               }

               if (☃xxxxxx == PathNodeType.DOOR_OPEN && !☃) {
                  ☃xxxxxx = PathNodeType.BLOCKED;
               }

               if (☃xxxxxx == PathNodeType.RAIL
                  && !(☃.func_180495_p(☃).func_177230_c() instanceof BlockRailBase)
                  && !(☃.func_180495_p(☃.func_177977_b()).func_177230_c() instanceof BlockRailBase)) {
                  ☃xxxxxx = PathNodeType.FENCE;
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

   private PathNodeType func_186329_a(EntityLiving var1, BlockPos var2) {
      return this.func_186331_a(☃, ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   private PathNodeType func_186331_a(EntityLiving var1, int var2, int var3, int var4) {
      return this.func_186319_a(
         this.field_176169_a, ☃, ☃, ☃, ☃, this.field_176168_c, this.field_176165_d, this.field_176166_e, this.func_186324_d(), this.func_186323_c()
      );
   }

   @Override
   public PathNodeType func_186330_a(IBlockReader var1, int var2, int var3, int var4) {
      PathNodeType ☃ = this.func_189553_b(☃, ☃, ☃, ☃);
      if (☃ == PathNodeType.OPEN && ☃ >= 1) {
         Block ☃x = ☃.func_180495_p(new BlockPos(☃, ☃ - 1, ☃)).func_177230_c();
         PathNodeType ☃xx = this.func_189553_b(☃, ☃, ☃ - 1, ☃);
         ☃ = ☃xx != PathNodeType.WALKABLE && ☃xx != PathNodeType.OPEN && ☃xx != PathNodeType.WATER && ☃xx != PathNodeType.LAVA
            ? PathNodeType.WALKABLE
            : PathNodeType.OPEN;
         if (☃xx == PathNodeType.DAMAGE_FIRE || ☃x == Blocks.field_196814_hQ) {
            ☃ = PathNodeType.DAMAGE_FIRE;
         }

         if (☃xx == PathNodeType.DAMAGE_CACTUS) {
            ☃ = PathNodeType.DAMAGE_CACTUS;
         }
      }

      return this.func_193578_a(☃, ☃, ☃, ☃, ☃);
   }

   public PathNodeType func_193578_a(IBlockReader var1, int var2, int var3, int var4, PathNodeType var5) {
      if (☃ == PathNodeType.WALKABLE) {
         try (BlockPos.PooledMutableBlockPos ☃ = BlockPos.PooledMutableBlockPos.func_185346_s()) {
            for(int ☃x = -1; ☃x <= 1; ++☃x) {
               for(int ☃xx = -1; ☃xx <= 1; ++☃xx) {
                  if (☃x != 0 || ☃xx != 0) {
                     Block ☃xxx = ☃.func_180495_p(☃.func_181079_c(☃x + ☃, ☃, ☃xx + ☃)).func_177230_c();
                     if (☃xxx == Blocks.field_150434_aF) {
                        ☃ = PathNodeType.DANGER_CACTUS;
                     } else if (☃xxx == Blocks.field_150480_ab) {
                        ☃ = PathNodeType.DANGER_FIRE;
                     }
                  }
               }
            }
         }
      }

      return ☃;
   }

   protected PathNodeType func_189553_b(IBlockReader var1, int var2, int var3, int var4) {
      BlockPos ☃ = new BlockPos(☃, ☃, ☃);
      IBlockState ☃x = ☃.func_180495_p(☃);
      Block ☃xx = ☃x.func_177230_c();
      Material ☃xxx = ☃x.func_185904_a();
      if (☃x.func_196958_f()) {
         return PathNodeType.OPEN;
      } else if (☃xx.func_203417_a(BlockTags.field_212185_E) || ☃xx == Blocks.field_196651_dG) {
         return PathNodeType.TRAPDOOR;
      } else if (☃xx == Blocks.field_150480_ab) {
         return PathNodeType.DAMAGE_FIRE;
      } else if (☃xx == Blocks.field_150434_aF) {
         return PathNodeType.DAMAGE_CACTUS;
      } else if (☃xx instanceof BlockDoor && ☃xxx == Material.field_151575_d && !☃x.func_177229_b(BlockDoor.field_176519_b)) {
         return PathNodeType.DOOR_WOOD_CLOSED;
      } else if (☃xx instanceof BlockDoor && ☃xxx == Material.field_151573_f && !☃x.func_177229_b(BlockDoor.field_176519_b)) {
         return PathNodeType.DOOR_IRON_CLOSED;
      } else if (☃xx instanceof BlockDoor && ☃x.func_177229_b(BlockDoor.field_176519_b)) {
         return PathNodeType.DOOR_OPEN;
      } else if (☃xx instanceof BlockRailBase) {
         return PathNodeType.RAIL;
      } else if (!(☃xx instanceof BlockFence)
         && !(☃xx instanceof BlockWall)
         && (!(☃xx instanceof BlockFenceGate) || ☃x.func_177229_b(BlockFenceGate.field_176466_a))) {
         IFluidState ☃ = ☃.func_204610_c(☃);
         if (☃.func_206884_a(FluidTags.field_206959_a)) {
            return PathNodeType.WATER;
         } else if (☃.func_206884_a(FluidTags.field_206960_b)) {
            return PathNodeType.LAVA;
         } else {
            return ☃x.func_196957_g(☃, ☃, PathType.LAND) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
         }
      } else {
         return PathNodeType.FENCE;
      }
   }
}
