package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VeryBiasedToBottomHeight extends HeightProvider {
   public static final Codec<VeryBiasedToBottomHeight> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(var0x -> var0x.minInclusive),
               VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(var0x -> var0x.maxInclusive),
               Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("inner", 1).forGetter(var0x -> var0x.inner)
            )
            .apply(var0, VeryBiasedToBottomHeight::new)
   );
   private static final Logger LOGGER = LogManager.getLogger();
   private final VerticalAnchor minInclusive;
   private final VerticalAnchor maxInclusive;
   private final int inner;

   private VeryBiasedToBottomHeight(VerticalAnchor var1, VerticalAnchor var2, int var3) {
      this.minInclusive = â˜ƒ;
      this.maxInclusive = â˜ƒ;
      this.inner = â˜ƒ;
   }

   public static VeryBiasedToBottomHeight of(VerticalAnchor var0, VerticalAnchor var1, int var2) {
      return new VeryBiasedToBottomHeight(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int sample(Random var1, WorldGenerationContext var2) {
      int â˜ƒ = this.minInclusive.resolveY(â˜ƒ);
      int â˜ƒx = this.maxInclusive.resolveY(â˜ƒ);
      if (â˜ƒx - â˜ƒ - this.inner + 1 <= 0) {
         LOGGER.warn("Empty height range: {}", this);
         return â˜ƒ;
      } else {
         int â˜ƒ = Mth.nextInt(â˜ƒ, â˜ƒ + this.inner, â˜ƒx);
         int â˜ƒx = Mth.nextInt(â˜ƒ, â˜ƒ, â˜ƒ - 1);
         return Mth.nextInt(â˜ƒ, â˜ƒ, â˜ƒx - 1 + this.inner);
      }
   }

   @Override
   public HeightProviderType<?> getType() {
      return HeightProviderType.VERY_BIASED_TO_BOTTOM;
   }

   public String toString() {
      return "biased[" + this.minInclusive + "-" + this.maxInclusive + " inner: " + this.inner + "]";
   }
}
