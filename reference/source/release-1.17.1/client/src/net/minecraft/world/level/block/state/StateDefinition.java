package net.minecraft.world.level.block.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.properties.Property;

public class StateDefinition<O, S extends StateHolder<O, S>> {
   static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
   private final O owner;
   private final ImmutableSortedMap<String, Property<?>> propertiesByName;
   private final ImmutableList<S> states;

   protected StateDefinition(Function<O, S> var1, O var2, StateDefinition.Factory<O, S> var3, Map<String, Property<?>> var4) {
      this.owner = â˜ƒ;
      this.propertiesByName = ImmutableSortedMap.copyOf(â˜ƒ);
      Supplier<S> â˜ƒ = () -> (StateHolder)â˜ƒ.apply(â˜ƒ);
      MapCodec<S> â˜ƒx = MapCodec.of(Encoder.empty(), Decoder.unit(â˜ƒ));

      for(Entry<String, Property<?>> â˜ƒxx : this.propertiesByName.entrySet()) {
         â˜ƒx = appendPropertyCodec(â˜ƒx, â˜ƒ, (String)â˜ƒxx.getKey(), (Property)â˜ƒxx.getValue());
      }

      MapCodec<S> â˜ƒxx = â˜ƒx;
      Map<Map<Property<?>, Comparable<?>>, S> â˜ƒxxx = Maps.newLinkedHashMap();
      List<S> â˜ƒxxxx = Lists.<S>newArrayList();
      Stream<List<Pair<Property<?>, Comparable<?>>>> â˜ƒxxxxx = Stream.of(Collections.emptyList());

      for(Property<?> â˜ƒxxxxxx : this.propertiesByName.values()) {
         â˜ƒxxxxx = â˜ƒxxxxx.flatMap(var1x -> â˜ƒ.getPossibleValues().stream().map(var2x -> {
               List<Pair<Property<?>, Comparable<?>>> â˜ƒ = Lists.<Pair<Property<?>, Comparable<?>>>newArrayList(var1x);
               â˜ƒ.add(Pair.of(â˜ƒ, (S)var2x));
               return â˜ƒ;
            }));
      }

      â˜ƒxxxxx.forEach(var5x -> {
         ImmutableMap<Property<?>, Comparable<?>> â˜ƒ = (ImmutableMap)var5x.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
         S â˜ƒx = â˜ƒ.create(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.put(â˜ƒ, â˜ƒx);
         â˜ƒ.add(â˜ƒx);
      });

      for(S â˜ƒxxxxxx : â˜ƒxxxx) {
         â˜ƒxxxxxx.populateNeighbours(â˜ƒxxx);
      }

      this.states = ImmutableList.copyOf(â˜ƒxxxx);
   }

   private static <S extends StateHolder<?, S>, T extends Comparable<T>> MapCodec<S> appendPropertyCodec(
      MapCodec<S> var0, Supplier<S> var1, String var2, Property<T> var3
   ) {
      return Codec.mapPair(â˜ƒ, â˜ƒ.valueCodec().fieldOf(â˜ƒ).setPartial(() -> â˜ƒ.value((StateHolder<?, ?>)â˜ƒ.get())))
         .xmap(
            var1x -> (StateHolder)((StateHolder)var1x.getFirst()).setValue(â˜ƒ, ((Property.Value)var1x.getSecond()).value()),
            var1x -> Pair.of(var1x, â˜ƒ.value(var1x))
         );
   }

   public ImmutableList<S> getPossibleStates() {
      return this.states;
   }

   public S any() {
      return (S)this.states.get(0);
   }

   public O getOwner() {
      return this.owner;
   }

   public Collection<Property<?>> getProperties() {
      return this.propertiesByName.values();
   }

   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("block", this.owner)
         .add("properties", this.propertiesByName.values().stream().map(Property::getName).collect(Collectors.toList()))
         .toString();
   }

   @Nullable
   public Property<?> getProperty(String var1) {
      return this.propertiesByName.get(â˜ƒ);
   }

   public static class Builder<O, S extends StateHolder<O, S>> {
      private final O owner;
      private final Map<String, Property<?>> properties = Maps.newHashMap();

      public Builder(O var1) {
         this.owner = â˜ƒ;
      }

      public StateDefinition.Builder<O, S> add(Property<?>... var1) {
         for(Property<?> â˜ƒ : â˜ƒ) {
            this.validateProperty(â˜ƒ);
            this.properties.put(â˜ƒ.getName(), â˜ƒ);
         }

         return this;
      }

      private <T extends Comparable<T>> void validateProperty(Property<T> var1) {
         String â˜ƒ = â˜ƒ.getName();
         if (!StateDefinition.NAME_PATTERN.matcher(â˜ƒ).matches()) {
            throw new IllegalArgumentException(this.owner + " has invalidly named property: " + â˜ƒ);
         } else {
            Collection<T> â˜ƒ = â˜ƒ.getPossibleValues();
            if (â˜ƒ.size() <= 1) {
               throw new IllegalArgumentException(this.owner + " attempted use property " + â˜ƒ + " with <= 1 possible values");
            } else {
               for(T â˜ƒ : â˜ƒ) {
                  String â˜ƒx = â˜ƒ.getName(â˜ƒ);
                  if (!StateDefinition.NAME_PATTERN.matcher(â˜ƒx).matches()) {
                     throw new IllegalArgumentException(this.owner + " has property: " + â˜ƒ + " with invalidly named value: " + â˜ƒx);
                  }
               }

               if (this.properties.containsKey(â˜ƒ)) {
                  throw new IllegalArgumentException(this.owner + " has duplicate property: " + â˜ƒ);
               }
            }
         }
      }

      public StateDefinition<O, S> create(Function<O, S> var1, StateDefinition.Factory<O, S> var2) {
         return new StateDefinition<>(â˜ƒ, this.owner, â˜ƒ, this.properties);
      }
   }

   public interface Factory<O, S> {
      S create(O var1, ImmutableMap<Property<?>, Comparable<?>> var2, MapCodec<S> var3);
   }
}
