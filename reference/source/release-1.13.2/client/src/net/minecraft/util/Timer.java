package net.minecraft.util;

public class Timer {
   public int field_74280_b;
   public float field_194147_b;
   public float field_194148_c;
   private long field_74277_g;
   private final float field_194149_e;

   public Timer(float var1, long var2) {
      this.field_194149_e = 1000.0F / ☃;
      this.field_74277_g = ☃;
   }

   public void func_74275_a(long var1) {
      this.field_194148_c = (float)(☃ - this.field_74277_g) / this.field_194149_e;
      this.field_74277_g = ☃;
      this.field_194147_b += this.field_194148_c;
      this.field_74280_b = (int)this.field_194147_b;
      this.field_194147_b -= (float)this.field_74280_b;
   }
}
