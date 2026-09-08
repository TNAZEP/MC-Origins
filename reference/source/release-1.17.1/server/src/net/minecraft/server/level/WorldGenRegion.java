package net.minecraft.server.level;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenRegion implements WorldGenLevel {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<ChunkAccess> cache;
   private final ChunkPos center;
   private final int size;
   private final ServerLevel level;
   private final long seed;
   private final LevelData levelData;
   private final Random random;
   private final DimensionType dimensionType;
   private final TickList<Block> blockTicks = new WorldGenTickList<>(var1x -> this.getChunk(var1x).getBlockTicks());
   private final TickList<Fluid> liquidTicks = new WorldGenTickList<>(var1x -> this.getChunk(var1x).getLiquidTicks());
   private final BiomeManager biomeManager;
   private final ChunkPos firstPos;
   private final ChunkPos lastPos;
   private final StructureFeatureManager structureFeatureManager;
   private final ChunkStatus generatingStatus;
   private final int writeRadiusCutoff;
   @Nullable
   private Supplier<String> currentlyGenerating;

   public WorldGenRegion(ServerLevel var1, List<ChunkAccess> var2, ChunkStatus var3, int var4) {
      this.generatingStatus = â˜ƒ;
      this.writeRadiusCutoff = â˜ƒ;
      int â˜ƒ = Mth.floor(Math.sqrt((double)â˜ƒ.size()));
      if (â˜ƒ * â˜ƒ != â˜ƒ.size()) {
         throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("Cache size is not a square."));
      } else {
         ChunkPos â˜ƒ = ((ChunkAccess)â˜ƒ.get(â˜ƒ.size() / 2)).getPos();
         this.cache = â˜ƒ;
         this.center = â˜ƒ;
         this.size = â˜ƒ;
         this.level = â˜ƒ;
         this.seed = â˜ƒ.getSeed();
         this.levelData = â˜ƒ.getLevelData();
         this.random = â˜ƒ.getRandom();
         this.dimensionType = â˜ƒ.dimensionType();
         this.biomeManager = new BiomeManager(this, BiomeManager.obfuscateSeed(this.seed), â˜ƒ.dimensionType().getBiomeZoomer());
         this.firstPos = ((ChunkAccess)â˜ƒ.get(0)).getPos();
         this.lastPos = ((ChunkAccess)â˜ƒ.get(â˜ƒ.size() - 1)).getPos();
         this.structureFeatureManager = â˜ƒ.structureFeatureManager().forWorldGenRegion(this);
      }
   }

   public ChunkPos getCenter() {
      return this.center;
   }

   public void setCurrentlyGenerating(@Nullable Supplier<String> var1) {
      this.currentlyGenerating = â˜ƒ;
   }

   @Override
   public ChunkAccess getChunk(int var1, int var2) {
      return this.getChunk(â˜ƒ, â˜ƒ, ChunkStatus.EMPTY);
   }

   @Nullable
   @Override
   public ChunkAccess getChunk(int var1, int var2, ChunkStatus var3, boolean var4) {
      ChunkAccess â˜ƒ;
      if (this.hasChunk(â˜ƒ, â˜ƒ)) {
         int â˜ƒx = â˜ƒ - this.firstPos.x;
         int â˜ƒxx = â˜ƒ - this.firstPos.z;
         â˜ƒ = (ChunkAccess)this.cache.get(â˜ƒx + â˜ƒxx * this.size);
         if (â˜ƒ.getStatus().isOrAfter(â˜ƒ)) {
            return â˜ƒ;
         }
      } else {
         â˜ƒ = null;
      }

      if (!â˜ƒ) {
         return null;
      } else {
         LOGGER.error("Requested chunk : {} {}", â˜ƒ, â˜ƒ);
         LOGGER.error("Region bounds : {} {} | {} {}", this.firstPos.x, this.firstPos.z, this.lastPos.x, this.lastPos.z);
         if (â˜ƒ != null) {
            throw (RuntimeException)Util.pauseInIde(
               new RuntimeException(String.format("Chunk is not of correct status. Expecting %s, got %s | %s %s", â˜ƒ, â˜ƒ.getStatus(), â˜ƒ, â˜ƒ))
            );
         } else {
            throw (RuntimeException)Util.pauseInIde(new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", â˜ƒ, â˜ƒ)));
         }
      }
   }

   @Override
   public boolean hasChunk(int var1, int var2) {
      return â˜ƒ >= this.firstPos.x && â˜ƒ <= this.lastPos.x && â˜ƒ >= this.firstPos.z && â˜ƒ <= this.lastPos.z;
   }

   @Override
   public BlockState getBlockState(BlockPos var1) {
      return this.getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ())).getBlockState(â˜ƒ);
   }

   @Override
   public FluidState getFluidState(BlockPos var1) {
      return this.getChunk(â˜ƒ).getFluidState(â˜ƒ);
   }

   @Nullable
   @Override
   public Player getNearestPlayer(double var1, double var3, double var5, double var7, Predicate<Entity> var9) {
      return null;
   }

   @Override
   public int getSkyDarken() {
      return 0;
   }

   @Override
   public BiomeManager getBiomeManager() {
      return this.biomeManager;
   }

   @Override
   public Biome getUncachedNoiseBiome(int var1, int var2, int var3) {
      return this.level.getUncachedNoiseBiome(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public float getShade(Direction var1, boolean var2) {
      return 1.0F;
   }

   @Override
   public LevelLightEngine getLightEngine() {
      return this.level.getLightEngine();
   }

   @Override
   public boolean destroyBlock(BlockPos var1, boolean var2, @Nullable Entity var3, int var4) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ);
      if (â˜ƒ.isAir()) {
         return false;
      } else {
         if (â˜ƒ) {
            BlockEntity â˜ƒ = â˜ƒ.hasBlockEntity() ? this.getBlockEntity(â˜ƒ) : null;
            Block.dropResources(â˜ƒ, this.level, â˜ƒ, â˜ƒ, â˜ƒ, ItemStack.EMPTY);
         }

         return this.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 3, â˜ƒ);
      }
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos var1) {
      ChunkAccess â˜ƒ = this.getChunk(â˜ƒ);
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx != null) {
         return â˜ƒx;
      } else {
         CompoundTag â˜ƒ = â˜ƒ.getBlockEntityNbt(â˜ƒ);
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         if (â˜ƒ != null) {
            if ("DUMMY".equals(â˜ƒ.getString("id"))) {
               if (!â˜ƒx.hasBlockEntity()) {
                  return null;
               }

               â˜ƒx = ((EntityBlock)â˜ƒx.getBlock()).newBlockEntity(â˜ƒ, â˜ƒx);
            } else {
               â˜ƒx = BlockEntity.loadStatic(â˜ƒ, â˜ƒx, â˜ƒ);
            }

            if (â˜ƒx != null) {
               â˜ƒ.setBlockEntity(â˜ƒx);
               return â˜ƒx;
            }
         }

         if (â˜ƒx.hasBlockEntity()) {
            LOGGER.warn("Tried to access a block entity before it was created. {}", â˜ƒ);
         }

         return null;
      }
   }

   @Override
   public boolean ensureCanWrite(BlockPos var1) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getX());
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getZ());
      int â˜ƒxx = Math.abs(this.center.x - â˜ƒ);
      int â˜ƒxxx = Math.abs(this.center.z - â˜ƒx);
      if (â˜ƒxx <= this.writeRadiusCutoff && â˜ƒxxx <= this.writeRadiusCutoff) {
         return true;
      } else {
         Util.logAndPauseIfInIde(
            "Detected setBlock in a far chunk ["
               + â˜ƒ
               + ", "
               + â˜ƒx
               + "], pos: "
               + â˜ƒ
               + ", status: "
               + this.generatingStatus
               + (this.currentlyGenerating == null ? "" : ", currently generating: " + (String)this.currentlyGenerating.get())
         );
         return false;
      }
   }

   @Override
   public boolean setBlock(BlockPos var1, BlockState var2, int var3, int var4) {
      if (!this.ensureCanWrite(â˜ƒ)) {
         return false;
      } else {
         ChunkAccess â˜ƒ = this.getChunk(â˜ƒ);
         BlockState â˜ƒx = â˜ƒ.setBlockState(â˜ƒ, â˜ƒ, false);
         if (â˜ƒx != null) {
            this.level.onBlockStateChange(â˜ƒ, â˜ƒx, â˜ƒ);
         }

         if (â˜ƒ.hasBlockEntity()) {
            if (â˜ƒ.getStatus().getChunkType() == ChunkStatus.ChunkType.LEVELCHUNK) {
               BlockEntity â˜ƒ = ((EntityBlock)â˜ƒ.getBlock()).newBlockEntity(â˜ƒ, â˜ƒ);
               if (â˜ƒ != null) {
                  â˜ƒ.setBlockEntity(â˜ƒ);
               } else {
                  â˜ƒ.removeBlockEntity(â˜ƒ);
               }
            } else {
               CompoundTag â˜ƒ = new CompoundTag();
               â˜ƒ.putInt("x", â˜ƒ.getX());
               â˜ƒ.putInt("y", â˜ƒ.getY());
               â˜ƒ.putInt("z", â˜ƒ.getZ());
               â˜ƒ.putString("id", "DUMMY");
               â˜ƒ.setBlockEntityNbt(â˜ƒ);
            }
         } else if (â˜ƒx != null && â˜ƒx.hasBlockEntity()) {
            â˜ƒ.removeBlockEntity(â˜ƒ);
         }

         if (â˜ƒ.hasPostProcess(this, â˜ƒ)) {
            this.markPosForPostprocessing(â˜ƒ);
         }

         return true;
      }
   }

   private void markPosForPostprocessing(BlockPos var1) {
      this.getChunk(â˜ƒ).markPosForPostprocessing(â˜ƒ);
   }

   @Override
   public boolean addFreshEntity(Entity var1) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getBlockX());
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getBlockZ());
      this.getChunk(â˜ƒ, â˜ƒx).addEntity(â˜ƒ);
      return true;
   }

   @Override
   public boolean removeBlock(BlockPos var1, boolean var2) {
      return this.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 3);
   }

   @Override
   public WorldBorder getWorldBorder() {
      return this.level.getWorldBorder();
   }

   @Override
   public boolean isClientSide() {
      return false;
   }

   @Deprecated
   @Override
   public ServerLevel getLevel() {
      return this.level;
   }

   @Override
   public RegistryAccess registryAccess() {
      return this.level.registryAccess();
   }

   @Override
   public LevelData getLevelData() {
      return this.levelData;
   }

   @Override
   public DifficultyInstance getCurrentDifficultyAt(BlockPos var1) {
      if (!this.hasChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()))) {
         throw new RuntimeException("We are asking a region for a chunk out of bound");
      } else {
         return new DifficultyInstance(this.level.getDifficulty(), this.level.getDayTime(), 0L, this.level.getMoonBrightness());
      }
   }

   @Nullable
   @Override
   public MinecraftServer getServer() {
      return this.level.getServer();
   }

   @Override
   public ChunkSource getChunkSource() {
      return this.level.getChunkSource();
   }

   @Override
   public long getSeed() {
      return this.seed;
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
   public int getSeaLevel() {
      return this.level.getSeaLevel();
   }

   @Override
   public Random getRandom() {
      return this.random;
   }

   @Override
   public int getHeight(Heightmap.Types var1, int var2, int var3) {
      return this.getChunk(SectionPos.blockToSectionCoord(â˜ƒ), SectionPos.blockToSectionCoord(â˜ƒ)).getHeight(â˜ƒ, â˜ƒ & 15, â˜ƒ & 15) + 1;
   }

   @Override
   public void playSound(@Nullable Player var1, BlockPos var2, SoundEvent var3, SoundSource var4, float var5, float var6) {
   }

   @Override
   public void addParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12) {
   }

   @Override
   public void levelEvent(@Nullable Player var1, int var2, BlockPos var3, int var4) {
   }

   @Override
   public void gameEvent(@Nullable Entity var1, GameEvent var2, BlockPos var3) {
   }

   @Override
   public DimensionType dimensionType() {
      return this.dimensionType;
   }

   @Override
   public boolean isStateAtPosition(BlockPos var1, Predicate<BlockState> var2) {
      return â˜ƒ.test(this.getBlockState(â˜ƒ));
   }

   @Override
   public boolean isFluidAtPosition(BlockPos var1, Predicate<FluidState> var2) {
      return â˜ƒ.test(this.getFluidState(â˜ƒ));
   }

   @Override
   public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> var1, AABB var2, Predicate<? super T> var3) {
      return Collections.emptyList();
   }

   @Override
   public List<Entity> getEntities(@Nullable Entity var1, AABB var2, @Nullable Predicate<? super Entity> var3) {
      return Collections.emptyList();
   }

   @Override
   public List<Player> players() {
      return Collections.emptyList();
   }

   @Override
   public Stream<? extends StructureStart<?>> startsForFeature(SectionPos var1, StructureFeature<?> var2) {
      return this.structureFeatureManager.startsForFeature(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getMinBuildHeight() {
      return this.level.getMinBuildHeight();
   }

   @Override
   public int getHeight() {
      return this.level.getHeight();
   }
}
