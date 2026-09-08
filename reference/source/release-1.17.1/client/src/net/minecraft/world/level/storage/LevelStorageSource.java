package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.DirectoryLock;
import net.minecraft.util.MemoryReserve;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelStorageSource {
   static final Logger LOGGER = LogManager.getLogger();
   static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.DAY_OF_MONTH, 2)
      .appendLiteral('_')
      .appendValue(ChronoField.HOUR_OF_DAY, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
      .toFormatter();
   private static final String ICON_FILENAME = "icon.png";
   private static final ImmutableList<String> OLD_SETTINGS_KEYS = ImmutableList.of(
      "RandomSeed", "generatorName", "generatorOptions", "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest"
   );
   final Path baseDir;
   private final Path backupDir;
   final DataFixer fixerUpper;

   public LevelStorageSource(Path var1, Path var2, DataFixer var3) {
      this.fixerUpper = â˜ƒ;

      try {
         Files.createDirectories(Files.exists(â˜ƒ, new LinkOption[0]) ? â˜ƒ.toRealPath() : â˜ƒ);
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      this.baseDir = â˜ƒ;
      this.backupDir = â˜ƒ;
   }

   public static LevelStorageSource createDefault(Path var0) {
      return new LevelStorageSource(â˜ƒ, â˜ƒ.resolve("../backups"), DataFixers.getDataFixer());
   }

   private static <T> Pair<WorldGenSettings, Lifecycle> readWorldGenSettings(Dynamic<T> var0, DataFixer var1, int var2) {
      Dynamic<T> â˜ƒ = â˜ƒ.get("WorldGenSettings").orElseEmptyMap();

      for(String â˜ƒx : OLD_SETTINGS_KEYS) {
         Optional<? extends Dynamic<?>> â˜ƒxx = â˜ƒ.get(â˜ƒx).result();
         if (â˜ƒxx.isPresent()) {
            â˜ƒ = â˜ƒ.set(â˜ƒx, (Dynamic<?>)â˜ƒxx.get());
         }
      }

      Dynamic<T> â˜ƒx = â˜ƒ.update(References.WORLD_GEN_SETTINGS, â˜ƒ, â˜ƒ, SharedConstants.getCurrentVersion().getWorldVersion());
      DataResult<WorldGenSettings> â˜ƒxx = WorldGenSettings.CODEC.parse(â˜ƒx);
      return Pair.of(
         (WorldGenSettings)â˜ƒxx.resultOrPartial(Util.prefix("WorldGenSettings: ", LOGGER::error))
            .orElseGet(
               () -> {
                  Registry<DimensionType> â˜ƒ = (Registry)RegistryLookupCodec.create(Registry.DIMENSION_TYPE_REGISTRY)
                     .codec()
                     .parse(â˜ƒ)
                     .resultOrPartial(Util.prefix("Dimension type registry: ", LOGGER::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get dimension registry"));
                  Registry<Biome> â˜ƒx = (Registry)RegistryLookupCodec.create(Registry.BIOME_REGISTRY)
                     .codec()
                     .parse(â˜ƒ)
                     .resultOrPartial(Util.prefix("Biome registry: ", LOGGER::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get biome registry"));
                  Registry<NoiseGeneratorSettings> â˜ƒxx = (Registry)RegistryLookupCodec.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY)
                     .codec()
                     .parse(â˜ƒ)
                     .resultOrPartial(Util.prefix("Noise settings registry: ", LOGGER::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get noise settings registry"));
                  return WorldGenSettings.makeDefault(â˜ƒ, â˜ƒx, â˜ƒxx);
               }
            ),
         â˜ƒxx.lifecycle()
      );
   }

   private static DataPackConfig readDataPackConfig(Dynamic<?> var0) {
      return (DataPackConfig)DataPackConfig.CODEC.parse(â˜ƒ).resultOrPartial(LOGGER::error).orElse(DataPackConfig.DEFAULT);
   }

   public String getName() {
      return "Anvil";
   }

   public List<LevelSummary> getLevelList() throws LevelStorageException {
      if (!Files.isDirectory(this.baseDir, new LinkOption[0])) {
         throw new LevelStorageException(new TranslatableComponent("selectWorld.load_folder_access").getString());
      } else {
         List<LevelSummary> â˜ƒ = Lists.<LevelSummary>newArrayList();
         File[] â˜ƒx = this.baseDir.toFile().listFiles();

         for(File â˜ƒxx : â˜ƒx) {
            if (â˜ƒxx.isDirectory()) {
               boolean â˜ƒ;
               try {
                  â˜ƒ = DirectoryLock.isLocked(â˜ƒxx.toPath());
               } catch (Exception var10) {
                  LOGGER.warn("Failed to read {} lock", â˜ƒxx, var10);
                  continue;
               }

               try {
                  LevelSummary â˜ƒxxx = this.readLevelData(â˜ƒxx, this.levelSummaryReader(â˜ƒxx, â˜ƒ));
                  if (â˜ƒxxx != null) {
                     â˜ƒ.add(â˜ƒxxx);
                  }
               } catch (OutOfMemoryError var9) {
                  MemoryReserve.release();
                  System.gc();
                  LOGGER.fatal("Ran out of memory trying to read summary of {}", â˜ƒxx);
                  throw var9;
               }
            }
         }

         return â˜ƒ;
      }
   }

   int getStorageVersion() {
      return 19133;
   }

   @Nullable
   <T> T readLevelData(File var1, BiFunction<File, DataFixer, T> var2) {
      if (!â˜ƒ.exists()) {
         return null;
      } else {
         File â˜ƒ = new File(â˜ƒ, "level.dat");
         if (â˜ƒ.exists()) {
            T â˜ƒx = (T)â˜ƒ.apply(â˜ƒ, this.fixerUpper);
            if (â˜ƒx != null) {
               return â˜ƒx;
            }
         }

         â˜ƒ = new File(â˜ƒ, "level.dat_old");
         return (T)(â˜ƒ.exists() ? â˜ƒ.apply(â˜ƒ, this.fixerUpper) : null);
      }
   }

   @Nullable
   private static DataPackConfig getDataPacks(File var0, DataFixer var1) {
      try {
         CompoundTag â˜ƒ = NbtIo.readCompressed(â˜ƒ);
         CompoundTag â˜ƒx = â˜ƒ.getCompound("Data");
         â˜ƒx.remove("Player");
         int â˜ƒxx = â˜ƒx.contains("DataVersion", 99) ? â˜ƒx.getInt("DataVersion") : -1;
         Dynamic<Tag> â˜ƒxxx = â˜ƒ.update(
            DataFixTypes.LEVEL.getType(), new Dynamic<>(NbtOps.INSTANCE, â˜ƒx), â˜ƒxx, SharedConstants.getCurrentVersion().getWorldVersion()
         );
         return (DataPackConfig)â˜ƒxxx.get("DataPacks").result().map(LevelStorageSource::readDataPackConfig).orElse(DataPackConfig.DEFAULT);
      } catch (Exception var6) {
         LOGGER.error("Exception reading {}", â˜ƒ, var6);
         return null;
      }
   }

   static BiFunction<File, DataFixer, PrimaryLevelData> getLevelData(DynamicOps<Tag> var0, DataPackConfig var1) {
      return (var2, var3) -> {
         try {
            CompoundTag â˜ƒ = NbtIo.readCompressed(var2);
            CompoundTag â˜ƒx = â˜ƒ.getCompound("Data");
            CompoundTag â˜ƒxx = â˜ƒx.contains("Player", 10) ? â˜ƒx.getCompound("Player") : null;
            â˜ƒx.remove("Player");
            int â˜ƒxxx = â˜ƒx.contains("DataVersion", 99) ? â˜ƒx.getInt("DataVersion") : -1;
            Dynamic<Tag> â˜ƒxxxx = var3.update(
               DataFixTypes.LEVEL.getType(), new Dynamic<>(â˜ƒ, â˜ƒx), â˜ƒxxx, SharedConstants.getCurrentVersion().getWorldVersion()
            );
            Pair<WorldGenSettings, Lifecycle> â˜ƒxxxxx = readWorldGenSettings(â˜ƒxxxx, var3, â˜ƒxxx);
            LevelVersion â˜ƒxxxxxx = LevelVersion.parse(â˜ƒxxxx);
            LevelSettings â˜ƒxxxxxxx = LevelSettings.parse(â˜ƒxxxx, â˜ƒ);
            return PrimaryLevelData.parse(â˜ƒxxxx, var3, â˜ƒxxx, â˜ƒxx, â˜ƒxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxx.getFirst(), â˜ƒxxxxx.getSecond());
         } catch (Exception var12) {
            LOGGER.error("Exception reading {}", var2, var12);
            return null;
         }
      };
   }

   BiFunction<File, DataFixer, LevelSummary> levelSummaryReader(File var1, boolean var2) {
      return (var3, var4) -> {
         try {
            CompoundTag â˜ƒ = NbtIo.readCompressed(var3);
            CompoundTag â˜ƒx = â˜ƒ.getCompound("Data");
            â˜ƒx.remove("Player");
            int â˜ƒxx = â˜ƒx.contains("DataVersion", 99) ? â˜ƒx.getInt("DataVersion") : -1;
            Dynamic<Tag> â˜ƒxxx = var4.update(
               DataFixTypes.LEVEL.getType(), new Dynamic<>(NbtOps.INSTANCE, â˜ƒx), â˜ƒxx, SharedConstants.getCurrentVersion().getWorldVersion()
            );
            LevelVersion â˜ƒxxxx = LevelVersion.parse(â˜ƒxxx);
            int â˜ƒxxxxx = â˜ƒxxxx.levelDataVersion();
            if (â˜ƒxxxxx != 19132 && â˜ƒxxxxx != 19133) {
               return null;
            } else {
               boolean â˜ƒ = â˜ƒxxxxx != this.getStorageVersion();
               File â˜ƒx = new File(â˜ƒ, "icon.png");
               DataPackConfig â˜ƒxx = (DataPackConfig)â˜ƒxxx.get("DataPacks")
                  .result()
                  .map(LevelStorageSource::readDataPackConfig)
                  .orElse(DataPackConfig.DEFAULT);
               LevelSettings â˜ƒxxx = LevelSettings.parse(â˜ƒxxx, â˜ƒxx);
               return new LevelSummary(â˜ƒxxx, â˜ƒxxxx, â˜ƒ.getName(), â˜ƒ, â˜ƒ, â˜ƒx);
            }
         } catch (Exception var15) {
            LOGGER.error("Exception reading {}", var3, var15);
            return null;
         }
      };
   }

   public boolean isNewLevelIdAcceptable(String var1) {
      try {
         Path â˜ƒ = this.baseDir.resolve(â˜ƒ);
         Files.createDirectory(â˜ƒ);
         Files.deleteIfExists(â˜ƒ);
         return true;
      } catch (IOException var3) {
         return false;
      }
   }

   public boolean levelExists(String var1) {
      return Files.isDirectory(this.baseDir.resolve(â˜ƒ), new LinkOption[0]);
   }

   public Path getBaseDir() {
      return this.baseDir;
   }

   public Path getBackupPath() {
      return this.backupDir;
   }

   public LevelStorageSource.LevelStorageAccess createAccess(String var1) throws IOException {
      return new LevelStorageSource.LevelStorageAccess(â˜ƒ);
   }

   public class LevelStorageAccess implements AutoCloseable {
      final DirectoryLock lock;
      final Path levelPath;
      private final String levelId;
      private final Map<LevelResource, Path> resources = Maps.newHashMap();

      public LevelStorageAccess(String var2) throws IOException {
         this.levelId = â˜ƒ;
         this.levelPath = LevelStorageSource.this.baseDir.resolve(â˜ƒ);
         this.lock = DirectoryLock.create(this.levelPath);
      }

      public String getLevelId() {
         return this.levelId;
      }

      public Path getLevelPath(LevelResource var1) {
         return (Path)this.resources.computeIfAbsent(â˜ƒ, var1x -> this.levelPath.resolve(var1x.getId()));
      }

      public File getDimensionPath(ResourceKey<Level> var1) {
         return DimensionType.getStorageFolder(â˜ƒ, this.levelPath.toFile());
      }

      private void checkLock() {
         if (!this.lock.isValid()) {
            throw new IllegalStateException("Lock is no longer valid");
         }
      }

      public PlayerDataStorage createPlayerStorage() {
         this.checkLock();
         return new PlayerDataStorage(this, LevelStorageSource.this.fixerUpper);
      }

      public boolean requiresConversion() {
         LevelSummary â˜ƒ = this.getSummary();
         return â˜ƒ != null && â˜ƒ.levelVersion().levelDataVersion() != LevelStorageSource.this.getStorageVersion();
      }

      public boolean convertLevel(ProgressListener var1) {
         this.checkLock();
         return McRegionUpgrader.convertLevel(this, â˜ƒ);
      }

      @Nullable
      public LevelSummary getSummary() {
         this.checkLock();
         return LevelStorageSource.this.readLevelData(this.levelPath.toFile(), LevelStorageSource.this.levelSummaryReader(this.levelPath.toFile(), false));
      }

      @Nullable
      public WorldData getDataTag(DynamicOps<Tag> var1, DataPackConfig var2) {
         this.checkLock();
         return LevelStorageSource.this.readLevelData(this.levelPath.toFile(), LevelStorageSource.getLevelData(â˜ƒ, â˜ƒ));
      }

      @Nullable
      public DataPackConfig getDataPacks() {
         this.checkLock();
         return LevelStorageSource.this.readLevelData(this.levelPath.toFile(), LevelStorageSource::getDataPacks);
      }

      public void saveDataTag(RegistryAccess var1, WorldData var2) {
         this.saveDataTag(â˜ƒ, â˜ƒ, null);
      }

      public void saveDataTag(RegistryAccess var1, WorldData var2, @Nullable CompoundTag var3) {
         File â˜ƒ = this.levelPath.toFile();
         CompoundTag â˜ƒx = â˜ƒ.createTag(â˜ƒ, â˜ƒ);
         CompoundTag â˜ƒxx = new CompoundTag();
         â˜ƒxx.put("Data", â˜ƒx);

         try {
            File â˜ƒxxx = File.createTempFile("level", ".dat", â˜ƒ);
            NbtIo.writeCompressed(â˜ƒxx, â˜ƒxxx);
            File â˜ƒxxxx = new File(â˜ƒ, "level.dat_old");
            File â˜ƒxxxxx = new File(â˜ƒ, "level.dat");
            Util.safeReplaceFile(â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx);
         } catch (Exception var10) {
            LevelStorageSource.LOGGER.error("Failed to save level {}", â˜ƒ, var10);
         }
      }

      public Optional<Path> getIconFile() {
         return !this.lock.isValid() ? Optional.empty() : Optional.of(this.levelPath.resolve("icon.png"));
      }

      public void deleteLevel() throws IOException {
         this.checkLock();
         final Path â˜ƒ = this.levelPath.resolve("session.lock");

         for(int â˜ƒx = 1; â˜ƒx <= 5; ++â˜ƒx) {
            LevelStorageSource.LOGGER.info("Attempt {}...", â˜ƒx);

            try {
               Files.walkFileTree(this.levelPath, new SimpleFileVisitor<Path>() {
                  public FileVisitResult visitFile(Path var1x, BasicFileAttributes var2) throws IOException {
                     if (!â˜ƒ.equals(â˜ƒ)) {
                        LevelStorageSource.LOGGER.debug("Deleting {}", â˜ƒ);
                        Files.delete(â˜ƒ);
                     }

                     return FileVisitResult.CONTINUE;
                  }

                  public FileVisitResult postVisitDirectory(Path var1x, IOException var2) throws IOException {
                     if (â˜ƒ != null) {
                        throw â˜ƒ;
                     } else {
                        if (â˜ƒ.equals(LevelStorageAccess.this.levelPath)) {
                           LevelStorageAccess.this.lock.close();
                           Files.deleteIfExists(â˜ƒ);
                        }

                        Files.delete(â˜ƒ);
                        return FileVisitResult.CONTINUE;
                     }
                  }
               });
               break;
            } catch (IOException var6) {
               if (â˜ƒx >= 5) {
                  throw var6;
               }

               LevelStorageSource.LOGGER.warn("Failed to delete {}", this.levelPath, var6);

               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var5) {
               }
            }
         }
      }

      public void renameLevel(String var1) throws IOException {
         this.checkLock();
         File â˜ƒ = new File(LevelStorageSource.this.baseDir.toFile(), this.levelId);
         if (â˜ƒ.exists()) {
            File â˜ƒx = new File(â˜ƒ, "level.dat");
            if (â˜ƒx.exists()) {
               CompoundTag â˜ƒxx = NbtIo.readCompressed(â˜ƒx);
               CompoundTag â˜ƒxxx = â˜ƒxx.getCompound("Data");
               â˜ƒxxx.putString("LevelName", â˜ƒ);
               NbtIo.writeCompressed(â˜ƒxx, â˜ƒx);
            }
         }
      }

      public long makeWorldBackup() throws IOException {
         this.checkLock();
         String â˜ƒ = LocalDateTime.now().format(LevelStorageSource.FORMATTER) + "_" + this.levelId;
         Path â˜ƒx = LevelStorageSource.this.getBackupPath();

         try {
            Files.createDirectories(Files.exists(â˜ƒx, new LinkOption[0]) ? â˜ƒx.toRealPath() : â˜ƒx);
         } catch (IOException var9) {
            throw new RuntimeException(var9);
         }

         Path â˜ƒxx = â˜ƒx.resolve(FileUtil.findAvailableName(â˜ƒx, â˜ƒ, ".zip"));
         final ZipOutputStream â˜ƒxxx = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(â˜ƒxx)));

         try {
            final Path â˜ƒxxxx = Paths.get(this.levelId);
            Files.walkFileTree(this.levelPath, new SimpleFileVisitor<Path>() {
               public FileVisitResult visitFile(Path var1, BasicFileAttributes var2) throws IOException {
                  if (â˜ƒ.endsWith("session.lock")) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     String â˜ƒ = â˜ƒ.resolve(LevelStorageAccess.this.levelPath.relativize(â˜ƒ)).toString().replace('\\', '/');
                     ZipEntry â˜ƒx = new ZipEntry(â˜ƒ);
                     â˜ƒ.putNextEntry(â˜ƒx);
                     com.google.common.io.Files.asByteSource(â˜ƒ.toFile()).copyTo(â˜ƒ);
                     â˜ƒ.closeEntry();
                     return FileVisitResult.CONTINUE;
                  }
               }
            });
         } catch (Throwable var8) {
            try {
               â˜ƒxxx.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }

            throw var8;
         }

         â˜ƒxxx.close();
         return Files.size(â˜ƒxx);
      }

      public void close() throws IOException {
         this.lock.close();
      }
   }
}
