package net.minecraft.world.entity.ai;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Brain<E extends LivingEntity> {
   static final Logger LOGGER = LogManager.getLogger();
   private final Supplier<Codec<Brain<E>>> codec;
   private static final int SCHEDULE_UPDATE_DELAY = 20;
   private final Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> memories = Maps.newHashMap();
   private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> sensors = Maps.<SensorType<? extends Sensor<? super E>>, Sensor<? super E>>newLinkedHashMap(
      
   );
   private final Map<Integer, Map<Activity, Set<Behavior<? super E>>>> availableBehaviorsByPriority = Maps.newTreeMap();
   private Schedule schedule = Schedule.EMPTY;
   private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> activityRequirements = Maps.newHashMap();
   private final Map<Activity, Set<MemoryModuleType<?>>> activityMemoriesToEraseWhenStopped = Maps.newHashMap();
   private Set<Activity> coreActivities = Sets.<Activity>newHashSet();
   private final Set<Activity> activeActivities = Sets.<Activity>newHashSet();
   private Activity defaultActivity = Activity.IDLE;
   private long lastScheduleUpdate = -9999L;

   public static <E extends LivingEntity> Brain.Provider<E> provider(
      Collection<? extends MemoryModuleType<?>> var0, Collection<? extends SensorType<? extends Sensor<? super E>>> var1
   ) {
      return new Brain.Provider<>(â˜ƒ, â˜ƒ);
   }

   public static <E extends LivingEntity> Codec<Brain<E>> codec(
      final Collection<? extends MemoryModuleType<?>> var0, final Collection<? extends SensorType<? extends Sensor<? super E>>> var1
   ) {
      final MutableObject<Codec<Brain<E>>> â˜ƒ = new MutableObject<>();
      â˜ƒ.setValue(
         (new MapCodec<Brain<E>>() {
               @Override
               public <T> Stream<T> keys(DynamicOps<T> var1x) {
                  return â˜ƒ.stream()
                     .flatMap(var0x -> Util.toStream(var0x.getCodec().map(var1xx -> Registry.MEMORY_MODULE_TYPE.getKey(var0x))))
                     .map(var1xx -> â˜ƒ.createString(var1xx.toString()));
               }
      
               @Override
               public <T> DataResult<Brain<E>> decode(DynamicOps<T> var1x, MapLike<T> var2x) {
                  MutableObject<DataResult<Builder<Brain.MemoryValue<?>>>> â˜ƒ = new MutableObject<>(DataResult.success(ImmutableList.builder()));
                  â˜ƒ.entries().forEach(var3x -> {
                     DataResult<MemoryModuleType<?>> â˜ƒ = Registry.MEMORY_MODULE_TYPE.parse(â˜ƒ, (T)var3x.getFirst());
                     DataResult<? extends Brain.MemoryValue<?>> â˜ƒx = â˜ƒ.flatMap(var3xx -> this.captureRead(var3xx, â˜ƒ, (T)var3x.getSecond()));
                     â˜ƒ.setValue(â˜ƒ.getValue().apply2(Builder::add, â˜ƒx));
                  });
                  ImmutableList<Brain.MemoryValue<?>> â˜ƒx = (ImmutableList)â˜ƒ.getValue()
                     .resultOrPartial(Brain.LOGGER::error)
                     .map(Builder::build)
                     .orElseGet(ImmutableList::of);
                  return DataResult.success(new Brain<>(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ::getValue));
               }
      
               private <T, U> DataResult<Brain.MemoryValue<U>> captureRead(MemoryModuleType<U> var1x, DynamicOps<T> var2x, T var3) {
                  return ((DataResult)â˜ƒ.getCodec().map(DataResult::success).orElseGet(() -> DataResult.error("No codec for memory: " + â˜ƒ)))
                     .flatMap(var2xx -> var2xx.parse(â˜ƒ, â˜ƒ))
                     .map(var1xx -> new Brain.MemoryValue<>(â˜ƒ, Optional.of(var1xx)));
               }
      
               public <T> RecordBuilder<T> encode(Brain<E> var1x, DynamicOps<T> var2x, RecordBuilder<T> var3) {
                  â˜ƒ.memories().forEach(var2xx -> var2xx.serialize(â˜ƒ, â˜ƒ));
                  return â˜ƒ;
               }
            })
            .fieldOf("memories")
            .codec()
      );
      return â˜ƒ.getValue();
   }

   public Brain(
      Collection<? extends MemoryModuleType<?>> var1,
      Collection<? extends SensorType<? extends Sensor<? super E>>> var2,
      ImmutableList<Brain.MemoryValue<?>> var3,
      Supplier<Codec<Brain<E>>> var4
   ) {
      this.codec = â˜ƒ;

      for(MemoryModuleType<?> â˜ƒ : â˜ƒ) {
         this.memories.put(â˜ƒ, Optional.empty());
      }

      for(SensorType<? extends Sensor<? super E>> â˜ƒ : â˜ƒ) {
         this.sensors.put(â˜ƒ, â˜ƒ.create());
      }

      for(Sensor<? super E> â˜ƒ : this.sensors.values()) {
         for(MemoryModuleType<?> â˜ƒx : â˜ƒ.requires()) {
            this.memories.put(â˜ƒx, Optional.empty());
         }
      }

      for(Brain.MemoryValue<?> â˜ƒ : â˜ƒ) {
         â˜ƒ.setMemoryInternal(this);
      }
   }

   public <T> DataResult<T> serializeStart(DynamicOps<T> var1) {
      return ((Codec)this.codec.get()).encodeStart(â˜ƒ, this);
   }

   Stream<Brain.MemoryValue<?>> memories() {
      return this.memories
         .entrySet()
         .stream()
         .map(var0 -> Brain.MemoryValue.createUnchecked((MemoryModuleType)var0.getKey(), (Optional<? extends ExpirableValue<?>>)var0.getValue()));
   }

   public boolean hasMemoryValue(MemoryModuleType<?> var1) {
      return this.checkMemory(â˜ƒ, MemoryStatus.VALUE_PRESENT);
   }

   public <U> void eraseMemory(MemoryModuleType<U> var1) {
      this.setMemory(â˜ƒ, Optional.empty());
   }

   public <U> void setMemory(MemoryModuleType<U> var1, @Nullable U var2) {
      this.setMemory(â˜ƒ, Optional.ofNullable(â˜ƒ));
   }

   public <U> void setMemoryWithExpiry(MemoryModuleType<U> var1, U var2, long var3) {
      this.setMemoryInternal(â˜ƒ, Optional.of(ExpirableValue.of(â˜ƒ, â˜ƒ)));
   }

   public <U> void setMemory(MemoryModuleType<U> var1, Optional<? extends U> var2) {
      this.setMemoryInternal(â˜ƒ, â˜ƒ.map(ExpirableValue::of));
   }

   <U> void setMemoryInternal(MemoryModuleType<U> var1, Optional<? extends ExpirableValue<?>> var2) {
      if (this.memories.containsKey(â˜ƒ)) {
         if (â˜ƒ.isPresent() && this.isEmptyCollection(((ExpirableValue)â˜ƒ.get()).getValue())) {
            this.eraseMemory(â˜ƒ);
         } else {
            this.memories.put(â˜ƒ, â˜ƒ);
         }
      }
   }

   public <U> Optional<U> getMemory(MemoryModuleType<U> var1) {
      return ((Optional)this.memories.get(â˜ƒ)).map(ExpirableValue::getValue);
   }

   public <U> long getTimeUntilExpiry(MemoryModuleType<U> var1) {
      Optional<? extends ExpirableValue<?>> â˜ƒ = (Optional)this.memories.get(â˜ƒ);
      return â˜ƒ.map(ExpirableValue::getTimeToLive).orElse(0L);
   }

   @Deprecated
   @VisibleForDebug
   public Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> getMemories() {
      return this.memories;
   }

   public <U> boolean isMemoryValue(MemoryModuleType<U> var1, U var2) {
      return !this.hasMemoryValue(â˜ƒ) ? false : this.getMemory(â˜ƒ).filter(var1x -> var1x.equals(â˜ƒ)).isPresent();
   }

   public boolean checkMemory(MemoryModuleType<?> var1, MemoryStatus var2) {
      Optional<? extends ExpirableValue<?>> â˜ƒ = (Optional)this.memories.get(â˜ƒ);
      if (â˜ƒ == null) {
         return false;
      } else {
         return â˜ƒ == MemoryStatus.REGISTERED || â˜ƒ == MemoryStatus.VALUE_PRESENT && â˜ƒ.isPresent() || â˜ƒ == MemoryStatus.VALUE_ABSENT && !â˜ƒ.isPresent();
      }
   }

   public Schedule getSchedule() {
      return this.schedule;
   }

   public void setSchedule(Schedule var1) {
      this.schedule = â˜ƒ;
   }

   public void setCoreActivities(Set<Activity> var1) {
      this.coreActivities = â˜ƒ;
   }

   @Deprecated
   @VisibleForDebug
   public Set<Activity> getActiveActivities() {
      return this.activeActivities;
   }

   @Deprecated
   @VisibleForDebug
   public List<Behavior<? super E>> getRunningBehaviors() {
      List<Behavior<? super E>> â˜ƒ = new ObjectArrayList<>();

      for(Map<Activity, Set<Behavior<? super E>>> â˜ƒx : this.availableBehaviorsByPriority.values()) {
         for(Set<Behavior<? super E>> â˜ƒxx : â˜ƒx.values()) {
            for(Behavior<? super E> â˜ƒxxx : â˜ƒxx) {
               if (â˜ƒxxx.getStatus() == Behavior.Status.RUNNING) {
                  â˜ƒ.add(â˜ƒxxx);
               }
            }
         }
      }

      return â˜ƒ;
   }

   public void useDefaultActivity() {
      this.setActiveActivity(this.defaultActivity);
   }

   public Optional<Activity> getActiveNonCoreActivity() {
      for(Activity â˜ƒ : this.activeActivities) {
         if (!this.coreActivities.contains(â˜ƒ)) {
            return Optional.of(â˜ƒ);
         }
      }

      return Optional.empty();
   }

   public void setActiveActivityIfPossible(Activity var1) {
      if (this.activityRequirementsAreMet(â˜ƒ)) {
         this.setActiveActivity(â˜ƒ);
      } else {
         this.useDefaultActivity();
      }
   }

   private void setActiveActivity(Activity var1) {
      if (!this.isActive(â˜ƒ)) {
         this.eraseMemoriesForOtherActivitesThan(â˜ƒ);
         this.activeActivities.clear();
         this.activeActivities.addAll(this.coreActivities);
         this.activeActivities.add(â˜ƒ);
      }
   }

   private void eraseMemoriesForOtherActivitesThan(Activity var1) {
      for(Activity â˜ƒ : this.activeActivities) {
         if (â˜ƒ != â˜ƒ) {
            Set<MemoryModuleType<?>> â˜ƒx = (Set)this.activityMemoriesToEraseWhenStopped.get(â˜ƒ);
            if (â˜ƒx != null) {
               for(MemoryModuleType<?> â˜ƒxx : â˜ƒx) {
                  this.eraseMemory(â˜ƒxx);
               }
            }
         }
      }
   }

   public void updateActivityFromSchedule(long var1, long var3) {
      if (â˜ƒ - this.lastScheduleUpdate > 20L) {
         this.lastScheduleUpdate = â˜ƒ;
         Activity â˜ƒ = this.getSchedule().getActivityAt((int)(â˜ƒ % 24000L));
         if (!this.activeActivities.contains(â˜ƒ)) {
            this.setActiveActivityIfPossible(â˜ƒ);
         }
      }
   }

   public void setActiveActivityToFirstValid(List<Activity> var1) {
      for(Activity â˜ƒ : â˜ƒ) {
         if (this.activityRequirementsAreMet(â˜ƒ)) {
            this.setActiveActivity(â˜ƒ);
            break;
         }
      }
   }

   public void setDefaultActivity(Activity var1) {
      this.defaultActivity = â˜ƒ;
   }

   public void addActivity(Activity var1, int var2, ImmutableList<? extends Behavior<? super E>> var3) {
      this.addActivity(â˜ƒ, this.createPriorityPairs(â˜ƒ, â˜ƒ));
   }

   public void addActivityAndRemoveMemoryWhenStopped(Activity var1, int var2, ImmutableList<? extends Behavior<? super E>> var3, MemoryModuleType<?> var4) {
      Set<Pair<MemoryModuleType<?>, MemoryStatus>> â˜ƒ = ImmutableSet.of(Pair.of(â˜ƒ, MemoryStatus.VALUE_PRESENT));
      Set<MemoryModuleType<?>> â˜ƒx = ImmutableSet.of(â˜ƒ);
      this.addActivityAndRemoveMemoriesWhenStopped(â˜ƒ, this.createPriorityPairs(â˜ƒ, â˜ƒ), â˜ƒ, â˜ƒx);
   }

   public void addActivity(Activity var1, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> var2) {
      this.addActivityAndRemoveMemoriesWhenStopped(â˜ƒ, â˜ƒ, ImmutableSet.of(), Sets.<MemoryModuleType<?>>newHashSet());
   }

   public void addActivityWithConditions(
      Activity var1, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> var2, Set<Pair<MemoryModuleType<?>, MemoryStatus>> var3
   ) {
      this.addActivityAndRemoveMemoriesWhenStopped(â˜ƒ, â˜ƒ, â˜ƒ, Sets.<MemoryModuleType<?>>newHashSet());
   }

   public void addActivityAndRemoveMemoriesWhenStopped(
      Activity var1,
      ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> var2,
      Set<Pair<MemoryModuleType<?>, MemoryStatus>> var3,
      Set<MemoryModuleType<?>> var4
   ) {
      this.activityRequirements.put(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isEmpty()) {
         this.activityMemoriesToEraseWhenStopped.put(â˜ƒ, â˜ƒ);
      }

      for(Pair<Integer, ? extends Behavior<? super E>> â˜ƒ : â˜ƒ) {
         ((Set)((Map)this.availableBehaviorsByPriority.computeIfAbsent((Integer)â˜ƒ.getFirst(), var0 -> Maps.newHashMap()))
               .computeIfAbsent(â˜ƒ, var0 -> Sets.newLinkedHashSet()))
            .add(â˜ƒ.getSecond());
      }
   }

   @VisibleForTesting
   public void removeAllBehaviors() {
      this.availableBehaviorsByPriority.clear();
   }

   public boolean isActive(Activity var1) {
      return this.activeActivities.contains(â˜ƒ);
   }

   public Brain<E> copyWithoutBehaviors() {
      Brain<E> â˜ƒ = new Brain<>(this.memories.keySet(), this.sensors.keySet(), ImmutableList.of(), this.codec);

      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> â˜ƒx : this.memories.entrySet()) {
         MemoryModuleType<?> â˜ƒxx = (MemoryModuleType)â˜ƒx.getKey();
         if (((Optional)â˜ƒx.getValue()).isPresent()) {
            â˜ƒ.memories.put(â˜ƒxx, (Optional)â˜ƒx.getValue());
         }
      }

      return â˜ƒ;
   }

   public void tick(ServerLevel var1, E var2) {
      this.forgetOutdatedMemories();
      this.tickSensors(â˜ƒ, â˜ƒ);
      this.startEachNonRunningBehavior(â˜ƒ, â˜ƒ);
      this.tickEachRunningBehavior(â˜ƒ, â˜ƒ);
   }

   private void tickSensors(ServerLevel var1, E var2) {
      for(Sensor<? super E> â˜ƒ : this.sensors.values()) {
         â˜ƒ.tick(â˜ƒ, â˜ƒ);
      }
   }

   private void forgetOutdatedMemories() {
      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> â˜ƒ : this.memories.entrySet()) {
         if (((Optional)â˜ƒ.getValue()).isPresent()) {
            ExpirableValue<?> â˜ƒx = (ExpirableValue)((Optional)â˜ƒ.getValue()).get();
            â˜ƒx.tick();
            if (â˜ƒx.hasExpired()) {
               this.eraseMemory((MemoryModuleType)â˜ƒ.getKey());
            }
         }
      }
   }

   public void stopAll(ServerLevel var1, E var2) {
      long â˜ƒ = â˜ƒ.level.getGameTime();

      for(Behavior<? super E> â˜ƒx : this.getRunningBehaviors()) {
         â˜ƒx.doStop(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void startEachNonRunningBehavior(ServerLevel var1, E var2) {
      long â˜ƒ = â˜ƒ.getGameTime();

      for(Map<Activity, Set<Behavior<? super E>>> â˜ƒx : this.availableBehaviorsByPriority.values()) {
         for(Entry<Activity, Set<Behavior<? super E>>> â˜ƒxx : â˜ƒx.entrySet()) {
            Activity â˜ƒxxx = (Activity)â˜ƒxx.getKey();
            if (this.activeActivities.contains(â˜ƒxxx)) {
               for(Behavior<? super E> â˜ƒxxxx : (Set)â˜ƒxx.getValue()) {
                  if (â˜ƒxxxx.getStatus() == Behavior.Status.STOPPED) {
                     â˜ƒxxxx.tryStart(â˜ƒ, â˜ƒ, â˜ƒ);
                  }
               }
            }
         }
      }
   }

   private void tickEachRunningBehavior(ServerLevel var1, E var2) {
      long â˜ƒ = â˜ƒ.getGameTime();

      for(Behavior<? super E> â˜ƒx : this.getRunningBehaviors()) {
         â˜ƒx.tickOrStop(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private boolean activityRequirementsAreMet(Activity var1) {
      if (!this.activityRequirements.containsKey(â˜ƒ)) {
         return false;
      } else {
         for(Pair<MemoryModuleType<?>, MemoryStatus> â˜ƒ : (Set)this.activityRequirements.get(â˜ƒ)) {
            MemoryModuleType<?> â˜ƒx = â˜ƒ.getFirst();
            MemoryStatus â˜ƒxx = (MemoryStatus)â˜ƒ.getSecond();
            if (!this.checkMemory(â˜ƒx, â˜ƒxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isEmptyCollection(Object var1) {
      return â˜ƒ instanceof Collection && ((Collection)â˜ƒ).isEmpty();
   }

   ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> createPriorityPairs(int var1, ImmutableList<? extends Behavior<? super E>> var2) {
      int â˜ƒ = â˜ƒ;
      Builder<Pair<Integer, ? extends Behavior<? super E>>> â˜ƒx = ImmutableList.builder();

      for(Behavior<? super E> â˜ƒxx : â˜ƒ) {
         â˜ƒx.add(Pair.of(â˜ƒ++, â˜ƒxx));
      }

      return â˜ƒx.build();
   }

   static final class MemoryValue<U> {
      private final MemoryModuleType<U> type;
      private final Optional<? extends ExpirableValue<U>> value;

      static <U> Brain.MemoryValue<U> createUnchecked(MemoryModuleType<U> var0, Optional<? extends ExpirableValue<?>> var1) {
         return new Brain.MemoryValue<>(â˜ƒ, â˜ƒ);
      }

      MemoryValue(MemoryModuleType<U> var1, Optional<? extends ExpirableValue<U>> var2) {
         this.type = â˜ƒ;
         this.value = â˜ƒ;
      }

      void setMemoryInternal(Brain<?> var1) {
         â˜ƒ.setMemoryInternal(this.type, this.value);
      }

      public <T> void serialize(DynamicOps<T> var1, RecordBuilder<T> var2) {
         this.type
            .getCodec()
            .ifPresent(var3 -> this.value.ifPresent(var4 -> â˜ƒ.add(Registry.MEMORY_MODULE_TYPE.encodeStart(â˜ƒ, this.type), var3.encodeStart(â˜ƒ, var4))));
      }
   }

   public static final class Provider<E extends LivingEntity> {
      private final Collection<? extends MemoryModuleType<?>> memoryTypes;
      private final Collection<? extends SensorType<? extends Sensor<? super E>>> sensorTypes;
      private final Codec<Brain<E>> codec;

      Provider(Collection<? extends MemoryModuleType<?>> var1, Collection<? extends SensorType<? extends Sensor<? super E>>> var2) {
         this.memoryTypes = â˜ƒ;
         this.sensorTypes = â˜ƒ;
         this.codec = Brain.codec(â˜ƒ, â˜ƒ);
      }

      public Brain<E> makeBrain(Dynamic<?> var1) {
         return (Brain<E>)this.codec
            .parse(â˜ƒ)
            .resultOrPartial(Brain.LOGGER::error)
            .orElseGet(() -> new Brain(this.memoryTypes, this.sensorTypes, ImmutableList.of(), () -> this.codec));
      }
   }
}
