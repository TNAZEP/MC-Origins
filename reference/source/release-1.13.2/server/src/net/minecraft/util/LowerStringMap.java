package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class LowerStringMap<V> implements Map<String, V> {
   private final Map<String, V> field_76117_a = Maps.newLinkedHashMap();

   public int size() {
      return this.field_76117_a.size();
   }

   public boolean isEmpty() {
      return this.field_76117_a.isEmpty();
   }

   public boolean containsKey(Object var1) {
      return this.field_76117_a.containsKey(☃.toString().toLowerCase(Locale.ROOT));
   }

   public boolean containsValue(Object var1) {
      return this.field_76117_a.containsValue(☃);
   }

   public V get(Object var1) {
      return (V)this.field_76117_a.get(☃.toString().toLowerCase(Locale.ROOT));
   }

   public V put(String var1, V var2) {
      return (V)this.field_76117_a.put(☃.toLowerCase(Locale.ROOT), ☃);
   }

   public V remove(Object var1) {
      return (V)this.field_76117_a.remove(☃.toString().toLowerCase(Locale.ROOT));
   }

   public void putAll(Map<? extends String, ? extends V> var1) {
      for(Entry<? extends String, ? extends V> ☃ : ☃.entrySet()) {
         this.put((String)☃.getKey(), (V)☃.getValue());
      }
   }

   public void clear() {
      this.field_76117_a.clear();
   }

   public Set<String> keySet() {
      return this.field_76117_a.keySet();
   }

   public Collection<V> values() {
      return this.field_76117_a.values();
   }

   public Set<Entry<String, V>> entrySet() {
      return this.field_76117_a.entrySet();
   }
}
