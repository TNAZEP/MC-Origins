package net.minecraft.client.resources.sounds;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.Validate;

public class SoundEventRegistrationSerializer implements JsonDeserializer<SoundEventRegistration> {
   public SoundEventRegistration deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "entry");
      boolean â˜ƒx = GsonHelper.getAsBoolean(â˜ƒ, "replace", false);
      String â˜ƒxx = GsonHelper.getAsString(â˜ƒ, "subtitle", null);
      List<Sound> â˜ƒxxx = this.getSounds(â˜ƒ);
      return new SoundEventRegistration(â˜ƒxxx, â˜ƒx, â˜ƒxx);
   }

   private List<Sound> getSounds(JsonObject var1) {
      List<Sound> â˜ƒ = Lists.<Sound>newArrayList();
      if (â˜ƒ.has("sounds")) {
         JsonArray â˜ƒx = GsonHelper.getAsJsonArray(â˜ƒ, "sounds");

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            JsonElement â˜ƒxxx = â˜ƒx.get(â˜ƒxx);
            if (GsonHelper.isStringValue(â˜ƒxxx)) {
               String â˜ƒxxxx = GsonHelper.convertToString(â˜ƒxxx, "sound");
               â˜ƒ.add(new Sound(â˜ƒxxxx, 1.0F, 1.0F, 1, Sound.Type.FILE, false, false, 16));
            } else {
               â˜ƒ.add(this.getSound(GsonHelper.convertToJsonObject(â˜ƒxxx, "sound")));
            }
         }
      }

      return â˜ƒ;
   }

   private Sound getSound(JsonObject var1) {
      String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "name");
      Sound.Type â˜ƒx = this.getType(â˜ƒ, Sound.Type.FILE);
      float â˜ƒxx = GsonHelper.getAsFloat(â˜ƒ, "volume", 1.0F);
      Validate.isTrue(â˜ƒxx > 0.0F, "Invalid volume");
      float â˜ƒxxx = GsonHelper.getAsFloat(â˜ƒ, "pitch", 1.0F);
      Validate.isTrue(â˜ƒxxx > 0.0F, "Invalid pitch");
      int â˜ƒxxxx = GsonHelper.getAsInt(â˜ƒ, "weight", 1);
      Validate.isTrue(â˜ƒxxxx > 0, "Invalid weight");
      boolean â˜ƒxxxxx = GsonHelper.getAsBoolean(â˜ƒ, "preload", false);
      boolean â˜ƒxxxxxx = GsonHelper.getAsBoolean(â˜ƒ, "stream", false);
      int â˜ƒxxxxxxx = GsonHelper.getAsInt(â˜ƒ, "attenuation_distance", 16);
      return new Sound(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒx, â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx);
   }

   private Sound.Type getType(JsonObject var1, Sound.Type var2) {
      Sound.Type â˜ƒ = â˜ƒ;
      if (â˜ƒ.has("type")) {
         â˜ƒ = Sound.Type.getByName(GsonHelper.getAsString(â˜ƒ, "type"));
         Validate.notNull(â˜ƒ, "Invalid type");
      }

      return â˜ƒ;
   }
}
