package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import java.util.Optional;

public class OutlineBufferSource implements MultiBufferSource {
   private final MultiBufferSource.BufferSource bufferSource;
   private final MultiBufferSource.BufferSource outlineBufferSource = MultiBufferSource.immediate(new BufferBuilder(256));
   private int teamR = 255;
   private int teamG = 255;
   private int teamB = 255;
   private int teamA = 255;

   public OutlineBufferSource(MultiBufferSource.BufferSource var1) {
      this.bufferSource = â˜ƒ;
   }

   @Override
   public VertexConsumer getBuffer(RenderType var1) {
      if (â˜ƒ.isOutline()) {
         VertexConsumer â˜ƒ = this.outlineBufferSource.getBuffer(â˜ƒ);
         return new OutlineBufferSource.EntityOutlineGenerator(â˜ƒ, this.teamR, this.teamG, this.teamB, this.teamA);
      } else {
         VertexConsumer â˜ƒ = this.bufferSource.getBuffer(â˜ƒ);
         Optional<RenderType> â˜ƒx = â˜ƒ.outline();
         if (â˜ƒx.isPresent()) {
            VertexConsumer â˜ƒxx = this.outlineBufferSource.getBuffer((RenderType)â˜ƒx.get());
            OutlineBufferSource.EntityOutlineGenerator â˜ƒxxx = new OutlineBufferSource.EntityOutlineGenerator(
               â˜ƒxx, this.teamR, this.teamG, this.teamB, this.teamA
            );
            return VertexMultiConsumer.create(â˜ƒxxx, â˜ƒ);
         } else {
            return â˜ƒ;
         }
      }
   }

   public void setColor(int var1, int var2, int var3, int var4) {
      this.teamR = â˜ƒ;
      this.teamG = â˜ƒ;
      this.teamB = â˜ƒ;
      this.teamA = â˜ƒ;
   }

   public void endOutlineBatch() {
      this.outlineBufferSource.endBatch();
   }

   static class EntityOutlineGenerator extends DefaultedVertexConsumer {
      private final VertexConsumer delegate;
      private double x;
      private double y;
      private double z;
      private float u;
      private float v;

      EntityOutlineGenerator(VertexConsumer var1, int var2, int var3, int var4, int var5) {
         this.delegate = â˜ƒ;
         super.defaultColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public void defaultColor(int var1, int var2, int var3, int var4) {
      }

      @Override
      public void unsetDefaultColor() {
      }

      @Override
      public VertexConsumer vertex(double var1, double var3, double var5) {
         this.x = â˜ƒ;
         this.y = â˜ƒ;
         this.z = â˜ƒ;
         return this;
      }

      @Override
      public VertexConsumer color(int var1, int var2, int var3, int var4) {
         return this;
      }

      @Override
      public VertexConsumer uv(float var1, float var2) {
         this.u = â˜ƒ;
         this.v = â˜ƒ;
         return this;
      }

      @Override
      public VertexConsumer overlayCoords(int var1, int var2) {
         return this;
      }

      @Override
      public VertexConsumer uv2(int var1, int var2) {
         return this;
      }

      @Override
      public VertexConsumer normal(float var1, float var2, float var3) {
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
         this.delegate.vertex((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ).color(this.defaultR, this.defaultG, this.defaultB, this.defaultA).uv(â˜ƒ, â˜ƒ).endVertex();
      }

      @Override
      public void endVertex() {
         this.delegate.vertex(this.x, this.y, this.z).color(this.defaultR, this.defaultG, this.defaultB, this.defaultA).uv(this.u, this.v).endVertex();
      }
   }
}
