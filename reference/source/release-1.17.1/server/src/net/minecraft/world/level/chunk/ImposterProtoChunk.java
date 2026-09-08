package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class ImposterProtoChunk extends ProtoChunk {
   private final LevelChunk wrapped;

   public ImposterProtoChunk(LevelChunk var1) {
      super(â˜ƒ.getPos(), UpgradeData.EMPTY, â˜ƒ);
      this.wrapped = â˜ƒ;
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos var1) {
      return this.wrapped.getBlockEntity(â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getBlockState(BlockPos var1) {
      return this.wrapped.getBlockState(â˜ƒ);
   }

   @Override
   public FluidState getFluidState(BlockPos var1) {
      return this.wrapped.getFluidState(â˜ƒ);
   }

   @Override
   public int getMaxLightLevel() {
      return this.wrapped.getMaxLightLevel();
   }

   @Nullable
   @Override
   public BlockState setBlockState(BlockPos var1, BlockState var2, boolean var3) {
      return null;
   }

   @Override
   public void setBlockEntity(BlockEntity var1) {
   }

   @Override
   public void addEntity(Entity var1) {
   }

   @Override
   public void setStatus(ChunkStatus var1) {
   }

   @Override
   public LevelChunkSection[] getSections() {
      return this.wrapped.getSections();
   }

   @Override
   public void setHeightmap(Heightmap.Types var1, long[] var2) {
   }

   private Heightmap.Types fixType(Heightmap.Types var1) {
      if (â˜ƒ == Heightmap.Types.WORLD_SURFACE_WG) {
         return Heightmap.Types.WORLD_SURFACE;
      } else {
         return â˜ƒ == Heightmap.Types.OCEAN_FLOOR_WG ? Heightmap.Types.OCEAN_FLOOR : â˜ƒ;
      }
   }

   @Override
   public int getHeight(Heightmap.Types var1, int var2, int var3) {
      return this.wrapped.getHeight(this.fixType(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockPos getHeighestPosition(Heightmap.Types var1) {
      return this.wrapped.getHeighestPosition(this.fixType(â˜ƒ));
   }

   @Override
   public ChunkPos getPos() {
      return this.wrapped.getPos();
   }

   @Nullable
   @Override
   public StructureStart<?> getStartForFeature(StructureFeature<?> var1) {
      return this.wrapped.getStartForFeature(â˜ƒ);
   }

   @Override
   public void setStartForFeature(StructureFeature<?> var1, StructureStart<?> var2) {
   }

   @Override
   public Map<StructureFeature<?>, StructureStart<?>> getAllStarts() {
      return this.wrapped.getAllStarts();
   }

   @Override
   public void setAllStarts(Map<StructureFeature<?>, StructureStart<?>> var1) {
   }

   @Override
   public LongSet getReferencesForFeature(StructureFeature<?> var1) {
      return this.wrapped.getReferencesForFeature(â˜ƒ);
   }

   @Override
   public void addReferenceForFeature(StructureFeature<?> var1, long var2) {
   }

   @Override
   public Map<StructureFeature<?>, LongSet> getAllReferences() {
      return this.wrapped.getAllReferences();
   }

   @Override
   public void setAllReferences(Map<StructureFeature<?>, LongSet> var1) {
   }

   @Override
   public ChunkBiomeContainer getBiomes() {
      return this.wrapped.getBiomes();
   }

   @Override
   public void setUnsaved(boolean var1) {
   }

   @Override
   public boolean isUnsaved() {
      return false;
   }

   @Override
   public ChunkStatus getStatus() {
      return this.wrapped.getStatus();
   }

   @Override
   public void removeBlockEntity(BlockPos var1) {
   }

   @Override
   public void markPosForPostprocessing(BlockPos var1) {
   }

   @Override
   public void setBlockEntityNbt(CompoundTag var1) {
   }

   @Nullable
   @Override
   public CompoundTag getBlockEntityNbt(BlockPos var1) {
      return this.wrapped.getBlockEntityNbt(â˜ƒ);
   }

   @Nullable
   @Override
   public CompoundTag getBlockEntityNbtForSaving(BlockPos var1) {
      return this.wrapped.getBlockEntityNbtForSaving(â˜ƒ);
   }

   @Override
   public void setBiomes(ChunkBiomeContainer var1) {
   }

   @Override
   public Stream<BlockPos> getLights() {
      return this.wrapped.getLights();
   }

   @Override
   public ProtoTickList<Block> getBlockTicks() {
      return new ProtoTickList<>(var0 -> var0.defaultBlockState().isAir(), this.getPos(), this);
   }

   @Override
   public ProtoTickList<Fluid> getLiquidTicks() {
      return new ProtoTickList<>(var0 -> var0 == Fluids.EMPTY, this.getPos(), this);
   }

   @Override
   public BitSet getCarvingMask(GenerationStep.Carving var1) {
      throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("Meaningless in this context"));
   }

   @Override
   public BitSet getOrCreateCarvingMask(GenerationStep.Carving var1) {
      throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("Meaningless in this context"));
   }

   public LevelChunk getWrapped() {
      return this.wrapped;
   }

   @Override
   public boolean isLightCorrect() {
      return this.wrapped.isLightCorrect();
   }

   @Override
   public void setLightCorrect(boolean var1) {
      this.wrapped.setLightCorrect(â˜ƒ);
   }
}
