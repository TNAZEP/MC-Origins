package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public abstract class RenderTarget {
   private static final int RED_CHANNEL = 0;
   private static final int GREEN_CHANNEL = 1;
   private static final int BLUE_CHANNEL = 2;
   private static final int ALPHA_CHANNEL = 3;
   public int width;
   public int height;
   public int viewWidth;
   public int viewHeight;
   public final boolean useDepth;
   public int frameBufferId;
   protected int colorTextureId;
   protected int depthBufferId;
   private final float[] clearChannels = Util.make(() -> new float[]{1.0F, 1.0F, 1.0F, 0.0F});
   public int filterMode;

   public RenderTarget(boolean var1) {
      this.useDepth = â˜ƒ;
      this.frameBufferId = -1;
      this.colorTextureId = -1;
      this.depthBufferId = -1;
   }

   public void resize(int var1, int var2, boolean var3) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this._resize(â˜ƒ, â˜ƒ, â˜ƒ));
      } else {
         this._resize(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void _resize(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._enableDepthTest();
      if (this.frameBufferId >= 0) {
         this.destroyBuffers();
      }

      this.createBuffers(â˜ƒ, â˜ƒ, â˜ƒ);
      GlStateManager._glBindFramebuffer(36160, 0);
   }

   public void destroyBuffers() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.unbindRead();
      this.unbindWrite();
      if (this.depthBufferId > -1) {
         TextureUtil.releaseTextureId(this.depthBufferId);
         this.depthBufferId = -1;
      }

      if (this.colorTextureId > -1) {
         TextureUtil.releaseTextureId(this.colorTextureId);
         this.colorTextureId = -1;
      }

      if (this.frameBufferId > -1) {
         GlStateManager._glBindFramebuffer(36160, 0);
         GlStateManager._glDeleteFramebuffers(this.frameBufferId);
         this.frameBufferId = -1;
      }
   }

   public void copyDepthFrom(RenderTarget var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._glBindFramebuffer(36008, â˜ƒ.frameBufferId);
      GlStateManager._glBindFramebuffer(36009, this.frameBufferId);
      GlStateManager._glBlitFrameBuffer(0, 0, â˜ƒ.width, â˜ƒ.height, 0, 0, this.width, this.height, 256, 9728);
      GlStateManager._glBindFramebuffer(36160, 0);
   }

   public void createBuffers(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      int â˜ƒ = RenderSystem.maxSupportedTextureSize();
      if (â˜ƒ > 0 && â˜ƒ <= â˜ƒ && â˜ƒ > 0 && â˜ƒ <= â˜ƒ) {
         this.viewWidth = â˜ƒ;
         this.viewHeight = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.frameBufferId = GlStateManager.glGenFramebuffers();
         this.colorTextureId = TextureUtil.generateTextureId();
         if (this.useDepth) {
            this.depthBufferId = TextureUtil.generateTextureId();
            GlStateManager._bindTexture(this.depthBufferId);
            GlStateManager._texParameter(3553, 10241, 9728);
            GlStateManager._texParameter(3553, 10240, 9728);
            GlStateManager._texParameter(3553, 34892, 0);
            GlStateManager._texParameter(3553, 10242, 33071);
            GlStateManager._texParameter(3553, 10243, 33071);
            GlStateManager._texImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, null);
         }

         this.setFilterMode(9728);
         GlStateManager._bindTexture(this.colorTextureId);
         GlStateManager._texParameter(3553, 10242, 33071);
         GlStateManager._texParameter(3553, 10243, 33071);
         GlStateManager._texImage2D(3553, 0, 32856, this.width, this.height, 0, 6408, 5121, null);
         GlStateManager._glBindFramebuffer(36160, this.frameBufferId);
         GlStateManager._glFramebufferTexture2D(36160, 36064, 3553, this.colorTextureId, 0);
         if (this.useDepth) {
            GlStateManager._glFramebufferTexture2D(36160, 36096, 3553, this.depthBufferId, 0);
         }

         this.checkStatus();
         this.clear(â˜ƒ);
         this.unbindRead();
      } else {
         throw new IllegalArgumentException("Window " + â˜ƒ + "x" + â˜ƒ + " size out of bounds (max. size: " + â˜ƒ + ")");
      }
   }

   public void setFilterMode(int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.filterMode = â˜ƒ;
      GlStateManager._bindTexture(this.colorTextureId);
      GlStateManager._texParameter(3553, 10241, â˜ƒ);
      GlStateManager._texParameter(3553, 10240, â˜ƒ);
      GlStateManager._bindTexture(0);
   }

   public void checkStatus() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      int â˜ƒ = GlStateManager.glCheckFramebufferStatus(36160);
      if (â˜ƒ != 36053) {
         if (â˜ƒ == 36054) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
         } else if (â˜ƒ == 36055) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
         } else if (â˜ƒ == 36059) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
         } else if (â˜ƒ == 36060) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
         } else if (â˜ƒ == 36061) {
            throw new RuntimeException("GL_FRAMEBUFFER_UNSUPPORTED");
         } else if (â˜ƒ == 1285) {
            throw new RuntimeException("GL_OUT_OF_MEMORY");
         } else {
            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + â˜ƒ);
         }
      }
   }

   public void bindRead() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager._bindTexture(this.colorTextureId);
   }

   public void unbindRead() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._bindTexture(0);
   }

   public void bindWrite(boolean var1) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this._bindWrite(â˜ƒ));
      } else {
         this._bindWrite(â˜ƒ);
      }
   }

   private void _bindWrite(boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._glBindFramebuffer(36160, this.frameBufferId);
      if (â˜ƒ) {
         GlStateManager._viewport(0, 0, this.viewWidth, this.viewHeight);
      }
   }

   public void unbindWrite() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> GlStateManager._glBindFramebuffer(36160, 0));
      } else {
         GlStateManager._glBindFramebuffer(36160, 0);
      }
   }

   public void setClearColor(float var1, float var2, float var3, float var4) {
      this.clearChannels[0] = â˜ƒ;
      this.clearChannels[1] = â˜ƒ;
      this.clearChannels[2] = â˜ƒ;
      this.clearChannels[3] = â˜ƒ;
   }

   public void blitToScreen(int var1, int var2) {
      this.blitToScreen(â˜ƒ, â˜ƒ, true);
   }

   public void blitToScreen(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
      if (!RenderSystem.isInInitPhase()) {
         RenderSystem.recordRenderCall(() -> this._blitToScreen(â˜ƒ, â˜ƒ, â˜ƒ));
      } else {
         this._blitToScreen(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void _blitToScreen(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager._colorMask(true, true, true, false);
      GlStateManager._disableDepthTest();
      GlStateManager._depthMask(false);
      GlStateManager._viewport(0, 0, â˜ƒ, â˜ƒ);
      if (â˜ƒ) {
         GlStateManager._disableBlend();
      }

      Minecraft â˜ƒ = Minecraft.getInstance();
      ShaderInstance â˜ƒx = â˜ƒ.gameRenderer.blitShader;
      â˜ƒx.setSampler("DiffuseSampler", this.colorTextureId);
      Matrix4f â˜ƒxx = Matrix4f.orthographic((float)â˜ƒ, (float)(-â˜ƒ), 1000.0F, 3000.0F);
      RenderSystem.setProjectionMatrix(â˜ƒxx);
      if (â˜ƒx.MODEL_VIEW_MATRIX != null) {
         â˜ƒx.MODEL_VIEW_MATRIX.set(Matrix4f.createTranslateMatrix(0.0F, 0.0F, -2000.0F));
      }

      if (â˜ƒx.PROJECTION_MATRIX != null) {
         â˜ƒx.PROJECTION_MATRIX.set(â˜ƒxx);
      }

      â˜ƒx.apply();
      float â˜ƒ = (float)â˜ƒ;
      float â˜ƒx = (float)â˜ƒ;
      float â˜ƒxx = (float)this.viewWidth / (float)this.width;
      float â˜ƒxxx = (float)this.viewHeight / (float)this.height;
      Tesselator â˜ƒxxxx = RenderSystem.renderThreadTesselator();
      BufferBuilder â˜ƒxxxxx = â˜ƒxxxx.getBuilder();
      â˜ƒxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      â˜ƒxxxxx.vertex(0.0, (double)â˜ƒx, 0.0).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
      â˜ƒxxxxx.vertex((double)â˜ƒ, (double)â˜ƒx, 0.0).uv(â˜ƒxx, 0.0F).color(255, 255, 255, 255).endVertex();
      â˜ƒxxxxx.vertex((double)â˜ƒ, 0.0, 0.0).uv(â˜ƒxx, â˜ƒxxx).color(255, 255, 255, 255).endVertex();
      â˜ƒxxxxx.vertex(0.0, 0.0, 0.0).uv(0.0F, â˜ƒxxx).color(255, 255, 255, 255).endVertex();
      â˜ƒxxxxx.end();
      BufferUploader._endInternal(â˜ƒxxxxx);
      â˜ƒx.clear();
      GlStateManager._depthMask(true);
      GlStateManager._colorMask(true, true, true, true);
   }

   public void clear(boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.bindWrite(true);
      GlStateManager._clearColor(this.clearChannels[0], this.clearChannels[1], this.clearChannels[2], this.clearChannels[3]);
      int â˜ƒ = 16384;
      if (this.useDepth) {
         GlStateManager._clearDepth(1.0);
         â˜ƒ |= 256;
      }

      GlStateManager._clear(â˜ƒ, â˜ƒ);
      this.unbindWrite();
   }

   public int getColorTextureId() {
      return this.colorTextureId;
   }

   public int getDepthTextureId() {
      return this.depthBufferId;
   }
}
