package net.minecraft.world.level.block.state;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class StateHolder<O, S> {
   public static final String NAME_TAG = "Name";
   public static final String PROPERTIES_TAG = "Properties";
   private static final Function<Entry<Property<?>, Comparable<?>>, String> PROPERTY_ENTRY_TO_STRING_FUNCTION = new Function<Entry<Property<?>, Comparable<?>>, String>(
      
   ) {
      public String apply(@Nullable Entry<Property<?>, Comparable<?>> var1) {
         if (â˜ƒ == null) {
            return "<NULL>";
         } else {
            Property<?> â˜ƒ = (Property)â˜ƒ.getKey();
            return â˜ƒ.getName() + "=" + this.getName(â˜ƒ, (Comparable<?>)â˜ƒ.getValue());
         }
      }

      private <T extends Comparable<T>> String getName(Property<T> var1, Comparable<?> var2) {
         return â˜ƒ.getName((T)â˜ƒ);
      }
   };
   protected final O owner;
   private final ImmutableMap<Property<?>, Comparable<?>> values;
   private Table<Property<?>, Comparable<?>, S> neighbours;
   protected final MapCodec<S> propertiesCodec;

   protected StateHolder(O var1, ImmutableMap<Property<?>, Comparable<?>> var2, MapCodec<S> var3) {
      this.owner = â˜ƒ;
      this.values = â˜ƒ;
      this.propertiesCodec = â˜ƒ;
   }

   public <T extends Comparable<T>> S cycle(Property<T> var1) {
      return this.setValue(â˜ƒ, findNextInCollection(â˜ƒ.getPossibleValues(), this.getValue(â˜ƒ)));
   }

   protected static <T> T findNextInCollection(Collection<T> var0, T var1) {
      Iterator<T> â˜ƒ = â˜ƒ.iterator();

      while(â˜ƒ.hasNext()) {
         if (â˜ƒ.next().equals(â˜ƒ)) {
            if (â˜ƒ.hasNext()) {
               return (T)â˜ƒ.next();
            }

            return (T)â˜ƒ.iterator().next();
         }
      }

      return (T)â˜ƒ.next();
   }

   public String toString() {
      StringBuilder â˜ƒ = new StringBuilder();
      â˜ƒ.append(this.owner);
      if (!this.getValues().isEmpty()) {
         â˜ƒ.append('[');
         â˜ƒ.append((String)this.getValues().entrySet().stream().map(PROPERTY_ENTRY_TO_STRING_FUNCTION).collect(Collectors.joining(",")));
         â˜ƒ.append(']');
      }

      return â˜ƒ.toString();
   }

   public Collection<Property<?>> getProperties() {
      return Collections.unmodifiableCollection(this.values.keySet());
   }

   public <T extends Comparable<T>> boolean hasProperty(Property<T> var1) {
      return this.values.containsKey(â˜ƒ);
   }

   public <T extends Comparable<T>> T getValue(Property<T> var1) {
      Comparable<?> â˜ƒ = (Comparable)this.values.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("Cannot get property " + â˜ƒ + " as it does not exist in " + this.owner);
      } else {
         return (T)â˜ƒ.getValueClass().cast(â˜ƒ);
      }
   }

   public <T extends Comparable<T>> Optional<T> getOptionalValue(Property<T> var1) {
      Comparable<?> â˜ƒ = (Comparable)this.values.get(â˜ƒ);
      return â˜ƒ == null ? Optional.empty() : Optional.of((Comparable)â˜ƒ.getValueClass().cast(â˜ƒ));
   }

   public <T extends Comparable<T>, V extends T> S setValue(Property<T> var1, V var2) {
      Comparable<?> â˜ƒ = (Comparable)this.values.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("Cannot set property " + â˜ƒ + " as it does not exist in " + this.owner);
      } else if (â˜ƒ == â˜ƒ) {
         return (S)this;
      } else {
         S â˜ƒ = this.neighbours.get(â˜ƒ, â˜ƒ);
         if (â˜ƒ == null) {
            throw new IllegalArgumentException("Cannot set property " + â˜ƒ + " to " + â˜ƒ + " on " + this.owner + ", it is not an allowed value");
         } else {
            return â˜ƒ;
         }
      }
   }

   public void populateNeighbours(Map<Map<Property<?>, Comparable<?>>, S> var1) {
      if (this.neighbours != null) {
         throw new IllegalStateException();
      } else {
         Table<Property<?>, Comparable<?>, S> â˜ƒ = HashBasedTable.create();

         for(Entry<Property<?>, Comparable<?>> â˜ƒx : this.values.entrySet()) {
            Property<?> â˜ƒxx = (Property)â˜ƒx.getKey();

            for(Comparable<?> â˜ƒxxx : â˜ƒxx.getPossibleValues()) {
               if (â˜ƒxxx != â˜ƒx.getValue()) {
                  â˜ƒ.put(â˜ƒxx, â˜ƒxxx, (S)â˜ƒ.get(this.makeNeighbourValues(â˜ƒxx, â˜ƒxxx)));
               }
            }
         }

         this.neighbours = (Table<Property<?>, Comparable<?>, S>)(â˜ƒ.isEmpty() ? â˜ƒ : ArrayTable.create(â˜ƒ));
      }
   }

   private Map<Property<?>, Comparable<?>> makeNeighbourValues(Property<?> var1, Comparable<?> var2) {
      Map<Property<?>, Comparable<?>> â˜ƒ = Maps.newHashMap(this.values);
      â˜ƒ.put(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public ImmutableMap<Property<?>, Comparable<?>> getValues() {
      return this.values;
   }

   protected static <O, S extends StateHolder<O, S>> Codec<S> codec(Codec<O> var0, Function<O, S> var1) {
      return â˜ƒ.dispatch("Name", var0x -> var0x.owner, var1x -> {
         S â˜ƒ = (S)â˜ƒ.apply(var1x);
         return â˜ƒ.getValues().isEmpty() ? Codec.unit(â˜ƒ) : â˜ƒ.propertiesCodec.fieldOf("Properties").codec();
      });
   }
}
