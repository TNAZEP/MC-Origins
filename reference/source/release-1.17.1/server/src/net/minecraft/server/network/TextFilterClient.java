package net.minecraft.server.network;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.thread.ProcessorMailbox;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextFilterClient implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final AtomicInteger WORKER_COUNT = new AtomicInteger(1);
   private static final ThreadFactory THREAD_FACTORY = var0 -> {
      Thread â˜ƒ = new Thread(var0);
      â˜ƒ.setName("Chat-Filter-Worker-" + WORKER_COUNT.getAndIncrement());
      return â˜ƒ;
   };
   private final URL chatEndpoint;
   final URL joinEndpoint;
   final URL leaveEndpoint;
   private final String authKey;
   private final int ruleId;
   private final String serverId;
   final TextFilterClient.IgnoreStrategy chatIgnoreStrategy;
   final ExecutorService workerPool;

   private TextFilterClient(URI var1, String var2, int var3, String var4, TextFilterClient.IgnoreStrategy var5, int var6) throws MalformedURLException {
      this.authKey = â˜ƒ;
      this.ruleId = â˜ƒ;
      this.serverId = â˜ƒ;
      this.chatIgnoreStrategy = â˜ƒ;
      this.chatEndpoint = â˜ƒ.resolve("/v1/chat").toURL();
      this.joinEndpoint = â˜ƒ.resolve("/v1/join").toURL();
      this.leaveEndpoint = â˜ƒ.resolve("/v1/leave").toURL();
      this.workerPool = Executors.newFixedThreadPool(â˜ƒ, THREAD_FACTORY);
   }

   @Nullable
   public static TextFilterClient createFromConfig(String var0) {
      if (Strings.isNullOrEmpty(â˜ƒ)) {
         return null;
      } else {
         try {
            JsonObject â˜ƒ = GsonHelper.parse(â˜ƒ);
            URI â˜ƒx = new URI(GsonHelper.getAsString(â˜ƒ, "apiServer"));
            String â˜ƒxx = GsonHelper.getAsString(â˜ƒ, "apiKey");
            if (â˜ƒxx.isEmpty()) {
               throw new IllegalArgumentException("Missing API key");
            } else {
               int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "ruleId", 1);
               String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "serverId", "");
               int â˜ƒxx = GsonHelper.getAsInt(â˜ƒ, "hashesToDrop", -1);
               int â˜ƒxxx = GsonHelper.getAsInt(â˜ƒ, "maxConcurrentRequests", 7);
               TextFilterClient.IgnoreStrategy â˜ƒxxxx = TextFilterClient.IgnoreStrategy.select(â˜ƒxx);
               return new TextFilterClient(â˜ƒx, new Base64().encodeToString(â˜ƒxx.getBytes(StandardCharsets.US_ASCII)), â˜ƒ, â˜ƒx, â˜ƒxxxx, â˜ƒxxx);
            }
         } catch (Exception var9) {
            LOGGER.warn("Failed to parse chat filter config {}", â˜ƒ, var9);
            return null;
         }
      }
   }

   void processJoinOrLeave(GameProfile var1, URL var2, Executor var3) {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.addProperty("server", this.serverId);
      â˜ƒ.addProperty("room", "Chat");
      â˜ƒ.addProperty("user_id", â˜ƒ.getId().toString());
      â˜ƒ.addProperty("user_display_name", â˜ƒ.getName());
      â˜ƒ.execute(() -> {
         try {
            this.processRequest(â˜ƒ, â˜ƒ);
         } catch (Exception var5) {
            LOGGER.warn("Failed to send join/leave packet to {} for player {}", â˜ƒ, â˜ƒ, var5);
         }
      });
   }

   CompletableFuture<TextFilter.FilteredText> requestMessageProcessing(GameProfile var1, String var2, TextFilterClient.IgnoreStrategy var3, Executor var4) {
      if (â˜ƒ.isEmpty()) {
         return CompletableFuture.completedFuture(TextFilter.FilteredText.EMPTY);
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("rule", this.ruleId);
         â˜ƒ.addProperty("server", this.serverId);
         â˜ƒ.addProperty("room", "Chat");
         â˜ƒ.addProperty("player", â˜ƒ.getId().toString());
         â˜ƒ.addProperty("player_display_name", â˜ƒ.getName());
         â˜ƒ.addProperty("text", â˜ƒ);
         return CompletableFuture.supplyAsync(() -> {
            try {
               JsonObject â˜ƒ = this.processRequestResponse(â˜ƒ, this.chatEndpoint);
               boolean â˜ƒx = GsonHelper.getAsBoolean(â˜ƒ, "response", false);
               if (â˜ƒx) {
                  return TextFilter.FilteredText.passThrough(â˜ƒ);
               } else {
                  String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "hashed", null);
                  if (â˜ƒ == null) {
                     return TextFilter.FilteredText.fullyFiltered(â˜ƒ);
                  } else {
                     int â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "hashes").size();
                     return â˜ƒ.shouldIgnore(â˜ƒ, â˜ƒ) ? TextFilter.FilteredText.fullyFiltered(â˜ƒ) : new TextFilter.FilteredText(â˜ƒ, â˜ƒ);
                  }
               }
            } catch (Exception var8) {
               LOGGER.warn("Failed to validate message '{}'", â˜ƒ, var8);
               return TextFilter.FilteredText.fullyFiltered(â˜ƒ);
            }
         }, â˜ƒ);
      }
   }

   public void close() {
      this.workerPool.shutdownNow();
   }

   private void drainStream(InputStream var1) throws IOException {
      byte[] â˜ƒ = new byte[1024];

      while(â˜ƒ.read(â˜ƒ) != -1) {
      }
   }

   private JsonObject processRequestResponse(JsonObject var1, URL var2) throws IOException {
      HttpURLConnection â˜ƒ = this.makeRequest(â˜ƒ, â˜ƒ);
      InputStream â˜ƒx = â˜ƒ.getInputStream();

      JsonObject var13;
      label74: {
         try {
            if (â˜ƒ.getResponseCode() == 204) {
               var13 = new JsonObject();
               break label74;
            }

            try {
               var13 = Streams.parse(new JsonReader(new InputStreamReader(â˜ƒx))).getAsJsonObject();
            } finally {
               this.drainStream(â˜ƒx);
            }
         } catch (Throwable var12) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var10) {
                  var12.addSuppressed(var10);
               }
            }

            throw var12;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }

         return var13;
      }

      if (â˜ƒx != null) {
         â˜ƒx.close();
      }

      return var13;
   }

   private void processRequest(JsonObject var1, URL var2) throws IOException {
      HttpURLConnection â˜ƒ = this.makeRequest(â˜ƒ, â˜ƒ);
      InputStream â˜ƒx = â˜ƒ.getInputStream();

      try {
         this.drainStream(â˜ƒx);
      } catch (Throwable var8) {
         if (â˜ƒx != null) {
            try {
               â˜ƒx.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (â˜ƒx != null) {
         â˜ƒx.close();
      }
   }

   private HttpURLConnection makeRequest(JsonObject var1, URL var2) throws IOException {
      HttpURLConnection â˜ƒ = (HttpURLConnection)â˜ƒ.openConnection();
      â˜ƒ.setConnectTimeout(15000);
      â˜ƒ.setReadTimeout(2000);
      â˜ƒ.setUseCaches(false);
      â˜ƒ.setDoOutput(true);
      â˜ƒ.setDoInput(true);
      â˜ƒ.setRequestMethod("POST");
      â˜ƒ.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      â˜ƒ.setRequestProperty("Accept", "application/json");
      â˜ƒ.setRequestProperty("Authorization", "Basic " + this.authKey);
      â˜ƒ.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.getCurrentVersion().getName());
      OutputStreamWriter â˜ƒx = new OutputStreamWriter(â˜ƒ.getOutputStream(), StandardCharsets.UTF_8);

      try {
         JsonWriter â˜ƒxx = new JsonWriter(â˜ƒx);

         try {
            Streams.write(â˜ƒ, â˜ƒxx);
         } catch (Throwable var10) {
            try {
               â˜ƒxx.close();
            } catch (Throwable var9) {
               var10.addSuppressed(var9);
            }

            throw var10;
         }

         â˜ƒxx.close();
      } catch (Throwable var11) {
         try {
            â˜ƒx.close();
         } catch (Throwable var8) {
            var11.addSuppressed(var8);
         }

         throw var11;
      }

      â˜ƒx.close();
      int â˜ƒxx = â˜ƒ.getResponseCode();
      if (â˜ƒxx >= 200 && â˜ƒxx < 300) {
         return â˜ƒ;
      } else {
         throw new TextFilterClient.RequestFailedException(â˜ƒxx + " " + â˜ƒ.getResponseMessage());
      }
   }

   public TextFilter createContext(GameProfile var1) {
      return new TextFilterClient.PlayerContext(â˜ƒ);
   }

   @FunctionalInterface
   public interface IgnoreStrategy {
      TextFilterClient.IgnoreStrategy NEVER_IGNORE = (var0, var1) -> false;
      TextFilterClient.IgnoreStrategy IGNORE_FULLY_FILTERED = (var0, var1) -> var0.length() == var1;

      static TextFilterClient.IgnoreStrategy ignoreOverThreshold(int var0) {
         return (var1, var2) -> var2 >= â˜ƒ;
      }

      static TextFilterClient.IgnoreStrategy select(int var0) {
         switch(â˜ƒ) {
            case -1:
               return NEVER_IGNORE;
            case 0:
               return IGNORE_FULLY_FILTERED;
            default:
               return ignoreOverThreshold(â˜ƒ);
         }
      }

      boolean shouldIgnore(String var1, int var2);
   }

   class PlayerContext implements TextFilter {
      private final GameProfile profile;
      private final Executor streamExecutor;

      PlayerContext(GameProfile var2) {
         this.profile = â˜ƒ;
         ProcessorMailbox<Runnable> â˜ƒ = ProcessorMailbox.create(TextFilterClient.this.workerPool, "chat stream for " + â˜ƒ.getName());
         this.streamExecutor = â˜ƒ::tell;
      }

      @Override
      public void join() {
         TextFilterClient.this.processJoinOrLeave(this.profile, TextFilterClient.this.joinEndpoint, this.streamExecutor);
      }

      @Override
      public void leave() {
         TextFilterClient.this.processJoinOrLeave(this.profile, TextFilterClient.this.leaveEndpoint, this.streamExecutor);
      }

      @Override
      public CompletableFuture<List<TextFilter.FilteredText>> processMessageBundle(List<String> var1) {
         List<CompletableFuture<TextFilter.FilteredText>> â˜ƒ = (List)â˜ƒ.stream()
            .map(var1x -> TextFilterClient.this.requestMessageProcessing(this.profile, var1x, TextFilterClient.this.chatIgnoreStrategy, this.streamExecutor))
            .collect(ImmutableList.toImmutableList());
         return Util.sequenceFailFast(â˜ƒ).exceptionally(var0 -> ImmutableList.of());
      }

      @Override
      public CompletableFuture<TextFilter.FilteredText> processStreamMessage(String var1) {
         return TextFilterClient.this.requestMessageProcessing(this.profile, â˜ƒ, TextFilterClient.this.chatIgnoreStrategy, this.streamExecutor);
      }
   }

   public static class RequestFailedException extends RuntimeException {
      RequestFailedException(String var1) {
         super(â˜ƒ);
      }
   }
}
