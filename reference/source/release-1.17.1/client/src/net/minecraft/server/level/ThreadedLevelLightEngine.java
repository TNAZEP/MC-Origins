package net.minecraft.server.level;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadedLevelLightEngine extends LevelLightEngine implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ProcessorMailbox<Runnable> taskMailbox;
   private final ObjectList<Pair<ThreadedLevelLightEngine.TaskType, Runnable>> lightTasks = new ObjectArrayList<>();
   private final ChunkMap chunkMap;
   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> sorterMailbox;
   private volatile int taskPerBatch = 5;
   private final AtomicBoolean scheduled = new AtomicBoolean();

   public ThreadedLevelLightEngine(
      LightChunkGetter var1, ChunkMap var2, boolean var3, ProcessorMailbox<Runnable> var4, ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> var5
   ) {
      super(â˜ƒ, true, â˜ƒ);
      this.chunkMap = â˜ƒ;
      this.sorterMailbox = â˜ƒ;
      this.taskMailbox = â˜ƒ;
   }

   public void close() {
   }

   @Override
   public int runUpdates(int var1, boolean var2, boolean var3) {
      throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("Ran automatically on a different thread!"));
   }

   @Override
   public void onBlockEmissionIncrease(BlockPos var1, int var2) {
      throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("Ran automatically on a different thread!"));
   }

   @Override
   public void checkBlock(BlockPos var1) {
      BlockPos â˜ƒ = â˜ƒ.immutable();
      this.addTask(
         SectionPos.blockToSectionCoord(â˜ƒ.getX()),
         SectionPos.blockToSectionCoord(â˜ƒ.getZ()),
         ThreadedLevelLightEngine.TaskType.POST_UPDATE,
         Util.name(() -> super.checkBlock(â˜ƒ), () -> "checkBlock " + â˜ƒ)
      );
   }

   protected void updateChunkStatus(ChunkPos var1) {
      this.addTask(â˜ƒ.x, â˜ƒ.z, () -> 0, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.name(() -> {
         super.retainData(â˜ƒ, false);
         super.enableLightSources(â˜ƒ, false);

         for(int â˜ƒ = this.getMinLightSection(); â˜ƒ < this.getMaxLightSection(); ++â˜ƒ) {
            super.queueSectionData(LightLayer.BLOCK, SectionPos.of(â˜ƒ, â˜ƒ), null, true);
            super.queueSectionData(LightLayer.SKY, SectionPos.of(â˜ƒ, â˜ƒ), null, true);
         }

         for(int â˜ƒ = this.levelHeightAccessor.getMinSection(); â˜ƒ < this.levelHeightAccessor.getMaxSection(); ++â˜ƒ) {
            super.updateSectionStatus(SectionPos.of(â˜ƒ, â˜ƒ), true);
         }
      }, () -> "updateChunkStatus " + â˜ƒ + " true"));
   }

   @Override
   public void updateSectionStatus(SectionPos var1, boolean var2) {
      this.addTask(
         â˜ƒ.x(),
         â˜ƒ.z(),
         () -> 0,
         ThreadedLevelLightEngine.TaskType.PRE_UPDATE,
         Util.name(() -> super.updateSectionStatus(â˜ƒ, â˜ƒ), () -> "updateSectionStatus " + â˜ƒ + " " + â˜ƒ)
      );
   }

   @Override
   public void enableLightSources(ChunkPos var1, boolean var2) {
      this.addTask(
         â˜ƒ.x,
         â˜ƒ.z,
         ThreadedLevelLightEngine.TaskType.PRE_UPDATE,
         Util.name(() -> super.enableLightSources(â˜ƒ, â˜ƒ), () -> "enableLight " + â˜ƒ + " " + â˜ƒ)
      );
   }

   @Override
   public void queueSectionData(LightLayer var1, SectionPos var2, @Nullable DataLayer var3, boolean var4) {
      this.addTask(
         â˜ƒ.x(),
         â˜ƒ.z(),
         () -> 0,
         ThreadedLevelLightEngine.TaskType.PRE_UPDATE,
         Util.name(() -> super.queueSectionData(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ), () -> "queueData " + â˜ƒ)
      );
   }

   private void addTask(int var1, int var2, ThreadedLevelLightEngine.TaskType var3, Runnable var4) {
      this.addTask(â˜ƒ, â˜ƒ, this.chunkMap.getChunkQueueLevel(ChunkPos.asLong(â˜ƒ, â˜ƒ)), â˜ƒ, â˜ƒ);
   }

   private void addTask(int var1, int var2, IntSupplier var3, ThreadedLevelLightEngine.TaskType var4, Runnable var5) {
      this.sorterMailbox.tell(ChunkTaskPriorityQueueSorter.message((Runnable)(() -> {
         this.lightTasks.add(Pair.of(â˜ƒ, â˜ƒ));
         if (this.lightTasks.size() >= this.taskPerBatch) {
            this.runUpdate();
         }
      }), ChunkPos.asLong(â˜ƒ, â˜ƒ), â˜ƒ));
   }

   @Override
   public void retainData(ChunkPos var1, boolean var2) {
      this.addTask(â˜ƒ.x, â˜ƒ.z, () -> 0, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.name(() -> super.retainData(â˜ƒ, â˜ƒ), () -> "retainData " + â˜ƒ));
   }

   public CompletableFuture<ChunkAccess> lightChunk(ChunkAccess var1, boolean var2) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      â˜ƒ.setLightCorrect(false);
      this.addTask(â˜ƒ.x, â˜ƒ.z, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.name(() -> {
         LevelChunkSection[] â˜ƒ = â˜ƒ.getSections();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getSectionsCount(); ++â˜ƒx) {
            LevelChunkSection â˜ƒxx = â˜ƒ[â˜ƒx];
            if (!LevelChunkSection.isEmpty(â˜ƒxx)) {
               int â˜ƒxxx = this.levelHeightAccessor.getSectionYFromSectionIndex(â˜ƒx);
               super.updateSectionStatus(SectionPos.of(â˜ƒ, â˜ƒxxx), false);
            }
         }

         super.enableLightSources(â˜ƒ, true);
         if (!â˜ƒ) {
            â˜ƒ.getLights().forEach(var2x -> super.onBlockEmissionIncrease(var2x, â˜ƒ.getLightEmission(var2x)));
         }
      }, () -> "lightChunk " + â˜ƒ + " " + â˜ƒ));
      return CompletableFuture.supplyAsync(() -> {
         â˜ƒ.setLightCorrect(true);
         super.retainData(â˜ƒ, false);
         this.chunkMap.releaseLightTicket(â˜ƒ);
         return â˜ƒ;
      }, var2x -> this.addTask(â˜ƒ.x, â˜ƒ.z, ThreadedLevelLightEngine.TaskType.POST_UPDATE, var2x));
   }

   public void tryScheduleUpdate() {
      if ((!this.lightTasks.isEmpty() || super.hasLightWork()) && this.scheduled.compareAndSet(false, true)) {
         this.taskMailbox.tell((Runnable)() -> {
            this.runUpdate();
            this.scheduled.set(false);
         });
      }
   }

   private void runUpdate() {
      int â˜ƒ = Math.min(this.lightTasks.size(), this.taskPerBatch);
      ObjectListIterator<Pair<ThreadedLevelLightEngine.TaskType, Runnable>> â˜ƒx = this.lightTasks.iterator();

      int â˜ƒ;
      for(â˜ƒ = 0; â˜ƒx.hasNext() && â˜ƒ < â˜ƒ; ++â˜ƒ) {
         Pair<ThreadedLevelLightEngine.TaskType, Runnable> â˜ƒxx = (Pair)â˜ƒx.next();
         if (â˜ƒxx.getFirst() == ThreadedLevelLightEngine.TaskType.PRE_UPDATE) {
            ((Runnable)â˜ƒxx.getSecond()).run();
         }
      }

      â˜ƒx.back(â˜ƒ);
      super.runUpdates(Integer.MAX_VALUE, true, true);

      for(int var5 = 0; â˜ƒx.hasNext() && var5 < â˜ƒ; ++var5) {
         Pair<ThreadedLevelLightEngine.TaskType, Runnable> â˜ƒxx = (Pair)â˜ƒx.next();
         if (â˜ƒxx.getFirst() == ThreadedLevelLightEngine.TaskType.POST_UPDATE) {
            ((Runnable)â˜ƒxx.getSecond()).run();
         }

         â˜ƒx.remove();
      }
   }

   public void setTaskPerBatch(int var1) {
      this.taskPerBatch = â˜ƒ;
   }

   static enum TaskType {
      PRE_UPDATE,
      POST_UPDATE;
   }
}
