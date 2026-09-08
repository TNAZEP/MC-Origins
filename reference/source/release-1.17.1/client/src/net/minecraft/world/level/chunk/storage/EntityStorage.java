package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.entity.ChunkEntities;
import net.minecraft.world.level.entity.EntityPersistentStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityStorage implements EntityPersistentStorage<Entity> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String ENTITIES_TAG = "Entities";
   private static final String POSITION_TAG = "Position";
   private final ServerLevel level;
   private final IOWorker worker;
   private final LongSet emptyChunks = new LongOpenHashSet();
   private final ProcessorMailbox<Runnable> entityDeserializerQueue;
   protected final DataFixer fixerUpper;

   public EntityStorage(ServerLevel var1, File var2, DataFixer var3, boolean var4, Executor var5) {
      this.level = â˜ƒ;
      this.fixerUpper = â˜ƒ;
      this.entityDeserializerQueue = ProcessorMailbox.create(â˜ƒ, "entity-deserializer");
      this.worker = new IOWorker(â˜ƒ, â˜ƒ, "entities");
   }

   @Override
   public CompletableFuture<ChunkEntities<Entity>> loadEntities(ChunkPos var1) {
      return this.emptyChunks.contains(â˜ƒ.toLong()) ? CompletableFuture.completedFuture(emptyChunk(â˜ƒ)) : this.worker.loadAsync(â˜ƒ).thenApplyAsync(var2 -> {
         if (var2 == null) {
            this.emptyChunks.add(â˜ƒ.toLong());
            return emptyChunk(â˜ƒ);
         } else {
            try {
               ChunkPos â˜ƒ = readChunkPos(var2);
               if (!Objects.equals(â˜ƒ, â˜ƒ)) {
                  LOGGER.error("Chunk file at {} is in the wrong location. (Expected {}, got {})", â˜ƒ, â˜ƒ, â˜ƒ);
               }
            } catch (Exception var6) {
               LOGGER.warn("Failed to parse chunk {} position info", â˜ƒ, var6);
            }

            CompoundTag â˜ƒ = this.upgradeChunkTag(var2);
            ListTag â˜ƒx = â˜ƒ.getList("Entities", 10);
            List<Entity> â˜ƒxx = (List)EntityType.loadEntitiesRecursive(â˜ƒx, this.level).collect(ImmutableList.toImmutableList());
            return new ChunkEntities(â˜ƒ, â˜ƒxx);
         }
      }, this.entityDeserializerQueue::tell);
   }

   private static ChunkPos readChunkPos(CompoundTag var0) {
      int[] â˜ƒ = â˜ƒ.getIntArray("Position");
      return new ChunkPos(â˜ƒ[0], â˜ƒ[1]);
   }

   private static void writeChunkPos(CompoundTag var0, ChunkPos var1) {
      â˜ƒ.put("Position", new IntArrayTag(new int[]{â˜ƒ.x, â˜ƒ.z}));
   }

   private static ChunkEntities<Entity> emptyChunk(ChunkPos var0) {
      return new ChunkEntities<>(â˜ƒ, ImmutableList.of());
   }

   @Override
   public void storeEntities(ChunkEntities<Entity> var1) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      if (â˜ƒ.isEmpty()) {
         if (this.emptyChunks.add(â˜ƒ.toLong())) {
            this.worker.store(â˜ƒ, null);
         }
      } else {
         ListTag â˜ƒ = new ListTag();
         â˜ƒ.getEntities().forEach(var1x -> {
            CompoundTag â˜ƒ = new CompoundTag();
            if (var1x.save(â˜ƒ)) {
               â˜ƒ.add(â˜ƒ);
            }
         });
         CompoundTag â˜ƒx = new CompoundTag();
         â˜ƒx.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());
         â˜ƒx.put("Entities", â˜ƒ);
         writeChunkPos(â˜ƒx, â˜ƒ);
         this.worker.store(â˜ƒ, â˜ƒx).exceptionally(var1x -> {
            LOGGER.error("Failed to store chunk {}", â˜ƒ, var1x);
            return null;
         });
         this.emptyChunks.remove(â˜ƒ.toLong());
      }
   }

   @Override
   public void flush(boolean var1) {
      this.worker.synchronize(â˜ƒ).join();
      this.entityDeserializerQueue.runAll();
   }

   private CompoundTag upgradeChunkTag(CompoundTag var1) {
      int â˜ƒ = getVersion(â˜ƒ);
      return NbtUtils.update(this.fixerUpper, DataFixTypes.ENTITY_CHUNK, â˜ƒ, â˜ƒ);
   }

   public static int getVersion(CompoundTag var0) {
      return â˜ƒ.contains("DataVersion", 99) ? â˜ƒ.getInt("DataVersion") : -1;
   }

   @Override
   public void close() throws IOException {
      this.worker.close();
   }
}
