package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import net.minecraft.util.EnumFacing;

final class VoxelShapeArray extends VoxelShape {
   private final DoubleList field_197782_a;
   private final DoubleList field_197783_b;
   private final DoubleList field_197784_c;

   VoxelShapeArray(VoxelShapePart var1, double[] var2, double[] var3, double[] var4) {
      this(
         ☃,
         DoubleArrayList.wrap(Arrays.copyOf(☃, ☃.func_197823_b() + 1)),
         DoubleArrayList.wrap(Arrays.copyOf(☃, ☃.func_197820_c() + 1)),
         DoubleArrayList.wrap(Arrays.copyOf(☃, ☃.func_197821_d() + 1))
      );
   }

   VoxelShapeArray(VoxelShapePart var1, DoubleList var2, DoubleList var3, DoubleList var4) {
      super(☃);
      int ☃ = ☃.func_197823_b() + 1;
      int ☃x = ☃.func_197820_c() + 1;
      int ☃xx = ☃.func_197821_d() + 1;
      if (☃ == ☃.size() && ☃x == ☃.size() && ☃xx == ☃.size()) {
         this.field_197782_a = ☃;
         this.field_197783_b = ☃;
         this.field_197784_c = ☃;
      } else {
         throw new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape.");
      }
   }

   @Override
   protected DoubleList func_197757_a(EnumFacing.Axis var1) {
      switch(☃) {
         case X:
            return this.field_197782_a;
         case Y:
            return this.field_197783_b;
         case Z:
            return this.field_197784_c;
         default:
            throw new IllegalArgumentException();
      }
   }
}
