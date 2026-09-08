package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ShufflingList<U> {
   protected final List<ShufflingList.WeightedEntry<U>> entries;
   private final Random random = new Random();

   public ShufflingList() {
      this.entries = Lists.<ShufflingList.WeightedEntry<U>>newArrayList();
   }

   private ShufflingList(List<ShufflingList.WeightedEntry<U>> var1) {
      this.entries = Lists.<ShufflingList.WeightedEntry<U>>newArrayList(â˜ƒ);
   }

   public static <U> Codec<ShufflingList<U>> codec(Codec<U> var0) {
      return ShufflingList.WeightedEntry.codec(â˜ƒ).listOf().xmap(ShufflingList::new, var0x -> var0x.entries);
   }

   public ShufflingList<U> add(U var1, int var2) {
      this.entries.add(new ShufflingList.WeightedEntry<>(â˜ƒ, â˜ƒ));
      return this;
   }

   public ShufflingList<U> shuffle() {
      this.entries.forEach(var1 -> var1.setRandom(this.random.nextFloat()));
      this.entries.sort(Comparator.comparingDouble(ShufflingList.WeightedEntry::getRandWeight));
      return this;
   }

   public Stream<U> stream() {
      return this.entries.stream().map(ShufflingList.WeightedEntry::getData);
   }

   public String toString() {
      return "ShufflingList[" + this.entries + "]";
   }

   public static class WeightedEntry<T> {
      final T data;
      final int weight;
      private double randWeight;

      WeightedEntry(T var1, int var2) {
         this.weight = â˜ƒ;
         this.data = â˜ƒ;
      }

      private double getRandWeight() {
         return this.randWeight;
      }

      void setRandom(float var1) {
         this.randWeight = -Math.pow((double)â˜ƒ, (double)(1.0F / (float)this.weight));
      }

      public T getData() {
         return this.data;
      }

      public int getWeight() {
         return this.weight;
      }

      public String toString() {
         return this.weight + ":" + this.data;
      }

      public static <E> Codec<ShufflingList.WeightedEntry<E>> codec(final Codec<E> var0) {
         return new Codec<ShufflingList.WeightedEntry<E>>() {
            @Override
            public <T> DataResult<Pair<ShufflingList.WeightedEntry<E>, T>> decode(DynamicOps<T> var1, T var2) {
               Dynamic<T> â˜ƒ = new Dynamic<>(â˜ƒ, â˜ƒ);
               return â˜ƒ.get("data")
                  .flatMap(â˜ƒ::parse)
                  .map(var1x -> new ShufflingList.WeightedEntry<>(var1x, â˜ƒ.get("weight").asInt(1)))
                  .map(var1x -> Pair.of(var1x, â˜ƒ.empty()));
            }

            public <T> DataResult<T> encode(ShufflingList.WeightedEntry<E> var1, DynamicOps<T> var2, T var3) {
               return â˜ƒ.mapBuilder().add("weight", â˜ƒ.createInt(â˜ƒ.weight)).add("data", â˜ƒ.encodeStart(â˜ƒ, â˜ƒ.data)).build(â˜ƒ);
            }
         };
      }
   }
}
