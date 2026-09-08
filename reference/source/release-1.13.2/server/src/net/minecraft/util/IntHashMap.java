package net.minecraft.util;

import javax.annotation.Nullable;

public class IntHashMap<V> {
   private transient IntHashMap.Entry<V>[] field_76055_a;
   private transient int field_76053_b;
   private int field_76054_c;
   private final float field_76051_d = 0.75F;

   public IntHashMap() {
      this.field_76054_c = 12;
      this.field_76055_a = new IntHashMap.Entry[16];
   }

   private static int func_76044_g(int var0) {
      ☃ ^= ☃ >>> 20 ^ ☃ >>> 12;
      return ☃ ^ ☃ >>> 7 ^ ☃ >>> 4;
   }

   private static int func_76043_a(int var0, int var1) {
      return ☃ & ☃ - 1;
   }

   @Nullable
   public V func_76041_a(int var1) {
      int ☃ = func_76044_g(☃);

      for(IntHashMap.Entry<V> ☃x = this.field_76055_a[func_76043_a(☃, this.field_76055_a.length)]; ☃x != null; ☃x = ☃x.field_76034_c) {
         if (☃x.field_76035_a == ☃) {
            return ☃x.field_76033_b;
         }
      }

      return null;
   }

   public boolean func_76037_b(int var1) {
      return this.func_76045_c(☃) != null;
   }

   @Nullable
   final IntHashMap.Entry<V> func_76045_c(int var1) {
      int ☃ = func_76044_g(☃);

      for(IntHashMap.Entry<V> ☃x = this.field_76055_a[func_76043_a(☃, this.field_76055_a.length)]; ☃x != null; ☃x = ☃x.field_76034_c) {
         if (☃x.field_76035_a == ☃) {
            return ☃x;
         }
      }

      return null;
   }

   public void func_76038_a(int var1, V var2) {
      int ☃ = func_76044_g(☃);
      int ☃x = func_76043_a(☃, this.field_76055_a.length);

      for(IntHashMap.Entry<V> ☃xx = this.field_76055_a[☃x]; ☃xx != null; ☃xx = ☃xx.field_76034_c) {
         if (☃xx.field_76035_a == ☃) {
            ☃xx.field_76033_b = ☃;
            return;
         }
      }

      this.func_76040_a(☃, ☃, ☃, ☃x);
   }

   private void func_76047_h(int var1) {
      IntHashMap.Entry<V>[] ☃ = this.field_76055_a;
      int ☃x = ☃.length;
      if (☃x == 1073741824) {
         this.field_76054_c = Integer.MAX_VALUE;
      } else {
         IntHashMap.Entry<V>[] ☃ = new IntHashMap.Entry[☃];
         this.func_76048_a(☃);
         this.field_76055_a = ☃;
         this.field_76054_c = (int)((float)☃ * this.field_76051_d);
      }
   }

   private void func_76048_a(IntHashMap.Entry<V>[] var1) {
      IntHashMap.Entry<V>[] ☃ = this.field_76055_a;
      int ☃x = ☃.length;

      for(int ☃xx = 0; ☃xx < ☃.length; ++☃xx) {
         IntHashMap.Entry<V> ☃xxx = ☃[☃xx];
         if (☃xxx != null) {
            ☃[☃xx] = null;

            while(true) {
               IntHashMap.Entry<V> ☃xxxx = ☃xxx.field_76034_c;
               int ☃xxxxx = func_76043_a(☃xxx.field_76032_d, ☃x);
               ☃xxx.field_76034_c = ☃[☃xxxxx];
               ☃[☃xxxxx] = ☃xxx;
               ☃xxx = ☃xxxx;
               if (☃xxxx == null) {
                  break;
               }
            }
         }
      }
   }

   @Nullable
   public V func_76049_d(int var1) {
      IntHashMap.Entry<V> ☃ = this.func_76036_e(☃);
      return ☃ == null ? null : ☃.field_76033_b;
   }

   @Nullable
   final IntHashMap.Entry<V> func_76036_e(int var1) {
      int ☃ = func_76044_g(☃);
      int ☃x = func_76043_a(☃, this.field_76055_a.length);
      IntHashMap.Entry<V> ☃xx = this.field_76055_a[☃x];

      IntHashMap.Entry<V> ☃;
      IntHashMap.Entry<V> ☃;
      for(☃ = ☃xx; ☃ != null; ☃ = ☃) {
         ☃ = ☃.field_76034_c;
         if (☃.field_76035_a == ☃) {
            --this.field_76053_b;
            if (☃xx == ☃) {
               this.field_76055_a[☃x] = ☃;
            } else {
               ☃xx.field_76034_c = ☃;
            }

            return ☃;
         }

         ☃xx = ☃;
      }

      return ☃;
   }

   public void func_76046_c() {
      IntHashMap.Entry<V>[] ☃ = this.field_76055_a;

      for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
         ☃[☃x] = null;
      }

      this.field_76053_b = 0;
   }

   private void func_76040_a(int var1, int var2, V var3, int var4) {
      IntHashMap.Entry<V> ☃ = this.field_76055_a[☃];
      this.field_76055_a[☃] = new IntHashMap.Entry<>(☃, ☃, ☃, ☃);
      if (this.field_76053_b++ >= this.field_76054_c) {
         this.func_76047_h(2 * this.field_76055_a.length);
      }
   }

   static class Entry<V> {
      private final int field_76035_a;
      private V field_76033_b;
      private IntHashMap.Entry<V> field_76034_c;
      private final int field_76032_d;

      Entry(int var1, int var2, V var3, IntHashMap.Entry<V> var4) {
         this.field_76033_b = ☃;
         this.field_76034_c = ☃;
         this.field_76035_a = ☃;
         this.field_76032_d = ☃;
      }

      public final int func_76031_a() {
         return this.field_76035_a;
      }

      public final V func_76030_b() {
         return this.field_76033_b;
      }

      public final boolean equals(Object var1) {
         if (!(☃ instanceof IntHashMap.Entry)) {
            return false;
         } else {
            IntHashMap.Entry<V> ☃ = (IntHashMap.Entry)☃;
            if (this.field_76035_a == ☃.field_76035_a) {
               Object ☃x = this.func_76030_b();
               Object ☃xx = ☃.func_76030_b();
               if (☃x == ☃xx || ☃x != null && ☃x.equals(☃xx)) {
                  return true;
               }
            }

            return false;
         }
      }

      public final int hashCode() {
         return IntHashMap.func_76044_g(this.field_76035_a);
      }

      public final String toString() {
         return this.func_76031_a() + "=" + this.func_76030_b();
      }
   }
}
