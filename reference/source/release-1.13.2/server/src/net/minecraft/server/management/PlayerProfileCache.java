package net.minecraft.server.management;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.IOUtils;

public class PlayerProfileCache {
   public static final SimpleDateFormat field_152659_a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   private static boolean field_187322_c;
   private final Map<String, PlayerProfileCache.ProfileEntry> field_152661_c = Maps.newHashMap();
   private final Map<UUID, PlayerProfileCache.ProfileEntry> field_152662_d = Maps.newHashMap();
   private final Deque<GameProfile> field_152663_e = Lists.<GameProfile>newLinkedList();
   private final GameProfileRepository field_187323_g;
   protected final Gson field_152660_b;
   private final File field_152665_g;
   private static final ParameterizedType field_152666_h = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{PlayerProfileCache.ProfileEntry.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };

   public PlayerProfileCache(GameProfileRepository var1, File var2) {
      this.field_187323_g = ☃;
      this.field_152665_g = ☃;
      GsonBuilder ☃ = new GsonBuilder();
      ☃.registerTypeHierarchyAdapter(PlayerProfileCache.ProfileEntry.class, new PlayerProfileCache.Serializer());
      this.field_152660_b = ☃.create();
      this.func_152657_b();
   }

   private static GameProfile func_187319_a(GameProfileRepository var0, String var1) {
      final GameProfile[] ☃ = new GameProfile[1];
      ProfileLookupCallback ☃x = new ProfileLookupCallback() {
         @Override
         public void onProfileLookupSucceeded(GameProfile var1) {
            ☃[0] = ☃;
         }

         @Override
         public void onProfileLookupFailed(GameProfile var1, Exception var2x) {
            ☃[0] = null;
         }
      };
      ☃.findProfilesByNames(new String[]{☃}, Agent.MINECRAFT, ☃x);
      if (!func_187321_d() && ☃[0] == null) {
         UUID ☃xx = EntityPlayer.func_146094_a(new GameProfile(null, ☃));
         GameProfile ☃xxx = new GameProfile(☃xx, ☃);
         ☃x.onProfileLookupSucceeded(☃xxx);
      }

      return ☃[0];
   }

   public static void func_187320_a(boolean var0) {
      field_187322_c = ☃;
   }

   private static boolean func_187321_d() {
      return field_187322_c;
   }

   public void func_152649_a(GameProfile var1) {
      this.func_152651_a(☃, null);
   }

   private void func_152651_a(GameProfile var1, Date var2) {
      UUID ☃ = ☃.getId();
      if (☃ == null) {
         Calendar ☃x = Calendar.getInstance();
         ☃x.setTime(new Date());
         ☃x.add(2, 1);
         ☃ = ☃x.getTime();
      }

      PlayerProfileCache.ProfileEntry ☃ = new PlayerProfileCache.ProfileEntry(☃, ☃);
      if (this.field_152662_d.containsKey(☃)) {
         PlayerProfileCache.ProfileEntry ☃x = (PlayerProfileCache.ProfileEntry)this.field_152662_d.get(☃);
         this.field_152661_c.remove(☃x.func_152668_a().getName().toLowerCase(Locale.ROOT));
         this.field_152663_e.remove(☃);
      }

      this.field_152661_c.put(☃.getName().toLowerCase(Locale.ROOT), ☃);
      this.field_152662_d.put(☃, ☃);
      this.field_152663_e.addFirst(☃);
      this.func_152658_c();
   }

   @Nullable
   public GameProfile func_152655_a(String var1) {
      String ☃ = ☃.toLowerCase(Locale.ROOT);
      PlayerProfileCache.ProfileEntry ☃x = (PlayerProfileCache.ProfileEntry)this.field_152661_c.get(☃);
      if (☃x != null && new Date().getTime() >= ☃x.field_152673_c.getTime()) {
         this.field_152662_d.remove(☃x.func_152668_a().getId());
         this.field_152661_c.remove(☃x.func_152668_a().getName().toLowerCase(Locale.ROOT));
         this.field_152663_e.remove(☃x.func_152668_a());
         ☃x = null;
      }

      if (☃x != null) {
         GameProfile ☃ = ☃x.func_152668_a();
         this.field_152663_e.remove(☃);
         this.field_152663_e.addFirst(☃);
      } else {
         GameProfile ☃ = func_187319_a(this.field_187323_g, ☃);
         if (☃ != null) {
            this.func_152649_a(☃);
            ☃x = (PlayerProfileCache.ProfileEntry)this.field_152661_c.get(☃);
         }
      }

      this.func_152658_c();
      return ☃x == null ? null : ☃x.func_152668_a();
   }

