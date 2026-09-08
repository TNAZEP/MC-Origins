package net.minecraft.client.searchtree;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

public class ReloadableSearchTree<T> extends ReloadableIdSearchTree<T> {
   protected SuffixArray<T> tree = new SuffixArray<>();
   private final Function<T, Stream<String>> filler;

   public ReloadableSearchTree(Function<T, Stream<String>> var1, Function<T, Stream<ResourceLocation>> var2) {
      super(â˜ƒ);
      this.filler = â˜ƒ;
   }

   @Override
   public void refresh() {
      this.tree = new SuffixArray<>();
      super.refresh();
      this.tree.generate();
   }

   @Override
   protected void index(T var1) {
      super.index(â˜ƒ);
      ((Stream)this.filler.apply(â˜ƒ)).forEach(var2 -> this.tree.add(â˜ƒ, var2.toLowerCase(Locale.ROOT)));
   }

   @Override
   public List<T> search(String var1) {
      int â˜ƒ = â˜ƒ.indexOf(58);
      if (â˜ƒ < 0) {
         return this.tree.search(â˜ƒ);
      } else {
         List<T> â˜ƒ = this.namespaceTree.search(â˜ƒ.substring(0, â˜ƒ).trim());
         String â˜ƒx = â˜ƒ.substring(â˜ƒ + 1).trim();
         List<T> â˜ƒxx = this.pathTree.search(â˜ƒx);
         List<T> â˜ƒxxx = this.tree.search(â˜ƒx);
         return Lists.<T>newArrayList(
            new ReloadableIdSearchTree.IntersectionIterator<>(
               â˜ƒ.iterator(),
               new ReloadableSearchTree.MergingUniqueIterator<>(â˜ƒxx.iterator(), â˜ƒxxx.iterator(), this::comparePosition),
               this::comparePosition
            )
         );
      }
   }

   static class MergingUniqueIterator<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> firstIterator;
      private final PeekingIterator<T> secondIterator;
      private final Comparator<T> orderT;

      public MergingUniqueIterator(Iterator<T> var1, Iterator<T> var2, Comparator<T> var3) {
         this.firstIterator = Iterators.peekingIterator(â˜ƒ);
         this.secondIterator = Iterators.peekingIterator(â˜ƒ);
         this.orderT = â˜ƒ;
      }

      @Override
      protected T computeNext() {
         boolean â˜ƒ = !this.firstIterator.hasNext();
         boolean â˜ƒx = !this.secondIterator.hasNext();
         if (â˜ƒ && â˜ƒx) {
            return this.endOfData();
         } else if (â˜ƒ) {
            return this.secondIterator.next();
         } else if (â˜ƒx) {
            return this.firstIterator.next();
         } else {
            int â˜ƒ = this.orderT.compare(this.firstIterator.peek(), this.secondIterator.peek());
            if (â˜ƒ == 0) {
               this.secondIterator.next();
            }

            return (T)(â˜ƒ <= 0 ? this.firstIterator.next() : this.secondIterator.next());
         }
      }
   }
}
