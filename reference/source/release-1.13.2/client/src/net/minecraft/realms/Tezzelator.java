package net.minecraft.realms;

import net.minecraft.client.renderer.Tessellator;

public class Tezzelator {
   public static Tessellator t = Tessellator.func_178181_a();
   public static final Tezzelator instance = new Tezzelator();

   public void end() {
      t.func_78381_a();
   }

   public Tezzelator vertex(double var1, double var3, double var5) {
      t.func_178180_c().func_181662_b(☃, ☃, ☃);
      return this;
   }

   public void color(float var1, float var2, float var3, float var4) {
      t.func_178180_c().func_181666_a(☃, ☃, ☃, ☃);
   }

   public void tex2(short var1, short var2) {
      t.func_178180_c().func_187314_a(☃, ☃);
   }

   public void normal(float var1, float var2, float var3) {
      t.func_178180_c().func_181663_c(☃, ☃, ☃);
   }

   public void begin(int var1, RealmsVertexFormat var2) {
      t.func_178180_c().func_181668_a(☃, ☃.getVertexFormat());
   }

   public void endVertex() {
      t.func_178180_c().func_181675_d();
   }

   public void offset(double var1, double var3, double var5) {
      t.func_178180_c().func_178969_c(☃, ☃, ☃);
   }

   public RealmsBufferBuilder color(int var1, int var2, int var3, int var4) {
      return new RealmsBufferBuilder(t.func_178180_c().func_181669_b(☃, ☃, ☃, ☃));
   }

   public Tezzelator tex(double var1, double var3) {
      t.func_178180_c().func_187315_a(☃, ☃);
      return this;
   }
}
