package net.minecraft.client.searchtree;

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
   private static final boolean DEBUG_COMPARISONS = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
   private static final boolean DEBUG_ARRAY = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int END_OF_TEXT_MARKER = -1;
   private static final int END_OF_DATA = -2;
   protected final List<T> list = Lists.<T>newArrayList();
   private final IntList chars = new IntArrayList();
   private final IntList wordStarts = new IntArrayList();
   private IntList suffixToT = new IntArrayList();
   private IntList offsets = new IntArrayList();
   private int maxStringLength;

   public void add(T var1, String var2) {
      this.maxStringLength = Math.max(this.maxStringLength, â˜ƒ.length());
      int â˜ƒ = this.list.size();
      this.list.add(â˜ƒ);
      this.wordStarts.add(this.chars.size());

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length(); ++â˜ƒx) {
         this.suffixToT.add(â˜ƒ);
         this.offsets.add(â˜ƒx);
         this.chars.add(â˜ƒ.charAt(â˜ƒx));
      }

      this.suffixToT.add(â˜ƒ);
      this.offsets.add(â˜ƒ.length());
      this.chars.add(-1);
   }

   public void generate() {
      int â˜ƒ = this.chars.size();
      int[] â˜ƒx = new int[â˜ƒ];
      final int[] â˜ƒxx = new int[â˜ƒ];
      final int[] â˜ƒxxx = new int[â˜ƒ];
      int[] â˜ƒxxxx = new int[â˜ƒ];
      IntComparator â˜ƒxxxxx = new IntComparator() {
         @Override
         public int compare(int var1, int var2) {
            return â˜ƒ[â˜ƒ] == â˜ƒ[â˜ƒ] ? Integer.compare(â˜ƒ[â˜ƒ], â˜ƒ[â˜ƒ]) : Integer.compare(â˜ƒ[â˜ƒ], â˜ƒ[â˜ƒ]);
         }

         @Override
         public int compare(Integer var1, Integer var2) {
            return this.compare(â˜ƒ.intValue(), â˜ƒ.intValue());
         }
      };
      Swapper â˜ƒxxxxxx = (var3x, var4x) -> {
         if (var3x != var4x) {
            int â˜ƒ = â˜ƒ[var3x];
            â˜ƒ[var3x] = â˜ƒ[var4x];
            â˜ƒ[var4x] = â˜ƒ;
            â˜ƒ = â˜ƒ[var3x];
            â˜ƒ[var3x] = â˜ƒ[var4x];
            â˜ƒ[var4x] = â˜ƒ;
            â˜ƒ = â˜ƒ[var3x];
            â˜ƒ[var3x] = â˜ƒ[var4x];
            â˜ƒ[var4x] = â˜ƒ;
         }
      };

      for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxx) {
         â˜ƒx[â˜ƒxxxxxxx] = this.chars.getInt(â˜ƒxxxxxxx);
      }

      int â˜ƒxxxxxxx = 1;

      for(int â˜ƒxxxxxxxx = Math.min(â˜ƒ, this.maxStringLength); â˜ƒxxxxxxx * 2 < â˜ƒxxxxxxxx; â˜ƒxxxxxxx *= 2) {
         for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒ; â˜ƒxxxx[â˜ƒxxxxxxxxx] = â˜ƒxxxxxxxxx++) {
            â˜ƒxx[â˜ƒxxxxxxxxx] = â˜ƒx[â˜ƒxxxxxxxxx];
            â˜ƒxxx[â˜ƒxxxxxxxxx] = â˜ƒxxxxxxxxx + â˜ƒxxxxxxx < â˜ƒ ? â˜ƒx[â˜ƒxxxxxxxxx + â˜ƒxxxxxxx] : -2;
         }

         Arrays.quickSort(0, â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx);

         for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxxxx) {
            if (â˜ƒxxxxxxxxx > 0 && â˜ƒxx[â˜ƒxxxxxxxxx] == â˜ƒxx[â˜ƒxxxxxxxxx - 1] && â˜ƒxxx[â˜ƒxxxxxxxxx] == â˜ƒxxx[â˜ƒxxxxxxxxx - 1]) {
               â˜ƒx[â˜ƒxxxx[â˜ƒxxxxxxxxx]] = â˜ƒx[â˜ƒxxxx[â˜ƒxxxxxxxxx - 1]];
            } else {
               â˜ƒx[â˜ƒxxxx[â˜ƒxxxxxxxxx]] = â˜ƒxxxxxxxxx;
            }
         }
      }

      IntList â˜ƒxxxxxxxx = this.suffixToT;
      IntList â˜ƒxxxxxxxxx = this.offsets;
      this.suffixToT = new IntArrayList(â˜ƒxxxxxxxx.size());
      this.offsets = new IntArrayList(â˜ƒxxxxxxxxx.size());

      for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxxxxx) {
         int â˜ƒxxxxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxxxxxxx];
         this.suffixToT.add(â˜ƒxxxxxxxx.getInt(â˜ƒxxxxxxxxxxx));
         this.offsets.add(â˜ƒxxxxxxxxx.getInt(â˜ƒxxxxxxxxxxx));
      }

      if (DEBUG_ARRAY) {
         this.print();
      }
   }

   private void print() {
      for(int â˜ƒ = 0; â˜ƒ < this.suffixToT.size(); ++â˜ƒ) {
         LOGGER.debug("{} {}", â˜ƒ, this.getString(â˜ƒ));
      }

      LOGGER.debug("");
   }

   private String getString(int var1) {
      int â˜ƒ = this.offsets.getInt(â˜ƒ);
      int â˜ƒx = this.wordStarts.getInt(this.suffixToT.getInt(â˜ƒ));
      StringBuilder â˜ƒxx = new StringBuilder();

      for(int â˜ƒxxx = 0; â˜ƒx + â˜ƒxxx < this.chars.size(); ++â˜ƒxxx) {
         if (â˜ƒxxx == â˜ƒ) {
            â˜ƒxx.append('^');
         }

         int â˜ƒxxxx = this.chars.get(â˜ƒx + â˜ƒxxx);
         if (â˜ƒxxxx == -1) {
            break;
         }

         â˜ƒxx.append((char)â˜ƒxxxx);
      }

      return â˜ƒxx.toString();
   }

   private int compare(String var1, int var2) {
      int â˜ƒ = this.wordStarts.getInt(this.suffixToT.getInt(â˜ƒ));
      int â˜ƒx = this.offsets.getInt(â˜ƒ);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.length(); ++â˜ƒxx) {
         int â˜ƒxxx = this.chars.getInt(â˜ƒ + â˜ƒx + â˜ƒxx);
         if (â˜ƒxxx == -1) {
            return 1;
         }

         char â˜ƒxxx = â˜ƒ.charAt(â˜ƒxx);
         char â˜ƒxxxx = (char)â˜ƒxxx;
         if (â˜ƒxxx < â˜ƒxxxx) {
            return -1;
         }

         if (â˜ƒxxx > â˜ƒxxxx) {
            return 1;
         }
      }

      return 0;
   }

   public List<T> search(String var1) {
      int â˜ƒ = this.suffixToT.size();
      int â˜ƒx = 0;
      int â˜ƒxx = â˜ƒ;

      while(â˜ƒx < â˜ƒxx) {
         int â˜ƒxxx = â˜ƒx + (â˜ƒxx - â˜ƒx) / 2;
         int â˜ƒxxxx = this.compare(â˜ƒ, â˜ƒxxx);
         if (DEBUG_COMPARISONS) {
            LOGGER.debug("comparing lower \"{}\" with {} \"{}\": {}", â˜ƒ, â˜ƒxxx, this.getString(â˜ƒxxx), â˜ƒxxxx);
         }

         if (â˜ƒxxxx > 0) {
            â˜ƒx = â˜ƒxxx + 1;
         } else {
            â˜ƒxx = â˜ƒxxx;
         }
      }

      if (â˜ƒx >= 0 && â˜ƒx < â˜ƒ) {
         int â˜ƒxxx = â˜ƒx;
         â˜ƒxx = â˜ƒ;

         while(â˜ƒx < â˜ƒxx) {
            int â˜ƒxxxx = â˜ƒx + (â˜ƒxx - â˜ƒx) / 2;
            int â˜ƒxxxxx = this.compare(â˜ƒ, â˜ƒxxxx);
            if (DEBUG_COMPARISONS) {
               LOGGER.debug("comparing upper \"{}\" with {} \"{}\": {}", â˜ƒ, â˜ƒxxxx, this.getString(â˜ƒxxxx), â˜ƒxxxxx);
            }

            if (â˜ƒxxxxx >= 0) {
               â˜ƒx = â˜ƒxxxx + 1;
            } else {
               â˜ƒxx = â˜ƒxxxx;
            }
         }

         int â˜ƒxxxx = â˜ƒx;
         IntSet â˜ƒxxxxx = new IntOpenHashSet();

         for(int â˜ƒxxxxxx = â˜ƒxxx; â˜ƒxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxx) {
            â˜ƒxxxxx.add(this.suffixToT.getInt(â˜ƒxxxxxx));
         }

         int[] â˜ƒxxxxxx = â˜ƒxxxxx.toIntArray();
         java.util.Arrays.sort(â˜ƒxxxxxx);
         Set<T> â˜ƒxxxxxxx = Sets.<T>newLinkedHashSet();

         for(int â˜ƒxxxxxxxx : â˜ƒxxxxxx) {
            â˜ƒxxxxxxx.add(this.list.get(â˜ƒxxxxxxxx));
         }

         return Lists.<T>newArrayList(â˜ƒxxxxxxx);
      } else {
         return Collections.emptyList();
      }
   }
}
