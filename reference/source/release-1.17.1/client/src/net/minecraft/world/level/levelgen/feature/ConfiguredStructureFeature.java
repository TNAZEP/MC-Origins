package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class ConfiguredStructureFeature<FC extends FeatureConfiguration, F extends StructureFeature<FC>> {
   public static final Codec<ConfiguredStructureFeature<?, ?>> DIRECT_CODEC = Registry.STRUCTURE_FEATURE
      .dispatch(var0 -> var0.feature, StructureFeature::configuredStructureCodec);
   public static final Codec<Supplier<ConfiguredStructureFeature<?, ?>>> CODEC = RegistryFileCodec.create(
      Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, DIRECT_CODEC
   );
   public static final Codec<List<Supplier<ConfiguredStructureFeature<?, ?>>>> LIST_CODEC = RegistryFileCodec.homogeneousList(
      Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, DIRECT_CODEC
   );
   public final F feature;
   public final FC config;

   public ConfiguredStructureFeature(F var1, FC var2) {
      this.feature = â˜ƒ;
      this.config = â˜ƒ;
   }

   public StructureStart<?> generate(
      RegistryAccess var1,
      ChunkGenerator var2,
      BiomeSource var3,
      StructureManager var4,
      long var5,
      ChunkPos var7,
      Biome var8,
      int var9,
      StructureFeatureConfiguration var10,
      LevelHeightAccessor var11
   ) {
      return this.feature.generate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, new WorldgenRandom(), â˜ƒ, this.config, â˜ƒ);
   }
}
