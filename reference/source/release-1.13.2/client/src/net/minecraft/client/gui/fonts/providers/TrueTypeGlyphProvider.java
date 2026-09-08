package net.minecraft.client.gui.fonts.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.chars.CharArraySet;
import it.unimi.dsi.fastutil.chars.CharSet;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

public class TrueTypeGlyphProvider implements IGlyphProvider {
   private static final Logger field_211263_a = LogManager.getLogger();
   private final STBTTFontinfo field_211264_b;
   private final float field_211618_c;
   private final CharSet field_211619_d = new CharArraySet();
   private final float field_211620_e;
   private final float field_211621_f;
   private final float field_211266_d;
   private final float field_211622_h;

   protected TrueTypeGlyphProvider(STBTTFontinfo var1, float var2, float var3, float var4, float var5, String var6) {
      this.field_211264_b = ☃;
      this.field_211618_c = ☃;
      ☃.chars().forEach(var1x -> this.field_211619_d.add((char)(var1x & 65535)));
      this.field_211620_e = ☃ * ☃;
      this.field_211621_f = ☃ * ☃;
      this.field_211266_d = STBTruetype.stbtt_ScaleForPixelHeight(☃, ☃ * ☃);

      try (MemoryStack ☃ = MemoryStack.stackPush()) {
         IntBuffer ☃x = ☃.mallocInt(1);
         IntBuffer ☃xx = ☃.mallocInt(1);
         IntBuffer ☃xxx = ☃.mallocInt(1);
         STBTruetype.stbtt_GetFontVMetrics(☃, ☃x, ☃xx, ☃xxx);
         this.field_211622_h = (float)☃x.get(0) * this.field_211266_d;
      }
   }

   @Nullable
   public TrueTypeGlyphProvider.GlpyhInfo func_212248_a(char var1) {
      if (this.field_211619_d.contains(☃)) {
         return null;
      } else {
         TrueTypeGlyphProvider.GlpyhInfo var13;
         try (MemoryStack ☃ = MemoryStack.stackPush()) {
            IntBuffer ☃x = ☃.mallocInt(1);
            IntBuffer ☃xx = ☃.mallocInt(1);
            IntBuffer ☃xxx = ☃.mallocInt(1);
            IntBuffer ☃xxxx = ☃.mallocInt(1);
            int ☃xxxxx = STBTruetype.stbtt_FindGlyphIndex(this.field_211264_b, ☃);
            if (☃xxxxx == 0) {
               return null;
            }

            STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(
               this.field_211264_b, ☃xxxxx, this.field_211266_d, this.field_211266_d, this.field_211620_e, this.field_211621_f, ☃x, ☃xx, ☃xxx, ☃xxxx
            );
            int ☃x = ☃xxx.get(0) - ☃x.get(0);
            int ☃xx = ☃xxxx.get(0) - ☃xx.get(0);
            if (☃x == 0 || ☃xx == 0) {
               return null;
            }

            IntBuffer ☃x = ☃.mallocInt(1);
            IntBuffer ☃xx = ☃.mallocInt(1);
            STBTruetype.stbtt_GetGlyphHMetrics(this.field_211264_b, ☃xxxxx, ☃x, ☃xx);
            var13 = new TrueTypeGlyphProvider.GlpyhInfo(
               ☃x.get(0), ☃xxx.get(0), -☃xx.get(0), -☃xxxx.get(0), (float)☃x.get(0) * this.field_211266_d, (float)☃xx.get(0) * this.field_211266_d, ☃xxxxx
            );
         }

         return var13;
      }
   }

   public static class Factory implements IGlyphProviderFactory {
      private final ResourceLocation field_211249_a;
      private final float field_211250_b;
      private final float field_211625_c;
      private final float field_211626_d;
      private final float field_211627_e;
      private final String field_211628_f;

      public Factory(ResourceLocation var1, float var2, float var3, float var4, float var5, String var6) {
         this.field_211249_a = ☃;
         this.field_211250_b = ☃;
         this.field_211625_c = ☃;
         this.field_211626_d = ☃;
         this.field_211627_e = ☃;
         this.field_211628_f = ☃;
      }

