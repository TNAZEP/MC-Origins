package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PlayDead extends Behavior<Axolotl> {
   public PlayDead() {
      super(ImmutableMap.of(MemoryModuleType.PLAY_DEAD_TICKS, MemoryStatus.VALUE_PRESENT, MemoryModuleType.HURT_BY_ENTITY, MemoryStatus.VALUE_PRESENT), 200);
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Axolotl var2) {
      return â˜ƒ.isInWaterOrBubble();
   }

   protected boolean canStillUse(ServerLevel var1, Axolotl var2, long var3) {
      return â˜ƒ.isInWaterOrBubble() && â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.PLAY_DEAD_TICKS);
   }

   protected void start(ServerLevel var1, Axolotl var2, long var3) {
      Brain<Axolotl> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.eraseMemory(MemoryModuleType.LOOK_TARGET);
      â˜ƒ.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
   }
}
