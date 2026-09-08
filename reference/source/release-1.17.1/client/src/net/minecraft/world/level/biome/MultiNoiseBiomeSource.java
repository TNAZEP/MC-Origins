package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class MultiNoiseBiomeSource extends BiomeSource {
   private static final MultiNoiseBiomeSource.NoiseParameters DEFAULT_NOISE_PARAMETERS = new MultiNoiseBiomeSource.NoiseParameters(
      -7, ImmutableList.of(1.0, 1.0)
   );
   public static final MapCodec<MultiNoiseBiomeSource> DIRECT_CODEC = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.LONG.fieldOf("seed").forGetter(var0x -> var0x.seed),
               RecordCodecBuilder.create(
                     var0x -> var0x.group(
                              Biome.ClimateParameters.CODEC.fieldOf("parameters").forGetter(Pair::getFirst),
                              Biome.CODEC.fieldOf("biome").forGetter(Pair::getSecond)
                           )
                           .apply(var0x, Pair::of)
                  )
                  .listOf()
                  .fieldOf("biomes")
                  .forGetter(var0x -> var0x.parameters),
               MultiNoiseBiomeSource.NoiseParameters.CODEC.fieldOf("temperature_noise").forGetter(var0x -> var0x.temperatureParams),
               MultiNoiseBiomeSource.NoiseParameters.CODEC.fieldOf("humidity_noise").forGetter(var0x -> var0x.humidityParams),
               MultiNoiseBiomeSource.NoiseParameters.CODEC.fieldOf("altitude_noise").forGetter(var0x -> var0x.altitudeParams),
               MultiNoiseBiomeSource.NoiseParameters.CODEC.fieldOf("weirdness_noise").forGetter(var0x -> var0x.weirdnessParams)
            )
            .apply(var0, MultiNoiseBiomeSource::new)
   );
   public static final Codec<MultiNoiseBiomeSource> CODEC = Codec.mapEither(MultiNoiseBiomeSource.PresetInstance.CODEC, DIRECT_CODEC)
      .<MultiNoiseBiomeSource>xmap(
         var0 -> var0.map(MultiNoiseBiomeSource.PresetInstance::biomeSource, Function.identity()),
         var0 -> (Either)var0.preset().map(Either::left).orElseGet(() -> Either.right(var0))
      )
      .codec();
   private final MultiNoiseBiomeSource.NoiseParameters temperatureParams;
   private final MultiNoiseBiomeSource.NoiseParameters humidityParams;
   private final MultiNoiseBiomeSource.NoiseParameters altitudeParams;
   private final MultiNoiseBiomeSource.NoiseParameters weirdnessParams;
   private final NormalNoise temperatureNoise;
   private final NormalNoise humidityNoise;
   private final NormalNoise altitudeNoise;
   private final NormalNoise weirdnessNoise;
   private final List<Pair<Biome.ClimateParameters, Supplier<Biome>>> parameters;
   private final boolean useY;
   private final long seed;
   private final Optional<Pair<Registry<Biome>, MultiNoiseBiomeSource.Preset>> preset;

   public MultiNoiseBiomeSource(long var1, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> var3) {
      this(â˜ƒ, â˜ƒ, Optional.empty());
   }

   MultiNoiseBiomeSource(
      long var1, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> var3, Optional<Pair<Registry<Biome>, MultiNoiseBiomeSource.Preset>> var4
   ) {
      this(â˜ƒ, â˜ƒ, DEFAULT_NOISE_PARAMETERS, DEFAULT_NOISE_PARAMETERS, DEFAULT_NOISE_PARAMETERS, DEFAULT_NOISE_PARAMETERS, â˜ƒ);
   }

   private MultiNoiseBiomeSource(
      long var1,
      List<Pair<Biome.ClimateParameters, Supplier<Biome>>> var3,
      MultiNoiseBiomeSource.NoiseParameters var4,
      MultiNoiseBiomeSource.NoiseParameters var5,
      MultiNoiseBiomeSource.NoiseParameters var6,
      MultiNoiseBiomeSource.NoiseParameters var7
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Optional.empty());
   }

   private MultiNoiseBiomeSource(
      long var1,
      List<Pair<Biome.ClimateParameters, Supplier<Biome>>> var3,
      MultiNoiseBiomeSource.NoiseParameters var4,
      MultiNoiseBiomeSource.NoiseParameters var5,
      MultiNoiseBiomeSource.NoiseParameters var6,
      MultiNoiseBiomeSource.NoiseParameters var7,
      Optional<Pair<Registry<Biome>, MultiNoiseBiomeSource.Preset>> var8
   ) {
      super(â˜ƒ.stream().map(Pair::getSecond));
      this.seed = â˜ƒ;
      this.preset = â˜ƒ;
      this.temperatureParams = â˜ƒ;
      this.humidityParams = â˜ƒ;
      this.altitudeParams = â˜ƒ;
      this.weirdnessParams = â˜ƒ;
      this.temperatureNoise = NormalNoise.create(new WorldgenRandom(â˜ƒ), â˜ƒ.firstOctave(), â˜ƒ.amplitudes());
      this.humidityNoise = NormalNoise.create(new WorldgenRandom(â˜ƒ + 1L), â˜ƒ.firstOctave(), â˜ƒ.amplitudes());
      this.altitudeNoise = NormalNoise.create(new WorldgenRandom(â˜ƒ + 2L), â˜ƒ.firstOctave(), â˜ƒ.amplitudes());
      this.weirdnessNoise = NormalNoise.create(new WorldgenRandom(â˜ƒ + 3L), â˜ƒ.firstOctave(), â˜ƒ.amplitudes());
      this.parameters = â˜ƒ;
      this.useY = false;
   }

   public static MultiNoiseBiomeSource overworld(Registry<Biome> var0, long var1) {
      ImmutableList<Pair<Biome.ClimateParameters, Supplier<Biome>>> â˜ƒ = parameters(â˜ƒ);
      MultiNoiseBiomeSource.NoiseParameters â˜ƒx = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0, 0.0, 3.0, 3.0, 3.0, 3.0);
      MultiNoiseBiomeSource.NoiseParameters â˜ƒxx = new MultiNoiseBiomeSource.NoiseParameters(-7, 1.0, 2.0, 4.0, 4.0);
      MultiNoiseBiomeSource.NoiseParameters â˜ƒxxx = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0);
      MultiNoiseBiomeSource.NoiseParameters â˜ƒxxxx = new MultiNoiseBiomeSource.NoiseParameters(-8, 1.2, 0.6, 0.0, 0.0, 1.0, 0.0);
      return new MultiNoiseBiomeSource(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, Optional.empty());
   }

   @Override
   protected Codec<? extends BiomeSource> codec() {
      return CODEC;
   }

   @Override
   public BiomeSource withSeed(long var1) {
      return new MultiNoiseBiomeSource(
         â˜ƒ, this.parameters, this.temperatureParams, this.humidityParams, this.altitudeParams, this.weirdnessParams, this.preset
      );
   }

   private Optional<MultiNoiseBiomeSource.PresetInstance> preset() {
      return this.preset
         .map(var1 -> new MultiNoiseBiomeSource.PresetInstance((MultiNoiseBiomeSource.Preset)var1.getSecond(), (Registry<Biome>)var1.getFirst(), this.seed));
   }

   @Override
   public Biome getNoiseBiome(int var1, int var2, int var3) {
      int â˜ƒ = this.useY ? â˜ƒ : 0;
      Biome.ClimateParameters â˜ƒx = new Biome.ClimateParameters(
         (float)this.temperatureNoise.getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ),
         (float)this.humidityNoise.getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ),
         (float)this.altitudeNoise.getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ),
         (float)this.weirdnessNoise.getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ),
         0.0F
      );
      return (Biome)this.parameters
         .stream()
         .min(Comparator.comparing(var1x -> ((Biome.ClimateParameters)var1x.getFirst()).fitness(â˜ƒ)))
         .map(Pair::getSecond)
         .map(Supplier::get)
         .orElse(net.minecraft.data.worldgen.biome.Biomes.THE_VOID);
   }

   public static ImmutableList<Pair<Biome.ClimateParameters, Supplier<Biome>>> parameters(Registry<Biome> var0) {
      return ImmutableList.of(Pair.of(new Biome.ClimateParameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), (Supplier)() -> â˜ƒ.getOrThrow(Biomes.PLAINS)));
   }

   public boolean stable(long var1) {
      return this.seed == â˜ƒ && this.preset.isPresent() && Objects.equals(((Pair)this.preset.get()).getSecond(), MultiNoiseBiomeSource.Preset.NETHER);
   }

   static class NoiseParameters {
      private final int firstOctave;
      private final DoubleList amplitudes;
      public static final Codec<MultiNoiseBiomeSource.NoiseParameters> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("firstOctave").forGetter(MultiNoiseBiomeSource.NoiseParameters::firstOctave),
                  Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(MultiNoiseBiomeSource.NoiseParameters::amplitudes)
               )
               .apply(var0, MultiNoiseBiomeSource.NoiseParameters::new)
      );

      public NoiseParameters(int var1, List<Double> var2) {
         this.firstOctave = â˜ƒ;
         this.amplitudes = new DoubleArrayList(â˜ƒ);
      }

      public NoiseParameters(int var1, double... var2) {
         this.firstOctave = â˜ƒ;
         this.amplitudes = new DoubleArrayList(â˜ƒ);
      }

      public int firstOctave() {
         return this.firstOctave;
      }

      public DoubleList amplitudes() {
         return this.amplitudes;
      }
   }

   public static class Preset {
      static final Map<ResourceLocation, MultiNoiseBiomeSource.Preset> BY_NAME = Maps.<ResourceLocation, MultiNoiseBiomeSource.Preset>newHashMap();
      public static final MultiNoiseBiomeSource.Preset NETHER = new MultiNoiseBiomeSource.Preset(
         new ResourceLocation("nether"),
         (var0, var1, var2) -> new MultiNoiseBiomeSource(
               var2,
               ImmutableList.of(
                  Pair.of(new Biome.ClimateParameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), (Supplier)() -> var1.getOrThrow(Biomes.NETHER_WASTES)),
                  Pair.of(new Biome.ClimateParameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F), (Supplier)() -> var1.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                  Pair.of(new Biome.ClimateParameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F), (Supplier)() -> var1.getOrThrow(Biomes.CRIMSON_FOREST)),
                  Pair.of(new Biome.ClimateParameters(0.0F, 0.5F, 0.0F, 0.0F, 0.375F), (Supplier)() -> var1.getOrThrow(Biomes.WARPED_FOREST)),
                  Pair.of(new Biome.ClimateParameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F), (Supplier)() -> var1.getOrThrow(Biomes.BASALT_DELTAS))
               ),
               Optional.of(Pair.of(var1, var0))
            )
      );
      final ResourceLocation name;
      private final Function3<MultiNoiseBiomeSource.Preset, Registry<Biome>, Long, MultiNoiseBiomeSource> biomeSource;

      public Preset(ResourceLocation var1, Function3<MultiNoiseBiomeSource.Preset, Registry<Biome>, Long, MultiNoiseBiomeSource> var2) {
         this.name = â˜ƒ;
         this.biomeSource = â˜ƒ;
         BY_NAME.put(â˜ƒ, this);
      }

      public MultiNoiseBiomeSource biomeSource(Registry<Biome> var1, long var2) {
         return this.biomeSource.apply(this, â˜ƒ, â˜ƒ);
      }
   }

   static final class PresetInstance {
      public static final MapCodec<MultiNoiseBiomeSource.PresetInstance> CODEC = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  ResourceLocation.CODEC
                     .flatXmap(
                        var0x -> (DataResult)Optional.ofNullable((MultiNoiseBiomeSource.Preset)MultiNoiseBiomeSource.Preset.BY_NAME.get(var0x))
                              .map(DataResult::success)
                              .orElseGet(() -> DataResult.error("Unknown preset: " + var0x)),
                        var0x -> DataResult.success(var0x.name)
                     )
                     .fieldOf("preset")
                     .stable()
                     .forGetter(MultiNoiseBiomeSource.PresetInstance::preset),
                  RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(MultiNoiseBiomeSource.PresetInstance::biomes),
                  Codec.LONG.fieldOf("seed").stable().forGetter(MultiNoiseBiomeSource.PresetInstance::seed)
               )
               .apply(var0, var0.stable(MultiNoiseBiomeSource.PresetInstance::new))
      );
      private final MultiNoiseBiomeSource.Preset preset;
      private final Registry<Biome> biomes;
      private final long seed;

      PresetInstance(MultiNoiseBiomeSource.Preset var1, Registry<Biome> var2, long var3) {
         this.preset = â˜ƒ;
         this.biomes = â˜ƒ;
         this.seed = â˜ƒ;
      }

      public MultiNoiseBiomeSource.Preset preset() {
         return this.preset;
      }

      public Registry<Biome> biomes() {
         return this.biomes;
      }

      public long seed() {
         return this.seed;
      }

      public MultiNoiseBiomeSource biomeSource() {
         return this.preset.biomeSource(this.biomes, this.seed);
      }
   }
}
