package com.mojang.realmsclient.gui.task;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class RepeatableTask implements Runnable {
   private final BooleanSupplier isActive;
   private final RestartDelayCalculator restartDelayCalculator;
   private final Duration interval;
   private final Runnable runnable;

   private RepeatableTask(Runnable var1, Duration var2, BooleanSupplier var3, RestartDelayCalculator var4) {
      this.runnable = â˜ƒ;
      this.interval = â˜ƒ;
      this.isActive = â˜ƒ;
      this.restartDelayCalculator = â˜ƒ;
   }

   public void run() {
      if (this.isActive.getAsBoolean()) {
         this.restartDelayCalculator.markExecutionStart();
         this.runnable.run();
      }
   }

   public ScheduledFuture<?> schedule(ScheduledExecutorService var1) {
      return â˜ƒ.scheduleAtFixedRate(this, this.restartDelayCalculator.getNextDelayMs(), this.interval.toMillis(), TimeUnit.MILLISECONDS);
   }

   public static RepeatableTask withRestartDelayAccountingForInterval(Runnable var0, Duration var1, BooleanSupplier var2) {
      return new RepeatableTask(â˜ƒ, â˜ƒ, â˜ƒ, new IntervalBasedStartupDelay(â˜ƒ));
   }

   public static RepeatableTask withImmediateRestart(Runnable var0, Duration var1, BooleanSupplier var2) {
      return new RepeatableTask(â˜ƒ, â˜ƒ, â˜ƒ, new NoStartupDelay());
   }
}
