package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;

public class SkyLightSectionStorage extends LayerLightSectionStorage<SkyLightSectionStorage.SkyDataLayerStorageMap> {
   private static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
   private final LongSet sectionsWithSources = new LongOpenHashSet();
   private final LongSet sectionsToAddSourcesTo = new LongOpenHashSet();
   private final LongSet sectionsToRemoveSourcesFrom = new LongOpenHashSet();
   private final LongSet columnsWithSkySources = new LongOpenHashSet();
   private volatile boolean hasSourceInconsistencies;

   protected SkyLightSectionStorage(LightChunkGetter var1) {
      super(
         LightLayer.SKY, â˜ƒ, new SkyLightSectionStorage.SkyDataLayerStorageMap(new Long2ObjectOpenHashMap<>(), new Long2IntOpenHashMap(), Integer.MAX_VALUE)
      );
   }

   @Override
   protected int getLightValue(long var1) {
      return this.getLightValue(â˜ƒ, false);
   }

   protected int getLightValue(long var1, boolean var3) {
      long â˜ƒ = SectionPos.blockToSection(â˜ƒ);
      int â˜ƒx = SectionPos.y(â˜ƒ);
      SkyLightSectionStorage.SkyDataLayerStorageMap â˜ƒxx = â˜ƒ ? this.updatingSectionData : this.visibleSectionData;
      int â˜ƒxxx = â˜ƒxx.topSections.get(SectionPos.getZeroNode(â˜ƒ));
      if (â˜ƒxxx != â˜ƒxx.currentLowestY && â˜ƒx < â˜ƒxxx) {
         DataLayer â˜ƒxxxx = this.getDataLayer(â˜ƒxx, â˜ƒ);
         if (â˜ƒxxxx == null) {
            for(â˜ƒ = BlockPos.getFlatIndex(â˜ƒ); â˜ƒxxxx == null; â˜ƒxxxx = this.getDataLayer(â˜ƒxx, â˜ƒ)) {
               if (++â˜ƒx >= â˜ƒxxx) {
                  return 15;
               }

               â˜ƒ = BlockPos.offset(â˜ƒ, 0, 16, 0);
               â˜ƒ = SectionPos.offset(â˜ƒ, Direction.UP);
            }
         }

         return â˜ƒxxxx.get(
            SectionPos.sectionRelative(BlockPos.getX(â˜ƒ)), SectionPos.sectionRelative(BlockPos.getY(â˜ƒ)), SectionPos.sectionRelative(BlockPos.getZ(â˜ƒ))
         );
      } else {
         return â˜ƒ && !this.lightOnInSection(â˜ƒ) ? 0 : 15;
      }
   }

   @Override
   protected void onNodeAdded(long var1) {
      int â˜ƒ = SectionPos.y(â˜ƒ);
      if (this.updatingSectionData.currentLowestY > â˜ƒ) {
         this.updatingSectionData.currentLowestY = â˜ƒ;
         this.updatingSectionData.topSections.defaultReturnValue(this.updatingSectionData.currentLowestY);
      }

      long â˜ƒ = SectionPos.getZeroNode(â˜ƒ);
      int â˜ƒx = this.updatingSectionData.topSections.get(â˜ƒ);
      if (â˜ƒx < â˜ƒ + 1) {
         this.updatingSectionData.topSections.put(â˜ƒ, â˜ƒ + 1);
         if (this.columnsWithSkySources.contains(â˜ƒ)) {
            this.queueAddSource(â˜ƒ);
            if (â˜ƒx > this.updatingSectionData.currentLowestY) {
               long â˜ƒxx = SectionPos.asLong(SectionPos.x(â˜ƒ), â˜ƒx - 1, SectionPos.z(â˜ƒ));
               this.queueRemoveSource(â˜ƒxx);
            }

            this.recheckInconsistencyFlag();
         }
      }
   }

   private void queueRemoveSource(long var1) {
      this.sectionsToRemoveSourcesFrom.add(â˜ƒ);
      this.sectionsToAddSourcesTo.remove(â˜ƒ);
   }

   private void queueAddSource(long var1) {
      this.sectionsToAddSourcesTo.add(â˜ƒ);
      this.sectionsToRemoveSourcesFrom.remove(â˜ƒ);
   }

