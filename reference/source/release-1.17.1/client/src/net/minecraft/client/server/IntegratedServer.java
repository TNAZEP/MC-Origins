package net.minecraft.client.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.stats.Stats;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Snooper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer {
   public static final int CLIENT_VIEW_DISTANCE_OFFSET = -1;
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft minecraft;
   private boolean paused;
   private int publishedPort = -1;
   @Nullable
   private GameType publishedGameType;
   private LanServerPinger lanPinger;
   private UUID uuid;

   public IntegratedServer(
      Thread var1,
      Minecraft var2,
      RegistryAccess.RegistryHolder var3,
      LevelStorageSource.LevelStorageAccess var4,
      PackRepository var5,
      ServerResources var6,
      WorldData var7,
      MinecraftSessionService var8,
      GameProfileRepository var9,
      GameProfileCache var10,
      ChunkProgressListenerFactory var11
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getProxy(), â˜ƒ.getFixerUpper(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setSingleplayerName(â˜ƒ.getUser().getName());
      this.setDemo(â˜ƒ.isDemo());
      this.setPlayerList(new IntegratedPlayerList(this, this.registryHolder, this.playerDataStorage));
      this.minecraft = â˜ƒ;
   }

   @Override
   public boolean initServer() {
      LOGGER.info("Starting integrated minecraft server version {}", SharedConstants.getCurrentVersion().getName());
      this.setUsesAuthentication(true);
      this.setPvpAllowed(true);
      this.setFlightAllowed(true);
      this.initializeKeyPair();
      this.loadLevel();
      this.setMotd(this.getSingleplayerName() + " - " + this.getWorldData().getLevelName());
      return true;
   }

   @Override
   public void tickServer(BooleanSupplier var1) {
      boolean â˜ƒ = this.paused;
      this.paused = Minecraft.getInstance().getConnection() != null && Minecraft.getInstance().isPaused();
      ProfilerFiller â˜ƒx = this.getProfiler();
      if (!â˜ƒ && this.paused) {
         â˜ƒx.push("autoSave");
         LOGGER.info("Saving and pausing game...");
         this.getPlayerList().saveAll();
         this.saveAllChunks(false, false, false);
         â˜ƒx.pop();
      }

      if (this.paused) {
         this.tickPaused();
      } else {
         super.tickServer(â˜ƒ);
         int â˜ƒ = Math.max(2, this.minecraft.options.renderDistance + -1);
         if (â˜ƒ != this.getPlayerList().getViewDistance()) {
            LOGGER.info("Changing view distance to {}, from {}", â˜ƒ, this.getPlayerList().getViewDistance());
            this.getPlayerList().setViewDistance(â˜ƒ);
         }
      }
   }

   private void tickPaused() {
      for(ServerPlayer â˜ƒ : this.getPlayerList().getPlayers()) {
         â˜ƒ.awardStat(Stats.TOTAL_WORLD_TIME);
      }
   }

   @Override
   public boolean shouldRconBroadcast() {
      return true;
   }

   @Override
   public boolean shouldInformAdmins() {
      return true;
   }

   @Override
   public File getServerDirectory() {
      return this.minecraft.gameDirectory;
   }

   @Override
   public boolean isDedicatedServer() {
      return false;
   }

   @Override
   public int getRateLimitPacketsPerSecond() {
      return 0;
   }

   @Override
   public boolean isEpollEnabled() {
      return false;
   }

   @Override
   public void onServerCrash(CrashReport var1) {
      this.minecraft.delayCrash(â˜ƒ);
   }

   @Override
   public SystemReport fillServerSystemReport(SystemReport var1) {
      â˜ƒ.setDetail("Type", "Integrated Server (map_client.txt)");
      â˜ƒ.setDetail(
         "Is Modded",
         (Supplier<String>)(() -> (String)this.getModdedStatus().orElse("Probably not. Jar signature remains and both client + server brands are untouched."))
      );
      return â˜ƒ;
   }

   @Override
   public Optional<String> getModdedStatus() {
      String â˜ƒ = ClientBrandRetriever.getClientModName();
      if (!â˜ƒ.equals("vanilla")) {
         return Optional.of("Definitely; Client brand changed to '" + â˜ƒ + "'");
      } else {
         â˜ƒ = this.getServerModName();
         if (!"vanilla".equals(â˜ƒ)) {
            return Optional.of("Definitely; Server brand changed to '" + â˜ƒ + "'");
         } else {
            return Minecraft.class.getSigners() == null ? Optional.of("Very likely; Jar signature invalidated") : Optional.empty();
         }
      }
   }

   @Override
   public void populateSnooper(Snooper var1) {
      super.populateSnooper(â˜ƒ);
      â˜ƒ.setDynamicData("snooper_partner", this.minecraft.getSnooper().getToken());
   }

   @Override
   public boolean isSnooperEnabled() {
      return Minecraft.getInstance().isSnooperEnabled();
   }

   @Override
   public boolean publishServer(@Nullable GameType var1, boolean var2, int var3) {
      try {
         this.getConnection().startTcpServerListener(null, â˜ƒ);
         LOGGER.info("Started serving on {}", â˜ƒ);
         this.publishedPort = â˜ƒ;
         this.lanPinger = new LanServerPinger(this.getMotd(), â˜ƒ + "");
         this.lanPinger.start();
         this.publishedGameType = â˜ƒ;
         this.getPlayerList().setAllowCheatsForAllPlayers(â˜ƒ);
         int â˜ƒ = this.getProfilePermissions(this.minecraft.player.getGameProfile());
         this.minecraft.player.setPermissionLevel(â˜ƒ);

         for(ServerPlayer â˜ƒx : this.getPlayerList().getPlayers()) {
            this.getCommands().sendCommands(â˜ƒx);
         }

         return true;
      } catch (IOException var7) {
         return false;
      }
   }

   @Override
   public void stopServer() {
      super.stopServer();
      if (this.lanPinger != null) {
         this.lanPinger.interrupt();
         this.lanPinger = null;
      }
   }

   @Override
   public void halt(boolean var1) {
      this.executeBlocking(() -> {
         for(ServerPlayer â˜ƒ : Lists.newArrayList(this.getPlayerList().getPlayers())) {
            if (!â˜ƒ.getUUID().equals(this.uuid)) {
               this.getPlayerList().remove(â˜ƒ);
            }
         }
      });
      super.halt(â˜ƒ);
      if (this.lanPinger != null) {
         this.lanPinger.interrupt();
         this.lanPinger = null;
      }
   }

   @Override
   public boolean isPublished() {
      return this.publishedPort > -1;
   }

   @Override
   public int getPort() {
      return this.publishedPort;
   }

   @Override
   public void setDefaultGameType(GameType var1) {
      super.setDefaultGameType(â˜ƒ);
      this.publishedGameType = null;
   }

   @Override
   public boolean isCommandBlockEnabled() {
      return true;
   }

   @Override
   public int getOperatorUserPermissionLevel() {
      return 2;
   }

   @Override
   public int getFunctionCompilationLevel() {
      return 2;
   }

   public void setUUID(UUID var1) {
      this.uuid = â˜ƒ;
   }

   @Override
   public boolean isSingleplayerOwner(GameProfile var1) {
      return â˜ƒ.getName().equalsIgnoreCase(this.getSingleplayerName());
   }

   @Override
   public int getScaledTrackingDistance(int var1) {
      return (int)(this.minecraft.options.entityDistanceScaling * (float)â˜ƒ);
   }

   @Override
   public boolean forceSynchronousWrites() {
      return this.minecraft.options.syncWrites;
   }

   @Nullable
   @Override
   public GameType getForcedGameType() {
      return this.isPublished() ? MoreObjects.firstNonNull(this.publishedGameType, this.worldData.getGameType()) : null;
   }
}
