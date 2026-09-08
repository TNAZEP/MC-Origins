package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SocializeAtBell extends Behavior<LivingEntity> {
   private static final float SPEED_MODIFIER = 0.3F;

   public SocializeAtBell() {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.MEETING_POINT,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryStatus.VALUE_ABSENT
         )
      );
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      Optional<GlobalPos> â˜ƒx = â˜ƒ.getMemory(MemoryModuleType.MEETING_POINT);
      return â˜ƒ.getRandom().nextInt(100) == 0
         && â˜ƒx.isPresent()
         && â˜ƒ.dimension() == ((GlobalPos)â˜ƒx.get()).dimension()
         && ((GlobalPos)â˜ƒx.get()).pos().closerThan(â˜ƒ.position(), 4.0)
         && ((List)â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get()).stream().anyMatch(var0 -> EntityType.VILLAGER.equals(var0.getType()));
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
         .ifPresent(
            var2x -> var2x.stream()
                  .filter(var0x -> EntityType.VILLAGER.equals(var0x.getType()))
                  .filter(var1x -> var1x.distanceToSqr(â˜ƒ) <= 32.0)
                  .findFirst()
                  .ifPresent(var1x -> {
                     â˜ƒ.setMemory(MemoryModuleType.INTERACTION_TARGET, var1x);
                     â˜ƒ.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(var1x, true));
                     â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(var1x, false), 0.3F, 1));
                  })
         );
   }
}
