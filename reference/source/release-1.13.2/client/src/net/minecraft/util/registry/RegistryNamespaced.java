package net.minecraft.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryNamespaced<V> implements IRegistry<V> {
   protected static final Logger field_148743_a = LogManager.getLogger();
   protected final IntIdentityHashBiMap<V> field_148759_a = new IntIdentityHashBiMap<>(256);
   protected final BiMap<ResourceLocation, V> field_82596_a = HashBiMap.create();
   protected Object[] field_186802_b;
   private int field_195869_d;

   @Override
   public void func_177775_a(int var1, ResourceLocation var2, V var3) {
      this.field_148759_a.func_186814_a(☃, ☃);
      Validate.notNull(☃);
      Validate.notNull(☃);
      this.field_186802_b = null;
      if (this.field_82596_a.containsKey(☃)) {
         field_148743_a.debug("Adding duplicate key '{}' to registry", ☃);
      }

      this.field_82596_a.put(☃, ☃);
      if (this.field_195869_d <= ☃) {
         this.field_195869_d = ☃ + 1;
      }
   }

   @Override
   public void func_82595_a(ResourceLocation var1, V var2) {
      this.func_177775_a(this.field_195869_d, ☃, ☃);
   }

   @Nullable
   @Override
   public ResourceLocation func_177774_c(V var1) {
      return (ResourceLocation)this.field_82596_a.inverse().get(☃);
   }

   @Override
   public V func_82594_a(@Nullable ResourceLocation var1) {
      throw new UnsupportedOperationException("No default value");
   }

   @Override
   public ResourceLocation func_212609_b() {
      throw new UnsupportedOperationException("No default key");
   }

   @Override
   public int func_148757_b(@Nullable V var1) {
      return this.field_148759_a.func_186815_a(☃);
   }

   @Nullable
   @Override
   public V func_148754_a(int var1) {
      return this.field_148759_a.func_186813_a(☃);
   }

   @Override
   public Iterator<V> iterator() {
      return this.field_148759_a.iterator();
   }

   @Nullable
   @Override
   public V func_212608_b(@Nullable ResourceLocation var1) {
      return (V)this.field_82596_a.get(☃);
   }

   @Override
   public Set<ResourceLocation> func_148742_b() {
      return Collections.unmodifiableSet(this.field_82596_a.keySet());
   }

   @Override
   public boolean func_195866_d() {
      return this.field_82596_a.isEmpty();
   }

   @Nullable
   @Override
   public V func_186801_a(Random var1) {
      if (this.field_186802_b == null) {
         Collection<?> ☃ = this.field_82596_a.values();
         if (☃.isEmpty()) {
            return null;
         }

         this.field_186802_b = ☃.toArray(new Object[☃.size()]);
      }

      return (V)this.field_186802_b[☃.nextInt(this.field_186802_b.length)];
   }

   @Override
   public boolean func_212607_c(ResourceLocation var1) {
      return this.field_82596_a.containsKey(☃);
   }
}
