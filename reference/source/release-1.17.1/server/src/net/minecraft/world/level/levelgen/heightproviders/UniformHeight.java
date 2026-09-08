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

public class UniformHeight extends HeightProvider {
   public static final Codec<UniformHeight> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(var0x -> var0x.minInclusive),
               VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(var0x -> var0x.maxInclusive)
            )
            .apply(var0, UniformHeight::new)
   );
   private static final Logger LOGGER = LogManager.getLogger();
   private final VerticalAnchor minInclusive;
   private final VerticalAnchor maxInclusive;

   private UniformHeight(VerticalAnchor var1, VerticalAnchor var2) {
      this.minInclusive = â˜ƒ;
      this.maxInclusive = â˜ƒ;
   }

   public static UniformHeight of(VerticalAnchor var0, VerticalAnchor var1) {
      return new UniformHeight(â˜ƒ, â˜ƒ);
   }

   @Override
   public int sample(Random var1, WorldGenerationContext var2) {
      int â˜ƒ = this.minInclusive.resolveY(â˜ƒ);
      int â˜ƒx = this.maxInclusive.resolveY(â˜ƒ);
      if (â˜ƒ > â˜ƒx) {
         LOGGER.warn("Empty height range: {}", this);
         return â˜ƒ;
      } else {
         return Mth.randomBetweenInclusive(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }

   @Override
   public HeightProviderType<?> getType() {
      return HeightProviderType.UNIFORM;
   }

   public String toString() {
      return "[" + this.minInclusive + "-" + this.maxInclusive + "]";
   }
}
