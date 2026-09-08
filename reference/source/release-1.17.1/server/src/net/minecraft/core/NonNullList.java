package net.minecraft.core;

import com.google.common.collect.Lists;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class NonNullList<E> extends AbstractList<E> {
   private final List<E> list;
   @Nullable
   private final E defaultValue;

   public static <E> NonNullList<E> create() {
      return new NonNullList<>(Lists.<E>newArrayList(), (E)null);
   }

   public static <E> NonNullList<E> createWithCapacity(int var0) {
      return new NonNullList<>(Lists.<E>newArrayListWithCapacity(â˜ƒ), (E)null);
   }

   public static <E> NonNullList<E> withSize(int var0, E var1) {
      Validate.notNull(â˜ƒ);
      Object[] â˜ƒ = new Object[â˜ƒ];
      Arrays.fill(â˜ƒ, â˜ƒ);
      return new NonNullList<>(Arrays.asList(â˜ƒ), â˜ƒ);
   }

   @SafeVarargs
   public static <E> NonNullList<E> of(E var0, E... var1) {
      return new NonNullList<>(Arrays.asList(â˜ƒ), â˜ƒ);
   }

   protected NonNullList(List<E> var1, @Nullable E var2) {
      this.list = â˜ƒ;
      this.defaultValue = â˜ƒ;
   }

   @Nonnull
   public E get(int var1) {
      return (E)this.list.get(â˜ƒ);
   }

   public E set(int var1, E var2) {
      Validate.notNull(â˜ƒ);
      return (E)this.list.set(â˜ƒ, â˜ƒ);
   }

   public void add(int var1, E var2) {
      Validate.notNull(â˜ƒ);
      this.list.add(â˜ƒ, â˜ƒ);
   }

   public E remove(int var1) {
      return (E)this.list.remove(â˜ƒ);
   }

   public int size() {
      return this.list.size();
   }

   public void clear() {
      if (this.defaultValue == null) {
         super.clear();
      } else {
         for(int â˜ƒ = 0; â˜ƒ < this.size(); ++â˜ƒ) {
            this.set(â˜ƒ, this.defaultValue);
         }
      }
   }
}
