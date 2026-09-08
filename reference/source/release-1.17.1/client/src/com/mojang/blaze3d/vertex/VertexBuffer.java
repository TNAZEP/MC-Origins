package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public class VertexBuffer implements AutoCloseable {
   private int vertextBufferId;
   private int indexBufferId;
   private VertexFormat.IndexType indexType;
   private int arrayObjectId;
   private int indexCount;
   private VertexFormat.Mode mode;
   private boolean sequentialIndices;
   private VertexFormat format;

   public VertexBuffer() {
      RenderSystem.glGenBuffers(var1 -> this.vertextBufferId = var1);
      RenderSystem.glGenVertexArrays(var1 -> this.arrayObjectId = var1);
      RenderSystem.glGenBuffers(var1 -> this.indexBufferId = var1);
   }

   public void bind() {
      RenderSystem.glBindBuffer(34962, () -> this.vertextBufferId);
      if (this.sequentialIndices) {
         RenderSystem.glBindBuffer(34963, () -> {
            RenderSystem.AutoStorageIndexBuffer â˜ƒ = RenderSystem.getSequentialBuffer(this.mode, this.indexCount);
            this.indexType = â˜ƒ.type();
            return â˜ƒ.name();
         });
      } else {
         RenderSystem.glBindBuffer(34963, () -> this.indexBufferId);
      }
   }

   public void upload(BufferBuilder var1) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.upload_(â˜ƒ));
      } else {
         this.upload_(â˜ƒ);
      }
   }

   public CompletableFuture<Void> uploadLater(BufferBuilder var1) {
      if (!RenderSystem.isOnRenderThread()) {
         return CompletableFuture.runAsync(() -> this.upload_(â˜ƒ), var0 -> RenderSystem.recordRenderCall(var0::run));
      } else {
         this.upload_(â˜ƒ);
         return CompletableFuture.completedFuture(null);
      }
   }

   private void upload_(BufferBuilder var1) {
      Pair<BufferBuilder.DrawState, ByteBuffer> â˜ƒ = â˜ƒ.popNextBuffer();
      if (this.vertextBufferId != 0) {
         BufferUploader.reset();
         BufferBuilder.DrawState â˜ƒx = â˜ƒ.getFirst();
         ByteBuffer â˜ƒxx = (ByteBuffer)â˜ƒ.getSecond();
         int â˜ƒxxx = â˜ƒx.vertexBufferSize();
         this.indexCount = â˜ƒx.indexCount();
         this.indexType = â˜ƒx.indexType();
         this.format = â˜ƒx.format();
         this.mode = â˜ƒx.mode();
         this.sequentialIndices = â˜ƒx.sequentialIndex();
         this.bindVertexArray();
         this.bind();
         if (!â˜ƒx.indexOnly()) {
            â˜ƒxx.limit(â˜ƒxxx);
            RenderSystem.glBufferData(34962, â˜ƒxx, 35044);
            â˜ƒxx.position(â˜ƒxxx);
         }

         if (!this.sequentialIndices) {
            â˜ƒxx.limit(â˜ƒx.bufferSize());
            RenderSystem.glBufferData(34963, â˜ƒxx, 35044);
            â˜ƒxx.position(0);
         } else {
            â˜ƒxx.limit(â˜ƒx.bufferSize());
            â˜ƒxx.position(0);
         }

         unbind();
         unbindVertexArray();
      }
   }

   private void bindVertexArray() {
      RenderSystem.glBindVertexArray(() -> this.arrayObjectId);
   }

   public static void unbindVertexArray() {
      RenderSystem.glBindVertexArray(() -> 0);
   }

   public void draw() {
      if (this.indexCount != 0) {
         RenderSystem.drawElements(this.mode.asGLMode, this.indexCount, this.indexType.asGLType);
      }
   }

   public void drawWithShader(Matrix4f var1, Matrix4f var2, ShaderInstance var3) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this._drawWithShader(â˜ƒ.copy(), â˜ƒ.copy(), â˜ƒ));
      } else {
         this._drawWithShader(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void _drawWithShader(Matrix4f var1, Matrix4f var2, ShaderInstance var3) {
      if (this.indexCount != 0) {
         RenderSystem.assertThread(RenderSystem::isOnRenderThread);
         BufferUploader.reset();

         for(int â˜ƒ = 0; â˜ƒ < 12; ++â˜ƒ) {
            int â˜ƒx = RenderSystem.getShaderTexture(â˜ƒ);
            â˜ƒ.setSampler("Sampler" + â˜ƒ, â˜ƒx);
         }

         if (â˜ƒ.MODEL_VIEW_MATRIX != null) {
            â˜ƒ.MODEL_VIEW_MATRIX.set(â˜ƒ);
         }

         if (â˜ƒ.PROJECTION_MATRIX != null) {
            â˜ƒ.PROJECTION_MATRIX.set(â˜ƒ);
         }

         if (â˜ƒ.COLOR_MODULATOR != null) {
            â˜ƒ.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
         }

         if (â˜ƒ.FOG_START != null) {
            â˜ƒ.FOG_START.set(RenderSystem.getShaderFogStart());
         }

         if (â˜ƒ.FOG_END != null) {
            â˜ƒ.FOG_END.set(RenderSystem.getShaderFogEnd());
         }

         if (â˜ƒ.FOG_COLOR != null) {
            â˜ƒ.FOG_COLOR.set(RenderSystem.getShaderFogColor());
         }

         if (â˜ƒ.TEXTURE_MATRIX != null) {
            â˜ƒ.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
         }

         if (â˜ƒ.GAME_TIME != null) {
            â˜ƒ.GAME_TIME.set(RenderSystem.getShaderGameTime());
         }

         if (â˜ƒ.SCREEN_SIZE != null) {
            Window â˜ƒ = Minecraft.getInstance().getWindow();
            â˜ƒ.SCREEN_SIZE.set((float)â˜ƒ.getWidth(), (float)â˜ƒ.getHeight());
         }

         if (â˜ƒ.LINE_WIDTH != null && (this.mode == VertexFormat.Mode.LINES || this.mode == VertexFormat.Mode.LINE_STRIP)) {
            â˜ƒ.LINE_WIDTH.set(RenderSystem.getShaderLineWidth());
         }

         RenderSystem.setupShaderLights(â˜ƒ);
         this.bindVertexArray();
         this.bind();
         this.getFormat().setupBufferState();
         â˜ƒ.apply();
         RenderSystem.drawElements(this.mode.asGLMode, this.indexCount, this.indexType.asGLType);
         â˜ƒ.clear();
         this.getFormat().clearBufferState();
         unbind();
         unbindVertexArray();
      }
   }

   public void drawChunkLayer() {
      if (this.indexCount != 0) {
         RenderSystem.assertThread(RenderSystem::isOnRenderThread);
         this.bindVertexArray();
         this.bind();
         this.format.setupBufferState();
         RenderSystem.drawElements(this.mode.asGLMode, this.indexCount, this.indexType.asGLType);
      }
   }

   public static void unbind() {
      RenderSystem.glBindBuffer(34962, () -> 0);
      RenderSystem.glBindBuffer(34963, () -> 0);
   }

   public void close() {
      if (this.indexBufferId >= 0) {
         RenderSystem.glDeleteBuffers(this.indexBufferId);
         this.indexBufferId = -1;
      }

      if (this.vertextBufferId > 0) {
         RenderSystem.glDeleteBuffers(this.vertextBufferId);
         this.vertextBufferId = 0;
      }

      if (this.arrayObjectId > 0) {
         RenderSystem.glDeleteVertexArrays(this.arrayObjectId);
         this.arrayObjectId = 0;
      }
   }

   public VertexFormat getFormat() {
      return this.format;
   }
}
