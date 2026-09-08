package net.minecraft.state;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public abstract class AbstractStateHolder<O, S> implements IStateHolder<S> {
   private static final Function<Entry<IProperty<?>, Comparable<?>>, String> field_177233_b = new Function<Entry<IProperty<?>, Comparable<?>>, String>() {
      public String apply(@Nullable Entry<IProperty<?>, Comparable<?>> var1) {
         if (☃ == null) {
            return "<NULL>";
         } else {
            IProperty<?> ☃ = (IProperty)☃.getKey();
            return ☃.func_177701_a() + "=" + this.func_185886_a(☃, (Comparable<?>)☃.getValue());
         }
      }

      private <T extends Comparable<T>> String func_185886_a(IProperty<T> var1, Comparable<?> var2) {
         return ☃.func_177702_a((T)☃);
      }
   };
   protected final O field_206876_a;
   private final ImmutableMap<IProperty<?>, Comparable<?>> field_206877_c;
   private final int field_206878_d;
   private Table<IProperty<?>, Comparable<?>, S> field_206879_e;

   protected AbstractStateHolder(O var1, ImmutableMap<IProperty<?>, Comparable<?>> var2) {
      this.field_206876_a = ☃;
      this.field_206877_c = ☃;
      this.field_206878_d = ☃.hashCode();
   }

   @Override
   public <T extends Comparable<T>> S func_177231_a(IProperty<T> var1) {
      return this.func_206870_a(☃, func_177232_a(☃.func_177700_c(), this.func_177229_b(☃)));
   }

   protected static <T> T func_177232_a(Collection<T> var0, T var1) {
      Iterator<T> ☃ = ☃.iterator();

      while(☃.hasNext()) {
         if (☃.next().equals(☃)) {
            if (☃.hasNext()) {
               return (T)☃.next();
            }

            return (T)☃.iterator().next();
         }
      }

      return (T)☃.next();
   }

   public String toString() {
      StringBuilder ☃ = new StringBuilder();
      ☃.append(this.field_206876_a);
      if (!this.func_206871_b().isEmpty()) {
         ☃.append('[');
         ☃.append((String)this.func_206871_b().entrySet().stream().map(field_177233_b).collect(Collectors.joining(",")));
         ☃.append(']');
      }

      return ☃.toString();
   }

   @Override
   public Collection<IProperty<?>> func_206869_a() {
      return Collections.unmodifiableCollection(this.field_206877_c.keySet());
   }

   @Override
   public <T extends Comparable<T>> boolean func_196959_b(IProperty<T> var1) {
      return this.field_206877_c.containsKey(☃);
   }

   @Override
   public <T extends Comparable<T>> T func_177229_b(IProperty<T> var1) {
      Comparable<?> ☃ = (Comparable)this.field_206877_c.get(☃);
      if (☃ == null) {
         throw new IllegalArgumentException("Cannot get property " + ☃ + " as it does not exist in " + this.field_206876_a);
      } else {
         return (T)☃.func_177699_b().cast(☃);
      }
   }

   @Override
   public <T extends Comparable<T>, V extends T> S func_206870_a(IProperty<T> var1, V var2) {
      Comparable<?> ☃ = (Comparable)this.field_206877_c.get(☃);
      if (☃ == null) {
         throw new IllegalArgumentException("Cannot set property " + ☃ + " as it does not exist in " + this.field_206876_a);
      } else if (☃ == ☃) {
         return (S)this;
      } else {
         S ☃ = this.field_206879_e.get(☃, ☃);
         if (☃ == null) {
            throw new IllegalArgumentException("Cannot set property " + ☃ + " to " + ☃ + " on " + this.field_206876_a + ", it is not an allowed value");
         } else {
            return ☃;
         }
      }
   }

   public void func_206874_a(Map<Map<IProperty<?>, Comparable<?>>, S> var1) {
      if (this.field_206879_e != null) {
         throw new IllegalStateException();
      } else {
         Table<IProperty<?>, Comparable<?>, S> ☃ = HashBasedTable.create();

         for(Entry<IProperty<?>, Comparable<?>> ☃x : this.field_206877_c.entrySet()) {
            IProperty<?> ☃xx = (IProperty)☃x.getKey();

            for(Comparable<?> ☃xxx : ☃xx.func_177700_c()) {
               if (☃xxx != ☃x.getValue()) {
                  ☃.put(☃xx, ☃xxx, (S)☃.get(this.func_206875_b(☃xx, ☃xxx)));
               }
            }
         }

         this.field_206879_e = (Table<IProperty<?>, Comparable<?>, S>)(☃.isEmpty() ? ☃ : ArrayTable.create(☃));
      }
   }

   private Map<IProperty<?>, Comparable<?>> func_206875_b(IProperty<?> var1, Comparable<?> var2) {
      Map<IProperty<?>, Comparable<?>> ☃ = Maps.newHashMap(this.field_206877_c);
      ☃.put(☃, ☃);
      return ☃;
   }

   @Override
   public ImmutableMap<IProperty<?>, Comparable<?>> func_206871_b() {
      return this.field_206877_c;
   }

   public boolean equals(Object var1) {
      return this == ☃;
   }

   public int hashCode() {
      return this.field_206878_d;
   }
}
