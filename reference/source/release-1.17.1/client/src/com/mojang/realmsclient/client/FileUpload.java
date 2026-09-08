package com.mojang.realmsclient.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.gui.screens.UploadResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import net.minecraft.client.User;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUpload {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int MAX_RETRIES = 5;
   private static final String UPLOAD_PATH = "/upload";
   private final File file;
   private final long worldId;
   private final int slotId;
   private final UploadInfo uploadInfo;
   private final String sessionId;
   private final String username;
   private final String clientVersion;
   private final UploadStatus uploadStatus;
   private final AtomicBoolean cancelled = new AtomicBoolean(false);
   private CompletableFuture<UploadResult> uploadTask;
   private final RequestConfig requestConfig = RequestConfig.custom()
      .setSocketTimeout((int)TimeUnit.MINUTES.toMillis(10L))
      .setConnectTimeout((int)TimeUnit.SECONDS.toMillis(15L))
      .build();

   public FileUpload(File var1, long var2, int var4, UploadInfo var5, User var6, String var7, UploadStatus var8) {
      this.file = â˜ƒ;
      this.worldId = â˜ƒ;
      this.slotId = â˜ƒ;
      this.uploadInfo = â˜ƒ;
      this.sessionId = â˜ƒ.getSessionId();
      this.username = â˜ƒ.getName();
      this.clientVersion = â˜ƒ;
      this.uploadStatus = â˜ƒ;
   }

   public void upload(Consumer<UploadResult> var1) {
      if (this.uploadTask == null) {
         this.uploadTask = CompletableFuture.supplyAsync(() -> this.requestUpload(0));
         this.uploadTask.thenAccept(â˜ƒ);
      }
   }

   public void cancel() {
      this.cancelled.set(true);
      if (this.uploadTask != null) {
         this.uploadTask.cancel(false);
         this.uploadTask = null;
      }
   }

   private UploadResult requestUpload(int var1) {
      UploadResult.Builder â˜ƒ = new UploadResult.Builder();
      if (this.cancelled.get()) {
         return â˜ƒ.build();
      } else {
         this.uploadStatus.totalBytes = this.file.length();
         HttpPost â˜ƒ = new HttpPost(this.uploadInfo.getUploadEndpoint().resolve("/upload/" + this.worldId + "/" + this.slotId));
         CloseableHttpClient â˜ƒx = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();

         UploadResult var8;
         try {
            this.setupRequest(â˜ƒ);
            HttpResponse â˜ƒxx = â˜ƒx.execute(â˜ƒ);
            long â˜ƒxxx = this.getRetryDelaySeconds(â˜ƒxx);
            if (!this.shouldRetry(â˜ƒxxx, â˜ƒ)) {
               this.handleResponse(â˜ƒxx, â˜ƒ);
               return â˜ƒ.build();
            }

            var8 = this.retryUploadAfter(â˜ƒxxx, â˜ƒ);
         } catch (Exception var12) {
            if (!this.cancelled.get()) {
               LOGGER.error("Caught exception while uploading: ", var12);
            }

            return â˜ƒ.build();
         } finally {
            this.cleanup(â˜ƒ, â˜ƒx);
         }

         return var8;
      }
   }

   private void cleanup(HttpPost var1, CloseableHttpClient var2) {
      â˜ƒ.releaseConnection();
      if (â˜ƒ != null) {
         try {
            â˜ƒ.close();
         } catch (IOException var4) {
            LOGGER.error("Failed to close Realms upload client");
         }
      }
   }

   private void setupRequest(HttpPost var1) throws FileNotFoundException {
      â˜ƒ.setHeader("Cookie", "sid=" + this.sessionId + ";token=" + this.uploadInfo.getToken() + ";user=" + this.username + ";version=" + this.clientVersion);
      FileUpload.CustomInputStreamEntity â˜ƒ = new FileUpload.CustomInputStreamEntity(new FileInputStream(this.file), this.file.length(), this.uploadStatus);
      â˜ƒ.setContentType("application/octet-stream");
      â˜ƒ.setEntity(â˜ƒ);
   }

   private void handleResponse(HttpResponse var1, UploadResult.Builder var2) throws IOException {
      int â˜ƒ = â˜ƒ.getStatusLine().getStatusCode();
      if (â˜ƒ == 401) {
         LOGGER.debug("Realms server returned 401: {}", â˜ƒ.getFirstHeader("WWW-Authenticate"));
      }

      â˜ƒ.withStatusCode(â˜ƒ);
      if (â˜ƒ.getEntity() != null) {
         String â˜ƒ = EntityUtils.toString(â˜ƒ.getEntity(), "UTF-8");
         if (â˜ƒ != null) {
            try {
               JsonParser â˜ƒx = new JsonParser();
               JsonElement â˜ƒxx = â˜ƒx.parse(â˜ƒ).getAsJsonObject().get("errorMsg");
               Optional<String> â˜ƒxxx = Optional.ofNullable(â˜ƒxx).map(JsonElement::getAsString);
               â˜ƒ.withErrorMessage((String)â˜ƒxxx.orElse(null));
            } catch (Exception var8) {
            }
         }
      }
   }

   private boolean shouldRetry(long var1, int var3) {
      return â˜ƒ > 0L && â˜ƒ + 1 < 5;
   }

   private UploadResult retryUploadAfter(long var1, int var3) throws InterruptedException {
      Thread.sleep(Duration.ofSeconds(â˜ƒ).toMillis());
      return this.requestUpload(â˜ƒ + 1);
   }

   private long getRetryDelaySeconds(HttpResponse var1) {
      return Optional.ofNullable(â˜ƒ.getFirstHeader("Retry-After")).map(Header::getValue).map(Long::valueOf).orElse(0L);
   }

   public boolean isFinished() {
      return this.uploadTask.isDone() || this.uploadTask.isCancelled();
   }

   static class CustomInputStreamEntity extends InputStreamEntity {
      private final long length;
      private final InputStream content;
      private final UploadStatus uploadStatus;

      public CustomInputStreamEntity(InputStream var1, long var2, UploadStatus var4) {
         super(â˜ƒ);
         this.content = â˜ƒ;
         this.length = â˜ƒ;
         this.uploadStatus = â˜ƒ;
      }

      @Override
      public void writeTo(OutputStream var1) throws IOException {
         Args.notNull(â˜ƒ, "Output stream");
         InputStream â˜ƒ = this.content;

         try {
            byte[] â˜ƒxx = new byte[4096];
            int â˜ƒx;
            if (this.length < 0L) {
               while((â˜ƒx = â˜ƒ.read(â˜ƒxx)) != -1) {
                  â˜ƒ.write(â˜ƒxx, 0, â˜ƒx);
                  this.uploadStatus.bytesWritten += (long)â˜ƒx;
               }
            } else {
               long â˜ƒx = this.length;

               while(â˜ƒx > 0L) {
                  â˜ƒx = â˜ƒ.read(â˜ƒxx, 0, (int)Math.min(4096L, â˜ƒx));
                  if (â˜ƒx == -1) {
                     break;
                  }

                  â˜ƒ.write(â˜ƒxx, 0, â˜ƒx);
                  this.uploadStatus.bytesWritten += (long)â˜ƒx;
                  â˜ƒx -= (long)â˜ƒx;
                  â˜ƒ.flush();
               }
            }
         } finally {
            â˜ƒ.close();
         }
      }
   }
}
