package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;

public class TexturedQuad {
   public PositionTextureVertex[] field_78239_a;
   public int field_78237_b;
   private boolean field_78238_c;

   public TexturedQuad(PositionTextureVertex[] var1) {
      this.field_78239_a = ☃;
      this.field_78237_b = ☃.length;
   }

   public TexturedQuad(PositionTextureVertex[] var1, int var2, int var3, int var4, int var5, float var6, float var7) {
      this(☃);
      float ☃ = 0.0F / ☃;
      float ☃x = 0.0F / ☃;
      ☃[0] = ☃[0].func_78240_a((float)☃ / ☃ - ☃, (float)☃ / ☃ + ☃x);
      ☃[1] = ☃[1].func_78240_a((float)☃ / ☃ + ☃, (float)☃ / ☃ + ☃x);
      ☃[2] = ☃[2].func_78240_a((float)☃ / ☃ + ☃, (float)☃ / ☃ - ☃x);
      ☃[3] = ☃[3].func_78240_a((float)☃ / ☃ - ☃, (float)☃ / ☃ - ☃x);
   }

   public void func_78235_a() {
      PositionTextureVertex[] ☃ = new PositionTextureVertex[this.field_78239_a.length];

      for(int ☃x = 0; ☃x < this.field_78239_a.length; ++☃x) {
         ☃[☃x] = this.field_78239_a[this.field_78239_a.length - ☃x - 1];
      }

      this.field_78239_a = ☃;
   }

   public void func_178765_a(BufferBuilder var1, float var2) {
      Vec3d ☃ = this.field_78239_a[1].field_78243_a.func_72444_a(this.field_78239_a[0].field_78243_a);
      Vec3d ☃x = this.field_78239_a[1].field_78243_a.func_72444_a(this.field_78239_a[2].field_78243_a);
      Vec3d ☃xx = ☃x.func_72431_c(☃).func_72432_b();
      float ☃xxx = (float)☃xx.field_72450_a;
      float ☃xxxx = (float)☃xx.field_72448_b;
      float ☃xxxxx = (float)☃xx.field_72449_c;
      if (this.field_78238_c) {
         ☃xxx = -☃xxx;
         ☃xxxx = -☃xxxx;
         ☃xxxxx = -☃xxxxx;
      }

      ☃.func_181668_a(7, DefaultVertexFormats.field_181703_c);

      for(int ☃ = 0; ☃ < 4; ++☃) {
         PositionTextureVertex ☃x = this.field_78239_a[☃];
         ☃.func_181662_b(☃x.field_78243_a.field_72450_a * (double)☃, ☃x.field_78243_a.field_72448_b * (double)☃, ☃x.field_78243_a.field_72449_c * (double)☃)
            .func_187315_a((double)☃x.field_78241_b, (double)☃x.field_78242_c)
            .func_181663_c(☃xxx, ☃xxxx, ☃xxxxx)
            .func_181675_d();
      }

      Tessellator.func_178181_a().func_78381_a();
   }
}
