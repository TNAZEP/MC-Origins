package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import java.util.function.Function;

public class TrapezoidFloat extends FloatProvider {
   public static final Codec<TrapezoidFloat> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.FLOAT.fieldOf("min").forGetter(var0x -> var0x.min),
                  Codec.FLOAT.fieldOf("max").forGetter(var0x -> var0x.max),
                  Codec.FLOAT.fieldOf("plateau").forGetter(var0x -> var0x.plateau)
               )
               .apply(var0, TrapezoidFloat::new)
      )
      .comapFlatMap(
         var0 -> {
            if (var0.max < var0.min) {
               return DataResult.error("Max must be larger than min: [" + var0.min + ", " + var0.max + "]");
            } else {
               return var0.plateau > var0.max - var0.min
                  ? DataResult.error("Plateau can at most be the full span: [" + var0.min + ", " + var0.max + "]")
                  : DataResult.success(var0);
            }
         },
         Function.identity()
      );
   private final float min;
   private final float max;
   private final float plateau;

   public static TrapezoidFloat of(float var0, float var1, float var2) {
      return new TrapezoidFloat(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private TrapezoidFloat(float var1, float var2, float var3) {
      this.min = â˜ƒ;
      this.max = â˜ƒ;
      this.plateau = â˜ƒ;
   }

   @Override
   public float sample(Random var1) {
      float â˜ƒ = this.max - this.min;
      float â˜ƒx = (â˜ƒ - this.plateau) / 2.0F;
      float â˜ƒxx = â˜ƒ - â˜ƒx;
      return this.min + â˜ƒ.nextFloat() * â˜ƒxx + â˜ƒ.nextFloat() * â˜ƒx;
   }

   @Override
   public float getMinValue() {
      return this.min;
   }

   @Override
   public float getMaxValue() {
      return this.max;
   }

   @Override
   public FloatProviderType<?> getType() {
      return FloatProviderType.TRAPEZOID;
   }

   public String toString() {
      return "trapezoid(" + this.plateau + ") in [" + this.min + "-" + this.max + "]";
   }
}
