package net.minecraft.server.dedicated;

import com.google.common.collect.Streams;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.Util;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerWatchdog implements Runnable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final long MAX_SHUTDOWN_TIME = 10000L;
   private static final int SHUTDOWN_STATUS = 1;
   private final DedicatedServer server;
   private final long maxTickTime;

   public ServerWatchdog(DedicatedServer var1) {
      this.server = â˜ƒ;
      this.maxTickTime = â˜ƒ.getMaxTickLength();
   }

   public void run() {
      while(this.server.isRunning()) {
         long â˜ƒ = this.server.getNextTickTime();
         long â˜ƒx = Util.getMillis();
         long â˜ƒxx = â˜ƒx - â˜ƒ;
         if (â˜ƒxx > this.maxTickTime) {
            LOGGER.fatal(
               "A single server tick took {} seconds (should be max {})",
               String.format(Locale.ROOT, "%.2f", (float)â˜ƒxx / 1000.0F),
               String.format(Locale.ROOT, "%.2f", 0.05F)
            );
            LOGGER.fatal("Considering it to be crashed, server will forcibly shutdown.");
            ThreadMXBean â˜ƒxxx = ManagementFactory.getThreadMXBean();
            ThreadInfo[] â˜ƒxxxx = â˜ƒxxx.dumpAllThreads(true, true);
            StringBuilder â˜ƒxxxxx = new StringBuilder();
            Error â˜ƒxxxxxx = new Error("Watchdog");

            for(ThreadInfo â˜ƒxxxxxxx : â˜ƒxxxx) {
               if (â˜ƒxxxxxxx.getThreadId() == this.server.getRunningThread().getId()) {
                  â˜ƒxxxxxx.setStackTrace(â˜ƒxxxxxxx.getStackTrace());
               }

               â˜ƒxxxxx.append(â˜ƒxxxxxxx);
               â˜ƒxxxxx.append("\n");
            }

            CrashReport â˜ƒxxxxxxx = new CrashReport("Watching Server", â˜ƒxxxxxx);
            this.server.fillSystemReport(â˜ƒxxxxxxx.getSystemReport());
            CrashReportCategory â˜ƒxxxxxxxx = â˜ƒxxxxxxx.addCategory("Thread Dump");
            â˜ƒxxxxxxxx.setDetail("Threads", â˜ƒxxxxx);
            CrashReportCategory â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.addCategory("Performance stats");
            â˜ƒxxxxxxxxx.setDetail(
               "Random tick rate",
               (CrashReportDetail<String>)(() -> this.server.getWorldData().getGameRules().getRule(GameRules.RULE_RANDOMTICKING).toString())
            );
            â˜ƒxxxxxxxxx.setDetail(
               "Level stats",
               (CrashReportDetail<String>)(() -> (String)Streams.stream(this.server.getAllLevels())
                     .map(var0 -> var0.dimension() + ": " + var0.getWatchdogStats())
                     .collect(Collectors.joining(",\n")))
            );
            Bootstrap.realStdoutPrintln("Crash report:\n" + â˜ƒxxxxxxx.getFriendlyReport());
            File â˜ƒxxxxxxxxxx = new File(
               new File(this.server.getServerDirectory(), "crash-reports"),
               "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt"
            );
            if (â˜ƒxxxxxxx.saveToFile(â˜ƒxxxxxxxxxx)) {
               LOGGER.error("This crash report has been saved to: {}", â˜ƒxxxxxxxxxx.getAbsolutePath());
            } else {
               LOGGER.error("We were unable to save this crash report to disk.");
            }

            this.exit();
         }

         try {
            Thread.sleep(â˜ƒ + this.maxTickTime - â˜ƒx);
         } catch (InterruptedException var15) {
         }
      }
   }

   private void exit() {
      try {
         Timer â˜ƒ = new Timer();
         â˜ƒ.schedule(new TimerTask() {
            public void run() {
               Runtime.getRuntime().halt(1);
            }
         }, 10000L);
         System.exit(1);
      } catch (Throwable var2) {
         Runtime.getRuntime().halt(1);
      }
   }
}