   @Nullable
   public GameProfile func_152652_a(UUID var1) {
      PlayerProfileCache.ProfileEntry ☃ = (PlayerProfileCache.ProfileEntry)this.field_152662_d.get(☃);
      return ☃ == null ? null : ☃.func_152668_a();
   }

   private PlayerProfileCache.ProfileEntry func_152653_b(UUID var1) {
      PlayerProfileCache.ProfileEntry ☃ = (PlayerProfileCache.ProfileEntry)this.field_152662_d.get(☃);
      if (☃ != null) {
         GameProfile ☃x = ☃.func_152668_a();
         this.field_152663_e.remove(☃x);
         this.field_152663_e.addFirst(☃x);
      }

      return ☃;
   }

   public void func_152657_b() {
      BufferedReader ☃ = null;

      try {
         ☃ = Files.newReader(this.field_152665_g, StandardCharsets.UTF_8);
         List<PlayerProfileCache.ProfileEntry> ☃x = JsonUtils.func_193841_a(this.field_152660_b, ☃, field_152666_h);
         this.field_152661_c.clear();
         this.field_152662_d.clear();
         this.field_152663_e.clear();
         if (☃x != null) {
            for(PlayerProfileCache.ProfileEntry ☃xx : Lists.reverse(☃x)) {
               if (☃xx != null) {
                  this.func_152651_a(☃xx.func_152668_a(), ☃xx.func_152670_b());
               }
            }
         }
      } catch (FileNotFoundException var9) {
      } catch (JsonParseException var10) {
      } finally {
         IOUtils.closeQuietly(☃);
      }
   }

   public void func_152658_c() {
      String ☃ = this.field_152660_b.toJson(this.func_152656_a(1000));
      BufferedWriter ☃x = null;

      try {
         ☃x = Files.newWriter(this.field_152665_g, StandardCharsets.UTF_8);
         ☃x.write(☃);
         return;
      } catch (FileNotFoundException var8) {
         return;
      } catch (IOException var9) {
      } finally {
         IOUtils.closeQuietly(☃x);
      }
   }

   private List<PlayerProfileCache.ProfileEntry> func_152656_a(int var1) {
      List<PlayerProfileCache.ProfileEntry> ☃ = Lists.<PlayerProfileCache.ProfileEntry>newArrayList();

      for(GameProfile ☃x : Lists.newArrayList(Iterators.limit(this.field_152663_e.iterator(), ☃))) {
         PlayerProfileCache.ProfileEntry ☃xx = this.func_152653_b(☃x.getId());
         if (☃xx != null) {
            ☃.add(☃xx);
         }
      }

      return ☃;
   }

   class ProfileEntry {
      private final GameProfile field_152672_b;
      private final Date field_152673_c;

      private ProfileEntry(GameProfile var2, Date var3) {
         this.field_152672_b = ☃;
         this.field_152673_c = ☃;
      }

      public GameProfile func_152668_a() {
         return this.field_152672_b;
      }

      public Date func_152670_b() {
         return this.field_152673_c;
      }
   }

   class Serializer implements JsonDeserializer<PlayerProfileCache.ProfileEntry>, JsonSerializer<PlayerProfileCache.ProfileEntry> {
      private Serializer() {
      }

      public JsonElement serialize(PlayerProfileCache.ProfileEntry var1, Type var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("name", ☃.func_152668_a().getName());
         UUID ☃x = ☃.func_152668_a().getId();
         ☃.addProperty("uuid", ☃x == null ? "" : ☃x.toString());
         ☃.addProperty("expiresOn", PlayerProfileCache.field_152659_a.format(☃.func_152670_b()));
         return ☃;
      }

      public PlayerProfileCache.ProfileEntry deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (☃.isJsonObject()) {
            JsonObject ☃ = ☃.getAsJsonObject();
            JsonElement ☃x = ☃.get("name");
            JsonElement ☃xx = ☃.get("uuid");
            JsonElement ☃xxx = ☃.get("expiresOn");
            if (☃x != null && ☃xx != null) {
               String ☃xxxx = ☃xx.getAsString();
               String ☃xxxxx = ☃x.getAsString();
               Date ☃xxxxxx = null;
               if (☃xxx != null) {
                  try {
                     ☃xxxxxx = PlayerProfileCache.field_152659_a.parse(☃xxx.getAsString());
                  } catch (ParseException var14) {
                     ☃xxxxxx = null;
                  }
               }

               if (☃xxxxx != null && ☃xxxx != null) {
                  UUID ☃;
                  try {
                     ☃ = UUID.fromString(☃xxxx);
                  } catch (Throwable var13) {
                     return null;
                  }

                  return PlayerProfileCache.this.new ProfileEntry(new GameProfile(☃, ☃xxxxx), ☃xxxxxx);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }
}
