package net.minecraft.data.models.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ModelTemplate {
   private final Optional<ResourceLocation> model;
   private final Set<TextureSlot> requiredSlots;
   private final Optional<String> suffix;

   public ModelTemplate(Optional<ResourceLocation> var1, Optional<String> var2, TextureSlot... var3) {
      this.model = â˜ƒ;
      this.suffix = â˜ƒ;
      this.requiredSlots = ImmutableSet.copyOf(â˜ƒ);
   }

   public ResourceLocation create(Block var1, TextureMapping var2, BiConsumer<ResourceLocation, Supplier<JsonElement>> var3) {
      return this.create(ModelLocationUtils.getModelLocation(â˜ƒ, (String)this.suffix.orElse("")), â˜ƒ, â˜ƒ);
   }

   public ResourceLocation createWithSuffix(Block var1, String var2, TextureMapping var3, BiConsumer<ResourceLocation, Supplier<JsonElement>> var4) {
      return this.create(ModelLocationUtils.getModelLocation(â˜ƒ, â˜ƒ + (String)this.suffix.orElse("")), â˜ƒ, â˜ƒ);
   }

   public ResourceLocation createWithOverride(Block var1, String var2, TextureMapping var3, BiConsumer<ResourceLocation, Supplier<JsonElement>> var4) {
      return this.create(ModelLocationUtils.getModelLocation(â˜ƒ, â˜ƒ), â˜ƒ, â˜ƒ);
   }

   public ResourceLocation create(ResourceLocation var1, TextureMapping var2, BiConsumer<ResourceLocation, Supplier<JsonElement>> var3) {
      Map<TextureSlot, ResourceLocation> â˜ƒ = this.createMap(â˜ƒ);
      â˜ƒ.accept(â˜ƒ, (Supplier)() -> {
         JsonObject â˜ƒ = new JsonObject();
         this.model.ifPresent(var1x -> â˜ƒ.addProperty("parent", var1x.toString()));
         if (!â˜ƒ.isEmpty()) {
            JsonObject â˜ƒx = new JsonObject();
            â˜ƒ.forEach((var1x, var2x) -> â˜ƒ.addProperty(var1x.getId(), var2x.toString()));
            â˜ƒ.add("textures", â˜ƒx);
         }

         return â˜ƒ;
      });
      return â˜ƒ;
   }

   private Map<TextureSlot, ResourceLocation> createMap(TextureMapping var1) {
      return (Map<TextureSlot, ResourceLocation>)Streams.concat(this.requiredSlots.stream(), â˜ƒ.getForced())
         .collect(ImmutableMap.toImmutableMap(Function.identity(), â˜ƒ::get));
   }
}
