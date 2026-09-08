package net.minecraft.server.packs.metadata.pack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;

public class PackMetadataSectionSerializer implements MetadataSectionSerializer<PackMetadataSection> {
   public PackMetadataSection fromJson(JsonObject var1) {
      Component â˜ƒ = Component.Serializer.fromJson(â˜ƒ.get("description"));
      if (â˜ƒ == null) {
         throw new JsonParseException("Invalid/missing description!");
      } else {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "pack_format");
         return new PackMetadataSection(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public String getMetadataSectionName() {
      return "pack";
   }
}
