package net.minecraft.world.level.entity;

import javax.annotation.Nullable;

public interface EntityTypeTest<B, T extends B> {
   static <B, T extends B> EntityTypeTest<B, T> forClass(final Class<T> var0) {
      return new EntityTypeTest<B, T>() {
         @Nullable
         @Override
         public T tryCast(B var1) {
            return (T)(â˜ƒ.isInstance(â˜ƒ) ? â˜ƒ : null);
         }

         @Override
         public Class<? extends B> getBaseClass() {
            return â˜ƒ;
         }
      };
   }

   @Nullable
   T tryCast(B var1);

   Class<? extends B> getBaseClass();
}
