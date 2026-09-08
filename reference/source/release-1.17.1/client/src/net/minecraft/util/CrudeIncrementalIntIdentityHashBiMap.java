package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.IdMap;

public class CrudeIncrementalIntIdentityHashBiMap<K> implements IdMap<K> {
   public static final int NOT_FOUND = -1;
   private static final Object EMPTY_SLOT = null;
   private static final float LOADFACTOR = 0.8F;
   private K[] keys;
   private int[] values;
   private K[] byId;
   private int nextId;
   private int size;

   public CrudeIncrementalIntIdentityHashBiMap(int var1) {
      â˜ƒ = (int)((float)â˜ƒ / 0.8F);
      this.keys = (K[])(new Object[â˜ƒ]);
      this.values = new int[â˜ƒ];
      this.byId = (K[])(new Object[â˜ƒ]);
   }

   @Override
   public int getId(@Nullable K var1) {
      return this.getValue(this.indexOf(â˜ƒ, this.hash(â˜ƒ)));
   }

   @Nullable
   @Override
   public K byId(int var1) {
      return â˜ƒ >= 0 && â˜ƒ < this.byId.length ? this.byId[â˜ƒ] : null;
   }

   private int getValue(int var1) {
      return â˜ƒ == -1 ? -1 : this.values[â˜ƒ];
   }

   public boolean contains(K var1) {
      return this.getId(â˜ƒ) != -1;
   }

   public boolean contains(int var1) {
      return this.byId(â˜ƒ) != null;
   }

   public int add(K var1) {
      int â˜ƒ = this.nextId();
      this.addMapping(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   private int nextId() {
      while(this.nextId < this.byId.length && this.byId[this.nextId] != null) {
         ++this.nextId;
      }

      return this.nextId;
   }

   private void grow(int var1) {
      K[] â˜ƒ = this.keys;
      int[] â˜ƒx = this.values;
      this.keys = (K[])(new Object[â˜ƒ]);
      this.values = new int[â˜ƒ];
      this.byId = (K[])(new Object[â˜ƒ]);
      this.nextId = 0;
      this.size = 0;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.length; ++â˜ƒxx) {
         if (â˜ƒ[â˜ƒxx] != null) {
            this.addMapping(â˜ƒ[â˜ƒxx], â˜ƒx[â˜ƒxx]);
         }
      }
   }

   public void addMapping(K var1, int var2) {
      int â˜ƒ = Math.max(â˜ƒ, this.size + 1);
      if ((float)â˜ƒ >= (float)this.keys.length * 0.8F) {
         int â˜ƒx = this.keys.length << 1;

         while(â˜ƒx < â˜ƒ) {
            â˜ƒx <<= 1;
         }

         this.grow(â˜ƒx);
      }

      int â˜ƒ = this.findEmpty(this.hash(â˜ƒ));
      this.keys[â˜ƒ] = â˜ƒ;
      this.values[â˜ƒ] = â˜ƒ;
      this.byId[â˜ƒ] = â˜ƒ;
      ++this.size;
      if (â˜ƒ == this.nextId) {
         ++this.nextId;
      }
   }

   private int hash(@Nullable K var1) {
      return (Mth.murmurHash3Mixer(System.identityHashCode(â˜ƒ)) & 2147483647) % this.keys.length;
   }

   private int indexOf(@Nullable K var1, int var2) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ < this.keys.length; ++â˜ƒ) {
         if (this.keys[â˜ƒ] == â˜ƒ) {
            return â˜ƒ;
         }

         if (this.keys[â˜ƒ] == EMPTY_SLOT) {
            return -1;
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         if (this.keys[â˜ƒ] == â˜ƒ) {
            return â˜ƒ;
         }

         if (this.keys[â˜ƒ] == EMPTY_SLOT) {
            return -1;
         }
      }

      return -1;
   }

   private int findEmpty(int var1) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ < this.keys.length; ++â˜ƒ) {
         if (this.keys[â˜ƒ] == EMPTY_SLOT) {
            return â˜ƒ;
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         if (this.keys[â˜ƒ] == EMPTY_SLOT) {
            return â˜ƒ;
         }
      }

      throw new RuntimeException("Overflowed :(");
   }

   public Iterator<K> iterator() {
      return Iterators.filter(Iterators.forArray(this.byId), Predicates.notNull());
   }

   public void clear() {
      Arrays.fill(this.keys, null);
      Arrays.fill(this.byId, null);
      this.nextId = 0;
      this.size = 0;
   }

   public int size() {
      return this.size;
   }
}
