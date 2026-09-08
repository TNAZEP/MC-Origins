package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderType;

public class BakedGlyph {
   private final RenderType normalType;
   private final RenderType seeThroughType;
   private final RenderType polygonOffsetType;
   private final float u0;
   private final float u1;
   private final float v0;
   private final float v1;
   private final float left;
   private final float right;
   private final float up;
   private final float down;

   public BakedGlyph(
      RenderType var1, RenderType var2, RenderType var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11
   ) {
      this.normalType = â˜ƒ;
      this.seeThroughType = â˜ƒ;
      this.polygonOffsetType = â˜ƒ;
      this.u0 = â˜ƒ;
      this.u1 = â˜ƒ;
      this.v0 = â˜ƒ;
      this.v1 = â˜ƒ;
      this.left = â˜ƒ;
      this.right = â˜ƒ;
      this.up = â˜ƒ;
      this.down = â˜ƒ;
   }

   public void render(boolean var1, float var2, float var3, Matrix4f var4, VertexConsumer var5, float var6, float var7, float var8, float var9, int var10) {
      int â˜ƒ = 3;
      float â˜ƒx = â˜ƒ + this.left;
      float â˜ƒxx = â˜ƒ + this.right;
      float â˜ƒxxx = this.up - 3.0F;
      float â˜ƒxxxx = this.down - 3.0F;
      float â˜ƒxxxxx = â˜ƒ + â˜ƒxxx;
      float â˜ƒxxxxxx = â˜ƒ + â˜ƒxxxx;
      float â˜ƒxxxxxxx = â˜ƒ ? 1.0F - 0.25F * â˜ƒxxx : 0.0F;
      float â˜ƒxxxxxxxx = â˜ƒ ? 1.0F - 0.25F * â˜ƒxxxx : 0.0F;
      â˜ƒ.vertex(â˜ƒ, â˜ƒx + â˜ƒxxxxxxx, â˜ƒxxxxx, 0.0F).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).uv(this.u0, this.v0).uv2(â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒx + â˜ƒxxxxxxxx, â˜ƒxxxxxx, 0.0F).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).uv(this.u0, this.v1).uv2(â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx + â˜ƒxxxxxxxx, â˜ƒxxxxxx, 0.0F).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).uv(this.u1, this.v1).uv2(â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx + â˜ƒxxxxxxx, â˜ƒxxxxx, 0.0F).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).uv(this.u1, this.v0).uv2(â˜ƒ).endVertex();
   }

   public void renderEffect(BakedGlyph.Effect var1, Matrix4f var2, VertexConsumer var3, int var4) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ.x0, â˜ƒ.y0, â˜ƒ.depth).color(â˜ƒ.r, â˜ƒ.g, â˜ƒ.b, â˜ƒ.a).uv(this.u0, this.v0).uv2(â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ.x1, â˜ƒ.y0, â˜ƒ.depth).color(â˜ƒ.r, â˜ƒ.g, â˜ƒ.b, â˜ƒ.a).uv(this.u0, this.v1).uv2(â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ.x1, â˜ƒ.y1, â˜ƒ.depth).color(â˜ƒ.r, â˜ƒ.g, â˜ƒ.b, â˜ƒ.a).uv(this.u1, this.v1).uv2(â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ.x0, â˜ƒ.y1, â˜ƒ.depth).color(â˜ƒ.r, â˜ƒ.g, â˜ƒ.b, â˜ƒ.a).uv(this.u1, this.v0).uv2(â˜ƒ).endVertex();
   }

   public RenderType renderType(Font.DisplayMode var1) {
      switch(â˜ƒ) {
         case NORMAL:
         default:
            return this.normalType;
         case SEE_THROUGH:
            return this.seeThroughType;
         case POLYGON_OFFSET:
            return this.polygonOffsetType;
      }
   }

   public static class Effect {
      protected final float x0;
      protected final float y0;
      protected final float x1;
      protected final float y1;
      protected final float depth;
      protected final float r;
      protected final float g;
      protected final float b;
      protected final float a;

      public Effect(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
         this.x0 = â˜ƒ;
         this.y0 = â˜ƒ;
         this.x1 = â˜ƒ;
         this.y1 = â˜ƒ;
         this.depth = â˜ƒ;
         this.r = â˜ƒ;
         this.g = â˜ƒ;
         this.b = â˜ƒ;
         this.a = â˜ƒ;
      }
   }
}
