package net.minecraft.util.profiling.metrics.profiling;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import org.apache.commons.lang3.tuple.Pair;

public class ProfilerSamplerAdapter {
   private final Set<String> previouslyFoundSamplerNames = new ObjectOpenHashSet();

   public Set<MetricSampler> newSamplersFoundInProfiler(Supplier<ProfileCollector> var1) {
      Set<MetricSampler> â˜ƒ = (Set)((ProfileCollector)â˜ƒ.get())
         .getChartedPaths()
         .stream()
         .filter(var1x -> !this.previouslyFoundSamplerNames.contains(var1x.getLeft()))
         .map(var1x -> samplerForProfilingPath(â˜ƒ, (String)var1x.getLeft(), (MetricCategory)var1x.getRight()))
         .collect(Collectors.toSet());

      for(MetricSampler â˜ƒx : â˜ƒ) {
         this.previouslyFoundSamplerNames.add(â˜ƒx.getName());
      }

      return â˜ƒ;
   }

   private static MetricSampler samplerForProfilingPath(Supplier<ProfileCollector> var0, String var1, MetricCategory var2) {
      return MetricSampler.create(â˜ƒ, â˜ƒ, () -> {
         ActiveProfiler.PathEntry â˜ƒ = ((ProfileCollector)â˜ƒ.get()).getEntry(â˜ƒ);
         return â˜ƒ == null ? 0.0 : (double)â˜ƒ.getMaxDuration() / (double)TimeUtil.NANOSECONDS_PER_MILLISECOND;
      });
   }
}
