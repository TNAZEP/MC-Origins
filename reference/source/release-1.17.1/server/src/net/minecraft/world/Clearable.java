package net.minecraft.world;

import javax.annotation.Nullable;

public interface Clearable {
   void clearContent();

   static void tryClear(@Nullable Object var0) {
      if (â˜ƒ instanceof Clearable) {
         ((Clearable)â˜ƒ).clearContent();
      }
   }
}
