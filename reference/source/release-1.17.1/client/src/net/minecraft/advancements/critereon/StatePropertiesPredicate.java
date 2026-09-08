package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;

public class StatePropertiesPredicate {
   public static final StatePropertiesPredicate ANY = new StatePropertiesPredicate(ImmutableList.of());
   private final List<StatePropertiesPredicate.PropertyMatcher> properties;

   private static StatePropertiesPredicate.PropertyMatcher fromJson(String var0, JsonElement var1) {
      if (â˜ƒ.isJsonPrimitive()) {
         String â˜ƒ = â˜ƒ.getAsString();
         return new StatePropertiesPredicate.ExactPropertyMatcher(â˜ƒ, â˜ƒ);
      } else {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "value");
         String â˜ƒx = â˜ƒ.has("min") ? getStringOrNull(â˜ƒ.get("min")) : null;
         String â˜ƒxx = â˜ƒ.has("max") ? getStringOrNull(â˜ƒ.get("max")) : null;
         return (StatePropertiesPredicate.PropertyMatcher)(â˜ƒx != null && â˜ƒx.equals(â˜ƒxx)
            ? new StatePropertiesPredicate.ExactPropertyMatcher(â˜ƒ, â˜ƒx)
            : new StatePropertiesPredicate.RangedPropertyMatcher(â˜ƒ, â˜ƒx, â˜ƒxx));
      }
   }

   @Nullable
   private static String getStringOrNull(JsonElement var0) {
      return â˜ƒ.isJsonNull() ? null : â˜ƒ.getAsString();
   }

   StatePropertiesPredicate(List<StatePropertiesPredicate.PropertyMatcher> var1) {
      this.properties = ImmutableList.copyOf(â˜ƒ);
   }

   public <S extends StateHolder<?, S>> boolean matches(StateDefinition<?, S> var1, S var2) {
      for(StatePropertiesPredicate.PropertyMatcher â˜ƒ : this.properties) {
         if (!â˜ƒ.match(â˜ƒ, â˜ƒ)) {
            return false;
         }
      }

      return true;
   }

   public boolean matches(BlockState var1) {
      return this.matches(â˜ƒ.getBlock().getStateDefinition(), â˜ƒ);
   }

   public boolean matches(FluidState var1) {
      return this.matches(â˜ƒ.getType().getStateDefinition(), â˜ƒ);
   }

   public void checkState(StateDefinition<?, ?> var1, Consumer<String> var2) {
      this.properties.forEach(var2x -> var2x.checkState(â˜ƒ, â˜ƒ));
   }

   public static StatePropertiesPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "properties");
         List<StatePropertiesPredicate.PropertyMatcher> â˜ƒx = Lists.<StatePropertiesPredicate.PropertyMatcher>newArrayList();

         for(Entry<String, JsonElement> â˜ƒxx : â˜ƒ.entrySet()) {
            â˜ƒx.add(fromJson((String)â˜ƒxx.getKey(), (JsonElement)â˜ƒxx.getValue()));
         }

         return new StatePropertiesPredicate(â˜ƒx);
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (!this.properties.isEmpty()) {
            this.properties.forEach(var1x -> â˜ƒ.add(var1x.getName(), var1x.toJson()));
         }

         return â˜ƒ;
      }
   }

   public static class Builder {
      private final List<StatePropertiesPredicate.PropertyMatcher> matchers = Lists.<StatePropertiesPredicate.PropertyMatcher>newArrayList();

      private Builder() {
      }

      public static StatePropertiesPredicate.Builder properties() {
         return new StatePropertiesPredicate.Builder();
      }

      public StatePropertiesPredicate.Builder hasProperty(Property<?> var1, String var2) {
         this.matchers.add(new StatePropertiesPredicate.ExactPropertyMatcher(â˜ƒ.getName(), â˜ƒ));
         return this;
      }

      public StatePropertiesPredicate.Builder hasProperty(Property<Integer> var1, int var2) {
         return this.hasProperty(â˜ƒ, Integer.toString(â˜ƒ));
      }

      public StatePropertiesPredicate.Builder hasProperty(Property<Boolean> var1, boolean var2) {
         return this.hasProperty(â˜ƒ, Boolean.toString(â˜ƒ));
      }

      public <T extends Comparable<T> & StringRepresentable> StatePropertiesPredicate.Builder hasProperty(Property<T> var1, T var2) {
         return this.hasProperty(â˜ƒ, â˜ƒ.getSerializedName());
      }

      public StatePropertiesPredicate build() {
         return new StatePropertiesPredicate(this.matchers);
      }
   }

   static class ExactPropertyMatcher extends StatePropertiesPredicate.PropertyMatcher {
      private final String value;

      public ExactPropertyMatcher(String var1, String var2) {
         super(â˜ƒ);
         this.value = â˜ƒ;
      }

      @Override
      protected <T extends Comparable<T>> boolean match(StateHolder<?, ?> var1, Property<T> var2) {
         T â˜ƒ = â˜ƒ.getValue(â˜ƒ);
         Optional<T> â˜ƒx = â˜ƒ.getValue(this.value);
         return â˜ƒx.isPresent() && â˜ƒ.compareTo((Comparable)â˜ƒx.get()) == 0;
      }

      @Override
      public JsonElement toJson() {
         return new JsonPrimitive(this.value);
      }
   }

   abstract static class PropertyMatcher {
      private final String name;

      public PropertyMatcher(String var1) {
         this.name = â˜ƒ;
      }

      public <S extends StateHolder<?, S>> boolean match(StateDefinition<?, S> var1, S var2) {
         Property<?> â˜ƒ = â˜ƒ.getProperty(this.name);
         return â˜ƒ == null ? false : this.match(â˜ƒ, â˜ƒ);
      }

      protected abstract <T extends Comparable<T>> boolean match(StateHolder<?, ?> var1, Property<T> var2);

      public abstract JsonElement toJson();

      public String getName() {
         return this.name;
      }

      public void checkState(StateDefinition<?, ?> var1, Consumer<String> var2) {
         Property<?> â˜ƒ = â˜ƒ.getProperty(this.name);
         if (â˜ƒ == null) {
            â˜ƒ.accept(this.name);
         }
      }
   }

   static class RangedPropertyMatcher extends StatePropertiesPredicate.PropertyMatcher {
      @Nullable
      private final String minValue;
      @Nullable
      private final String maxValue;

      public RangedPropertyMatcher(String var1, @Nullable String var2, @Nullable String var3) {
         super(â˜ƒ);
         this.minValue = â˜ƒ;
         this.maxValue = â˜ƒ;
      }

      @Override
      protected <T extends Comparable<T>> boolean match(StateHolder<?, ?> var1, Property<T> var2) {
         T â˜ƒ = â˜ƒ.getValue(â˜ƒ);
         if (this.minValue != null) {
            Optional<T> â˜ƒx = â˜ƒ.getValue(this.minValue);
            if (!â˜ƒx.isPresent() || â˜ƒ.compareTo((Comparable)â˜ƒx.get()) < 0) {
               return false;
            }
         }

         if (this.maxValue != null) {
            Optional<T> â˜ƒ = â˜ƒ.getValue(this.maxValue);
            if (!â˜ƒ.isPresent() || â˜ƒ.compareTo((Comparable)â˜ƒ.get()) > 0) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonElement toJson() {
         JsonObject â˜ƒ = new JsonObject();
         if (this.minValue != null) {
            â˜ƒ.addProperty("min", this.minValue);
         }

         if (this.maxValue != null) {
            â˜ƒ.addProperty("max", this.maxValue);
         }

         return â˜ƒ;
      }
   }
}
