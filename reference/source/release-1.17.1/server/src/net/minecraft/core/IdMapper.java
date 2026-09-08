package net.minecraft.core;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class IdMapper<T> implements IdMap<T> {
   public static final int DEFAULT = -1;
   private int nextId;
   private final IdentityHashMap<T, Integer> tToId;
   private final List<T> idToT;

   public IdMapper() {
      this(512);
   }

   public IdMapper(int var1) {
      this.idToT = Lists.<T>newArrayListWithExpectedSize(â˜ƒ);
      this.tToId = new IdentityHashMap(â˜ƒ);
   }

   public void addMapping(T var1, int var2) {
      this.tToId.put(â˜ƒ, â˜ƒ);

      while(this.idToT.size() <= â˜ƒ) {
         this.idToT.add(null);
      }

      this.idToT.set(â˜ƒ, â˜ƒ);
      if (this.nextId <= â˜ƒ) {
         this.nextId = â˜ƒ + 1;
      }
   }

   public void add(T var1) {
      this.addMapping(â˜ƒ, this.nextId);
   }

   @Override
   public int getId(T var1) {
      Integer â˜ƒ = (Integer)this.tToId.get(â˜ƒ);
      return â˜ƒ == null ? -1 : â˜ƒ;
   }

   @Nullable
   @Override
   public final T byId(int var1) {
      return (T)(â˜ƒ >= 0 && â˜ƒ < this.idToT.size() ? this.idToT.get(â˜ƒ) : null);
   }

   public Iterator<T> iterator() {
      return Iterators.filter(this.idToT.iterator(), Predicates.notNull());
   }

   public boolean contains(int var1) {
      return this.byId(â˜ƒ) != null;
   }

   public int size() {
      return this.tToId.size();
   }
}
