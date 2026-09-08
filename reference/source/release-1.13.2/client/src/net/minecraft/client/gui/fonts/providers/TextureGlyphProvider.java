package net.minecraft.client.gui.fonts.providers;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureGlyphProvider implements IGlyphProvider {
   private static final Logger field_211609_a = LogManager.getLogger();
   private final NativeImage field_211610_b;
   private final Char2ObjectMap<TextureGlyphProvider.GlyphInfo> field_211267_a;

   public TextureGlyphProvider(NativeImage var1, Char2ObjectMap<TextureGlyphProvider.GlyphInfo> var2) {
      this.field_211610_b = ☃;
      this.field_211267_a = ☃;
   }

   @Override
   public void close() {
      this.field_211610_b.close();
   }

   @Nullable
   @Override
   public IGlyphInfo func_212248_a(char var1) {
      return this.field_211267_a.get(☃);
   }

   public static class Factory implements IGlyphProviderFactory {
      private final ResourceLocation field_211252_a;
      private final List<String> field_211634_b;
      private final int field_211635_c;
      private final int field_211636_d;

      public Factory(ResourceLocation var1, int var2, int var3, List<String> var4) {
         this.field_211252_a = new ResourceLocation(☃.func_110624_b(), "textures/" + ☃.func_110623_a());
         this.field_211634_b = ☃;
         this.field_211635_c = ☃;
         this.field_211636_d = ☃;
      }

      public static TextureGlyphProvider.Factory func_211633_a(JsonObject var0) {
         int ☃ = JsonUtils.func_151208_a(☃, "height", 8);
         int ☃x = JsonUtils.func_151203_m(☃, "ascent");
         if (☃x > ☃) {
            throw new JsonParseException("Ascent " + ☃x + " higher than height " + ☃);
         } else {
            List<String> ☃ = Lists.newArrayList();
            JsonArray ☃x = JsonUtils.func_151214_t(☃, "chars");

            for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
               String ☃xxx = JsonUtils.func_151206_a(☃x.get(☃xx), "chars[" + ☃xx + "]");
               if (☃xx > 0) {
                  int ☃xxxx = ☃xxx.length();
                  int ☃xxxxx = ((String)☃.get(0)).length();
                  if (☃xxxx != ☃xxxxx) {
                     throw new JsonParseException(
                        "Elements of chars have to be the same lenght (found: " + ☃xxxx + ", expected: " + ☃xxxxx + "), pad with space or \\u0000"
                     );
                  }
               }

               ☃.add(☃xxx);
            }

            if (!☃.isEmpty() && !((String)☃.get(0)).isEmpty()) {
               return new TextureGlyphProvider.Factory(new ResourceLocation(JsonUtils.func_151200_h(☃, "file")), ☃, ☃x, ☃);
            } else {
               throw new JsonParseException("Expected to find data in chars, found none.");
            }
         }
      }

      @Nullable
      @Override
      public IGlyphProvider func_211246_a(IResourceManager var1) {
         try {
            IResource ☃ = ☃.func_199002_a(this.field_211252_a);
            Throwable var3 = null;

            TextureGlyphProvider var27;
            try {
               NativeImage ☃x = NativeImage.func_211679_a(NativeImage.PixelFormat.RGBA, ☃.func_199027_b());
               int ☃xx = ☃x.func_195702_a();
               int ☃xxx = ☃x.func_195714_b();
               int ☃xxxx = ☃xx / ((String)this.field_211634_b.get(0)).length();
               int ☃xxxxx = ☃xxx / this.field_211634_b.size();
               float ☃xxxxxx = (float)this.field_211635_c / (float)☃xxxxx;
               Char2ObjectMap<TextureGlyphProvider.GlyphInfo> ☃xxxxxxx = new Char2ObjectOpenHashMap<>();

               for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < this.field_211634_b.size(); ++☃xxxxxxxx) {
                  String ☃xxxxxxxxx = (String)this.field_211634_b.get(☃xxxxxxxx);

                  for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃xxxxxxxxx.length(); ++☃xxxxxxxxxx) {
                     char ☃xxxxxxxxxxx = ☃xxxxxxxxx.charAt(☃xxxxxxxxxx);
                     if (☃xxxxxxxxxxx != 0 && ☃xxxxxxxxxxx != ' ') {
                        int ☃xxxxxxxxxxxx = this.func_211632_a(☃x, ☃xxxx, ☃xxxxx, ☃xxxxxxxxxx, ☃xxxxxxxx);
                        ☃xxxxxxx.put(
                           ☃xxxxxxxxxxx,
                           new TextureGlyphProvider.GlyphInfo(
                              ☃xxxxxx,
                              ☃x,
                              ☃xxxxxxxxxx * ☃xxxx,
                              ☃xxxxxxxx * ☃xxxxx,
                              ☃xxxx,
                              ☃xxxxx,
                              (int)(0.5 + (double)((float)☃xxxxxxxxxxxx * ☃xxxxxx)) + 1,
                              this.field_211636_d
                           )
                        );
                     }
                  }
               }

               var27 = new TextureGlyphProvider(☃x, ☃xxxxxxx);
            } catch (Throwable var24) {
               var3 = var24;
               throw var24;
            } finally {
               if (☃ != null) {
                  if (var3 != null) {
                     try {
                        ☃.close();
                     } catch (Throwable var23) {
                        var3.addSuppressed(var23);
                     }
                  } else {
                     ☃.close();
                  }
               }
            }

            return var27;
         } catch (IOException var26) {
            throw new RuntimeException(var26.getMessage());
         }
      }

      private int func_211632_a(NativeImage var1, int var2, int var3, int var4, int var5) {
         int ☃;
         for(☃ = ☃ - 1; ☃ >= 0; --☃) {
            int ☃ = ☃ * ☃ + ☃;

            for(int ☃x = 0; ☃x < ☃; ++☃x) {
               int ☃xx = ☃ * ☃ + ☃x;
               if (☃.func_211675_e(☃, ☃xx) != 0) {
                  return ☃ + 1;
               }
            }
         }

         return ☃ + 1;
      }
   }

   static final class GlyphInfo implements IGlyphInfo {
      private final float field_211582_a;
      private final NativeImage field_211583_b;
      private final int field_211584_c;
      private final int field_211585_d;
      private final int field_211586_e;
      private final int field_211587_f;
      private final int field_211588_g;
      private final int field_211589_h;

      private GlyphInfo(float var1, NativeImage var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         this.field_211582_a = ☃;
         this.field_211583_b = ☃;
         this.field_211584_c = ☃;
         this.field_211585_d = ☃;
         this.field_211586_e = ☃;
         this.field_211587_f = ☃;
         this.field_211588_g = ☃;
         this.field_211589_h = ☃;
      }

      @Override
      public float func_211578_g() {
         return 1.0F / this.field_211582_a;
      }

      @Override
      public int func_211202_a() {
         return this.field_211586_e;
      }

      @Override
      public int func_211203_b() {
         return this.field_211587_f;
      }

      @Override
      public float getAdvance() {
         return (float)this.field_211588_g;
      }

      @Override
      public float getBearingY() {
         return IGlyphInfo.super.getBearingY() + 7.0F - (float)this.field_211589_h;
      }

      @Override
      public void func_211573_a(int var1, int var2) {
         this.field_211583_b.func_195706_a(0, ☃, ☃, this.field_211584_c, this.field_211585_d, this.field_211586_e, this.field_211587_f, false);
      }

      @Override
      public boolean func_211579_f() {
         return this.field_211583_b.func_211678_c().func_211651_a() > 1;
      }
   }
}
