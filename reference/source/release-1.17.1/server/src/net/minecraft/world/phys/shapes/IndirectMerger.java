package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleLists;

public class IndirectMerger implements IndexMerger {
   private static final DoubleList EMPTY = DoubleLists.unmodifiable(DoubleArrayList.wrap(new double[]{0.0}));
   private final double[] result;
   private final int[] firstIndices;
   private final int[] secondIndices;
   private final int resultLength;

   public IndirectMerger(DoubleList var1, DoubleList var2, boolean var3, boolean var4) {
      double â˜ƒ = Double.NaN;
      int â˜ƒx = â˜ƒ.size();
      int â˜ƒxx = â˜ƒ.size();
      int â˜ƒxxx = â˜ƒx + â˜ƒxx;
      this.result = new double[â˜ƒxxx];
      this.firstIndices = new int[â˜ƒxxx];
      this.secondIndices = new int[â˜ƒxxx];
      boolean â˜ƒxxxx = !â˜ƒ;
      boolean â˜ƒxxxxx = !â˜ƒ;
      int â˜ƒxxxxxx = 0;
      int â˜ƒxxxxxxx = 0;
      int â˜ƒxxxxxxxx = 0;

      while(true) {
         boolean â˜ƒxxxxxxxxx = â˜ƒxxxxxxx >= â˜ƒx;
         boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx >= â˜ƒxx;
         if (â˜ƒxxxxxxxxx && â˜ƒxxxxxxxxxx) {
            this.resultLength = Math.max(1, â˜ƒxxxxxx);
            return;
         }

         boolean â˜ƒxxxxxxxxx = !â˜ƒxxxxxxxxx && (â˜ƒxxxxxxxxxx || â˜ƒ.getDouble(â˜ƒxxxxxxx) < â˜ƒ.getDouble(â˜ƒxxxxxxxx) + 1.0E-7);
         if (â˜ƒxxxxxxxxx) {
            ++â˜ƒxxxxxxx;
            if (â˜ƒxxxx && (â˜ƒxxxxxxxx == 0 || â˜ƒxxxxxxxxxx)) {
               continue;
            }
         } else {
            ++â˜ƒxxxxxxxx;
            if (â˜ƒxxxxx && (â˜ƒxxxxxxx == 0 || â˜ƒxxxxxxxxx)) {
               continue;
            }
         }

         int â˜ƒxxxxxxxxx = â˜ƒxxxxxxx - 1;
         int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx - 1;
         double â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx ? â˜ƒ.getDouble(â˜ƒxxxxxxxxx) : â˜ƒ.getDouble(â˜ƒxxxxxxxxxx);
         if (!(â˜ƒ >= â˜ƒxxxxxxxxxxx - 1.0E-7)) {
            this.firstIndices[â˜ƒxxxxxx] = â˜ƒxxxxxxxxx;
            this.secondIndices[â˜ƒxxxxxx] = â˜ƒxxxxxxxxxx;
            this.result[â˜ƒxxxxxx] = â˜ƒxxxxxxxxxxx;
            ++â˜ƒxxxxxx;
            â˜ƒ = â˜ƒxxxxxxxxxxx;
         } else {
            this.firstIndices[â˜ƒxxxxxx - 1] = â˜ƒxxxxxxxxx;
            this.secondIndices[â˜ƒxxxxxx - 1] = â˜ƒxxxxxxxxxx;
         }
      }
   }

   @Override
   public boolean forMergedIndexes(IndexMerger.IndexConsumer var1) {
      int â˜ƒ = this.resultLength - 1;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         if (!â˜ƒ.merge(this.firstIndices[â˜ƒx], this.secondIndices[â˜ƒx], â˜ƒx)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int size() {
      return this.resultLength;
   }

   @Override
   public DoubleList getList() {
      return (DoubleList)(this.resultLength <= 1 ? EMPTY : DoubleArrayList.wrap(this.result, this.resultLength));
   }
}
