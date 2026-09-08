package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.OfflineSocialInteractions;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.GlDebug;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Function4;
import com.mojang.math.Matrix4f;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.FileUtil;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DatapackLoadFailureScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.OutOfMemoryScreen;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.profiling.ClientMetricsSamplersProvider;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.VirtualScreen;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.LegacyPackResourcesAdapter;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.client.resources.PackResourcesAdapterV4;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.searchtree.MutableSearchTree;
import net.minecraft.client.searchtree.ReloadableIdSearchTree;
import net.minecraft.client.searchtree.ReloadableSearchTree;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.KeybindComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.level.progress.ProcessorChunkProgressListener;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FileZipper;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MemoryReserve;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Unit;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.profiling.ContinuousProfiler;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.util.profiling.SingleTickProfiler;
import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Snooper;
import net.minecraft.world.SnooperPopulator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class Minecraft extends ReentrantBlockableEventLoop<Runnable> implements SnooperPopulator, WindowEventHandler {
   private static Minecraft instance;
   private static final Logger LOGGER = LogManager.getLogger();
   public static final boolean ON_OSX = Util.getPlatform() == Util.OS.OSX;
   private static final int MAX_TICKS_PER_UPDATE = 10;
   public static final ResourceLocation DEFAULT_FONT = new ResourceLocation("default");
   public static final ResourceLocation UNIFORM_FONT = new ResourceLocation("uniform");
   public static final ResourceLocation ALT_FONT = new ResourceLocation("alt");
   private static final CompletableFuture<Unit> RESOURCE_RELOAD_INITIAL_TASK = CompletableFuture.completedFuture(Unit.INSTANCE);
   private static final Component SOCIAL_INTERACTIONS_NOT_AVAILABLE = new TranslatableComponent("multiplayer.socialInteractions.not_available");
   public static final String UPDATE_DRIVERS_ADVICE = "Please make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).";
   private final File resourcePackDirectory;
   private final PropertyMap profileProperties;
   private final TextureManager textureManager;
   private final DataFixer fixerUpper;
   private final VirtualScreen virtualScreen;
   private final Window window;
   private final Timer timer = new Timer(20.0F, 0L);
   private final Snooper snooper = new Snooper("client", this, Util.getMillis());
   private final RenderBuffers renderBuffers;
   public final LevelRenderer levelRenderer;
   private final EntityRenderDispatcher entityRenderDispatcher;
   private final ItemRenderer itemRenderer;
   private final ItemInHandRenderer itemInHandRenderer;
   public final ParticleEngine particleEngine;
   private final SearchRegistry searchRegistry = new SearchRegistry();
   private final User user;
   public final Font font;
   public final GameRenderer gameRenderer;
   public final DebugRenderer debugRenderer;
   private final AtomicReference<StoringChunkProgressListener> progressListener = new AtomicReference();
   public final Gui gui;
   public final Options options;
   private final HotbarManager hotbarManager;
   public final MouseHandler mouseHandler;
   public final KeyboardHandler keyboardHandler;
   public final File gameDirectory;
   private final String launchedVersion;
   private final String versionType;
   private final Proxy proxy;
   private final LevelStorageSource levelSource;
   public final FrameTimer frameTimer = new FrameTimer();
   private final boolean is64bit;
   private final boolean demo;
   private final boolean allowsMultiplayer;
   private final boolean allowsChat;
   private final ReloadableResourceManager resourceManager;
   private final ClientPackSource clientPackSource;
   private final PackRepository resourcePackRepository;
   private final LanguageManager languageManager;
   private final BlockColors blockColors;
   private final ItemColors itemColors;
   private final RenderTarget mainRenderTarget;
   private final SoundManager soundManager;
   private final MusicManager musicManager;
   private final FontManager fontManager;
   private final SplashManager splashManager;
   private final GpuWarnlistManager gpuWarnlistManager;
   private final MinecraftSessionService minecraftSessionService;
   private final SocialInteractionsService socialInteractionsService;
   private final SkinManager skinManager;
   private final ModelManager modelManager;
   private final BlockRenderDispatcher blockRenderer;
   private final PaintingTextureManager paintingTextures;
   private final MobEffectTextureManager mobEffectTextures;
   private final ToastComponent toast;
   private final Game game = new Game(this);
   private final Tutorial tutorial;
   private final PlayerSocialManager playerSocialManager;
   private final EntityModelSet entityModels;
   private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
   @Nullable
   public MultiPlayerGameMode gameMode;
   @Nullable
   public ClientLevel level;
   @Nullable
   public LocalPlayer player;
   @Nullable
   private IntegratedServer singleplayerServer;
   @Nullable
   private ServerData currentServer;
   @Nullable
   private Connection pendingConnection;
   private boolean isLocalServer;
   @Nullable
   public Entity cameraEntity;
   @Nullable
   public Entity crosshairPickEntity;
   @Nullable
   public HitResult hitResult;
   private int rightClickDelay;
   protected int missTime;
   private boolean pause;
   private float pausePartialTick;
   private long lastNanoTime = Util.getNanos();
   private long lastTime;
   private int frames;
   public boolean noRender;
   @Nullable
   public Screen screen;
   @Nullable
   private Overlay overlay;
   private boolean connectedToRealms;
   private Thread gameThread;
   private volatile boolean running = true;
   @Nullable
   private CrashReport delayedCrash;
   private static int fps;
   public String fpsString = "";
   public boolean wireframe;
   public boolean chunkPath;
   public boolean chunkVisibility;
   public boolean smartCull = true;
   private boolean windowActive;
   private final Queue<Runnable> progressTasks = Queues.newConcurrentLinkedQueue();
   @Nullable
   private CompletableFuture<Void> pendingReload;
   @Nullable
   private TutorialToast socialInteractionsToast;
   private ProfilerFiller profiler = InactiveProfiler.INSTANCE;
   private int fpsPieRenderTicks;
   private final ContinuousProfiler fpsPieProfiler = new ContinuousProfiler(Util.timeSource, () -> this.fpsPieRenderTicks);
   @Nullable
   private ProfileResults fpsPieResults;
   private MetricsRecorder metricsRecorder = InactiveMetricsRecorder.INSTANCE;
   private final ResourceLoadStateTracker reloadStateTracker = new ResourceLoadStateTracker();
   private String debugPath = "root";

   public Minecraft(GameConfig var1) {
      super("Client");
      instance = this;
      this.gameDirectory = â˜ƒ.location.gameDirectory;
      File â˜ƒxx = â˜ƒ.location.assetDirectory;
      this.resourcePackDirectory = â˜ƒ.location.resourcePackDirectory;
      this.launchedVersion = â˜ƒ.game.launchVersion;
      this.versionType = â˜ƒ.game.versionType;
      this.profileProperties = â˜ƒ.user.profileProperties;
      this.clientPackSource = new ClientPackSource(new File(this.gameDirectory, "server-resource-packs"), â˜ƒ.location.getAssetIndex());
      this.resourcePackRepository = new PackRepository(
         Minecraft::createClientPackAdapter, this.clientPackSource, new FolderRepositorySource(this.resourcePackDirectory, PackSource.DEFAULT)
      );
      this.proxy = â˜ƒ.user.proxy;
      YggdrasilAuthenticationService â˜ƒxxx = new YggdrasilAuthenticationService(this.proxy);
      this.minecraftSessionService = â˜ƒxxx.createMinecraftSessionService();
      this.socialInteractionsService = this.createSocialInteractions(â˜ƒxxx, â˜ƒ);
      this.user = â˜ƒ.user.user;
      LOGGER.info("Setting user: {}", this.user.getName());
      LOGGER.debug("(Session ID is {})", this.user.getSessionId());
      this.demo = â˜ƒ.game.demo;
      this.allowsMultiplayer = !â˜ƒ.game.disableMultiplayer;
      this.allowsChat = !â˜ƒ.game.disableChat;
      this.is64bit = checkIs64Bit();
      this.singleplayerServer = null;
      String â˜ƒ;
      int â˜ƒx;
      if (this.allowsMultiplayer() && â˜ƒ.server.hostname != null) {
         â˜ƒ = â˜ƒ.server.hostname;
         â˜ƒx = â˜ƒ.server.port;
      } else {
         â˜ƒ = null;
         â˜ƒx = 0;
      }

      KeybindComponent.setKeyResolver(KeyMapping::createNameSupplier);
      this.fixerUpper = DataFixers.getDataFixer();
      this.toast = new ToastComponent(this);
      this.gameThread = Thread.currentThread();
      this.options = new Options(this, this.gameDirectory);
      this.tutorial = new Tutorial(this, this.options);
      this.hotbarManager = new HotbarManager(this.gameDirectory, this.fixerUpper);
      LOGGER.info("Backend library: {}", RenderSystem.getBackendDescription());
      DisplayData â˜ƒ;
      if (this.options.overrideHeight > 0 && this.options.overrideWidth > 0) {
         â˜ƒ = new DisplayData(
            this.options.overrideWidth, this.options.overrideHeight, â˜ƒ.display.fullscreenWidth, â˜ƒ.display.fullscreenHeight, â˜ƒ.display.isFullscreen
         );
      } else {
         â˜ƒ = â˜ƒ.display;
      }

      Util.timeSource = RenderSystem.initBackendSystem();
      this.virtualScreen = new VirtualScreen(this);
      this.window = this.virtualScreen.newWindow(â˜ƒ, this.options.fullscreenVideoModeString, this.createTitle());
      this.setWindowActive(true);

      try {
         InputStream â˜ƒ = this.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_16x16.png"));
         InputStream â˜ƒx = this.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_32x32.png"));
         this.window.setIcon(â˜ƒ, â˜ƒx);
      } catch (IOException var9) {
         LOGGER.error("Couldn't set icon", var9);
      }

      this.window.setFramerateLimit(this.options.framerateLimit);
      this.mouseHandler = new MouseHandler(this);
      this.mouseHandler.setup(this.window.getWindow());
      this.keyboardHandler = new KeyboardHandler(this);
      this.keyboardHandler.setup(this.window.getWindow());
      RenderSystem.initRenderer(this.options.glDebugVerbosity, false);
      this.mainRenderTarget = new MainTarget(this.window.getWidth(), this.window.getHeight());
      this.mainRenderTarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.mainRenderTarget.clear(ON_OSX);
      this.resourceManager = new SimpleReloadableResourceManager(PackType.CLIENT_RESOURCES);
      this.resourcePackRepository.reload();
      this.options.loadSelectedResourcePacks(this.resourcePackRepository);
      this.languageManager = new LanguageManager(this.options.languageCode);
      this.resourceManager.registerReloadListener(this.languageManager);
      this.textureManager = new TextureManager(this.resourceManager);
      this.resourceManager.registerReloadListener(this.textureManager);
      this.skinManager = new SkinManager(this.textureManager, new File(â˜ƒxx, "skins"), this.minecraftSessionService);
      this.levelSource = new LevelStorageSource(this.gameDirectory.toPath().resolve("saves"), this.gameDirectory.toPath().resolve("backups"), this.fixerUpper);
      this.soundManager = new SoundManager(this.resourceManager, this.options);
      this.resourceManager.registerReloadListener(this.soundManager);
      this.splashManager = new SplashManager(this.user);
      this.resourceManager.registerReloadListener(this.splashManager);
      this.musicManager = new MusicManager(this);
      this.fontManager = new FontManager(this.textureManager);
      this.font = this.fontManager.createFont();
      this.resourceManager.registerReloadListener(this.fontManager.getReloadListener());
      this.selectMainFont(this.isEnforceUnicode());
      this.resourceManager.registerReloadListener(new GrassColorReloadListener());
      this.resourceManager.registerReloadListener(new FoliageColorReloadListener());
      this.window.setErrorSection("Startup");
      RenderSystem.setupDefaultState(0, 0, this.window.getWidth(), this.window.getHeight());
      this.window.setErrorSection("Post startup");
      this.blockColors = BlockColors.createDefault();
      this.itemColors = ItemColors.createDefault(this.blockColors);
      this.modelManager = new ModelManager(this.textureManager, this.blockColors, this.options.mipmapLevels);
      this.resourceManager.registerReloadListener(this.modelManager);
      this.entityModels = new EntityModelSet();
      this.resourceManager.registerReloadListener(this.entityModels);
      this.blockEntityRenderDispatcher = new BlockEntityRenderDispatcher(this.font, this.entityModels, this::getBlockRenderer);
      this.resourceManager.registerReloadListener(this.blockEntityRenderDispatcher);
      BlockEntityWithoutLevelRenderer â˜ƒ = new BlockEntityWithoutLevelRenderer(this.blockEntityRenderDispatcher, this.entityModels);
      this.resourceManager.registerReloadListener(â˜ƒ);
      this.itemRenderer = new ItemRenderer(this.textureManager, this.modelManager, this.itemColors, â˜ƒ);
      this.entityRenderDispatcher = new EntityRenderDispatcher(this.textureManager, this.itemRenderer, this.font, this.options, this.entityModels);
      this.resourceManager.registerReloadListener(this.entityRenderDispatcher);
      this.itemInHandRenderer = new ItemInHandRenderer(this);
      this.resourceManager.registerReloadListener(this.itemRenderer);
      this.renderBuffers = new RenderBuffers();
      this.gameRenderer = new GameRenderer(this, this.resourceManager, this.renderBuffers);
      this.resourceManager.registerReloadListener(this.gameRenderer);
      this.playerSocialManager = new PlayerSocialManager(this, this.socialInteractionsService);
      this.blockRenderer = new BlockRenderDispatcher(this.modelManager.getBlockModelShaper(), â˜ƒ, this.blockColors);
      this.resourceManager.registerReloadListener(this.blockRenderer);
      this.levelRenderer = new LevelRenderer(this, this.renderBuffers);
      this.resourceManager.registerReloadListener(this.levelRenderer);
      this.createSearchTrees();
      this.resourceManager.registerReloadListener(this.searchRegistry);
      this.particleEngine = new ParticleEngine(this.level, this.textureManager);
      this.resourceManager.registerReloadListener(this.particleEngine);
      this.paintingTextures = new PaintingTextureManager(this.textureManager);
      this.resourceManager.registerReloadListener(this.paintingTextures);
      this.mobEffectTextures = new MobEffectTextureManager(this.textureManager);
      this.resourceManager.registerReloadListener(this.mobEffectTextures);
      this.gpuWarnlistManager = new GpuWarnlistManager();
      this.resourceManager.registerReloadListener(this.gpuWarnlistManager);
      this.gui = new Gui(this);
      this.debugRenderer = new DebugRenderer(this);
      RenderSystem.setErrorCallback(this::onFullscreenError);
      if (this.mainRenderTarget.width != this.window.getWidth() || this.mainRenderTarget.height != this.window.getHeight()) {
         StringBuilder â˜ƒx = new StringBuilder(
            "Recovering from unsupported resolution ("
               + this.window.getWidth()
               + "x"
               + this.window.getHeight()
               + ").\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions)."
         );
         if (GlDebug.isDebugEnabled()) {
            â˜ƒx.append("\n\nReported GL debug messages:\n").append(String.join("\n", GlDebug.getLastOpenGlDebugMessages()));
         }

         this.window.setWindowed(this.mainRenderTarget.width, this.mainRenderTarget.height);
         TinyFileDialogs.tinyfd_messageBox("Minecraft", â˜ƒx.toString(), "ok", "error", false);
      } else if (this.options.fullscreen && !this.window.isFullscreen()) {
         this.window.toggleFullScreen();
         this.options.fullscreen = this.window.isFullscreen();
      }

      this.window.updateVsync(this.options.enableVsync);
      this.window.updateRawMouseInput(this.options.rawMouseInput);
      this.window.setDefaultErrorCallback();
      this.resizeDisplay();
      this.gameRenderer.preloadUiShader(this.getClientPackSource().getVanillaPack());
      LoadingOverlay.registerTextures(this);
      List<PackResources> â˜ƒ = this.resourcePackRepository.openAllSelected();
      this.reloadStateTracker.startReload(ResourceLoadStateTracker.ReloadReason.INITIAL, â˜ƒ);
      this.setOverlay(
         new LoadingOverlay(
            this,
            this.resourceManager.createReload(Util.backgroundExecutor(), this, RESOURCE_RELOAD_INITIAL_TASK, â˜ƒ),
            var1x -> Util.ifElse(var1x, this::rollbackResourcePacks, () -> {
                  if (SharedConstants.IS_RUNNING_IN_IDE) {
                     this.selfTest();
                  }
      
                  this.reloadStateTracker.finishReload();
               }),
            false
         )
      );
      if (â˜ƒ != null) {
         ConnectScreen.startConnecting(new TitleScreen(), this, new ServerAddress(â˜ƒ, â˜ƒx), null);
      } else {
         this.setScreen(new TitleScreen(true));
      }
   }

   public void updateTitle() {
      this.window.setTitle(this.createTitle());
   }

   private String createTitle() {
      StringBuilder â˜ƒ = new StringBuilder("Minecraft");
      if (this.isProbablyModded()) {
         â˜ƒ.append("*");
      }

      â˜ƒ.append(" ");
      â˜ƒ.append(SharedConstants.getCurrentVersion().getName());
      ClientPacketListener â˜ƒ = this.getConnection();
      if (â˜ƒ != null && â˜ƒ.getConnection().isConnected()) {
         â˜ƒ.append(" - ");
         if (this.singleplayerServer != null && !this.singleplayerServer.isPublished()) {
            â˜ƒ.append(I18n.get("title.singleplayer"));
         } else if (this.isConnectedToRealms()) {
            â˜ƒ.append(I18n.get("title.multiplayer.realms"));
         } else if (this.singleplayerServer == null && (this.currentServer == null || !this.currentServer.isLan())) {
            â˜ƒ.append(I18n.get("title.multiplayer.other"));
         } else {
            â˜ƒ.append(I18n.get("title.multiplayer.lan"));
         }
      }

      return â˜ƒ.toString();
   }

   private SocialInteractionsService createSocialInteractions(YggdrasilAuthenticationService var1, GameConfig var2) {
      try {
         return â˜ƒ.createSocialInteractionsService(â˜ƒ.user.user.getAccessToken());
      } catch (AuthenticationException var4) {
         LOGGER.error("Failed to verify authentication", var4);
         return new OfflineSocialInteractions();
      }
   }

   public boolean isProbablyModded() {
      return !"vanilla".equals(ClientBrandRetriever.getClientModName()) || Minecraft.class.getSigners() == null;
   }

   private void rollbackResourcePacks(Throwable var1) {
      if (this.resourcePackRepository.getSelectedIds().size() > 1) {
         Component â˜ƒ;
         if (â˜ƒ instanceof SimpleReloadableResourceManager.ResourcePackLoadingFailure) {
            â˜ƒ = new TextComponent(((SimpleReloadableResourceManager.ResourcePackLoadingFailure)â˜ƒ).getPack().getName());
         } else {
            â˜ƒ = null;
         }

         this.clearResourcePacksOnError(â˜ƒ, â˜ƒ);
      } else {
         Util.throwAsRuntime(â˜ƒ);
      }
   }

   public void clearResourcePacksOnError(Throwable var1, @Nullable Component var2) {
      LOGGER.info("Caught error loading resourcepacks, removing all selected resourcepacks", â˜ƒ);
      this.reloadStateTracker.startRecovery(â˜ƒ);
      this.resourcePackRepository.setSelected(Collections.emptyList());
      this.options.resourcePacks.clear();
      this.options.incompatibleResourcePacks.clear();
      this.options.save();
      this.reloadResourcePacks(true).thenRun(() -> {
         ToastComponent â˜ƒ = this.getToasts();
         SystemToast.addOrUpdate(â˜ƒ, SystemToast.SystemToastIds.PACK_LOAD_FAILURE, new TranslatableComponent("resourcePack.load_fail"), â˜ƒ);
      });
   }

   public void run() {
      this.gameThread = Thread.currentThread();

      try {
         boolean â˜ƒ = false;

         while(this.running) {
            if (this.delayedCrash != null) {
               crash(this.delayedCrash);
               return;
            }

            try {
               SingleTickProfiler â˜ƒx = SingleTickProfiler.createTickProfiler("Renderer");
               boolean â˜ƒxx = this.shouldRenderFpsPie();
               this.profiler = this.constructProfiler(â˜ƒxx, â˜ƒx);
               this.profiler.startTick();
               this.metricsRecorder.startTick();
               this.runTick(!â˜ƒ);
               this.metricsRecorder.endTick();
               this.profiler.endTick();
               this.finishProfilers(â˜ƒxx, â˜ƒx);
            } catch (OutOfMemoryError var4) {
               if (â˜ƒ) {
                  throw var4;
               }

               this.emergencySave();
               this.setScreen(new OutOfMemoryScreen());
               System.gc();
               LOGGER.fatal("Out of memory", var4);
               â˜ƒ = true;
            }
         }
      } catch (ReportedException var5) {
         this.fillReport(var5.getReport());
         this.emergencySave();
         LOGGER.fatal("Reported exception thrown!", var5);
         crash(var5.getReport());
      } catch (Throwable var6) {
         CrashReport â˜ƒ = this.fillReport(new CrashReport("Unexpected error", var6));
         LOGGER.fatal("Unreported exception thrown!", var6);
         this.emergencySave();
         crash(â˜ƒ);
      }
   }

   void selectMainFont(boolean var1) {
      this.fontManager.setRenames(â˜ƒ ? ImmutableMap.of(DEFAULT_FONT, UNIFORM_FONT) : ImmutableMap.of());
   }

   private void createSearchTrees() {
      ReloadableSearchTree<ItemStack> â˜ƒ = new ReloadableSearchTree<>(
         var0 -> var0.getTooltipLines(null, TooltipFlag.Default.NORMAL)
               .stream()
               .map(var0x -> ChatFormatting.stripFormatting(var0x.getString()).trim())
               .filter(var0x -> !var0x.isEmpty()),
         var0 -> Stream.of(Registry.ITEM.getKey(var0.getItem()))
      );
      ReloadableIdSearchTree<ItemStack> â˜ƒx = new ReloadableIdSearchTree<>(var0 -> ItemTags.getAllTags().getMatchingTags(var0.getItem()).stream());
      NonNullList<ItemStack> â˜ƒxx = NonNullList.create();

      for(Item â˜ƒxxx : Registry.ITEM) {
         â˜ƒxxx.fillItemCategory(CreativeModeTab.TAB_SEARCH, â˜ƒxx);
      }

      â˜ƒxx.forEach(var2x -> {
         â˜ƒ.add(var2x);
         â˜ƒ.add(var2x);
      });
      ReloadableSearchTree<RecipeCollection> â˜ƒxxx = new ReloadableSearchTree<>(
         var0 -> var0.getRecipes()
               .stream()
               .flatMap(var0x -> var0x.getResultItem().getTooltipLines(null, TooltipFlag.Default.NORMAL).stream())
               .map(var0x -> ChatFormatting.stripFormatting(var0x.getString()).trim())
               .filter(var0x -> !var0x.isEmpty()),
         var0 -> var0.getRecipes().stream().map(var0x -> Registry.ITEM.getKey(var0x.getResultItem().getItem()))
      );
      this.searchRegistry.register(SearchRegistry.CREATIVE_NAMES, â˜ƒ);
      this.searchRegistry.register(SearchRegistry.CREATIVE_TAGS, â˜ƒx);
      this.searchRegistry.register(SearchRegistry.RECIPE_COLLECTIONS, â˜ƒxxx);
   }

   private void onFullscreenError(int var1, long var2) {
      this.options.enableVsync = false;
      this.options.save();
   }

   private static boolean checkIs64Bit() {
      String[] â˜ƒ = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for(String â˜ƒx : â˜ƒ) {
         String â˜ƒxx = System.getProperty(â˜ƒx);
         if (â˜ƒxx != null && â˜ƒxx.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public RenderTarget getMainRenderTarget() {
      return this.mainRenderTarget;
   }

   public String getLaunchedVersion() {
      return this.launchedVersion;
   }

   public String getVersionType() {
      return this.versionType;
   }

   public void delayCrash(CrashReport var1) {
      this.delayedCrash = â˜ƒ;
   }

   public static void crash(CrashReport var0) {
      File â˜ƒ = new File(getInstance().gameDirectory, "crash-reports");
      File â˜ƒx = new File(â˜ƒ, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
      Bootstrap.realStdoutPrintln(â˜ƒ.getFriendlyReport());
      if (â˜ƒ.getSaveFile() != null) {
         Bootstrap.realStdoutPrintln("#@!@# Game crashed! Crash report saved to: #@!@# " + â˜ƒ.getSaveFile());
         System.exit(-1);
      } else if (â˜ƒ.saveToFile(â˜ƒx)) {
         Bootstrap.realStdoutPrintln("#@!@# Game crashed! Crash report saved to: #@!@# " + â˜ƒx.getAbsolutePath());
         System.exit(-1);
      } else {
         Bootstrap.realStdoutPrintln("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }
   }

   public boolean isEnforceUnicode() {
      return this.options.forceUnicodeFont;
   }

   public CompletableFuture<Void> reloadResourcePacks() {
      return this.reloadResourcePacks(false);
   }

   private CompletableFuture<Void> reloadResourcePacks(boolean var1) {
      if (this.pendingReload != null) {
         return this.pendingReload;
      } else {
         CompletableFuture<Void> â˜ƒ = new CompletableFuture();
         if (!â˜ƒ && this.overlay instanceof LoadingOverlay) {
            this.pendingReload = â˜ƒ;
            return â˜ƒ;
         } else {
            this.resourcePackRepository.reload();
            List<PackResources> â˜ƒ = this.resourcePackRepository.openAllSelected();
            if (!â˜ƒ) {
               this.reloadStateTracker.startReload(ResourceLoadStateTracker.ReloadReason.MANUAL, â˜ƒ);
            }

            this.setOverlay(
               new LoadingOverlay(
                  this,
                  this.resourceManager.createReload(Util.backgroundExecutor(), this, RESOURCE_RELOAD_INITIAL_TASK, â˜ƒ),
                  var2x -> Util.ifElse(var2x, this::rollbackResourcePacks, () -> {
                        this.levelRenderer.allChanged();
                        this.reloadStateTracker.finishReload();
                        â˜ƒ.complete(null);
                     }),
                  true
               )
            );
            return â˜ƒ;
         }
      }
   }

   private void selfTest() {
      boolean â˜ƒ = false;
      BlockModelShaper â˜ƒx = this.getBlockRenderer().getBlockModelShaper();
      BakedModel â˜ƒxx = â˜ƒx.getModelManager().getMissingModel();

      for(Block â˜ƒxxx : Registry.BLOCK) {
         for(BlockState â˜ƒxxxx : â˜ƒxxx.getStateDefinition().getPossibleStates()) {
            if (â˜ƒxxxx.getRenderShape() == RenderShape.MODEL) {
               BakedModel â˜ƒxxxxx = â˜ƒx.getBlockModel(â˜ƒxxxx);
               if (â˜ƒxxxxx == â˜ƒxx) {
                  LOGGER.debug("Missing model for: {}", â˜ƒxxxx);
                  â˜ƒ = true;
               }
            }
         }
      }

      TextureAtlasSprite â˜ƒxxx = â˜ƒxx.getParticleIcon();

      for(Block â˜ƒxxxx : Registry.BLOCK) {
         for(BlockState â˜ƒxxxxx : â˜ƒxxxx.getStateDefinition().getPossibleStates()) {
            TextureAtlasSprite â˜ƒxxxxxx = â˜ƒx.getParticleIcon(â˜ƒxxxxx);
            if (!â˜ƒxxxxx.isAir() && â˜ƒxxxxxx == â˜ƒxxx) {
               LOGGER.debug("Missing particle icon for: {}", â˜ƒxxxxx);
               â˜ƒ = true;
            }
         }
      }

      NonNullList<ItemStack> â˜ƒxxxx = NonNullList.create();

      for(Item â˜ƒxxxxx : Registry.ITEM) {
         â˜ƒxxxx.clear();
         â˜ƒxxxxx.fillItemCategory(CreativeModeTab.TAB_SEARCH, â˜ƒxxxx);

         for(ItemStack â˜ƒxxxxxx : â˜ƒxxxx) {
            String â˜ƒxxxxxxx = â˜ƒxxxxxx.getDescriptionId();
            String â˜ƒxxxxxxxx = new TranslatableComponent(â˜ƒxxxxxxx).getString();
            if (â˜ƒxxxxxxxx.toLowerCase(Locale.ROOT).equals(â˜ƒxxxxx.getDescriptionId())) {
               LOGGER.debug("Missing translation for: {} {} {}", â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx.getItem());
            }
         }
      }

      â˜ƒ |= MenuScreens.selfTest();
      â˜ƒ |= EntityRenderers.validateRegistrations();
      if (â˜ƒ) {
         throw new IllegalStateException("Your game data is foobar, fix the errors above!");
      }
   }

   public LevelStorageSource getLevelSource() {
      return this.levelSource;
   }

   private void openChatScreen(String var1) {
      Minecraft.ChatStatus â˜ƒ = this.getChatStatus();
      if (!â˜ƒ.isChatAllowed(this.isLocalServer())) {
         this.gui.setOverlayMessage(â˜ƒ.getMessage(), false);
      } else {
         this.setScreen(new ChatScreen(â˜ƒ));
      }
   }

   public void setScreen(@Nullable Screen var1) {
      if (SharedConstants.IS_RUNNING_IN_IDE && Thread.currentThread() != this.gameThread) {
         LOGGER.error("setScreen called from non-game thread");
      }

      if (this.screen != null) {
         this.screen.removed();
      }

      if (â˜ƒ == null && this.level == null) {
         â˜ƒ = new TitleScreen();
      } else if (â˜ƒ == null && this.player.isDeadOrDying()) {
         if (this.player.shouldShowDeathScreen()) {
            â˜ƒ = new DeathScreen(null, this.level.getLevelData().isHardcore());
         } else {
            this.player.respawn();
         }
      }

      this.screen = â˜ƒ;
      BufferUploader.reset();
      if (â˜ƒ != null) {
         this.mouseHandler.releaseMouse();
         KeyMapping.releaseAll();
         â˜ƒ.init(this, this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());
         this.noRender = false;
      } else {
         this.soundManager.resume();
         this.mouseHandler.grabMouse();
      }

      this.updateTitle();
   }

   public void setOverlay(@Nullable Overlay var1) {
      this.overlay = â˜ƒ;
   }

   public void destroy() {
      try {
         LOGGER.info("Stopping!");

         try {
            NarratorChatListener.INSTANCE.destroy();
         } catch (Throwable var7) {
         }

         try {
            if (this.level != null) {
               this.level.disconnect();
            }

            this.clearLevel();
         } catch (Throwable var6) {
         }

         if (this.screen != null) {
            this.screen.removed();
         }

         this.close();
      } finally {
         Util.timeSource = System::nanoTime;
         if (this.delayedCrash == null) {
            System.exit(0);
         }
      }
   }

   @Override
   public void close() {
      try {
         this.modelManager.close();
         this.fontManager.close();
         this.gameRenderer.close();
         this.levelRenderer.close();
         this.soundManager.destroy();
         this.resourcePackRepository.close();
         this.particleEngine.close();
         this.mobEffectTextures.close();
         this.paintingTextures.close();
         this.textureManager.close();
         this.resourceManager.close();
         Util.shutdownExecutors();
      } catch (Throwable var5) {
         LOGGER.error("Shutdown failure!", var5);
         throw var5;
      } finally {
         this.virtualScreen.close();
         this.window.close();
      }
   }

   private void runTick(boolean var1) {
      this.window.setErrorSection("Pre render");
      long â˜ƒ = Util.getNanos();
      if (this.window.shouldClose()) {
         this.stop();
      }

      if (this.pendingReload != null && !(this.overlay instanceof LoadingOverlay)) {
         CompletableFuture<Void> â˜ƒ = this.pendingReload;
         this.pendingReload = null;
         this.reloadResourcePacks().thenRun(() -> â˜ƒ.complete(null));
      }

      Runnable â˜ƒ;
      while((â˜ƒ = (Runnable)this.progressTasks.poll()) != null) {
         â˜ƒ.run();
      }

      if (â˜ƒ) {
         int â˜ƒ = this.timer.advanceTime(Util.getMillis());
         this.profiler.push("scheduledExecutables");
         this.runAllTasks();
         this.profiler.pop();
         this.profiler.push("tick");

         for(int â˜ƒx = 0; â˜ƒx < Math.min(10, â˜ƒ); ++â˜ƒx) {
            this.profiler.incrementCounter("clientTick");
            this.tick();
         }

         this.profiler.pop();
      }

      this.mouseHandler.turnPlayer();
      this.window.setErrorSection("Render");
      this.profiler.push("sound");
      this.soundManager.updateSource(this.gameRenderer.getMainCamera());
      this.profiler.pop();
      this.profiler.push("render");
      PoseStack â˜ƒ = RenderSystem.getModelViewStack();
      â˜ƒ.pushPose();
      RenderSystem.applyModelViewMatrix();
      RenderSystem.clear(16640, ON_OSX);
      this.mainRenderTarget.bindWrite(true);
      FogRenderer.setupNoFog();
      this.profiler.push("display");
      RenderSystem.enableTexture();
      RenderSystem.enableCull();
      this.profiler.pop();
      if (!this.noRender) {
         this.profiler.popPush("gameRenderer");
         this.gameRenderer.render(this.pause ? this.pausePartialTick : this.timer.partialTick, â˜ƒ, â˜ƒ);
         this.profiler.popPush("toasts");
         this.toast.render(new PoseStack());
         this.profiler.pop();
      }

      if (this.fpsPieResults != null) {
         this.profiler.push("fpsPie");
         this.renderFpsMeter(new PoseStack(), this.fpsPieResults);
         this.profiler.pop();
      }

      this.profiler.push("blit");
      this.mainRenderTarget.unbindWrite();
      â˜ƒ.popPose();
      â˜ƒ.pushPose();
      RenderSystem.applyModelViewMatrix();
      this.mainRenderTarget.blitToScreen(this.window.getWidth(), this.window.getHeight());
      â˜ƒ.popPose();
      RenderSystem.applyModelViewMatrix();
      this.profiler.popPush("updateDisplay");
      this.window.updateDisplay();
      int â˜ƒ = this.getFramerateLimit();
      if ((double)â˜ƒ < Option.FRAMERATE_LIMIT.getMaxValue()) {
         RenderSystem.limitDisplayFPS(â˜ƒ);
      }

      this.profiler.popPush("yield");
      Thread.yield();
      this.profiler.pop();
      this.window.setErrorSection("Post render");
      ++this.frames;
      boolean â˜ƒ = this.hasSingleplayerServer()
         && (this.screen != null && this.screen.isPauseScreen() || this.overlay != null && this.overlay.isPauseScreen())
         && !this.singleplayerServer.isPublished();
      if (this.pause != â˜ƒ) {
         if (this.pause) {
            this.pausePartialTick = this.timer.partialTick;
         } else {
            this.timer.partialTick = this.pausePartialTick;
         }

         this.pause = â˜ƒ;
      }

      long â˜ƒ = Util.getNanos();
      this.frameTimer.logFrameDuration(â˜ƒ - this.lastNanoTime);
      this.lastNanoTime = â˜ƒ;
      this.profiler.push("fpsUpdate");

      while(Util.getMillis() >= this.lastTime + 1000L) {
         fps = this.frames;
         this.fpsString = String.format(
            "%d fps T: %s%s%s%s B: %d",
            fps,
            (double)this.options.framerateLimit == Option.FRAMERATE_LIMIT.getMaxValue() ? "inf" : this.options.framerateLimit,
            this.options.enableVsync ? " vsync" : "",
            this.options.graphicsMode.toString(),
            this.options.renderClouds == CloudStatus.OFF ? "" : (this.options.renderClouds == CloudStatus.FAST ? " fast-clouds" : " fancy-clouds"),
            this.options.biomeBlendRadius
         );
         this.lastTime += 1000L;
         this.frames = 0;
         this.snooper.prepare();
         if (!this.snooper.isStarted()) {
            this.snooper.start();
         }
      }

      this.profiler.pop();
   }

   private boolean shouldRenderFpsPie() {
      return this.options.renderDebug && this.options.renderDebugCharts && !this.options.hideGui;
   }

   private ProfilerFiller constructProfiler(boolean var1, @Nullable SingleTickProfiler var2) {
      if (!â˜ƒ) {
         this.fpsPieProfiler.disable();
         if (!this.metricsRecorder.isRecording() && â˜ƒ == null) {
            return InactiveProfiler.INSTANCE;
         }
      }

      ProfilerFiller â˜ƒ;
      if (â˜ƒ) {
         if (!this.fpsPieProfiler.isEnabled()) {
            this.fpsPieRenderTicks = 0;
            this.fpsPieProfiler.enable();
         }

         ++this.fpsPieRenderTicks;
         â˜ƒ = this.fpsPieProfiler.getFiller();
      } else {
         â˜ƒ = InactiveProfiler.INSTANCE;
      }

      if (this.metricsRecorder.isRecording()) {
         â˜ƒ = ProfilerFiller.tee(â˜ƒ, this.metricsRecorder.getProfiler());
      }

      return SingleTickProfiler.decorateFiller(â˜ƒ, â˜ƒ);
   }

   private void finishProfilers(boolean var1, @Nullable SingleTickProfiler var2) {
      if (â˜ƒ != null) {
         â˜ƒ.endTick();
      }

      if (â˜ƒ) {
         this.fpsPieResults = this.fpsPieProfiler.getResults();
      } else {
         this.fpsPieResults = null;
      }

      this.profiler = this.fpsPieProfiler.getFiller();
   }

   @Override
   public void resizeDisplay() {
      int â˜ƒ = this.window.calculateScale(this.options.guiScale, this.isEnforceUnicode());
      this.window.setGuiScale((double)â˜ƒ);
      if (this.screen != null) {
         this.screen.resize(this, this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());
      }

      RenderTarget â˜ƒ = this.getMainRenderTarget();
      â˜ƒ.resize(this.window.getWidth(), this.window.getHeight(), ON_OSX);
      this.gameRenderer.resize(this.window.getWidth(), this.window.getHeight());
      this.mouseHandler.setIgnoreFirstMove();
   }

   @Override
   public void cursorEntered() {
      this.mouseHandler.cursorEntered();
   }

   private int getFramerateLimit() {
      return this.level != null || this.screen == null && this.overlay == null ? this.window.getFramerateLimit() : 60;
   }

   public void emergencySave() {
      try {
         MemoryReserve.release();
         this.levelRenderer.clear();
      } catch (Throwable var3) {
      }

      try {
         System.gc();
         if (this.isLocalServer && this.singleplayerServer != null) {
            this.singleplayerServer.halt(true);
         }

         this.clearLevel(new GenericDirtMessageScreen(new TranslatableComponent("menu.savingLevel")));
      } catch (Throwable var2) {
      }

      System.gc();
   }

   public boolean debugClientMetricsStart(Consumer<TranslatableComponent> var1) {
      if (this.metricsRecorder.isRecording()) {
         this.debugClientMetricsStop();
         return false;
      } else {
         Consumer<ProfileResults> â˜ƒx = var2x -> {
            int â˜ƒ = var2x.getTickDuration();
            double â˜ƒx = (double)var2x.getNanoDuration() / (double)TimeUtil.NANOSECONDS_PER_SECOND;
            this.execute(
               () -> â˜ƒ.accept(
                     new TranslatableComponent(
                        "commands.debug.stopped", String.format(Locale.ROOT, "%.2f", â˜ƒ), â˜ƒ, String.format(Locale.ROOT, "%.2f", (double)â˜ƒ / â˜ƒ)
                     )
                  )
            );
         };
         Consumer<Path> â˜ƒxx = var2x -> {
            Component â˜ƒ = new TextComponent(var2x.toString())
               .withStyle(ChatFormatting.UNDERLINE)
               .withStyle(var1x -> var1x.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, var2x.toFile().getParent())));
            this.execute(() -> â˜ƒ.accept(new TranslatableComponent("debug.profiling.stop", â˜ƒ)));
         };
         SystemReport â˜ƒxxx = fillSystemReport(new SystemReport(), this, this.languageManager, this.launchedVersion, this.options);
         Consumer<List<Path>> â˜ƒxxxx = var3x -> {
            Path â˜ƒ = this.archiveProfilingReport(â˜ƒ, var3x);
            â˜ƒ.accept(â˜ƒ);
         };
         Consumer<Path> â˜ƒ;
         if (this.singleplayerServer == null) {
            â˜ƒ = var1x -> â˜ƒ.accept(ImmutableList.of(var1x));
         } else {
            this.singleplayerServer.fillSystemReport(â˜ƒxxx);
            CompletableFuture<Path> â˜ƒ = new CompletableFuture();
            CompletableFuture<Path> â˜ƒx = new CompletableFuture();
            CompletableFuture.allOf(â˜ƒ, â˜ƒx).thenRunAsync(() -> â˜ƒ.accept(ImmutableList.<Object>of((Path)â˜ƒ.join(), (Path)â˜ƒ.join())), Util.ioPool());
            this.singleplayerServer.startRecordingMetrics(var0 -> {
            }, â˜ƒx::complete);
            â˜ƒ = â˜ƒ::complete;
         }

         this.metricsRecorder = ActiveMetricsRecorder.createStarted(
            new ClientMetricsSamplersProvider(Util.timeSource, this.levelRenderer), Util.timeSource, Util.ioPool(), new MetricsPersister("client"), var2x -> {
               this.metricsRecorder = InactiveMetricsRecorder.INSTANCE;
               â˜ƒ.accept(var2x);
            }, â˜ƒ
         );
         return true;
      }
   }

   private void debugClientMetricsStop() {
      this.metricsRecorder.end();
      if (this.singleplayerServer != null) {
         this.singleplayerServer.finishRecordingMetrics();
      }
   }

   private Path archiveProfilingReport(SystemReport var1, List<Path> var2) {
      String â˜ƒ;
      if (this.isLocalServer()) {
         â˜ƒ = this.getSingleplayerServer().getWorldData().getLevelName();
      } else {
         â˜ƒ = this.getCurrentServer().name;
      }

      Path â˜ƒ;
      try {
         String â˜ƒ = String.format(
            "%s-%s-%s", new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()), â˜ƒ, SharedConstants.getCurrentVersion().getId()
         );
         String â˜ƒx = FileUtil.findAvailableName(MetricsPersister.PROFILING_RESULTS_DIR, â˜ƒ, ".zip");
         â˜ƒ = MetricsPersister.PROFILING_RESULTS_DIR.resolve(â˜ƒx);
      } catch (IOException var21) {
         throw new UncheckedIOException(var21);
      }

      try {
         FileZipper â˜ƒ = new FileZipper(â˜ƒ);

         try {
            â˜ƒ.add(Paths.get("system.txt"), â˜ƒ.toLineSeparatedString());
            â˜ƒ.add(Paths.get("client").resolve(this.options.getFile().getName()), this.options.dumpOptionsForReport());
            â˜ƒ.forEach(â˜ƒ::add);
         } catch (Throwable var20) {
            try {
               â˜ƒ.close();
            } catch (Throwable var19) {
               var20.addSuppressed(var19);
            }

            throw var20;
         }

         â˜ƒ.close();
      } finally {
         for(Path â˜ƒ : â˜ƒ) {
            try {
               FileUtils.forceDelete(â˜ƒ.toFile());
            } catch (IOException var18) {
               LOGGER.warn("Failed to delete temporary profiling result {}", â˜ƒ, var18);
            }
         }
      }

      return â˜ƒ;
   }

   public void debugFpsMeterKeyPress(int var1) {
      if (this.fpsPieResults != null) {
         List<ResultField> â˜ƒ = this.fpsPieResults.getTimes(this.debugPath);
         if (!â˜ƒ.isEmpty()) {
            ResultField â˜ƒx = (ResultField)â˜ƒ.remove(0);
            if (â˜ƒ == 0) {
               if (!â˜ƒx.name.isEmpty()) {
                  int â˜ƒxx = this.debugPath.lastIndexOf(30);
                  if (â˜ƒxx >= 0) {
                     this.debugPath = this.debugPath.substring(0, â˜ƒxx);
                  }
               }
            } else {
               --â˜ƒ;
               if (â˜ƒ < â˜ƒ.size() && !"unspecified".equals(((ResultField)â˜ƒ.get(â˜ƒ)).name)) {
                  if (!this.debugPath.isEmpty()) {
                     this.debugPath = this.debugPath + "\u001e";
                  }

                  this.debugPath = this.debugPath + ((ResultField)â˜ƒ.get(â˜ƒ)).name;
               }
            }
         }
      }
   }

   private void renderFpsMeter(PoseStack var1, ProfileResults var2) {
      List<ResultField> â˜ƒ = â˜ƒ.getTimes(this.debugPath);
      ResultField â˜ƒx = (ResultField)â˜ƒ.remove(0);
      RenderSystem.clear(256, ON_OSX);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      Matrix4f â˜ƒxx = Matrix4f.orthographic(0.0F, (float)this.window.getWidth(), 0.0F, (float)this.window.getHeight(), 1000.0F, 3000.0F);
      RenderSystem.setProjectionMatrix(â˜ƒxx);
      PoseStack â˜ƒxxx = RenderSystem.getModelViewStack();
      â˜ƒxxx.setIdentity();
      â˜ƒxxx.translate(0.0, 0.0, -2000.0);
      RenderSystem.applyModelViewMatrix();
      RenderSystem.lineWidth(1.0F);
      RenderSystem.disableTexture();
      Tesselator â˜ƒxxxx = Tesselator.getInstance();
      BufferBuilder â˜ƒxxxxx = â˜ƒxxxx.getBuilder();
      int â˜ƒxxxxxx = 160;
      int â˜ƒxxxxxxx = this.window.getWidth() - 160 - 10;
      int â˜ƒxxxxxxxx = this.window.getHeight() - 320;
      RenderSystem.enableBlend();
      â˜ƒxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      â˜ƒxxxxx.vertex((double)((float)â˜ƒxxxxxxx - 176.0F), (double)((float)â˜ƒxxxxxxxx - 96.0F - 16.0F), 0.0).color(200, 0, 0, 0).endVertex();
      â˜ƒxxxxx.vertex((double)((float)â˜ƒxxxxxxx - 176.0F), (double)(â˜ƒxxxxxxxx + 320), 0.0).color(200, 0, 0, 0).endVertex();
      â˜ƒxxxxx.vertex((double)((float)â˜ƒxxxxxxx + 176.0F), (double)(â˜ƒxxxxxxxx + 320), 0.0).color(200, 0, 0, 0).endVertex();
      â˜ƒxxxxx.vertex((double)((float)â˜ƒxxxxxxx + 176.0F), (double)((float)â˜ƒxxxxxxxx - 96.0F - 16.0F), 0.0).color(200, 0, 0, 0).endVertex();
      â˜ƒxxxx.end();
      RenderSystem.disableBlend();
      double â˜ƒxxxxxxxxx = 0.0;

      for(ResultField â˜ƒxxxxxxxxxx : â˜ƒ) {
         int â˜ƒxxxxxxxxxxx = Mth.floor(â˜ƒxxxxxxxxxx.percentage / 4.0) + 1;
         â˜ƒxxxxx.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
         int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.getColor();
         int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx >> 16 & 0xFF;
         int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx >> 8 & 0xFF;
         int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx & 0xFF;
         â˜ƒxxxxx.vertex((double)â˜ƒxxxxxxx, (double)â˜ƒxxxxxxxx, 0.0).color(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, 255).endVertex();

         for(int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxxxxxxxx) {
            float â˜ƒxxxxxxxxxxxxxxxxx = (float)(
               (â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxx.percentage * (double)â˜ƒxxxxxxxxxxxxxxxx / (double)â˜ƒxxxxxxxxxxx) * (float) (Math.PI * 2) / 100.0
            );
            float â˜ƒxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxxxxxxx) * 160.0F;
            float â˜ƒxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxxxxxxx) * 160.0F * 0.5F;
            â˜ƒxxxxx.vertex((double)((float)â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxx), (double)((float)â˜ƒxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxx), 0.0)
               .color(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, 255)
               .endVertex();
         }

         â˜ƒxxxx.end();
         â˜ƒxxxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

         for(int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxxxxxxxx) {
            float â˜ƒxxxxxxxxxxxxxxxxx = (float)(
               (â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxx.percentage * (double)â˜ƒxxxxxxxxxxxxxxxx / (double)â˜ƒxxxxxxxxxxx) * (float) (Math.PI * 2) / 100.0
            );
            float â˜ƒxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxxxxxxx) * 160.0F;
            float â˜ƒxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxxxxxxx) * 160.0F * 0.5F;
            if (!(â˜ƒxxxxxxxxxxxxxxxxxxx > 0.0F)) {
               â˜ƒxxxxx.vertex((double)((float)â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxx), (double)((float)â˜ƒxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxx), 0.0)
                  .color(â˜ƒxxxxxxxxxxxxx >> 1, â˜ƒxxxxxxxxxxxxxx >> 1, â˜ƒxxxxxxxxxxxxxxx >> 1, 255)
                  .endVertex();
               â˜ƒxxxxx.vertex((double)((float)â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxx), (double)((float)â˜ƒxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxx + 10.0F), 0.0)
                  .color(â˜ƒxxxxxxxxxxxxx >> 1, â˜ƒxxxxxxxxxxxxxx >> 1, â˜ƒxxxxxxxxxxxxxxx >> 1, 255)
                  .endVertex();
            }
         }

         â˜ƒxxxx.end();
         â˜ƒxxxxxxxxx += â˜ƒxxxxxxxxxx.percentage;
      }

      DecimalFormat â˜ƒxxxxxxxxxx = new DecimalFormat("##0.00");
      â˜ƒxxxxxxxxxx.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
      RenderSystem.enableTexture();
      String â˜ƒxxxxxxxxxxx = ProfileResults.demanglePath(â˜ƒx.name);
      String â˜ƒxxxxxxxxxxxx = "";
      if (!"unspecified".equals(â˜ƒxxxxxxxxxxx)) {
         â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx + "[0] ";
      }

      if (â˜ƒxxxxxxxxxxx.isEmpty()) {
         â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx + "ROOT ";
      } else {
         â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxx + " ";
      }

      int â˜ƒxxxxxxxxxx = 16777215;
      this.font.drawShadow(â˜ƒ, â˜ƒxxxxxxxxxxxx, (float)(â˜ƒxxxxxxx - 160), (float)(â˜ƒxxxxxxxx - 80 - 16), 16777215);
      â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.format(â˜ƒx.globalPercentage) + "%";
      this.font.drawShadow(â˜ƒ, â˜ƒxxxxxxxxxxxx, (float)(â˜ƒxxxxxxx + 160 - this.font.width(â˜ƒxxxxxxxxxxxx)), (float)(â˜ƒxxxxxxxx - 80 - 16), 16777215);

      for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < â˜ƒ.size(); ++â˜ƒxxxxxxxxxxx) {
         ResultField â˜ƒxxxxxxxxxxxx = (ResultField)â˜ƒ.get(â˜ƒxxxxxxxxxxx);
         StringBuilder â˜ƒxxxxxxxxxxxxx = new StringBuilder();
         if ("unspecified".equals(â˜ƒxxxxxxxxxxxx.name)) {
            â˜ƒxxxxxxxxxxxxx.append("[?] ");
         } else {
            â˜ƒxxxxxxxxxxxxx.append("[").append(â˜ƒxxxxxxxxxxx + 1).append("] ");
         }

         String â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.append(â˜ƒxxxxxxxxxxxx.name).toString();
         this.font.drawShadow(â˜ƒ, â˜ƒxxxxxxxxxxxx, (float)(â˜ƒxxxxxxx - 160), (float)(â˜ƒxxxxxxxx + 80 + â˜ƒxxxxxxxxxxx * 8 + 20), â˜ƒxxxxxxxxxxxx.getColor());
         â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.format(â˜ƒxxxxxxxxxxxx.percentage) + "%";
         this.font
            .drawShadow(
               â˜ƒ,
               â˜ƒxxxxxxxxxxxx,
               (float)(â˜ƒxxxxxxx + 160 - 50 - this.font.width(â˜ƒxxxxxxxxxxxx)),
               (float)(â˜ƒxxxxxxxx + 80 + â˜ƒxxxxxxxxxxx * 8 + 20),
               â˜ƒxxxxxxxxxxxx.getColor()
            );
         â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.format(â˜ƒxxxxxxxxxxxx.globalPercentage) + "%";
         this.font
            .drawShadow(
               â˜ƒ,
               â˜ƒxxxxxxxxxxxx,
               (float)(â˜ƒxxxxxxx + 160 - this.font.width(â˜ƒxxxxxxxxxxxx)),
               (float)(â˜ƒxxxxxxxx + 80 + â˜ƒxxxxxxxxxxx * 8 + 20),
               â˜ƒxxxxxxxxxxxx.getColor()
            );
      }
   }

   public void stop() {
      this.running = false;
   }

   public boolean isRunning() {
      return this.running;
   }

   public void pauseGame(boolean var1) {
      if (this.screen == null) {
         boolean â˜ƒ = this.hasSingleplayerServer() && !this.singleplayerServer.isPublished();
         if (â˜ƒ) {
            this.setScreen(new PauseScreen(!â˜ƒ));
            this.soundManager.pause();
         } else {
            this.setScreen(new PauseScreen(true));
         }
      }
   }

   private void continueAttack(boolean var1) {
      if (!â˜ƒ) {
         this.missTime = 0;
      }

      if (this.missTime <= 0 && !this.player.isUsingItem()) {
         if (â˜ƒ && this.hitResult != null && this.hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult â˜ƒ = (BlockHitResult)this.hitResult;
            BlockPos â˜ƒx = â˜ƒ.getBlockPos();
            if (!this.level.getBlockState(â˜ƒx).isAir()) {
               Direction â˜ƒxx = â˜ƒ.getDirection();
               if (this.gameMode.continueDestroyBlock(â˜ƒx, â˜ƒxx)) {
                  this.particleEngine.crack(â˜ƒx, â˜ƒxx);
                  this.player.swing(InteractionHand.MAIN_HAND);
               }
            }
         } else {
            this.gameMode.stopDestroyBlock();
         }
      }
   }

   private void startAttack() {
      if (this.missTime <= 0) {
         if (this.hitResult == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.gameMode.hasMissTime()) {
               this.missTime = 10;
            }
         } else if (!this.player.isHandsBusy()) {
            switch(this.hitResult.getType()) {
               case ENTITY:
                  this.gameMode.attack(this.player, ((EntityHitResult)this.hitResult).getEntity());
                  break;
               case BLOCK:
                  BlockHitResult â˜ƒ = (BlockHitResult)this.hitResult;
                  BlockPos â˜ƒx = â˜ƒ.getBlockPos();
                  if (!this.level.getBlockState(â˜ƒx).isAir()) {
                     this.gameMode.startDestroyBlock(â˜ƒx, â˜ƒ.getDirection());
                     break;
                  }
               case MISS:
                  if (this.gameMode.hasMissTime()) {
                     this.missTime = 10;
                  }

                  this.player.resetAttackStrengthTicker();
            }

            this.player.swing(InteractionHand.MAIN_HAND);
         }
      }
   }

   private void startUseItem() {
      if (!this.gameMode.isDestroying()) {
         this.rightClickDelay = 4;
         if (!this.player.isHandsBusy()) {
            if (this.hitResult == null) {
               LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
            }

            for(InteractionHand â˜ƒ : InteractionHand.values()) {
               ItemStack â˜ƒx = this.player.getItemInHand(â˜ƒ);
               if (this.hitResult != null) {
                  switch(this.hitResult.getType()) {
                     case ENTITY:
                        EntityHitResult â˜ƒxx = (EntityHitResult)this.hitResult;
                        Entity â˜ƒxxx = â˜ƒxx.getEntity();
                        InteractionResult â˜ƒxxxx = this.gameMode.interactAt(this.player, â˜ƒxxx, â˜ƒxx, â˜ƒ);
                        if (!â˜ƒxxxx.consumesAction()) {
                           â˜ƒxxxx = this.gameMode.interact(this.player, â˜ƒxxx, â˜ƒ);
                        }

                        if (â˜ƒxxxx.consumesAction()) {
                           if (â˜ƒxxxx.shouldSwing()) {
                              this.player.swing(â˜ƒ);
                           }

                           return;
                        }
                        break;
                     case BLOCK:
                        BlockHitResult â˜ƒxx = (BlockHitResult)this.hitResult;
                        int â˜ƒxxx = â˜ƒx.getCount();
                        InteractionResult â˜ƒxxxx = this.gameMode.useItemOn(this.player, this.level, â˜ƒ, â˜ƒxx);
                        if (â˜ƒxxxx.consumesAction()) {
                           if (â˜ƒxxxx.shouldSwing()) {
                              this.player.swing(â˜ƒ);
                              if (!â˜ƒx.isEmpty() && (â˜ƒx.getCount() != â˜ƒxxx || this.gameMode.hasInfiniteItems())) {
                                 this.gameRenderer.itemInHandRenderer.itemUsed(â˜ƒ);
                              }
                           }

                           return;
                        }

                        if (â˜ƒxxxx == InteractionResult.FAIL) {
                           return;
                        }
                  }
               }

               if (!â˜ƒx.isEmpty()) {
                  InteractionResult â˜ƒx = this.gameMode.useItem(this.player, this.level, â˜ƒ);
                  if (â˜ƒx.consumesAction()) {
                     if (â˜ƒx.shouldSwing()) {
                        this.player.swing(â˜ƒ);
                     }

                     this.gameRenderer.itemInHandRenderer.itemUsed(â˜ƒ);
                     return;
                  }
               }
            }
         }
      }
   }

   public MusicManager getMusicManager() {
      return this.musicManager;
   }

   public void tick() {
      if (this.rightClickDelay > 0) {
         --this.rightClickDelay;
      }

      this.profiler.push("gui");
      if (!this.pause) {
         this.gui.tick();
      }

      this.profiler.pop();
      this.gameRenderer.pick(1.0F);
      this.tutorial.onLookAt(this.level, this.hitResult);
      this.profiler.push("gameMode");
      if (!this.pause && this.level != null) {
         this.gameMode.tick();
      }

      this.profiler.popPush("textures");
      if (this.level != null) {
         this.textureManager.tick();
      }

      if (this.screen == null && this.player != null) {
         if (this.player.isDeadOrDying() && !(this.screen instanceof DeathScreen)) {
            this.setScreen(null);
         } else if (this.player.isSleeping() && this.level != null) {
            this.setScreen(new InBedChatScreen());
         }
      } else if (this.screen != null && this.screen instanceof InBedChatScreen && !this.player.isSleeping()) {
         this.setScreen(null);
      }

      if (this.screen != null) {
         this.missTime = 10000;
      }

      if (this.screen != null) {
         Screen.wrapScreenError(() -> this.screen.tick(), "Ticking screen", this.screen.getClass().getCanonicalName());
      }

      if (!this.options.renderDebug) {
         this.gui.clearCache();
      }

      if (this.overlay == null && (this.screen == null || this.screen.passEvents)) {
         this.profiler.popPush("Keybindings");
         this.handleKeybinds();
         if (this.missTime > 0) {
            --this.missTime;
         }
      }

      if (this.level != null) {
         this.profiler.popPush("gameRenderer");
         if (!this.pause) {
            this.gameRenderer.tick();
         }

         this.profiler.popPush("levelRenderer");
         if (!this.pause) {
            this.levelRenderer.tick();
         }

         this.profiler.popPush("level");
         if (!this.pause) {
            if (this.level.getSkyFlashTime() > 0) {
               this.level.setSkyFlashTime(this.level.getSkyFlashTime() - 1);
            }

            this.level.tickEntities();
         }
      } else if (this.gameRenderer.currentEffect() != null) {
         this.gameRenderer.shutdownEffect();
      }

      if (!this.pause) {
         this.musicManager.tick();
      }

      this.soundManager.tick(this.pause);
      if (this.level != null) {
         if (!this.pause) {
            if (!this.options.joinedFirstServer && this.isMultiplayerServer()) {
               Component â˜ƒ = new TranslatableComponent("tutorial.socialInteractions.title");
               Component â˜ƒx = new TranslatableComponent("tutorial.socialInteractions.description", Tutorial.key("socialInteractions"));
               this.socialInteractionsToast = new TutorialToast(TutorialToast.Icons.SOCIAL_INTERACTIONS, â˜ƒ, â˜ƒx, true);
               this.tutorial.addTimedToast(this.socialInteractionsToast, 160);
               this.options.joinedFirstServer = true;
               this.options.save();
            }

            this.tutorial.tick();

            try {
               this.level.tick(() -> true);
            } catch (Throwable var4) {
               CrashReport â˜ƒ = CrashReport.forThrowable(var4, "Exception in world tick");
               if (this.level == null) {
                  CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Affected level");
                  â˜ƒx.setDetail("Problem", "Level is null!");
               } else {
                  this.level.fillReportDetails(â˜ƒ);
               }

               throw new ReportedException(â˜ƒ);
            }
         }

         this.profiler.popPush("animateTick");
         if (!this.pause && this.level != null) {
            this.level.animateTick(this.player.getBlockX(), this.player.getBlockY(), this.player.getBlockZ());
         }

         this.profiler.popPush("particles");
         if (!this.pause) {
            this.particleEngine.tick();
         }
      } else if (this.pendingConnection != null) {
         this.profiler.popPush("pendingConnection");
         this.pendingConnection.tick();
      }

      this.profiler.popPush("keyboard");
      this.keyboardHandler.tick();
      this.profiler.pop();
   }

   private boolean isMultiplayerServer() {
      return !this.isLocalServer || this.singleplayerServer != null && this.singleplayerServer.isPublished();
   }

   private void handleKeybinds() {
      for(; this.options.keyTogglePerspective.consumeClick(); this.levelRenderer.needsUpdate()) {
         CameraType â˜ƒ = this.options.getCameraType();
         this.options.setCameraType(this.options.getCameraType().cycle());
         if (â˜ƒ.isFirstPerson() != this.options.getCameraType().isFirstPerson()) {
            this.gameRenderer.checkEntityPostEffect(this.options.getCameraType().isFirstPerson() ? this.getCameraEntity() : null);
         }
      }

      while(this.options.keySmoothCamera.consumeClick()) {
         this.options.smoothCamera = !this.options.smoothCamera;
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         boolean â˜ƒx = this.options.keySaveHotbarActivator.isDown();
         boolean â˜ƒxx = this.options.keyLoadHotbarActivator.isDown();
         if (this.options.keyHotbarSlots[â˜ƒ].consumeClick()) {
            if (this.player.isSpectator()) {
               this.gui.getSpectatorGui().onHotbarSelected(â˜ƒ);
            } else if (!this.player.isCreative() || this.screen != null || !â˜ƒxx && !â˜ƒx) {
               this.player.getInventory().selected = â˜ƒ;
            } else {
               CreativeModeInventoryScreen.handleHotbarLoadOrSave(this, â˜ƒ, â˜ƒxx, â˜ƒx);
            }
         }
      }

      while(this.options.keySocialInteractions.consumeClick()) {
         if (!this.isMultiplayerServer()) {
            this.player.displayClientMessage(SOCIAL_INTERACTIONS_NOT_AVAILABLE, true);
            NarratorChatListener.INSTANCE.sayNow(SOCIAL_INTERACTIONS_NOT_AVAILABLE);
         } else {
            if (this.socialInteractionsToast != null) {
               this.tutorial.removeTimedToast(this.socialInteractionsToast);
               this.socialInteractionsToast = null;
            }

            this.setScreen(new SocialInteractionsScreen());
         }
      }

      while(this.options.keyInventory.consumeClick()) {
         if (this.gameMode.isServerControlledInventory()) {
            this.player.sendOpenInventory();
         } else {
            this.tutorial.onOpenInventory();
            this.setScreen(new InventoryScreen(this.player));
         }
      }

      while(this.options.keyAdvancements.consumeClick()) {
         this.setScreen(new AdvancementsScreen(this.player.connection.getAdvancements()));
      }

      while(this.options.keySwapOffhand.consumeClick()) {
         if (!this.player.isSpectator()) {
            this.getConnection()
               .send(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
         }
      }

      while(this.options.keyDrop.consumeClick()) {
         if (!this.player.isSpectator() && this.player.drop(Screen.hasControlDown())) {
            this.player.swing(InteractionHand.MAIN_HAND);
         }
      }

      while(this.options.keyChat.consumeClick()) {
         this.openChatScreen("");
      }

      if (this.screen == null && this.overlay == null && this.options.keyCommand.consumeClick()) {
         this.openChatScreen("/");
      }

      if (this.player.isUsingItem()) {
         if (!this.options.keyUse.isDown()) {
            this.gameMode.releaseUsingItem(this.player);
         }

         while(this.options.keyAttack.consumeClick()) {
         }

         while(this.options.keyUse.consumeClick()) {
         }

         while(this.options.keyPickItem.consumeClick()) {
         }
      } else {
         while(this.options.keyAttack.consumeClick()) {
            this.startAttack();
         }

         while(this.options.keyUse.consumeClick()) {
            this.startUseItem();
         }

         while(this.options.keyPickItem.consumeClick()) {
            this.pickBlock();
         }
      }

      if (this.options.keyUse.isDown() && this.rightClickDelay == 0 && !this.player.isUsingItem()) {
         this.startUseItem();
      }

      this.continueAttack(this.screen == null && this.options.keyAttack.isDown() && this.mouseHandler.isMouseGrabbed());
   }

   public static DataPackConfig loadDataPacks(LevelStorageSource.LevelStorageAccess var0) {
      MinecraftServer.convertFromRegionFormatIfNeeded(â˜ƒ);
      DataPackConfig â˜ƒ = â˜ƒ.getDataPacks();
      if (â˜ƒ == null) {
         throw new IllegalStateException("Failed to load data pack config");
      } else {
         return â˜ƒ;
      }
   }

   public static WorldData loadWorldData(
      LevelStorageSource.LevelStorageAccess var0, RegistryAccess.RegistryHolder var1, ResourceManager var2, DataPackConfig var3
   ) {
      RegistryReadOps<Tag> â˜ƒ = RegistryReadOps.createAndLoad(NbtOps.INSTANCE, â˜ƒ, â˜ƒ);
      WorldData â˜ƒx = â˜ƒ.getDataTag(â˜ƒ, â˜ƒ);
      if (â˜ƒx == null) {
         throw new IllegalStateException("Failed to load world");
      } else {
         return â˜ƒx;
      }
   }

   public void loadLevel(String var1) {
      this.doLoadLevel(â˜ƒ, RegistryAccess.builtin(), Minecraft::loadDataPacks, Minecraft::loadWorldData, false, Minecraft.ExperimentalDialogType.BACKUP);
   }

   public void createLevel(String var1, LevelSettings var2, RegistryAccess.RegistryHolder var3, WorldGenSettings var4) {
      this.doLoadLevel(
         â˜ƒ,
         â˜ƒ,
         var1x -> â˜ƒ.getDataPackConfig(),
         (var3x, var4x, var5, var6) -> {
            RegistryWriteOps<JsonElement> â˜ƒ = RegistryWriteOps.create(JsonOps.INSTANCE, â˜ƒ);
            RegistryReadOps<JsonElement> â˜ƒx = RegistryReadOps.createAndLoad(JsonOps.INSTANCE, var5, â˜ƒ);
            DataResult<WorldGenSettings> â˜ƒxx = WorldGenSettings.CODEC
               .encodeStart(â˜ƒ, â˜ƒ)
               .setLifecycle(Lifecycle.stable())
               .flatMap(var1x -> WorldGenSettings.CODEC.parse(â˜ƒ, var1x));
            WorldGenSettings â˜ƒxxx = (WorldGenSettings)â˜ƒxx.resultOrPartial(
                  Util.prefix("Error reading worldgen settings after loading data packs: ", LOGGER::error)
               )
               .orElse(â˜ƒ);
            return new PrimaryLevelData(â˜ƒ, â˜ƒxxx, â˜ƒxx.lifecycle());
         },
         false,
         Minecraft.ExperimentalDialogType.CREATE
      );
   }

   private void doLoadLevel(
      String var1,
      RegistryAccess.RegistryHolder var2,
      Function<LevelStorageSource.LevelStorageAccess, DataPackConfig> var3,
      Function4<LevelStorageSource.LevelStorageAccess, RegistryAccess.RegistryHolder, ResourceManager, DataPackConfig, WorldData> var4,
      boolean var5,
      Minecraft.ExperimentalDialogType var6
   ) {
      LevelStorageSource.LevelStorageAccess â˜ƒ;
      try {
         â˜ƒ = this.levelSource.createAccess(â˜ƒ);
      } catch (IOException var21) {
         LOGGER.warn("Failed to read level {} data", â˜ƒ, var21);
         SystemToast.onWorldAccessFailure(this, â˜ƒ);
         this.setScreen(null);
         return;
      }

      Minecraft.ServerStem â˜ƒ;
      try {
         â˜ƒ = this.makeServerStem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (Exception var20) {
         LOGGER.warn("Failed to load datapacks, can't proceed with server load", var20);
         this.setScreen(new DatapackLoadFailureScreen(() -> this.doLoadLevel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, â˜ƒ)));

         try {
            â˜ƒ.close();
         } catch (IOException var16) {
            LOGGER.warn("Failed to unlock access to level {}", â˜ƒ, var16);
         }

         return;
      }

      WorldData â˜ƒ = â˜ƒ.worldData();
      boolean â˜ƒx = â˜ƒ.worldGenSettings().isOldCustomizedWorld();
      boolean â˜ƒxx = â˜ƒ.worldGenSettingsLifecycle() != Lifecycle.stable();
      if (â˜ƒ == Minecraft.ExperimentalDialogType.NONE || !â˜ƒx && !â˜ƒxx) {
         this.clearLevel();
         this.progressListener.set(null);

         try {
            â˜ƒ.saveDataTag(â˜ƒ, â˜ƒ);
            â˜ƒ.serverResources().updateGlobals();
            YggdrasilAuthenticationService â˜ƒxxx = new YggdrasilAuthenticationService(this.proxy);
            MinecraftSessionService â˜ƒxxxx = â˜ƒxxx.createMinecraftSessionService();
            GameProfileRepository â˜ƒxxxxx = â˜ƒxxx.createProfileRepository();
            GameProfileCache â˜ƒxxxxxx = new GameProfileCache(â˜ƒxxxxx, new File(this.gameDirectory, MinecraftServer.USERID_CACHE_FILE.getName()));
            â˜ƒxxxxxx.setExecutor(this);
            SkullBlockEntity.setProfileCache(â˜ƒxxxxxx);
            SkullBlockEntity.setSessionService(â˜ƒxxxx);
            SkullBlockEntity.setMainThreadExecutor(this);
            GameProfileCache.setUsesAuthentication(false);
            this.singleplayerServer = MinecraftServer.spin(
               var8x -> new IntegratedServer(var8x, this, â˜ƒ, â˜ƒ, â˜ƒ.packRepository(), â˜ƒ.serverResources(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, var1x -> {
                     StoringChunkProgressListener â˜ƒ = new StoringChunkProgressListener(var1x + 0);
                     this.progressListener.set(â˜ƒ);
                     return ProcessorChunkProgressListener.createStarted(â˜ƒ, this.progressTasks::add);
                  })
            );
            this.isLocalServer = true;
         } catch (Throwable var19) {
            CrashReport â˜ƒxxxxxxx = CrashReport.forThrowable(var19, "Starting integrated server");
            CrashReportCategory â˜ƒxxxxxxxx = â˜ƒxxxxxxx.addCategory("Starting integrated server");
            â˜ƒxxxxxxxx.setDetail("Level ID", â˜ƒ);
            â˜ƒxxxxxxxx.setDetail("Level Name", â˜ƒ.getLevelName());
            throw new ReportedException(â˜ƒxxxxxxx);
         }

         while(this.progressListener.get() == null) {
            Thread.yield();
         }

         LevelLoadingScreen â˜ƒxxx = new LevelLoadingScreen((StoringChunkProgressListener)this.progressListener.get());
         this.setScreen(â˜ƒxxx);
         this.profiler.push("waitForServer");

         while(!this.singleplayerServer.isReady()) {
            â˜ƒxxx.tick();
            this.runTick(false);

            try {
               Thread.sleep(16L);
            } catch (InterruptedException var18) {
            }

            if (this.delayedCrash != null) {
               crash(this.delayedCrash);
               return;
            }
         }

         this.profiler.pop();
         SocketAddress â˜ƒxxxx = this.singleplayerServer.getConnection().startMemoryChannel();
         Connection â˜ƒxxxxx = Connection.connectToLocalServer(â˜ƒxxxx);
         â˜ƒxxxxx.setListener(new ClientHandshakePacketListenerImpl(â˜ƒxxxxx, this, null, var0 -> {
         }));
         â˜ƒxxxxx.send(new ClientIntentionPacket(â˜ƒxxxx.toString(), 0, ConnectionProtocol.LOGIN));
         â˜ƒxxxxx.send(new ServerboundHelloPacket(this.getUser().getGameProfile()));
         this.pendingConnection = â˜ƒxxxxx;
      } else {
         this.displayExperimentalConfirmationDialog(â˜ƒ, â˜ƒ, â˜ƒx, () -> this.doLoadLevel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Minecraft.ExperimentalDialogType.NONE));
         â˜ƒ.close();

         try {
            â˜ƒ.close();
         } catch (IOException var17) {
            LOGGER.warn("Failed to unlock access to level {}", â˜ƒ, var17);
         }
      }
   }

   private void displayExperimentalConfirmationDialog(Minecraft.ExperimentalDialogType var1, String var2, boolean var3, Runnable var4) {
      if (â˜ƒ == Minecraft.ExperimentalDialogType.BACKUP) {
         Component â˜ƒ;
         Component â˜ƒx;
         if (â˜ƒ) {
            â˜ƒ = new TranslatableComponent("selectWorld.backupQuestion.customized");
            â˜ƒx = new TranslatableComponent("selectWorld.backupWarning.customized");
         } else {
            â˜ƒ = new TranslatableComponent("selectWorld.backupQuestion.experimental");
            â˜ƒx = new TranslatableComponent("selectWorld.backupWarning.experimental");
         }

         this.setScreen(new BackupConfirmScreen(null, (var3x, var4x) -> {
            if (var3x) {
               EditWorldScreen.makeBackupAndShowToast(this.levelSource, â˜ƒ);
            }

            â˜ƒ.run();
         }, â˜ƒ, â˜ƒx, false));
      } else {
         this.setScreen(
            new ConfirmScreen(
               var3x -> {
                  if (var3x) {
                     â˜ƒ.run();
                  } else {
                     this.setScreen(null);
      
                     try (LevelStorageSource.LevelStorageAccess â˜ƒ = this.levelSource.createAccess(â˜ƒ)) {
                        â˜ƒ.deleteLevel();
                     } catch (IOException var9) {
                        SystemToast.onWorldDeleteFailure(this, â˜ƒ);
                        LOGGER.error("Failed to delete world {}", â˜ƒ, var9);
                     }
                  }
               },
               new TranslatableComponent("selectWorld.backupQuestion.experimental"),
               new TranslatableComponent("selectWorld.backupWarning.experimental"),
               CommonComponents.GUI_PROCEED,
               CommonComponents.GUI_CANCEL
            )
         );
      }
   }

   public Minecraft.ServerStem makeServerStem(
      RegistryAccess.RegistryHolder var1,
      Function<LevelStorageSource.LevelStorageAccess, DataPackConfig> var2,
      Function4<LevelStorageSource.LevelStorageAccess, RegistryAccess.RegistryHolder, ResourceManager, DataPackConfig, WorldData> var3,
      boolean var4,
      LevelStorageSource.LevelStorageAccess var5
   ) throws InterruptedException, ExecutionException {
      DataPackConfig â˜ƒ = (DataPackConfig)â˜ƒ.apply(â˜ƒ);
      PackRepository â˜ƒx = new PackRepository(
         PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(â˜ƒ.getLevelPath(LevelResource.DATAPACK_DIR).toFile(), PackSource.WORLD)
      );

      try {
         DataPackConfig â˜ƒxx = MinecraftServer.configurePackRepository(â˜ƒx, â˜ƒ, â˜ƒ);
         CompletableFuture<ServerResources> â˜ƒxxx = ServerResources.loadResources(
            â˜ƒx.openAllSelected(), â˜ƒ, Commands.CommandSelection.INTEGRATED, 2, Util.backgroundExecutor(), this
         );
         this.managedBlock(â˜ƒxxx::isDone);
         ServerResources â˜ƒxxxx = (ServerResources)â˜ƒxxx.get();
         WorldData â˜ƒxxxxx = â˜ƒ.apply(â˜ƒ, â˜ƒ, â˜ƒxxxx.getResourceManager(), â˜ƒxx);
         return new Minecraft.ServerStem(â˜ƒx, â˜ƒxxxx, â˜ƒxxxxx);
      } catch (ExecutionException | InterruptedException var12) {
         â˜ƒx.close();
         throw var12;
      }
   }

   public void setLevel(ClientLevel var1) {
      ProgressScreen â˜ƒ = new ProgressScreen(true);
      â˜ƒ.progressStartNoAbort(new TranslatableComponent("connect.joining"));
      this.updateScreenAndTick(â˜ƒ);
      this.level = â˜ƒ;
      this.updateLevelInEngines(â˜ƒ);
      if (!this.isLocalServer) {
         AuthenticationService â˜ƒx = new YggdrasilAuthenticationService(this.proxy);
         MinecraftSessionService â˜ƒxx = â˜ƒx.createMinecraftSessionService();
         GameProfileRepository â˜ƒxxx = â˜ƒx.createProfileRepository();
         GameProfileCache â˜ƒxxxx = new GameProfileCache(â˜ƒxxx, new File(this.gameDirectory, MinecraftServer.USERID_CACHE_FILE.getName()));
         â˜ƒxxxx.setExecutor(this);
         SkullBlockEntity.setProfileCache(â˜ƒxxxx);
         SkullBlockEntity.setSessionService(â˜ƒxx);
         SkullBlockEntity.setMainThreadExecutor(this);
         GameProfileCache.setUsesAuthentication(false);
      }
   }

   public void clearLevel() {
      this.clearLevel(new ProgressScreen(true));
   }

   public void clearLevel(Screen var1) {
      ClientPacketListener â˜ƒ = this.getConnection();
      if (â˜ƒ != null) {
         this.dropAllTasks();
         â˜ƒ.cleanup();
      }

      IntegratedServer â˜ƒ = this.singleplayerServer;
      this.singleplayerServer = null;
      this.gameRenderer.resetData();
      this.gameMode = null;
      NarratorChatListener.INSTANCE.clear();
      this.updateScreenAndTick(â˜ƒ);
      if (this.level != null) {
         if (â˜ƒ != null) {
            this.profiler.push("waitForServer");

            while(!â˜ƒ.isShutdown()) {
               this.runTick(false);
            }

            this.profiler.pop();
         }

         this.clientPackSource.clearServerPack();
         this.gui.onDisconnected();
         this.currentServer = null;
         this.isLocalServer = false;
         this.game.onLeaveGameSession();
      }

      this.level = null;
      this.updateLevelInEngines(null);
      this.player = null;
   }

   private void updateScreenAndTick(Screen var1) {
      this.profiler.push("forcedTick");
      this.soundManager.stop();
      this.cameraEntity = null;
      this.pendingConnection = null;
      this.setScreen(â˜ƒ);
      this.runTick(false);
      this.profiler.pop();
   }

   public void forceSetScreen(Screen var1) {
      this.profiler.push("forcedTick");
      this.setScreen(â˜ƒ);
      this.runTick(false);
      this.profiler.pop();
   }

   private void updateLevelInEngines(@Nullable ClientLevel var1) {
      this.levelRenderer.setLevel(â˜ƒ);
      this.particleEngine.setLevel(â˜ƒ);
      this.blockEntityRenderDispatcher.setLevel(â˜ƒ);
      this.updateTitle();
   }

   public boolean allowsMultiplayer() {
      return this.allowsMultiplayer && this.socialInteractionsService.serversAllowed();
   }

   public boolean allowsRealms() {
      return this.socialInteractionsService.realmsAllowed();
   }

   public boolean isBlocked(UUID var1) {
      if (this.getChatStatus().isChatAllowed(false)) {
         return this.playerSocialManager.shouldHideMessageFrom(â˜ƒ);
      } else {
         return (this.player == null || !â˜ƒ.equals(this.player.getUUID())) && !â˜ƒ.equals(Util.NIL_UUID);
      }
   }

   public Minecraft.ChatStatus getChatStatus() {
      if (this.options.chatVisibility == ChatVisiblity.HIDDEN) {
         return Minecraft.ChatStatus.DISABLED_BY_OPTIONS;
      } else if (!this.allowsChat) {
         return Minecraft.ChatStatus.DISABLED_BY_LAUNCHER;
      } else {
         return !this.socialInteractionsService.chatAllowed() ? Minecraft.ChatStatus.DISABLED_BY_PROFILE : Minecraft.ChatStatus.ENABLED;
      }
   }

   public final boolean isDemo() {
      return this.demo;
   }

   @Nullable
   public ClientPacketListener getConnection() {
      return this.player == null ? null : this.player.connection;
   }

   public static boolean renderNames() {
      return !instance.options.hideGui;
   }

   public static boolean useFancyGraphics() {
      return instance.options.graphicsMode.getId() >= GraphicsStatus.FANCY.getId();
   }

   public static boolean useShaderTransparency() {
      return !instance.gameRenderer.isPanoramicMode() && instance.options.graphicsMode.getId() >= GraphicsStatus.FABULOUS.getId();
   }

   public static boolean useAmbientOcclusion() {
      return instance.options.ambientOcclusion != AmbientOcclusionStatus.OFF;
   }

   private void pickBlock() {
      if (this.hitResult != null && this.hitResult.getType() != HitResult.Type.MISS) {
         boolean â˜ƒx = this.player.getAbilities().instabuild;
         BlockEntity â˜ƒxx = null;
         HitResult.Type â˜ƒxxx = this.hitResult.getType();
         ItemStack â˜ƒ;
         if (â˜ƒxxx == HitResult.Type.BLOCK) {
            BlockPos â˜ƒxxxx = ((BlockHitResult)this.hitResult).getBlockPos();
            BlockState â˜ƒxxxxx = this.level.getBlockState(â˜ƒxxxx);
            if (â˜ƒxxxxx.isAir()) {
               return;
            }

            Block â˜ƒxxxx = â˜ƒxxxxx.getBlock();
            â˜ƒ = â˜ƒxxxx.getCloneItemStack(this.level, â˜ƒxxxx, â˜ƒxxxxx);
            if (â˜ƒ.isEmpty()) {
               return;
            }

            if (â˜ƒx && Screen.hasControlDown() && â˜ƒxxxxx.hasBlockEntity()) {
               â˜ƒxx = this.level.getBlockEntity(â˜ƒxxxx);
            }
         } else {
            if (â˜ƒxxx != HitResult.Type.ENTITY || !â˜ƒx) {
               return;
            }

            Entity â˜ƒ = ((EntityHitResult)this.hitResult).getEntity();
            â˜ƒ = â˜ƒ.getPickResult();
            if (â˜ƒ == null) {
               return;
            }
         }

         if (â˜ƒ.isEmpty()) {
            String â˜ƒ = "";
            if (â˜ƒxxx == HitResult.Type.BLOCK) {
               â˜ƒ = Registry.BLOCK.getKey(this.level.getBlockState(((BlockHitResult)this.hitResult).getBlockPos()).getBlock()).toString();
            } else if (â˜ƒxxx == HitResult.Type.ENTITY) {
               â˜ƒ = Registry.ENTITY_TYPE.getKey(((EntityHitResult)this.hitResult).getEntity().getType()).toString();
            }

            LOGGER.warn("Picking on: [{}] {} gave null item", â˜ƒxxx, â˜ƒ);
         } else {
            Inventory â˜ƒ = this.player.getInventory();
            if (â˜ƒxx != null) {
               this.addCustomNbtData(â˜ƒ, â˜ƒxx);
            }

            int â˜ƒ = â˜ƒ.findSlotMatchingItem(â˜ƒ);
            if (â˜ƒx) {
               â˜ƒ.setPickedItem(â˜ƒ);
               this.gameMode.handleCreativeModeItemAdd(this.player.getItemInHand(InteractionHand.MAIN_HAND), 36 + â˜ƒ.selected);
            } else if (â˜ƒ != -1) {
               if (Inventory.isHotbarSlot(â˜ƒ)) {
                  â˜ƒ.selected = â˜ƒ;
               } else {
                  this.gameMode.handlePickItem(â˜ƒ);
               }
            }
         }
      }
   }

   private ItemStack addCustomNbtData(ItemStack var1, BlockEntity var2) {
      CompoundTag â˜ƒ = â˜ƒ.save(new CompoundTag());
      if (â˜ƒ.getItem() instanceof PlayerHeadItem && â˜ƒ.contains("SkullOwner")) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound("SkullOwner");
         â˜ƒ.getOrCreateTag().put("SkullOwner", â˜ƒx);
         return â˜ƒ;
      } else {
         â˜ƒ.addTagElement("BlockEntityTag", â˜ƒ);
         CompoundTag â˜ƒ = new CompoundTag();
         ListTag â˜ƒx = new ListTag();
         â˜ƒx.add(StringTag.valueOf("\"(+NBT)\""));
         â˜ƒ.put("Lore", â˜ƒx);
         â˜ƒ.addTagElement("display", â˜ƒ);
         return â˜ƒ;
      }
   }

   public CrashReport fillReport(CrashReport var1) {
      SystemReport â˜ƒ = â˜ƒ.getSystemReport();
      fillSystemReport(â˜ƒ, this, this.languageManager, this.launchedVersion, this.options);
      if (this.level != null) {
         this.level.fillReportDetails(â˜ƒ);
      }

      if (this.singleplayerServer != null) {
         this.singleplayerServer.fillSystemReport(â˜ƒ);
      }

      this.reloadStateTracker.fillCrashReport(â˜ƒ);
      return â˜ƒ;
   }

   public static void fillReport(@Nullable Minecraft var0, @Nullable LanguageManager var1, String var2, @Nullable Options var3, CrashReport var4) {
      SystemReport â˜ƒ = â˜ƒ.getSystemReport();
      fillSystemReport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static SystemReport fillSystemReport(SystemReport var0, @Nullable Minecraft var1, @Nullable LanguageManager var2, String var3, Options var4) {
      â˜ƒ.setDetail("Launched Version", (Supplier<String>)(() -> â˜ƒ));
      â˜ƒ.setDetail("Backend library", RenderSystem::getBackendDescription);
      â˜ƒ.setDetail("Backend API", RenderSystem::getApiDescription);
      â˜ƒ.setDetail("Window size", (Supplier<String>)(() -> â˜ƒ != null ? â˜ƒ.window.getWidth() + "x" + â˜ƒ.window.getHeight() : "<not initialized>"));
      â˜ƒ.setDetail("GL Caps", RenderSystem::getCapsString);
      â˜ƒ.setDetail(
         "GL debug messages", (Supplier<String>)(() -> GlDebug.isDebugEnabled() ? String.join("\n", GlDebug.getLastOpenGlDebugMessages()) : "<disabled>")
      );
      â˜ƒ.setDetail("Using VBOs", (Supplier<String>)(() -> "Yes"));
      â˜ƒ.setDetail(
         "Is Modded",
         (Supplier<String>)(() -> {
            String â˜ƒ = ClientBrandRetriever.getClientModName();
            if (!"vanilla".equals(â˜ƒ)) {
               return "Definitely; Client brand changed to '" + â˜ƒ + "'";
            } else {
               return Minecraft.class.getSigners() == null
                  ? "Very likely; Jar signature invalidated"
                  : "Probably not. Jar signature remains and client brand is untouched.";
            }
         })
      );
      â˜ƒ.setDetail("Type", "Client (map_client.txt)");
      if (â˜ƒ != null) {
         if (instance != null) {
            String â˜ƒ = instance.getGpuWarnlistManager().getAllWarnings();
            if (â˜ƒ != null) {
               â˜ƒ.setDetail("GPU Warnings", â˜ƒ);
            }
         }

         â˜ƒ.setDetail("Graphics mode", â˜ƒ.graphicsMode.toString());
         â˜ƒ.setDetail("Resource Packs", (Supplier<String>)(() -> {
            StringBuilder â˜ƒ = new StringBuilder();

            for(String â˜ƒx : â˜ƒ.resourcePacks) {
               if (â˜ƒ.length() > 0) {
                  â˜ƒ.append(", ");
               }

               â˜ƒ.append(â˜ƒx);
               if (â˜ƒ.incompatibleResourcePacks.contains(â˜ƒx)) {
                  â˜ƒ.append(" (incompatible)");
               }
            }

            return â˜ƒ.toString();
         }));
      }

      if (â˜ƒ != null) {
         â˜ƒ.setDetail("Current Language", (Supplier<String>)(() -> â˜ƒ.getSelected().toString()));
      }

      â˜ƒ.setDetail("CPU", GlUtil::getCpuInfo);
      return â˜ƒ;
   }

   public static Minecraft getInstance() {
      return instance;
   }

   public CompletableFuture<Void> delayTextureReload() {
      return this.submit(this::reloadResourcePacks).thenCompose(var0 -> var0);
   }

   @Override
   public void populateSnooper(Snooper var1) {
      â˜ƒ.setDynamicData("fps", fps);
      â˜ƒ.setDynamicData("vsync_enabled", this.options.enableVsync);
      â˜ƒ.setDynamicData("display_frequency", this.window.getRefreshRate());
      â˜ƒ.setDynamicData("display_type", this.window.isFullscreen() ? "fullscreen" : "windowed");
      â˜ƒ.setDynamicData("run_time", (Util.getMillis() - â˜ƒ.getStartupTime()) / 60L * 1000L);
      â˜ƒ.setDynamicData("current_action", this.getCurrentSnooperAction());
      â˜ƒ.setDynamicData("language", this.options.languageCode == null ? "en_us" : this.options.languageCode);
      String â˜ƒ = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      â˜ƒ.setDynamicData("endianness", â˜ƒ);
      â˜ƒ.setDynamicData("subtitles", this.options.showSubtitles);
      â˜ƒ.setDynamicData("touch", this.options.touchscreen ? "touch" : "mouse");
      int â˜ƒx = 0;

      for(Pack â˜ƒxx : this.resourcePackRepository.getSelectedPacks()) {
         if (!â˜ƒxx.isRequired() && !â˜ƒxx.isFixedPosition()) {
            â˜ƒ.setDynamicData("resource_pack[" + â˜ƒx++ + "]", â˜ƒxx.getId());
         }
      }

      â˜ƒ.setDynamicData("resource_packs", â˜ƒx);
      if (this.singleplayerServer != null) {
         â˜ƒ.setDynamicData("snooper_partner", this.singleplayerServer.getSnooper().getToken());
      }
   }

   private String getCurrentSnooperAction() {
      if (this.singleplayerServer != null) {
         return this.singleplayerServer.isPublished() ? "hosting_lan" : "singleplayer";
      } else if (this.currentServer != null) {
         return this.currentServer.isLan() ? "playing_lan" : "multiplayer";
      } else {
         return "out_of_game";
      }
   }

   @Override
   public void populateSnooperInitial(Snooper var1) {
      â˜ƒ.setFixedData("client_brand", ClientBrandRetriever.getClientModName());
      â˜ƒ.setFixedData("launched_version", this.launchedVersion);
      populateSnooperWithOpenGL(â˜ƒ);
      â˜ƒ.setFixedData("gl_max_texture_size", RenderSystem.maxSupportedTextureSize());
      GameProfile â˜ƒ = this.user.getGameProfile();
      if (â˜ƒ.getId() != null) {
         â˜ƒ.setFixedData("uuid", Hashing.sha1().hashBytes(â˜ƒ.getId().toString().getBytes(Charsets.ISO_8859_1)).toString());
      }
   }

   private static void populateSnooperWithOpenGL(Snooper var0) {
      GlUtil.populateSnooperWithOpenGL(â˜ƒ::setFixedData);
   }

   @Override
   public boolean isSnooperEnabled() {
      return this.options.snooperEnabled;
   }

   public void setCurrentServer(@Nullable ServerData var1) {
      this.currentServer = â˜ƒ;
   }

   @Nullable
   public ServerData getCurrentServer() {
      return this.currentServer;
   }

   public boolean isLocalServer() {
      return this.isLocalServer;
   }

   public boolean hasSingleplayerServer() {
      return this.isLocalServer && this.singleplayerServer != null;
   }

   @Nullable
   public IntegratedServer getSingleplayerServer() {
      return this.singleplayerServer;
   }

   public Snooper getSnooper() {
      return this.snooper;
   }

   public User getUser() {
      return this.user;
   }

   public PropertyMap getProfileProperties() {
      if (this.profileProperties.isEmpty()) {
         GameProfile â˜ƒ = this.getMinecraftSessionService().fillProfileProperties(this.user.getGameProfile(), false);
         this.profileProperties.putAll(â˜ƒ.getProperties());
      }

      return this.profileProperties;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public TextureManager getTextureManager() {
      return this.textureManager;
   }

   public ResourceManager getResourceManager() {
      return this.resourceManager;
   }

   public PackRepository getResourcePackRepository() {
      return this.resourcePackRepository;
   }

   public ClientPackSource getClientPackSource() {
      return this.clientPackSource;
   }

   public File getResourcePackDirectory() {
      return this.resourcePackDirectory;
   }

   public LanguageManager getLanguageManager() {
      return this.languageManager;
   }

   public Function<ResourceLocation, TextureAtlasSprite> getTextureAtlas(ResourceLocation var1) {
      return this.modelManager.getAtlas(â˜ƒ)::getSprite;
   }

   public boolean is64Bit() {
      return this.is64bit;
   }

   public boolean isPaused() {
      return this.pause;
   }

   public GpuWarnlistManager getGpuWarnlistManager() {
      return this.gpuWarnlistManager;
   }

   public SoundManager getSoundManager() {
      return this.soundManager;
   }

   public Music getSituationalMusic() {
      if (this.screen instanceof WinScreen) {
         return Musics.CREDITS;
      } else if (this.player != null) {
         if (this.player.level.dimension() == Level.END) {
            return this.gui.getBossOverlay().shouldPlayMusic() ? Musics.END_BOSS : Musics.END;
         } else {
            Biome.BiomeCategory â˜ƒ = this.player.level.getBiome(this.player.blockPosition()).getBiomeCategory();
            if (!this.musicManager.isPlayingMusic(Musics.UNDER_WATER)
               && (!this.player.isUnderWater() || â˜ƒ != Biome.BiomeCategory.OCEAN && â˜ƒ != Biome.BiomeCategory.RIVER)) {
               return this.player.level.dimension() != Level.NETHER && this.player.getAbilities().instabuild && this.player.getAbilities().mayfly
                  ? Musics.CREATIVE
                  : (Music)this.level.getBiomeManager().getNoiseBiomeAtPosition(this.player.blockPosition()).getBackgroundMusic().orElse(Musics.GAME);
            } else {
               return Musics.UNDER_WATER;
            }
         }
      } else {
         return Musics.MENU;
      }
   }

   public MinecraftSessionService getMinecraftSessionService() {
      return this.minecraftSessionService;
   }

   public SkinManager getSkinManager() {
      return this.skinManager;
   }

   @Nullable
   public Entity getCameraEntity() {
      return this.cameraEntity;
   }

   public void setCameraEntity(Entity var1) {
      this.cameraEntity = â˜ƒ;
      this.gameRenderer.checkEntityPostEffect(â˜ƒ);
   }

   public boolean shouldEntityAppearGlowing(Entity var1) {
      return â˜ƒ.isCurrentlyGlowing()
         || this.player != null && this.player.isSpectator() && this.options.keySpectatorOutlines.isDown() && â˜ƒ.getType() == EntityType.PLAYER;
   }

   @Override
   protected Thread getRunningThread() {
      return this.gameThread;
   }

   @Override
   protected Runnable wrapRunnable(Runnable var1) {
      return â˜ƒ;
   }

   @Override
   protected boolean shouldRun(Runnable var1) {
      return true;
   }

   public BlockRenderDispatcher getBlockRenderer() {
      return this.blockRenderer;
   }

   public EntityRenderDispatcher getEntityRenderDispatcher() {
      return this.entityRenderDispatcher;
   }

   public BlockEntityRenderDispatcher getBlockEntityRenderDispatcher() {
      return this.blockEntityRenderDispatcher;
   }

   public ItemRenderer getItemRenderer() {
      return this.itemRenderer;
   }

   public ItemInHandRenderer getItemInHandRenderer() {
      return this.itemInHandRenderer;
   }

   public <T> MutableSearchTree<T> getSearchTree(SearchRegistry.Key<T> var1) {
      return this.searchRegistry.getTree(â˜ƒ);
   }

   public FrameTimer getFrameTimer() {
      return this.frameTimer;
   }

   public boolean isConnectedToRealms() {
      return this.connectedToRealms;
   }

   public void setConnectedToRealms(boolean var1) {
      this.connectedToRealms = â˜ƒ;
   }

   public DataFixer getFixerUpper() {
      return this.fixerUpper;
   }

   public float getFrameTime() {
      return this.timer.partialTick;
   }

   public float getDeltaFrameTime() {
      return this.timer.tickDelta;
   }

   public BlockColors getBlockColors() {
      return this.blockColors;
   }

   public boolean showOnlyReducedInfo() {
      return this.player != null && this.player.isReducedDebugInfo() || this.options.reducedDebugInfo;
   }

   public ToastComponent getToasts() {
      return this.toast;
   }

   public Tutorial getTutorial() {
      return this.tutorial;
   }

   public boolean isWindowActive() {
      return this.windowActive;
   }

   public HotbarManager getHotbarManager() {
      return this.hotbarManager;
   }

   public ModelManager getModelManager() {
      return this.modelManager;
   }

   public PaintingTextureManager getPaintingTextures() {
      return this.paintingTextures;
   }

   public MobEffectTextureManager getMobEffectTextures() {
      return this.mobEffectTextures;
   }

   @Override
   public void setWindowActive(boolean var1) {
      this.windowActive = â˜ƒ;
   }

   public Component grabPanoramixScreenshot(File var1, int var2, int var3) {
      int â˜ƒ = this.window.getWidth();
      int â˜ƒx = this.window.getHeight();
      RenderTarget â˜ƒxx = new TextureTarget(â˜ƒ, â˜ƒ, true, ON_OSX);
      float â˜ƒxxx = this.player.getXRot();
      float â˜ƒxxxx = this.player.getYRot();
      float â˜ƒxxxxx = this.player.xRotO;
      float â˜ƒxxxxxx = this.player.yRotO;
      this.gameRenderer.setRenderBlockOutline(false);

      TranslatableComponent var12;
      try {
         this.gameRenderer.setPanoramicMode(true);
         this.levelRenderer.graphicsChanged();
         this.window.setWidth(â˜ƒ);
         this.window.setHeight(â˜ƒ);

         for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 6; ++â˜ƒxxxxxxx) {
            switch(â˜ƒxxxxxxx) {
               case 0:
                  this.player.setYRot(â˜ƒxxxx);
                  this.player.setXRot(0.0F);
                  break;
               case 1:
                  this.player.setYRot((â˜ƒxxxx + 90.0F) % 360.0F);
                  this.player.setXRot(0.0F);
                  break;
               case 2:
                  this.player.setYRot((â˜ƒxxxx + 180.0F) % 360.0F);
                  this.player.setXRot(0.0F);
                  break;
               case 3:
                  this.player.setYRot((â˜ƒxxxx - 90.0F) % 360.0F);
                  this.player.setXRot(0.0F);
                  break;
               case 4:
                  this.player.setYRot(â˜ƒxxxx);
                  this.player.setXRot(-90.0F);
                  break;
               case 5:
               default:
                  this.player.setYRot(â˜ƒxxxx);
                  this.player.setXRot(90.0F);
            }

            this.player.yRotO = this.player.getYRot();
            this.player.xRotO = this.player.getXRot();
            â˜ƒxx.bindWrite(true);
            this.gameRenderer.renderLevel(1.0F, 0L, new PoseStack());

            try {
               Thread.sleep(10L);
            } catch (InterruptedException var17) {
            }

            Screenshot.grab(â˜ƒ, "panorama_" + â˜ƒxxxxxxx + ".png", â˜ƒxx, var0 -> {
            });
         }

         Component â˜ƒxxxxxxx = new TextComponent(â˜ƒ.getName())
            .withStyle(ChatFormatting.UNDERLINE)
            .withStyle(var1x -> var1x.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, â˜ƒ.getAbsolutePath())));
         return new TranslatableComponent("screenshot.success", â˜ƒxxxxxxx);
      } catch (Exception var18) {
         LOGGER.error("Couldn't save image", var18);
         var12 = new TranslatableComponent("screenshot.failure", var18.getMessage());
      } finally {
         this.player.setXRot(â˜ƒxxx);
         this.player.setYRot(â˜ƒxxxx);
         this.player.xRotO = â˜ƒxxxxx;
         this.player.yRotO = â˜ƒxxxxxx;
         this.gameRenderer.setRenderBlockOutline(true);
         this.window.setWidth(â˜ƒ);
         this.window.setHeight(â˜ƒx);
         â˜ƒxx.destroyBuffers();
         this.gameRenderer.setPanoramicMode(false);
         this.levelRenderer.graphicsChanged();
         this.getMainRenderTarget().bindWrite(true);
      }

      return var12;
   }

   private Component grabHugeScreenshot(File var1, int var2, int var3, int var4, int var5) {
      try {
         ByteBuffer â˜ƒ = GlUtil.allocateMemory(â˜ƒ * â˜ƒ * 3);
         Screenshot â˜ƒx = new Screenshot(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         float â˜ƒxx = (float)â˜ƒ / (float)â˜ƒ;
         float â˜ƒxxx = (float)â˜ƒ / (float)â˜ƒ;
         float â˜ƒxxxx = â˜ƒxx > â˜ƒxxx ? â˜ƒxx : â˜ƒxxx;

         for(int â˜ƒxxxxx = (â˜ƒ - 1) / â˜ƒ * â˜ƒ; â˜ƒxxxxx >= 0; â˜ƒxxxxx -= â˜ƒ) {
            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒ; â˜ƒxxxxxx += â˜ƒ) {
               RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
               float â˜ƒxxxxxxx = (float)(â˜ƒ - â˜ƒ) / 2.0F * 2.0F - (float)(â˜ƒxxxxxx * 2);
               float â˜ƒxxxxxxxx = (float)(â˜ƒ - â˜ƒ) / 2.0F * 2.0F - (float)(â˜ƒxxxxx * 2);
               â˜ƒxxxxxxx /= (float)â˜ƒ;
               â˜ƒxxxxxxxx /= (float)â˜ƒ;
               this.gameRenderer.renderZoomed(â˜ƒxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
               â˜ƒ.clear();
               RenderSystem.pixelStore(3333, 1);
               RenderSystem.pixelStore(3317, 1);
               RenderSystem.readPixels(0, 0, â˜ƒ, â˜ƒ, 32992, 5121, â˜ƒ);
               â˜ƒx.addRegion(â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ);
            }

            â˜ƒx.saveRow();
         }

         File â˜ƒxxxxx = â˜ƒx.close();
         GlUtil.freeMemory(â˜ƒ);
         Component â˜ƒxxxxxx = new TextComponent(â˜ƒxxxxx.getName())
            .withStyle(ChatFormatting.UNDERLINE)
            .withStyle(var1x -> var1x.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, â˜ƒ.getAbsolutePath())));
         return new TranslatableComponent("screenshot.success", â˜ƒxxxxxx);
      } catch (Exception var15) {
         LOGGER.warn("Couldn't save screenshot", var15);
         return new TranslatableComponent("screenshot.failure", var15.getMessage());
      }
   }

   public ProfilerFiller getProfiler() {
      return this.profiler;
   }

   public Game getGame() {
      return this.game;
   }

   @Nullable
   public StoringChunkProgressListener getProgressListener() {
      return (StoringChunkProgressListener)this.progressListener.get();
   }

   public SplashManager getSplashManager() {
      return this.splashManager;
   }

   @Nullable
   public Overlay getOverlay() {
      return this.overlay;
   }

   public PlayerSocialManager getPlayerSocialManager() {
      return this.playerSocialManager;
   }

   public boolean renderOnThread() {
      return false;
   }

   public Window getWindow() {
      return this.window;
   }

   public RenderBuffers renderBuffers() {
      return this.renderBuffers;
   }

   private static Pack createClientPackAdapter(
      String var0, Component var1, boolean var2, Supplier<PackResources> var3, PackMetadataSection var4, Pack.Position var5, PackSource var6
   ) {
      int â˜ƒ = â˜ƒ.getPackFormat();
      Supplier<PackResources> â˜ƒx = â˜ƒ;
      if (â˜ƒ <= 3) {
         â˜ƒx = adaptV3(â˜ƒ);
      }

      if (â˜ƒ <= 4) {
         â˜ƒx = adaptV4(â˜ƒx);
      }

      return new Pack(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, PackType.CLIENT_RESOURCES, â˜ƒ, â˜ƒ);
   }

   private static Supplier<PackResources> adaptV3(Supplier<PackResources> var0) {
      return () -> new LegacyPackResourcesAdapter((PackResources)â˜ƒ.get(), LegacyPackResourcesAdapter.V3);
   }

   private static Supplier<PackResources> adaptV4(Supplier<PackResources> var0) {
      return () -> new PackResourcesAdapterV4((PackResources)â˜ƒ.get());
   }

   public void updateMaxMipLevel(int var1) {
      this.modelManager.updateMaxMipLevel(â˜ƒ);
   }

   public EntityModelSet getEntityModels() {
      return this.entityModels;
   }

   public boolean isTextFilteringEnabled() {
      return false;
   }

   public static enum ChatStatus {
      ENABLED(TextComponent.EMPTY) {
         @Override
         public boolean isChatAllowed(boolean var1) {
            return true;
         }
      },
      DISABLED_BY_OPTIONS(new TranslatableComponent("chat.disabled.options").withStyle(ChatFormatting.RED)) {
         @Override
         public boolean isChatAllowed(boolean var1) {
            return false;
         }
      },
      DISABLED_BY_LAUNCHER(new TranslatableComponent("chat.disabled.launcher").withStyle(ChatFormatting.RED)) {
         @Override
         public boolean isChatAllowed(boolean var1) {
            return â˜ƒ;
         }
      },
      DISABLED_BY_PROFILE(new TranslatableComponent("chat.disabled.profile").withStyle(ChatFormatting.RED)) {
         @Override
         public boolean isChatAllowed(boolean var1) {
            return â˜ƒ;
         }
      };

      private final Component message;

      ChatStatus(Component var3) {
         this.message = â˜ƒ;
      }

      public Component getMessage() {
         return this.message;
      }

      public abstract boolean isChatAllowed(boolean var1);
   }

   static enum ExperimentalDialogType {
      NONE,
      CREATE,
      BACKUP;
   }

   public static final class ServerStem implements AutoCloseable {
      private final PackRepository packRepository;
      private final ServerResources serverResources;
      private final WorldData worldData;

      ServerStem(PackRepository var1, ServerResources var2, WorldData var3) {
         this.packRepository = â˜ƒ;
         this.serverResources = â˜ƒ;
         this.worldData = â˜ƒ;
      }

      public PackRepository packRepository() {
         return this.packRepository;
      }

      public ServerResources serverResources() {
         return this.serverResources;
      }

      public WorldData worldData() {
         return this.worldData;
      }

      public void close() {
         this.packRepository.close();
         this.serverResources.close();
      }
   }
}
