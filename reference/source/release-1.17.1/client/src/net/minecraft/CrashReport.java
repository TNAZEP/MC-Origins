package net.minecraft;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import net.minecraft.util.MemoryReserve;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String title;
   private final Throwable exception;
   private final List<CrashReportCategory> details = Lists.<CrashReportCategory>newArrayList();
   private File saveFile;
   private boolean trackingStackTrace = true;
   private StackTraceElement[] uncategorizedStackTrace = new StackTraceElement[0];
   private final SystemReport systemReport = new SystemReport();

   public CrashReport(String var1, Throwable var2) {
      this.title = â˜ƒ;
      this.exception = â˜ƒ;
   }

   public String getTitle() {
      return this.title;
   }

   public Throwable getException() {
      return this.exception;
   }

   public String getDetails() {
      StringBuilder â˜ƒ = new StringBuilder();
      this.getDetails(â˜ƒ);
      return â˜ƒ.toString();
   }

   public void getDetails(StringBuilder var1) {
      if ((this.uncategorizedStackTrace == null || this.uncategorizedStackTrace.length <= 0) && !this.details.isEmpty()) {
         this.uncategorizedStackTrace = ArrayUtils.subarray(((CrashReportCategory)this.details.get(0)).getStacktrace(), 0, 1);
      }

      if (this.uncategorizedStackTrace != null && this.uncategorizedStackTrace.length > 0) {
         â˜ƒ.append("-- Head --\n");
         â˜ƒ.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         â˜ƒ.append("Stacktrace:\n");

         for(StackTraceElement â˜ƒ : this.uncategorizedStackTrace) {
            â˜ƒ.append("\t").append("at ").append(â˜ƒ);
            â˜ƒ.append("\n");
         }

         â˜ƒ.append("\n");
      }

      for(CrashReportCategory â˜ƒ : this.details) {
         â˜ƒ.getDetails(â˜ƒ);
         â˜ƒ.append("\n\n");
      }

      this.systemReport.appendToCrashReportString(â˜ƒ);
   }

   public String getExceptionMessage() {
      StringWriter â˜ƒ = null;
      PrintWriter â˜ƒx = null;
      Throwable â˜ƒxx = this.exception;
      if (â˜ƒxx.getMessage() == null) {
         if (â˜ƒxx instanceof NullPointerException) {
            â˜ƒxx = new NullPointerException(this.title);
         } else if (â˜ƒxx instanceof StackOverflowError) {
            â˜ƒxx = new StackOverflowError(this.title);
         } else if (â˜ƒxx instanceof OutOfMemoryError) {
            â˜ƒxx = new OutOfMemoryError(this.title);
         }

         â˜ƒxx.setStackTrace(this.exception.getStackTrace());
      }

      String var4;
      try {
         â˜ƒ = new StringWriter();
         â˜ƒx = new PrintWriter(â˜ƒ);
         â˜ƒxx.printStackTrace(â˜ƒx);
         var4 = â˜ƒ.toString();
      } finally {
         IOUtils.closeQuietly(â˜ƒ);
         IOUtils.closeQuietly(â˜ƒx);
      }

      return var4;
   }

   public String getFriendlyReport() {
      StringBuilder â˜ƒ = new StringBuilder();
      â˜ƒ.append("---- Minecraft Crash Report ----\n");
      â˜ƒ.append("// ");
      â˜ƒ.append(getErrorComment());
      â˜ƒ.append("\n\n");
      â˜ƒ.append("Time: ");
      â˜ƒ.append(new SimpleDateFormat().format(new Date()));
      â˜ƒ.append("\n");
      â˜ƒ.append("Description: ");
      â˜ƒ.append(this.title);
      â˜ƒ.append("\n\n");
      â˜ƒ.append(this.getExceptionMessage());
      â˜ƒ.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for(int â˜ƒx = 0; â˜ƒx < 87; ++â˜ƒx) {
         â˜ƒ.append("-");
      }

      â˜ƒ.append("\n\n");
      this.getDetails(â˜ƒ);
      return â˜ƒ.toString();
   }

   public File getSaveFile() {
      return this.saveFile;
   }

   public boolean saveToFile(File var1) {
      if (this.saveFile != null) {
         return false;
      } else {
         if (â˜ƒ.getParentFile() != null) {
            â˜ƒ.getParentFile().mkdirs();
         }

         Writer â˜ƒ = null;

         boolean var4;
         try {
            â˜ƒ = new OutputStreamWriter(new FileOutputStream(â˜ƒ), StandardCharsets.UTF_8);
            â˜ƒ.write(this.getFriendlyReport());
            this.saveFile = â˜ƒ;
            return true;
         } catch (Throwable var8) {
            LOGGER.error("Could not save crash report to {}", â˜ƒ, var8);
            var4 = false;
         } finally {
            IOUtils.closeQuietly(â˜ƒ);
         }

         return var4;
      }
   }

   public SystemReport getSystemReport() {
      return this.systemReport;
   }

   public CrashReportCategory addCategory(String var1) {
      return this.addCategory(â˜ƒ, 1);
   }

   public CrashReportCategory addCategory(String var1, int var2) {
      CrashReportCategory â˜ƒ = new CrashReportCategory(â˜ƒ);
      if (this.trackingStackTrace) {
         int â˜ƒx = â˜ƒ.fillInStackTrace(â˜ƒ);
         StackTraceElement[] â˜ƒxx = this.exception.getStackTrace();
         StackTraceElement â˜ƒxxx = null;
         StackTraceElement â˜ƒxxxx = null;
         int â˜ƒxxxxx = â˜ƒxx.length - â˜ƒx;
         if (â˜ƒxxxxx < 0) {
            System.out.println("Negative index in crash report handler (" + â˜ƒxx.length + "/" + â˜ƒx + ")");
         }

         if (â˜ƒxx != null && 0 <= â˜ƒxxxxx && â˜ƒxxxxx < â˜ƒxx.length) {
            â˜ƒxxx = â˜ƒxx[â˜ƒxxxxx];
            if (â˜ƒxx.length + 1 - â˜ƒx < â˜ƒxx.length) {
               â˜ƒxxxx = â˜ƒxx[â˜ƒxx.length + 1 - â˜ƒx];
            }
         }

         this.trackingStackTrace = â˜ƒ.validateStackTrace(â˜ƒxxx, â˜ƒxxxx);
         if (â˜ƒx > 0 && !this.details.isEmpty()) {
            CrashReportCategory â˜ƒx = (CrashReportCategory)this.details.get(this.details.size() - 1);
            â˜ƒx.trimStacktrace(â˜ƒx);
         } else if (â˜ƒxx != null && â˜ƒxx.length >= â˜ƒx && 0 <= â˜ƒxxxxx && â˜ƒxxxxx < â˜ƒxx.length) {
            this.uncategorizedStackTrace = new StackTraceElement[â˜ƒxxxxx];
            System.arraycopy(â˜ƒxx, 0, this.uncategorizedStackTrace, 0, this.uncategorizedStackTrace.length);
         } else {
            this.trackingStackTrace = false;
         }
      }

      this.details.add(â˜ƒ);
      return â˜ƒ;
   }

   private static String getErrorComment() {
      String[] â˜ƒ = new String[]{
         "Who set us up the TNT?",
         "Everything's going to plan. No, really, that was supposed to happen.",
         "Uh... Did I do that?",
         "Oops.",
         "Why did you do that?",
         "I feel sad now :(",
         "My bad.",
         "I'm sorry, Dave.",
         "I let you down. Sorry :(",
         "On the bright side, I bought you a teddy bear!",
         "Daisy, daisy...",
         "Oh - I know what I did wrong!",
         "Hey, that tickles! Hehehe!",
         "I blame Dinnerbone.",
         "You should try our sister game, Minceraft!",
         "Don't be sad. I'll do better next time, I promise!",
         "Don't be sad, have a hug! <3",
         "I just don't know what went wrong :(",
         "Shall we play a game?",
         "Quite honestly, I wouldn't worry myself about that.",
         "I bet Cylons wouldn't have this problem.",
         "Sorry :(",
         "Surprise! Haha. Well, this is awkward.",
         "Would you like a cupcake?",
         "Hi. I'm Minecraft, and I'm a crashaholic.",
         "Ooh. Shiny.",
         "This doesn't make any sense!",
         "Why is it breaking :(",
         "Don't do that.",
         "Ouch. That hurt :(",
         "You're mean.",
         "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]",
         "There are four lights!",
         "But it works on my machine."
      };

      try {
         return â˜ƒ[(int)(Util.getNanos() % (long)â˜ƒ.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   public static CrashReport forThrowable(Throwable var0, String var1) {
      while(â˜ƒ instanceof CompletionException && â˜ƒ.getCause() != null) {
         â˜ƒ = â˜ƒ.getCause();
      }

      CrashReport â˜ƒ;
      if (â˜ƒ instanceof ReportedException) {
         â˜ƒ = ((ReportedException)â˜ƒ).getReport();
      } else {
         â˜ƒ = new CrashReport(â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   public static void preload() {
      MemoryReserve.allocate();
      new CrashReport("Don't panic!", new Throwable()).getFriendlyReport();
   }
}
