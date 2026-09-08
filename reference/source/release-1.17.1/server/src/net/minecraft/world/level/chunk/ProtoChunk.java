package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProtoChunk implements ChunkAccess {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ChunkPos chunkPos;
   private volatile boolean isDirty;
   @Nullable
   private ChunkBiomeContainer biomes;
   @Nullable
   private volatile LevelLightEngine lightEngine;
   private final Map<Heightmap.Types, Heightmap> heightmaps = Maps.newEnumMap(Heightmap.Types.class);
   private volatile ChunkStatus status = ChunkStatus.EMPTY;
   private final Map<BlockPos, BlockEntity> blockEntities = Maps.<BlockPos, BlockEntity>newHashMap();
   private final Map<BlockPos, CompoundTag> blockEntityNbts = Maps.<BlockPos, CompoundTag>newHashMap();
   private final LevelChunkSection[] sections;
   private final List<CompoundTag> entities = Lists.<CompoundTag>newArrayList();
   private final List<BlockPos> lights = Lists.<BlockPos>newArrayList();
   private final ShortList[] postProcessing;
   private final Map<StructureFeature<?>, StructureStart<?>> structureStarts = Maps.<StructureFeature<?>, StructureStart<?>>newHashMap();
   private final Map<StructureFeature<?>, LongSet> structuresRefences = Maps.<StructureFeature<?>, LongSet>newHashMap();
   private final UpgradeData upgradeData;
   private final ProtoTickList<Block> blockTicks;
   private final ProtoTickList<Fluid> liquidTicks;
   private final LevelHeightAccessor levelHeightAccessor;
   private long inhabitedTime;
   private final Map<GenerationStep.Carving, BitSet> carvingMasks = new Object2ObjectArrayMap<>();
   private volatile boolean isLightCorrect;

   public ProtoChunk(ChunkPos var1, UpgradeData var2, LevelHeightAccessor var3) {
      this(
         â˜ƒ,
         â˜ƒ,
         null,
         new ProtoTickList<>(var0 -> var0 == null || var0.defaultBlockState().isAir(), â˜ƒ, â˜ƒ),
         new ProtoTickList<>(var0 -> var0 == null || var0 == Fluids.EMPTY, â˜ƒ, â˜ƒ),
         â˜ƒ
      );
   }

   public ProtoChunk(
      ChunkPos var1, UpgradeData var2, @Nullable LevelChunkSection[] var3, ProtoTickList<Block> var4, ProtoTickList<Fluid> var5, LevelHeightAccessor var6
   ) {
      this.chunkPos = â˜ƒ;
      this.upgradeData = â˜ƒ;
      this.blockTicks = â˜ƒ;
      this.liquidTicks = â˜ƒ;
      this.levelHeightAccessor = â˜ƒ;
      this.sections = new LevelChunkSection[â˜ƒ.getSectionsCount()];
      if (â˜ƒ != null) {
         if (this.sections.length == â˜ƒ.length) {
            System.arraycopy(â˜ƒ, 0, this.sections, 0, this.sections.length);
         } else {
            LOGGER.warn("Could not set level chunk sections, array length is {} instead of {}", â˜ƒ.length, this.sections.length);
         }
      }

      this.postProcessing = new ShortList[â˜ƒ.getSectionsCount()];
   }

   @Override
   public BlockState getBlockState(BlockPos var1) {
      int â˜ƒ = â˜ƒ.getY();
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return Blocks.VOID_AIR.defaultBlockState();
      } else {
         LevelChunkSection â˜ƒ = this.getSections()[this.getSectionIndex(â˜ƒ)];
         return LevelChunkSection.isEmpty(â˜ƒ) ? Blocks.AIR.defaultBlockState() : â˜ƒ.getBlockState(â˜ƒ.getX() & 15, â˜ƒ & 15, â˜ƒ.getZ() & 15);
      }
   }

   @Override
   public FluidState getFluidState(BlockPos var1) {
      int â˜ƒ = â˜ƒ.getY();
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return Fluids.EMPTY.defaultFluidState();
      } else {
         LevelChunkSection â˜ƒ = this.getSections()[this.getSectionIndex(â˜ƒ)];
         return LevelChunkSection.isEmpty(â˜ƒ) ? Fluids.EMPTY.defaultFluidState() : â˜ƒ.getFluidState(â˜ƒ.getX() & 15, â˜ƒ & 15, â˜ƒ.getZ() & 15);
      }
   }

   @Override
   public Stream<BlockPos> getLights() {
      return this.lights.stream();
   }

   public ShortList[] getPackedLights() {
      ShortList[] â˜ƒ = new ShortList[this.getSectionsCount()];

      for(BlockPos â˜ƒx : this.lights) {
         ChunkAccess.getOrCreateOffsetList(â˜ƒ, this.getSectionIndex(â˜ƒx.getY())).add(packOffsetCoordinates(â˜ƒx));
      }

      return â˜ƒ;
   }

   public void addLight(short var1, int var2) {
      this.addLight(unpackOffsetCoordinates(â˜ƒ, this.getSectionYFromSectionIndex(â˜ƒ), this.chunkPos));
   }

   public void addLight(BlockPos var1) {
      this.lights.add(â˜ƒ.immutable());
   }

   @Nullable
   @Override
   public BlockState setBlockState(BlockPos var1, BlockState var2, boolean var3) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();
      if (â˜ƒx >= this.getMinBuildHeight() && â˜ƒx < this.getMaxBuildHeight()) {
         int â˜ƒxxx = this.getSectionIndex(â˜ƒx);
         if (this.sections[â˜ƒxxx] == LevelChunk.EMPTY_SECTION && â˜ƒ.is(Blocks.AIR)) {
            return â˜ƒ;
         } else {
            if (â˜ƒ.getLightEmission() > 0) {
               this.lights.add(new BlockPos((â˜ƒ & 15) + this.getPos().getMinBlockX(), â˜ƒx, (â˜ƒxx & 15) + this.getPos().getMinBlockZ()));
            }

            LevelChunkSection â˜ƒxxx = this.getOrCreateSection(â˜ƒxxx);
            BlockState â˜ƒxxxx = â˜ƒxxx.setBlockState(â˜ƒ & 15, â˜ƒx & 15, â˜ƒxx & 15, â˜ƒ);
            if (this.status.isOrAfter(ChunkStatus.FEATURES)
               && â˜ƒ != â˜ƒxxxx
               && (
                  â˜ƒ.getLightBlock(this, â˜ƒ) != â˜ƒxxxx.getLightBlock(this, â˜ƒ)
                     || â˜ƒ.getLightEmission() != â˜ƒxxxx.getLightEmission()
                     || â˜ƒ.useShapeForLightOcclusion()
                     || â˜ƒxxxx.useShapeForLightOcclusion()
               )) {
               this.lightEngine.checkBlock(â˜ƒ);
            }

            EnumSet<Heightmap.Types> â˜ƒxxx = this.getStatus().heightmapsAfter();
            EnumSet<Heightmap.Types> â˜ƒxxxx = null;

            for(Heightmap.Types â˜ƒxxxxx : â˜ƒxxx) {
               Heightmap â˜ƒxxxxxx = (Heightmap)this.heightmaps.get(â˜ƒxxxxx);
               if (â˜ƒxxxxxx == null) {
                  if (â˜ƒxxxx == null) {
                     â˜ƒxxxx = EnumSet.noneOf(Heightmap.Types.class);
                  }

                  â˜ƒxxxx.add(â˜ƒxxxxx);
               }
            }

            if (â˜ƒxxxx != null) {
               Heightmap.primeHeightmaps(this, â˜ƒxxxx);
            }

            for(Heightmap.Types â˜ƒxxxxx : â˜ƒxxx) {
               ((Heightmap)this.heightmaps.get(â˜ƒxxxxx)).update(â˜ƒ & 15, â˜ƒx, â˜ƒxx & 15, â˜ƒ);
            }

            return â˜ƒxxxx;
         }
      } else {
         return Blocks.VOID_AIR.defaultBlockState();
      }
   }

   @Override
   public void setBlockEntity(BlockEntity var1) {
      this.blockEntities.put(â˜ƒ.getBlockPos(), â˜ƒ);
   }

   @Override
   public Set<BlockPos> getBlockEntitiesPos() {
      Set<BlockPos> â˜ƒ = Sets.<BlockPos>newHashSet(this.blockEntityNbts.keySet());
      â˜ƒ.addAll(this.blockEntities.keySet());
      return â˜ƒ;
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos var1) {
      return (BlockEntity)this.blockEntities.get(â˜ƒ);
   }

   public Map<BlockPos, BlockEntity> getBlockEntities() {
      return this.blockEntities;
   }

   public void addEntity(CompoundTag var1) {
      this.entities.add(â˜ƒ);
   }

   @Override
   public void addEntity(Entity var1) {
      if (!â˜ƒ.isPassenger()) {
         CompoundTag â˜ƒ = new CompoundTag();
         â˜ƒ.save(â˜ƒ);
         this.addEntity(â˜ƒ);
      }
   }

   public List<CompoundTag> getEntities() {
      return this.entities;
   }

   public void setBiomes(ChunkBiomeContainer var1) {
      this.biomes = â˜ƒ;
   }

   @Nullable
   @Override
   public ChunkBiomeContainer getBiomes() {
      return this.biomes;
   }

   @Override
   public void setUnsaved(boolean var1) {
      this.isDirty = â˜ƒ;
   }

   @Override
   public boolean isUnsaved() {
      return this.isDirty;
   }

   @Override
   public ChunkStatus getStatus() {
      return this.status;
   }

   public void setStatus(ChunkStatus var1) {
      this.status = â˜ƒ;
      this.setUnsaved(true);
   }

   @Override
   public LevelChunkSection[] getSections() {
      return this.sections;
   }

   @Override
   public Collection<Entry<Heightmap.Types, Heightmap>> getHeightmaps() {
      return Collections.unmodifiableSet(this.heightmaps.entrySet());
   }

   @Override
   public Heightmap getOrCreateHeightmapUnprimed(Heightmap.Types var1) {
      return (Heightmap)this.heightmaps.computeIfAbsent(â˜ƒ, var1x -> new Heightmap(this, var1x));
   }

   @Override
   public int getHeight(Heightmap.Types var1, int var2, int var3) {
      Heightmap â˜ƒ = (Heightmap)this.heightmaps.get(â˜ƒ);
      if (â˜ƒ == null) {
         Heightmap.primeHeightmaps(this, EnumSet.of(â˜ƒ));
         â˜ƒ = (Heightmap)this.heightmaps.get(â˜ƒ);
      }

      return â˜ƒ.getFirstAvailable(â˜ƒ & 15, â˜ƒ & 15) - 1;
   }

   @Override
   public BlockPos getHeighestPosition(Heightmap.Types var1) {
      int â˜ƒ = this.getMinBuildHeight();
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxx = this.chunkPos.getMinBlockX(); â˜ƒxx <= this.chunkPos.getMaxBlockX(); ++â˜ƒxx) {
         for(int â˜ƒxxx = this.chunkPos.getMinBlockZ(); â˜ƒxxx <= this.chunkPos.getMaxBlockZ(); ++â˜ƒxxx) {
            int â˜ƒxxxx = this.getHeight(â˜ƒ, â˜ƒxx & 15, â˜ƒxxx & 15);
            if (â˜ƒxxxx > â˜ƒ) {
               â˜ƒ = â˜ƒxxxx;
               â˜ƒx.set(â˜ƒxx, â˜ƒxxxx, â˜ƒxxx);
            }
         }
      }

      return â˜ƒx.immutable();
   }

   @Override
   public ChunkPos getPos() {
      return this.chunkPos;
   }

   @Nullable
   @Override
   public StructureStart<?> getStartForFeature(StructureFeature<?> var1) {
      return (StructureStart<?>)this.structureStarts.get(â˜ƒ);
   }

   @Override
   public void setStartForFeature(StructureFeature<?> var1, StructureStart<?> var2) {
      this.structureStarts.put(â˜ƒ, â˜ƒ);
      this.isDirty = true;
   }

   @Override
   public Map<StructureFeature<?>, StructureStart<?>> getAllStarts() {
      return Collections.unmodifiableMap(this.structureStarts);
   }

   @Override
   public void setAllStarts(Map<StructureFeature<?>, StructureStart<?>> var1) {
      this.structureStarts.clear();
      this.structureStarts.putAll(â˜ƒ);
      this.isDirty = true;
   }

   @Override
   public LongSet getReferencesForFeature(StructureFeature<?> var1) {
      return (LongSet)this.structuresRefences.computeIfAbsent(â˜ƒ, var0 -> new LongOpenHashSet());
   }

   @Override
   public void addReferenceForFeature(StructureFeature<?> var1, long var2) {
      ((LongSet)this.structuresRefences.computeIfAbsent(â˜ƒ, var0 -> new LongOpenHashSet())).add(â˜ƒ);
      this.isDirty = true;
   }

   @Override
   public Map<StructureFeature<?>, LongSet> getAllReferences() {
      return Collections.unmodifiableMap(this.structuresRefences);
   }

   @Override
   public void setAllReferences(Map<StructureFeature<?>, LongSet> var1) {
      this.structuresRefences.clear();
      this.structuresRefences.putAll(â˜ƒ);
      this.isDirty = true;
   }

   public static short packOffsetCoordinates(BlockPos var0) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();
      int â˜ƒxxx = â˜ƒ & 15;
      int â˜ƒxxxx = â˜ƒx & 15;
      int â˜ƒxxxxx = â˜ƒxx & 15;
      return (short)(â˜ƒxxx | â˜ƒxxxx << 4 | â˜ƒxxxxx << 8);
   }

   public static BlockPos unpackOffsetCoordinates(short var0, int var1, ChunkPos var2) {
      int â˜ƒ = SectionPos.sectionToBlockCoord(â˜ƒ.x, â˜ƒ & 15);
      int â˜ƒx = SectionPos.sectionToBlockCoord(â˜ƒ, â˜ƒ >>> 4 & 15);
      int â˜ƒxx = SectionPos.sectionToBlockCoord(â˜ƒ.z, â˜ƒ >>> 8 & 15);
      return new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   @Override
   public void markPosForPostprocessing(BlockPos var1) {
      if (!this.isOutsideBuildHeight(â˜ƒ)) {
         ChunkAccess.getOrCreateOffsetList(this.postProcessing, this.getSectionIndex(â˜ƒ.getY())).add(packOffsetCoordinates(â˜ƒ));
      }
   }

   @Override
   public ShortList[] getPostProcessing() {
      return this.postProcessing;
   }

   @Override
   public void addPackedPostProcess(short var1, int var2) {
      ChunkAccess.getOrCreateOffsetList(this.postProcessing, â˜ƒ).add(â˜ƒ);
   }

   public ProtoTickList<Block> getBlockTicks() {
      return this.blockTicks;
   }

   public ProtoTickList<Fluid> getLiquidTicks() {
      return this.liquidTicks;
   }

   @Override
   public UpgradeData getUpgradeData() {
      return this.upgradeData;
   }

   @Override
   public void setInhabitedTime(long var1) {
      this.inhabitedTime = â˜ƒ;
   }

   @Override
   public long getInhabitedTime() {
      return this.inhabitedTime;
   }

   @Override
   public void setBlockEntityNbt(CompoundTag var1) {
      this.blockEntityNbts.put(new BlockPos(â˜ƒ.getInt("x"), â˜ƒ.getInt("y"), â˜ƒ.getInt("z")), â˜ƒ);
   }

   public Map<BlockPos, CompoundTag> getBlockEntityNbts() {
      return Collections.unmodifiableMap(this.blockEntityNbts);
   }

   @Override
   public CompoundTag getBlockEntityNbt(BlockPos var1) {
      return (CompoundTag)this.blockEntityNbts.get(â˜ƒ);
   }

   @Nullable
   @Override
   public CompoundTag getBlockEntityNbtForSaving(BlockPos var1) {
      BlockEntity â˜ƒ = this.getBlockEntity(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.save(new CompoundTag()) : (CompoundTag)this.blockEntityNbts.get(â˜ƒ);
   }

   @Override
   public void removeBlockEntity(BlockPos var1) {
      this.blockEntities.remove(â˜ƒ);
      this.blockEntityNbts.remove(â˜ƒ);
   }

   @Nullable
   public BitSet getCarvingMask(GenerationStep.Carving var1) {
      return (BitSet)this.carvingMasks.get(â˜ƒ);
   }

   public BitSet getOrCreateCarvingMask(GenerationStep.Carving var1) {
      return (BitSet)this.carvingMasks.computeIfAbsent(â˜ƒ, var0 -> new BitSet(65536));
   }

   public void setCarvingMask(GenerationStep.Carving var1, BitSet var2) {
      this.carvingMasks.put(â˜ƒ, â˜ƒ);
   }

   public void setLightEngine(LevelLightEngine var1) {
      this.lightEngine = â˜ƒ;
   }

   @Override
   public boolean isLightCorrect() {
      return this.isLightCorrect;
   }

   @Override
   public void setLightCorrect(boolean var1) {
      this.isLightCorrect = â˜ƒ;
      this.setUnsaved(true);
   }

   @Override
   public int getMinBuildHeight() {
      return this.levelHeightAccessor.getMinBuildHeight();
   }

   @Override
   public int getHeight() {
      return this.levelHeightAccessor.getHeight();
   }
}
