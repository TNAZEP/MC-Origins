package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class OffsetDoubleList extends AbstractDoubleList {
   private final DoubleList delegate;
   private final double offset;

   public OffsetDoubleList(DoubleList var1, double var2) {
      this.delegate = â˜ƒ;
      this.offset = â˜ƒ;
   }

   @Override
   public double getDouble(int var1) {
      return this.delegate.getDouble(â˜ƒ) + this.offset;
   }

   public int size() {
      return this.delegate.size();
   }
}
