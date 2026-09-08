package net.minecraft.util.profiling.metrics.storage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetricsPersister {
   public static final Path PROFILING_RESULTS_DIR = Paths.get("debug/profiling");
   public static final String METRICS_DIR_NAME = "metrics";
   public static final String DEVIATIONS_DIR_NAME = "deviations";
   public static final String PROFILING_RESULT_FILENAME = "profiling.txt";
   private static final Logger LOGGER = LogManager.getLogger();
   private final String rootFolderName;

   public MetricsPersister(String var1) {
      this.rootFolderName = â˜ƒ;
   }

   public Path saveReports(Set<MetricSampler> var1, Map<MetricSampler, List<RecordedDeviation>> var2, ProfileResults var3) {
      try {
         Files.createDirectories(PROFILING_RESULTS_DIR);
      } catch (IOException var8) {
         throw new UncheckedIOException(var8);
      }

      try {
         Path â˜ƒ = Files.createTempDirectory("minecraft-profiling");
         â˜ƒ.toFile().deleteOnExit();
         Files.createDirectories(PROFILING_RESULTS_DIR);
         Path â˜ƒx = â˜ƒ.resolve(this.rootFolderName);
         Path â˜ƒxx = â˜ƒx.resolve("metrics");
         this.saveMetrics(â˜ƒ, â˜ƒxx);
         if (!â˜ƒ.isEmpty()) {
            this.saveDeviations(â˜ƒ, â˜ƒx.resolve("deviations"));
         }

         this.saveProfilingTaskExecutionResult(â˜ƒ, â˜ƒx);
         return â˜ƒ;
      } catch (IOException var7) {
         throw new UncheckedIOException(var7);
      }
   }

   private void saveMetrics(Set<MetricSampler> var1, Path var2) {
      if (â˜ƒ.isEmpty()) {
         throw new IllegalArgumentException("Expected at least one sampler to persist");
      } else {
         Map<MetricCategory, List<MetricSampler>> â˜ƒ = (Map)â˜ƒ.stream().collect(Collectors.groupingBy(MetricSampler::getCategory));
         â˜ƒ.forEach((var2x, var3x) -> this.saveCategory(var2x, var3x, â˜ƒ));
      }
   }

   private void saveCategory(MetricCategory var1, List<MetricSampler> var2, Path var3) {
      Path â˜ƒ = â˜ƒ.resolve(Util.sanitizeName(â˜ƒ.getDescription(), ResourceLocation::validPathChar) + ".csv");
      Writer â˜ƒx = null;

      try {
         Files.createDirectories(â˜ƒ.getParent());
         â˜ƒx = Files.newBufferedWriter(â˜ƒ, StandardCharsets.UTF_8);
         CsvOutput.Builder â˜ƒxx = CsvOutput.builder();
         â˜ƒxx.addColumn("@tick");

         for(MetricSampler â˜ƒxxx : â˜ƒ) {
            â˜ƒxx.addColumn(â˜ƒxxx.getName());
         }

         CsvOutput â˜ƒxxx = â˜ƒxx.build(â˜ƒx);
         List<MetricSampler.SamplerResult> â˜ƒxxxx = (List)â˜ƒ.stream().map(MetricSampler::result).collect(Collectors.toList());
         int â˜ƒxxxxx = â˜ƒxxxx.stream().mapToInt(MetricSampler.SamplerResult::getFirstTick).summaryStatistics().getMin();
         int â˜ƒxxxxxx = â˜ƒxxxx.stream().mapToInt(MetricSampler.SamplerResult::getLastTick).summaryStatistics().getMax();

         for(int â˜ƒxxxxxxx = â˜ƒxxxxx; â˜ƒxxxxxxx <= â˜ƒxxxxxx; ++â˜ƒxxxxxxx) {
            int â˜ƒxxxxxxxx = â˜ƒxxxxxxx;
            Stream<String> â˜ƒxxxxxxxxx = â˜ƒxxxx.stream().map(var1x -> String.valueOf(var1x.valueAtTick(â˜ƒ)));
            Object[] â˜ƒxxxxxxxxxx = Stream.concat(Stream.of(String.valueOf(â˜ƒxxxxxxx)), â˜ƒxxxxxxxxx).toArray(var0 -> new String[var0]);
            â˜ƒxxx.writeRow(â˜ƒxxxxxxxxxx);
         }

         LOGGER.info("Flushed metrics to {}", â˜ƒ);
      } catch (Exception var18) {
         LOGGER.error("Could not save profiler results to {}", â˜ƒ, var18);
      } finally {
         IOUtils.closeQuietly(â˜ƒx);
      }
   }

   private void saveDeviations(Map<MetricSampler, List<RecordedDeviation>> var1, Path var2) {
      DateTimeFormatter â˜ƒ = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS", Locale.UK).withZone(ZoneId.systemDefault());
      â˜ƒ.forEach(
         (var2x, var3x) -> var3x.forEach(
               var3xx -> {
                  String â˜ƒ = â˜ƒ.format(var3xx.timestamp);
                  Path â˜ƒx = â˜ƒ.resolve(Util.sanitizeName(var2x.getName(), ResourceLocation::validPathChar))
                     .resolve(String.format(Locale.ROOT, "%d@%s.txt", var3xx.tick, â˜ƒ));
                  var3xx.profilerResultAtTick.saveResults(â˜ƒx);
               }
            )
      );
   }

   private void saveProfilingTaskExecutionResult(ProfileResults var1, Path var2) {
      â˜ƒ.saveResults(â˜ƒ.resolve("profiling.txt"));
   }
}
