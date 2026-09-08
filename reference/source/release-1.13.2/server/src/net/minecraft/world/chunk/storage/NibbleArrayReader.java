package net.minecraft.world.chunk.storage;

public class NibbleArrayReader {
   public final byte[] field_76689_a;
   private final int field_76687_b;
   private final int field_76688_c;

   public NibbleArrayReader(byte[] var1, int var2) {
      this.field_76689_a = ☃;
      this.field_76687_b = ☃;
      this.field_76688_c = ☃ + 4;
   }

   public int func_76686_a(int var1, int var2, int var3) {
      int ☃ = ☃ << this.field_76688_c | ☃ << this.field_76687_b | ☃;
      int ☃x = ☃ >> 1;
      int ☃xx = ☃ & 1;
      return ☃xx == 0 ? this.field_76689_a[☃x] & 15 : this.field_76689_a[☃x] >> 4 & 15;
   }
}
