package net.minecraft.world.level;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.Scoreboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Level implements LevelAccessor, AutoCloseable {
   protected static final Logger LOGGER = LogManager.getLogger();
   public static final Codec<ResourceKey<Level>> RESOURCE_KEY_CODEC = ResourceLocation.CODEC
      .xmap(ResourceKey.elementKey(Registry.DIMENSION_REGISTRY), ResourceKey::location);
   public static final ResourceKey<Level> OVERWORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("overworld"));
   public static final ResourceKey<Level> NETHER = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("the_nether"));
   public static final ResourceKey<Level> END = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("the_end"));
   public static final int MAX_LEVEL_SIZE = 30000000;
   public static final int LONG_PARTICLE_CLIP_RANGE = 512;
   public static final int SHORT_PARTICLE_CLIP_RANGE = 32;
   private static final Direction[] DIRECTIONS = Direction.values();
   public static final int MAX_BRIGHTNESS = 15;
   public static final int TICKS_PER_DAY = 24000;
   public static final int MAX_ENTITY_SPAWN_Y = 20000000;
   public static final int MIN_ENTITY_SPAWN_Y = -20000000;
   protected final List<TickingBlockEntity> blockEntityTickers = Lists.<TickingBlockEntity>newArrayList();
   private final List<TickingBlockEntity> pendingBlockEntityTickers = Lists.<TickingBlockEntity>newArrayList();
   private boolean tickingBlockEntities;
   private final Thread thread;
   private final boolean isDebug;
   private int skyDarken;
   protected int randValue = new Random().nextInt();
   protected final int addend = 1013904223;
   protected float oRainLevel;
   protected float rainLevel;
   protected float oThunderLevel;
   protected float thunderLevel;
   public final Random random = new Random();
   private final DimensionType dimensionType;
   protected final WritableLevelData levelData;
   private final Supplier<ProfilerFiller> profiler;
   public final boolean isClientSide;
   private final WorldBorder worldBorder;
   private final BiomeManager biomeManager;
   private final ResourceKey<Level> dimension;

   protected Level(
      WritableLevelData var1, ResourceKey<Level> var2, final DimensionType var3, Supplier<ProfilerFiller> var4, boolean var5, boolean var6, long var7
   ) {
      this.profiler = â˜ƒ;
      this.levelData = â˜ƒ;
      this.dimensionType = â˜ƒ;
      this.dimension = â˜ƒ;
      this.isClientSide = â˜ƒ;
      if (â˜ƒ.coordinateScale() != 1.0) {
         this.worldBorder = new WorldBorder() {
            @Override
            public double getCenterX() {
               return super.getCenterX() / â˜ƒ.coordinateScale();
            }

            @Override
            public double getCenterZ() {
               return super.getCenterZ() / â˜ƒ.coordinateScale();
            }
         };
      } else {
         this.worldBorder = new WorldBorder();
      }

      this.thread = Thread.currentThread();
      this.biomeManager = new BiomeManager(this, â˜ƒ, â˜ƒ.getBiomeZoomer());
      this.isDebug = â˜ƒ;
   }

   @Override
   public boolean isClientSide() {
      return this.isClientSide;
   }

   @Nullable
   @Override
   public MinecraftServer getServer() {
      return null;
   }

   public boolean isInWorldBounds(BlockPos var1) {
      return !this.isOutsideBuildHeight(â˜ƒ) && isInWorldBoundsHorizontal(â˜ƒ);
   }

   public static boolean isInSpawnableBounds(BlockPos var0) {
      return !isOutsideSpawnableHeight(â˜ƒ.getY()) && isInWorldBoundsHorizontal(â˜ƒ);
   }

   private static boolean isInWorldBoundsHorizontal(BlockPos var0) {
      return â˜ƒ.getX() >= -30000000 && â˜ƒ.getZ() >= -30000000 && â˜ƒ.getX() < 30000000 && â˜ƒ.getZ() < 30000000;
   }

   private static boolean isOutsideSpawnableHeight(int var0) {
      return â˜ƒ < -20000000 || â˜ƒ >= 20000000;
   }

   public LevelChunk getChunkAt(BlockPos var1) {
      return this.getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()));
   }

   public LevelChunk getChunk(int var1, int var2) {
      return (LevelChunk)this.getChunk(â˜ƒ, â˜ƒ, ChunkStatus.FULL);
   }

   @Nullable
   @Override
   public ChunkAccess getChunk(int var1, int var2, ChunkStatus var3, boolean var4) {
      ChunkAccess â˜ƒ = this.getChunkSource().getChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ == null && â˜ƒ) {
         throw new IllegalStateException("Should always be able to create a chunk!");
      } else {
         return â˜ƒ;
      }
   }

   @Override
   public boolean setBlock(BlockPos var1, BlockState var2, int var3) {
      return this.setBlock(â˜ƒ, â˜ƒ, â˜ƒ, 512);
   }

   @Override
   public boolean setBlock(BlockPos var1, BlockState var2, int var3, int var4) {
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return false;
      } else if (!this.isClientSide && this.isDebug()) {
         return false;
      } else {
         LevelChunk â˜ƒ = this.getChunkAt(â˜ƒ);
         Block â˜ƒx = â˜ƒ.getBlock();
         BlockState â˜ƒxx = â˜ƒ.setBlockState(â˜ƒ, â˜ƒ, (â˜ƒ & 64) != 0);
         if (â˜ƒxx == null) {
            return false;
         } else {
            BlockState â˜ƒ = this.getBlockState(â˜ƒ);
            if ((â˜ƒ & 128) == 0
               && â˜ƒ != â˜ƒxx
               && (
                  â˜ƒ.getLightBlock(this, â˜ƒ) != â˜ƒxx.getLightBlock(this, â˜ƒ)
                     || â˜ƒ.getLightEmission() != â˜ƒxx.getLightEmission()
                     || â˜ƒ.useShapeForLightOcclusion()
                     || â˜ƒxx.useShapeForLightOcclusion()
               )) {
               this.getProfiler().push("queueCheckLight");
               this.getChunkSource().getLightEngine().checkBlock(â˜ƒ);
               this.getProfiler().pop();
            }

            if (â˜ƒ == â˜ƒ) {
               if (â˜ƒxx != â˜ƒ) {
                  this.setBlocksDirty(â˜ƒ, â˜ƒxx, â˜ƒ);
               }

               if ((â˜ƒ & 2) != 0
                  && (!this.isClientSide || (â˜ƒ & 4) == 0)
                  && (this.isClientSide || â˜ƒ.getFullStatus() != null && â˜ƒ.getFullStatus().isOrAfter(ChunkHolder.FullChunkStatus.TICKING))) {
                  this.sendBlockUpdated(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ);
               }

               if ((â˜ƒ & 1) != 0) {
                  this.blockUpdated(â˜ƒ, â˜ƒxx.getBlock());
                  if (!this.isClientSide && â˜ƒ.hasAnalogOutputSignal()) {
                     this.updateNeighbourForOutputSignal(â˜ƒ, â˜ƒx);
                  }
               }

               if ((â˜ƒ & 16) == 0 && â˜ƒ > 0) {
                  int â˜ƒ = â˜ƒ & -34;
                  â˜ƒxx.updateIndirectNeighbourShapes(this, â˜ƒ, â˜ƒ, â˜ƒ - 1);
                  â˜ƒ.updateNeighbourShapes(this, â˜ƒ, â˜ƒ, â˜ƒ - 1);
                  â˜ƒ.updateIndirectNeighbourShapes(this, â˜ƒ, â˜ƒ, â˜ƒ - 1);
               }

               this.onBlockStateChange(â˜ƒ, â˜ƒxx, â˜ƒ);
            }

            return true;
         }
      }
   }

   public void onBlockStateChange(BlockPos var1, BlockState var2, BlockState var3) {
   }

   @Override
   public boolean removeBlock(BlockPos var1, boolean var2) {
      FluidState â˜ƒ = this.getFluidState(â˜ƒ);
      return this.setBlock(â˜ƒ, â˜ƒ.createLegacyBlock(), 3 | (â˜ƒ ? 64 : 0));
   }

   @Override
   public boolean destroyBlock(BlockPos var1, boolean var2, @Nullable Entity var3, int var4) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ);
      if (â˜ƒ.isAir()) {
         return false;
      } else {
         FluidState â˜ƒ = this.getFluidState(â˜ƒ);
         if (!(â˜ƒ.getBlock() instanceof BaseFireBlock)) {
            this.levelEvent(2001, â˜ƒ, Block.getId(â˜ƒ));
         }

         if (â˜ƒ) {
            BlockEntity â˜ƒ = â˜ƒ.hasBlockEntity() ? this.getBlockEntity(â˜ƒ) : null;
            Block.dropResources(â˜ƒ, this, â˜ƒ, â˜ƒ, â˜ƒ, ItemStack.EMPTY);
         }

         boolean â˜ƒ = this.setBlock(â˜ƒ, â˜ƒ.createLegacyBlock(), 3, â˜ƒ);
         if (â˜ƒ) {
            this.gameEvent(â˜ƒ, GameEvent.BLOCK_DESTROY, â˜ƒ);
         }

         return â˜ƒ;
      }
   }

   public void addDestroyBlockEffect(BlockPos var1, BlockState var2) {
   }

   public boolean setBlockAndUpdate(BlockPos var1, BlockState var2) {
      return this.setBlock(â˜ƒ, â˜ƒ, 3);
   }

   public abstract void sendBlockUpdated(BlockPos var1, BlockState var2, BlockState var3, int var4);

   public void setBlocksDirty(BlockPos var1, BlockState var2, BlockState var3) {
   }

   public void updateNeighborsAt(BlockPos var1, Block var2) {
      this.neighborChanged(â˜ƒ.west(), â˜ƒ, â˜ƒ);
      this.neighborChanged(â˜ƒ.east(), â˜ƒ, â˜ƒ);
      this.neighborChanged(â˜ƒ.below(), â˜ƒ, â˜ƒ);
      this.neighborChanged(â˜ƒ.above(), â˜ƒ, â˜ƒ);
      this.neighborChanged(â˜ƒ.north(), â˜ƒ, â˜ƒ);
      this.neighborChanged(â˜ƒ.south(), â˜ƒ, â˜ƒ);
   }

   public void updateNeighborsAtExceptFromFacing(BlockPos var1, Block var2, Direction var3) {
      if (â˜ƒ != Direction.WEST) {
         this.neighborChanged(â˜ƒ.west(), â˜ƒ, â˜ƒ);
      }

      if (â˜ƒ != Direction.EAST) {
         this.neighborChanged(â˜ƒ.east(), â˜ƒ, â˜ƒ);
      }

      if (â˜ƒ != Direction.DOWN) {
         this.neighborChanged(â˜ƒ.below(), â˜ƒ, â˜ƒ);
      }

      if (â˜ƒ != Direction.UP) {
         this.neighborChanged(â˜ƒ.above(), â˜ƒ, â˜ƒ);
      }

      if (â˜ƒ != Direction.NORTH) {
         this.neighborChanged(â˜ƒ.north(), â˜ƒ, â˜ƒ);
      }

      if (â˜ƒ != Direction.SOUTH) {
         this.neighborChanged(â˜ƒ.south(), â˜ƒ, â˜ƒ);
      }
   }

   public void neighborChanged(BlockPos var1, Block var2, BlockPos var3) {
      if (!this.isClientSide) {
         BlockState â˜ƒ = this.getBlockState(â˜ƒ);

         try {
            â˜ƒ.neighborChanged(this, â˜ƒ, â˜ƒ, â˜ƒ, false);
         } catch (Throwable var8) {
            CrashReport â˜ƒx = CrashReport.forThrowable(var8, "Exception while updating neighbours");
            CrashReportCategory â˜ƒxx = â˜ƒx.addCategory("Block being updated");
            â˜ƒxx.setDetail("Source block type", (CrashReportDetail<String>)(() -> {
               try {
                  return String.format("ID #%s (%s // %s)", Registry.BLOCK.getKey(â˜ƒ), â˜ƒ.getDescriptionId(), â˜ƒ.getClass().getCanonicalName());
               } catch (Throwable var2xx) {
                  return "ID #" + Registry.BLOCK.getKey(â˜ƒ);
               }
            }));
            CrashReportCategory.populateBlockDetails(â˜ƒxx, this, â˜ƒ, â˜ƒ);
            throw new ReportedException(â˜ƒx);
         }
      }
   }

   @Override
   public int getHeight(Heightmap.Types var1, int var2, int var3) {
      int â˜ƒ;
      if (â˜ƒ >= -30000000 && â˜ƒ >= -30000000 && â˜ƒ < 30000000 && â˜ƒ < 30000000) {
         if (this.hasChunk(SectionPos.blockToSectionCoord(â˜ƒ), SectionPos.blockToSectionCoord(â˜ƒ))) {
            â˜ƒ = this.getChunk(SectionPos.blockToSectionCoord(â˜ƒ), SectionPos.blockToSectionCoord(â˜ƒ)).getHeight(â˜ƒ, â˜ƒ & 15, â˜ƒ & 15) + 1;
         } else {
            â˜ƒ = this.getMinBuildHeight();
         }
      } else {
         â˜ƒ = this.getSeaLevel() + 1;
      }

      return â˜ƒ;
   }

   @Override
   public LevelLightEngine getLightEngine() {
      return this.getChunkSource().getLightEngine();
   }

   @Override
   public BlockState getBlockState(BlockPos var1) {
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return Blocks.VOID_AIR.defaultBlockState();
      } else {
         LevelChunk â˜ƒ = this.getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()));
         return â˜ƒ.getBlockState(â˜ƒ);
      }
   }

   @Override
   public FluidState getFluidState(BlockPos var1) {
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return Fluids.EMPTY.defaultFluidState();
      } else {
         LevelChunk â˜ƒ = this.getChunkAt(â˜ƒ);
         return â˜ƒ.getFluidState(â˜ƒ);
      }
   }

   public boolean isDay() {
      return !this.dimensionType().hasFixedTime() && this.skyDarken < 4;
   }

   public boolean isNight() {
      return !this.dimensionType().hasFixedTime() && !this.isDay();
   }

   @Override
   public void playSound(@Nullable Player var1, BlockPos var2, SoundEvent var3, SoundSource var4, float var5, float var6) {
      this.playSound(â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public abstract void playSound(@Nullable Player var1, double var2, double var4, double var6, SoundEvent var8, SoundSource var9, float var10, float var11);

   public abstract void playSound(@Nullable Player var1, Entity var2, SoundEvent var3, SoundSource var4, float var5, float var6);

   public void playLocalSound(double var1, double var3, double var5, SoundEvent var7, SoundSource var8, float var9, float var10, boolean var11) {
   }

   @Override
   public void addParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12) {
   }

   public void addParticle(ParticleOptions var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
   }

   public void addAlwaysVisibleParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12) {
   }

   public void addAlwaysVisibleParticle(ParticleOptions var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
   }

   public float getSunAngle(float var1) {
      float â˜ƒ = this.getTimeOfDay(â˜ƒ);
      return â˜ƒ * (float) (Math.PI * 2);
   }

   public void addBlockEntityTicker(TickingBlockEntity var1) {
      (this.tickingBlockEntities ? this.pendingBlockEntityTickers : this.blockEntityTickers).add(â˜ƒ);
   }

   protected void tickBlockEntities() {
      ProfilerFiller â˜ƒ = this.getProfiler();
      â˜ƒ.push("blockEntities");
      this.tickingBlockEntities = true;
      if (!this.pendingBlockEntityTickers.isEmpty()) {
         this.blockEntityTickers.addAll(this.pendingBlockEntityTickers);
         this.pendingBlockEntityTickers.clear();
      }

      Iterator<TickingBlockEntity> â˜ƒ = this.blockEntityTickers.iterator();

      while(â˜ƒ.hasNext()) {
         TickingBlockEntity â˜ƒx = (TickingBlockEntity)â˜ƒ.next();
         if (â˜ƒx.isRemoved()) {
            â˜ƒ.remove();
         } else {
            â˜ƒx.tick();
         }
      }

      this.tickingBlockEntities = false;
      â˜ƒ.pop();
   }

   public <T extends Entity> void guardEntityTick(Consumer<T> var1, T var2) {
      try {
         â˜ƒ.accept(â˜ƒ);
      } catch (Throwable var6) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var6, "Ticking entity");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Entity being ticked");
         â˜ƒ.fillCrashReportCategory(â˜ƒx);
         throw new ReportedException(â˜ƒ);
      }
   }

   public Explosion explode(@Nullable Entity var1, double var2, double var4, double var6, float var8, Explosion.BlockInteraction var9) {
      return this.explode(â˜ƒ, null, null, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ);
   }

   public Explosion explode(@Nullable Entity var1, double var2, double var4, double var6, float var8, boolean var9, Explosion.BlockInteraction var10) {
      return this.explode(â˜ƒ, null, null, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Explosion explode(
      @Nullable Entity var1,
      @Nullable DamageSource var2,
      @Nullable ExplosionDamageCalculator var3,
      double var4,
      double var6,
      double var8,
      float var10,
      boolean var11,
      Explosion.BlockInteraction var12
   ) {
      Explosion â˜ƒ = new Explosion(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.explode();
      â˜ƒ.finalizeExplosion(true);
      return â˜ƒ;
   }

   public abstract String gatherChunkSourceStats();

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos var1) {
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return null;
      } else {
         return !this.isClientSide && Thread.currentThread() != this.thread
            ? null
            : this.getChunkAt(â˜ƒ).getBlockEntity(â˜ƒ, LevelChunk.EntityCreationType.IMMEDIATE);
      }
   }

   public void setBlockEntity(BlockEntity var1) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      if (!this.isOutsideBuildHeight(â˜ƒ)) {
         this.getChunkAt(â˜ƒ).addAndRegisterBlockEntity(â˜ƒ);
      }
   }

   public void removeBlockEntity(BlockPos var1) {
      if (!this.isOutsideBuildHeight(â˜ƒ)) {
         this.getChunkAt(â˜ƒ).removeBlockEntity(â˜ƒ);
      }
   }

   public boolean isLoaded(BlockPos var1) {
      return this.isOutsideBuildHeight(â˜ƒ)
         ? false
         : this.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()));
   }

   public boolean loadedAndEntityCanStandOnFace(BlockPos var1, Entity var2, Direction var3) {
      if (this.isOutsideBuildHeight(â˜ƒ)) {
         return false;
      } else {
         ChunkAccess â˜ƒ = this.getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()), ChunkStatus.FULL, false);
         return â˜ƒ == null ? false : â˜ƒ.getBlockState(â˜ƒ).entityCanStandOnFace(this, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public boolean loadedAndEntityCanStandOn(BlockPos var1, Entity var2) {
      return this.loadedAndEntityCanStandOnFace(â˜ƒ, â˜ƒ, Direction.UP);
   }

   public void updateSkyBrightness() {
      double â˜ƒ = 1.0 - (double)(this.getRainLevel(1.0F) * 5.0F) / 16.0;
      double â˜ƒx = 1.0 - (double)(this.getThunderLevel(1.0F) * 5.0F) / 16.0;
      double â˜ƒxx = 0.5 + 2.0 * Mth.clamp((double)Mth.cos(this.getTimeOfDay(1.0F) * (float) (Math.PI * 2)), -0.25, 0.25);
      this.skyDarken = (int)((1.0 - â˜ƒxx * â˜ƒ * â˜ƒx) * 11.0);
   }

   public void setSpawnSettings(boolean var1, boolean var2) {
      this.getChunkSource().setSpawnSettings(â˜ƒ, â˜ƒ);
   }

   protected void prepareWeather() {
      if (this.levelData.isRaining()) {
         this.rainLevel = 1.0F;
         if (this.levelData.isThundering()) {
            this.thunderLevel = 1.0F;
         }
      }
   }

   public void close() throws IOException {
      this.getChunkSource().close();
   }

   @Nullable
   @Override
   public BlockGetter getChunkForCollisions(int var1, int var2) {
      return this.getChunk(â˜ƒ, â˜ƒ, ChunkStatus.FULL, false);
   }

   @Override
   public List<Entity> getEntities(@Nullable Entity var1, AABB var2, Predicate<? super Entity> var3) {
      this.getProfiler().incrementCounter("getEntities");
      List<Entity> â˜ƒ = Lists.<Entity>newArrayList();
      this.getEntities().get(â˜ƒ, var3x -> {
         if (var3x != â˜ƒ && â˜ƒ.test(var3x)) {
            â˜ƒ.add(var3x);
         }

         if (var3x instanceof EnderDragon) {
            for(EnderDragonPart â˜ƒ : ((EnderDragon)var3x).getSubEntities()) {
               if (var3x != â˜ƒ && â˜ƒ.test(â˜ƒ)) {
                  â˜ƒ.add(â˜ƒ);
               }
            }
         }
      });
      return â˜ƒ;
   }

   @Override
   public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> var1, AABB var2, Predicate<? super T> var3) {
      this.getProfiler().incrementCounter("getEntities");
      List<T> â˜ƒ = Lists.<T>newArrayList();
      this.getEntities().get(â˜ƒ, â˜ƒ, var3x -> {
         if (â˜ƒ.test(var3x)) {
            â˜ƒ.add(var3x);
         }

         if (var3x instanceof EnderDragon) {
            for(EnderDragonPart â˜ƒ : ((EnderDragon)var3x).getSubEntities()) {
               T â˜ƒx = â˜ƒ.tryCast(â˜ƒ);
               if (â˜ƒx != null && â˜ƒ.test(â˜ƒx)) {
                  â˜ƒ.add(â˜ƒx);
               }
            }
         }
      });
      return â˜ƒ;
   }

   @Nullable
   public abstract Entity getEntity(int var1);

   public void blockEntityChanged(BlockPos var1) {
      if (this.hasChunkAt(â˜ƒ)) {
         this.getChunkAt(â˜ƒ).markUnsaved();
      }
   }

   @Override
   public int getSeaLevel() {
      return 63;
   }

   public int getDirectSignalTo(BlockPos var1) {
      int â˜ƒ = 0;
      â˜ƒ = Math.max(â˜ƒ, this.getDirectSignal(â˜ƒ.below(), Direction.DOWN));
      if (â˜ƒ >= 15) {
         return â˜ƒ;
      } else {
         â˜ƒ = Math.max(â˜ƒ, this.getDirectSignal(â˜ƒ.above(), Direction.UP));
         if (â˜ƒ >= 15) {
            return â˜ƒ;
         } else {
            â˜ƒ = Math.max(â˜ƒ, this.getDirectSignal(â˜ƒ.north(), Direction.NORTH));
            if (â˜ƒ >= 15) {
               return â˜ƒ;
            } else {
               â˜ƒ = Math.max(â˜ƒ, this.getDirectSignal(â˜ƒ.south(), Direction.SOUTH));
               if (â˜ƒ >= 15) {
                  return â˜ƒ;
               } else {
                  â˜ƒ = Math.max(â˜ƒ, this.getDirectSignal(â˜ƒ.west(), Direction.WEST));
                  if (â˜ƒ >= 15) {
                     return â˜ƒ;
                  } else {
                     â˜ƒ = Math.max(â˜ƒ, this.getDirectSignal(â˜ƒ.east(), Direction.EAST));
                     return â˜ƒ >= 15 ? â˜ƒ : â˜ƒ;
                  }
               }
            }
         }
      }
   }

   public boolean hasSignal(BlockPos var1, Direction var2) {
      return this.getSignal(â˜ƒ, â˜ƒ) > 0;
   }

   public int getSignal(BlockPos var1, Direction var2) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ);
      int â˜ƒx = â˜ƒ.getSignal(this, â˜ƒ, â˜ƒ);
      return â˜ƒ.isRedstoneConductor(this, â˜ƒ) ? Math.max(â˜ƒx, this.getDirectSignalTo(â˜ƒ)) : â˜ƒx;
   }

   public boolean hasNeighborSignal(BlockPos var1) {
      if (this.getSignal(â˜ƒ.below(), Direction.DOWN) > 0) {
         return true;
      } else if (this.getSignal(â˜ƒ.above(), Direction.UP) > 0) {
         return true;
      } else if (this.getSignal(â˜ƒ.north(), Direction.NORTH) > 0) {
         return true;
      } else if (this.getSignal(â˜ƒ.south(), Direction.SOUTH) > 0) {
         return true;
      } else if (this.getSignal(â˜ƒ.west(), Direction.WEST) > 0) {
         return true;
      } else {
         return this.getSignal(â˜ƒ.east(), Direction.EAST) > 0;
      }
   }

   public int getBestNeighborSignal(BlockPos var1) {
      int â˜ƒ = 0;

      for(Direction â˜ƒx : DIRECTIONS) {
         int â˜ƒxx = this.getSignal(â˜ƒ.relative(â˜ƒx), â˜ƒx);
         if (â˜ƒxx >= 15) {
            return 15;
         }

         if (â˜ƒxx > â˜ƒ) {
            â˜ƒ = â˜ƒxx;
         }
      }

      return â˜ƒ;
   }

   public void disconnect() {
   }

   public long getGameTime() {
      return this.levelData.getGameTime();
   }

   public long getDayTime() {
      return this.levelData.getDayTime();
   }

   public boolean mayInteract(Player var1, BlockPos var2) {
      return true;
   }

   public void broadcastEntityEvent(Entity var1, byte var2) {
   }

   public void blockEvent(BlockPos var1, Block var2, int var3, int var4) {
      this.getBlockState(â˜ƒ).triggerEvent(this, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public LevelData getLevelData() {
      return this.levelData;
   }

   public GameRules getGameRules() {
      return this.levelData.getGameRules();
   }

   public float getThunderLevel(float var1) {
      return Mth.lerp(â˜ƒ, this.oThunderLevel, this.thunderLevel) * this.getRainLevel(â˜ƒ);
   }

   public void setThunderLevel(float var1) {
      float â˜ƒ = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
      this.oThunderLevel = â˜ƒ;
      this.thunderLevel = â˜ƒ;
   }

   public float getRainLevel(float var1) {
      return Mth.lerp(â˜ƒ, this.oRainLevel, this.rainLevel);
   }

   public void setRainLevel(float var1) {
      float â˜ƒ = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
      this.oRainLevel = â˜ƒ;
      this.rainLevel = â˜ƒ;
   }

   public boolean isThundering() {
      if (this.dimensionType().hasSkyLight() && !this.dimensionType().hasCeiling()) {
         return (double)this.getThunderLevel(1.0F) > 0.9;
      } else {
         return false;
      }
   }

   public boolean isRaining() {
      return (double)this.getRainLevel(1.0F) > 0.2;
   }

   public boolean isRainingAt(BlockPos var1) {
      if (!this.isRaining()) {
         return false;
      } else if (!this.canSeeSky(â˜ƒ)) {
         return false;
      } else if (this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, â˜ƒ).getY() > â˜ƒ.getY()) {
         return false;
      } else {
         Biome â˜ƒ = this.getBiome(â˜ƒ);
         return â˜ƒ.getPrecipitation() == Biome.Precipitation.RAIN && â˜ƒ.getTemperature(â˜ƒ) >= 0.15F;
      }
   }

   public boolean isHumidAt(BlockPos var1) {
      Biome â˜ƒ = this.getBiome(â˜ƒ);
      return â˜ƒ.isHumid();
   }

   @Nullable
   public abstract MapItemSavedData getMapData(String var1);

   public abstract void setMapData(String var1, MapItemSavedData var2);

   public abstract int getFreeMapId();

   public void globalLevelEvent(int var1, BlockPos var2, int var3) {
   }

   public CrashReportCategory fillReportDetails(CrashReport var1) {
      CrashReportCategory â˜ƒ = â˜ƒ.addCategory("Affected level", 1);
      â˜ƒ.setDetail("All players", (CrashReportDetail<String>)(() -> this.players().size() + " total; " + this.players()));
      â˜ƒ.setDetail("Chunk stats", this.getChunkSource()::gatherStats);
      â˜ƒ.setDetail("Level dimension", (CrashReportDetail<String>)(() -> this.dimension().location().toString()));

      try {
         this.levelData.fillCrashReportCategory(â˜ƒ, this);
      } catch (Throwable var4) {
         â˜ƒ.setDetailError("Level Data Unobtainable", var4);
      }

      return â˜ƒ;
   }

   public abstract void destroyBlockProgress(int var1, BlockPos var2, int var3);

   public void createFireworks(double var1, double var3, double var5, double var7, double var9, double var11, @Nullable CompoundTag var13) {
   }

   public abstract Scoreboard getScoreboard();

   public void updateNeighbourForOutputSignal(BlockPos var1, Block var2) {
      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
         if (this.hasChunkAt(â˜ƒx)) {
            BlockState â˜ƒxx = this.getBlockState(â˜ƒx);
            if (â˜ƒxx.is(Blocks.COMPARATOR)) {
               â˜ƒxx.neighborChanged(this, â˜ƒx, â˜ƒ, â˜ƒ, false);
            } else if (â˜ƒxx.isRedstoneConductor(this, â˜ƒx)) {
               â˜ƒx = â˜ƒx.relative(â˜ƒ);
               â˜ƒxx = this.getBlockState(â˜ƒx);
               if (â˜ƒxx.is(Blocks.COMPARATOR)) {
                  â˜ƒxx.neighborChanged(this, â˜ƒx, â˜ƒ, â˜ƒ, false);
               }
            }
         }
      }
   }

   @Override
   public DifficultyInstance getCurrentDifficultyAt(BlockPos var1) {
      long â˜ƒ = 0L;
      float â˜ƒx = 0.0F;
      if (this.hasChunkAt(â˜ƒ)) {
         â˜ƒx = this.getMoonBrightness();
         â˜ƒ = this.getChunkAt(â˜ƒ).getInhabitedTime();
      }

      return new DifficultyInstance(this.getDifficulty(), this.getDayTime(), â˜ƒ, â˜ƒx);
   }

   @Override
   public int getSkyDarken() {
      return this.skyDarken;
   }

   public void setSkyFlashTime(int var1) {
   }

   @Override
   public WorldBorder getWorldBorder() {
      return this.worldBorder;
   }

   public void sendPacketToServer(Packet<?> var1) {
      throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
   }

   @Override
   public DimensionType dimensionType() {
      return this.dimensionType;
   }

   public ResourceKey<Level> dimension() {
      return this.dimension;
   }

   @Override
   public Random getRandom() {
      return this.random;
   }

   @Override
   public boolean isStateAtPosition(BlockPos var1, Predicate<BlockState> var2) {
      return â˜ƒ.test(this.getBlockState(â˜ƒ));
   }

   @Override
   public boolean isFluidAtPosition(BlockPos var1, Predicate<FluidState> var2) {
      return â˜ƒ.test(this.getFluidState(â˜ƒ));
   }

   public abstract RecipeManager getRecipeManager();

   public abstract TagContainer getTagManager();

   public BlockPos getBlockRandomPos(int var1, int var2, int var3, int var4) {
      this.randValue = this.randValue * 3 + 1013904223;
      int â˜ƒ = this.randValue >> 2;
      return new BlockPos(â˜ƒ + (â˜ƒ & 15), â˜ƒ + (â˜ƒ >> 16 & â˜ƒ), â˜ƒ + (â˜ƒ >> 8 & 15));
   }

   public boolean noSave() {
      return false;
   }

   public ProfilerFiller getProfiler() {
      return (ProfilerFiller)this.profiler.get();
   }

   public Supplier<ProfilerFiller> getProfilerSupplier() {
      return this.profiler;
   }

   @Override
   public BiomeManager getBiomeManager() {
      return this.biomeManager;
   }

   public final boolean isDebug() {
      return this.isDebug;
   }

   protected abstract LevelEntityGetter<Entity> getEntities();

   protected void postGameEventInRadius(@Nullable Entity var1, GameEvent var2, BlockPos var3, int var4) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getX() - â˜ƒ);
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getZ() - â˜ƒ);
      int â˜ƒxx = SectionPos.blockToSectionCoord(â˜ƒ.getX() + â˜ƒ);
      int â˜ƒxxx = SectionPos.blockToSectionCoord(â˜ƒ.getZ() + â˜ƒ);
      int â˜ƒxxxx = SectionPos.blockToSectionCoord(â˜ƒ.getY() - â˜ƒ);
      int â˜ƒxxxxx = SectionPos.blockToSectionCoord(â˜ƒ.getY() + â˜ƒ);

      for(int â˜ƒxxxxxx = â˜ƒ; â˜ƒxxxxxx <= â˜ƒxx; ++â˜ƒxxxxxx) {
         for(int â˜ƒxxxxxxx = â˜ƒx; â˜ƒxxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxxx) {
            ChunkAccess â˜ƒxxxxxxxx = this.getChunkSource().getChunkNow(â˜ƒxxxxxx, â˜ƒxxxxxxx);
            if (â˜ƒxxxxxxxx != null) {
               for(int â˜ƒxxxxxxxxx = â˜ƒxxxx; â˜ƒxxxxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxxxxx) {
                  â˜ƒxxxxxxxx.getEventDispatcher(â˜ƒxxxxxxxxx).post(â˜ƒ, â˜ƒ, â˜ƒ);
               }
            }
         }
      }
   }
}
