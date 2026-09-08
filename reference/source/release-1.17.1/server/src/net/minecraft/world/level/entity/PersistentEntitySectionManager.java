package net.minecraft.world.level.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersistentEntitySectionManager<T extends EntityAccess> implements AutoCloseable {
   static final Logger LOGGER = LogManager.getLogger();
   final Set<UUID> knownUuids = Sets.newHashSet();
   final LevelCallback<T> callbacks;
   private final EntityPersistentStorage<T> permanentStorage;
   private final EntityLookup<T> visibleEntityStorage;
   final EntitySectionStorage<T> sectionStorage;
   private final LevelEntityGetter<T> entityGetter;
   private final Long2ObjectMap<Visibility> chunkVisibility = new Long2ObjectOpenHashMap();
   private final Long2ObjectMap<PersistentEntitySectionManager.ChunkLoadStatus> chunkLoadStatuses = new Long2ObjectOpenHashMap();
   private final LongSet chunksToUnload = new LongOpenHashSet();
   private final Queue<ChunkEntities<T>> loadingInbox = Queues.<ChunkEntities<T>>newConcurrentLinkedQueue();

   public PersistentEntitySectionManager(Class<T> var1, LevelCallback<T> var2, EntityPersistentStorage<T> var3) {
      this.visibleEntityStorage = new EntityLookup<>();
      this.sectionStorage = new EntitySectionStorage<>(â˜ƒ, this.chunkVisibility);
      this.chunkVisibility.defaultReturnValue(Visibility.HIDDEN);
      this.chunkLoadStatuses.defaultReturnValue(PersistentEntitySectionManager.ChunkLoadStatus.FRESH);
      this.callbacks = â˜ƒ;
      this.permanentStorage = â˜ƒ;
      this.entityGetter = new LevelEntityGetterAdapter<>(this.visibleEntityStorage, this.sectionStorage);
   }

   void removeSectionIfEmpty(long var1, EntitySection<T> var3) {
      if (â˜ƒ.isEmpty()) {
         this.sectionStorage.remove(â˜ƒ);
      }
   }

   private boolean addEntityUuid(T var1) {
      if (!this.knownUuids.add(â˜ƒ.getUUID())) {
         LOGGER.warn("UUID of added entity already exists: {}", â˜ƒ);
         return false;
      } else {
         return true;
      }
   }

   public boolean addNewEntity(T var1) {
      return this.addEntity(â˜ƒ, false);
   }

   private boolean addEntity(T var1, boolean var2) {
      if (!this.addEntityUuid(â˜ƒ)) {
         return false;
      } else {
         long â˜ƒ = SectionPos.asLong(â˜ƒ.blockPosition());
         EntitySection<T> â˜ƒx = this.sectionStorage.getOrCreateSection(â˜ƒ);
         â˜ƒx.add(â˜ƒ);
         â˜ƒ.setLevelCallback(new PersistentEntitySectionManager.Callback(â˜ƒ, â˜ƒ, â˜ƒx));
         if (!â˜ƒ) {
            this.callbacks.onCreated(â˜ƒ);
         }

         Visibility â˜ƒ = getEffectiveStatus(â˜ƒ, â˜ƒx.getStatus());
         if (â˜ƒ.isAccessible()) {
            this.startTracking(â˜ƒ);
         }

         if (â˜ƒ.isTicking()) {
            this.startTicking(â˜ƒ);
         }

         return true;
      }
   }

   static <T extends EntityAccess> Visibility getEffectiveStatus(T var0, Visibility var1) {
      return â˜ƒ.isAlwaysTicking() ? Visibility.TICKING : â˜ƒ;
   }

   public void addLegacyChunkEntities(Stream<T> var1) {
      â˜ƒ.forEach(var1x -> this.addEntity((T)var1x, true));
   }

   public void addWorldGenChunkEntities(Stream<T> var1) {
      â˜ƒ.forEach(var1x -> this.addEntity((T)var1x, false));
   }

   void startTicking(T var1) {
      this.callbacks.onTickingStart(â˜ƒ);
   }

   void stopTicking(T var1) {
      this.callbacks.onTickingEnd(â˜ƒ);
   }

   void startTracking(T var1) {
      this.visibleEntityStorage.add(â˜ƒ);
      this.callbacks.onTrackingStart(â˜ƒ);
   }

   void stopTracking(T var1) {
      this.callbacks.onTrackingEnd(â˜ƒ);
      this.visibleEntityStorage.remove(â˜ƒ);
   }

   public void updateChunkStatus(ChunkPos var1, ChunkHolder.FullChunkStatus var2) {
      Visibility â˜ƒ = Visibility.fromFullChunkStatus(â˜ƒ);
      this.updateChunkStatus(â˜ƒ, â˜ƒ);
   }

   public void updateChunkStatus(ChunkPos var1, Visibility var2) {
      long â˜ƒ = â˜ƒ.toLong();
      if (â˜ƒ == Visibility.HIDDEN) {
         this.chunkVisibility.remove(â˜ƒ);
         this.chunksToUnload.add(â˜ƒ);
      } else {
         this.chunkVisibility.put(â˜ƒ, â˜ƒ);
         this.chunksToUnload.remove(â˜ƒ);
         this.ensureChunkQueuedForLoad(â˜ƒ);
      }

      this.sectionStorage.getExistingSectionsInChunk(â˜ƒ).forEach(var2x -> {
         Visibility â˜ƒ = var2x.updateChunkStatus(â˜ƒ);
         boolean â˜ƒx = â˜ƒ.isAccessible();
         boolean â˜ƒxx = â˜ƒ.isAccessible();
         boolean â˜ƒxxx = â˜ƒ.isTicking();
         boolean â˜ƒxxxx = â˜ƒ.isTicking();
         if (â˜ƒxxx && !â˜ƒxxxx) {
            var2x.getEntities().filter(var0 -> !var0.isAlwaysTicking()).forEach(this::stopTicking);
         }

         if (â˜ƒx && !â˜ƒxx) {
            var2x.getEntities().filter(var0 -> !var0.isAlwaysTicking()).forEach(this::stopTracking);
         } else if (!â˜ƒx && â˜ƒxx) {
            var2x.getEntities().filter(var0 -> !var0.isAlwaysTicking()).forEach(this::startTracking);
         }

         if (!â˜ƒxxx && â˜ƒxxxx) {
            var2x.getEntities().filter(var0 -> !var0.isAlwaysTicking()).forEach(this::startTicking);
         }
      });
   }

   private void ensureChunkQueuedForLoad(long var1) {
      PersistentEntitySectionManager.ChunkLoadStatus â˜ƒ = (PersistentEntitySectionManager.ChunkLoadStatus)this.chunkLoadStatuses.get(â˜ƒ);
      if (â˜ƒ == PersistentEntitySectionManager.ChunkLoadStatus.FRESH) {
         this.requestChunkLoad(â˜ƒ);
      }
   }

   private boolean storeChunkSections(long var1, Consumer<T> var3) {
      PersistentEntitySectionManager.ChunkLoadStatus â˜ƒ = (PersistentEntitySectionManager.ChunkLoadStatus)this.chunkLoadStatuses.get(â˜ƒ);
      if (â˜ƒ == PersistentEntitySectionManager.ChunkLoadStatus.PENDING) {
         return false;
      } else {
         List<T> â˜ƒ = (List)this.sectionStorage
            .getExistingSectionsInChunk(â˜ƒ)
            .flatMap(var0 -> var0.getEntities().filter(EntityAccess::shouldBeSaved))
            .collect(Collectors.toList());
         if (â˜ƒ.isEmpty()) {
            if (â˜ƒ == PersistentEntitySectionManager.ChunkLoadStatus.LOADED) {
               this.permanentStorage.storeEntities(new ChunkEntities<>(new ChunkPos(â˜ƒ), ImmutableList.of()));
            }

            return true;
         } else if (â˜ƒ == PersistentEntitySectionManager.ChunkLoadStatus.FRESH) {
            this.requestChunkLoad(â˜ƒ);
            return false;
         } else {
            this.permanentStorage.storeEntities(new ChunkEntities<>(new ChunkPos(â˜ƒ), â˜ƒ));
            â˜ƒ.forEach(â˜ƒ);
            return true;
         }
      }
   }

   private void requestChunkLoad(long var1) {
      this.chunkLoadStatuses.put(â˜ƒ, PersistentEntitySectionManager.ChunkLoadStatus.PENDING);
      ChunkPos â˜ƒ = new ChunkPos(â˜ƒ);
      this.permanentStorage.loadEntities(â˜ƒ).thenAccept(this.loadingInbox::add).exceptionally(var1x -> {
         LOGGER.error("Failed to read chunk {}", â˜ƒ, var1x);
         return null;
      });
   }

   private boolean processChunkUnload(long var1) {
      boolean â˜ƒ = this.storeChunkSections(â˜ƒ, var1x -> var1x.getPassengersAndSelf().forEach(this::unloadEntity));
      if (!â˜ƒ) {
         return false;
      } else {
         this.chunkLoadStatuses.remove(â˜ƒ);
         return true;
      }
   }

   private void unloadEntity(EntityAccess var1) {
      â˜ƒ.setRemoved(Entity.RemovalReason.UNLOADED_TO_CHUNK);
      â˜ƒ.setLevelCallback(EntityInLevelCallback.NULL);
   }

   private void processUnloads() {
      this.chunksToUnload.removeIf(var1 -> this.chunkVisibility.get(var1) != Visibility.HIDDEN ? true : this.processChunkUnload(var1));
   }

   private void processPendingLoads() {
      ChunkEntities<T> â˜ƒ;
      while((â˜ƒ = (ChunkEntities)this.loadingInbox.poll()) != null) {
         â˜ƒ.getEntities().forEach(var1x -> this.addEntity((T)var1x, true));
         this.chunkLoadStatuses.put(â˜ƒ.getPos().toLong(), PersistentEntitySectionManager.ChunkLoadStatus.LOADED);
      }
   }

   public void tick() {
      this.processPendingLoads();
      this.processUnloads();
   }

   private LongSet getAllChunksToSave() {
      LongSet â˜ƒ = this.sectionStorage.getAllChunksWithExistingSections();

      for(Entry<PersistentEntitySectionManager.ChunkLoadStatus> â˜ƒx : Long2ObjectMaps.fastIterable(this.chunkLoadStatuses)) {
         if (â˜ƒx.getValue() == PersistentEntitySectionManager.ChunkLoadStatus.LOADED) {
            â˜ƒ.add(â˜ƒx.getLongKey());
         }
      }

      return â˜ƒ;
   }

   public void autoSave() {
      this.getAllChunksToSave().forEach(var1 -> {
         boolean â˜ƒ = this.chunkVisibility.get(var1) == Visibility.HIDDEN;
         if (â˜ƒ) {
            this.processChunkUnload(var1);
         } else {
            this.storeChunkSections(var1, var0 -> {
            });
         }
      });
   }

   public void saveAll() {
      LongSet â˜ƒ = this.getAllChunksToSave();

      while(!â˜ƒ.isEmpty()) {
         this.permanentStorage.flush(false);
         this.processPendingLoads();
         â˜ƒ.removeIf(var1x -> {
            boolean â˜ƒ = this.chunkVisibility.get(var1x) == Visibility.HIDDEN;
            return â˜ƒ ? this.processChunkUnload(var1x) : this.storeChunkSections(var1x, var0 -> {
            });
         });
      }

      this.permanentStorage.flush(true);
   }

   public void close() throws IOException {
      this.saveAll();
      this.permanentStorage.close();
   }

   public boolean isLoaded(UUID var1) {
      return this.knownUuids.contains(â˜ƒ);
   }

   public LevelEntityGetter<T> getEntityGetter() {
      return this.entityGetter;
   }

   public boolean isPositionTicking(BlockPos var1) {
      return ((Visibility)this.chunkVisibility.get(ChunkPos.asLong(â˜ƒ))).isTicking();
   }

   public boolean isPositionTicking(ChunkPos var1) {
      return ((Visibility)this.chunkVisibility.get(â˜ƒ.toLong())).isTicking();
   }

   public boolean areEntitiesLoaded(long var1) {
      return this.chunkLoadStatuses.get(â˜ƒ) == PersistentEntitySectionManager.ChunkLoadStatus.LOADED;
   }

   public void dumpSections(Writer var1) throws IOException {
      CsvOutput â˜ƒ = CsvOutput.builder()
         .addColumn("x")
         .addColumn("y")
         .addColumn("z")
         .addColumn("visibility")
         .addColumn("load_status")
         .addColumn("entity_count")
         .build(â˜ƒ);
      this.sectionStorage.getAllChunksWithExistingSections().forEach(var2x -> {
         PersistentEntitySectionManager.ChunkLoadStatus â˜ƒ = (PersistentEntitySectionManager.ChunkLoadStatus)this.chunkLoadStatuses.get(var2x);
         this.sectionStorage.getExistingSectionPositionsInChunk(var2x).forEach(var3 -> {
            EntitySection<T> â˜ƒ = this.sectionStorage.getSection(var3);
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.writeRow(SectionPos.x(var3), SectionPos.y(var3), SectionPos.z(var3), â˜ƒ.getStatus(), â˜ƒ, â˜ƒ.size());
               } catch (IOException var7) {
                  throw new UncheckedIOException(var7);
               }
            }
         });
      });
   }

   @VisibleForDebug
   public String gatherStats() {
      return this.knownUuids.size()
         + ","
         + this.visibleEntityStorage.count()
         + ","
         + this.sectionStorage.count()
         + ","
         + this.chunkLoadStatuses.size()
         + ","
         + this.chunkVisibility.size()
         + ","
         + this.loadingInbox.size()
         + ","
         + this.chunksToUnload.size();
   }

   class Callback implements EntityInLevelCallback {
      private final T entity;
      private long currentSectionKey;
      private EntitySection<T> currentSection;

      Callback(T var2, long var3, EntitySection<T> var5) {
         this.entity = â˜ƒ;
         this.currentSectionKey = â˜ƒ;
         this.currentSection = â˜ƒ;
      }

      @Override
      public void onMove() {
         BlockPos â˜ƒ = this.entity.blockPosition();
         long â˜ƒx = SectionPos.asLong(â˜ƒ);
         if (â˜ƒx != this.currentSectionKey) {
            Visibility â˜ƒxx = this.currentSection.getStatus();
            if (!this.currentSection.remove(this.entity)) {
               PersistentEntitySectionManager.LOGGER
                  .warn("Entity {} wasn't found in section {} (moving to {})", this.entity, SectionPos.of(this.currentSectionKey), â˜ƒx);
            }

            PersistentEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
            EntitySection<T> â˜ƒxx = PersistentEntitySectionManager.this.sectionStorage.getOrCreateSection(â˜ƒx);
            â˜ƒxx.add(this.entity);
            this.currentSection = â˜ƒxx;
            this.currentSectionKey = â˜ƒx;
            this.updateStatus(â˜ƒxx, â˜ƒxx.getStatus());
         }
      }

      private void updateStatus(Visibility var1, Visibility var2) {
         Visibility â˜ƒ = PersistentEntitySectionManager.getEffectiveStatus(this.entity, â˜ƒ);
         Visibility â˜ƒx = PersistentEntitySectionManager.getEffectiveStatus(this.entity, â˜ƒ);
         if (â˜ƒ != â˜ƒx) {
            boolean â˜ƒxx = â˜ƒ.isAccessible();
            boolean â˜ƒxxx = â˜ƒx.isAccessible();
            if (â˜ƒxx && !â˜ƒxxx) {
               PersistentEntitySectionManager.this.stopTracking(this.entity);
            } else if (!â˜ƒxx && â˜ƒxxx) {
               PersistentEntitySectionManager.this.startTracking(this.entity);
            }

            boolean â˜ƒxx = â˜ƒ.isTicking();
            boolean â˜ƒxxx = â˜ƒx.isTicking();
            if (â˜ƒxx && !â˜ƒxxx) {
               PersistentEntitySectionManager.this.stopTicking(this.entity);
            } else if (!â˜ƒxx && â˜ƒxxx) {
               PersistentEntitySectionManager.this.startTicking(this.entity);
            }
         }
      }

      @Override
      public void onRemove(Entity.RemovalReason var1) {
         if (!this.currentSection.remove(this.entity)) {
            PersistentEntitySectionManager.LOGGER
               .warn("Entity {} wasn't found in section {} (destroying due to {})", this.entity, SectionPos.of(this.currentSectionKey), â˜ƒ);
         }

         Visibility â˜ƒ = PersistentEntitySectionManager.getEffectiveStatus(this.entity, this.currentSection.getStatus());
         if (â˜ƒ.isTicking()) {
            PersistentEntitySectionManager.this.stopTicking(this.entity);
         }

         if (â˜ƒ.isAccessible()) {
            PersistentEntitySectionManager.this.stopTracking(this.entity);
         }

         if (â˜ƒ.shouldDestroy()) {
            PersistentEntitySectionManager.this.callbacks.onDestroyed(this.entity);
         }

         PersistentEntitySectionManager.this.knownUuids.remove(this.entity.getUUID());
         this.entity.setLevelCallback(NULL);
         PersistentEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
      }
   }

   static enum ChunkLoadStatus {
      FRESH,
      PENDING,
      LOADED;
   }
}
