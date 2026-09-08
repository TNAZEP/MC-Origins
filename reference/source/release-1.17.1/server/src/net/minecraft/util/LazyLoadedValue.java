package net.minecraft.util;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;

@Deprecated
public class LazyLoadedValue<T> {
   private final Supplier<T> factory;

   public LazyLoadedValue(Supplier<T> var1) {
      this.factory = Suppliers.memoize(â˜ƒ::get);
   }

   public T get() {
      return (T)this.factory.get();
   }
}
