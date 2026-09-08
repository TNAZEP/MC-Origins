package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture extends AbstractTexture {
   static final Logger LOGGER = LogManager.getLogger();
   protected final ResourceLocation location;

   public SimpleTexture(ResourceLocation var1) {
      this.location = â˜ƒ;
   }

   @Override
   public void load(ResourceManager var1) throws IOException {
      SimpleTexture.TextureImage â˜ƒxx = this.getTextureImage(â˜ƒ);
      â˜ƒxx.throwIfError();
      TextureMetadataSection â˜ƒxxx = â˜ƒxx.getTextureMetadata();
      boolean â˜ƒ;
      boolean â˜ƒx;
      if (â˜ƒxxx != null) {
         â˜ƒ = â˜ƒxxx.isBlur();
         â˜ƒx = â˜ƒxxx.isClamp();
      } else {
         â˜ƒ = false;
         â˜ƒx = false;
      }

      NativeImage â˜ƒ = â˜ƒxx.getImage();
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> this.doLoad(â˜ƒ, â˜ƒ, â˜ƒ));
      } else {
         this.doLoad(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }

   private void doLoad(NativeImage var1, boolean var2, boolean var3) {
      TextureUtil.prepareImage(this.getId(), 0, â˜ƒ.getWidth(), â˜ƒ.getHeight());
      â˜ƒ.upload(0, 0, 0, 0, 0, â˜ƒ.getWidth(), â˜ƒ.getHeight(), â˜ƒ, â˜ƒ, false, true);
   }

   protected SimpleTexture.TextureImage getTextureImage(ResourceManager var1) {
      return SimpleTexture.TextureImage.load(â˜ƒ, this.location);
   }

   protected static class TextureImage implements Closeable {
      @Nullable
      private final TextureMetadataSection metadata;
      @Nullable
      private final NativeImage image;
      @Nullable
      private final IOException exception;

      public TextureImage(IOException var1) {
         this.exception = â˜ƒ;
         this.metadata = null;
         this.image = null;
      }

      public TextureImage(@Nullable TextureMetadataSection var1, NativeImage var2) {
         this.exception = null;
         this.metadata = â˜ƒ;
         this.image = â˜ƒ;
      }

      public static SimpleTexture.TextureImage load(ResourceManager var0, ResourceLocation var1) {
         try {
            Resource â˜ƒ = â˜ƒ.getResource(â˜ƒ);

            SimpleTexture.TextureImage var5;
            try {
               NativeImage â˜ƒx = NativeImage.read(â˜ƒ.getInputStream());
               TextureMetadataSection â˜ƒxx = null;

               try {
                  â˜ƒxx = â˜ƒ.getMetadata(TextureMetadataSection.SERIALIZER);
               } catch (RuntimeException var7) {
                  SimpleTexture.LOGGER.warn("Failed reading metadata of: {}", â˜ƒ, var7);
               }

               var5 = new SimpleTexture.TextureImage(â˜ƒxx, â˜ƒx);
            } catch (Throwable var8) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var6) {
                     var8.addSuppressed(var6);
                  }
               }

               throw var8;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }

            return var5;
         } catch (IOException var9) {
            return new SimpleTexture.TextureImage(var9);
         }
      }

      @Nullable
      public TextureMetadataSection getTextureMetadata() {
         return this.metadata;
      }

      public NativeImage getImage() throws IOException {
         if (this.exception != null) {
            throw this.exception;
         } else {
            return this.image;
         }
      }

      public void close() {
         if (this.image != null) {
            this.image.close();
         }
      }

      public void throwIfError() throws IOException {
         if (this.exception != null) {
            throw this.exception;
         }
      }
   }
}
