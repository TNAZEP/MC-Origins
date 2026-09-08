package net.minecraft.util.random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class WeightedRandomList<E extends WeightedEntry> {
   private final int totalWeight;
   private final ImmutableList<E> items;

   WeightedRandomList(List<? extends E> var1) {
      this.items = ImmutableList.copyOf(â˜ƒ);
      this.totalWeight = WeightedRandom.getTotalWeight(â˜ƒ);
   }

   public static <E extends WeightedEntry> WeightedRandomList<E> create() {
      return new WeightedRandomList<>(ImmutableList.of());
   }

   @SafeVarargs
   public static <E extends WeightedEntry> WeightedRandomList<E> create(E... var0) {
      return new WeightedRandomList<>(ImmutableList.copyOf(â˜ƒ));
   }

   public static <E extends WeightedEntry> WeightedRandomList<E> create(List<E> var0) {
      return new WeightedRandomList<>(â˜ƒ);
   }

   public boolean isEmpty() {
      return this.items.isEmpty();
   }

   public Optional<E> getRandom(Random var1) {
      if (this.totalWeight == 0) {
         return Optional.empty();
      } else {
         int â˜ƒ = â˜ƒ.nextInt(this.totalWeight);
         return WeightedRandom.getWeightedItem(this.items, â˜ƒ);
      }
   }

   public List<E> unwrap() {
      return this.items;
   }

   public static <E extends WeightedEntry> Codec<WeightedRandomList<E>> codec(Codec<E> var0) {
      return â˜ƒ.listOf().xmap(WeightedRandomList::create, WeightedRandomList::unwrap);
   }
}
