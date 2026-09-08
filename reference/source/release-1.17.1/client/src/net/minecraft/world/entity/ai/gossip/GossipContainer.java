package net.minecraft.world.entity.ai.gossip;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.SerializableUUID;
import net.minecraft.util.VisibleForDebug;

public class GossipContainer {
   public static final int DISCARD_THRESHOLD = 2;
   private final Map<UUID, GossipContainer.EntityGossips> gossips = Maps.newHashMap();

   @VisibleForDebug
   public Map<UUID, Object2IntMap<GossipType>> getGossipEntries() {
      Map<UUID, Object2IntMap<GossipType>> â˜ƒ = Maps.newHashMap();
      this.gossips.keySet().forEach(var2 -> {
         GossipContainer.EntityGossips â˜ƒ = (GossipContainer.EntityGossips)this.gossips.get(var2);
         â˜ƒ.put(var2, â˜ƒ.entries);
      });
      return â˜ƒ;
   }

   public void decay() {
      Iterator<GossipContainer.EntityGossips> â˜ƒ = this.gossips.values().iterator();

      while(â˜ƒ.hasNext()) {
         GossipContainer.EntityGossips â˜ƒx = (GossipContainer.EntityGossips)â˜ƒ.next();
         â˜ƒx.decay();
         if (â˜ƒx.isEmpty()) {
            â˜ƒ.remove();
         }
      }
   }

   private Stream<GossipContainer.GossipEntry> unpack() {
      return this.gossips.entrySet().stream().flatMap(var0 -> ((GossipContainer.EntityGossips)var0.getValue()).unpack((UUID)var0.getKey()));
   }

   private Collection<GossipContainer.GossipEntry> selectGossipsForTransfer(Random var1, int var2) {
      List<GossipContainer.GossipEntry> â˜ƒ = (List)this.unpack().collect(Collectors.toList());
      if (â˜ƒ.isEmpty()) {
         return Collections.emptyList();
      } else {
         int[] â˜ƒ = new int[â˜ƒ.size()];
         int â˜ƒx = 0;

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
            GossipContainer.GossipEntry â˜ƒxxx = (GossipContainer.GossipEntry)â˜ƒ.get(â˜ƒxx);
            â˜ƒx += Math.abs(â˜ƒxxx.weightedValue());
            â˜ƒ[â˜ƒxx] = â˜ƒx - 1;
         }

         Set<GossipContainer.GossipEntry> â˜ƒxx = Sets.newIdentityHashSet();

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
            int â˜ƒxxxx = â˜ƒ.nextInt(â˜ƒx);
            int â˜ƒxxxxx = Arrays.binarySearch(â˜ƒ, â˜ƒxxxx);
            â˜ƒxx.add((GossipContainer.GossipEntry)â˜ƒ.get(â˜ƒxxxxx < 0 ? -â˜ƒxxxxx - 1 : â˜ƒxxxxx));
         }

