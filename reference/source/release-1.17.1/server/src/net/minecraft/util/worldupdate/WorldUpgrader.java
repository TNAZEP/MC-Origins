package net.minecraft.util.worldupdate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.chunk.storage.RegionFile;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldUpgrader {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setDaemon(true).build();
   private final ImmutableSet<ResourceKey<Level>> levels;
   private final boolean eraseCache;
   private final LevelStorageSource.LevelStorageAccess levelStorage;
   private final Thread thread;
   private final DataFixer dataFixer;
   private volatile boolean running = true;
   private volatile boolean finished;
   private volatile float progress;
   private volatile int totalChunks;
   private volatile int converted;
   private volatile int skipped;
   private final Object2FloatMap<ResourceKey<Level>> progressMap = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap<>(Util.identityStrategy()));
   private volatile Component status = new TranslatableComponent("optimizeWorld.stage.counting");
   private static final Pattern REGEX = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
   private final DimensionDataStorage overworldDataStorage;

   public WorldUpgrader(LevelStorageSource.LevelStorageAccess var1, DataFixer var2, ImmutableSet<ResourceKey<Level>> var3, boolean var4) {
      this.levels = â˜ƒ;
      this.eraseCache = â˜ƒ;
      this.dataFixer = â˜ƒ;
      this.levelStorage = â˜ƒ;
      this.overworldDataStorage = new DimensionDataStorage(new File(this.levelStorage.getDimensionPath(Level.OVERWORLD), "data"), â˜ƒ);
      this.thread = THREAD_FACTORY.newThread(this::work);
      this.thread.setUncaughtExceptionHandler((var1x, var2x) -> {
         LOGGER.error("Error upgrading world", var2x);
         this.status = new TranslatableComponent("optimizeWorld.stage.failed");
         this.finished = true;
      });
      this.thread.start();
   }

   public void cancel() {
      this.running = false;

      try {
         this.thread.join();
      } catch (InterruptedException var2) {
      }
   }

   private void work() {
      this.totalChunks = 0;
      Builder<ResourceKey<Level>, ListIterator<ChunkPos>> â˜ƒ = ImmutableMap.builder();

      for(ResourceKey<Level> â˜ƒx : this.levels) {
         List<ChunkPos> â˜ƒxx = this.getAllChunkPos(â˜ƒx);
         â˜ƒ.put(â˜ƒx, â˜ƒxx.listIterator());
         this.totalChunks += â˜ƒxx.size();
      }

      if (this.totalChunks == 0) {
         this.finished = true;
      } else {
         float â˜ƒx = (float)this.totalChunks;
         ImmutableMap<ResourceKey<Level>, ListIterator<ChunkPos>> â˜ƒxx = â˜ƒ.build();
         Builder<ResourceKey<Level>, ChunkStorage> â˜ƒxxx = ImmutableMap.builder();

         for(ResourceKey<Level> â˜ƒxxxx : this.levels) {
            File â˜ƒxxxxx = this.levelStorage.getDimensionPath(â˜ƒxxxx);
            â˜ƒxxx.put(â˜ƒxxxx, new ChunkStorage(new File(â˜ƒxxxxx, "region"), this.dataFixer, true));
         }

         ImmutableMap<ResourceKey<Level>, ChunkStorage> â˜ƒxxxx = â˜ƒxxx.build();
         long â˜ƒxxxxx = Util.getMillis();
         this.status = new TranslatableComponent("optimizeWorld.stage.upgrading");

         while(this.running) {
            boolean â˜ƒxxxxxx = false;
            float â˜ƒxxxxxxx = 0.0F;

            for(ResourceKey<Level> â˜ƒxxxxxxxx : this.levels) {
               ListIterator<ChunkPos> â˜ƒxxxxxxxxx = (ListIterator)â˜ƒxx.get(â˜ƒxxxxxxxx);
               ChunkStorage â˜ƒxxxxxxxxxx = â˜ƒxxxx.get(â˜ƒxxxxxxxx);
               if (â˜ƒxxxxxxxxx.hasNext()) {
                  ChunkPos â˜ƒxxxxxxxxxxx = (ChunkPos)â˜ƒxxxxxxxxx.next();
                  boolean â˜ƒxxxxxxxxxxxx = false;

                  try {
                     CompoundTag â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.read(â˜ƒxxxxxxxxxxx);
                     if (â˜ƒxxxxxxxxxxxxx != null) {
                        int â˜ƒxxxxxxxxxxxxxx = ChunkStorage.getVersion(â˜ƒxxxxxxxxxxxxx);
                        CompoundTag â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.upgradeChunkTag(â˜ƒxxxxxxxx, () -> this.overworldDataStorage, â˜ƒxxxxxxxxxxxxx);
                        CompoundTag â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx.getCompound("Level");
                        ChunkPos â˜ƒxxxxxxxxxxxxxxxxx = new ChunkPos(â˜ƒxxxxxxxxxxxxxxxx.getInt("xPos"), â˜ƒxxxxxxxxxxxxxxxx.getInt("zPos"));
                        if (!â˜ƒxxxxxxxxxxxxxxxxx.equals(â˜ƒxxxxxxxxxxx)) {
                           LOGGER.warn("Chunk {} has invalid position {}", â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx);
                        }

                        boolean â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx < SharedConstants.getCurrentVersion().getWorldVersion();
                        if (this.eraseCache) {
                           â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx || â˜ƒxxxxxxxxxxxxxxxx.contains("Heightmaps");
                           â˜ƒxxxxxxxxxxxxxxxx.remove("Heightmaps");
                           â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx || â˜ƒxxxxxxxxxxxxxxxx.contains("isLightOn");
                           â˜ƒxxxxxxxxxxxxxxxx.remove("isLightOn");
                        }

                        if (â˜ƒxxxxxxxxxxxxxx) {
                           â˜ƒxxxxxxxxxx.write(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
                           â˜ƒxxxxxxxxxxxx = true;
                        }
                     }
                  } catch (ReportedException var23) {
                     Throwable â˜ƒxxxxxxxxxxxxx = var23.getCause();
                     if (!(â˜ƒxxxxxxxxxxxxx instanceof IOException)) {
                        throw var23;
                     }

                     LOGGER.error("Error upgrading chunk {}", â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx);
                  } catch (IOException var24) {
                     LOGGER.error("Error upgrading chunk {}", â˜ƒxxxxxxxxxxx, var24);
                  }

                  if (â˜ƒxxxxxxxxxxxx) {
                     ++this.converted;
                  } else {
                     ++this.skipped;
                  }

                  â˜ƒxxxxxx = true;
               }

               float â˜ƒxxxxxxxxx = (float)â˜ƒxxxxxxxxx.nextIndex() / â˜ƒx;
               this.progressMap.put(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
               â˜ƒxxxxxxx += â˜ƒxxxxxxxxx;
            }

            this.progress = â˜ƒxxxxxxx;
            if (!â˜ƒxxxxxx) {
               this.running = false;
            }
         }

         this.status = new TranslatableComponent("optimizeWorld.stage.finished");

         for(ChunkStorage â˜ƒxxxxxx : â˜ƒxxxx.values()) {
            try {
               â˜ƒxxxxxx.close();
            } catch (IOException var22) {
               LOGGER.error("Error upgrading chunk", var22);
            }
         }

         this.overworldDataStorage.save();
         â˜ƒxxxxx = Util.getMillis() - â˜ƒxxxxx;
         LOGGER.info("World optimizaton finished after {} ms", â˜ƒxxxxx);
         this.finished = true;
      }
   }

   private List<ChunkPos> getAllChunkPos(ResourceKey<Level> var1) {
      File â˜ƒ = this.levelStorage.getDimensionPath(â˜ƒ);
      File â˜ƒx = new File(â˜ƒ, "region");
      File[] â˜ƒxx = â˜ƒx.listFiles((var0, var1x) -> var1x.endsWith(".mca"));
      if (â˜ƒxx == null) {
         return ImmutableList.of();
      } else {
         List<ChunkPos> â˜ƒ = Lists.<ChunkPos>newArrayList();

         for(File â˜ƒx : â˜ƒxx) {
            Matcher â˜ƒxx = REGEX.matcher(â˜ƒx.getName());
            if (â˜ƒxx.matches()) {
               int â˜ƒxxx = Integer.parseInt(â˜ƒxx.group(1)) << 5;
               int â˜ƒxxxx = Integer.parseInt(â˜ƒxx.group(2)) << 5;

               try (RegionFile â˜ƒxxxxx = new RegionFile(â˜ƒx, â˜ƒx, true)) {
                  for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 32; ++â˜ƒxxxxxx) {
                     for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 32; ++â˜ƒxxxxxxx) {
                        ChunkPos â˜ƒxxxxxxxx = new ChunkPos(â˜ƒxxxxxx + â˜ƒxxx, â˜ƒxxxxxxx + â˜ƒxxxx);
                        if (â˜ƒxxxxx.doesChunkExist(â˜ƒxxxxxxxx)) {
                           â˜ƒ.add(â˜ƒxxxxxxxx);
                        }
                     }
                  }
               } catch (Throwable var19) {
               }
            }
         }

         return â˜ƒ;
      }
   }

   public boolean isFinished() {
      return this.finished;
   }

   public ImmutableSet<ResourceKey<Level>> levels() {
      return this.levels;
   }

   public float dimensionProgress(ResourceKey<Level> var1) {
      return this.progressMap.getFloat(â˜ƒ);
   }

   public float getProgress() {
      return this.progress;
   }

   public int getTotalChunks() {
      return this.totalChunks;
   }

   public int getConverted() {
      return this.converted;
   }

   public int getSkipped() {
      return this.skipped;
   }

   public Component getStatus() {
      return this.status;
   }
}
