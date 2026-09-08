package net.minecraft.client.renderer.block.model.multipart;

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
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class Selector {
   private final Condition condition;
   private final MultiVariant variant;

   public Selector(Condition var1, MultiVariant var2) {
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("Missing condition for selector");
      } else if (â˜ƒ == null) {
         throw new IllegalArgumentException("Missing variant for selector");
      } else {
         this.condition = â˜ƒ;
         this.variant = â˜ƒ;
      }
   }

   public MultiVariant getVariant() {
      return this.variant;
   }

   public Predicate<BlockState> getPredicate(StateDefinition<Block, BlockState> var1) {
      return this.condition.getPredicate(â˜ƒ);
   }

   public boolean equals(Object var1) {
      return this == â˜ƒ;
   }

   public int hashCode() {
      return System.identityHashCode(this);
   }

   public static class Deserializer implements JsonDeserializer<Selector> {
      public Selector deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         return new Selector(this.getSelector(â˜ƒ), â˜ƒ.deserialize(â˜ƒ.get("apply"), MultiVariant.class));
      }

      private Condition getSelector(JsonObject var1) {
         return â˜ƒ.has("when") ? getCondition(GsonHelper.getAsJsonObject(â˜ƒ, "when")) : Condition.TRUE;
      }

      @VisibleForTesting
      static Condition getCondition(JsonObject var0) {
         Set<Entry<String, JsonElement>> â˜ƒ = â˜ƒ.entrySet();
         if (â˜ƒ.isEmpty()) {
            throw new JsonParseException("No elements found in selector");
         } else if (â˜ƒ.size() == 1) {
            if (â˜ƒ.has("OR")) {
               List<Condition> â˜ƒ = (List)Streams.stream(GsonHelper.getAsJsonArray(â˜ƒ, "OR"))
                  .map(var0x -> getCondition(var0x.getAsJsonObject()))
                  .collect(Collectors.toList());
               return new OrCondition(â˜ƒ);
            } else if (â˜ƒ.has("AND")) {
               List<Condition> â˜ƒ = (List)Streams.stream(GsonHelper.getAsJsonArray(â˜ƒ, "AND"))
                  .map(var0x -> getCondition(var0x.getAsJsonObject()))
                  .collect(Collectors.toList());
               return new AndCondition(â˜ƒ);
            } else {
               return getKeyValueCondition((Entry<String, JsonElement>)â˜ƒ.iterator().next());
            }
         } else {
            return new AndCondition((Iterable<? extends Condition>)â˜ƒ.stream().map(Selector.Deserializer::getKeyValueCondition).collect(Collectors.toList()));
         }
      }

      private static Condition getKeyValueCondition(Entry<String, JsonElement> var0) {
         return new KeyValueCondition((String)â˜ƒ.getKey(), ((JsonElement)â˜ƒ.getValue()).getAsString());
      }
   }
}
