package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class DebugLevelSource extends ChunkGenerator {
   public static final Codec<DebugLevelSource> CODEC = RegistryLookupCodec.create(Registry.BIOME_REGISTRY)
      .<DebugLevelSource>xmap(DebugLevelSource::new, DebugLevelSource::biomes)
      .stable()
      .codec();
   private static final int BLOCK_MARGIN = 2;
   private static final List<BlockState> ALL_BLOCKS = (List<BlockState>)StreamSupport.stream(Registry.BLOCK.spliterator(), false)
      .flatMap(var0 -> var0.getStateDefinition().getPossibleStates().stream())
      .collect(Collectors.toList());
   private static final int GRID_WIDTH = Mth.ceil(Mth.sqrt((float)ALL_BLOCKS.size()));
   private static final int GRID_HEIGHT = Mth.ceil((float)ALL_BLOCKS.size() / (float)GRID_WIDTH);
   protected static final BlockState AIR = Blocks.AIR.defaultBlockState();
   protected static final BlockState BARRIER = Blocks.BARRIER.defaultBlockState();
   public static final int HEIGHT = 70;
   public static final int BARRIER_HEIGHT = 60;
   private final Registry<Biome> biomes;

   public DebugLevelSource(Registry<Biome> var1) {
      super(new FixedBiomeSource(â˜ƒ.getOrThrow(Biomes.PLAINS)), new StructureSettings(false));
      this.biomes = â˜ƒ;
   }

   public Registry<Biome> biomes() {
      return this.biomes;
   }

   @Override
   protected Codec<? extends ChunkGenerator> codec() {
      return CODEC;
   }

   @Override
   public ChunkGenerator withSeed(long var1) {
      return this;
   }

   @Override
   public void buildSurfaceAndBedrock(WorldGenRegion var1, ChunkAccess var2) {
   }

   @Override
   public void applyCarvers(long var1, BiomeManager var3, ChunkAccess var4, GenerationStep.Carving var5) {
   }

   @Override
   public void applyBiomeDecoration(WorldGenRegion var1, StructureFeatureManager var2) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      ChunkPos â˜ƒx = â˜ƒ.getCenter();

      for(int â˜ƒxx = 0; â˜ƒxx < 16; ++â˜ƒxx) {
         for(int â˜ƒxxx = 0; â˜ƒxxx < 16; ++â˜ƒxxx) {
            int â˜ƒxxxx = SectionPos.sectionToBlockCoord(â˜ƒx.x, â˜ƒxx);
            int â˜ƒxxxxx = SectionPos.sectionToBlockCoord(â˜ƒx.z, â˜ƒxxx);
            â˜ƒ.setBlock(â˜ƒ.set(â˜ƒxxxx, 60, â˜ƒxxxxx), BARRIER, 2);
            BlockState â˜ƒxxxxxx = getBlockStateFor(â˜ƒxxxx, â˜ƒxxxxx);
            if (â˜ƒxxxxxx != null) {
               â˜ƒ.setBlock(â˜ƒ.set(â˜ƒxxxx, 70, â˜ƒxxxxx), â˜ƒxxxxxx, 2);
            }
         }
      }
   }

   @Override
   public CompletableFuture<ChunkAccess> fillFromNoise(Executor var1, StructureFeatureManager var2, ChunkAccess var3) {
      return CompletableFuture.completedFuture(â˜ƒ);
   }

   @Override
   public int getBaseHeight(int var1, int var2, Heightmap.Types var3, LevelHeightAccessor var4) {
      return 0;
   }

   @Override
   public NoiseColumn getBaseColumn(int var1, int var2, LevelHeightAccessor var3) {
      return new NoiseColumn(0, new BlockState[0]);
   }

   public static BlockState getBlockStateFor(int var0, int var1) {
      BlockState â˜ƒ = AIR;
      if (â˜ƒ > 0 && â˜ƒ > 0 && â˜ƒ % 2 != 0 && â˜ƒ % 2 != 0) {
         â˜ƒ /= 2;
         â˜ƒ /= 2;
         if (â˜ƒ <= GRID_WIDTH && â˜ƒ <= GRID_HEIGHT) {
            int â˜ƒx = Mth.abs(â˜ƒ * GRID_WIDTH + â˜ƒ);
            if (â˜ƒx < ALL_BLOCKS.size()) {
               â˜ƒ = (BlockState)ALL_BLOCKS.get(â˜ƒx);
            }
         }
      }

      return â˜ƒ;
   }
}
