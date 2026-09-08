package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

@DontObfuscate
public class TextureUtil {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final int MIN_MIPMAP_LEVEL = 0;
   private static final int DEFAULT_IMAGE_BUFFER_SIZE = 8192;

   public static int generateTextureId() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         int[] â˜ƒ = new int[ThreadLocalRandom.current().nextInt(15) + 1];
         GlStateManager._genTextures(â˜ƒ);
         int â˜ƒx = GlStateManager._genTexture();
         GlStateManager._deleteTextures(â˜ƒ);
         return â˜ƒx;
      } else {
         return GlStateManager._genTexture();
      }
   }

   public static void releaseTextureId(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._deleteTexture(â˜ƒ);
   }

   public static void prepareImage(int var0, int var1, int var2) {
      prepareImage(NativeImage.InternalGlFormat.RGBA, â˜ƒ, 0, â˜ƒ, â˜ƒ);
   }

   public static void prepareImage(NativeImage.InternalGlFormat var0, int var1, int var2, int var3) {
      prepareImage(â˜ƒ, â˜ƒ, 0, â˜ƒ, â˜ƒ);
   }

   public static void prepareImage(int var0, int var1, int var2, int var3) {
      prepareImage(NativeImage.InternalGlFormat.RGBA, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void prepareImage(NativeImage.InternalGlFormat var0, int var1, int var2, int var3, int var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      bind(â˜ƒ);
      if (â˜ƒ >= 0) {
         GlStateManager._texParameter(3553, 33085, â˜ƒ);
         GlStateManager._texParameter(3553, 33082, 0);
         GlStateManager._texParameter(3553, 33083, â˜ƒ);
         GlStateManager._texParameter(3553, 34049, 0.0F);
      }

      for(int â˜ƒ = 0; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         GlStateManager._texImage2D(3553, â˜ƒ, â˜ƒ.glFormat(), â˜ƒ >> â˜ƒ, â˜ƒ >> â˜ƒ, 0, 6408, 5121, null);
      }
   }

   private static void bind(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._bindTexture(â˜ƒ);
   }

   public static ByteBuffer readResource(InputStream var0) throws IOException {
      ByteBuffer â˜ƒx;
      if (â˜ƒ instanceof FileInputStream â˜ƒ) {
         FileChannel â˜ƒxx = â˜ƒ.getChannel();
         â˜ƒx = MemoryUtil.memAlloc((int)â˜ƒxx.size() + 1);

         while(â˜ƒxx.read(â˜ƒx) != -1) {
         }
      } else {
         â˜ƒx = MemoryUtil.memAlloc(8192);
         ReadableByteChannel â˜ƒ = Channels.newChannel(â˜ƒ);

         while(â˜ƒ.read(â˜ƒx) != -1) {
            if (â˜ƒx.remaining() == 0) {
               â˜ƒx = MemoryUtil.memRealloc(â˜ƒx, â˜ƒx.capacity() * 2);
            }
         }
      }

      return â˜ƒx;
   }

   @Nullable
   public static String readResourceAsString(InputStream var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      ByteBuffer â˜ƒ = null;

      try {
         â˜ƒ = readResource(â˜ƒ);
         int â˜ƒx = â˜ƒ.position();
         â˜ƒ.rewind();
         return MemoryUtil.memASCII(â˜ƒ, â˜ƒx);
      } catch (IOException var7) {
      } finally {
         if (â˜ƒ != null) {
            MemoryUtil.memFree(â˜ƒ);
         }
      }

      return null;
   }

   public static void writeAsPNG(String var0, int var1, int var2, int var3, int var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      bind(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         String â˜ƒx = â˜ƒ + "_" + â˜ƒ + ".png";
         int â˜ƒxx = â˜ƒ >> â˜ƒ;
         int â˜ƒxxx = â˜ƒ >> â˜ƒ;

         try (NativeImage â˜ƒxxxx = new NativeImage(â˜ƒxx, â˜ƒxxx, false)) {
            â˜ƒxxxx.downloadTexture(â˜ƒ, false);
            â˜ƒxxxx.writeToFile(â˜ƒx);
            LOGGER.debug("Exported png to: {}", new File(â˜ƒx).getAbsolutePath());
         } catch (IOException var14) {
            LOGGER.debug("Unable to write: ", var14);
         }
      }
   }

   public static void initTexture(IntBuffer var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPixelStorei(3312, 0);
      GL11.glPixelStorei(3313, 0);
      GL11.glPixelStorei(3314, 0);
      GL11.glPixelStorei(3315, 0);
      GL11.glPixelStorei(3316, 0);
      GL11.glPixelStorei(3317, 4);
      GL11.glTexImage2D(3553, 0, 6408, â˜ƒ, â˜ƒ, 0, 32993, 33639, â˜ƒ);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10241, 9729);
   }
}
