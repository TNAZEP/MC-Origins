package net.minecraft.util.random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SimpleWeightedRandomList<E> extends WeightedRandomList<WeightedEntry.Wrapper<E>> {
   public static <E> Codec<SimpleWeightedRandomList<E>> wrappedCodec(Codec<E> var0) {
      return WeightedEntry.Wrapper.codec(â˜ƒ).listOf().xmap(SimpleWeightedRandomList::new, WeightedRandomList::unwrap);
   }

   SimpleWeightedRandomList(List<? extends WeightedEntry.Wrapper<E>> var1) {
      super(â˜ƒ);
   }

   public static <E> SimpleWeightedRandomList.Builder<E> builder() {
      return new SimpleWeightedRandomList.Builder<>();
   }

   public Optional<E> getRandomValue(Random var1) {
      return this.getRandom(â˜ƒ).map(WeightedEntry.Wrapper::getData);
   }

   public static class Builder<E> {
      private final ImmutableList.Builder<WeightedEntry.Wrapper<E>> result = ImmutableList.builder();

      public SimpleWeightedRandomList.Builder<E> add(E var1, int var2) {
         this.result.add(WeightedEntry.wrap(â˜ƒ, â˜ƒ));
         return this;
      }

      public SimpleWeightedRandomList<E> build() {
         return new SimpleWeightedRandomList<>(this.result.build());
      }
   }
}
