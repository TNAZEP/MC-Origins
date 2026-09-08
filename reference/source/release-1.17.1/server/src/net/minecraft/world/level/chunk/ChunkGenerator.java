package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.SingleBaseStoneSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StrongholdConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public abstract class ChunkGenerator {
   public static final Codec<ChunkGenerator> CODEC = Registry.CHUNK_GENERATOR.dispatchStable(ChunkGenerator::codec, Function.identity());
   protected final BiomeSource biomeSource;
   protected final BiomeSource runtimeBiomeSource;
   private final StructureSettings settings;
   private final long strongholdSeed;
   private final List<ChunkPos> strongholdPositions = Lists.<ChunkPos>newArrayList();
   private final BaseStoneSource defaultBaseStoneSource;

   public ChunkGenerator(BiomeSource var1, StructureSettings var2) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, 0L);
   }

   public ChunkGenerator(BiomeSource var1, BiomeSource var2, StructureSettings var3, long var4) {
      this.biomeSource = â˜ƒ;
      this.runtimeBiomeSource = â˜ƒ;
      this.settings = â˜ƒ;
      this.strongholdSeed = â˜ƒ;
      this.defaultBaseStoneSource = new SingleBaseStoneSource(Blocks.STONE.defaultBlockState());
   }

   private void generateStrongholds() {
      if (this.strongholdPositions.isEmpty()) {
         StrongholdConfiguration â˜ƒ = this.settings.stronghold();
         if (â˜ƒ != null && â˜ƒ.count() != 0) {
            List<Biome> â˜ƒx = Lists.<Biome>newArrayList();

            for(Biome â˜ƒxx : this.biomeSource.possibleBiomes()) {
               if (â˜ƒxx.getGenerationSettings().isValidStart(StructureFeature.STRONGHOLD)) {
                  â˜ƒx.add(â˜ƒxx);
               }
            }

            int â˜ƒxx = â˜ƒ.distance();
            int â˜ƒxxx = â˜ƒ.count();
            int â˜ƒxxxx = â˜ƒ.spread();
            Random â˜ƒxxxxx = new Random();
            â˜ƒxxxxx.setSeed(this.strongholdSeed);
            double â˜ƒxxxxxx = â˜ƒxxxxx.nextDouble() * Math.PI * 2.0;
            int â˜ƒxxxxxxx = 0;
            int â˜ƒxxxxxxxx = 0;

            for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxxxxx) {
               double â˜ƒxxxxxxxxxx = (double)(4 * â˜ƒxx + â˜ƒxx * â˜ƒxxxxxxxx * 6) + (â˜ƒxxxxx.nextDouble() - 0.5) * (double)â˜ƒxx * 2.5;
               int â˜ƒxxxxxxxxxxx = (int)Math.round(Math.cos(â˜ƒxxxxxx) * â˜ƒxxxxxxxxxx);
               int â˜ƒxxxxxxxxxxxx = (int)Math.round(Math.sin(â˜ƒxxxxxx) * â˜ƒxxxxxxxxxx);
               BlockPos â˜ƒxxxxxxxxxxxxx = this.biomeSource
                  .findBiomeHorizontal(
                     SectionPos.sectionToBlockCoord(â˜ƒxxxxxxxxxxx, 8), 0, SectionPos.sectionToBlockCoord(â˜ƒxxxxxxxxxxxx, 8), 112, â˜ƒx::contains, â˜ƒxxxxx
                  );
               if (â˜ƒxxxxxxxxxxxxx != null) {
                  â˜ƒxxxxxxxxxxx = SectionPos.blockToSectionCoord(â˜ƒxxxxxxxxxxxxx.getX());
                  â˜ƒxxxxxxxxxxxx = SectionPos.blockToSectionCoord(â˜ƒxxxxxxxxxxxxx.getZ());
               }

               this.strongholdPositions.add(new ChunkPos(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx));
               â˜ƒxxxxxx += (Math.PI * 2) / (double)â˜ƒxxxx;
               if (++â˜ƒxxxxxxx == â˜ƒxxxx) {
                  ++â˜ƒxxxxxxxx;
                  â˜ƒxxxxxxx = 0;
                  â˜ƒxxxx += 2 * â˜ƒxxxx / (â˜ƒxxxxxxxx + 1);
                  â˜ƒxxxx = Math.min(â˜ƒxxxx, â˜ƒxxx - â˜ƒxxxxxxxxx);
                  â˜ƒxxxxxx += â˜ƒxxxxx.nextDouble() * Math.PI * 2.0;
               }
            }
         }
      }
   }

   protected abstract Codec<? extends ChunkGenerator> codec();

   public abstract ChunkGenerator withSeed(long var1);

   public void createBiomes(Registry<Biome> var1, ChunkAccess var2) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      ((ProtoChunk)â˜ƒ).setBiomes(new ChunkBiomeContainer(â˜ƒ, â˜ƒ, â˜ƒ, this.runtimeBiomeSource));
   }

   public void applyCarvers(long var1, BiomeManager var3, ChunkAccess var4, GenerationStep.Carving var5) {
      BiomeManager â˜ƒ = â˜ƒ.withDifferentSource(this.biomeSource);
      WorldgenRandom â˜ƒx = new WorldgenRandom();
      int â˜ƒxx = 8;
      ChunkPos â˜ƒxxx = â˜ƒ.getPos();
      CarvingContext â˜ƒxxxx = new CarvingContext(this, â˜ƒ);
      Aquifer â˜ƒxxxxx = this.createAquifer(â˜ƒ);
      BitSet â˜ƒxxxxxx = ((ProtoChunk)â˜ƒ).getOrCreateCarvingMask(â˜ƒ);

      for(int â˜ƒxxxxxxx = -8; â˜ƒxxxxxxx <= 8; ++â˜ƒxxxxxxx) {
         for(int â˜ƒxxxxxxxx = -8; â˜ƒxxxxxxxx <= 8; ++â˜ƒxxxxxxxx) {
            ChunkPos â˜ƒxxxxxxxxx = new ChunkPos(â˜ƒxxx.x + â˜ƒxxxxxxx, â˜ƒxxx.z + â˜ƒxxxxxxxx);
            BiomeGenerationSettings â˜ƒxxxxxxxxxx = this.biomeSource
               .getNoiseBiome(QuartPos.fromBlock(â˜ƒxxxxxxxxx.getMinBlockX()), 0, QuartPos.fromBlock(â˜ƒxxxxxxxxx.getMinBlockZ()))
               .getGenerationSettings();
            List<Supplier<ConfiguredWorldCarver<?>>> â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx.getCarvers(â˜ƒ);
            ListIterator<Supplier<ConfiguredWorldCarver<?>>> â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.listIterator();

            while(â˜ƒxxxxxxxxxxxx.hasNext()) {
               int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.nextIndex();
               ConfiguredWorldCarver<?> â˜ƒxxxxxxxxxxxxxx = (ConfiguredWorldCarver)((Supplier)â˜ƒxxxxxxxxxxxx.next()).get();
               â˜ƒx.setLargeFeatureSeed(â˜ƒ + (long)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxx.x, â˜ƒxxxxxxxxx.z);
               if (â˜ƒxxxxxxxxxxxxxx.isStartChunk(â˜ƒx)) {
                  â˜ƒxxxxxxxxxxxxxx.carve(â˜ƒxxxx, â˜ƒ, â˜ƒ::getBiome, â˜ƒx, â˜ƒxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxx);
               }
            }
         }
      }
   }

   protected Aquifer createAquifer(ChunkAccess var1) {
      return Aquifer.createDisabled(this.getSeaLevel(), Blocks.WATER.defaultBlockState());
   }

   @Nullable
   public BlockPos findNearestMapFeature(ServerLevel var1, StructureFeature<?> var2, BlockPos var3, int var4, boolean var5) {
      if (!this.biomeSource.canGenerateStructure(â˜ƒ)) {
         return null;
      } else if (â˜ƒ == StructureFeature.STRONGHOLD) {
         this.generateStrongholds();
         BlockPos â˜ƒ = null;
         double â˜ƒx = Double.MAX_VALUE;
         BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

         for(ChunkPos â˜ƒxxx : this.strongholdPositions) {
            â˜ƒxx.set(SectionPos.sectionToBlockCoord(â˜ƒxxx.x, 8), 32, SectionPos.sectionToBlockCoord(â˜ƒxxx.z, 8));
            double â˜ƒxxxx = â˜ƒxx.distSqr(â˜ƒ);
            if (â˜ƒ == null) {
               â˜ƒ = new BlockPos(â˜ƒxx);
               â˜ƒx = â˜ƒxxxx;
            } else if (â˜ƒxxxx < â˜ƒx) {
               â˜ƒ = new BlockPos(â˜ƒxx);
               â˜ƒx = â˜ƒxxxx;
            }
         }

         return â˜ƒ;
      } else {
         StructureFeatureConfiguration â˜ƒ = this.settings.getConfig(â˜ƒ);
         return â˜ƒ == null ? null : â˜ƒ.getNearestGeneratedFeature(â˜ƒ, â˜ƒ.structureFeatureManager(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getSeed(), â˜ƒ);
      }
   }

   public void applyBiomeDecoration(WorldGenRegion var1, StructureFeatureManager var2) {
      ChunkPos â˜ƒ = â˜ƒ.getCenter();
      int â˜ƒx = â˜ƒ.getMinBlockX();
      int â˜ƒxx = â˜ƒ.getMinBlockZ();
      BlockPos â˜ƒxxx = new BlockPos(â˜ƒx, â˜ƒ.getMinBuildHeight(), â˜ƒxx);
      Biome â˜ƒxxxx = this.biomeSource.getPrimaryBiome(â˜ƒ);
      WorldgenRandom â˜ƒxxxxx = new WorldgenRandom();
      long â˜ƒxxxxxx = â˜ƒxxxxx.setDecorationSeed(â˜ƒ.getSeed(), â˜ƒx, â˜ƒxx);

      try {
         â˜ƒxxxx.generate(â˜ƒ, this, â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒxxx);
      } catch (Exception var13) {
         CrashReport â˜ƒxxxxxxx = CrashReport.forThrowable(var13, "Biome decoration");
         â˜ƒxxxxxxx.addCategory("Generation").setDetail("CenterX", â˜ƒ.x).setDetail("CenterZ", â˜ƒ.z).setDetail("Seed", â˜ƒxxxxxx).setDetail("Biome", â˜ƒxxxx);
         throw new ReportedException(â˜ƒxxxxxxx);
      }
   }

   public abstract void buildSurfaceAndBedrock(WorldGenRegion var1, ChunkAccess var2);

   public void spawnOriginalMobs(WorldGenRegion var1) {
   }

   public StructureSettings getSettings() {
      return this.settings;
   }

   public int getSpawnHeight(LevelHeightAccessor var1) {
      return 64;
   }

   public BiomeSource getBiomeSource() {
      return this.runtimeBiomeSource;
   }

   public int getGenDepth() {
      return 256;
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt(Biome var1, StructureFeatureManager var2, MobCategory var3, BlockPos var4) {
      return â˜ƒ.getMobSettings().getMobs(â˜ƒ);
   }

   public void createStructures(RegistryAccess var1, StructureFeatureManager var2, ChunkAccess var3, StructureManager var4, long var5) {
      Biome â˜ƒ = this.biomeSource.getPrimaryBiome(â˜ƒ.getPos());
      this.createStructure(StructureFeatures.STRONGHOLD, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);

      for(Supplier<ConfiguredStructureFeature<?, ?>> â˜ƒx : â˜ƒ.getGenerationSettings().structures()) {
         this.createStructure((ConfiguredStructureFeature<?, ?>)â˜ƒx.get(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void createStructure(
      ConfiguredStructureFeature<?, ?> var1, RegistryAccess var2, StructureFeatureManager var3, ChunkAccess var4, StructureManager var5, long var6, Biome var8
   ) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      SectionPos â˜ƒx = SectionPos.bottomOf(â˜ƒ);
      StructureStart<?> â˜ƒxx = â˜ƒ.getStartForFeature(â˜ƒx, â˜ƒ.feature, â˜ƒ);
      int â˜ƒxxx = â˜ƒxx != null ? â˜ƒxx.getReferences() : 0;
      StructureFeatureConfiguration â˜ƒxxxx = this.settings.getConfig(â˜ƒ.feature);
      if (â˜ƒxxxx != null) {
         StructureStart<?> â˜ƒxxxxx = â˜ƒ.generate(â˜ƒ, this, this.biomeSource, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒ);
         â˜ƒ.setStartForFeature(â˜ƒx, â˜ƒ.feature, â˜ƒxxxxx, â˜ƒ);
      }
   }

   public void createReferences(WorldGenLevel var1, StructureFeatureManager var2, ChunkAccess var3) {
      int â˜ƒ = 8;
      ChunkPos â˜ƒx = â˜ƒ.getPos();
      int â˜ƒxx = â˜ƒx.x;
      int â˜ƒxxx = â˜ƒx.z;
      int â˜ƒxxxx = â˜ƒx.getMinBlockX();
      int â˜ƒxxxxx = â˜ƒx.getMinBlockZ();
      SectionPos â˜ƒxxxxxx = SectionPos.bottomOf(â˜ƒ);

      for(int â˜ƒxxxxxxx = â˜ƒxx - 8; â˜ƒxxxxxxx <= â˜ƒxx + 8; ++â˜ƒxxxxxxx) {
         for(int â˜ƒxxxxxxxx = â˜ƒxxx - 8; â˜ƒxxxxxxxx <= â˜ƒxxx + 8; ++â˜ƒxxxxxxxx) {
            long â˜ƒxxxxxxxxx = ChunkPos.asLong(â˜ƒxxxxxxx, â˜ƒxxxxxxxx);

            for(StructureStart<?> â˜ƒxxxxxxxxxx : â˜ƒ.getChunk(â˜ƒxxxxxxx, â˜ƒxxxxxxxx).getAllStarts().values()) {
               try {
                  if (â˜ƒxxxxxxxxxx.isValid() && â˜ƒxxxxxxxxxx.getBoundingBox().intersects(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxx + 15, â˜ƒxxxxx + 15)) {
                     â˜ƒ.addReferenceForFeature(â˜ƒxxxxxx, â˜ƒxxxxxxxxxx.getFeature(), â˜ƒxxxxxxxxx, â˜ƒ);
                     DebugPackets.sendStructurePacket(â˜ƒ, â˜ƒxxxxxxxxxx);
                  }
               } catch (Exception var20) {
                  CrashReport â˜ƒxxxxxxxxxxx = CrashReport.forThrowable(var20, "Generating structure reference");
                  CrashReportCategory â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.addCategory("Structure");
                  â˜ƒxxxxxxxxxxxx.setDetail("Id", (CrashReportDetail<String>)(() -> Registry.STRUCTURE_FEATURE.getKey(â˜ƒ.getFeature()).toString()));
                  â˜ƒxxxxxxxxxxxx.setDetail("Name", (CrashReportDetail<String>)(() -> â˜ƒ.getFeature().getFeatureName()));
                  â˜ƒxxxxxxxxxxxx.setDetail("Class", (CrashReportDetail<String>)(() -> â˜ƒ.getFeature().getClass().getCanonicalName()));
                  throw new ReportedException(â˜ƒxxxxxxxxxxx);
               }
            }
         }
      }
   }

   public abstract CompletableFuture<ChunkAccess> fillFromNoise(Executor var1, StructureFeatureManager var2, ChunkAccess var3);

   public int getSeaLevel() {
      return 63;
   }

   public int getMinY() {
      return 0;
   }

   public abstract int getBaseHeight(int var1, int var2, Heightmap.Types var3, LevelHeightAccessor var4);

   public abstract NoiseColumn getBaseColumn(int var1, int var2, LevelHeightAccessor var3);

   public int getFirstFreeHeight(int var1, int var2, Heightmap.Types var3, LevelHeightAccessor var4) {
      return this.getBaseHeight(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public int getFirstOccupiedHeight(int var1, int var2, Heightmap.Types var3, LevelHeightAccessor var4) {
      return this.getBaseHeight(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) - 1;
   }

   public boolean hasStronghold(ChunkPos var1) {
      this.generateStrongholds();
      return this.strongholdPositions.contains(â˜ƒ);
   }

   public BaseStoneSource getBaseStoneSource() {
      return this.defaultBaseStoneSource;
   }

   static {
      Registry.register(Registry.CHUNK_GENERATOR, "noise", NoiseBasedChunkGenerator.CODEC);
      Registry.register(Registry.CHUNK_GENERATOR, "flat", FlatLevelSource.CODEC);
      Registry.register(Registry.CHUNK_GENERATOR, "debug", DebugLevelSource.CODEC);
   }
}
