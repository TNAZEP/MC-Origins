package net.minecraft.state;

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
import net.minecraft.util.IStringSerializable;

public class EnumProperty<T extends Enum<T> & IStringSerializable> extends AbstractProperty<T> {
   private final ImmutableSet<T> field_177711_a;
   private final Map<String, T> field_177710_b = Maps.newHashMap();

   protected EnumProperty(String var1, Class<T> var2, Collection<T> var3) {
      super(☃, ☃);
      this.field_177711_a = ImmutableSet.copyOf(☃);

      for(T ☃ : ☃) {
         String ☃x = ☃.func_176610_l();
         if (this.field_177710_b.containsKey(☃x)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + ☃x + "'");
         }

         this.field_177710_b.put(☃x, ☃);
      }
   }

   @Override
   public Collection<T> func_177700_c() {
      return this.field_177711_a;
   }

   @Override
   public Optional<T> func_185929_b(String var1) {
      return Optional.ofNullable(this.field_177710_b.get(☃));
   }

   public String func_177702_a(T var1) {
      return ☃.func_176610_l();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ instanceof EnumProperty && super.equals(☃)) {
         EnumProperty<?> ☃ = (EnumProperty)☃;
         return this.field_177711_a.equals(☃.field_177711_a) && this.field_177710_b.equals(☃.field_177710_b);
      } else {
         return false;
      }
   }

   @Override
   public int func_206906_c() {
      int ☃ = super.func_206906_c();
      ☃ = 31 * ☃ + this.field_177711_a.hashCode();
      return 31 * ☃ + this.field_177710_b.hashCode();
   }

   public static <T extends Enum<T> & IStringSerializable> EnumProperty<T> func_177709_a(String var0, Class<T> var1) {
      return func_177708_a(☃, ☃, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & IStringSerializable> EnumProperty<T> func_177708_a(String var0, Class<T> var1, Predicate<T> var2) {
      return func_177707_a(☃, ☃, (Collection<T>)Arrays.stream(☃.getEnumConstants()).filter(☃).collect(Collectors.toList()));
   }

   public static <T extends Enum<T> & IStringSerializable> EnumProperty<T> func_177706_a(String var0, Class<T> var1, T... var2) {
      return func_177707_a(☃, ☃, Lists.<T>newArrayList(☃));
   }

   public static <T extends Enum<T> & IStringSerializable> EnumProperty<T> func_177707_a(String var0, Class<T> var1, Collection<T> var2) {
      return new EnumProperty<>(☃, ☃, ☃);
   }
}
