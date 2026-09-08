package net.minecraft.util.profiling;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingleTickProfiler {
   private static final Logger LOGGER = LogManager.getLogger();
   private final LongSupplier realTime;
   private final long saveThreshold;
   private int tick;
   private final File location;
   private ProfileCollector profiler = InactiveProfiler.INSTANCE;

   public SingleTickProfiler(LongSupplier var1, String var2, long var3) {
      this.realTime = â˜ƒ;
      this.location = new File("debug", â˜ƒ);
      this.saveThreshold = â˜ƒ;
   }

   public ProfilerFiller startTick() {
      this.profiler = new ActiveProfiler(this.realTime, () -> this.tick, false);
      ++this.tick;
      return this.profiler;
   }

   public void endTick() {
      if (this.profiler != InactiveProfiler.INSTANCE) {
         ProfileResults â˜ƒ = this.profiler.getResults();
         this.profiler = InactiveProfiler.INSTANCE;
         if (â˜ƒ.getNanoDuration() >= this.saveThreshold) {
            File â˜ƒx = new File(this.location, "tick-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
            â˜ƒ.saveResults(â˜ƒx.toPath());
            LOGGER.info("Recorded long tick -- wrote info to: {}", â˜ƒx.getAbsolutePath());
         }
      }
   }

   @Nullable
   public static SingleTickProfiler createTickProfiler(String var0) {
      return null;
   }

   public static ProfilerFiller decorateFiller(ProfilerFiller var0, @Nullable SingleTickProfiler var1) {
      return â˜ƒ != null ? ProfilerFiller.tee(â˜ƒ.startTick(), â˜ƒ) : â˜ƒ;
   }
}
