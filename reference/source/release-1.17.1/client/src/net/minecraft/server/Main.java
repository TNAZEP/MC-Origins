package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.CrashReport;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.level.progress.LoggerChunkProgressListener;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.worldupdate.WorldUpgrader;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
   private static final Logger LOGGER = LogManager.getLogger();

   @DontObfuscate
   public static void main(String[] var0) {
      SharedConstants.tryDetectVersion();
      OptionParser â˜ƒ = new OptionParser();
      OptionSpec<Void> â˜ƒx = â˜ƒ.accepts("nogui");
      OptionSpec<Void> â˜ƒxx = â˜ƒ.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
      OptionSpec<Void> â˜ƒxxx = â˜ƒ.accepts("demo");
      OptionSpec<Void> â˜ƒxxxx = â˜ƒ.accepts("bonusChest");
      OptionSpec<Void> â˜ƒxxxxx = â˜ƒ.accepts("forceUpgrade");
      OptionSpec<Void> â˜ƒxxxxxx = â˜ƒ.accepts("eraseCache");
      OptionSpec<Void> â˜ƒxxxxxxx = â˜ƒ.accepts("safeMode", "Loads level with vanilla datapack only");
      OptionSpec<Void> â˜ƒxxxxxxxx = â˜ƒ.accepts("help").forHelp();
      OptionSpec<String> â˜ƒxxxxxxxxx = â˜ƒ.accepts("singleplayer").withRequiredArg();
      OptionSpec<String> â˜ƒxxxxxxxxxx = â˜ƒ.accepts("universe").withRequiredArg().defaultsTo(".");
      OptionSpec<String> â˜ƒxxxxxxxxxxx = â˜ƒ.accepts("world").withRequiredArg();
      OptionSpec<Integer> â˜ƒxxxxxxxxxxxx = â˜ƒ.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(-1);
      OptionSpec<String> â˜ƒxxxxxxxxxxxxx = â˜ƒ.accepts("serverId").withRequiredArg();
      OptionSpec<String> â˜ƒxxxxxxxxxxxxxx = â˜ƒ.nonOptions();

      try {
         OptionSet â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.parse(â˜ƒ);
         if (â˜ƒxxxxxxxxxxxxxxx.has(â˜ƒxxxxxxxx)) {
            â˜ƒ.printHelpOn(System.err);
            return;
         }

         CrashReport.preload();
         Bootstrap.bootStrap();
         Bootstrap.validate();
         Util.startTimerHackThread();
         RegistryAccess.RegistryHolder â˜ƒxxxxxxxxxxxxxxx = RegistryAccess.builtin();
         Path â˜ƒxxxxxxxxxxxxxxxx = Paths.get("server.properties");
         DedicatedServerSettings â˜ƒxxxxxxxxxxxxxxxxx = new DedicatedServerSettings(â˜ƒxxxxxxxxxxxxxxxx);
         â˜ƒxxxxxxxxxxxxxxxxx.forceSave();
         Path â˜ƒxxxxxxxxxxxxxxxxxx = Paths.get("eula.txt");
         Eula â˜ƒxxxxxxxxxxxxxxxxxxx = new Eula(â˜ƒxxxxxxxxxxxxxxxxxx);
         if (â˜ƒxxxxxxxxxxxxxxx.has(â˜ƒxx)) {
            LOGGER.info("Initialized '{}' and '{}'", â˜ƒxxxxxxxxxxxxxxxx.toAbsolutePath(), â˜ƒxxxxxxxxxxxxxxxxxx.toAbsolutePath());
            return;
         }

         if (!â˜ƒxxxxxxxxxxxxxxxxxxx.hasAgreedToEULA()) {
            LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            return;
         }

         File â˜ƒxxxxxxxxxxxxxxx = new File(â˜ƒxxxxxxxxxxxxxxx.valueOf(â˜ƒxxxxxxxxxx));
         YggdrasilAuthenticationService â˜ƒxxxxxxxxxxxxxxxx = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
         MinecraftSessionService â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx.createMinecraftSessionService();
         GameProfileRepository â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx.createProfileRepository();
         GameProfileCache â˜ƒxxxxxxxxxxxxxxxxxxx = new GameProfileCache(
            â˜ƒxxxxxxxxxxxxxxxxxx, new File(â˜ƒxxxxxxxxxxxxxxx, MinecraftServer.USERID_CACHE_FILE.getName())
         );
         String â˜ƒxxxxxxxxxxxxxxxxxxxx = (String)Optional.ofNullable((String)â˜ƒxxxxxxxxxxxxxxx.valueOf(â˜ƒxxxxxxxxxxx))
            .orElse(â˜ƒxxxxxxxxxxxxxxxxx.getProperties().levelName);
         LevelStorageSource â˜ƒxxxxxxxxxxxxxxxxxxxxx = LevelStorageSource.createDefault(â˜ƒxxxxxxxxxxxxxxx.toPath());
         LevelStorageSource.LevelStorageAccess â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx.createAccess(â˜ƒxxxxxxxxxxxxxxxxxxxx);
         MinecraftServer.convertFromRegionFormatIfNeeded(â˜ƒxxxxxxxxxxxxxxxxxxxxxx);
         LevelSummary â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getSummary();
         if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx != null && â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.isIncompatibleWorldHeight()) {
            LOGGER.info("Loading of worlds with extended height is disabled.");
            return;
         }

         DataPackConfig â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getDataPacks();
         boolean â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx.has(â˜ƒxxxxxxx);
         if (â˜ƒxxxxxxxxxxxxxxxx) {
            LOGGER.warn("Safe mode active, only vanilla datapack will be loaded");
         }

         PackRepository â˜ƒxxxxxxxxxxxxxxx = new PackRepository(
            PackType.SERVER_DATA,
            new ServerPacksSource(),
            new FolderRepositorySource(â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getLevelPath(LevelResource.DATAPACK_DIR).toFile(), PackSource.WORLD)
         );
         DataPackConfig â˜ƒxxxxxxxxxxxxxxxx = MinecraftServer.configurePackRepository(
            â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx == null ? DataPackConfig.DEFAULT : â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx
         );
         CompletableFuture<ServerResources> â˜ƒxxxxxxxxxxxxxxxxx = ServerResources.loadResources(
            â˜ƒxxxxxxxxxxxxxxx.openAllSelected(),
            â˜ƒxxxxxxxxxxxxxxx,
            Commands.CommandSelection.DEDICATED,
            â˜ƒxxxxxxxxxxxxxxxxx.getProperties().functionPermissionLevel,
            Util.backgroundExecutor(),
            Runnable::run
         );

         ServerResources â˜ƒ;
         try {
            â˜ƒ = (ServerResources)â˜ƒxxxxxxxxxxxxxxxxx.get();
         } catch (Exception var42) {
            LOGGER.warn(
               "Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", var42
            );
            â˜ƒxxxxxxxxxxxxxxx.close();
            return;
         }

         â˜ƒ.updateGlobals();
         RegistryReadOps<Tag> â˜ƒxxxxxxxxxxxxxxxxxx = RegistryReadOps.createAndLoad(NbtOps.INSTANCE, â˜ƒ.getResourceManager(), â˜ƒxxxxxxxxxxxxxxx);
         â˜ƒxxxxxxxxxxxxxxxxx.getProperties().getWorldGenSettings(â˜ƒxxxxxxxxxxxxxxx);
         WorldData â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getDataTag(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
         if (â˜ƒxxxxxxxxxxxxxxxxxxx == null) {
            LevelSettings â˜ƒxxxxxxxxxxxxxxxxxxxx;
            WorldGenSettings â˜ƒxxxxxxxxxxxxxxxxxxxxx;
            if (â˜ƒxxxxxxxxxxxxxxx.has(â˜ƒxxx)) {
               â˜ƒxxxxxxxxxxxxxxxxxxxx = MinecraftServer.DEMO_SETTINGS;
               â˜ƒxxxxxxxxxxxxxxxxxxxxx = WorldGenSettings.demoSettings(â˜ƒxxxxxxxxxxxxxxx);
            } else {
               DedicatedServerProperties â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx.getProperties();
               â˜ƒxxxxxxxxxxxxxxxxxxxx = new LevelSettings(
                  â˜ƒxxxxxxxxxxxxxxxxxxxx.levelName,
                  â˜ƒxxxxxxxxxxxxxxxxxxxx.gamemode,
                  â˜ƒxxxxxxxxxxxxxxxxxxxx.hardcore,
                  â˜ƒxxxxxxxxxxxxxxxxxxxx.difficulty,
                  false,
                  new GameRules(),
                  â˜ƒxxxxxxxxxxxxxxxx
               );
               â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx.has(â˜ƒxxxx)
                  ? â˜ƒxxxxxxxxxxxxxxxxxxxx.getWorldGenSettings(â˜ƒxxxxxxxxxxxxxxx).withBonusChest()
                  : â˜ƒxxxxxxxxxxxxxxxxxxxx.getWorldGenSettings(â˜ƒxxxxxxxxxxxxxxx);
            }

            â˜ƒxxxxxxxxxxxxxxxxxxx = new PrimaryLevelData(â˜ƒxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx, Lifecycle.stable());
         }

         if (â˜ƒxxxxxxxxxxxxxxx.has(â˜ƒxxxxx)) {
            forceUpgrade(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               DataFixers.getDataFixer(),
               â˜ƒxxxxxxxxxxxxxxx.has(â˜ƒxxxxxx),
               () -> true,
               â˜ƒxxxxxxxxxxxxxxxxxxx.worldGenSettings().levels()
            );
         }

         â˜ƒxxxxxxxxxxxxxxxxxxxxxx.saveDataTag(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx);
         WorldData â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx;
         final DedicatedServer â˜ƒxxxxxxxxxxxxxxxxxxx = MinecraftServer.spin(
            var16x -> {
               DedicatedServer â˜ƒ = new DedicatedServer(
                  var16x, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, DataFixers.getDataFixer(), â˜ƒ, â˜ƒ, â˜ƒ, LoggerChunkProgressListener::new
               );
               â˜ƒ.setSingleplayerName(â˜ƒ.valueOf(â˜ƒ));
               â˜ƒ.setPort(â˜ƒ.valueOf(â˜ƒ));
               â˜ƒ.setDemo(â˜ƒ.has(â˜ƒ));
               â˜ƒ.setId(â˜ƒ.valueOf(â˜ƒ));
               boolean â˜ƒx = !â˜ƒ.has(â˜ƒ) && !â˜ƒ.valuesOf(â˜ƒ).contains("nogui");
               if (â˜ƒx && !GraphicsEnvironment.isHeadless()) {
                  â˜ƒ.showGui();
               }
   
               return â˜ƒ;
            }
         );
         Thread â˜ƒxxxxxxxxxxxxxxxxxxxx = new Thread("Server Shutdown Thread") {
            public void run() {
               â˜ƒ.halt(true);
            }
         };
         â˜ƒxxxxxxxxxxxxxxxxxxxx.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
         Runtime.getRuntime().addShutdownHook(â˜ƒxxxxxxxxxxxxxxxxxxxx);
      } catch (Exception var43) {
         LOGGER.fatal("Failed to start the minecraft server", var43);
      }
   }

   private static void forceUpgrade(
      LevelStorageSource.LevelStorageAccess var0, DataFixer var1, boolean var2, BooleanSupplier var3, ImmutableSet<ResourceKey<Level>> var4
   ) {
      LOGGER.info("Forcing world upgrade!");
      WorldUpgrader â˜ƒ = new WorldUpgrader(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      Component â˜ƒx = null;

      while(!â˜ƒ.isFinished()) {
         Component â˜ƒxx = â˜ƒ.getStatus();
         if (â˜ƒx != â˜ƒxx) {
            â˜ƒx = â˜ƒxx;
            LOGGER.info(â˜ƒ.getStatus().getString());
         }

         int â˜ƒxx = â˜ƒ.getTotalChunks();
         if (â˜ƒxx > 0) {
            int â˜ƒxxx = â˜ƒ.getConverted() + â˜ƒ.getSkipped();
            LOGGER.info("{}% completed ({} / {} chunks)...", Mth.floor((float)â˜ƒxxx / (float)â˜ƒxx * 100.0F), â˜ƒxxx, â˜ƒxx);
         }

         if (!â˜ƒ.getAsBoolean()) {
            â˜ƒ.cancel();
         } else {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var10) {
            }
         }
      }
   }
}
