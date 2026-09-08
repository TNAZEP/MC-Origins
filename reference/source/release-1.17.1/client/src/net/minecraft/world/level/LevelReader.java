package net.minecraft.world.level;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public interface LevelReader extends BlockAndTintGetter, CollisionGetter, BiomeManager.NoiseBiomeSource {
   @Nullable
   ChunkAccess getChunk(int var1, int var2, ChunkStatus var3, boolean var4);

   @Deprecated
   boolean hasChunk(int var1, int var2);

   int getHeight(Heightmap.Types var1, int var2, int var3);

   int getSkyDarken();

   BiomeManager getBiomeManager();

   default Biome getBiome(BlockPos var1) {
      return this.getBiomeManager().getBiome(â˜ƒ);
   }

   default Stream<BlockState> getBlockStatesIfLoaded(AABB var1) {
      int â˜ƒ = Mth.floor(â˜ƒ.minX);
      int â˜ƒx = Mth.floor(â˜ƒ.maxX);
      int â˜ƒxx = Mth.floor(â˜ƒ.minY);
      int â˜ƒxxx = Mth.floor(â˜ƒ.maxY);
      int â˜ƒxxxx = Mth.floor(â˜ƒ.minZ);
      int â˜ƒxxxxx = Mth.floor(â˜ƒ.maxZ);
      return this.hasChunksAt(â˜ƒ, â˜ƒxx, â˜ƒxxxx, â˜ƒx, â˜ƒxxx, â˜ƒxxxxx) ? this.getBlockStates(â˜ƒ) : Stream.empty();
   }

   @Override
   default int getBlockTint(BlockPos var1, ColorResolver var2) {
      return â˜ƒ.getColor(this.getBiome(â˜ƒ), (double)â˜ƒ.getX(), (double)â˜ƒ.getZ());
   }

   @Override
   default Biome getNoiseBiome(int var1, int var2, int var3) {
      ChunkAccess â˜ƒ = this.getChunk(QuartPos.toSection(â˜ƒ), QuartPos.toSection(â˜ƒ), ChunkStatus.BIOMES, false);
      return â˜ƒ != null && â˜ƒ.getBiomes() != null ? â˜ƒ.getBiomes().getNoiseBiome(â˜ƒ, â˜ƒ, â˜ƒ) : this.getUncachedNoiseBiome(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   Biome getUncachedNoiseBiome(int var1, int var2, int var3);

   boolean isClientSide();

   @Deprecated
   int getSeaLevel();

   DimensionType dimensionType();

   @Override
   default int getMinBuildHeight() {
      return this.dimensionType().minY();
   }

   @Override
   default int getHeight() {
      return this.dimensionType().height();
   }

   default BlockPos getHeightmapPos(Heightmap.Types var1, BlockPos var2) {
      return new BlockPos(â˜ƒ.getX(), this.getHeight(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getZ()), â˜ƒ.getZ());
   }

   default boolean isEmptyBlock(BlockPos var1) {
      return this.getBlockState(â˜ƒ).isAir();
   }

   default boolean canSeeSkyFromBelowWater(BlockPos var1) {
      if (â˜ƒ.getY() >= this.getSeaLevel()) {
         return this.canSeeSky(â˜ƒ);
      } else {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ.getX(), this.getSeaLevel(), â˜ƒ.getZ());
         if (!this.canSeeSky(â˜ƒ)) {
            return false;
         } else {
            for(BlockPos var4 = â˜ƒ.below(); var4.getY() > â˜ƒ.getY(); var4 = var4.below()) {
               BlockState â˜ƒ = this.getBlockState(var4);
               if (â˜ƒ.getLightBlock(this, var4) > 0 && !â˜ƒ.getMaterial().isLiquid()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Deprecated
   default float getBrightness(BlockPos var1) {
      return this.dimensionType().brightness(this.getMaxLocalRawBrightness(â˜ƒ));
   }

   default int getDirectSignal(BlockPos var1, Direction var2) {
      return this.getBlockState(â˜ƒ).getDirectSignal(this, â˜ƒ, â˜ƒ);
   }

   default ChunkAccess getChunk(BlockPos var1) {
      return this.getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()));
   }

   default ChunkAccess getChunk(int var1, int var2) {
      return this.getChunk(â˜ƒ, â˜ƒ, ChunkStatus.FULL, true);
   }

   default ChunkAccess getChunk(int var1, int var2, ChunkStatus var3) {
      return this.getChunk(â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   @Nullable
   @Override
   default BlockGetter getChunkForCollisions(int var1, int var2) {
      return this.getChunk(â˜ƒ, â˜ƒ, ChunkStatus.EMPTY, false);
   }

   default boolean isWaterAt(BlockPos var1) {
      return this.getFluidState(â˜ƒ).is(FluidTags.WATER);
   }

   default boolean containsAnyLiquid(AABB var1) {
      int â˜ƒ = Mth.floor(â˜ƒ.minX);
      int â˜ƒx = Mth.ceil(â˜ƒ.maxX);
      int â˜ƒxx = Mth.floor(â˜ƒ.minY);
      int â˜ƒxxx = Mth.ceil(â˜ƒ.maxY);
      int â˜ƒxxxx = Mth.floor(â˜ƒ.minZ);
      int â˜ƒxxxxx = Mth.ceil(â˜ƒ.maxZ);
      BlockPos.MutableBlockPos â˜ƒxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxx = â˜ƒ; â˜ƒxxxxxxx < â˜ƒx; ++â˜ƒxxxxxxx) {
         for(int â˜ƒxxxxxxxx = â˜ƒxx; â˜ƒxxxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxxxx) {
            for(int â˜ƒxxxxxxxxx = â˜ƒxxxx; â˜ƒxxxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxxxx) {
               BlockState â˜ƒxxxxxxxxxx = this.getBlockState(â˜ƒxxxxxx.set(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx));
               if (!â˜ƒxxxxxxxxxx.getFluidState().isEmpty()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   default int getMaxLocalRawBrightness(BlockPos var1) {
      return this.getMaxLocalRawBrightness(â˜ƒ, this.getSkyDarken());
   }

   default int getMaxLocalRawBrightness(BlockPos var1, int var2) {
      return â˜ƒ.getX() >= -30000000 && â˜ƒ.getZ() >= -30000000 && â˜ƒ.getX() < 30000000 && â˜ƒ.getZ() < 30000000 ? this.getRawBrightness(â˜ƒ, â˜ƒ) : 15;
   }

   @Deprecated
   default boolean hasChunkAt(int var1, int var2) {
      return this.hasChunk(SectionPos.blockToSectionCoord(â˜ƒ), SectionPos.blockToSectionCoord(â˜ƒ));
   }

   @Deprecated
   default boolean hasChunkAt(BlockPos var1) {
      return this.hasChunkAt(â˜ƒ.getX(), â˜ƒ.getZ());
   }

   @Deprecated
   default boolean hasChunksAt(BlockPos var1, BlockPos var2) {
      return this.hasChunksAt(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   @Deprecated
   default boolean hasChunksAt(int var1, int var2, int var3, int var4, int var5, int var6) {
      return â˜ƒ >= this.getMinBuildHeight() && â˜ƒ < this.getMaxBuildHeight() ? this.hasChunksAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : false;
   }

   @Deprecated
   default boolean hasChunksAt(int var1, int var2, int var3, int var4) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ);
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ);
      int â˜ƒxx = SectionPos.blockToSectionCoord(â˜ƒ);
      int â˜ƒxxx = SectionPos.blockToSectionCoord(â˜ƒ);

      for(int â˜ƒxxxx = â˜ƒ; â˜ƒxxxx <= â˜ƒx; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = â˜ƒxx; â˜ƒxxxxx <= â˜ƒxxx; ++â˜ƒxxxxx) {
            if (!this.hasChunk(â˜ƒxxxx, â˜ƒxxxxx)) {
               return false;
            }
         }
      }

      return true;
   }
}
