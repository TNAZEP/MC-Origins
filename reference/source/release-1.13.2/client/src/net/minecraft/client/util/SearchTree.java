package net.minecraft.client.util;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.util.ResourceLocation;

public class SearchTree<T> implements ISearchTree<T> {
   protected SuffixArray<T> field_194044_a = new SuffixArray<>();
   protected SuffixArray<T> field_195834_b = new SuffixArray<>();
   protected SuffixArray<T> field_195835_c = new SuffixArray<>();
   private final Function<T, Iterable<String>> field_194046_c;
   private final Function<T, Iterable<ResourceLocation>> field_194047_d;
   private final List<T> field_194048_e = Lists.<T>newArrayList();
   private final Object2IntMap<T> field_194049_f = new Object2IntOpenHashMap<>();

   public SearchTree(Function<T, Iterable<String>> var1, Function<T, Iterable<ResourceLocation>> var2) {
      this.field_194046_c = ☃;
      this.field_194047_d = ☃;
   }

   public void func_194040_a() {
      this.field_194044_a = new SuffixArray<>();
      this.field_195834_b = new SuffixArray<>();
      this.field_195835_c = new SuffixArray<>();

      for(T ☃ : this.field_194048_e) {
         this.func_194042_b(☃);
      }

      this.field_194044_a.func_194058_a();
      this.field_195834_b.func_194058_a();
      this.field_195835_c.func_194058_a();
   }

   public void func_194043_a(T var1) {
      this.field_194049_f.put(☃, this.field_194048_e.size());
      this.field_194048_e.add(☃);
      this.func_194042_b(☃);
   }

   public void func_199550_b() {
      this.field_194048_e.clear();
      this.field_194049_f.clear();
   }

   private void func_194042_b(T var1) {
      ((Iterable)this.field_194047_d.apply(☃)).forEach(var2 -> {
         this.field_195834_b.func_194057_a(☃, var2.func_110624_b().toLowerCase(Locale.ROOT));
         this.field_195835_c.func_194057_a(☃, var2.func_110623_a().toLowerCase(Locale.ROOT));
      });
      ((Iterable)this.field_194046_c.apply(☃)).forEach(var2 -> this.field_194044_a.func_194057_a(☃, var2.toLowerCase(Locale.ROOT)));
   }

   @Override
   public List<T> func_194038_a(String var1) {
      int ☃ = ☃.indexOf(58);
      if (☃ < 0) {
         return this.field_194044_a.func_194055_a(☃);
      } else {
         List<T> ☃ = this.field_195834_b.func_194055_a(☃.substring(0, ☃).trim());
         String ☃x = ☃.substring(☃ + 1, ☃.length()).trim();
         List<T> ☃xx = this.field_195835_c.func_194055_a(☃x);
         List<T> ☃xxx = this.field_194044_a.func_194055_a(☃x);
         return Lists.<T>newArrayList(
            new SearchTree.IntersectingIterator<>(
               ☃.iterator(), new SearchTree.MergingIterator<>(☃xx.iterator(), ☃xxx.iterator(), this.field_194049_f), this.field_194049_f
            )
         );
      }
   }

   static class IntersectingIterator<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> field_195831_a;
      private final PeekingIterator<T> field_195832_b;
      private final Object2IntMap<T> field_195833_c;

      public IntersectingIterator(Iterator<T> var1, Iterator<T> var2, Object2IntMap<T> var3) {
         this.field_195831_a = Iterators.peekingIterator(☃);
         this.field_195832_b = Iterators.peekingIterator(☃);
         this.field_195833_c = ☃;
      }

      @Override
      protected T computeNext() {
         while(this.field_195831_a.hasNext() && this.field_195832_b.hasNext()) {
            int ☃ = Integer.compare(this.field_195833_c.getInt(this.field_195831_a.peek()), this.field_195833_c.getInt(this.field_195832_b.peek()));
            if (☃ == 0) {
               this.field_195832_b.next();
               return this.field_195831_a.next();
            }

            if (☃ < 0) {
               this.field_195831_a.next();
            } else {
               this.field_195832_b.next();
            }
         }

         return this.endOfData();
      }
   }

   static class MergingIterator<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> field_194033_a;
      private final PeekingIterator<T> field_194034_b;
      private final Object2IntMap<T> field_194035_c;

      public MergingIterator(Iterator<T> var1, Iterator<T> var2, Object2IntMap<T> var3) {
         this.field_194033_a = Iterators.peekingIterator(☃);
         this.field_194034_b = Iterators.peekingIterator(☃);
         this.field_194035_c = ☃;
      }

      @Override
      protected T computeNext() {
         boolean ☃ = !this.field_194033_a.hasNext();
         boolean ☃x = !this.field_194034_b.hasNext();
         if (☃ && ☃x) {
            return this.endOfData();
         } else if (☃) {
            return this.field_194034_b.next();
         } else if (☃x) {
            return this.field_194033_a.next();
         } else {
            int ☃ = Integer.compare(this.field_194035_c.getInt(this.field_194033_a.peek()), this.field_194035_c.getInt(this.field_194034_b.peek()));
            if (☃ == 0) {
               this.field_194034_b.next();
            }

            return (T)(☃ <= 0 ? this.field_194033_a.next() : this.field_194034_b.next());
         }
      }
   }
}
