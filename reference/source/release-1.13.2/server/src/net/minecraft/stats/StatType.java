package net.minecraft.stats;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.util.registry.IRegistry;

public class StatType<T> implements Iterable<Stat<T>> {
   private final IRegistry<T> field_199082_a;
   private final Map<T, Stat<T>> field_199083_b = new IdentityHashMap();

   public StatType(IRegistry<T> var1) {
      this.field_199082_a = ☃;
   }

   public Stat<T> func_199077_a(T var1, IStatFormater var2) {
      return (Stat<T>)this.field_199083_b.computeIfAbsent(☃, var2x -> new Stat<>(this, (T)var2x, ☃));
   }

   public IRegistry<T> func_199080_a() {
      return this.field_199082_a;
   }

   public Iterator<Stat<T>> iterator() {
      return this.field_199083_b.values().iterator();
   }

   public Stat<T> func_199076_b(T var1) {
      return this.func_199077_a(☃, IStatFormater.DEFAULT);
   }
}
