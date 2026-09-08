package net.minecraft.client.renderer.model.multipart;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.model.VariantList;
import net.minecraft.state.StateContainer;
import net.minecraft.util.JsonUtils;

public class Selector {
   private final ICondition field_188167_a;
   private final VariantList field_188168_b;

   public Selector(ICondition var1, VariantList var2) {
      if (☃ == null) {
         throw new IllegalArgumentException("Missing condition for selector");
      } else if (☃ == null) {
         throw new IllegalArgumentException("Missing variant for selector");
      } else {
         this.field_188167_a = ☃;
         this.field_188168_b = ☃;
      }
   }

   public VariantList func_188165_a() {
      return this.field_188168_b;
   }

   public Predicate<IBlockState> func_188166_a(StateContainer<Block, IBlockState> var1) {
      return this.field_188167_a.getPredicate(☃);
   }

   public boolean equals(Object var1) {
      return this == ☃;
   }

   public int hashCode() {
      return System.identityHashCode(this);
   }

   public static class Deserializer implements JsonDeserializer<Selector> {
      public Selector deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         return new Selector(this.func_188159_b(☃), ☃.deserialize(☃.get("apply"), VariantList.class));
      }

      private ICondition func_188159_b(JsonObject var1) {
         return ☃.has("when") ? func_188158_a(JsonUtils.func_152754_s(☃, "when")) : ICondition.TRUE;
      }

      @VisibleForTesting
      static ICondition func_188158_a(JsonObject var0) {
         Set<Entry<String, JsonElement>> ☃ = ☃.entrySet();
         if (☃.isEmpty()) {
            throw new JsonParseException("No elements found in selector");
         } else if (☃.size() == 1) {
            if (☃.has("OR")) {
               List<ICondition> ☃ = (List)Streams.stream(JsonUtils.func_151214_t(☃, "OR"))
                  .map(var0x -> func_188158_a(var0x.getAsJsonObject()))
                  .collect(Collectors.toList());
               return new OrCondition(☃);
            } else if (☃.has("AND")) {
               List<ICondition> ☃ = (List)Streams.stream(JsonUtils.func_151214_t(☃, "AND"))
                  .map(var0x -> func_188158_a(var0x.getAsJsonObject()))
                  .collect(Collectors.toList());
               return new AndCondition(☃);
            } else {
               return func_188161_b((Entry<String, JsonElement>)☃.iterator().next());
            }
         } else {
            return new AndCondition((Iterable<? extends ICondition>)☃.stream().map(var0x -> func_188161_b(var0x)).collect(Collectors.toList()));
         }
      }

      private static ICondition func_188161_b(Entry<String, JsonElement> var0) {
         return new PropertyValueCondition((String)☃.getKey(), ((JsonElement)☃.getValue()).getAsString());
      }
   }
}
