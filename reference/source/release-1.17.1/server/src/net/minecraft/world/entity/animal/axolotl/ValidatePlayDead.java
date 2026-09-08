package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ValidatePlayDead extends Behavior<Axolotl> {
   public ValidatePlayDead() {
      super(ImmutableMap.of(MemoryModuleType.PLAY_DEAD_TICKS, MemoryStatus.VALUE_PRESENT));
   }

   protected void start(ServerLevel var1, Axolotl var2, long var3) {
      Brain<Axolotl> â˜ƒ = â˜ƒ.getBrain();
      int â˜ƒx = â˜ƒ.getMemory(MemoryModuleType.PLAY_DEAD_TICKS).get();
      if (â˜ƒx <= 0) {
         â˜ƒ.eraseMemory(MemoryModuleType.PLAY_DEAD_TICKS);
         â˜ƒ.eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
         â˜ƒ.useDefaultActivity();
      } else {
         â˜ƒ.setMemory(MemoryModuleType.PLAY_DEAD_TICKS, â˜ƒx - 1);
      }
   }
}
