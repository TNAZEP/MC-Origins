package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.function.Consumer;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FileZipper;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PerfCommand {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final SimpleCommandExceptionType ERROR_NOT_RUNNING = new SimpleCommandExceptionType(new TranslatableComponent("commands.perf.notRunning"));
   private static final SimpleCommandExceptionType ERROR_ALREADY_RUNNING = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.perf.alreadyRunning")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("perf")
            .requires(var0x -> var0x.hasPermission(4))
            .then(Commands.literal("start").executes(var0x -> startProfilingDedicatedServer(var0x.getSource())))
            .then(Commands.literal("stop").executes(var0x -> stopProfilingDedicatedServer(var0x.getSource())))
      );
   }

   private static int startProfilingDedicatedServer(CommandSourceStack var0) throws CommandSyntaxException {
      MinecraftServer â˜ƒ = â˜ƒ.getServer();
      if (â˜ƒ.isRecordingMetrics()) {
         throw ERROR_ALREADY_RUNNING.create();
      } else {
         Consumer<ProfileResults> â˜ƒ = var1x -> whenStopped(â˜ƒ, var1x);
         Consumer<Path> â˜ƒx = var2x -> saveResults(â˜ƒ, var2x, â˜ƒ);
         â˜ƒ.startRecordingMetrics(â˜ƒ, â˜ƒx);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.perf.started"), false);
         return 0;
      }
   }

   private static int stopProfilingDedicatedServer(CommandSourceStack var0) throws CommandSyntaxException {
      MinecraftServer â˜ƒ = â˜ƒ.getServer();
      if (!â˜ƒ.isRecordingMetrics()) {
         throw ERROR_NOT_RUNNING.create();
      } else {
         â˜ƒ.finishRecordingMetrics();
         return 0;
      }
   }

   private static void saveResults(CommandSourceStack var0, Path var1, MinecraftServer var2) {
      String â˜ƒ = String.format(
         "%s-%s-%s",
         new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()),
         â˜ƒ.getWorldData().getLevelName(),
         SharedConstants.getCurrentVersion().getId()
      );

      String â˜ƒ;
      try {
         â˜ƒ = FileUtil.findAvailableName(MetricsPersister.PROFILING_RESULTS_DIR, â˜ƒ, ".zip");
      } catch (IOException var11) {
         â˜ƒ.sendFailure(new TranslatableComponent("commands.perf.reportFailed"));
         LOGGER.error(var11);
         return;
      }

      FileZipper â˜ƒx = new FileZipper(MetricsPersister.PROFILING_RESULTS_DIR.resolve(â˜ƒ));

      try {
         â˜ƒx.add(Paths.get("system.txt"), â˜ƒ.fillSystemReport(new SystemReport()).toLineSeparatedString());
         â˜ƒx.add(â˜ƒ);
      } catch (Throwable var10) {
         try {
            â˜ƒx.close();
         } catch (Throwable var8) {
            var10.addSuppressed(var8);
         }

         throw var10;
      }

      â˜ƒx.close();

      try {
         FileUtils.forceDelete(â˜ƒ.toFile());
      } catch (IOException var9) {
         LOGGER.warn("Failed to delete temporary profiling file {}", â˜ƒ, var9);
      }

      â˜ƒ.sendSuccess(new TranslatableComponent("commands.perf.reportSaved", â˜ƒ), false);
   }

   private static void whenStopped(CommandSourceStack var0, ProfileResults var1) {
      int â˜ƒ = â˜ƒ.getTickDuration();
      double â˜ƒx = (double)â˜ƒ.getNanoDuration() / (double)TimeUtil.NANOSECONDS_PER_SECOND;
      â˜ƒ.sendSuccess(
         new TranslatableComponent(
            "commands.perf.stopped", String.format(Locale.ROOT, "%.2f", â˜ƒx), â˜ƒ, String.format(Locale.ROOT, "%.2f", (double)â˜ƒ / â˜ƒx)
         ),
         false
      );
   }
}
