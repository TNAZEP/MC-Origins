package net.minecraft.world.level.block.state.properties;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.StateHolder;

public abstract class Property<T extends Comparable<T>> {
   private final Class<T> clazz;
   private final String name;
   private Integer hashCode;
   private final Codec<T> codec = Codec.STRING
      .comapFlatMap(
         var1x -> (DataResult)this.getValue(var1x)
               .map(DataResult::success)
               .orElseGet(() -> DataResult.error("Unable to read property: " + this + " with value: " + var1x)),
         this::getName
      );
   private final Codec<Property.Value<T>> valueCodec = this.codec.xmap(this::value, Property.Value::value);

   protected Property(String var1, Class<T> var2) {
      this.clazz = â˜ƒ;
      this.name = â˜ƒ;
   }

   public Property.Value<T> value(T var1) {
      return new Property.Value<>(this, â˜ƒ);
   }

   public Property.Value<T> value(StateHolder<?, ?> var1) {
      return new Property.Value<>(this, â˜ƒ.getValue(this));
   }

   public Stream<Property.Value<T>> getAllValues() {
      return this.getPossibleValues().stream().map(this::value);
   }

   public Codec<T> codec() {
      return this.codec;
   }

   public Codec<Property.Value<T>> valueCodec() {
      return this.valueCodec;
   }

   public String getName() {
      return this.name;
   }

   public Class<T> getValueClass() {
      return this.clazz;
   }

   public abstract Collection<T> getPossibleValues();

   public abstract String getName(T var1);

   public abstract Optional<T> getValue(String var1);

   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.name).add("clazz", this.clazz).add("values", this.getPossibleValues()).toString();
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof Property)) {
         return false;
      } else {
         Property<?> â˜ƒ = (Property)â˜ƒ;
         return this.clazz.equals(â˜ƒ.clazz) && this.name.equals(â˜ƒ.name);
      }
   }

   public final int hashCode() {
      if (this.hashCode == null) {
         this.hashCode = this.generateHashCode();
      }

      return this.hashCode;
   }

   public int generateHashCode() {
      return 31 * this.clazz.hashCode() + this.name.hashCode();
   }

   public <U, S extends StateHolder<?, S>> DataResult<S> parseValue(DynamicOps<U> var1, S var2, U var3) {
      DataResult<T> â˜ƒ = this.codec.parse(â˜ƒ, â˜ƒ);
      return â˜ƒ.<S>map(var2x -> â˜ƒ.setValue(this, var2x)).setPartial(â˜ƒ);
   }

   public static final class Value<T extends Comparable<T>> {
      private final Property<T> property;
      private final T value;

      Value(Property<T> var1, T var2) {
         if (!â˜ƒ.getPossibleValues().contains(â˜ƒ)) {
            throw new IllegalArgumentException("Value " + â˜ƒ + " does not belong to property " + â˜ƒ);
         } else {
            this.property = â˜ƒ;
            this.value = â˜ƒ;
         }
      }

      public Property<T> getProperty() {
         return this.property;
      }

      public T value() {
         return this.value;
      }

      public String toString() {
         return this.property.getName() + "=" + this.property.getName(this.value);
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (!(â˜ƒ instanceof Property.Value)) {
            return false;
         } else {
            Property.Value<?> â˜ƒ = (Property.Value)â˜ƒ;
            return this.property == â˜ƒ.property && this.value.equals(â˜ƒ.value);
         }
      }

      public int hashCode() {
         int â˜ƒ = this.property.hashCode();
         return 31 * â˜ƒ + this.value.hashCode();
      }
   }
}
