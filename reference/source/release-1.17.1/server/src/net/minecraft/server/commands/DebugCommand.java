package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ProfileResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugCommand {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final SimpleCommandExceptionType ERROR_NOT_RUNNING = new SimpleCommandExceptionType(new TranslatableComponent("commands.debug.notRunning"));
   private static final SimpleCommandExceptionType ERROR_ALREADY_RUNNING = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.debug.alreadyRunning")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("debug")
            .requires(var0x -> var0x.hasPermission(3))
            .then(Commands.literal("start").executes(var0x -> start(var0x.getSource())))
            .then(Commands.literal("stop").executes(var0x -> stop(var0x.getSource())))
            .then(
               Commands.literal("function")
                  .requires(var0x -> var0x.hasPermission(3))
                  .then(
                     Commands.argument("name", FunctionArgument.functions())
                        .suggests(FunctionCommand.SUGGEST_FUNCTION)
                        .executes(var0x -> traceFunction(var0x.getSource(), FunctionArgument.getFunctions(var0x, "name")))
                  )
            )
      );
   }

   private static int start(CommandSourceStack var0) throws CommandSyntaxException {
      MinecraftServer â˜ƒ = â˜ƒ.getServer();
      if (â˜ƒ.isTimeProfilerRunning()) {
         throw ERROR_ALREADY_RUNNING.create();
      } else {
         â˜ƒ.startTimeProfiler();
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.debug.started"), true);
         return 0;
      }
   }

   private static int stop(CommandSourceStack var0) throws CommandSyntaxException {
      MinecraftServer â˜ƒ = â˜ƒ.getServer();
      if (!â˜ƒ.isTimeProfilerRunning()) {
         throw ERROR_NOT_RUNNING.create();
      } else {
         ProfileResults â˜ƒ = â˜ƒ.stopTimeProfiler();
         double â˜ƒx = (double)â˜ƒ.getNanoDuration() / (double)TimeUtil.NANOSECONDS_PER_SECOND;
         double â˜ƒxx = (double)â˜ƒ.getTickDuration() / â˜ƒx;
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", â˜ƒx), â˜ƒ.getTickDuration(), String.format("%.2f", â˜ƒxx)),
            true
         );
         return (int)â˜ƒxx;
      }
   }

   private static int traceFunction(CommandSourceStack var0, Collection<CommandFunction> var1) {
      int â˜ƒ = 0;
      MinecraftServer â˜ƒx = â˜ƒ.getServer();
      String â˜ƒxx = "debug-trace-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt";

      try {
         Path â˜ƒxxx = â˜ƒx.getFile("debug").toPath();
         Files.createDirectories(â˜ƒxxx);
         Writer â˜ƒxxxx = Files.newBufferedWriter(â˜ƒxxx.resolve(â˜ƒxx), StandardCharsets.UTF_8);

         try {
            PrintWriter â˜ƒxxxxx = new PrintWriter(â˜ƒxxxx);

            for(CommandFunction â˜ƒxxxxxx : â˜ƒ) {
               â˜ƒxxxxx.println(â˜ƒxxxxxx.getId());
               DebugCommand.Tracer â˜ƒxxxxxxx = new DebugCommand.Tracer(â˜ƒxxxxx);
               â˜ƒ += â˜ƒ.getServer().getFunctions().execute(â˜ƒxxxxxx, â˜ƒ.withSource(â˜ƒxxxxxxx).withMaximumPermission(2), â˜ƒxxxxxxx);
            }
         } catch (Throwable var12) {
            if (â˜ƒxxxx != null) {
               try {
                  â˜ƒxxxx.close();
               } catch (Throwable var11) {
                  var12.addSuppressed(var11);
               }
            }

            throw var12;
         }

         if (â˜ƒxxxx != null) {
            â˜ƒxxxx.close();
         }
      } catch (IOException | UncheckedIOException var13) {
         LOGGER.warn("Tracing failed", var13);
         â˜ƒ.sendFailure(new TranslatableComponent("commands.debug.function.traceFailed"));
      }

      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.debug.function.success.single", â˜ƒ, ((CommandFunction)â˜ƒ.iterator().next()).getId(), â˜ƒxx), true
         );
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.debug.function.success.multiple", â˜ƒ, â˜ƒ.size(), â˜ƒxx), true);
      }

      return â˜ƒ;
   }

   static class Tracer implements CommandSource, ServerFunctionManager.TraceCallbacks {
      public static final int INDENT_OFFSET = 1;
      private final PrintWriter output;
      private int lastIndent;
      private boolean waitingForResult;

      Tracer(PrintWriter var1) {
         this.output = â˜ƒ;
      }

      private void indentAndSave(int var1) {
         this.printIndent(â˜ƒ);
         this.lastIndent = â˜ƒ;
      }

      private void printIndent(int var1) {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ + 1; ++â˜ƒ) {
            this.output.write("    ");
         }
      }

      private void newLine() {
         if (this.waitingForResult) {
            this.output.println();
            this.waitingForResult = false;
         }
      }

      @Override
      public void onCommand(int var1, String var2) {
         this.newLine();
         this.indentAndSave(â˜ƒ);
         this.output.print("[C] ");
         this.output.print(â˜ƒ);
         this.waitingForResult = true;
      }

      @Override
      public void onReturn(int var1, String var2, int var3) {
         if (this.waitingForResult) {
            this.output.print(" -> ");
            this.output.println(â˜ƒ);
            this.waitingForResult = false;
         } else {
            this.indentAndSave(â˜ƒ);
            this.output.print("[R = ");
            this.output.print(â˜ƒ);
            this.output.print("] ");
            this.output.println(â˜ƒ);
         }
      }

      @Override
      public void onCall(int var1, ResourceLocation var2, int var3) {
         this.newLine();
         this.indentAndSave(â˜ƒ);
         this.output.print("[F] ");
         this.output.print(â˜ƒ);
         this.output.print(" size=");
         this.output.println(â˜ƒ);
      }

      @Override
      public void onError(int var1, String var2) {
         this.newLine();
         this.indentAndSave(â˜ƒ + 1);
         this.output.print("[E] ");
         this.output.print(â˜ƒ);
      }

      @Override
      public void sendMessage(Component var1, UUID var2) {
         this.newLine();
         this.printIndent(this.lastIndent + 1);
         this.output.print("[M] ");
         if (â˜ƒ != Util.NIL_UUID) {
            this.output.print(â˜ƒ);
            this.output.print(": ");
         }

         this.output.println(â˜ƒ.getString());
      }

      @Override
      public boolean acceptsSuccess() {
         return true;
      }

      @Override
      public boolean acceptsFailure() {
         return true;
      }

      @Override
      public boolean shouldInformAdmins() {
         return false;
      }

      @Override
      public boolean alwaysAccepts() {
         return true;
      }
   }
}
