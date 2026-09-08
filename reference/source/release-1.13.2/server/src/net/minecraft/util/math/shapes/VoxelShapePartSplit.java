package net.minecraft.util.math.shapes;

import net.minecraft.util.EnumFacing;

final class VoxelShapePartSplit extends VoxelShapePart {
   private final VoxelShapePart field_197847_k;
   private final int field_197841_e;
   private final int field_197842_f;
   private final int field_197843_g;
   private final int field_197844_h;
   private final int field_197845_i;
   private final int field_197846_j;

   public VoxelShapePartSplit(VoxelShapePart var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(☃ - ☃, ☃ - ☃, ☃ - ☃);
      this.field_197847_k = ☃;
      this.field_197841_e = ☃;
      this.field_197842_f = ☃;
      this.field_197843_g = ☃;
      this.field_197844_h = ☃;
      this.field_197845_i = ☃;
      this.field_197846_j = ☃;
   }

   @Override
   public boolean func_197835_b(int var1, int var2, int var3) {
      return this.field_197847_k.func_197835_b(this.field_197841_e + ☃, this.field_197842_f + ☃, this.field_197843_g + ☃);
   }

   @Override
   public void func_199625_a(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.field_197847_k.func_199625_a(this.field_197841_e + ☃, this.field_197842_f + ☃, this.field_197843_g + ☃, ☃, ☃);
   }

   @Override
   public int func_199623_a(EnumFacing.Axis var1) {
      return Math.max(0, this.field_197847_k.func_199623_a(☃) - ☃.func_196052_a(this.field_197841_e, this.field_197842_f, this.field_197843_g));
   }

   @Override
   public int func_199624_b(EnumFacing.Axis var1) {
      return Math.min(
         ☃.func_196052_a(this.field_197844_h, this.field_197845_i, this.field_197846_j),
         this.field_197847_k.func_199624_b(☃) - ☃.func_196052_a(this.field_197841_e, this.field_197842_f, this.field_197843_g)
      );
   }
}
