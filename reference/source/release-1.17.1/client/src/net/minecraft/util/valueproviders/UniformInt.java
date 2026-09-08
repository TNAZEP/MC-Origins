package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.Mth;

public class UniformInt extends IntProvider {
   public static final Codec<UniformInt> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("min_inclusive").forGetter(var0x -> var0x.minInclusive),
                  Codec.INT.fieldOf("max_inclusive").forGetter(var0x -> var0x.maxInclusive)
               )
               .apply(var0, UniformInt::new)
      )
      .comapFlatMap(
         var0 -> var0.maxInclusive < var0.minInclusive
               ? DataResult.error("Max must be at least min, min_inclusive: " + var0.minInclusive + ", max_inclusive: " + var0.maxInclusive)
               : DataResult.success(var0),
         Function.identity()
      );
   private final int minInclusive;
   private final int maxInclusive;

   private UniformInt(int var1, int var2) {
      this.minInclusive = â˜ƒ;
      this.maxInclusive = â˜ƒ;
   }

   public static UniformInt of(int var0, int var1) {
      return new UniformInt(â˜ƒ, â˜ƒ);
   }

   @Override
   public int sample(Random var1) {
      return Mth.randomBetweenInclusive(â˜ƒ, this.minInclusive, this.maxInclusive);
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
      return IntProviderType.UNIFORM;
   }

   public String toString() {
      return "[" + this.minInclusive + "-" + this.maxInclusive + "]";
   }
}
