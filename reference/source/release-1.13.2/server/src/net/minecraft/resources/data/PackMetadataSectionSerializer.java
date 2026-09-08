package net.minecraft.resources.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.ITextComponent;

public class PackMetadataSectionSerializer implements IMetadataSectionSerializer<PackMetadataSection> {
   public PackMetadataSection func_195812_a(JsonObject var1) {
      ITextComponent ☃ = ITextComponent.Serializer.func_197672_a(☃.get("description"));
      if (☃ == null) {
         throw new JsonParseException("Invalid/missing description!");
      } else {
         int ☃ = JsonUtils.func_151203_m(☃, "pack_format");
         return new PackMetadataSection(☃, ☃);
      }
   }

   @Override
   public String func_110483_a() {
      return "pack";
   }
}
