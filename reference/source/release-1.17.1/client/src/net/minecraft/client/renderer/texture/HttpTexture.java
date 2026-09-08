package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpTexture extends SimpleTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int SKIN_WIDTH = 64;
   private static final int SKIN_HEIGHT = 64;
   private static final int LEGACY_SKIN_HEIGHT = 32;
   @Nullable
   private final File file;
   private final String urlString;
   private final boolean processLegacySkin;
   @Nullable
   private final Runnable onDownloaded;
   @Nullable
   private CompletableFuture<?> future;
   private boolean uploaded;

   public HttpTexture(@Nullable File var1, String var2, ResourceLocation var3, boolean var4, @Nullable Runnable var5) {
      super(â˜ƒ);
      this.file = â˜ƒ;
      this.urlString = â˜ƒ;
      this.processLegacySkin = â˜ƒ;
      this.onDownloaded = â˜ƒ;
   }

   private void loadCallback(NativeImage var1) {
      if (this.onDownloaded != null) {
         this.onDownloaded.run();
      }

      Minecraft.getInstance().execute(() -> {
         this.uploaded = true;
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.upload(â˜ƒ));
         } else {
            this.upload(â˜ƒ);
         }
      });
   }

   private void upload(NativeImage var1) {
      TextureUtil.prepareImage(this.getId(), â˜ƒ.getWidth(), â˜ƒ.getHeight());
      â˜ƒ.upload(0, 0, 0, true);
   }

   @Override
   public void load(ResourceManager var1) throws IOException {
      Minecraft.getInstance().execute(() -> {
         if (!this.uploaded) {
            try {
               super.load(â˜ƒ);
            } catch (IOException var3xx) {
               LOGGER.warn("Failed to load texture: {}", this.location, var3xx);
            }

            this.uploaded = true;
         }
      });
      if (this.future == null) {
         NativeImage â˜ƒ;
         if (this.file != null && this.file.isFile()) {
            LOGGER.debug("Loading http texture from local cache ({})", this.file);
            FileInputStream â˜ƒx = new FileInputStream(this.file);
            â˜ƒ = this.load(â˜ƒx);
         } else {
            â˜ƒ = null;
         }

         if (â˜ƒ != null) {
            this.loadCallback(â˜ƒ);
         } else {
            this.future = CompletableFuture.runAsync(() -> {
               HttpURLConnection â˜ƒ = null;
               LOGGER.debug("Downloading http texture from {} to {}", this.urlString, this.file);

               try {
                  â˜ƒ = (HttpURLConnection)new URL(this.urlString).openConnection(Minecraft.getInstance().getProxy());
                  â˜ƒ.setDoInput(true);
                  â˜ƒ.setDoOutput(false);
                  â˜ƒ.connect();
                  if (â˜ƒ.getResponseCode() / 100 == 2) {
                     InputStream â˜ƒx;
                     if (this.file != null) {
                        FileUtils.copyInputStreamToFile(â˜ƒ.getInputStream(), this.file);
                        â˜ƒx = new FileInputStream(this.file);
                     } else {
                        â˜ƒx = â˜ƒ.getInputStream();
                     }

                     Minecraft.getInstance().execute(() -> {
                        NativeImage â˜ƒ = this.load(â˜ƒ);
                        if (â˜ƒ != null) {
                           this.loadCallback(â˜ƒ);
                        }
                     });
                     return;
                  }
               } catch (Exception var6) {
                  LOGGER.error("Couldn't download http texture", var6);
                  return;
               } finally {
                  if (â˜ƒ != null) {
                     â˜ƒ.disconnect();
                  }
               }
            }, Util.backgroundExecutor());
         }
      }
   }

   @Nullable
   private NativeImage load(InputStream var1) {
      NativeImage â˜ƒ = null;

      try {
         â˜ƒ = NativeImage.read(â˜ƒ);
         if (this.processLegacySkin) {
            â˜ƒ = this.processLegacySkin(â˜ƒ);
         }
      } catch (Exception var4) {
         LOGGER.warn("Error while loading the skin texture", var4);
      }

      return â˜ƒ;
   }

   @Nullable
   private NativeImage processLegacySkin(NativeImage var1) {
      int â˜ƒ = â˜ƒ.getHeight();
      int â˜ƒx = â˜ƒ.getWidth();
      if (â˜ƒx == 64 && (â˜ƒ == 32 || â˜ƒ == 64)) {
         boolean â˜ƒxx = â˜ƒ == 32;
         if (â˜ƒxx) {
            NativeImage â˜ƒxxx = new NativeImage(64, 64, true);
            â˜ƒxxx.copyFrom(â˜ƒ);
            â˜ƒ.close();
            â˜ƒ = â˜ƒxxx;
            â˜ƒxxx.fillRect(0, 32, 64, 32, 0);
            â˜ƒxxx.copyRect(4, 16, 16, 32, 4, 4, true, false);
            â˜ƒxxx.copyRect(8, 16, 16, 32, 4, 4, true, false);
            â˜ƒxxx.copyRect(0, 20, 24, 32, 4, 12, true, false);
            â˜ƒxxx.copyRect(4, 20, 16, 32, 4, 12, true, false);
            â˜ƒxxx.copyRect(8, 20, 8, 32, 4, 12, true, false);
            â˜ƒxxx.copyRect(12, 20, 16, 32, 4, 12, true, false);
            â˜ƒxxx.copyRect(44, 16, -8, 32, 4, 4, true, false);
            â˜ƒxxx.copyRect(48, 16, -8, 32, 4, 4, true, false);
            â˜ƒxxx.copyRect(40, 20, 0, 32, 4, 12, true, false);
            â˜ƒxxx.copyRect(44, 20, -8, 32, 4, 12, true, false);
            â˜ƒxxx.copyRect(48, 20, -16, 32, 4, 12, true, false);
            â˜ƒxxx.copyRect(52, 20, -8, 32, 4, 12, true, false);
         }

         setNoAlpha(â˜ƒ, 0, 0, 32, 16);
         if (â˜ƒxx) {
            doNotchTransparencyHack(â˜ƒ, 32, 0, 64, 32);
         }

         setNoAlpha(â˜ƒ, 0, 16, 64, 32);
         setNoAlpha(â˜ƒ, 16, 48, 48, 64);
         return â˜ƒ;
      } else {
         â˜ƒ.close();
         LOGGER.warn("Discarding incorrectly sized ({}x{}) skin texture from {}", â˜ƒx, â˜ƒ, this.urlString);
         return null;
      }
   }

   private static void doNotchTransparencyHack(NativeImage var0, int var1, int var2, int var3, int var4) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            int â˜ƒxx = â˜ƒ.getPixelRGBA(â˜ƒ, â˜ƒx);
            if ((â˜ƒxx >> 24 & 0xFF) < 128) {
               return;
            }
         }
      }

      for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            â˜ƒ.setPixelRGBA(â˜ƒ, â˜ƒx, â˜ƒ.getPixelRGBA(â˜ƒ, â˜ƒx) & 16777215);
         }
      }
   }

   private static void setNoAlpha(NativeImage var0, int var1, int var2, int var3, int var4) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            â˜ƒ.setPixelRGBA(â˜ƒ, â˜ƒx, â˜ƒ.getPixelRGBA(â˜ƒ, â˜ƒx) | 0xFF000000);
         }
      }
   }
}
