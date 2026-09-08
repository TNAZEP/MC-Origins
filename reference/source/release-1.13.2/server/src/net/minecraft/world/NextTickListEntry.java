package net.minecraft.world;

import net.minecraft.util.math.BlockPos;

public class NextTickListEntry<T> implements Comparable<NextTickListEntry<T>> {
   private static long field_77177_f;
   private final T field_151352_g;
   public final BlockPos field_180282_a;
   public final long field_77180_e;
   public final TickPriority field_82754_f;
   private final long field_77178_g;

   public NextTickListEntry(BlockPos var1, T var2) {
      this(☃, ☃, 0L, TickPriority.NORMAL);
   }

   public NextTickListEntry(BlockPos var1, T var2, long var3, TickPriority var5) {
      this.field_77178_g = (long)(field_77177_f++);
      this.field_180282_a = ☃.func_185334_h();
      this.field_151352_g = ☃;
      this.field_77180_e = ☃;
      this.field_82754_f = ☃;
   }

   public boolean equals(Object var1) {
      if (!(☃ instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry ☃ = (NextTickListEntry)☃;
         return this.field_180282_a.equals(☃.field_180282_a) && this.field_151352_g == ☃.field_151352_g;
      }
   }

   public int hashCode() {
      return this.field_180282_a.hashCode();
   }

   public int compareTo(NextTickListEntry var1) {
      if (this.field_77180_e < ☃.field_77180_e) {
         return -1;
      } else if (this.field_77180_e > ☃.field_77180_e) {
         return 1;
      } else if (this.field_82754_f.ordinal() < ☃.field_82754_f.ordinal()) {
         return -1;
      } else if (this.field_82754_f.ordinal() > ☃.field_82754_f.ordinal()) {
         return 1;
      } else if (this.field_77178_g < ☃.field_77178_g) {
         return -1;
      } else {
         return this.field_77178_g > ☃.field_77178_g ? 1 : 0;
      }
   }

   public String toString() {
      return this.field_151352_g + ": " + this.field_180282_a + ", " + this.field_77180_e + ", " + this.field_82754_f + ", " + this.field_77178_g;
   }

   public T func_151351_a() {
      return this.field_151352_g;
   }
}
