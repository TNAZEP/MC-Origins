package net.minecraft.util.math.shapes;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.AxisRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public abstract class VoxelShape {
   protected final VoxelShapePart field_197768_g;

   VoxelShape(VoxelShapePart var1) {
      this.field_197768_g = ☃;
   }

   public double func_197762_b(EnumFacing.Axis var1) {
      int ☃ = this.field_197768_g.func_199623_a(☃);
      return ☃ >= this.field_197768_g.func_197819_a(☃) ? Double.POSITIVE_INFINITY : this.func_197759_b(☃, ☃);
   }

   public double func_197758_c(EnumFacing.Axis var1) {
      int ☃ = this.field_197768_g.func_199624_b(☃);
      return ☃ <= 0 ? Double.NEGATIVE_INFINITY : this.func_197759_b(☃, ☃);
   }

   public AxisAlignedBB func_197752_a() {
      if (this.func_197766_b()) {
         throw new UnsupportedOperationException("No bounds for empty shape.");
      } else {
         return new AxisAlignedBB(
            this.func_197762_b(EnumFacing.Axis.X),
            this.func_197762_b(EnumFacing.Axis.Y),
            this.func_197762_b(EnumFacing.Axis.Z),
            this.func_197758_c(EnumFacing.Axis.X),
            this.func_197758_c(EnumFacing.Axis.Y),
            this.func_197758_c(EnumFacing.Axis.Z)
         );
      }
   }

   protected double func_197759_b(EnumFacing.Axis var1, int var2) {
      return this.func_197757_a(☃).getDouble(☃);
   }

   protected abstract DoubleList func_197757_a(EnumFacing.Axis var1);

   public boolean func_197766_b() {
      return this.field_197768_g.func_197830_a();
   }

   public VoxelShape func_197751_a(double var1, double var3, double var5) {
      return (VoxelShape)(this.func_197766_b()
         ? VoxelShapes.func_197880_a()
         : new VoxelShapeArray(
            this.field_197768_g,
            new OffsetDoubleList(this.func_197757_a(EnumFacing.Axis.X), ☃),
            new OffsetDoubleList(this.func_197757_a(EnumFacing.Axis.Y), ☃),
            new OffsetDoubleList(this.func_197757_a(EnumFacing.Axis.Z), ☃)
         ));
   }

   public VoxelShape func_197753_c() {
      VoxelShape[] ☃ = new VoxelShape[]{VoxelShapes.func_197880_a()};
      this.func_197755_b(
         (var1x, var3, var5, var7, var9, var11) -> ☃[0] = VoxelShapes.func_197882_b(
               ☃[0], VoxelShapes.func_197873_a(var1x, var3, var5, var7, var9, var11), IBooleanFunction.OR
            )
      );
      return ☃[0];
   }

   public void func_197755_b(VoxelShapes.LineConsumer var1) {
      this.field_197768_g
         .func_197831_b(
            (var2, var3, var4, var5, var6, var7) -> ☃.consume(
                  this.func_197759_b(EnumFacing.Axis.X, var2),
                  this.func_197759_b(EnumFacing.Axis.Y, var3),
                  this.func_197759_b(EnumFacing.Axis.Z, var4),
                  this.func_197759_b(EnumFacing.Axis.X, var5),
                  this.func_197759_b(EnumFacing.Axis.Y, var6),
                  this.func_197759_b(EnumFacing.Axis.Z, var7)
               ),
            true
         );
   }

   public List<AxisAlignedBB> func_197756_d() {
      List<AxisAlignedBB> ☃ = Lists.<AxisAlignedBB>newArrayList();
      this.func_197755_b((var1x, var3, var5, var7, var9, var11) -> ☃.add(new AxisAlignedBB(var1x, var3, var5, var7, var9, var11)));
      return ☃;
   }

   protected int func_197749_a(EnumFacing.Axis var1, double var2) {
      return MathHelper.func_199093_a(0, this.field_197768_g.func_197819_a(☃) + 1, var4 -> {
         if (var4 < 0) {
            return false;
         } else if (var4 > this.field_197768_g.func_197819_a(☃)) {
            return true;
         } else {
            return ☃ < this.func_197759_b(☃, var4);
         }
      }) - 1;
   }

   protected boolean func_211542_b(double var1, double var3, double var5) {
      return this.field_197768_g
         .func_197818_c(this.func_197749_a(EnumFacing.Axis.X, ☃), this.func_197749_a(EnumFacing.Axis.Y, ☃), this.func_197749_a(EnumFacing.Axis.Z, ☃));
   }

   @Nullable
   public RayTraceResult func_212433_a(Vec3d var1, Vec3d var2, BlockPos var3) {
      if (this.func_197766_b()) {
         return null;
      } else {
         Vec3d ☃ = ☃.func_178788_d(☃);
         if (☃.func_189985_c() < 1.0E-7) {
            return null;
         } else {
            Vec3d ☃ = ☃.func_178787_e(☃.func_186678_a(0.001));
            Vec3d ☃x = ☃.func_178787_e(☃.func_186678_a(0.001)).func_178786_a((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p());
            return this.func_211542_b(☃x.field_72450_a, ☃x.field_72448_b, ☃x.field_72449_c)
               ? new RayTraceResult(☃, EnumFacing.func_210769_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c), ☃)
               : AxisAlignedBB.func_197743_a(this.func_197756_d(), ☃, ☃, ☃);
         }
      }
   }

   public VoxelShape func_212434_a(EnumFacing var1) {
      if (!this.func_197766_b() && this != VoxelShapes.func_197868_b()) {
         EnumFacing.Axis ☃ = ☃.func_176740_k();
         EnumFacing.AxisDirection ☃x = ☃.func_176743_c();
         DoubleList ☃xx = this.func_197757_a(☃);
         if (☃xx.size() == 2 && DoubleMath.fuzzyEquals(☃xx.getDouble(0), 0.0, 1.0E-7) && DoubleMath.fuzzyEquals(☃xx.getDouble(1), 1.0, 1.0E-7)) {
            return this;
         } else {
            int ☃ = this.func_197749_a(☃, ☃x == EnumFacing.AxisDirection.POSITIVE ? 0.9999999 : 1.0E-7);
            return new VoxelShapeSplit(this, ☃, ☃);
         }
      } else {
         return this;
      }
   }

   public double func_212430_a(EnumFacing.Axis var1, AxisAlignedBB var2, double var3) {
      return this.func_212431_a(AxisRotation.func_197516_a(☃, EnumFacing.Axis.X), ☃, ☃);
   }

   protected double func_212431_a(AxisRotation var1, AxisAlignedBB var2, double var3) {
      if (this.func_197766_b()) {
         return ☃;
      } else if (Math.abs(☃) < 1.0E-7) {
         return 0.0;
      } else {
         AxisRotation ☃ = ☃.func_197514_a();
         EnumFacing.Axis ☃x = ☃.func_197513_a(EnumFacing.Axis.X);
         EnumFacing.Axis ☃xx = ☃.func_197513_a(EnumFacing.Axis.Y);
         EnumFacing.Axis ☃xxx = ☃.func_197513_a(EnumFacing.Axis.Z);
         double ☃xxxx = ☃.func_197742_b(☃x);
         double ☃xxxxx = ☃.func_197745_a(☃x);
         int ☃xxxxxx = this.func_197749_a(☃x, ☃xxxxx + 1.0E-7);
         int ☃xxxxxxx = this.func_197749_a(☃x, ☃xxxx - 1.0E-7);
         int ☃xxxxxxxx = Math.max(0, this.func_197749_a(☃xx, ☃.func_197745_a(☃xx) + 1.0E-7));
         int ☃xxxxxxxxx = Math.min(this.field_197768_g.func_197819_a(☃xx), this.func_197749_a(☃xx, ☃.func_197742_b(☃xx) - 1.0E-7) + 1);
         int ☃xxxxxxxxxx = Math.max(0, this.func_197749_a(☃xxx, ☃.func_197745_a(☃xxx) + 1.0E-7));
         int ☃xxxxxxxxxxx = Math.min(this.field_197768_g.func_197819_a(☃xxx), this.func_197749_a(☃xxx, ☃.func_197742_b(☃xxx) - 1.0E-7) + 1);
         int ☃xxxxxxxxxxxx = this.field_197768_g.func_197819_a(☃x);
         if (☃ > 0.0) {
            for(int ☃xxxxxxxxxxxxx = ☃xxxxxxx + 1; ☃xxxxxxxxxxxxx < ☃xxxxxxxxxxxx; ++☃xxxxxxxxxxxxx) {
               for(int ☃xxxxxxxxxxxxxx = ☃xxxxxxxx; ☃xxxxxxxxxxxxxx < ☃xxxxxxxxx; ++☃xxxxxxxxxxxxxx) {
                  for(int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxx; ☃xxxxxxxxxxxxxxx < ☃xxxxxxxxxxx; ++☃xxxxxxxxxxxxxxx) {
                     if (this.field_197768_g.func_197824_a(☃, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)) {
                        double ☃xxxxxxxxxxxxxxxx = this.func_197759_b(☃x, ☃xxxxxxxxxxxxx) - ☃xxxx;
                        if (☃xxxxxxxxxxxxxxxx >= -1.0E-7) {
                           ☃ = Math.min(☃, ☃xxxxxxxxxxxxxxxx);
                        }

                        return ☃;
                     }
                  }
               }
            }
         } else if (☃ < 0.0) {
            for(int ☃ = ☃xxxxxx - 1; ☃ >= 0; --☃) {
               for(int ☃x = ☃xxxxxxxx; ☃x < ☃xxxxxxxxx; ++☃x) {
                  for(int ☃xx = ☃xxxxxxxxxx; ☃xx < ☃xxxxxxxxxxx; ++☃xx) {
                     if (this.field_197768_g.func_197824_a(☃, ☃, ☃x, ☃xx)) {
                        double ☃xxx = this.func_197759_b(☃x, ☃ + 1) - ☃xxxxx;
                        if (☃xxx <= 1.0E-7) {
                           ☃ = Math.max(☃, ☃xxx);
                        }

                        return ☃;
                     }
                  }
               }
            }
         }

         return ☃;
      }
   }

   public String toString() {
      return this.func_197766_b() ? "EMPTY" : "VoxelShape[" + this.func_197752_a() + "]";
   }
}
