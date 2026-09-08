package net.minecraft.realms;

import com.google.common.util.concurrent.RateLimiter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.network.chat.Component;

public class RepeatedNarrator {
   private final float permitsPerSecond;
   private final AtomicReference<RepeatedNarrator.Params> params = new AtomicReference();

   public RepeatedNarrator(Duration var1) {
      this.permitsPerSecond = 1000.0F / (float)â˜ƒ.toMillis();
   }

   public void narrate(Component var1) {
      RepeatedNarrator.Params â˜ƒ = (RepeatedNarrator.Params)this.params
         .updateAndGet(
            var2x -> var2x != null && â˜ƒ.equals(var2x.narration) ? var2x : new RepeatedNarrator.Params(â˜ƒ, RateLimiter.create((double)this.permitsPerSecond))
         );
      if (â˜ƒ.rateLimiter.tryAcquire(1)) {
         NarratorChatListener.INSTANCE.sayNow(â˜ƒ);
      }
   }

   static class Params {
      final Component narration;
      final RateLimiter rateLimiter;

      Params(Component var1, RateLimiter var2) {
         this.narration = â˜ƒ;
         this.rateLimiter = â˜ƒ;
      }
   }
}
