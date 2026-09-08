package net.minecraft.server.level.progress;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public class StoringChunkProgressListener implements ChunkProgressListener {
   private final LoggerChunkProgressListener delegate;
   private final Long2ObjectOpenHashMap<ChunkStatus> statuses;
   private ChunkPos spawnPos = new ChunkPos(0, 0);
   private final int fullDiameter;
   private final int radius;
   private final int diameter;
   private boolean started;

   public StoringChunkProgressListener(int var1) {
      this.delegate = new LoggerChunkProgressListener(â˜ƒ);
      this.fullDiameter = â˜ƒ * 2 + 1;
      this.radius = â˜ƒ + ChunkStatus.maxDistance();
      this.diameter = this.radius * 2 + 1;
      this.statuses = new Long2ObjectOpenHashMap<>();
   }

   @Override
   public void updateSpawnPos(ChunkPos var1) {
      if (this.started) {
         this.delegate.updateSpawnPos(â˜ƒ);
         this.spawnPos = â˜ƒ;
      }
   }

   @Override
   public void onStatusChange(ChunkPos var1, @Nullable ChunkStatus var2) {
      if (this.started) {
         this.delegate.onStatusChange(â˜ƒ, â˜ƒ);
         if (â˜ƒ == null) {
            this.statuses.remove(â˜ƒ.toLong());
         } else {
            this.statuses.put(â˜ƒ.toLong(), â˜ƒ);
         }
      }
   }

   @Override
   public void start() {
      this.started = true;
      this.statuses.clear();
   }

   @Override
   public void stop() {
      this.started = false;
      this.delegate.stop();
   }

   public int getFullDiameter() {
      return this.fullDiameter;
   }

   public int getDiameter() {
      return this.diameter;
   }

   public int getProgress() {
      return this.delegate.getProgress();
   }

   @Nullable
   public ChunkStatus getStatus(int var1, int var2) {
      return this.statuses.get(ChunkPos.asLong(â˜ƒ + this.spawnPos.x - this.radius, â˜ƒ + this.spawnPos.z - this.radius));
   }
}
