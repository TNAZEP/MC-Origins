package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class DismountOrSkipMounting<E extends LivingEntity, T extends Entity> extends Behavior<E> {
   private final int maxWalkDistToRideTarget;
   private final BiPredicate<E, Entity> dontRideIf;

   public DismountOrSkipMounting(int var1, BiPredicate<E, Entity> var2) {
      super(ImmutableMap.of(MemoryModuleType.RIDE_TARGET, MemoryStatus.REGISTERED));
      this.maxWalkDistToRideTarget = â˜ƒ;
      this.dontRideIf = â˜ƒ;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      Entity â˜ƒ = â˜ƒ.getVehicle();
      Entity â˜ƒx = (Entity)â˜ƒ.getBrain().getMemory(MemoryModuleType.RIDE_TARGET).orElse(null);
      if (â˜ƒ == null && â˜ƒx == null) {
         return false;
      } else {
         Entity â˜ƒ = â˜ƒ == null ? â˜ƒx : â˜ƒ;
         return !this.isVehicleValid(â˜ƒ, â˜ƒ) || this.dontRideIf.test(â˜ƒ, â˜ƒ);
      }
   }

   private boolean isVehicleValid(E var1, Entity var2) {
      return â˜ƒ.isAlive() && â˜ƒ.closerThan(â˜ƒ, (double)this.maxWalkDistToRideTarget) && â˜ƒ.level == â˜ƒ.level;
   }

   @Override
   protected void start(ServerLevel var1, E var2, long var3) {
      â˜ƒ.stopRiding();
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.RIDE_TARGET);
   }
}
