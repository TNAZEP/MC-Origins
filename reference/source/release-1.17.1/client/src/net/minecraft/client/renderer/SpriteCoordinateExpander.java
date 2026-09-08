package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SpriteCoordinateExpander implements VertexConsumer {
   private final VertexConsumer delegate;
   private final TextureAtlasSprite sprite;

   public SpriteCoordinateExpander(VertexConsumer var1, TextureAtlasSprite var2) {
      this.delegate = â˜ƒ;
      this.sprite = â˜ƒ;
   }

   @Override
   public VertexConsumer vertex(double var1, double var3, double var5) {
      return this.delegate.vertex(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public VertexConsumer color(int var1, int var2, int var3, int var4) {
      return this.delegate.color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public VertexConsumer uv(float var1, float var2) {
      return this.delegate.uv(this.sprite.getU((double)(â˜ƒ * 16.0F)), this.sprite.getV((double)(â˜ƒ * 16.0F)));
   }

   @Override
   public VertexConsumer overlayCoords(int var1, int var2) {
      return this.delegate.overlayCoords(â˜ƒ, â˜ƒ);
   }

   @Override
   public VertexConsumer uv2(int var1, int var2) {
      return this.delegate.uv2(â˜ƒ, â˜ƒ);
   }

   @Override
   public VertexConsumer normal(float var1, float var2, float var3) {
      return this.delegate.normal(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void endVertex() {
      this.delegate.endVertex();
   }

   @Override
   public void defaultColor(int var1, int var2, int var3, int var4) {
      this.delegate.defaultColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void unsetDefaultColor() {
      this.delegate.unsetDefaultColor();
   }

   @Override
   public void vertex(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      int var10,
      int var11,
      float var12,
      float var13,
      float var14
   ) {
      this.delegate
         .vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.sprite.getU((double)(â˜ƒ * 16.0F)), this.sprite.getV((double)(â˜ƒ * 16.0F)), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
