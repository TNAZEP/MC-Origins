package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.EnumFacing;

public class VoxelShapeSplit extends VoxelShape {
   private final VoxelShape field_197776_a;
   private final EnumFacing.Axis field_197777_b;
   private final DoubleList field_197778_c = new DoubleRangeList(1);

   public VoxelShapeSplit(VoxelShape var1, EnumFacing.Axis var2, int var3) {
      super(func_197775_a(☃.field_197768_g, ☃, ☃));
      this.field_197776_a = ☃;
      this.field_197777_b = ☃;
   }

   private static VoxelShapePart func_197775_a(VoxelShapePart var0, EnumFacing.Axis var1, int var2) {
      return new VoxelShapePartSplit(
         ☃,
         ☃.func_196052_a(☃, 0, 0),
         ☃.func_196052_a(0, ☃, 0),
         ☃.func_196052_a(0, 0, ☃),
         ☃.func_196052_a(☃ + 1, ☃.field_197838_b, ☃.field_197838_b),
         ☃.func_196052_a(☃.field_197839_c, ☃ + 1, ☃.field_197839_c),
         ☃.func_196052_a(☃.field_197840_d, ☃.field_197840_d, ☃ + 1)
      );
   }

   @Override
   protected DoubleList func_197757_a(EnumFacing.Axis var1) {
      return ☃ == this.field_197777_b ? this.field_197778_c : this.field_197776_a.func_197757_a(☃);
   }
}
