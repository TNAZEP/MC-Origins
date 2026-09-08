package net.minecraft.client.renderer.texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData extends SimpleTexture {
   private static final Logger field_147644_c = LogManager.getLogger();
   private static final AtomicInteger field_147643_d = new AtomicInteger(0);
   @Nullable
   private final File field_152434_e;
   private final String field_110562_b;
   @Nullable
   private final IImageBuffer field_110563_c;
   @Nullable
   private Thread field_110561_e;
   private volatile boolean field_110559_g;

   public ThreadDownloadImageData(@Nullable File var1, String var2, ResourceLocation var3, @Nullable IImageBuffer var4) {
      super(☃);
      this.field_152434_e = ☃;
      this.field_110562_b = ☃;
      this.field_110563_c = ☃;
   }

   private void func_195416_b(NativeImage var1) {
      TextureUtil.func_110991_a(this.func_110552_b(), ☃.func_195702_a(), ☃.func_195714_b());
      ☃.func_195697_a(0, 0, 0, false);
   }

   public void func_195417_a(NativeImage var1) {
      if (this.field_110563_c != null) {
         this.field_110563_c.func_152634_a();
      }

      synchronized(this) {
         this.func_195416_b(☃);
         this.field_110559_g = true;
      }
   }

   @Override
   public void func_195413_a(IResourceManager var1) throws IOException {
      if (!this.field_110559_g) {
         synchronized(this) {
            super.func_195413_a(☃);
            this.field_110559_g = true;
         }
      }

      if (this.field_110561_e == null) {
         if (this.field_152434_e != null && this.field_152434_e.isFile()) {
            field_147644_c.debug("Loading http texture from local cache ({})", this.field_152434_e);
            NativeImage ☃ = null;

            try {
               try {
                  ☃ = NativeImage.func_195713_a(new FileInputStream(this.field_152434_e));
                  if (this.field_110563_c != null) {
                     ☃ = this.field_110563_c.func_195786_a(☃);
                  }

                  this.func_195417_a(☃);
               } catch (IOException var8) {
                  field_147644_c.error("Couldn't load skin {}", this.field_152434_e, var8);
                  this.func_152433_a();
               }
            } finally {
               if (☃ != null) {
                  ☃.close();
               }
            }
         } else {
            this.func_152433_a();
         }
      }
   }

   protected void func_152433_a() {
      this.field_110561_e = new Thread("Texture Downloader #" + field_147643_d.incrementAndGet()) {
         public void run() {
            HttpURLConnection ☃ = null;
            ThreadDownloadImageData.field_147644_c
               .debug("Downloading http texture from {} to {}", ThreadDownloadImageData.this.field_110562_b, ThreadDownloadImageData.this.field_152434_e);

            try {
               ☃ = (HttpURLConnection)new URL(ThreadDownloadImageData.this.field_110562_b).openConnection(Minecraft.func_71410_x().func_110437_J());
               ☃.setDoInput(true);
               ☃.setDoOutput(false);
               ☃.connect();
               if (☃.getResponseCode() / 100 == 2) {
                  InputStream ☃x;
                  if (ThreadDownloadImageData.this.field_152434_e != null) {
                     FileUtils.copyInputStreamToFile(☃.getInputStream(), ThreadDownloadImageData.this.field_152434_e);
                     ☃x = new FileInputStream(ThreadDownloadImageData.this.field_152434_e);
                  } else {
                     ☃x = ☃.getInputStream();
                  }

                  Minecraft.func_71410_x().func_152344_a(() -> {
                     NativeImage ☃ = null;

                     try {
                        ☃ = NativeImage.func_195713_a(☃);
                        if (ThreadDownloadImageData.this.field_110563_c != null) {
                           ☃ = ThreadDownloadImageData.this.field_110563_c.func_195786_a(☃);
                        }

                        NativeImage ☃x = ☃;
                        Minecraft.func_71410_x().func_152344_a(() -> ThreadDownloadImageData.this.func_195417_a(☃));
                     } catch (IOException var7xx) {
                        ThreadDownloadImageData.field_147644_c.warn("Error while loading the skin texture", var7xx);
                     } finally {
                        if (☃ != null) {
                           ☃.close();
                        }

                        IOUtils.closeQuietly(☃);
                     }
                  });
                  return;
               }
            } catch (Exception var6) {
               ThreadDownloadImageData.field_147644_c.error("Couldn't download http texture", var6);
               return;
            } finally {
               if (☃ != null) {
                  ☃.disconnect();
               }
            }
         }
      };
      this.field_110561_e.setDaemon(true);
      this.field_110561_e.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_147644_c));
      this.field_110561_e.start();
   }
}