      public static IGlyphProviderFactory func_211624_a(JsonObject var0) {
         float ☃ = 0.0F;
         float ☃x = 0.0F;
         if (☃.has("shift")) {
            JsonArray ☃xx = ☃.getAsJsonArray("shift");
            if (☃xx.size() != 2) {
               throw new JsonParseException("Expected 2 elements in 'shift', found " + ☃xx.size());
            }

            ☃ = JsonUtils.func_151220_d(☃xx.get(0), "shift[0]");
            ☃x = JsonUtils.func_151220_d(☃xx.get(1), "shift[1]");
         }

         StringBuilder ☃ = new StringBuilder();
         if (☃.has("skip")) {
            JsonElement ☃x = ☃.get("skip");
            if (☃x.isJsonArray()) {
               JsonArray ☃xx = JsonUtils.func_151207_m(☃x, "skip");

               for(int ☃xxx = 0; ☃xxx < ☃xx.size(); ++☃xxx) {
                  ☃.append(JsonUtils.func_151206_a(☃xx.get(☃xxx), "skip[" + ☃xxx + "]"));
               }
            } else {
               ☃.append(JsonUtils.func_151206_a(☃x, "skip"));
            }
         }

         return new TrueTypeGlyphProvider.Factory(
            new ResourceLocation(JsonUtils.func_151200_h(☃, "file")),
            JsonUtils.func_151221_a(☃, "size", 11.0F),
            JsonUtils.func_151221_a(☃, "oversample", 1.0F),
            ☃,
            ☃x,
            ☃.toString()
         );
      }

      @Nullable
      @Override
      public IGlyphProvider func_211246_a(IResourceManager var1) {
         try {
            IResource ☃ = ☃.func_199002_a(new ResourceLocation(this.field_211249_a.func_110624_b(), "font/" + this.field_211249_a.func_110623_a()));
            Throwable var3 = null;

            TrueTypeGlyphProvider var6;
            try {
               TrueTypeGlyphProvider.field_211263_a.info("Loading font");
               ByteBuffer ☃x = TextureUtil.func_195724_a(☃.func_199027_b());
               ☃x.flip();
               STBTTFontinfo ☃xx = STBTTFontinfo.create();
               TrueTypeGlyphProvider.field_211263_a.info("Reading font");
               if (!STBTruetype.stbtt_InitFont(☃xx, ☃x)) {
                  throw new IOException("Invalid ttf");
               }

               var6 = new TrueTypeGlyphProvider(☃xx, this.field_211250_b, this.field_211625_c, this.field_211626_d, this.field_211627_e, this.field_211628_f);
            } catch (Throwable var16) {
               var3 = var16;
               throw var16;
            } finally {
               if (☃ != null) {
                  if (var3 != null) {
                     try {
                        ☃.close();
                     } catch (Throwable var15) {
                        var3.addSuppressed(var15);
                     }
                  } else {
                     ☃.close();
                  }
               }
            }

            return var6;
         } catch (IOException var18) {
            TrueTypeGlyphProvider.field_211263_a.error("Couldn't load truetype font {}", this.field_211249_a, var18);
            return null;
         }
      }
   }

   class GlpyhInfo implements IGlyphInfo {
      private final int field_211216_b;
      private final int field_211217_c;
      private final float field_212464_d;
      private final float field_212465_e;
      private final float field_211598_i;
      private final int field_211599_j;

      private GlpyhInfo(int var2, int var3, int var4, int var5, float var6, float var7, int var8) {
         this.field_211216_b = ☃ - ☃;
         this.field_211217_c = ☃ - ☃;
         this.field_211598_i = ☃ / TrueTypeGlyphProvider.this.field_211618_c;
         this.field_212464_d = (☃ + (float)☃ + TrueTypeGlyphProvider.this.field_211620_e) / TrueTypeGlyphProvider.this.field_211618_c;
         this.field_212465_e = (TrueTypeGlyphProvider.this.field_211622_h - (float)☃ + TrueTypeGlyphProvider.this.field_211621_f)
            / TrueTypeGlyphProvider.this.field_211618_c;
         this.field_211599_j = ☃;
      }

      @Override
      public int func_211202_a() {
         return this.field_211216_b;
      }

      @Override
      public int func_211203_b() {
         return this.field_211217_c;
      }

      @Override
      public float func_211578_g() {
         return TrueTypeGlyphProvider.this.field_211618_c;
      }

      @Override
      public float getAdvance() {
         return this.field_211598_i;
      }

      @Override
      public float getBearingX() {
         return this.field_212464_d;
      }

      @Override
      public float getBearingY() {
         return this.field_212465_e;
      }

      @Override
      public void func_211573_a(int var1, int var2) {
         try (NativeImage ☃ = new NativeImage(NativeImage.PixelFormat.LUMINANCE, this.field_211216_b, this.field_211217_c, false)) {
            ☃.func_211676_a(
               TrueTypeGlyphProvider.this.field_211264_b,
               this.field_211599_j,
               this.field_211216_b,
               this.field_211217_c,
               TrueTypeGlyphProvider.this.field_211266_d,
               TrueTypeGlyphProvider.this.field_211266_d,
               TrueTypeGlyphProvider.this.field_211620_e,
               TrueTypeGlyphProvider.this.field_211621_f,
               0,
               0
            );
            ☃.func_195706_a(0, ☃, ☃, 0, 0, this.field_211216_b, this.field_211217_c, false);
         }
      }

      @Override
      public boolean func_211579_f() {
         return false;
      }
   }
}
