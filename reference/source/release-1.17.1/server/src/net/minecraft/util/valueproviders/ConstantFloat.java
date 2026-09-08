package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;

public class ConstantFloat extends FloatProvider {
   public static final ConstantFloat ZERO = new ConstantFloat(0.0F);
   public static final Codec<ConstantFloat> CODEC = Codec.either(
         Codec.FLOAT,
         RecordCodecBuilder.create(var0 -> var0.group(Codec.FLOAT.fieldOf("value").forGetter(var0x -> var0x.value)).apply(var0, ConstantFloat::new))
      )
      .xmap(var0 -> var0.map(ConstantFloat::of, var0x -> var0x), var0 -> Either.left(var0.value));
   private final float value;

   public static ConstantFloat of(float var0) {
      return â˜ƒ == 0.0F ? ZERO : new ConstantFloat(â˜ƒ);
   }

   private ConstantFloat(float var1) {
      this.value = â˜ƒ;
   }

   public float getValue() {
      return this.value;
   }

   @Override
   public float sample(Random var1) {
      return this.value;
   }

   @Override
   public float getMinValue() {
      return this.value;
   }

   @Override
   public float getMaxValue() {
      return this.value + 1.0F;
   }

   @Override
   public FloatProviderType<?> getType() {
      return FloatProviderType.CONSTANT;
   }

   public String toString() {
      return Float.toString(this.value);
   }
}
