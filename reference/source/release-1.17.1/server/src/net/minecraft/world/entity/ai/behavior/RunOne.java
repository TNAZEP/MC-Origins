package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class RunOne<E extends LivingEntity> extends GateBehavior<E> {
   public RunOne(List<Pair<Behavior<? super E>, Integer>> var1) {
      this(ImmutableMap.of(), â˜ƒ);
   }

   public RunOne(Map<MemoryModuleType<?>, MemoryStatus> var1, List<Pair<Behavior<? super E>, Integer>> var2) {
      super(â˜ƒ, ImmutableSet.of(), GateBehavior.OrderPolicy.SHUFFLED, GateBehavior.RunningPolicy.RUN_ONE, â˜ƒ);
   }
}
