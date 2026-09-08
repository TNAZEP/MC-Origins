package net.minecraft.world.level.levelgen.flat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlatLevelGeneratorSettings {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Codec<FlatLevelGeneratorSettings> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(var0x -> var0x.biomes),
                  StructureSettings.CODEC.fieldOf("structures").forGetter(FlatLevelGeneratorSettings::structureSettings),
                  FlatLayerInfo.CODEC.listOf().fieldOf("layers").forGetter(FlatLevelGeneratorSettings::getLayersInfo),
                  Codec.BOOL.fieldOf("lakes").orElse(false).forGetter(var0x -> var0x.addLakes),
                  Codec.BOOL.fieldOf("features").orElse(false).forGetter(var0x -> var0x.decoration),
                  Biome.CODEC.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter(var0x -> Optional.of(var0x.biome))
               )
               .apply(var0, FlatLevelGeneratorSettings::new)
      )
      .<FlatLevelGeneratorSettings>comapFlatMap(FlatLevelGeneratorSettings::validateHeight, Function.identity())
      .stable();
   private static final Map<StructureFeature<?>, ConfiguredStructureFeature<?, ?>> STRUCTURE_FEATURES = Util.make(
      Maps.<StructureFeature<?>, ConfiguredStructureFeature<?, ?>>newHashMap(), var0 -> {
         var0.put(StructureFeature.MINESHAFT, StructureFeatures.MINESHAFT);
         var0.put(StructureFeature.VILLAGE, StructureFeatures.VILLAGE_PLAINS);
         var0.put(StructureFeature.STRONGHOLD, StructureFeatures.STRONGHOLD);
         var0.put(StructureFeature.SWAMP_HUT, StructureFeatures.SWAMP_HUT);
         var0.put(StructureFeature.DESERT_PYRAMID, StructureFeatures.DESERT_PYRAMID);
         var0.put(StructureFeature.JUNGLE_TEMPLE, StructureFeatures.JUNGLE_TEMPLE);
         var0.put(StructureFeature.IGLOO, StructureFeatures.IGLOO);
         var0.put(StructureFeature.OCEAN_RUIN, StructureFeatures.OCEAN_RUIN_COLD);
         var0.put(StructureFeature.SHIPWRECK, StructureFeatures.SHIPWRECK);
         var0.put(StructureFeature.OCEAN_MONUMENT, StructureFeatures.OCEAN_MONUMENT);
         var0.put(StructureFeature.END_CITY, StructureFeatures.END_CITY);
         var0.put(StructureFeature.WOODLAND_MANSION, StructureFeatures.WOODLAND_MANSION);
         var0.put(StructureFeature.NETHER_BRIDGE, StructureFeatures.NETHER_BRIDGE);
         var0.put(StructureFeature.PILLAGER_OUTPOST, StructureFeatures.PILLAGER_OUTPOST);
         var0.put(StructureFeature.RUINED_PORTAL, StructureFeatures.RUINED_PORTAL_STANDARD);
         var0.put(StructureFeature.BASTION_REMNANT, StructureFeatures.BASTION_REMNANT);
      }
   );
   private final Registry<Biome> biomes;
   private final StructureSettings structureSettings;
   private final List<FlatLayerInfo> layersInfo = Lists.<FlatLayerInfo>newArrayList();
   private Supplier<Biome> biome;
   private final List<BlockState> layers;
   private boolean voidGen;
   private boolean decoration;
   private boolean addLakes;

   private static DataResult<FlatLevelGeneratorSettings> validateHeight(FlatLevelGeneratorSettings var0) {
      int â˜ƒ = â˜ƒ.layersInfo.stream().mapToInt(FlatLayerInfo::getHeight).sum();
      return â˜ƒ > DimensionType.Y_SIZE ? DataResult.error("Sum of layer heights is > " + DimensionType.Y_SIZE, â˜ƒ) : DataResult.success(â˜ƒ);
   }

   private FlatLevelGeneratorSettings(
      Registry<Biome> var1, StructureSettings var2, List<FlatLayerInfo> var3, boolean var4, boolean var5, Optional<Supplier<Biome>> var6
   ) {
      this(â˜ƒ, â˜ƒ);
      if (â˜ƒ) {
         this.setAddLakes();
      }

      if (â˜ƒ) {
         this.setDecoration();
      }

      this.layersInfo.addAll(â˜ƒ);
      this.updateLayers();
      if (!â˜ƒ.isPresent()) {
         LOGGER.error("Unknown biome, defaulting to plains");
         this.biome = () -> â˜ƒ.getOrThrow(Biomes.PLAINS);
      } else {
         this.biome = (Supplier)â˜ƒ.get();
      }
   }

   public FlatLevelGeneratorSettings(StructureSettings var1, Registry<Biome> var2) {
      this.biomes = â˜ƒ;
      this.structureSettings = â˜ƒ;
      this.biome = () -> â˜ƒ.getOrThrow(Biomes.PLAINS);
      this.layers = Lists.<BlockState>newArrayList();
   }

   public FlatLevelGeneratorSettings withStructureSettings(StructureSettings var1) {
      return this.withLayers(this.layersInfo, â˜ƒ);
   }

   public FlatLevelGeneratorSettings withLayers(List<FlatLayerInfo> var1, StructureSettings var2) {
      FlatLevelGeneratorSettings â˜ƒ = new FlatLevelGeneratorSettings(â˜ƒ, this.biomes);

      for(FlatLayerInfo â˜ƒx : â˜ƒ) {
         â˜ƒ.layersInfo.add(new FlatLayerInfo(â˜ƒx.getHeight(), â˜ƒx.getBlockState().getBlock()));
         â˜ƒ.updateLayers();
      }

      â˜ƒ.setBiome(this.biome);
      if (this.decoration) {
         â˜ƒ.setDecoration();
      }

      if (this.addLakes) {
         â˜ƒ.setAddLakes();
      }

      return â˜ƒ;
   }

   public void setDecoration() {
      this.decoration = true;
   }

   public void setAddLakes() {
      this.addLakes = true;
   }

   public Biome getBiomeFromSettings() {
      Biome â˜ƒ = this.getBiome();
      BiomeGenerationSettings â˜ƒx = â˜ƒ.getGenerationSettings();
      BiomeGenerationSettings.Builder â˜ƒxx = new BiomeGenerationSettings.Builder().surfaceBuilder(â˜ƒx.getSurfaceBuilder());
      if (this.addLakes) {
         â˜ƒxx.addFeature(GenerationStep.Decoration.LAKES, Features.LAKE_WATER);
         â˜ƒxx.addFeature(GenerationStep.Decoration.LAKES, Features.LAKE_LAVA);
      }

      for(Entry<StructureFeature<?>, StructureFeatureConfiguration> â˜ƒ : this.structureSettings.structureConfig().entrySet()) {
         â˜ƒxx.addStructureStart(â˜ƒx.withBiomeConfig((ConfiguredStructureFeature<?, ?>)STRUCTURE_FEATURES.get(â˜ƒ.getKey())));
      }

      boolean â˜ƒ = (!this.voidGen || this.biomes.getResourceKey(â˜ƒ).equals(Optional.of(Biomes.THE_VOID))) && this.decoration;
      if (â˜ƒ) {
         List<List<Supplier<ConfiguredFeature<?, ?>>>> â˜ƒx = â˜ƒx.features();

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            if (â˜ƒxx != GenerationStep.Decoration.UNDERGROUND_STRUCTURES.ordinal() && â˜ƒxx != GenerationStep.Decoration.SURFACE_STRUCTURES.ordinal()) {
               for(Supplier<ConfiguredFeature<?, ?>> â˜ƒxxx : (List)â˜ƒx.get(â˜ƒxx)) {
                  â˜ƒxx.addFeature(â˜ƒxx, â˜ƒxxx);
               }
            }
         }
      }

      List<BlockState> â˜ƒ = this.getLayers();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         BlockState â˜ƒxx = (BlockState)â˜ƒ.get(â˜ƒx);
         if (!Heightmap.Types.MOTION_BLOCKING.isOpaque().test(â˜ƒxx)) {
            â˜ƒ.set(â˜ƒx, null);
            â˜ƒxx.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, Feature.FILL_LAYER.configured(new LayerConfiguration(â˜ƒx, â˜ƒxx)));
         }
      }

      return new Biome.BiomeBuilder()
         .precipitation(â˜ƒ.getPrecipitation())
         .biomeCategory(â˜ƒ.getBiomeCategory())
         .depth(â˜ƒ.getDepth())
         .scale(â˜ƒ.getScale())
         .temperature(â˜ƒ.getBaseTemperature())
         .downfall(â˜ƒ.getDownfall())
         .specialEffects(â˜ƒ.getSpecialEffects())
         .generationSettings(â˜ƒxx.build())
         .mobSpawnSettings(â˜ƒ.getMobSettings())
         .build();
   }

   public StructureSettings structureSettings() {
      return this.structureSettings;
   }

   public Biome getBiome() {
      return (Biome)this.biome.get();
   }

   public void setBiome(Supplier<Biome> var1) {
      this.biome = â˜ƒ;
   }

   public List<FlatLayerInfo> getLayersInfo() {
      return this.layersInfo;
   }

   public List<BlockState> getLayers() {
      return this.layers;
   }

   public void updateLayers() {
      this.layers.clear();

      for(FlatLayerInfo â˜ƒ : this.layersInfo) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getHeight(); ++â˜ƒx) {
            this.layers.add(â˜ƒ.getBlockState());
         }
      }

      this.voidGen = this.layers.stream().allMatch(var0 -> var0.is(Blocks.AIR));
   }

   public static FlatLevelGeneratorSettings getDefault(Registry<Biome> var0) {
      StructureSettings â˜ƒ = new StructureSettings(
         Optional.of(StructureSettings.DEFAULT_STRONGHOLD),
         Maps.<StructureFeature<?>, StructureFeatureConfiguration>newHashMap(
            ImmutableMap.of(StructureFeature.VILLAGE, StructureSettings.DEFAULTS.get(StructureFeature.VILLAGE))
         )
      );
      FlatLevelGeneratorSettings â˜ƒx = new FlatLevelGeneratorSettings(â˜ƒ, â˜ƒ);
      â˜ƒx.biome = () -> â˜ƒ.getOrThrow(Biomes.PLAINS);
      â˜ƒx.getLayersInfo().add(new FlatLayerInfo(1, Blocks.BEDROCK));
      â˜ƒx.getLayersInfo().add(new FlatLayerInfo(2, Blocks.DIRT));
      â˜ƒx.getLayersInfo().add(new FlatLayerInfo(1, Blocks.GRASS_BLOCK));
      â˜ƒx.updateLayers();
      return â˜ƒx;
   }
}
