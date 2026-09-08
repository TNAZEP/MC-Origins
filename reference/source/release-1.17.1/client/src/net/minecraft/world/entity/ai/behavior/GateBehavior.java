package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class GateBehavior<E extends LivingEntity> extends Behavior<E> {
   private final Set<MemoryModuleType<?>> exitErasedMemories;
   private final GateBehavior.OrderPolicy orderPolicy;
   private final GateBehavior.RunningPolicy runningPolicy;
   private final ShufflingList<Behavior<? super E>> behaviors = new ShufflingList<>();

   public GateBehavior(
      Map<MemoryModuleType<?>, MemoryStatus> var1,
      Set<MemoryModuleType<?>> var2,
      GateBehavior.OrderPolicy var3,
      GateBehavior.RunningPolicy var4,
      List<Pair<Behavior<? super E>, Integer>> var5
   ) {
      super(â˜ƒ);
      this.exitErasedMemories = â˜ƒ;
      this.orderPolicy = â˜ƒ;
      this.runningPolicy = â˜ƒ;
      â˜ƒ.forEach(var1x -> this.behaviors.add((Behavior<? super E>)var1x.getFirst(), var1x.getSecond()));
   }

   @Override
   protected boolean canStillUse(ServerLevel var1, E var2, long var3) {
      return this.behaviors.stream().filter(var0 -> var0.getStatus() == Behavior.Status.RUNNING).anyMatch(var4 -> var4.canStillUse(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   @Override
   protected boolean timedOut(long var1) {
      return false;
   }

   @Override
   protected void start(ServerLevel var1, E var2, long var3) {
      this.orderPolicy.apply(this.behaviors);
      this.runningPolicy.apply(this.behaviors.stream(), â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void tick(ServerLevel var1, E var2, long var3) {
      this.behaviors.stream().filter(var0 -> var0.getStatus() == Behavior.Status.RUNNING).forEach(var4 -> var4.tickOrStop(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   @Override
   protected void stop(ServerLevel var1, E var2, long var3) {
      this.behaviors.stream().filter(var0 -> var0.getStatus() == Behavior.Status.RUNNING).forEach(var4 -> var4.doStop(â˜ƒ, â˜ƒ, â˜ƒ));
      this.exitErasedMemories.forEach(â˜ƒ.getBrain()::eraseMemory);
   }

   @Override
   public String toString() {
      Set<? extends Behavior<? super E>> â˜ƒ = (Set)this.behaviors
         .stream()
         .filter(var0 -> var0.getStatus() == Behavior.Status.RUNNING)
         .collect(Collectors.toSet());
      return "(" + this.getClass().getSimpleName() + "): " + â˜ƒ;
   }

   public static enum OrderPolicy {
      ORDERED(var0 -> {
      }),
      SHUFFLED(ShufflingList::shuffle);

      private final Consumer<ShufflingList<?>> consumer;

      private OrderPolicy(Consumer<ShufflingList<?>> var3) {
         this.consumer = â˜ƒ;
      }

      public void apply(ShufflingList<?> var1) {
         this.consumer.accept(â˜ƒ);
      }
   }

   public static enum RunningPolicy {
      RUN_ONE {
         @Override
         public <E extends LivingEntity> void apply(Stream<Behavior<? super E>> var1, ServerLevel var2, E var3, long var4) {
            â˜ƒ.filter(var0 -> var0.getStatus() == Behavior.Status.STOPPED).filter(var4x -> var4x.tryStart(â˜ƒ, â˜ƒ, â˜ƒ)).findFirst();
         }
      },
      TRY_ALL {
         @Override
         public <E extends LivingEntity> void apply(Stream<Behavior<? super E>> var1, ServerLevel var2, E var3, long var4) {
            â˜ƒ.filter(var0 -> var0.getStatus() == Behavior.Status.STOPPED).forEach(var4x -> var4x.tryStart(â˜ƒ, â˜ƒ, â˜ƒ));
         }
      };

      public abstract <E extends LivingEntity> void apply(Stream<Behavior<? super E>> var1, ServerLevel var2, E var3, long var4);
   }
}
