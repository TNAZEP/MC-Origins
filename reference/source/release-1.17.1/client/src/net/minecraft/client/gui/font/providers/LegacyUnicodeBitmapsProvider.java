package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LegacyUnicodeBitmapsProvider implements GlyphProvider {
   static final Logger LOGGER = LogManager.getLogger();
   private static final int UNICODE_SHEETS = 256;
   private static final int CHARS_PER_SHEET = 256;
   private static final int TEXTURE_SIZE = 256;
   private final ResourceManager resourceManager;
   private final byte[] sizes;
   private final String texturePattern;
   private final Map<ResourceLocation, NativeImage> textures = Maps.<ResourceLocation, NativeImage>newHashMap();

   public LegacyUnicodeBitmapsProvider(ResourceManager var1, byte[] var2, String var3) {
      this.resourceManager = â˜ƒ;
      this.sizes = â˜ƒ;
      this.texturePattern = â˜ƒ;

      for(int â˜ƒ = 0; â˜ƒ < 256; ++â˜ƒ) {
         int â˜ƒx = â˜ƒ * 256;
         ResourceLocation â˜ƒxx = this.getSheetLocation(â˜ƒx);

         try {
            Resource â˜ƒxxx = this.resourceManager.getResource(â˜ƒxx);

            label90: {
               label89:
               try (NativeImage â˜ƒxxxx = NativeImage.read(NativeImage.Format.RGBA, â˜ƒxxx.getInputStream())) {
                  if (â˜ƒxxxx.getWidth() == 256 && â˜ƒxxxx.getHeight() == 256) {
                     int â˜ƒxxxxx = 0;

                     while(true) {
                        if (â˜ƒxxxxx >= 256) {
                           break label89;
                        }

                        byte â˜ƒxxxxxx = â˜ƒ[â˜ƒx + â˜ƒxxxxx];
                        if (â˜ƒxxxxxx != 0 && getLeft(â˜ƒxxxxxx) > getRight(â˜ƒxxxxxx)) {
                           â˜ƒ[â˜ƒx + â˜ƒxxxxx] = 0;
                        }

                        ++â˜ƒxxxxx;
                     }
                  }
                  break label90;
               } catch (Throwable var14) {
                  if (â˜ƒxxx != null) {
                     try {
                        â˜ƒxxx.close();
                     } catch (Throwable var11) {
                        var14.addSuppressed(var11);
                     }
                  }

                  throw var14;
               }

               if (â˜ƒxxx != null) {
                  â˜ƒxxx.close();
               }
               continue;
            }

            if (â˜ƒxxx != null) {
               â˜ƒxxx.close();
            }
         } catch (IOException var15) {
         }

         Arrays.fill(â˜ƒ, â˜ƒx, â˜ƒx + 256, (byte)0);
      }
   }

   @Override
   public void close() {
      this.textures.values().forEach(NativeImage::close);
   }

   private ResourceLocation getSheetLocation(int var1) {
      ResourceLocation â˜ƒ = new ResourceLocation(String.format(this.texturePattern, String.format("%02x", â˜ƒ / 256)));
      return new ResourceLocation(â˜ƒ.getNamespace(), "textures/" + â˜ƒ.getPath());
   }

   @Nullable
   @Override
   public RawGlyph getGlyph(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ <= 65535) {
         byte â˜ƒ = this.sizes[â˜ƒ];
         if (â˜ƒ != 0) {
            NativeImage â˜ƒx = (NativeImage)this.textures.computeIfAbsent(this.getSheetLocation(â˜ƒ), this::loadTexture);
            if (â˜ƒx != null) {
               int â˜ƒxx = getLeft(â˜ƒ);
               return new LegacyUnicodeBitmapsProvider.Glyph(â˜ƒ % 16 * 16 + â˜ƒxx, (â˜ƒ & 0xFF) / 16 * 16, getRight(â˜ƒ) - â˜ƒxx, 16, â˜ƒx);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public IntSet getSupportedGlyphs() {
      IntSet â˜ƒ = new IntOpenHashSet();

      for(int â˜ƒx = 0; â˜ƒx < 65535; ++â˜ƒx) {
         if (this.sizes[â˜ƒx] != 0) {
            â˜ƒ.add(â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   @Nullable
   private NativeImage loadTexture(ResourceLocation var1) {
      try {
         Resource â˜ƒ = this.resourceManager.getResource(â˜ƒ);

         NativeImage var3;
         try {
            var3 = NativeImage.read(NativeImage.Format.RGBA, â˜ƒ.getInputStream());
         } catch (Throwable var6) {
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }

         return var3;
      } catch (IOException var7) {
         LOGGER.error("Couldn't load texture {}", â˜ƒ, var7);
         return null;
      }
   }

   private static int getLeft(byte var0) {
      return â˜ƒ >> 4 & 15;
   }

   private static int getRight(byte var0) {
      return (â˜ƒ & 15) + 1;
   }

   public static class Builder implements GlyphProviderBuilder {
      private final ResourceLocation metadata;
      private final String texturePattern;

      public Builder(ResourceLocation var1, String var2) {
         this.metadata = â˜ƒ;
         this.texturePattern = â˜ƒ;
      }

      public static GlyphProviderBuilder fromJson(JsonObject var0) {
         return new LegacyUnicodeBitmapsProvider.Builder(new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "sizes")), getTemplate(â˜ƒ));
      }

      private static String getTemplate(JsonObject var0) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "template");

         try {
            String.format(â˜ƒ, "");
            return â˜ƒ;
         } catch (IllegalFormatException var3) {
            throw new JsonParseException("Invalid legacy unicode template supplied, expected single '%s': " + â˜ƒ);
         }
      }

      @Nullable
      @Override
      public GlyphProvider create(ResourceManager var1) {
         try {
            Resource â˜ƒ = Minecraft.getInstance().getResourceManager().getResource(this.metadata);

            LegacyUnicodeBitmapsProvider var4;
            try {
               byte[] â˜ƒx = new byte[65536];
               â˜ƒ.getInputStream().read(â˜ƒx);
               var4 = new LegacyUnicodeBitmapsProvider(â˜ƒ, â˜ƒx, this.texturePattern);
            } catch (Throwable var6) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }
               }

               throw var6;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }

            return var4;
         } catch (IOException var7) {
            LegacyUnicodeBitmapsProvider.LOGGER.error("Cannot load {}, unicode glyphs will not render correctly", this.metadata);
            return null;
         }
      }
   }

   static class Glyph implements RawGlyph {
      private final int width;
      private final int height;
      private final int sourceX;
      private final int sourceY;
      private final NativeImage source;

      Glyph(int var1, int var2, int var3, int var4, NativeImage var5) {
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.sourceX = â˜ƒ;
         this.sourceY = â˜ƒ;
         this.source = â˜ƒ;
      }

      @Override
      public float getOversample() {
         return 2.0F;
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
         return (float)(this.width / 2 + 1);
      }

      @Override
      public void upload(int var1, int var2) {
         this.source.upload(0, â˜ƒ, â˜ƒ, this.sourceX, this.sourceY, this.width, this.height, false, false);
      }

      @Override
      public boolean isColored() {
         return this.source.format().components() > 1;
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
