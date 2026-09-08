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

public class TrapezoidHeight extends HeightProvider {
   public static final Codec<TrapezoidHeight> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(var0x -> var0x.minInclusive),
               VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(var0x -> var0x.maxInclusive),
               Codec.INT.optionalFieldOf("plateau", Integer.valueOf(0)).forGetter(var0x -> var0x.plateau)
            )
            .apply(var0, TrapezoidHeight::new)
   );
   private static final Logger LOGGER = LogManager.getLogger();
   private final VerticalAnchor minInclusive;
   private final VerticalAnchor maxInclusive;
   private final int plateau;

   private TrapezoidHeight(VerticalAnchor var1, VerticalAnchor var2, int var3) {
      this.minInclusive = â˜ƒ;
      this.maxInclusive = â˜ƒ;
      this.plateau = â˜ƒ;
   }

   public static TrapezoidHeight of(VerticalAnchor var0, VerticalAnchor var1, int var2) {
      return new TrapezoidHeight(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static TrapezoidHeight of(VerticalAnchor var0, VerticalAnchor var1) {
      return of(â˜ƒ, â˜ƒ, 0);
   }

   @Override
   public int sample(Random var1, WorldGenerationContext var2) {
      int â˜ƒ = this.minInclusive.resolveY(â˜ƒ);
      int â˜ƒx = this.maxInclusive.resolveY(â˜ƒ);
      if (â˜ƒ > â˜ƒx) {
         LOGGER.warn("Empty height range: {}", this);
         return â˜ƒ;
      } else {
         int â˜ƒ = â˜ƒx - â˜ƒ;
         if (this.plateau >= â˜ƒ) {
            return Mth.randomBetweenInclusive(â˜ƒ, â˜ƒ, â˜ƒx);
         } else {
            int â˜ƒ = (â˜ƒ - this.plateau) / 2;
            int â˜ƒx = â˜ƒ - â˜ƒ;
            return â˜ƒ + Mth.randomBetweenInclusive(â˜ƒ, 0, â˜ƒx) + Mth.randomBetweenInclusive(â˜ƒ, 0, â˜ƒ);
         }
      }
   }

   @Override
   public HeightProviderType<?> getType() {
      return HeightProviderType.TRAPEZOID;
   }

   public String toString() {
      return this.plateau == 0
         ? "triangle (" + this.minInclusive + "-" + this.maxInclusive + ")"
         : "trapezoid(" + this.plateau + ") in [" + this.minInclusive + "-" + this.maxInclusive + "]";
   }
}
