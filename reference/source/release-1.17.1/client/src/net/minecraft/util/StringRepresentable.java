package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface StringRepresentable {
   String getSerializedName();

   static <E extends Enum<E> & StringRepresentable> Codec<E> fromEnum(Supplier<E[]> var0, Function<? super String, ? extends E> var1) {
      E[] â˜ƒ = (E[])â˜ƒ.get();
      return fromStringResolver(var0x -> ((Enum)var0x).ordinal(), var1x -> â˜ƒ[var1x], â˜ƒ);
   }

   static <E extends StringRepresentable> Codec<E> fromStringResolver(
      final ToIntFunction<E> var0, final IntFunction<E> var1, final Function<? super String, ? extends E> var2
   ) {
      return new Codec<E>() {
         public <T> DataResult<T> encode(E var1x, DynamicOps<T> var2x, T var3) {
            return â˜ƒ.compressMaps()
               ? â˜ƒ.mergeToPrimitive(â˜ƒ, â˜ƒ.createInt(â˜ƒ.applyAsInt(â˜ƒ)))
               : â˜ƒ.mergeToPrimitive(â˜ƒ, â˜ƒ.createString(â˜ƒ.getSerializedName()));
         }

         @Override
         public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> var1x, T var2x) {
            return â˜ƒ.compressMaps()
               ? â˜ƒ.getNumberValue(â˜ƒ)
                  .flatMap(
                     var1xx -> (DataResult)Optional.ofNullable((StringRepresentable)â˜ƒ.apply(var1xx.intValue()))
                           .map(DataResult::success)
                           .orElseGet(() -> DataResult.error("Unknown element id: " + var1xx))
                  )
                  .map(var1xx -> Pair.of(var1xx, â˜ƒ.empty()))
               : â˜ƒ.getStringValue(â˜ƒ)
                  .flatMap(
                     var1xx -> (DataResult)Optional.ofNullable((StringRepresentable)â˜ƒ.apply(var1xx))
                           .map(DataResult::success)
                           .orElseGet(() -> DataResult.error("Unknown element name: " + var1xx))
                  )
                  .map(var1xx -> Pair.of(var1xx, â˜ƒ.empty()));
         }

         public String toString() {
            return "StringRepresentable[" + â˜ƒ + "]";
         }
      };
   }

   static Keyable keys(final StringRepresentable[] var0) {
      return new Keyable() {
         @Override
         public <T> Stream<T> keys(DynamicOps<T> var1) {
            return â˜ƒ.compressMaps()
               ? IntStream.range(0, â˜ƒ.length).mapToObj(â˜ƒ::createInt)
               : Arrays.stream(â˜ƒ).map(StringRepresentable::getSerializedName).map(â˜ƒ::createString);
         }
      };
   }
}
