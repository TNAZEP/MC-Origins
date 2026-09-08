package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import java.util.Optional;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.Session;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
   private static final Logger field_199741_a = LogManager.getLogger();

   public static void main(String[] var0) {
      OptionParser ☃ = new OptionParser();
      ☃.allowsUnrecognizedOptions();
      ☃.accepts("demo");
      ☃.accepts("fullscreen");
      ☃.accepts("checkGlErrors");
      OptionSpec<String> ☃x = ☃.accepts("server").withRequiredArg();
      OptionSpec<Integer> ☃xx = ☃.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
      OptionSpec<File> ☃xxx = ☃.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
      OptionSpec<File> ☃xxxx = ☃.accepts("assetsDir").withRequiredArg().ofType(File.class);
      OptionSpec<File> ☃xxxxx = ☃.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
      OptionSpec<String> ☃xxxxxx = ☃.accepts("proxyHost").withRequiredArg();
      OptionSpec<Integer> ☃xxxxxxx = ☃.accepts("proxyPort").withRequiredArg().defaultsTo("8080").ofType(Integer.class);
      OptionSpec<String> ☃xxxxxxxx = ☃.accepts("proxyUser").withRequiredArg();
      OptionSpec<String> ☃xxxxxxxxx = ☃.accepts("proxyPass").withRequiredArg();
      OptionSpec<String> ☃xxxxxxxxxx = ☃.accepts("username").withRequiredArg().defaultsTo("Player" + Util.func_211177_b() % 1000L);
      OptionSpec<String> ☃xxxxxxxxxxx = ☃.accepts("uuid").withRequiredArg();
      OptionSpec<String> ☃xxxxxxxxxxxx = ☃.accepts("accessToken").withRequiredArg().required();
      OptionSpec<String> ☃xxxxxxxxxxxxx = ☃.accepts("version").withRequiredArg().required();
      OptionSpec<Integer> ☃xxxxxxxxxxxxxx = ☃.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
      OptionSpec<Integer> ☃xxxxxxxxxxxxxxx = ☃.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
      OptionSpec<Integer> ☃xxxxxxxxxxxxxxxx = ☃.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
      OptionSpec<Integer> ☃xxxxxxxxxxxxxxxxx = ☃.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
      OptionSpec<String> ☃xxxxxxxxxxxxxxxxxx = ☃.accepts("userProperties").withRequiredArg().defaultsTo("{}");
      OptionSpec<String> ☃xxxxxxxxxxxxxxxxxxx = ☃.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
      OptionSpec<String> ☃xxxxxxxxxxxxxxxxxxxx = ☃.accepts("assetIndex").withRequiredArg();
      OptionSpec<String> ☃xxxxxxxxxxxxxxxxxxxxx = ☃.accepts("userType").withRequiredArg().defaultsTo("legacy");
      OptionSpec<String> ☃xxxxxxxxxxxxxxxxxxxxxx = ☃.accepts("versionType").withRequiredArg().defaultsTo("release");
      OptionSpec<String> ☃xxxxxxxxxxxxxxxxxxxxxxx = ☃.nonOptions();
      OptionSet ☃xxxxxxxxxxxxxxxxxxxxxxxx = ☃.parse(☃);
      List<String> ☃xxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx.valuesOf(☃xxxxxxxxxxxxxxxxxxxxxxx);
      if (!☃xxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
         System.out.println("Completely ignored arguments: " + ☃xxxxxxxxxxxxxxxxxxxxxxxxx);
      }

      String ☃ = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxx);
      Proxy ☃x = Proxy.NO_PROXY;
      if (☃ != null) {
         try {
            ☃x = new Proxy(Type.SOCKS, new InetSocketAddress(☃, func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxx)));
         } catch (Exception var52) {
         }
      }

      final String ☃ = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxx);
      final String ☃x = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxx);
      if (!☃x.equals(Proxy.NO_PROXY) && func_110121_a(☃) && func_110121_a(☃x)) {
         Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(☃, ☃.toCharArray());
            }
         });
      }

      int ☃ = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx);
      int ☃x = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx);
      Optional<Integer> ☃xx = Optional.ofNullable(func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx));
      Optional<Integer> ☃xxx = Optional.ofNullable(func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx));
      boolean ☃xxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx.has("fullscreen");
      boolean ☃xxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx.has("demo");
      String ☃xxxxxx = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx);
      Gson ☃xxxxxxx = new GsonBuilder().registerTypeAdapter(PropertyMap.class, new Serializer()).create();
      PropertyMap ☃xxxxxxxx = JsonUtils.func_188178_a(☃xxxxxxx, func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx), PropertyMap.class);
      PropertyMap ☃xxxxxxxxx = JsonUtils.func_188178_a(☃xxxxxxx, func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx), PropertyMap.class);
      String ☃xxxxxxxxxx = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxx);
      File ☃xxxxxxxxxxx = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxx);
      File ☃xxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx.has(☃xxxx) ? func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxx) : new File(☃xxxxxxxxxxx, "assets/");
      File ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx.has(☃xxxxx) ? func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxx) : new File(☃xxxxxxxxxxx, "resourcepacks/");
      String ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx.has(☃xxxxxxxxxxx)
         ? (String)☃xxxxxxxxxxx.value(☃xxxxxxxxxxxxxxxxxxxxxxxx)
         : EntityPlayer.func_175147_b((String)☃xxxxxxxxxx.value(☃xxxxxxxxxxxxxxxxxxxxxxxx)).toString();
      String ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx.has(☃xxxxxxxxxxxxxxxxxxxx) ? (String)☃xxxxxxxxxxxxxxxxxxxx.value(☃xxxxxxxxxxxxxxxxxxxxxxxx) : null;
      String ☃xxxxxxxxxxxxxxxx = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃x);
      Integer ☃xxxxxxxxxxxxxxxxx = func_206236_a(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xx);
      Session ☃xxxxxxxxxxxxxxxxxx = new Session(
         (String)☃xxxxxxxxxx.value(☃xxxxxxxxxxxxxxxxxxxxxxxx),
         ☃xxxxxxxxxxxxxx,
         (String)☃xxxxxxxxxxxx.value(☃xxxxxxxxxxxxxxxxxxxxxxxx),
         (String)☃xxxxxxxxxxxxxxxxxxxxx.value(☃xxxxxxxxxxxxxxxxxxxxxxxx)
      );
      GameConfiguration ☃xxxxxxxxxxxxxxxxxxx = new GameConfiguration(
         new GameConfiguration.UserInformation(☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, ☃x),
         new GameConfiguration.DisplayInformation(☃, ☃x, ☃xx, ☃xxx, ☃xxxx),
         new GameConfiguration.FolderInformation(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx),
         new GameConfiguration.GameInformation(☃xxxxx, ☃xxxxxx, ☃xxxxxxxxxx),
         new GameConfiguration.ServerInformation(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx)
      );
      Thread ☃xxxxxxxxxxxxxxxxxxxx = new Thread("Client Shutdown Thread") {
         public void run() {
            Minecraft.func_71363_D();
         }
      };
      ☃xxxxxxxxxxxxxxxxxxxx.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_199741_a));
      Runtime.getRuntime().addShutdownHook(☃xxxxxxxxxxxxxxxxxxxx);
      Thread.currentThread().setName("Client thread");
      new Minecraft(☃xxxxxxxxxxxxxxxxxxx).func_99999_d();
   }

   private static <T> T func_206236_a(OptionSet var0, OptionSpec<T> var1) {
      try {
         return ☃.valueOf(☃);
      } catch (Throwable var5) {
         if (☃ instanceof ArgumentAcceptingOptionSpec) {
            ArgumentAcceptingOptionSpec<T> ☃ = (ArgumentAcceptingOptionSpec)☃;
            List<T> ☃x = ☃.defaultValues();
            if (!☃x.isEmpty()) {
               return (T)☃x.get(0);
            }
         }

         throw var5;
      }
   }

   private static boolean func_110121_a(String var0) {
      return ☃ != null && !☃.isEmpty();
   }

   static {
      System.setProperty("java.awt.headless", "true");
   }
}
