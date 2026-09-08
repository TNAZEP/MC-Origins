package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class ConstantHeight extends HeightProvider {
   public static final ConstantHeight ZERO = new ConstantHeight(VerticalAnchor.absolute(0));
   public static final Codec<ConstantHeight> CODEC = Codec.either(
         VerticalAnchor.CODEC,
         RecordCodecBuilder.create(var0 -> var0.group(VerticalAnchor.CODEC.fieldOf("value").forGetter(var0x -> var0x.value)).apply(var0, ConstantHeight::new))
      )
      .xmap(var0 -> var0.map(ConstantHeight::of, var0x -> var0x), var0 -> Either.left(var0.value));
   private final VerticalAnchor value;

   public static ConstantHeight of(VerticalAnchor var0) {
      return new ConstantHeight(â˜ƒ);
   }

   private ConstantHeight(VerticalAnchor var1) {
      this.value = â˜ƒ;
   }

   public VerticalAnchor getValue() {
      return this.value;
   }

   @Override
   public int sample(Random var1, WorldGenerationContext var2) {
      return this.value.resolveY(â˜ƒ);
   }

   @Override
   public HeightProviderType<?> getType() {
      return HeightProviderType.CONSTANT;
   }

   public String toString() {
      return this.value.toString();
   }
}
