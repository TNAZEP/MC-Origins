package net.minecraft.data.models.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class DelegatedModel implements Supplier<JsonElement> {
   private final ResourceLocation parent;

   public DelegatedModel(ResourceLocation var1) {
      this.parent = â˜ƒ;
   }

   public JsonElement get() {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.addProperty("parent", this.parent.toString());
      return â˜ƒ;
   }
}
