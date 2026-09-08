package net.minecraft.util;

import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map;

public class ExpiringMap<T> extends Long2ObjectOpenHashMap<T> {
   private final int field_201843_a;
   private final Long2LongMap field_201844_b = new Long2LongLinkedOpenHashMap();

   public ExpiringMap(int var1, int var2) {
      super(☃);
      this.field_201843_a = ☃;
   }

   private void func_201842_a(long var1) {
      long ☃ = Util.func_211177_b();
      this.field_201844_b.put(☃, ☃);
      ObjectIterator<Long2LongMap.Entry> ☃x = this.field_201844_b.long2LongEntrySet().iterator();

      while(☃x.hasNext()) {
         Long2LongMap.Entry ☃xx = (Long2LongMap.Entry)☃x.next();
         T ☃xxx = (T)super.get(☃xx.getLongKey());
         if (☃ - ☃xx.getLongValue() <= (long)this.field_201843_a) {
            break;
         }

         if (☃xxx != null && this.func_205609_a_(☃xxx)) {
            super.remove(☃xx.getLongKey());
            ☃x.remove();
         }
      }
   }

   protected boolean func_205609_a_(T var1) {
      return true;
   }

   @Override
   public T put(long var1, T var3) {
      this.func_201842_a(☃);
      return super.put(☃, ☃);
   }

   @Override
   public T put(Long var1, T var2) {
      this.func_201842_a(☃);
      return super.put(☃, ☃);
   }

   @Override
   public T get(long var1) {
      this.func_201842_a(☃);
      return (T)super.get(☃);
   }

   @Override
   public void putAll(Map<? extends Long, ? extends T> var1) {
      throw new RuntimeException("Not implemented");
   }

   @Override
   public T remove(long var1) {
      throw new RuntimeException("Not implemented");
   }

   @Override
   public T remove(Object var1) {
      throw new RuntimeException("Not implemented");
   }
}
