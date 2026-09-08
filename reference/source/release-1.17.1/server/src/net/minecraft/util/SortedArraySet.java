package net.minecraft.util;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

public class SortedArraySet<T> extends AbstractSet<T> {
   private static final int DEFAULT_INITIAL_CAPACITY = 10;
   private final Comparator<T> comparator;
   T[] contents;
   int size;

   private SortedArraySet(int var1, Comparator<T> var2) {
      this.comparator = â˜ƒ;
      if (â˜ƒ < 0) {
         throw new IllegalArgumentException("Initial capacity (" + â˜ƒ + ") is negative");
      } else {
         this.contents = (T[])castRawArray(new Object[â˜ƒ]);
      }
   }

   public static <T extends Comparable<T>> SortedArraySet<T> create() {
      return create(10);
   }

   public static <T extends Comparable<T>> SortedArraySet<T> create(int var0) {
      return new SortedArraySet(â˜ƒ, Comparator.naturalOrder());
   }

   public static <T> SortedArraySet<T> create(Comparator<T> var0) {
      return create(â˜ƒ, 10);
   }

   public static <T> SortedArraySet<T> create(Comparator<T> var0, int var1) {
      return new SortedArraySet<>(â˜ƒ, â˜ƒ);
   }

   private static <T> T[] castRawArray(Object[] var0) {
      return (T[])â˜ƒ;
   }

   private int findIndex(T var1) {
      return Arrays.binarySearch(this.contents, 0, this.size, â˜ƒ, this.comparator);
   }

   private static int getInsertionPosition(int var0) {
      return -â˜ƒ - 1;
   }

   public boolean add(T var1) {
      int â˜ƒ = this.findIndex(â˜ƒ);
      if (â˜ƒ >= 0) {
         return false;
      } else {
         int â˜ƒ = getInsertionPosition(â˜ƒ);
         this.addInternal(â˜ƒ, â˜ƒ);
         return true;
      }
   }

   private void grow(int var1) {
      if (â˜ƒ > this.contents.length) {
         if (this.contents != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            â˜ƒ = (int)Math.max(Math.min((long)this.contents.length + (long)(this.contents.length >> 1), 2147483639L), (long)â˜ƒ);
         } else if (â˜ƒ < 10) {
            â˜ƒ = 10;
         }

         Object[] â˜ƒ = new Object[â˜ƒ];
         System.arraycopy(this.contents, 0, â˜ƒ, 0, this.size);
         this.contents = (T[])castRawArray(â˜ƒ);
      }
   }

   private void addInternal(T var1, int var2) {
      this.grow(this.size + 1);
      if (â˜ƒ != this.size) {
         System.arraycopy(this.contents, â˜ƒ, this.contents, â˜ƒ + 1, this.size - â˜ƒ);
      }

      this.contents[â˜ƒ] = â˜ƒ;
      ++this.size;
   }

   void removeInternal(int var1) {
      --this.size;
      if (â˜ƒ != this.size) {
         System.arraycopy(this.contents, â˜ƒ + 1, this.contents, â˜ƒ, this.size - â˜ƒ);
      }

      this.contents[this.size] = null;
   }

   private T getInternal(int var1) {
      return this.contents[â˜ƒ];
   }

   public T addOrGet(T var1) {
      int â˜ƒ = this.findIndex(â˜ƒ);
      if (â˜ƒ >= 0) {
         return this.getInternal(â˜ƒ);
      } else {
         this.addInternal(â˜ƒ, getInsertionPosition(â˜ƒ));
         return â˜ƒ;
      }
   }

   public boolean remove(Object var1) {
      int â˜ƒ = this.findIndex((T)â˜ƒ);
      if (â˜ƒ >= 0) {
         this.removeInternal(â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public T get(T var1) {
      int â˜ƒ = this.findIndex(â˜ƒ);
      return â˜ƒ >= 0 ? this.getInternal(â˜ƒ) : null;
   }

   public T first() {
      return this.getInternal(0);
   }

   public T last() {
      return this.getInternal(this.size - 1);
   }

   public boolean contains(Object var1) {
      int â˜ƒ = this.findIndex((T)â˜ƒ);
      return â˜ƒ >= 0;
   }

   public Iterator<T> iterator() {
      return new SortedArraySet.ArrayIterator();
   }

   public int size() {
      return this.size;
   }

   public Object[] toArray() {
      return this.contents.clone();
   }

   public <U> U[] toArray(U[] var1) {
      if (â˜ƒ.length < this.size) {
         return (U[])Arrays.copyOf(this.contents, this.size, â˜ƒ.getClass());
      } else {
         System.arraycopy(this.contents, 0, â˜ƒ, 0, this.size);
         if (â˜ƒ.length > this.size) {
            â˜ƒ[this.size] = null;
         }

         return â˜ƒ;
      }
   }

   public void clear() {
      Arrays.fill(this.contents, 0, this.size, null);
      this.size = 0;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         if (â˜ƒ instanceof SortedArraySet â˜ƒ && this.comparator.equals(â˜ƒ.comparator)) {
            return this.size == â˜ƒ.size && Arrays.equals(this.contents, â˜ƒ.contents);
         }

         return super.equals(â˜ƒ);
      }
   }

   class ArrayIterator implements Iterator<T> {
      private int index;
      private int last = -1;

      public boolean hasNext() {
         return this.index < SortedArraySet.this.size;
      }

      public T next() {
         if (this.index >= SortedArraySet.this.size) {
            throw new NoSuchElementException();
         } else {
            this.last = this.index++;
            return SortedArraySet.this.contents[this.last];
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            SortedArraySet.this.removeInternal(this.last);
            --this.index;
            this.last = -1;
         }
      }
   }
}
