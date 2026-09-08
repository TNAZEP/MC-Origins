package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.Registry;

public abstract class IntProvider {
   private static final Codec<Either<Integer, IntProvider>> CONSTANT_OR_DISPATCH_CODEC = Codec.either(
      Codec.INT, Registry.INT_PROVIDER_TYPES.dispatch(IntProvider::getType, IntProviderType::codec)
   );
   public static final Codec<IntProvider> CODEC = CONSTANT_OR_DISPATCH_CODEC.xmap(
      var0 -> var0.map(ConstantInt::of, var0x -> var0x),
      var0 -> var0.getType() == IntProviderType.CONSTANT ? Either.left(((ConstantInt)var0).getValue()) : Either.right(var0)
   );
   public static final Codec<IntProvider> NON_NEGATIVE_CODEC = codec(0, Integer.MAX_VALUE);
   public static final Codec<IntProvider> POSITIVE_CODEC = codec(1, Integer.MAX_VALUE);

   public static Codec<IntProvider> codec(int var0, int var1) {
      Function<IntProvider, DataResult<IntProvider>> â˜ƒ = var2x -> {
         if (var2x.getMinValue() < â˜ƒ) {
            return DataResult.error("Value provider too low: " + â˜ƒ + " [" + var2x.getMinValue() + "-" + var2x.getMaxValue() + "]");
         } else {
            return var2x.getMaxValue() > â˜ƒ
               ? DataResult.error("Value provider too high: " + â˜ƒ + " [" + var2x.getMinValue() + "-" + var2x.getMaxValue() + "]")
               : DataResult.success(var2x);
         }
      };
      return CODEC.flatXmap(â˜ƒ, â˜ƒ);
   }

   public abstract int sample(Random var1);

   public abstract int getMinValue();

   public abstract int getMaxValue();

   public abstract IntProviderType<?> getType();
}
