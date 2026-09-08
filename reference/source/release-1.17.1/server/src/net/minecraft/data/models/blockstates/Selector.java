package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.level.block.state.properties.Property;

public final class Selector {
   private static final Selector EMPTY = new Selector(ImmutableList.of());
   private static final Comparator<Property.Value<?>> COMPARE_BY_NAME = Comparator.comparing(var0 -> var0.getProperty().getName());
   private final List<Property.Value<?>> values;

   public Selector extend(Property.Value<?> var1) {
      return new Selector(ImmutableList.<Property.Value<?>>builder().addAll(this.values).add(â˜ƒ).build());
   }

   public Selector extend(Selector var1) {
      return new Selector(ImmutableList.<Property.Value<?>>builder().addAll(this.values).addAll(â˜ƒ.values).build());
   }

   private Selector(List<Property.Value<?>> var1) {
      this.values = â˜ƒ;
   }

   public static Selector empty() {
      return EMPTY;
   }

   public static Selector of(Property.Value<?>... var0) {
      return new Selector(ImmutableList.copyOf(â˜ƒ));
   }

   public boolean equals(Object var1) {
      return this == â˜ƒ || â˜ƒ instanceof Selector && this.values.equals(((Selector)â˜ƒ).values);
   }

   public int hashCode() {
      return this.values.hashCode();
   }

   public String getKey() {
      return (String)this.values.stream().sorted(COMPARE_BY_NAME).map(Property.Value::toString).collect(Collectors.joining(","));
   }

   public String toString() {
      return this.getKey();
   }
}
