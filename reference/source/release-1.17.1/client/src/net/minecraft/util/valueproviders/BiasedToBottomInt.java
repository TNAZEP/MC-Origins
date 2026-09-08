package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import java.util.function.Function;

public class BiasedToBottomInt extends IntProvider {
   public static final Codec<BiasedToBottomInt> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("min_inclusive").forGetter(var0x -> var0x.minInclusive),
                  Codec.INT.fieldOf("max_inclusive").forGetter(var0x -> var0x.maxInclusive)
               )
               .apply(var0, BiasedToBottomInt::new)
      )
      .comapFlatMap(
         var0 -> var0.maxInclusive < var0.minInclusive
               ? DataResult.error("Max must be at least min, min_inclusive: " + var0.minInclusive + ", max_inclusive: " + var0.maxInclusive)
               : DataResult.success(var0),
         Function.identity()
      );
   private final int minInclusive;
   private final int maxInclusive;

   private BiasedToBottomInt(int var1, int var2) {
      this.minInclusive = â˜ƒ;
      this.maxInclusive = â˜ƒ;
   }

   public static BiasedToBottomInt of(int var0, int var1) {
      return new BiasedToBottomInt(â˜ƒ, â˜ƒ);
   }

   @Override
   public int sample(Random var1) {
      return this.minInclusive + â˜ƒ.nextInt(â˜ƒ.nextInt(this.maxInclusive - this.minInclusive + 1) + 1);
   }

   @Override
   public int getMinValue() {
      return this.minInclusive;
   }

   @Override
   public int getMaxValue() {
      return this.maxInclusive;
   }

   @Override
   public IntProviderType<?> getType() {
      return IntProviderType.BIASED_TO_BOTTOM;
   }

   public String toString() {
      return "[" + this.minInclusive + "-" + this.maxInclusive + "]";
   }
}
