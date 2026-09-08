package net.minecraft.world.level.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.io.File;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeZoomer;
import net.minecraft.world.level.biome.FuzzyOffsetBiomeZoomer;
import net.minecraft.world.level.biome.FuzzyOffsetConstantColumnBiomeZoomer;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class DimensionType {
   public static final int BITS_FOR_Y = BlockPos.PACKED_Y_LENGTH;
   public static final int MIN_HEIGHT = 16;
   public static final int Y_SIZE = (1 << BITS_FOR_Y) - 32;
   public static final int MAX_Y = (Y_SIZE >> 1) - 1;
   public static final int MIN_Y = MAX_Y - Y_SIZE + 1;
   public static final ResourceLocation OVERWORLD_EFFECTS = new ResourceLocation("overworld");
   public static final ResourceLocation NETHER_EFFECTS = new ResourceLocation("the_nether");
   public static final ResourceLocation END_EFFECTS = new ResourceLocation("the_end");
   public static final Codec<DimensionType> DIRECT_CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.LONG
                     .optionalFieldOf("fixed_time")
                     .xmap(
                        var0x -> (OptionalLong)var0x.map(OptionalLong::of).orElseGet(OptionalLong::empty),
                        var0x -> var0x.isPresent() ? Optional.of(var0x.getAsLong()) : Optional.empty()
                     )
                     .forGetter(var0x -> var0x.fixedTime),
                  Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionType::hasSkyLight),
                  Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionType::hasCeiling),
                  Codec.BOOL.fieldOf("ultrawarm").forGetter(DimensionType::ultraWarm),
                  Codec.BOOL.fieldOf("natural").forGetter(DimensionType::natural),
                  Codec.doubleRange(1.0E-5F, 3.0E7).fieldOf("coordinate_scale").forGetter(DimensionType::coordinateScale),
                  Codec.BOOL.fieldOf("piglin_safe").forGetter(DimensionType::piglinSafe),
                  Codec.BOOL.fieldOf("bed_works").forGetter(DimensionType::bedWorks),
                  Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(DimensionType::respawnAnchorWorks),
                  Codec.BOOL.fieldOf("has_raids").forGetter(DimensionType::hasRaids),
                  Codec.intRange(MIN_Y, MAX_Y).fieldOf("min_y").forGetter(DimensionType::minY),
                  Codec.intRange(16, Y_SIZE).fieldOf("height").forGetter(DimensionType::height),
                  Codec.intRange(0, Y_SIZE).fieldOf("logical_height").forGetter(DimensionType::logicalHeight),
                  ResourceLocation.CODEC.fieldOf("infiniburn").forGetter(var0x -> var0x.infiniburn),
                  ResourceLocation.CODEC.fieldOf("effects").orElse(OVERWORLD_EFFECTS).forGetter(var0x -> var0x.effectsLocation),
                  Codec.FLOAT.fieldOf("ambient_light").forGetter(var0x -> var0x.ambientLight)
               )
               .apply(var0, DimensionType::new)
      )
      .comapFlatMap(DimensionType::guardY, Function.identity());
   private static final int MOON_PHASES = 8;
   public static final float[] MOON_BRIGHTNESS_PER_PHASE = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   public static final ResourceKey<DimensionType> OVERWORLD_LOCATION = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation("overworld"));
   public static final ResourceKey<DimensionType> NETHER_LOCATION = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation("the_nether"));
   public static final ResourceKey<DimensionType> END_LOCATION = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation("the_end"));
   protected static final DimensionType DEFAULT_OVERWORLD = create(
      OptionalLong.empty(),
      true,
      false,
      false,
      true,
      1.0,
      false,
      false,
      true,
      false,
      true,
      0,
      256,
      256,
      FuzzyOffsetConstantColumnBiomeZoomer.INSTANCE,
      BlockTags.INFINIBURN_OVERWORLD.getName(),
      OVERWORLD_EFFECTS,
      0.0F
   );
   protected static final DimensionType DEFAULT_NETHER = create(
      OptionalLong.of(18000L),
      false,
      true,
      true,
      false,
      8.0,
      false,
      true,
      false,
      true,
      false,
      0,
      256,
      128,
      FuzzyOffsetBiomeZoomer.INSTANCE,
      BlockTags.INFINIBURN_NETHER.getName(),
      NETHER_EFFECTS,
      0.1F
   );
   protected static final DimensionType DEFAULT_END = create(
      OptionalLong.of(6000L),
      false,
      false,
      false,
      false,
      1.0,
      true,
      false,
      false,
      false,
      true,
      0,
      256,
      256,
      FuzzyOffsetBiomeZoomer.INSTANCE,
      BlockTags.INFINIBURN_END.getName(),
      END_EFFECTS,
      0.0F
   );
   public static final ResourceKey<DimensionType> OVERWORLD_CAVES_LOCATION = ResourceKey.create(
      Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation("overworld_caves")
   );
   protected static final DimensionType DEFAULT_OVERWORLD_CAVES = create(
      OptionalLong.empty(),
      true,
      true,
      false,
      true,
      1.0,
      false,
      false,
      true,
      false,
      true,
      0,
      256,
      256,
      FuzzyOffsetConstantColumnBiomeZoomer.INSTANCE,
      BlockTags.INFINIBURN_OVERWORLD.getName(),
      OVERWORLD_EFFECTS,
      0.0F
   );
   public static final Codec<Supplier<DimensionType>> CODEC = RegistryFileCodec.create(Registry.DIMENSION_TYPE_REGISTRY, DIRECT_CODEC);
   private final OptionalLong fixedTime;
   private final boolean hasSkylight;
   private final boolean hasCeiling;
   private final boolean ultraWarm;
   private final boolean natural;
   private final double coordinateScale;
   private final boolean createDragonFight;
   private final boolean piglinSafe;
   private final boolean bedWorks;
   private final boolean respawnAnchorWorks;
   private final boolean hasRaids;
   private final int minY;
   private final int height;
   private final int logicalHeight;
   private final BiomeZoomer biomeZoomer;
   private final ResourceLocation infiniburn;
   private final ResourceLocation effectsLocation;
   private final float ambientLight;
   private final transient float[] brightnessRamp;

   private static DataResult<DimensionType> guardY(DimensionType var0) {
      if (â˜ƒ.height() < 16) {
         return DataResult.error("height has to be at least 16");
      } else if (â˜ƒ.minY() + â˜ƒ.height() > MAX_Y + 1) {
         return DataResult.error("min_y + height cannot be higher than: " + (MAX_Y + 1));
      } else if (â˜ƒ.logicalHeight() > â˜ƒ.height()) {
         return DataResult.error("logical_height cannot be higher than height");
      } else if (â˜ƒ.height() % 16 != 0) {
         return DataResult.error("height has to be multiple of 16");
      } else {
         return â˜ƒ.minY() % 16 != 0 ? DataResult.error("min_y has to be a multiple of 16") : DataResult.success(â˜ƒ);
      }
   }

   private DimensionType(
      OptionalLong var1,
      boolean var2,
      boolean var3,
      boolean var4,
      boolean var5,
      double var6,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      int var12,
      int var13,
      int var14,
      ResourceLocation var15,
      ResourceLocation var16,
      float var17
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, FuzzyOffsetBiomeZoomer.INSTANCE, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static DimensionType create(
      OptionalLong var0,
      boolean var1,
      boolean var2,
      boolean var3,
      boolean var4,
      double var5,
      boolean var7,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      int var12,
      int var13,
      int var14,
      BiomeZoomer var15,
      ResourceLocation var16,
      ResourceLocation var17,
      float var18
   ) {
      DimensionType â˜ƒ = new DimensionType(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      guardY(â˜ƒ).error().ifPresent(var0x -> {
         throw new IllegalStateException(var0x.message());
      });
      return â˜ƒ;
   }

   @Deprecated
   private DimensionType(
      OptionalLong var1,
      boolean var2,
      boolean var3,
      boolean var4,
      boolean var5,
      double var6,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      boolean var12,
      int var13,
      int var14,
      int var15,
      BiomeZoomer var16,
      ResourceLocation var17,
      ResourceLocation var18,
      float var19
   ) {
      this.fixedTime = â˜ƒ;
      this.hasSkylight = â˜ƒ;
      this.hasCeiling = â˜ƒ;
      this.ultraWarm = â˜ƒ;
      this.natural = â˜ƒ;
      this.coordinateScale = â˜ƒ;
      this.createDragonFight = â˜ƒ;
      this.piglinSafe = â˜ƒ;
      this.bedWorks = â˜ƒ;
      this.respawnAnchorWorks = â˜ƒ;
      this.hasRaids = â˜ƒ;
      this.minY = â˜ƒ;
      this.height = â˜ƒ;
      this.logicalHeight = â˜ƒ;
      this.biomeZoomer = â˜ƒ;
      this.infiniburn = â˜ƒ;
      this.effectsLocation = â˜ƒ;
      this.ambientLight = â˜ƒ;
      this.brightnessRamp = fillBrightnessRamp(â˜ƒ);
   }

   private static float[] fillBrightnessRamp(float var0) {
      float[] â˜ƒ = new float[16];

      for(int â˜ƒx = 0; â˜ƒx <= 15; ++â˜ƒx) {
         float â˜ƒxx = (float)â˜ƒx / 15.0F;
         float â˜ƒxxx = â˜ƒxx / (4.0F - 3.0F * â˜ƒxx);
         â˜ƒ[â˜ƒx] = Mth.lerp(â˜ƒ, â˜ƒxxx, 1.0F);
      }

      return â˜ƒ;
   }

   @Deprecated
   public static DataResult<ResourceKey<Level>> parseLegacy(Dynamic<?> var0) {
      Optional<Number> â˜ƒ = â˜ƒ.asNumber().result();
      if (â˜ƒ.isPresent()) {
         int â˜ƒx = ((Number)â˜ƒ.get()).intValue();
         if (â˜ƒx == -1) {
            return DataResult.success(Level.NETHER);
         }

         if (â˜ƒx == 0) {
            return DataResult.success(Level.OVERWORLD);
         }

         if (â˜ƒx == 1) {
            return DataResult.success(Level.END);
         }
      }

      return Level.RESOURCE_KEY_CODEC.parse(â˜ƒ);
   }

   public static RegistryAccess.RegistryHolder registerBuiltin(RegistryAccess.RegistryHolder var0) {
      WritableRegistry<DimensionType> â˜ƒ = â˜ƒ.ownedRegistryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
      â˜ƒ.register(OVERWORLD_LOCATION, DEFAULT_OVERWORLD, Lifecycle.stable());
      â˜ƒ.register(OVERWORLD_CAVES_LOCATION, DEFAULT_OVERWORLD_CAVES, Lifecycle.stable());
      â˜ƒ.register(NETHER_LOCATION, DEFAULT_NETHER, Lifecycle.stable());
      â˜ƒ.register(END_LOCATION, DEFAULT_END, Lifecycle.stable());
      return â˜ƒ;
   }

   private static ChunkGenerator defaultEndGenerator(Registry<Biome> var0, Registry<NoiseGeneratorSettings> var1, long var2) {
      return new NoiseBasedChunkGenerator(new TheEndBiomeSource(â˜ƒ, â˜ƒ), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.END));
   }

   private static ChunkGenerator defaultNetherGenerator(Registry<Biome> var0, Registry<NoiseGeneratorSettings> var1, long var2) {
      return new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.Preset.NETHER.biomeSource(â˜ƒ, â˜ƒ), â˜ƒ, () -> â˜ƒ.getOrThrow(NoiseGeneratorSettings.NETHER));
   }

   public static MappedRegistry<LevelStem> defaultDimensions(
      Registry<DimensionType> var0, Registry<Biome> var1, Registry<NoiseGeneratorSettings> var2, long var3
   ) {
      MappedRegistry<LevelStem> â˜ƒ = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental());
      â˜ƒ.register(LevelStem.NETHER, new LevelStem(() -> â˜ƒ.getOrThrow(NETHER_LOCATION), defaultNetherGenerator(â˜ƒ, â˜ƒ, â˜ƒ)), Lifecycle.stable());
      â˜ƒ.register(LevelStem.END, new LevelStem(() -> â˜ƒ.getOrThrow(END_LOCATION), defaultEndGenerator(â˜ƒ, â˜ƒ, â˜ƒ)), Lifecycle.stable());
      return â˜ƒ;
   }

   public static double getTeleportationScale(DimensionType var0, DimensionType var1) {
      double â˜ƒ = â˜ƒ.coordinateScale();
      double â˜ƒx = â˜ƒ.coordinateScale();
      return â˜ƒ / â˜ƒx;
   }

   @Deprecated
   public String getFileSuffix() {
      return this.equalTo(DEFAULT_END) ? "_end" : "";
   }

   public static File getStorageFolder(ResourceKey<Level> var0, File var1) {
      if (â˜ƒ == Level.OVERWORLD) {
         return â˜ƒ;
      } else if (â˜ƒ == Level.END) {
         return new File(â˜ƒ, "DIM1");
      } else {
         return â˜ƒ == Level.NETHER ? new File(â˜ƒ, "DIM-1") : new File(â˜ƒ, "dimensions/" + â˜ƒ.location().getNamespace() + "/" + â˜ƒ.location().getPath());
      }
   }

   public boolean hasSkyLight() {
      return this.hasSkylight;
   }

   public boolean hasCeiling() {
      return this.hasCeiling;
   }

   public boolean ultraWarm() {
      return this.ultraWarm;
   }

   public boolean natural() {
      return this.natural;
   }

   public double coordinateScale() {
      return this.coordinateScale;
   }

   public boolean piglinSafe() {
      return this.piglinSafe;
   }

   public boolean bedWorks() {
      return this.bedWorks;
   }

   public boolean respawnAnchorWorks() {
      return this.respawnAnchorWorks;
   }

   public boolean hasRaids() {
      return this.hasRaids;
   }

   public int minY() {
      return this.minY;
   }

   public int height() {
      return this.height;
   }

   public int logicalHeight() {
      return this.logicalHeight;
   }

   public boolean createDragonFight() {
      return this.createDragonFight;
   }

   public BiomeZoomer getBiomeZoomer() {
      return this.biomeZoomer;
   }

   public boolean hasFixedTime() {
      return this.fixedTime.isPresent();
   }

   public float timeOfDay(long var1) {
      double â˜ƒ = Mth.frac((double)this.fixedTime.orElse(â˜ƒ) / 24000.0 - 0.25);
      double â˜ƒx = 0.5 - Math.cos(â˜ƒ * Math.PI) / 2.0;
      return (float)(â˜ƒ * 2.0 + â˜ƒx) / 3.0F;
   }

   public int moonPhase(long var1) {
      return (int)(â˜ƒ / 24000L % 8L + 8L) % 8;
   }

   public float brightness(int var1) {
      return this.brightnessRamp[â˜ƒ];
   }

   public Tag<Block> infiniburn() {
      Tag<Block> â˜ƒ = BlockTags.getAllTags().getTag(this.infiniburn);
      return (Tag<Block>)(â˜ƒ != null ? â˜ƒ : BlockTags.INFINIBURN_OVERWORLD);
   }

   public ResourceLocation effectsLocation() {
      return this.effectsLocation;
   }

   public boolean equalTo(DimensionType var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return this.hasSkylight == â˜ƒ.hasSkylight
            && this.hasCeiling == â˜ƒ.hasCeiling
            && this.ultraWarm == â˜ƒ.ultraWarm
            && this.natural == â˜ƒ.natural
            && this.coordinateScale == â˜ƒ.coordinateScale
            && this.createDragonFight == â˜ƒ.createDragonFight
            && this.piglinSafe == â˜ƒ.piglinSafe
            && this.bedWorks == â˜ƒ.bedWorks
            && this.respawnAnchorWorks == â˜ƒ.respawnAnchorWorks
            && this.hasRaids == â˜ƒ.hasRaids
            && this.minY == â˜ƒ.minY
            && this.height == â˜ƒ.height
            && this.logicalHeight == â˜ƒ.logicalHeight
            && Float.compare(â˜ƒ.ambientLight, this.ambientLight) == 0
            && this.fixedTime.equals(â˜ƒ.fixedTime)
            && this.biomeZoomer.equals(â˜ƒ.biomeZoomer)
            && this.infiniburn.equals(â˜ƒ.infiniburn)
            && this.effectsLocation.equals(â˜ƒ.effectsLocation);
      }
   }
}
