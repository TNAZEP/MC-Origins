package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class CopyMemoryWithExpiry<E extends Mob, T> extends Behavior<E> {
   private final Predicate<E> predicate;
   private final MemoryModuleType<? extends T> sourceMemory;
   private final MemoryModuleType<T> targetMemory;
   private final UniformInt durationOfCopy;

   public CopyMemoryWithExpiry(Predicate<E> var1, MemoryModuleType<? extends T> var2, MemoryModuleType<T> var3, UniformInt var4) {
      super(ImmutableMap.of(â˜ƒ, MemoryStatus.VALUE_PRESENT, â˜ƒ, MemoryStatus.VALUE_ABSENT));
      this.predicate = â˜ƒ;
      this.sourceMemory = â˜ƒ;
      this.targetMemory = â˜ƒ;
      this.durationOfCopy = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      return this.predicate.test(â˜ƒ);
   }

   protected void start(ServerLevel var1, E var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.setMemoryWithExpiry(this.targetMemory, (T)â˜ƒ.getMemory(this.sourceMemory).get(), (long)this.durationOfCopy.sample(â˜ƒ.random));
   }
}
