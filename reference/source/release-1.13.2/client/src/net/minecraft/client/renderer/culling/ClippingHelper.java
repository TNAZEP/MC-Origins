package net.minecraft.client.renderer.culling;

public class ClippingHelper {
   public float[][] field_78557_a = new float[6][4];
   public float[] field_178625_b = new float[16];
   public float[] field_178626_c = new float[16];
   public float[] field_78554_d = new float[16];

   private double func_178624_a(float[] var1, double var2, double var4, double var6) {
      return (double)☃[0] * ☃ + (double)☃[1] * ☃ + (double)☃[2] * ☃ + (double)☃[3];
   }

   public boolean func_78553_b(double var1, double var3, double var5, double var7, double var9, double var11) {
      for(int ☃ = 0; ☃ < 6; ++☃) {
         float[] ☃x = this.field_78557_a[☃];
         if (!(this.func_178624_a(☃x, ☃, ☃, ☃) > 0.0)
            && !(this.func_178624_a(☃x, ☃, ☃, ☃) > 0.0)
            && !(this.func_178624_a(☃x, ☃, ☃, ☃) > 0.0)
            && !(this.func_178624_a(☃x, ☃, ☃, ☃) > 0.0)
            && !(this.func_178624_a(☃x, ☃, ☃, ☃) > 0.0)
            && !(this.func_178624_a(☃x, ☃, ☃, ☃) > 0.0)
            && !(this.func_178624_a(☃x, ☃, ☃, ☃) > 0.0)
            && !(this.func_178624_a(☃x, ☃, ☃, ☃) > 0.0)) {
            return false;
         }
      }

      return true;
   }
}
