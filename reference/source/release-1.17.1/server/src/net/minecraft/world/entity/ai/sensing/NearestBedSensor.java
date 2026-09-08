package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;

public class NearestBedSensor extends Sensor<Mob> {
   private static final int CACHE_TIMEOUT = 40;
   private static final int BATCH_SIZE = 5;
   private static final int RATE = 20;
   private final Long2LongMap batchCache = new Long2LongOpenHashMap();
   private int triedCount;
   private long lastUpdate;

   public NearestBedSensor() {
      super(20);
   }

   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_BED);
   }

   protected void doTick(ServerLevel var1, Mob var2) {
      if (â˜ƒ.isBaby()) {
         this.triedCount = 0;
         this.lastUpdate = â˜ƒ.getGameTime() + (long)â˜ƒ.getRandom().nextInt(20);
         PoiManager â˜ƒ = â˜ƒ.getPoiManager();
         Predicate<BlockPos> â˜ƒx = var1x -> {
            long â˜ƒ = var1x.asLong();
            if (this.batchCache.containsKey(â˜ƒ)) {
               return false;
            } else if (++this.triedCount >= 5) {
               return false;
            } else {
               this.batchCache.put(â˜ƒ, this.lastUpdate + 40L);
               return true;
            }
         };
         Stream<BlockPos> â˜ƒxx = â˜ƒ.findAll(PoiType.HOME.getPredicate(), â˜ƒx, â˜ƒ.blockPosition(), 48, PoiManager.Occupancy.ANY);
         Path â˜ƒxxx = â˜ƒ.getNavigation().createPath(â˜ƒxx, PoiType.HOME.getValidRange());
         if (â˜ƒxxx != null && â˜ƒxxx.canReach()) {
            BlockPos â˜ƒxxxx = â˜ƒxxx.getTarget();
            Optional<PoiType> â˜ƒxxxxx = â˜ƒ.getType(â˜ƒxxxx);
            if (â˜ƒxxxxx.isPresent()) {
               â˜ƒ.getBrain().setMemory(MemoryModuleType.NEAREST_BED, â˜ƒxxxx);
            }
         } else if (this.triedCount < 5) {
            this.batchCache.long2LongEntrySet().removeIf(var1x -> var1x.getLongValue() < this.lastUpdate);
         }
      }
   }
}
