package net.minecraft.pathfinding;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

public class SwimNodeProcessor extends NodeProcessor {
   private final boolean field_205202_j;

   public SwimNodeProcessor(boolean var1) {
      this.field_205202_j = ☃;
   }

   @Override
   public PathPoint func_186318_b() {
      return super.func_176159_a(
         MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72340_a),
         MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72338_b + 0.5),
         MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72339_c)
      );
   }

   @Override
   public PathPoint func_186325_a(double var1, double var3, double var5) {
      return super.func_176159_a(
         MathHelper.func_76128_c(☃ - (double)(this.field_186326_b.field_70130_N / 2.0F)),
         MathHelper.func_76128_c(☃ + 0.5),
         MathHelper.func_76128_c(☃ - (double)(this.field_186326_b.field_70130_N / 2.0F))
      );
   }

   @Override
   public int func_186320_a(PathPoint[] var1, PathPoint var2, PathPoint var3, float var4) {
      int ☃ = 0;

      for(EnumFacing ☃x : EnumFacing.values()) {
         PathPoint ☃xx = this.func_186328_b(☃.field_75839_a + ☃x.func_82601_c(), ☃.field_75837_b + ☃x.func_96559_d(), ☃.field_75838_c + ☃x.func_82599_e());
         if (☃xx != null && !☃xx.field_75842_i && ☃xx.func_75829_a(☃) < ☃) {
            ☃[☃++] = ☃xx;
         }
      }

      return ☃;
   }

   @Override
   public PathNodeType func_186319_a(
      IBlockReader var1, int var2, int var3, int var4, EntityLiving var5, int var6, int var7, int var8, boolean var9, boolean var10
   ) {
      return this.func_186330_a(☃, ☃, ☃, ☃);
   }

   @Override
   public PathNodeType func_186330_a(IBlockReader var1, int var2, int var3, int var4) {
      BlockPos ☃ = new BlockPos(☃, ☃, ☃);
      IFluidState ☃x = ☃.func_204610_c(☃);
      IBlockState ☃xx = ☃.func_180495_p(☃);
      if (☃x.func_206888_e() && ☃xx.func_196957_g(☃, ☃.func_177977_b(), PathType.WATER) && ☃xx.func_196958_f()) {
         return PathNodeType.BREACH;
      } else {
         return ☃x.func_206884_a(FluidTags.field_206959_a) && ☃xx.func_196957_g(☃, ☃, PathType.WATER) ? PathNodeType.WATER : PathNodeType.BLOCKED;
      }
   }

   @Nullable
   private PathPoint func_186328_b(int var1, int var2, int var3) {
      PathNodeType ☃ = this.func_186327_c(☃, ☃, ☃);
      return (!this.field_205202_j || ☃ != PathNodeType.BREACH) && ☃ != PathNodeType.WATER ? null : this.func_176159_a(☃, ☃, ☃);
   }

   @Nullable
   @Override
   protected PathPoint func_176159_a(int var1, int var2, int var3) {
      PathPoint ☃ = null;
      PathNodeType ☃x = this.func_186330_a(this.field_186326_b.field_70170_p, ☃, ☃, ☃);
      float ☃xx = this.field_186326_b.func_184643_a(☃x);
      if (☃xx >= 0.0F) {
         ☃ = super.func_176159_a(☃, ☃, ☃);
         ☃.field_186287_m = ☃x;
         ☃.field_186286_l = Math.max(☃.field_186286_l, ☃xx);
         if (this.field_176169_a.func_204610_c(new BlockPos(☃, ☃, ☃)).func_206888_e()) {
            ☃.field_186286_l += 8.0F;
         }
      }

      return ☃x == PathNodeType.OPEN ? ☃ : ☃;
   }

   private PathNodeType func_186327_c(int var1, int var2, int var3) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos();

      for(int ☃x = ☃; ☃x < ☃ + this.field_176168_c; ++☃x) {
         for(int ☃xx = ☃; ☃xx < ☃ + this.field_176165_d; ++☃xx) {
            for(int ☃xxx = ☃; ☃xxx < ☃ + this.field_176166_e; ++☃xxx) {
               IFluidState ☃xxxx = this.field_176169_a.func_204610_c(☃.func_181079_c(☃x, ☃xx, ☃xxx));
               IBlockState ☃xxxxx = this.field_176169_a.func_180495_p(☃.func_181079_c(☃x, ☃xx, ☃xxx));
               if (☃xxxx.func_206888_e() && ☃xxxxx.func_196957_g(this.field_176169_a, ☃.func_177977_b(), PathType.WATER) && ☃xxxxx.func_196958_f()) {
                  return PathNodeType.BREACH;
               }

               if (!☃xxxx.func_206884_a(FluidTags.field_206959_a)) {
                  return PathNodeType.BLOCKED;
               }
            }
         }
      }

      IBlockState ☃x = this.field_176169_a.func_180495_p(☃);
      return ☃x.func_196957_g(this.field_176169_a, ☃, PathType.WATER) ? PathNodeType.WATER : PathNodeType.BLOCKED;
   }
}
