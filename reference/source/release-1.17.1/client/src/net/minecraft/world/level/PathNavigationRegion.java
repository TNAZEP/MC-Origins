package net.minecraft.world.level;

import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PathNavigationRegion implements BlockGetter, CollisionGetter {
   protected final int centerX;
   protected final int centerZ;
   protected final ChunkAccess[][] chunks;
   protected boolean allEmpty;
   protected final Level level;

   public PathNavigationRegion(Level var1, BlockPos var2, BlockPos var3) {
      this.level = â˜ƒ;
      this.centerX = SectionPos.blockToSectionCoord(â˜ƒ.getX());
      this.centerZ = SectionPos.blockToSectionCoord(â˜ƒ.getZ());
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getX());
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getZ());
      this.chunks = new ChunkAccess[â˜ƒ - this.centerX + 1][â˜ƒx - this.centerZ + 1];
      ChunkSource â˜ƒxx = â˜ƒ.getChunkSource();
      this.allEmpty = true;

      for(int â˜ƒxxx = this.centerX; â˜ƒxxx <= â˜ƒ; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = this.centerZ; â˜ƒxxxx <= â˜ƒx; ++â˜ƒxxxx) {
            this.chunks[â˜ƒxxx - this.centerX][â˜ƒxxxx - this.centerZ] = â˜ƒxx.getChunkNow(â˜ƒxxx, â˜ƒxxxx);
         }
      }

      for(int â˜ƒxxx = SectionPos.blockToSectionCoord(â˜ƒ.getX()); â˜ƒxxx <= SectionPos.blockToSectionCoord(â˜ƒ.getX()); ++â˜ƒxxx) {
         for(int â˜ƒxxxx = SectionPos.blockToSectionCoord(â˜ƒ.getZ()); â˜ƒxxxx <= SectionPos.blockToSectionCoord(â˜ƒ.getZ()); ++â˜ƒxxxx) {
            ChunkAccess â˜ƒxxxxx = this.chunks[â˜ƒxxx - this.centerX][â˜ƒxxxx - this.centerZ];
            if (â˜ƒxxxxx != null && !â˜ƒxxxxx.isYSpaceEmpty(â˜ƒ.getY(), â˜ƒ.getY())) {
               this.allEmpty = false;
               return;
            }
         }
      }
   }

   private ChunkAccess getChunk(BlockPos var1) {
      return this.getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()));
   }

   private ChunkAccess getChunk(int var1, int var2) {
      int â˜ƒ = â˜ƒ - this.centerX;
      int â˜ƒx = â˜ƒ - this.centerZ;
      if (â˜ƒ >= 0 && â˜ƒ < this.chunks.length && â˜ƒx >= 0 && â˜ƒx < this.chunks[â˜ƒ].length) {
         ChunkAccess â˜ƒxx = this.chunks[â˜ƒ][â˜ƒx];
         return (ChunkAccess)(â˜ƒxx != null ? â˜ƒxx : new EmptyLevelChunk(this.level, new ChunkPos(â˜ƒ, â˜ƒ)));
      } else {
         return new EmptyLevelChunk(this.level, new ChunkPos(â˜ƒ, â˜ƒ));
      }
   }

   @Override
   public WorldBorder getWorldBorder() {
      return this.level.getWorldBorder();
   }

   @Override
   public BlockGetter getChunkForCollisions(int var1, int var2) {
      return this.getChunk(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos var1) {
      ChunkAccess â˜ƒ = this.getChunk(â˜ƒ);
      return â˜ƒ.getBlockEntity(â˜ƒ);
   }

   @Override
   public BlockState getBlockState(BlockPos var1) {
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         ChunkAccess â˜ƒ = this.getChunk(â˜ƒ);
         return â˜ƒ.getBlockState(â˜ƒ);
      }
   }

   @Override
   public Stream<VoxelShape> getEntityCollisions(@Nullable Entity var1, AABB var2, Predicate<Entity> var3) {
      return Stream.empty();
   }

   @Override
   public Stream<VoxelShape> getCollisions(@Nullable Entity var1, AABB var2, Predicate<Entity> var3) {
      return this.getBlockCollisions(â˜ƒ, â˜ƒ);
   }

   @Override
   public FluidState getFluidState(BlockPos var1) {
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return Fluids.EMPTY.defaultFluidState();
      } else {
         ChunkAccess â˜ƒ = this.getChunk(â˜ƒ);
         return â˜ƒ.getFluidState(â˜ƒ);
      }
   }

   @Override
   public int getMinBuildHeight() {
      return this.level.getMinBuildHeight();
   }

   @Override
   public int getHeight() {
      return this.level.getHeight();
   }

   public ProfilerFiller getProfiler() {
      return this.level.getProfiler();
   }
}
