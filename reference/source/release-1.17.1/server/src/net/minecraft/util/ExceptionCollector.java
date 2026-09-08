package net.minecraft.util;

import javax.annotation.Nullable;

public class ExceptionCollector<T extends Throwable> {
   @Nullable
   private T result;

   public void add(T var1) {
      if (this.result == null) {
         this.result = â˜ƒ;
      } else {
         this.result.addSuppressed(â˜ƒ);
      }
   }

   public void throwIfPresent() throws T {
      if (this.result != null) {
         throw this.result;
      }
   }
}
