package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.CreateBuffetWorldScreen;
import net.minecraft.client.gui.screens.CreateFlatWorldScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public abstract class WorldPreset {
   public static final WorldPreset NORMAL = new WorldPreset("default") {
      @Override
      protected ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3) {
         return new NoiseBasedChunkGenerator(new OverworldBiomeSource(â˜ƒ, false, false, â˜ƒ), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.OVERWORLD));
      }
   };
   private static final WorldPreset FLAT = new WorldPreset("flat") {
      @Override
      protected ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3) {
         return new FlatLevelSource(FlatLevelGeneratorSettings.getDefault(â˜ƒ));
      }
   };
   private static final WorldPreset LARGE_BIOMES = new WorldPreset("large_biomes") {
      @Override
      protected ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3) {
         return new NoiseBasedChunkGenerator(new OverworldBiomeSource(â˜ƒ, false, true, â˜ƒ), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.OVERWORLD));
      }
   };
   public static final WorldPreset AMPLIFIED = new WorldPreset("amplified") {
      @Override
      protected ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3) {
         return new NoiseBasedChunkGenerator(new OverworldBiomeSource(â˜ƒ, false, false, â˜ƒ), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));
      }
   };
   private static final WorldPreset SINGLE_BIOME_SURFACE = new WorldPreset("single_biome_surface") {
      @Override
      protected ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3) {
         return new NoiseBasedChunkGenerator(new FixedBiomeSource(â˜ƒ.getOrThrow(Biomes.PLAINS)), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.OVERWORLD));
      }
   };
   private static final WorldPreset SINGLE_BIOME_CAVES = new WorldPreset("single_biome_caves") {
      @Override
      public WorldGenSettings create(RegistryAccess.RegistryHolder var1, long var2, boolean var4, boolean var5) {
         Registry<Biome> â˜ƒ = â˜ƒ.registryOrThrow(Registry.BIOME_REGISTRY);
         Registry<DimensionType> â˜ƒx = â˜ƒ.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
         Registry<NoiseGeneratorSettings> â˜ƒxx = â˜ƒ.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
         return new WorldGenSettings(
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            WorldGenSettings.withOverworld(
               DimensionType.defaultDimensions(â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ),
               () -> â˜ƒ.getOrThrow(DimensionType.OVERWORLD_CAVES_LOCATION),
               this.generator(â˜ƒ, â˜ƒxx, â˜ƒ)
            )
         );
      }

      @Override
      protected ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3) {
         return new NoiseBasedChunkGenerator(new FixedBiomeSource(â˜ƒ.getOrThrow(Biomes.PLAINS)), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.CAVES));
      }
   };
   private static final WorldPreset SINGLE_BIOME_FLOATING_ISLANDS = new WorldPreset("single_biome_floating_islands") {
      @Override
      protected ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3) {
         return new NoiseBasedChunkGenerator(
            new FixedBiomeSource(â˜ƒ.getOrThrow(Biomes.PLAINS)), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.FLOATING_ISLANDS)
         );
      }
   };
   private static final WorldPreset DEBUG = new WorldPreset("debug_all_block_states") {
      @Override
      protected ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3) {
         return new DebugLevelSource(â˜ƒ);
      }
   };
   protected static final List<WorldPreset> PRESETS = Lists.<WorldPreset>newArrayList(
      NORMAL, FLAT, LARGE_BIOMES, AMPLIFIED, SINGLE_BIOME_SURFACE, SINGLE_BIOME_CAVES, SINGLE_BIOME_FLOATING_ISLANDS, DEBUG
   );
   protected static final Map<Optional<WorldPreset>, WorldPreset.PresetEditor> EDITORS = ImmutableMap.of(
      Optional.of(FLAT),
      (var0, var1) -> {
         ChunkGenerator â˜ƒ = var1.overworld();
         return new CreateFlatWorldScreen(
            var0,
            var2x -> var0.worldGenSettingsComponent
                  .updateSettings(
                     new WorldGenSettings(
                        var1.seed(),
                        var1.generateFeatures(),
                        var1.generateBonusChest(),
                        WorldGenSettings.withOverworld(
                           var0.worldGenSettingsComponent.registryHolder().registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY),
                           var1.dimensions(),
                           new FlatLevelSource(var2x)
                        )
                     )
                  ),
            â˜ƒ instanceof FlatLevelSource
               ? ((FlatLevelSource)â˜ƒ).settings()
               : FlatLevelGeneratorSettings.getDefault(var0.worldGenSettingsComponent.registryHolder().registryOrThrow(Registry.BIOME_REGISTRY))
         );
      },
      Optional.of(SINGLE_BIOME_SURFACE),
      (var0, var1) -> new CreateBuffetWorldScreen(
            var0,
            var0.worldGenSettingsComponent.registryHolder(),
            var2 -> var0.worldGenSettingsComponent
                  .updateSettings(fromBuffetSettings(var0.worldGenSettingsComponent.registryHolder(), var1, SINGLE_BIOME_SURFACE, var2)),
            parseBuffetSettings(var0.worldGenSettingsComponent.registryHolder(), var1)
         ),
      Optional.of(SINGLE_BIOME_CAVES),
      (var0, var1) -> new CreateBuffetWorldScreen(
            var0,
            var0.worldGenSettingsComponent.registryHolder(),
            var2 -> var0.worldGenSettingsComponent
                  .updateSettings(fromBuffetSettings(var0.worldGenSettingsComponent.registryHolder(), var1, SINGLE_BIOME_CAVES, var2)),
            parseBuffetSettings(var0.worldGenSettingsComponent.registryHolder(), var1)
         ),
      Optional.of(SINGLE_BIOME_FLOATING_ISLANDS),
      (var0, var1) -> new CreateBuffetWorldScreen(
            var0,
            var0.worldGenSettingsComponent.registryHolder(),
            var2 -> var0.worldGenSettingsComponent
                  .updateSettings(fromBuffetSettings(var0.worldGenSettingsComponent.registryHolder(), var1, SINGLE_BIOME_FLOATING_ISLANDS, var2)),
            parseBuffetSettings(var0.worldGenSettingsComponent.registryHolder(), var1)
         )
   );
   private final Component description;

   WorldPreset(String var1) {
      this.description = new TranslatableComponent("generator." + â˜ƒ);
   }

   private static WorldGenSettings fromBuffetSettings(RegistryAccess var0, WorldGenSettings var1, WorldPreset var2, Biome var3) {
      BiomeSource â˜ƒx = new FixedBiomeSource(â˜ƒ);
      Registry<DimensionType> â˜ƒxx = â˜ƒ.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
      Registry<NoiseGeneratorSettings> â˜ƒxxx = â˜ƒ.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
      Supplier<NoiseGeneratorSettings> â˜ƒ;
      if (â˜ƒ == SINGLE_BIOME_CAVES) {
         â˜ƒ = () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.CAVES);
      } else if (â˜ƒ == SINGLE_BIOME_FLOATING_ISLANDS) {
         â˜ƒ = () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.FLOATING_ISLANDS);
      } else {
         â˜ƒ = () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
      }

      return new WorldGenSettings(
         â˜ƒ.seed(),
         â˜ƒ.generateFeatures(),
         â˜ƒ.generateBonusChest(),
         WorldGenSettings.withOverworld(â˜ƒxx, â˜ƒ.dimensions(), new NoiseBasedChunkGenerator(â˜ƒx, â˜ƒ.seed(), â˜ƒ))
      );
   }

   private static Biome parseBuffetSettings(RegistryAccess var0, WorldGenSettings var1) {
      return (Biome)â˜ƒ.overworld()
         .getBiomeSource()
         .possibleBiomes()
         .stream()
         .findFirst()
         .orElse(â˜ƒ.registryOrThrow(Registry.BIOME_REGISTRY).getOrThrow(Biomes.PLAINS));
   }

   public static Optional<WorldPreset> of(WorldGenSettings var0) {
      ChunkGenerator â˜ƒ = â˜ƒ.overworld();
      if (â˜ƒ instanceof FlatLevelSource) {
         return Optional.of(FLAT);
      } else {
         return â˜ƒ instanceof DebugLevelSource ? Optional.of(DEBUG) : Optional.empty();
      }
   }

   public Component description() {
      return this.description;
   }

   public WorldGenSettings create(RegistryAccess.RegistryHolder var1, long var2, boolean var4, boolean var5) {
      Registry<Biome> â˜ƒ = â˜ƒ.registryOrThrow(Registry.BIOME_REGISTRY);
      Registry<DimensionType> â˜ƒx = â˜ƒ.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
      Registry<NoiseGeneratorSettings> â˜ƒxx = â˜ƒ.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
      return new WorldGenSettings(
         â˜ƒ, â˜ƒ, â˜ƒ, WorldGenSettings.withOverworld(â˜ƒx, DimensionType.defaultDimensions(â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ), this.generator(â˜ƒ, â˜ƒxx, â˜ƒ))
      );
   }

   protected abstract ChunkGenerator generator(Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3);

   public static boolean isVisibleByDefault(WorldPreset var0) {
      return â˜ƒ != DEBUG;
   }

   public interface PresetEditor {
      Screen createEditScreen(CreateWorldScreen var1, WorldGenSettings var2);
   }
}
