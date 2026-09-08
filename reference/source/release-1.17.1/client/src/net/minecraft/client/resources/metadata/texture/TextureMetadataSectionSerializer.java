package net.minecraft.client.resources.metadata.texture;

import com.google.gson.JsonObject;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;

public class TextureMetadataSectionSerializer implements MetadataSectionSerializer<TextureMetadataSection> {
   public TextureMetadataSection fromJson(JsonObject var1) {
      boolean â˜ƒ = GsonHelper.getAsBoolean(â˜ƒ, "blur", false);
      boolean â˜ƒx = GsonHelper.getAsBoolean(â˜ƒ, "clamp", false);
      return new TextureMetadataSection(â˜ƒ, â˜ƒx);
   }

   @Override
   public String getMetadataSectionName() {
      return "texture";
   }
}
