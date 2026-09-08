package net.minecraft.world.level.timers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.primitives.UnsignedLong;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerQueue<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String CALLBACK_DATA_TAG = "Callback";
   private static final String TIMER_NAME_TAG = "Name";
   private static final String TIMER_TRIGGER_TIME_TAG = "TriggerTime";
   private final TimerCallbacks<T> callbacksRegistry;
   private final Queue<TimerQueue.Event<T>> queue = new PriorityQueue(createComparator());
   private UnsignedLong sequentialId = UnsignedLong.ZERO;
   private final Table<String, Long, TimerQueue.Event<T>> events = HashBasedTable.create();

   private static <T> Comparator<TimerQueue.Event<T>> createComparator() {
      return Comparator.comparingLong(var0 -> var0.triggerTime).thenComparing(var0 -> var0.sequentialId);
   }

   public TimerQueue(TimerCallbacks<T> var1, Stream<Dynamic<Tag>> var2) {
      this(â˜ƒ);
      this.queue.clear();
      this.events.clear();
      this.sequentialId = UnsignedLong.ZERO;
      â˜ƒ.forEach(var1x -> {
         if (!(var1x.getValue() instanceof CompoundTag)) {
            LOGGER.warn("Invalid format of events: {}", var1x);
         } else {
            this.loadEvent((CompoundTag)var1x.getValue());
         }
      });
   }

   public TimerQueue(TimerCallbacks<T> var1) {
      this.callbacksRegistry = â˜ƒ;
   }

   public void tick(T var1, long var2) {
      while(true) {
         TimerQueue.Event<T> â˜ƒ = (TimerQueue.Event)this.queue.peek();
         if (â˜ƒ == null || â˜ƒ.triggerTime > â˜ƒ) {
            return;
         }

         this.queue.remove();
         this.events.remove(â˜ƒ.id, â˜ƒ);
         â˜ƒ.callback.handle(â˜ƒ, this, â˜ƒ);
      }
   }

   public void schedule(String var1, long var2, TimerCallback<T> var4) {
      if (!this.events.contains(â˜ƒ, â˜ƒ)) {
         this.sequentialId = this.sequentialId.plus(UnsignedLong.ONE);
         TimerQueue.Event<T> â˜ƒ = new TimerQueue.Event<>(â˜ƒ, this.sequentialId, â˜ƒ, â˜ƒ);
         this.events.put(â˜ƒ, â˜ƒ, â˜ƒ);
         this.queue.add(â˜ƒ);
      }
   }

   public int remove(String var1) {
      Collection<TimerQueue.Event<T>> â˜ƒ = this.events.row(â˜ƒ).values();
      â˜ƒ.forEach(this.queue::remove);
      int â˜ƒx = â˜ƒ.size();
      â˜ƒ.clear();
      return â˜ƒx;
   }

   public Set<String> getEventsIds() {
      return Collections.unmodifiableSet(this.events.rowKeySet());
   }

   private void loadEvent(CompoundTag var1) {
      CompoundTag â˜ƒ = â˜ƒ.getCompound("Callback");
      TimerCallback<T> â˜ƒx = this.callbacksRegistry.deserialize(â˜ƒ);
      if (â˜ƒx != null) {
         String â˜ƒxx = â˜ƒ.getString("Name");
         long â˜ƒxxx = â˜ƒ.getLong("TriggerTime");
         this.schedule(â˜ƒxx, â˜ƒxxx, â˜ƒx);
      }
   }

   private CompoundTag storeEvent(TimerQueue.Event<T> var1) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("Name", â˜ƒ.id);
      â˜ƒ.putLong("TriggerTime", â˜ƒ.triggerTime);
      â˜ƒ.put("Callback", this.callbacksRegistry.serialize(â˜ƒ.callback));
      return â˜ƒ;
   }

   public ListTag store() {
      ListTag â˜ƒ = new ListTag();
      this.queue.stream().sorted(createComparator()).map(this::storeEvent).forEach(â˜ƒ::add);
      return â˜ƒ;
   }

   public static class Event<T> {
      public final long triggerTime;
      public final UnsignedLong sequentialId;
      public final String id;
      public final TimerCallback<T> callback;

      Event(long var1, UnsignedLong var3, String var4, TimerCallback<T> var5) {
         this.triggerTime = â˜ƒ;
         this.sequentialId = â˜ƒ;
         this.id = â˜ƒ;
         this.callback = â˜ƒ;
      }
   }
}
