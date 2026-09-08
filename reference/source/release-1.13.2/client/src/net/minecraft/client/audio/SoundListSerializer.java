package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class SoundListSerializer implements JsonDeserializer<SoundList> {
   public SoundList deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject ☃ = JsonUtils.func_151210_l(☃, "entry");
      boolean ☃x = JsonUtils.func_151209_a(☃, "replace", false);
      String ☃xx = JsonUtils.func_151219_a(☃, "subtitle", null);
      List<Sound> ☃xxx = this.func_188733_a(☃);
      return new SoundList(☃xxx, ☃x, ☃xx);
   }

   private List<Sound> func_188733_a(JsonObject var1) {
      List<Sound> ☃ = Lists.<Sound>newArrayList();
      if (☃.has("sounds")) {
         JsonArray ☃x = JsonUtils.func_151214_t(☃, "sounds");

         for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
            JsonElement ☃xxx = ☃x.get(☃xx);
            if (JsonUtils.func_151211_a(☃xxx)) {
               String ☃xxxx = JsonUtils.func_151206_a(☃xxx, "sound");
               ☃.add(new Sound(☃xxxx, 1.0F, 1.0F, 1, Sound.Type.FILE, false, false, 16));
            } else {
               ☃.add(this.func_188734_b(JsonUtils.func_151210_l(☃xxx, "sound")));
            }
         }
      }

      return ☃;
   }

   private Sound func_188734_b(JsonObject var1) {
      String ☃ = JsonUtils.func_151200_h(☃, "name");
      Sound.Type ☃x = this.func_188732_a(☃, Sound.Type.FILE);
      float ☃xx = JsonUtils.func_151221_a(☃, "volume", 1.0F);
      Validate.isTrue(☃xx > 0.0F, "Invalid volume");
      float ☃xxx = JsonUtils.func_151221_a(☃, "pitch", 1.0F);
      Validate.isTrue(☃xxx > 0.0F, "Invalid pitch");
      int ☃xxxx = JsonUtils.func_151208_a(☃, "weight", 1);
      Validate.isTrue(☃xxxx > 0, "Invalid weight");
      boolean ☃xxxxx = JsonUtils.func_151209_a(☃, "preload", false);
      boolean ☃xxxxxx = JsonUtils.func_151209_a(☃, "stream", false);
      int ☃xxxxxxx = JsonUtils.func_151208_a(☃, "attenuation_distance", 16);
      return new Sound(☃, ☃xx, ☃xxx, ☃xxxx, ☃x, ☃xxxxxx, ☃xxxxx, ☃xxxxxxx);
   }

   private Sound.Type func_188732_a(JsonObject var1, Sound.Type var2) {
      Sound.Type ☃ = ☃;
      if (☃.has("type")) {
         ☃ = Sound.Type.func_188704_a(JsonUtils.func_151200_h(☃, "type"));
         Validate.notNull(☃, "Invalid type");
      }

      return ☃;
   }
}