         return â˜ƒxx;
      }
   }

   private GossipContainer.EntityGossips getOrCreate(UUID var1) {
      return (GossipContainer.EntityGossips)this.gossips.computeIfAbsent(â˜ƒ, var0 -> new GossipContainer.EntityGossips());
   }

   public void transferFrom(GossipContainer var1, Random var2, int var3) {
      Collection<GossipContainer.GossipEntry> â˜ƒ = â˜ƒ.selectGossipsForTransfer(â˜ƒ, â˜ƒ);
      â˜ƒ.forEach(var1x -> {
         int â˜ƒ = var1x.value - var1x.type.decayPerTransfer;
         if (â˜ƒ >= 2) {
            this.getOrCreate(var1x.target).entries.mergeInt(var1x.type, â˜ƒ, GossipContainer::mergeValuesForTransfer);
         }
      });
   }

   public int getReputation(UUID var1, Predicate<GossipType> var2) {
      GossipContainer.EntityGossips â˜ƒ = (GossipContainer.EntityGossips)this.gossips.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.weightedValue(â˜ƒ) : 0;
   }

   public long getCountForType(GossipType var1, DoublePredicate var2) {
      return this.gossips.values().stream().filter(var2x -> â˜ƒ.test((double)(var2x.entries.getOrDefault(â˜ƒ, 0) * â˜ƒ.weight))).count();
   }

   public void add(UUID var1, GossipType var2, int var3) {
      GossipContainer.EntityGossips â˜ƒ = this.getOrCreate(â˜ƒ);
      â˜ƒ.entries.mergeInt(â˜ƒ, â˜ƒ, (var2x, var3x) -> this.mergeValuesForAddition(â˜ƒ, var2x, var3x));
      â˜ƒ.makeSureValueIsntTooLowOrTooHigh(â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         this.gossips.remove(â˜ƒ);
      }
   }

   public void remove(UUID var1, GossipType var2, int var3) {
      this.add(â˜ƒ, â˜ƒ, -â˜ƒ);
   }

   public void remove(UUID var1, GossipType var2) {
      GossipContainer.EntityGossips â˜ƒ = (GossipContainer.EntityGossips)this.gossips.get(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.remove(â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            this.gossips.remove(â˜ƒ);
         }
      }
   }

   public void remove(GossipType var1) {
      Iterator<GossipContainer.EntityGossips> â˜ƒ = this.gossips.values().iterator();

      while(â˜ƒ.hasNext()) {
         GossipContainer.EntityGossips â˜ƒx = (GossipContainer.EntityGossips)â˜ƒ.next();
         â˜ƒx.remove(â˜ƒ);
         if (â˜ƒx.isEmpty()) {
            â˜ƒ.remove();
         }
      }
   }

   public <T> Dynamic<T> store(DynamicOps<T> var1) {
      return new Dynamic<>(â˜ƒ, â˜ƒ.createList(this.unpack().map(var1x -> var1x.store(â˜ƒ)).map(Dynamic::getValue)));
   }

   public void update(Dynamic<?> var1) {
      â˜ƒ.asStream()
         .map(GossipContainer.GossipEntry::load)
         .flatMap(var0 -> Util.toStream(var0.result()))
         .forEach(var1x -> this.getOrCreate(var1x.target).entries.put(var1x.type, var1x.value));
   }

   private static int mergeValuesForTransfer(int var0, int var1) {
      return Math.max(â˜ƒ, â˜ƒ);
   }

   private int mergeValuesForAddition(GossipType var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ + â˜ƒ;
      return â˜ƒ > â˜ƒ.max ? Math.max(â˜ƒ.max, â˜ƒ) : â˜ƒ;
   }

   static class EntityGossips {
      final Object2IntMap<GossipType> entries = new Object2IntOpenHashMap();

      public int weightedValue(Predicate<GossipType> var1) {
         return this.entries
            .object2IntEntrySet()
            .stream()
            .filter(var1x -> â˜ƒ.test((GossipType)var1x.getKey()))
            .mapToInt(var0 -> var0.getIntValue() * ((GossipType)var0.getKey()).weight)
            .sum();
      }

      public Stream<GossipContainer.GossipEntry> unpack(UUID var1) {
         return this.entries.object2IntEntrySet().stream().map(var1x -> new GossipContainer.GossipEntry(â˜ƒ, (GossipType)var1x.getKey(), var1x.getIntValue()));
      }

      public void decay() {
         ObjectIterator<Entry<GossipType>> â˜ƒ = this.entries.object2IntEntrySet().iterator();

         while(â˜ƒ.hasNext()) {
            Entry<GossipType> â˜ƒx = (Entry)â˜ƒ.next();
            int â˜ƒxx = â˜ƒx.getIntValue() - ((GossipType)â˜ƒx.getKey()).decayPerDay;
            if (â˜ƒxx < 2) {
               â˜ƒ.remove();
            } else {
               â˜ƒx.setValue(â˜ƒxx);
            }
         }
      }

      public boolean isEmpty() {
         return this.entries.isEmpty();
      }

      public void makeSureValueIsntTooLowOrTooHigh(GossipType var1) {
         int â˜ƒ = this.entries.getInt(â˜ƒ);
         if (â˜ƒ > â˜ƒ.max) {
            this.entries.put(â˜ƒ, â˜ƒ.max);
         }

         if (â˜ƒ < 2) {
            this.remove(â˜ƒ);
         }
      }

      public void remove(GossipType var1) {
         this.entries.removeInt(â˜ƒ);
      }
   }

   static class GossipEntry {
      public static final String TAG_TARGET = "Target";
      public static final String TAG_TYPE = "Type";
      public static final String TAG_VALUE = "Value";
      public final UUID target;
      public final GossipType type;
      public final int value;

      public GossipEntry(UUID var1, GossipType var2, int var3) {
         this.target = â˜ƒ;
         this.type = â˜ƒ;
         this.value = â˜ƒ;
      }

      public int weightedValue() {
         return this.value * this.type.weight;
      }

      public String toString() {
         return "GossipEntry{target=" + this.target + ", type=" + this.type + ", value=" + this.value + "}";
      }

      public <T> Dynamic<T> store(DynamicOps<T> var1) {
         return new Dynamic<>(
            â˜ƒ,
            â˜ƒ.createMap(
               ImmutableMap.of(
                  â˜ƒ.createString("Target"),
                  (T)SerializableUUID.CODEC.encodeStart(â˜ƒ, this.target).result().orElseThrow(RuntimeException::new),
                  â˜ƒ.createString("Type"),
                  â˜ƒ.createString(this.type.id),
                  â˜ƒ.createString("Value"),
                  â˜ƒ.createInt(this.value)
               )
            )
         );
      }

      public static DataResult<GossipContainer.GossipEntry> load(Dynamic<?> var0) {
         return DataResult.unbox(
            DataResult.instance()
               .group(
                  â˜ƒ.get("Target").read(SerializableUUID.CODEC),
                  â˜ƒ.get("Type").asString().map(GossipType::byId),
                  â˜ƒ.get("Value").asNumber().map(Number::intValue)
               )
               .apply(DataResult.instance(), GossipContainer.GossipEntry::new)
         );
      }
   }
}
