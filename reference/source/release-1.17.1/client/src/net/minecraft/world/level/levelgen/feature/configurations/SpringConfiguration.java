package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;

public class SpringConfiguration implements FeatureConfiguration {
   public static final Codec<SpringConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               FluidState.CODEC.fieldOf("state").forGetter(var0x -> var0x.state),
               Codec.BOOL.fieldOf("requires_block_below").orElse(true).forGetter(var0x -> var0x.requiresBlockBelow),
               Codec.INT.fieldOf("rock_count").orElse(4).forGetter(var0x -> var0x.rockCount),
               Codec.INT.fieldOf("hole_count").orElse(1).forGetter(var0x -> var0x.holeCount),
               Registry.BLOCK.listOf().fieldOf("valid_blocks").xmap(ImmutableSet::copyOf, ImmutableList::copyOf).forGetter(var0x -> var0x.validBlocks)
            )
            .apply(var0, SpringConfiguration::new)
   );
   public final FluidState state;
   public final boolean requiresBlockBelow;
   public final int rockCount;
   public final int holeCount;
   public final Set<Block> validBlocks;

   public SpringConfiguration(FluidState var1, boolean var2, int var3, int var4, Set<Block> var5) {
      this.state = â˜ƒ;
      this.requiresBlockBelow = â˜ƒ;
      this.rockCount = â˜ƒ;
      this.holeCount = â˜ƒ;
      this.validBlocks = â˜ƒ;
   }
}
