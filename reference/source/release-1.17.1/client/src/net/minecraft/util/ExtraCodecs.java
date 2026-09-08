package net.minecraft.util;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExtraCodecs {
   public static final Codec<Integer> NON_NEGATIVE_INT = intRangeWithMessage(0, Integer.MAX_VALUE, var0 -> "Value must be non-negative: " + var0);
   public static final Codec<Integer> POSITIVE_INT = intRangeWithMessage(1, Integer.MAX_VALUE, var0 -> "Value must be positive: " + var0);

   public static <F, S> Codec<Either<F, S>> xor(Codec<F> var0, Codec<S> var1) {
      return new ExtraCodecs.XorCodec<>(â˜ƒ, â˜ƒ);
   }

   private static <N extends Number & Comparable<N>> Function<N, DataResult<N>> checkRangeWithMessage(N var0, N var1, Function<N, String> var2) {
      return var3 -> ((Comparable)var3).compareTo(â˜ƒ) >= 0 && ((Comparable)var3).compareTo(â˜ƒ) <= 0
            ? DataResult.success(var3)
            : DataResult.error((String)â˜ƒ.apply(var3));
   }

   private static Codec<Integer> intRangeWithMessage(int var0, int var1, Function<Integer, String> var2) {
      Function<Integer, DataResult<Integer>> â˜ƒ = checkRangeWithMessage(â˜ƒ, â˜ƒ, â˜ƒ);
      return Codec.INT.flatXmap(â˜ƒ, â˜ƒ);
   }

   public static <T> Function<List<T>, DataResult<List<T>>> nonEmptyListCheck() {
      return var0 -> var0.isEmpty() ? DataResult.error("List must have contents") : DataResult.success(var0);
   }

   public static <T> Codec<List<T>> nonEmptyList(Codec<List<T>> var0) {
      return â˜ƒ.flatXmap(nonEmptyListCheck(), nonEmptyListCheck());
   }

   public static <T> Function<List<Supplier<T>>, DataResult<List<Supplier<T>>>> nonNullSupplierListCheck() {
      return var0 -> {
         List<String> â˜ƒ = Lists.newArrayList();

         for(int â˜ƒx = 0; â˜ƒx < var0.size(); ++â˜ƒx) {
            Supplier<T> â˜ƒxx = (Supplier)var0.get(â˜ƒx);

            try {
               if (â˜ƒxx.get() == null) {
                  â˜ƒ.add("Missing value [" + â˜ƒx + "] : " + â˜ƒxx);
               }
            } catch (Exception var5) {
               â˜ƒ.add("Invalid value [" + â˜ƒx + "]: " + â˜ƒxx + ", message: " + var5.getMessage());
            }
         }

         return !â˜ƒ.isEmpty() ? DataResult.error(String.join("; ", â˜ƒ)) : DataResult.success(var0, Lifecycle.stable());
      };
   }

   public static <T> Function<Supplier<T>, DataResult<Supplier<T>>> nonNullSupplierCheck() {
      return var0 -> {
         try {
            if (var0.get() == null) {
               return DataResult.error("Missing value: " + var0);
            }
         } catch (Exception var2) {
            return DataResult.error("Invalid value: " + var0 + ", message: " + var2.getMessage());
         }

         return DataResult.success(var0, Lifecycle.stable());
      };
   }

   static final class XorCodec<F, S> implements Codec<Either<F, S>> {
      private final Codec<F> first;
      private final Codec<S> second;

      public XorCodec(Codec<F> var1, Codec<S> var2) {
         this.first = â˜ƒ;
         this.second = â˜ƒ;
      }

      @Override
      public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> var1, T var2) {
         DataResult<Pair<Either<F, S>, T>> â˜ƒ = this.first.decode(â˜ƒ, â˜ƒ).map(var0 -> var0.mapFirst(Either::left));
         DataResult<Pair<Either<F, S>, T>> â˜ƒx = this.second.decode(â˜ƒ, â˜ƒ).map(var0 -> var0.mapFirst(Either::right));
         Optional<Pair<Either<F, S>, T>> â˜ƒxx = â˜ƒ.result();
         Optional<Pair<Either<F, S>, T>> â˜ƒxxx = â˜ƒx.result();
         if (â˜ƒxx.isPresent() && â˜ƒxxx.isPresent()) {
            return DataResult.error(
               "Both alternatives read successfully, can not pick the correct one; first: " + â˜ƒxx.get() + " second: " + â˜ƒxxx.get(),
               (Pair<Either<F, S>, T>)â˜ƒxx.get()
            );
         } else {
            return â˜ƒxx.isPresent() ? â˜ƒ : â˜ƒx;
         }
      }

      public <T> DataResult<T> encode(Either<F, S> var1, DynamicOps<T> var2, T var3) {
         return â˜ƒ.map(var3x -> this.first.encode((F)var3x, â˜ƒ, â˜ƒ), var3x -> this.second.encode((S)var3x, â˜ƒ, â˜ƒ));
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
            ExtraCodecs.XorCodec<?, ?> â˜ƒ = (ExtraCodecs.XorCodec)â˜ƒ;
            return Objects.equals(this.first, â˜ƒ.first) && Objects.equals(this.second, â˜ƒ.second);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.first, this.second});
      }

      public String toString() {
         return "XorCodec[" + this.first + ", " + this.second + "]";
      }
   }
}
