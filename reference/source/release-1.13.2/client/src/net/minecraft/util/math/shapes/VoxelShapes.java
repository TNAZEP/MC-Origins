package net.minecraft.util.math.shapes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.util.AxisRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;

public final class VoxelShapes {
   private static final VoxelShape field_197886_a = new VoxelShapeArray(
      new VoxelShapePartBitSet(0, 0, 0), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0})
   );
   private static final VoxelShape field_197887_b = Util.func_199748_a(() -> {
      VoxelShapePart ☃ = new VoxelShapePartBitSet(1, 1, 1);
      ☃.func_199625_a(0, 0, 0, true, true);
      return new VoxelShapeCube(☃);
   });

   public static VoxelShape func_197880_a() {
      return field_197886_a;
   }

   public static VoxelShape func_197868_b() {
      return field_197887_b;
   }

   public static VoxelShape func_197873_a(double var0, double var2, double var4, double var6, double var8, double var10) {
      return func_197881_a(new AxisAlignedBB(☃, ☃, ☃, ☃, ☃, ☃));
   }

   public static VoxelShape func_197881_a(AxisAlignedBB var0) {
      int ☃ = func_197885_a(☃.field_72340_a, ☃.field_72336_d);
      int ☃x = func_197885_a(☃.field_72338_b, ☃.field_72337_e);
      int ☃xx = func_197885_a(☃.field_72339_c, ☃.field_72334_f);
      if (☃ >= 0 && ☃x >= 0 && ☃xx >= 0) {
         if (☃ == 0 && ☃x == 0 && ☃xx == 0) {
            return ☃.func_197744_e(0.5, 0.5, 0.5) ? func_197868_b() : func_197880_a();
         } else {
            int ☃xxx = 1 << ☃;
            int ☃xxxx = 1 << ☃x;
            int ☃xxxxx = 1 << ☃xx;
            int ☃xxxxxx = (int)Math.round(☃.field_72340_a * (double)☃xxx);
            int ☃xxxxxxx = (int)Math.round(☃.field_72336_d * (double)☃xxx);
            int ☃xxxxxxxx = (int)Math.round(☃.field_72338_b * (double)☃xxxx);
            int ☃xxxxxxxxx = (int)Math.round(☃.field_72337_e * (double)☃xxxx);
            int ☃xxxxxxxxxx = (int)Math.round(☃.field_72339_c * (double)☃xxxxx);
            int ☃xxxxxxxxxxx = (int)Math.round(☃.field_72334_f * (double)☃xxxxx);
            VoxelShapePartBitSet ☃xxxxxxxxxxxx = new VoxelShapePartBitSet(
               ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxxx
            );

            for(long ☃xxxxxxxxxxxxx = (long)☃xxxxxx; ☃xxxxxxxxxxxxx < (long)☃xxxxxxx; ++☃xxxxxxxxxxxxx) {
               for(long ☃xxxxxxxxxxxxxx = (long)☃xxxxxxxx; ☃xxxxxxxxxxxxxx < (long)☃xxxxxxxxx; ++☃xxxxxxxxxxxxxx) {
                  for(long ☃xxxxxxxxxxxxxxx = (long)☃xxxxxxxxxx; ☃xxxxxxxxxxxxxxx < (long)☃xxxxxxxxxxx; ++☃xxxxxxxxxxxxxxx) {
                     ☃xxxxxxxxxxxx.func_199625_a((int)☃xxxxxxxxxxxxx, (int)☃xxxxxxxxxxxxxx, (int)☃xxxxxxxxxxxxxxx, false, true);
                  }
               }
            }

            return new VoxelShapeCube(☃xxxxxxxxxxxx);
         }
      } else {
         return new VoxelShapeArray(
            field_197887_b.field_197768_g,
            new double[]{☃.field_72340_a, ☃.field_72336_d},
            new double[]{☃.field_72338_b, ☃.field_72337_e},
            new double[]{☃.field_72339_c, ☃.field_72334_f}
         );
      }
   }

   private static int func_197885_a(double var0, double var2) {
      if (!(☃ < -1.0E-7) && !(☃ > 1.0000001)) {
         for(int ☃ = 0; ☃ <= 3; ++☃) {
            double ☃x = ☃ * (double)(1 << ☃);
            double ☃xx = ☃ * (double)(1 << ☃);
            boolean ☃xxx = Math.abs(☃x - Math.floor(☃x)) < 1.0E-7;
            boolean ☃xxxx = Math.abs(☃xx - Math.floor(☃xx)) < 1.0E-7;
            if (☃xxx && ☃xxxx) {
               return ☃;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   protected static long func_197877_a(int var0, int var1) {
      return (long)☃ * (long)(☃ / IntMath.gcd(☃, ☃));
   }

   public static VoxelShape func_197872_a(VoxelShape var0, VoxelShape var1) {
      return func_197878_a(☃, ☃, IBooleanFunction.OR);
   }

   public static VoxelShape func_197878_a(VoxelShape var0, VoxelShape var1, IBooleanFunction var2) {
      return func_197882_b(☃, ☃, ☃).func_197753_c();
   }

   public static VoxelShape func_197882_b(VoxelShape var0, VoxelShape var1, IBooleanFunction var2) {
      if (☃.apply(false, false)) {
         throw new IllegalArgumentException();
      } else if (☃ == ☃) {
         return ☃.apply(true, true) ? ☃ : func_197880_a();
      } else {
         boolean ☃ = ☃.apply(true, false);
         boolean ☃x = ☃.apply(false, true);
         if (☃.func_197766_b()) {
            return ☃x ? ☃ : func_197880_a();
         } else if (☃.func_197766_b()) {
            return ☃ ? ☃ : func_197880_a();
         } else {
            IDoubleListMerger ☃ = func_199410_a(1, ☃.func_197757_a(EnumFacing.Axis.X), ☃.func_197757_a(EnumFacing.Axis.X), ☃, ☃x);
            IDoubleListMerger ☃x = func_199410_a(☃.func_212435_a().size() - 1, ☃.func_197757_a(EnumFacing.Axis.Y), ☃.func_197757_a(EnumFacing.Axis.Y), ☃, ☃x);
            IDoubleListMerger ☃xx = func_199410_a(
               (☃.func_212435_a().size() - 1) * (☃x.func_212435_a().size() - 1), ☃.func_197757_a(EnumFacing.Axis.Z), ☃.func_197757_a(EnumFacing.Axis.Z), ☃, ☃x
            );
            VoxelShapePartBitSet ☃xxx = VoxelShapePartBitSet.func_197852_a(☃.field_197768_g, ☃.field_197768_g, ☃, ☃x, ☃xx, ☃);
            return (VoxelShape)(☃ instanceof DoubleCubeMergingList && ☃x instanceof DoubleCubeMergingList && ☃xx instanceof DoubleCubeMergingList
               ? new VoxelShapeCube(☃xxx)
               : new VoxelShapeArray(☃xxx, ☃.func_212435_a(), ☃x.func_212435_a(), ☃xx.func_212435_a()));
         }
      }
   }

   public static boolean func_197879_c(VoxelShape var0, VoxelShape var1, IBooleanFunction var2) {
      if (☃.apply(false, false)) {
         throw new IllegalArgumentException();
      } else if (☃ == ☃) {
         return ☃.apply(true, true);
      } else if (☃.func_197766_b()) {
         return ☃.apply(false, !☃.func_197766_b());
      } else if (☃.func_197766_b()) {
         return ☃.apply(!☃.func_197766_b(), false);
      } else {
         boolean ☃ = ☃.apply(true, false);
         boolean ☃x = ☃.apply(false, true);

         for(EnumFacing.Axis ☃xx : AxisRotation.field_197521_d) {
            if (☃.func_197758_c(☃xx) < ☃.func_197762_b(☃xx) - 1.0E-7) {
               return ☃ || ☃x;
            }

            if (☃.func_197758_c(☃xx) < ☃.func_197762_b(☃xx) - 1.0E-7) {
               return ☃ || ☃x;
            }
         }

         IDoubleListMerger ☃xx = func_199410_a(1, ☃.func_197757_a(EnumFacing.Axis.X), ☃.func_197757_a(EnumFacing.Axis.X), ☃, ☃x);
         IDoubleListMerger ☃xxx = func_199410_a(☃xx.func_212435_a().size() - 1, ☃.func_197757_a(EnumFacing.Axis.Y), ☃.func_197757_a(EnumFacing.Axis.Y), ☃, ☃x);
         IDoubleListMerger ☃xxxx = func_199410_a(
            (☃xx.func_212435_a().size() - 1) * (☃xxx.func_212435_a().size() - 1), ☃.func_197757_a(EnumFacing.Axis.Z), ☃.func_197757_a(EnumFacing.Axis.Z), ☃, ☃x
         );
         return func_197874_a(☃xx, ☃xxx, ☃xxxx, ☃.field_197768_g, ☃.field_197768_g, ☃);
      }
   }

   private static boolean func_197874_a(
      IDoubleListMerger var0, IDoubleListMerger var1, IDoubleListMerger var2, VoxelShapePart var3, VoxelShapePart var4, IBooleanFunction var5
   ) {
      return !☃.func_197855_a(
         (var5x, var6, var7) -> ☃.func_197855_a(
               (var6x, var7x, var8) -> ☃.func_197855_a(
                     (var7xx, var8x, var9) -> !☃.apply(☃.func_197818_c(var5x, var6x, var7xx), ☃.func_197818_c(var6, var7x, var8x))
                  )
            )
      );
   }

   public static double func_212437_a(EnumFacing.Axis var0, AxisAlignedBB var1, Stream<VoxelShape> var2, double var3) {
      for(Iterator<VoxelShape> ☃ = ☃.iterator(); ☃.hasNext(); ☃ = ((VoxelShape)☃.next()).func_212430_a(☃, ☃, ☃)) {
         if (Math.abs(☃) < 1.0E-7) {
            return 0.0;
         }
      }

      return ☃;
   }

   public static boolean func_197875_a(VoxelShape var0, VoxelShape var1, EnumFacing var2) {
      if (☃ == func_197868_b() && ☃ == func_197868_b()) {
         return true;
      } else if (☃.func_197766_b()) {
         return false;
      } else {
         EnumFacing.Axis ☃ = ☃.func_176740_k();
         EnumFacing.AxisDirection ☃x = ☃.func_176743_c();
         VoxelShape ☃xx = ☃x == EnumFacing.AxisDirection.POSITIVE ? ☃ : ☃;
         VoxelShape ☃xxx = ☃x == EnumFacing.AxisDirection.POSITIVE ? ☃ : ☃;
         IBooleanFunction ☃xxxx = ☃x == EnumFacing.AxisDirection.POSITIVE ? IBooleanFunction.ONLY_FIRST : IBooleanFunction.ONLY_SECOND;
         return DoubleMath.fuzzyEquals(☃xx.func_197758_c(☃), 1.0, 1.0E-7)
            && DoubleMath.fuzzyEquals(☃xxx.func_197762_b(☃), 0.0, 1.0E-7)
            && !func_197879_c(new VoxelShapeSplit(☃xx, ☃, ☃xx.field_197768_g.func_197819_a(☃) - 1), new VoxelShapeSplit(☃xxx, ☃, 0), ☃xxxx);
      }
   }

   public static boolean func_204642_b(VoxelShape var0, VoxelShape var1, EnumFacing var2) {
      if (☃ != func_197868_b() && ☃ != func_197868_b()) {
         EnumFacing.Axis ☃ = ☃.func_176740_k();
         EnumFacing.AxisDirection ☃x = ☃.func_176743_c();
         VoxelShape ☃xx = ☃x == EnumFacing.AxisDirection.POSITIVE ? ☃ : ☃;
         VoxelShape ☃xxx = ☃x == EnumFacing.AxisDirection.POSITIVE ? ☃ : ☃;
         if (!DoubleMath.fuzzyEquals(☃xx.func_197758_c(☃), 1.0, 1.0E-7)) {
            ☃xx = func_197880_a();
         }

         if (!DoubleMath.fuzzyEquals(☃xxx.func_197762_b(☃), 0.0, 1.0E-7)) {
            ☃xxx = func_197880_a();
         }

         return !func_197879_c(
            func_197868_b(),
            func_197882_b(new VoxelShapeSplit(☃xx, ☃, ☃xx.field_197768_g.func_197819_a(☃) - 1), new VoxelShapeSplit(☃xxx, ☃, 0), IBooleanFunction.OR),
            IBooleanFunction.ONLY_FIRST
         );
      } else {
         return true;
      }
   }

   @VisibleForTesting
   protected static IDoubleListMerger func_199410_a(int var0, DoubleList var1, DoubleList var2, boolean var3, boolean var4) {
      if (☃ instanceof DoubleRangeList && ☃ instanceof DoubleRangeList) {
         int ☃ = ☃.size() - 1;
         int ☃x = ☃.size() - 1;
         long ☃xx = func_197877_a(☃, ☃x);
         if ((long)☃ * ☃xx <= 256L) {
            return new DoubleCubeMergingList(☃, ☃x);
         }
      }

      if (☃.getDouble(☃.size() - 1) < ☃.getDouble(0) - 1.0E-7) {
         return new NonOverlappingMerger(☃, ☃, false);
      } else if (☃.getDouble(☃.size() - 1) < ☃.getDouble(0) - 1.0E-7) {
         return new NonOverlappingMerger(☃, ☃, true);
      } else if (Objects.equals(☃, ☃)) {
         if (☃ instanceof SimpleDoubleMerger) {
            return (IDoubleListMerger)☃;
         } else {
            return (IDoubleListMerger)(☃ instanceof SimpleDoubleMerger ? (IDoubleListMerger)☃ : new SimpleDoubleMerger(☃));
         }
      } else {
         return new IndirectMerger(☃, ☃, ☃, ☃);
      }
   }

   public interface LineConsumer {
      void consume(double var1, double var3, double var5, double var7, double var9, double var11);
   }
}
