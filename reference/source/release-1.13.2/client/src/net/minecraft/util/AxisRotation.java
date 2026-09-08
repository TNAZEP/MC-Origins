package net.minecraft.util;

public enum AxisRotation {
   NONE {
      @Override
      public int func_197517_a(int var1, int var2, int var3, EnumFacing.Axis var4) {
         return ☃.func_196052_a(☃, ☃, ☃);
      }

      @Override
      public EnumFacing.Axis func_197513_a(EnumFacing.Axis var1) {
         return ☃;
      }

      @Override
      public AxisRotation func_197514_a() {
         return this;
      }
   },
   FORWARD {
      @Override
      public int func_197517_a(int var1, int var2, int var3, EnumFacing.Axis var4) {
         return ☃.func_196052_a(☃, ☃, ☃);
      }

      @Override
      public EnumFacing.Axis func_197513_a(EnumFacing.Axis var1) {
         return field_197521_d[Math.floorMod(☃.ordinal() + 1, 3)];
      }

      @Override
      public AxisRotation func_197514_a() {
         return BACKWARD;
      }
   },
   BACKWARD {
      @Override
      public int func_197517_a(int var1, int var2, int var3, EnumFacing.Axis var4) {
         return ☃.func_196052_a(☃, ☃, ☃);
      }

      @Override
      public EnumFacing.Axis func_197513_a(EnumFacing.Axis var1) {
         return field_197521_d[Math.floorMod(☃.ordinal() - 1, 3)];
      }

      @Override
      public AxisRotation func_197514_a() {
         return FORWARD;
      }
   };

   public static final EnumFacing.Axis[] field_197521_d = EnumFacing.Axis.values();
   public static final AxisRotation[] field_197522_e = values();

   private AxisRotation() {
   }

   public abstract int func_197517_a(int var1, int var2, int var3, EnumFacing.Axis var4);

   public abstract EnumFacing.Axis func_197513_a(EnumFacing.Axis var1);

   public abstract AxisRotation func_197514_a();

   public static AxisRotation func_197516_a(EnumFacing.Axis var0, EnumFacing.Axis var1) {
      return field_197522_e[Math.floorMod(☃.ordinal() - ☃.ordinal(), 3)];
   }
}
