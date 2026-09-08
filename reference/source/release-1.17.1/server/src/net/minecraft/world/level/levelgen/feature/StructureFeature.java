package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MineshaftConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OceanRuinConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RuinedPortalConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ShipwreckConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.NetherFossilFeature;
import net.minecraft.world.level.levelgen.structure.OceanRuinFeature;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StructureFeature<C extends FeatureConfiguration> {
   public static final BiMap<String, StructureFeature<?>> STRUCTURES_REGISTRY = HashBiMap.create();
   private static final Map<StructureFeature<?>, GenerationStep.Decoration> STEP = Maps.newHashMap();
   private static final Logger LOGGER = LogManager.getLogger();
   public static final StructureFeature<JigsawConfiguration> PILLAGER_OUTPOST = register(
      "Pillager_Outpost", new PillagerOutpostFeature(JigsawConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<MineshaftConfiguration> MINESHAFT = register(
      "Mineshaft", new MineshaftFeature(MineshaftConfiguration.CODEC), GenerationStep.Decoration.UNDERGROUND_STRUCTURES
   );
   public static final StructureFeature<NoneFeatureConfiguration> WOODLAND_MANSION = register(
      "Mansion", new WoodlandMansionFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<NoneFeatureConfiguration> JUNGLE_TEMPLE = register(
      "Jungle_Pyramid", new JunglePyramidFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<NoneFeatureConfiguration> DESERT_PYRAMID = register(
      "Desert_Pyramid", new DesertPyramidFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<NoneFeatureConfiguration> IGLOO = register(
      "Igloo", new IglooFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<RuinedPortalConfiguration> RUINED_PORTAL = register(
      "Ruined_Portal", new RuinedPortalFeature(RuinedPortalConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<ShipwreckConfiguration> SHIPWRECK = register(
      "Shipwreck", new ShipwreckFeature(ShipwreckConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final SwamplandHutFeature SWAMP_HUT = register(
      "Swamp_Hut", new SwamplandHutFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<NoneFeatureConfiguration> STRONGHOLD = register(
      "Stronghold", new StrongholdFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.STRONGHOLDS
   );
   public static final StructureFeature<NoneFeatureConfiguration> OCEAN_MONUMENT = register(
      "Monument", new OceanMonumentFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<OceanRuinConfiguration> OCEAN_RUIN = register(
      "Ocean_Ruin", new OceanRuinFeature(OceanRuinConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<NoneFeatureConfiguration> NETHER_BRIDGE = register(
      "Fortress", new NetherFortressFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.UNDERGROUND_DECORATION
   );
   public static final StructureFeature<NoneFeatureConfiguration> END_CITY = register(
      "EndCity", new EndCityFeature(NoneFeatureConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<ProbabilityFeatureConfiguration> BURIED_TREASURE = register(
      "Buried_Treasure", new BuriedTreasureFeature(ProbabilityFeatureConfiguration.CODEC), GenerationStep.Decoration.UNDERGROUND_STRUCTURES
   );
   public static final StructureFeature<JigsawConfiguration> VILLAGE = register(
      "Village", new VillageFeature(JigsawConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final StructureFeature<RangeDecoratorConfiguration> NETHER_FOSSIL = register(
      "Nether_Fossil", new NetherFossilFeature(RangeDecoratorConfiguration.CODEC), GenerationStep.Decoration.UNDERGROUND_DECORATION
   );
   public static final StructureFeature<JigsawConfiguration> BASTION_REMNANT = register(
      "Bastion_Remnant", new BastionFeature(JigsawConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES
   );
   public static final List<StructureFeature<?>> NOISE_AFFECTING_FEATURES = ImmutableList.of(PILLAGER_OUTPOST, VILLAGE, NETHER_FOSSIL, STRONGHOLD);
   private static final ResourceLocation JIGSAW_RENAME = new ResourceLocation("jigsaw");
   private static final Map<ResourceLocation, ResourceLocation> RENAMES = ImmutableMap.<ResourceLocation, ResourceLocation>builder()
      .put(new ResourceLocation("nvi"), JIGSAW_RENAME)
      .put(new ResourceLocation("pcp"), JIGSAW_RENAME)
      .put(new ResourceLocation("bastionremnant"), JIGSAW_RENAME)
      .put(new ResourceLocation("runtime"), JIGSAW_RENAME)
      .build();
   public static final int MAX_STRUCTURE_RANGE = 8;
   private final Codec<ConfiguredStructureFeature<C, StructureFeature<C>>> configuredStructureCodec;

   private static <F extends StructureFeature<?>> F register(String var0, F var1, GenerationStep.Decoration var2) {
      STRUCTURES_REGISTRY.put(â˜ƒ.toLowerCase(Locale.ROOT), â˜ƒ);
      STEP.put(â˜ƒ, â˜ƒ);
      return Registry.register(Registry.STRUCTURE_FEATURE, â˜ƒ.toLowerCase(Locale.ROOT), â˜ƒ);
   }

   public StructureFeature(Codec<C> var1) {
      this.configuredStructureCodec = â˜ƒ.fieldOf("config")
         .<ConfiguredStructureFeature<C, StructureFeature<C>>>xmap(var1x -> new ConfiguredStructureFeature<>(this, (C)var1x), var0 -> var0.config)
         .codec();
   }

   public GenerationStep.Decoration step() {
      return (GenerationStep.Decoration)STEP.get(this);
   }

   public static void bootstrap() {
   }

   @Nullable
   public static StructureStart<?> loadStaticStart(ServerLevel var0, CompoundTag var1, long var2) {
      String â˜ƒ = â˜ƒ.getString("id");
      if ("INVALID".equals(â˜ƒ)) {
         return StructureStart.INVALID_START;
      } else {
         StructureFeature<?> â˜ƒ = Registry.STRUCTURE_FEATURE.get(new ResourceLocation(â˜ƒ.toLowerCase(Locale.ROOT)));
         if (â˜ƒ == null) {
            LOGGER.error("Unknown feature id: {}", â˜ƒ);
            return null;
         } else {
            ChunkPos â˜ƒ = new ChunkPos(â˜ƒ.getInt("ChunkX"), â˜ƒ.getInt("ChunkZ"));
            int â˜ƒx = â˜ƒ.getInt("references");
            ListTag â˜ƒxx = â˜ƒ.getList("Children", 10);

            try {
               StructureStart<?> â˜ƒxxx = â˜ƒ.createStart(â˜ƒ, â˜ƒx, â˜ƒ);

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxx.size(); ++â˜ƒxxxx) {
                  CompoundTag â˜ƒxxxxx = â˜ƒxx.getCompound(â˜ƒxxxx);
                  String â˜ƒxxxxxx = â˜ƒxxxxx.getString("id").toLowerCase(Locale.ROOT);
                  ResourceLocation â˜ƒxxxxxxx = new ResourceLocation(â˜ƒxxxxxx);
                  ResourceLocation â˜ƒxxxxxxxx = (ResourceLocation)RENAMES.getOrDefault(â˜ƒxxxxxxx, â˜ƒxxxxxxx);
                  StructurePieceType â˜ƒxxxxxxxxx = Registry.STRUCTURE_PIECE.get(â˜ƒxxxxxxxx);
                  if (â˜ƒxxxxxxxxx == null) {
                     LOGGER.error("Unknown structure piece id: {}", â˜ƒxxxxxxxx);
                  } else {
                     try {
                        StructurePiece â˜ƒxxxxx = â˜ƒxxxxxxxxx.load(â˜ƒ, â˜ƒxxxxx);
                        â˜ƒxxx.addPiece(â˜ƒxxxxx);
                     } catch (Exception var17) {
                        LOGGER.error("Exception loading structure piece with id {}", â˜ƒxxxxxxxx, var17);
                     }
                  }
               }

               return â˜ƒxxx;
            } catch (Exception var18) {
               LOGGER.error("Failed Start with id {}", â˜ƒ, var18);
               return null;
            }
         }
      }
   }

   public Codec<ConfiguredStructureFeature<C, StructureFeature<C>>> configuredStructureCodec() {
      return this.configuredStructureCodec;
   }

   public ConfiguredStructureFeature<C, ? extends StructureFeature<C>> configured(C var1) {
      return new ConfiguredStructureFeature<>(this, â˜ƒ);
   }

   @Nullable
   public BlockPos getNearestGeneratedFeature(
      LevelReader var1, StructureFeatureManager var2, BlockPos var3, int var4, boolean var5, long var6, StructureFeatureConfiguration var8
   ) {
      int â˜ƒ = â˜ƒ.spacing();
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getX());
      int â˜ƒxx = SectionPos.blockToSectionCoord(â˜ƒ.getZ());
      int â˜ƒxxx = 0;

      for(WorldgenRandom â˜ƒxxxx = new WorldgenRandom(); â˜ƒxxx <= â˜ƒ; ++â˜ƒxxx) {
         for(int â˜ƒxxxxx = -â˜ƒxxx; â˜ƒxxxxx <= â˜ƒxxx; ++â˜ƒxxxxx) {
            boolean â˜ƒxxxxxx = â˜ƒxxxxx == -â˜ƒxxx || â˜ƒxxxxx == â˜ƒxxx;

            for(int â˜ƒxxxxxxx = -â˜ƒxxx; â˜ƒxxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxxx) {
               boolean â˜ƒxxxxxxxx = â˜ƒxxxxxxx == -â˜ƒxxx || â˜ƒxxxxxxx == â˜ƒxxx;
               if (â˜ƒxxxxxx || â˜ƒxxxxxxxx) {
                  int â˜ƒxxxxxxxxx = â˜ƒx + â˜ƒ * â˜ƒxxxxx;
                  int â˜ƒxxxxxxxxxx = â˜ƒxx + â˜ƒ * â˜ƒxxxxxxx;
                  ChunkPos â˜ƒxxxxxxxxxxx = this.getPotentialFeatureChunk(â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
                  boolean â˜ƒxxxxxxxxxxxx = â˜ƒ.getBiomeManager().getPrimaryBiomeAtChunk(â˜ƒxxxxxxxxxxx).getGenerationSettings().isValidStart(this);
                  if (â˜ƒxxxxxxxxxxxx) {
                     ChunkAccess â˜ƒxxxxxxxxxxxxx = â˜ƒ.getChunk(â˜ƒxxxxxxxxxxx.x, â˜ƒxxxxxxxxxxx.z, ChunkStatus.STRUCTURE_STARTS);
                     StructureStart<?> â˜ƒxxxxxxxxxxxxxx = â˜ƒ.getStartForFeature(SectionPos.bottomOf(â˜ƒxxxxxxxxxxxxx), this, â˜ƒxxxxxxxxxxxxx);
                     if (â˜ƒxxxxxxxxxxxxxx != null && â˜ƒxxxxxxxxxxxxxx.isValid()) {
                        if (â˜ƒ && â˜ƒxxxxxxxxxxxxxx.canBeReferenced()) {
                           â˜ƒxxxxxxxxxxxxxx.addReference();
                           return â˜ƒxxxxxxxxxxxxxx.getLocatePos();
                        }

                        if (!â˜ƒ) {
                           return â˜ƒxxxxxxxxxxxxxx.getLocatePos();
                        }
                     }
                  }

                  if (â˜ƒxxx == 0) {
                     break;
                  }
               }
            }

            if (â˜ƒxxx == 0) {
               break;
            }
         }
      }

      return null;
   }

   protected boolean linearSeparation() {
      return true;
   }

   public final ChunkPos getPotentialFeatureChunk(StructureFeatureConfiguration var1, long var2, WorldgenRandom var4, int var5, int var6) {
      int â˜ƒxx = â˜ƒ.spacing();
      int â˜ƒxxx = â˜ƒ.separation();
      int â˜ƒxxxx = Math.floorDiv(â˜ƒ, â˜ƒxx);
      int â˜ƒxxxxx = Math.floorDiv(â˜ƒ, â˜ƒxx);
      â˜ƒ.setLargeFeatureWithSalt(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ.salt());
      int â˜ƒ;
      int â˜ƒx;
      if (this.linearSeparation()) {
         â˜ƒ = â˜ƒ.nextInt(â˜ƒxx - â˜ƒxxx);
         â˜ƒx = â˜ƒ.nextInt(â˜ƒxx - â˜ƒxxx);
      } else {
         â˜ƒ = (â˜ƒ.nextInt(â˜ƒxx - â˜ƒxxx) + â˜ƒ.nextInt(â˜ƒxx - â˜ƒxxx)) / 2;
         â˜ƒx = (â˜ƒ.nextInt(â˜ƒxx - â˜ƒxxx) + â˜ƒ.nextInt(â˜ƒxx - â˜ƒxxx)) / 2;
      }

      return new ChunkPos(â˜ƒxxxx * â˜ƒxx + â˜ƒ, â˜ƒxxxxx * â˜ƒxx + â˜ƒx);
   }

   protected boolean isFeatureChunk(
      ChunkGenerator var1, BiomeSource var2, long var3, WorldgenRandom var5, ChunkPos var6, Biome var7, ChunkPos var8, C var9, LevelHeightAccessor var10
   ) {
      return true;
   }

   private StructureStart<C> createStart(ChunkPos var1, int var2, long var3) {
      return this.getStartFactory().create(this, â˜ƒ, â˜ƒ, â˜ƒ);
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
      WorldgenRandom var10,
      StructureFeatureConfiguration var11,
      C var12,
      LevelHeightAccessor var13
   ) {
      ChunkPos â˜ƒ = this.getPotentialFeatureChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.x, â˜ƒ.z);
      if (â˜ƒ.x == â˜ƒ.x && â˜ƒ.z == â˜ƒ.z && this.isFeatureChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         StructureStart<C> â˜ƒx = this.createStart(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒx.generatePieces(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒx.isValid()) {
            return â˜ƒx;
         }
      }

      return StructureStart.INVALID_START;
   }

   public abstract StructureFeature.StructureStartFactory<C> getStartFactory();

   public String getFeatureName() {
      return (String)STRUCTURES_REGISTRY.inverse().get(this);
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialEnemies() {
      return MobSpawnSettings.EMPTY_MOB_LIST;
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialAnimals() {
      return MobSpawnSettings.EMPTY_MOB_LIST;
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialUndergroundWaterAnimals() {
      return MobSpawnSettings.EMPTY_MOB_LIST;
   }

   public interface StructureStartFactory<C extends FeatureConfiguration> {
      StructureStart<C> create(StructureFeature<C> var1, ChunkPos var2, int var3, long var4);
   }
}
