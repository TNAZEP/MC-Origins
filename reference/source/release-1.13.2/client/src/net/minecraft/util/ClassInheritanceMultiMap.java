package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassInheritanceMultiMap<T> extends AbstractSet<T> {
   private static final Set<Class<?>> field_181158_a = Sets.newHashSet();
   private final Map<Class<?>, List<T>> field_180218_a = Maps.newHashMap();
   private final Set<Class<?>> field_180216_b = Sets.newIdentityHashSet();
   private final Class<T> field_180217_c;
   private final List<T> field_181745_e = Lists.<T>newArrayList();

   public ClassInheritanceMultiMap(Class<T> var1) {
      this.field_180217_c = ☃;
      this.field_180216_b.add(☃);
      this.field_180218_a.put(☃, this.field_181745_e);

      for(Class<?> ☃ : Lists.newArrayList(field_181158_a)) {
         this.func_180213_a(☃);
      }
   }

   protected void func_180213_a(Class<?> var1) {
      field_181158_a.add(☃);

      for(T ☃ : this.field_181745_e) {
         if (☃.isAssignableFrom(☃.getClass())) {
            this.func_181743_a(☃, ☃);
         }
      }

      this.field_180216_b.add(☃);
   }

   protected Class<?> func_181157_b(Class<?> var1) {
      if (this.field_180217_c.isAssignableFrom(☃)) {
         if (!this.field_180216_b.contains(☃)) {
            this.func_180213_a(☃);
         }

         return ☃;
      } else {
         throw new IllegalArgumentException("Don't know how to search for " + ☃);
      }
   }

   public boolean add(T var1) {
      for(Class<?> ☃ : this.field_180216_b) {
         if (☃.isAssignableFrom(☃.getClass())) {
            this.func_181743_a(☃, ☃);
         }
      }

      return true;
   }

   private void func_181743_a(T var1, Class<?> var2) {
      List<T> ☃ = (List)this.field_180218_a.get(☃);
      if (☃ == null) {
         this.field_180218_a.put(☃, Lists.<T>newArrayList(☃));
      } else {
         ☃.add(☃);
      }
   }

   public boolean remove(Object var1) {
      T ☃ = (T)☃;
      boolean ☃x = false;

      for(Class<?> ☃xx : this.field_180216_b) {
         if (☃xx.isAssignableFrom(☃.getClass())) {
            List<T> ☃xxx = (List)this.field_180218_a.get(☃xx);
            if (☃xxx != null && ☃xxx.remove(☃)) {
               ☃x = true;
            }
         }
      }

      return ☃x;
   }

   public boolean contains(Object var1) {
      return Iterators.contains(this.func_180215_b(☃.getClass()).iterator(), ☃);
   }

   public <S> Iterable<S> func_180215_b(Class<S> var1) {
      return () -> {
         List<T> ☃ = (List)this.field_180218_a.get(this.func_181157_b(☃));
         if (☃ == null) {
            return Collections.emptyIterator();
         } else {
            Iterator<T> ☃ = ☃.iterator();
            return Iterators.filter(☃, ☃);
         }
      };
   }

   public Iterator<T> iterator() {
      return (Iterator<T>)(this.field_181745_e.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.field_181745_e.iterator()));
   }

   public int size() {
      return this.field_181745_e.size();
   }
}
