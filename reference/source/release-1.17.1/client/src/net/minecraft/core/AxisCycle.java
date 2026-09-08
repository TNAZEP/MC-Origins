package net.minecraft.core;

public enum AxisCycle {
   NONE {
      @Override
      public int cycle(int var1, int var2, int var3, Direction.Axis var4) {
         return â˜ƒ.choose(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public double cycle(double var1, double var3, double var5, Direction.Axis var7) {
         return â˜ƒ.choose(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public Direction.Axis cycle(Direction.Axis var1) {
         return â˜ƒ;
      }

      @Override
      public AxisCycle inverse() {
         return this;
      }
   },
   FORWARD {
      @Override
      public int cycle(int var1, int var2, int var3, Direction.Axis var4) {
         return â˜ƒ.choose(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public double cycle(double var1, double var3, double var5, Direction.Axis var7) {
         return â˜ƒ.choose(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public Direction.Axis cycle(Direction.Axis var1) {
         return AXIS_VALUES[Math.floorMod(â˜ƒ.ordinal() + 1, 3)];
      }

      @Override
      public AxisCycle inverse() {
         return BACKWARD;
      }
   },
   BACKWARD {
      @Override
      public int cycle(int var1, int var2, int var3, Direction.Axis var4) {
         return â˜ƒ.choose(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public double cycle(double var1, double var3, double var5, Direction.Axis var7) {
         return â˜ƒ.choose(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public Direction.Axis cycle(Direction.Axis var1) {
         return AXIS_VALUES[Math.floorMod(â˜ƒ.ordinal() - 1, 3)];
      }

      @Override
      public AxisCycle inverse() {
         return FORWARD;
      }
   };

   public static final Direction.Axis[] AXIS_VALUES = Direction.Axis.values();
   public static final AxisCycle[] VALUES = values();

   public abstract int cycle(int var1, int var2, int var3, Direction.Axis var4);

   public abstract double cycle(double var1, double var3, double var5, Direction.Axis var7);

   public abstract Direction.Axis cycle(Direction.Axis var1);

   public abstract AxisCycle inverse();

   public static AxisCycle between(Direction.Axis var0, Direction.Axis var1) {
      return VALUES[Math.floorMod(â˜ƒ.ordinal() - â˜ƒ.ordinal(), 3)];
   }
}
