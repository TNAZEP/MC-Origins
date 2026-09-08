package net.minecraft.util.profiling.metrics.profiling;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ContinuousProfiler;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.profiling.metrics.storage.RecordedDeviation;

public class ActiveMetricsRecorder implements MetricsRecorder {
   public static final int PROFILING_MAX_DURATION_SECONDS = 10;
   @Nullable
   private static Consumer<Path> globalOnReportFinished = null;
   private final Map<MetricSampler, List<RecordedDeviation>> deviationsBySampler = new Object2ObjectOpenHashMap<>();
   private final ContinuousProfiler taskProfiler;
   private final Executor ioExecutor;
   private final MetricsPersister metricsPersister;
   private final Consumer<ProfileResults> onProfilingEnd;
   private final Consumer<Path> onReportFinished;
   private final MetricsSamplerProvider metricsSamplerProvider;
   private final LongSupplier wallTimeSource;
   private final long deadlineNano;
   private int currentTick;
   private ProfileCollector singleTickProfiler;
   private volatile boolean killSwitch;
   private Set<MetricSampler> thisTickSamplers = ImmutableSet.of();

   private ActiveMetricsRecorder(
      MetricsSamplerProvider var1, LongSupplier var2, Executor var3, MetricsPersister var4, Consumer<ProfileResults> var5, Consumer<Path> var6
   ) {
      this.metricsSamplerProvider = â˜ƒ;
      this.wallTimeSource = â˜ƒ;
      this.taskProfiler = new ContinuousProfiler(â˜ƒ, () -> this.currentTick);
      this.ioExecutor = â˜ƒ;
      this.metricsPersister = â˜ƒ;
      this.onProfilingEnd = â˜ƒ;
      this.onReportFinished = globalOnReportFinished == null ? â˜ƒ : â˜ƒ.andThen(globalOnReportFinished);
      this.deadlineNano = â˜ƒ.getAsLong() + TimeUnit.NANOSECONDS.convert(10L, TimeUnit.SECONDS);
      this.singleTickProfiler = new ActiveProfiler(this.wallTimeSource, () -> this.currentTick, false);
      this.taskProfiler.enable();
   }

   public static ActiveMetricsRecorder createStarted(
      MetricsSamplerProvider var0, LongSupplier var1, Executor var2, MetricsPersister var3, Consumer<ProfileResults> var4, Consumer<Path> var5
   ) {
      return new ActiveMetricsRecorder(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public synchronized void end() {
      if (this.isRecording()) {
         this.killSwitch = true;
      }
   }

   @Override
   public void startTick() {
      this.verifyStarted();
      this.thisTickSamplers = this.metricsSamplerProvider.samplers(() -> this.singleTickProfiler);

      for(MetricSampler â˜ƒ : this.thisTickSamplers) {
         â˜ƒ.onStartTick();
      }

      ++this.currentTick;
   }

   @Override
   public void endTick() {
      this.verifyStarted();
      if (this.currentTick != 0) {
         for(MetricSampler â˜ƒ : this.thisTickSamplers) {
            â˜ƒ.onEndTick(this.currentTick);
            if (â˜ƒ.triggersThreshold()) {
               RecordedDeviation â˜ƒx = new RecordedDeviation(Instant.now(), this.currentTick, this.singleTickProfiler.getResults());
               ((List)this.deviationsBySampler.computeIfAbsent(â˜ƒ, var0 -> Lists.newArrayList())).add(â˜ƒx);
            }
         }

         if (!this.killSwitch && this.wallTimeSource.getAsLong() <= this.deadlineNano) {
            this.singleTickProfiler = new ActiveProfiler(this.wallTimeSource, () -> this.currentTick, false);
         } else {
            this.killSwitch = false;
            this.singleTickProfiler = InactiveProfiler.INSTANCE;
            ProfileResults â˜ƒ = this.taskProfiler.getResults();
            this.onProfilingEnd.accept(â˜ƒ);
            this.scheduleSaveResults(â˜ƒ);
         }
      }
   }

   @Override
   public boolean isRecording() {
      return this.taskProfiler.isEnabled();
   }

   @Override
   public ProfilerFiller getProfiler() {
      return ProfilerFiller.tee(this.taskProfiler.getFiller(), this.singleTickProfiler);
   }

   private void verifyStarted() {
      if (!this.isRecording()) {
         throw new IllegalStateException("Not started!");
      }
   }

   private void scheduleSaveResults(ProfileResults var1) {
      HashSet<MetricSampler> â˜ƒ = new HashSet(this.thisTickSamplers);
      this.ioExecutor.execute(() -> {
         Path â˜ƒ = this.metricsPersister.saveReports(â˜ƒ, this.deviationsBySampler, â˜ƒ);

         for(MetricSampler â˜ƒx : â˜ƒ) {
            â˜ƒx.onFinished();
         }

         this.deviationsBySampler.clear();
         this.taskProfiler.disable();
         this.onReportFinished.accept(â˜ƒ);
      });
   }

   public static void registerGlobalCompletionCallback(Consumer<Path> var0) {
      globalOnReportFinished = â˜ƒ;
   }
}
