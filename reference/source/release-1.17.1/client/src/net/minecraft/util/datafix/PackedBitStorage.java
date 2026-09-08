package net.minecraft.util.datafix;

import net.minecraft.util.Mth;
import org.apache.commons.lang3.Validate;

public class PackedBitStorage {
   private static final int BIT_TO_LONG_SHIFT = 6;
   private final long[] data;
   private final int bits;
   private final long mask;
   private final int size;

   public PackedBitStorage(int var1, int var2) {
      this(â˜ƒ, â˜ƒ, new long[Mth.roundToward(â˜ƒ * â˜ƒ, 64) / 64]);
   }

   public PackedBitStorage(int var1, int var2, long[] var3) {
      Validate.inclusiveBetween(1L, 32L, (long)â˜ƒ);
      this.size = â˜ƒ;
      this.bits = â˜ƒ;
      this.data = â˜ƒ;
      this.mask = (1L << â˜ƒ) - 1L;
      int â˜ƒ = Mth.roundToward(â˜ƒ * â˜ƒ, 64) / 64;
      if (â˜ƒ.length != â˜ƒ) {
         throw new IllegalArgumentException("Invalid length given for storage, got: " + â˜ƒ.length + " but expected: " + â˜ƒ);
      }
   }

   public void set(int var1, int var2) {
      Validate.inclusiveBetween(0L, (long)(this.size - 1), (long)â˜ƒ);
      Validate.inclusiveBetween(0L, this.mask, (long)â˜ƒ);
      int â˜ƒ = â˜ƒ * this.bits;
      int â˜ƒx = â˜ƒ >> 6;
      int â˜ƒxx = (â˜ƒ + 1) * this.bits - 1 >> 6;
      int â˜ƒxxx = â˜ƒ ^ â˜ƒx << 6;
      this.data[â˜ƒx] = this.data[â˜ƒx] & ~(this.mask << â˜ƒxxx) | ((long)â˜ƒ & this.mask) << â˜ƒxxx;
      if (â˜ƒx != â˜ƒxx) {
         int â˜ƒxxxx = 64 - â˜ƒxxx;
         int â˜ƒxxxxx = this.bits - â˜ƒxxxx;
         this.data[â˜ƒxx] = this.data[â˜ƒxx] >>> â˜ƒxxxxx << â˜ƒxxxxx | ((long)â˜ƒ & this.mask) >> â˜ƒxxxx;
      }
   }

   public int get(int var1) {
      Validate.inclusiveBetween(0L, (long)(this.size - 1), (long)â˜ƒ);
      int â˜ƒ = â˜ƒ * this.bits;
      int â˜ƒx = â˜ƒ >> 6;
      int â˜ƒxx = (â˜ƒ + 1) * this.bits - 1 >> 6;
      int â˜ƒxxx = â˜ƒ ^ â˜ƒx << 6;
      if (â˜ƒx == â˜ƒxx) {
         return (int)(this.data[â˜ƒx] >>> â˜ƒxxx & this.mask);
      } else {
         int â˜ƒ = 64 - â˜ƒxxx;
         return (int)((this.data[â˜ƒx] >>> â˜ƒxxx | this.data[â˜ƒxx] << â˜ƒ) & this.mask);
      }
   }

   public long[] getRaw() {
      return this.data;
   }

   public int getBits() {
      return this.bits;
   }
}
