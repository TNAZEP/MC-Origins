package net.minecraft.util;

import java.util.List;
import java.util.Random;

public class WeightedRandom {
   public static int func_76272_a(List<? extends WeightedRandom.Item> var0) {
      int ☃ = 0;
      int ☃x = 0;

      for(int ☃xx = ☃.size(); ☃x < ☃xx; ++☃x) {
         WeightedRandom.Item ☃xxx = (WeightedRandom.Item)☃.get(☃x);
         ☃ += ☃xxx.field_76292_a;
      }

      return ☃;
   }

   public static <T extends WeightedRandom.Item> T func_76273_a(Random var0, List<T> var1, int var2) {
      if (☃ <= 0) {
         throw new IllegalArgumentException();
      } else {
         int ☃ = ☃.nextInt(☃);
         return func_180166_a(☃, ☃);
      }
   }

   public static <T extends WeightedRandom.Item> T func_180166_a(List<T> var0, int var1) {
      int ☃ = 0;

      for(int ☃x = ☃.size(); ☃ < ☃x; ++☃) {
         T ☃xx = (T)☃.get(☃);
         ☃ -= ☃xx.field_76292_a;
         if (☃ < 0) {
            return ☃xx;
         }
      }

      return null;
   }

   public static <T extends WeightedRandom.Item> T func_76271_a(Random var0, List<T> var1) {
      return func_76273_a(☃, ☃, func_76272_a(☃));
   }

   public static class Item {
      protected int field_76292_a;

      public Item(int var1) {
         this.field_76292_a = ☃;
      }
   }
}
