package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;

public class AcquirePoi extends Behavior<PathfinderMob> {
   private static final int BATCH_SIZE = 5;
   private static final int RATE = 20;
   public static final int SCAN_RANGE = 48;
   private final PoiType poiType;
   private final MemoryModuleType<GlobalPos> memoryToAcquire;
   private final boolean onlyIfAdult;
   private final Optional<Byte> onPoiAcquisitionEvent;
   private long nextScheduledStart;
   private final Long2ObjectMap<AcquirePoi.JitteredLinearRetry> batchCache = new Long2ObjectOpenHashMap<>();

   public AcquirePoi(PoiType var1, MemoryModuleType<GlobalPos> var2, MemoryModuleType<GlobalPos> var3, boolean var4, Optional<Byte> var5) {
      super(constructEntryConditionMap(â˜ƒ, â˜ƒ));
      this.poiType = â˜ƒ;
      this.memoryToAcquire = â˜ƒ;
      this.onlyIfAdult = â˜ƒ;
      this.onPoiAcquisitionEvent = â˜ƒ;
   }

   public AcquirePoi(PoiType var1, MemoryModuleType<GlobalPos> var2, boolean var3, Optional<Byte> var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static ImmutableMap<MemoryModuleType<?>, MemoryStatus> constructEntryConditionMap(MemoryModuleType<GlobalPos> var0, MemoryModuleType<GlobalPos> var1) {
      Builder<MemoryModuleType<?>, MemoryStatus> â˜ƒ = ImmutableMap.builder();
      â˜ƒ.put(â˜ƒ, MemoryStatus.VALUE_ABSENT);
      if (â˜ƒ != â˜ƒ) {
         â˜ƒ.put(â˜ƒ, MemoryStatus.VALUE_ABSENT);
      }

      return â˜ƒ.build();
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      if (this.onlyIfAdult && â˜ƒ.isBaby()) {
         return false;
      } else if (this.nextScheduledStart == 0L) {
         this.nextScheduledStart = â˜ƒ.level.getGameTime() + (long)â˜ƒ.random.nextInt(20);
         return false;
      } else {
         return â˜ƒ.getGameTime() >= this.nextScheduledStart;
      }
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      this.nextScheduledStart = â˜ƒ + 20L + (long)â˜ƒ.getRandom().nextInt(20);
      PoiManager â˜ƒ = â˜ƒ.getPoiManager();
      this.batchCache.long2ObjectEntrySet().removeIf(var2x -> !((AcquirePoi.JitteredLinearRetry)var2x.getValue()).isStillValid(â˜ƒ));
      Predicate<BlockPos> â˜ƒx = var3x -> {
         AcquirePoi.JitteredLinearRetry â˜ƒ = this.batchCache.get(var3x.asLong());
         if (â˜ƒ == null) {
            return true;
         } else if (!â˜ƒ.shouldRetry(â˜ƒ)) {
            return false;
         } else {
            â˜ƒ.markAttempt(â˜ƒ);
            return true;
         }
      };
      Set<BlockPos> â˜ƒxx = (Set)â˜ƒ.findAllClosestFirst(this.poiType.getPredicate(), â˜ƒx, â˜ƒ.blockPosition(), 48, PoiManager.Occupancy.HAS_SPACE)
         .limit(5L)
         .collect(Collectors.toSet());
      Path â˜ƒxxx = â˜ƒ.getNavigation().createPath(â˜ƒxx, this.poiType.getValidRange());
      if (â˜ƒxxx != null && â˜ƒxxx.canReach()) {
         BlockPos â˜ƒxxxx = â˜ƒxxx.getTarget();
         â˜ƒ.getType(â˜ƒxxxx).ifPresent(var5x -> {
            â˜ƒ.take(this.poiType.getPredicate(), var1x -> var1x.equals(â˜ƒ), â˜ƒ, 1);
            â˜ƒ.getBrain().setMemory(this.memoryToAcquire, GlobalPos.of(â˜ƒ.dimension(), â˜ƒ));
            this.onPoiAcquisitionEvent.ifPresent(var2x -> â˜ƒ.broadcastEntityEvent(â˜ƒ, var2x));
            this.batchCache.clear();
            DebugPackets.sendPoiTicketCountPacket(â˜ƒ, â˜ƒ);
         });
      } else {
         for(BlockPos â˜ƒ : â˜ƒxx) {
            this.batchCache.computeIfAbsent(â˜ƒ.asLong(), var3x -> new AcquirePoi.JitteredLinearRetry(â˜ƒ.level.random, â˜ƒ));
         }
      }
   }

   static class JitteredLinearRetry {
      private static final int MIN_INTERVAL_INCREASE = 40;
      private static final int MAX_INTERVAL_INCREASE = 80;
      private static final int MAX_RETRY_PATHFINDING_INTERVAL = 400;
      private final Random random;
      private long previousAttemptTimestamp;
      private long nextScheduledAttemptTimestamp;
      private int currentDelay;

      JitteredLinearRetry(Random var1, long var2) {
         this.random = â˜ƒ;
         this.markAttempt(â˜ƒ);
      }

      public void markAttempt(long var1) {
         this.previousAttemptTimestamp = â˜ƒ;
         int â˜ƒ = this.currentDelay + this.random.nextInt(40) + 40;
         this.currentDelay = Math.min(â˜ƒ, 400);
         this.nextScheduledAttemptTimestamp = â˜ƒ + (long)this.currentDelay;
      }

      public boolean isStillValid(long var1) {
         return â˜ƒ - this.previousAttemptTimestamp < 400L;
      }

      public boolean shouldRetry(long var1) {
         return â˜ƒ >= this.nextScheduledAttemptTimestamp;
      }

      public String toString() {
         return "RetryMarker{, previousAttemptAt="
            + this.previousAttemptTimestamp
            + ", nextScheduledAttemptAt="
            + this.nextScheduledAttemptTimestamp
            + ", currentDelay="
            + this.currentDelay
            + "}";
      }
   }
}
