package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class LocateHidingPlace extends Behavior<LivingEntity> {
   private final float speedModifier;
   private final int radius;
   private final int closeEnoughDist;
   private Optional<BlockPos> currentPos = Optional.empty();

   public LocateHidingPlace(int var1, float var2, int var3) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.HOME,
            MemoryStatus.REGISTERED,
            MemoryModuleType.HIDING_PLACE,
            MemoryStatus.REGISTERED
         )
      );
      this.radius = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.closeEnoughDist = â˜ƒ;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      Optional<BlockPos> â˜ƒ = â˜ƒ.getPoiManager()
         .find(var0 -> var0 == PoiType.HOME, var0 -> true, â˜ƒ.blockPosition(), this.closeEnoughDist + 1, PoiManager.Occupancy.ANY);
      if (â˜ƒ.isPresent() && ((BlockPos)â˜ƒ.get()).closerThan(â˜ƒ.position(), (double)this.closeEnoughDist)) {
         this.currentPos = â˜ƒ;
      } else {
         this.currentPos = Optional.empty();
      }

      return true;
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      Optional<BlockPos> â˜ƒx = this.currentPos;
      if (!â˜ƒx.isPresent()) {
         â˜ƒx = â˜ƒ.getPoiManager()
            .getRandom(var0 -> var0 == PoiType.HOME, var0 -> true, PoiManager.Occupancy.ANY, â˜ƒ.blockPosition(), this.radius, â˜ƒ.getRandom());
         if (!â˜ƒx.isPresent()) {
            Optional<GlobalPos> â˜ƒxx = â˜ƒ.getMemory(MemoryModuleType.HOME);
            if (â˜ƒxx.isPresent()) {
               â˜ƒx = Optional.of(((GlobalPos)â˜ƒxx.get()).pos());
            }
         }
      }

      if (â˜ƒx.isPresent()) {
         â˜ƒ.eraseMemory(MemoryModuleType.PATH);
         â˜ƒ.eraseMemory(MemoryModuleType.LOOK_TARGET);
         â˜ƒ.eraseMemory(MemoryModuleType.BREED_TARGET);
         â˜ƒ.eraseMemory(MemoryModuleType.INTERACTION_TARGET);
         â˜ƒ.setMemory(MemoryModuleType.HIDING_PLACE, GlobalPos.of(â˜ƒ.dimension(), (BlockPos)â˜ƒx.get()));
         if (!((BlockPos)â˜ƒx.get()).closerThan(â˜ƒ.position(), (double)this.closeEnoughDist)) {
            â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget((BlockPos)â˜ƒx.get(), this.speedModifier, this.closeEnoughDist));
         }
      }
   }
}
