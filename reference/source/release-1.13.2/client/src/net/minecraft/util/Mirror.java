package net.minecraft.util;

public enum Mirror {
   NONE,
   LEFT_RIGHT,
   FRONT_BACK;

   public int func_185802_a(int var1, int var2) {
      int ☃ = ☃ / 2;
      int ☃x = ☃ > ☃ ? ☃ - ☃ : ☃;
      switch(this) {
         case FRONT_BACK:
            return (☃ - ☃x) % ☃;
         case LEFT_RIGHT:
            return (☃ - ☃x + ☃) % ☃;
         default:
            return ☃;
      }
   }

   public Rotation func_185800_a(EnumFacing var1) {
      EnumFacing.Axis ☃ = ☃.func_176740_k();
      return (this != LEFT_RIGHT || ☃ != EnumFacing.Axis.Z) && (this != FRONT_BACK || ☃ != EnumFacing.Axis.X) ? Rotation.NONE : Rotation.CLOCKWISE_180;
   }

   public EnumFacing func_185803_b(EnumFacing var1) {
      if (this == FRONT_BACK && ☃.func_176740_k() == EnumFacing.Axis.X) {
         return ☃.func_176734_d();
      } else {
         return this == LEFT_RIGHT && ☃.func_176740_k() == EnumFacing.Axis.Z ? ☃.func_176734_d() : ☃;
      }
   }
}
