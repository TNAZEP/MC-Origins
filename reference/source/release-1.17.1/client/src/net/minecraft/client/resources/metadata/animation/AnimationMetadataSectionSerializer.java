package net.minecraft.client.resources.metadata.animation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import javax.annotation.Nullable;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer implements MetadataSectionSerializer<AnimationMetadataSection> {
   public AnimationMetadataSection fromJson(JsonObject var1) {
      Builder<AnimationFrame> â˜ƒ = ImmutableList.builder();
      int â˜ƒx = GsonHelper.getAsInt(â˜ƒ, "frametime", 1);
      if (â˜ƒx != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)â˜ƒx, "Invalid default frame time");
      }

      if (â˜ƒ.has("frames")) {
         try {
            JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "frames");

            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
               JsonElement â˜ƒxx = â˜ƒ.get(â˜ƒx);
               AnimationFrame â˜ƒxxx = this.getFrame(â˜ƒx, â˜ƒxx);
               if (â˜ƒxxx != null) {
                  â˜ƒ.add(â˜ƒxxx);
               }
            }
         } catch (ClassCastException var8) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + â˜ƒ.get("frames"), var8);
         }
      }

      int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "width", -1);
      int â˜ƒx = GsonHelper.getAsInt(â˜ƒ, "height", -1);
      if (â˜ƒ != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)â˜ƒ, "Invalid width");
      }

      if (â˜ƒx != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)â˜ƒx, "Invalid height");
      }

      boolean â˜ƒ = GsonHelper.getAsBoolean(â˜ƒ, "interpolate", false);
      return new AnimationMetadataSection(â˜ƒ.build(), â˜ƒ, â˜ƒx, â˜ƒx, â˜ƒ);
   }

   @Nullable
   private AnimationFrame getFrame(int var1, JsonElement var2) {
      if (â˜ƒ.isJsonPrimitive()) {
         return new AnimationFrame(GsonHelper.convertToInt(â˜ƒ, "frames[" + â˜ƒ + "]"));
      } else if (â˜ƒ.isJsonObject()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "frames[" + â˜ƒ + "]");
         int â˜ƒx = GsonHelper.getAsInt(â˜ƒ, "time", -1);
         if (â˜ƒ.has("time")) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)â˜ƒx, "Invalid frame time");
         }

         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "index");
         Validate.inclusiveBetween(0L, 2147483647L, (long)â˜ƒ, "Invalid frame index");
         return new AnimationFrame(â˜ƒ, â˜ƒx);
      } else {
         return null;
      }
   }

   @Override
   public String getMetadataSectionName() {
      return "animation";
   }
}
