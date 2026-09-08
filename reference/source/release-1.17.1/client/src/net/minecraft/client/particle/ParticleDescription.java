package net.minecraft.client.particle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class ParticleDescription {
   @Nullable
   private final List<ResourceLocation> textures;

   private ParticleDescription(@Nullable List<ResourceLocation> var1) {
      this.textures = â˜ƒ;
   }

   @Nullable
   public List<ResourceLocation> getTextures() {
      return this.textures;
   }

   public static ParticleDescription fromJson(JsonObject var0) {
      JsonArray â˜ƒx = GsonHelper.getAsJsonArray(â˜ƒ, "textures", null);
      List<ResourceLocation> â˜ƒ;
      if (â˜ƒx != null) {
         â˜ƒ = (List)Streams.stream(â˜ƒx)
            .map(var0x -> GsonHelper.convertToString(var0x, "texture"))
            .map(ResourceLocation::new)
            .collect(ImmutableList.toImmutableList());
      } else {
         â˜ƒ = null;
      }

      return new ParticleDescription(â˜ƒ);
   }
}
