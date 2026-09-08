package net.minecraft.client.gui.fonts.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureGlyphProviderUnicode implements IGlyphProvider {
   private static final Logger field_211256_a = LogManager.getLogger();
   private final IResourceManager field_211257_b;
   private final byte[] field_211258_c;
   private final String field_211259_d;
   private final Map<ResourceLocation, NativeImage> field_211845_e = Maps.<ResourceLocation, NativeImage>newHashMap();

   public TextureGlyphProviderUnicode(IResourceManager var1, byte[] var2, String var3) {
      this.field_211257_b = ☃;
      this.field_211258_c = ☃;
      this.field_211259_d = ☃;

      for(int ☃ = 0; ☃ < 256; ++☃) {
         char ☃x = (char)(☃ * 256);
         ResourceLocation ☃xx = this.func_211623_c(☃x);

         try {
            IResource ☃xxx = this.field_211257_b.func_199002_a(☃xx);
            Throwable var8 = null;

            try (NativeImage ☃xxxx = NativeImage.func_211679_a(NativeImage.PixelFormat.RGBA, ☃xxx.func_199027_b())) {
               if (☃xxxx.func_195702_a() == 256 && ☃xxxx.func_195714_b() == 256) {
                  for(int ☃xxxxx = 0; ☃xxxxx < 256; ++☃xxxxx) {
                     byte ☃xxxxxx = ☃[☃x + ☃xxxxx];
                     if (☃xxxxxx != 0 && func_212453_a(☃xxxxxx) > func_212454_b(☃xxxxxx)) {
                        ☃[☃x + ☃xxxxx] = 0;
                     }
                  }
                  continue;
               }
            } catch (Throwable var41) {
               var8 = var41;
               throw var41;
            } finally {
               if (☃xxx != null) {
                  if (var8 != null) {
                     try {
                        ☃xxx.close();
                     } catch (Throwable var37) {
                        var8.addSuppressed(var37);
                     }
                  } else {
                     ☃xxx.close();
                  }
               }
            }
         } catch (IOException var43) {
         }

         Arrays.fill(☃, ☃x, ☃x + 256, (byte)0);
      }
   }

   @Override
   public void close() {
      this.field_211845_e.values().forEach(NativeImage::close);
   }

   private ResourceLocation func_211623_c(char var1) {
      ResourceLocation ☃ = new ResourceLocation(String.format(this.field_211259_d, String.format("%02x", ☃ / 256)));
      return new ResourceLocation(☃.func_110624_b(), "textures/" + ☃.func_110623_a());
   }

   @Nullable
   @Override
   public IGlyphInfo func_212248_a(char var1) {
      byte ☃ = this.field_211258_c[☃];
      if (☃ != 0) {
         NativeImage ☃x = (NativeImage)this.field_211845_e.computeIfAbsent(this.func_211623_c(☃), this::func_211255_a);
         if (☃x != null) {
            int ☃xx = func_212453_a(☃);
            return new TextureGlyphProviderUnicode.GlpyhInfo(☃ % 16 * 16 + ☃xx, (☃ & 255) / 16 * 16, func_212454_b(☃) - ☃xx, 16, ☃x);
         }
      }

      return null;
   }

   @Nullable
   private NativeImage func_211255_a(ResourceLocation var1) {
      try {
         IResource ☃ = this.field_211257_b.func_199002_a(☃);
         Throwable var3 = null;

         NativeImage var4;
         try {
            var4 = NativeImage.func_211679_a(NativeImage.PixelFormat.RGBA, ☃.func_199027_b());
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (☃ != null) {
               if (var3 != null) {
                  try {
                     ☃.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
                  }
               } else {
                  ☃.close();
               }
            }
         }

         return var4;
      } catch (IOException var16) {
         field_211256_a.error("Couldn't load texture {}", ☃, var16);
         return null;
      }
   }

   private static int func_212453_a(byte var0) {
      return ☃ >> 4 & 15;
   }

   private static int func_212454_b(byte var0) {
      return (☃ & 15) + 1;
   }

   public static class Factory implements IGlyphProviderFactory {
      private final ResourceLocation field_211247_a;
      private final String field_211248_b;

      public Factory(ResourceLocation var1, String var2) {
         this.field_211247_a = ☃;
         this.field_211248_b = ☃;
      }

      public static IGlyphProviderFactory func_211629_a(JsonObject var0) {
         return new TextureGlyphProviderUnicode.Factory(new ResourceLocation(JsonUtils.func_151200_h(☃, "sizes")), JsonUtils.func_151200_h(☃, "template"));
      }

      @Nullable
      @Override
      public IGlyphProvider func_211246_a(IResourceManager var1) {
         try {
            IResource ☃ = Minecraft.func_71410_x().func_195551_G().func_199002_a(this.field_211247_a);
            Throwable var3 = null;

            TextureGlyphProviderUnicode var5;
            try {
               byte[] ☃x = new byte[65536];
               ☃.func_199027_b().read(☃x);
               var5 = new TextureGlyphProviderUnicode(☃, ☃x, this.field_211248_b);
            } catch (Throwable var15) {
               var3 = var15;
               throw var15;
            } finally {
               if (☃ != null) {
                  if (var3 != null) {
                     try {
                        ☃.close();
                     } catch (Throwable var14) {
                        var3.addSuppressed(var14);
                     }
                  } else {
                     ☃.close();
                  }
               }
            }

            return var5;
         } catch (IOException var17) {
            TextureGlyphProviderUnicode.field_211256_a.error("Cannot load {}, unicode glyphs will not render correctly", this.field_211247_a);
            return null;
         }
      }
   }

   static class GlpyhInfo implements IGlyphInfo {
      private final int field_211210_a;
      private final int field_211211_b;
      private final int field_211212_c;
      private final int field_211213_d;
      private final NativeImage field_211214_e;

      private GlpyhInfo(int var1, int var2, int var3, int var4, NativeImage var5) {
         this.field_211210_a = ☃;
         this.field_211211_b = ☃;
         this.field_211212_c = ☃;
         this.field_211213_d = ☃;
         this.field_211214_e = ☃;
      }

      @Override
      public float func_211578_g() {
         return 2.0F;
      }

      @Override
      public int func_211202_a() {
         return this.field_211210_a;
      }

      @Override
      public int func_211203_b() {
         return this.field_211211_b;
      }

      @Override
      public float getAdvance() {
         return (float)(this.field_211210_a / 2 + 1);
      }

      @Override
      public void func_211573_a(int var1, int var2) {
         this.field_211214_e.func_195706_a(0, ☃, ☃, this.field_211212_c, this.field_211213_d, this.field_211210_a, this.field_211211_b, false);
      }

      @Override
      public boolean func_211579_f() {
         return this.field_211214_e.func_211678_c().func_211651_a() > 1;
      }

      @Override
      public float getShadowOffset() {
         return 0.5F;
      }

      @Override
      public float getBoldOffset() {
         return 0.5F;
      }
   }
}
