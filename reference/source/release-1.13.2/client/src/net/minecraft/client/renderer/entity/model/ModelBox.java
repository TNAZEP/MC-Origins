package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.BufferBuilder;

public class ModelBox {
   private final PositionTextureVertex[] field_78253_h;
   private final TexturedQuad[] field_78254_i;
   public final float field_78252_a;
   public final float field_78250_b;
   public final float field_78251_c;
   public final float field_78248_d;
   public final float field_78249_e;
   public final float field_78246_f;
   public String field_78247_g;

   public ModelBox(ModelRenderer var1, int var2, int var3, float var4, float var5, float var6, int var7, int var8, int var9, float var10) {
      this(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃.field_78809_i);
   }

   public ModelBox(ModelRenderer var1, int var2, int var3, float var4, float var5, float var6, int var7, int var8, int var9, float var10, boolean var11) {
      this.field_78252_a = ☃;
      this.field_78250_b = ☃;
      this.field_78251_c = ☃;
      this.field_78248_d = ☃ + (float)☃;
      this.field_78249_e = ☃ + (float)☃;
      this.field_78246_f = ☃ + (float)☃;
      this.field_78253_h = new PositionTextureVertex[8];
      this.field_78254_i = new TexturedQuad[6];
      float ☃ = ☃ + (float)☃;
      float ☃x = ☃ + (float)☃;
      float ☃xx = ☃ + (float)☃;
      ☃ -= ☃;
      ☃ -= ☃;
      ☃ -= ☃;
      ☃ += ☃;
      ☃x += ☃;
      ☃xx += ☃;
      if (☃) {
         float ☃xxx = ☃;
         ☃ = ☃;
         ☃ = ☃xxx;
      }

      PositionTextureVertex ☃ = new PositionTextureVertex(☃, ☃, ☃, 0.0F, 0.0F);
      PositionTextureVertex ☃x = new PositionTextureVertex(☃, ☃, ☃, 0.0F, 8.0F);
      PositionTextureVertex ☃xx = new PositionTextureVertex(☃, ☃x, ☃, 8.0F, 8.0F);
      PositionTextureVertex ☃xxx = new PositionTextureVertex(☃, ☃x, ☃, 8.0F, 0.0F);
      PositionTextureVertex ☃xxxx = new PositionTextureVertex(☃, ☃, ☃xx, 0.0F, 0.0F);
      PositionTextureVertex ☃xxxxx = new PositionTextureVertex(☃, ☃, ☃xx, 0.0F, 8.0F);
      PositionTextureVertex ☃xxxxxx = new PositionTextureVertex(☃, ☃x, ☃xx, 8.0F, 8.0F);
      PositionTextureVertex ☃xxxxxxx = new PositionTextureVertex(☃, ☃x, ☃xx, 8.0F, 0.0F);
      this.field_78253_h[0] = ☃;
      this.field_78253_h[1] = ☃x;
      this.field_78253_h[2] = ☃xx;
      this.field_78253_h[3] = ☃xxx;
      this.field_78253_h[4] = ☃xxxx;
      this.field_78253_h[5] = ☃xxxxx;
      this.field_78253_h[6] = ☃xxxxxx;
      this.field_78253_h[7] = ☃xxxxxxx;
      this.field_78254_i[0] = new TexturedQuad(
         new PositionTextureVertex[]{☃xxxxx, ☃x, ☃xx, ☃xxxxxx}, ☃ + ☃ + ☃, ☃ + ☃, ☃ + ☃ + ☃ + ☃, ☃ + ☃ + ☃, ☃.field_78801_a, ☃.field_78799_b
      );
      this.field_78254_i[1] = new TexturedQuad(
         new PositionTextureVertex[]{☃, ☃xxxx, ☃xxxxxxx, ☃xxx}, ☃, ☃ + ☃, ☃ + ☃, ☃ + ☃ + ☃, ☃.field_78801_a, ☃.field_78799_b
      );
      this.field_78254_i[2] = new TexturedQuad(new PositionTextureVertex[]{☃xxxxx, ☃xxxx, ☃, ☃x}, ☃ + ☃, ☃, ☃ + ☃ + ☃, ☃ + ☃, ☃.field_78801_a, ☃.field_78799_b);
      this.field_78254_i[3] = new TexturedQuad(
         new PositionTextureVertex[]{☃xx, ☃xxx, ☃xxxxxxx, ☃xxxxxx}, ☃ + ☃ + ☃, ☃ + ☃, ☃ + ☃ + ☃ + ☃, ☃, ☃.field_78801_a, ☃.field_78799_b
      );
      this.field_78254_i[4] = new TexturedQuad(
         new PositionTextureVertex[]{☃x, ☃, ☃xxx, ☃xx}, ☃ + ☃, ☃ + ☃, ☃ + ☃ + ☃, ☃ + ☃ + ☃, ☃.field_78801_a, ☃.field_78799_b
      );
      this.field_78254_i[5] = new TexturedQuad(
         new PositionTextureVertex[]{☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx}, ☃ + ☃ + ☃ + ☃, ☃ + ☃, ☃ + ☃ + ☃ + ☃ + ☃, ☃ + ☃ + ☃, ☃.field_78801_a, ☃.field_78799_b
      );
      if (☃) {
         for(TexturedQuad ☃xxxxxxxx : this.field_78254_i) {
            ☃xxxxxxxx.func_78235_a();
         }
      }
   }

   public void func_178780_a(BufferBuilder var1, float var2) {
      for(TexturedQuad ☃ : this.field_78254_i) {
         ☃.func_178765_a(☃, ☃);
      }
   }

   public ModelBox func_78244_a(String var1) {
      this.field_78247_g = ☃;
      return this;
   }
}
