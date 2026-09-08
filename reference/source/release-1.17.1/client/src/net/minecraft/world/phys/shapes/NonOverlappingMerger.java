package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class NonOverlappingMerger extends AbstractDoubleList implements IndexMerger {
   private final DoubleList lower;
   private final DoubleList upper;
   private final boolean swap;

   protected NonOverlappingMerger(DoubleList var1, DoubleList var2, boolean var3) {
      this.lower = â˜ƒ;
      this.upper = â˜ƒ;
      this.swap = â˜ƒ;
   }

   @Override
   public int size() {
      return this.lower.size() + this.upper.size();
   }

   @Override
   public boolean forMergedIndexes(IndexMerger.IndexConsumer var1) {
      return this.swap ? this.forNonSwappedIndexes((var1x, var2, var3) -> â˜ƒ.merge(var2, var1x, var3)) : this.forNonSwappedIndexes(â˜ƒ);
   }

   private boolean forNonSwappedIndexes(IndexMerger.IndexConsumer var1) {
      int â˜ƒ = this.lower.size();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         if (!â˜ƒ.merge(â˜ƒx, -1, â˜ƒx)) {
            return false;
         }
      }

      int â˜ƒx = this.upper.size() - 1;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         if (!â˜ƒ.merge(â˜ƒ - 1, â˜ƒxx, â˜ƒ + â˜ƒxx)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public double getDouble(int var1) {
      return â˜ƒ < this.lower.size() ? this.lower.getDouble(â˜ƒ) : this.upper.getDouble(â˜ƒ - this.lower.size());
   }

   @Override
   public DoubleList getList() {
      return this;
   }
}
