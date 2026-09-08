package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;

public class SetClosestHomeAsWalkTarget extends Behavior<LivingEntity> {
   private static final int CACHE_TIMEOUT = 40;
   private static final int BATCH_SIZE = 5;
   private static final int RATE = 20;
   private static final int OK_DISTANCE_SQR = 4;
   private final float speedModifier;
   private final Long2LongMap batchCache = new Long2LongOpenHashMap();
   private int triedCount;
   private long lastUpdate;

   public SetClosestHomeAsWalkTarget(float var1) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT));
      this.speedModifier = â˜ƒ;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      if (â˜ƒ.getGameTime() - this.lastUpdate < 20L) {
         return false;
      } else {
         PathfinderMob â˜ƒ = (PathfinderMob)â˜ƒ;
         PoiManager â˜ƒx = â˜ƒ.getPoiManager();
         Optional<BlockPos> â˜ƒxx = â˜ƒx.findClosest(PoiType.HOME.getPredicate(), â˜ƒ.blockPosition(), 48, PoiManager.Occupancy.ANY);
         return â˜ƒxx.isPresent() && !(((BlockPos)â˜ƒxx.get()).distSqr(â˜ƒ.blockPosition()) <= 4.0);
      }
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      this.triedCount = 0;
      this.lastUpdate = â˜ƒ.getGameTime() + (long)â˜ƒ.getRandom().nextInt(20);
      PathfinderMob â˜ƒ = (PathfinderMob)â˜ƒ;
      PoiManager â˜ƒx = â˜ƒ.getPoiManager();
      Predicate<BlockPos> â˜ƒxx = var1x -> {
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
      Stream<BlockPos> â˜ƒxxx = â˜ƒx.findAll(PoiType.HOME.getPredicate(), â˜ƒxx, â˜ƒ.blockPosition(), 48, PoiManager.Occupancy.ANY);
      Path â˜ƒxxxx = â˜ƒ.getNavigation().createPath(â˜ƒxxx, PoiType.HOME.getValidRange());
      if (â˜ƒxxxx != null && â˜ƒxxxx.canReach()) {
         BlockPos â˜ƒxxxxx = â˜ƒxxxx.getTarget();
         Optional<PoiType> â˜ƒxxxxxx = â˜ƒx.getType(â˜ƒxxxxx);
         if (â˜ƒxxxxxx.isPresent()) {
            â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒxxxxx, this.speedModifier, 1));
            DebugPackets.sendPoiTicketCountPacket(â˜ƒ, â˜ƒxxxxx);
         }
      } else if (this.triedCount < 5) {
         this.batchCache.long2LongEntrySet().removeIf(var1x -> var1x.getLongValue() < this.lastUpdate);
      }
   }
}
