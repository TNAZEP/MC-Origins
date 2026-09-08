package com.mojang.blaze3d.vertex;

import java.util.function.Consumer;

public class VertexMultiConsumer {
   public static VertexConsumer create() {
      throw new IllegalArgumentException();
   }

   public static VertexConsumer create(VertexConsumer var0) {
      return â˜ƒ;
   }

   public static VertexConsumer create(VertexConsumer var0, VertexConsumer var1) {
      return new VertexMultiConsumer.Double(â˜ƒ, â˜ƒ);
   }

   public static VertexConsumer create(VertexConsumer... var0) {
      return new VertexMultiConsumer.Multiple(â˜ƒ);
   }

   static class Double implements VertexConsumer {
      private final VertexConsumer first;
      private final VertexConsumer second;

      public Double(VertexConsumer var1, VertexConsumer var2) {
         if (â˜ƒ == â˜ƒ) {
            throw new IllegalArgumentException("Duplicate delegates");
         } else {
            this.first = â˜ƒ;
            this.second = â˜ƒ;
         }
      }

      @Override
      public VertexConsumer vertex(double var1, double var3, double var5) {
         this.first.vertex(â˜ƒ, â˜ƒ, â˜ƒ);
         this.second.vertex(â˜ƒ, â˜ƒ, â˜ƒ);
         return this;
      }

      @Override
      public VertexConsumer color(int var1, int var2, int var3, int var4) {
         this.first.color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.second.color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return this;
      }

      @Override
      public VertexConsumer uv(float var1, float var2) {
         this.first.uv(â˜ƒ, â˜ƒ);
         this.second.uv(â˜ƒ, â˜ƒ);
         return this;
      }

      @Override
      public VertexConsumer overlayCoords(int var1, int var2) {
         this.first.overlayCoords(â˜ƒ, â˜ƒ);
         this.second.overlayCoords(â˜ƒ, â˜ƒ);
         return this;
      }

      @Override
      public VertexConsumer uv2(int var1, int var2) {
         this.first.uv2(â˜ƒ, â˜ƒ);
         this.second.uv2(â˜ƒ, â˜ƒ);
         return this;
      }

      @Override
      public VertexConsumer normal(float var1, float var2, float var3) {
         this.first.normal(â˜ƒ, â˜ƒ, â˜ƒ);
         this.second.normal(â˜ƒ, â˜ƒ, â˜ƒ);
         return this;
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
         this.first.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.second.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public void endVertex() {
         this.first.endVertex();
         this.second.endVertex();
      }

      @Override
      public void defaultColor(int var1, int var2, int var3, int var4) {
         this.first.defaultColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.second.defaultColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public void unsetDefaultColor() {
         this.first.unsetDefaultColor();
         this.second.unsetDefaultColor();
      }
   }

   static class Multiple implements VertexConsumer {
      private final VertexConsumer[] delegates;

      public Multiple(VertexConsumer[] var1) {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length; ++â˜ƒ) {
            for(int â˜ƒx = â˜ƒ + 1; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
               if (â˜ƒ[â˜ƒ] == â˜ƒ[â˜ƒx]) {
                  throw new IllegalArgumentException("Duplicate delegates");
               }
            }
         }

         this.delegates = â˜ƒ;
      }

      private void forEach(Consumer<VertexConsumer> var1) {
         for(VertexConsumer â˜ƒ : this.delegates) {
            â˜ƒ.accept(â˜ƒ);
         }
      }

      @Override
      public VertexConsumer vertex(double var1, double var3, double var5) {
         this.forEach(var6 -> var6.vertex(â˜ƒ, â˜ƒ, â˜ƒ));
         return this;
      }

      @Override
      public VertexConsumer color(int var1, int var2, int var3, int var4) {
         this.forEach(var4x -> var4x.color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
         return this;
      }

      @Override
      public VertexConsumer uv(float var1, float var2) {
         this.forEach(var2x -> var2x.uv(â˜ƒ, â˜ƒ));
         return this;
      }

      @Override
      public VertexConsumer overlayCoords(int var1, int var2) {
         this.forEach(var2x -> var2x.overlayCoords(â˜ƒ, â˜ƒ));
         return this;
      }

      @Override
      public VertexConsumer uv2(int var1, int var2) {
         this.forEach(var2x -> var2x.uv2(â˜ƒ, â˜ƒ));
         return this;
      }

      @Override
      public VertexConsumer normal(float var1, float var2, float var3) {
         this.forEach(var3x -> var3x.normal(â˜ƒ, â˜ƒ, â˜ƒ));
         return this;
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
         this.forEach(var14x -> var14x.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }

      @Override
      public void endVertex() {
         this.forEach(VertexConsumer::endVertex);
      }

      @Override
      public void defaultColor(int var1, int var2, int var3, int var4) {
         this.forEach(var4x -> var4x.defaultColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }

      @Override
      public void unsetDefaultColor() {
         this.forEach(VertexConsumer::unsetDefaultColor);
      }
   }
}
