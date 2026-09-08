package net.minecraft.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class NonNullList<E> extends AbstractList<E> {
   private final List<E> field_191198_a;
   private final E field_191199_b;

   public static <E> NonNullList<E> func_191196_a() {
      return new NonNullList<>();
   }

   public static <E> NonNullList<E> func_191197_a(int var0, E var1) {
      Validate.notNull(☃);
      Object[] ☃ = new Object[☃];
      Arrays.fill(☃, ☃);
      return new NonNullList<>(Arrays.asList(☃), ☃);
   }

   @SafeVarargs
   public static <E> NonNullList<E> func_193580_a(E var0, E... var1) {
      return new NonNullList<>(Arrays.asList(☃), ☃);
   }

   protected NonNullList() {
      this(new ArrayList(), (E)null);
   }

   protected NonNullList(List<E> var1, @Nullable E var2) {
      this.field_191198_a = ☃;
      this.field_191199_b = ☃;
   }

   @Nonnull
   public E get(int var1) {
      return (E)this.field_191198_a.get(☃);
   }

   public E set(int var1, E var2) {
      Validate.notNull(☃);
      return (E)this.field_191198_a.set(☃, ☃);
   }

   public void add(int var1, E var2) {
      Validate.notNull(☃);
      this.field_191198_a.add(☃, ☃);
   }

   public E remove(int var1) {
      return (E)this.field_191198_a.remove(☃);
   }

   public int size() {
      return this.field_191198_a.size();
   }

   public void clear() {
      if (this.field_191199_b == null) {
         super.clear();
      } else {
         for(int ☃ = 0; ☃ < this.size(); ++☃) {
            this.set(☃, this.field_191199_b);
         }
      }
   }
}
