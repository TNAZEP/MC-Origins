package net.minecraft.client;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Options {
   static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new Gson();
   private static final TypeToken<List<String>> RESOURCE_PACK_TYPE = new TypeToken<List<String>>() {
   };
   public static final int RENDER_DISTANCE_TINY = 2;
   public static final int RENDER_DISTANCE_SHORT = 4;
   public static final int RENDER_DISTANCE_NORMAL = 8;
   public static final int RENDER_DISTANCE_FAR = 12;
   public static final int RENDER_DISTANCE_REALLY_FAR = 16;
   public static final int RENDER_DISTANCE_EXTREME = 32;
   private static final Splitter OPTION_SPLITTER = Splitter.on(':').limit(2);
   private static final float DEFAULT_VOLUME = 1.0F;
   public boolean darkMojangStudiosBackground;
   public double sensitivity = 0.5;
   public int renderDistance;
   public float entityDistanceScaling = 1.0F;
   public int framerateLimit = 120;
   public CloudStatus renderClouds = CloudStatus.FANCY;
   public GraphicsStatus graphicsMode = GraphicsStatus.FANCY;
   public AmbientOcclusionStatus ambientOcclusion = AmbientOcclusionStatus.MAX;
   public List<String> resourcePacks = Lists.newArrayList();
   public List<String> incompatibleResourcePacks = Lists.newArrayList();
   public ChatVisiblity chatVisibility = ChatVisiblity.FULL;
   public double chatOpacity = 1.0;
   public double chatLineSpacing;
   public double textBackgroundOpacity = 0.5;
   @Nullable
   public String fullscreenVideoModeString;
   public boolean hideServerAddress;
   public boolean advancedItemTooltips;
   public boolean pauseOnLostFocus = true;
   private final Set<PlayerModelPart> modelParts = EnumSet.allOf(PlayerModelPart.class);
   public HumanoidArm mainHand = HumanoidArm.RIGHT;
   public int overrideWidth;
   public int overrideHeight;
   public boolean heldItemTooltips = true;
   public double chatScale = 1.0;
   public double chatWidth = 1.0;
   public double chatHeightUnfocused = 0.44366196F;
   public double chatHeightFocused = 1.0;
   public double chatDelay;
   public int mipmapLevels = 4;
   private final Object2FloatMap<SoundSource> sourceVolumes = Util.make(new Object2FloatOpenHashMap(), var0 -> var0.defaultReturnValue(1.0F));
   public boolean useNativeTransport = true;
   public AttackIndicatorStatus attackIndicator = AttackIndicatorStatus.CROSSHAIR;
   public TutorialSteps tutorialStep = TutorialSteps.MOVEMENT;
   public boolean joinedFirstServer = false;
   public boolean hideBundleTutorial = false;
   public int biomeBlendRadius = 2;
   public double mouseWheelSensitivity = 1.0;
   public boolean rawMouseInput = true;
   public int glDebugVerbosity = 1;
   public boolean autoJump = true;
   public boolean autoSuggestions = true;
   public boolean chatColors = true;
   public boolean chatLinks = true;
   public boolean chatLinksPrompt = true;
   public boolean enableVsync = true;
   public boolean entityShadows = true;
   public boolean forceUnicodeFont;
   public boolean invertYMouse;
   public boolean discreteMouseScroll;
   public boolean realmsNotifications = true;
   public boolean reducedDebugInfo;
   public boolean snooperEnabled = true;
   public boolean showSubtitles;
   public boolean backgroundForChatOnly = true;
   public boolean touchscreen;
   public boolean fullscreen;
   public boolean bobView = true;
   public boolean toggleCrouch;
   public boolean toggleSprint;
   public boolean skipMultiplayerWarning;
   public boolean hideMatchedNames = true;
   public final KeyMapping keyUp = new KeyMapping("key.forward", 87, "key.categories.movement");
   public final KeyMapping keyLeft = new KeyMapping("key.left", 65, "key.categories.movement");
   public final KeyMapping keyDown = new KeyMapping("key.back", 83, "key.categories.movement");
   public final KeyMapping keyRight = new KeyMapping("key.right", 68, "key.categories.movement");
   public final KeyMapping keyJump = new KeyMapping("key.jump", 32, "key.categories.movement");
   public final KeyMapping keyShift = new ToggleKeyMapping("key.sneak", 340, "key.categories.movement", () -> this.toggleCrouch);
   public final KeyMapping keySprint = new ToggleKeyMapping("key.sprint", 341, "key.categories.movement", () -> this.toggleSprint);
   public final KeyMapping keyInventory = new KeyMapping("key.inventory", 69, "key.categories.inventory");
   public final KeyMapping keySwapOffhand = new KeyMapping("key.swapOffhand", 70, "key.categories.inventory");
   public final KeyMapping keyDrop = new KeyMapping("key.drop", 81, "key.categories.inventory");
   public final KeyMapping keyUse = new KeyMapping("key.use", InputConstants.Type.MOUSE, 1, "key.categories.gameplay");
   public final KeyMapping keyAttack = new KeyMapping("key.attack", InputConstants.Type.MOUSE, 0, "key.categories.gameplay");
   public final KeyMapping keyPickItem = new KeyMapping("key.pickItem", InputConstants.Type.MOUSE, 2, "key.categories.gameplay");
   public final KeyMapping keyChat = new KeyMapping("key.chat", 84, "key.categories.multiplayer");
   public final KeyMapping keyPlayerList = new KeyMapping("key.playerlist", 258, "key.categories.multiplayer");
   public final KeyMapping keyCommand = new KeyMapping("key.command", 47, "key.categories.multiplayer");
   public final KeyMapping keySocialInteractions = new KeyMapping("key.socialInteractions", 80, "key.categories.multiplayer");
   public final KeyMapping keyScreenshot = new KeyMapping("key.screenshot", 291, "key.categories.misc");
   public final KeyMapping keyTogglePerspective = new KeyMapping("key.togglePerspective", 294, "key.categories.misc");
   public final KeyMapping keySmoothCamera = new KeyMapping("key.smoothCamera", InputConstants.UNKNOWN.getValue(), "key.categories.misc");
   public final KeyMapping keyFullscreen = new KeyMapping("key.fullscreen", 300, "key.categories.misc");
   public final KeyMapping keySpectatorOutlines = new KeyMapping("key.spectatorOutlines", InputConstants.UNKNOWN.getValue(), "key.categories.misc");
   public final KeyMapping keyAdvancements = new KeyMapping("key.advancements", 76, "key.categories.misc");
   public final KeyMapping[] keyHotbarSlots = new KeyMapping[]{
      new KeyMapping("key.hotbar.1", 49, "key.categories.inventory"),
      new KeyMapping("key.hotbar.2", 50, "key.categories.inventory"),
      new KeyMapping("key.hotbar.3", 51, "key.categories.inventory"),
      new KeyMapping("key.hotbar.4", 52, "key.categories.inventory"),
      new KeyMapping("key.hotbar.5", 53, "key.categories.inventory"),
      new KeyMapping("key.hotbar.6", 54, "key.categories.inventory"),
      new KeyMapping("key.hotbar.7", 55, "key.categories.inventory"),
      new KeyMapping("key.hotbar.8", 56, "key.categories.inventory"),
      new KeyMapping("key.hotbar.9", 57, "key.categories.inventory")
   };
   public final KeyMapping keySaveHotbarActivator = new KeyMapping("key.saveToolbarActivator", 67, "key.categories.creative");
   public final KeyMapping keyLoadHotbarActivator = new KeyMapping("key.loadToolbarActivator", 88, "key.categories.creative");
   public final KeyMapping[] keyMappings = ArrayUtils.addAll(
      new KeyMapping[]{
         this.keyAttack,
         this.keyUse,
         this.keyUp,
         this.keyLeft,
         this.keyDown,
         this.keyRight,
         this.keyJump,
         this.keyShift,
         this.keySprint,
         this.keyDrop,
         this.keyInventory,
         this.keyChat,
         this.keyPlayerList,
         this.keyPickItem,
         this.keyCommand,
         this.keySocialInteractions,
         this.keyScreenshot,
         this.keyTogglePerspective,
         this.keySmoothCamera,
         this.keyFullscreen,
         this.keySpectatorOutlines,
         this.keySwapOffhand,
         this.keySaveHotbarActivator,
         this.keyLoadHotbarActivator,
         this.keyAdvancements
      },
      this.keyHotbarSlots
   );
   protected Minecraft minecraft;
   private final File optionsFile;
   public Difficulty difficulty = Difficulty.NORMAL;
   public boolean hideGui;
   private CameraType cameraType = CameraType.FIRST_PERSON;
   public boolean renderDebug;
   public boolean renderDebugCharts;
   public boolean renderFpsChart;
   public String lastMpIp = "";
   public boolean smoothCamera;
   public double fov = 70.0;
   public float screenEffectScale = 1.0F;
   public float fovEffectScale = 1.0F;
   public double gamma;
   public int guiScale;
   public ParticleStatus particles = ParticleStatus.ALL;
   public NarratorStatus narratorStatus = NarratorStatus.OFF;
   public String languageCode = "en_us";
   public boolean syncWrites;

   public Options(Minecraft var1, File var2) {
      this.minecraft = â˜ƒ;
      this.optionsFile = new File(â˜ƒ, "options.txt");
      if (â˜ƒ.is64Bit() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         Option.RENDER_DISTANCE.setMaxValue(32.0F);
      } else {
         Option.RENDER_DISTANCE.setMaxValue(16.0F);
      }

      this.renderDistance = â˜ƒ.is64Bit() ? 12 : 8;
      this.syncWrites = Util.getPlatform() == Util.OS.WINDOWS;
      this.load();
   }

   public float getBackgroundOpacity(float var1) {
      return this.backgroundForChatOnly ? â˜ƒ : (float)this.textBackgroundOpacity;
   }

   public int getBackgroundColor(float var1) {
      return (int)(this.getBackgroundOpacity(â˜ƒ) * 255.0F) << 24 & 0xFF000000;
   }

   public int getBackgroundColor(int var1) {
      return this.backgroundForChatOnly ? â˜ƒ : (int)(this.textBackgroundOpacity * 255.0) << 24 & 0xFF000000;
   }

   public void setKey(KeyMapping var1, InputConstants.Key var2) {
      â˜ƒ.setKey(â˜ƒ);
      this.save();
   }

   private void processOptions(Options.FieldAccess var1) {
      this.autoJump = â˜ƒ.process("autoJump", this.autoJump);
      this.autoSuggestions = â˜ƒ.process("autoSuggestions", this.autoSuggestions);
      this.chatColors = â˜ƒ.process("chatColors", this.chatColors);
      this.chatLinks = â˜ƒ.process("chatLinks", this.chatLinks);
      this.chatLinksPrompt = â˜ƒ.process("chatLinksPrompt", this.chatLinksPrompt);
      this.enableVsync = â˜ƒ.process("enableVsync", this.enableVsync);
      this.entityShadows = â˜ƒ.process("entityShadows", this.entityShadows);
      this.forceUnicodeFont = â˜ƒ.process("forceUnicodeFont", this.forceUnicodeFont);
      this.discreteMouseScroll = â˜ƒ.process("discrete_mouse_scroll", this.discreteMouseScroll);
      this.invertYMouse = â˜ƒ.process("invertYMouse", this.invertYMouse);
      this.realmsNotifications = â˜ƒ.process("realmsNotifications", this.realmsNotifications);
      this.reducedDebugInfo = â˜ƒ.process("reducedDebugInfo", this.reducedDebugInfo);
      this.snooperEnabled = â˜ƒ.process("snooperEnabled", this.snooperEnabled);
      this.showSubtitles = â˜ƒ.process("showSubtitles", this.showSubtitles);
      this.touchscreen = â˜ƒ.process("touchscreen", this.touchscreen);
      this.fullscreen = â˜ƒ.process("fullscreen", this.fullscreen);
      this.bobView = â˜ƒ.process("bobView", this.bobView);
      this.toggleCrouch = â˜ƒ.process("toggleCrouch", this.toggleCrouch);
      this.toggleSprint = â˜ƒ.process("toggleSprint", this.toggleSprint);
      this.darkMojangStudiosBackground = â˜ƒ.process("darkMojangStudiosBackground", this.darkMojangStudiosBackground);
      this.sensitivity = â˜ƒ.process("mouseSensitivity", this.sensitivity);
      this.fov = â˜ƒ.process("fov", (this.fov - 70.0) / 40.0) * 40.0 + 70.0;
      this.screenEffectScale = â˜ƒ.process("screenEffectScale", this.screenEffectScale);
      this.fovEffectScale = â˜ƒ.process("fovEffectScale", this.fovEffectScale);
      this.gamma = â˜ƒ.process("gamma", this.gamma);
      this.renderDistance = â˜ƒ.process("renderDistance", this.renderDistance);
      this.entityDistanceScaling = â˜ƒ.process("entityDistanceScaling", this.entityDistanceScaling);
      this.guiScale = â˜ƒ.process("guiScale", this.guiScale);
      this.particles = â˜ƒ.process("particles", this.particles, ParticleStatus::byId, ParticleStatus::getId);
      this.framerateLimit = â˜ƒ.process("maxFps", this.framerateLimit);
      this.difficulty = â˜ƒ.process("difficulty", this.difficulty, Difficulty::byId, Difficulty::getId);
      this.graphicsMode = â˜ƒ.process("graphicsMode", this.graphicsMode, GraphicsStatus::byId, GraphicsStatus::getId);
      this.ambientOcclusion = â˜ƒ.process("ao", this.ambientOcclusion, Options::readAmbientOcclusion, var0 -> Integer.toString(var0.getId()));
      this.biomeBlendRadius = â˜ƒ.process("biomeBlendRadius", this.biomeBlendRadius);
      this.renderClouds = â˜ƒ.process("renderClouds", this.renderClouds, Options::readCloudStatus, Options::writeCloudStatus);
      this.resourcePacks = â˜ƒ.process("resourcePacks", this.resourcePacks, Options::readPackList, GSON::toJson);
      this.incompatibleResourcePacks = â˜ƒ.process("incompatibleResourcePacks", this.incompatibleResourcePacks, Options::readPackList, GSON::toJson);
      this.lastMpIp = â˜ƒ.process("lastServer", this.lastMpIp);
      this.languageCode = â˜ƒ.process("lang", this.languageCode);
      this.chatVisibility = â˜ƒ.process("chatVisibility", this.chatVisibility, ChatVisiblity::byId, ChatVisiblity::getId);
      this.chatOpacity = â˜ƒ.process("chatOpacity", this.chatOpacity);
      this.chatLineSpacing = â˜ƒ.process("chatLineSpacing", this.chatLineSpacing);
      this.textBackgroundOpacity = â˜ƒ.process("textBackgroundOpacity", this.textBackgroundOpacity);
      this.backgroundForChatOnly = â˜ƒ.process("backgroundForChatOnly", this.backgroundForChatOnly);
      this.hideServerAddress = â˜ƒ.process("hideServerAddress", this.hideServerAddress);
      this.advancedItemTooltips = â˜ƒ.process("advancedItemTooltips", this.advancedItemTooltips);
      this.pauseOnLostFocus = â˜ƒ.process("pauseOnLostFocus", this.pauseOnLostFocus);
      this.overrideWidth = â˜ƒ.process("overrideWidth", this.overrideWidth);
      this.overrideHeight = â˜ƒ.process("overrideHeight", this.overrideHeight);
      this.heldItemTooltips = â˜ƒ.process("heldItemTooltips", this.heldItemTooltips);
      this.chatHeightFocused = â˜ƒ.process("chatHeightFocused", this.chatHeightFocused);
      this.chatDelay = â˜ƒ.process("chatDelay", this.chatDelay);
      this.chatHeightUnfocused = â˜ƒ.process("chatHeightUnfocused", this.chatHeightUnfocused);
      this.chatScale = â˜ƒ.process("chatScale", this.chatScale);
      this.chatWidth = â˜ƒ.process("chatWidth", this.chatWidth);
      this.mipmapLevels = â˜ƒ.process("mipmapLevels", this.mipmapLevels);
      this.useNativeTransport = â˜ƒ.process("useNativeTransport", this.useNativeTransport);
      this.mainHand = â˜ƒ.process("mainHand", this.mainHand, Options::readMainHand, Options::writeMainHand);
      this.attackIndicator = â˜ƒ.process("attackIndicator", this.attackIndicator, AttackIndicatorStatus::byId, AttackIndicatorStatus::getId);
      this.narratorStatus = â˜ƒ.process("narrator", this.narratorStatus, NarratorStatus::byId, NarratorStatus::getId);
      this.tutorialStep = â˜ƒ.process("tutorialStep", this.tutorialStep, TutorialSteps::getByName, TutorialSteps::getName);
      this.mouseWheelSensitivity = â˜ƒ.process("mouseWheelSensitivity", this.mouseWheelSensitivity);
      this.rawMouseInput = â˜ƒ.process("rawMouseInput", this.rawMouseInput);
      this.glDebugVerbosity = â˜ƒ.process("glDebugVerbosity", this.glDebugVerbosity);
      this.skipMultiplayerWarning = â˜ƒ.process("skipMultiplayerWarning", this.skipMultiplayerWarning);
      this.hideMatchedNames = â˜ƒ.process("hideMatchedNames", this.hideMatchedNames);
      this.joinedFirstServer = â˜ƒ.process("joinedFirstServer", this.joinedFirstServer);
      this.hideBundleTutorial = â˜ƒ.process("hideBundleTutorial", this.hideBundleTutorial);
      this.syncWrites = â˜ƒ.process("syncChunkWrites", this.syncWrites);

      for(KeyMapping â˜ƒ : this.keyMappings) {
         String â˜ƒx = â˜ƒ.saveString();
         String â˜ƒxx = â˜ƒ.process("key_" + â˜ƒ.getName(), â˜ƒx);
         if (!â˜ƒx.equals(â˜ƒxx)) {
            â˜ƒ.setKey(InputConstants.getKey(â˜ƒxx));
         }
      }

      for(SoundSource â˜ƒ : SoundSource.values()) {
         this.sourceVolumes.computeFloat(â˜ƒ, (var1x, var2) -> â˜ƒ.process("soundCategory_" + var1x.getName(), var2 != null ? var2 : 1.0F));
      }

      for(PlayerModelPart â˜ƒ : PlayerModelPart.values()) {
         boolean â˜ƒx = this.modelParts.contains(â˜ƒ);
         boolean â˜ƒxx = â˜ƒ.process("modelPart_" + â˜ƒ.getId(), â˜ƒx);
         if (â˜ƒxx != â˜ƒx) {
            this.setModelPart(â˜ƒ, â˜ƒxx);
         }
      }
   }

   public void load() {
      try {
         if (!this.optionsFile.exists()) {
            return;
         }

         this.sourceVolumes.clear();
         CompoundTag â˜ƒ = new CompoundTag();
         BufferedReader â˜ƒx = Files.newReader(this.optionsFile, Charsets.UTF_8);

         try {
            â˜ƒx.lines().forEach(var1x -> {
               try {
                  Iterator<String> â˜ƒ = OPTION_SPLITTER.split(var1x).iterator();
                  â˜ƒ.putString((String)â˜ƒ.next(), (String)â˜ƒ.next());
               } catch (Exception var3) {
                  LOGGER.warn("Skipping bad option: {}", var1x);
               }
            });
         } catch (Throwable var6) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }

         final CompoundTag â˜ƒxx = this.dataFix(â˜ƒ);
         if (!â˜ƒxx.contains("graphicsMode") && â˜ƒxx.contains("fancyGraphics")) {
            if (isTrue(â˜ƒxx.getString("fancyGraphics"))) {
               this.graphicsMode = GraphicsStatus.FANCY;
            } else {
               this.graphicsMode = GraphicsStatus.FAST;
            }
         }

         this.processOptions(new Options.FieldAccess() {
            @Nullable
            private String getValueOrNull(String var1) {
               return â˜ƒ.contains(â˜ƒ) ? â˜ƒ.getString(â˜ƒ) : null;
            }

            @Override
            public int process(String var1, int var2) {
               String â˜ƒ = this.getValueOrNull(â˜ƒ);
               if (â˜ƒ != null) {
                  try {
                     return Integer.parseInt(â˜ƒ);
                  } catch (NumberFormatException var5) {
                     Options.LOGGER.warn("Invalid integer value for option {} = {}", â˜ƒ, â˜ƒ, var5);
                  }
               }

               return â˜ƒ;
            }

            @Override
            public boolean process(String var1, boolean var2) {
               String â˜ƒ = this.getValueOrNull(â˜ƒ);
               return â˜ƒ != null ? Options.isTrue(â˜ƒ) : â˜ƒ;
            }

            @Override
            public String process(String var1, String var2) {
               return MoreObjects.firstNonNull(this.getValueOrNull(â˜ƒ), â˜ƒ);
            }

            @Override
            public double process(String var1, double var2) {
               String â˜ƒ = this.getValueOrNull(â˜ƒ);
               if (â˜ƒ == null) {
                  return â˜ƒ;
               } else if (Options.isTrue(â˜ƒ)) {
                  return 1.0;
               } else if (Options.isFalse(â˜ƒ)) {
                  return 0.0;
               } else {
                  try {
                     return Double.parseDouble(â˜ƒ);
                  } catch (NumberFormatException var6) {
                     Options.LOGGER.warn("Invalid floating point value for option {} = {}", â˜ƒ, â˜ƒ, var6);
                     return â˜ƒ;
                  }
               }
            }

            @Override
            public float process(String var1, float var2) {
               String â˜ƒ = this.getValueOrNull(â˜ƒ);
               if (â˜ƒ == null) {
                  return â˜ƒ;
               } else if (Options.isTrue(â˜ƒ)) {
                  return 1.0F;
               } else if (Options.isFalse(â˜ƒ)) {
                  return 0.0F;
               } else {
                  try {
                     return Float.parseFloat(â˜ƒ);
                  } catch (NumberFormatException var5) {
                     Options.LOGGER.warn("Invalid floating point value for option {} = {}", â˜ƒ, â˜ƒ, var5);
                     return â˜ƒ;
                  }
               }
            }

            @Override
            public <T> T process(String var1, T var2, Function<String, T> var3, Function<T, String> var4) {
               String â˜ƒ = this.getValueOrNull(â˜ƒ);
               return (T)(â˜ƒ == null ? â˜ƒ : â˜ƒ.apply(â˜ƒ));
            }

            @Override
            public <T> T process(String var1, T var2, IntFunction<T> var3, ToIntFunction<T> var4) {
               String â˜ƒ = this.getValueOrNull(â˜ƒ);
               if (â˜ƒ != null) {
                  try {
                     return (T)â˜ƒ.apply(Integer.parseInt(â˜ƒ));
                  } catch (Exception var7) {
                     Options.LOGGER.warn("Invalid integer value for option {} = {}", â˜ƒ, â˜ƒ, var7);
                  }
               }

               return â˜ƒ;
            }
         });
         if (â˜ƒxx.contains("fullscreenResolution")) {
            this.fullscreenVideoModeString = â˜ƒxx.getString("fullscreenResolution");
         }

         if (this.minecraft.getWindow() != null) {
            this.minecraft.getWindow().setFramerateLimit(this.framerateLimit);
         }

         KeyMapping.resetMapping();
      } catch (Exception var7) {
         LOGGER.error("Failed to load options", var7);
      }
   }

   static boolean isTrue(String var0) {
      return "true".equals(â˜ƒ);
   }

   static boolean isFalse(String var0) {
      return "false".equals(â˜ƒ);
   }

   private CompoundTag dataFix(CompoundTag var1) {
      int â˜ƒ = 0;

      try {
         â˜ƒ = Integer.parseInt(â˜ƒ.getString("version"));
      } catch (RuntimeException var4) {
      }

      return NbtUtils.update(this.minecraft.getFixerUpper(), DataFixTypes.OPTIONS, â˜ƒ, â˜ƒ);
   }

   public void save() {
      try {
         final PrintWriter â˜ƒ = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8));

         try {
            â˜ƒ.println("version:" + SharedConstants.getCurrentVersion().getWorldVersion());
            this.processOptions(new Options.FieldAccess() {
               public void writePrefix(String var1x) {
                  â˜ƒ.print(â˜ƒ);
                  â˜ƒ.print(':');
               }

               @Override
               public int process(String var1x, int var2) {
                  this.writePrefix(â˜ƒ);
                  â˜ƒ.println(â˜ƒ);
                  return â˜ƒ;
               }

               @Override
               public boolean process(String var1x, boolean var2) {
                  this.writePrefix(â˜ƒ);
                  â˜ƒ.println(â˜ƒ);
                  return â˜ƒ;
               }

               @Override
               public String process(String var1x, String var2) {
                  this.writePrefix(â˜ƒ);
                  â˜ƒ.println(â˜ƒ);
                  return â˜ƒ;
               }

               @Override
               public double process(String var1x, double var2) {
                  this.writePrefix(â˜ƒ);
                  â˜ƒ.println(â˜ƒ);
                  return â˜ƒ;
               }

               @Override
               public float process(String var1x, float var2) {
                  this.writePrefix(â˜ƒ);
                  â˜ƒ.println(â˜ƒ);
                  return â˜ƒ;
               }

               @Override
               public <T> T process(String var1x, T var2, Function<String, T> var3, Function<T, String> var4) {
                  this.writePrefix(â˜ƒ);
                  â˜ƒ.println((String)â˜ƒ.apply(â˜ƒ));
                  return â˜ƒ;
               }

               @Override
               public <T> T process(String var1x, T var2, IntFunction<T> var3, ToIntFunction<T> var4) {
                  this.writePrefix(â˜ƒ);
                  â˜ƒ.println(â˜ƒ.applyAsInt(â˜ƒ));
                  return â˜ƒ;
               }
            });
            if (this.minecraft.getWindow().getPreferredFullscreenVideoMode().isPresent()) {
               â˜ƒ.println("fullscreenResolution:" + ((VideoMode)this.minecraft.getWindow().getPreferredFullscreenVideoMode().get()).write());
            }
         } catch (Throwable var5) {
            try {
               â˜ƒ.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }

            throw var5;
         }

         â˜ƒ.close();
      } catch (Exception var6) {
         LOGGER.error("Failed to save options", var6);
      }

      this.broadcastOptions();
   }

   public float getSoundSourceVolume(SoundSource var1) {
      return this.sourceVolumes.getFloat(â˜ƒ);
   }

   public void setSoundCategoryVolume(SoundSource var1, float var2) {
      this.sourceVolumes.put(â˜ƒ, â˜ƒ);
      this.minecraft.getSoundManager().updateSourceVolume(â˜ƒ, â˜ƒ);
   }

   public void broadcastOptions() {
      if (this.minecraft.player != null) {
         int â˜ƒ = 0;

         for(PlayerModelPart â˜ƒx : this.modelParts) {
            â˜ƒ |= â˜ƒx.getMask();
         }

         this.minecraft
            .player
            .connection
            .send(
               new ServerboundClientInformationPacket(
                  this.languageCode, this.renderDistance, this.chatVisibility, this.chatColors, â˜ƒ, this.mainHand, this.minecraft.isTextFilteringEnabled()
               )
            );
      }
   }

   private void setModelPart(PlayerModelPart var1, boolean var2) {
      if (â˜ƒ) {
         this.modelParts.add(â˜ƒ);
      } else {
         this.modelParts.remove(â˜ƒ);
      }
   }

   public boolean isModelPartEnabled(PlayerModelPart var1) {
      return this.modelParts.contains(â˜ƒ);
   }

   public void toggleModelPart(PlayerModelPart var1, boolean var2) {
      this.setModelPart(â˜ƒ, â˜ƒ);
      this.broadcastOptions();
   }

   public CloudStatus getCloudsType() {
      return this.renderDistance >= 4 ? this.renderClouds : CloudStatus.OFF;
   }

   public boolean useNativeTransport() {
      return this.useNativeTransport;
   }

   public void loadSelectedResourcePacks(PackRepository var1) {
      Set<String> â˜ƒ = Sets.newLinkedHashSet();
      Iterator<String> â˜ƒx = this.resourcePacks.iterator();

      while(â˜ƒx.hasNext()) {
         String â˜ƒxx = (String)â˜ƒx.next();
         Pack â˜ƒxxx = â˜ƒ.getPack(â˜ƒxx);
         if (â˜ƒxxx == null && !â˜ƒxx.startsWith("file/")) {
            â˜ƒxxx = â˜ƒ.getPack("file/" + â˜ƒxx);
         }

         if (â˜ƒxxx == null) {
            LOGGER.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", â˜ƒxx);
            â˜ƒx.remove();
         } else if (!â˜ƒxxx.getCompatibility().isCompatible() && !this.incompatibleResourcePacks.contains(â˜ƒxx)) {
            LOGGER.warn("Removed resource pack {} from options because it is no longer compatible", â˜ƒxx);
            â˜ƒx.remove();
         } else if (â˜ƒxxx.getCompatibility().isCompatible() && this.incompatibleResourcePacks.contains(â˜ƒxx)) {
            LOGGER.info("Removed resource pack {} from incompatibility list because it's now compatible", â˜ƒxx);
            this.incompatibleResourcePacks.remove(â˜ƒxx);
         } else {
            â˜ƒ.add(â˜ƒxxx.getId());
         }
      }

      â˜ƒ.setSelected(â˜ƒ);
   }

   public CameraType getCameraType() {
      return this.cameraType;
   }

   public void setCameraType(CameraType var1) {
      this.cameraType = â˜ƒ;
   }

   private static List<String> readPackList(String var0) {
      List<String> â˜ƒ = GsonHelper.fromJson(GSON, â˜ƒ, RESOURCE_PACK_TYPE);
      return (List<String>)(â˜ƒ != null ? â˜ƒ : Lists.newArrayList());
   }

   private static CloudStatus readCloudStatus(String var0) {
      switch(â˜ƒ) {
         case "true":
            return CloudStatus.FANCY;
         case "fast":
            return CloudStatus.FAST;
         case "false":
         default:
            return CloudStatus.OFF;
      }
   }

   private static String writeCloudStatus(CloudStatus var0) {
      switch(â˜ƒ) {
         case FANCY:
            return "true";
         case FAST:
            return "fast";
         case OFF:
         default:
            return "false";
      }
   }

   private static AmbientOcclusionStatus readAmbientOcclusion(String var0) {
      if (isTrue(â˜ƒ)) {
         return AmbientOcclusionStatus.MAX;
      } else {
         return isFalse(â˜ƒ) ? AmbientOcclusionStatus.OFF : AmbientOcclusionStatus.byId(Integer.parseInt(â˜ƒ));
      }
   }

   private static HumanoidArm readMainHand(String var0) {
      return "left".equals(â˜ƒ) ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
   }

   private static String writeMainHand(HumanoidArm var0) {
      return â˜ƒ == HumanoidArm.LEFT ? "left" : "right";
   }

   public File getFile() {
      return this.optionsFile;
   }

   public String dumpOptionsForReport() {
      ImmutableList<Pair<String, String>> â˜ƒ = ImmutableList.<Pair<String, String>>builder()
         .add(Pair.of("ao", String.valueOf(this.ambientOcclusion)))
         .add(Pair.of("biomeBlendRadius", String.valueOf(this.biomeBlendRadius)))
         .add(Pair.of("enableVsync", String.valueOf(this.enableVsync)))
         .add(Pair.of("entityDistanceScaling", String.valueOf(this.entityDistanceScaling)))
         .add(Pair.of("entityShadows", String.valueOf(this.entityShadows)))
         .add(Pair.of("forceUnicodeFont", String.valueOf(this.forceUnicodeFont)))
         .add(Pair.of("fov", String.valueOf(this.fov)))
         .add(Pair.of("fovEffectScale", String.valueOf(this.fovEffectScale)))
         .add(Pair.of("fullscreen", String.valueOf(this.fullscreen)))
         .add(Pair.of("fullscreenResolution", String.valueOf(this.fullscreenVideoModeString)))
         .add(Pair.of("gamma", String.valueOf(this.gamma)))
         .add(Pair.of("glDebugVerbosity", String.valueOf(this.glDebugVerbosity)))
         .add(Pair.of("graphicsMode", String.valueOf(this.graphicsMode)))
         .add(Pair.of("guiScale", String.valueOf(this.guiScale)))
         .add(Pair.of("maxFps", String.valueOf(this.framerateLimit)))
         .add(Pair.of("mipmapLevels", String.valueOf(this.mipmapLevels)))
         .add(Pair.of("narrator", String.valueOf(this.narratorStatus)))
         .add(Pair.of("overrideHeight", String.valueOf(this.overrideHeight)))
         .add(Pair.of("overrideWidth", String.valueOf(this.overrideWidth)))
         .add(Pair.of("particles", String.valueOf(this.particles)))
         .add(Pair.of("reducedDebugInfo", String.valueOf(this.reducedDebugInfo)))
         .add(Pair.of("renderClouds", String.valueOf(this.renderClouds)))
         .add(Pair.of("renderDistance", String.valueOf(this.renderDistance)))
         .add(Pair.of("resourcePacks", String.valueOf(this.resourcePacks)))
         .add(Pair.of("screenEffectScale", String.valueOf(this.screenEffectScale)))
         .add(Pair.of("syncChunkWrites", String.valueOf(this.syncWrites)))
         .add(Pair.of("useNativeTransport", String.valueOf(this.useNativeTransport)))
         .build();
      return (String)â˜ƒ.stream().map(var0 -> (String)var0.getFirst() + ": " + (String)var0.getSecond()).collect(Collectors.joining(System.lineSeparator()));
   }

   interface FieldAccess {
      int process(String var1, int var2);

      boolean process(String var1, boolean var2);

      String process(String var1, String var2);

      double process(String var1, double var2);

      float process(String var1, float var2);

      <T> T process(String var1, T var2, Function<String, T> var3, Function<T, String> var4);

      <T> T process(String var1, T var2, IntFunction<T> var3, ToIntFunction<T> var4);
   }
}
