package net.minecraft.client.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Swapper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SuffixArray<T> {
   private static final boolean field_194062_b = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
   private static final boolean field_194063_c = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
   private static final Logger field_194064_d = LogManager.getLogger();
   protected final List<T> field_194061_a = Lists.<T>newArrayList();
   private final IntList field_194065_e = new IntArrayList();
   private final IntList field_194066_f = new IntArrayList();
   private IntList field_194067_g = new IntArrayList();
   private IntList field_194068_h = new IntArrayList();
   private int field_194069_i;

   public void func_194057_a(T var1, String var2) {
      this.field_194069_i = Math.max(this.field_194069_i, ☃.length());
      int ☃ = this.field_194061_a.size();
      this.field_194061_a.add(☃);
      this.field_194066_f.add(this.field_194065_e.size());

      for(int ☃x = 0; ☃x < ☃.length(); ++☃x) {
         this.field_194067_g.add(☃);
         this.field_194068_h.add(☃x);
         this.field_194065_e.add(☃.charAt(☃x));
      }

      this.field_194067_g.add(☃);
      this.field_194068_h.add(☃.length());
      this.field_194065_e.add(-1);
   }

   public void func_194058_a() {
      int ☃ = this.field_194065_e.size();
      int[] ☃x = new int[☃];
      final int[] ☃xx = new int[☃];
      final int[] ☃xxx = new int[☃];
      int[] ☃xxxx = new int[☃];
      IntComparator ☃xxxxx = new IntComparator() {
         @Override
         public int compare(int var1, int var2) {
            return ☃[☃] == ☃[☃] ? Integer.compare(☃[☃], ☃[☃]) : Integer.compare(☃[☃], ☃[☃]);
         }

         @Override
         public int compare(Integer var1, Integer var2) {
            return this.compare(☃.intValue(), ☃.intValue());
         }
      };
      Swapper ☃xxxxxx = (var3x, var4x) -> {
         if (var3x != var4x) {
            int ☃ = ☃[var3x];
            ☃[var3x] = ☃[var4x];
            ☃[var4x] = ☃;
            ☃ = ☃[var3x];
            ☃[var3x] = ☃[var4x];
            ☃[var4x] = ☃;
            ☃ = ☃[var3x];
            ☃[var3x] = ☃[var4x];
            ☃[var4x] = ☃;
         }
      };

      for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃; ++☃xxxxxxx) {
         ☃x[☃xxxxxxx] = this.field_194065_e.getInt(☃xxxxxxx);
      }

      int ☃xxxxxxx = 1;

      for(int ☃xxxxxxxx = Math.min(☃, this.field_194069_i); ☃xxxxxxx * 2 < ☃xxxxxxxx; ☃xxxxxxx *= 2) {
         for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃; ☃xxxx[☃xxxxxxxxx] = ☃xxxxxxxxx++) {
            ☃xx[☃xxxxxxxxx] = ☃x[☃xxxxxxxxx];
            ☃xxx[☃xxxxxxxxx] = ☃xxxxxxxxx + ☃xxxxxxx < ☃ ? ☃x[☃xxxxxxxxx + ☃xxxxxxx] : -2;
         }

         Arrays.quickSort(0, ☃, ☃xxxxx, ☃xxxxxx);

         for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃; ++☃xxxxxxxxx) {
            if (☃xxxxxxxxx > 0 && ☃xx[☃xxxxxxxxx] == ☃xx[☃xxxxxxxxx - 1] && ☃xxx[☃xxxxxxxxx] == ☃xxx[☃xxxxxxxxx - 1]) {
               ☃x[☃xxxx[☃xxxxxxxxx]] = ☃x[☃xxxx[☃xxxxxxxxx - 1]];
            } else {
               ☃x[☃xxxx[☃xxxxxxxxx]] = ☃xxxxxxxxx;
            }
         }
      }

      IntList ☃xxxxxxxx = this.field_194067_g;
      IntList ☃xxxxxxxxx = this.field_194068_h;
      this.field_194067_g = new IntArrayList(☃xxxxxxxx.size());
      this.field_194068_h = new IntArrayList(☃xxxxxxxxx.size());

      for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃; ++☃xxxxxxxxxx) {
         int ☃xxxxxxxxxxx = ☃xxxx[☃xxxxxxxxxx];
         this.field_194067_g.add(☃xxxxxxxx.getInt(☃xxxxxxxxxxx));
         this.field_194068_h.add(☃xxxxxxxxx.getInt(☃xxxxxxxxxxx));
      }

      if (field_194063_c) {
         this.func_194060_b();
      }
   }

   private void func_194060_b() {
      for(int ☃ = 0; ☃ < this.field_194067_g.size(); ++☃) {
         field_194064_d.debug("{} {}", ☃, this.func_194059_a(☃));
      }

      field_194064_d.debug("");
   }

   private String func_194059_a(int var1) {
      int ☃ = this.field_194068_h.getInt(☃);
      int ☃x = this.field_194066_f.getInt(this.field_194067_g.getInt(☃));
      StringBuilder ☃xx = new StringBuilder();

      for(int ☃xxx = 0; ☃x + ☃xxx < this.field_194065_e.size(); ++☃xxx) {
         if (☃xxx == ☃) {
            ☃xx.append('^');
         }

         int ☃xxxx = this.field_194065_e.get(☃x + ☃xxx);
         if (☃xxxx == -1) {
            break;
         }

         ☃xx.append((char)☃xxxx);
      }

      return ☃xx.toString();
   }

   private int func_194056_a(String var1, int var2) {
      int ☃ = this.field_194066_f.getInt(this.field_194067_g.getInt(☃));
      int ☃x = this.field_194068_h.getInt(☃);

      for(int ☃xx = 0; ☃xx < ☃.length(); ++☃xx) {
         int ☃xxx = this.field_194065_e.getInt(☃ + ☃x + ☃xx);
         if (☃xxx == -1) {
            return 1;
         }

         char ☃xxx = ☃.charAt(☃xx);
         char ☃xxxx = (char)☃xxx;
         if (☃xxx < ☃xxxx) {
            return -1;
         }

         if (☃xxx > ☃xxxx) {
            return 1;
         }
      }

      return 0;
   }

   public List<T> func_194055_a(String var1) {
      int ☃ = this.field_194067_g.size();
      int ☃x = 0;
      int ☃xx = ☃;

      while(☃x < ☃xx) {
         int ☃xxx = ☃x + (☃xx - ☃x) / 2;
         int ☃xxxx = this.func_194056_a(☃, ☃xxx);
         if (field_194062_b) {
            field_194064_d.debug("comparing lower \"{}\" with {} \"{}\": {}", ☃, ☃xxx, this.func_194059_a(☃xxx), ☃xxxx);
         }

         if (☃xxxx > 0) {
            ☃x = ☃xxx + 1;
         } else {
            ☃xx = ☃xxx;
         }
      }

      if (☃x >= 0 && ☃x < ☃) {
         int ☃xxx = ☃x;
         ☃xx = ☃;

         while(☃x < ☃xx) {
            int ☃xxxx = ☃x + (☃xx - ☃x) / 2;
            int ☃xxxxx = this.func_194056_a(☃, ☃xxxx);
            if (field_194062_b) {
               field_194064_d.debug("comparing upper \"{}\" with {} \"{}\": {}", ☃, ☃xxxx, this.func_194059_a(☃xxxx), ☃xxxxx);
            }

            if (☃xxxxx >= 0) {
               ☃x = ☃xxxx + 1;
            } else {
               ☃xx = ☃xxxx;
            }
         }

         int ☃xxxx = ☃x;
         IntSet ☃xxxxx = new IntOpenHashSet();

         for(int ☃xxxxxx = ☃xxx; ☃xxxxxx < ☃xxxx; ++☃xxxxxx) {
            ☃xxxxx.add(this.field_194067_g.getInt(☃xxxxxx));
         }

         int[] ☃xxxxxx = ☃xxxxx.toIntArray();
         java.util.Arrays.sort(☃xxxxxx);
         Set<T> ☃xxxxxxx = Sets.<T>newLinkedHashSet();

         for(int ☃xxxxxxxx : ☃xxxxxx) {
            ☃xxxxxxx.add(this.field_194061_a.get(☃xxxxxxxx));
         }

         return Lists.<T>newArrayList(☃xxxxxxx);
      } else {
         return Collections.emptyList();
      }
   }
}
