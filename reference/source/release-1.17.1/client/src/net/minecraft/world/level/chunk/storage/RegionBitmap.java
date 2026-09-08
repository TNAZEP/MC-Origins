package net.minecraft.world.level.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.BitSet;

public class RegionBitmap {
   private final BitSet used = new BitSet();

   public void force(int var1, int var2) {
      this.used.set(â˜ƒ, â˜ƒ + â˜ƒ);
   }

   public void free(int var1, int var2) {
      this.used.clear(â˜ƒ, â˜ƒ + â˜ƒ);
   }

   public int allocate(int var1) {
      int â˜ƒ = 0;

      while(true) {
         int â˜ƒx = this.used.nextClearBit(â˜ƒ);
         int â˜ƒxx = this.used.nextSetBit(â˜ƒx);
         if (â˜ƒxx == -1 || â˜ƒxx - â˜ƒx >= â˜ƒ) {
            this.force(â˜ƒx, â˜ƒ);
            return â˜ƒx;
         }

         â˜ƒ = â˜ƒxx;
      }
   }

   @VisibleForTesting
   public IntSet getUsed() {
      return (IntSet)this.used.stream().collect(IntArraySet::new, IntCollection::add, IntCollection::addAll);
   }
}
