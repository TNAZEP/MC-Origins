package net.minecraft.util.profiling.metrics.profiling;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class ServerMetricsSamplersProvider implements MetricsSamplerProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Set<MetricSampler> samplers = new ObjectOpenHashSet<>();
   private final ProfilerSamplerAdapter samplerFactory = new ProfilerSamplerAdapter();

   public ServerMetricsSamplersProvider(LongSupplier var1, boolean var2) {
      this.samplers.add(tickTimeSampler(â˜ƒ));
      if (â˜ƒ) {
         this.samplers.addAll(runtimeIndependentSamplers());
      }
   }

   public static Set<MetricSampler> runtimeIndependentSamplers() {
      Builder<MetricSampler> â˜ƒ = ImmutableSet.builder();

      try {
         ServerMetricsSamplersProvider.CpuStats â˜ƒx = new ServerMetricsSamplersProvider.CpuStats();
         IntStream.range(0, â˜ƒx.nrOfCpus)
            .mapToObj(var1x -> MetricSampler.create("cpu#" + var1x, MetricCategory.CPU, () -> â˜ƒ.loadForCpu(var1x)))
            .forEach(â˜ƒ::add);
      } catch (Throwable var2) {
         LOGGER.warn("Failed to query cpu, no cpu stats will be recorded", var2);
      }

      â˜ƒ.add(
         MetricSampler.create(
            "heap MiB", MetricCategory.JVM, () -> (double)((float)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576.0F)
         )
      );
      â˜ƒ.addAll(MetricsRegistry.INSTANCE.getRegisteredSamplers());
      return â˜ƒ.build();
   }

   @Override
   public Set<MetricSampler> samplers(Supplier<ProfileCollector> var1) {
      this.samplers.addAll(this.samplerFactory.newSamplersFoundInProfiler(â˜ƒ));
      return this.samplers;
   }

   public static MetricSampler tickTimeSampler(final LongSupplier var0) {
      Stopwatch â˜ƒ = Stopwatch.createUnstarted(new Ticker() {
         @Override
         public long read() {
            return â˜ƒ.getAsLong();
         }
      });
      ToDoubleFunction<Stopwatch> â˜ƒx = var0x -> {
         if (var0x.isRunning()) {
            var0x.stop();
         }

         long â˜ƒ = var0x.elapsed(TimeUnit.NANOSECONDS);
         var0x.reset();
         return (double)â˜ƒ;
      };
      MetricSampler.ValueIncreasedByPercentage â˜ƒxx = new MetricSampler.ValueIncreasedByPercentage(2.0F);
      return MetricSampler.builder("ticktime", MetricCategory.TICK_LOOP, â˜ƒx, â˜ƒ).withBeforeTick(Stopwatch::start).withThresholdAlert(â˜ƒxx).build();
   }

   static class CpuStats {
      private final SystemInfo systemInfo = new SystemInfo();
      private final CentralProcessor processor = this.systemInfo.getHardware().getProcessor();
      public final int nrOfCpus = this.processor.getLogicalProcessorCount();
      private long[][] previousCpuLoadTick = this.processor.getProcessorCpuLoadTicks();
      private double[] currentLoad = this.processor.getProcessorCpuLoadBetweenTicks(this.previousCpuLoadTick);
      private long lastPollMs;

      public double loadForCpu(int var1) {
         long â˜ƒ = System.currentTimeMillis();
         if (this.lastPollMs == 0L || this.lastPollMs + 501L < â˜ƒ) {
            this.currentLoad = this.processor.getProcessorCpuLoadBetweenTicks(this.previousCpuLoadTick);
            this.previousCpuLoadTick = this.processor.getProcessorCpuLoadTicks();
            this.lastPollMs = â˜ƒ;
         }

         return this.currentLoad[â˜ƒ] * 100.0;
      }
   }
}
