package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.multipart.MultiPart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class BlockModelDefinition {
   private final Map<String, MultiVariant> variants = Maps.newLinkedHashMap();
   private MultiPart multiPart;

   public static BlockModelDefinition fromStream(BlockModelDefinition.Context var0, Reader var1) {
      return GsonHelper.fromJson(â˜ƒ.gson, â˜ƒ, BlockModelDefinition.class);
   }

   public BlockModelDefinition(Map<String, MultiVariant> var1, MultiPart var2) {
      this.multiPart = â˜ƒ;
      this.variants.putAll(â˜ƒ);
   }

   public BlockModelDefinition(List<BlockModelDefinition> var1) {
      BlockModelDefinition â˜ƒ = null;

      for(BlockModelDefinition â˜ƒx : â˜ƒ) {
         if (â˜ƒx.isMultiPart()) {
            this.variants.clear();
            â˜ƒ = â˜ƒx;
         }

         this.variants.putAll(â˜ƒx.variants);
      }

      if (â˜ƒ != null) {
         this.multiPart = â˜ƒ.multiPart;
      }
   }

   @VisibleForTesting
   public boolean hasVariant(String var1) {
      return this.variants.get(â˜ƒ) != null;
   }

   @VisibleForTesting
   public MultiVariant getVariant(String var1) {
      MultiVariant â˜ƒ = (MultiVariant)this.variants.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw new BlockModelDefinition.MissingVariantException();
      } else {
         return â˜ƒ;
      }
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         if (â˜ƒ instanceof BlockModelDefinition â˜ƒ && this.variants.equals(â˜ƒ.variants)) {
            return this.isMultiPart() ? this.multiPart.equals(â˜ƒ.multiPart) : !â˜ƒ.isMultiPart();
         }

         return false;
      }
   }

   public int hashCode() {
      return 31 * this.variants.hashCode() + (this.isMultiPart() ? this.multiPart.hashCode() : 0);
   }

   public Map<String, MultiVariant> getVariants() {
      return this.variants;
   }

   @VisibleForTesting
   public Set<MultiVariant> getMultiVariants() {
      Set<MultiVariant> â˜ƒ = Sets.<MultiVariant>newHashSet(this.variants.values());
      if (this.isMultiPart()) {
         â˜ƒ.addAll(this.multiPart.getMultiVariants());
      }

      return â˜ƒ;
   }

   public boolean isMultiPart() {
      return this.multiPart != null;
   }

   public MultiPart getMultiPart() {
      return this.multiPart;
   }

   public static final class Context {
      protected final Gson gson = new GsonBuilder()
         .registerTypeAdapter(BlockModelDefinition.class, new BlockModelDefinition.Deserializer())
         .registerTypeAdapter(Variant.class, new Variant.Deserializer())
         .registerTypeAdapter(MultiVariant.class, new MultiVariant.Deserializer())
         .registerTypeAdapter(MultiPart.class, new MultiPart.Deserializer(this))
         .registerTypeAdapter(Selector.class, new Selector.Deserializer())
         .create();
      private StateDefinition<Block, BlockState> definition;

      public StateDefinition<Block, BlockState> getDefinition() {
         return this.definition;
      }

      public void setDefinition(StateDefinition<Block, BlockState> var1) {
         this.definition = â˜ƒ;
      }
   }

   public static class Deserializer implements JsonDeserializer<BlockModelDefinition> {
      public BlockModelDefinition deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         Map<String, MultiVariant> â˜ƒx = this.getVariants(â˜ƒ, â˜ƒ);
         MultiPart â˜ƒxx = this.getMultiPart(â˜ƒ, â˜ƒ);
         if (!â˜ƒx.isEmpty() || â˜ƒxx != null && !â˜ƒxx.getMultiVariants().isEmpty()) {
            return new BlockModelDefinition(â˜ƒx, â˜ƒxx);
         } else {
            throw new JsonParseException("Neither 'variants' nor 'multipart' found");
         }
      }

      protected Map<String, MultiVariant> getVariants(JsonDeserializationContext var1, JsonObject var2) {
         Map<String, MultiVariant> â˜ƒ = Maps.newHashMap();
         if (â˜ƒ.has("variants")) {
            JsonObject â˜ƒx = GsonHelper.getAsJsonObject(â˜ƒ, "variants");

            for(Entry<String, JsonElement> â˜ƒxx : â˜ƒx.entrySet()) {
               â˜ƒ.put((String)â˜ƒxx.getKey(), (MultiVariant)â˜ƒ.deserialize((JsonElement)â˜ƒxx.getValue(), MultiVariant.class));
            }
         }

         return â˜ƒ;
      }

      @Nullable
      protected MultiPart getMultiPart(JsonDeserializationContext var1, JsonObject var2) {
         if (!â˜ƒ.has("multipart")) {
            return null;
         } else {
            JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "multipart");
            return â˜ƒ.deserialize(â˜ƒ, MultiPart.class);
         }
      }
   }

   protected class MissingVariantException extends RuntimeException {
   }
}
