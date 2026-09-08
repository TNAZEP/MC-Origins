package net.minecraft.util;

public enum Rotation {
   NONE,
   CLOCKWISE_90,
   CLOCKWISE_180,
   COUNTERCLOCKWISE_90;

   public Rotation func_185830_a(Rotation var1) {
      switch(☃) {
         case CLOCKWISE_180:
            switch(this) {
               case NONE:
                  return CLOCKWISE_180;
               case CLOCKWISE_90:
                  return COUNTERCLOCKWISE_90;
               case CLOCKWISE_180:
                  return NONE;
               case COUNTERCLOCKWISE_90:
                  return CLOCKWISE_90;
            }
         case COUNTERCLOCKWISE_90:
            switch(this) {
               case NONE:
                  return COUNTERCLOCKWISE_90;
               case CLOCKWISE_90:
                  return NONE;
               case CLOCKWISE_180:
                  return CLOCKWISE_90;
               case COUNTERCLOCKWISE_90:
                  return CLOCKWISE_180;
            }
         case CLOCKWISE_90:
            switch(this) {
               case NONE:
                  return CLOCKWISE_90;
               case CLOCKWISE_90:
                  return CLOCKWISE_180;
               case CLOCKWISE_180:
                  return COUNTERCLOCKWISE_90;
               case COUNTERCLOCKWISE_90:
                  return NONE;
            }
         default:
            return this;
      }
   }

   public EnumFacing func_185831_a(EnumFacing var1) {
      if (☃.func_176740_k() == EnumFacing.Axis.Y) {
         return ☃;
      } else {
         switch(this) {
            case CLOCKWISE_90:
               return ☃.func_176746_e();
            case CLOCKWISE_180:
               return ☃.func_176734_d();
            case COUNTERCLOCKWISE_90:
               return ☃.func_176735_f();
            default:
               return ☃;
         }
      }
   }

   public int func_185833_a(int var1, int var2) {
      switch(this) {
         case CLOCKWISE_90:
            return (☃ + ☃ / 4) % ☃;
         case CLOCKWISE_180:
            return (☃ + ☃ / 2) % ☃;
         case COUNTERCLOCKWISE_90:
            return (☃ + ☃ * 3 / 4) % ☃;
         default:
            return ☃;
      }
   }
}
