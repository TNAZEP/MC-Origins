package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StoredUserList<K, V extends StoredUserEntry<K>> {
   protected static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final File file;
   private final Map<String, V> map = Maps.newHashMap();

   public StoredUserList(File var1) {
      this.file = â˜ƒ;
   }

   public File getFile() {
      return this.file;
   }

   public void add(V var1) {
      this.map.put(this.getKeyForUser(â˜ƒ.getUser()), â˜ƒ);

      try {
         this.save();
      } catch (IOException var3) {
         LOGGER.warn("Could not save the list after adding a user.", var3);
      }
   }

   @Nullable
   public V get(K var1) {
      this.removeExpired();
      return (V)this.map.get(this.getKeyForUser(â˜ƒ));
   }

   public void remove(K var1) {
      this.map.remove(this.getKeyForUser(â˜ƒ));

      try {
         this.save();
      } catch (IOException var3) {
         LOGGER.warn("Could not save the list after removing a user.", var3);
      }
   }

   public void remove(StoredUserEntry<K> var1) {
      this.remove(â˜ƒ.getUser());
   }

   public String[] getUserList() {
      return (String[])this.map.keySet().toArray(new String[0]);
   }

   public boolean isEmpty() {
      return this.map.size() < 1;
   }

   protected String getKeyForUser(K var1) {
      return â˜ƒ.toString();
   }

   protected boolean contains(K var1) {
      return this.map.containsKey(this.getKeyForUser(â˜ƒ));
   }

   private void removeExpired() {
      List<K> â˜ƒ = Lists.<K>newArrayList();

      for(V â˜ƒx : this.map.values()) {
         if (â˜ƒx.hasExpired()) {
            â˜ƒ.add(â˜ƒx.getUser());
         }
      }

      for(K â˜ƒx : â˜ƒ) {
         this.map.remove(this.getKeyForUser(â˜ƒx));
      }
   }

   protected abstract StoredUserEntry<K> createEntry(JsonObject var1);

   public Collection<V> getEntries() {
      return this.map.values();
   }

   public void save() throws IOException {
      JsonArray â˜ƒ = new JsonArray();
      this.map.values().stream().map(var0 -> Util.make(new JsonObject(), var0::serialize)).forEach(â˜ƒ::add);
      BufferedWriter â˜ƒx = Files.newWriter(this.file, StandardCharsets.UTF_8);

      try {
         GSON.toJson(â˜ƒ, â˜ƒx);
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
   }

   public void load() throws IOException {
      if (this.file.exists()) {
         BufferedReader â˜ƒ = Files.newReader(this.file, StandardCharsets.UTF_8);

         try {
            JsonArray â˜ƒx = GSON.fromJson(â˜ƒ, JsonArray.class);
            this.map.clear();

            for(JsonElement â˜ƒxx : â˜ƒx) {
               JsonObject â˜ƒxxx = GsonHelper.convertToJsonObject(â˜ƒxx, "entry");
               StoredUserEntry<K> â˜ƒxxxx = this.createEntry(â˜ƒxxx);
               if (â˜ƒxxxx.getUser() != null) {
                  this.map.put(this.getKeyForUser(â˜ƒxxxx.getUser()), â˜ƒxxxx);
               }
            }
         } catch (Throwable var8) {
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }
      }
   }
}
