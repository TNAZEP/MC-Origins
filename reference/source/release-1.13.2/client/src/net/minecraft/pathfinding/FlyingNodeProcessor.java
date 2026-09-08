package net.minecraft.pathfinding;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

public class FlyingNodeProcessor extends WalkNodeProcessor {
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
      } else {
         ☃ = MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72338_b + 0.5);
      }

      BlockPos ☃ = new BlockPos(this.field_186326_b);
      PathNodeType ☃x = this.func_192558_a(this.field_186326_b, ☃.func_177958_n(), ☃, ☃.func_177952_p());
      if (this.field_186326_b.func_184643_a(☃x) < 0.0F) {
         Set<BlockPos> ☃xx = Sets.<BlockPos>newHashSet();
         ☃xx.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)☃, this.field_186326_b.func_174813_aQ().field_72339_c));
         ☃xx.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)☃, this.field_186326_b.func_174813_aQ().field_72334_f));
         ☃xx.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)☃, this.field_186326_b.func_174813_aQ().field_72339_c));
         ☃xx.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)☃, this.field_186326_b.func_174813_aQ().field_72334_f));

         for(BlockPos ☃xxx : ☃xx) {
            PathNodeType ☃xxxx = this.func_192559_a(this.field_186326_b, ☃xxx);
            if (this.field_186326_b.func_184643_a(☃xxxx) >= 0.0F) {
               return super.func_176159_a(☃xxx.func_177958_n(), ☃xxx.func_177956_o(), ☃xxx.func_177952_p());
            }
         }
      }

      return super.func_176159_a(☃.func_177958_n(), ☃, ☃.func_177952_p());
   }

   @Override
   public PathPoint func_186325_a(double var1, double var3, double var5) {
      return super.func_176159_a(MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃));
   }

   @Override
   public int func_186320_a(PathPoint[] var1, PathPoint var2, PathPoint var3, float var4) {
      int ☃ = 0;
      PathPoint ☃x = this.func_176159_a(☃.field_75839_a, ☃.field_75837_b, ☃.field_75838_c + 1);
      PathPoint ☃xx = this.func_176159_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c);
      PathPoint ☃xxx = this.func_176159_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c);
      PathPoint ☃xxxx = this.func_176159_a(☃.field_75839_a, ☃.field_75837_b, ☃.field_75838_c - 1);
      PathPoint ☃xxxxx = this.func_176159_a(☃.field_75839_a, ☃.field_75837_b + 1, ☃.field_75838_c);
      PathPoint ☃xxxxxx = this.func_176159_a(☃.field_75839_a, ☃.field_75837_b - 1, ☃.field_75838_c);
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

      if (☃xxxxx != null && !☃xxxxx.field_75842_i && ☃xxxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxxx;
      }

      if (☃xxxxxx != null && !☃xxxxxx.field_75842_i && ☃xxxxxx.func_75829_a(☃) < ☃) {
         ☃[☃++] = ☃xxxxxx;
      }

      boolean ☃ = ☃xxxx == null || ☃xxxx.field_186286_l != 0.0F;
      boolean ☃x = ☃x == null || ☃x.field_186286_l != 0.0F;
      boolean ☃xx = ☃xxx == null || ☃xxx.field_186286_l != 0.0F;
      boolean ☃xxx = ☃xx == null || ☃xx.field_186286_l != 0.0F;
      boolean ☃xxxx = ☃xxxxx == null || ☃xxxxx.field_186286_l != 0.0F;
      boolean ☃xxxxx = ☃xxxxxx == null || ☃xxxxxx.field_186286_l != 0.0F;
      if (☃ && ☃xxx) {
         PathPoint ☃xxxxxx = this.func_176159_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c - 1);
         if (☃xxxxxx != null && !☃xxxxxx.field_75842_i && ☃xxxxxx.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃xxxxxx;
         }
      }

      if (☃ && ☃xx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c - 1);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃x && ☃xxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a - 1, ☃.field_75837_b, ☃.field_75838_c + 1);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃x && ☃xx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a + 1, ☃.field_75837_b, ☃.field_75838_c + 1);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃ && ☃xxxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a, ☃.field_75837_b + 1, ☃.field_75838_c - 1);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃x && ☃xxxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a, ☃.field_75837_b + 1, ☃.field_75838_c + 1);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃xx && ☃xxxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a + 1, ☃.field_75837_b + 1, ☃.field_75838_c);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃xxx && ☃xxxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a - 1, ☃.field_75837_b + 1, ☃.field_75838_c);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃ && ☃xxxxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a, ☃.field_75837_b - 1, ☃.field_75838_c - 1);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃x && ☃xxxxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a, ☃.field_75837_b - 1, ☃.field_75838_c + 1);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃xx && ☃xxxxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a + 1, ☃.field_75837_b - 1, ☃.field_75838_c);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      if (☃xxx && ☃xxxxx) {
         PathPoint ☃ = this.func_176159_a(☃.field_75839_a - 1, ☃.field_75837_b - 1, ☃.field_75838_c);
         if (☃ != null && !☃.field_75842_i && ☃.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃;
         }
      }

      return ☃;
   }

   @Nullable
   @Override
   protected PathPoint func_176159_a(int var1, int var2, int var3) {
      PathPoint ☃ = null;
      PathNodeType ☃x = this.func_192558_a(this.field_186326_b, ☃, ☃, ☃);
      float ☃xx = this.field_186326_b.func_184643_a(☃x);
      if (☃xx >= 0.0F) {
         ☃ = super.func_176159_a(☃, ☃, ☃);
         ☃.field_186287_m = ☃x;
         ☃.field_186286_l = Math.max(☃.field_186286_l, ☃xx);
         if (☃x == PathNodeType.WALKABLE) {
            ++☃.field_186286_l;
         }
      }

      return ☃x != PathNodeType.OPEN && ☃x != PathNodeType.WALKABLE ? ☃ : ☃;
   }

   @Override
   public PathNodeType func_186319_a(
      IBlockReader var1, int var2, int var3, int var4, EntityLiving var5, int var6, int var7, int var8, boolean var9, boolean var10
   ) {
      EnumSet<PathNodeType> ☃ = EnumSet.noneOf(PathNodeType.class);
      PathNodeType ☃x = PathNodeType.BLOCKED;
      BlockPos ☃xx = new BlockPos(☃);
      ☃x = this.func_193577_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃x, ☃xx);
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

   @Override
   public PathNodeType func_186330_a(IBlockReader var1, int var2, int var3, int var4) {
      PathNodeType ☃ = this.func_189553_b(☃, ☃, ☃, ☃);
      if (☃ == PathNodeType.OPEN && ☃ >= 1) {
         Block ☃x = ☃.func_180495_p(new BlockPos(☃, ☃ - 1, ☃)).func_177230_c();
         PathNodeType ☃xx = this.func_189553_b(☃, ☃, ☃ - 1, ☃);
         if (☃xx == PathNodeType.DAMAGE_FIRE || ☃x == Blocks.field_196814_hQ || ☃xx == PathNodeType.LAVA) {
            ☃ = PathNodeType.DAMAGE_FIRE;
         } else if (☃xx == PathNodeType.DAMAGE_CACTUS) {
            ☃ = PathNodeType.DAMAGE_CACTUS;
         } else {
            ☃ = ☃xx != PathNodeType.WALKABLE && ☃xx != PathNodeType.OPEN && ☃xx != PathNodeType.WATER ? PathNodeType.WALKABLE : PathNodeType.OPEN;
         }
      }

      return this.func_193578_a(☃, ☃, ☃, ☃, ☃);
   }

   private PathNodeType func_192559_a(EntityLiving var1, BlockPos var2) {
      return this.func_192558_a(☃, ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   private PathNodeType func_192558_a(EntityLiving var1, int var2, int var3, int var4) {
      return this.func_186319_a(
         this.field_176169_a, ☃, ☃, ☃, ☃, this.field_176168_c, this.field_176165_d, this.field_176166_e, this.func_186324_d(), this.func_186323_c()
      );
   }
}
