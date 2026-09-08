package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Biome {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final Codec<Biome> DIRECT_CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Biome.ClimateSettings.CODEC.forGetter(var0x -> var0x.climateSettings),
               Biome.BiomeCategory.CODEC.fieldOf("category").forGetter(var0x -> var0x.biomeCategory),
               Codec.FLOAT.fieldOf("depth").forGetter(var0x -> var0x.depth),
               Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.scale),
               BiomeSpecialEffects.CODEC.fieldOf("effects").forGetter(var0x -> var0x.specialEffects),
               BiomeGenerationSettings.CODEC.forGetter(var0x -> var0x.generationSettings),
               MobSpawnSettings.CODEC.forGetter(var0x -> var0x.mobSettings)
            )
            .apply(var0, Biome::new)
   );
   public static final Codec<Biome> NETWORK_CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Biome.ClimateSettings.CODEC.forGetter(var0x -> var0x.climateSettings),
               Biome.BiomeCategory.CODEC.fieldOf("category").forGetter(var0x -> var0x.biomeCategory),
               Codec.FLOAT.fieldOf("depth").forGetter(var0x -> var0x.depth),
               Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.scale),
               BiomeSpecialEffects.CODEC.fieldOf("effects").forGetter(var0x -> var0x.specialEffects)
            )
            .apply(var0, (var0x, var1, var2, var3, var4) -> new Biome(var0x, var1, var2, var3, var4, BiomeGenerationSettings.EMPTY, MobSpawnSettings.EMPTY))
   );
   public static final Codec<Supplier<Biome>> CODEC = RegistryFileCodec.create(Registry.BIOME_REGISTRY, DIRECT_CODEC);
   public static final Codec<List<Supplier<Biome>>> LIST_CODEC = RegistryFileCodec.homogeneousList(Registry.BIOME_REGISTRY, DIRECT_CODEC);
   private final Map<Integer, List<StructureFeature<?>>> structuresByStep = (Map<Integer, List<StructureFeature<?>>>)Registry.STRUCTURE_FEATURE
      .stream()
      .collect(Collectors.groupingBy(var0 -> var0.step().ordinal()));
   private static final PerlinSimplexNoise TEMPERATURE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(1234L), ImmutableList.of(0));
   static final PerlinSimplexNoise FROZEN_TEMPERATURE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(3456L), ImmutableList.of(-2, -1, 0));
   public static final PerlinSimplexNoise BIOME_INFO_NOISE = new PerlinSimplexNoise(new WorldgenRandom(2345L), ImmutableList.of(0));
   private static final int TEMPERATURE_CACHE_SIZE = 1024;
   private final Biome.ClimateSettings climateSettings;
   private final BiomeGenerationSettings generationSettings;
   private final MobSpawnSettings mobSettings;
   private final float depth;
   private final float scale;
   private final Biome.BiomeCategory biomeCategory;
   private final BiomeSpecialEffects specialEffects;
   private final ThreadLocal<Long2FloatLinkedOpenHashMap> temperatureCache = ThreadLocal.withInitial(() -> Util.make(() -> {
         Long2FloatLinkedOpenHashMap â˜ƒ = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
            @Override
            protected void rehash(int var1) {
            }
         };
         â˜ƒ.defaultReturnValue(Float.NaN);
         return â˜ƒ;
      }));

   Biome(
      Biome.ClimateSettings var1,
      Biome.BiomeCategory var2,
      float var3,
      float var4,
      BiomeSpecialEffects var5,
      BiomeGenerationSettings var6,
      MobSpawnSettings var7
   ) {
      this.climateSettings = â˜ƒ;
      this.generationSettings = â˜ƒ;
      this.mobSettings = â˜ƒ;
      this.biomeCategory = â˜ƒ;
      this.depth = â˜ƒ;
      this.scale = â˜ƒ;
      this.specialEffects = â˜ƒ;
   }

   public int getSkyColor() {
      return this.specialEffects.getSkyColor();
   }

   public MobSpawnSettings getMobSettings() {
      return this.mobSettings;
   }

   public Biome.Precipitation getPrecipitation() {
      return this.climateSettings.precipitation;
   }

   public boolean isHumid() {
      return this.getDownfall() > 0.85F;
   }

   private float getHeightAdjustedTemperature(BlockPos var1) {
      float â˜ƒ = this.climateSettings.temperatureModifier.modifyTemperature(â˜ƒ, this.getBaseTemperature());
      if (â˜ƒ.getY() > 64) {
         float â˜ƒx = (float)(TEMPERATURE_NOISE.getValue((double)((float)â˜ƒ.getX() / 8.0F), (double)((float)â˜ƒ.getZ() / 8.0F), false) * 4.0);
         return â˜ƒ - (â˜ƒx + (float)â˜ƒ.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return â˜ƒ;
      }
   }

   public final float getTemperature(BlockPos var1) {
      long â˜ƒ = â˜ƒ.asLong();
      Long2FloatLinkedOpenHashMap â˜ƒx = (Long2FloatLinkedOpenHashMap)this.temperatureCache.get();
      float â˜ƒxx = â˜ƒx.get(â˜ƒ);
      if (!Float.isNaN(â˜ƒxx)) {
         return â˜ƒxx;
      } else {
         float â˜ƒ = this.getHeightAdjustedTemperature(â˜ƒ);
         if (â˜ƒx.size() == 1024) {
            â˜ƒx.removeFirstFloat();
         }

         â˜ƒx.put(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      }
   }

   public boolean shouldFreeze(LevelReader var1, BlockPos var2) {
      return this.shouldFreeze(â˜ƒ, â˜ƒ, true);
   }

   public boolean shouldFreeze(LevelReader var1, BlockPos var2, boolean var3) {
      if (this.getTemperature(â˜ƒ) >= 0.15F) {
         return false;
      } else {
         if (â˜ƒ.getY() >= â˜ƒ.getMinBuildHeight() && â˜ƒ.getY() < â˜ƒ.getMaxBuildHeight() && â˜ƒ.getBrightness(LightLayer.BLOCK, â˜ƒ) < 10) {
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
            FluidState â˜ƒx = â˜ƒ.getFluidState(â˜ƒ);
            if (â˜ƒx.getType() == Fluids.WATER && â˜ƒ.getBlock() instanceof LiquidBlock) {
               if (!â˜ƒ) {
                  return true;
               }

               boolean â˜ƒxx = â˜ƒ.isWaterAt(â˜ƒ.west()) && â˜ƒ.isWaterAt(â˜ƒ.east()) && â˜ƒ.isWaterAt(â˜ƒ.north()) && â˜ƒ.isWaterAt(â˜ƒ.south());
               if (!â˜ƒxx) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isColdEnoughToSnow(BlockPos var1) {
      return this.getTemperature(â˜ƒ) < 0.15F;
   }

   public boolean shouldSnow(LevelReader var1, BlockPos var2) {
      if (!this.isColdEnoughToSnow(â˜ƒ)) {
         return false;
      } else {
         if (â˜ƒ.getY() >= â˜ƒ.getMinBuildHeight() && â˜ƒ.getY() < â˜ƒ.getMaxBuildHeight() && â˜ƒ.getBrightness(LightLayer.BLOCK, â˜ƒ) < 10) {
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
            if (â˜ƒ.isAir() && Blocks.SNOW.defaultBlockState().canSurvive(â˜ƒ, â˜ƒ)) {
               return true;
            }
         }

         return false;
      }
   }

   public BiomeGenerationSettings getGenerationSettings() {
      return this.generationSettings;
   }

   public void generate(StructureFeatureManager var1, ChunkGenerator var2, WorldGenRegion var3, long var4, WorldgenRandom var6, BlockPos var7) {
      List<List<Supplier<ConfiguredFeature<?, ?>>>> â˜ƒ = this.generationSettings.features();
      Registry<ConfiguredFeature<?, ?>> â˜ƒx = â˜ƒ.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY);
      Registry<StructureFeature<?>> â˜ƒxx = â˜ƒ.registryAccess().registryOrThrow(Registry.STRUCTURE_FEATURE_REGISTRY);
      int â˜ƒxxx = GenerationStep.Decoration.values().length;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx; ++â˜ƒxxxx) {
         int â˜ƒxxxxx = 0;
         if (â˜ƒ.shouldGenerateFeatures()) {
            for(StructureFeature<?> â˜ƒxxxxxx : (List)this.structuresByStep.getOrDefault(â˜ƒxxxx, Collections.emptyList())) {
               â˜ƒ.setFeatureSeed(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxx);
               int â˜ƒxxxxxxx = SectionPos.blockToSectionCoord(â˜ƒ.getX());
               int â˜ƒxxxxxxxx = SectionPos.blockToSectionCoord(â˜ƒ.getZ());
               int â˜ƒxxxxxxxxx = SectionPos.sectionToBlockCoord(â˜ƒxxxxxxx);
               int â˜ƒxxxxxxxxxx = SectionPos.sectionToBlockCoord(â˜ƒxxxxxxxx);
               Supplier<String> â˜ƒxxxxxxxxxxx = () -> (String)â˜ƒ.getResourceKey(â˜ƒ).map(Object::toString).orElseGet(â˜ƒ::toString);

               try {
                  int â˜ƒxxxxxxxxxxxx = â˜ƒ.getMinBuildHeight() + 1;
                  int â˜ƒxxxxxxxxxxxxx = â˜ƒ.getMaxBuildHeight() - 1;
                  â˜ƒ.setCurrentlyGenerating(â˜ƒxxxxxxxxxxx);
                  â˜ƒ.startsForFeature(SectionPos.of(â˜ƒ), â˜ƒxxxxxx)
                     .forEach(
                        var10x -> var10x.placeInChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, new BoundingBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 15, â˜ƒ, â˜ƒ + 15), new ChunkPos(â˜ƒ, â˜ƒ))
                     );
               } catch (Exception var24) {
                  CrashReport â˜ƒxxxxxxxxxxxxxx = CrashReport.forThrowable(var24, "Feature placement");
                  â˜ƒxxxxxxxxxxxxxx.addCategory("Feature").setDetail("Description", â˜ƒxxxxxxxxxxx::get);
                  throw new ReportedException(â˜ƒxxxxxxxxxxxxxx);
               }

               ++â˜ƒxxxxx;
            }
         }

         if (â˜ƒ.size() > â˜ƒxxxx) {
            for(Supplier<ConfiguredFeature<?, ?>> â˜ƒxxxxx : (List)â˜ƒ.get(â˜ƒxxxx)) {
               ConfiguredFeature<?, ?> â˜ƒxxxxxx = (ConfiguredFeature)â˜ƒxxxxx.get();
               Supplier<String> â˜ƒxxxxxxx = () -> (String)â˜ƒ.getResourceKey(â˜ƒ).map(Object::toString).orElseGet(â˜ƒ::toString);
               â˜ƒ.setFeatureSeed(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxx);

               try {
                  â˜ƒ.setCurrentlyGenerating(â˜ƒxxxxxxx);
                  â˜ƒxxxxxx.place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               } catch (Exception var25) {
                  CrashReport â˜ƒxxxxxxxx = CrashReport.forThrowable(var25, "Feature placement");
                  â˜ƒxxxxxxxx.addCategory("Feature").setDetail("Description", â˜ƒxxxxxxx::get);
                  throw new ReportedException(â˜ƒxxxxxxxx);
               }

               ++â˜ƒxxxxx;
            }
         }
      }

      â˜ƒ.setCurrentlyGenerating(null);
   }

   public int getFogColor() {
      return this.specialEffects.getFogColor();
   }

   public int getGrassColor(double var1, double var3) {
      int â˜ƒ = this.specialEffects.getGrassColorOverride().orElseGet(this::getGrassColorFromTexture);
      return this.specialEffects.getGrassColorModifier().modifyColor(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private int getGrassColorFromTexture() {
      double â˜ƒ = (double)Mth.clamp(this.climateSettings.temperature, 0.0F, 1.0F);
      double â˜ƒx = (double)Mth.clamp(this.climateSettings.downfall, 0.0F, 1.0F);
      return GrassColor.get(â˜ƒ, â˜ƒx);
   }

   public int getFoliageColor() {
      return this.specialEffects.getFoliageColorOverride().orElseGet(this::getFoliageColorFromTexture);
   }

   private int getFoliageColorFromTexture() {
      double â˜ƒ = (double)Mth.clamp(this.climateSettings.temperature, 0.0F, 1.0F);
      double â˜ƒx = (double)Mth.clamp(this.climateSettings.downfall, 0.0F, 1.0F);
      return FoliageColor.get(â˜ƒ, â˜ƒx);
   }

   public void buildSurfaceAt(
      Random var1, ChunkAccess var2, int var3, int var4, int var5, double var6, BlockState var8, BlockState var9, int var10, int var11, long var12
   ) {
      ConfiguredSurfaceBuilder<?> â˜ƒ = (ConfiguredSurfaceBuilder)this.generationSettings.getSurfaceBuilder().get();
      â˜ƒ.initNoise(â˜ƒ);
      â˜ƒ.apply(â˜ƒ, â˜ƒ, this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public final float getDepth() {
      return this.depth;
   }

   public final float getDownfall() {
      return this.climateSettings.downfall;
   }

   public final float getScale() {
      return this.scale;
   }

   public final float getBaseTemperature() {
      return this.climateSettings.temperature;
   }

   public BiomeSpecialEffects getSpecialEffects() {
      return this.specialEffects;
   }

   public final int getWaterColor() {
      return this.specialEffects.getWaterColor();
   }

   public final int getWaterFogColor() {
      return this.specialEffects.getWaterFogColor();
   }

   public Optional<AmbientParticleSettings> getAmbientParticle() {
      return this.specialEffects.getAmbientParticleSettings();
   }

   public Optional<SoundEvent> getAmbientLoop() {
      return this.specialEffects.getAmbientLoopSoundEvent();
   }

   public Optional<AmbientMoodSettings> getAmbientMood() {
      return this.specialEffects.getAmbientMoodSettings();
   }

   public Optional<AmbientAdditionsSettings> getAmbientAdditions() {
      return this.specialEffects.getAmbientAdditionsSettings();
   }

   public Optional<Music> getBackgroundMusic() {
      return this.specialEffects.getBackgroundMusic();
   }

   public final Biome.BiomeCategory getBiomeCategory() {
      return this.biomeCategory;
   }

   public String toString() {
      ResourceLocation â˜ƒ = BuiltinRegistries.BIOME.getKey(this);
      return â˜ƒ == null ? super.toString() : â˜ƒ.toString();
   }

   public static class BiomeBuilder {
      @Nullable
      private Biome.Precipitation precipitation;
      @Nullable
      private Biome.BiomeCategory biomeCategory;
      @Nullable
      private Float depth;
      @Nullable
      private Float scale;
      @Nullable
      private Float temperature;
      private Biome.TemperatureModifier temperatureModifier = Biome.TemperatureModifier.NONE;
      @Nullable
      private Float downfall;
      @Nullable
      private BiomeSpecialEffects specialEffects;
      @Nullable
      private MobSpawnSettings mobSpawnSettings;
      @Nullable
      private BiomeGenerationSettings generationSettings;

      public Biome.BiomeBuilder precipitation(Biome.Precipitation var1) {
         this.precipitation = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder biomeCategory(Biome.BiomeCategory var1) {
         this.biomeCategory = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder depth(float var1) {
         this.depth = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder scale(float var1) {
         this.scale = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder temperature(float var1) {
         this.temperature = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder downfall(float var1) {
         this.downfall = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder specialEffects(BiomeSpecialEffects var1) {
         this.specialEffects = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder mobSpawnSettings(MobSpawnSettings var1) {
         this.mobSpawnSettings = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder generationSettings(BiomeGenerationSettings var1) {
         this.generationSettings = â˜ƒ;
         return this;
      }

      public Biome.BiomeBuilder temperatureAdjustment(Biome.TemperatureModifier var1) {
         this.temperatureModifier = â˜ƒ;
         return this;
      }

      public Biome build() {
         if (this.precipitation != null
            && this.biomeCategory != null
            && this.depth != null
            && this.scale != null
            && this.temperature != null
            && this.downfall != null
            && this.specialEffects != null
            && this.mobSpawnSettings != null
            && this.generationSettings != null) {
            return new Biome(
               new Biome.ClimateSettings(this.precipitation, this.temperature, this.temperatureModifier, this.downfall),
               this.biomeCategory,
               this.depth,
               this.scale,
               this.specialEffects,
               this.generationSettings,
               this.mobSpawnSettings
            );
         } else {
            throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
         }
      }

      public String toString() {
         return "BiomeBuilder{\nprecipitation="
            + this.precipitation
            + ",\nbiomeCategory="
            + this.biomeCategory
            + ",\ndepth="
            + this.depth
            + ",\nscale="
            + this.scale
            + ",\ntemperature="
            + this.temperature
            + ",\ntemperatureModifier="
            + this.temperatureModifier
            + ",\ndownfall="
            + this.downfall
            + ",\nspecialEffects="
            + this.specialEffects
            + ",\nmobSpawnSettings="
            + this.mobSpawnSettings
            + ",\ngenerationSettings="
            + this.generationSettings
            + ",\n}";
      }
   }

   public static enum BiomeCategory implements StringRepresentable {
      NONE("none"),
      TAIGA("taiga"),
      EXTREME_HILLS("extreme_hills"),
      JUNGLE("jungle"),
      MESA("mesa"),
      PLAINS("plains"),
      SAVANNA("savanna"),
      ICY("icy"),
      THEEND("the_end"),
      BEACH("beach"),
      FOREST("forest"),
      OCEAN("ocean"),
      DESERT("desert"),
      RIVER("river"),
      SWAMP("swamp"),
      MUSHROOM("mushroom"),
      NETHER("nether"),
      UNDERGROUND("underground");

      public static final Codec<Biome.BiomeCategory> CODEC = StringRepresentable.fromEnum(Biome.BiomeCategory::values, Biome.BiomeCategory::byName);
      private static final Map<String, Biome.BiomeCategory> BY_NAME = (Map<String, Biome.BiomeCategory>)Arrays.stream(values())
         .collect(Collectors.toMap(Biome.BiomeCategory::getName, var0 -> var0));
      private final String name;

      private BiomeCategory(String var3) {
         this.name = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public static Biome.BiomeCategory byName(String var0) {
         return (Biome.BiomeCategory)BY_NAME.get(â˜ƒ);
      }

      @Override
      public String getSerializedName() {
         return this.name;
      }
   }

   public static class ClimateParameters {
      public static final Codec<Biome.ClimateParameters> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("temperature").forGetter(var0x -> var0x.temperature),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("humidity").forGetter(var0x -> var0x.humidity),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("altitude").forGetter(var0x -> var0x.altitude),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("weirdness").forGetter(var0x -> var0x.weirdness),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("offset").forGetter(var0x -> var0x.offset)
               )
               .apply(var0, Biome.ClimateParameters::new)
      );
      private final float temperature;
      private final float humidity;
      private final float altitude;
      private final float weirdness;
      private final float offset;

      public ClimateParameters(float var1, float var2, float var3, float var4, float var5) {
         this.temperature = â˜ƒ;
         this.humidity = â˜ƒ;
         this.altitude = â˜ƒ;
         this.weirdness = â˜ƒ;
         this.offset = â˜ƒ;
      }

      public String toString() {
         return "temp: " + this.temperature + ", hum: " + this.humidity + ", alt: " + this.altitude + ", weird: " + this.weirdness + ", offset: " + this.offset;
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
            Biome.ClimateParameters â˜ƒ = (Biome.ClimateParameters)â˜ƒ;
            if (Float.compare(â˜ƒ.temperature, this.temperature) != 0) {
               return false;
            } else if (Float.compare(â˜ƒ.humidity, this.humidity) != 0) {
               return false;
            } else if (Float.compare(â˜ƒ.altitude, this.altitude) != 0) {
               return false;
            } else {
               return Float.compare(â˜ƒ.weirdness, this.weirdness) == 0;
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int â˜ƒ = this.temperature != 0.0F ? Float.floatToIntBits(this.temperature) : 0;
         â˜ƒ = 31 * â˜ƒ + (this.humidity != 0.0F ? Float.floatToIntBits(this.humidity) : 0);
         â˜ƒ = 31 * â˜ƒ + (this.altitude != 0.0F ? Float.floatToIntBits(this.altitude) : 0);
         return 31 * â˜ƒ + (this.weirdness != 0.0F ? Float.floatToIntBits(this.weirdness) : 0);
      }

      public float fitness(Biome.ClimateParameters var1) {
         return (this.temperature - â˜ƒ.temperature) * (this.temperature - â˜ƒ.temperature)
            + (this.humidity - â˜ƒ.humidity) * (this.humidity - â˜ƒ.humidity)
            + (this.altitude - â˜ƒ.altitude) * (this.altitude - â˜ƒ.altitude)
            + (this.weirdness - â˜ƒ.weirdness) * (this.weirdness - â˜ƒ.weirdness)
            + (this.offset - â˜ƒ.offset) * (this.offset - â˜ƒ.offset);
      }
   }

   static class ClimateSettings {
      public static final MapCodec<Biome.ClimateSettings> CODEC = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(var0x -> var0x.precipitation),
                  Codec.FLOAT.fieldOf("temperature").forGetter(var0x -> var0x.temperature),
                  Biome.TemperatureModifier.CODEC
                     .optionalFieldOf("temperature_modifier", Biome.TemperatureModifier.NONE)
                     .forGetter(var0x -> var0x.temperatureModifier),
                  Codec.FLOAT.fieldOf("downfall").forGetter(var0x -> var0x.downfall)
               )
               .apply(var0, Biome.ClimateSettings::new)
      );
      final Biome.Precipitation precipitation;
      final float temperature;
      final Biome.TemperatureModifier temperatureModifier;
      final float downfall;

      ClimateSettings(Biome.Precipitation var1, float var2, Biome.TemperatureModifier var3, float var4) {
         this.precipitation = â˜ƒ;
         this.temperature = â˜ƒ;
         this.temperatureModifier = â˜ƒ;
         this.downfall = â˜ƒ;
      }
   }

   public static enum Precipitation implements StringRepresentable {
      NONE("none"),
      RAIN("rain"),
      SNOW("snow");

      public static final Codec<Biome.Precipitation> CODEC = StringRepresentable.fromEnum(Biome.Precipitation::values, Biome.Precipitation::byName);
      private static final Map<String, Biome.Precipitation> BY_NAME = (Map<String, Biome.Precipitation>)Arrays.stream(values())
         .collect(Collectors.toMap(Biome.Precipitation::getName, var0 -> var0));
      private final String name;

      private Precipitation(String var3) {
         this.name = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public static Biome.Precipitation byName(String var0) {
         return (Biome.Precipitation)BY_NAME.get(â˜ƒ);
      }

      @Override
      public String getSerializedName() {
         return this.name;
      }
   }

   public static enum TemperatureModifier implements StringRepresentable {
      NONE("none") {
         @Override
         public float modifyTemperature(BlockPos var1, float var2) {
            return â˜ƒ;
         }
      },
      FROZEN("frozen") {
         @Override
         public float modifyTemperature(BlockPos var1, float var2) {
            double â˜ƒ = Biome.FROZEN_TEMPERATURE_NOISE.getValue((double)â˜ƒ.getX() * 0.05, (double)â˜ƒ.getZ() * 0.05, false) * 7.0;
            double â˜ƒx = Biome.BIOME_INFO_NOISE.getValue((double)â˜ƒ.getX() * 0.2, (double)â˜ƒ.getZ() * 0.2, false);
            double â˜ƒxx = â˜ƒ + â˜ƒx;
            if (â˜ƒxx < 0.3) {
               double â˜ƒxxx = Biome.BIOME_INFO_NOISE.getValue((double)â˜ƒ.getX() * 0.09, (double)â˜ƒ.getZ() * 0.09, false);
               if (â˜ƒxxx < 0.8) {
                  return 0.2F;
               }
            }

            return â˜ƒ;
         }
      };

      private final String name;
      public static final Codec<Biome.TemperatureModifier> CODEC = StringRepresentable.fromEnum(
         Biome.TemperatureModifier::values, Biome.TemperatureModifier::byName
      );
      private static final Map<String, Biome.TemperatureModifier> BY_NAME = (Map<String, Biome.TemperatureModifier>)Arrays.stream(values())
         .collect(Collectors.toMap(Biome.TemperatureModifier::getName, var0 -> var0));

      public abstract float modifyTemperature(BlockPos var1, float var2);

      TemperatureModifier(String var3) {
         this.name = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      @Override
      public String getSerializedName() {
         return this.name;
      }

      public static Biome.TemperatureModifier byName(String var0) {
         return (Biome.TemperatureModifier)BY_NAME.get(â˜ƒ);
      }
   }
}
