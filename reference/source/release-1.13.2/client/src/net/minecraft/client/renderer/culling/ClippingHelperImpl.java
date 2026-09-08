package net.minecraft.client.renderer.culling;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class ClippingHelperImpl extends ClippingHelper {
   private static final ClippingHelperImpl field_195630_e = new ClippingHelperImpl();
   private final FloatBuffer field_78561_f = GLAllocation.func_74529_h(16);
   private final FloatBuffer field_78562_g = GLAllocation.func_74529_h(16);
   private final FloatBuffer field_78564_h = GLAllocation.func_74529_h(16);

   public static ClippingHelper func_78558_a() {
      field_195630_e.func_78560_b();
      return field_195630_e;
   }

   private void func_180547_a(float[] var1) {
      float ☃ = MathHelper.func_76129_c(☃[0] * ☃[0] + ☃[1] * ☃[1] + ☃[2] * ☃[2]);
      ☃[0] /= ☃;
      ☃[1] /= ☃;
      ☃[2] /= ☃;
      ☃[3] /= ☃;
   }

   public void func_78560_b() {
      this.field_78561_f.clear();
      this.field_78562_g.clear();
      this.field_78564_h.clear();
      GlStateManager.func_179111_a(2983, this.field_78561_f);
      GlStateManager.func_179111_a(2982, this.field_78562_g);
      float[] ☃ = this.field_178625_b;
      float[] ☃x = this.field_178626_c;
      this.field_78561_f.flip().limit(16);
      this.field_78561_f.get(☃);
      this.field_78562_g.flip().limit(16);
      this.field_78562_g.get(☃x);
      this.field_78554_d[0] = ☃x[0] * ☃[0] + ☃x[1] * ☃[4] + ☃x[2] * ☃[8] + ☃x[3] * ☃[12];
      this.field_78554_d[1] = ☃x[0] * ☃[1] + ☃x[1] * ☃[5] + ☃x[2] * ☃[9] + ☃x[3] * ☃[13];
      this.field_78554_d[2] = ☃x[0] * ☃[2] + ☃x[1] * ☃[6] + ☃x[2] * ☃[10] + ☃x[3] * ☃[14];
      this.field_78554_d[3] = ☃x[0] * ☃[3] + ☃x[1] * ☃[7] + ☃x[2] * ☃[11] + ☃x[3] * ☃[15];
      this.field_78554_d[4] = ☃x[4] * ☃[0] + ☃x[5] * ☃[4] + ☃x[6] * ☃[8] + ☃x[7] * ☃[12];
      this.field_78554_d[5] = ☃x[4] * ☃[1] + ☃x[5] * ☃[5] + ☃x[6] * ☃[9] + ☃x[7] * ☃[13];
      this.field_78554_d[6] = ☃x[4] * ☃[2] + ☃x[5] * ☃[6] + ☃x[6] * ☃[10] + ☃x[7] * ☃[14];
      this.field_78554_d[7] = ☃x[4] * ☃[3] + ☃x[5] * ☃[7] + ☃x[6] * ☃[11] + ☃x[7] * ☃[15];
      this.field_78554_d[8] = ☃x[8] * ☃[0] + ☃x[9] * ☃[4] + ☃x[10] * ☃[8] + ☃x[11] * ☃[12];
      this.field_78554_d[9] = ☃x[8] * ☃[1] + ☃x[9] * ☃[5] + ☃x[10] * ☃[9] + ☃x[11] * ☃[13];
      this.field_78554_d[10] = ☃x[8] * ☃[2] + ☃x[9] * ☃[6] + ☃x[10] * ☃[10] + ☃x[11] * ☃[14];
      this.field_78554_d[11] = ☃x[8] * ☃[3] + ☃x[9] * ☃[7] + ☃x[10] * ☃[11] + ☃x[11] * ☃[15];
      this.field_78554_d[12] = ☃x[12] * ☃[0] + ☃x[13] * ☃[4] + ☃x[14] * ☃[8] + ☃x[15] * ☃[12];
      this.field_78554_d[13] = ☃x[12] * ☃[1] + ☃x[13] * ☃[5] + ☃x[14] * ☃[9] + ☃x[15] * ☃[13];
      this.field_78554_d[14] = ☃x[12] * ☃[2] + ☃x[13] * ☃[6] + ☃x[14] * ☃[10] + ☃x[15] * ☃[14];
      this.field_78554_d[15] = ☃x[12] * ☃[3] + ☃x[13] * ☃[7] + ☃x[14] * ☃[11] + ☃x[15] * ☃[15];
      float[] ☃xx = this.field_78557_a[0];
      ☃xx[0] = this.field_78554_d[3] - this.field_78554_d[0];
      ☃xx[1] = this.field_78554_d[7] - this.field_78554_d[4];
      ☃xx[2] = this.field_78554_d[11] - this.field_78554_d[8];
      ☃xx[3] = this.field_78554_d[15] - this.field_78554_d[12];
      this.func_180547_a(☃xx);
      float[] ☃xxx = this.field_78557_a[1];
      ☃xxx[0] = this.field_78554_d[3] + this.field_78554_d[0];
      ☃xxx[1] = this.field_78554_d[7] + this.field_78554_d[4];
      ☃xxx[2] = this.field_78554_d[11] + this.field_78554_d[8];
      ☃xxx[3] = this.field_78554_d[15] + this.field_78554_d[12];
      this.func_180547_a(☃xxx);
      float[] ☃xxxx = this.field_78557_a[2];
      ☃xxxx[0] = this.field_78554_d[3] + this.field_78554_d[1];
      ☃xxxx[1] = this.field_78554_d[7] + this.field_78554_d[5];
      ☃xxxx[2] = this.field_78554_d[11] + this.field_78554_d[9];
      ☃xxxx[3] = this.field_78554_d[15] + this.field_78554_d[13];
      this.func_180547_a(☃xxxx);
      float[] ☃xxxxx = this.field_78557_a[3];
      ☃xxxxx[0] = this.field_78554_d[3] - this.field_78554_d[1];
      ☃xxxxx[1] = this.field_78554_d[7] - this.field_78554_d[5];
      ☃xxxxx[2] = this.field_78554_d[11] - this.field_78554_d[9];
      ☃xxxxx[3] = this.field_78554_d[15] - this.field_78554_d[13];
      this.func_180547_a(☃xxxxx);
      float[] ☃xxxxxx = this.field_78557_a[4];
      ☃xxxxxx[0] = this.field_78554_d[3] - this.field_78554_d[2];
      ☃xxxxxx[1] = this.field_78554_d[7] - this.field_78554_d[6];
      ☃xxxxxx[2] = this.field_78554_d[11] - this.field_78554_d[10];
      ☃xxxxxx[3] = this.field_78554_d[15] - this.field_78554_d[14];
      this.func_180547_a(☃xxxxxx);
      float[] ☃xxxxxxx = this.field_78557_a[5];
      ☃xxxxxxx[0] = this.field_78554_d[3] + this.field_78554_d[2];
      ☃xxxxxxx[1] = this.field_78554_d[7] + this.field_78554_d[6];
      ☃xxxxxxx[2] = this.field_78554_d[11] + this.field_78554_d[10];
      ☃xxxxxxx[3] = this.field_78554_d[15] + this.field_78554_d[14];
      this.func_180547_a(☃xxxxxxx);
   }
}
