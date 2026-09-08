package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public class FlatLevelSource extends ChunkGenerator {
   public static final Codec<FlatLevelSource> CODEC = FlatLevelGeneratorSettings.CODEC
      .fieldOf("settings")
      .<FlatLevelSource>xmap(FlatLevelSource::new, FlatLevelSource::settings)
      .codec();
   private final FlatLevelGeneratorSettings settings;

   public FlatLevelSource(FlatLevelGeneratorSettings var1) {
      super(new FixedBiomeSource(â˜ƒ.getBiomeFromSettings()), new FixedBiomeSource(â˜ƒ.getBiome()), â˜ƒ.structureSettings(), 0L);
      this.settings = â˜ƒ;
   }

   @Override
   protected Codec<? extends ChunkGenerator> codec() {
      return CODEC;
   }

   @Override
   public ChunkGenerator withSeed(long var1) {
      return this;
   }

   public FlatLevelGeneratorSettings settings() {
      return this.settings;
   }

   @Override
   public void buildSurfaceAndBedrock(WorldGenRegion var1, ChunkAccess var2) {
   }

   @Override
   public int getSpawnHeight(LevelHeightAccessor var1) {
      return â˜ƒ.getMinBuildHeight() + Math.min(â˜ƒ.getHeight(), this.settings.getLayers().size());
   }

   @Override
   public CompletableFuture<ChunkAccess> fillFromNoise(Executor var1, StructureFeatureManager var2, ChunkAccess var3) {
      List<BlockState> â˜ƒ = this.settings.getLayers();
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();
      Heightmap â˜ƒxx = â˜ƒ.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
      Heightmap â˜ƒxxx = â˜ƒ.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < Math.min(â˜ƒ.getHeight(), â˜ƒ.size()); ++â˜ƒxxxx) {
         BlockState â˜ƒxxxxx = (BlockState)â˜ƒ.get(â˜ƒxxxx);
         if (â˜ƒxxxxx != null) {
            int â˜ƒxxxxxx = â˜ƒ.getMinBuildHeight() + â˜ƒxxxx;

            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 16; ++â˜ƒxxxxxxx) {
               for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 16; ++â˜ƒxxxxxxxx) {
                  â˜ƒ.setBlockState(â˜ƒx.set(â˜ƒxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxx), â˜ƒxxxxx, false);
                  â˜ƒxx.update(â˜ƒxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxx);
                  â˜ƒxxx.update(â˜ƒxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxx);
               }
            }
         }
      }

      return CompletableFuture.completedFuture(â˜ƒ);
   }

   @Override
   public int getBaseHeight(int var1, int var2, Heightmap.Types var3, LevelHeightAccessor var4) {
      List<BlockState> â˜ƒ = this.settings.getLayers();

      for(int â˜ƒx = Math.min(â˜ƒ.size(), â˜ƒ.getMaxBuildHeight()) - 1; â˜ƒx >= 0; --â˜ƒx) {
         BlockState â˜ƒxx = (BlockState)â˜ƒ.get(â˜ƒx);
         if (â˜ƒxx != null && â˜ƒ.isOpaque().test(â˜ƒxx)) {
            return â˜ƒ.getMinBuildHeight() + â˜ƒx + 1;
         }
      }

      return â˜ƒ.getMinBuildHeight();
   }

   @Override
   public NoiseColumn getBaseColumn(int var1, int var2, LevelHeightAccessor var3) {
      return new NoiseColumn(
         â˜ƒ.getMinBuildHeight(),
         (BlockState[])this.settings
            .getLayers()
            .stream()
            .limit((long)â˜ƒ.getHeight())
            .map(var0 -> var0 == null ? Blocks.AIR.defaultBlockState() : var0)
            .toArray(var0 -> new BlockState[var0])
      );
   }
}
