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
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class InteractWith<E extends LivingEntity, T extends LivingEntity> extends Behavior<E> {
   private final int maxDist;
   private final float speedModifier;
   private final EntityType<? extends T> type;
   private final int interactionRangeSqr;
   private final Predicate<T> targetFilter;
   private final Predicate<E> selfFilter;
   private final MemoryModuleType<T> memory;

   public InteractWith(EntityType<? extends T> var1, int var2, Predicate<E> var3, Predicate<T> var4, MemoryModuleType<T> var5, float var6, int var7) {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryStatus.VALUE_PRESENT
         )
      );
      this.type = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.interactionRangeSqr = â˜ƒ * â˜ƒ;
      this.maxDist = â˜ƒ;
      this.targetFilter = â˜ƒ;
      this.selfFilter = â˜ƒ;
      this.memory = â˜ƒ;
   }

   public static <T extends LivingEntity> InteractWith<LivingEntity, T> of(
      EntityType<? extends T> var0, int var1, MemoryModuleType<T> var2, float var3, int var4
   ) {
      return new InteractWith<>(â˜ƒ, â˜ƒ, var0x -> true, var0x -> true, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static <T extends LivingEntity> InteractWith<LivingEntity, T> of(
      EntityType<? extends T> var0, int var1, Predicate<T> var2, MemoryModuleType<T> var3, float var4, int var5
   ) {
      return new InteractWith<>(â˜ƒ, â˜ƒ, var0x -> true, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      return this.selfFilter.test(â˜ƒ) && this.seesAtLeastOneValidTarget(â˜ƒ);
   }

   private boolean seesAtLeastOneValidTarget(E var1) {
      List<LivingEntity> â˜ƒ = (List)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get();
      return â˜ƒ.stream().anyMatch(this::isTargetValid);
   }

   private boolean isTargetValid(LivingEntity var1) {
      return this.type.equals(â˜ƒ.getType()) && this.targetFilter.test(â˜ƒ);
   }

   @Override
   protected void start(ServerLevel var1, E var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
         .ifPresent(
            var3x -> var3x.stream()
                  .filter(var1x -> this.type.equals(var1x.getType()))
                  .map(var0 -> var0)
                  .filter(var2x -> var2x.distanceToSqr(â˜ƒ) <= (double)this.interactionRangeSqr)
                  .filter(this.targetFilter)
                  .findFirst()
                  .ifPresent(var2x -> {
                     â˜ƒ.setMemory(this.memory, (T)var2x);
                     â˜ƒ.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(var2x, true));
                     â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(var2x, false), this.speedModifier, this.maxDist));
                  })
         );
   }
}
