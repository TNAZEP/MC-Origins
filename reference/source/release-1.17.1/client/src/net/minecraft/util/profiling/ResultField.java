package net.minecraft.util.profiling;

public final class ResultField implements Comparable<ResultField> {
   public final double percentage;
   public final double globalPercentage;
   public final long count;
   public final String name;

   public ResultField(String var1, double var2, double var4, long var6) {
      this.name = â˜ƒ;
      this.percentage = â˜ƒ;
      this.globalPercentage = â˜ƒ;
      this.count = â˜ƒ;
   }

   public int compareTo(ResultField var1) {
      if (â˜ƒ.percentage < this.percentage) {
         return -1;
      } else {
         return â˜ƒ.percentage > this.percentage ? 1 : â˜ƒ.name.compareTo(this.name);
      }
   }

   public int getColor() {
      return (this.name.hashCode() & 11184810) + 4473924;
   }
}
