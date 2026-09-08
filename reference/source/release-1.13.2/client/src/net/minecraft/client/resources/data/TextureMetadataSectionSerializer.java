package net.minecraft.client.resources.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JsonUtils;

public class TextureMetadataSectionSerializer implements IMetadataSectionSerializer<TextureMetadataSection> {
   public TextureMetadataSection func_195812_a(JsonObject var1) {
      boolean ☃ = JsonUtils.func_151209_a(☃, "blur", false);
      boolean ☃x = JsonUtils.func_151209_a(☃, "clamp", false);
      return new TextureMetadataSection(☃, ☃x);
   }

   @Override
   public String func_110483_a() {
      return "texture";
   }
}
