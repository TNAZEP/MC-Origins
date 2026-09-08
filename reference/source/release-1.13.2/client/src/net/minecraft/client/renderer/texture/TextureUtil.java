package net.minecraft.client.renderer.texture;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryUtil;

public class TextureUtil {
   private static final Logger field_147959_c = LogManager.getLogger();

   public static int func_110996_a() {
      return GlStateManager.func_179146_y();
   }

   public static void func_147942_a(int var0) {
      GlStateManager.func_179150_h(☃);
   }

   public static void func_110991_a(int var0, int var1, int var2) {
      func_211682_a(NativeImage.PixelFormatGLCode.RGBA, ☃, 0, ☃, ☃);
   }

   public static void func_211681_a(NativeImage.PixelFormatGLCode var0, int var1, int var2, int var3) {
      func_211682_a(☃, ☃, 0, ☃, ☃);
   }

   public static void func_180600_a(int var0, int var1, int var2, int var3) {
      func_211682_a(NativeImage.PixelFormatGLCode.RGBA, ☃, ☃, ☃, ☃);
   }

   public static void func_211682_a(NativeImage.PixelFormatGLCode var0, int var1, int var2, int var3, int var4) {
      func_94277_a(☃);
      if (☃ >= 0) {
         GlStateManager.func_187421_b(3553, 33085, ☃);
         GlStateManager.func_187421_b(3553, 33082, 0);
         GlStateManager.func_187421_b(3553, 33083, ☃);
         GlStateManager.func_187403_b(3553, 34049, 0.0F);
      }

      for(int ☃ = 0; ☃ <= ☃; ++☃) {
         GlStateManager.func_187419_a(3553, ☃, ☃.func_211672_a(), ☃ >> ☃, ☃ >> ☃, 0, 6408, 5121, null);
      }
   }

   private static void func_94277_a(int var0) {
      GlStateManager.func_179144_i(☃);
   }

   @Deprecated
   public static int[] func_195725_a(IResourceManager var0, ResourceLocation var1) throws IOException {
      IResource ☃ = ☃.func_199002_a(☃);
      Throwable var3 = null;

      int[] var6;
      try (NativeImage ☃x = NativeImage.func_195713_a(☃.func_199027_b())) {
         var6 = ☃x.func_195716_c();
      } catch (Throwable var31) {
         var3 = var31;
         throw var31;
      } finally {
         if (☃ != null) {
            if (var3 != null) {
               try {
                  ☃.close();
               } catch (Throwable var27) {
                  var3.addSuppressed(var27);
               }
            } else {
               ☃.close();
            }
         }
      }

      return var6;
   }

   public static ByteBuffer func_195724_a(InputStream var0) throws IOException {
      ByteBuffer ☃;
      if (☃ instanceof FileInputStream) {
         FileInputStream ☃x = (FileInputStream)☃;
         FileChannel ☃xx = ☃x.getChannel();
         ☃ = MemoryUtil.memAlloc((int)☃xx.size() + 1);

         while(☃xx.read(☃) != -1) {
         }
      } else {
         ☃ = MemoryUtil.memAlloc(8192);
         ReadableByteChannel ☃ = Channels.newChannel(☃);

         while(☃.read(☃) != -1) {
            if (☃.remaining() == 0) {
               ☃ = MemoryUtil.memRealloc(☃, ☃.capacity() * 2);
            }
         }
      }

      return ☃;
   }
}
