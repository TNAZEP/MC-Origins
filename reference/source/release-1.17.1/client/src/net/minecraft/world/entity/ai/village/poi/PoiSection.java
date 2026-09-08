package net.minecraft.world.entity.ai.village.poi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.VisibleForDebug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoiSection {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Short2ObjectMap<PoiRecord> records = new Short2ObjectOpenHashMap<>();
   private final Map<PoiType, Set<PoiRecord>> byType = Maps.newHashMap();
   private final Runnable setDirty;
   private boolean isValid;

   public static Codec<PoiSection> codec(Runnable var0) {
      return RecordCodecBuilder.<PoiSection>create(
            var1 -> var1.group(
                     RecordCodecBuilder.point(â˜ƒ),
                     Codec.BOOL.optionalFieldOf("Valid", Boolean.valueOf(false)).forGetter(var0x -> var0x.isValid),
                     PoiRecord.codec(â˜ƒ).listOf().fieldOf("Records").forGetter(var0x -> ImmutableList.copyOf(var0x.records.values()))
                  )
                  .apply(var1, PoiSection::new)
         )
         .orElseGet(Util.prefix("Failed to read POI section: ", LOGGER::error), () -> new PoiSection(â˜ƒ, false, ImmutableList.of()));
   }

   public PoiSection(Runnable var1) {
      this(â˜ƒ, true, ImmutableList.of());
   }

   private PoiSection(Runnable var1, boolean var2, List<PoiRecord> var3) {
      this.setDirty = â˜ƒ;
      this.isValid = â˜ƒ;
      â˜ƒ.forEach(this::add);
   }

   public Stream<PoiRecord> getRecords(Predicate<PoiType> var1, PoiManager.Occupancy var2) {
      return this.byType
         .entrySet()
         .stream()
         .filter(var1x -> â˜ƒ.test((PoiType)var1x.getKey()))
         .flatMap(var0 -> ((Set)var0.getValue()).stream())
         .filter(â˜ƒ.getTest());
   }

   public void add(BlockPos var1, PoiType var2) {
      if (this.add(new PoiRecord(â˜ƒ, â˜ƒ, this.setDirty))) {
         LOGGER.debug("Added POI of type {} @ {}", () -> â˜ƒ, () -> â˜ƒ);
         this.setDirty.run();
      }
   }

   private boolean add(PoiRecord var1) {
      BlockPos â˜ƒ = â˜ƒ.getPos();
      PoiType â˜ƒx = â˜ƒ.getPoiType();
      short â˜ƒxx = SectionPos.sectionRelativePos(â˜ƒ);
      PoiRecord â˜ƒxxx = this.records.get(â˜ƒxx);
      if (â˜ƒxxx != null) {
         if (â˜ƒx.equals(â˜ƒxxx.getPoiType())) {
            return false;
         }

         Util.logAndPauseIfInIde("POI data mismatch: already registered at " + â˜ƒ);
      }

      this.records.put(â˜ƒxx, â˜ƒ);
      ((Set)this.byType.computeIfAbsent(â˜ƒx, var0 -> Sets.newHashSet())).add(â˜ƒ);
      return true;
   }

   public void remove(BlockPos var1) {
      PoiRecord â˜ƒ = this.records.remove(SectionPos.sectionRelativePos(â˜ƒ));
      if (â˜ƒ == null) {
         LOGGER.error("POI data mismatch: never registered at {}", â˜ƒ);
      } else {
         ((Set)this.byType.get(â˜ƒ.getPoiType())).remove(â˜ƒ);
         LOGGER.debug("Removed POI of type {} @ {}", â˜ƒ::getPoiType, â˜ƒ::getPos);
         this.setDirty.run();
      }
   }

   @Deprecated
   @VisibleForDebug
   public int getFreeTickets(BlockPos var1) {
      return this.getPoiRecord(â˜ƒ).map(PoiRecord::getFreeTickets).orElse(0);
   }

   public boolean release(BlockPos var1) {
      PoiRecord â˜ƒ = this.records.get(SectionPos.sectionRelativePos(â˜ƒ));
      if (â˜ƒ == null) {
         throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("POI never registered at " + â˜ƒ));
      } else {
         boolean â˜ƒ = â˜ƒ.releaseTicket();
         this.setDirty.run();
         return â˜ƒ;
      }
   }

   public boolean exists(BlockPos var1, Predicate<PoiType> var2) {
      return this.getType(â˜ƒ).filter(â˜ƒ).isPresent();
   }

   public Optional<PoiType> getType(BlockPos var1) {
      return this.getPoiRecord(â˜ƒ).map(PoiRecord::getPoiType);
   }

   private Optional<PoiRecord> getPoiRecord(BlockPos var1) {
      return Optional.ofNullable(this.records.get(SectionPos.sectionRelativePos(â˜ƒ)));
   }

   public void refresh(Consumer<BiConsumer<BlockPos, PoiType>> var1) {
      if (!this.isValid) {
         Short2ObjectMap<PoiRecord> â˜ƒ = new Short2ObjectOpenHashMap<>(this.records);
         this.clear();
         â˜ƒ.accept((BiConsumer)(var2x, var3) -> {
            short â˜ƒ = SectionPos.sectionRelativePos(var2x);
            PoiRecord â˜ƒx = â˜ƒ.computeIfAbsent(â˜ƒ, var3x -> new PoiRecord(var2x, var3, this.setDirty));
            this.add(â˜ƒx);
         });
         this.isValid = true;
         this.setDirty.run();
      }
   }

   private void clear() {
      this.records.clear();
      this.byType.clear();
   }

   boolean isValid() {
      return this.isValid;
   }
}
