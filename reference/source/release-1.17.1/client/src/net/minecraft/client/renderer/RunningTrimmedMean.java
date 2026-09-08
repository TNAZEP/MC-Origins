package net.minecraft.client.renderer;

public class RunningTrimmedMean {
   private final long[] values;
   private int count;
   private int cursor;

   public RunningTrimmedMean(int var1) {
      this.values = new long[â˜ƒ];
   }

   public long registerValueAndGetMean(long var1) {
      if (this.count < this.values.length) {
         ++this.count;
      }

      this.values[this.cursor] = â˜ƒ;
      this.cursor = (this.cursor + 1) % this.values.length;
      long â˜ƒ = Long.MAX_VALUE;
      long â˜ƒx = Long.MIN_VALUE;
      long â˜ƒxx = 0L;

      for(int â˜ƒxxx = 0; â˜ƒxxx < this.count; ++â˜ƒxxx) {
         long â˜ƒxxxx = this.values[â˜ƒxxx];
         â˜ƒxx += â˜ƒxxxx;
         â˜ƒ = Math.min(â˜ƒ, â˜ƒxxxx);
         â˜ƒx = Math.max(â˜ƒx, â˜ƒxxxx);
      }

      if (this.count > 2) {
         â˜ƒxx -= â˜ƒ + â˜ƒx;
         return â˜ƒxx / (long)(this.count - 2);
      } else {
         return â˜ƒxx > 0L ? (long)this.count / â˜ƒxx : 0L;
      }
   }
}
