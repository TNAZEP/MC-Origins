package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class PropertyDispatch {
   private final Map<Selector, List<Variant>> values = Maps.newHashMap();

   protected void putValue(Selector var1, List<Variant> var2) {
      List<Variant> â˜ƒ = (List)this.values.put(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         throw new IllegalStateException("Value " + â˜ƒ + " is already defined");
      }
   }

   Map<Selector, List<Variant>> getEntries() {
      this.verifyComplete();
      return ImmutableMap.copyOf(this.values);
   }

   private void verifyComplete() {
      List<Property<?>> â˜ƒ = this.getDefinedProperties();
      Stream<Selector> â˜ƒx = Stream.of(Selector.empty());

      for(Property<?> â˜ƒxx : â˜ƒ) {
         â˜ƒx = â˜ƒx.flatMap(var1x -> â˜ƒ.getAllValues().map(var1x::extend));
      }

      List<Selector> â˜ƒxx = (List)â˜ƒx.filter(var1x -> !this.values.containsKey(var1x)).collect(Collectors.toList());
      if (!â˜ƒxx.isEmpty()) {
         throw new IllegalStateException("Missing definition for properties: " + â˜ƒxx);
      }
   }

   abstract List<Property<?>> getDefinedProperties();

   public static <T1 extends Comparable<T1>> PropertyDispatch.C1<T1> property(Property<T1> var0) {
      return new PropertyDispatch.C1<>(â˜ƒ);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> PropertyDispatch.C2<T1, T2> properties(Property<T1> var0, Property<T2> var1) {
      return new PropertyDispatch.C2<>(â˜ƒ, â˜ƒ);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> PropertyDispatch.C3<T1, T2, T3> properties(
      Property<T1> var0, Property<T2> var1, Property<T3> var2
   ) {
      return new PropertyDispatch.C3<>(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> PropertyDispatch.C4<T1, T2, T3, T4> properties(
      Property<T1> var0, Property<T2> var1, Property<T3> var2, Property<T4> var3
   ) {
      return new PropertyDispatch.C4<>(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> PropertyDispatch.C5<T1, T2, T3, T4, T5> properties(
      Property<T1> var0, Property<T2> var1, Property<T3> var2, Property<T4> var3, Property<T5> var4
   ) {
      return new PropertyDispatch.C5<>(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static class C1<T1 extends Comparable<T1>> extends PropertyDispatch {
      private final Property<T1> property1;

      C1(Property<T1> var1) {
         this.property1 = â˜ƒ;
      }

      @Override
      public List<Property<?>> getDefinedProperties() {
         return ImmutableList.of(this.property1);
      }

      public PropertyDispatch.C1<T1> select(T1 var1, List<Variant> var2) {
         Selector â˜ƒ = Selector.of(this.property1.value(â˜ƒ));
         this.putValue(â˜ƒ, â˜ƒ);
         return this;
      }

      public PropertyDispatch.C1<T1> select(T1 var1, Variant var2) {
         return this.select(â˜ƒ, Collections.singletonList(â˜ƒ));
      }

      public PropertyDispatch generate(Function<T1, Variant> var1) {
         this.property1.getPossibleValues().forEach(var2 -> this.select((T1)var2, (Variant)â˜ƒ.apply(var2)));
         return this;
      }

      public PropertyDispatch generateList(Function<T1, List<Variant>> var1) {
         this.property1.getPossibleValues().forEach(var2 -> this.select((T1)var2, (List<Variant>)â˜ƒ.apply(var2)));
         return this;
      }
   }

   public static class C2<T1 extends Comparable<T1>, T2 extends Comparable<T2>> extends PropertyDispatch {
      private final Property<T1> property1;
      private final Property<T2> property2;

      C2(Property<T1> var1, Property<T2> var2) {
         this.property1 = â˜ƒ;
         this.property2 = â˜ƒ;
      }

      @Override
      public List<Property<?>> getDefinedProperties() {
         return ImmutableList.of(this.property1, this.property2);
      }

      public PropertyDispatch.C2<T1, T2> select(T1 var1, T2 var2, List<Variant> var3) {
         Selector â˜ƒ = Selector.of(this.property1.value(â˜ƒ), this.property2.value(â˜ƒ));
         this.putValue(â˜ƒ, â˜ƒ);
         return this;
      }

      public PropertyDispatch.C2<T1, T2> select(T1 var1, T2 var2, Variant var3) {
         return this.select(â˜ƒ, â˜ƒ, Collections.singletonList(â˜ƒ));
      }

      public PropertyDispatch generate(BiFunction<T1, T2, Variant> var1) {
         this.property1
            .getPossibleValues()
            .forEach(var2 -> this.property2.getPossibleValues().forEach(var3 -> this.select((T1)var2, (T2)var3, (Variant)â˜ƒ.apply(var2, var3))));
         return this;
      }

      public PropertyDispatch generateList(BiFunction<T1, T2, List<Variant>> var1) {
         this.property1
            .getPossibleValues()
            .forEach(var2 -> this.property2.getPossibleValues().forEach(var3 -> this.select((T1)var2, (T2)var3, (List<Variant>)â˜ƒ.apply(var2, var3))));
         return this;
      }
   }

   public static class C3<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> extends PropertyDispatch {
      private final Property<T1> property1;
      private final Property<T2> property2;
      private final Property<T3> property3;

      C3(Property<T1> var1, Property<T2> var2, Property<T3> var3) {
         this.property1 = â˜ƒ;
         this.property2 = â˜ƒ;
         this.property3 = â˜ƒ;
      }

      @Override
      public List<Property<?>> getDefinedProperties() {
         return ImmutableList.of(this.property1, this.property2, this.property3);
      }

      public PropertyDispatch.C3<T1, T2, T3> select(T1 var1, T2 var2, T3 var3, List<Variant> var4) {
         Selector â˜ƒ = Selector.of(this.property1.value(â˜ƒ), this.property2.value(â˜ƒ), this.property3.value(â˜ƒ));
         this.putValue(â˜ƒ, â˜ƒ);
         return this;
      }

      public PropertyDispatch.C3<T1, T2, T3> select(T1 var1, T2 var2, T3 var3, Variant var4) {
         return this.select(â˜ƒ, â˜ƒ, â˜ƒ, Collections.singletonList(â˜ƒ));
      }

      public PropertyDispatch generate(PropertyDispatch.TriFunction<T1, T2, T3, Variant> var1) {
         this.property1
            .getPossibleValues()
            .forEach(
               var2 -> this.property2
                     .getPossibleValues()
                     .forEach(
                        var3 -> this.property3.getPossibleValues().forEach(var4 -> this.select((T1)var2, (T2)var3, (T3)var4, â˜ƒ.apply(var2, var3, var4)))
                     )
            );
         return this;
      }

      public PropertyDispatch generateList(PropertyDispatch.TriFunction<T1, T2, T3, List<Variant>> var1) {
         this.property1
            .getPossibleValues()
            .forEach(
               var2 -> this.property2
                     .getPossibleValues()
                     .forEach(
                        var3 -> this.property3
                              .getPossibleValues()
                              .forEach(var4 -> this.select((T1)var2, (T2)var3, (T3)var4, (List<Variant>)â˜ƒ.apply(var2, var3, var4)))
                     )
            );
         return this;
      }
   }

   public static class C4<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> extends PropertyDispatch {
      private final Property<T1> property1;
      private final Property<T2> property2;
      private final Property<T3> property3;
      private final Property<T4> property4;

      C4(Property<T1> var1, Property<T2> var2, Property<T3> var3, Property<T4> var4) {
         this.property1 = â˜ƒ;
         this.property2 = â˜ƒ;
         this.property3 = â˜ƒ;
         this.property4 = â˜ƒ;
      }

      @Override
      public List<Property<?>> getDefinedProperties() {
         return ImmutableList.of(this.property1, this.property2, this.property3, this.property4);
      }

      public PropertyDispatch.C4<T1, T2, T3, T4> select(T1 var1, T2 var2, T3 var3, T4 var4, List<Variant> var5) {
         Selector â˜ƒ = Selector.of(this.property1.value(â˜ƒ), this.property2.value(â˜ƒ), this.property3.value(â˜ƒ), this.property4.value(â˜ƒ));
         this.putValue(â˜ƒ, â˜ƒ);
         return this;
      }

      public PropertyDispatch.C4<T1, T2, T3, T4> select(T1 var1, T2 var2, T3 var3, T4 var4, Variant var5) {
         return this.select(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Collections.singletonList(â˜ƒ));
      }

      public PropertyDispatch generate(PropertyDispatch.QuadFunction<T1, T2, T3, T4, Variant> var1) {
         this.property1
            .getPossibleValues()
            .forEach(
               var2 -> this.property2
                     .getPossibleValues()
                     .forEach(
                        var3 -> this.property3
                              .getPossibleValues()
                              .forEach(
                                 var4 -> this.property4
                                       .getPossibleValues()
                                       .forEach(var5 -> this.select((T1)var2, (T2)var3, (T3)var4, (T4)var5, â˜ƒ.apply(var2, var3, var4, var5)))
                              )
                     )
            );
         return this;
      }

      public PropertyDispatch generateList(PropertyDispatch.QuadFunction<T1, T2, T3, T4, List<Variant>> var1) {
         this.property1
            .getPossibleValues()
            .forEach(
               var2 -> this.property2
                     .getPossibleValues()
                     .forEach(
                        var3 -> this.property3
                              .getPossibleValues()
                              .forEach(
                                 var4 -> this.property4
                                       .getPossibleValues()
                                       .forEach(var5 -> this.select((T1)var2, (T2)var3, (T3)var4, (T4)var5, (List<Variant>)â˜ƒ.apply(var2, var3, var4, var5)))
                              )
                     )
            );
         return this;
      }
   }

   public static class C5<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>>
      extends PropertyDispatch {
      private final Property<T1> property1;
      private final Property<T2> property2;
      private final Property<T3> property3;
      private final Property<T4> property4;
      private final Property<T5> property5;

      C5(Property<T1> var1, Property<T2> var2, Property<T3> var3, Property<T4> var4, Property<T5> var5) {
         this.property1 = â˜ƒ;
         this.property2 = â˜ƒ;
         this.property3 = â˜ƒ;
         this.property4 = â˜ƒ;
         this.property5 = â˜ƒ;
      }

      @Override
      public List<Property<?>> getDefinedProperties() {
         return ImmutableList.of(this.property1, this.property2, this.property3, this.property4, this.property5);
      }

      public PropertyDispatch.C5<T1, T2, T3, T4, T5> select(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, List<Variant> var6) {
         Selector â˜ƒ = Selector.of(
            this.property1.value(â˜ƒ), this.property2.value(â˜ƒ), this.property3.value(â˜ƒ), this.property4.value(â˜ƒ), this.property5.value(â˜ƒ)
         );
         this.putValue(â˜ƒ, â˜ƒ);
         return this;
      }

      public PropertyDispatch.C5<T1, T2, T3, T4, T5> select(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, Variant var6) {
         return this.select(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Collections.singletonList(â˜ƒ));
      }

      public PropertyDispatch generate(PropertyDispatch.PentaFunction<T1, T2, T3, T4, T5, Variant> var1) {
         this.property1
            .getPossibleValues()
            .forEach(
               var2 -> this.property2
                     .getPossibleValues()
                     .forEach(
                        var3 -> this.property3
                              .getPossibleValues()
                              .forEach(
                                 var4 -> this.property4
                                       .getPossibleValues()
                                       .forEach(
                                          var5 -> this.property5
                                                .getPossibleValues()
                                                .forEach(
                                                   var6 -> this.select(
                                                         (T1)var2, (T2)var3, (T3)var4, (T4)var5, (T5)var6, â˜ƒ.apply(var2, var3, var4, var5, var6)
                                                      )
                                                )
                                       )
                              )
                     )
            );
         return this;
      }

      public PropertyDispatch generateList(PropertyDispatch.PentaFunction<T1, T2, T3, T4, T5, List<Variant>> var1) {
         this.property1
            .getPossibleValues()
            .forEach(
               var2 -> this.property2
                     .getPossibleValues()
                     .forEach(
                        var3 -> this.property3
                              .getPossibleValues()
                              .forEach(
                                 var4 -> this.property4
                                       .getPossibleValues()
                                       .forEach(
                                          var5 -> this.property5
                                                .getPossibleValues()
                                                .forEach(
                                                   var6 -> this.select(
                                                         (T1)var2,
                                                         (T2)var3,
                                                         (T3)var4,
                                                         (T4)var5,
                                                         (T5)var6,
                                                         (List<Variant>)â˜ƒ.apply(var2, var3, var4, var5, var6)
                                                      )
                                                )
                                       )
                              )
                     )
            );
         return this;
      }
   }

   @FunctionalInterface
   public interface PentaFunction<P1, P2, P3, P4, P5, R> {
      R apply(P1 var1, P2 var2, P3 var3, P4 var4, P5 var5);
   }

   @FunctionalInterface
   public interface QuadFunction<P1, P2, P3, P4, R> {
      R apply(P1 var1, P2 var2, P3 var3, P4 var4);
   }

   @FunctionalInterface
   public interface TriFunction<P1, P2, P3, R> {
      R apply(P1 var1, P2 var2, P3 var3);
   }
}
