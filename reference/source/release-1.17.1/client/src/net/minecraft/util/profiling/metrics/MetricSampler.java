package net.minecraft.util.profiling.metrics;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;

public class MetricSampler {
   private final String name;
   private final MetricCategory category;
   private final DoubleSupplier sampler;
   private final ByteBuf ticks;
   private final ByteBuf values;
   private volatile boolean isRunning;
   @Nullable
   private final Runnable beforeTick;
   @Nullable
   final MetricSampler.ThresholdTest thresholdTest;
   private double currentValue;

   protected MetricSampler(String var1, MetricCategory var2, DoubleSupplier var3, @Nullable Runnable var4, @Nullable MetricSampler.ThresholdTest var5) {
      this.name = â˜ƒ;
      this.category = â˜ƒ;
      this.beforeTick = â˜ƒ;
      this.sampler = â˜ƒ;
      this.thresholdTest = â˜ƒ;
      this.values = ByteBufAllocator.DEFAULT.buffer();
      this.ticks = ByteBufAllocator.DEFAULT.buffer();
      this.isRunning = true;
   }

   public static MetricSampler create(String var0, MetricCategory var1, DoubleSupplier var2) {
      return new MetricSampler(â˜ƒ, â˜ƒ, â˜ƒ, null, null);
   }

   public static <T> MetricSampler create(String var0, MetricCategory var1, T var2, ToDoubleFunction<T> var3) {
      return builder(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).build();
   }

   public static <T> MetricSampler.MetricSamplerBuilder<T> builder(String var0, MetricCategory var1, ToDoubleFunction<T> var2, T var3) {
      return new MetricSampler.MetricSamplerBuilder<>(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void onStartTick() {
      if (!this.isRunning) {
         throw new IllegalStateException("Not running");
      } else {
         if (this.beforeTick != null) {
            this.beforeTick.run();
         }
      }
   }

   public void onEndTick(int var1) {
      this.verifyRunning();
      this.currentValue = this.sampler.getAsDouble();
      this.values.writeDouble(this.currentValue);
      this.ticks.writeInt(â˜ƒ);
   }

   public void onFinished() {
      this.verifyRunning();
      this.values.release();
      this.ticks.release();
      this.isRunning = false;
   }

   private void verifyRunning() {
      if (!this.isRunning) {
         throw new IllegalStateException(String.format("Sampler for metric %s not started!", this.name));
      }
   }

   DoubleSupplier getSampler() {
      return this.sampler;
   }

   public String getName() {
      return this.name;
   }

   public MetricCategory getCategory() {
      return this.category;
   }

   public MetricSampler.SamplerResult result() {
      Int2DoubleMap â˜ƒ = new Int2DoubleOpenHashMap();
      int â˜ƒx = Integer.MIN_VALUE;

      int â˜ƒ;
      int â˜ƒ;
      for(â˜ƒ = Integer.MIN_VALUE; this.values.isReadable(8); â˜ƒ = â˜ƒ) {
         â˜ƒ = this.ticks.readInt();
         if (â˜ƒx == Integer.MIN_VALUE) {
            â˜ƒx = â˜ƒ;
         }

         â˜ƒ.put(â˜ƒ, this.values.readDouble());
      }

      return new MetricSampler.SamplerResult(â˜ƒx, â˜ƒ, â˜ƒ);
   }

   public boolean triggersThreshold() {
      return this.thresholdTest != null && this.thresholdTest.test(this.currentValue);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         MetricSampler â˜ƒ = (MetricSampler)â˜ƒ;
         return this.name.equals(â˜ƒ.name) && this.category.equals(â˜ƒ.category);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public static class MetricSamplerBuilder<T> {
      private final String name;
      private final MetricCategory category;
      private final DoubleSupplier sampler;
      private final T context;
      @Nullable
      private Runnable beforeTick;
      @Nullable
      private MetricSampler.ThresholdTest thresholdTest;

      public MetricSamplerBuilder(String var1, MetricCategory var2, ToDoubleFunction<T> var3, T var4) {
         this.name = â˜ƒ;
         this.category = â˜ƒ;
         this.sampler = () -> â˜ƒ.applyAsDouble(â˜ƒ);
         this.context = â˜ƒ;
      }

      public MetricSampler.MetricSamplerBuilder<T> withBeforeTick(Consumer<T> var1) {
         this.beforeTick = () -> â˜ƒ.accept(this.context);
         return this;
      }

      public MetricSampler.MetricSamplerBuilder<T> withThresholdAlert(MetricSampler.ThresholdTest var1) {
         this.thresholdTest = â˜ƒ;
         return this;
      }

      public MetricSampler build() {
         return new MetricSampler(this.name, this.category, this.sampler, this.beforeTick, this.thresholdTest);
      }
   }

   public static class SamplerResult {
      private final Int2DoubleMap recording;
      private final int firstTick;
      private final int lastTick;

      public SamplerResult(int var1, int var2, Int2DoubleMap var3) {
         this.firstTick = â˜ƒ;
         this.lastTick = â˜ƒ;
         this.recording = â˜ƒ;
      }

      public double valueAtTick(int var1) {
         return this.recording.get(â˜ƒ);
      }

      public int getFirstTick() {
         return this.firstTick;
      }

      public int getLastTick() {
         return this.lastTick;
      }
   }

   public interface ThresholdTest {
      boolean test(double var1);
   }

   public static class ValueIncreasedByPercentage implements MetricSampler.ThresholdTest {
      private final float percentageIncreaseThreshold;
      private double previousValue = Double.MIN_VALUE;

      public ValueIncreasedByPercentage(float var1) {
         this.percentageIncreaseThreshold = â˜ƒ;
      }

      @Override
      public boolean test(double var1) {
         boolean â˜ƒ;
         if (this.previousValue != Double.MIN_VALUE && !(â˜ƒ <= this.previousValue)) {
            â˜ƒ = (â˜ƒ - this.previousValue) / this.previousValue >= (double)this.percentageIncreaseThreshold;
         } else {
            â˜ƒ = false;
         }

         this.previousValue = â˜ƒ;
         return â˜ƒ;
      }
   }
}
