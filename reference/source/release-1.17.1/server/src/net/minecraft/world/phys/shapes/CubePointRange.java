package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;

public class CubePointRange extends AbstractDoubleList {
   private final int parts;

   CubePointRange(int var1) {
      if (â˜ƒ <= 0) {
         throw new IllegalArgumentException("Need at least 1 part");
      } else {
         this.parts = â˜ƒ;
      }
   }

   @Override
   public double getDouble(int var1) {
      return (double)â˜ƒ / (double)this.parts;
   }

   public int size() {
      return this.parts + 1;
   }
}
