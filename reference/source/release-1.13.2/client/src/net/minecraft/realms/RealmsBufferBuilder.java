package net.minecraft.realms;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class RealmsBufferBuilder {
   private BufferBuilder b;

   public RealmsBufferBuilder(BufferBuilder var1) {
      this.b = ☃;
   }

   public RealmsBufferBuilder from(BufferBuilder var1) {
      this.b = ☃;
      return this;
   }

   public void sortQuads(float var1, float var2, float var3) {
      this.b.func_181674_a(☃, ☃, ☃);
   }

   public void fixupQuadColor(int var1) {
      this.b.func_178968_d(☃);
   }

   public ByteBuffer getBuffer() {
      return this.b.func_178966_f();
   }

   public void postNormal(float var1, float var2, float var3) {
      this.b.func_178975_e(☃, ☃, ☃);
   }

   public int getDrawMode() {
      return this.b.func_178979_i();
   }

   public void offset(double var1, double var3, double var5) {
      this.b.func_178969_c(☃, ☃, ☃);
   }

   public void restoreState(BufferBuilder.State var1) {
      this.b.func_178993_a(☃);
   }

   public void endVertex() {
      this.b.func_181675_d();
   }

   public RealmsBufferBuilder normal(float var1, float var2, float var3) {
      return this.from(this.b.func_181663_c(☃, ☃, ☃));
   }

   public void end() {
      this.b.func_178977_d();
   }

   public void begin(int var1, VertexFormat var2) {
      this.b.func_181668_a(☃, ☃);
   }

   public RealmsBufferBuilder color(int var1, int var2, int var3, int var4) {
      return this.from(this.b.func_181669_b(☃, ☃, ☃, ☃));
   }

   public void faceTex2(int var1, int var2, int var3, int var4) {
      this.b.func_178962_a(☃, ☃, ☃, ☃);
   }

   public void postProcessFacePosition(double var1, double var3, double var5) {
      this.b.func_178987_a(☃, ☃, ☃);
   }

   public void fixupVertexColor(float var1, float var2, float var3, int var4) {
      this.b.func_178994_b(☃, ☃, ☃, ☃);
   }

   public RealmsBufferBuilder color(float var1, float var2, float var3, float var4) {
      return this.from(this.b.func_181666_a(☃, ☃, ☃, ☃));
   }

   public RealmsVertexFormat getVertexFormat() {
      return new RealmsVertexFormat(this.b.func_178973_g());
   }

   public void faceTint(float var1, float var2, float var3, int var4) {
      this.b.func_178978_a(☃, ☃, ☃, ☃);
   }

   public RealmsBufferBuilder tex2(int var1, int var2) {
      return this.from(this.b.func_187314_a(☃, ☃));
   }

   public void putBulkData(int[] var1) {
      this.b.func_178981_a(☃);
   }

   public RealmsBufferBuilder tex(double var1, double var3) {
      return this.from(this.b.func_187315_a(☃, ☃));
   }

   public int getVertexCount() {
      return this.b.func_178989_h();
   }

   public void clear() {
      this.b.func_178965_a();
   }

   public RealmsBufferBuilder vertex(double var1, double var3, double var5) {
      return this.from(this.b.func_181662_b(☃, ☃, ☃));
   }

   public void fixupQuadColor(float var1, float var2, float var3) {
      this.b.func_178990_f(☃, ☃, ☃);
   }

   public void noColor() {
      this.b.func_78914_f();
   }
}
