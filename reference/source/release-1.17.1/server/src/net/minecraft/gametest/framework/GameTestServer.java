package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Lifecycle;
import java.net.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.LoggerChunkProgressListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameTestServer extends MinecraftServer {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int PROGRESS_REPORT_INTERVAL = 20;
   private final List<GameTestBatch> testBatches;
   private final BlockPos spawnPos;
   private static final GameRules TEST_GAME_RULES = Util.make(new GameRules(), var0 -> {
      var0.getRule(GameRules.RULE_DOMOBSPAWNING).set(false, null);
      var0.getRule(GameRules.RULE_WEATHER_CYCLE).set(false, null);
   });
   private static final LevelSettings TEST_SETTINGS = new LevelSettings(
      "Test Level", GameType.CREATIVE, false, Difficulty.NORMAL, true, TEST_GAME_RULES, DataPackConfig.DEFAULT
   );
   @Nullable
   private MultipleTestTracker testTracker;

   public GameTestServer(
      Thread var1,
      LevelStorageSource.LevelStorageAccess var2,
      PackRepository var3,
      ServerResources var4,
      Collection<GameTestBatch> var5,
      BlockPos var6,
      RegistryAccess.RegistryHolder var7
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.registryOrThrow(Registry.BIOME_REGISTRY), â˜ƒ.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY));
   }

   private GameTestServer(
      Thread var1,
      LevelStorageSource.LevelStorageAccess var2,
      PackRepository var3,
      ServerResources var4,
      Collection<GameTestBatch> var5,
      BlockPos var6,
      RegistryAccess.RegistryHolder var7,
      Registry<Biome> var8,
      Registry<DimensionType> var9
   ) {
      super(
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         new PrimaryLevelData(
            TEST_SETTINGS,
            new WorldGenSettings(
               0L,
               false,
               false,
               WorldGenSettings.withOverworld(
                  â˜ƒ,
                  DimensionType.defaultDimensions(â˜ƒ, â˜ƒ, â˜ƒ.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY), 0L),
                  new FlatLevelSource(FlatLevelGeneratorSettings.getDefault(â˜ƒ))
               )
            ),
            Lifecycle.stable()
         ),
         â˜ƒ,
         Proxy.NO_PROXY,
         DataFixers.getDataFixer(),
         â˜ƒ,
         null,
         null,
         null,
         LoggerChunkProgressListener::new
      );
      this.testBatches = Lists.<GameTestBatch>newArrayList(â˜ƒ);
      this.spawnPos = â˜ƒ;
      if (â˜ƒ.isEmpty()) {
         throw new IllegalArgumentException("No test batches were given!");
      }
   }

   @Override
   public boolean initServer() {
      this.setPlayerList(new PlayerList(this, this.registryHolder, this.playerDataStorage, 1) {
      });
      this.loadLevel();
      ServerLevel â˜ƒ = this.overworld();
      â˜ƒ.setDefaultSpawnPos(this.spawnPos, 0.0F);
      â˜ƒ.getLevelData().setRaining(false);
      â˜ƒ.getLevelData().setRaining(false);
      return true;
   }

   @Override
   public void tickServer(BooleanSupplier var1) {
      super.tickServer(â˜ƒ);
      ServerLevel â˜ƒ = this.overworld();
      if (!this.haveTestsStarted()) {
         this.startTests(â˜ƒ);
      }

      if (â˜ƒ.getGameTime() % 20L == 0L) {
         LOGGER.info(this.testTracker.getProgressBar());
      }

      if (this.testTracker.isDone()) {
         this.halt(false);
         LOGGER.info(this.testTracker.getProgressBar());
         GlobalTestReporter.finish();
         LOGGER.info("========= {} GAME TESTS COMPLETE ======================", this.testTracker.getTotalCount());
         if (this.testTracker.hasFailedRequired()) {
            LOGGER.info("{} required tests failed :(", this.testTracker.getFailedRequiredCount());
            this.testTracker.getFailedRequired().forEach(var0 -> LOGGER.info("   - {}", var0.getTestName()));
         } else {
            LOGGER.info("All {} required tests passed :)", this.testTracker.getTotalCount());
         }

         if (this.testTracker.hasFailedOptional()) {
            LOGGER.info("{} optional tests failed", this.testTracker.getFailedOptionalCount());
            this.testTracker.getFailedOptional().forEach(var0 -> LOGGER.info("   - {}", var0.getTestName()));
         }

         LOGGER.info("====================================================");
      }
   }

   @Override
   public SystemReport fillServerSystemReport(SystemReport var1) {
      â˜ƒ.setDetail("Type", "Game test server");
      return â˜ƒ;
   }

   @Override
   public void onServerExit() {
      super.onServerExit();
      System.exit(this.testTracker.getFailedRequiredCount());
   }

   @Override
   public void onServerCrash(CrashReport var1) {
      System.exit(1);
   }

   private void startTests(ServerLevel var1) {
      Collection<GameTestInfo> â˜ƒ = GameTestRunner.runTestBatches(this.testBatches, new BlockPos(0, 4, 0), Rotation.NONE, â˜ƒ, GameTestTicker.SINGLETON, 8);
      this.testTracker = new MultipleTestTracker(â˜ƒ);
      LOGGER.info("{} tests are now running!", this.testTracker.getTotalCount());
   }

   private boolean haveTestsStarted() {
      return this.testTracker != null;
   }

   @Override
   public boolean isHardcore() {
      return false;
   }

   @Override
   public int getOperatorUserPermissionLevel() {
      return 0;
   }

   @Override
   public int getFunctionCompilationLevel() {
      return 4;
   }

   @Override
   public boolean shouldRconBroadcast() {
      return false;
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
   public boolean isCommandBlockEnabled() {
      return true;
   }

   @Override
   public boolean isPublished() {
      return false;
   }

   @Override
   public boolean shouldInformAdmins() {
      return false;
   }

   @Override
   public boolean isSingleplayerOwner(GameProfile var1) {
      return false;
   }

   @Override
   public Optional<String> getModdedStatus() {
      return Optional.empty();
   }
}
