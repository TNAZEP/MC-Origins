package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugCommand {
   private static final Logger field_198337_a = LogManager.getLogger();
   private static final SimpleCommandExceptionType field_198338_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.debug.notRunning"));
   private static final SimpleCommandExceptionType field_198339_c = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.debug.alreadyRunning")
   );

   public static void func_198330_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("debug")
            .requires(var0x -> var0x.func_197034_c(3))
            .then(Commands.func_197057_a("start").executes(var0x -> func_198335_a(var0x.getSource())))
            .then(Commands.func_197057_a("stop").executes(var0x -> func_198336_b(var0x.getSource())))
      );
   }

   private static int func_198335_a(CommandSource var0) throws CommandSyntaxException {
      MinecraftServer ☃ = ☃.func_197028_i();
      Profiler ☃x = ☃.field_71304_b;
      if (☃x.func_199094_a()) {
         throw field_198339_c.create();
      } else {
         ☃.func_71223_ag();
         ☃.func_197030_a(new TextComponentTranslation("commands.debug.started", "Started the debug profiler. Type '/debug stop' to stop it."), true);
         return 0;
      }
   }

   private static int func_198336_b(CommandSource var0) throws CommandSyntaxException {
      MinecraftServer ☃ = ☃.func_197028_i();
      Profiler ☃x = ☃.field_71304_b;
      if (!☃x.func_199094_a()) {
         throw field_198338_b.create();
      } else {
         long ☃ = Util.func_211178_c();
         int ☃x = ☃.func_71259_af();
         long ☃xx = ☃ - ☃x.func_199097_c();
         int ☃xxx = ☃x - ☃x.func_199096_d();
         File ☃xxxx = new File(☃.func_71209_f("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
         ☃xxxx.getParentFile().mkdirs();
         Writer ☃xxxxx = null;

         try {
            ☃xxxxx = new OutputStreamWriter(new FileOutputStream(☃xxxx), StandardCharsets.UTF_8);
            ☃xxxxx.write(func_198328_a(☃xx, ☃xxx, ☃x));
         } catch (Throwable var15) {
            field_198337_a.error("Could not save profiler results to {}", ☃xxxx, var15);
         } finally {
            IOUtils.closeQuietly(☃xxxxx);
         }

         ☃x.func_199098_b();
         float ☃xxxxxx = (float)☃xx / 1.0E9F;
         float ☃xxxxxxx = (float)☃xxx / ☃xxxxxx;
         ☃.func_197030_a(
            new TextComponentTranslation("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", ☃xxxxxx), ☃xxx, String.format("%.2f", ☃xxxxxxx)), true
         );
         return MathHelper.func_76141_d(☃xxxxxxx);
      }
   }

   private static String func_198328_a(long var0, int var2, Profiler var3) {
      StringBuilder ☃ = new StringBuilder();
      ☃.append("---- Minecraft Profiler Results ----\n");
      ☃.append("// ");
      ☃.append(func_198331_a());
      ☃.append("\n\n");
      ☃.append("Time span: ").append(☃).append(" ms\n");
      ☃.append("Tick span: ").append(☃).append(" ticks\n");
      ☃.append("// This is approximately ")
         .append(String.format(Locale.ROOT, "%.2f", (float)☃ / ((float)☃ / 1.0E9F)))
         .append(" ticks per second. It should be ")
         .append(20)
         .append(" ticks per second\n\n");
      ☃.append("--- BEGIN PROFILE DUMP ---\n\n");
      func_198334_a(0, "root", ☃, ☃);
      ☃.append("--- END PROFILE DUMP ---\n\n");
      return ☃.toString();
   }

   private static void func_198334_a(int var0, String var1, StringBuilder var2, Profiler var3) {
      List<Profiler.Result> ☃ = ☃.func_76321_b(☃);
      if (☃ != null && ☃.size() >= 3) {
         for(int ☃x = 1; ☃x < ☃.size(); ++☃x) {
            Profiler.Result ☃xx = (Profiler.Result)☃.get(☃x);
            ☃.append(String.format("[%02d] ", ☃));

            for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
               ☃.append("|   ");
            }

            ☃.append(☃xx.field_76331_c)
               .append(" - ")
               .append(String.format(Locale.ROOT, "%.2f", ☃xx.field_76332_a))
               .append("%/")
               .append(String.format(Locale.ROOT, "%.2f", ☃xx.field_76330_b))
               .append("%\n");
            if (!"unspecified".equals(☃xx.field_76331_c)) {
               try {
                  func_198334_a(☃ + 1, ☃ + "." + ☃xx.field_76331_c, ☃, ☃);
               } catch (Exception var8) {
                  ☃.append("[[ EXCEPTION ").append(var8).append(" ]]");
               }
            }
         }
      }
   }

   private static String func_198331_a() {
      String[] ☃ = new String[]{
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
         return ☃[(int)(Util.func_211178_c() % (long)☃.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }
}
