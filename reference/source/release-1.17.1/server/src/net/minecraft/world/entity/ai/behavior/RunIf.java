package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class RunIf<E extends LivingEntity> extends Behavior<E> {
   private final Predicate<E> predicate;
   private final Behavior<? super E> wrappedBehavior;
   private final boolean checkWhileRunningAlso;

   public RunIf(Map<MemoryModuleType<?>, MemoryStatus> var1, Predicate<E> var2, Behavior<? super E> var3, boolean var4) {
      super(mergeMaps(â˜ƒ, â˜ƒ.entryCondition));
      this.predicate = â˜ƒ;
      this.wrappedBehavior = â˜ƒ;
      this.checkWhileRunningAlso = â˜ƒ;
   }

   private static Map<MemoryModuleType<?>, MemoryStatus> mergeMaps(Map<MemoryModuleType<?>, MemoryStatus> var0, Map<MemoryModuleType<?>, MemoryStatus> var1) {
      Map<MemoryModuleType<?>, MemoryStatus> â˜ƒ = Maps.newHashMap();
      â˜ƒ.putAll(â˜ƒ);
      â˜ƒ.putAll(â˜ƒ);
      return â˜ƒ;
   }

   public RunIf(Predicate<E> var1, Behavior<? super E> var2, boolean var3) {
      this(ImmutableMap.of(), â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public RunIf(Predicate<E> var1, Behavior<? super E> var2) {
      this(ImmutableMap.of(), â˜ƒ, â˜ƒ, false);
   }

   public RunIf(Map<MemoryModuleType<?>, MemoryStatus> var1, Behavior<? super E> var2) {
      this(â˜ƒ, var0 -> true, â˜ƒ, false);
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      return this.predicate.test(â˜ƒ) && this.wrappedBehavior.checkExtraStartConditions(â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean canStillUse(ServerLevel var1, E var2, long var3) {
      return this.checkWhileRunningAlso && this.predicate.test(â˜ƒ) && this.wrappedBehavior.canStillUse(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean timedOut(long var1) {
      return false;
   }

   @Override
   protected void start(ServerLevel var1, E var2, long var3) {
      this.wrappedBehavior.start(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void tick(ServerLevel var1, E var2, long var3) {
      this.wrappedBehavior.tick(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void stop(ServerLevel var1, E var2, long var3) {
      this.wrappedBehavior.stop(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public String toString() {
      return "RunIf: " + this.wrappedBehavior;
   }
}
