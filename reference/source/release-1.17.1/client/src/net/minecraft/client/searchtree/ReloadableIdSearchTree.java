package net.minecraft.client.searchtree;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

public class ReloadableIdSearchTree<T> implements MutableSearchTree<T> {
   protected SuffixArray<T> namespaceTree = new SuffixArray<>();
   protected SuffixArray<T> pathTree = new SuffixArray<>();
   private final Function<T, Stream<ResourceLocation>> idGetter;
   private final List<T> contents = Lists.<T>newArrayList();
   private final Object2IntMap<T> orderT = new Object2IntOpenHashMap<>();

   public ReloadableIdSearchTree(Function<T, Stream<ResourceLocation>> var1) {
      this.idGetter = â˜ƒ;
   }

   @Override
   public void refresh() {
      this.namespaceTree = new SuffixArray<>();
      this.pathTree = new SuffixArray<>();

      for(T â˜ƒ : this.contents) {
         this.index(â˜ƒ);
      }

      this.namespaceTree.generate();
      this.pathTree.generate();
   }

   @Override
   public void add(T var1) {
      this.orderT.put(â˜ƒ, this.contents.size());
      this.contents.add(â˜ƒ);
      this.index(â˜ƒ);
   }

   @Override
   public void clear() {
      this.contents.clear();
      this.orderT.clear();
   }

   protected void index(T var1) {
      ((Stream)this.idGetter.apply(â˜ƒ)).forEach(var2 -> {
         this.namespaceTree.add(â˜ƒ, var2.getNamespace().toLowerCase(Locale.ROOT));
         this.pathTree.add(â˜ƒ, var2.getPath().toLowerCase(Locale.ROOT));
      });
   }

   protected int comparePosition(T var1, T var2) {
      return Integer.compare(this.orderT.getInt(â˜ƒ), this.orderT.getInt(â˜ƒ));
   }

   @Override
   public List<T> search(String var1) {
      int â˜ƒ = â˜ƒ.indexOf(58);
      if (â˜ƒ == -1) {
         return this.pathTree.search(â˜ƒ);
      } else {
         List<T> â˜ƒ = this.namespaceTree.search(â˜ƒ.substring(0, â˜ƒ).trim());
         String â˜ƒx = â˜ƒ.substring(â˜ƒ + 1).trim();
         List<T> â˜ƒxx = this.pathTree.search(â˜ƒx);
         return Lists.<T>newArrayList(new ReloadableIdSearchTree.IntersectionIterator<>(â˜ƒ.iterator(), â˜ƒxx.iterator(), this::comparePosition));
      }
   }

   protected static class IntersectionIterator<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> firstIterator;
      private final PeekingIterator<T> secondIterator;
      private final Comparator<T> orderT;

      public IntersectionIterator(Iterator<T> var1, Iterator<T> var2, Comparator<T> var3) {
         this.firstIterator = Iterators.peekingIterator(â˜ƒ);
         this.secondIterator = Iterators.peekingIterator(â˜ƒ);
         this.orderT = â˜ƒ;
      }

      @Override
      protected T computeNext() {
         while(this.firstIterator.hasNext() && this.secondIterator.hasNext()) {
            int â˜ƒ = this.orderT.compare(this.firstIterator.peek(), this.secondIterator.peek());
            if (â˜ƒ == 0) {
               this.secondIterator.next();
               return this.firstIterator.next();
            }

            if (â˜ƒ < 0) {
               this.firstIterator.next();
            } else {
               this.secondIterator.next();
            }
         }

         return this.endOfData();
      }
   }
}
