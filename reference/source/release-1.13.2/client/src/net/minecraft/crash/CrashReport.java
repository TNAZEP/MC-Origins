package net.minecraft.crash;

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
import java.util.stream.Collectors;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
   private static final Logger field_147150_a = LogManager.getLogger();
   private final String field_71513_a;
   private final Throwable field_71511_b;
   private final CrashReportCategory field_85061_c = new CrashReportCategory(this, "System Details");
   private final List<CrashReportCategory> field_71512_c = Lists.<CrashReportCategory>newArrayList();
   private File field_71510_d;
   private boolean field_85059_f = true;
   private StackTraceElement[] field_85060_g = new StackTraceElement[0];

   public CrashReport(String var1, Throwable var2) {
      this.field_71513_a = ☃;
      this.field_71511_b = ☃;
      this.func_71504_g();
   }

   private void func_71504_g() {
      this.field_85061_c.func_189529_a("Minecraft Version", () -> "1.13.2");
      this.field_85061_c
         .func_189529_a(
            "Operating System", () -> System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version")
         );
      this.field_85061_c.func_189529_a("Java Version", () -> System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
      this.field_85061_c
         .func_189529_a(
            "Java VM Version",
            () -> System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor")
         );
      this.field_85061_c.func_189529_a("Memory", () -> {
         Runtime ☃ = Runtime.getRuntime();
         long ☃x = ☃.maxMemory();
         long ☃xx = ☃.totalMemory();
         long ☃xxx = ☃.freeMemory();
         long ☃xxxx = ☃x / 1024L / 1024L;
         long ☃xxxxx = ☃xx / 1024L / 1024L;
         long ☃xxxxxx = ☃xxx / 1024L / 1024L;
         return ☃xxx + " bytes (" + ☃xxxxxx + " MB) / " + ☃xx + " bytes (" + ☃xxxxx + " MB) up to " + ☃x + " bytes (" + ☃xxxx + " MB)";
      });
      this.field_85061_c.func_189529_a("JVM Flags", () -> {
         List<String> ☃ = (List)Util.func_211565_f().collect(Collectors.toList());
         return String.format("%d total; %s", ☃.size(), ☃.stream().collect(Collectors.joining(" ")));
      });
   }

   public String func_71501_a() {
      return this.field_71513_a;
   }

   public Throwable func_71505_b() {
      return this.field_71511_b;
   }

   public void func_71506_a(StringBuilder var1) {
      if ((this.field_85060_g == null || this.field_85060_g.length <= 0) && !this.field_71512_c.isEmpty()) {
         this.field_85060_g = ArrayUtils.subarray(((CrashReportCategory)this.field_71512_c.get(0)).func_147152_a(), 0, 1);
      }

      if (this.field_85060_g != null && this.field_85060_g.length > 0) {
         ☃.append("-- Head --\n");
         ☃.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         ☃.append("Stacktrace:\n");

         for(StackTraceElement ☃ : this.field_85060_g) {
            ☃.append("\t").append("at ").append(☃);
            ☃.append("\n");
         }

         ☃.append("\n");
      }

      for(CrashReportCategory ☃ : this.field_71512_c) {
         ☃.func_85072_a(☃);
         ☃.append("\n\n");
      }

      this.field_85061_c.func_85072_a(☃);
   }

   public String func_71498_d() {
      StringWriter ☃ = null;
      PrintWriter ☃x = null;
      Throwable ☃xx = this.field_71511_b;
      if (☃xx.getMessage() == null) {
         if (☃xx instanceof NullPointerException) {
            ☃xx = new NullPointerException(this.field_71513_a);
         } else if (☃xx instanceof StackOverflowError) {
            ☃xx = new StackOverflowError(this.field_71513_a);
         } else if (☃xx instanceof OutOfMemoryError) {
            ☃xx = new OutOfMemoryError(this.field_71513_a);
         }

         ☃xx.setStackTrace(this.field_71511_b.getStackTrace());
      }

      String var4;
      try {
         ☃ = new StringWriter();
         ☃x = new PrintWriter(☃);
         ☃xx.printStackTrace(☃x);
         var4 = ☃.toString();
      } finally {
         IOUtils.closeQuietly(☃);
         IOUtils.closeQuietly(☃x);
      }

      return var4;
   }

   public String func_71502_e() {
      StringBuilder ☃ = new StringBuilder();
      ☃.append("---- Minecraft Crash Report ----\n");
      ☃.append("// ");
      ☃.append(func_71503_h());
      ☃.append("\n\n");
      ☃.append("Time: ");
      ☃.append(new SimpleDateFormat().format(new Date()));
      ☃.append("\n");
      ☃.append("Description: ");
      ☃.append(this.field_71513_a);
      ☃.append("\n\n");
      ☃.append(this.func_71498_d());
      ☃.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for(int ☃x = 0; ☃x < 87; ++☃x) {
         ☃.append("-");
      }

      ☃.append("\n\n");
      this.func_71506_a(☃);
      return ☃.toString();
   }

   public File func_71497_f() {
      return this.field_71510_d;
   }

   public boolean func_147149_a(File var1) {
      if (this.field_71510_d != null) {
         return false;
      } else {
         if (☃.getParentFile() != null) {
            ☃.getParentFile().mkdirs();
         }

         Writer ☃ = null;

         boolean var4;
         try {
            ☃ = new OutputStreamWriter(new FileOutputStream(☃), StandardCharsets.UTF_8);
            ☃.write(this.func_71502_e());
            this.field_71510_d = ☃;
            return true;
         } catch (Throwable var8) {
            field_147150_a.error("Could not save crash report to {}", ☃, var8);
            var4 = false;
         } finally {
            IOUtils.closeQuietly(☃);
         }

         return var4;
      }
   }

   public CrashReportCategory func_85056_g() {
      return this.field_85061_c;
   }

   public CrashReportCategory func_85058_a(String var1) {
      return this.func_85057_a(☃, 1);
   }

   public CrashReportCategory func_85057_a(String var1, int var2) {
      CrashReportCategory ☃ = new CrashReportCategory(this, ☃);
      if (this.field_85059_f) {
         int ☃x = ☃.func_85073_a(☃);
         StackTraceElement[] ☃xx = this.field_71511_b.getStackTrace();
         StackTraceElement ☃xxx = null;
         StackTraceElement ☃xxxx = null;
         int ☃xxxxx = ☃xx.length - ☃x;
         if (☃xxxxx < 0) {
            System.out.println("Negative index in crash report handler (" + ☃xx.length + "/" + ☃x + ")");
         }

         if (☃xx != null && 0 <= ☃xxxxx && ☃xxxxx < ☃xx.length) {
            ☃xxx = ☃xx[☃xxxxx];
            if (☃xx.length + 1 - ☃x < ☃xx.length) {
               ☃xxxx = ☃xx[☃xx.length + 1 - ☃x];
            }
         }

         this.field_85059_f = ☃.func_85069_a(☃xxx, ☃xxxx);
         if (☃x > 0 && !this.field_71512_c.isEmpty()) {
            CrashReportCategory ☃x = (CrashReportCategory)this.field_71512_c.get(this.field_71512_c.size() - 1);
            ☃x.func_85070_b(☃x);
         } else if (☃xx != null && ☃xx.length >= ☃x && 0 <= ☃xxxxx && ☃xxxxx < ☃xx.length) {
            this.field_85060_g = new StackTraceElement[☃xxxxx];
            System.arraycopy(☃xx, 0, this.field_85060_g, 0, this.field_85060_g.length);
         } else {
            this.field_85059_f = false;
         }
      }

      this.field_71512_c.add(☃);
      return ☃;
   }

   private static String func_71503_h() {
      String[] ☃ = new String[]{
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
         return ☃[(int)(Util.func_211178_c() % (long)☃.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   public static CrashReport func_85055_a(Throwable var0, String var1) {
      CrashReport ☃;
      if (☃ instanceof ReportedException) {
         ☃ = ((ReportedException)☃).func_71575_a();
      } else {
         ☃ = new CrashReport(☃, ☃);
      }

      return ☃;
   }
}
