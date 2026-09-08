package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.CrashReport;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
   static final Logger LOGGER = LogManager.getLogger();

   @DontObfuscate
   public static void main(String[] var0) {
      SharedConstants.tryDetectVersion();
      OptionParser â˜ƒ = new OptionParser();
      â˜ƒ.allowsUnrecognizedOptions();
      â˜ƒ.accepts("demo");
      â˜ƒ.accepts("disableMultiplayer");
      â˜ƒ.accepts("disableChat");
      â˜ƒ.accepts("fullscreen");
      â˜ƒ.accepts("checkGlErrors");
      OptionSpec<String> â˜ƒx = â˜ƒ.accepts("server").withRequiredArg();
      OptionSpec<Integer> â˜ƒxx = â˜ƒ.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
      OptionSpec<File> â˜ƒxxx = â˜ƒ.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
      OptionSpec<File> â˜ƒxxxx = â˜ƒ.accepts("assetsDir").withRequiredArg().ofType(File.class);
      OptionSpec<File> â˜ƒxxxxx = â˜ƒ.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
      OptionSpec<String> â˜ƒxxxxxx = â˜ƒ.accepts("proxyHost").withRequiredArg();
      OptionSpec<Integer> â˜ƒxxxxxxx = â˜ƒ.accepts("proxyPort").withRequiredArg().defaultsTo("8080").ofType(Integer.class);
      OptionSpec<String> â˜ƒxxxxxxxx = â˜ƒ.accepts("proxyUser").withRequiredArg();
      OptionSpec<String> â˜ƒxxxxxxxxx = â˜ƒ.accepts("proxyPass").withRequiredArg();
      OptionSpec<String> â˜ƒxxxxxxxxxx = â˜ƒ.accepts("username").withRequiredArg().defaultsTo("Player" + Util.getMillis() % 1000L);
      OptionSpec<String> â˜ƒxxxxxxxxxxx = â˜ƒ.accepts("uuid").withRequiredArg();
      OptionSpec<String> â˜ƒxxxxxxxxxxxx = â˜ƒ.accepts("accessToken").withRequiredArg().required();
      OptionSpec<String> â˜ƒxxxxxxxxxxxxx = â˜ƒ.accepts("version").withRequiredArg().required();
      OptionSpec<Integer> â˜ƒxxxxxxxxxxxxxx = â˜ƒ.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
      OptionSpec<Integer> â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
      OptionSpec<Integer> â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
      OptionSpec<Integer> â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
      OptionSpec<String> â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ.accepts("userProperties").withRequiredArg().defaultsTo("{}");
      OptionSpec<String> â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒ.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
      OptionSpec<String> â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒ.accepts("assetIndex").withRequiredArg();
      OptionSpec<String> â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.accepts("userType").withRequiredArg().defaultsTo("legacy");
      OptionSpec<String> â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.accepts("versionType").withRequiredArg().defaultsTo("release");
      OptionSpec<String> â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.nonOptions();
      OptionSet â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.parse(â˜ƒ);
      List<String> â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.valuesOf(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx);
      if (!â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
         System.out.println("Completely ignored arguments: " + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx);
      }

      String â˜ƒ = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxx);
      Proxy â˜ƒx = Proxy.NO_PROXY;
      if (â˜ƒ != null) {
         try {
            â˜ƒx = new Proxy(Type.SOCKS, new InetSocketAddress(â˜ƒ, parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxx)));
         } catch (Exception var70) {
         }
      }

      final String â˜ƒ = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxx);
      final String â˜ƒx = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxx);
      if (!â˜ƒx.equals(Proxy.NO_PROXY) && stringHasValue(â˜ƒ) && stringHasValue(â˜ƒx)) {
         Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(â˜ƒ, â˜ƒ.toCharArray());
            }
         });
      }

      int â˜ƒ = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx);
      int â˜ƒx = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
      OptionalInt â˜ƒxx = ofNullable(parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx));
      OptionalInt â˜ƒxxx = ofNullable(parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx));
      boolean â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.has("fullscreen");
      boolean â˜ƒxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.has("demo");
      boolean â˜ƒxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.has("disableMultiplayer");
      boolean â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.has("disableChat");
      String â˜ƒxxxxxxxx = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx);
      Gson â˜ƒxxxxxxxxx = new GsonBuilder().registerTypeAdapter(PropertyMap.class, new Serializer()).create();
      PropertyMap â˜ƒxxxxxxxxxx = GsonHelper.fromJson(â˜ƒxxxxxxxxx, parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx), PropertyMap.class);
      PropertyMap â˜ƒxxxxxxxxxxx = GsonHelper.fromJson(â˜ƒxxxxxxxxx, parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx), PropertyMap.class);
      String â˜ƒxxxxxxxxxxxx = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxx);
      File â˜ƒxxxxxxxxxxxxx = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxx);
      File â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.has(â˜ƒxxxx)
         ? parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxx)
         : new File(â˜ƒxxxxxxxxxxxxx, "assets/");
      File â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.has(â˜ƒxxxxx)
         ? parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxx)
         : new File(â˜ƒxxxxxxxxxxxxx, "resourcepacks/");
      String â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.has(â˜ƒxxxxxxxxxxx)
         ? (String)â˜ƒxxxxxxxxxxx.value(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx)
         : Player.createPlayerUUID((String)â˜ƒxxxxxxxxxx.value(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx)).toString();
      String â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.has(â˜ƒxxxxxxxxxxxxxxxxxxxx)
         ? (String)â˜ƒxxxxxxxxxxxxxxxxxxxx.value(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx)
         : null;
      String â˜ƒxxxxxxxxxxxxxxxxxx = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒx);
      Integer â˜ƒxxxxxxxxxxxxxxxxxxx = parseArgument(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxx);
      CrashReport.preload();
      Bootstrap.bootStrap();
      Bootstrap.validate();
      Util.startTimerHackThread();
      User â˜ƒxxxxxxxxxxxxxxxxxxxx = new User(
         (String)â˜ƒxxxxxxxxxx.value(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx),
         â˜ƒxxxxxxxxxxxxxxxx,
         (String)â˜ƒxxxxxxxxxxxx.value(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx),
         (String)â˜ƒxxxxxxxxxxxxxxxxxxxxx.value(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      GameConfig â˜ƒxxxxxxxxxxxxxxxxxxxxx = new GameConfig(
         new GameConfig.UserData(â˜ƒxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒx),
         new DisplayData(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx),
         new GameConfig.FolderData(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx),
         new GameConfig.GameData(â˜ƒxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx),
         new GameConfig.ServerData(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx)
      );
      Thread â˜ƒxxxxxxxxxxxxxxxxxxxxxx = new Thread("Client Shutdown Thread") {
         public void run() {
            Minecraft â˜ƒ = Minecraft.getInstance();
            if (â˜ƒ != null) {
               IntegratedServer â˜ƒx = â˜ƒ.getSingleplayerServer();
               if (â˜ƒx != null) {
                  â˜ƒx.halt(true);
               }
            }
         }
      };
      â˜ƒxxxxxxxxxxxxxxxxxxxxxx.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
      Runtime.getRuntime().addShutdownHook(â˜ƒxxxxxxxxxxxxxxxxxxxxxx);
      new RenderPipeline();

      final Minecraft â˜ƒ;
      try {
         Thread.currentThread().setName("Render thread");
         RenderSystem.initRenderThread();
         RenderSystem.beginInitialization();
         â˜ƒ = new Minecraft(â˜ƒxxxxxxxxxxxxxxxxxxxxx);
         RenderSystem.finishInitialization();
      } catch (SilentInitException var68) {
         LOGGER.warn("Failed to create window: ", var68);
         return;
      } catch (Throwable var69) {
         CrashReport â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = CrashReport.forThrowable(var69, "Initializing game");
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.addCategory("Initialization");
         Minecraft.fillReport(null, null, â˜ƒxxxxxxxxxxxxxxxxxxxxx.game.launchVersion, null, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx);
         Minecraft.crash(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx);
         return;
      }

      Thread â˜ƒxxxxxxxxxxxxxxxxxxxxxxx;
      if (â˜ƒ.renderOnThread()) {
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = new Thread("Game thread") {
            public void run() {
               try {
                  RenderSystem.initGameThread(true);
                  â˜ƒ.run();
               } catch (Throwable var2) {
                  Main.LOGGER.error("Exception in client thread", var2);
               }
            }
         };
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.start();

         while(â˜ƒ.isRunning()) {
         }
      } else {
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = null;

         try {
            RenderSystem.initGameThread(false);
            â˜ƒ.run();
         } catch (Throwable var67) {
            LOGGER.error("Unhandled game exception", var67);
         }
      }

      BufferUploader.reset();

      try {
         â˜ƒ.stop();
         if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx != null) {
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.join();
         }
      } catch (InterruptedException var65) {
         LOGGER.error("Exception during client thread shutdown", var65);
      } finally {
         â˜ƒ.destroy();
      }
   }

   private static OptionalInt ofNullable(@Nullable Integer var0) {
      return â˜ƒ != null ? OptionalInt.of(â˜ƒ) : OptionalInt.empty();
   }

   @Nullable
   private static <T> T parseArgument(OptionSet var0, OptionSpec<T> var1) {
      try {
         return â˜ƒ.valueOf(â˜ƒ);
      } catch (Throwable var5) {
         if (â˜ƒ instanceof ArgumentAcceptingOptionSpec â˜ƒ) {
            List<T> â˜ƒx = â˜ƒ.defaultValues();
            if (!â˜ƒx.isEmpty()) {
               return (T)â˜ƒx.get(0);
            }
         }

         throw var5;
      }
   }

   private static boolean stringHasValue(@Nullable String var0) {
      return â˜ƒ != null && !â˜ƒ.isEmpty();
   }

   static {
      System.setProperty("java.awt.headless", "true");
   }
}
