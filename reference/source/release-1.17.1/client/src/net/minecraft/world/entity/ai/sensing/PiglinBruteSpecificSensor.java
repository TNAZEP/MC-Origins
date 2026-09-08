package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;

public class PiglinBruteSpecificSensor extends Sensor<LivingEntity> {
   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEARBY_ADULT_PIGLINS);
   }

   @Override
   protected void doTick(ServerLevel var1, LivingEntity var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      Optional<Mob> â˜ƒx = Optional.empty();
      List<AbstractPiglin> â˜ƒxx = Lists.<AbstractPiglin>newArrayList();

      for(LivingEntity â˜ƒxxx : (List)â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(ImmutableList.of())) {
         if (â˜ƒxxx instanceof WitherSkeleton || â˜ƒxxx instanceof WitherBoss) {
            â˜ƒx = Optional.of((Mob)â˜ƒxxx);
            break;
         }
      }

      for(LivingEntity â˜ƒxxx : (List)â˜ƒ.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of())) {
         if (â˜ƒxxx instanceof AbstractPiglin && ((AbstractPiglin)â˜ƒxxx).isAdult()) {
            â˜ƒxx.add((AbstractPiglin)â˜ƒxxx);
         }
      }

      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, â˜ƒx);
      â˜ƒ.setMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS, â˜ƒxx);
   }
}
