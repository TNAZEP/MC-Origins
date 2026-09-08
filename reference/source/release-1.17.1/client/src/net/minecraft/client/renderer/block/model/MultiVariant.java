package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.resources.ResourceLocation;

public class MultiVariant implements UnbakedModel {
   private final List<Variant> variants;

   public MultiVariant(List<Variant> var1) {
      this.variants = â˜ƒ;
   }

   public List<Variant> getVariants() {
      return this.variants;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof MultiVariant â˜ƒ ? this.variants.equals(â˜ƒ.variants) : false;
      }
   }

   public int hashCode() {
      return this.variants.hashCode();
   }

   @Override
   public Collection<ResourceLocation> getDependencies() {
      return (Collection<ResourceLocation>)this.getVariants().stream().map(Variant::getModelLocation).collect(Collectors.toSet());
   }

   @Override
   public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> var1, Set<Pair<String, String>> var2) {
      return (Collection<Material>)this.getVariants()
         .stream()
         .map(Variant::getModelLocation)
         .distinct()
         .flatMap(var2x -> ((UnbakedModel)â˜ƒ.apply(var2x)).getMaterials(â˜ƒ, â˜ƒ).stream())
         .collect(Collectors.toSet());
   }

   @Nullable
   @Override
   public BakedModel bake(ModelBakery var1, Function<Material, TextureAtlasSprite> var2, ModelState var3, ResourceLocation var4) {
      if (this.getVariants().isEmpty()) {
         return null;
      } else {
         WeightedBakedModel.Builder â˜ƒ = new WeightedBakedModel.Builder();

         for(Variant â˜ƒx : this.getVariants()) {
            BakedModel â˜ƒxx = â˜ƒ.bake(â˜ƒx.getModelLocation(), â˜ƒx);
            â˜ƒ.add(â˜ƒxx, â˜ƒx.getWeight());
         }

         return â˜ƒ.build();
      }
   }

   public static class Deserializer implements JsonDeserializer<MultiVariant> {
      public MultiVariant deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         List<Variant> â˜ƒ = Lists.<Variant>newArrayList();
         if (â˜ƒ.isJsonArray()) {
            JsonArray â˜ƒx = â˜ƒ.getAsJsonArray();
            if (â˜ƒx.size() == 0) {
               throw new JsonParseException("Empty variant array");
            }

            for(JsonElement â˜ƒx : â˜ƒx) {
               â˜ƒ.add((Variant)â˜ƒ.deserialize(â˜ƒx, Variant.class));
            }
         } else {
            â˜ƒ.add((Variant)â˜ƒ.deserialize(â˜ƒ, Variant.class));
         }

         return new MultiVariant(â˜ƒ);
      }
   }
}
