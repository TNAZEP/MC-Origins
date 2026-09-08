package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ClassInstanceMultiMap<T> extends AbstractCollection<T> {
   private final Map<Class<?>, List<T>> byClass = Maps.newHashMap();
   private final Class<T> baseClass;
   private final List<T> allInstances = Lists.<T>newArrayList();

   public ClassInstanceMultiMap(Class<T> var1) {
      this.baseClass = â˜ƒ;
      this.byClass.put(â˜ƒ, this.allInstances);
   }

   public boolean add(T var1) {
      boolean â˜ƒ = false;

      for(Entry<Class<?>, List<T>> â˜ƒx : this.byClass.entrySet()) {
         if (((Class)â˜ƒx.getKey()).isInstance(â˜ƒ)) {
            â˜ƒ |= ((List)â˜ƒx.getValue()).add(â˜ƒ);
         }
      }

      return â˜ƒ;
   }

   public boolean remove(Object var1) {
      boolean â˜ƒ = false;

      for(Entry<Class<?>, List<T>> â˜ƒx : this.byClass.entrySet()) {
         if (((Class)â˜ƒx.getKey()).isInstance(â˜ƒ)) {
            List<T> â˜ƒxx = (List)â˜ƒx.getValue();
            â˜ƒ |= â˜ƒxx.remove(â˜ƒ);
         }
      }

      return â˜ƒ;
   }

   public boolean contains(Object var1) {
      return this.find(â˜ƒ.getClass()).contains(â˜ƒ);
   }

   public <S> Collection<S> find(Class<S> var1) {
      if (!this.baseClass.isAssignableFrom(â˜ƒ)) {
         throw new IllegalArgumentException("Don't know how to search for " + â˜ƒ);
      } else {
         List<? extends T> â˜ƒ = (List)this.byClass
            .computeIfAbsent(â˜ƒ, var1x -> (List)this.allInstances.stream().filter(var1x::isInstance).collect(Collectors.toList()));
         return Collections.unmodifiableCollection(â˜ƒ);
      }
   }

   public Iterator<T> iterator() {
      return (Iterator<T>)(this.allInstances.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.allInstances.iterator()));
   }

   public List<T> getAllInstances() {
      return ImmutableList.copyOf(this.allInstances);
   }

   public int size() {
      return this.allInstances.size();
   }
}
