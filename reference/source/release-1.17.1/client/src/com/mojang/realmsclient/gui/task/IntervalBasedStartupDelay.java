package com.mojang.realmsclient.gui.task;

import com.google.common.annotations.VisibleForTesting;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class IntervalBasedStartupDelay implements RestartDelayCalculator {
   private final Duration interval;
   private final Supplier<Clock> clock;
   @Nullable
   private Instant lastStartedTimestamp;

   public IntervalBasedStartupDelay(Duration var1) {
      this.interval = â˜ƒ;
      this.clock = Clock::systemUTC;
   }

   @VisibleForTesting
   protected IntervalBasedStartupDelay(Duration var1, Supplier<Clock> var2) {
      this.interval = â˜ƒ;
      this.clock = â˜ƒ;
   }

   @Override
   public void markExecutionStart() {
      this.lastStartedTimestamp = Instant.now((Clock)this.clock.get());
   }

   @Override
   public long getNextDelayMs() {
      return this.lastStartedTimestamp == null
         ? 0L
         : Math.max(0L, Duration.between(Instant.now((Clock)this.clock.get()), this.lastStartedTimestamp.plus(this.interval)).toMillis());
   }
}
