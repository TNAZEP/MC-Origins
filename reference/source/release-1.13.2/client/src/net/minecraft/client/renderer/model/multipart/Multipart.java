package net.minecraft.client.renderer.model.multipart;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBlockDefinition;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.model.MultipartBakedModel;
import net.minecraft.client.renderer.model.VariantList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;

public class Multipart implements IUnbakedModel {
   private final StateContainer<Block, IBlockState> field_188140_b;
   private final List<Selector> field_188139_a;

   public Multipart(StateContainer<Block, IBlockState> var1, List<Selector> var2) {
      this.field_188140_b = ☃;
      this.field_188139_a = ☃;
   }

   public List<Selector> func_188136_a() {
      return this.field_188139_a;
   }

   public Set<VariantList> func_188137_b() {
      Set<VariantList> ☃ = Sets.<VariantList>newHashSet();

      for(Selector ☃x : this.field_188139_a) {
         ☃.add(☃x.func_188165_a());
      }

      return ☃;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof Multipart)) {
         return false;
      } else {
         Multipart ☃ = (Multipart)☃;
         return Objects.equals(this.field_188140_b, ☃.field_188140_b) && Objects.equals(this.field_188139_a, ☃.field_188139_a);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.field_188140_b, this.field_188139_a});
   }

   @Override
   public Collection<ResourceLocation> func_187965_e() {
      return (Collection<ResourceLocation>)this.func_188136_a()
         .stream()
         .flatMap(var0 -> var0.func_188165_a().func_187965_e().stream())
         .collect(Collectors.toSet());
   }

   @Override
   public Collection<ResourceLocation> func_209559_a(Function<ResourceLocation, IUnbakedModel> var1, Set<String> var2) {
      return (Collection<ResourceLocation>)this.func_188136_a()
         .stream()
         .flatMap(var2x -> var2x.func_188165_a().func_209559_a(☃, ☃).stream())
         .collect(Collectors.toSet());
   }

   @Nullable
   @Override
   public IBakedModel func_209558_a(
      Function<ResourceLocation, IUnbakedModel> var1, Function<ResourceLocation, TextureAtlasSprite> var2, ModelRotation var3, boolean var4
   ) {
      MultipartBakedModel.Builder ☃ = new MultipartBakedModel.Builder();

      for(Selector ☃x : this.func_188136_a()) {
         IBakedModel ☃xx = ☃x.func_188165_a().func_209558_a(☃, ☃, ☃, ☃);
         if (☃xx != null) {
            ☃.func_188648_a(☃x.func_188166_a(this.field_188140_b), ☃xx);
         }
      }

      return ☃.func_188647_a();
   }

   public static class Deserializer implements JsonDeserializer<Multipart> {
      private final ModelBlockDefinition.ContainerHolder field_209584_a;

      public Deserializer(ModelBlockDefinition.ContainerHolder var1) {
         this.field_209584_a = ☃;
      }

      public Multipart deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         return new Multipart(this.field_209584_a.func_209574_a(), this.func_188133_a(☃, ☃.getAsJsonArray()));
      }

      private List<Selector> func_188133_a(JsonDeserializationContext var1, JsonArray var2) {
         List<Selector> ☃ = Lists.<Selector>newArrayList();

         for(JsonElement ☃x : ☃) {
            ☃.add(☃.deserialize(☃x, Selector.class));
         }

         return ☃;
      }
   }
}
