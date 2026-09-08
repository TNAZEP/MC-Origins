package net.minecraft.util.profiling.metrics;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class MetricsRegistry {
   public static final MetricsRegistry INSTANCE = new MetricsRegistry();
   private final WeakHashMap<ProfilerMeasured, Void> measuredInstances = new WeakHashMap();

   private MetricsRegistry() {
   }

   public void add(ProfilerMeasured var1) {
      this.measuredInstances.put(â˜ƒ, null);
   }

   public List<MetricSampler> getRegisteredSamplers() {
      Map<String, List<MetricSampler>> â˜ƒ = (Map)this.measuredInstances
         .keySet()
         .stream()
         .flatMap(var0 -> var0.profiledMetrics().stream())
         .collect(Collectors.groupingBy(MetricSampler::getName));
      return aggregateDuplicates(â˜ƒ);
   }

   private static List<MetricSampler> aggregateDuplicates(Map<String, List<MetricSampler>> var0) {
      return (List<MetricSampler>)â˜ƒ.entrySet().stream().map(var0x -> {
         String â˜ƒ = (String)var0x.getKey();
         List<MetricSampler> â˜ƒx = (List)var0x.getValue();
         return (MetricSampler)(â˜ƒx.size() > 1 ? new MetricsRegistry.AggregatedMetricSampler(â˜ƒ, â˜ƒx) : (MetricSampler)â˜ƒx.get(0));
      }).collect(Collectors.toList());
   }

   static class AggregatedMetricSampler extends MetricSampler {
      private final List<MetricSampler> delegates;

      AggregatedMetricSampler(String var1, List<MetricSampler> var2) {
         super(â˜ƒ, ((MetricSampler)â˜ƒ.get(0)).getCategory(), () -> averageValueFromDelegates(â˜ƒ), () -> beforeTick(â˜ƒ), thresholdTest(â˜ƒ));
         this.delegates = â˜ƒ;
      }

      private static MetricSampler.ThresholdTest thresholdTest(List<MetricSampler> var0) {
         return var1 -> â˜ƒ.stream().anyMatch(var2 -> var2.thresholdTest != null ? var2.thresholdTest.test(var1) : false);
      }

      private static void beforeTick(List<MetricSampler> var0) {
         for(MetricSampler â˜ƒ : â˜ƒ) {
            â˜ƒ.onStartTick();
         }
      }

      private static double averageValueFromDelegates(List<MetricSampler> var0) {
         double â˜ƒ = 0.0;

         for(MetricSampler â˜ƒx : â˜ƒ) {
            â˜ƒ += â˜ƒx.getSampler().getAsDouble();
         }

         return â˜ƒ / (double)â˜ƒ.size();
      }

      @Override
      public boolean equals(@Nullable Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ == null || this.getClass() != â˜ƒ.getClass()) {
            return false;
         } else if (!super.equals(â˜ƒ)) {
            return false;
         } else {
            MetricsRegistry.AggregatedMetricSampler â˜ƒ = (MetricsRegistry.AggregatedMetricSampler)â˜ƒ;
            return this.delegates.equals(â˜ƒ.delegates);
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(new Object[]{super.hashCode(), this.delegates});
      }
   }
}
