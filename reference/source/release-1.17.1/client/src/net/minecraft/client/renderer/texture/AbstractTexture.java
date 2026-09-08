package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.concurrent.Executor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public abstract class AbstractTexture implements AutoCloseable {
   public static final int NOT_ASSIGNED = -1;
   protected int id = -1;
   protected boolean blur;
   protected boolean mipmap;

   public void setFilter(boolean var1, boolean var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.blur = â˜ƒ;
      this.mipmap = â˜ƒ;
      int â˜ƒ;
      int â˜ƒx;
      if (â˜ƒ) {
         â˜ƒ = â˜ƒ ? 9987 : 9729;
         â˜ƒx = 9729;
      } else {
         â˜ƒ = â˜ƒ ? 9986 : 9728;
         â˜ƒx = 9728;
      }

      this.bind();
      GlStateManager._texParameter(3553, 10241, â˜ƒ);
      GlStateManager._texParameter(3553, 10240, â˜ƒx);
   }

   public int getId() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (this.id == -1) {
         this.id = TextureUtil.generateTextureId();
      }

      return this.id;
   }

   public void releaseId() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            if (this.id != -1) {
               TextureUtil.releaseTextureId(this.id);
               this.id = -1;
            }
         });
      } else if (this.id != -1) {
         TextureUtil.releaseTextureId(this.id);
         this.id = -1;
      }
   }

   public abstract void load(ResourceManager var1) throws IOException;

   public void bind() {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> GlStateManager._bindTexture(this.getId()));
      } else {
         GlStateManager._bindTexture(this.getId());
      }
   }

   public void reset(TextureManager var1, ResourceManager var2, ResourceLocation var3, Executor var4) {
      â˜ƒ.register(â˜ƒ, this);
   }

   public void close() {
   }
}
