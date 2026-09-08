package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;

public class HoglinSpecificSensor extends Sensor<Hoglin> {
   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(
         MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
         MemoryModuleType.NEAREST_REPELLENT,
         MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN,
         MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS,
         MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT,
         MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT
      );
   }

   protected void doTick(ServerLevel var1, Hoglin var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_REPELLENT, this.findNearestRepellent(â˜ƒ, â˜ƒ));
      Optional<Piglin> â˜ƒx = Optional.empty();
      int â˜ƒxx = 0;
      List<Hoglin> â˜ƒxxx = Lists.<Hoglin>newArrayList();

      for(LivingEntity â˜ƒxxxx : (List)â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(Lists.newArrayList())) {
         if (â˜ƒxxxx instanceof Piglin && !â˜ƒxxxx.isBaby()) {
            ++â˜ƒxx;
            if (!â˜ƒx.isPresent()) {
               â˜ƒx = Optional.of((Piglin)â˜ƒxxxx);
            }
         }

         if (â˜ƒxxxx instanceof Hoglin && !â˜ƒxxxx.isBaby()) {
            â˜ƒxxx.add((Hoglin)â˜ƒxxxx);
         }
      }

      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, â˜ƒx);
      â˜ƒ.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, â˜ƒxxx);
      â˜ƒ.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, â˜ƒxx);
      â˜ƒ.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, â˜ƒxxx.size());
   }

   private Optional<BlockPos> findNearestRepellent(ServerLevel var1, Hoglin var2) {
      return BlockPos.findClosestMatch(â˜ƒ.blockPosition(), 8, 4, var1x -> â˜ƒ.getBlockState(var1x).is(BlockTags.HOGLIN_REPELLENTS));
   }
}
