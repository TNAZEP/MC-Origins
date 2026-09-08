package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;

public class IdenticalMerger implements IndexMerger {
   private final DoubleList coords;

   public IdenticalMerger(DoubleList var1) {
      this.coords = â˜ƒ;
   }

   @Override
   public boolean forMergedIndexes(IndexMerger.IndexConsumer var1) {
      int â˜ƒ = this.coords.size() - 1;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         if (!â˜ƒ.merge(â˜ƒx, â˜ƒx, â˜ƒx)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int size() {
      return this.coords.size();
   }

   @Override
   public DoubleList getList() {
      return this.coords;
   }
}
