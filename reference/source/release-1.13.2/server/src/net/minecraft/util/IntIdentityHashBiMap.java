package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;

public class IntIdentityHashBiMap<K> implements IObjectIntIterable<K> {
   private static final Object field_186817_a = null;
   private K[] field_186818_b;
   private int[] field_186819_c;
   private K[] field_186820_d;
   private int field_186821_e;
   private int field_186822_f;

   public IntIdentityHashBiMap(int var1) {
      ☃ = (int)((float)☃ / 0.8F);
      this.field_186818_b = (K[])(new Object[☃]);
      this.field_186819_c = new int[☃];
      this.field_186820_d = (K[])(new Object[☃]);
   }

   public int func_186815_a(@Nullable K var1) {
      return this.func_186805_c(this.func_186816_b(☃, this.func_186811_d(☃)));
   }

   @Nullable
   public K func_186813_a(int var1) {
      return ☃ >= 0 && ☃ < this.field_186820_d.length ? this.field_186820_d[☃] : null;
   }

   private int func_186805_c(int var1) {
      return ☃ == -1 ? -1 : this.field_186819_c[☃];
   }

   public int func_186808_c(K var1) {
      int ☃ = this.func_186809_c();
      this.func_186814_a(☃, ☃);
      return ☃;
   }

   private int func_186809_c() {
      while(this.field_186821_e < this.field_186820_d.length && this.field_186820_d[this.field_186821_e] != null) {
         ++this.field_186821_e;
      }

      return this.field_186821_e;
   }

   private void func_186807_d(int var1) {
      K[] ☃ = this.field_186818_b;
      int[] ☃x = this.field_186819_c;
      this.field_186818_b = (K[])(new Object[☃]);
      this.field_186819_c = new int[☃];
      this.field_186820_d = (K[])(new Object[☃]);
      this.field_186821_e = 0;
      this.field_186822_f = 0;

      for(int ☃xx = 0; ☃xx < ☃.length; ++☃xx) {
         if (☃[☃xx] != null) {
            this.func_186814_a(☃[☃xx], ☃x[☃xx]);
         }
      }
   }

   public void func_186814_a(K var1, int var2) {
      int ☃ = Math.max(☃, this.field_186822_f + 1);
      if ((float)☃ >= (float)this.field_186818_b.length * 0.8F) {
         int ☃x = this.field_186818_b.length << 1;

         while(☃x < ☃) {
            ☃x <<= 1;
         }

         this.func_186807_d(☃x);
      }

      int ☃ = this.func_186806_e(this.func_186811_d(☃));
      this.field_186818_b[☃] = ☃;
      this.field_186819_c[☃] = ☃;
      this.field_186820_d[☃] = ☃;
      ++this.field_186822_f;
      if (☃ == this.field_186821_e) {
         ++this.field_186821_e;
      }
   }

   private int func_186811_d(@Nullable K var1) {
      return (MathHelper.func_188208_f(System.identityHashCode(☃)) & 2147483647) % this.field_186818_b.length;
   }

   private int func_186816_b(@Nullable K var1, int var2) {
      for(int ☃ = ☃; ☃ < this.field_186818_b.length; ++☃) {
         if (this.field_186818_b[☃] == ☃) {
            return ☃;
         }

         if (this.field_186818_b[☃] == field_186817_a) {
            return -1;
         }
      }

      for(int ☃ = 0; ☃ < ☃; ++☃) {
         if (this.field_186818_b[☃] == ☃) {
            return ☃;
         }

         if (this.field_186818_b[☃] == field_186817_a) {
            return -1;
         }
      }

      return -1;
   }

   private int func_186806_e(int var1) {
      for(int ☃ = ☃; ☃ < this.field_186818_b.length; ++☃) {
         if (this.field_186818_b[☃] == field_186817_a) {
            return ☃;
         }
      }

      for(int ☃ = 0; ☃ < ☃; ++☃) {
         if (this.field_186818_b[☃] == field_186817_a) {
            return ☃;
         }
      }

      throw new RuntimeException("Overflowed :(");
   }

   public Iterator<K> iterator() {
      return Iterators.filter(Iterators.forArray(this.field_186820_d), Predicates.notNull());
   }

   public void func_186812_a() {
      Arrays.fill(this.field_186818_b, null);
      Arrays.fill(this.field_186820_d, null);
      this.field_186821_e = 0;
      this.field_186822_f = 0;
   }

   public int func_186810_b() {
      return this.field_186822_f;
   }
}
