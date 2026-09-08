package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.core.Direction;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class GrowingPlantConfiguration implements FeatureConfiguration {
   public static final Codec<GrowingPlantConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               SimpleWeightedRandomList.wrappedCodec(IntProvider.CODEC).fieldOf("height_distribution").forGetter(var0x -> var0x.heightDistribution),
               Direction.CODEC.fieldOf("direction").forGetter(var0x -> var0x.direction),
               BlockStateProvider.CODEC.fieldOf("body_provider").forGetter(var0x -> var0x.bodyProvider),
               BlockStateProvider.CODEC.fieldOf("head_provider").forGetter(var0x -> var0x.headProvider),
               Codec.BOOL.fieldOf("allow_water").forGetter(var0x -> var0x.allowWater)
            )
            .apply(var0, GrowingPlantConfiguration::new)
   );
   public final SimpleWeightedRandomList<IntProvider> heightDistribution;
   public final Direction direction;
   public final BlockStateProvider bodyProvider;
   public final BlockStateProvider headProvider;
   public final boolean allowWater;

   public GrowingPlantConfiguration(SimpleWeightedRandomList<IntProvider> var1, Direction var2, BlockStateProvider var3, BlockStateProvider var4, boolean var5) {
      this.heightDistribution = â˜ƒ;
      this.direction = â˜ƒ;
      this.bodyProvider = â˜ƒ;
      this.headProvider = â˜ƒ;
      this.allowWater = â˜ƒ;
   }
}
