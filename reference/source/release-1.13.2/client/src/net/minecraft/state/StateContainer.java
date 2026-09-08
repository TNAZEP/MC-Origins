package net.minecraft.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.MapPopulator;

public class StateContainer<O, S extends IStateHolder<S>> {
   private static final Pattern field_185921_a = Pattern.compile("^[a-z0-9_]+$");
   private final O field_177627_c;
   private final ImmutableSortedMap<String, IProperty<?>> field_177624_d;
   private final ImmutableList<S> field_177625_e;

   protected <A extends AbstractStateHolder<O, S>> StateContainer(O var1, StateContainer.IFactory<O, S, A> var2, Map<String, IProperty<?>> var3) {
      this.field_177627_c = ☃;
      this.field_177624_d = ImmutableSortedMap.copyOf(☃);
      Map<Map<IProperty<?>, Comparable<?>>, A> ☃ = Maps.newLinkedHashMap();
      List<A> ☃x = Lists.<A>newArrayList();
      Stream<List<Comparable<?>>> ☃xx = Stream.of(Collections.emptyList());

      for(IProperty<?> ☃xxx : this.field_177624_d.values()) {
         ☃xx = ☃xx.flatMap(var1x -> ☃.func_177700_c().stream().map(var1xx -> {
               List<Comparable<?>> ☃ = Lists.newArrayList(var1x);
               ☃.add(var1xx);
               return ☃;
            }));
      }

      ☃xx.forEach(var5x -> {
         Map<IProperty<?>, Comparable<?>> ☃ = MapPopulator.func_179400_b(this.field_177624_d.values(), var5x);
         A ☃x = ☃.create(☃, ImmutableMap.copyOf(☃));
         ☃.put(☃, ☃x);
         ☃.add(☃x);
      });

      for(A ☃xxx : ☃x) {
         ☃xxx.func_206874_a(☃);
      }

      this.field_177625_e = ImmutableList.copyOf(☃x);
   }

   public ImmutableList<S> func_177619_a() {
      return this.field_177625_e;
   }

   public S func_177621_b() {
      return (S)this.field_177625_e.get(0);
   }

   public O func_177622_c() {
      return this.field_177627_c;
   }

   public Collection<IProperty<?>> func_177623_d() {
      return this.field_177624_d.values();
   }

   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("block", this.field_177627_c)
         .add("properties", this.field_177624_d.values().stream().map(IProperty::func_177701_a).collect(Collectors.toList()))
         .toString();
   }

   @Nullable
   public IProperty<?> func_185920_a(String var1) {
      return this.field_177624_d.get(☃);
   }

   public static class Builder<O, S extends IStateHolder<S>> {
      private final O field_206895_a;
      private final Map<String, IProperty<?>> field_206896_b = Maps.newHashMap();

      public Builder(O var1) {
         this.field_206895_a = ☃;
      }

      public StateContainer.Builder<O, S> func_206894_a(IProperty<?>... var1) {
         for(IProperty<?> ☃ : ☃) {
            this.func_206892_a(☃);
            this.field_206896_b.put(☃.func_177701_a(), ☃);
         }

         return this;
      }

      private <T extends Comparable<T>> void func_206892_a(IProperty<T> var1) {
         String ☃ = ☃.func_177701_a();
         if (!StateContainer.field_185921_a.matcher(☃).matches()) {
            throw new IllegalArgumentException(this.field_206895_a + " has invalidly named property: " + ☃);
         } else {
            Collection<T> ☃ = ☃.func_177700_c();
            if (☃.size() <= 1) {
               throw new IllegalArgumentException(this.field_206895_a + " attempted use property " + ☃ + " with <= 1 possible values");
            } else {
               for(T ☃ : ☃) {
                  String ☃x = ☃.func_177702_a(☃);
                  if (!StateContainer.field_185921_a.matcher(☃x).matches()) {
                     throw new IllegalArgumentException(this.field_206895_a + " has property: " + ☃ + " with invalidly named value: " + ☃x);
                  }
               }

               if (this.field_206896_b.containsKey(☃)) {
                  throw new IllegalArgumentException(this.field_206895_a + " has duplicate property: " + ☃);
               }
            }
         }
      }

      public <A extends AbstractStateHolder<O, S>> StateContainer<O, S> func_206893_a(StateContainer.IFactory<O, S, A> var1) {
         return new StateContainer<>(this.field_206895_a, ☃, this.field_206896_b);
      }
   }

   public interface IFactory<O, S extends IStateHolder<S>, A extends AbstractStateHolder<O, S>> {
      A create(O var1, ImmutableMap<IProperty<?>, Comparable<?>> var2);
   }
}
