package com.mojang.blaze3d.pipeline;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Objects;

public class MainTarget extends RenderTarget {
   public static final int DEFAULT_WIDTH = 854;
   public static final int DEFAULT_HEIGHT = 480;
   static final MainTarget.Dimension DEFAULT_DIMENSIONS = new MainTarget.Dimension(854, 480);

   public MainTarget(int var1, int var2) {
      super(true);
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.createFrameBuffer(â˜ƒ, â˜ƒ));
      } else {
         this.createFrameBuffer(â˜ƒ, â˜ƒ);
      }
   }

   private void createFrameBuffer(int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      MainTarget.Dimension â˜ƒ = this.allocateAttachments(â˜ƒ, â˜ƒ);
      this.frameBufferId = GlStateManager.glGenFramebuffers();
      GlStateManager._glBindFramebuffer(36160, this.frameBufferId);
      GlStateManager._bindTexture(this.colorTextureId);
      GlStateManager._texParameter(3553, 10241, 9728);
      GlStateManager._texParameter(3553, 10240, 9728);
      GlStateManager._texParameter(3553, 10242, 33071);
      GlStateManager._texParameter(3553, 10243, 33071);
      GlStateManager._glFramebufferTexture2D(36160, 36064, 3553, this.colorTextureId, 0);
      GlStateManager._bindTexture(this.depthBufferId);
      GlStateManager._texParameter(3553, 34892, 0);
      GlStateManager._texParameter(3553, 10241, 9728);
      GlStateManager._texParameter(3553, 10240, 9728);
      GlStateManager._texParameter(3553, 10242, 33071);
      GlStateManager._texParameter(3553, 10243, 33071);
      GlStateManager._glFramebufferTexture2D(36160, 36096, 3553, this.depthBufferId, 0);
      GlStateManager._bindTexture(0);
      this.viewWidth = â˜ƒ.width;
      this.viewHeight = â˜ƒ.height;
      this.width = â˜ƒ.width;
      this.height = â˜ƒ.height;
      this.checkStatus();
      GlStateManager._glBindFramebuffer(36160, 0);
   }

   private MainTarget.Dimension allocateAttachments(int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.colorTextureId = TextureUtil.generateTextureId();
      this.depthBufferId = TextureUtil.generateTextureId();
      MainTarget.AttachmentState â˜ƒ = MainTarget.AttachmentState.NONE;

      for(MainTarget.Dimension â˜ƒx : MainTarget.Dimension.listWithFallback(â˜ƒ, â˜ƒ)) {
         â˜ƒ = MainTarget.AttachmentState.NONE;
         if (this.allocateColorAttachment(â˜ƒx)) {
            â˜ƒ = â˜ƒ.with(MainTarget.AttachmentState.COLOR);
         }

         if (this.allocateDepthAttachment(â˜ƒx)) {
            â˜ƒ = â˜ƒ.with(MainTarget.AttachmentState.DEPTH);
         }

         if (â˜ƒ == MainTarget.AttachmentState.COLOR_DEPTH) {
            return â˜ƒx;
         }
      }

      throw new RuntimeException("Unrecoverable GL_OUT_OF_MEMORY (allocated attachments = " + â˜ƒ.name() + ")");
   }

   private boolean allocateColorAttachment(MainTarget.Dimension var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._getError();
      GlStateManager._bindTexture(this.colorTextureId);
      GlStateManager._texImage2D(3553, 0, 32856, â˜ƒ.width, â˜ƒ.height, 0, 6408, 5121, null);
      return GlStateManager._getError() != 1285;
   }

   private boolean allocateDepthAttachment(MainTarget.Dimension var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._getError();
      GlStateManager._bindTexture(this.depthBufferId);
      GlStateManager._texImage2D(3553, 0, 6402, â˜ƒ.width, â˜ƒ.height, 0, 6402, 5126, null);
      return GlStateManager._getError() != 1285;
   }

   static enum AttachmentState {
      NONE,
      COLOR,
      DEPTH,
      COLOR_DEPTH;

      private static final MainTarget.AttachmentState[] VALUES = values();

      MainTarget.AttachmentState with(MainTarget.AttachmentState var1) {
         return VALUES[this.ordinal() | â˜ƒ.ordinal()];
      }
   }

   static class Dimension {
      public final int width;
      public final int height;

      Dimension(int var1, int var2) {
         this.width = â˜ƒ;
         this.height = â˜ƒ;
      }

      static List<MainTarget.Dimension> listWithFallback(int var0, int var1) {
         RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
         int â˜ƒ = RenderSystem.maxSupportedTextureSize();
         return â˜ƒ > 0 && â˜ƒ <= â˜ƒ && â˜ƒ > 0 && â˜ƒ <= â˜ƒ
            ? ImmutableList.of(new MainTarget.Dimension(â˜ƒ, â˜ƒ), MainTarget.DEFAULT_DIMENSIONS)
            : ImmutableList.of(MainTarget.DEFAULT_DIMENSIONS);
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
            MainTarget.Dimension â˜ƒ = (MainTarget.Dimension)â˜ƒ;
            return this.width == â˜ƒ.width && this.height == â˜ƒ.height;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.width, this.height});
      }

      public String toString() {
         return this.width + "x" + this.height;
      }
   }
}
