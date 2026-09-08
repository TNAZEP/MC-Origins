package net.minecraft.server;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Proxy;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.Features;
import net.minecraft.gametest.framework.GameTestTicker;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.DemoMode;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.network.ServerConnectionListener;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Mth;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.EmptyProfileResults;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.util.profiling.SingleTickProfiler;
import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.ServerMetricsSamplersProvider;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Snooper;
import net.minecraft.world.SnooperPopulator;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.ForcedChunksSavedData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.storage.CommandStorage;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer extends ReentrantBlockableEventLoop<TickTask> implements SnooperPopulator, CommandSource, AutoCloseable {
   static final Logger LOGGER = LogManager.getLogger();
   private static final float AVERAGE_TICK_TIME_SMOOTHING = 0.8F;
   private static final int TICK_STATS_SPAN = 100;
   public static final int MS_PER_TICK = 50;
   private static final int SNOOPER_UPDATE_INTERVAL = 6000;
   private static final int OVERLOADED_THRESHOLD = 2000;
   private static final int OVERLOADED_WARNING_INTERVAL = 15000;
   public static final String LEVEL_STORAGE_PROTOCOL = "level";
   public static final String LEVEL_STORAGE_SCHEMA = "level://";
   private static final long STATUS_EXPIRE_TIME_NS = 5000000000L;
   private static final int MAX_STATUS_PLAYER_SAMPLE = 12;
   public static final String MAP_RESOURCE_FILE = "resources.zip";
   public static final File USERID_CACHE_FILE = new File("usercache.json");
   public static final int START_CHUNK_RADIUS = 11;
   private static final int START_TICKING_CHUNK_COUNT = 441;
   private static final int AUTOSAVE_INTERVAL = 6000;
   private static final int MAX_TICK_LATENCY = 3;
   public static final int ABSOLUTE_MAX_WORLD_SIZE = 29999984;
   public static final LevelSettings DEMO_SETTINGS = new LevelSettings(
      "Demo World", GameType.SURVIVAL, false, Difficulty.NORMAL, false, new GameRules(), DataPackConfig.DEFAULT
   );
   private static final long DELAYED_TASKS_TICK_EXTENSION = 50L;
   protected final LevelStorageSource.LevelStorageAccess storageSource;
   protected final PlayerDataStorage playerDataStorage;
   private final Snooper snooper = new Snooper("server", this, Util.getMillis());
   private final List<Runnable> tickables = Lists.newArrayList();
   private MetricsRecorder metricsRecorder = InactiveMetricsRecorder.INSTANCE;
   private ProfilerFiller profiler = this.metricsRecorder.getProfiler();
   private Consumer<ProfileResults> onMetricsRecordingStopped = var1x -> this.stopRecordingMetrics();
   private Consumer<Path> onMetricsRecordingFinished = var0 -> {
   };
   private boolean willStartRecordingMetrics;
   @Nullable
   private MinecraftServer.TimeProfiler debugCommandProfiler;
   private boolean debugCommandProfilerDelayStart;
   private final ServerConnectionListener connection;
   private final ChunkProgressListenerFactory progressListenerFactory;
   private final ServerStatus status = new ServerStatus();
   private final Random random = new Random();
   private final DataFixer fixerUpper;
   private String localIp;
   private int port = -1;
   protected final RegistryAccess.RegistryHolder registryHolder;
   private final Map<ResourceKey<Level>, ServerLevel> levels = Maps.<ResourceKey<Level>, ServerLevel>newLinkedHashMap();
   private PlayerList playerList;
   private volatile boolean running = true;
   private boolean stopped;
   private int tickCount;
   protected final Proxy proxy;
   private boolean onlineMode;
   private boolean preventProxyConnections;
   private boolean pvp;
   private boolean allowFlight;
   @Nullable
   private String motd;
   private int playerIdleTimeout;
   public final long[] tickTimes = new long[100];
   @Nullable
   private KeyPair keyPair;
   @Nullable
   private String singleplayerName;
   private boolean isDemo;
   private String resourcePack = "";
   private String resourcePackHash = "";
   private volatile boolean isReady;
   private long lastOverloadWarning;
   private final MinecraftSessionService sessionService;
   @Nullable
   private final GameProfileRepository profileRepository;
   @Nullable
   private final GameProfileCache profileCache;
   private long lastServerStatus;
   private final Thread serverThread;
   private long nextTickTime = Util.getMillis();
   private long delayedTasksMaxNextTickTime;
   private boolean mayHaveDelayedTasks;
   private final PackRepository packRepository;
   private final ServerScoreboard scoreboard = new ServerScoreboard(this);
   @Nullable
   private CommandStorage commandStorage;
   private final CustomBossEvents customBossEvents = new CustomBossEvents();
   private final ServerFunctionManager functionManager;
   private final FrameTimer frameTimer = new FrameTimer();
   private boolean enforceWhitelist;
   private float averageTickTime;
   private final Executor executor;
   @Nullable
   private String serverId;
   private ServerResources resources;
   private final StructureManager structureManager;
   protected final WorldData worldData;

   public static <S extends MinecraftServer> S spin(Function<Thread, S> var0) {
      AtomicReference<S> â˜ƒ = new AtomicReference();
      Thread â˜ƒx = new Thread(() -> ((MinecraftServer)â˜ƒ.get()).runServer(), "Server thread");
      â˜ƒx.setUncaughtExceptionHandler((var0x, var1x) -> LOGGER.error(var1x));
      S â˜ƒxx = (S)â˜ƒ.apply(â˜ƒx);
      â˜ƒ.set(â˜ƒxx);
      â˜ƒx.start();
      return â˜ƒxx;
   }

   public MinecraftServer(
      Thread var1,
      RegistryAccess.RegistryHolder var2,
      LevelStorageSource.LevelStorageAccess var3,
      WorldData var4,
      PackRepository var5,
      Proxy var6,
      DataFixer var7,
      ServerResources var8,
      @Nullable MinecraftSessionService var9,
      @Nullable GameProfileRepository var10,
      @Nullable GameProfileCache var11,
      ChunkProgressListenerFactory var12
   ) {
      super("Server");
      this.registryHolder = â˜ƒ;
      this.worldData = â˜ƒ;
      this.proxy = â˜ƒ;
      this.packRepository = â˜ƒ;
      this.resources = â˜ƒ;
      this.sessionService = â˜ƒ;
      this.profileRepository = â˜ƒ;
      this.profileCache = â˜ƒ;
      if (â˜ƒ != null) {
         â˜ƒ.setExecutor(this);
      }

      this.connection = new ServerConnectionListener(this);
      this.progressListenerFactory = â˜ƒ;
      this.storageSource = â˜ƒ;
      this.playerDataStorage = â˜ƒ.createPlayerStorage();
      this.fixerUpper = â˜ƒ;
      this.functionManager = new ServerFunctionManager(this, â˜ƒ.getFunctionLibrary());
      this.structureManager = new StructureManager(â˜ƒ.getResourceManager(), â˜ƒ, â˜ƒ);
      this.serverThread = â˜ƒ;
      this.executor = Util.backgroundExecutor();
   }

   private void readScoreboard(DimensionDataStorage var1) {
      â˜ƒ.computeIfAbsent(this.getScoreboard()::createData, this.getScoreboard()::createData, "scoreboard");
   }

   protected abstract boolean initServer() throws IOException;

   public static void convertFromRegionFormatIfNeeded(LevelStorageSource.LevelStorageAccess var0) {
      if (â˜ƒ.requiresConversion()) {
         LOGGER.info("Converting map!");
         â˜ƒ.convertLevel(new ProgressListener() {
            private long timeStamp = Util.getMillis();

            @Override
            public void progressStartNoAbort(Component var1) {
            }

            @Override
            public void progressStart(Component var1) {
            }

            @Override
            public void progressStagePercentage(int var1) {
               if (Util.getMillis() - this.timeStamp >= 1000L) {
                  this.timeStamp = Util.getMillis();
                  MinecraftServer.LOGGER.info("Converting... {}%", â˜ƒ);
               }
            }

            @Override
            public void stop() {
            }

            @Override
            public void progressStage(Component var1) {
            }
         });
      }
   }

   protected void loadLevel() {
      this.detectBundledResources();
      this.worldData.setModdedInfo(this.getServerModName(), this.getModdedStatus().isPresent());
      ChunkProgressListener â˜ƒ = this.progressListenerFactory.create(11);
      this.createLevels(â˜ƒ);
      this.forceDifficulty();
      this.prepareLevels(â˜ƒ);
   }

   protected void forceDifficulty() {
   }

   protected void createLevels(ChunkProgressListener var1) {
      ServerLevelData â˜ƒxx = this.worldData.overworldData();
      WorldGenSettings â˜ƒxxx = this.worldData.worldGenSettings();
      boolean â˜ƒxxxx = â˜ƒxxx.isDebug();
      long â˜ƒxxxxx = â˜ƒxxx.seed();
      long â˜ƒxxxxxx = BiomeManager.obfuscateSeed(â˜ƒxxxxx);
      List<CustomSpawner> â˜ƒxxxxxxx = ImmutableList.of(
         new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(â˜ƒxx)
      );
      MappedRegistry<LevelStem> â˜ƒxxxxxxxx = â˜ƒxxx.dimensions();
      LevelStem â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx.get(LevelStem.OVERWORLD);
      ChunkGenerator â˜ƒ;
      DimensionType â˜ƒx;
      if (â˜ƒxxxxxxxxx == null) {
         â˜ƒx = this.registryHolder.<DimensionType>registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).getOrThrow(DimensionType.OVERWORLD_LOCATION);
         â˜ƒ = WorldGenSettings.makeDefaultOverworld(
            this.registryHolder.registryOrThrow(Registry.BIOME_REGISTRY),
            this.registryHolder.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY),
            new Random().nextLong()
         );
      } else {
         â˜ƒx = â˜ƒxxxxxxxxx.type();
         â˜ƒ = â˜ƒxxxxxxxxx.generator();
      }

      ServerLevel â˜ƒ = new ServerLevel(this, this.executor, this.storageSource, â˜ƒxx, Level.OVERWORLD, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, true);
      this.levels.put(Level.OVERWORLD, â˜ƒ);
      DimensionDataStorage â˜ƒx = â˜ƒ.getDataStorage();
      this.readScoreboard(â˜ƒx);
      this.commandStorage = new CommandStorage(â˜ƒx);
      WorldBorder â˜ƒxx = â˜ƒ.getWorldBorder();
      â˜ƒxx.applySettings(â˜ƒxx.getWorldBorder());
      if (!â˜ƒxx.isInitialized()) {
         try {
            setInitialSpawn(â˜ƒ, â˜ƒxx, â˜ƒxxx.generateBonusChest(), â˜ƒxxxx);
            â˜ƒxx.setInitialized(true);
            if (â˜ƒxxxx) {
               this.setupDebugLevel(this.worldData);
            }
         } catch (Throwable var26) {
            CrashReport â˜ƒxxx = CrashReport.forThrowable(var26, "Exception initializing level");

            try {
               â˜ƒ.fillReportDetails(â˜ƒxxx);
            } catch (Throwable var25) {
            }

            throw new ReportedException(â˜ƒxxx);
         }

         â˜ƒxx.setInitialized(true);
      }

      this.getPlayerList().setLevel(â˜ƒ);
      if (this.worldData.getCustomBossEvents() != null) {
         this.getCustomBossEvents().load(this.worldData.getCustomBossEvents());
      }

      for(Entry<ResourceKey<LevelStem>, LevelStem> â˜ƒ : â˜ƒxxxxxxxx.entrySet()) {
         ResourceKey<LevelStem> â˜ƒx = (ResourceKey)â˜ƒ.getKey();
         if (â˜ƒx != LevelStem.OVERWORLD) {
            ResourceKey<Level> â˜ƒxx = ResourceKey.create(Registry.DIMENSION_REGISTRY, â˜ƒx.location());
            DimensionType â˜ƒxxx = ((LevelStem)â˜ƒ.getValue()).type();
            ChunkGenerator â˜ƒxxxx = ((LevelStem)â˜ƒ.getValue()).generator();
            DerivedLevelData â˜ƒxxxxx = new DerivedLevelData(this.worldData, â˜ƒxx);
            ServerLevel â˜ƒxxxxxx = new ServerLevel(
               this, this.executor, this.storageSource, â˜ƒxxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒxxxx, â˜ƒxxxx, â˜ƒxxxxxx, ImmutableList.of(), false
            );
            â˜ƒxx.addListener(new BorderChangeListener.DelegateBorderChangeListener(â˜ƒxxxxxx.getWorldBorder()));
            this.levels.put(â˜ƒxx, â˜ƒxxxxxx);
         }
      }
   }

   private static void setInitialSpawn(ServerLevel var0, ServerLevelData var1, boolean var2, boolean var3) {
      if (â˜ƒ) {
         â˜ƒ.setSpawn(BlockPos.ZERO.above(80), 0.0F);
      } else {
         ChunkGenerator â˜ƒ = â˜ƒ.getChunkSource().getGenerator();
         BiomeSource â˜ƒx = â˜ƒ.getBiomeSource();
         Random â˜ƒxx = new Random(â˜ƒ.getSeed());
         BlockPos â˜ƒxxx = â˜ƒx.findBiomeHorizontal(0, â˜ƒ.getSeaLevel(), 0, 256, var0x -> var0x.getMobSettings().playerSpawnFriendly(), â˜ƒxx);
         ChunkPos â˜ƒxxxx = â˜ƒxxx == null ? new ChunkPos(0, 0) : new ChunkPos(â˜ƒxxx);
         if (â˜ƒxxx == null) {
            LOGGER.warn("Unable to find spawn biome");
         }

         boolean â˜ƒ = false;

         for(Block â˜ƒx : BlockTags.VALID_SPAWN.getValues()) {
            if (â˜ƒx.getSurfaceBlocks().contains(â˜ƒx.defaultBlockState())) {
               â˜ƒ = true;
               break;
            }
         }

         int â˜ƒx = â˜ƒ.getSpawnHeight(â˜ƒ);
         if (â˜ƒx < â˜ƒ.getMinBuildHeight()) {
            BlockPos â˜ƒxx = â˜ƒxxxx.getWorldPosition();
            â˜ƒx = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒxx.getX() + 8, â˜ƒxx.getZ() + 8);
         }

         â˜ƒ.setSpawn(â˜ƒxxxx.getWorldPosition().offset(8, â˜ƒx, 8), 0.0F);
         int â˜ƒx = 0;
         int â˜ƒxx = 0;
         int â˜ƒxxx = 0;
         int â˜ƒxxxx = -1;
         int â˜ƒxxxxx = 32;

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 1024; ++â˜ƒxxxxxx) {
            if (â˜ƒx > -16 && â˜ƒx <= 16 && â˜ƒxx > -16 && â˜ƒxx <= 16) {
               BlockPos â˜ƒxxxxxxx = PlayerRespawnLogic.getSpawnPosInChunk(â˜ƒ, new ChunkPos(â˜ƒxxxx.x + â˜ƒx, â˜ƒxxxx.z + â˜ƒxx), â˜ƒ);
               if (â˜ƒxxxxxxx != null) {
                  â˜ƒ.setSpawn(â˜ƒxxxxxxx, 0.0F);
                  break;
               }
            }

            if (â˜ƒx == â˜ƒxx || â˜ƒx < 0 && â˜ƒx == -â˜ƒxx || â˜ƒx > 0 && â˜ƒx == 1 - â˜ƒxx) {
               int â˜ƒxxxxxxx = â˜ƒxxx;
               â˜ƒxxx = -â˜ƒxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxx;
            }

            â˜ƒx += â˜ƒxxx;
            â˜ƒxx += â˜ƒxxxx;
         }

         if (â˜ƒ) {
            ConfiguredFeature<?, ?> â˜ƒxxxxxx = Features.BONUS_CHEST;
            â˜ƒxxxxxx.place(â˜ƒ, â˜ƒ, â˜ƒ.random, new BlockPos(â˜ƒ.getXSpawn(), â˜ƒ.getYSpawn(), â˜ƒ.getZSpawn()));
         }
      }
   }

   private void setupDebugLevel(WorldData var1) {
      â˜ƒ.setDifficulty(Difficulty.PEACEFUL);
      â˜ƒ.setDifficultyLocked(true);
      ServerLevelData â˜ƒ = â˜ƒ.overworldData();
      â˜ƒ.setRaining(false);
      â˜ƒ.setThundering(false);
      â˜ƒ.setClearWeatherTime(1000000000);
      â˜ƒ.setDayTime(6000L);
      â˜ƒ.setGameType(GameType.SPECTATOR);
   }

   private void prepareLevels(ChunkProgressListener var1) {
      ServerLevel â˜ƒ = this.overworld();
      LOGGER.info("Preparing start region for dimension {}", â˜ƒ.dimension().location());
      BlockPos â˜ƒx = â˜ƒ.getSharedSpawnPos();
      â˜ƒ.updateSpawnPos(new ChunkPos(â˜ƒx));
      ServerChunkCache â˜ƒxx = â˜ƒ.getChunkSource();
      â˜ƒxx.getLightEngine().setTaskPerBatch(500);
      this.nextTickTime = Util.getMillis();
      â˜ƒxx.addRegionTicket(TicketType.START, new ChunkPos(â˜ƒx), 11, Unit.INSTANCE);

      while(â˜ƒxx.getTickingGenerated() != 441) {
         this.nextTickTime = Util.getMillis() + 10L;
         this.waitUntilNextTick();
      }

      this.nextTickTime = Util.getMillis() + 10L;
      this.waitUntilNextTick();

      for(ServerLevel â˜ƒxxx : this.levels.values()) {
         ForcedChunksSavedData â˜ƒxxxx = â˜ƒxxx.getDataStorage().get(ForcedChunksSavedData::load, "chunks");
         if (â˜ƒxxxx != null) {
            LongIterator â˜ƒxxxxx = â˜ƒxxxx.getChunks().iterator();

            while(â˜ƒxxxxx.hasNext()) {
               long â˜ƒxxxxxx = â˜ƒxxxxx.nextLong();
               ChunkPos â˜ƒxxxxxxx = new ChunkPos(â˜ƒxxxxxx);
               â˜ƒxxx.getChunkSource().updateChunkForced(â˜ƒxxxxxxx, true);
            }
         }
      }

      this.nextTickTime = Util.getMillis() + 10L;
      this.waitUntilNextTick();
      â˜ƒ.stop();
      â˜ƒxx.getLightEngine().setTaskPerBatch(5);
      this.updateMobSpawningFlags();
   }

   protected void detectBundledResources() {
      File â˜ƒ = this.storageSource.getLevelPath(LevelResource.MAP_RESOURCE_FILE).toFile();
      if (â˜ƒ.isFile()) {
         String â˜ƒx = this.storageSource.getLevelId();

         try {
            this.setResourcePack("level://" + URLEncoder.encode(â˜ƒx, StandardCharsets.UTF_8.toString()) + "/resources.zip", "");
         } catch (UnsupportedEncodingException var4) {
            LOGGER.warn("Something went wrong url encoding {}", â˜ƒx);
         }
      }
   }

   public GameType getDefaultGameType() {
      return this.worldData.getGameType();
   }

   public boolean isHardcore() {
      return this.worldData.isHardcore();
   }

   public abstract int getOperatorUserPermissionLevel();

   public abstract int getFunctionCompilationLevel();

   public abstract boolean shouldRconBroadcast();

   public boolean saveAllChunks(boolean var1, boolean var2, boolean var3) {
      boolean â˜ƒ = false;

      for(ServerLevel â˜ƒx : this.getAllLevels()) {
         if (!â˜ƒ) {
            LOGGER.info("Saving chunks for level '{}'/{}", â˜ƒx, â˜ƒx.dimension().location());
         }

         â˜ƒx.save(null, â˜ƒ, â˜ƒx.noSave && !â˜ƒ);
         â˜ƒ = true;
      }

      ServerLevel â˜ƒx = this.overworld();
      ServerLevelData â˜ƒxx = this.worldData.overworldData();
      â˜ƒxx.setWorldBorder(â˜ƒx.getWorldBorder().createSettings());
      this.worldData.setCustomBossEvents(this.getCustomBossEvents().save());
      this.storageSource.saveDataTag(this.registryHolder, this.worldData, this.getPlayerList().getSingleplayerData());
      if (â˜ƒ) {
         for(ServerLevel â˜ƒxxx : this.getAllLevels()) {
            LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", â˜ƒxxx.getChunkSource().chunkMap.getStorageName());
         }

         LOGGER.info("ThreadedAnvilChunkStorage: All dimensions are saved");
      }

      return â˜ƒ;
   }

   @Override
   public void close() {
      this.stopServer();
   }

   public void stopServer() {
      LOGGER.info("Stopping server");
      if (this.getConnection() != null) {
         this.getConnection().stop();
      }

      if (this.playerList != null) {
         LOGGER.info("Saving players");
         this.playerList.saveAll();
         this.playerList.removeAll();
      }

      LOGGER.info("Saving worlds");

      for(ServerLevel â˜ƒ : this.getAllLevels()) {
         if (â˜ƒ != null) {
            â˜ƒ.noSave = false;
         }
      }

      this.saveAllChunks(false, true, false);

      for(ServerLevel â˜ƒ : this.getAllLevels()) {
         if (â˜ƒ != null) {
            try {
               â˜ƒ.close();
            } catch (IOException var5) {
               LOGGER.error("Exception closing the level", var5);
            }
         }
      }

      if (this.snooper.isStarted()) {
         this.snooper.interrupt();
      }

      this.resources.close();

      try {
         this.storageSource.close();
      } catch (IOException var4) {
         LOGGER.error("Failed to unlock level {}", this.storageSource.getLevelId(), var4);
      }
   }

   public String getLocalIp() {
      return this.localIp;
   }

   public void setLocalIp(String var1) {
      this.localIp = â˜ƒ;
   }

   public boolean isRunning() {
      return this.running;
   }

   public void halt(boolean var1) {
      this.running = false;
      if (â˜ƒ) {
         try {
            this.serverThread.join();
         } catch (InterruptedException var3) {
            LOGGER.error("Error while shutting down", var3);
         }
      }
   }

   protected void runServer() {
      try {
         if (this.initServer()) {
            this.nextTickTime = Util.getMillis();
            this.status.setDescription(new TextComponent(this.motd));
            this.status
               .setVersion(new ServerStatus.Version(SharedConstants.getCurrentVersion().getName(), SharedConstants.getCurrentVersion().getProtocolVersion()));
            this.updateStatusIcon(this.status);

            while(this.running) {
               long â˜ƒ = Util.getMillis() - this.nextTickTime;
               if (â˜ƒ > 2000L && this.nextTickTime - this.lastOverloadWarning >= 15000L) {
                  long â˜ƒx = â˜ƒ / 50L;
                  LOGGER.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", â˜ƒ, â˜ƒx);
                  this.nextTickTime += â˜ƒx * 50L;
                  this.lastOverloadWarning = this.nextTickTime;
               }

               if (this.debugCommandProfilerDelayStart) {
                  this.debugCommandProfilerDelayStart = false;
                  this.debugCommandProfiler = new MinecraftServer.TimeProfiler(Util.getNanos(), this.tickCount);
               }

               this.nextTickTime += 50L;
               this.startMetricsRecordingTick();
               this.profiler.push("tick");
               this.tickServer(this::haveTime);
               this.profiler.popPush("nextTickWait");
               this.mayHaveDelayedTasks = true;
               this.delayedTasksMaxNextTickTime = Math.max(Util.getMillis() + 50L, this.nextTickTime);
               this.waitUntilNextTick();
               this.profiler.pop();
               this.endMetricsRecordingTick();
               this.isReady = true;
            }
         } else {
            this.onServerCrash(null);
         }
      } catch (Throwable var44) {
         LOGGER.error("Encountered an unexpected exception", var44);
         CrashReport â˜ƒ;
         if (var44 instanceof ReportedException) {
            â˜ƒ = ((ReportedException)var44).getReport();
         } else {
            â˜ƒ = new CrashReport("Exception in server tick loop", var44);
         }

         this.fillSystemReport(â˜ƒ.getSystemReport());
         File â˜ƒ = new File(
            new File(this.getServerDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt"
         );
         if (â˜ƒ.saveToFile(â˜ƒ)) {
            LOGGER.error("This crash report has been saved to: {}", â˜ƒ.getAbsolutePath());
         } else {
            LOGGER.error("We were unable to save this crash report to disk.");
         }

         this.onServerCrash(â˜ƒ);
      } finally {
         try {
            this.stopped = true;
            this.stopServer();
         } catch (Throwable var42) {
            LOGGER.error("Exception stopping the server", var42);
         } finally {
            this.onServerExit();
         }
      }
   }

   private boolean haveTime() {
      return this.runningTask() || Util.getMillis() < (this.mayHaveDelayedTasks ? this.delayedTasksMaxNextTickTime : this.nextTickTime);
   }

   protected void waitUntilNextTick() {
      this.runAllTasks();
      this.managedBlock(() -> !this.haveTime());
   }

   protected TickTask wrapRunnable(Runnable var1) {
      return new TickTask(this.tickCount, â˜ƒ);
   }

   protected boolean shouldRun(TickTask var1) {
      return â˜ƒ.getTick() + 3 < this.tickCount || this.haveTime();
   }

   @Override
   public boolean pollTask() {
      boolean â˜ƒ = this.pollTaskInternal();
      this.mayHaveDelayedTasks = â˜ƒ;
      return â˜ƒ;
   }

   private boolean pollTaskInternal() {
      if (super.pollTask()) {
         return true;
      } else {
         if (this.haveTime()) {
            for(ServerLevel â˜ƒ : this.getAllLevels()) {
               if (â˜ƒ.getChunkSource().pollTask()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected void doRunTask(TickTask var1) {
      this.getProfiler().incrementCounter("runTask");
      super.doRunTask(â˜ƒ);
   }

   private void updateStatusIcon(ServerStatus var1) {
      Optional<File> â˜ƒ = Optional.of(this.getFile("server-icon.png")).filter(File::isFile);
      if (!â˜ƒ.isPresent()) {
         â˜ƒ = this.storageSource.getIconFile().map(Path::toFile).filter(File::isFile);
      }

      â˜ƒ.ifPresent(var1x -> {
         try {
            BufferedImage â˜ƒ = ImageIO.read(var1x);
            Validate.validState(â˜ƒ.getWidth() == 64, "Must be 64 pixels wide");
            Validate.validState(â˜ƒ.getHeight() == 64, "Must be 64 pixels high");
            ByteArrayOutputStream â˜ƒx = new ByteArrayOutputStream();
            ImageIO.write(â˜ƒ, "PNG", â˜ƒx);
            byte[] â˜ƒxx = Base64.getEncoder().encode(â˜ƒx.toByteArray());
            â˜ƒ.setFavicon("data:image/png;base64," + new String(â˜ƒxx, StandardCharsets.UTF_8));
         } catch (Exception var5) {
            LOGGER.error("Couldn't load server icon", var5);
         }
      });
   }

   public Optional<Path> getWorldScreenshotFile() {
      return this.storageSource.getIconFile();
   }

   public File getServerDirectory() {
      return new File(".");
   }

   protected void onServerCrash(CrashReport var1) {
   }

   public void onServerExit() {
   }

   public void tickServer(BooleanSupplier var1) {
      long â˜ƒ = Util.getNanos();
      ++this.tickCount;
      this.tickChildren(â˜ƒ);
      if (â˜ƒ - this.lastServerStatus >= 5000000000L) {
         this.lastServerStatus = â˜ƒ;
         this.status.setPlayers(new ServerStatus.Players(this.getMaxPlayers(), this.getPlayerCount()));
         GameProfile[] â˜ƒx = new GameProfile[Math.min(this.getPlayerCount(), 12)];
         int â˜ƒxx = Mth.nextInt(this.random, 0, this.getPlayerCount() - â˜ƒx.length);

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx.length; ++â˜ƒxxx) {
            â˜ƒx[â˜ƒxxx] = ((ServerPlayer)this.playerList.getPlayers().get(â˜ƒxx + â˜ƒxxx)).getGameProfile();
         }

         Collections.shuffle(Arrays.asList(â˜ƒx));
         this.status.getPlayers().setSample(â˜ƒx);
      }

      if (this.tickCount % 6000 == 0) {
         LOGGER.debug("Autosave started");
         this.profiler.push("save");
         this.playerList.saveAll();
         this.saveAllChunks(true, false, false);
         this.profiler.pop();
         LOGGER.debug("Autosave finished");
      }

      this.profiler.push("snooper");
      if (!this.snooper.isStarted() && this.tickCount > 100) {
         this.snooper.start();
      }

      if (this.tickCount % 6000 == 0) {
         this.snooper.prepare();
      }

      this.profiler.pop();
      this.profiler.push("tallying");
      long â˜ƒ = this.tickTimes[this.tickCount % 100] = Util.getNanos() - â˜ƒ;
      this.averageTickTime = this.averageTickTime * 0.8F + (float)â˜ƒ / 1000000.0F * 0.19999999F;
      long â˜ƒx = Util.getNanos();
      this.frameTimer.logFrameDuration(â˜ƒx - â˜ƒ);
      this.profiler.pop();
   }

   public void tickChildren(BooleanSupplier var1) {
      this.profiler.push("commandFunctions");
      this.getFunctions().tick();
      this.profiler.popPush("levels");

      for(ServerLevel â˜ƒ : this.getAllLevels()) {
         this.profiler.push((Supplier<String>)(() -> â˜ƒ + " " + â˜ƒ.dimension().location()));
         if (this.tickCount % 20 == 0) {
            this.profiler.push("timeSync");
            this.playerList
               .broadcastAll(
                  new ClientboundSetTimePacket(â˜ƒ.getGameTime(), â˜ƒ.getDayTime(), â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)), â˜ƒ.dimension()
               );
            this.profiler.pop();
         }

         this.profiler.push("tick");

         try {
            â˜ƒ.tick(â˜ƒ);
         } catch (Throwable var6) {
            CrashReport â˜ƒx = CrashReport.forThrowable(var6, "Exception ticking world");
            â˜ƒ.fillReportDetails(â˜ƒx);
            throw new ReportedException(â˜ƒx);
         }

         this.profiler.pop();
         this.profiler.pop();
      }

      this.profiler.popPush("connection");
      this.getConnection().tick();
      this.profiler.popPush("players");
      this.playerList.tick();
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         GameTestTicker.SINGLETON.tick();
      }

      this.profiler.popPush("server gui refresh");

      for(int â˜ƒ = 0; â˜ƒ < this.tickables.size(); ++â˜ƒ) {
         ((Runnable)this.tickables.get(â˜ƒ)).run();
      }

      this.profiler.pop();
   }

   public boolean isNetherEnabled() {
      return true;
   }

   public void addTickable(Runnable var1) {
      this.tickables.add(â˜ƒ);
   }

   protected void setId(String var1) {
      this.serverId = â˜ƒ;
   }

   public boolean isShutdown() {
      return !this.serverThread.isAlive();
   }

   public File getFile(String var1) {
      return new File(this.getServerDirectory(), â˜ƒ);
   }

   public final ServerLevel overworld() {
      return (ServerLevel)this.levels.get(Level.OVERWORLD);
   }

   @Nullable
   public ServerLevel getLevel(ResourceKey<Level> var1) {
      return (ServerLevel)this.levels.get(â˜ƒ);
   }

   public Set<ResourceKey<Level>> levelKeys() {
      return this.levels.keySet();
   }

   public Iterable<ServerLevel> getAllLevels() {
      return this.levels.values();
   }

   public String getServerVersion() {
      return SharedConstants.getCurrentVersion().getName();
   }

   public int getPlayerCount() {
      return this.playerList.getPlayerCount();
   }

   public int getMaxPlayers() {
      return this.playerList.getMaxPlayers();
   }

   public String[] getPlayerNames() {
      return this.playerList.getPlayerNamesArray();
   }

   @DontObfuscate
   public String getServerModName() {
      return "vanilla";
   }

   public SystemReport fillSystemReport(SystemReport var1) {
      if (this.playerList != null) {
         â˜ƒ.setDetail(
            "Player Count",
            (Supplier<String>)(() -> this.playerList.getPlayerCount() + " / " + this.playerList.getMaxPlayers() + "; " + this.playerList.getPlayers())
         );
      }

      â˜ƒ.setDetail("Data Packs", (Supplier<String>)(() -> {
         StringBuilder â˜ƒ = new StringBuilder();

         for(Pack â˜ƒx : this.packRepository.getSelectedPacks()) {
            if (â˜ƒ.length() > 0) {
               â˜ƒ.append(", ");
            }

            â˜ƒ.append(â˜ƒx.getId());
            if (!â˜ƒx.getCompatibility().isCompatible()) {
               â˜ƒ.append(" (incompatible)");
            }
         }

         return â˜ƒ.toString();
      }));
      if (this.serverId != null) {
         â˜ƒ.setDetail("Server Id", (Supplier<String>)(() -> this.serverId));
      }

      return this.fillServerSystemReport(â˜ƒ);
   }

   public abstract SystemReport fillServerSystemReport(SystemReport var1);

   public abstract Optional<String> getModdedStatus();

   @Override
   public void sendMessage(Component var1, UUID var2) {
      LOGGER.info(â˜ƒ.getString());
   }

   public KeyPair getKeyPair() {
      return this.keyPair;
   }

   public int getPort() {
      return this.port;
   }

   public void setPort(int var1) {
      this.port = â˜ƒ;
   }

   public String getSingleplayerName() {
      return this.singleplayerName;
   }

   public void setSingleplayerName(String var1) {
      this.singleplayerName = â˜ƒ;
   }

   public boolean isSingleplayer() {
      return this.singleplayerName != null;
   }

   protected void initializeKeyPair() {
      LOGGER.info("Generating keypair");

      try {
         this.keyPair = Crypt.generateKeyPair();
      } catch (CryptException var2) {
         throw new IllegalStateException("Failed to generate key pair", var2);
      }
   }

   public void setDifficulty(Difficulty var1, boolean var2) {
      if (â˜ƒ || !this.worldData.isDifficultyLocked()) {
         this.worldData.setDifficulty(this.worldData.isHardcore() ? Difficulty.HARD : â˜ƒ);
         this.updateMobSpawningFlags();
         this.getPlayerList().getPlayers().forEach(this::sendDifficultyUpdate);
      }
   }

   public int getScaledTrackingDistance(int var1) {
      return â˜ƒ;
   }

   private void updateMobSpawningFlags() {
      for(ServerLevel â˜ƒ : this.getAllLevels()) {
         â˜ƒ.setSpawnSettings(this.isSpawningMonsters(), this.isSpawningAnimals());
      }
   }

   public void setDifficultyLocked(boolean var1) {
      this.worldData.setDifficultyLocked(â˜ƒ);
      this.getPlayerList().getPlayers().forEach(this::sendDifficultyUpdate);
   }

   private void sendDifficultyUpdate(ServerPlayer var1) {
      LevelData â˜ƒ = â˜ƒ.getLevel().getLevelData();
      â˜ƒ.connection.send(new ClientboundChangeDifficultyPacket(â˜ƒ.getDifficulty(), â˜ƒ.isDifficultyLocked()));
   }

   public boolean isSpawningMonsters() {
      return this.worldData.getDifficulty() != Difficulty.PEACEFUL;
   }

   public boolean isDemo() {
      return this.isDemo;
   }

   public void setDemo(boolean var1) {
      this.isDemo = â˜ƒ;
   }

   public String getResourcePack() {
      return this.resourcePack;
   }

   public String getResourcePackHash() {
      return this.resourcePackHash;
   }

   public void setResourcePack(String var1, String var2) {
      this.resourcePack = â˜ƒ;
      this.resourcePackHash = â˜ƒ;
   }

   @Override
   public void populateSnooper(Snooper var1) {
      â˜ƒ.setDynamicData("whitelist_enabled", false);
      â˜ƒ.setDynamicData("whitelist_count", 0);
      if (this.playerList != null) {
         â˜ƒ.setDynamicData("players_current", this.getPlayerCount());
         â˜ƒ.setDynamicData("players_max", this.getMaxPlayers());
         â˜ƒ.setDynamicData("players_seen", this.playerDataStorage.getSeenPlayers().length);
      }

      â˜ƒ.setDynamicData("uses_auth", this.onlineMode);
      â˜ƒ.setDynamicData("gui_state", this.hasGui() ? "enabled" : "disabled");
      â˜ƒ.setDynamicData("run_time", (Util.getMillis() - â˜ƒ.getStartupTime()) / 60L * 1000L);
      â˜ƒ.setDynamicData("avg_tick_ms", (int)(Mth.average(this.tickTimes) * 1.0E-6));
      int â˜ƒ = 0;

      for(ServerLevel â˜ƒx : this.getAllLevels()) {
         if (â˜ƒx != null) {
            â˜ƒ.setDynamicData("world[" + â˜ƒ + "][dimension]", â˜ƒx.dimension().location());
            â˜ƒ.setDynamicData("world[" + â˜ƒ + "][mode]", this.worldData.getGameType());
            â˜ƒ.setDynamicData("world[" + â˜ƒ + "][difficulty]", â˜ƒx.getDifficulty());
            â˜ƒ.setDynamicData("world[" + â˜ƒ + "][hardcore]", this.worldData.isHardcore());
            â˜ƒ.setDynamicData("world[" + â˜ƒ + "][height]", â˜ƒx.getMaxBuildHeight());
            â˜ƒ.setDynamicData("world[" + â˜ƒ + "][chunks_loaded]", â˜ƒx.getChunkSource().getLoadedChunksCount());
            ++â˜ƒ;
         }
      }

      â˜ƒ.setDynamicData("worlds", â˜ƒ);
   }

   @Override
   public void populateSnooperInitial(Snooper var1) {
      â˜ƒ.setFixedData("singleplayer", this.isSingleplayer());
      â˜ƒ.setFixedData("server_brand", this.getServerModName());
      â˜ƒ.setFixedData("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
      â˜ƒ.setFixedData("dedicated", this.isDedicatedServer());
   }

   @Override
   public boolean isSnooperEnabled() {
      return true;
   }

   public abstract boolean isDedicatedServer();

   public abstract int getRateLimitPacketsPerSecond();

   public boolean usesAuthentication() {
      return this.onlineMode;
   }

   public void setUsesAuthentication(boolean var1) {
      this.onlineMode = â˜ƒ;
   }

   public boolean getPreventProxyConnections() {
      return this.preventProxyConnections;
   }

   public void setPreventProxyConnections(boolean var1) {
      this.preventProxyConnections = â˜ƒ;
   }

   public boolean isSpawningAnimals() {
      return true;
   }

   public boolean areNpcsEnabled() {
      return true;
   }

   public abstract boolean isEpollEnabled();

   public boolean isPvpAllowed() {
      return this.pvp;
   }

   public void setPvpAllowed(boolean var1) {
      this.pvp = â˜ƒ;
   }

   public boolean isFlightAllowed() {
      return this.allowFlight;
   }

   public void setFlightAllowed(boolean var1) {
      this.allowFlight = â˜ƒ;
   }

   public abstract boolean isCommandBlockEnabled();

   public String getMotd() {
      return this.motd;
   }

   public void setMotd(String var1) {
      this.motd = â˜ƒ;
   }

   public boolean isStopped() {
      return this.stopped;
   }

   public PlayerList getPlayerList() {
      return this.playerList;
   }

   public void setPlayerList(PlayerList var1) {
      this.playerList = â˜ƒ;
   }

   public abstract boolean isPublished();

   public void setDefaultGameType(GameType var1) {
      this.worldData.setGameType(â˜ƒ);
   }

   @Nullable
   public ServerConnectionListener getConnection() {
      return this.connection;
   }

   public boolean isReady() {
      return this.isReady;
   }

   public boolean hasGui() {
      return false;
   }

   public boolean publishServer(@Nullable GameType var1, boolean var2, int var3) {
      return false;
   }

   public int getTickCount() {
      return this.tickCount;
   }

   public Snooper getSnooper() {
      return this.snooper;
   }

   public int getSpawnProtectionRadius() {
      return 16;
   }

   public boolean isUnderSpawnProtection(ServerLevel var1, BlockPos var2, Player var3) {
      return false;
   }

   public boolean repliesToStatus() {
      return true;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public int getPlayerIdleTimeout() {
      return this.playerIdleTimeout;
   }

   public void setPlayerIdleTimeout(int var1) {
      this.playerIdleTimeout = â˜ƒ;
   }

   public MinecraftSessionService getSessionService() {
      return this.sessionService;
   }

   public GameProfileRepository getProfileRepository() {
      return this.profileRepository;
   }

   public GameProfileCache getProfileCache() {
      return this.profileCache;
   }

   public ServerStatus getStatus() {
      return this.status;
   }

   public void invalidateStatus() {
      this.lastServerStatus = 0L;
   }

   public int getAbsoluteMaxWorldSize() {
      return 29999984;
   }

   @Override
   public boolean scheduleExecutables() {
      return super.scheduleExecutables() && !this.isStopped();
   }

   @Override
   public Thread getRunningThread() {
      return this.serverThread;
   }

   public int getCompressionThreshold() {
      return 256;
   }

   public long getNextTickTime() {
      return this.nextTickTime;
   }

   public DataFixer getFixerUpper() {
      return this.fixerUpper;
   }

   public int getSpawnRadius(@Nullable ServerLevel var1) {
      return â˜ƒ != null ? â˜ƒ.getGameRules().getInt(GameRules.RULE_SPAWN_RADIUS) : 10;
   }

   public ServerAdvancementManager getAdvancements() {
      return this.resources.getAdvancements();
   }

   public ServerFunctionManager getFunctions() {
      return this.functionManager;
   }

   public CompletableFuture<Void> reloadResources(Collection<String> var1) {
      CompletableFuture<Void> â˜ƒ = CompletableFuture.supplyAsync(
            () -> (ImmutableList)â˜ƒ.stream()
                  .map(this.packRepository::getPack)
                  .filter(Objects::nonNull)
                  .map(Pack::open)
                  .collect(ImmutableList.toImmutableList()),
            this
         )
         .thenCompose(
            var1x -> ServerResources.loadResources(
                  var1x,
                  this.registryHolder,
                  this.isDedicatedServer() ? Commands.CommandSelection.DEDICATED : Commands.CommandSelection.INTEGRATED,
                  this.getFunctionCompilationLevel(),
                  this.executor,
                  this
               )
         )
         .thenAcceptAsync(var2x -> {
            this.resources.close();
            this.resources = var2x;
            this.packRepository.setSelected(â˜ƒ);
            this.worldData.setDataPackConfig(getSelectedPacks(this.packRepository));
            var2x.updateGlobals();
            this.getPlayerList().saveAll();
            this.getPlayerList().reloadResources();
            this.functionManager.replaceLibrary(this.resources.getFunctionLibrary());
            this.structureManager.onResourceManagerReload(this.resources.getResourceManager());
         }, this);
      if (this.isSameThread()) {
         this.managedBlock(â˜ƒ::isDone);
      }

      return â˜ƒ;
   }

   public static DataPackConfig configurePackRepository(PackRepository var0, DataPackConfig var1, boolean var2) {
      â˜ƒ.reload();
      if (â˜ƒ) {
         â˜ƒ.setSelected(Collections.singleton("vanilla"));
         return new DataPackConfig(ImmutableList.of("vanilla"), ImmutableList.of());
      } else {
         Set<String> â˜ƒ = Sets.newLinkedHashSet();

         for(String â˜ƒx : â˜ƒ.getEnabled()) {
            if (â˜ƒ.isAvailable(â˜ƒx)) {
               â˜ƒ.add(â˜ƒx);
            } else {
               LOGGER.warn("Missing data pack {}", â˜ƒx);
            }
         }

         for(Pack â˜ƒx : â˜ƒ.getAvailablePacks()) {
            String â˜ƒxx = â˜ƒx.getId();
            if (!â˜ƒ.getDisabled().contains(â˜ƒxx) && !â˜ƒ.contains(â˜ƒxx)) {
               LOGGER.info("Found new data pack {}, loading it automatically", â˜ƒxx);
               â˜ƒ.add(â˜ƒxx);
            }
         }

         if (â˜ƒ.isEmpty()) {
            LOGGER.info("No datapacks selected, forcing vanilla");
            â˜ƒ.add("vanilla");
         }

         â˜ƒ.setSelected(â˜ƒ);
         return getSelectedPacks(â˜ƒ);
      }
   }

   private static DataPackConfig getSelectedPacks(PackRepository var0) {
      Collection<String> â˜ƒ = â˜ƒ.getSelectedIds();
      List<String> â˜ƒx = ImmutableList.copyOf(â˜ƒ);
      List<String> â˜ƒxx = (List)â˜ƒ.getAvailableIds().stream().filter(var1x -> !â˜ƒ.contains(var1x)).collect(ImmutableList.toImmutableList());
      return new DataPackConfig(â˜ƒx, â˜ƒxx);
   }

   public void kickUnlistedPlayers(CommandSourceStack var1) {
      if (this.isEnforceWhitelist()) {
         PlayerList â˜ƒ = â˜ƒ.getServer().getPlayerList();
         UserWhiteList â˜ƒx = â˜ƒ.getWhiteList();

         for(ServerPlayer â˜ƒxx : Lists.newArrayList(â˜ƒ.getPlayers())) {
            if (!â˜ƒx.isWhiteListed(â˜ƒxx.getGameProfile())) {
               â˜ƒxx.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.not_whitelisted"));
            }
         }
      }
   }

   public PackRepository getPackRepository() {
      return this.packRepository;
   }

   public Commands getCommands() {
      return this.resources.getCommands();
   }

   public CommandSourceStack createCommandSourceStack() {
      ServerLevel â˜ƒ = this.overworld();
      return new CommandSourceStack(
         this, â˜ƒ == null ? Vec3.ZERO : Vec3.atLowerCornerOf(â˜ƒ.getSharedSpawnPos()), Vec2.ZERO, â˜ƒ, 4, "Server", new TextComponent("Server"), this, null
      );
   }

   @Override
   public boolean acceptsSuccess() {
      return true;
   }

   @Override
   public boolean acceptsFailure() {
      return true;
   }

   @Override
   public abstract boolean shouldInformAdmins();

   public RecipeManager getRecipeManager() {
      return this.resources.getRecipeManager();
   }

   public TagContainer getTags() {
      return this.resources.getTags();
   }

   public ServerScoreboard getScoreboard() {
      return this.scoreboard;
   }

   public CommandStorage getCommandStorage() {
      if (this.commandStorage == null) {
         throw new NullPointerException("Called before server init");
      } else {
         return this.commandStorage;
      }
   }

   public LootTables getLootTables() {
      return this.resources.getLootTables();
   }

   public PredicateManager getPredicateManager() {
      return this.resources.getPredicateManager();
   }

   public ItemModifierManager getItemModifierManager() {
      return this.resources.getItemModifierManager();
   }

   public GameRules getGameRules() {
      return this.overworld().getGameRules();
   }

   public CustomBossEvents getCustomBossEvents() {
      return this.customBossEvents;
   }

   public boolean isEnforceWhitelist() {
      return this.enforceWhitelist;
   }

   public void setEnforceWhitelist(boolean var1) {
      this.enforceWhitelist = â˜ƒ;
   }

   public float getAverageTickTime() {
      return this.averageTickTime;
   }

   public int getProfilePermissions(GameProfile var1) {
      if (this.getPlayerList().isOp(â˜ƒ)) {
         ServerOpListEntry â˜ƒ = this.getPlayerList().getOps().get(â˜ƒ);
         if (â˜ƒ != null) {
            return â˜ƒ.getLevel();
         } else if (this.isSingleplayerOwner(â˜ƒ)) {
            return 4;
         } else if (this.isSingleplayer()) {
            return this.getPlayerList().isAllowCheatsForAllPlayers() ? 4 : 0;
         } else {
            return this.getOperatorUserPermissionLevel();
         }
      } else {
         return 0;
      }
   }

   public FrameTimer getFrameTimer() {
      return this.frameTimer;
   }

   public ProfilerFiller getProfiler() {
      return this.profiler;
   }

   public abstract boolean isSingleplayerOwner(GameProfile var1);

   public void dumpServerProperties(Path var1) throws IOException {
   }

   private void saveDebugReport(Path var1) {
      Path â˜ƒ = â˜ƒ.resolve("levels");

      try {
         for(Entry<ResourceKey<Level>, ServerLevel> â˜ƒx : this.levels.entrySet()) {
            ResourceLocation â˜ƒxx = ((ResourceKey)â˜ƒx.getKey()).location();
            Path â˜ƒxxx = â˜ƒ.resolve(â˜ƒxx.getNamespace()).resolve(â˜ƒxx.getPath());
            Files.createDirectories(â˜ƒxxx);
            ((ServerLevel)â˜ƒx.getValue()).saveDebugReport(â˜ƒxxx);
         }

         this.dumpGameRules(â˜ƒ.resolve("gamerules.txt"));
         this.dumpClasspath(â˜ƒ.resolve("classpath.txt"));
         this.dumpMiscStats(â˜ƒ.resolve("stats.txt"));
         this.dumpThreads(â˜ƒ.resolve("threads.txt"));
         this.dumpServerProperties(â˜ƒ.resolve("server.properties.txt"));
      } catch (IOException var7) {
         LOGGER.warn("Failed to save debug report", var7);
      }
   }

   private void dumpMiscStats(Path var1) throws IOException {
      Writer â˜ƒ = Files.newBufferedWriter(â˜ƒ);

      try {
         â˜ƒ.write(String.format("pending_tasks: %d\n", this.getPendingTasksCount()));
         â˜ƒ.write(String.format("average_tick_time: %f\n", this.getAverageTickTime()));
         â˜ƒ.write(String.format("tick_times: %s\n", Arrays.toString(this.tickTimes)));
         â˜ƒ.write(String.format("queue: %s\n", Util.backgroundExecutor()));
      } catch (Throwable var6) {
         if (â˜ƒ != null) {
            try {
               â˜ƒ.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }
         }

         throw var6;
      }

      if (â˜ƒ != null) {
         â˜ƒ.close();
      }
   }

   private void dumpGameRules(Path var1) throws IOException {
      Writer â˜ƒ = Files.newBufferedWriter(â˜ƒ);

      try {
         final List<String> â˜ƒx = Lists.newArrayList();
         final GameRules â˜ƒxx = this.getGameRules();
         GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
            @Override
            public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> var1, GameRules.Type<T> var2) {
               â˜ƒ.add(String.format("%s=%s\n", â˜ƒ.getId(), â˜ƒ.<T>getRule(â˜ƒ)));
            }
         });

         for(String â˜ƒxxx : â˜ƒx) {
            â˜ƒ.write(â˜ƒxxx);
         }
      } catch (Throwable var8) {
         if (â˜ƒ != null) {
            try {
               â˜ƒ.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (â˜ƒ != null) {
         â˜ƒ.close();
      }
   }

   private void dumpClasspath(Path var1) throws IOException {
      Writer â˜ƒ = Files.newBufferedWriter(â˜ƒ);

      try {
         String â˜ƒx = System.getProperty("java.class.path");
         String â˜ƒxx = System.getProperty("path.separator");

         for(String â˜ƒxxx : Splitter.on(â˜ƒxx).split(â˜ƒx)) {
            â˜ƒ.write(â˜ƒxxx);
            â˜ƒ.write("\n");
         }
      } catch (Throwable var8) {
         if (â˜ƒ != null) {
            try {
               â˜ƒ.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (â˜ƒ != null) {
         â˜ƒ.close();
      }
   }

   private void dumpThreads(Path var1) throws IOException {
      ThreadMXBean â˜ƒ = ManagementFactory.getThreadMXBean();
      ThreadInfo[] â˜ƒx = â˜ƒ.dumpAllThreads(true, true);
      Arrays.sort(â˜ƒx, Comparator.comparing(ThreadInfo::getThreadName));
      Writer â˜ƒxx = Files.newBufferedWriter(â˜ƒ);

      try {
         for(ThreadInfo â˜ƒxxx : â˜ƒx) {
            â˜ƒxx.write(â˜ƒxxx.toString());
            â˜ƒxx.write(10);
         }
      } catch (Throwable var10) {
         if (â˜ƒxx != null) {
            try {
               â˜ƒxx.close();
            } catch (Throwable var9) {
               var10.addSuppressed(var9);
            }
         }

         throw var10;
      }

      if (â˜ƒxx != null) {
         â˜ƒxx.close();
      }
   }

   private void startMetricsRecordingTick() {
      if (this.willStartRecordingMetrics) {
         this.metricsRecorder = ActiveMetricsRecorder.createStarted(
            new ServerMetricsSamplersProvider(Util.timeSource, this.isDedicatedServer()),
            Util.timeSource,
            Util.ioPool(),
            new MetricsPersister("server"),
            this.onMetricsRecordingStopped,
            var1 -> {
               this.executeBlocking(() -> this.saveDebugReport(var1.resolve("server")));
               this.onMetricsRecordingFinished.accept(var1);
            }
         );
         this.willStartRecordingMetrics = false;
      }

      this.profiler = SingleTickProfiler.decorateFiller(this.metricsRecorder.getProfiler(), SingleTickProfiler.createTickProfiler("Server"));
      this.metricsRecorder.startTick();
      this.profiler.startTick();
   }

   private void endMetricsRecordingTick() {
      this.profiler.endTick();
      this.metricsRecorder.endTick();
   }

   public boolean isRecordingMetrics() {
      return this.metricsRecorder.isRecording();
   }

   public void startRecordingMetrics(Consumer<ProfileResults> var1, Consumer<Path> var2) {
      this.onMetricsRecordingStopped = var2x -> {
         this.stopRecordingMetrics();
         â˜ƒ.accept(var2x);
      };
      this.onMetricsRecordingFinished = â˜ƒ;
      this.willStartRecordingMetrics = true;
   }

   public void stopRecordingMetrics() {
      this.metricsRecorder = InactiveMetricsRecorder.INSTANCE;
   }

   public void finishRecordingMetrics() {
      this.metricsRecorder.end();
   }

   public Path getWorldPath(LevelResource var1) {
      return this.storageSource.getLevelPath(â˜ƒ);
   }

   public boolean forceSynchronousWrites() {
      return true;
   }

   public StructureManager getStructureManager() {
      return this.structureManager;
   }

   public WorldData getWorldData() {
      return this.worldData;
   }

   public RegistryAccess registryAccess() {
      return this.registryHolder;
   }

   public TextFilter createTextFilterForPlayer(ServerPlayer var1) {
      return TextFilter.DUMMY;
   }

   public boolean isResourcePackRequired() {
      return false;
   }

   public ServerPlayerGameMode createGameModeForPlayer(ServerPlayer var1) {
      return (ServerPlayerGameMode)(this.isDemo() ? new DemoMode(â˜ƒ) : new ServerPlayerGameMode(â˜ƒ));
   }

   @Nullable
   public GameType getForcedGameType() {
      return null;
   }

   public ResourceManager getResourceManager() {
      return this.resources.getResourceManager();
   }

   @Nullable
   public Component getResourcePackPrompt() {
      return null;
   }

   public boolean isTimeProfilerRunning() {
      return this.debugCommandProfilerDelayStart || this.debugCommandProfiler != null;
   }

   public void startTimeProfiler() {
      this.debugCommandProfilerDelayStart = true;
   }

   public ProfileResults stopTimeProfiler() {
      if (this.debugCommandProfiler == null) {
         return EmptyProfileResults.EMPTY;
      } else {
         ProfileResults â˜ƒ = this.debugCommandProfiler.stop(Util.getNanos(), this.tickCount);
         this.debugCommandProfiler = null;
         return â˜ƒ;
      }
   }

   static class TimeProfiler {
      final long startNanos;
      final int startTick;

      TimeProfiler(long var1, int var3) {
         this.startNanos = â˜ƒ;
         this.startTick = â˜ƒ;
      }

      ProfileResults stop(final long var1, final int var3) {
         return new ProfileResults() {
            @Override
            public List<ResultField> getTimes(String var1x) {
               return Collections.emptyList();
            }

            @Override
            public boolean saveResults(Path var1x) {
               return false;
            }

            @Override
            public long getStartTimeNano() {
               return TimeProfiler.this.startNanos;
            }

            @Override
            public int getStartTimeTicks() {
               return TimeProfiler.this.startTick;
            }

            @Override
            public long getEndTimeNano() {
               return â˜ƒ;
            }

            @Override
            public int getEndTimeTicks() {
               return â˜ƒ;
            }

            @Override
            public String getProfilerResults() {
               return "";
            }
         };
      }
   }
}
