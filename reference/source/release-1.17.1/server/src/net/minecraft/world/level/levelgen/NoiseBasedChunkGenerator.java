package net.minecraft.world.level.levelgen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import net.minecraft.world.level.levelgen.synth.SurfaceNoise;

public final class NoiseBasedChunkGenerator extends ChunkGenerator {
   public static final Codec<NoiseBasedChunkGenerator> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               BiomeSource.CODEC.fieldOf("biome_source").forGetter(var0x -> var0x.biomeSource),
               Codec.LONG.fieldOf("seed").stable().forGetter(var0x -> var0x.seed),
               NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(var0x -> var0x.settings)
            )
            .apply(var0, var0.stable(NoiseBasedChunkGenerator::new))
   );
   private static final BlockState AIR = Blocks.AIR.defaultBlockState();
   private static final BlockState[] EMPTY_COLUMN = new BlockState[0];
   private final int cellHeight;
   private final int cellWidth;
   final int cellCountX;
   final int cellCountY;
   final int cellCountZ;
   private final SurfaceNoise surfaceNoise;
   private final NormalNoise barrierNoise;
   private final NormalNoise waterLevelNoise;
   private final NormalNoise lavaNoise;
   protected final BlockState defaultBlock;
   protected final BlockState defaultFluid;
   private final long seed;
   protected final Supplier<NoiseGeneratorSettings> settings;
   private final int height;
   private final NoiseSampler sampler;
   private final BaseStoneSource baseStoneSource;
   final OreVeinifier oreVeinifier;
   final NoodleCavifier noodleCavifier;

   public NoiseBasedChunkGenerator(BiomeSource var1, long var2, Supplier<NoiseGeneratorSettings> var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private NoiseBasedChunkGenerator(BiomeSource var1, BiomeSource var2, long var3, Supplier<NoiseGeneratorSettings> var5) {
      super(â˜ƒ, â˜ƒ, ((NoiseGeneratorSettings)â˜ƒ.get()).structureSettings(), â˜ƒ);
      this.seed = â˜ƒ;
      NoiseGeneratorSettings â˜ƒx = (NoiseGeneratorSettings)â˜ƒ.get();
      this.settings = â˜ƒ;
      NoiseSettings â˜ƒxx = â˜ƒx.noiseSettings();
      this.height = â˜ƒxx.height();
      this.cellHeight = QuartPos.toBlock(â˜ƒxx.noiseSizeVertical());
      this.cellWidth = QuartPos.toBlock(â˜ƒxx.noiseSizeHorizontal());
      this.defaultBlock = â˜ƒx.getDefaultBlock();
      this.defaultFluid = â˜ƒx.getDefaultFluid();
      this.cellCountX = 16 / this.cellWidth;
      this.cellCountY = â˜ƒxx.height() / this.cellHeight;
      this.cellCountZ = 16 / this.cellWidth;
      WorldgenRandom â˜ƒxxx = new WorldgenRandom(â˜ƒ);
      BlendedNoise â˜ƒxxxx = new BlendedNoise(â˜ƒxxx);
      this.surfaceNoise = (SurfaceNoise)(â˜ƒxx.useSimplexSurfaceNoise()
         ? new PerlinSimplexNoise(â˜ƒxxx, IntStream.rangeClosed(-3, 0))
         : new PerlinNoise(â˜ƒxxx, IntStream.rangeClosed(-3, 0)));
      â˜ƒxxx.consumeCount(2620);
      PerlinNoise â˜ƒxxxxx = new PerlinNoise(â˜ƒxxx, IntStream.rangeClosed(-15, 0));
      SimplexNoise â˜ƒ;
      if (â˜ƒxx.islandNoiseOverride()) {
         WorldgenRandom â˜ƒxxxxxx = new WorldgenRandom(â˜ƒ);
         â˜ƒxxxxxx.consumeCount(17292);
         â˜ƒ = new SimplexNoise(â˜ƒxxxxxx);
      } else {
         â˜ƒ = null;
      }

      this.barrierNoise = NormalNoise.create(new SimpleRandomSource(â˜ƒxxx.nextLong()), -3, 1.0);
      this.waterLevelNoise = NormalNoise.create(new SimpleRandomSource(â˜ƒxxx.nextLong()), -3, 1.0, 0.0, 2.0);
      this.lavaNoise = NormalNoise.create(new SimpleRandomSource(â˜ƒxxx.nextLong()), -1, 1.0, 0.0);
      NoiseModifier â˜ƒ;
      if (â˜ƒx.isNoiseCavesEnabled()) {
         â˜ƒ = new Cavifier(â˜ƒxxx, â˜ƒxx.minY() / this.cellHeight);
      } else {
         â˜ƒ = NoiseModifier.PASSTHROUGH;
      }

      this.sampler = new NoiseSampler(â˜ƒ, this.cellWidth, this.cellHeight, this.cellCountY, â˜ƒxx, â˜ƒxxxx, â˜ƒ, â˜ƒxxxxx, â˜ƒ);
      this.baseStoneSource = new DepthBasedReplacingBaseStoneSource(â˜ƒ, this.defaultBlock, Blocks.DEEPSLATE.defaultBlockState(), â˜ƒx);
      this.oreVeinifier = new OreVeinifier(â˜ƒ, this.defaultBlock, this.cellWidth, this.cellHeight, â˜ƒx.noiseSettings().minY());
      this.noodleCavifier = new NoodleCavifier(â˜ƒ);
   }

   private boolean isAquifersEnabled() {
      return ((NoiseGeneratorSettings)this.settings.get()).isAquifersEnabled();
   }

   @Override
   protected Codec<? extends ChunkGenerator> codec() {
      return CODEC;
   }

   @Override
   public ChunkGenerator withSeed(long var1) {
      return new NoiseBasedChunkGenerator(this.biomeSource.withSeed(â˜ƒ), â˜ƒ, this.settings);
   }

   public boolean stable(long var1, ResourceKey<NoiseGeneratorSettings> var3) {
      return this.seed == â˜ƒ && ((NoiseGeneratorSettings)this.settings.get()).stable(â˜ƒ);
   }

   private double[] makeAndFillNoiseColumn(int var1, int var2, int var3, int var4) {
      double[] â˜ƒ = new double[â˜ƒ + 1];
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   private void fillNoiseColumn(double[] var1, int var2, int var3, int var4, int var5) {
      NoiseSettings â˜ƒ = ((NoiseGeneratorSettings)this.settings.get()).noiseSettings();
      this.sampler.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.getSeaLevel(), â˜ƒ, â˜ƒ);
   }

   @Override
   public int getBaseHeight(int var1, int var2, Heightmap.Types var3, LevelHeightAccessor var4) {
      int â˜ƒ = Math.max(((NoiseGeneratorSettings)this.settings.get()).noiseSettings().minY(), â˜ƒ.getMinBuildHeight());
      int â˜ƒx = Math.min(
         ((NoiseGeneratorSettings)this.settings.get()).noiseSettings().minY() + ((NoiseGeneratorSettings)this.settings.get()).noiseSettings().height(),
         â˜ƒ.getMaxBuildHeight()
      );
      int â˜ƒxx = Mth.intFloorDiv(â˜ƒ, this.cellHeight);
      int â˜ƒxxx = Mth.intFloorDiv(â˜ƒx - â˜ƒ, this.cellHeight);
      return â˜ƒxxx <= 0 ? â˜ƒ.getMinBuildHeight() : this.iterateNoiseColumn(â˜ƒ, â˜ƒ, null, â˜ƒ.isOpaque(), â˜ƒxx, â˜ƒxxx).orElse(â˜ƒ.getMinBuildHeight());
   }

   @Override
   public NoiseColumn getBaseColumn(int var1, int var2, LevelHeightAccessor var3) {
      int â˜ƒ = Math.max(((NoiseGeneratorSettings)this.settings.get()).noiseSettings().minY(), â˜ƒ.getMinBuildHeight());
      int â˜ƒx = Math.min(
         ((NoiseGeneratorSettings)this.settings.get()).noiseSettings().minY() + ((NoiseGeneratorSettings)this.settings.get()).noiseSettings().height(),
         â˜ƒ.getMaxBuildHeight()
      );
      int â˜ƒxx = Mth.intFloorDiv(â˜ƒ, this.cellHeight);
      int â˜ƒxxx = Mth.intFloorDiv(â˜ƒx - â˜ƒ, this.cellHeight);
      if (â˜ƒxxx <= 0) {
         return new NoiseColumn(â˜ƒ, EMPTY_COLUMN);
      } else {
         BlockState[] â˜ƒ = new BlockState[â˜ƒxxx * this.cellHeight];
         this.iterateNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, null, â˜ƒxx, â˜ƒxxx);
         return new NoiseColumn(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public BaseStoneSource getBaseStoneSource() {
      return this.baseStoneSource;
   }

   private OptionalInt iterateNoiseColumn(int var1, int var2, @Nullable BlockState[] var3, @Nullable Predicate<BlockState> var4, int var5, int var6) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ);
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ);
      int â˜ƒxx = Math.floorDiv(â˜ƒ, this.cellWidth);
      int â˜ƒxxx = Math.floorDiv(â˜ƒ, this.cellWidth);
      int â˜ƒxxxx = Math.floorMod(â˜ƒ, this.cellWidth);
      int â˜ƒxxxxx = Math.floorMod(â˜ƒ, this.cellWidth);
      double â˜ƒxxxxxx = (double)â˜ƒxxxx / (double)this.cellWidth;
      double â˜ƒxxxxxxx = (double)â˜ƒxxxxx / (double)this.cellWidth;
      double[][] â˜ƒxxxxxxxx = new double[][]{
         this.makeAndFillNoiseColumn(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒ),
         this.makeAndFillNoiseColumn(â˜ƒxx, â˜ƒxxx + 1, â˜ƒ, â˜ƒ),
         this.makeAndFillNoiseColumn(â˜ƒxx + 1, â˜ƒxxx, â˜ƒ, â˜ƒ),
         this.makeAndFillNoiseColumn(â˜ƒxx + 1, â˜ƒxxx + 1, â˜ƒ, â˜ƒ)
      };
      Aquifer â˜ƒxxxxxxxxx = this.getAquifer(â˜ƒ, â˜ƒ, new ChunkPos(â˜ƒ, â˜ƒx));

      for(int â˜ƒxxxxxxxxxx = â˜ƒ - 1; â˜ƒxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxx) {
         double â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx[0][â˜ƒxxxxxxxxxx];
         double â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxx[1][â˜ƒxxxxxxxxxx];
         double â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxx[2][â˜ƒxxxxxxxxxx];
         double â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxx[3][â˜ƒxxxxxxxxxx];
         double â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx[0][â˜ƒxxxxxxxxxx + 1];
         double â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx[1][â˜ƒxxxxxxxxxx + 1];
         double â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx[2][â˜ƒxxxxxxxxxx + 1];
         double â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx[3][â˜ƒxxxxxxxxxx + 1];

         for(int â˜ƒxxxxxxxxxxxxxxxxxxx = this.cellHeight - 1; â˜ƒxxxxxxxxxxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxxxxxxxxxxx) {
            double â˜ƒxxxxxxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxxxxxxxxxxxx / (double)this.cellHeight;
            double â˜ƒxxxxxxxxxxxxxxxxxxxxx = Mth.lerp3(
               â˜ƒxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxx,
               â˜ƒxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxx
            );
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx * this.cellHeight + â˜ƒxxxxxxxxxxxxxxxxxxx;
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxx + â˜ƒ * this.cellHeight;
            BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = this.updateNoiseAndGenerateBaseState(
               Beardifier.NO_BEARDS,
               â˜ƒxxxxxxxxx,
               this.baseStoneSource,
               NoiseModifier.PASSTHROUGH,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxx
            );
            if (â˜ƒ != null) {
               â˜ƒ[â˜ƒxxxxxxxxxxxxxxxxxxxxxx] = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx;
            }

            if (â˜ƒ != null && â˜ƒ.test(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx)) {
               return OptionalInt.of(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 1);
            }
         }
      }

      return OptionalInt.empty();
   }

   private Aquifer getAquifer(int var1, int var2, ChunkPos var3) {
      return !this.isAquifersEnabled()
         ? Aquifer.createDisabled(this.getSeaLevel(), this.defaultFluid)
         : Aquifer.create(
            â˜ƒ,
            this.barrierNoise,
            this.waterLevelNoise,
            this.lavaNoise,
            (NoiseGeneratorSettings)this.settings.get(),
            this.sampler,
            â˜ƒ * this.cellHeight,
            â˜ƒ * this.cellHeight
         );
   }

   protected BlockState updateNoiseAndGenerateBaseState(
      Beardifier var1, Aquifer var2, BaseStoneSource var3, NoiseModifier var4, int var5, int var6, int var7, double var8
   ) {
      double â˜ƒ = Mth.clamp(â˜ƒ / 200.0, -1.0, 1.0);
      â˜ƒ = â˜ƒ / 2.0 - â˜ƒ * â˜ƒ * â˜ƒ / 24.0;
      â˜ƒ = â˜ƒ.modifyNoise(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ += â˜ƒ.beardifyOrBury(â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ.computeState(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void buildSurfaceAndBedrock(WorldGenRegion var1, ChunkAccess var2) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      int â˜ƒx = â˜ƒ.x;
      int â˜ƒxx = â˜ƒ.z;
      WorldgenRandom â˜ƒxxx = new WorldgenRandom();
      â˜ƒxxx.setBaseChunkSeed(â˜ƒx, â˜ƒxx);
      ChunkPos â˜ƒxxxx = â˜ƒ.getPos();
      int â˜ƒxxxxx = â˜ƒxxxx.getMinBlockX();
      int â˜ƒxxxxxx = â˜ƒxxxx.getMinBlockZ();
      double â˜ƒxxxxxxx = 0.0625;
      BlockPos.MutableBlockPos â˜ƒxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 16; ++â˜ƒxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < 16; ++â˜ƒxxxxxxxxxx) {
            int â˜ƒxxxxxxxxxxx = â˜ƒxxxxx + â˜ƒxxxxxxxxx;
            int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxx + â˜ƒxxxxxxxxxx;
            int â˜ƒxxxxxxxxxxxxx = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE_WG, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx) + 1;
            double â˜ƒxxxxxxxxxxxxxx = this.surfaceNoise
                  .getSurfaceNoiseValue((double)â˜ƒxxxxxxxxxxx * 0.0625, (double)â˜ƒxxxxxxxxxxxx * 0.0625, 0.0625, (double)â˜ƒxxxxxxxxx * 0.0625)
               * 15.0;
            int â˜ƒxxxxxxxxxxxxxxx = ((NoiseGeneratorSettings)this.settings.get()).getMinSurfaceLevel();
            â˜ƒ.getBiome(â˜ƒxxxxxxxx.set(â˜ƒxxxxx + â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxx + â˜ƒxxxxxxxxxx))
               .buildSurfaceAt(
                  â˜ƒxxx,
                  â˜ƒ,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  this.defaultBlock,
                  this.defaultFluid,
                  this.getSeaLevel(),
                  â˜ƒxxxxxxxxxxxxxxx,
                  â˜ƒ.getSeed()
               );
         }
      }

      this.setBedrock(â˜ƒ, â˜ƒxxx);
   }

   private void setBedrock(ChunkAccess var1, Random var2) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      int â˜ƒx = â˜ƒ.getPos().getMinBlockX();
      int â˜ƒxx = â˜ƒ.getPos().getMinBlockZ();
      NoiseGeneratorSettings â˜ƒxxx = (NoiseGeneratorSettings)this.settings.get();
      int â˜ƒxxxx = â˜ƒxxx.noiseSettings().minY();
      int â˜ƒxxxxx = â˜ƒxxxx + â˜ƒxxx.getBedrockFloorPosition();
      int â˜ƒxxxxxx = this.height - 1 + â˜ƒxxxx - â˜ƒxxx.getBedrockRoofPosition();
      int â˜ƒxxxxxxx = 5;
      int â˜ƒxxxxxxxx = â˜ƒ.getMinBuildHeight();
      int â˜ƒxxxxxxxxx = â˜ƒ.getMaxBuildHeight();
      boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxx + 5 - 1 >= â˜ƒxxxxxxxx && â˜ƒxxxxxx < â˜ƒxxxxxxxxx;
      boolean â˜ƒxxxxxxxxxxx = â˜ƒxxxxx + 5 - 1 >= â˜ƒxxxxxxxx && â˜ƒxxxxx < â˜ƒxxxxxxxxx;
      if (â˜ƒxxxxxxxxxx || â˜ƒxxxxxxxxxxx) {
         for(BlockPos â˜ƒxxxxxxxxxxxx : BlockPos.betweenClosed(â˜ƒx, 0, â˜ƒxx, â˜ƒx + 15, 0, â˜ƒxx + 15)) {
            if (â˜ƒxxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxx < 5; ++â˜ƒxxxxxxxxxxxxx) {
                  if (â˜ƒxxxxxxxxxxxxx <= â˜ƒ.nextInt(5)) {
                     â˜ƒ.setBlockState(
                        â˜ƒ.set(â˜ƒxxxxxxxxxxxx.getX(), â˜ƒxxxxxx - â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx.getZ()), Blocks.BEDROCK.defaultBlockState(), false
                     );
                  }
               }
            }

            if (â˜ƒxxxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxxxxx = 4; â˜ƒxxxxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxxxxx) {
                  if (â˜ƒxxxxxxxxxxxxx <= â˜ƒ.nextInt(5)) {
                     â˜ƒ.setBlockState(
                        â˜ƒ.set(â˜ƒxxxxxxxxxxxx.getX(), â˜ƒxxxxx + â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx.getZ()), Blocks.BEDROCK.defaultBlockState(), false
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public CompletableFuture<ChunkAccess> fillFromNoise(Executor var1, StructureFeatureManager var2, ChunkAccess var3) {
      NoiseSettings â˜ƒ = ((NoiseGeneratorSettings)this.settings.get()).noiseSettings();
      int â˜ƒx = Math.max(â˜ƒ.minY(), â˜ƒ.getMinBuildHeight());
      int â˜ƒxx = Math.min(â˜ƒ.minY() + â˜ƒ.height(), â˜ƒ.getMaxBuildHeight());
      int â˜ƒxxx = Mth.intFloorDiv(â˜ƒx, this.cellHeight);
      int â˜ƒxxxx = Mth.intFloorDiv(â˜ƒxx - â˜ƒx, this.cellHeight);
      if (â˜ƒxxxx <= 0) {
         return CompletableFuture.completedFuture(â˜ƒ);
      } else {
         int â˜ƒ = â˜ƒ.getSectionIndex(â˜ƒxxxx * this.cellHeight - 1 + â˜ƒx);
         int â˜ƒx = â˜ƒ.getSectionIndex(â˜ƒx);
         return CompletableFuture.supplyAsync(() -> {
            Set<LevelChunkSection> â˜ƒ = Sets.<LevelChunkSection>newHashSet();

            ChunkAccess var16;
            try {
               for(int â˜ƒx = â˜ƒ; â˜ƒx >= â˜ƒ; --â˜ƒx) {
                  LevelChunkSection â˜ƒxx = â˜ƒ.getOrCreateSection(â˜ƒx);
                  â˜ƒxx.acquire();
                  â˜ƒ.add(â˜ƒxx);
               }

               var16 = this.doFill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            } finally {
               for(LevelChunkSection â˜ƒx : â˜ƒ) {
                  â˜ƒx.release();
               }
            }

            return var16;
         }, Util.backgroundExecutor());
      }
   }

   private ChunkAccess doFill(StructureFeatureManager var1, ChunkAccess var2, int var3, int var4) {
      Heightmap â˜ƒ = â˜ƒ.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
      Heightmap â˜ƒx = â˜ƒ.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
      ChunkPos â˜ƒxx = â˜ƒ.getPos();
      int â˜ƒxxx = â˜ƒxx.getMinBlockX();
      int â˜ƒxxxx = â˜ƒxx.getMinBlockZ();
      Beardifier â˜ƒxxxxx = new Beardifier(â˜ƒ, â˜ƒ);
      Aquifer â˜ƒxxxxxx = this.getAquifer(â˜ƒ, â˜ƒ, â˜ƒxx);
      NoiseInterpolator â˜ƒxxxxxxx = new NoiseInterpolator(this.cellCountX, â˜ƒ, this.cellCountZ, â˜ƒxx, â˜ƒ, this::fillNoiseColumn);
      List<NoiseInterpolator> â˜ƒxxxxxxxx = Lists.<NoiseInterpolator>newArrayList(â˜ƒxxxxxxx);
      Consumer<NoiseInterpolator> â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx::add;
      DoubleFunction<BaseStoneSource> â˜ƒxxxxxxxxxx = this.createBaseStoneSource(â˜ƒ, â˜ƒxx, â˜ƒxxxxxxxxx);
      DoubleFunction<NoiseModifier> â˜ƒxxxxxxxxxxx = this.createCaveNoiseModifier(â˜ƒ, â˜ƒxx, â˜ƒxxxxxxxxx);
      â˜ƒxxxxxxxx.forEach(NoiseInterpolator::initializeForFirstCellX);
      BlockPos.MutableBlockPos â˜ƒxxxxxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxx < this.cellCountX; ++â˜ƒxxxxxxxxxxxxx) {
         int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx;
         â˜ƒxxxxxxxx.forEach(var1x -> var1x.advanceCellX(â˜ƒ));

         for(int â˜ƒxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxx < this.cellCountZ; ++â˜ƒxxxxxxxxxxxxxxx) {
            LevelChunkSection â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ.getOrCreateSection(â˜ƒ.getSectionsCount() - 1);

            for(int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ - 1; â˜ƒxxxxxxxxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxxxxxxxxx) {
               int â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx;
               int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxx.forEach(var2x -> var2x.selectCellYZ(â˜ƒ, â˜ƒ));

               for(int â˜ƒxxxxxxxxxxxxxxxxxxxx = this.cellHeight - 1; â˜ƒxxxxxxxxxxxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxxxxxxxxxxxx) {
                  int â˜ƒxxxxxxxxxxxxxxxxxxxxx = (â˜ƒ + â˜ƒxxxxxxxxxxxxxxxxx) * this.cellHeight + â˜ƒxxxxxxxxxxxxxxxxxxxx;
                  int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx & 15;
                  int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getSectionIndex(â˜ƒxxxxxxxxxxxxxxxxxxxxx);
                  if (â˜ƒ.getSectionIndex(â˜ƒxxxxxxxxxxxxxxxx.bottomBlockY()) != â˜ƒxxxxxxxxxxxxxxxxxxxxxxx) {
                     â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ.getOrCreateSection(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx);
                  }

                  double â˜ƒxxxxxxxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxxxxxxxxxxxxx / (double)this.cellHeight;
                  â˜ƒxxxxxxxx.forEach(var2x -> var2x.updateForY(â˜ƒ));

                  for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxx < this.cellWidth; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxx) {
                     int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxx + â˜ƒxxxxxxxxxxxxx * this.cellWidth + â˜ƒxxxxxxxxxxxxxxxxxxxxxx;
                     int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx & 15;
                     double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxxxxxxxxxxxxxxx / (double)this.cellWidth;
                     â˜ƒxxxxxxxx.forEach(var2x -> var2x.updateForX(â˜ƒ));

                     for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx < this.cellWidth; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                        int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxx + â˜ƒxxxxxxxxxxxxxxx * this.cellWidth + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx;
                        int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx & 15;
                        double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx / (double)this.cellWidth;
                        double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx.calculateValue(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                        BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.updateNoiseAndGenerateBaseState(
                           â˜ƒxxxxx,
                           â˜ƒxxxxxx,
                           (BaseStoneSource)â˜ƒxxxxxxxxxx.apply(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx),
                           (NoiseModifier)â˜ƒxxxxxxxxxxx.apply(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx),
                           â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
                           â˜ƒxxxxxxxxxxxxxxxxxxxxx,
                           â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != AIR) {
                           if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getLightEmission() != 0 && â˜ƒ instanceof ProtoChunk) {
                              â˜ƒxxxxxxxxxxxx.set(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                              ((ProtoChunk)â˜ƒ).addLight(â˜ƒxxxxxxxxxxxx);
                           }

                           â˜ƒxxxxxxxxxxxxxxxx.setBlockState(
                              â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              false
                           );
                           â˜ƒ.update(
                              â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           );
                           â˜ƒx.update(
                              â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           );
                           if (â˜ƒxxxxxx.shouldScheduleFluidUpdate() && !â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFluidState().isEmpty()) {
                              â˜ƒxxxxxxxxxxxx.set(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                              â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFluidState().getType(), 0);
                           }
                        }
                     }
                  }
               }
            }
         }

         â˜ƒxxxxxxxx.forEach(NoiseInterpolator::swapSlices);
      }

      return â˜ƒ;
   }

   private DoubleFunction<NoiseModifier> createCaveNoiseModifier(int var1, ChunkPos var2, Consumer<NoiseInterpolator> var3) {
      if (!((NoiseGeneratorSettings)this.settings.get()).isNoodleCavesEnabled()) {
         return var0 -> NoiseModifier.PASSTHROUGH;
      } else {
         NoiseBasedChunkGenerator.NoodleCaveNoiseModifier â˜ƒ = new NoiseBasedChunkGenerator.NoodleCaveNoiseModifier(â˜ƒ, â˜ƒ);
         â˜ƒ.listInterpolators(â˜ƒ);
         return â˜ƒ::prepare;
      }
   }

   private DoubleFunction<BaseStoneSource> createBaseStoneSource(int var1, ChunkPos var2, Consumer<NoiseInterpolator> var3) {
      if (!((NoiseGeneratorSettings)this.settings.get()).isOreVeinsEnabled()) {
         return var1x -> this.baseStoneSource;
      } else {
         NoiseBasedChunkGenerator.OreVeinNoiseSource â˜ƒ = new NoiseBasedChunkGenerator.OreVeinNoiseSource(â˜ƒ, â˜ƒ, this.seed + 1L);
         â˜ƒ.listInterpolators(â˜ƒ);
         BaseStoneSource â˜ƒx = (var2x, var3x, var4x) -> {
            BlockState â˜ƒ = â˜ƒ.getBaseBlock(var2x, var3x, var4x);
            return â˜ƒ != this.defaultBlock ? â˜ƒ : this.baseStoneSource.getBaseBlock(var2x, var3x, var4x);
         };
         return var2x -> {
            â˜ƒ.prepare(var2x);
            return â˜ƒ;
         };
      }
   }

   @Override
   protected Aquifer createAquifer(ChunkAccess var1) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      int â˜ƒx = Math.max(((NoiseGeneratorSettings)this.settings.get()).noiseSettings().minY(), â˜ƒ.getMinBuildHeight());
      int â˜ƒxx = Mth.intFloorDiv(â˜ƒx, this.cellHeight);
      return this.getAquifer(â˜ƒxx, this.cellCountY, â˜ƒ);
   }

   @Override
   public int getGenDepth() {
      return this.height;
   }

   @Override
   public int getSeaLevel() {
      return ((NoiseGeneratorSettings)this.settings.get()).seaLevel();
   }

   @Override
   public int getMinY() {
      return ((NoiseGeneratorSettings)this.settings.get()).noiseSettings().minY();
   }

   @Override
   public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt(Biome var1, StructureFeatureManager var2, MobCategory var3, BlockPos var4) {
      if (â˜ƒ.getStructureAt(â˜ƒ, true, StructureFeature.SWAMP_HUT).isValid()) {
         if (â˜ƒ == MobCategory.MONSTER) {
            return StructureFeature.SWAMP_HUT.getSpecialEnemies();
         }

         if (â˜ƒ == MobCategory.CREATURE) {
            return StructureFeature.SWAMP_HUT.getSpecialAnimals();
         }
      }

      if (â˜ƒ == MobCategory.MONSTER) {
         if (â˜ƒ.getStructureAt(â˜ƒ, false, StructureFeature.PILLAGER_OUTPOST).isValid()) {
            return StructureFeature.PILLAGER_OUTPOST.getSpecialEnemies();
         }

         if (â˜ƒ.getStructureAt(â˜ƒ, false, StructureFeature.OCEAN_MONUMENT).isValid()) {
            return StructureFeature.OCEAN_MONUMENT.getSpecialEnemies();
         }

         if (â˜ƒ.getStructureAt(â˜ƒ, true, StructureFeature.NETHER_BRIDGE).isValid()) {
            return StructureFeature.NETHER_BRIDGE.getSpecialEnemies();
         }
      }

      return â˜ƒ == MobCategory.UNDERGROUND_WATER_CREATURE && â˜ƒ.getStructureAt(â˜ƒ, false, StructureFeature.OCEAN_MONUMENT).isValid()
         ? StructureFeature.OCEAN_MONUMENT.getSpecialUndergroundWaterAnimals()
         : super.getMobsAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void spawnOriginalMobs(WorldGenRegion var1) {
      if (!((NoiseGeneratorSettings)this.settings.get()).disableMobGeneration()) {
         ChunkPos â˜ƒ = â˜ƒ.getCenter();
         Biome â˜ƒx = â˜ƒ.getBiome(â˜ƒ.getWorldPosition());
         WorldgenRandom â˜ƒxx = new WorldgenRandom();
         â˜ƒxx.setDecorationSeed(â˜ƒ.getSeed(), â˜ƒ.getMinBlockX(), â˜ƒ.getMinBlockZ());
         NaturalSpawner.spawnMobsForChunkGeneration(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx);
      }
   }

   class NoodleCaveNoiseModifier implements NoiseModifier {
      private final NoiseInterpolator toggle;
      private final NoiseInterpolator thickness;
      private final NoiseInterpolator ridgeA;
      private final NoiseInterpolator ridgeB;
      private double factorZ;

      public NoodleCaveNoiseModifier(ChunkPos var2, int var3) {
         this.toggle = new NoiseInterpolator(
            NoiseBasedChunkGenerator.this.cellCountX,
            NoiseBasedChunkGenerator.this.cellCountY,
            NoiseBasedChunkGenerator.this.cellCountZ,
            â˜ƒ,
            â˜ƒ,
            NoiseBasedChunkGenerator.this.noodleCavifier::fillToggleNoiseColumn
         );
         this.thickness = new NoiseInterpolator(
            NoiseBasedChunkGenerator.this.cellCountX,
            NoiseBasedChunkGenerator.this.cellCountY,
            NoiseBasedChunkGenerator.this.cellCountZ,
            â˜ƒ,
            â˜ƒ,
            NoiseBasedChunkGenerator.this.noodleCavifier::fillThicknessNoiseColumn
         );
         this.ridgeA = new NoiseInterpolator(
            NoiseBasedChunkGenerator.this.cellCountX,
            NoiseBasedChunkGenerator.this.cellCountY,
            NoiseBasedChunkGenerator.this.cellCountZ,
            â˜ƒ,
            â˜ƒ,
            NoiseBasedChunkGenerator.this.noodleCavifier::fillRidgeANoiseColumn
         );
         this.ridgeB = new NoiseInterpolator(
            NoiseBasedChunkGenerator.this.cellCountX,
            NoiseBasedChunkGenerator.this.cellCountY,
            NoiseBasedChunkGenerator.this.cellCountZ,
            â˜ƒ,
            â˜ƒ,
            NoiseBasedChunkGenerator.this.noodleCavifier::fillRidgeBNoiseColumn
         );
      }

      public NoiseModifier prepare(double var1) {
         this.factorZ = â˜ƒ;
         return this;
      }

      @Override
      public double modifyNoise(double var1, int var3, int var4, int var5) {
         double â˜ƒ = this.toggle.calculateValue(this.factorZ);
         double â˜ƒx = this.thickness.calculateValue(this.factorZ);
         double â˜ƒxx = this.ridgeA.calculateValue(this.factorZ);
         double â˜ƒxxx = this.ridgeB.calculateValue(this.factorZ);
         return NoiseBasedChunkGenerator.this.noodleCavifier
            .noodleCavify(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, NoiseBasedChunkGenerator.this.getMinY());
      }

      public void listInterpolators(Consumer<NoiseInterpolator> var1) {
         â˜ƒ.accept(this.toggle);
         â˜ƒ.accept(this.thickness);
         â˜ƒ.accept(this.ridgeA);
         â˜ƒ.accept(this.ridgeB);
      }
   }

   class OreVeinNoiseSource implements BaseStoneSource {
      private final NoiseInterpolator veininess;
      private final NoiseInterpolator veinA;
      private final NoiseInterpolator veinB;
      private double factorZ;
      private final long seed;
      private final WorldgenRandom random = new WorldgenRandom();

      public OreVeinNoiseSource(ChunkPos var2, int var3, long var4) {
         this.veininess = new NoiseInterpolator(
            NoiseBasedChunkGenerator.this.cellCountX,
            NoiseBasedChunkGenerator.this.cellCountY,
            NoiseBasedChunkGenerator.this.cellCountZ,
            â˜ƒ,
            â˜ƒ,
            NoiseBasedChunkGenerator.this.oreVeinifier::fillVeininessNoiseColumn
         );
         this.veinA = new NoiseInterpolator(
            NoiseBasedChunkGenerator.this.cellCountX,
            NoiseBasedChunkGenerator.this.cellCountY,
            NoiseBasedChunkGenerator.this.cellCountZ,
            â˜ƒ,
            â˜ƒ,
            NoiseBasedChunkGenerator.this.oreVeinifier::fillNoiseColumnA
         );
         this.veinB = new NoiseInterpolator(
            NoiseBasedChunkGenerator.this.cellCountX,
            NoiseBasedChunkGenerator.this.cellCountY,
            NoiseBasedChunkGenerator.this.cellCountZ,
            â˜ƒ,
            â˜ƒ,
            NoiseBasedChunkGenerator.this.oreVeinifier::fillNoiseColumnB
         );
         this.seed = â˜ƒ;
      }

      public void listInterpolators(Consumer<NoiseInterpolator> var1) {
         â˜ƒ.accept(this.veininess);
         â˜ƒ.accept(this.veinA);
         â˜ƒ.accept(this.veinB);
      }

      public void prepare(double var1) {
         this.factorZ = â˜ƒ;
      }

      @Override
      public BlockState getBaseBlock(int var1, int var2, int var3) {
         double â˜ƒ = this.veininess.calculateValue(this.factorZ);
         double â˜ƒx = this.veinA.calculateValue(this.factorZ);
         double â˜ƒxx = this.veinB.calculateValue(this.factorZ);
         this.random.setBaseStoneSeed(this.seed, â˜ƒ, â˜ƒ, â˜ƒ);
         return NoiseBasedChunkGenerator.this.oreVeinifier.oreVeinify(this.random, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
      }
   }
}
