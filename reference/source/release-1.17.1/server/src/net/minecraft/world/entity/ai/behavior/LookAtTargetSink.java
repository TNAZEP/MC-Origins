package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class LookAtTargetSink extends Behavior<Mob> {
   public LookAtTargetSink(int var1, int var2) {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_PRESENT), â˜ƒ, â˜ƒ);
   }

   protected boolean canStillUse(ServerLevel var1, Mob var2, long var3) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.LOOK_TARGET).filter(var1x -> var1x.isVisibleBy(â˜ƒ)).isPresent();
   }

   protected void stop(ServerLevel var1, Mob var2, long var3) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
   }

   protected void tick(ServerLevel var1, Mob var2, long var3) {
      â˜ƒ.getBrain().getMemory(MemoryModuleType.LOOK_TARGET).ifPresent(var1x -> â˜ƒ.getLookControl().setLookAt(var1x.currentPosition()));
   }
}
