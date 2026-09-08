package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class SetLookAndInteract extends Behavior<LivingEntity> {
   private final EntityType<?> type;
   private final int interactionRangeSqr;
   private final Predicate<LivingEntity> targetFilter;
   private final Predicate<LivingEntity> selfFilter;

   public SetLookAndInteract(EntityType<?> var1, int var2, Predicate<LivingEntity> var3, Predicate<LivingEntity> var4) {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryStatus.VALUE_PRESENT
         )
      );
      this.type = â˜ƒ;
      this.interactionRangeSqr = â˜ƒ * â˜ƒ;
      this.targetFilter = â˜ƒ;
      this.selfFilter = â˜ƒ;
   }

   public SetLookAndInteract(EntityType<?> var1, int var2) {
      this(â˜ƒ, â˜ƒ, var0 -> true, var0 -> true);
   }

   @Override
   public boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      return this.selfFilter.test(â˜ƒ) && this.getVisibleEntities(â˜ƒ).stream().anyMatch(this::isMatchingTarget);
   }

   @Override
   public void start(ServerLevel var1, LivingEntity var2, long var3) {
      super.start(â˜ƒ, â˜ƒ, â˜ƒ);
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
         .ifPresent(
            var3x -> var3x.stream()
                  .filter(var2x -> var2x.distanceToSqr(â˜ƒ) <= (double)this.interactionRangeSqr)
                  .filter(this::isMatchingTarget)
                  .findFirst()
                  .ifPresent(var1x -> {
                     â˜ƒ.setMemory(MemoryModuleType.INTERACTION_TARGET, var1x);
                     â˜ƒ.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(var1x, true));
                  })
         );
   }

   private boolean isMatchingTarget(LivingEntity var1) {
      return this.type.equals(â˜ƒ.getType()) && this.targetFilter.test(â˜ƒ);
   }

   private List<LivingEntity> getVisibleEntities(LivingEntity var1) {
      return (List<LivingEntity>)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get();
   }
}
