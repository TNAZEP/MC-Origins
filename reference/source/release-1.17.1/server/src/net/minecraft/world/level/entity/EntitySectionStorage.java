package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import java.util.Objects;
import java.util.Spliterators;
import java.util.PrimitiveIterator.OfLong;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;

public class EntitySectionStorage<T extends EntityAccess> {
   private final Class<T> entityClass;
   private final Long2ObjectFunction<Visibility> intialSectionVisibility;
   private final Long2ObjectMap<EntitySection<T>> sections = new Long2ObjectOpenHashMap<>();
   private final LongSortedSet sectionIds = new LongAVLTreeSet();

   public EntitySectionStorage(Class<T> var1, Long2ObjectFunction<Visibility> var2) {
      this.entityClass = â˜ƒ;
      this.intialSectionVisibility = â˜ƒ;
   }

   public void forEachAccessibleSection(AABB var1, Consumer<EntitySection<T>> var2) {
      int â˜ƒ = SectionPos.posToSectionCoord(â˜ƒ.minX - 2.0);
      int â˜ƒx = SectionPos.posToSectionCoord(â˜ƒ.minY - 2.0);
      int â˜ƒxx = SectionPos.posToSectionCoord(â˜ƒ.minZ - 2.0);
      int â˜ƒxxx = SectionPos.posToSectionCoord(â˜ƒ.maxX + 2.0);
      int â˜ƒxxxx = SectionPos.posToSectionCoord(â˜ƒ.maxY + 2.0);
      int â˜ƒxxxxx = SectionPos.posToSectionCoord(â˜ƒ.maxZ + 2.0);

      for(int â˜ƒxxxxxx = â˜ƒ; â˜ƒxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxx) {
         long â˜ƒxxxxxxx = SectionPos.asLong(â˜ƒxxxxxx, 0, 0);
         long â˜ƒxxxxxxxx = SectionPos.asLong(â˜ƒxxxxxx, -1, -1);
         LongIterator â˜ƒxxxxxxxxx = this.sectionIds.subSet(â˜ƒxxxxxxx, â˜ƒxxxxxxxx + 1L).iterator();

         while(â˜ƒxxxxxxxxx.hasNext()) {
            long â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.nextLong();
            int â˜ƒxxxxxxxxxxx = SectionPos.y(â˜ƒxxxxxxxxxx);
            int â˜ƒxxxxxxxxxxxx = SectionPos.z(â˜ƒxxxxxxxxxx);
            if (â˜ƒxxxxxxxxxxx >= â˜ƒx && â˜ƒxxxxxxxxxxx <= â˜ƒxxxx && â˜ƒxxxxxxxxxxxx >= â˜ƒxx && â˜ƒxxxxxxxxxxxx <= â˜ƒxxxxx) {
               EntitySection<T> â˜ƒxxxxxxxxxxxxx = this.sections.get(â˜ƒxxxxxxxxxx);
               if (â˜ƒxxxxxxxxxxxxx != null && â˜ƒxxxxxxxxxxxxx.getStatus().isAccessible()) {
                  â˜ƒ.accept(â˜ƒxxxxxxxxxxxxx);
               }
            }
         }
      }
   }

   public LongStream getExistingSectionPositionsInChunk(long var1) {
      int â˜ƒ = ChunkPos.getX(â˜ƒ);
      int â˜ƒx = ChunkPos.getZ(â˜ƒ);
      LongSortedSet â˜ƒxx = this.getChunkSections(â˜ƒ, â˜ƒx);
      if (â˜ƒxx.isEmpty()) {
         return LongStream.empty();
      } else {
         OfLong â˜ƒ = â˜ƒxx.iterator();
         return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(â˜ƒ, 1301), false);
      }
   }

   private LongSortedSet getChunkSections(int var1, int var2) {
      long â˜ƒ = SectionPos.asLong(â˜ƒ, 0, â˜ƒ);
      long â˜ƒx = SectionPos.asLong(â˜ƒ, -1, â˜ƒ);
      return this.sectionIds.subSet(â˜ƒ, â˜ƒx + 1L);
   }

   public Stream<EntitySection<T>> getExistingSectionsInChunk(long var1) {
      return this.getExistingSectionPositionsInChunk(â˜ƒ).mapToObj(this.sections::get).filter(Objects::nonNull);
   }

   private static long getChunkKeyFromSectionKey(long var0) {
      return ChunkPos.asLong(SectionPos.x(â˜ƒ), SectionPos.z(â˜ƒ));
   }

   public EntitySection<T> getOrCreateSection(long var1) {
      return this.sections.computeIfAbsent(â˜ƒ, this::createSection);
   }

   @Nullable
   public EntitySection<T> getSection(long var1) {
      return this.sections.get(â˜ƒ);
   }

   private EntitySection<T> createSection(long var1) {
      long â˜ƒ = getChunkKeyFromSectionKey(â˜ƒ);
      Visibility â˜ƒx = (Visibility)this.intialSectionVisibility.get(â˜ƒ);
      this.sectionIds.add(â˜ƒ);
      return new EntitySection<>(this.entityClass, â˜ƒx);
   }

   public LongSet getAllChunksWithExistingSections() {
      LongSet â˜ƒ = new LongOpenHashSet();
      this.sections.keySet().forEach(var1x -> â˜ƒ.add(getChunkKeyFromSectionKey(var1x)));
      return â˜ƒ;
   }

   private static <T extends EntityAccess> Predicate<T> createBoundingBoxCheck(AABB var0) {
      return var1 -> var1.getBoundingBox().intersects(â˜ƒ);
   }

   public void getEntities(AABB var1, Consumer<T> var2) {
      this.forEachAccessibleSection(â˜ƒ, var2x -> var2x.getEntities(createBoundingBoxCheck(â˜ƒ), â˜ƒ));
   }

   public <U extends T> void getEntities(EntityTypeTest<T, U> var1, AABB var2, Consumer<U> var3) {
      this.forEachAccessibleSection(â˜ƒ, var3x -> var3x.getEntities(â˜ƒ, createBoundingBoxCheck(â˜ƒ), â˜ƒ));
   }

   public void remove(long var1) {
      this.sections.remove(â˜ƒ);
      this.sectionIds.remove(â˜ƒ);
   }

   @VisibleForDebug
   public int count() {
      return this.sectionIds.size();
   }
}
