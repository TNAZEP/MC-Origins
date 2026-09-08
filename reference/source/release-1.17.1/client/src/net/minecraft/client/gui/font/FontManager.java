package net.minecraft.client.gui.font;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.font.GlyphProvider;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FontManager implements AutoCloseable {
   static final Logger LOGGER = LogManager.getLogger();
   private static final String FONTS_PATH = "fonts.json";
   public static final ResourceLocation MISSING_FONT = new ResourceLocation("minecraft", "missing");
   private final FontSet missingFontSet;
   final Map<ResourceLocation, FontSet> fontSets = Maps.<ResourceLocation, FontSet>newHashMap();
   final TextureManager textureManager;
   private Map<ResourceLocation, ResourceLocation> renames = ImmutableMap.of();
   private final PreparableReloadListener reloadListener = new SimplePreparableReloadListener<Map<ResourceLocation, List<GlyphProvider>>>() {
      protected Map<ResourceLocation, List<GlyphProvider>> prepare(ResourceManager var1, ProfilerFiller var2) {
         â˜ƒ.startTick();
         Gson â˜ƒ = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
         Map<ResourceLocation, List<GlyphProvider>> â˜ƒx = Maps.newHashMap();

         for(ResourceLocation â˜ƒxx : â˜ƒ.listResources("font", var0 -> var0.endsWith(".json"))) {
            String â˜ƒxxx = â˜ƒxx.getPath();
            ResourceLocation â˜ƒxxxx = new ResourceLocation(â˜ƒxx.getNamespace(), â˜ƒxxx.substring("font/".length(), â˜ƒxxx.length() - ".json".length()));
            List<GlyphProvider> â˜ƒxxxxx = (List)â˜ƒx.computeIfAbsent(â˜ƒxxxx, var0 -> Lists.<GlyphProvider>newArrayList(new AllMissingGlyphProvider()));
            â˜ƒ.push(â˜ƒxxxx::toString);

            try {
               for(Resource â˜ƒxxxxxx : â˜ƒ.getResources(â˜ƒxx)) {
                  â˜ƒ.push(â˜ƒxxxxxx::getSourceName);

                  try {
                     InputStream â˜ƒxxxxxxx = â˜ƒxxxxxx.getInputStream();

                     try {
                        Reader â˜ƒxxxxxxxx = new BufferedReader(new InputStreamReader(â˜ƒxxxxxxx, StandardCharsets.UTF_8));

                        try {
                           â˜ƒ.push("reading");
                           JsonArray â˜ƒxxxxxxxxx = GsonHelper.getAsJsonArray(GsonHelper.fromJson(â˜ƒ, â˜ƒxxxxxxxx, JsonObject.class), "providers");
                           â˜ƒ.popPush("parsing");

                           for(int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.size() - 1; â˜ƒxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxx) {
                              JsonObject â˜ƒxxxxxxxxxxx = GsonHelper.convertToJsonObject(â˜ƒxxxxxxxxx.get(â˜ƒxxxxxxxxxx), "providers[" + â˜ƒxxxxxxxxxx + "]");

                              try {
                                 String â˜ƒxxxxxxxxxxxx = GsonHelper.getAsString(â˜ƒxxxxxxxxxxx, "type");
                                 GlyphProviderBuilderType â˜ƒxxxxxxxxxxxxx = GlyphProviderBuilderType.byName(â˜ƒxxxxxxxxxxxx);
                                 â˜ƒ.push(â˜ƒxxxxxxxxxxxx);
                                 GlyphProvider â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.create(â˜ƒxxxxxxxxxxx).create(â˜ƒ);
                                 if (â˜ƒxxxxxxxxxxxxxx != null) {
                                    â˜ƒxxxxx.add(â˜ƒxxxxxxxxxxxxxx);
                                 }

                                 â˜ƒ.pop();
                              } catch (RuntimeException var22) {
                                 FontManager.LOGGER
                                    .warn(
                                       "Unable to read definition '{}' in {} in resourcepack: '{}': {}",
                                       â˜ƒxxxx,
                                       "fonts.json",
                                       â˜ƒxxxxxx.getSourceName(),
                                       var22.getMessage()
                                    );
                              }
                           }

                           â˜ƒ.pop();
                        } catch (Throwable var23) {
                           try {
                              â˜ƒxxxxxxxx.close();
                           } catch (Throwable var21) {
                              var23.addSuppressed(var21);
                           }

                           throw var23;
                        }

                        â˜ƒxxxxxxxx.close();
                     } catch (Throwable var24) {
                        if (â˜ƒxxxxxxx != null) {
                           try {
                              â˜ƒxxxxxxx.close();
                           } catch (Throwable var20) {
                              var24.addSuppressed(var20);
                           }
                        }

                        throw var24;
                     }

                     if (â˜ƒxxxxxxx != null) {
                        â˜ƒxxxxxxx.close();
                     }
                  } catch (RuntimeException var25) {
                     FontManager.LOGGER
                        .warn("Unable to load font '{}' in {} in resourcepack: '{}': {}", â˜ƒxxxx, "fonts.json", â˜ƒxxxxxx.getSourceName(), var25.getMessage());
                  }

                  â˜ƒ.pop();
               }
            } catch (IOException var26) {
               FontManager.LOGGER.warn("Unable to load font '{}' in {}: {}", â˜ƒxxxx, "fonts.json", var26.getMessage());
            }

            â˜ƒ.push("caching");
            IntSet â˜ƒxxxxxx = new IntOpenHashSet();

            for(GlyphProvider â˜ƒxxxxxxx : â˜ƒxxxxx) {
               â˜ƒxxxxxx.addAll(â˜ƒxxxxxxx.getSupportedGlyphs());
            }

            â˜ƒxxxxxx.forEach(var1x -> {
               if (var1x != 32) {
                  for(GlyphProvider â˜ƒ : Lists.reverse(â˜ƒ)) {
                     if (â˜ƒ.getGlyph(var1x) != null) {
                        break;
                     }
                  }
               }
            });
            â˜ƒ.pop();
            â˜ƒ.pop();
         }

         â˜ƒ.endTick();
         return â˜ƒx;
      }

      protected void apply(Map<ResourceLocation, List<GlyphProvider>> var1, ResourceManager var2, ProfilerFiller var3) {
         â˜ƒ.startTick();
         â˜ƒ.push("closing");
         FontManager.this.fontSets.values().forEach(FontSet::close);
         FontManager.this.fontSets.clear();
         â˜ƒ.popPush("reloading");
         â˜ƒ.forEach((var1x, var2x) -> {
            FontSet â˜ƒ = new FontSet(FontManager.this.textureManager, var1x);
            â˜ƒ.reload(Lists.reverse(var2x));
            FontManager.this.fontSets.put(var1x, â˜ƒ);
         });
         â˜ƒ.pop();
         â˜ƒ.endTick();
      }

      @Override
      public String getName() {
         return "FontManager";
      }
   };

   public FontManager(TextureManager var1) {
      this.textureManager = â˜ƒ;
      this.missingFontSet = Util.make(new FontSet(â˜ƒ, MISSING_FONT), var0 -> var0.reload(Lists.<GlyphProvider>newArrayList(new AllMissingGlyphProvider())));
   }

   public void setRenames(Map<ResourceLocation, ResourceLocation> var1) {
      this.renames = â˜ƒ;
   }

   public Font createFont() {
      return new Font(var1 -> (FontSet)this.fontSets.getOrDefault(this.renames.getOrDefault(var1, var1), this.missingFontSet));
   }

   public PreparableReloadListener getReloadListener() {
      return this.reloadListener;
   }

   public void close() {
      this.fontSets.values().forEach(FontSet::close);
      this.missingFontSet.close();
   }
}
