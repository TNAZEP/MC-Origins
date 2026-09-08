package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public class BufferUploader {
   private static int lastVertexArrayObject;
   private static int lastVertexBufferObject;
   private static int lastIndexBufferObject;
   @Nullable
   private static VertexFormat lastFormat;

   public static void reset() {
      if (lastFormat != null) {
         lastFormat.clearBufferState();
         lastFormat = null;
      }

      GlStateManager._glBindBuffer(34963, 0);
      lastIndexBufferObject = 0;
      GlStateManager._glBindBuffer(34962, 0);
      lastVertexBufferObject = 0;
      GlStateManager._glBindVertexArray(0);
      lastVertexArrayObject = 0;
   }

   public static void invalidateElementArrayBufferBinding() {
      GlStateManager._glBindBuffer(34963, 0);
      lastIndexBufferObject = 0;
   }

   public static void end(BufferBuilder var0) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> {
            Pair<BufferBuilder.DrawState, ByteBuffer> â˜ƒ = â˜ƒ.popNextBuffer();
            BufferBuilder.DrawState â˜ƒx = â˜ƒ.getFirst();
            _end((ByteBuffer)â˜ƒ.getSecond(), â˜ƒx.mode(), â˜ƒx.format(), â˜ƒx.vertexCount(), â˜ƒx.indexType(), â˜ƒx.indexCount(), â˜ƒx.sequentialIndex());
         });
      } else {
         Pair<BufferBuilder.DrawState, ByteBuffer> â˜ƒ = â˜ƒ.popNextBuffer();
         BufferBuilder.DrawState â˜ƒx = â˜ƒ.getFirst();
         _end((ByteBuffer)â˜ƒ.getSecond(), â˜ƒx.mode(), â˜ƒx.format(), â˜ƒx.vertexCount(), â˜ƒx.indexType(), â˜ƒx.indexCount(), â˜ƒx.sequentialIndex());
      }
   }

   private static void _end(ByteBuffer var0, VertexFormat.Mode var1, VertexFormat var2, int var3, VertexFormat.IndexType var4, int var5, boolean var6) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      â˜ƒ.clear();
      if (â˜ƒ > 0) {
         int â˜ƒx = â˜ƒ * â˜ƒ.getVertexSize();
         updateVertexSetup(â˜ƒ);
         â˜ƒ.position(0);
         â˜ƒ.limit(â˜ƒx);
         GlStateManager._glBufferData(34962, â˜ƒ, 35048);
         int â˜ƒ;
         if (â˜ƒ) {
            RenderSystem.AutoStorageIndexBuffer â˜ƒxx = RenderSystem.getSequentialBuffer(â˜ƒ, â˜ƒ);
            int â˜ƒxxx = â˜ƒxx.name();
            if (â˜ƒxxx != lastIndexBufferObject) {
               GlStateManager._glBindBuffer(34963, â˜ƒxxx);
               lastIndexBufferObject = â˜ƒxxx;
            }

            â˜ƒ = â˜ƒxx.type().asGLType;
         } else {
            int â˜ƒ = â˜ƒ.getOrCreateIndexBufferObject();
            if (â˜ƒ != lastIndexBufferObject) {
               GlStateManager._glBindBuffer(34963, â˜ƒ);
               lastIndexBufferObject = â˜ƒ;
            }

            â˜ƒ.position(â˜ƒx);
            â˜ƒ.limit(â˜ƒx + â˜ƒ * â˜ƒ.bytes);
            GlStateManager._glBufferData(34963, â˜ƒ, 35048);
            â˜ƒ = â˜ƒ.asGLType;
         }

         ShaderInstance â˜ƒ = RenderSystem.getShader();

         for(int â˜ƒx = 0; â˜ƒx < 8; ++â˜ƒx) {
            int â˜ƒxx = RenderSystem.getShaderTexture(â˜ƒx);
            â˜ƒ.setSampler("Sampler" + â˜ƒx, â˜ƒxx);
         }

         if (â˜ƒ.MODEL_VIEW_MATRIX != null) {
            â˜ƒ.MODEL_VIEW_MATRIX.set(RenderSystem.getModelViewMatrix());
         }

         if (â˜ƒ.PROJECTION_MATRIX != null) {
            â˜ƒ.PROJECTION_MATRIX.set(RenderSystem.getProjectionMatrix());
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
            Window â˜ƒx = Minecraft.getInstance().getWindow();
            â˜ƒ.SCREEN_SIZE.set((float)â˜ƒx.getWidth(), (float)â˜ƒx.getHeight());
         }

         if (â˜ƒ.LINE_WIDTH != null && (â˜ƒ == VertexFormat.Mode.LINES || â˜ƒ == VertexFormat.Mode.LINE_STRIP)) {
            â˜ƒ.LINE_WIDTH.set(RenderSystem.getShaderLineWidth());
         }

         RenderSystem.setupShaderLights(â˜ƒ);
         â˜ƒ.apply();
         GlStateManager._drawElements(â˜ƒ.asGLMode, â˜ƒ, â˜ƒ, 0L);
         â˜ƒ.clear();
         â˜ƒ.position(0);
      }
   }

   public static void _endInternal(BufferBuilder var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Pair<BufferBuilder.DrawState, ByteBuffer> â˜ƒ = â˜ƒ.popNextBuffer();
      BufferBuilder.DrawState â˜ƒx = â˜ƒ.getFirst();
      ByteBuffer â˜ƒxx = (ByteBuffer)â˜ƒ.getSecond();
      VertexFormat â˜ƒxxx = â˜ƒx.format();
      int â˜ƒxxxx = â˜ƒx.vertexCount();
      â˜ƒxx.clear();
      if (â˜ƒxxxx > 0) {
         int â˜ƒxxxxx = â˜ƒxxxx * â˜ƒxxx.getVertexSize();
         updateVertexSetup(â˜ƒxxx);
         â˜ƒxx.position(0);
         â˜ƒxx.limit(â˜ƒxxxxx);
         GlStateManager._glBufferData(34962, â˜ƒxx, 35048);
         RenderSystem.AutoStorageIndexBuffer â˜ƒxxxxxx = RenderSystem.getSequentialBuffer(â˜ƒx.mode(), â˜ƒx.indexCount());
         int â˜ƒxxxxxxx = â˜ƒxxxxxx.name();
         if (â˜ƒxxxxxxx != lastIndexBufferObject) {
            GlStateManager._glBindBuffer(34963, â˜ƒxxxxxxx);
            lastIndexBufferObject = â˜ƒxxxxxxx;
         }

         int â˜ƒxxxxx = â˜ƒxxxxxx.type().asGLType;
         GlStateManager._drawElements(â˜ƒx.mode().asGLMode, â˜ƒx.indexCount(), â˜ƒxxxxx, 0L);
         â˜ƒxx.position(0);
      }
   }

   private static void updateVertexSetup(VertexFormat var0) {
      int â˜ƒ = â˜ƒ.getOrCreateVertexArrayObject();
      int â˜ƒx = â˜ƒ.getOrCreateVertexBufferObject();
      boolean â˜ƒxx = â˜ƒ != lastFormat;
      if (â˜ƒxx) {
         reset();
      }

      if (â˜ƒ != lastVertexArrayObject) {
         GlStateManager._glBindVertexArray(â˜ƒ);
         lastVertexArrayObject = â˜ƒ;
      }

      if (â˜ƒx != lastVertexBufferObject) {
         GlStateManager._glBindBuffer(34962, â˜ƒx);
         lastVertexBufferObject = â˜ƒx;
      }

      if (â˜ƒxx) {
         â˜ƒ.setupBufferState();
         lastFormat = â˜ƒ;
      }
   }
}
