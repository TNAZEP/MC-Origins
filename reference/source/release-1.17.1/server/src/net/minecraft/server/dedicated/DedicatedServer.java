package net.minecraft.server.dedicated;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ConsoleInput;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.ServerResources;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.network.TextFilterClient;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.server.rcon.RconConsoleSource;
import net.minecraft.server.rcon.thread.QueryThreadGs4;
import net.minecraft.server.rcon.thread.RconThread;
import net.minecraft.util.Mth;
import net.minecraft.util.monitoring.jmx.MinecraftServerStatistics;
import net.minecraft.world.Snooper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedServer extends MinecraftServer implements ServerInterface {
   static final Logger LOGGER = LogManager.getLogger();
   private static final int CONVERSION_RETRY_DELAY_MS = 5000;
   private static final int CONVERSION_RETRIES = 2;
   private static final Pattern SHA1 = Pattern.compile("^[a-fA-F0-9]{40}$");
   private final List<ConsoleInput> consoleInput = Collections.synchronizedList(Lists.newArrayList());
   private QueryThreadGs4 queryThreadGs4;
   private final RconConsoleSource rconConsoleSource;
   private RconThread rconThread;
   private final DedicatedServerSettings settings;
   @Nullable
   private MinecraftServerGui gui;
   @Nullable
   private final TextFilterClient textFilterClient;
   @Nullable
   private final Component resourcePackPrompt;

   public DedicatedServer(
      Thread var1,
      RegistryAccess.RegistryHolder var2,
      LevelStorageSource.LevelStorageAccess var3,
      PackRepository var4,
      ServerResources var5,
      WorldData var6,
      DedicatedServerSettings var7,
      DataFixer var8,
      MinecraftSessionService var9,
      GameProfileRepository var10,
      GameProfileCache var11,
      ChunkProgressListenerFactory var12
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Proxy.NO_PROXY, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.settings = â˜ƒ;
      this.rconConsoleSource = new RconConsoleSource(this);
      this.textFilterClient = TextFilterClient.createFromConfig(â˜ƒ.getProperties().textFilteringConfig);
      this.resourcePackPrompt = parseResourcePackPrompt(â˜ƒ);
   }

   @Override
   public boolean initServer() throws IOException {
      Thread â˜ƒ = new Thread("Server console handler") {
         public void run() {
            BufferedReader â˜ƒ = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            String â˜ƒ;
            try {
               while(!DedicatedServer.this.isStopped() && DedicatedServer.this.isRunning() && (â˜ƒ = â˜ƒ.readLine()) != null) {
                  DedicatedServer.this.handleConsoleInput(â˜ƒ, DedicatedServer.this.createCommandSourceStack());
               }
            } catch (IOException var4) {
               DedicatedServer.LOGGER.error("Exception handling console input", var4);
            }
         }
      };
      â˜ƒ.setDaemon(true);
      â˜ƒ.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
      â˜ƒ.start();
      LOGGER.info("Starting minecraft server version {}", SharedConstants.getCurrentVersion().getName());
      if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
         LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
      }

      LOGGER.info("Loading properties");
      DedicatedServerProperties â˜ƒ = this.settings.getProperties();
      if (this.isSingleplayer()) {
         this.setLocalIp("127.0.0.1");
      } else {
         this.setUsesAuthentication(â˜ƒ.onlineMode);
         this.setPreventProxyConnections(â˜ƒ.preventProxyConnections);
         this.setLocalIp(â˜ƒ.serverIp);
      }

      this.setPvpAllowed(â˜ƒ.pvp);
      this.setFlightAllowed(â˜ƒ.allowFlight);
      this.setResourcePack(â˜ƒ.resourcePack, this.getPackHash());
      this.setMotd(â˜ƒ.motd);
      super.setPlayerIdleTimeout(â˜ƒ.playerIdleTimeout.get());
      this.setEnforceWhitelist(â˜ƒ.enforceWhitelist);
      this.worldData.setGameType(â˜ƒ.gamemode);
      LOGGER.info("Default game type: {}", â˜ƒ.gamemode);
      InetAddress â˜ƒ = null;
      if (!this.getLocalIp().isEmpty()) {
         â˜ƒ = InetAddress.getByName(this.getLocalIp());
      }

      if (this.getPort() < 0) {
         this.setPort(â˜ƒ.serverPort);
      }

      this.initializeKeyPair();
      LOGGER.info("Starting Minecraft server on {}:{}", this.getLocalIp().isEmpty() ? "*" : this.getLocalIp(), this.getPort());

      try {
         this.getConnection().startTcpServerListener(â˜ƒ, this.getPort());
      } catch (IOException var10) {
         LOGGER.warn("**** FAILED TO BIND TO PORT!");
         LOGGER.warn("The exception was: {}", var10.toString());
         LOGGER.warn("Perhaps a server is already running on that port?");
         return false;
      }

      if (!this.usesAuthentication()) {
         LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
         LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
         LOGGER.warn(
            "While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose."
         );
         LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
      }

      if (this.convertOldUsers()) {
         this.getProfileCache().save();
      }

      if (!OldUsersConverter.serverReadyAfterUserconversion(this)) {
         return false;
      } else {
         this.setPlayerList(new DedicatedPlayerList(this, this.registryHolder, this.playerDataStorage));
         long â˜ƒ = Util.getNanos();
         SkullBlockEntity.setProfileCache(this.getProfileCache());
         SkullBlockEntity.setSessionService(this.getSessionService());
         SkullBlockEntity.setMainThreadExecutor(this);
         GameProfileCache.setUsesAuthentication(this.usesAuthentication());
         LOGGER.info("Preparing level \"{}\"", this.getLevelIdName());
         this.loadLevel();
         long â˜ƒx = Util.getNanos() - â˜ƒ;
         String â˜ƒxx = String.format(Locale.ROOT, "%.3fs", (double)â˜ƒx / 1.0E9);
         LOGGER.info("Done ({})! For help, type \"help\"", â˜ƒxx);
         if (â˜ƒ.announcePlayerAchievements != null) {
            this.getGameRules().getRule(GameRules.RULE_ANNOUNCE_ADVANCEMENTS).set(â˜ƒ.announcePlayerAchievements, this);
         }

         if (â˜ƒ.enableQuery) {
            LOGGER.info("Starting GS4 status listener");
            this.queryThreadGs4 = QueryThreadGs4.create(this);
         }

         if (â˜ƒ.enableRcon) {
            LOGGER.info("Starting remote control listener");
            this.rconThread = RconThread.create(this);
         }

         if (this.getMaxTickLength() > 0L) {
            Thread â˜ƒ = new Thread(new ServerWatchdog(this));
            â˜ƒ.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(LOGGER));
            â˜ƒ.setName("Server Watchdog");
            â˜ƒ.setDaemon(true);
            â˜ƒ.start();
         }

         Items.AIR.fillItemCategory(CreativeModeTab.TAB_SEARCH, NonNullList.create());
         if (â˜ƒ.enableJmxMonitoring) {
            MinecraftServerStatistics.registerJmxMonitoring(this);
            LOGGER.info("JMX monitoring enabled");
         }

         return true;
      }
   }

   @Override
   public boolean isSpawningAnimals() {
      return this.getProperties().spawnAnimals && super.isSpawningAnimals();
   }

   @Override
   public boolean isSpawningMonsters() {
      return this.settings.getProperties().spawnMonsters && super.isSpawningMonsters();
   }

   @Override
   public boolean areNpcsEnabled() {
      return this.settings.getProperties().spawnNpcs && super.areNpcsEnabled();
   }

   public String getPackHash() {
      DedicatedServerProperties â˜ƒx = this.settings.getProperties();
      String â˜ƒ;
      if (!â˜ƒx.resourcePackSha1.isEmpty()) {
         â˜ƒ = â˜ƒx.resourcePackSha1;
         if (!Strings.isNullOrEmpty(â˜ƒx.resourcePackHash)) {
            LOGGER.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
         }
      } else if (!Strings.isNullOrEmpty(â˜ƒx.resourcePackHash)) {
         LOGGER.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
         â˜ƒ = â˜ƒx.resourcePackHash;
      } else {
         â˜ƒ = "";
      }

      if (!â˜ƒ.isEmpty() && !SHA1.matcher(â˜ƒ).matches()) {
         LOGGER.warn("Invalid sha1 for ressource-pack-sha1");
      }

      if (!â˜ƒx.resourcePack.isEmpty() && â˜ƒ.isEmpty()) {
         LOGGER.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
      }

      return â˜ƒ;
   }

   @Override
   public DedicatedServerProperties getProperties() {
      return this.settings.getProperties();
   }

   @Override
   public void forceDifficulty() {
      this.setDifficulty(this.getProperties().difficulty, true);
   }

   @Override
   public boolean isHardcore() {
      return this.getProperties().hardcore;
   }

   @Override
   public SystemReport fillServerSystemReport(SystemReport var1) {
      â˜ƒ.setDetail("Is Modded", (Supplier<String>)(() -> (String)this.getModdedStatus().orElse("Unknown (can't tell)")));
      â˜ƒ.setDetail("Type", (Supplier<String>)(() -> "Dedicated Server (map_server.txt)"));
      return â˜ƒ;
   }

   @Override
   public void dumpServerProperties(Path var1) throws IOException {
      DedicatedServerProperties â˜ƒ = this.getProperties();
      Writer â˜ƒx = Files.newBufferedWriter(â˜ƒ);

      try {
         â˜ƒx.write(String.format("sync-chunk-writes=%s%n", â˜ƒ.syncChunkWrites));
         â˜ƒx.write(String.format("gamemode=%s%n", â˜ƒ.gamemode));
         â˜ƒx.write(String.format("spawn-monsters=%s%n", â˜ƒ.spawnMonsters));
         â˜ƒx.write(String.format("entity-broadcast-range-percentage=%d%n", â˜ƒ.entityBroadcastRangePercentage));
         â˜ƒx.write(String.format("max-world-size=%d%n", â˜ƒ.maxWorldSize));
         â˜ƒx.write(String.format("spawn-npcs=%s%n", â˜ƒ.spawnNpcs));
         â˜ƒx.write(String.format("view-distance=%d%n", â˜ƒ.viewDistance));
         â˜ƒx.write(String.format("spawn-animals=%s%n", â˜ƒ.spawnAnimals));
         â˜ƒx.write(String.format("generate-structures=%s%n", â˜ƒ.getWorldGenSettings(this.registryHolder).generateFeatures()));
         â˜ƒx.write(String.format("use-native=%s%n", â˜ƒ.useNativeTransport));
         â˜ƒx.write(String.format("rate-limit=%d%n", â˜ƒ.rateLimitPacketsPerSecond));
      } catch (Throwable var7) {
         if (â˜ƒx != null) {
            try {
               â˜ƒx.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }
         }

         throw var7;
      }

      if (â˜ƒx != null) {
         â˜ƒx.close();
      }
   }

   @Override
   public Optional<String> getModdedStatus() {
      String â˜ƒ = this.getServerModName();
      return !"vanilla".equals(â˜ƒ) ? Optional.of("Definitely; Server brand changed to '" + â˜ƒ + "'") : Optional.empty();
   }

   @Override
   public void onServerExit() {
      if (this.textFilterClient != null) {
         this.textFilterClient.close();
      }

      if (this.gui != null) {
         this.gui.close();
      }

      if (this.rconThread != null) {
         this.rconThread.stop();
      }

      if (this.queryThreadGs4 != null) {
         this.queryThreadGs4.stop();
      }
   }

   @Override
   public void tickChildren(BooleanSupplier var1) {
      super.tickChildren(â˜ƒ);
      this.handleConsoleInputs();
   }

   @Override
   public boolean isNetherEnabled() {
      return this.getProperties().allowNether;
   }

   @Override
   public void populateSnooper(Snooper var1) {
      â˜ƒ.setDynamicData("whitelist_enabled", this.getPlayerList().isUsingWhitelist());
      â˜ƒ.setDynamicData("whitelist_count", this.getPlayerList().getWhiteListNames().length);
      super.populateSnooper(â˜ƒ);
   }

   @Override
   public boolean isSnooperEnabled() {
      return this.getProperties().snooperEnabled;
   }

   public void handleConsoleInput(String var1, CommandSourceStack var2) {
      this.consoleInput.add(new ConsoleInput(â˜ƒ, â˜ƒ));
   }

   public void handleConsoleInputs() {
      while(!this.consoleInput.isEmpty()) {
         ConsoleInput â˜ƒ = (ConsoleInput)this.consoleInput.remove(0);
         this.getCommands().performCommand(â˜ƒ.source, â˜ƒ.msg);
      }
   }

   @Override
   public boolean isDedicatedServer() {
      return true;
   }

   @Override
   public int getRateLimitPacketsPerSecond() {
      return this.getProperties().rateLimitPacketsPerSecond;
   }

   @Override
   public boolean isEpollEnabled() {
      return this.getProperties().useNativeTransport;
   }

   public DedicatedPlayerList getPlayerList() {
      return (DedicatedPlayerList)super.getPlayerList();
   }

   @Override
   public boolean isPublished() {
      return true;
   }

   @Override
   public String getServerIp() {
      return this.getLocalIp();
   }

   @Override
   public int getServerPort() {
      return this.getPort();
   }

   @Override
   public String getServerName() {
      return this.getMotd();
   }

   public void showGui() {
      if (this.gui == null) {
         this.gui = MinecraftServerGui.showFrameFor(this);
      }
   }

   @Override
   public boolean hasGui() {
      return this.gui != null;
   }

   @Override
   public boolean isCommandBlockEnabled() {
      return this.getProperties().enableCommandBlock;
   }

   @Override
   public int getSpawnProtectionRadius() {
      return this.getProperties().spawnProtection;
   }

   @Override
   public boolean isUnderSpawnProtection(ServerLevel var1, BlockPos var2, Player var3) {
      if (â˜ƒ.dimension() != Level.OVERWORLD) {
         return false;
      } else if (this.getPlayerList().getOps().isEmpty()) {
         return false;
      } else if (this.getPlayerList().isOp(â˜ƒ.getGameProfile())) {
         return false;
      } else if (this.getSpawnProtectionRadius() <= 0) {
         return false;
      } else {
         BlockPos â˜ƒ = â˜ƒ.getSharedSpawnPos();
         int â˜ƒx = Mth.abs(â˜ƒ.getX() - â˜ƒ.getX());
         int â˜ƒxx = Mth.abs(â˜ƒ.getZ() - â˜ƒ.getZ());
         int â˜ƒxxx = Math.max(â˜ƒx, â˜ƒxx);
         return â˜ƒxxx <= this.getSpawnProtectionRadius();
      }
   }

   @Override
   public boolean repliesToStatus() {
      return this.getProperties().enableStatus;
   }

   @Override
   public int getOperatorUserPermissionLevel() {
      return this.getProperties().opPermissionLevel;
   }

   @Override
   public int getFunctionCompilationLevel() {
      return this.getProperties().functionPermissionLevel;
   }

   @Override
   public void setPlayerIdleTimeout(int var1) {
      super.setPlayerIdleTimeout(â˜ƒ);
      this.settings.update(var2 -> var2.playerIdleTimeout.update(this.registryAccess(), â˜ƒ));
   }

   @Override
   public boolean shouldRconBroadcast() {
      return this.getProperties().broadcastRconToOps;
   }

   @Override
   public boolean shouldInformAdmins() {
      return this.getProperties().broadcastConsoleToOps;
   }

   @Override
   public int getAbsoluteMaxWorldSize() {
      return this.getProperties().maxWorldSize;
   }

   @Override
   public int getCompressionThreshold() {
      return this.getProperties().networkCompressionThreshold;
   }

   protected boolean convertOldUsers() {
      boolean â˜ƒ = false;

      for(int â˜ƒx = 0; !â˜ƒ && â˜ƒx <= 2; ++â˜ƒx) {
         if (â˜ƒx > 0) {
            LOGGER.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
            this.waitForRetry();
         }

         â˜ƒ = OldUsersConverter.convertUserBanlist(this);
      }

      boolean â˜ƒx = false;

      for(int var7 = 0; !â˜ƒx && var7 <= 2; ++var7) {
         if (var7 > 0) {
            LOGGER.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
            this.waitForRetry();
         }

         â˜ƒx = OldUsersConverter.convertIpBanlist(this);
      }

      boolean â˜ƒxx = false;

      for(int var8 = 0; !â˜ƒxx && var8 <= 2; ++var8) {
         if (var8 > 0) {
            LOGGER.warn("Encountered a problem while converting the op list, retrying in a few seconds");
            this.waitForRetry();
         }

         â˜ƒxx = OldUsersConverter.convertOpsList(this);
      }

      boolean â˜ƒxxx = false;

      for(int var9 = 0; !â˜ƒxxx && var9 <= 2; ++var9) {
         if (var9 > 0) {
            LOGGER.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
            this.waitForRetry();
         }

         â˜ƒxxx = OldUsersConverter.convertWhiteList(this);
      }

      boolean â˜ƒxxxx = false;

      for(int var10 = 0; !â˜ƒxxxx && var10 <= 2; ++var10) {
         if (var10 > 0) {
            LOGGER.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
            this.waitForRetry();
         }

         â˜ƒxxxx = OldUsersConverter.convertPlayers(this);
      }

      return â˜ƒ || â˜ƒx || â˜ƒxx || â˜ƒxxx || â˜ƒxxxx;
   }

   private void waitForRetry() {
      try {
         Thread.sleep(5000L);
      } catch (InterruptedException var2) {
      }
   }

   public long getMaxTickLength() {
      return this.getProperties().maxTickTime;
   }

   @Override
   public String getPluginNames() {
      return "";
   }

   @Override
   public String runCommand(String var1) {
      this.rconConsoleSource.prepareForCommand();
      this.executeBlocking(() -> this.getCommands().performCommand(this.rconConsoleSource.createCommandSourceStack(), â˜ƒ));
      return this.rconConsoleSource.getCommandResponse();
   }

   public void storeUsingWhiteList(boolean var1) {
      this.settings.update(var2 -> var2.whiteList.update(this.registryAccess(), â˜ƒ));
   }

   @Override
   public void stopServer() {
      super.stopServer();
      Util.shutdownExecutors();
   }

   @Override
   public boolean isSingleplayerOwner(GameProfile var1) {
      return false;
   }

   @Override
   public int getScaledTrackingDistance(int var1) {
      return this.getProperties().entityBroadcastRangePercentage * â˜ƒ / 100;
   }

   @Override
   public String getLevelIdName() {
      return this.storageSource.getLevelId();
   }

   @Override
   public boolean forceSynchronousWrites() {
      return this.settings.getProperties().syncChunkWrites;
   }

   @Override
   public TextFilter createTextFilterForPlayer(ServerPlayer var1) {
      return this.textFilterClient != null ? this.textFilterClient.createContext(â˜ƒ.getGameProfile()) : TextFilter.DUMMY;
   }

   @Override
   public boolean isResourcePackRequired() {
      return this.settings.getProperties().requireResourcePack;
   }

   @Nullable
   @Override
   public GameType getForcedGameType() {
      return this.settings.getProperties().forceGameMode ? this.worldData.getGameType() : null;
   }

   @Nullable
   private static Component parseResourcePackPrompt(DedicatedServerSettings var0) {
      String â˜ƒ = â˜ƒ.getProperties().resourcePackPrompt;
      if (!Strings.isNullOrEmpty(â˜ƒ)) {
         try {
            return Component.Serializer.fromJson(â˜ƒ);
         } catch (Exception var3) {
            LOGGER.warn("Failed to parse resource pack prompt '{}'", â˜ƒ, var3);
         }
      }

      return null;
   }

   @Nullable
   @Override
   public Component getResourcePackPrompt() {
      return this.resourcePackPrompt;
   }
}
