package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;

public class RunSometimes<E extends LivingEntity> extends Behavior<E> {
   private boolean resetTicks;
   private boolean wasRunning;
   private final UniformInt interval;
   private final Behavior<? super E> wrappedBehavior;
   private int ticksUntilNextStart;

   public RunSometimes(Behavior<? super E> var1, UniformInt var2) {
      this(â˜ƒ, false, â˜ƒ);
   }

   public RunSometimes(Behavior<? super E> var1, boolean var2, UniformInt var3) {
      super(â˜ƒ.entryCondition);
      this.wrappedBehavior = â˜ƒ;
      this.resetTicks = !â˜ƒ;
      this.interval = â˜ƒ;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      if (!this.wrappedBehavior.checkExtraStartConditions(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         if (this.resetTicks) {
            this.resetTicksUntilNextStart(â˜ƒ);
            this.resetTicks = false;
         }

         if (this.ticksUntilNextStart > 0) {
            --this.ticksUntilNextStart;
         }

         return !this.wasRunning && this.ticksUntilNextStart == 0;
      }
   }

   @Override
   protected void start(ServerLevel var1, E var2, long var3) {
      this.wrappedBehavior.start(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean canStillUse(ServerLevel var1, E var2, long var3) {
      return this.wrappedBehavior.canStillUse(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void tick(ServerLevel var1, E var2, long var3) {
      this.wrappedBehavior.tick(â˜ƒ, â˜ƒ, â˜ƒ);
      this.wasRunning = this.wrappedBehavior.getStatus() == Behavior.Status.RUNNING;
   }

   @Override
   protected void stop(ServerLevel var1, E var2, long var3) {
      this.resetTicksUntilNextStart(â˜ƒ);
      this.wrappedBehavior.stop(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void resetTicksUntilNextStart(ServerLevel var1) {
      this.ticksUntilNextStart = this.interval.sample(â˜ƒ.random);
   }

   @Override
   protected boolean timedOut(long var1) {
      return false;
   }

   @Override
   public String toString() {
      return "RunSometimes: " + this.wrappedBehavior;
   }
}
