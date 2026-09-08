package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer implements IMetadataSectionSerializer<AnimationMetadataSection> {
   public AnimationMetadataSection func_195812_a(JsonObject var1) {
      List<AnimationFrame> ☃ = Lists.<AnimationFrame>newArrayList();
      int ☃x = JsonUtils.func_151208_a(☃, "frametime", 1);
      if (☃x != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)☃x, "Invalid default frame time");
      }

      if (☃.has("frames")) {
         try {
            JsonArray ☃ = JsonUtils.func_151214_t(☃, "frames");

            for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
               JsonElement ☃xx = ☃.get(☃x);
               AnimationFrame ☃xxx = this.func_110492_a(☃x, ☃xx);
               if (☃xxx != null) {
                  ☃.add(☃xxx);
               }
            }
         } catch (ClassCastException var8) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + ☃.get("frames"), var8);
         }
      }

      int ☃ = JsonUtils.func_151208_a(☃, "width", -1);
      int ☃x = JsonUtils.func_151208_a(☃, "height", -1);
      if (☃ != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)☃, "Invalid width");
      }

      if (☃x != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)☃x, "Invalid height");
      }

      boolean ☃ = JsonUtils.func_151209_a(☃, "interpolate", false);
      return new AnimationMetadataSection(☃, ☃, ☃x, ☃x, ☃);
   }

   private AnimationFrame func_110492_a(int var1, JsonElement var2) {
      if (☃.isJsonPrimitive()) {
         return new AnimationFrame(JsonUtils.func_151215_f(☃, "frames[" + ☃ + "]"));
      } else if (☃.isJsonObject()) {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "frames[" + ☃ + "]");
         int ☃x = JsonUtils.func_151208_a(☃, "time", -1);
         if (☃.has("time")) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)☃x, "Invalid frame time");
         }

         int ☃ = JsonUtils.func_151203_m(☃, "index");
         Validate.inclusiveBetween(0L, 2147483647L, (long)☃, "Invalid frame index");
         return new AnimationFrame(☃, ☃x);
      } else {
         return null;
      }
   }

   @Override
   public String func_110483_a() {
      return "animation";
   }
}
