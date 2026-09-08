package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;

public class DiskConfiguration implements FeatureConfiguration {
   public static final Codec<DiskConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               BlockState.CODEC.fieldOf("state").forGetter(var0x -> var0x.state),
               IntProvider.codec(0, 8).fieldOf("radius").forGetter(var0x -> var0x.radius),
               Codec.intRange(0, 4).fieldOf("half_height").forGetter(var0x -> var0x.halfHeight),
               BlockState.CODEC.listOf().fieldOf("targets").forGetter(var0x -> var0x.targets)
            )
            .apply(var0, DiskConfiguration::new)
   );
   public final BlockState state;
   public final IntProvider radius;
   public final int halfHeight;
   public final List<BlockState> targets;

   public DiskConfiguration(BlockState var1, IntProvider var2, int var3, List<BlockState> var4) {
      this.state = â˜ƒ;
      this.radius = â˜ƒ;
      this.halfHeight = â˜ƒ;
      this.targets = â˜ƒ;
   }
}
