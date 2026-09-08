package net.minecraft.client.gui.font.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.TrueTypeGlyphProvider;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;

public class TrueTypeGlyphProviderBuilder implements GlyphProviderBuilder {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ResourceLocation location;
   private final float size;
   private final float oversample;
   private final float shiftX;
   private final float shiftY;
   private final String skip;

   public TrueTypeGlyphProviderBuilder(ResourceLocation var1, float var2, float var3, float var4, float var5, String var6) {
      this.location = â˜ƒ;
      this.size = â˜ƒ;
      this.oversample = â˜ƒ;
      this.shiftX = â˜ƒ;
      this.shiftY = â˜ƒ;
      this.skip = â˜ƒ;
   }

   public static GlyphProviderBuilder fromJson(JsonObject var0) {
      float â˜ƒ = 0.0F;
      float â˜ƒx = 0.0F;
      if (â˜ƒ.has("shift")) {
         JsonArray â˜ƒxx = â˜ƒ.getAsJsonArray("shift");
         if (â˜ƒxx.size() != 2) {
            throw new JsonParseException("Expected 2 elements in 'shift', found " + â˜ƒxx.size());
         }

         â˜ƒ = GsonHelper.convertToFloat(â˜ƒxx.get(0), "shift[0]");
         â˜ƒx = GsonHelper.convertToFloat(â˜ƒxx.get(1), "shift[1]");
      }

      StringBuilder â˜ƒ = new StringBuilder();
      if (â˜ƒ.has("skip")) {
         JsonElement â˜ƒx = â˜ƒ.get("skip");
         if (â˜ƒx.isJsonArray()) {
            JsonArray â˜ƒxx = GsonHelper.convertToJsonArray(â˜ƒx, "skip");

            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.size(); ++â˜ƒxxx) {
               â˜ƒ.append(GsonHelper.convertToString(â˜ƒxx.get(â˜ƒxxx), "skip[" + â˜ƒxxx + "]"));
            }
         } else {
            â˜ƒ.append(GsonHelper.convertToString(â˜ƒx, "skip"));
         }
      }

      return new TrueTypeGlyphProviderBuilder(
         new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "file")),
         GsonHelper.getAsFloat(â˜ƒ, "size", 11.0F),
         GsonHelper.getAsFloat(â˜ƒ, "oversample", 1.0F),
         â˜ƒ,
         â˜ƒx,
         â˜ƒ.toString()
      );
   }

   @Nullable
   @Override
   public GlyphProvider create(ResourceManager var1) {
      STBTTFontinfo â˜ƒ = null;
      ByteBuffer â˜ƒx = null;

      try {
         Resource â˜ƒxx = â˜ƒ.getResource(new ResourceLocation(this.location.getNamespace(), "font/" + this.location.getPath()));

         TrueTypeGlyphProvider var5;
         try {
            LOGGER.debug("Loading font {}", this.location);
            â˜ƒ = STBTTFontinfo.malloc();
            â˜ƒx = TextureUtil.readResource(â˜ƒxx.getInputStream());
            â˜ƒx.flip();
            LOGGER.debug("Reading font {}", this.location);
            if (!STBTruetype.stbtt_InitFont(â˜ƒ, â˜ƒx)) {
               throw new IOException("Invalid ttf");
            }

            var5 = new TrueTypeGlyphProvider(â˜ƒx, â˜ƒ, this.size, this.oversample, this.shiftX, this.shiftY, this.skip);
         } catch (Throwable var8) {
            if (â˜ƒxx != null) {
               try {
                  â˜ƒxx.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (â˜ƒxx != null) {
            â˜ƒxx.close();
         }

         return var5;
      } catch (Exception var9) {
         LOGGER.error("Couldn't load truetype font {}", this.location, var9);
         if (â˜ƒ != null) {
            â˜ƒ.free();
         }

         MemoryUtil.memFree(â˜ƒx);
         return null;
      }
   }
}
