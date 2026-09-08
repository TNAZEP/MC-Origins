package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.Registry;

public abstract class FloatProvider {
   private static final Codec<Either<Float, FloatProvider>> CONSTANT_OR_DISPATCH_CODEC = Codec.either(
      Codec.FLOAT, Registry.FLOAT_PROVIDER_TYPES.dispatch(FloatProvider::getType, FloatProviderType::codec)
   );
   public static final Codec<FloatProvider> CODEC = CONSTANT_OR_DISPATCH_CODEC.xmap(
      var0 -> var0.map(ConstantFloat::of, var0x -> var0x),
      var0 -> var0.getType() == FloatProviderType.CONSTANT ? Either.left(((ConstantFloat)var0).getValue()) : Either.right(var0)
   );

   public static Codec<FloatProvider> codec(float var0, float var1) {
      Function<FloatProvider, DataResult<FloatProvider>> â˜ƒ = var2x -> {
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

   public abstract float sample(Random var1);

   public abstract float getMinValue();

   public abstract float getMaxValue();

   public abstract FloatProviderType<?> getType();
}
