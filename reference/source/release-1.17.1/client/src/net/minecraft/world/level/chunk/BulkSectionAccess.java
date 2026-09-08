package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BulkSectionAccess implements AutoCloseable {
   private final LevelAccessor level;
   private final Long2ObjectMap<LevelChunkSection> acquiredSections = new Long2ObjectOpenHashMap<>();
   @Nullable
   private LevelChunkSection lastSection;
   private long lastSectionKey;

   public BulkSectionAccess(LevelAccessor var1) {
      this.level = â˜ƒ;
   }

   @Nullable
   public LevelChunkSection getSection(BlockPos var1) {
      int â˜ƒ = this.level.getSectionIndex(â˜ƒ.getY());
      if (â˜ƒ >= 0 && â˜ƒ < this.level.getSectionsCount()) {
         long â˜ƒx = SectionPos.asLong(â˜ƒ);
         if (this.lastSection == null || this.lastSectionKey != â˜ƒx) {
            this.lastSection = this.acquiredSections.computeIfAbsent(â˜ƒx, var3x -> {
               ChunkAccess â˜ƒ = this.level.getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()));
               LevelChunkSection â˜ƒx = â˜ƒ.getOrCreateSection(â˜ƒ);
               â˜ƒx.acquire();
               return â˜ƒx;
            });
            this.lastSectionKey = â˜ƒx;
         }

         return this.lastSection;
      } else {
         return LevelChunk.EMPTY_SECTION;
      }
   }

   public BlockState getBlockState(BlockPos var1) {
      LevelChunkSection â˜ƒ = this.getSection(â˜ƒ);
      if (â˜ƒ == LevelChunk.EMPTY_SECTION) {
         return Blocks.AIR.defaultBlockState();
      } else {
         int â˜ƒ = SectionPos.sectionRelative(â˜ƒ.getX());
         int â˜ƒx = SectionPos.sectionRelative(â˜ƒ.getY());
         int â˜ƒxx = SectionPos.sectionRelative(â˜ƒ.getZ());
         return â˜ƒ.getBlockState(â˜ƒ, â˜ƒx, â˜ƒxx);
      }
   }

   public void close() {
      for(LevelChunkSection â˜ƒ : this.acquiredSections.values()) {
         â˜ƒ.release();
      }
   }
}
