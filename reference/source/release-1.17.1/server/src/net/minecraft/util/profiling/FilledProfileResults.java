package net.minecraft.util.profiling;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilledProfileResults implements ProfileResults {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ProfilerPathEntry EMPTY = new ProfilerPathEntry() {
      @Override
      public long getDuration() {
         return 0L;
      }

      @Override
      public long getMaxDuration() {
         return 0L;
      }

      @Override
      public long getCount() {
         return 0L;
      }

      @Override
      public Object2LongMap<String> getCounters() {
         return Object2LongMaps.emptyMap();
      }
   };
   private static final Splitter SPLITTER = Splitter.on('\u001e');
   private static final Comparator<Entry<String, FilledProfileResults.CounterCollector>> COUNTER_ENTRY_COMPARATOR = Entry.comparingByValue(
         Comparator.comparingLong(var0 -> var0.totalValue)
      )
      .reversed();
   private final Map<String, ? extends ProfilerPathEntry> entries;
   private final long startTimeNano;
   private final int startTimeTicks;
   private final long endTimeNano;
   private final int endTimeTicks;
   private final int tickDuration;

   public FilledProfileResults(Map<String, ? extends ProfilerPathEntry> var1, long var2, int var4, long var5, int var7) {
      this.entries = â˜ƒ;
      this.startTimeNano = â˜ƒ;
      this.startTimeTicks = â˜ƒ;
      this.endTimeNano = â˜ƒ;
      this.endTimeTicks = â˜ƒ;
      this.tickDuration = â˜ƒ - â˜ƒ;
   }

   private ProfilerPathEntry getEntry(String var1) {
      ProfilerPathEntry â˜ƒ = (ProfilerPathEntry)this.entries.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ : EMPTY;
   }

   @Override
   public List<ResultField> getTimes(String var1) {
      String â˜ƒ = â˜ƒ;
      ProfilerPathEntry â˜ƒx = this.getEntry("root");
      long â˜ƒxx = â˜ƒx.getDuration();
      ProfilerPathEntry â˜ƒxxx = this.getEntry(â˜ƒ);
      long â˜ƒxxxx = â˜ƒxxx.getDuration();
      long â˜ƒxxxxx = â˜ƒxxx.getCount();
      List<ResultField> â˜ƒxxxxxx = Lists.<ResultField>newArrayList();
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ = â˜ƒ + "\u001e";
      }

      long â˜ƒ = 0L;

      for(String â˜ƒx : this.entries.keySet()) {
         if (isDirectChild(â˜ƒ, â˜ƒx)) {
            â˜ƒ += this.getEntry(â˜ƒx).getDuration();
         }
      }

      float â˜ƒx = (float)â˜ƒ;
      if (â˜ƒ < â˜ƒxxxx) {
         â˜ƒ = â˜ƒxxxx;
      }

      if (â˜ƒxx < â˜ƒ) {
         â˜ƒxx = â˜ƒ;
      }

      for(String â˜ƒx : this.entries.keySet()) {
         if (isDirectChild(â˜ƒ, â˜ƒx)) {
            ProfilerPathEntry â˜ƒxx = this.getEntry(â˜ƒx);
            long â˜ƒxxx = â˜ƒxx.getDuration();
            double â˜ƒxxxx = (double)â˜ƒxxx * 100.0 / (double)â˜ƒ;
            double â˜ƒxxxxx = (double)â˜ƒxxx * 100.0 / (double)â˜ƒxx;
            String â˜ƒxxxxxx = â˜ƒx.substring(â˜ƒ.length());
            â˜ƒxxxxxx.add(new ResultField(â˜ƒxxxxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxx.getCount()));
         }
      }

      if ((float)â˜ƒ > â˜ƒx) {
         â˜ƒxxxxxx.add(
            new ResultField("unspecified", (double)((float)â˜ƒ - â˜ƒx) * 100.0 / (double)â˜ƒ, (double)((float)â˜ƒ - â˜ƒx) * 100.0 / (double)â˜ƒxx, â˜ƒxxxxx)
         );
      }

      Collections.sort(â˜ƒxxxxxx);
      â˜ƒxxxxxx.add(0, new ResultField(â˜ƒ, 100.0, (double)â˜ƒ * 100.0 / (double)â˜ƒxx, â˜ƒxxxxx));
      return â˜ƒxxxxxx;
   }

   private static boolean isDirectChild(String var0, String var1) {
      return â˜ƒ.length() > â˜ƒ.length() && â˜ƒ.startsWith(â˜ƒ) && â˜ƒ.indexOf(30, â˜ƒ.length() + 1) < 0;
   }

   private Map<String, FilledProfileResults.CounterCollector> getCounterValues() {
      Map<String, FilledProfileResults.CounterCollector> â˜ƒ = Maps.newTreeMap();
      this.entries
         .forEach(
            (var1x, var2) -> {
               Object2LongMap<String> â˜ƒ = var2.getCounters();
               if (!â˜ƒ.isEmpty()) {
                  List<String> â˜ƒx = SPLITTER.splitToList(var1x);
                  â˜ƒ.forEach(
                     (var2x, var3x) -> ((FilledProfileResults.CounterCollector)â˜ƒ.computeIfAbsent(var2x, var0x -> new FilledProfileResults.CounterCollector()))
                           .addValue(â˜ƒ.iterator(), var3x)
                  );
               }
            }
         );
      return â˜ƒ;
   }

   @Override
   public long getStartTimeNano() {
      return this.startTimeNano;
   }

   @Override
   public int getStartTimeTicks() {
      return this.startTimeTicks;
   }

   @Override
   public long getEndTimeNano() {
      return this.endTimeNano;
   }

   @Override
   public int getEndTimeTicks() {
      return this.endTimeTicks;
   }

   @Override
   public boolean saveResults(Path var1) {
      Writer â˜ƒ = null;

      boolean var4;
      try {
         Files.createDirectories(â˜ƒ.getParent());
         â˜ƒ = Files.newBufferedWriter(â˜ƒ, StandardCharsets.UTF_8);
         â˜ƒ.write(this.getProfilerResults(this.getNanoDuration(), this.getTickDuration()));
         return true;
      } catch (Throwable var8) {
         LOGGER.error("Could not save profiler results to {}", â˜ƒ, var8);
         var4 = false;
      } finally {
         IOUtils.closeQuietly(â˜ƒ);
      }

      return var4;
   }

   protected String getProfilerResults(long var1, int var3) {
      StringBuilder â˜ƒ = new StringBuilder();
      â˜ƒ.append("---- Minecraft Profiler Results ----\n");
      â˜ƒ.append("// ");
      â˜ƒ.append(getComment());
      â˜ƒ.append("\n\n");
      â˜ƒ.append("Version: ").append(SharedConstants.getCurrentVersion().getId()).append('\n');
      â˜ƒ.append("Time span: ").append(â˜ƒ / 1000000L).append(" ms\n");
      â˜ƒ.append("Tick span: ").append(â˜ƒ).append(" ticks\n");
      â˜ƒ.append("// This is approximately ")
         .append(String.format(Locale.ROOT, "%.2f", (float)â˜ƒ / ((float)â˜ƒ / 1.0E9F)))
         .append(" ticks per second. It should be ")
         .append(20)
         .append(" ticks per second\n\n");
      â˜ƒ.append("--- BEGIN PROFILE DUMP ---\n\n");
      this.appendProfilerResults(0, "root", â˜ƒ);
      â˜ƒ.append("--- END PROFILE DUMP ---\n\n");
      Map<String, FilledProfileResults.CounterCollector> â˜ƒx = this.getCounterValues();
      if (!â˜ƒx.isEmpty()) {
         â˜ƒ.append("--- BEGIN COUNTER DUMP ---\n\n");
         this.appendCounters(â˜ƒx, â˜ƒ, â˜ƒ);
         â˜ƒ.append("--- END COUNTER DUMP ---\n\n");
      }

      return â˜ƒ.toString();
   }

   @Override
   public String getProfilerResults() {
      StringBuilder â˜ƒ = new StringBuilder();
      this.appendProfilerResults(0, "root", â˜ƒ);
      return â˜ƒ.toString();
   }

   private static StringBuilder indentLine(StringBuilder var0, int var1) {
      â˜ƒ.append(String.format("[%02d] ", â˜ƒ));

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         â˜ƒ.append("|   ");
      }

      return â˜ƒ;
   }

   private void appendProfilerResults(int var1, String var2, StringBuilder var3) {
      List<ResultField> â˜ƒ = this.getTimes(â˜ƒ);
      Object2LongMap<String> â˜ƒx = ObjectUtils.firstNonNull((ProfilerPathEntry)this.entries.get(â˜ƒ), EMPTY).getCounters();
      â˜ƒx.forEach(
         (var3x, var4x) -> indentLine(â˜ƒ, â˜ƒ)
               .append('#')
               .append(var3x)
               .append(' ')
               .append(var4x)
               .append('/')
               .append(var4x / (long)this.tickDuration)
               .append('\n')
      );
      if (â˜ƒ.size() >= 3) {
         for(int â˜ƒxx = 1; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
            ResultField â˜ƒxxx = (ResultField)â˜ƒ.get(â˜ƒxx);
            indentLine(â˜ƒ, â˜ƒ)
               .append(â˜ƒxxx.name)
               .append('(')
               .append(â˜ƒxxx.count)
               .append('/')
               .append(String.format(Locale.ROOT, "%.0f", (float)â˜ƒxxx.count / (float)this.tickDuration))
               .append(')')
               .append(" - ")
               .append(String.format(Locale.ROOT, "%.2f", â˜ƒxxx.percentage))
               .append("%/")
               .append(String.format(Locale.ROOT, "%.2f", â˜ƒxxx.globalPercentage))
               .append("%\n");
            if (!"unspecified".equals(â˜ƒxxx.name)) {
               try {
                  this.appendProfilerResults(â˜ƒ + 1, â˜ƒ + "\u001e" + â˜ƒxxx.name, â˜ƒ);
               } catch (Exception var9) {
                  â˜ƒ.append("[[ EXCEPTION ").append(var9).append(" ]]");
               }
            }
         }
      }
   }

   private void appendCounterResults(int var1, String var2, FilledProfileResults.CounterCollector var3, int var4, StringBuilder var5) {
      indentLine(â˜ƒ, â˜ƒ)
         .append(â˜ƒ)
         .append(" total:")
         .append(â˜ƒ.selfValue)
         .append('/')
         .append(â˜ƒ.totalValue)
         .append(" average: ")
         .append(â˜ƒ.selfValue / (long)â˜ƒ)
         .append('/')
         .append(â˜ƒ.totalValue / (long)â˜ƒ)
         .append('\n');
      â˜ƒ.children
         .entrySet()
         .stream()
         .sorted(COUNTER_ENTRY_COMPARATOR)
         .forEach(var4x -> this.appendCounterResults(â˜ƒ + 1, (String)var4x.getKey(), (FilledProfileResults.CounterCollector)var4x.getValue(), â˜ƒ, â˜ƒ));
   }

   private void appendCounters(Map<String, FilledProfileResults.CounterCollector> var1, StringBuilder var2, int var3) {
      â˜ƒ.forEach((var3x, var4) -> {
         â˜ƒ.append("-- Counter: ").append(var3x).append(" --\n");
         this.appendCounterResults(0, "root", (FilledProfileResults.CounterCollector)var4.children.get("root"), â˜ƒ, â˜ƒ);
         â˜ƒ.append("\n\n");
      });
   }

   private static String getComment() {
      String[] â˜ƒ = new String[]{
         "Shiny numbers!",
         "Am I not running fast enough? :(",
         "I'm working as hard as I can!",
         "Will I ever be good enough for you? :(",
         "Speedy. Zoooooom!",
         "Hello world",
         "40% better than a crash report.",
         "Now with extra numbers",
         "Now with less numbers",
         "Now with the same numbers",
         "You should add flames to things, it makes them go faster!",
         "Do you feel the need for... optimization?",
         "*cracks redstone whip*",
         "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."
      };

      try {
         return â˜ƒ[(int)(Util.getNanos() % (long)â˜ƒ.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   @Override
   public int getTickDuration() {
      return this.tickDuration;
   }

   static class CounterCollector {
      long selfValue;
      long totalValue;
      final Map<String, FilledProfileResults.CounterCollector> children = Maps.newHashMap();

      public void addValue(Iterator<String> var1, long var2) {
         this.totalValue += â˜ƒ;
         if (!â˜ƒ.hasNext()) {
            this.selfValue += â˜ƒ;
         } else {
            ((FilledProfileResults.CounterCollector)this.children.computeIfAbsent((String)â˜ƒ.next(), var0 -> new FilledProfileResults.CounterCollector()))
               .addValue(â˜ƒ, â˜ƒ);
         }
      }
   }
}
