package net.minecraft.util;

public class FrameTimer {
   public static final int LOGGING_LENGTH = 240;
   private final long[] loggedTimes = new long[240];
   private int logStart;
   private int logLength;
   private int logEnd;

   public void logFrameDuration(long var1) {
      this.loggedTimes[this.logEnd] = â˜ƒ;
      ++this.logEnd;
      if (this.logEnd == 240) {
         this.logEnd = 0;
      }

      if (this.logLength < 240) {
         this.logStart = 0;
         ++this.logLength;
      } else {
         this.logStart = this.wrapIndex(this.logEnd + 1);
      }
   }

   public long getAverageDuration(int var1) {
      int â˜ƒ = (this.logStart + â˜ƒ) % 240;
      int â˜ƒx = this.logStart;

      long â˜ƒ;
      for(â˜ƒ = 0L; â˜ƒx != â˜ƒ; ++â˜ƒx) {
         â˜ƒ += this.loggedTimes[â˜ƒx];
      }

      return â˜ƒ / (long)â˜ƒ;
   }

   public int scaleAverageDurationTo(int var1, int var2) {
      return this.scaleSampleTo(this.getAverageDuration(â˜ƒ), â˜ƒ, 60);
   }

   public int scaleSampleTo(long var1, int var3, int var4) {
      double â˜ƒ = (double)â˜ƒ / (double)(1000000000L / (long)â˜ƒ);
      return (int)(â˜ƒ * (double)â˜ƒ);
   }

   public int getLogStart() {
      return this.logStart;
   }

   public int getLogEnd() {
      return this.logEnd;
   }

   public int wrapIndex(int var1) {
      return â˜ƒ % 240;
   }

   public long[] getLog() {
      return this.loggedTimes;
   }
}
