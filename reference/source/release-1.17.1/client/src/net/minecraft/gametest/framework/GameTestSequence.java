package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class GameTestSequence {
   final GameTestInfo parent;
   private final List<GameTestEvent> events = Lists.<GameTestEvent>newArrayList();
   private long lastTick;

   GameTestSequence(GameTestInfo var1) {
      this.parent = â˜ƒ;
      this.lastTick = â˜ƒ.getTick();
   }

   public GameTestSequence thenWaitUntil(Runnable var1) {
      this.events.add(GameTestEvent.create(â˜ƒ));
      return this;
   }

   public GameTestSequence thenWaitUntil(long var1, Runnable var3) {
      this.events.add(GameTestEvent.create(â˜ƒ, â˜ƒ));
      return this;
   }

   public GameTestSequence thenIdle(int var1) {
      return this.thenExecuteAfter(â˜ƒ, () -> {
      });
   }

   public GameTestSequence thenExecute(Runnable var1) {
      this.events.add(GameTestEvent.create(() -> this.executeWithoutFail(â˜ƒ)));
      return this;
   }

   public GameTestSequence thenExecuteAfter(int var1, Runnable var2) {
      this.events.add(GameTestEvent.create(() -> {
         if (this.parent.getTick() < this.lastTick + (long)â˜ƒ) {
            throw new GameTestAssertException("Waiting");
         } else {
            this.executeWithoutFail(â˜ƒ);
         }
      }));
      return this;
   }

   public GameTestSequence thenExecuteFor(int var1, Runnable var2) {
      this.events.add(GameTestEvent.create(() -> {
         if (this.parent.getTick() < this.lastTick + (long)â˜ƒ) {
            this.executeWithoutFail(â˜ƒ);
            throw new GameTestAssertException("Waiting");
         }
      }));
      return this;
   }

   public void thenSucceed() {
      this.events.add(GameTestEvent.create(this.parent::succeed));
   }

   public void thenFail(Supplier<Exception> var1) {
      this.events.add(GameTestEvent.create(() -> this.parent.fail((Throwable)â˜ƒ.get())));
   }

   public GameTestSequence.Condition thenTrigger() {
      GameTestSequence.Condition â˜ƒ = new GameTestSequence.Condition();
      this.events.add(GameTestEvent.create(() -> â˜ƒ.trigger(this.parent.getTick())));
      return â˜ƒ;
   }

   public void tickAndContinue(long var1) {
      try {
         this.tick(â˜ƒ);
      } catch (GameTestAssertException var4) {
      }
   }

   public void tickAndFailIfNotComplete(long var1) {
      try {
         this.tick(â˜ƒ);
      } catch (GameTestAssertException var4) {
         this.parent.fail(var4);
      }
   }

   private void executeWithoutFail(Runnable var1) {
      try {
         â˜ƒ.run();
      } catch (GameTestAssertException var3) {
         this.parent.fail(var3);
      }
   }

   private void tick(long var1) {
      Iterator<GameTestEvent> â˜ƒ = this.events.iterator();

      while(â˜ƒ.hasNext()) {
         GameTestEvent â˜ƒx = (GameTestEvent)â˜ƒ.next();
         â˜ƒx.assertion.run();
         â˜ƒ.remove();
         long â˜ƒxx = â˜ƒ - this.lastTick;
         long â˜ƒxxx = this.lastTick;
         this.lastTick = â˜ƒ;
         if (â˜ƒx.expectedDelay != null && â˜ƒx.expectedDelay != â˜ƒxx) {
            this.parent
               .fail(new GameTestAssertException("Succeeded in invalid tick: expected " + (â˜ƒxxx + â˜ƒx.expectedDelay) + ", but current tick is " + â˜ƒ));
            break;
         }
      }
   }

   public class Condition {
      private static final long NOT_TRIGGERED = -1L;
      private long triggerTime = -1L;

      void trigger(long var1) {
         if (this.triggerTime != -1L) {
            throw new IllegalStateException("Condition already triggered at " + this.triggerTime);
         } else {
            this.triggerTime = â˜ƒ;
         }
      }

      public void assertTriggeredThisTick() {
         long â˜ƒ = GameTestSequence.this.parent.getTick();
         if (this.triggerTime != â˜ƒ) {
            if (this.triggerTime == -1L) {
               throw new GameTestAssertException("Condition not triggered (t=" + â˜ƒ + ")");
            } else {
               throw new GameTestAssertException("Condition triggered at " + this.triggerTime + ", (t=" + â˜ƒ + ")");
            }
         }
      }
   }
}
