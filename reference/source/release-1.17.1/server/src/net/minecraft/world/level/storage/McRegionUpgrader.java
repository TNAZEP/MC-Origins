package net.minecraft.world.level.storage;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.ProgressListener;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.chunk.storage.OldChunkStorage;
import net.minecraft.world.level.chunk.storage.RegionFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class McRegionUpgrader {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String MCREGION_EXTENSION = ".mcr";

   static boolean convertLevel(LevelStorageSource.LevelStorageAccess var0, ProgressListener var1) {
      â˜ƒ.progressStagePercentage(0);
      List<File> â˜ƒ = Lists.newArrayList();
      List<File> â˜ƒx = Lists.newArrayList();
      List<File> â˜ƒxx = Lists.newArrayList();
      File â˜ƒxxx = â˜ƒ.getDimensionPath(Level.OVERWORLD);
      File â˜ƒxxxx = â˜ƒ.getDimensionPath(Level.NETHER);
      File â˜ƒxxxxx = â˜ƒ.getDimensionPath(Level.END);
      LOGGER.info("Scanning folders...");
      addRegionFiles(â˜ƒxxx, â˜ƒ);
      if (â˜ƒxxxx.exists()) {
         addRegionFiles(â˜ƒxxxx, â˜ƒx);
      }

      if (â˜ƒxxxxx.exists()) {
         addRegionFiles(â˜ƒxxxxx, â˜ƒxx);
      }

      int â˜ƒx = â˜ƒ.size() + â˜ƒx.size() + â˜ƒxx.size();
      LOGGER.info("Total conversion count is {}", â˜ƒx);
      RegistryAccess.RegistryHolder â˜ƒxx = RegistryAccess.builtin();
      RegistryReadOps<Tag> â˜ƒxxx = RegistryReadOps.createAndLoad(NbtOps.INSTANCE, ResourceManager.Empty.INSTANCE, â˜ƒxx);
      WorldData â˜ƒxxxx = â˜ƒ.getDataTag(â˜ƒxxx, DataPackConfig.DEFAULT);
      long â˜ƒxxxxx = â˜ƒxxxx != null ? â˜ƒxxxx.worldGenSettings().seed() : 0L;
      Registry<Biome> â˜ƒxxxxxx = â˜ƒxx.registryOrThrow(Registry.BIOME_REGISTRY);
      BiomeSource â˜ƒ;
      if (â˜ƒxxxx != null && â˜ƒxxxx.worldGenSettings().isFlatWorld()) {
         â˜ƒ = new FixedBiomeSource(â˜ƒxxxxxx.getOrThrow(Biomes.PLAINS));
      } else {
         â˜ƒ = new OverworldBiomeSource(â˜ƒxxxxx, false, false, â˜ƒxxxxxx);
      }

      convertRegions(â˜ƒxx, new File(â˜ƒxxx, "region"), â˜ƒ, â˜ƒ, 0, â˜ƒx, â˜ƒ);
      convertRegions(â˜ƒxx, new File(â˜ƒxxxx, "region"), â˜ƒx, new FixedBiomeSource(â˜ƒxxxxxx.getOrThrow(Biomes.NETHER_WASTES)), â˜ƒ.size(), â˜ƒx, â˜ƒ);
      convertRegions(
         â˜ƒxx, new File(â˜ƒxxxxx, "region"), â˜ƒxx, new FixedBiomeSource(â˜ƒxxxxxx.getOrThrow(Biomes.THE_END)), â˜ƒ.size() + â˜ƒx.size(), â˜ƒx, â˜ƒ
      );
      makeMcrLevelDatBackup(â˜ƒ);
      â˜ƒ.saveDataTag(â˜ƒxx, â˜ƒxxxx);
      return true;
   }

   private static void makeMcrLevelDatBackup(LevelStorageSource.LevelStorageAccess var0) {
      File â˜ƒ = â˜ƒ.getLevelPath(LevelResource.LEVEL_DATA_FILE).toFile();
      if (!â˜ƒ.exists()) {
         LOGGER.warn("Unable to create level.dat_mcr backup");
      } else {
         File â˜ƒ = new File(â˜ƒ.getParent(), "level.dat_mcr");
         if (!â˜ƒ.renameTo(â˜ƒ)) {
            LOGGER.warn("Unable to create level.dat_mcr backup");
         }
      }
   }

   private static void convertRegions(
      RegistryAccess.RegistryHolder var0, File var1, Iterable<File> var2, BiomeSource var3, int var4, int var5, ProgressListener var6
   ) {
      for(File â˜ƒ : â˜ƒ) {
         convertRegion(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         ++â˜ƒ;
         int â˜ƒx = (int)Math.round(100.0 * (double)â˜ƒ / (double)â˜ƒ);
         â˜ƒ.progressStagePercentage(â˜ƒx);
      }
   }

   private static void convertRegion(RegistryAccess.RegistryHolder var0, File var1, File var2, BiomeSource var3, int var4, int var5, ProgressListener var6) {
      String â˜ƒ = â˜ƒ.getName();

      try (
         RegionFile â˜ƒx = new RegionFile(â˜ƒ, â˜ƒ, true);
         RegionFile â˜ƒxx = new RegionFile(new File(â˜ƒ, â˜ƒ.substring(0, â˜ƒ.length() - ".mcr".length()) + ".mca"), â˜ƒ, true);
      ) {
         for(int â˜ƒxxx = 0; â˜ƒxxx < 32; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 32; ++â˜ƒxxxx) {
               ChunkPos â˜ƒxxxxx = new ChunkPos(â˜ƒxxx, â˜ƒxxxx);
               if (â˜ƒx.hasChunk(â˜ƒxxxxx) && !â˜ƒxx.hasChunk(â˜ƒxxxxx)) {
                  CompoundTag â˜ƒ;
                  try {
                     DataInputStream â˜ƒxxxxxx = â˜ƒx.getChunkDataInputStream(â˜ƒxxxxx);

                     label111: {
                        try {
                           if (â˜ƒxxxxxx != null) {
                              â˜ƒ = NbtIo.read(â˜ƒxxxxxx);
                              break label111;
                           }

                           LOGGER.warn("Failed to fetch input stream for chunk {}", â˜ƒxxxxx);
                        } catch (Throwable var26) {
                           if (â˜ƒxxxxxx != null) {
                              try {
                                 â˜ƒxxxxxx.close();
                              } catch (Throwable var24) {
                                 var26.addSuppressed(var24);
                              }
                           }

                           throw var26;
                        }

                        if (â˜ƒxxxxxx != null) {
                           â˜ƒxxxxxx.close();
                        }
                        continue;
                     }

                     if (â˜ƒxxxxxx != null) {
                        â˜ƒxxxxxx.close();
                     }
                  } catch (IOException var27) {
                     LOGGER.warn("Failed to read data for chunk {}", â˜ƒxxxxx, var27);
                     continue;
                  }

                  CompoundTag â˜ƒxxxxxx = â˜ƒ.getCompound("Level");
                  OldChunkStorage.OldLevelChunk â˜ƒxxxxxxx = OldChunkStorage.load(â˜ƒxxxxxx);
                  CompoundTag â˜ƒxxxxxxxx = new CompoundTag();
                  CompoundTag â˜ƒxxxxxxxxx = new CompoundTag();
                  â˜ƒxxxxxxxx.put("Level", â˜ƒxxxxxxxxx);
                  OldChunkStorage.convertToAnvilFormat(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒ);
                  DataOutputStream â˜ƒxxxxxxxxxx = â˜ƒxx.getChunkDataOutputStream(â˜ƒxxxxx);

                  try {
                     NbtIo.write(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx);
                  } catch (Throwable var25) {
                     if (â˜ƒxxxxxxxxxx != null) {
                        try {
                           â˜ƒxxxxxxxxxx.close();
                        } catch (Throwable var23) {
                           var25.addSuppressed(var23);
                        }
                     }

                     throw var25;
                  }

                  if (â˜ƒxxxxxxxxxx != null) {
                     â˜ƒxxxxxxxxxx.close();
                  }
               }
            }

            int â˜ƒxxxx = (int)Math.round(100.0 * (double)(â˜ƒ * 1024) / (double)(â˜ƒ * 1024));
            int â˜ƒxxxxx = (int)Math.round(100.0 * (double)((â˜ƒxxx + 1) * 32 + â˜ƒ * 1024) / (double)(â˜ƒ * 1024));
            if (â˜ƒxxxxx > â˜ƒxxxx) {
               â˜ƒ.progressStagePercentage(â˜ƒxxxxx);
            }
         }
      } catch (IOException var30) {
         LOGGER.error("Failed to upgrade region file {}", â˜ƒ, var30);
      }
   }

   private static void addRegionFiles(File var0, Collection<File> var1) {
      File â˜ƒ = new File(â˜ƒ, "region");
      File[] â˜ƒx = â˜ƒ.listFiles((var0x, var1x) -> var1x.endsWith(".mcr"));
      if (â˜ƒx != null) {
         Collections.addAll(â˜ƒ, â˜ƒx);
      }
   }
}
