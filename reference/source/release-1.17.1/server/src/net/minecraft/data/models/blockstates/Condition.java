package net.minecraft.data.models.blockstates;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public interface Condition extends Supplier<JsonElement> {
   void validate(StateDefinition<?, ?> var1);

   static Condition.TerminalCondition condition() {
      return new Condition.TerminalCondition();
   }

   static Condition and(Condition... var0) {
      return new Condition.CompositeCondition(Condition.Operation.AND, Arrays.asList(â˜ƒ));
   }

   static Condition or(Condition... var0) {
      return new Condition.CompositeCondition(Condition.Operation.OR, Arrays.asList(â˜ƒ));
   }

   public static class CompositeCondition implements Condition {
      private final Condition.Operation operation;
      private final List<Condition> subconditions;

      CompositeCondition(Condition.Operation var1, List<Condition> var2) {
         this.operation = â˜ƒ;
         this.subconditions = â˜ƒ;
      }

      @Override
      public void validate(StateDefinition<?, ?> var1) {
         this.subconditions.forEach(var1x -> var1x.validate(â˜ƒ));
      }

      public JsonElement get() {
         JsonArray â˜ƒ = new JsonArray();
         this.subconditions.stream().map(Supplier::get).forEach(â˜ƒ::add);
         JsonObject â˜ƒx = new JsonObject();
         â˜ƒx.add(this.operation.id, â˜ƒ);
         return â˜ƒx;
      }
   }

   public static enum Operation {
      AND("AND"),
      OR("OR");

      final String id;

      private Operation(String var3) {
         this.id = â˜ƒ;
      }
   }

   public static class TerminalCondition implements Condition {
      private final Map<Property<?>, String> terms = Maps.newHashMap();

      private static <T extends Comparable<T>> String joinValues(Property<T> var0, Stream<T> var1) {
         return (String)â˜ƒ.map(â˜ƒ::getName).collect(Collectors.joining("|"));
      }

      private static <T extends Comparable<T>> String getTerm(Property<T> var0, T var1, T[] var2) {
         return joinValues(â˜ƒ, Stream.concat(Stream.of(â˜ƒ), Stream.of(â˜ƒ)));
      }

      private <T extends Comparable<T>> void putValue(Property<T> var1, String var2) {
         String â˜ƒ = (String)this.terms.put(â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            throw new IllegalStateException("Tried to replace " + â˜ƒ + " value from " + â˜ƒ + " to " + â˜ƒ);
         }
      }

      public final <T extends Comparable<T>> Condition.TerminalCondition term(Property<T> var1, T var2) {
         this.putValue(â˜ƒ, â˜ƒ.getName(â˜ƒ));
         return this;
      }

      @SafeVarargs
      public final <T extends Comparable<T>> Condition.TerminalCondition term(Property<T> var1, T var2, T... var3) {
         this.putValue(â˜ƒ, getTerm(â˜ƒ, â˜ƒ, â˜ƒ));
         return this;
      }

      public final <T extends Comparable<T>> Condition.TerminalCondition negatedTerm(Property<T> var1, T var2) {
         this.putValue(â˜ƒ, "!" + â˜ƒ.getName(â˜ƒ));
         return this;
      }

      @SafeVarargs
      public final <T extends Comparable<T>> Condition.TerminalCondition negatedTerm(Property<T> var1, T var2, T... var3) {
         this.putValue(â˜ƒ, "!" + getTerm(â˜ƒ, â˜ƒ, â˜ƒ));
         return this;
      }

      public JsonElement get() {
         JsonObject â˜ƒ = new JsonObject();
         this.terms.forEach((var1x, var2) -> â˜ƒ.addProperty(var1x.getName(), var2));
         return â˜ƒ;
      }

      @Override
      public void validate(StateDefinition<?, ?> var1) {
         List<Property<?>> â˜ƒ = (List)this.terms.keySet().stream().filter(var1x -> â˜ƒ.getProperty(var1x.getName()) != var1x).collect(Collectors.toList());
         if (!â˜ƒ.isEmpty()) {
            throw new IllegalStateException("Properties " + â˜ƒ + " are missing from " + â˜ƒ);
         }
      }
   }
}
