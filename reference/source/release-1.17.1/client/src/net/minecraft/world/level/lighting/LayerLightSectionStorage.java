package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.SectionTracker;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;

public abstract class LayerLightSectionStorage<M extends DataLayerStorageMap<M>> extends SectionTracker {
   protected static final int LIGHT_AND_DATA = 0;
   protected static final int LIGHT_ONLY = 1;
   protected static final int EMPTY = 2;
   protected static final DataLayer EMPTY_DATA = new DataLayer();
   private static final Direction[] DIRECTIONS = Direction.values();
   private final LightLayer layer;
   private final LightChunkGetter chunkSource;
   protected final LongSet dataSectionSet = new LongOpenHashSet();
   protected final LongSet toMarkNoData = new LongOpenHashSet();
   protected final LongSet toMarkData = new LongOpenHashSet();
   protected volatile M visibleSectionData;
   protected final M updatingSectionData;
   protected final LongSet changedSections = new LongOpenHashSet();
   protected final LongSet sectionsAffectedByLightUpdates = new LongOpenHashSet();
   protected final Long2ObjectMap<DataLayer> queuedSections = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());
   private final LongSet untrustedSections = new LongOpenHashSet();
   private final LongSet columnsToRetainQueuedDataFor = new LongOpenHashSet();
   private final LongSet toRemove = new LongOpenHashSet();
   protected volatile boolean hasToRemove;

   protected LayerLightSectionStorage(LightLayer var1, LightChunkGetter var2, M var3) {
      super(3, 16, 256);
      this.layer = â˜ƒ;
      this.chunkSource = â˜ƒ;
      this.updatingSectionData = â˜ƒ;
      this.visibleSectionData = â˜ƒ.copy();
      this.visibleSectionData.disableCache();
   }

   protected boolean storingLightForSection(long var1) {
      return this.getDataLayer(â˜ƒ, true) != null;
   }

   @Nullable
   protected DataLayer getDataLayer(long var1, boolean var3) {
      return this.getDataLayer((M)(â˜ƒ ? this.updatingSectionData : this.visibleSectionData), â˜ƒ);
   }

   @Nullable
   protected DataLayer getDataLayer(M var1, long var2) {
      return â˜ƒ.getLayer(â˜ƒ);
   }

   @Nullable
   public DataLayer getDataLayerData(long var1) {
      DataLayer â˜ƒ = this.queuedSections.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ : this.getDataLayer(â˜ƒ, false);
   }

   protected abstract int getLightValue(long var1);

   protected int getStoredLevel(long var1) {
      long â˜ƒ = SectionPos.blockToSection(â˜ƒ);
      DataLayer â˜ƒx = this.getDataLayer(â˜ƒ, true);
      return â˜ƒx.get(
         SectionPos.sectionRelative(BlockPos.getX(â˜ƒ)), SectionPos.sectionRelative(BlockPos.getY(â˜ƒ)), SectionPos.sectionRelative(BlockPos.getZ(â˜ƒ))
      );
   }

   protected void setStoredLevel(long var1, int var3) {
      long â˜ƒ = SectionPos.blockToSection(â˜ƒ);
      if (this.changedSections.add(â˜ƒ)) {
         this.updatingSectionData.copyDataLayer(â˜ƒ);
      }

      DataLayer â˜ƒ = this.getDataLayer(â˜ƒ, true);
      â˜ƒ.set(
         SectionPos.sectionRelative(BlockPos.getX(â˜ƒ)), SectionPos.sectionRelative(BlockPos.getY(â˜ƒ)), SectionPos.sectionRelative(BlockPos.getZ(â˜ƒ)), â˜ƒ
      );

      for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
         for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
            for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
               this.sectionsAffectedByLightUpdates.add(SectionPos.blockToSection(BlockPos.offset(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒx)));
            }
         }
      }
   }

   @Override
   protected int getLevel(long var1) {
      if (â˜ƒ == Long.MAX_VALUE) {
         return 2;
      } else if (this.dataSectionSet.contains(â˜ƒ)) {
         return 0;
      } else {
         return !this.toRemove.contains(â˜ƒ) && this.updatingSectionData.hasLayer(â˜ƒ) ? 1 : 2;
      }
   }

   @Override
   protected int getLevelFromSource(long var1) {
      if (this.toMarkNoData.contains(â˜ƒ)) {
         return 2;
      } else {
         return !this.dataSectionSet.contains(â˜ƒ) && !this.toMarkData.contains(â˜ƒ) ? 2 : 0;
      }
   }

   @Override
   protected void setLevel(long var1, int var3) {
      int â˜ƒ = this.getLevel(â˜ƒ);
      if (â˜ƒ != 0 && â˜ƒ == 0) {
         this.dataSectionSet.add(â˜ƒ);
         this.toMarkData.remove(â˜ƒ);
      }

      if (â˜ƒ == 0 && â˜ƒ != 0) {
         this.dataSectionSet.remove(â˜ƒ);
         this.toMarkNoData.remove(â˜ƒ);
      }

      if (â˜ƒ >= 2 && â˜ƒ != 2) {
         if (this.toRemove.contains(â˜ƒ)) {
            this.toRemove.remove(â˜ƒ);
         } else {
            this.updatingSectionData.setLayer(â˜ƒ, this.createDataLayer(â˜ƒ));
            this.changedSections.add(â˜ƒ);
            this.onNodeAdded(â˜ƒ);

            for(int â˜ƒ = -1; â˜ƒ <= 1; ++â˜ƒ) {
               for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
                  for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
                     this.sectionsAffectedByLightUpdates.add(SectionPos.blockToSection(BlockPos.offset(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ)));
                  }
               }
            }
         }
      }

      if (â˜ƒ != 2 && â˜ƒ >= 2) {
         this.toRemove.add(â˜ƒ);
      }

      this.hasToRemove = !this.toRemove.isEmpty();
   }

   protected DataLayer createDataLayer(long var1) {
      DataLayer â˜ƒ = this.queuedSections.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ : new DataLayer();
   }

   protected void clearQueuedSectionBlocks(LayerLightEngine<?, ?> var1, long var2) {
      if (â˜ƒ.getQueueSize() < 8192) {
         â˜ƒ.removeIf(var2x -> SectionPos.blockToSection(var2x) == â˜ƒ);
      } else {
         int â˜ƒ = SectionPos.sectionToBlockCoord(SectionPos.x(â˜ƒ));
         int â˜ƒx = SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒ));
         int â˜ƒxx = SectionPos.sectionToBlockCoord(SectionPos.z(â˜ƒ));

         for(int â˜ƒxxx = 0; â˜ƒxxx < 16; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16; ++â˜ƒxxxx) {
               for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16; ++â˜ƒxxxxx) {
                  long â˜ƒxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxx, â˜ƒx + â˜ƒxxxx, â˜ƒxx + â˜ƒxxxxx);
                  â˜ƒ.removeFromQueue(â˜ƒxxxxxx);
               }
            }
         }
      }
   }

   protected boolean hasInconsistencies() {
      return this.hasToRemove;
   }

   protected void markNewInconsistencies(LayerLightEngine<M, ?> var1, boolean var2, boolean var3) {
      if (this.hasInconsistencies() || !this.queuedSections.isEmpty()) {
         LongIterator var4 = this.toRemove.iterator();

         while(var4.hasNext()) {
            long â˜ƒ = var4.next();
            this.clearQueuedSectionBlocks(â˜ƒ, â˜ƒ);
            DataLayer â˜ƒx = this.queuedSections.remove(â˜ƒ);
            DataLayer â˜ƒxx = this.updatingSectionData.removeLayer(â˜ƒ);
            if (this.columnsToRetainQueuedDataFor.contains(SectionPos.getZeroNode(â˜ƒ))) {
               if (â˜ƒx != null) {
                  this.queuedSections.put(â˜ƒ, â˜ƒx);
               } else if (â˜ƒxx != null) {
                  this.queuedSections.put(â˜ƒ, â˜ƒxx);
               }
            }
         }

         this.updatingSectionData.clearCache();
         var4 = this.toRemove.iterator();

         while(var4.hasNext()) {
            long â˜ƒ = var4.next();
            this.onNodeRemoved(â˜ƒ);
         }

         this.toRemove.clear();
         this.hasToRemove = false;

         for(Entry<DataLayer> â˜ƒ : this.queuedSections.long2ObjectEntrySet()) {
            long â˜ƒx = â˜ƒ.getLongKey();
            if (this.storingLightForSection(â˜ƒx)) {
               DataLayer â˜ƒxx = (DataLayer)â˜ƒ.getValue();
               if (this.updatingSectionData.getLayer(â˜ƒx) != â˜ƒxx) {
                  this.clearQueuedSectionBlocks(â˜ƒ, â˜ƒx);
                  this.updatingSectionData.setLayer(â˜ƒx, â˜ƒxx);
                  this.changedSections.add(â˜ƒx);
               }
            }
         }

         this.updatingSectionData.clearCache();
         if (!â˜ƒ) {
            var4 = this.queuedSections.keySet().iterator();

            while(var4.hasNext()) {
               long â˜ƒ = var4.next();
               this.checkEdgesForSection(â˜ƒ, â˜ƒ);
            }
         } else {
            var4 = this.untrustedSections.iterator();

            while(var4.hasNext()) {
               long â˜ƒ = var4.next();
               this.checkEdgesForSection(â˜ƒ, â˜ƒ);
            }
         }

         this.untrustedSections.clear();
         ObjectIterator<Entry<DataLayer>> â˜ƒ = this.queuedSections.long2ObjectEntrySet().iterator();

         while(â˜ƒ.hasNext()) {
            Entry<DataLayer> â˜ƒx = (Entry)â˜ƒ.next();
            long â˜ƒxx = â˜ƒx.getLongKey();
            if (this.storingLightForSection(â˜ƒxx)) {
               â˜ƒ.remove();
            }
         }
      }
   }

   private void checkEdgesForSection(LayerLightEngine<M, ?> var1, long var2) {
      if (this.storingLightForSection(â˜ƒ)) {
         int â˜ƒ = SectionPos.sectionToBlockCoord(SectionPos.x(â˜ƒ));
         int â˜ƒx = SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒ));
         int â˜ƒxx = SectionPos.sectionToBlockCoord(SectionPos.z(â˜ƒ));

         for(Direction â˜ƒxxx : DIRECTIONS) {
            long â˜ƒxxxx = SectionPos.offset(â˜ƒ, â˜ƒxxx);
            if (!this.queuedSections.containsKey(â˜ƒxxxx) && this.storingLightForSection(â˜ƒxxxx)) {
               for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16; ++â˜ƒxxxxx) {
                  for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 16; ++â˜ƒxxxxxx) {
                     long â˜ƒxxxxxxxx;
                     long â˜ƒxxxxxxx;
                     switch(â˜ƒxxx) {
                        case DOWN:
                           â˜ƒxxxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxxxxx, â˜ƒx, â˜ƒxx + â˜ƒxxxxx);
                           â˜ƒxxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxxxxx, â˜ƒx - 1, â˜ƒxx + â˜ƒxxxxx);
                           break;
                        case UP:
                           â˜ƒxxxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxxxxx, â˜ƒx + 16 - 1, â˜ƒxx + â˜ƒxxxxx);
                           â˜ƒxxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxxxxx, â˜ƒx + 16, â˜ƒxx + â˜ƒxxxxx);
                           break;
                        case NORTH:
                           â˜ƒxxxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxxxx, â˜ƒx + â˜ƒxxxxxx, â˜ƒxx);
                           â˜ƒxxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxxxx, â˜ƒx + â˜ƒxxxxxx, â˜ƒxx - 1);
                           break;
                        case SOUTH:
                           â˜ƒxxxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxxxx, â˜ƒx + â˜ƒxxxxxx, â˜ƒxx + 16 - 1);
                           â˜ƒxxxxxxx = BlockPos.asLong(â˜ƒ + â˜ƒxxxxx, â˜ƒx + â˜ƒxxxxxx, â˜ƒxx + 16);
                           break;
                        case WEST:
                           â˜ƒxxxxxxxx = BlockPos.asLong(â˜ƒ, â˜ƒx + â˜ƒxxxxx, â˜ƒxx + â˜ƒxxxxxx);
                           â˜ƒxxxxxxx = BlockPos.asLong(â˜ƒ - 1, â˜ƒx + â˜ƒxxxxx, â˜ƒxx + â˜ƒxxxxxx);
                           break;
                        default:
                           â˜ƒxxxxxxxx = BlockPos.asLong(â˜ƒ + 16 - 1, â˜ƒx + â˜ƒxxxxx, â˜ƒxx + â˜ƒxxxxxx);
                           â˜ƒxxxxxxx = BlockPos.asLong(â˜ƒ + 16, â˜ƒx + â˜ƒxxxxx, â˜ƒxx + â˜ƒxxxxxx);
                     }

                     â˜ƒ.checkEdge(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒ.computeLevelFromNeighbor(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒ.getLevel(â˜ƒxxxxxxxx)), false);
                     â˜ƒ.checkEdge(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒ.computeLevelFromNeighbor(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒ.getLevel(â˜ƒxxxxxxx)), false);
                  }
               }
            }
         }
      }
   }

   protected void onNodeAdded(long var1) {
   }

   protected void onNodeRemoved(long var1) {
   }

   protected void enableLightSources(long var1, boolean var3) {
   }

   public void retainData(long var1, boolean var3) {
      if (â˜ƒ) {
         this.columnsToRetainQueuedDataFor.add(â˜ƒ);
      } else {
         this.columnsToRetainQueuedDataFor.remove(â˜ƒ);
      }
   }

   protected void queueSectionData(long var1, @Nullable DataLayer var3, boolean var4) {
      if (â˜ƒ != null) {
         this.queuedSections.put(â˜ƒ, â˜ƒ);
         if (!â˜ƒ) {
            this.untrustedSections.add(â˜ƒ);
         }
      } else {
         this.queuedSections.remove(â˜ƒ);
      }
   }

   protected void updateSectionStatus(long var1, boolean var3) {
      boolean â˜ƒ = this.dataSectionSet.contains(â˜ƒ);
      if (!â˜ƒ && !â˜ƒ) {
         this.toMarkData.add(â˜ƒ);
         this.checkEdge(Long.MAX_VALUE, â˜ƒ, 0, true);
      }

      if (â˜ƒ && â˜ƒ) {
         this.toMarkNoData.add(â˜ƒ);
         this.checkEdge(Long.MAX_VALUE, â˜ƒ, 2, false);
      }
   }

   protected void runAllUpdates() {
      if (this.hasWork()) {
         this.runUpdates(Integer.MAX_VALUE);
      }
   }

   protected void swapSectionMap() {
      if (!this.changedSections.isEmpty()) {
         M â˜ƒ = this.updatingSectionData.copy();
         â˜ƒ.disableCache();
         this.visibleSectionData = â˜ƒ;
         this.changedSections.clear();
      }

      if (!this.sectionsAffectedByLightUpdates.isEmpty()) {
         LongIterator â˜ƒ = this.sectionsAffectedByLightUpdates.iterator();

         while(â˜ƒ.hasNext()) {
            long â˜ƒx = â˜ƒ.nextLong();
            this.chunkSource.onLightUpdate(this.layer, SectionPos.of(â˜ƒx));
         }

         this.sectionsAffectedByLightUpdates.clear();
      }
   }
}
