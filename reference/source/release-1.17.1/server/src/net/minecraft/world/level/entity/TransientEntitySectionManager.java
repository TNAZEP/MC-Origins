package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransientEntitySectionManager<T extends EntityAccess> {
   static final Logger LOGGER = LogManager.getLogger();
   final LevelCallback<T> callbacks;
   final EntityLookup<T> entityStorage;
   final EntitySectionStorage<T> sectionStorage;
   private final LongSet tickingChunks = new LongOpenHashSet();
   private final LevelEntityGetter<T> entityGetter;

   public TransientEntitySectionManager(Class<T> var1, LevelCallback<T> var2) {
      this.entityStorage = new EntityLookup<>();
      this.sectionStorage = new EntitySectionStorage<>(â˜ƒ, var1x -> this.tickingChunks.contains(var1x) ? Visibility.TICKING : Visibility.TRACKED);
      this.callbacks = â˜ƒ;
      this.entityGetter = new LevelEntityGetterAdapter<>(this.entityStorage, this.sectionStorage);
   }

   public void startTicking(ChunkPos var1) {
      long â˜ƒ = â˜ƒ.toLong();
      this.tickingChunks.add(â˜ƒ);
      this.sectionStorage.getExistingSectionsInChunk(â˜ƒ).forEach(var1x -> {
         Visibility â˜ƒ = var1x.updateChunkStatus(Visibility.TICKING);
         if (!â˜ƒ.isTicking()) {
            var1x.getEntities().filter(var0 -> !var0.isAlwaysTicking()).forEach(this.callbacks::onTickingStart);
         }
      });
   }

   public void stopTicking(ChunkPos var1) {
      long â˜ƒ = â˜ƒ.toLong();
      this.tickingChunks.remove(â˜ƒ);
      this.sectionStorage.getExistingSectionsInChunk(â˜ƒ).forEach(var1x -> {
         Visibility â˜ƒ = var1x.updateChunkStatus(Visibility.TRACKED);
         if (â˜ƒ.isTicking()) {
            var1x.getEntities().filter(var0 -> !var0.isAlwaysTicking()).forEach(this.callbacks::onTickingEnd);
         }
      });
   }

   public LevelEntityGetter<T> getEntityGetter() {
      return this.entityGetter;
   }

   public void addEntity(T var1) {
      this.entityStorage.add(â˜ƒ);
      long â˜ƒ = SectionPos.asLong(â˜ƒ.blockPosition());
      EntitySection<T> â˜ƒx = this.sectionStorage.getOrCreateSection(â˜ƒ);
      â˜ƒx.add(â˜ƒ);
      â˜ƒ.setLevelCallback(new TransientEntitySectionManager.Callback(â˜ƒ, â˜ƒ, â˜ƒx));
      this.callbacks.onCreated(â˜ƒ);
      this.callbacks.onTrackingStart(â˜ƒ);
      if (â˜ƒ.isAlwaysTicking() || â˜ƒx.getStatus().isTicking()) {
         this.callbacks.onTickingStart(â˜ƒ);
      }
   }

   @VisibleForDebug
   public int count() {
      return this.entityStorage.count();
   }

   void removeSectionIfEmpty(long var1, EntitySection<T> var3) {
      if (â˜ƒ.isEmpty()) {
         this.sectionStorage.remove(â˜ƒ);
      }
   }

   @VisibleForDebug
   public String gatherStats() {
      return this.entityStorage.count() + "," + this.sectionStorage.count() + "," + this.tickingChunks.size();
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
               TransientEntitySectionManager.LOGGER
                  .warn("Entity {} wasn't found in section {} (moving to {})", this.entity, SectionPos.of(this.currentSectionKey), â˜ƒx);
            }

            TransientEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
            EntitySection<T> â˜ƒxx = TransientEntitySectionManager.this.sectionStorage.getOrCreateSection(â˜ƒx);
            â˜ƒxx.add(this.entity);
            this.currentSection = â˜ƒxx;
            this.currentSectionKey = â˜ƒx;
            if (!this.entity.isAlwaysTicking()) {
               boolean â˜ƒxxx = â˜ƒxx.isTicking();
               boolean â˜ƒxxxx = â˜ƒxx.getStatus().isTicking();
               if (â˜ƒxxx && !â˜ƒxxxx) {
                  TransientEntitySectionManager.this.callbacks.onTickingEnd(this.entity);
               } else if (!â˜ƒxxx && â˜ƒxxxx) {
                  TransientEntitySectionManager.this.callbacks.onTickingStart(this.entity);
               }
            }
         }
      }

      @Override
      public void onRemove(Entity.RemovalReason var1) {
         if (!this.currentSection.remove(this.entity)) {
            TransientEntitySectionManager.LOGGER
               .warn("Entity {} wasn't found in section {} (destroying due to {})", this.entity, SectionPos.of(this.currentSectionKey), â˜ƒ);
         }

         Visibility â˜ƒ = this.currentSection.getStatus();
         if (â˜ƒ.isTicking() || this.entity.isAlwaysTicking()) {
            TransientEntitySectionManager.this.callbacks.onTickingEnd(this.entity);
         }

         TransientEntitySectionManager.this.callbacks.onTrackingEnd(this.entity);
         TransientEntitySectionManager.this.callbacks.onDestroyed(this.entity);
         TransientEntitySectionManager.this.entityStorage.remove(this.entity);
         this.entity.setLevelCallback(NULL);
         TransientEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
      }
   }
}
