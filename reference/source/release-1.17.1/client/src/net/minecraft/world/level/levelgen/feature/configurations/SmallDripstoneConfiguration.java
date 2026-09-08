package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;

public class SmallDripstoneConfiguration implements FeatureConfiguration {
   public static final Codec<SmallDripstoneConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, 100).fieldOf("max_placements").orElse(5).forGetter(var0x -> var0x.maxPlacements),
               Codec.intRange(0, 20).fieldOf("empty_space_search_radius").orElse(10).forGetter(var0x -> var0x.emptySpaceSearchRadius),
               Codec.intRange(0, 20).fieldOf("max_offset_from_origin").orElse(2).forGetter(var0x -> var0x.maxOffsetFromOrigin),
               Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter(var0x -> var0x.chanceOfTallerDripstone)
            )
            .apply(var0, SmallDripstoneConfiguration::new)
   );
   public final int maxPlacements;
   public final int emptySpaceSearchRadius;
   public final int maxOffsetFromOrigin;
   public final float chanceOfTallerDripstone;

   public SmallDripstoneConfiguration(int var1, int var2, int var3, float var4) {
      this.maxPlacements = â˜ƒ;
      this.emptySpaceSearchRadius = â˜ƒ;
      this.maxOffsetFromOrigin = â˜ƒ;
      this.chanceOfTallerDripstone = â˜ƒ;
   }
}