   private void recheckInconsistencyFlag() {
      this.hasSourceInconsistencies = !this.sectionsToAddSourcesTo.isEmpty() || !this.sectionsToRemoveSourcesFrom.isEmpty();
   }

   @Override
   protected void onNodeRemoved(long var1) {
      long â˜ƒ = SectionPos.getZeroNode(â˜ƒ);
      boolean â˜ƒx = this.columnsWithSkySources.contains(â˜ƒ);
      if (â˜ƒx) {
         this.queueRemoveSource(â˜ƒ);
      }

      int â˜ƒ = SectionPos.y(â˜ƒ);
      if (this.updatingSectionData.topSections.get(â˜ƒ) == â˜ƒ + 1) {
         long â˜ƒ;
         for(â˜ƒ = â˜ƒ; !this.storingLightForSection(â˜ƒ) && this.hasSectionsBelow(â˜ƒ); â˜ƒ = SectionPos.offset(â˜ƒ, Direction.DOWN)) {
            --â˜ƒ;
         }

         if (this.storingLightForSection(â˜ƒ)) {
            this.updatingSectionData.topSections.put(â˜ƒ, â˜ƒ + 1);
            if (â˜ƒx) {
               this.queueAddSource(â˜ƒ);
            }
         } else {
            this.updatingSectionData.topSections.remove(â˜ƒ);
         }
      }

      if (â˜ƒx) {
         this.recheckInconsistencyFlag();
      }
   }

   @Override
   protected void enableLightSources(long var1, boolean var3) {
      this.runAllUpdates();
      if (â˜ƒ && this.columnsWithSkySources.add(â˜ƒ)) {
         int â˜ƒ = this.updatingSectionData.topSections.get(â˜ƒ);
         if (â˜ƒ != this.updatingSectionData.currentLowestY) {
            long â˜ƒx = SectionPos.asLong(SectionPos.x(â˜ƒ), â˜ƒ - 1, SectionPos.z(â˜ƒ));
            this.queueAddSource(â˜ƒx);
            this.recheckInconsistencyFlag();
         }
      } else if (!â˜ƒ) {
         this.columnsWithSkySources.remove(â˜ƒ);
      }
   }

   @Override
   protected boolean hasInconsistencies() {
      return super.hasInconsistencies() || this.hasSourceInconsistencies;
   }

   @Override
   protected DataLayer createDataLayer(long var1) {
      DataLayer â˜ƒ = this.queuedSections.get(â˜ƒ);
      if (â˜ƒ != null) {
         return â˜ƒ;
      } else {
         long â˜ƒ = SectionPos.offset(â˜ƒ, Direction.UP);
         int â˜ƒx = this.updatingSectionData.topSections.get(SectionPos.getZeroNode(â˜ƒ));
         if (â˜ƒx != this.updatingSectionData.currentLowestY && SectionPos.y(â˜ƒ) < â˜ƒx) {
            DataLayer â˜ƒ;
            while((â˜ƒ = this.getDataLayer(â˜ƒ, true)) == null) {
               â˜ƒ = SectionPos.offset(â˜ƒ, Direction.UP);
            }

            return repeatFirstLayer(â˜ƒ);
         } else {
            return new DataLayer();
         }
      }
   }

   private static DataLayer repeatFirstLayer(DataLayer var0) {
      if (â˜ƒ.isEmpty()) {
         return new DataLayer();
      } else {
         byte[] â˜ƒ = â˜ƒ.getData();
         byte[] â˜ƒx = new byte[2048];

         for(int â˜ƒxx = 0; â˜ƒxx < 16; ++â˜ƒxx) {
            System.arraycopy(â˜ƒ, 0, â˜ƒx, â˜ƒxx * 128, 128);
         }

         return new DataLayer(â˜ƒx);
      }
   }

