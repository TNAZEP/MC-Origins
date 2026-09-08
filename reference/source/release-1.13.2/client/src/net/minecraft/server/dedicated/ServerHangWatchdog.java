package net.minecraft.server.dedicated;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerHangWatchdog implements Runnable {
   private static final Logger field_180251_a = LogManager.getLogger();
   private final DedicatedServer field_180249_b;
   private final long field_180250_c;

   public ServerHangWatchdog(DedicatedServer var1) {
      this.field_180249_b = ☃;
      this.field_180250_c = ☃.func_175593_aQ();
   }

   public void run() {
      while(this.field_180249_b.func_71278_l()) {
         long ☃ = this.field_180249_b.func_211150_az();
         long ☃x = Util.func_211177_b();
         long ☃xx = ☃x - ☃;
         if (☃xx > this.field_180250_c) {
            field_180251_a.fatal(
               "A single server tick took {} seconds (should be max {})",
               String.format(Locale.ROOT, "%.2f", (float)☃xx / 1000.0F),
               String.format(Locale.ROOT, "%.2f", 0.05F)
            );
            field_180251_a.fatal("Considering it to be crashed, server will forcibly shutdown.");
            ThreadMXBean ☃xxx = ManagementFactory.getThreadMXBean();
            ThreadInfo[] ☃xxxx = ☃xxx.dumpAllThreads(true, true);
            StringBuilder ☃xxxxx = new StringBuilder();
            Error ☃xxxxxx = new Error();

            for(ThreadInfo ☃xxxxxxx : ☃xxxx) {
               if (☃xxxxxxx.getThreadId() == this.field_180249_b.func_175583_aK().getId()) {
                  ☃xxxxxx.setStackTrace(☃xxxxxxx.getStackTrace());
               }

               ☃xxxxx.append(☃xxxxxxx);
               ☃xxxxx.append("\n");
            }

            CrashReport ☃xxxxxxx = new CrashReport("Watching Server", ☃xxxxxx);
            this.field_180249_b.func_71230_b(☃xxxxxxx);
            CrashReportCategory ☃xxxxxxxx = ☃xxxxxxx.func_85058_a("Thread Dump");
            ☃xxxxxxxx.func_71507_a("Threads", ☃xxxxx);
            File ☃xxxxxxxxx = new File(
               new File(this.field_180249_b.func_71238_n(), "crash-reports"),
               "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt"
            );
            if (☃xxxxxxx.func_147149_a(☃xxxxxxxxx)) {
               field_180251_a.error("This crash report has been saved to: {}", ☃xxxxxxxxx.getAbsolutePath());
            } else {
               field_180251_a.error("We were unable to save this crash report to disk.");
            }

            this.func_180248_a();
         }

         try {
            Thread.sleep(☃ + this.field_180250_c - ☃x);
         } catch (InterruptedException var15) {
         }
      }
   }

   private void func_180248_a() {
      try {
         Timer ☃ = new Timer();
         ☃.schedule(new TimerTask() {
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
