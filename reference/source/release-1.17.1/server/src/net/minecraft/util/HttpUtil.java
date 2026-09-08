package net.minecraft.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtil {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ListeningExecutorService DOWNLOAD_EXECUTOR = MoreExecutors.listeningDecorator(
      Executors.newCachedThreadPool(
         new ThreadFactoryBuilder()
            .setDaemon(true)
            .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER))
            .setNameFormat("Downloader %d")
            .build()
      )
   );

   private HttpUtil() {
   }

   public static String buildQuery(Map<String, Object> var0) {
      StringBuilder â˜ƒ = new StringBuilder();

      for(Entry<String, Object> â˜ƒx : â˜ƒ.entrySet()) {
         if (â˜ƒ.length() > 0) {
            â˜ƒ.append('&');
         }

         try {
            â˜ƒ.append(URLEncoder.encode((String)â˜ƒx.getKey(), "UTF-8"));
         } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
         }

         if (â˜ƒx.getValue() != null) {
            â˜ƒ.append('=');

            try {
               â˜ƒ.append(URLEncoder.encode(â˜ƒx.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException var5) {
               var5.printStackTrace();
            }
         }
      }

      return â˜ƒ.toString();
   }

   public static String performPost(URL var0, Map<String, Object> var1, boolean var2, @Nullable Proxy var3) {
      return performPost(â˜ƒ, buildQuery(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   private static String performPost(URL var0, String var1, boolean var2, @Nullable Proxy var3) {
      try {
         if (â˜ƒ == null) {
            â˜ƒ = Proxy.NO_PROXY;
         }

         HttpURLConnection â˜ƒ = (HttpURLConnection)â˜ƒ.openConnection(â˜ƒ);
         â˜ƒ.setRequestMethod("POST");
         â˜ƒ.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         â˜ƒ.setRequestProperty("Content-Length", â˜ƒ.getBytes().length + "");
         â˜ƒ.setRequestProperty("Content-Language", "en-US");
         â˜ƒ.setUseCaches(false);
         â˜ƒ.setDoInput(true);
         â˜ƒ.setDoOutput(true);
         DataOutputStream â˜ƒx = new DataOutputStream(â˜ƒ.getOutputStream());
         â˜ƒx.writeBytes(â˜ƒ);
         â˜ƒx.flush();
         â˜ƒx.close();
         BufferedReader â˜ƒxx = new BufferedReader(new InputStreamReader(â˜ƒ.getInputStream()));
         StringBuilder â˜ƒxxx = new StringBuilder();

         String â˜ƒ;
         while((â˜ƒ = â˜ƒxx.readLine()) != null) {
            â˜ƒxxx.append(â˜ƒ);
            â˜ƒxxx.append('\r');
         }

         â˜ƒxx.close();
         return â˜ƒxxx.toString();
      } catch (Exception var9) {
         if (!â˜ƒ) {
            LOGGER.error("Could not post to {}", â˜ƒ, var9);
         }

         return "";
      }
   }

   public static CompletableFuture<?> downloadTo(File var0, String var1, Map<String, String> var2, int var3, @Nullable ProgressListener var4, Proxy var5) {
      return CompletableFuture.supplyAsync(() -> {
         HttpURLConnection â˜ƒ = null;
         InputStream â˜ƒx = null;
         OutputStream â˜ƒxx = null;
         if (â˜ƒ != null) {
            â˜ƒ.progressStart(new TranslatableComponent("resourcepack.downloading"));
            â˜ƒ.progressStage(new TranslatableComponent("resourcepack.requesting"));
         }

         try {
            byte[] â˜ƒ = new byte[4096];
            URL â˜ƒx = new URL(â˜ƒ);
            â˜ƒ = (HttpURLConnection)â˜ƒx.openConnection(â˜ƒ);
            â˜ƒ.setInstanceFollowRedirects(true);
            float â˜ƒxx = 0.0F;
            float â˜ƒxxx = (float)â˜ƒ.entrySet().size();

            for(Entry<String, String> â˜ƒxxxx : â˜ƒ.entrySet()) {
               â˜ƒ.setRequestProperty((String)â˜ƒxxxx.getKey(), (String)â˜ƒxxxx.getValue());
               if (â˜ƒ != null) {
                  â˜ƒ.progressStagePercentage((int)(++â˜ƒxx / â˜ƒxxx * 100.0F));
               }
            }

            â˜ƒx = â˜ƒ.getInputStream();
            â˜ƒxxx = (float)â˜ƒ.getContentLength();
            int â˜ƒxxxx = â˜ƒ.getContentLength();
            if (â˜ƒ != null) {
               â˜ƒ.progressStage(new TranslatableComponent("resourcepack.progress", String.format(Locale.ROOT, "%.2f", â˜ƒxxx / 1000.0F / 1000.0F)));
            }

            if (â˜ƒ.exists()) {
               long â˜ƒxxxx = â˜ƒ.length();
               if (â˜ƒxxxx == (long)â˜ƒxxxx) {
                  if (â˜ƒ != null) {
                     â˜ƒ.stop();
                  }

                  return null;
               }

               LOGGER.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", â˜ƒ, â˜ƒxxxx, â˜ƒxxxx);
               FileUtils.deleteQuietly(â˜ƒ);
            } else if (â˜ƒ.getParentFile() != null) {
               â˜ƒ.getParentFile().mkdirs();
            }

            â˜ƒxx = new DataOutputStream(new FileOutputStream(â˜ƒ));
            if (â˜ƒ > 0 && â˜ƒxxx > (float)â˜ƒ) {
               if (â˜ƒ != null) {
                  â˜ƒ.stop();
               }

               throw new IOException("Filesize is bigger than maximum allowed (file is " + â˜ƒxx + ", limit is " + â˜ƒ + ")");
            } else {
               int â˜ƒ;
               while((â˜ƒ = â˜ƒx.read(â˜ƒ)) >= 0) {
                  â˜ƒxx += (float)â˜ƒ;
                  if (â˜ƒ != null) {
                     â˜ƒ.progressStagePercentage((int)(â˜ƒxx / â˜ƒxxx * 100.0F));
                  }

                  if (â˜ƒ > 0 && â˜ƒxx > (float)â˜ƒ) {
                     if (â˜ƒ != null) {
                        â˜ƒ.stop();
                     }

                     throw new IOException("Filesize was bigger than maximum allowed (got >= " + â˜ƒxx + ", limit was " + â˜ƒ + ")");
                  }

                  if (Thread.interrupted()) {
                     LOGGER.error("INTERRUPTED");
                     if (â˜ƒ != null) {
                        â˜ƒ.stop();
                     }

                     return null;
                  }

                  â˜ƒxx.write(â˜ƒ, 0, â˜ƒ);
               }

               if (â˜ƒ != null) {
                  â˜ƒ.stop();
               }

               return null;
            }
         } catch (Throwable var22) {
            var22.printStackTrace();
            if (â˜ƒ != null) {
               InputStream â˜ƒ = â˜ƒ.getErrorStream();

               try {
                  LOGGER.error(IOUtils.toString(â˜ƒ));
               } catch (IOException var21) {
                  var21.printStackTrace();
               }
            }

            if (â˜ƒ != null) {
               â˜ƒ.stop();
            }

            return null;
         } finally {
            IOUtils.closeQuietly(â˜ƒx);
            IOUtils.closeQuietly(â˜ƒxx);
         }
      }, DOWNLOAD_EXECUTOR);
   }

   public static int getAvailablePort() {
      try {
         ServerSocket â˜ƒ = new ServerSocket(0);

         int var1;
         try {
            var1 = â˜ƒ.getLocalPort();
         } catch (Throwable var4) {
            try {
               â˜ƒ.close();
            } catch (Throwable var3) {
               var4.addSuppressed(var3);
            }

            throw var4;
         }

         â˜ƒ.close();
         return var1;
      } catch (IOException var5) {
         return 25564;
      }
   }
}
