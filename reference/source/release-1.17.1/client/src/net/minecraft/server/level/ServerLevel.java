package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddVibrationSignalPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.Mth;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockEventData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.ForcedChunksSavedData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerTickList;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.TickNextTickData;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.storage.EntityStorage;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.entity.EntityPersistentStorage;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListenerRegistrar;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.saveddata.maps.MapIndex;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLevel extends Level implements WorldGenLevel {
   public static final BlockPos END_SPAWN_POINT = new BlockPos(100, 50, 0);
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int EMPTY_TIME_NO_TICK = 300;
   final List<ServerPlayer> players = Lists.<ServerPlayer>newArrayList();
   private final ServerChunkCache chunkSource;
   private final MinecraftServer server;
   private final ServerLevelData serverLevelData;
   final EntityTickList entityTickList = new EntityTickList();
   private final PersistentEntitySectionManager<Entity> entityManager;
   public boolean noSave;
   private final SleepStatus sleepStatus;
   private int emptyTime;
   private final PortalForcer portalForcer;
   private final ServerTickList<Block> blockTicks = new ServerTickList<>(
      this, var0 -> var0 == null || var0.defaultBlockState().isAir(), Registry.BLOCK::getKey, this::tickBlock
   );
   private final ServerTickList<Fluid> liquidTicks = new ServerTickList<>(
      this, var0 -> var0 == null || var0 == Fluids.EMPTY, Registry.FLUID::getKey, this::tickLiquid
   );
   final Set<Mob> navigatingMobs = new ObjectOpenHashSet<>();
   protected final Raids raids;
   private final ObjectLinkedOpenHashSet<BlockEventData> blockEvents = new ObjectLinkedOpenHashSet<>();
   private boolean handlingTick;
   private final List<CustomSpawner> customSpawners;
   @Nullable
   private final EndDragonFight dragonFight;
   final Int2ObjectMap<EnderDragonPart> dragonParts = new Int2ObjectOpenHashMap<>();
   private final StructureFeatureManager structureFeatureManager;
   private final boolean tickTime;

   public ServerLevel(
      MinecraftServer var1,
      Executor var2,
      LevelStorageSource.LevelStorageAccess var3,
      ServerLevelData var4,
      ResourceKey<Level> var5,
      DimensionType var6,
      ChunkProgressListener var7,
      ChunkGenerator var8,
      boolean var9,
      long var10,
      List<CustomSpawner> var12,
      boolean var13
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ::getProfiler, false, â˜ƒ, â˜ƒ);
      this.tickTime = â˜ƒ;
      this.server = â˜ƒ;
      this.customSpawners = â˜ƒ;
      this.serverLevelData = â˜ƒ;
      boolean â˜ƒ = â˜ƒ.forceSynchronousWrites();
      DataFixer â˜ƒx = â˜ƒ.getFixerUpper();
      EntityPersistentStorage<Entity> â˜ƒxx = new EntityStorage(this, new File(â˜ƒ.getDimensionPath(â˜ƒ), "entities"), â˜ƒx, â˜ƒ, â˜ƒ);
      this.entityManager = new PersistentEntitySectionManager<>(Entity.class, new ServerLevel.EntityCallbacks(), â˜ƒxx);
      this.chunkSource = new ServerChunkCache(
         this,
         â˜ƒ,
         â˜ƒx,
         â˜ƒ.getStructureManager(),
         â˜ƒ,
         â˜ƒ,
         â˜ƒ.getPlayerList().getViewDistance(),
         â˜ƒ,
         â˜ƒ,
         this.entityManager::updateChunkStatus,
         () -> â˜ƒ.overworld().getDataStorage()
      );
      this.portalForcer = new PortalForcer(this);
      this.updateSkyBrightness();
      this.prepareWeather();
      this.getWorldBorder().setAbsoluteMaxSize(â˜ƒ.getAbsoluteMaxWorldSize());
      this.raids = this.getDataStorage().computeIfAbsent(var1x -> Raids.load(this, var1x), () -> new Raids(this), Raids.getFileId(this.dimensionType()));
      if (!â˜ƒ.isSingleplayer()) {
         â˜ƒ.setGameType(â˜ƒ.getDefaultGameType());
      }

      this.structureFeatureManager = new StructureFeatureManager(this, â˜ƒ.getWorldData().worldGenSettings());
      if (this.dimensionType().createDragonFight()) {
         this.dragonFight = new EndDragonFight(this, â˜ƒ.getWorldData().worldGenSettings().seed(), â˜ƒ.getWorldData().endDragonFightData());
      } else {
         this.dragonFight = null;
      }

      this.sleepStatus = new SleepStatus();
   }

   public void setWeatherParameters(int var1, int var2, boolean var3, boolean var4) {
      this.serverLevelData.setClearWeatherTime(â˜ƒ);
      this.serverLevelData.setRainTime(â˜ƒ);
      this.serverLevelData.setThunderTime(â˜ƒ);
      this.serverLevelData.setRaining(â˜ƒ);
      this.serverLevelData.setThundering(â˜ƒ);
   }

   @Override
   public Biome getUncachedNoiseBiome(int var1, int var2, int var3) {
      return this.getChunkSource().getGenerator().getBiomeSource().getNoiseBiome(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public StructureFeatureManager structureFeatureManager() {
      return this.structureFeatureManager;
   }

   public void tick(BooleanSupplier var1) {
      ProfilerFiller â˜ƒ = this.getProfiler();
      this.handlingTick = true;
      â˜ƒ.push("world border");
      this.getWorldBorder().tick();
      â˜ƒ.popPush("weather");
      boolean â˜ƒx = this.isRaining();
      if (this.dimensionType().hasSkyLight()) {
         if (this.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
            int â˜ƒxx = this.serverLevelData.getClearWeatherTime();
            int â˜ƒxxx = this.serverLevelData.getThunderTime();
            int â˜ƒxxxx = this.serverLevelData.getRainTime();
            boolean â˜ƒxxxxx = this.levelData.isThundering();
            boolean â˜ƒxxxxxx = this.levelData.isRaining();
            if (â˜ƒxx > 0) {
               --â˜ƒxx;
               â˜ƒxxx = â˜ƒxxxxx ? 0 : 1;
               â˜ƒxxxx = â˜ƒxxxxxx ? 0 : 1;
               â˜ƒxxxxx = false;
               â˜ƒxxxxxx = false;
            } else {
               if (â˜ƒxxx > 0) {
                  if (--â˜ƒxxx == 0) {
                     â˜ƒxxxxx = !â˜ƒxxxxx;
                  }
               } else if (â˜ƒxxxxx) {
                  â˜ƒxxx = this.random.nextInt(12000) + 3600;
               } else {
                  â˜ƒxxx = this.random.nextInt(168000) + 12000;
               }

               if (â˜ƒxxxx > 0) {
                  if (--â˜ƒxxxx == 0) {
                     â˜ƒxxxxxx = !â˜ƒxxxxxx;
                  }
               } else if (â˜ƒxxxxxx) {
                  â˜ƒxxxx = this.random.nextInt(12000) + 12000;
               } else {
                  â˜ƒxxxx = this.random.nextInt(168000) + 12000;
               }
            }

            this.serverLevelData.setThunderTime(â˜ƒxxx);
            this.serverLevelData.setRainTime(â˜ƒxxxx);
            this.serverLevelData.setClearWeatherTime(â˜ƒxx);
            this.serverLevelData.setThundering(â˜ƒxxxxx);
            this.serverLevelData.setRaining(â˜ƒxxxxxx);
         }

         this.oThunderLevel = this.thunderLevel;
         if (this.levelData.isThundering()) {
            this.thunderLevel = (float)((double)this.thunderLevel + 0.01);
         } else {
            this.thunderLevel = (float)((double)this.thunderLevel - 0.01);
         }

         this.thunderLevel = Mth.clamp(this.thunderLevel, 0.0F, 1.0F);
         this.oRainLevel = this.rainLevel;
         if (this.levelData.isRaining()) {
            this.rainLevel = (float)((double)this.rainLevel + 0.01);
         } else {
            this.rainLevel = (float)((double)this.rainLevel - 0.01);
         }

         this.rainLevel = Mth.clamp(this.rainLevel, 0.0F, 1.0F);
      }

      if (this.oRainLevel != this.rainLevel) {
         this.server
            .getPlayerList()
            .broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel), this.dimension());
      }

      if (this.oThunderLevel != this.thunderLevel) {
         this.server
            .getPlayerList()
            .broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel), this.dimension());
      }

      if (â˜ƒx != this.isRaining()) {
         if (â˜ƒx) {
            this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 0.0F));
         } else {
            this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0F));
         }

         this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel));
         this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel));
      }

      int â˜ƒ = this.getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
      if (this.sleepStatus.areEnoughSleeping(â˜ƒ) && this.sleepStatus.areEnoughDeepSleeping(â˜ƒ, this.players)) {
         if (this.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            long â˜ƒx = this.levelData.getDayTime() + 24000L;
            this.setDayTime(â˜ƒx - â˜ƒx % 24000L);
         }

         this.wakeUpAllPlayers();
         if (this.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
            this.stopWeather();
         }
      }

      this.updateSkyBrightness();
      this.tickTime();
      â˜ƒ.popPush("tickPending");
      if (!this.isDebug()) {
         this.blockTicks.tick();
         this.liquidTicks.tick();
      }

      â˜ƒ.popPush("raid");
      this.raids.tick();
      â˜ƒ.popPush("chunkSource");
      this.getChunkSource().tick(â˜ƒ);
      â˜ƒ.popPush("blockEvents");
      this.runBlockEvents();
      this.handlingTick = false;
      â˜ƒ.pop();
      boolean â˜ƒ = !this.players.isEmpty() || !this.getForcedChunks().isEmpty();
      if (â˜ƒ) {
         this.resetEmptyTime();
      }

      if (â˜ƒ || this.emptyTime++ < 300) {
         â˜ƒ.push("entities");
         if (this.dragonFight != null) {
            â˜ƒ.push("dragonFight");
            this.dragonFight.tick();
            â˜ƒ.pop();
         }

         this.entityTickList.forEach(var2x -> {
            if (!var2x.isRemoved()) {
               if (this.shouldDiscardEntity(var2x)) {
                  var2x.discard();
               } else {
                  â˜ƒ.push("checkDespawn");
                  var2x.checkDespawn();
                  â˜ƒ.pop();
                  Entity â˜ƒ = var2x.getVehicle();
                  if (â˜ƒ != null) {
                     if (!â˜ƒ.isRemoved() && â˜ƒ.hasPassenger(var2x)) {
                        return;
                     }

                     var2x.stopRiding();
                  }

                  â˜ƒ.push("tick");
                  this.guardEntityTick(this::tickNonPassenger, var2x);
                  â˜ƒ.pop();
               }
            }
         });
         â˜ƒ.pop();
         this.tickBlockEntities();
      }

      â˜ƒ.push("entityManagement");
      this.entityManager.tick();
      â˜ƒ.pop();
   }

   protected void tickTime() {
      if (this.tickTime) {
         long â˜ƒ = this.levelData.getGameTime() + 1L;
         this.serverLevelData.setGameTime(â˜ƒ);
         this.serverLevelData.getScheduledEvents().tick(this.server, â˜ƒ);
         if (this.levelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            this.setDayTime(this.levelData.getDayTime() + 1L);
         }
      }
   }

   public void setDayTime(long var1) {
      this.serverLevelData.setDayTime(â˜ƒ);
   }

   public void tickCustomSpawners(boolean var1, boolean var2) {
      for(CustomSpawner â˜ƒ : this.customSpawners) {
         â˜ƒ.tick(this, â˜ƒ, â˜ƒ);
      }
   }

   private boolean shouldDiscardEntity(Entity var1) {
      if (this.server.isSpawningAnimals() || !(â˜ƒ instanceof Animal) && !(â˜ƒ instanceof WaterAnimal)) {
         return !this.server.areNpcsEnabled() && â˜ƒ instanceof Npc;
      } else {
         return true;
      }
   }

   private void wakeUpAllPlayers() {
      this.sleepStatus.removeAllSleepers();
      ((List)this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList())).forEach(var0 -> var0.stopSleepInBed(false, false));
   }

   public void tickChunk(LevelChunk var1, int var2) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      boolean â˜ƒx = this.isRaining();
      int â˜ƒxx = â˜ƒ.getMinBlockX();
      int â˜ƒxxx = â˜ƒ.getMinBlockZ();
      ProfilerFiller â˜ƒxxxx = this.getProfiler();
      â˜ƒxxxx.push("thunder");
      if (â˜ƒx && this.isThundering() && this.random.nextInt(100000) == 0) {
         BlockPos â˜ƒxxxxx = this.findLightningTargetAround(this.getBlockRandomPos(â˜ƒxx, 0, â˜ƒxxx, 15));
         if (this.isRainingAt(â˜ƒxxxxx)) {
            DifficultyInstance â˜ƒxxxxxx = this.getCurrentDifficultyAt(â˜ƒxxxxx);
            boolean â˜ƒxxxxxxx = this.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)
               && this.random.nextDouble() < (double)â˜ƒxxxxxx.getEffectiveDifficulty() * 0.01
               && !this.getBlockState(â˜ƒxxxxx.below()).is(Blocks.LIGHTNING_ROD);
            if (â˜ƒxxxxxxx) {
               SkeletonHorse â˜ƒxxxxxxxx = EntityType.SKELETON_HORSE.create(this);
               â˜ƒxxxxxxxx.setTrap(true);
               â˜ƒxxxxxxxx.setAge(0);
               â˜ƒxxxxxxxx.setPos((double)â˜ƒxxxxx.getX(), (double)â˜ƒxxxxx.getY(), (double)â˜ƒxxxxx.getZ());
               this.addFreshEntity(â˜ƒxxxxxxxx);
            }

            LightningBolt â˜ƒxxxxxx = EntityType.LIGHTNING_BOLT.create(this);
            â˜ƒxxxxxx.moveTo(Vec3.atBottomCenterOf(â˜ƒxxxxx));
            â˜ƒxxxxxx.setVisualOnly(â˜ƒxxxxxxx);
            this.addFreshEntity(â˜ƒxxxxxx);
         }
      }

      â˜ƒxxxx.popPush("iceandsnow");
      if (this.random.nextInt(16) == 0) {
         BlockPos â˜ƒ = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.getBlockRandomPos(â˜ƒxx, 0, â˜ƒxxx, 15));
         BlockPos â˜ƒx = â˜ƒ.below();
         Biome â˜ƒxx = this.getBiome(â˜ƒ);
         if (â˜ƒxx.shouldFreeze(this, â˜ƒx)) {
            this.setBlockAndUpdate(â˜ƒx, Blocks.ICE.defaultBlockState());
         }

         if (â˜ƒx) {
            if (â˜ƒxx.shouldSnow(this, â˜ƒ)) {
               this.setBlockAndUpdate(â˜ƒ, Blocks.SNOW.defaultBlockState());
            }

            BlockState â˜ƒ = this.getBlockState(â˜ƒx);
            Biome.Precipitation â˜ƒx = this.getBiome(â˜ƒ).getPrecipitation();
            if (â˜ƒx == Biome.Precipitation.RAIN && â˜ƒxx.isColdEnoughToSnow(â˜ƒx)) {
               â˜ƒx = Biome.Precipitation.SNOW;
            }

            â˜ƒ.getBlock().handlePrecipitation(â˜ƒ, this, â˜ƒx, â˜ƒx);
         }
      }

      â˜ƒxxxx.popPush("tickBlocks");
      if (â˜ƒ > 0) {
         for(LevelChunkSection â˜ƒ : â˜ƒ.getSections()) {
            if (â˜ƒ != LevelChunk.EMPTY_SECTION && â˜ƒ.isRandomlyTicking()) {
               int â˜ƒx = â˜ƒ.bottomBlockY();

               for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
                  BlockPos â˜ƒxxx = this.getBlockRandomPos(â˜ƒxx, â˜ƒx, â˜ƒxxx, 15);
                  â˜ƒxxxx.push("randomTick");
                  BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx.getX() - â˜ƒxx, â˜ƒxxx.getY() - â˜ƒx, â˜ƒxxx.getZ() - â˜ƒxxx);
                  if (â˜ƒxxxx.isRandomlyTicking()) {
                     â˜ƒxxxx.randomTick(this, â˜ƒxxx, this.random);
                  }

                  FluidState â˜ƒxxx = â˜ƒxxxx.getFluidState();
                  if (â˜ƒxxx.isRandomlyTicking()) {
                     â˜ƒxxx.randomTick(this, â˜ƒxxx, this.random);
                  }

                  â˜ƒxxxx.pop();
               }
            }
         }
      }

      â˜ƒxxxx.pop();
   }

   private Optional<BlockPos> findLightningRod(BlockPos var1) {
      Optional<BlockPos> â˜ƒ = this.getPoiManager()
         .findClosest(
            var0 -> var0 == PoiType.LIGHTNING_ROD,
            var1x -> var1x.getY() == this.getLevel().getHeight(Heightmap.Types.WORLD_SURFACE, var1x.getX(), var1x.getZ()) - 1,
            â˜ƒ,
            128,
            PoiManager.Occupancy.ANY
         );
      return â˜ƒ.map(var0 -> var0.above(1));
   }

   protected BlockPos findLightningTargetAround(BlockPos var1) {
      BlockPos â˜ƒ = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, â˜ƒ);
      Optional<BlockPos> â˜ƒx = this.findLightningRod(â˜ƒ);
      if (â˜ƒx.isPresent()) {
         return (BlockPos)â˜ƒx.get();
      } else {
         AABB â˜ƒ = new AABB(â˜ƒ, new BlockPos(â˜ƒ.getX(), this.getMaxBuildHeight(), â˜ƒ.getZ())).inflate(3.0);
         List<LivingEntity> â˜ƒx = this.getEntitiesOfClass(
            LivingEntity.class, â˜ƒ, var1x -> var1x != null && var1x.isAlive() && this.canSeeSky(var1x.blockPosition())
         );
         if (!â˜ƒx.isEmpty()) {
            return ((LivingEntity)â˜ƒx.get(this.random.nextInt(â˜ƒx.size()))).blockPosition();
         } else {
            if (â˜ƒ.getY() == this.getMinBuildHeight() - 1) {
               â˜ƒ = â˜ƒ.above(2);
            }

            return â˜ƒ;
         }
      }
   }

   public boolean isHandlingTick() {
      return this.handlingTick;
   }

   public boolean canSleepThroughNights() {
      return this.getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE) <= 100;
   }

   private void announceSleepStatus() {
      if (this.canSleepThroughNights()) {
         if (!this.getServer().isSingleplayer() || this.getServer().isPublished()) {
            int â˜ƒx = this.getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
            Component â˜ƒ;
            if (this.sleepStatus.areEnoughSleeping(â˜ƒx)) {
               â˜ƒ = new TranslatableComponent("sleep.skipping_night");
            } else {
               â˜ƒ = new TranslatableComponent("sleep.players_sleeping", this.sleepStatus.amountSleeping(), this.sleepStatus.sleepersNeeded(â˜ƒx));
            }

            for(ServerPlayer â˜ƒ : this.players) {
               â˜ƒ.displayClientMessage(â˜ƒ, true);
            }
         }
      }
   }

   public void updateSleepingPlayerList() {
      if (!this.players.isEmpty() && this.sleepStatus.update(this.players)) {
         this.announceSleepStatus();
      }
   }

   public ServerScoreboard getScoreboard() {
      return this.server.getScoreboard();
   }

   private void stopWeather() {
      this.serverLevelData.setRainTime(0);
      this.serverLevelData.setRaining(false);
      this.serverLevelData.setThunderTime(0);
      this.serverLevelData.setThundering(false);
   }

   public void resetEmptyTime() {
      this.emptyTime = 0;
   }

   private void tickLiquid(TickNextTickData<Fluid> var1) {
      FluidState â˜ƒ = this.getFluidState(â˜ƒ.pos);
      if (â˜ƒ.getType() == â˜ƒ.getType()) {
         â˜ƒ.tick(this, â˜ƒ.pos);
      }
   }

   private void tickBlock(TickNextTickData<Block> var1) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ.pos);
      if (â˜ƒ.is(â˜ƒ.getType())) {
         â˜ƒ.tick(this, â˜ƒ.pos, this.random);
      }
   }

   public void tickNonPassenger(Entity var1) {
      â˜ƒ.setOldPosAndRot();
      ProfilerFiller â˜ƒ = this.getProfiler();
      ++â˜ƒ.tickCount;
      this.getProfiler().push((Supplier<String>)(() -> Registry.ENTITY_TYPE.getKey(â˜ƒ.getType()).toString()));
      â˜ƒ.incrementCounter("tickNonPassenger");
      â˜ƒ.tick();
      this.getProfiler().pop();

      for(Entity â˜ƒx : â˜ƒ.getPassengers()) {
         this.tickPassenger(â˜ƒ, â˜ƒx);
      }
   }

   private void tickPassenger(Entity var1, Entity var2) {
      if (â˜ƒ.isRemoved() || â˜ƒ.getVehicle() != â˜ƒ) {
         â˜ƒ.stopRiding();
      } else if (â˜ƒ instanceof Player || this.entityTickList.contains(â˜ƒ)) {
         â˜ƒ.setOldPosAndRot();
         ++â˜ƒ.tickCount;
         ProfilerFiller â˜ƒ = this.getProfiler();
         â˜ƒ.push((Supplier<String>)(() -> Registry.ENTITY_TYPE.getKey(â˜ƒ.getType()).toString()));
         â˜ƒ.incrementCounter("tickPassenger");
         â˜ƒ.rideTick();
         â˜ƒ.pop();

         for(Entity â˜ƒx : â˜ƒ.getPassengers()) {
            this.tickPassenger(â˜ƒ, â˜ƒx);
         }
      }
   }

   @Override
   public boolean mayInteract(Player var1, BlockPos var2) {
      return !this.server.isUnderSpawnProtection(this, â˜ƒ, â˜ƒ) && this.getWorldBorder().isWithinBounds(â˜ƒ);
   }

   public void save(@Nullable ProgressListener var1, boolean var2, boolean var3) {
      ServerChunkCache â˜ƒ = this.getChunkSource();
      if (!â˜ƒ) {
         if (â˜ƒ != null) {
            â˜ƒ.progressStartNoAbort(new TranslatableComponent("menu.savingLevel"));
         }

         this.saveLevelData();
         if (â˜ƒ != null) {
            â˜ƒ.progressStage(new TranslatableComponent("menu.savingChunks"));
         }

         â˜ƒ.save(â˜ƒ);
         if (â˜ƒ) {
            this.entityManager.saveAll();
         } else {
            this.entityManager.autoSave();
         }
      }
   }

   private void saveLevelData() {
      if (this.dragonFight != null) {
         this.server.getWorldData().setEndDragonFightData(this.dragonFight.saveData());
      }

      this.getChunkSource().getDataStorage().save();
   }

   public <T extends Entity> List<? extends T> getEntities(EntityTypeTest<Entity, T> var1, Predicate<? super T> var2) {
      List<T> â˜ƒ = Lists.<T>newArrayList();
      this.getEntities().get(â˜ƒ, var2x -> {
         if (â˜ƒ.test(var2x)) {
            â˜ƒ.add(var2x);
         }
      });
      return â˜ƒ;
   }

   public List<? extends EnderDragon> getDragons() {
      return this.getEntities(EntityType.ENDER_DRAGON, LivingEntity::isAlive);
   }

   public List<ServerPlayer> getPlayers(Predicate<? super ServerPlayer> var1) {
      List<ServerPlayer> â˜ƒ = Lists.<ServerPlayer>newArrayList();

      for(ServerPlayer â˜ƒx : this.players) {
         if (â˜ƒ.test(â˜ƒx)) {
            â˜ƒ.add(â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   @Nullable
   public ServerPlayer getRandomPlayer() {
      List<ServerPlayer> â˜ƒ = this.getPlayers(LivingEntity::isAlive);
      return â˜ƒ.isEmpty() ? null : (ServerPlayer)â˜ƒ.get(this.random.nextInt(â˜ƒ.size()));
   }

   @Override
   public boolean addFreshEntity(Entity var1) {
      return this.addEntity(â˜ƒ);
   }

   public boolean addWithUUID(Entity var1) {
      return this.addEntity(â˜ƒ);
   }

   public void addDuringTeleport(Entity var1) {
      this.addEntity(â˜ƒ);
   }

   public void addDuringCommandTeleport(ServerPlayer var1) {
      this.addPlayer(â˜ƒ);
   }

   public void addDuringPortalTeleport(ServerPlayer var1) {
      this.addPlayer(â˜ƒ);
   }

   public void addNewPlayer(ServerPlayer var1) {
      this.addPlayer(â˜ƒ);
   }

   public void addRespawnedPlayer(ServerPlayer var1) {
      this.addPlayer(â˜ƒ);
   }

   private void addPlayer(ServerPlayer var1) {
      Entity â˜ƒ = this.getEntities().get(â˜ƒ.getUUID());
      if (â˜ƒ != null) {
         LOGGER.warn("Force-added player with duplicate UUID {}", â˜ƒ.getUUID().toString());
         â˜ƒ.unRide();
         this.removePlayerImmediately((ServerPlayer)â˜ƒ, Entity.RemovalReason.DISCARDED);
      }

      this.entityManager.addNewEntity(â˜ƒ);
   }

   private boolean addEntity(Entity var1) {
      if (â˜ƒ.isRemoved()) {
         LOGGER.warn("Tried to add entity {} but it was marked as removed already", EntityType.getKey(â˜ƒ.getType()));
         return false;
      } else {
         return this.entityManager.addNewEntity(â˜ƒ);
      }
   }

   public boolean tryAddFreshEntityWithPassengers(Entity var1) {
      if (â˜ƒ.getSelfAndPassengers().map(Entity::getUUID).anyMatch(this.entityManager::isLoaded)) {
         return false;
      } else {
         this.addFreshEntityWithPassengers(â˜ƒ);
         return true;
      }
   }

   public void unload(LevelChunk var1) {
      â˜ƒ.invalidateAllBlockEntities();
   }

   public void removePlayerImmediately(ServerPlayer var1, Entity.RemovalReason var2) {
      â˜ƒ.remove(â˜ƒ);
   }

   @Override
   public void destroyBlockProgress(int var1, BlockPos var2, int var3) {
      for(ServerPlayer â˜ƒ : this.server.getPlayerList().getPlayers()) {
         if (â˜ƒ != null && â˜ƒ.level == this && â˜ƒ.getId() != â˜ƒ) {
            double â˜ƒx = (double)â˜ƒ.getX() - â˜ƒ.getX();
            double â˜ƒxx = (double)â˜ƒ.getY() - â˜ƒ.getY();
            double â˜ƒxxx = (double)â˜ƒ.getZ() - â˜ƒ.getZ();
            if (â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx < 1024.0) {
               â˜ƒ.connection.send(new ClientboundBlockDestructionPacket(â˜ƒ, â˜ƒ, â˜ƒ));
            }
         }
      }
   }

   @Override
   public void playSound(@Nullable Player var1, double var2, double var4, double var6, SoundEvent var8, SoundSource var9, float var10, float var11) {
      this.server
         .getPlayerList()
         .broadcast(
            â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ > 1.0F ? (double)(16.0F * â˜ƒ) : 16.0, this.dimension(), new ClientboundSoundPacket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
         );
   }

   @Override
   public void playSound(@Nullable Player var1, Entity var2, SoundEvent var3, SoundSource var4, float var5, float var6) {
      this.server
         .getPlayerList()
         .broadcast(
            â˜ƒ,
            â˜ƒ.getX(),
            â˜ƒ.getY(),
            â˜ƒ.getZ(),
            â˜ƒ > 1.0F ? (double)(16.0F * â˜ƒ) : 16.0,
            this.dimension(),
            new ClientboundSoundEntityPacket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
         );
   }

   @Override
   public void globalLevelEvent(int var1, BlockPos var2, int var3) {
      this.server.getPlayerList().broadcastAll(new ClientboundLevelEventPacket(â˜ƒ, â˜ƒ, â˜ƒ, true));
   }

   @Override
   public void levelEvent(@Nullable Player var1, int var2, BlockPos var3, int var4) {
      this.server
         .getPlayerList()
         .broadcast(
            â˜ƒ, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), 64.0, this.dimension(), new ClientboundLevelEventPacket(â˜ƒ, â˜ƒ, â˜ƒ, false)
         );
   }

   @Override
   public int getLogicalHeight() {
      return this.dimensionType().logicalHeight();
   }

   @Override
   public void gameEvent(@Nullable Entity var1, GameEvent var2, BlockPos var3) {
      this.postGameEventInRadius(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getNotificationRadius());
   }

   @Override
   public void sendBlockUpdated(BlockPos var1, BlockState var2, BlockState var3, int var4) {
      this.getChunkSource().blockChanged(â˜ƒ);
      VoxelShape â˜ƒ = â˜ƒ.getCollisionShape(this, â˜ƒ);
      VoxelShape â˜ƒx = â˜ƒ.getCollisionShape(this, â˜ƒ);
      if (Shapes.joinIsNotEmpty(â˜ƒ, â˜ƒx, BooleanOp.NOT_SAME)) {
         for(Mob â˜ƒxx : this.navigatingMobs) {
            PathNavigation â˜ƒxxx = â˜ƒxx.getNavigation();
            if (!â˜ƒxxx.hasDelayedRecomputation()) {
               â˜ƒxxx.recomputePath(â˜ƒ);
            }
         }
      }
   }

   @Override
   public void broadcastEntityEvent(Entity var1, byte var2) {
      this.getChunkSource().broadcastAndSend(â˜ƒ, new ClientboundEntityEventPacket(â˜ƒ, â˜ƒ));
   }

   public ServerChunkCache getChunkSource() {
      return this.chunkSource;
   }

   @Override
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
      â˜ƒ.finalizeExplosion(false);
      if (â˜ƒ == Explosion.BlockInteraction.NONE) {
         â˜ƒ.clearToBlow();
      }

      for(ServerPlayer â˜ƒ : this.players) {
         if (â˜ƒ.distanceToSqr(â˜ƒ, â˜ƒ, â˜ƒ) < 4096.0) {
            â˜ƒ.connection.send(new ClientboundExplodePacket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getToBlow(), (Vec3)â˜ƒ.getHitPlayers().get(â˜ƒ)));
         }
      }

      return â˜ƒ;
   }

   @Override
   public void blockEvent(BlockPos var1, Block var2, int var3, int var4) {
      this.blockEvents.add(new BlockEventData(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   private void runBlockEvents() {
      while(!this.blockEvents.isEmpty()) {
         BlockEventData â˜ƒ = this.blockEvents.removeFirst();
         if (this.doBlockEvent(â˜ƒ)) {
            this.server
               .getPlayerList()
               .broadcast(
                  null,
                  (double)â˜ƒ.getPos().getX(),
                  (double)â˜ƒ.getPos().getY(),
                  (double)â˜ƒ.getPos().getZ(),
                  64.0,
                  this.dimension(),
                  new ClientboundBlockEventPacket(â˜ƒ.getPos(), â˜ƒ.getBlock(), â˜ƒ.getParamA(), â˜ƒ.getParamB())
               );
         }
      }
   }

   private boolean doBlockEvent(BlockEventData var1) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ.getPos());
      return â˜ƒ.is(â˜ƒ.getBlock()) ? â˜ƒ.triggerEvent(this, â˜ƒ.getPos(), â˜ƒ.getParamA(), â˜ƒ.getParamB()) : false;
   }

   public ServerTickList<Block> getBlockTicks() {
      return this.blockTicks;
   }

   public ServerTickList<Fluid> getLiquidTicks() {
      return this.liquidTicks;
   }

   @Nonnull
   @Override
   public MinecraftServer getServer() {
      return this.server;
   }

   public PortalForcer getPortalForcer() {
      return this.portalForcer;
   }

   public StructureManager getStructureManager() {
      return this.server.getStructureManager();
   }

   public void sendVibrationParticle(VibrationPath var1) {
      BlockPos â˜ƒ = â˜ƒ.getOrigin();
      ClientboundAddVibrationSignalPacket â˜ƒx = new ClientboundAddVibrationSignalPacket(â˜ƒ);
      this.players.forEach(var3x -> this.sendParticles(var3x, false, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), â˜ƒ));
   }

   public <T extends ParticleOptions> int sendParticles(
      T var1, double var2, double var4, double var6, int var8, double var9, double var11, double var13, double var15
   ) {
      ClientboundLevelParticlesPacket â˜ƒ = new ClientboundLevelParticlesPacket(â˜ƒ, false, â˜ƒ, â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ);
      int â˜ƒx = 0;

      for(int â˜ƒxx = 0; â˜ƒxx < this.players.size(); ++â˜ƒxx) {
         ServerPlayer â˜ƒxxx = (ServerPlayer)this.players.get(â˜ƒxx);
         if (this.sendParticles(â˜ƒxxx, false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            ++â˜ƒx;
         }
      }

      return â˜ƒx;
   }

   public <T extends ParticleOptions> boolean sendParticles(
      ServerPlayer var1, T var2, boolean var3, double var4, double var6, double var8, int var10, double var11, double var13, double var15, double var17
   ) {
      Packet<?> â˜ƒ = new ClientboundLevelParticlesPacket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ);
      return this.sendParticles(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private boolean sendParticles(ServerPlayer var1, boolean var2, double var3, double var5, double var7, Packet<?> var9) {
      if (â˜ƒ.getLevel() != this) {
         return false;
      } else {
         BlockPos â˜ƒ = â˜ƒ.blockPosition();
         if (â˜ƒ.closerThan(new Vec3(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ ? 512.0 : 32.0)) {
            â˜ƒ.connection.send(â˜ƒ);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   @Override
   public Entity getEntity(int var1) {
      return this.getEntities().get(â˜ƒ);
   }

   @Deprecated
   @Nullable
   public Entity getEntityOrPart(int var1) {
      Entity â˜ƒ = this.getEntities().get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ : this.dragonParts.get(â˜ƒ);
   }

   @Nullable
   public Entity getEntity(UUID var1) {
      return this.getEntities().get(â˜ƒ);
   }

   @Nullable
   public BlockPos findNearestMapFeature(StructureFeature<?> var1, BlockPos var2, int var3, boolean var4) {
      return !this.server.getWorldData().worldGenSettings().generateFeatures()
         ? null
         : this.getChunkSource().getGenerator().findNearestMapFeature(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   public BlockPos findNearestBiome(Biome var1, BlockPos var2, int var3, int var4) {
      return this.getChunkSource()
         .getGenerator()
         .getBiomeSource()
         .findBiomeHorizontal(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ, â˜ƒ, var1x -> var1x == â˜ƒ, this.random, true);
   }

   @Override
   public RecipeManager getRecipeManager() {
      return this.server.getRecipeManager();
   }

   @Override
   public TagContainer getTagManager() {
      return this.server.getTags();
   }

   @Override
   public boolean noSave() {
      return this.noSave;
   }

   @Override
   public RegistryAccess registryAccess() {
      return this.server.registryAccess();
   }

   public DimensionDataStorage getDataStorage() {
      return this.getChunkSource().getDataStorage();
   }

   @Nullable
   @Override
   public MapItemSavedData getMapData(String var1) {
      return this.getServer().overworld().getDataStorage().get(MapItemSavedData::load, â˜ƒ);
   }

   @Override
   public void setMapData(String var1, MapItemSavedData var2) {
      this.getServer().overworld().getDataStorage().set(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getFreeMapId() {
      return this.getServer().overworld().getDataStorage().<MapIndex>computeIfAbsent(MapIndex::load, MapIndex::new, "idcounts").getFreeAuxValueForMap();
   }

   public void setDefaultSpawnPos(BlockPos var1, float var2) {
      ChunkPos â˜ƒ = new ChunkPos(new BlockPos(this.levelData.getXSpawn(), 0, this.levelData.getZSpawn()));
      this.levelData.setSpawn(â˜ƒ, â˜ƒ);
      this.getChunkSource().removeRegionTicket(TicketType.START, â˜ƒ, 11, Unit.INSTANCE);
      this.getChunkSource().addRegionTicket(TicketType.START, new ChunkPos(â˜ƒ), 11, Unit.INSTANCE);
      this.getServer().getPlayerList().broadcastAll(new ClientboundSetDefaultSpawnPositionPacket(â˜ƒ, â˜ƒ));
   }

   public BlockPos getSharedSpawnPos() {
      BlockPos â˜ƒ = new BlockPos(this.levelData.getXSpawn(), this.levelData.getYSpawn(), this.levelData.getZSpawn());
      if (!this.getWorldBorder().isWithinBounds(â˜ƒ)) {
         â˜ƒ = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
      }

      return â˜ƒ;
   }

   public float getSharedSpawnAngle() {
      return this.levelData.getSpawnAngle();
   }

   public LongSet getForcedChunks() {
      ForcedChunksSavedData â˜ƒ = this.getDataStorage().get(ForcedChunksSavedData::load, "chunks");
      return (LongSet)(â˜ƒ != null ? LongSets.unmodifiable(â˜ƒ.getChunks()) : LongSets.EMPTY_SET);
   }

   public boolean setChunkForced(int var1, int var2, boolean var3) {
      ForcedChunksSavedData â˜ƒx = this.getDataStorage().computeIfAbsent(ForcedChunksSavedData::load, ForcedChunksSavedData::new, "chunks");
      ChunkPos â˜ƒxx = new ChunkPos(â˜ƒ, â˜ƒ);
      long â˜ƒxxx = â˜ƒxx.toLong();
      boolean â˜ƒ;
      if (â˜ƒ) {
         â˜ƒ = â˜ƒx.getChunks().add(â˜ƒxxx);
         if (â˜ƒ) {
            this.getChunk(â˜ƒ, â˜ƒ);
         }
      } else {
         â˜ƒ = â˜ƒx.getChunks().remove(â˜ƒxxx);
      }

      â˜ƒx.setDirty(â˜ƒ);
      if (â˜ƒ) {
         this.getChunkSource().updateChunkForced(â˜ƒxx, â˜ƒ);
      }

      return â˜ƒ;
   }

   @Override
   public List<ServerPlayer> players() {
      return this.players;
   }

   @Override
   public void onBlockStateChange(BlockPos var1, BlockState var2, BlockState var3) {
      Optional<PoiType> â˜ƒ = PoiType.forState(â˜ƒ);
      Optional<PoiType> â˜ƒx = PoiType.forState(â˜ƒ);
      if (!Objects.equals(â˜ƒ, â˜ƒx)) {
         BlockPos â˜ƒxx = â˜ƒ.immutable();
         â˜ƒ.ifPresent(var2x -> this.getServer().execute(() -> {
               this.getPoiManager().remove(â˜ƒ);
               DebugPackets.sendPoiRemovedPacket(this, â˜ƒ);
            }));
         â˜ƒx.ifPresent(var2x -> this.getServer().execute(() -> {
               this.getPoiManager().add(â˜ƒ, var2x);
               DebugPackets.sendPoiAddedPacket(this, â˜ƒ);
            }));
      }
   }

   public PoiManager getPoiManager() {
      return this.getChunkSource().getPoiManager();
   }

   public boolean isVillage(BlockPos var1) {
      return this.isCloseToVillage(â˜ƒ, 1);
   }

   public boolean isVillage(SectionPos var1) {
      return this.isVillage(â˜ƒ.center());
   }

   public boolean isCloseToVillage(BlockPos var1, int var2) {
      if (â˜ƒ > 6) {
         return false;
      } else {
         return this.sectionsToVillage(SectionPos.of(â˜ƒ)) <= â˜ƒ;
      }
   }

   public int sectionsToVillage(SectionPos var1) {
      return this.getPoiManager().sectionsToVillage(â˜ƒ);
   }

   public Raids getRaids() {
      return this.raids;
   }

   @Nullable
   public Raid getRaidAt(BlockPos var1) {
      return this.raids.getNearbyRaid(â˜ƒ, 9216);
   }

   public boolean isRaided(BlockPos var1) {
      return this.getRaidAt(â˜ƒ) != null;
   }

   public void onReputationEvent(ReputationEventType var1, Entity var2, ReputationEventHandler var3) {
      â˜ƒ.onReputationEventFrom(â˜ƒ, â˜ƒ);
   }

   public void saveDebugReport(Path var1) throws IOException {
      ChunkMap â˜ƒ = this.getChunkSource().chunkMap;
      Writer â˜ƒx = Files.newBufferedWriter(â˜ƒ.resolve("stats.txt"));

      try {
         â˜ƒx.write(String.format("spawning_chunks: %d\n", â˜ƒ.getDistanceManager().getNaturalSpawnChunkCount()));
         NaturalSpawner.SpawnState â˜ƒxx = this.getChunkSource().getLastSpawnState();
         if (â˜ƒxx != null) {
            for(Entry<MobCategory> â˜ƒxxx : â˜ƒxx.getMobCategoryCounts().object2IntEntrySet()) {
               â˜ƒx.write(String.format("spawn_count.%s: %d\n", ((MobCategory)â˜ƒxxx.getKey()).getName(), â˜ƒxxx.getIntValue()));
            }
         }

         â˜ƒx.write(String.format("entities: %s\n", this.entityManager.gatherStats()));
         â˜ƒx.write(String.format("block_entity_tickers: %d\n", this.blockEntityTickers.size()));
         â˜ƒx.write(String.format("block_ticks: %d\n", this.getBlockTicks().size()));
         â˜ƒx.write(String.format("fluid_ticks: %d\n", this.getLiquidTicks().size()));
         â˜ƒx.write("distance_manager: " + â˜ƒ.getDistanceManager().getDebugStatus() + "\n");
         â˜ƒx.write(String.format("pending_tasks: %d\n", this.getChunkSource().getPendingTasksCount()));
      } catch (Throwable var22) {
         if (â˜ƒx != null) {
            try {
               â˜ƒx.close();
            } catch (Throwable var16) {
               var22.addSuppressed(var16);
            }
         }

         throw var22;
      }

      if (â˜ƒx != null) {
         â˜ƒx.close();
      }

      CrashReport â˜ƒxx = new CrashReport("Level dump", new Exception("dummy"));
      this.fillReportDetails(â˜ƒxx);
      Writer â˜ƒxxx = Files.newBufferedWriter(â˜ƒ.resolve("example_crash.txt"));

      try {
         â˜ƒxxx.write(â˜ƒxx.getFriendlyReport());
      } catch (Throwable var21) {
         if (â˜ƒxxx != null) {
            try {
               â˜ƒxxx.close();
            } catch (Throwable var15) {
               var21.addSuppressed(var15);
            }
         }

         throw var21;
      }

      if (â˜ƒxxx != null) {
         â˜ƒxxx.close();
      }

      Path â˜ƒxxxx = â˜ƒ.resolve("chunks.csv");
      Writer â˜ƒxxxxx = Files.newBufferedWriter(â˜ƒxxxx);

      try {
         â˜ƒ.dumpChunks(â˜ƒxxxxx);
      } catch (Throwable var20) {
         if (â˜ƒxxxxx != null) {
            try {
               â˜ƒxxxxx.close();
            } catch (Throwable var14) {
               var20.addSuppressed(var14);
            }
         }

         throw var20;
      }

      if (â˜ƒxxxxx != null) {
         â˜ƒxxxxx.close();
      }

      Path â˜ƒxxxxxx = â˜ƒ.resolve("entity_chunks.csv");
      Writer â˜ƒxxxxxxx = Files.newBufferedWriter(â˜ƒxxxxxx);

      try {
         this.entityManager.dumpSections(â˜ƒxxxxxxx);
      } catch (Throwable var19) {
         if (â˜ƒxxxxxxx != null) {
            try {
               â˜ƒxxxxxxx.close();
            } catch (Throwable var13) {
               var19.addSuppressed(var13);
            }
         }

         throw var19;
      }

      if (â˜ƒxxxxxxx != null) {
         â˜ƒxxxxxxx.close();
      }

      Path â˜ƒxxxxxxxx = â˜ƒ.resolve("entities.csv");
      Writer â˜ƒxxxxxxxxx = Files.newBufferedWriter(â˜ƒxxxxxxxx);

      try {
         dumpEntities(â˜ƒxxxxxxxxx, this.getEntities().getAll());
      } catch (Throwable var18) {
         if (â˜ƒxxxxxxxxx != null) {
            try {
               â˜ƒxxxxxxxxx.close();
            } catch (Throwable var12) {
               var18.addSuppressed(var12);
            }
         }

         throw var18;
      }

      if (â˜ƒxxxxxxxxx != null) {
         â˜ƒxxxxxxxxx.close();
      }

      Path â˜ƒxxxxxxxxxx = â˜ƒ.resolve("block_entities.csv");
      Writer â˜ƒxxxxxxxxxxx = Files.newBufferedWriter(â˜ƒxxxxxxxxxx);

      try {
         this.dumpBlockEntityTickers(â˜ƒxxxxxxxxxxx);
      } catch (Throwable var17) {
         if (â˜ƒxxxxxxxxxxx != null) {
            try {
               â˜ƒxxxxxxxxxxx.close();
            } catch (Throwable var11) {
               var17.addSuppressed(var11);
            }
         }

         throw var17;
      }

      if (â˜ƒxxxxxxxxxxx != null) {
         â˜ƒxxxxxxxxxxx.close();
      }
   }

   private static void dumpEntities(Writer var0, Iterable<Entity> var1) throws IOException {
      CsvOutput â˜ƒ = CsvOutput.builder()
         .addColumn("x")
         .addColumn("y")
         .addColumn("z")
         .addColumn("uuid")
         .addColumn("type")
         .addColumn("alive")
         .addColumn("display_name")
         .addColumn("custom_name")
         .build(â˜ƒ);

      for(Entity â˜ƒx : â˜ƒ) {
         Component â˜ƒxx = â˜ƒx.getCustomName();
         Component â˜ƒxxx = â˜ƒx.getDisplayName();
         â˜ƒ.writeRow(
            â˜ƒx.getX(),
            â˜ƒx.getY(),
            â˜ƒx.getZ(),
            â˜ƒx.getUUID(),
            Registry.ENTITY_TYPE.getKey(â˜ƒx.getType()),
            â˜ƒx.isAlive(),
            â˜ƒxxx.getString(),
            â˜ƒxx != null ? â˜ƒxx.getString() : null
         );
      }
   }

   private void dumpBlockEntityTickers(Writer var1) throws IOException {
      CsvOutput â˜ƒ = CsvOutput.builder().addColumn("x").addColumn("y").addColumn("z").addColumn("type").build(â˜ƒ);

      for(TickingBlockEntity â˜ƒx : this.blockEntityTickers) {
         BlockPos â˜ƒxx = â˜ƒx.getPos();
         â˜ƒ.writeRow(â˜ƒxx.getX(), â˜ƒxx.getY(), â˜ƒxx.getZ(), â˜ƒx.getType());
      }
   }

   @VisibleForTesting
   public void clearBlockEvents(BoundingBox var1) {
      this.blockEvents.removeIf(var1x -> â˜ƒ.isInside(var1x.getPos()));
   }

   @Override
   public void blockUpdated(BlockPos var1, Block var2) {
      if (!this.isDebug()) {
         this.updateNeighborsAt(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public float getShade(Direction var1, boolean var2) {
      return 1.0F;
   }

   public Iterable<Entity> getAllEntities() {
      return this.getEntities().getAll();
   }

   public String toString() {
      return "ServerLevel[" + this.serverLevelData.getLevelName() + "]";
   }

   public boolean isFlat() {
      return this.server.getWorldData().worldGenSettings().isFlatWorld();
   }

   @Override
   public long getSeed() {
      return this.server.getWorldData().worldGenSettings().seed();
   }

   @Nullable
   public EndDragonFight dragonFight() {
      return this.dragonFight;
   }

   @Override
   public Stream<? extends StructureStart<?>> startsForFeature(SectionPos var1, StructureFeature<?> var2) {
      return this.structureFeatureManager().startsForFeature(â˜ƒ, â˜ƒ);
   }

   @Override
   public ServerLevel getLevel() {
      return this;
   }

   @VisibleForTesting
   public String getWatchdogStats() {
      return String.format(
         "players: %s, entities: %s [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s",
         this.players.size(),
         this.entityManager.gatherStats(),
         getTypeCount(this.entityManager.getEntityGetter().getAll(), var0 -> Registry.ENTITY_TYPE.getKey(var0.getType()).toString()),
         this.blockEntityTickers.size(),
         getTypeCount(this.blockEntityTickers, TickingBlockEntity::getType),
         this.getBlockTicks().size(),
         this.getLiquidTicks().size(),
         this.gatherChunkSourceStats()
      );
   }

   private static <T> String getTypeCount(Iterable<T> var0, Function<T, String> var1) {
      try {
         Object2IntOpenHashMap<String> â˜ƒ = new Object2IntOpenHashMap();

         for(T â˜ƒx : â˜ƒ) {
            String â˜ƒxx = (String)â˜ƒ.apply(â˜ƒx);
            â˜ƒ.addTo(â˜ƒxx, 1);
         }

         return (String)â˜ƒ.object2IntEntrySet()
            .stream()
            .sorted(Comparator.comparing(Entry::getIntValue).reversed())
            .limit(5L)
            .map(var0x -> (String)var0x.getKey() + ":" + var0x.getIntValue())
            .collect(Collectors.joining(","));
      } catch (Exception var6) {
         return "";
      }
   }

   public static void makeObsidianPlatform(ServerLevel var0) {
      BlockPos â˜ƒ = END_SPAWN_POINT;
      int â˜ƒx = â˜ƒ.getX();
      int â˜ƒxx = â˜ƒ.getY() - 2;
      int â˜ƒxxx = â˜ƒ.getZ();
      BlockPos.betweenClosed(â˜ƒx - 2, â˜ƒxx + 1, â˜ƒxxx - 2, â˜ƒx + 2, â˜ƒxx + 3, â˜ƒxxx + 2)
         .forEach(var1x -> â˜ƒ.setBlockAndUpdate(var1x, Blocks.AIR.defaultBlockState()));
      BlockPos.betweenClosed(â˜ƒx - 2, â˜ƒxx, â˜ƒxxx - 2, â˜ƒx + 2, â˜ƒxx, â˜ƒxxx + 2)
         .forEach(var1x -> â˜ƒ.setBlockAndUpdate(var1x, Blocks.OBSIDIAN.defaultBlockState()));
   }

   @Override
   protected LevelEntityGetter<Entity> getEntities() {
      return this.entityManager.getEntityGetter();
   }

   public void addLegacyChunkEntities(Stream<Entity> var1) {
      this.entityManager.addLegacyChunkEntities(â˜ƒ);
   }

   public void addWorldGenChunkEntities(Stream<Entity> var1) {
      this.entityManager.addWorldGenChunkEntities(â˜ƒ);
   }

   @Override
   public void close() throws IOException {
      super.close();
      this.entityManager.close();
   }

   @Override
   public String gatherChunkSourceStats() {
      return "Chunks[S] W: " + this.chunkSource.gatherStats() + " E: " + this.entityManager.gatherStats();
   }

   public boolean areEntitiesLoaded(long var1) {
      return this.entityManager.areEntitiesLoaded(â˜ƒ);
   }

   public boolean isPositionTickingWithEntitiesLoaded(BlockPos var1) {
      long â˜ƒ = ChunkPos.asLong(â˜ƒ);
      return this.chunkSource.isPositionTicking(â˜ƒ) && this.areEntitiesLoaded(â˜ƒ);
   }

   public boolean isPositionEntityTicking(BlockPos var1) {
      return this.entityManager.isPositionTicking(â˜ƒ);
   }

   public boolean isPositionEntityTicking(ChunkPos var1) {
      return this.entityManager.isPositionTicking(â˜ƒ);
   }

   final class EntityCallbacks implements LevelCallback<Entity> {
      public void onCreated(Entity var1) {
      }

      public void onDestroyed(Entity var1) {
         ServerLevel.this.getScoreboard().entityRemoved(â˜ƒ);
      }

      public void onTickingStart(Entity var1) {
         ServerLevel.this.entityTickList.add(â˜ƒ);
      }

      public void onTickingEnd(Entity var1) {
         ServerLevel.this.entityTickList.remove(â˜ƒ);
      }

      public void onTrackingStart(Entity var1) {
         ServerLevel.this.getChunkSource().addEntity(â˜ƒ);
         if (â˜ƒ instanceof ServerPlayer) {
            ServerLevel.this.players.add((ServerPlayer)â˜ƒ);
            ServerLevel.this.updateSleepingPlayerList();
         }

         if (â˜ƒ instanceof Mob) {
            ServerLevel.this.navigatingMobs.add((Mob)â˜ƒ);
         }

         if (â˜ƒ instanceof EnderDragon) {
            for(EnderDragonPart â˜ƒ : ((EnderDragon)â˜ƒ).getSubEntities()) {
               ServerLevel.this.dragonParts.put(â˜ƒ.getId(), â˜ƒ);
            }
         }
      }

      public void onTrackingEnd(Entity var1) {
         ServerLevel.this.getChunkSource().removeEntity(â˜ƒ);
         if (â˜ƒ instanceof ServerPlayer â˜ƒ) {
            ServerLevel.this.players.remove(â˜ƒ);
            ServerLevel.this.updateSleepingPlayerList();
         }

         if (â˜ƒ instanceof Mob) {
            ServerLevel.this.navigatingMobs.remove(â˜ƒ);
         }

         if (â˜ƒ instanceof EnderDragon) {
            for(EnderDragonPart â˜ƒ : ((EnderDragon)â˜ƒ).getSubEntities()) {
               ServerLevel.this.dragonParts.remove(â˜ƒ.getId());
            }
         }

         GameEventListenerRegistrar â˜ƒ = â˜ƒ.getGameEventListenerRegistrar();
         if (â˜ƒ != null) {
            â˜ƒ.onListenerRemoved(â˜ƒ.level);
         }
      }
   }
}
