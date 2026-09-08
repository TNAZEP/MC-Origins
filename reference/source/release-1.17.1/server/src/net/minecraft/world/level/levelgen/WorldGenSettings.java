package net.minecraft.world.level.levelgen;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenSettings {
   public static final Codec<WorldGenSettings> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.LONG.fieldOf("seed").stable().forGetter(WorldGenSettings::seed),
                  Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(WorldGenSettings::generateFeatures),
                  Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(WorldGenSettings::generateBonusChest),
                  MappedRegistry.dataPackCodec(Registry.LEVEL_STEM_REGISTRY, Lifecycle.stable(), LevelStem.CODEC)
                     .xmap(LevelStem::sortMap, Function.identity())
                     .fieldOf("dimensions")
                     .forGetter(WorldGenSettings::dimensions),
                  Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(var0x -> var0x.legacyCustomOptions)
               )
               .apply(var0, var0.stable(WorldGenSettings::new))
      )
      .comapFlatMap(WorldGenSettings::guardExperimental, Function.identity());
   private static final Logger LOGGER = LogManager.getLogger();
   private final long seed;
   private final boolean generateFeatures;
   private final boolean generateBonusChest;
   private final MappedRegistry<LevelStem> dimensions;
   private final Optional<String> legacyCustomOptions;

   private DataResult<WorldGenSettings> guardExperimental() {
      LevelStem â˜ƒ = this.dimensions.get(LevelStem.OVERWORLD);
      if (â˜ƒ == null) {
         return DataResult.error("Overworld settings missing");
      } else {
         return this.stable() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
      }
   }

   private boolean stable() {
      return LevelStem.stable(this.seed, this.dimensions);
   }

   public WorldGenSettings(long var1, boolean var3, boolean var4, MappedRegistry<LevelStem> var5) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Optional.empty());
      LevelStem â˜ƒ = â˜ƒ.get(LevelStem.OVERWORLD);
      if (â˜ƒ == null) {
         throw new IllegalStateException("Overworld settings missing");
      }
   }

   private WorldGenSettings(long var1, boolean var3, boolean var4, MappedRegistry<LevelStem> var5, Optional<String> var6) {
      this.seed = â˜ƒ;
      this.generateFeatures = â˜ƒ;
      this.generateBonusChest = â˜ƒ;
      this.dimensions = â˜ƒ;
      this.legacyCustomOptions = â˜ƒ;
   }

   public static WorldGenSettings demoSettings(RegistryAccess var0) {
      Registry<Biome> â˜ƒ = â˜ƒ.registryOrThrow(Registry.BIOME_REGISTRY);
      int â˜ƒx = "North Carolina".hashCode();
      Registry<DimensionType> â˜ƒxx = â˜ƒ.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
      Registry<NoiseGeneratorSettings> â˜ƒxxx = â˜ƒ.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
      return new WorldGenSettings(
         (long)â˜ƒx,
         true,
         true,
         withOverworld(â˜ƒxx, DimensionType.defaultDimensions(â˜ƒxx, â˜ƒ, â˜ƒxxx, (long)â˜ƒx), makeDefaultOverworld(â˜ƒ, â˜ƒxxx, (long)â˜ƒx))
      );
   }

   public static WorldGenSettings makeDefault(Registry<DimensionType> var0, Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2) {
      long â˜ƒ = new Random().nextLong();
      return new WorldGenSettings(
         â˜ƒ, true, false, withOverworld(â˜ƒ, DimensionType.defaultDimensions(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ), makeDefaultOverworld(â˜ƒ, â˜ƒ, â˜ƒ))
      );
   }

   public static NoiseBasedChunkGenerator makeDefaultOverworld(Registry<Biome> var0, Registry<NoiseGeneratorSettings> var1, long var2) {
      return new NoiseBasedChunkGenerator(new OverworldBiomeSource(â˜ƒ, false, false, â˜ƒ), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.OVERWORLD));
   }

   public long seed() {
      return this.seed;
   }

   public boolean generateFeatures() {
      return this.generateFeatures;
   }

   public boolean generateBonusChest() {
      return this.generateBonusChest;
   }

   public static MappedRegistry<LevelStem> withOverworld(Registry<DimensionType> var0, MappedRegistry<LevelStem> var1, ChunkGenerator var2) {
      LevelStem â˜ƒ = â˜ƒ.get(LevelStem.OVERWORLD);
      Supplier<DimensionType> â˜ƒx = () -> â˜ƒ == null ? â˜ƒ.getOrThrow(DimensionType.OVERWORLD_LOCATION) : â˜ƒ.type();
      return withOverworld(â˜ƒ, â˜ƒx, â˜ƒ);
   }

   public static MappedRegistry<LevelStem> withOverworld(MappedRegistry<LevelStem> var0, Supplier<DimensionType> var1, ChunkGenerator var2) {
      MappedRegistry<LevelStem> â˜ƒ = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental());
      â˜ƒ.register(LevelStem.OVERWORLD, new LevelStem(â˜ƒ, â˜ƒ), Lifecycle.stable());

      for(Entry<ResourceKey<LevelStem>, LevelStem> â˜ƒx : â˜ƒ.entrySet()) {
         ResourceKey<LevelStem> â˜ƒxx = (ResourceKey)â˜ƒx.getKey();
         if (â˜ƒxx != LevelStem.OVERWORLD) {
            â˜ƒ.register(â˜ƒxx, (LevelStem)â˜ƒx.getValue(), â˜ƒ.lifecycle((LevelStem)â˜ƒx.getValue()));
         }
      }

      return â˜ƒ;
   }

   public MappedRegistry<LevelStem> dimensions() {
      return this.dimensions;
   }

   public ChunkGenerator overworld() {
      LevelStem â˜ƒ = this.dimensions.get(LevelStem.OVERWORLD);
      if (â˜ƒ == null) {
         throw new IllegalStateException("Overworld settings missing");
      } else {
         return â˜ƒ.generator();
      }
   }

   public ImmutableSet<ResourceKey<Level>> levels() {
      return (ImmutableSet<ResourceKey<Level>>)this.dimensions()
         .entrySet()
         .stream()
         .map(var0 -> ResourceKey.create(Registry.DIMENSION_REGISTRY, ((ResourceKey)var0.getKey()).location()))
         .collect(ImmutableSet.toImmutableSet());
   }

   public boolean isDebug() {
      return this.overworld() instanceof DebugLevelSource;
   }

   public boolean isFlatWorld() {
      return this.overworld() instanceof FlatLevelSource;
   }

   public boolean isOldCustomizedWorld() {
      return this.legacyCustomOptions.isPresent();
   }

   public WorldGenSettings withBonusChest() {
      return new WorldGenSettings(this.seed, this.generateFeatures, true, this.dimensions, this.legacyCustomOptions);
   }

   public WorldGenSettings withFeaturesToggled() {
      return new WorldGenSettings(this.seed, !this.generateFeatures, this.generateBonusChest, this.dimensions);
   }

   public WorldGenSettings withBonusChestToggled() {
      return new WorldGenSettings(this.seed, this.generateFeatures, !this.generateBonusChest, this.dimensions);
   }

   public static WorldGenSettings create(RegistryAccess var0, Properties var1) {
      String â˜ƒ = MoreObjects.firstNonNull((String)â˜ƒ.get("generator-settings"), "");
      â˜ƒ.put("generator-settings", â˜ƒ);
      String â˜ƒx = MoreObjects.firstNonNull((String)â˜ƒ.get("level-seed"), "");
      â˜ƒ.put("level-seed", â˜ƒx);
      String â˜ƒxx = (String)â˜ƒ.get("generate-structures");
      boolean â˜ƒxxx = â˜ƒxx == null || Boolean.parseBoolean(â˜ƒxx);
      â˜ƒ.put("generate-structures", Objects.toString(â˜ƒxxx));
      String â˜ƒxxxx = (String)â˜ƒ.get("level-type");
      String â˜ƒxxxxx = (String)Optional.ofNullable(â˜ƒxxxx).map(var0x -> var0x.toLowerCase(Locale.ROOT)).orElse("default");
      â˜ƒ.put("level-type", â˜ƒxxxxx);
      long â˜ƒxxxxxx = new Random().nextLong();
      if (!â˜ƒx.isEmpty()) {
         try {
            long â˜ƒxxxxxxx = Long.parseLong(â˜ƒx);
            if (â˜ƒxxxxxxx != 0L) {
               â˜ƒxxxxxx = â˜ƒxxxxxxx;
            }
         } catch (NumberFormatException var18) {
            â˜ƒxxxxxx = (long)â˜ƒx.hashCode();
         }
      }

      Registry<DimensionType> â˜ƒ = â˜ƒ.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
      Registry<Biome> â˜ƒx = â˜ƒ.registryOrThrow(Registry.BIOME_REGISTRY);
      Registry<NoiseGeneratorSettings> â˜ƒxx = â˜ƒ.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
      MappedRegistry<LevelStem> â˜ƒxxx = DimensionType.defaultDimensions(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxxx);
      switch(â˜ƒxxxxx) {
         case "flat":
            JsonObject â˜ƒxxxx = !â˜ƒ.isEmpty() ? GsonHelper.parse(â˜ƒ) : new JsonObject();
            Dynamic<JsonElement> â˜ƒxxxxx = new Dynamic<>(JsonOps.INSTANCE, â˜ƒxxxx);
            return new WorldGenSettings(
               â˜ƒxxxxxx,
               â˜ƒxxx,
               false,
               withOverworld(
                  â˜ƒ,
                  â˜ƒxxx,
                  new FlatLevelSource(
                     (FlatLevelGeneratorSettings)FlatLevelGeneratorSettings.CODEC
                        .parse(â˜ƒxxxxx)
                        .resultOrPartial(LOGGER::error)
                        .orElseGet(() -> FlatLevelGeneratorSettings.getDefault(â˜ƒ))
                  )
               )
            );
         case "debug_all_block_states":
            return new WorldGenSettings(â˜ƒxxxxxx, â˜ƒxxx, false, withOverworld(â˜ƒ, â˜ƒxxx, new DebugLevelSource(â˜ƒx)));
         case "amplified":
            return new WorldGenSettings(
               â˜ƒxxxxxx,
               â˜ƒxxx,
               false,
               withOverworld(
                  â˜ƒ,
                  â˜ƒxxx,
                  new NoiseBasedChunkGenerator(
                     new OverworldBiomeSource(â˜ƒxxxxxx, false, false, â˜ƒx), â˜ƒxxxxxx, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.AMPLIFIED)
                  )
               )
            );
         case "largebiomes":
            return new WorldGenSettings(
               â˜ƒxxxxxx,
               â˜ƒxxx,
               false,
               withOverworld(
                  â˜ƒ,
                  â˜ƒxxx,
                  new NoiseBasedChunkGenerator(
                     new OverworldBiomeSource(â˜ƒxxxxxx, false, true, â˜ƒx), â˜ƒxxxxxx, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
                  )
               )
            );
         default:
            return new WorldGenSettings(â˜ƒxxxxxx, â˜ƒxxx, false, withOverworld(â˜ƒ, â˜ƒxxx, makeDefaultOverworld(â˜ƒx, â˜ƒxx, â˜ƒxxxxxx)));
      }
   }

   public WorldGenSettings withSeed(boolean var1, OptionalLong var2) {
      long â˜ƒx = â˜ƒ.orElse(this.seed);
      MappedRegistry<LevelStem> â˜ƒ;
      if (â˜ƒ.isPresent()) {
         â˜ƒ = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental());
         long â˜ƒxx = â˜ƒ.getAsLong();

         for(Entry<ResourceKey<LevelStem>, LevelStem> â˜ƒxxx : this.dimensions.entrySet()) {
            ResourceKey<LevelStem> â˜ƒxxxx = (ResourceKey)â˜ƒxxx.getKey();
            â˜ƒ.register(
               â˜ƒxxxx,
               new LevelStem(((LevelStem)â˜ƒxxx.getValue()).typeSupplier(), ((LevelStem)â˜ƒxxx.getValue()).generator().withSeed(â˜ƒxx)),
               this.dimensions.lifecycle((LevelStem)â˜ƒxxx.getValue())
            );
         }
      } else {
         â˜ƒ = this.dimensions;
      }

      WorldGenSettings â˜ƒ;
      if (this.isDebug()) {
         â˜ƒ = new WorldGenSettings(â˜ƒx, false, false, â˜ƒ);
      } else {
         â˜ƒ = new WorldGenSettings(â˜ƒx, this.generateFeatures(), this.generateBonusChest() && !â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }
}
