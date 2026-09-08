package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class OreConfiguration implements FeatureConfiguration {
   public static final Codec<OreConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.list(OreConfiguration.TargetBlockState.CODEC).fieldOf("targets").forGetter(var0x -> var0x.targetStates),
               Codec.intRange(0, 64).fieldOf("size").forGetter(var0x -> var0x.size),
               Codec.floatRange(0.0F, 1.0F).fieldOf("discard_chance_on_air_exposure").forGetter(var0x -> var0x.discardChanceOnAirExposure)
            )
            .apply(var0, OreConfiguration::new)
   );
   public final List<OreConfiguration.TargetBlockState> targetStates;
   public final int size;
   public final float discardChanceOnAirExposure;

   public OreConfiguration(List<OreConfiguration.TargetBlockState> var1, int var2, float var3) {
      this.size = â˜ƒ;
      this.targetStates = â˜ƒ;
      this.discardChanceOnAirExposure = â˜ƒ;
   }

   public OreConfiguration(List<OreConfiguration.TargetBlockState> var1, int var2) {
      this(â˜ƒ, â˜ƒ, 0.0F);
   }

   public OreConfiguration(RuleTest var1, BlockState var2, int var3, float var4) {
      this(ImmutableList.of(new OreConfiguration.TargetBlockState(â˜ƒ, â˜ƒ)), â˜ƒ, â˜ƒ);
   }

   public OreConfiguration(RuleTest var1, BlockState var2, int var3) {
      this(ImmutableList.of(new OreConfiguration.TargetBlockState(â˜ƒ, â˜ƒ)), â˜ƒ, 0.0F);
   }

   public static OreConfiguration.TargetBlockState target(RuleTest var0, BlockState var1) {
      return new OreConfiguration.TargetBlockState(â˜ƒ, â˜ƒ);
   }

   public static final class Predicates {
      public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
      public static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
      public static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
      public static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
      public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
   }

   public static class TargetBlockState {
      public static final Codec<OreConfiguration.TargetBlockState> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  RuleTest.CODEC.fieldOf("target").forGetter(var0x -> var0x.target), BlockState.CODEC.fieldOf("state").forGetter(var0x -> var0x.state)
               )
               .apply(var0, OreConfiguration.TargetBlockState::new)
      );
      public final RuleTest target;
      public final BlockState state;

      TargetBlockState(RuleTest var1, BlockState var2) {
         this.target = â˜ƒ;
         this.state = â˜ƒ;
      }
   }
}
