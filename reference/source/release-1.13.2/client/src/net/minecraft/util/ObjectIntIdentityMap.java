package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class ObjectIntIdentityMap<T> implements IObjectIntIterable<T> {
   private int field_195868_a;
   private final IdentityHashMap<T, Integer> field_148749_a;
   private final List<T> field_148748_b;

   public ObjectIntIdentityMap() {
      this(512);
   }

   public ObjectIntIdentityMap(int var1) {
      this.field_148748_b = Lists.<T>newArrayListWithExpectedSize(☃);
      this.field_148749_a = new IdentityHashMap(☃);
   }

   public void func_148746_a(T var1, int var2) {
      this.field_148749_a.put(☃, ☃);

      while(this.field_148748_b.size() <= ☃) {
         this.field_148748_b.add(null);
      }

      this.field_148748_b.set(☃, ☃);
      if (this.field_195868_a <= ☃) {
         this.field_195868_a = ☃ + 1;
      }
   }

   public void func_195867_b(T var1) {
      this.func_148746_a(☃, this.field_195868_a);
   }

   public int func_148747_b(T var1) {
      Integer ☃ = (Integer)this.field_148749_a.get(☃);
      return ☃ == null ? -1 : ☃;
   }

   @Nullable
   public final T func_148745_a(int var1) {
      return (T)(☃ >= 0 && ☃ < this.field_148748_b.size() ? this.field_148748_b.get(☃) : null);
   }

   public Iterator<T> iterator() {
      return Iterators.filter(this.field_148748_b.iterator(), Predicates.notNull());
   }

   public int func_186804_a() {
      return this.field_148749_a.size();
   }
}
