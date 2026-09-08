package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SerializableUUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.timers.TimerCallbacks;
import net.minecraft.world.level.timers.TimerQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrimaryLevelData implements ServerLevelData, WorldData {
   private static final Logger LOGGER = LogManager.getLogger();
   protected static final String WORLD_GEN_SETTINGS = "WorldGenSettings";
   private LevelSettings settings;
   private final WorldGenSettings worldGenSettings;
   private final Lifecycle worldGenSettingsLifecycle;
   private int xSpawn;
   private int ySpawn;
   private int zSpawn;
   private float spawnAngle;
   private long gameTime;
   private long dayTime;
   @Nullable
   private final DataFixer fixerUpper;
   private final int playerDataVersion;
   private boolean upgradedPlayerTag;
   @Nullable
   private CompoundTag loadedPlayerTag;
   private final int version;
   private int clearWeatherTime;
   private boolean raining;
   private int rainTime;
   private boolean thundering;
   private int thunderTime;
   private boolean initialized;
   private boolean difficultyLocked;
   private WorldBorder.Settings worldBorder;
   private CompoundTag endDragonFightData;
   @Nullable
   private CompoundTag customBossEvents;
   private int wanderingTraderSpawnDelay;
   private int wanderingTraderSpawnChance;
   @Nullable
   private UUID wanderingTraderId;
   private final Set<String> knownServerBrands;
   private boolean wasModded;
   private final TimerQueue<MinecraftServer> scheduledEvents;

   private PrimaryLevelData(
      @Nullable DataFixer var1,
      int var2,
      @Nullable CompoundTag var3,
      boolean var4,
      int var5,
      int var6,
      int var7,
      float var8,
      long var9,
      long var11,
      int var13,
      int var14,
      int var15,
      boolean var16,
      int var17,
      boolean var18,
      boolean var19,
      boolean var20,
      WorldBorder.Settings var21,
      int var22,
      int var23,
      @Nullable UUID var24,
      Set<String> var25,
      TimerQueue<MinecraftServer> var26,
      @Nullable CompoundTag var27,
      CompoundTag var28,
      LevelSettings var29,
      WorldGenSettings var30,
      Lifecycle var31
   ) {
      this.fixerUpper = â˜ƒ;
      this.wasModded = â˜ƒ;
      this.xSpawn = â˜ƒ;
      this.ySpawn = â˜ƒ;
      this.zSpawn = â˜ƒ;
      this.spawnAngle = â˜ƒ;
      this.gameTime = â˜ƒ;
      this.dayTime = â˜ƒ;
      this.version = â˜ƒ;
      this.clearWeatherTime = â˜ƒ;
      this.rainTime = â˜ƒ;
      this.raining = â˜ƒ;
      this.thunderTime = â˜ƒ;
      this.thundering = â˜ƒ;
      this.initialized = â˜ƒ;
      this.difficultyLocked = â˜ƒ;
      this.worldBorder = â˜ƒ;
      this.wanderingTraderSpawnDelay = â˜ƒ;
      this.wanderingTraderSpawnChance = â˜ƒ;
      this.wanderingTraderId = â˜ƒ;
      this.knownServerBrands = â˜ƒ;
      this.loadedPlayerTag = â˜ƒ;
      this.playerDataVersion = â˜ƒ;
      this.scheduledEvents = â˜ƒ;
      this.customBossEvents = â˜ƒ;
      this.endDragonFightData = â˜ƒ;
      this.settings = â˜ƒ;
      this.worldGenSettings = â˜ƒ;
      this.worldGenSettingsLifecycle = â˜ƒ;
   }

   public PrimaryLevelData(LevelSettings var1, WorldGenSettings var2, Lifecycle var3) {
      this(
         null,
         SharedConstants.getCurrentVersion().getWorldVersion(),
         null,
         false,
         0,
         0,
         0,
         0.0F,
         0L,
         0L,
         19133,
         0,
         0,
         false,
         0,
         false,
         false,
         false,
         WorldBorder.DEFAULT_SETTINGS,
         0,
         0,
         null,
         Sets.newLinkedHashSet(),
         new TimerQueue<>(TimerCallbacks.SERVER_CALLBACKS),
         null,
         new CompoundTag(),
         â˜ƒ.copy(),
         â˜ƒ,
         â˜ƒ
      );
   }

   public static PrimaryLevelData parse(
      Dynamic<Tag> var0, DataFixer var1, int var2, @Nullable CompoundTag var3, LevelSettings var4, LevelVersion var5, WorldGenSettings var6, Lifecycle var7
   ) {
      long â˜ƒ = â˜ƒ.get("Time").asLong(0L);
      CompoundTag â˜ƒx = (CompoundTag)â˜ƒ.get("DragonFight")
         .result()
         .map(Dynamic::getValue)
         .orElseGet(() -> â˜ƒ.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap().getValue());
      return new PrimaryLevelData(
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ.get("WasModded").asBoolean(false),
         â˜ƒ.get("SpawnX").asInt(0),
         â˜ƒ.get("SpawnY").asInt(0),
         â˜ƒ.get("SpawnZ").asInt(0),
         â˜ƒ.get("SpawnAngle").asFloat(0.0F),
         â˜ƒ,
         â˜ƒ.get("DayTime").asLong(â˜ƒ),
         â˜ƒ.levelDataVersion(),
         â˜ƒ.get("clearWeatherTime").asInt(0),
         â˜ƒ.get("rainTime").asInt(0),
         â˜ƒ.get("raining").asBoolean(false),
         â˜ƒ.get("thunderTime").asInt(0),
         â˜ƒ.get("thundering").asBoolean(false),
         â˜ƒ.get("initialized").asBoolean(true),
         â˜ƒ.get("DifficultyLocked").asBoolean(false),
         WorldBorder.Settings.read(â˜ƒ, WorldBorder.DEFAULT_SETTINGS),
         â˜ƒ.get("WanderingTraderSpawnDelay").asInt(0),
         â˜ƒ.get("WanderingTraderSpawnChance").asInt(0),
         (UUID)â˜ƒ.get("WanderingTraderId").read(SerializableUUID.CODEC).result().orElse(null),
         (Set<String>)â˜ƒ.get("ServerBrands")
            .asStream()
            .flatMap(var0x -> Util.toStream(var0x.asString().result()))
            .collect(Collectors.toCollection(Sets::newLinkedHashSet)),
         new TimerQueue<>(TimerCallbacks.SERVER_CALLBACKS, â˜ƒ.get("ScheduledEvents").asStream()),
         (CompoundTag)â˜ƒ.get("CustomBossEvents").orElseEmptyMap().getValue(),
         â˜ƒx,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ
      );
   }

   @Override
   public CompoundTag createTag(RegistryAccess var1, @Nullable CompoundTag var2) {
      this.updatePlayerTag();
      if (â˜ƒ == null) {
         â˜ƒ = this.loadedPlayerTag;
      }

      CompoundTag â˜ƒ = new CompoundTag();
      this.setTagData(â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   private void setTagData(RegistryAccess var1, CompoundTag var2, @Nullable CompoundTag var3) {
      ListTag â˜ƒ = new ListTag();
      this.knownServerBrands.stream().map(StringTag::valueOf).forEach(â˜ƒ::add);
      â˜ƒ.put("ServerBrands", â˜ƒ);
      â˜ƒ.putBoolean("WasModded", this.wasModded);
      CompoundTag â˜ƒx = new CompoundTag();
      â˜ƒx.putString("Name", SharedConstants.getCurrentVersion().getName());
      â˜ƒx.putInt("Id", SharedConstants.getCurrentVersion().getWorldVersion());
      â˜ƒx.putBoolean("Snapshot", !SharedConstants.getCurrentVersion().isStable());
      â˜ƒ.put("Version", â˜ƒx);
      â˜ƒ.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());
      RegistryWriteOps<Tag> â˜ƒxx = RegistryWriteOps.create(NbtOps.INSTANCE, â˜ƒ);
      WorldGenSettings.CODEC
         .encodeStart(â˜ƒxx, this.worldGenSettings)
         .resultOrPartial(Util.prefix("WorldGenSettings: ", LOGGER::error))
         .ifPresent(var1x -> â˜ƒ.put("WorldGenSettings", var1x));
      â˜ƒ.putInt("GameType", this.settings.gameType().getId());
      â˜ƒ.putInt("SpawnX", this.xSpawn);
      â˜ƒ.putInt("SpawnY", this.ySpawn);
      â˜ƒ.putInt("SpawnZ", this.zSpawn);
      â˜ƒ.putFloat("SpawnAngle", this.spawnAngle);
      â˜ƒ.putLong("Time", this.gameTime);
      â˜ƒ.putLong("DayTime", this.dayTime);
      â˜ƒ.putLong("LastPlayed", Util.getEpochMillis());
      â˜ƒ.putString("LevelName", this.settings.levelName());
      â˜ƒ.putInt("version", 19133);
      â˜ƒ.putInt("clearWeatherTime", this.clearWeatherTime);
      â˜ƒ.putInt("rainTime", this.rainTime);
      â˜ƒ.putBoolean("raining", this.raining);
      â˜ƒ.putInt("thunderTime", this.thunderTime);
      â˜ƒ.putBoolean("thundering", this.thundering);
      â˜ƒ.putBoolean("hardcore", this.settings.hardcore());
      â˜ƒ.putBoolean("allowCommands", this.settings.allowCommands());
      â˜ƒ.putBoolean("initialized", this.initialized);
      this.worldBorder.write(â˜ƒ);
      â˜ƒ.putByte("Difficulty", (byte)this.settings.difficulty().getId());
      â˜ƒ.putBoolean("DifficultyLocked", this.difficultyLocked);
      â˜ƒ.put("GameRules", this.settings.gameRules().createTag());
      â˜ƒ.put("DragonFight", this.endDragonFightData);
      if (â˜ƒ != null) {
         â˜ƒ.put("Player", â˜ƒ);
      }

      DataPackConfig.CODEC.encodeStart(NbtOps.INSTANCE, this.settings.getDataPackConfig()).result().ifPresent(var1x -> â˜ƒ.put("DataPacks", var1x));
      if (this.customBossEvents != null) {
         â˜ƒ.put("CustomBossEvents", this.customBossEvents);
      }

      â˜ƒ.put("ScheduledEvents", this.scheduledEvents.store());
      â˜ƒ.putInt("WanderingTraderSpawnDelay", this.wanderingTraderSpawnDelay);
      â˜ƒ.putInt("WanderingTraderSpawnChance", this.wanderingTraderSpawnChance);
      if (this.wanderingTraderId != null) {
         â˜ƒ.putUUID("WanderingTraderId", this.wanderingTraderId);
      }
   }

   @Override
   public int getXSpawn() {
      return this.xSpawn;
   }

   @Override
   public int getYSpawn() {
      return this.ySpawn;
   }

   @Override
   public int getZSpawn() {
      return this.zSpawn;
   }

   @Override
   public float getSpawnAngle() {
      return this.spawnAngle;
   }

   @Override
   public long getGameTime() {
      return this.gameTime;
   }

   @Override
   public long getDayTime() {
      return this.dayTime;
   }

   private void updatePlayerTag() {
      if (!this.upgradedPlayerTag && this.loadedPlayerTag != null) {
         if (this.playerDataVersion < SharedConstants.getCurrentVersion().getWorldVersion()) {
            if (this.fixerUpper == null) {
               throw (NullPointerException)Util.pauseInIde(
                  new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded.")
               );
            }

            this.loadedPlayerTag = NbtUtils.update(this.fixerUpper, DataFixTypes.PLAYER, this.loadedPlayerTag, this.playerDataVersion);
         }

         this.upgradedPlayerTag = true;
      }
   }

   @Override
   public CompoundTag getLoadedPlayerTag() {
      this.updatePlayerTag();
      return this.loadedPlayerTag;
   }

   @Override
   public void setXSpawn(int var1) {
      this.xSpawn = â˜ƒ;
   }

   @Override
   public void setYSpawn(int var1) {
      this.ySpawn = â˜ƒ;
   }

   @Override
   public void setZSpawn(int var1) {
      this.zSpawn = â˜ƒ;
   }

   @Override
   public void setSpawnAngle(float var1) {
      this.spawnAngle = â˜ƒ;
   }

   @Override
   public void setGameTime(long var1) {
      this.gameTime = â˜ƒ;
   }

   @Override
   public void setDayTime(long var1) {
      this.dayTime = â˜ƒ;
   }

   @Override
   public void setSpawn(BlockPos var1, float var2) {
      this.xSpawn = â˜ƒ.getX();
      this.ySpawn = â˜ƒ.getY();
      this.zSpawn = â˜ƒ.getZ();
      this.spawnAngle = â˜ƒ;
   }

   @Override
   public String getLevelName() {
      return this.settings.levelName();
   }

   @Override
   public int getVersion() {
      return this.version;
   }

   @Override
   public int getClearWeatherTime() {
      return this.clearWeatherTime;
   }

   @Override
   public void setClearWeatherTime(int var1) {
      this.clearWeatherTime = â˜ƒ;
   }

   @Override
   public boolean isThundering() {
      return this.thundering;
   }

   @Override
   public void setThundering(boolean var1) {
      this.thundering = â˜ƒ;
   }

   @Override
   public int getThunderTime() {
      return this.thunderTime;
   }

   @Override
   public void setThunderTime(int var1) {
      this.thunderTime = â˜ƒ;
   }

   @Override
   public boolean isRaining() {
      return this.raining;
   }

   @Override
   public void setRaining(boolean var1) {
      this.raining = â˜ƒ;
   }

   @Override
   public int getRainTime() {
      return this.rainTime;
   }

   @Override
   public void setRainTime(int var1) {
      this.rainTime = â˜ƒ;
   }

   @Override
   public GameType getGameType() {
      return this.settings.gameType();
   }

   @Override
   public void setGameType(GameType var1) {
      this.settings = this.settings.withGameType(â˜ƒ);
   }

   @Override
   public boolean isHardcore() {
      return this.settings.hardcore();
   }

   @Override
   public boolean getAllowCommands() {
      return this.settings.allowCommands();
   }

   @Override
   public boolean isInitialized() {
      return this.initialized;
   }

   @Override
   public void setInitialized(boolean var1) {
      this.initialized = â˜ƒ;
   }

   @Override
   public GameRules getGameRules() {
      return this.settings.gameRules();
   }

   @Override
   public WorldBorder.Settings getWorldBorder() {
      return this.worldBorder;
   }

   @Override
   public void setWorldBorder(WorldBorder.Settings var1) {
      this.worldBorder = â˜ƒ;
   }

   @Override
   public Difficulty getDifficulty() {
      return this.settings.difficulty();
   }

   @Override
   public void setDifficulty(Difficulty var1) {
      this.settings = this.settings.withDifficulty(â˜ƒ);
   }

   @Override
   public boolean isDifficultyLocked() {
      return this.difficultyLocked;
   }

   @Override
   public void setDifficultyLocked(boolean var1) {
      this.difficultyLocked = â˜ƒ;
   }

   @Override
   public TimerQueue<MinecraftServer> getScheduledEvents() {
      return this.scheduledEvents;
   }

   @Override
   public void fillCrashReportCategory(CrashReportCategory var1, LevelHeightAccessor var2) {
      ServerLevelData.super.fillCrashReportCategory(â˜ƒ, â˜ƒ);
      WorldData.super.fillCrashReportCategory(â˜ƒ);
   }

   @Override
   public WorldGenSettings worldGenSettings() {
      return this.worldGenSettings;
   }

   @Override
   public Lifecycle worldGenSettingsLifecycle() {
      return this.worldGenSettingsLifecycle;
   }

   @Override
   public CompoundTag endDragonFightData() {
      return this.endDragonFightData;
   }

   @Override
   public void setEndDragonFightData(CompoundTag var1) {
      this.endDragonFightData = â˜ƒ;
   }

   @Override
   public DataPackConfig getDataPackConfig() {
      return this.settings.getDataPackConfig();
   }

   @Override
   public void setDataPackConfig(DataPackConfig var1) {
      this.settings = this.settings.withDataPackConfig(â˜ƒ);
   }

   @Nullable
   @Override
   public CompoundTag getCustomBossEvents() {
      return this.customBossEvents;
   }

   @Override
   public void setCustomBossEvents(@Nullable CompoundTag var1) {
      this.customBossEvents = â˜ƒ;
   }

   @Override
   public int getWanderingTraderSpawnDelay() {
      return this.wanderingTraderSpawnDelay;
   }

   @Override
   public void setWanderingTraderSpawnDelay(int var1) {
      this.wanderingTraderSpawnDelay = â˜ƒ;
   }

   @Override
   public int getWanderingTraderSpawnChance() {
      return this.wanderingTraderSpawnChance;
   }

   @Override
   public void setWanderingTraderSpawnChance(int var1) {
      this.wanderingTraderSpawnChance = â˜ƒ;
   }

   @Nullable
   @Override
   public UUID getWanderingTraderId() {
      return this.wanderingTraderId;
   }

   @Override
   public void setWanderingTraderId(UUID var1) {
      this.wanderingTraderId = â˜ƒ;
   }

   @Override
   public void setModdedInfo(String var1, boolean var2) {
      this.knownServerBrands.add(â˜ƒ);
      this.wasModded |= â˜ƒ;
   }

   @Override
   public boolean wasModded() {
      return this.wasModded;
   }

   @Override
   public Set<String> getKnownServerBrands() {
      return ImmutableSet.copyOf(this.knownServerBrands);
   }

   @Override
   public ServerLevelData overworldData() {
      return this;
   }

   @Override
   public LevelSettings getLevelSettings() {
      return this.settings.copy();
   }
}
