package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SectionStorage<R> implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String SECTIONS_TAG = "Sections";
   private final IOWorker worker;
   private final Long2ObjectMap<Optional<R>> storage = new Long2ObjectOpenHashMap();
   private final LongLinkedOpenHashSet dirty = new LongLinkedOpenHashSet();
   private final Function<Runnable, Codec<R>> codec;
   private final Function<Runnable, R> factory;
   private final DataFixer fixerUpper;
   private final DataFixTypes type;
   protected final LevelHeightAccessor levelHeightAccessor;

   public SectionStorage(
      File var1, Function<Runnable, Codec<R>> var2, Function<Runnable, R> var3, DataFixer var4, DataFixTypes var5, boolean var6, LevelHeightAccessor var7
   ) {
      this.codec = â˜ƒ;
      this.factory = â˜ƒ;
      this.fixerUpper = â˜ƒ;
      this.type = â˜ƒ;
      this.levelHeightAccessor = â˜ƒ;
      this.worker = new IOWorker(â˜ƒ, â˜ƒ, â˜ƒ.getName());
   }

   protected void tick(BooleanSupplier var1) {
      while(!this.dirty.isEmpty() && â˜ƒ.getAsBoolean()) {
         ChunkPos â˜ƒ = SectionPos.of(this.dirty.firstLong()).chunk();
         this.writeColumn(â˜ƒ);
      }
   }

   @Nullable
   protected Optional<R> get(long var1) {
      return (Optional<R>)this.storage.get(â˜ƒ);
   }

   protected Optional<R> getOrLoad(long var1) {
      if (this.outsideStoredRange(â˜ƒ)) {
         return Optional.empty();
      } else {
         Optional<R> â˜ƒ = this.get(â˜ƒ);
         if (â˜ƒ != null) {
            return â˜ƒ;
         } else {
            this.readColumn(SectionPos.of(â˜ƒ).chunk());
            â˜ƒ = this.get(â˜ƒ);
            if (â˜ƒ == null) {
               throw (IllegalStateException)Util.pauseInIde(new IllegalStateException());
            } else {
               return â˜ƒ;
            }
         }
      }
   }

   protected boolean outsideStoredRange(long var1) {
      int â˜ƒ = SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒ));
      return this.levelHeightAccessor.isOutsideBuildHeight(â˜ƒ);
   }

   protected R getOrCreate(long var1) {
      if (this.outsideStoredRange(â˜ƒ)) {
         throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("sectionPos out of bounds"));
      } else {
         Optional<R> â˜ƒ = this.getOrLoad(â˜ƒ);
         if (â˜ƒ.isPresent()) {
            return (R)â˜ƒ.get();
         } else {
            R â˜ƒ = (R)this.factory.apply((Runnable)() -> this.setDirty(â˜ƒ));
            this.storage.put(â˜ƒ, Optional.of(â˜ƒ));
            return â˜ƒ;
         }
      }
   }

   private void readColumn(ChunkPos var1) {
      this.readColumn(â˜ƒ, NbtOps.INSTANCE, this.tryRead(â˜ƒ));
   }

   @Nullable
   private CompoundTag tryRead(ChunkPos var1) {
      try {
         return this.worker.load(â˜ƒ);
      } catch (IOException var3) {
         LOGGER.error("Error reading chunk {} data from disk", â˜ƒ, var3);
         return null;
      }
   }

   private <T> void readColumn(ChunkPos var1, DynamicOps<T> var2, @Nullable T var3) {
      if (â˜ƒ == null) {
         for(int â˜ƒ = this.levelHeightAccessor.getMinSection(); â˜ƒ < this.levelHeightAccessor.getMaxSection(); ++â˜ƒ) {
            this.storage.put(getKey(â˜ƒ, â˜ƒ), Optional.empty());
         }
      } else {
         Dynamic<T> â˜ƒ = new Dynamic<>(â˜ƒ, â˜ƒ);
         int â˜ƒx = getVersion(â˜ƒ);
         int â˜ƒxx = SharedConstants.getCurrentVersion().getWorldVersion();
         boolean â˜ƒxxx = â˜ƒx != â˜ƒxx;
         Dynamic<T> â˜ƒxxxx = this.fixerUpper.update(this.type.getType(), â˜ƒ, â˜ƒx, â˜ƒxx);
         OptionalDynamic<T> â˜ƒxxxxx = â˜ƒxxxx.get("Sections");

         for(int â˜ƒxxxxxx = this.levelHeightAccessor.getMinSection(); â˜ƒxxxxxx < this.levelHeightAccessor.getMaxSection(); ++â˜ƒxxxxxx) {
            long â˜ƒxxxxxxx = getKey(â˜ƒ, â˜ƒxxxxxx);
            Optional<R> â˜ƒxxxxxxxx = â˜ƒxxxxx.get(Integer.toString(â˜ƒxxxxxx))
               .result()
               .flatMap(var3x -> ((Codec)this.codec.apply((Runnable)() -> this.setDirty(â˜ƒ))).parse(var3x).resultOrPartial(LOGGER::error));
            this.storage.put(â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
            â˜ƒxxxxxxxx.ifPresent(var4x -> {
               this.onSectionLoad(â˜ƒ);
               if (â˜ƒ) {
                  this.setDirty(â˜ƒ);
               }
            });
         }
      }
   }

   private void writeColumn(ChunkPos var1) {
      Dynamic<Tag> â˜ƒ = this.writeColumn(â˜ƒ, NbtOps.INSTANCE);
      Tag â˜ƒx = â˜ƒ.getValue();
      if (â˜ƒx instanceof CompoundTag) {
         this.worker.store(â˜ƒ, (CompoundTag)â˜ƒx);
      } else {
         LOGGER.error("Expected compound tag, got {}", â˜ƒx);
      }
   }

   private <T> Dynamic<T> writeColumn(ChunkPos var1, DynamicOps<T> var2) {
      Map<T, T> â˜ƒ = Maps.<T, T>newHashMap();

      for(int â˜ƒx = this.levelHeightAccessor.getMinSection(); â˜ƒx < this.levelHeightAccessor.getMaxSection(); ++â˜ƒx) {
         long â˜ƒxx = getKey(â˜ƒ, â˜ƒx);
         this.dirty.remove(â˜ƒxx);
         Optional<R> â˜ƒxxx = (Optional)this.storage.get(â˜ƒxx);
         if (â˜ƒxxx != null && â˜ƒxxx.isPresent()) {
            DataResult<T> â˜ƒxxxx = ((Codec)this.codec.apply((Runnable)() -> this.setDirty(â˜ƒ))).encodeStart(â˜ƒ, â˜ƒxxx.get());
            String â˜ƒxxxxx = Integer.toString(â˜ƒx);
            â˜ƒxxxx.resultOrPartial(LOGGER::error).ifPresent(var3x -> â˜ƒ.put(â˜ƒ.createString(â˜ƒ), var3x));
         }
      }

      return new Dynamic<>(
         â˜ƒ,
         â˜ƒ.createMap(
            ImmutableMap.of(
               â˜ƒ.createString("Sections"),
               â˜ƒ.createMap(â˜ƒ),
               â˜ƒ.createString("DataVersion"),
               â˜ƒ.createInt(SharedConstants.getCurrentVersion().getWorldVersion())
            )
         )
      );
   }

   private static long getKey(ChunkPos var0, int var1) {
      return SectionPos.asLong(â˜ƒ.x, â˜ƒ, â˜ƒ.z);
   }

   protected void onSectionLoad(long var1) {
   }

   protected void setDirty(long var1) {
      Optional<R> â˜ƒ = (Optional)this.storage.get(â˜ƒ);
      if (â˜ƒ != null && â˜ƒ.isPresent()) {
         this.dirty.add(â˜ƒ);
      } else {
         LOGGER.warn("No data for position: {}", SectionPos.of(â˜ƒ));
      }
   }

   private static int getVersion(Dynamic<?> var0) {
      return â˜ƒ.get("DataVersion").asInt(1945);
   }

   public void flush(ChunkPos var1) {
      if (!this.dirty.isEmpty()) {
         for(int â˜ƒ = this.levelHeightAccessor.getMinSection(); â˜ƒ < this.levelHeightAccessor.getMaxSection(); ++â˜ƒ) {
            long â˜ƒx = getKey(â˜ƒ, â˜ƒ);
            if (this.dirty.contains(â˜ƒx)) {
               this.writeColumn(â˜ƒ);
               return;
            }
         }
      }
   }

   public void close() throws IOException {
      this.worker.close();
   }
}