   @Override
   protected void markNewInconsistencies(LayerLightEngine<SkyLightSectionStorage.SkyDataLayerStorageMap, ?> var1, boolean var2, boolean var3) {
      super.markNewInconsistencies(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ) {
         if (!this.sectionsToAddSourcesTo.isEmpty()) {
            LongIterator var4 = this.sectionsToAddSourcesTo.iterator();

            while(var4.hasNext()) {
               long â˜ƒ = var4.next();
               int â˜ƒx = this.getLevel(â˜ƒ);
               if (â˜ƒx != 2 && !this.sectionsToRemoveSourcesFrom.contains(â˜ƒ) && this.sectionsWithSources.add(â˜ƒ)) {
                  if (â˜ƒx == 1) {
                     this.clearQueuedSectionBlocks(â˜ƒ, â˜ƒ);
                     if (this.changedSections.add(â˜ƒ)) {
                        this.updatingSectionData.copyDataLayer(â˜ƒ);
                     }

                     Arrays.fill(this.getDataLayer(â˜ƒ, true).getData(), (byte)-1);
                     int â˜ƒxx = SectionPos.sectionToBlockCoord(SectionPos.x(â˜ƒ));
                     int â˜ƒxxx = SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒ));
                     int â˜ƒxxxx = SectionPos.sectionToBlockCoord(SectionPos.z(â˜ƒ));

                     for(Direction â˜ƒxxxxx : HORIZONTALS) {
                        long â˜ƒxxxxxx = SectionPos.offset(â˜ƒ, â˜ƒxxxxx);
                        if ((
                              this.sectionsToRemoveSourcesFrom.contains(â˜ƒxxxxxx)
                                 || !this.sectionsWithSources.contains(â˜ƒxxxxxx) && !this.sectionsToAddSourcesTo.contains(â˜ƒxxxxxx)
                           )
                           && this.storingLightForSection(â˜ƒxxxxxx)) {
                           for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 16; ++â˜ƒxxxxxxx) {
                              for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 16; ++â˜ƒxxxxxxxx) {
                                 long â˜ƒxxxxxxxxx;
                                 long â˜ƒxxxxxxxxxx;
                                 switch(â˜ƒxxxxx) {
                                    case NORTH:
                                       â˜ƒxxxxxxxxx = BlockPos.asLong(â˜ƒxx + â˜ƒxxxxxxx, â˜ƒxxx + â˜ƒxxxxxxxx, â˜ƒxxxx);
                                       â˜ƒxxxxxxxxxx = BlockPos.asLong(â˜ƒxx + â˜ƒxxxxxxx, â˜ƒxxx + â˜ƒxxxxxxxx, â˜ƒxxxx - 1);
                                       break;
                                    case SOUTH:
                                       â˜ƒxxxxxxxxx = BlockPos.asLong(â˜ƒxx + â˜ƒxxxxxxx, â˜ƒxxx + â˜ƒxxxxxxxx, â˜ƒxxxx + 16 - 1);
                                       â˜ƒxxxxxxxxxx = BlockPos.asLong(â˜ƒxx + â˜ƒxxxxxxx, â˜ƒxxx + â˜ƒxxxxxxxx, â˜ƒxxxx + 16);
                                       break;
                                    case WEST:
                                       â˜ƒxxxxxxxxx = BlockPos.asLong(â˜ƒxx, â˜ƒxxx + â˜ƒxxxxxxx, â˜ƒxxxx + â˜ƒxxxxxxxx);
                                       â˜ƒxxxxxxxxxx = BlockPos.asLong(â˜ƒxx - 1, â˜ƒxxx + â˜ƒxxxxxxx, â˜ƒxxxx + â˜ƒxxxxxxxx);
                                       break;
                                    default:
                                       â˜ƒxxxxxxxxx = BlockPos.asLong(â˜ƒxx + 16 - 1, â˜ƒxxx + â˜ƒxxxxxxx, â˜ƒxxxx + â˜ƒxxxxxxxx);
                                       â˜ƒxxxxxxxxxx = BlockPos.asLong(â˜ƒxx + 16, â˜ƒxxx + â˜ƒxxxxxxx, â˜ƒxxxx + â˜ƒxxxxxxxx);
                                 }

                                 â˜ƒ.checkEdge(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒ.computeLevelFromNeighbor(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, 0), true);
                              }
                           }
                        }
                     }

                     for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16; ++â˜ƒxxxxx) {
                        for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 16; ++â˜ƒxxxxxx) {
                           long â˜ƒxxxxxxx = BlockPos.asLong(
                              SectionPos.sectionToBlockCoord(SectionPos.x(â˜ƒ), â˜ƒxxxxx),
                              SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒ)),
                              SectionPos.sectionToBlockCoord(SectionPos.z(â˜ƒ), â˜ƒxxxxxx)
                           );
                           long â˜ƒxxxxxxxx = BlockPos.asLong(
                              SectionPos.sectionToBlockCoord(SectionPos.x(â˜ƒ), â˜ƒxxxxx),
                              SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒ)) - 1,
                              SectionPos.sectionToBlockCoord(SectionPos.z(â˜ƒ), â˜ƒxxxxxx)
                           );
                           â˜ƒ.checkEdge(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒ.computeLevelFromNeighbor(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, 0), true);
                        }
                     }
                  } else {
                     for(int â˜ƒxx = 0; â˜ƒxx < 16; ++â˜ƒxx) {
                        for(int â˜ƒxxx = 0; â˜ƒxxx < 16; ++â˜ƒxxx) {
                           long â˜ƒxxxx = BlockPos.asLong(
                              SectionPos.sectionToBlockCoord(SectionPos.x(â˜ƒ), â˜ƒxx),
                              SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒ), 15),
                              SectionPos.sectionToBlockCoord(SectionPos.z(â˜ƒ), â˜ƒxxx)
                           );
                           â˜ƒ.checkEdge(Long.MAX_VALUE, â˜ƒxxxx, 0, true);
                        }
                     }
                  }
               }
            }
         }

         this.sectionsToAddSourcesTo.clear();
         if (!this.sectionsToRemoveSourcesFrom.isEmpty()) {
            LongIterator var23 = this.sectionsToRemoveSourcesFrom.iterator();

            while(var23.hasNext()) {
               long â˜ƒ = var23.next();
               if (this.sectionsWithSources.remove(â˜ƒ) && this.storingLightForSection(â˜ƒ)) {
                  for(int â˜ƒx = 0; â˜ƒx < 16; ++â˜ƒx) {
                     for(int â˜ƒxx = 0; â˜ƒxx < 16; ++â˜ƒxx) {
                        long â˜ƒxxx = BlockPos.asLong(
                           SectionPos.sectionToBlockCoord(SectionPos.x(â˜ƒ), â˜ƒx),
                           SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒ), 15),
                           SectionPos.sectionToBlockCoord(SectionPos.z(â˜ƒ), â˜ƒxx)
                        );
                        â˜ƒ.checkEdge(Long.MAX_VALUE, â˜ƒxxx, 15, false);
                     }
                  }
               }
            }
         }

         this.sectionsToRemoveSourcesFrom.clear();
         this.hasSourceInconsistencies = false;
      }
   }

   protected boolean hasSectionsBelow(int var1) {
      return â˜ƒ >= this.updatingSectionData.currentLowestY;
   }

   protected boolean isAboveData(long var1) {
      long â˜ƒ = SectionPos.getZeroNode(â˜ƒ);
      int â˜ƒx = this.updatingSectionData.topSections.get(â˜ƒ);
      return â˜ƒx == this.updatingSectionData.currentLowestY || SectionPos.y(â˜ƒ) >= â˜ƒx;
   }

   protected boolean lightOnInSection(long var1) {
      long â˜ƒ = SectionPos.getZeroNode(â˜ƒ);
      return this.columnsWithSkySources.contains(â˜ƒ);
   }

   protected static final class SkyDataLayerStorageMap extends DataLayerStorageMap<SkyLightSectionStorage.SkyDataLayerStorageMap> {
      int currentLowestY;
      final Long2IntOpenHashMap topSections;

      public SkyDataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> var1, Long2IntOpenHashMap var2, int var3) {
         super(â˜ƒ);
         this.topSections = â˜ƒ;
         â˜ƒ.defaultReturnValue(â˜ƒ);
         this.currentLowestY = â˜ƒ;
      }

      public SkyLightSectionStorage.SkyDataLayerStorageMap copy() {
         return new SkyLightSectionStorage.SkyDataLayerStorageMap(this.map.clone(), this.topSections.clone(), this.currentLowestY);
      }
   }
}
