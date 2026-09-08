package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class VariantList implements IUnbakedModel {
   private final List<Variant> field_188115_a;

   public VariantList(List<Variant> var1) {
      this.field_188115_a = ☃;
   }

   public List<Variant> func_188114_a() {
      return this.field_188115_a;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ instanceof VariantList) {
         VariantList ☃ = (VariantList)☃;
         return this.field_188115_a.equals(☃.field_188115_a);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_188115_a.hashCode();
   }

   @Override
   public Collection<ResourceLocation> func_187965_e() {
      return (Collection<ResourceLocation>)this.func_188114_a().stream().map(Variant::func_188046_a).collect(Collectors.toSet());
   }

   @Override
   public Collection<ResourceLocation> func_209559_a(Function<ResourceLocation, IUnbakedModel> var1, Set<String> var2) {
      return (Collection<ResourceLocation>)this.func_188114_a()
         .stream()
         .map(Variant::func_188046_a)
         .distinct()
         .flatMap(var2x -> ((IUnbakedModel)☃.apply(var2x)).func_209559_a(☃, ☃).stream())
         .collect(Collectors.toSet());
   }

   @Nullable
   @Override
   public IBakedModel func_209558_a(
      Function<ResourceLocation, IUnbakedModel> var1, Function<ResourceLocation, TextureAtlasSprite> var2, ModelRotation var3, boolean var4
   ) {
      if (this.func_188114_a().isEmpty()) {
         return null;
      } else {
         WeightedBakedModel.Builder ☃ = new WeightedBakedModel.Builder();

         for(Variant ☃x : this.func_188114_a()) {
            IBakedModel ☃xx = ((IUnbakedModel)☃.apply(☃x.func_188046_a())).func_209558_a(☃, ☃, ☃x.func_188048_b(), ☃x.func_188049_c());
            ☃.func_177677_a(☃xx, ☃x.func_188047_d());
         }

         return ☃.func_209614_a();
      }
   }

   public static class Deserializer implements JsonDeserializer<VariantList> {
      public VariantList deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         List<Variant> ☃ = Lists.<Variant>newArrayList();
         if (☃.isJsonArray()) {
            JsonArray ☃x = ☃.getAsJsonArray();
            if (☃x.size() == 0) {
               throw new JsonParseException("Empty variant array");
            }

            for(JsonElement ☃x : ☃x) {
               ☃.add(☃.deserialize(☃x, Variant.class));
            }
         } else {
            ☃.add(☃.deserialize(☃, Variant.class));
         }

         return new VariantList(☃);
      }
   }
}
