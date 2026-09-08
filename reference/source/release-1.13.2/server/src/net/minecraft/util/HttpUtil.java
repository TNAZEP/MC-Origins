package net.minecraft.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtil {
   private static final Logger field_151227_b = LogManager.getLogger();
   public static final ListeningExecutorService field_180193_a = MoreExecutors.listeningDecorator(
      Executors.newCachedThreadPool(
         new ThreadFactoryBuilder()
            .setDaemon(true)
            .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_151227_b))
            .setNameFormat("Downloader %d")
            .build()
      )
   );

   public static int func_76181_a() {
      try {
         ServerSocket ☃ = new ServerSocket(0);
         Throwable var1 = null;

         int var2;
         try {
            var2 = ☃.getLocalPort();
         } catch (Throwable var12) {
            var1 = var12;
            throw var12;
         } finally {
            if (☃ != null) {
               if (var1 != null) {
                  try {
                     ☃.close();
                  } catch (Throwable var11) {
                     var1.addSuppressed(var11);
                  }
               } else {
                  ☃.close();
               }
            }
         }

         return var2;
      } catch (IOException var14) {
         return 25564;
      }
   }
}
