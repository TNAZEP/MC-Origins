package net.minecraft.world.level.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ChunkTickList;
import net.minecraft.world.level.EmptyTickList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.EuclideanGameEventDispatcher;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelChunk implements ChunkAccess {
   static final Logger LOGGER = LogManager.getLogger();
   private static final TickingBlockEntity NULL_TICKER = new TickingBlockEntity() {
      @Override
      public void tick() {
      }

      @Override
      public boolean isRemoved() {
         return true;
      }

      @Override
      public BlockPos getPos() {
         return BlockPos.ZERO;
      }

      @Override
      public String getType() {
         return "<null>";
      }
   };
   @Nullable
   public static final LevelChunkSection EMPTY_SECTION = null;
   private final LevelChunkSection[] sections;
   private ChunkBiomeContainer biomes;
   private final Map<BlockPos, CompoundTag> pendingBlockEntities = Maps.<BlockPos, CompoundTag>newHashMap();
   private final Map<BlockPos, LevelChunk.RebindableTickingBlockEntityWrapper> tickersInLevel = Maps.<BlockPos, LevelChunk.RebindableTickingBlockEntityWrapper>newHashMap(
      
   );
   private boolean loaded;
   final Level level;
   private final Map<Heightmap.Types, Heightmap> heightmaps = Maps.newEnumMap(Heightmap.Types.class);
   private final UpgradeData upgradeData;
   private final Map<BlockPos, BlockEntity> blockEntities = Maps.<BlockPos, BlockEntity>newHashMap();
   private final Map<StructureFeature<?>, StructureStart<?>> structureStarts = Maps.<StructureFeature<?>, StructureStart<?>>newHashMap();
   private final Map<StructureFeature<?>, LongSet> structuresRefences = Maps.<StructureFeature<?>, LongSet>newHashMap();
   private final ShortList[] postProcessing;
   private TickList<Block> blockTicks;
   private TickList<Fluid> liquidTicks;
   private volatile boolean unsaved;
   private long inhabitedTime;
   @Nullable
   private Supplier<ChunkHolder.FullChunkStatus> fullStatus;
   @Nullable
   private Consumer<LevelChunk> postLoad;
   private final ChunkPos chunkPos;
   private volatile boolean isLightCorrect;
   private final Int2ObjectMap<GameEventDispatcher> gameEventDispatcherSections;

   public LevelChunk(Level var1, ChunkPos var2, ChunkBiomeContainer var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, UpgradeData.EMPTY, EmptyTickList.empty(), EmptyTickList.empty(), 0L, null, null);
   }

   public LevelChunk(
      Level var1,
      ChunkPos var2,
      ChunkBiomeContainer var3,
      UpgradeData var4,
      TickList<Block> var5,
      TickList<Fluid> var6,
      long var7,
      @Nullable LevelChunkSection[] var9,
      @Nullable Consumer<LevelChunk> var10
   ) {
      this.level = â˜ƒ;
      this.chunkPos = â˜ƒ;
      this.upgradeData = â˜ƒ;
      this.gameEventDispatcherSections = new Int2ObjectOpenHashMap<>();

      for(Heightmap.Types â˜ƒ : Heightmap.Types.values()) {
         if (ChunkStatus.FULL.heightmapsAfter().contains(â˜ƒ)) {
            this.heightmaps.put(â˜ƒ, new Heightmap(this, â˜ƒ));
         }
      }

      this.biomes = â˜ƒ;
      this.blockTicks = â˜ƒ;
      this.liquidTicks = â˜ƒ;
      this.inhabitedTime = â˜ƒ;
      this.postLoad = â˜ƒ;
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

   public LevelChunk(ServerLevel var1, ProtoChunk var2, @Nullable Consumer<LevelChunk> var3) {
      this(â˜ƒ, â˜ƒ.getPos(), â˜ƒ.getBiomes(), â˜ƒ.getUpgradeData(), â˜ƒ.getBlockTicks(), â˜ƒ.getLiquidTicks(), â˜ƒ.getInhabitedTime(), â˜ƒ.getSections(), â˜ƒ);

      for(BlockEntity â˜ƒ : â˜ƒ.getBlockEntities().values()) {
         this.setBlockEntity(â˜ƒ);
      }

      this.pendingBlockEntities.putAll(â˜ƒ.getBlockEntityNbts());

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getPostProcessing().length; ++â˜ƒ) {
         this.postProcessing[â˜ƒ] = â˜ƒ.getPostProcessing()[â˜ƒ];
      }

      this.setAllStarts(â˜ƒ.getAllStarts());
      this.setAllReferences(â˜ƒ.getAllReferences());

      for(Entry<Heightmap.Types, Heightmap> â˜ƒ : â˜ƒ.getHeightmaps()) {
         if (ChunkStatus.FULL.heightmapsAfter().contains(â˜ƒ.getKey())) {
            this.setHeightmap((Heightmap.Types)â˜ƒ.getKey(), ((Heightmap)â˜ƒ.getValue()).getRawData());
         }
      }

      this.setLightCorrect(â˜ƒ.isLightCorrect());
      this.unsaved = true;
   }

   @Override
   public GameEventDispatcher getEventDispatcher(int var1) {
      return this.gameEventDispatcherSections.computeIfAbsent(â˜ƒ, var1x -> new EuclideanGameEventDispatcher(this.level));
   }

   @Override
   public Heightmap getOrCreateHeightmapUnprimed(Heightmap.Types var1) {
      return (Heightmap)this.heightmaps.computeIfAbsent(â˜ƒ, var1x -> new Heightmap(this, var1x));
   }

   @Override
   public Set<BlockPos> getBlockEntitiesPos() {
      Set<BlockPos> â˜ƒ = Sets.<BlockPos>newHashSet(this.pendingBlockEntities.keySet());
      â˜ƒ.addAll(this.blockEntities.keySet());
      return â˜ƒ;
   }

   @Override
   public LevelChunkSection[] getSections() {
      return this.sections;
   }

   @Override
   public BlockState getBlockState(BlockPos var1) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();
      if (this.level.isDebug()) {
         BlockState â˜ƒxxx = null;
         if (â˜ƒx == 60) {
            â˜ƒxxx = Blocks.BARRIER.defaultBlockState();
         }

         if (â˜ƒx == 70) {
            â˜ƒxxx = DebugLevelSource.getBlockStateFor(â˜ƒ, â˜ƒxx);
         }

         return â˜ƒxxx == null ? Blocks.AIR.defaultBlockState() : â˜ƒxxx;
      } else {
         try {
            int â˜ƒ = this.getSectionIndex(â˜ƒx);
            if (â˜ƒ >= 0 && â˜ƒ < this.sections.length) {
               LevelChunkSection â˜ƒx = this.sections[â˜ƒ];
               if (!LevelChunkSection.isEmpty(â˜ƒx)) {
                  return â˜ƒx.getBlockState(â˜ƒ & 15, â˜ƒx & 15, â˜ƒxx & 15);
               }
            }

            return Blocks.AIR.defaultBlockState();
         } catch (Throwable var8) {
            CrashReport â˜ƒ = CrashReport.forThrowable(var8, "Getting block state");
            CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Block being got");
            â˜ƒx.setDetail("Location", (CrashReportDetail<String>)(() -> CrashReportCategory.formatLocation(this, â˜ƒ, â˜ƒ, â˜ƒ)));
            throw new ReportedException(â˜ƒ);
         }
      }
   }

   @Override
   public FluidState getFluidState(BlockPos var1) {
      return this.getFluidState(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   public FluidState getFluidState(int var1, int var2, int var3) {
      try {
         int â˜ƒ = this.getSectionIndex(â˜ƒ);
         if (â˜ƒ >= 0 && â˜ƒ < this.sections.length) {
            LevelChunkSection â˜ƒx = this.sections[â˜ƒ];
            if (!LevelChunkSection.isEmpty(â˜ƒx)) {
               return â˜ƒx.getFluidState(â˜ƒ & 15, â˜ƒ & 15, â˜ƒ & 15);
            }
         }

         return Fluids.EMPTY.defaultFluidState();
      } catch (Throwable var7) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var7, "Getting fluid state");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Block being got");
         â˜ƒx.setDetail("Location", (CrashReportDetail<String>)(() -> CrashReportCategory.formatLocation(this, â˜ƒ, â˜ƒ, â˜ƒ)));
         throw new ReportedException(â˜ƒ);
      }
   }

   @Nullable
   @Override
   public BlockState setBlockState(BlockPos var1, BlockState var2, boolean var3) {
      int â˜ƒ = â˜ƒ.getY();
      int â˜ƒx = this.getSectionIndex(â˜ƒ);
      LevelChunkSection â˜ƒxx = this.sections[â˜ƒx];
      if (â˜ƒxx == EMPTY_SECTION) {
         if (â˜ƒ.isAir()) {
            return null;
         }

         â˜ƒxx = new LevelChunkSection(SectionPos.blockToSectionCoord(â˜ƒ));
         this.sections[â˜ƒx] = â˜ƒxx;
      }

      boolean â˜ƒ = â˜ƒxx.isEmpty();
      int â˜ƒx = â˜ƒ.getX() & 15;
      int â˜ƒxx = â˜ƒ & 15;
      int â˜ƒxxx = â˜ƒ.getZ() & 15;
      BlockState â˜ƒxxxx = â˜ƒxx.setBlockState(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ);
      if (â˜ƒxxxx == â˜ƒ) {
         return null;
      } else {
         Block â˜ƒ = â˜ƒ.getBlock();
         ((Heightmap)this.heightmaps.get(Heightmap.Types.MOTION_BLOCKING)).update(â˜ƒx, â˜ƒ, â˜ƒxxx, â˜ƒ);
         ((Heightmap)this.heightmaps.get(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)).update(â˜ƒx, â˜ƒ, â˜ƒxxx, â˜ƒ);
         ((Heightmap)this.heightmaps.get(Heightmap.Types.OCEAN_FLOOR)).update(â˜ƒx, â˜ƒ, â˜ƒxxx, â˜ƒ);
         ((Heightmap)this.heightmaps.get(Heightmap.Types.WORLD_SURFACE)).update(â˜ƒx, â˜ƒ, â˜ƒxxx, â˜ƒ);
         boolean â˜ƒx = â˜ƒxx.isEmpty();
         if (â˜ƒ != â˜ƒx) {
            this.level.getChunkSource().getLightEngine().updateSectionStatus(â˜ƒ, â˜ƒx);
         }

         boolean â˜ƒ = â˜ƒxxxx.hasBlockEntity();
         if (!this.level.isClientSide) {
            â˜ƒxxxx.onRemove(this.level, â˜ƒ, â˜ƒ, â˜ƒ);
         } else if (!â˜ƒxxxx.is(â˜ƒ) && â˜ƒ) {
            this.removeBlockEntity(â˜ƒ);
         }

         if (!â˜ƒxx.getBlockState(â˜ƒx, â˜ƒxx, â˜ƒxxx).is(â˜ƒ)) {
            return null;
         } else {
            if (!this.level.isClientSide) {
               â˜ƒ.onPlace(this.level, â˜ƒ, â˜ƒxxxx, â˜ƒ);
            }

            if (â˜ƒ.hasBlockEntity()) {
               BlockEntity â˜ƒ = this.getBlockEntity(â˜ƒ, LevelChunk.EntityCreationType.CHECK);
               if (â˜ƒ == null) {
                  â˜ƒ = ((EntityBlock)â˜ƒ).newBlockEntity(â˜ƒ, â˜ƒ);
                  if (â˜ƒ != null) {
                     this.addAndRegisterBlockEntity(â˜ƒ);
                  }
               } else {
                  â˜ƒ.setBlockState(â˜ƒ);
                  this.updateBlockEntityTicker(â˜ƒ);
               }
            }

            this.unsaved = true;
            return â˜ƒxxxx;
         }
      }
   }

   @Deprecated
   @Override
   public void addEntity(Entity var1) {
   }

   @Override
   public int getHeight(Heightmap.Types var1, int var2, int var3) {
      return ((Heightmap)this.heightmaps.get(â˜ƒ)).getFirstAvailable(â˜ƒ & 15, â˜ƒ & 15) - 1;
   }

   @Override
   public BlockPos getHeighestPosition(Heightmap.Types var1) {
      ChunkPos â˜ƒ = this.getPos();
      int â˜ƒx = this.getMinBuildHeight();
      BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxx = â˜ƒ.getMinBlockX(); â˜ƒxxx <= â˜ƒ.getMaxBlockX(); ++â˜ƒxxx) {
         for(int â˜ƒxxxx = â˜ƒ.getMinBlockZ(); â˜ƒxxxx <= â˜ƒ.getMaxBlockZ(); ++â˜ƒxxxx) {
            int â˜ƒxxxxx = this.getHeight(â˜ƒ, â˜ƒxxx & 15, â˜ƒxxxx & 15);
            if (â˜ƒxxxxx > â˜ƒx) {
               â˜ƒx = â˜ƒxxxxx;
               â˜ƒxx.set(â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxx);
            }
         }
      }

      return â˜ƒxx.immutable();
   }

   @Nullable
   private BlockEntity createBlockEntity(BlockPos var1) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ);
      return !â˜ƒ.hasBlockEntity() ? null : ((EntityBlock)â˜ƒ.getBlock()).newBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos var1) {
      return this.getBlockEntity(â˜ƒ, LevelChunk.EntityCreationType.CHECK);
   }

   @Nullable
   public BlockEntity getBlockEntity(BlockPos var1, LevelChunk.EntityCreationType var2) {
      BlockEntity â˜ƒ = (BlockEntity)this.blockEntities.get(â˜ƒ);
      if (â˜ƒ == null) {
         CompoundTag â˜ƒx = (CompoundTag)this.pendingBlockEntities.remove(â˜ƒ);
         if (â˜ƒx != null) {
            BlockEntity â˜ƒxx = this.promotePendingBlockEntity(â˜ƒ, â˜ƒx);
            if (â˜ƒxx != null) {
               return â˜ƒxx;
            }
         }
      }

      if (â˜ƒ == null) {
         if (â˜ƒ == LevelChunk.EntityCreationType.IMMEDIATE) {
            â˜ƒ = this.createBlockEntity(â˜ƒ);
            if (â˜ƒ != null) {
               this.addAndRegisterBlockEntity(â˜ƒ);
            }
         }
      } else if (â˜ƒ.isRemoved()) {
         this.blockEntities.remove(â˜ƒ);
         return null;
      }

      return â˜ƒ;
   }

   public void addAndRegisterBlockEntity(BlockEntity var1) {
      this.setBlockEntity(â˜ƒ);
      if (this.isInLevel()) {
         this.addGameEventListener(â˜ƒ);
         this.updateBlockEntityTicker(â˜ƒ);
      }
   }

   private boolean isInLevel() {
      return this.loaded || this.level.isClientSide();
   }

   boolean isTicking(BlockPos var1) {
      if (!this.level.getWorldBorder().isWithinBounds(â˜ƒ)) {
         return false;
      } else if (!(this.level instanceof ServerLevel)) {
         return true;
      } else {
         return this.getFullStatus().isOrAfter(ChunkHolder.FullChunkStatus.TICKING) && ((ServerLevel)this.level).areEntitiesLoaded(ChunkPos.asLong(â˜ƒ));
      }
   }

   @Override
   public void setBlockEntity(BlockEntity var1) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      if (this.getBlockState(â˜ƒ).hasBlockEntity()) {
         â˜ƒ.setLevel(this.level);
         â˜ƒ.clearRemoved();
         BlockEntity â˜ƒx = (BlockEntity)this.blockEntities.put(â˜ƒ.immutable(), â˜ƒ);
         if (â˜ƒx != null && â˜ƒx != â˜ƒ) {
            â˜ƒx.setRemoved();
         }
      }
   }

   @Override
   public void setBlockEntityNbt(CompoundTag var1) {
      this.pendingBlockEntities.put(new BlockPos(â˜ƒ.getInt("x"), â˜ƒ.getInt("y"), â˜ƒ.getInt("z")), â˜ƒ);
   }

   @Nullable
   @Override
   public CompoundTag getBlockEntityNbtForSaving(BlockPos var1) {
      BlockEntity â˜ƒ = this.getBlockEntity(â˜ƒ);
      if (â˜ƒ != null && !â˜ƒ.isRemoved()) {
         CompoundTag â˜ƒx = â˜ƒ.save(new CompoundTag());
         â˜ƒx.putBoolean("keepPacked", false);
         return â˜ƒx;
      } else {
         CompoundTag â˜ƒ = (CompoundTag)this.pendingBlockEntities.get(â˜ƒ);
         if (â˜ƒ != null) {
            â˜ƒ = â˜ƒ.copy();
            â˜ƒ.putBoolean("keepPacked", true);
         }

         return â˜ƒ;
      }
   }

   @Override
   public void removeBlockEntity(BlockPos var1) {
      if (this.isInLevel()) {
         BlockEntity â˜ƒ = (BlockEntity)this.blockEntities.remove(â˜ƒ);
         if (â˜ƒ != null) {
            this.removeGameEventListener(â˜ƒ);
            â˜ƒ.setRemoved();
         }
      }

      this.removeBlockEntityTicker(â˜ƒ);
   }

   private <T extends BlockEntity> void removeGameEventListener(T var1) {
      if (!this.level.isClientSide) {
         Block â˜ƒ = â˜ƒ.getBlockState().getBlock();
         if (â˜ƒ instanceof EntityBlock) {
            GameEventListener â˜ƒx = ((EntityBlock)â˜ƒ).getListener(this.level, â˜ƒ);
            if (â˜ƒx != null) {
               int â˜ƒxx = SectionPos.blockToSectionCoord(â˜ƒ.getBlockPos().getY());
               GameEventDispatcher â˜ƒxxx = this.getEventDispatcher(â˜ƒxx);
               â˜ƒxxx.unregister(â˜ƒx);
               if (â˜ƒxxx.isEmpty()) {
                  this.gameEventDispatcherSections.remove(â˜ƒxx);
               }
            }
         }
      }
   }

   private void removeBlockEntityTicker(BlockPos var1) {
      LevelChunk.RebindableTickingBlockEntityWrapper â˜ƒ = (LevelChunk.RebindableTickingBlockEntityWrapper)this.tickersInLevel.remove(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.rebind(NULL_TICKER);
      }
   }

   public void runPostLoad() {
      if (this.postLoad != null) {
         this.postLoad.accept(this);
         this.postLoad = null;
      }
   }

   public void markUnsaved() {
      this.unsaved = true;
   }

   public boolean isEmpty() {
      return false;
   }

   @Override
   public ChunkPos getPos() {
      return this.chunkPos;
   }

   public void replaceWithPacketData(@Nullable ChunkBiomeContainer var1, FriendlyByteBuf var2, CompoundTag var3, BitSet var4) {
      boolean â˜ƒ = â˜ƒ != null;
      if (â˜ƒ) {
         this.blockEntities.values().forEach(this::onBlockEntityRemove);
         this.blockEntities.clear();
      } else {
         this.blockEntities.values().removeIf(var2x -> {
            int â˜ƒ = this.getSectionIndex(var2x.getBlockPos().getY());
            if (â˜ƒ.get(â˜ƒ)) {
               var2x.setRemoved();
               return true;
            } else {
               return false;
            }
         });
      }

      for(int â˜ƒ = 0; â˜ƒ < this.sections.length; ++â˜ƒ) {
         LevelChunkSection â˜ƒx = this.sections[â˜ƒ];
         if (!â˜ƒ.get(â˜ƒ)) {
            if (â˜ƒ && â˜ƒx != EMPTY_SECTION) {
               this.sections[â˜ƒ] = EMPTY_SECTION;
            }
         } else {
            if (â˜ƒx == EMPTY_SECTION) {
               â˜ƒx = new LevelChunkSection(this.getSectionYFromSectionIndex(â˜ƒ));
               this.sections[â˜ƒ] = â˜ƒx;
            }

            â˜ƒx.read(â˜ƒ);
         }
      }

      if (â˜ƒ != null) {
         this.biomes = â˜ƒ;
      }

      for(Heightmap.Types â˜ƒ : Heightmap.Types.values()) {
         String â˜ƒx = â˜ƒ.getSerializationKey();
         if (â˜ƒ.contains(â˜ƒx, 12)) {
            this.setHeightmap(â˜ƒ, â˜ƒ.getLongArray(â˜ƒx));
         }
      }
   }

   private void onBlockEntityRemove(BlockEntity var1) {
      â˜ƒ.setRemoved();
      this.tickersInLevel.remove(â˜ƒ.getBlockPos());
   }

   @Override
   public ChunkBiomeContainer getBiomes() {
      return this.biomes;
   }

   public void setLoaded(boolean var1) {
      this.loaded = â˜ƒ;
   }

   public Level getLevel() {
      return this.level;
   }

   @Override
   public Collection<Entry<Heightmap.Types, Heightmap>> getHeightmaps() {
      return Collections.unmodifiableSet(this.heightmaps.entrySet());
   }

   public Map<BlockPos, BlockEntity> getBlockEntities() {
      return this.blockEntities;
   }

   @Override
   public CompoundTag getBlockEntityNbt(BlockPos var1) {
      return (CompoundTag)this.pendingBlockEntities.get(â˜ƒ);
   }

   @Override
   public Stream<BlockPos> getLights() {
      return StreamSupport.stream(
            BlockPos.betweenClosed(
                  this.chunkPos.getMinBlockX(),
                  this.getMinBuildHeight(),
                  this.chunkPos.getMinBlockZ(),
                  this.chunkPos.getMaxBlockX(),
                  this.getMaxBuildHeight() - 1,
                  this.chunkPos.getMaxBlockZ()
               )
               .spliterator(),
            false
         )
         .filter(var1 -> this.getBlockState(var1).getLightEmission() != 0);
   }

   @Override
   public TickList<Block> getBlockTicks() {
      return this.blockTicks;
   }

   @Override
   public TickList<Fluid> getLiquidTicks() {
      return this.liquidTicks;
   }

   @Override
   public void setUnsaved(boolean var1) {
      this.unsaved = â˜ƒ;
   }

   @Override
   public boolean isUnsaved() {
      return this.unsaved;
   }

   @Nullable
   @Override
   public StructureStart<?> getStartForFeature(StructureFeature<?> var1) {
      return (StructureStart<?>)this.structureStarts.get(â˜ƒ);
   }

   @Override
   public void setStartForFeature(StructureFeature<?> var1, StructureStart<?> var2) {
      this.structureStarts.put(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<StructureFeature<?>, StructureStart<?>> getAllStarts() {
      return this.structureStarts;
   }

   @Override
   public void setAllStarts(Map<StructureFeature<?>, StructureStart<?>> var1) {
      this.structureStarts.clear();
      this.structureStarts.putAll(â˜ƒ);
   }

   @Override
   public LongSet getReferencesForFeature(StructureFeature<?> var1) {
      return (LongSet)this.structuresRefences.computeIfAbsent(â˜ƒ, var0 -> new LongOpenHashSet());
   }

   @Override
   public void addReferenceForFeature(StructureFeature<?> var1, long var2) {
      ((LongSet)this.structuresRefences.computeIfAbsent(â˜ƒ, var0 -> new LongOpenHashSet())).add(â˜ƒ);
   }

   @Override
   public Map<StructureFeature<?>, LongSet> getAllReferences() {
      return this.structuresRefences;
   }

   @Override
   public void setAllReferences(Map<StructureFeature<?>, LongSet> var1) {
      this.structuresRefences.clear();
      this.structuresRefences.putAll(â˜ƒ);
   }

   @Override
   public long getInhabitedTime() {
      return this.inhabitedTime;
   }

   @Override
   public void setInhabitedTime(long var1) {
      this.inhabitedTime = â˜ƒ;
   }

   public void postProcessGeneration() {
      ChunkPos â˜ƒ = this.getPos();

      for(int â˜ƒx = 0; â˜ƒx < this.postProcessing.length; ++â˜ƒx) {
         if (this.postProcessing[â˜ƒx] != null) {
            for(Short â˜ƒxx : this.postProcessing[â˜ƒx]) {
               BlockPos â˜ƒxxx = ProtoChunk.unpackOffsetCoordinates(â˜ƒxx, this.getSectionYFromSectionIndex(â˜ƒx), â˜ƒ);
               BlockState â˜ƒxxxx = this.getBlockState(â˜ƒxxx);
               BlockState â˜ƒxxxxx = Block.updateFromNeighbourShapes(â˜ƒxxxx, this.level, â˜ƒxxx);
               this.level.setBlock(â˜ƒxxx, â˜ƒxxxxx, 20);
            }

            this.postProcessing[â˜ƒx].clear();
         }
      }

      this.unpackTicks();

      for(BlockPos â˜ƒx : ImmutableList.copyOf(this.pendingBlockEntities.keySet())) {
         this.getBlockEntity(â˜ƒx);
      }

      this.pendingBlockEntities.clear();
      this.upgradeData.upgrade(this);
   }

   @Nullable
   private BlockEntity promotePendingBlockEntity(BlockPos var1, CompoundTag var2) {
      BlockState â˜ƒx = this.getBlockState(â˜ƒ);
      BlockEntity â˜ƒ;
      if ("DUMMY".equals(â˜ƒ.getString("id"))) {
         if (â˜ƒx.hasBlockEntity()) {
            â˜ƒ = ((EntityBlock)â˜ƒx.getBlock()).newBlockEntity(â˜ƒ, â˜ƒx);
         } else {
            â˜ƒ = null;
            LOGGER.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", â˜ƒ, â˜ƒx);
         }
      } else {
         â˜ƒ = BlockEntity.loadStatic(â˜ƒ, â˜ƒx, â˜ƒ);
      }

      if (â˜ƒ != null) {
         â˜ƒ.setLevel(this.level);
         this.addAndRegisterBlockEntity(â˜ƒ);
      } else {
         LOGGER.warn("Tried to load a block entity for block {} but failed at location {}", â˜ƒx, â˜ƒ);
      }

      return â˜ƒ;
   }

   @Override
   public UpgradeData getUpgradeData() {
      return this.upgradeData;
   }

   @Override
   public ShortList[] getPostProcessing() {
      return this.postProcessing;
   }

   public void unpackTicks() {
      if (this.blockTicks instanceof ProtoTickList) {
         ((ProtoTickList)this.blockTicks).copyOut(this.level.getBlockTicks(), var1 -> this.getBlockState(var1).getBlock());
         this.blockTicks = EmptyTickList.empty();
      } else if (this.blockTicks instanceof ChunkTickList) {
         ((ChunkTickList)this.blockTicks).copyOut(this.level.getBlockTicks());
         this.blockTicks = EmptyTickList.empty();
      }

      if (this.liquidTicks instanceof ProtoTickList) {
         ((ProtoTickList)this.liquidTicks).copyOut(this.level.getLiquidTicks(), var1 -> this.getFluidState(var1).getType());
         this.liquidTicks = EmptyTickList.empty();
      } else if (this.liquidTicks instanceof ChunkTickList) {
         ((ChunkTickList)this.liquidTicks).copyOut(this.level.getLiquidTicks());
         this.liquidTicks = EmptyTickList.empty();
      }
   }

   public void packTicks(ServerLevel var1) {
      if (this.blockTicks == EmptyTickList.empty()) {
         this.blockTicks = new ChunkTickList<>(Registry.BLOCK::getKey, â˜ƒ.getBlockTicks().fetchTicksInChunk(this.chunkPos, true, false), â˜ƒ.getGameTime());
         this.setUnsaved(true);
      }

      if (this.liquidTicks == EmptyTickList.empty()) {
         this.liquidTicks = new ChunkTickList<>(Registry.FLUID::getKey, â˜ƒ.getLiquidTicks().fetchTicksInChunk(this.chunkPos, true, false), â˜ƒ.getGameTime());
         this.setUnsaved(true);
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

   @Override
   public ChunkStatus getStatus() {
      return ChunkStatus.FULL;
   }

   public ChunkHolder.FullChunkStatus getFullStatus() {
      return this.fullStatus == null ? ChunkHolder.FullChunkStatus.BORDER : (ChunkHolder.FullChunkStatus)this.fullStatus.get();
   }

   public void setFullStatus(Supplier<ChunkHolder.FullChunkStatus> var1) {
      this.fullStatus = â˜ƒ;
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

   public void invalidateAllBlockEntities() {
      this.blockEntities.values().forEach(this::onBlockEntityRemove);
   }

   public void registerAllBlockEntitiesAfterLevelLoad() {
      this.blockEntities.values().forEach(var1 -> {
         this.addGameEventListener(var1);
         this.updateBlockEntityTicker(var1);
      });
   }

   private <T extends BlockEntity> void addGameEventListener(T var1) {
      if (!this.level.isClientSide) {
         Block â˜ƒ = â˜ƒ.getBlockState().getBlock();
         if (â˜ƒ instanceof EntityBlock) {
            GameEventListener â˜ƒx = ((EntityBlock)â˜ƒ).getListener(this.level, â˜ƒ);
            if (â˜ƒx != null) {
               GameEventDispatcher â˜ƒxx = this.getEventDispatcher(SectionPos.blockToSectionCoord(â˜ƒ.getBlockPos().getY()));
               â˜ƒxx.register(â˜ƒx);
            }
         }
      }
   }

   private <T extends BlockEntity> void updateBlockEntityTicker(T var1) {
      BlockState â˜ƒ = â˜ƒ.getBlockState();
      BlockEntityTicker<T> â˜ƒx = â˜ƒ.getTicker(this.level, â˜ƒ.getType());
      if (â˜ƒx == null) {
         this.removeBlockEntityTicker(â˜ƒ.getBlockPos());
      } else {
         this.tickersInLevel.compute(â˜ƒ.getBlockPos(), (var3x, var4) -> {
            TickingBlockEntity â˜ƒ = this.createTicker(â˜ƒ, â˜ƒ);
            if (var4 != null) {
               var4.rebind(â˜ƒ);
               return var4;
            } else if (this.isInLevel()) {
               LevelChunk.RebindableTickingBlockEntityWrapper â˜ƒ = new LevelChunk.RebindableTickingBlockEntityWrapper(â˜ƒ);
               this.level.addBlockEntityTicker(â˜ƒ);
               return â˜ƒ;
            } else {
               return null;
            }
         });
      }
   }

   private <T extends BlockEntity> TickingBlockEntity createTicker(T var1, BlockEntityTicker<T> var2) {
      return new LevelChunk.BoundTickingBlockEntity<>(â˜ƒ, â˜ƒ);
   }

   class BoundTickingBlockEntity<T extends BlockEntity> implements TickingBlockEntity {
      private final T blockEntity;
      private final BlockEntityTicker<T> ticker;
      private boolean loggedInvalidBlockState;

      BoundTickingBlockEntity(T var2, BlockEntityTicker<T> var3) {
         this.blockEntity = â˜ƒ;
         this.ticker = â˜ƒ;
      }

      @Override
      public void tick() {
         if (!this.blockEntity.isRemoved() && this.blockEntity.hasLevel()) {
            BlockPos â˜ƒ = this.blockEntity.getBlockPos();
            if (LevelChunk.this.isTicking(â˜ƒ)) {
               try {
                  ProfilerFiller â˜ƒx = LevelChunk.this.level.getProfiler();
                  â˜ƒx.push(this::getType);
                  BlockState â˜ƒxx = LevelChunk.this.getBlockState(â˜ƒ);
                  if (this.blockEntity.getType().isValid(â˜ƒxx)) {
                     this.ticker.tick(LevelChunk.this.level, this.blockEntity.getBlockPos(), â˜ƒxx, this.blockEntity);
                     this.loggedInvalidBlockState = false;
                  } else if (!this.loggedInvalidBlockState) {
                     this.loggedInvalidBlockState = true;
                     LevelChunk.LOGGER.warn("Block entity {} @ {} state {} invalid for ticking:", this::getType, this::getPos, () -> â˜ƒ);
                  }

                  â˜ƒx.pop();
               } catch (Throwable var5) {
                  CrashReport â˜ƒx = CrashReport.forThrowable(var5, "Ticking block entity");
                  CrashReportCategory â˜ƒxx = â˜ƒx.addCategory("Block entity being ticked");
                  this.blockEntity.fillCrashReportCategory(â˜ƒxx);
                  throw new ReportedException(â˜ƒx);
               }
            }
         }
      }

      @Override
      public boolean isRemoved() {
         return this.blockEntity.isRemoved();
      }

      @Override
      public BlockPos getPos() {
         return this.blockEntity.getBlockPos();
      }

      @Override
      public String getType() {
         return BlockEntityType.getKey(this.blockEntity.getType()).toString();
      }

      public String toString() {
         return "Level ticker for " + this.getType() + "@" + this.getPos();
      }
   }

   public static enum EntityCreationType {
      IMMEDIATE,
      QUEUED,
      CHECK;
   }

   class RebindableTickingBlockEntityWrapper implements TickingBlockEntity {
      private TickingBlockEntity ticker;

      RebindableTickingBlockEntityWrapper(TickingBlockEntity var2) {
         this.ticker = â˜ƒ;
      }

      void rebind(TickingBlockEntity var1) {
         this.ticker = â˜ƒ;
      }

      @Override
      public void tick() {
         this.ticker.tick();
      }

      @Override
      public boolean isRemoved() {
         return this.ticker.isRemoved();
      }

      @Override
      public BlockPos getPos() {
         return this.ticker.getPos();
      }

      @Override
      public String getType() {
         return this.ticker.getType();
      }

      public String toString() {
         return this.ticker.toString() + " <wrapped>";
      }
   }
}
