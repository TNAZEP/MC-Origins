package net.minecraft.util.random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;

public interface WeightedEntry {
   Weight getWeight();

   static <T> WeightedEntry.Wrapper<T> wrap(T var0, int var1) {
      return new WeightedEntry.Wrapper<>(â˜ƒ, Weight.of(â˜ƒ));
   }

   public static class IntrusiveBase implements WeightedEntry {
      private final Weight weight;

      public IntrusiveBase(int var1) {
         this.weight = Weight.of(â˜ƒ);
      }

      public IntrusiveBase(Weight var1) {
         this.weight = â˜ƒ;
      }

      @Override
      public Weight getWeight() {
         return this.weight;
      }
   }

   public static class Wrapper<T> implements WeightedEntry {
      private final T data;
      private final Weight weight;

      Wrapper(T var1, Weight var2) {
         this.data = â˜ƒ;
         this.weight = â˜ƒ;
      }

      public T getData() {
         return this.data;
      }

      @Override
      public Weight getWeight() {
         return this.weight;
      }

      public static <E> Codec<WeightedEntry.Wrapper<E>> codec(Codec<E> var0) {
         return RecordCodecBuilder.create(
            var1 -> var1.group(
                     â˜ƒ.fieldOf("data").forGetter(WeightedEntry.Wrapper::getData), Weight.CODEC.fieldOf("weight").forGetter(WeightedEntry.Wrapper::getWeight)
                  )
                  .apply(var1, WeightedEntry.Wrapper::new)
         );
      }
   }
}
