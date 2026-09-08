package net.minecraft.client.renderer.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
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
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.model.multipart.Multipart;
import net.minecraft.client.renderer.model.multipart.Selector;
import net.minecraft.state.StateContainer;
import net.minecraft.util.JsonUtils;

public class ModelBlockDefinition {
   private final Map<String, VariantList> field_178332_b = Maps.newLinkedHashMap();
   private Multipart field_188005_c;

   public static ModelBlockDefinition func_209577_a(ModelBlockDefinition.ContainerHolder var0, Reader var1) {
      return JsonUtils.func_193839_a(☃.field_209575_a, ☃, ModelBlockDefinition.class);
   }

   public ModelBlockDefinition(Map<String, VariantList> var1, Multipart var2) {
      this.field_188005_c = ☃;
      this.field_178332_b.putAll(☃);
   }

   public ModelBlockDefinition(List<ModelBlockDefinition> var1) {
      ModelBlockDefinition ☃ = null;

      for(ModelBlockDefinition ☃x : ☃) {
         if (☃x.func_188002_b()) {
            this.field_178332_b.clear();
            ☃ = ☃x;
         }

         this.field_178332_b.putAll(☃x.field_178332_b);
      }

      if (☃ != null) {
         this.field_188005_c = ☃.field_188005_c;
      }
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         if (☃ instanceof ModelBlockDefinition) {
            ModelBlockDefinition ☃ = (ModelBlockDefinition)☃;
            if (this.field_178332_b.equals(☃.field_178332_b)) {
               return this.func_188002_b() ? this.field_188005_c.equals(☃.field_188005_c) : !☃.func_188002_b();
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return 31 * this.field_178332_b.hashCode() + (this.func_188002_b() ? this.field_188005_c.hashCode() : 0);
   }

   public Map<String, VariantList> func_209578_a() {
      return this.field_178332_b;
   }

   public boolean func_188002_b() {
      return this.field_188005_c != null;
   }

   public Multipart func_188001_c() {
      return this.field_188005_c;
   }

   public static final class ContainerHolder {
      @VisibleForTesting
      final Gson field_209575_a = new GsonBuilder()
         .registerTypeAdapter(ModelBlockDefinition.class, new ModelBlockDefinition.Deserializer())
         .registerTypeAdapter(Variant.class, new Variant.Deserializer())
         .registerTypeAdapter(VariantList.class, new VariantList.Deserializer())
         .registerTypeAdapter(Multipart.class, new Multipart.Deserializer(this))
         .registerTypeAdapter(Selector.class, new Selector.Deserializer())
         .create();
      private StateContainer<Block, IBlockState> field_209576_b;

      public StateContainer<Block, IBlockState> func_209574_a() {
         return this.field_209576_b;
      }

      public void func_209573_a(StateContainer<Block, IBlockState> var1) {
         this.field_209576_b = ☃;
      }
   }

   public static class Deserializer implements JsonDeserializer<ModelBlockDefinition> {
      public ModelBlockDefinition deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         Map<String, VariantList> ☃x = this.func_187999_a(☃, ☃);
         Multipart ☃xx = this.func_187998_b(☃, ☃);
         if (!☃x.isEmpty() || ☃xx != null && !☃xx.func_188137_b().isEmpty()) {
            return new ModelBlockDefinition(☃x, ☃xx);
         } else {
            throw new JsonParseException("Neither 'variants' nor 'multipart' found");
         }
      }

      protected Map<String, VariantList> func_187999_a(JsonDeserializationContext var1, JsonObject var2) {
         Map<String, VariantList> ☃ = Maps.newHashMap();
         if (☃.has("variants")) {
            JsonObject ☃x = JsonUtils.func_152754_s(☃, "variants");

            for(Entry<String, JsonElement> ☃xx : ☃x.entrySet()) {
               ☃.put(☃xx.getKey(), ☃.deserialize((JsonElement)☃xx.getValue(), VariantList.class));
            }
         }

         return ☃;
      }

      @Nullable
      protected Multipart func_187998_b(JsonDeserializationContext var1, JsonObject var2) {
         if (!☃.has("multipart")) {
            return null;
         } else {
            JsonArray ☃ = JsonUtils.func_151214_t(☃, "multipart");
            return ☃.deserialize(☃, Multipart.class);
         }
      }
   }
}
