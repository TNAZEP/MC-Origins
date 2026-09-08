package net.minecraft.world.chunk;

public class NibbleArray {
   private final byte[] field_76585_a;

   public NibbleArray() {
      this.field_76585_a = new byte[2048];
   }

   public NibbleArray(byte[] var1) {
      this.field_76585_a = ☃;
      if (☃.length != 2048) {
         throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + ☃.length);
      }
   }

   public int func_76582_a(int var1, int var2, int var3) {
      return this.func_177480_a(this.func_177483_b(☃, ☃, ☃));
   }

   public void func_76581_a(int var1, int var2, int var3, int var4) {
      this.func_177482_a(this.func_177483_b(☃, ☃, ☃), ☃);
   }

   private int func_177483_b(int var1, int var2, int var3) {
      return ☃ << 8 | ☃ << 4 | ☃;
   }

   public int func_177480_a(int var1) {
      int ☃ = this.func_177478_c(☃);
      return this.func_177479_b(☃) ? this.field_76585_a[☃] & 15 : this.field_76585_a[☃] >> 4 & 15;
   }

   public void func_177482_a(int var1, int var2) {
      int ☃ = this.func_177478_c(☃);
      if (this.func_177479_b(☃)) {
         this.field_76585_a[☃] = (byte)(this.field_76585_a[☃] & 240 | ☃ & 15);
      } else {
         this.field_76585_a[☃] = (byte)(this.field_76585_a[☃] & 15 | (☃ & 15) << 4);
      }
   }

   private boolean func_177479_b(int var1) {
      return (☃ & 1) == 0;
   }

   private int func_177478_c(int var1) {
      return ☃ >> 1;
   }

   public byte[] func_177481_a() {
      return this.field_76585_a;
   }
}
