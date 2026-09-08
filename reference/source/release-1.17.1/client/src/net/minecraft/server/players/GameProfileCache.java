package net.minecraft.server.players;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameProfileCache {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int GAMEPROFILES_MRU_LIMIT = 1000;
   private static final int GAMEPROFILES_EXPIRATION_MONTHS = 1;
   private static boolean usesAuthentication;
   private final Map<String, GameProfileCache.GameProfileInfo> profilesByName = Maps.newConcurrentMap();
   private final Map<UUID, GameProfileCache.GameProfileInfo> profilesByUUID = Maps.newConcurrentMap();
   private final Map<String, CompletableFuture<Optional<GameProfile>>> requests = Maps.newConcurrentMap();
   private final GameProfileRepository profileRepository;
   private final Gson gson = new GsonBuilder().create();
   private final File file;
   private final AtomicLong operationCount = new AtomicLong();
   @Nullable
   private Executor executor;

   public GameProfileCache(GameProfileRepository var1, File var2) {
      this.profileRepository = â˜ƒ;
      this.file = â˜ƒ;
      Lists.reverse(this.load()).forEach(this::safeAdd);
   }

   private void safeAdd(GameProfileCache.GameProfileInfo var1) {
      GameProfile â˜ƒ = â˜ƒ.getProfile();
      â˜ƒ.setLastAccess(this.getNextOperation());
      String â˜ƒx = â˜ƒ.getName();
      if (â˜ƒx != null) {
         this.profilesByName.put(â˜ƒx.toLowerCase(Locale.ROOT), â˜ƒ);
      }

      UUID â˜ƒ = â˜ƒ.getId();
      if (â˜ƒ != null) {
         this.profilesByUUID.put(â˜ƒ, â˜ƒ);
      }
   }

   private static Optional<GameProfile> lookupGameProfile(GameProfileRepository var0, String var1) {
      final AtomicReference<GameProfile> â˜ƒ = new AtomicReference();
      ProfileLookupCallback â˜ƒx = new ProfileLookupCallback() {
         @Override
         public void onProfileLookupSucceeded(GameProfile var1) {
            â˜ƒ.set(â˜ƒ);
         }

         @Override
         public void onProfileLookupFailed(GameProfile var1, Exception var2x) {
            â˜ƒ.set(null);
         }
      };
      â˜ƒ.findProfilesByNames(new String[]{â˜ƒ}, Agent.MINECRAFT, â˜ƒx);
      GameProfile â˜ƒxx = (GameProfile)â˜ƒ.get();
      if (!usesAuthentication() && â˜ƒxx == null) {
         UUID â˜ƒxxx = Player.createPlayerUUID(new GameProfile(null, â˜ƒ));
         return Optional.of(new GameProfile(â˜ƒxxx, â˜ƒ));
      } else {
         return Optional.ofNullable(â˜ƒxx);
      }
   }

   public static void setUsesAuthentication(boolean var0) {
      usesAuthentication = â˜ƒ;
   }

   private static boolean usesAuthentication() {
      return usesAuthentication;
   }

   public void add(GameProfile var1) {
      Calendar â˜ƒ = Calendar.getInstance();
      â˜ƒ.setTime(new Date());
      â˜ƒ.add(2, 1);
      Date â˜ƒx = â˜ƒ.getTime();
      GameProfileCache.GameProfileInfo â˜ƒxx = new GameProfileCache.GameProfileInfo(â˜ƒ, â˜ƒx);
      this.safeAdd(â˜ƒxx);
      this.save();
   }

   private long getNextOperation() {
      return this.operationCount.incrementAndGet();
   }

   public Optional<GameProfile> get(String var1) {
      String â˜ƒ = â˜ƒ.toLowerCase(Locale.ROOT);
      GameProfileCache.GameProfileInfo â˜ƒx = (GameProfileCache.GameProfileInfo)this.profilesByName.get(â˜ƒ);
      boolean â˜ƒxx = false;
      if (â˜ƒx != null && new Date().getTime() >= â˜ƒx.expirationDate.getTime()) {
         this.profilesByUUID.remove(â˜ƒx.getProfile().getId());
         this.profilesByName.remove(â˜ƒx.getProfile().getName().toLowerCase(Locale.ROOT));
         â˜ƒxx = true;
         â˜ƒx = null;
      }

      Optional<GameProfile> â˜ƒ;
      if (â˜ƒx != null) {
         â˜ƒx.setLastAccess(this.getNextOperation());
         â˜ƒ = Optional.of(â˜ƒx.getProfile());
      } else {
         â˜ƒ = lookupGameProfile(this.profileRepository, â˜ƒ);
         if (â˜ƒ.isPresent()) {
            this.add((GameProfile)â˜ƒ.get());
            â˜ƒxx = false;
         }
      }

      if (â˜ƒxx) {
         this.save();
      }

      return â˜ƒ;
   }

   public void getAsync(String var1, Consumer<Optional<GameProfile>> var2) {
      if (this.executor == null) {
         throw new IllegalStateException("No executor");
      } else {
         CompletableFuture<Optional<GameProfile>> â˜ƒ = (CompletableFuture)this.requests.get(â˜ƒ);
         if (â˜ƒ != null) {
            this.requests.put(â˜ƒ, â˜ƒ.whenCompleteAsync((var1x, var2x) -> â˜ƒ.accept(var1x), this.executor));
         } else {
            this.requests
               .put(
                  â˜ƒ,
                  CompletableFuture.supplyAsync(() -> this.get(â˜ƒ), Util.backgroundExecutor())
                     .whenCompleteAsync((var2x, var3x) -> this.requests.remove(â˜ƒ), this.executor)
                     .whenCompleteAsync((var1x, var2x) -> â˜ƒ.accept(var1x), this.executor)
               );
         }
      }
   }

   public Optional<GameProfile> get(UUID var1) {
      GameProfileCache.GameProfileInfo â˜ƒ = (GameProfileCache.GameProfileInfo)this.profilesByUUID.get(â˜ƒ);
      if (â˜ƒ == null) {
         return Optional.empty();
      } else {
         â˜ƒ.setLastAccess(this.getNextOperation());
         return Optional.of(â˜ƒ.getProfile());
      }
   }

   public void setExecutor(Executor var1) {
      this.executor = â˜ƒ;
   }

   private static DateFormat createDateFormat() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   }

   public List<GameProfileCache.GameProfileInfo> load() {
      List<GameProfileCache.GameProfileInfo> â˜ƒ = Lists.<GameProfileCache.GameProfileInfo>newArrayList();

      try {
         Reader â˜ƒx = Files.newReader(this.file, StandardCharsets.UTF_8);

         Object var9;
         label60: {
            try {
               JsonArray â˜ƒxx = this.gson.fromJson(â˜ƒx, JsonArray.class);
               if (â˜ƒxx == null) {
                  var9 = â˜ƒ;
                  break label60;
               }

               DateFormat â˜ƒxx = createDateFormat();
               â˜ƒxx.forEach(var2x -> readGameProfile(var2x, â˜ƒ).ifPresent(â˜ƒ::add));
            } catch (Throwable var6) {
               if (â˜ƒx != null) {
                  try {
                     â˜ƒx.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }
               }

               throw var6;
            }

            if (â˜ƒx != null) {
               â˜ƒx.close();
            }

            return â˜ƒ;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }

         return (List<GameProfileCache.GameProfileInfo>)var9;
      } catch (FileNotFoundException var7) {
      } catch (JsonParseException | IOException var8) {
         LOGGER.warn("Failed to load profile cache {}", this.file, var8);
      }

      return â˜ƒ;
   }

   public void save() {
      JsonArray â˜ƒ = new JsonArray();
      DateFormat â˜ƒx = createDateFormat();
      this.getTopMRUProfiles(1000).forEach(var2x -> â˜ƒ.add(writeGameProfile(var2x, â˜ƒ)));
      String â˜ƒxx = this.gson.toJson((JsonElement)â˜ƒ);

      try {
         Writer â˜ƒxxx = Files.newWriter(this.file, StandardCharsets.UTF_8);

         try {
            â˜ƒxxx.write(â˜ƒxx);
         } catch (Throwable var8) {
            if (â˜ƒxxx != null) {
               try {
                  â˜ƒxxx.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (â˜ƒxxx != null) {
            â˜ƒxxx.close();
         }
      } catch (IOException var9) {
      }
   }

   private Stream<GameProfileCache.GameProfileInfo> getTopMRUProfiles(int var1) {
      return ImmutableList.copyOf(this.profilesByUUID.values())
         .stream()
         .sorted(Comparator.comparing(GameProfileCache.GameProfileInfo::getLastAccess).reversed())
         .limit((long)â˜ƒ);
   }

   private static JsonElement writeGameProfile(GameProfileCache.GameProfileInfo var0, DateFormat var1) {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.addProperty("name", â˜ƒ.getProfile().getName());
      UUID â˜ƒx = â˜ƒ.getProfile().getId();
      â˜ƒ.addProperty("uuid", â˜ƒx == null ? "" : â˜ƒx.toString());
      â˜ƒ.addProperty("expiresOn", â˜ƒ.format(â˜ƒ.getExpirationDate()));
      return â˜ƒ;
   }

   private static Optional<GameProfileCache.GameProfileInfo> readGameProfile(JsonElement var0, DateFormat var1) {
      if (â˜ƒ.isJsonObject()) {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         JsonElement â˜ƒx = â˜ƒ.get("name");
         JsonElement â˜ƒxx = â˜ƒ.get("uuid");
         JsonElement â˜ƒxxx = â˜ƒ.get("expiresOn");
         if (â˜ƒx != null && â˜ƒxx != null) {
            String â˜ƒxxxx = â˜ƒxx.getAsString();
            String â˜ƒxxxxx = â˜ƒx.getAsString();
            Date â˜ƒxxxxxx = null;
            if (â˜ƒxxx != null) {
               try {
                  â˜ƒxxxxxx = â˜ƒ.parse(â˜ƒxxx.getAsString());
               } catch (ParseException var12) {
               }
            }

            if (â˜ƒxxxxx != null && â˜ƒxxxx != null && â˜ƒxxxxxx != null) {
               UUID â˜ƒ;
               try {
                  â˜ƒ = UUID.fromString(â˜ƒxxxx);
               } catch (Throwable var11) {
                  return Optional.empty();
               }

               return Optional.of(new GameProfileCache.GameProfileInfo(new GameProfile(â˜ƒ, â˜ƒxxxxx), â˜ƒxxxxxx));
            } else {
               return Optional.empty();
            }
         } else {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   static class GameProfileInfo {
      private final GameProfile profile;
      final Date expirationDate;
      private volatile long lastAccess;

      GameProfileInfo(GameProfile var1, Date var2) {
         this.profile = â˜ƒ;
         this.expirationDate = â˜ƒ;
      }

      public GameProfile getProfile() {
         return this.profile;
      }

      public Date getExpirationDate() {
         return this.expirationDate;
      }

      public void setLastAccess(long var1) {
         this.lastAccess = â˜ƒ;
      }

      public long getLastAccess() {
         return this.lastAccess;
      }
   }
}
