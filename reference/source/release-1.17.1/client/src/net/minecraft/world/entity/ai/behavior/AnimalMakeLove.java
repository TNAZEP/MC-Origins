package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.Animal;

public class AnimalMakeLove extends Behavior<Animal> {
   private static final int BREED_RANGE = 3;
   private static final int MIN_DURATION = 60;
   private static final int MAX_DURATION = 110;
   private final EntityType<? extends Animal> partnerType;
   private final float speedModifier;
   private long spawnChildAtTime;

   public AnimalMakeLove(EntityType<? extends Animal> var1, float var2) {
      super(
         ImmutableMap.of(
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.BREED_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED
         ),
         110
      );
      this.partnerType = â˜ƒ;
      this.speedModifier = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Animal var2) {
      return â˜ƒ.isInLove() && this.findValidBreedPartner(â˜ƒ).isPresent();
   }

   protected void start(ServerLevel var1, Animal var2, long var3) {
      Animal â˜ƒ = (Animal)this.findValidBreedPartner(â˜ƒ).get();
      â˜ƒ.getBrain().setMemory(MemoryModuleType.BREED_TARGET, â˜ƒ);
      â˜ƒ.getBrain().setMemory(MemoryModuleType.BREED_TARGET, â˜ƒ);
      BehaviorUtils.lockGazeAndWalkToEachOther(â˜ƒ, â˜ƒ, this.speedModifier);
      int â˜ƒx = 60 + â˜ƒ.getRandom().nextInt(50);
      this.spawnChildAtTime = â˜ƒ + (long)â˜ƒx;
   }

   protected boolean canStillUse(ServerLevel var1, Animal var2, long var3) {
      if (!this.hasBreedTargetOfRightType(â˜ƒ)) {
         return false;
      } else {
         Animal â˜ƒ = this.getBreedTarget(â˜ƒ);
         return â˜ƒ.isAlive() && â˜ƒ.canMate(â˜ƒ) && BehaviorUtils.entityIsVisible(â˜ƒ.getBrain(), â˜ƒ) && â˜ƒ <= this.spawnChildAtTime;
      }
   }

   protected void tick(ServerLevel var1, Animal var2, long var3) {
      Animal â˜ƒ = this.getBreedTarget(â˜ƒ);
      BehaviorUtils.lockGazeAndWalkToEachOther(â˜ƒ, â˜ƒ, this.speedModifier);
      if (â˜ƒ.closerThan(â˜ƒ, 3.0)) {
         if (â˜ƒ >= this.spawnChildAtTime) {
            â˜ƒ.spawnChildFromBreeding(â˜ƒ, â˜ƒ);
            â˜ƒ.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
            â˜ƒ.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
         }
      }
   }

   protected void stop(ServerLevel var1, Animal var2, long var3) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
      this.spawnChildAtTime = 0L;
   }

   private Animal getBreedTarget(Animal var1) {
      return (Animal)â˜ƒ.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
   }

   private boolean hasBreedTargetOfRightType(Animal var1) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      return â˜ƒ.hasMemoryValue(MemoryModuleType.BREED_TARGET)
         && ((AgeableMob)â˜ƒ.getMemory(MemoryModuleType.BREED_TARGET).get()).getType() == this.partnerType;
   }

   private Optional<? extends Animal> findValidBreedPartner(Animal var1) {
      return ((List)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get())
         .stream()
         .filter(var1x -> var1x.getType() == this.partnerType)
         .map(var0 -> (Animal)var0)
         .filter(â˜ƒ::canMate)
         .findFirst();
   }
}
