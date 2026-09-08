package net.minecraft.world.level.block.state.properties;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.util.StringRepresentable;

public class EnumProperty<T extends Enum<T> & StringRepresentable> extends Property<T> {
   private final ImmutableSet<T> values;
   private final Map<String, T> names = Maps.newHashMap();

   protected EnumProperty(String var1, Class<T> var2, Collection<T> var3) {
      super(â˜ƒ, â˜ƒ);
      this.values = ImmutableSet.copyOf(â˜ƒ);

      for(T â˜ƒ : â˜ƒ) {
         String â˜ƒx = â˜ƒ.getSerializedName();
         if (this.names.containsKey(â˜ƒx)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + â˜ƒx + "'");
         }

         this.names.put(â˜ƒx, â˜ƒ);
      }
   }

   @Override
   public Collection<T> getPossibleValues() {
      return this.values;
   }

   @Override
   public Optional<T> getValue(String var1) {
      return Optional.ofNullable((Enum)this.names.get(â˜ƒ));
   }

   public String getName(T var1) {
      return â˜ƒ.getSerializedName();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ instanceof EnumProperty â˜ƒ && super.equals(â˜ƒ)) {
         return this.values.equals(â˜ƒ.values) && this.names.equals(â˜ƒ.names);
      } else {
         return false;
      }
   }

   @Override
   public int generateHashCode() {
      int â˜ƒ = super.generateHashCode();
      â˜ƒ = 31 * â˜ƒ + this.values.hashCode();
      return 31 * â˜ƒ + this.names.hashCode();
   }

   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String var0, Class<T> var1) {
      return create(â˜ƒ, â˜ƒ, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String var0, Class<T> var1, Predicate<T> var2) {
      return create(â˜ƒ, â˜ƒ, (Collection<T>)Arrays.stream((Enum[])â˜ƒ.getEnumConstants()).filter(â˜ƒ).collect(Collectors.toList()));
   }

   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String var0, Class<T> var1, T... var2) {
      return create(â˜ƒ, â˜ƒ, Lists.<T>newArrayList(â˜ƒ));
   }

   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String var0, Class<T> var1, Collection<T> var2) {
      return new EnumProperty<>(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
