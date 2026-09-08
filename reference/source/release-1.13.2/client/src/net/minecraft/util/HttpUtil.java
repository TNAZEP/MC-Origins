package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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

   public static ListenableFuture<?> func_180192_a(File var0, String var1, Map<String, String> var2, int var3, @Nullable IProgressUpdate var4, Proxy var5) {
      return field_180193_a.submit((Runnable)(() -> {
         HttpURLConnection ☃ = null;
         InputStream ☃x = null;
         OutputStream ☃xx = null;
         if (☃ != null) {
            ☃.func_200211_b(new TextComponentTranslation("resourcepack.downloading"));
            ☃.func_200209_c(new TextComponentTranslation("resourcepack.requesting"));
         }

         try {
            byte[] ☃ = new byte[4096];
            URL ☃x = new URL(☃);
            ☃ = (HttpURLConnection)☃x.openConnection(☃);
            ☃.setInstanceFollowRedirects(true);
            float ☃xx = 0.0F;
            float ☃xxx = (float)☃.entrySet().size();

            for(Entry<String, String> ☃xxxx : ☃.entrySet()) {
               ☃.setRequestProperty((String)☃xxxx.getKey(), (String)☃xxxx.getValue());
               if (☃ != null) {
                  ☃.func_73718_a((int)(++☃xx / ☃xxx * 100.0F));
               }
            }

            ☃x = ☃.getInputStream();
            ☃xxx = (float)☃.getContentLength();
            int ☃xxxx = ☃.getContentLength();
            if (☃ != null) {
               ☃.func_200209_c(new TextComponentTranslation("resourcepack.progress", String.format(Locale.ROOT, "%.2f", ☃xxx / 1000.0F / 1000.0F)));
            }

            if (☃.exists()) {
               long ☃xxxx = ☃.length();
               if (☃xxxx == (long)☃xxxx) {
                  if (☃ != null) {
                     ☃.func_146586_a();
                  }

                  return;
               }

               field_151227_b.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", ☃, ☃xxxx, ☃xxxx);
               FileUtils.deleteQuietly(☃);
            } else if (☃.getParentFile() != null) {
               ☃.getParentFile().mkdirs();
            }

            ☃xx = new DataOutputStream(new FileOutputStream(☃));
            if (☃ > 0 && ☃xxx > (float)☃) {
               if (☃ != null) {
                  ☃.func_146586_a();
               }

               throw new IOException("Filesize is bigger than maximum allowed (file is " + ☃xx + ", limit is " + ☃ + ")");
            }

            while(true) {
               int ☃xxxx;
               if ((☃xxxx = ☃x.read(☃)) < 0) {
                  if (☃ != null) {
                     ☃.func_146586_a();
                  }

                  return;
               }

               ☃xx += (float)☃xxxx;
               if (☃ != null) {
                  ☃.func_73718_a((int)(☃xx / ☃xxx * 100.0F));
               }

               if (☃ > 0 && ☃xx > (float)☃) {
                  if (☃ != null) {
                     ☃.func_146586_a();
                  }

                  throw new IOException("Filesize was bigger than maximum allowed (got >= " + ☃xx + ", limit was " + ☃ + ")");
               }

               if (Thread.interrupted()) {
                  field_151227_b.error("INTERRUPTED");
                  if (☃ != null) {
                     ☃.func_146586_a();
                  }
                  break;
               }

               ☃xx.write(☃, 0, ☃xxxx);
            }
         } catch (Throwable var21) {
            var21.printStackTrace();
            if (☃ != null) {
               InputStream ☃ = ☃.getErrorStream();

               try {
                  field_151227_b.error(IOUtils.toString(☃));
               } catch (IOException var20) {
                  var20.printStackTrace();
               }
            }

            if (☃ != null) {
               ☃.func_146586_a();
            }

            return;
         } finally {
            IOUtils.closeQuietly(☃x);
            IOUtils.closeQuietly(☃xx);
         }
      }));
   }

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
