package net.minecraft.tags;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;

public class SetTag<T> implements Tag<T> {
   private final ImmutableList<T> valuesList;
   private final Set<T> values;
   @VisibleForTesting
   protected final Class<?> closestCommonSuperType;

   protected SetTag(Set<T> var1, Class<?> var2) {
      this.closestCommonSuperType = â˜ƒ;
      this.values = â˜ƒ;
      this.valuesList = ImmutableList.copyOf(â˜ƒ);
   }

   public static <T> SetTag<T> empty() {
      return new SetTag<>(ImmutableSet.of(), Void.class);
   }

   public static <T> SetTag<T> create(Set<T> var0) {
      return new SetTag<>(â˜ƒ, findCommonSuperClass(â˜ƒ));
   }

   @Override
   public boolean contains(T var1) {
      return this.closestCommonSuperType.isInstance(â˜ƒ) && this.values.contains(â˜ƒ);
   }

   @Override
   public List<T> getValues() {
      return this.valuesList;
   }

   private static <T> Class<?> findCommonSuperClass(Set<T> var0) {
      if (â˜ƒ.isEmpty()) {
         return Void.class;
      } else {
         Class<?> â˜ƒ = null;

         for(T â˜ƒx : â˜ƒ) {
            if (â˜ƒ == null) {
               â˜ƒ = â˜ƒx.getClass();
            } else {
               â˜ƒ = findClosestAncestor(â˜ƒ, â˜ƒx.getClass());
            }
         }

         return â˜ƒ;
      }
   }

   private static Class<?> findClosestAncestor(Class<?> var0, Class<?> var1) {
      while(!â˜ƒ.isAssignableFrom(â˜ƒ)) {
         â˜ƒ = â˜ƒ.getSuperclass();
      }

      return â˜ƒ;
   }
}
