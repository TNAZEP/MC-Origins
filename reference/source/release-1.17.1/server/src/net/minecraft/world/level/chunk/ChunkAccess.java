package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import org.apache.logging.log4j.LogManager;

public interface ChunkAccess extends BlockGetter, FeatureAccess {
   default GameEventDispatcher getEventDispatcher(int var1) {
      return GameEventDispatcher.NOOP;
   }

   @Nullable
   BlockState setBlockState(BlockPos var1, BlockState var2, boolean var3);

   void setBlockEntity(BlockEntity var1);

   void addEntity(Entity var1);

   @Nullable
   default LevelChunkSection getHighestSection() {
      LevelChunkSection[] â˜ƒ = this.getSections();

      for(int â˜ƒx = â˜ƒ.length - 1; â˜ƒx >= 0; --â˜ƒx) {
         LevelChunkSection â˜ƒxx = â˜ƒ[â˜ƒx];
         if (!LevelChunkSection.isEmpty(â˜ƒxx)) {
            return â˜ƒxx;
         }
      }

      return null;
   }

   default int getHighestSectionPosition() {
      LevelChunkSection â˜ƒ = this.getHighestSection();
      return â˜ƒ == null ? this.getMinBuildHeight() : â˜ƒ.bottomBlockY();
   }

   Set<BlockPos> getBlockEntitiesPos();

   LevelChunkSection[] getSections();

   default LevelChunkSection getOrCreateSection(int var1) {
      LevelChunkSection[] â˜ƒ = this.getSections();
      if (â˜ƒ[â˜ƒ] == LevelChunk.EMPTY_SECTION) {
         â˜ƒ[â˜ƒ] = new LevelChunkSection(this.getSectionYFromSectionIndex(â˜ƒ));
      }

      return â˜ƒ[â˜ƒ];
   }

   Collection<Entry<Heightmap.Types, Heightmap>> getHeightmaps();

   default void setHeightmap(Heightmap.Types var1, long[] var2) {
      this.getOrCreateHeightmapUnprimed(â˜ƒ).setRawData(this, â˜ƒ, â˜ƒ);
   }

   Heightmap getOrCreateHeightmapUnprimed(Heightmap.Types var1);

   int getHeight(Heightmap.Types var1, int var2, int var3);

   BlockPos getHeighestPosition(Heightmap.Types var1);

   ChunkPos getPos();

   Map<StructureFeature<?>, StructureStart<?>> getAllStarts();

   void setAllStarts(Map<StructureFeature<?>, StructureStart<?>> var1);

   default boolean isYSpaceEmpty(int var1, int var2) {
      if (â˜ƒ < this.getMinBuildHeight()) {
         â˜ƒ = this.getMinBuildHeight();
      }

      if (â˜ƒ >= this.getMaxBuildHeight()) {
         â˜ƒ = this.getMaxBuildHeight() - 1;
      }

      for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; â˜ƒ += 16) {
         if (!LevelChunkSection.isEmpty(this.getSections()[this.getSectionIndex(â˜ƒ)])) {
            return false;
         }
      }

      return true;
   }

   @Nullable
   ChunkBiomeContainer getBiomes();

   void setUnsaved(boolean var1);

   boolean isUnsaved();

   ChunkStatus getStatus();

   void removeBlockEntity(BlockPos var1);

   default void markPosForPostprocessing(BlockPos var1) {
      LogManager.getLogger().warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", â˜ƒ);
   }

   ShortList[] getPostProcessing();

   default void addPackedPostProcess(short var1, int var2) {
      getOrCreateOffsetList(this.getPostProcessing(), â˜ƒ).add(â˜ƒ);
   }

   default void setBlockEntityNbt(CompoundTag var1) {
      LogManager.getLogger().warn("Trying to set a BlockEntity, but this operation is not supported.");
   }

   @Nullable
   CompoundTag getBlockEntityNbt(BlockPos var1);

   @Nullable
   CompoundTag getBlockEntityNbtForSaving(BlockPos var1);

   Stream<BlockPos> getLights();

   TickList<Block> getBlockTicks();

   TickList<Fluid> getLiquidTicks();

   UpgradeData getUpgradeData();

   void setInhabitedTime(long var1);

   long getInhabitedTime();

   static ShortList getOrCreateOffsetList(ShortList[] var0, int var1) {
      if (â˜ƒ[â˜ƒ] == null) {
         â˜ƒ[â˜ƒ] = new ShortArrayList();
      }

      return â˜ƒ[â˜ƒ];
   }

   boolean isLightCorrect();

   void setLightCorrect(boolean var1);
}
