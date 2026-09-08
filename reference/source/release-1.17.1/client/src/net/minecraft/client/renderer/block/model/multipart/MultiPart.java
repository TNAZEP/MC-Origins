package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class MultiPart implements UnbakedModel {
   private final StateDefinition<Block, BlockState> definition;
   private final List<Selector> selectors;

   public MultiPart(StateDefinition<Block, BlockState> var1, List<Selector> var2) {
      this.definition = â˜ƒ;
      this.selectors = â˜ƒ;
   }

   public List<Selector> getSelectors() {
      return this.selectors;
   }

   public Set<MultiVariant> getMultiVariants() {
      Set<MultiVariant> â˜ƒ = Sets.<MultiVariant>newHashSet();

      for(Selector â˜ƒx : this.selectors) {
         â˜ƒ.add(â˜ƒx.getVariant());
      }

      return â˜ƒ;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof MultiPart)) {
         return false;
      } else {
         MultiPart â˜ƒ = (MultiPart)â˜ƒ;
         return Objects.equals(this.definition, â˜ƒ.definition) && Objects.equals(this.selectors, â˜ƒ.selectors);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.definition, this.selectors});
   }

   @Override
   public Collection<ResourceLocation> getDependencies() {
      return (Collection<ResourceLocation>)this.getSelectors()
         .stream()
         .flatMap(var0 -> var0.getVariant().getDependencies().stream())
         .collect(Collectors.toSet());
   }

   @Override
   public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> var1, Set<Pair<String, String>> var2) {
      return (Collection<Material>)this.getSelectors()
         .stream()
         .flatMap(var2x -> var2x.getVariant().getMaterials(â˜ƒ, â˜ƒ).stream())
         .collect(Collectors.toSet());
   }

   @Nullable
   @Override
   public BakedModel bake(ModelBakery var1, Function<Material, TextureAtlasSprite> var2, ModelState var3, ResourceLocation var4) {
      MultiPartBakedModel.Builder â˜ƒ = new MultiPartBakedModel.Builder();

      for(Selector â˜ƒx : this.getSelectors()) {
         BakedModel â˜ƒxx = â˜ƒx.getVariant().bake(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒxx != null) {
            â˜ƒ.add(â˜ƒx.getPredicate(this.definition), â˜ƒxx);
         }
      }

      return â˜ƒ.build();
   }

   public static class Deserializer implements JsonDeserializer<MultiPart> {
      private final BlockModelDefinition.Context context;

      public Deserializer(BlockModelDefinition.Context var1) {
         this.context = â˜ƒ;
      }

      public MultiPart deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         return new MultiPart(this.context.getDefinition(), this.getSelectors(â˜ƒ, â˜ƒ.getAsJsonArray()));
      }

      private List<Selector> getSelectors(JsonDeserializationContext var1, JsonArray var2) {
         List<Selector> â˜ƒ = Lists.<Selector>newArrayList();

         for(JsonElement â˜ƒx : â˜ƒ) {
            â˜ƒ.add((Selector)â˜ƒ.deserialize(â˜ƒx, Selector.class));
         }

         return â˜ƒ;
      }
   }
}
