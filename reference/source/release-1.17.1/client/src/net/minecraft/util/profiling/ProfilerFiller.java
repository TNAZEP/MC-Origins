package net.minecraft.util.profiling;

import java.util.function.Supplier;
import net.minecraft.util.profiling.metrics.MetricCategory;

public interface ProfilerFiller {
   String ROOT = "root";

   void startTick();

   void endTick();

   void push(String var1);

   void push(Supplier<String> var1);

   void pop();

   void popPush(String var1);

   void popPush(Supplier<String> var1);

   void markForCharting(MetricCategory var1);

   void incrementCounter(String var1);

   void incrementCounter(Supplier<String> var1);

   static ProfilerFiller tee(final ProfilerFiller var0, final ProfilerFiller var1) {
      if (â˜ƒ == InactiveProfiler.INSTANCE) {
         return â˜ƒ;
      } else {
         return â˜ƒ == InactiveProfiler.INSTANCE ? â˜ƒ : new ProfilerFiller() {
            @Override
            public void startTick() {
               â˜ƒ.startTick();
               â˜ƒ.startTick();
            }

            @Override
            public void endTick() {
               â˜ƒ.endTick();
               â˜ƒ.endTick();
            }

            @Override
            public void push(String var1x) {
               â˜ƒ.push(â˜ƒ);
               â˜ƒ.push(â˜ƒ);
            }

            @Override
            public void push(Supplier<String> var1x) {
               â˜ƒ.push(â˜ƒ);
               â˜ƒ.push(â˜ƒ);
            }

            @Override
            public void markForCharting(MetricCategory var1x) {
               â˜ƒ.markForCharting(â˜ƒ);
               â˜ƒ.markForCharting(â˜ƒ);
            }

            @Override
            public void pop() {
               â˜ƒ.pop();
               â˜ƒ.pop();
            }

            @Override
            public void popPush(String var1x) {
               â˜ƒ.popPush(â˜ƒ);
               â˜ƒ.popPush(â˜ƒ);
            }

            @Override
            public void popPush(Supplier<String> var1x) {
               â˜ƒ.popPush(â˜ƒ);
               â˜ƒ.popPush(â˜ƒ);
            }

            @Override
            public void incrementCounter(String var1x) {
               â˜ƒ.incrementCounter(â˜ƒ);
               â˜ƒ.incrementCounter(â˜ƒ);
            }

            @Override
            public void incrementCounter(Supplier<String> var1x) {
               â˜ƒ.incrementCounter(â˜ƒ);
               â˜ƒ.incrementCounter(â˜ƒ);
            }
         };
      }
   }
}
