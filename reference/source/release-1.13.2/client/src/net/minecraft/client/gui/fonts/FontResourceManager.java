package net.minecraft.client.gui.fonts;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.providers.DefaultGlyphProvider;
import net.minecraft.client.gui.fonts.providers.GlyphProviderTypes;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FontResourceManager implements IResourceManagerReloadListener {
   private static final Logger field_211509_a = LogManager.getLogger();
   private final Map<ResourceLocation, FontRenderer> field_211510_b = Maps.<ResourceLocation, FontRenderer>newHashMap();
   private final TextureManager field_211511_c;
   private boolean field_211826_d;

   public FontResourceManager(TextureManager var1, boolean var2) {
      this.field_211511_c = ☃;
      this.field_211826_d = ☃;
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      Gson ☃ = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
      Map<ResourceLocation, List<IGlyphProvider>> ☃x = Maps.newHashMap();

      for(ResourceLocation ☃xx : ☃.func_199003_a("font", var0 -> var0.endsWith(".json"))) {
         String ☃xxx = ☃xx.func_110623_a();
         ResourceLocation ☃xxxx = new ResourceLocation(☃xx.func_110624_b(), ☃xxx.substring("font/".length(), ☃xxx.length() - ".json".length()));
         List<IGlyphProvider> ☃xxxxx = (List)☃x.computeIfAbsent(☃xxxx, var0 -> Lists.<IGlyphProvider>newArrayList(new DefaultGlyphProvider()));

         try {
            for(IResource ☃xxxxxx : ☃.func_199004_b(☃xx)) {
               try {
                  InputStream ☃xxxxxxx = ☃xxxxxx.func_199027_b();
                  Throwable var12 = null;

                  try {
                     JsonArray ☃xxxxxxxx = JsonUtils.func_151214_t(
                        JsonUtils.func_188178_a(☃, IOUtils.toString(☃xxxxxxx, StandardCharsets.UTF_8), JsonObject.class), "providers"
                     );

                     for(int ☃xxxxxxxxx = ☃xxxxxxxx.size() - 1; ☃xxxxxxxxx >= 0; --☃xxxxxxxxx) {
                        JsonObject ☃xxxxxxxxxx = JsonUtils.func_151210_l(☃xxxxxxxx.get(☃xxxxxxxxx), "providers[" + ☃xxxxxxxxx + "]");

                        try {
                           GlyphProviderTypes ☃xxxxxxxxxxx = GlyphProviderTypes.func_211638_a(JsonUtils.func_151200_h(☃xxxxxxxxxx, "type"));
                           if (!this.field_211826_d || ☃xxxxxxxxxxx == GlyphProviderTypes.LEGACY_UNICODE || !☃xxxx.equals(Minecraft.field_211502_b)) {
                              IGlyphProvider ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx.func_211637_a(☃xxxxxxxxxx).func_211246_a(☃);
                              if (☃xxxxxxxxxxxx != null) {
                                 ☃xxxxx.add(☃xxxxxxxxxxxx);
                              }
                           }
                        } catch (RuntimeException var28) {
                           field_211509_a.warn(
                              "Unable to read definition '{}' in fonts.json in resourcepack: '{}': {}", ☃xxxx, ☃xxxxxx.func_199026_d(), var28.getMessage()
                           );
                        }
                     }
                  } catch (Throwable var29) {
                     var12 = var29;
                     throw var29;
                  } finally {
                     if (☃xxxxxxx != null) {
                        if (var12 != null) {
                           try {
                              ☃xxxxxxx.close();
                           } catch (Throwable var27) {
                              var12.addSuppressed(var27);
                           }
                        } else {
                           ☃xxxxxxx.close();
                        }
                     }
                  }
               } catch (RuntimeException var31) {
                  field_211509_a.warn("Unable to load font '{}' in fonts.json in resourcepack: '{}': {}", ☃xxxx, ☃xxxxxx.func_199026_d(), var31.getMessage());
               }
            }
         } catch (IOException var32) {
            field_211509_a.warn("Unable to load font '{}' in fonts.json: {}", ☃xxxx, var32.getMessage());
         }
      }

      Stream.concat(this.field_211510_b.keySet().stream(), ☃x.keySet().stream())
         .distinct()
         .forEach(
            var2x -> {
               List<IGlyphProvider> ☃ = (List)☃.getOrDefault(var2x, Collections.emptyList());
               Collections.reverse(☃);
               ((FontRenderer)this.field_211510_b.computeIfAbsent(var2x, var1x -> new FontRenderer(this.field_211511_c, new Font(this.field_211511_c, var1x))))
                  .func_211568_a(☃);
            }
         );
   }

   @Nullable
   public FontRenderer func_211504_a(ResourceLocation var1) {
      return (FontRenderer)this.field_211510_b.computeIfAbsent(☃, var1x -> {
         FontRenderer ☃ = new FontRenderer(this.field_211511_c, new Font(this.field_211511_c, var1x));
         ☃.func_211568_a(Lists.<IGlyphProvider>newArrayList(new DefaultGlyphProvider()));
         return ☃;
      });
   }

   public void func_211825_a(boolean var1) {
      if (☃ != this.field_211826_d) {
         this.field_211826_d = ☃;
         this.func_195410_a(Minecraft.func_71410_x().func_195551_G());
      }
   }
}
