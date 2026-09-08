package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BitmapProvider implements GlyphProvider {
   static final Logger LOGGER = LogManager.getLogger();
   private final NativeImage image;
   private final Int2ObjectMap<BitmapProvider.Glyph> glyphs;

   BitmapProvider(NativeImage var1, Int2ObjectMap<BitmapProvider.Glyph> var2) {
      this.image = â˜ƒ;
      this.glyphs = â˜ƒ;
   }

   @Override
   public void close() {
      this.image.close();
   }

   @Nullable
   @Override
   public RawGlyph getGlyph(int var1) {
      return this.glyphs.get(â˜ƒ);
   }

   @Override
   public IntSet getSupportedGlyphs() {
      return IntSets.unmodifiable(this.glyphs.keySet());
   }

   public static class Builder implements GlyphProviderBuilder {
      private final ResourceLocation texture;
      private final List<int[]> chars;
      private final int height;
      private final int ascent;

      public Builder(ResourceLocation var1, int var2, int var3, List<int[]> var4) {
         this.texture = new ResourceLocation(â˜ƒ.getNamespace(), "textures/" + â˜ƒ.getPath());
         this.chars = â˜ƒ;
         this.height = â˜ƒ;
         this.ascent = â˜ƒ;
      }

      public static BitmapProvider.Builder fromJson(JsonObject var0) {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "height", 8);
         int â˜ƒx = GsonHelper.getAsInt(â˜ƒ, "ascent");
         if (â˜ƒx > â˜ƒ) {
            throw new JsonParseException("Ascent " + â˜ƒx + " higher than height " + â˜ƒ);
         } else {
            List<int[]> â˜ƒ = Lists.<int[]>newArrayList();
            JsonArray â˜ƒx = GsonHelper.getAsJsonArray(â˜ƒ, "chars");

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
               String â˜ƒxxx = GsonHelper.convertToString(â˜ƒx.get(â˜ƒxx), "chars[" + â˜ƒxx + "]");
               int[] â˜ƒxxxx = â˜ƒxxx.codePoints().toArray();
               if (â˜ƒxx > 0) {
                  int â˜ƒxxxxx = ((int[])â˜ƒ.get(0)).length;
                  if (â˜ƒxxxx.length != â˜ƒxxxxx) {
                     throw new JsonParseException(
                        "Elements of chars have to be the same length (found: " + â˜ƒxxxx.length + ", expected: " + â˜ƒxxxxx + "), pad with space or \\u0000"
                     );
                  }
               }

               â˜ƒ.add(â˜ƒxxxx);
            }

            if (!â˜ƒ.isEmpty() && ((int[])â˜ƒ.get(0)).length != 0) {
               return new BitmapProvider.Builder(new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "file")), â˜ƒ, â˜ƒx, â˜ƒ);
            } else {
               throw new JsonParseException("Expected to find data in chars, found none.");
            }
         }
      }

      @Nullable
      @Override
      public GlyphProvider create(ResourceManager var1) {
         try {
            Resource â˜ƒ = â˜ƒ.getResource(this.texture);

            BitmapProvider var22;
            try {
               NativeImage â˜ƒx = NativeImage.read(NativeImage.Format.RGBA, â˜ƒ.getInputStream());
               int â˜ƒxx = â˜ƒx.getWidth();
               int â˜ƒxxx = â˜ƒx.getHeight();
               int â˜ƒxxxx = â˜ƒxx / ((int[])this.chars.get(0)).length;
               int â˜ƒxxxxx = â˜ƒxxx / this.chars.size();
               float â˜ƒxxxxxx = (float)this.height / (float)â˜ƒxxxxx;
               Int2ObjectMap<BitmapProvider.Glyph> â˜ƒxxxxxxx = new Int2ObjectOpenHashMap<>();

               for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < this.chars.size(); ++â˜ƒxxxxxxxx) {
                  int â˜ƒxxxxxxxxx = 0;

                  for(int â˜ƒxxxxxxxxxx : (int[])this.chars.get(â˜ƒxxxxxxxx)) {
                     int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx++;
                     if (â˜ƒxxxxxxxxxx != 0 && â˜ƒxxxxxxxxxx != 32) {
                        int â˜ƒxxxxxxxxxxxx = this.getActualGlyphWidth(â˜ƒx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxx);
                        BitmapProvider.Glyph â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxx.put(
                           â˜ƒxxxxxxxxxx,
                           new BitmapProvider.Glyph(
                              â˜ƒxxxxxx,
                              â˜ƒx,
                              â˜ƒxxxxxxxxxxx * â˜ƒxxxx,
                              â˜ƒxxxxxxxx * â˜ƒxxxxx,
                              â˜ƒxxxx,
                              â˜ƒxxxxx,
                              (int)(0.5 + (double)((float)â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxx)) + 1,
                              this.ascent
                           )
                        );
                        if (â˜ƒxxxxxxxxxxxxx != null) {
                           BitmapProvider.LOGGER.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString(â˜ƒxxxxxxxxxx), this.texture);
                        }
                     }
                  }
               }

               var22 = new BitmapProvider(â˜ƒx, â˜ƒxxxxxxx);
            } catch (Throwable var20) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var19) {
                     var20.addSuppressed(var19);
                  }
               }

               throw var20;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }

            return var22;
         } catch (IOException var21) {
            throw new RuntimeException(var21.getMessage());
         }
      }

      private int getActualGlyphWidth(NativeImage var1, int var2, int var3, int var4, int var5) {
         int â˜ƒ;
         for(â˜ƒ = â˜ƒ - 1; â˜ƒ >= 0; --â˜ƒ) {
            int â˜ƒ = â˜ƒ * â˜ƒ + â˜ƒ;

            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
               int â˜ƒxx = â˜ƒ * â˜ƒ + â˜ƒx;
               if (â˜ƒ.getLuminanceOrAlpha(â˜ƒ, â˜ƒxx) != 0) {
                  return â˜ƒ + 1;
               }
            }
         }

         return â˜ƒ + 1;
      }
   }

   static final class Glyph implements RawGlyph {
      private final float scale;
      private final NativeImage image;
      private final int offsetX;
      private final int offsetY;
      private final int width;
      private final int height;
      private final int advance;
      private final int ascent;

      Glyph(float var1, NativeImage var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         this.scale = â˜ƒ;
         this.image = â˜ƒ;
         this.offsetX = â˜ƒ;
         this.offsetY = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.advance = â˜ƒ;
         this.ascent = â˜ƒ;
      }

      @Override
      public float getOversample() {
         return 1.0F / this.scale;
      }

      @Override
      public int getPixelWidth() {
         return this.width;
      }

      @Override
      public int getPixelHeight() {
         return this.height;
      }

      @Override
      public float getAdvance() {
         return (float)this.advance;
      }

      @Override
      public float getBearingY() {
         return RawGlyph.super.getBearingY() + 7.0F - (float)this.ascent;
      }

      @Override
      public void upload(int var1, int var2) {
         this.image.upload(0, â˜ƒ, â˜ƒ, this.offsetX, this.offsetY, this.width, this.height, false, false);
      }

      @Override
      public boolean isColored() {
         return this.image.format().components() > 1;
      }
   }
}
