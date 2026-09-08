package net.minecraft.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.ITextComponent;

public class ServerStatusResponse {
   private ITextComponent field_151326_a;
   private ServerStatusResponse.Players field_151324_b;
   private ServerStatusResponse.Version field_151325_c;
   private String field_151323_d;

   public ITextComponent func_151317_a() {
      return this.field_151326_a;
   }

   public void func_151315_a(ITextComponent var1) {
      this.field_151326_a = ☃;
   }

   public ServerStatusResponse.Players func_151318_b() {
      return this.field_151324_b;
   }

   public void func_151319_a(ServerStatusResponse.Players var1) {
      this.field_151324_b = ☃;
   }

   public ServerStatusResponse.Version func_151322_c() {
      return this.field_151325_c;
   }

   public void func_151321_a(ServerStatusResponse.Version var1) {
      this.field_151325_c = ☃;
   }

   public void func_151320_a(String var1) {
      this.field_151323_d = ☃;
   }

   public String func_151316_d() {
      return this.field_151323_d;
   }

   public static class Players {
      private final int field_151336_a;
      private final int field_151334_b;
      private GameProfile[] field_151335_c;

      public Players(int var1, int var2) {
         this.field_151336_a = ☃;
         this.field_151334_b = ☃;
      }

      public int func_151332_a() {
         return this.field_151336_a;
      }

      public int func_151333_b() {
         return this.field_151334_b;
      }

      public GameProfile[] func_151331_c() {
         return this.field_151335_c;
      }

      public void func_151330_a(GameProfile[] var1) {
         this.field_151335_c = ☃;
      }

      public static class Serializer implements JsonDeserializer<ServerStatusResponse.Players>, JsonSerializer<ServerStatusResponse.Players> {
         public ServerStatusResponse.Players deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject ☃ = JsonUtils.func_151210_l(☃, "players");
            ServerStatusResponse.Players ☃x = new ServerStatusResponse.Players(JsonUtils.func_151203_m(☃, "max"), JsonUtils.func_151203_m(☃, "online"));
            if (JsonUtils.func_151202_d(☃, "sample")) {
               JsonArray ☃xx = JsonUtils.func_151214_t(☃, "sample");
               if (☃xx.size() > 0) {
                  GameProfile[] ☃xxx = new GameProfile[☃xx.size()];

                  for(int ☃xxxx = 0; ☃xxxx < ☃xxx.length; ++☃xxxx) {
                     JsonObject ☃xxxxx = JsonUtils.func_151210_l(☃xx.get(☃xxxx), "player[" + ☃xxxx + "]");
                     String ☃xxxxxx = JsonUtils.func_151200_h(☃xxxxx, "id");
                     ☃xxx[☃xxxx] = new GameProfile(UUID.fromString(☃xxxxxx), JsonUtils.func_151200_h(☃xxxxx, "name"));
                  }

                  ☃x.func_151330_a(☃xxx);
               }
            }

            return ☃x;
         }

         public JsonElement serialize(ServerStatusResponse.Players var1, Type var2, JsonSerializationContext var3) {
            JsonObject ☃ = new JsonObject();
            ☃.addProperty("max", ☃.func_151332_a());
            ☃.addProperty("online", ☃.func_151333_b());
            if (☃.func_151331_c() != null && ☃.func_151331_c().length > 0) {
               JsonArray ☃x = new JsonArray();

               for(int ☃xx = 0; ☃xx < ☃.func_151331_c().length; ++☃xx) {
                  JsonObject ☃xxx = new JsonObject();
                  UUID ☃xxxx = ☃.func_151331_c()[☃xx].getId();
                  ☃xxx.addProperty("id", ☃xxxx == null ? "" : ☃xxxx.toString());
                  ☃xxx.addProperty("name", ☃.func_151331_c()[☃xx].getName());
                  ☃x.add(☃xxx);
               }

               ☃.add("sample", ☃x);
            }

            return ☃;
         }
      }
   }

   public static class Serializer implements JsonDeserializer<ServerStatusResponse>, JsonSerializer<ServerStatusResponse> {
      public ServerStatusResponse deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "status");
         ServerStatusResponse ☃x = new ServerStatusResponse();
         if (☃.has("description")) {
            ☃x.func_151315_a(☃.deserialize(☃.get("description"), ITextComponent.class));
         }

         if (☃.has("players")) {
            ☃x.func_151319_a(☃.deserialize(☃.get("players"), ServerStatusResponse.Players.class));
         }

         if (☃.has("version")) {
            ☃x.func_151321_a(☃.deserialize(☃.get("version"), ServerStatusResponse.Version.class));
         }

         if (☃.has("favicon")) {
            ☃x.func_151320_a(JsonUtils.func_151200_h(☃, "favicon"));
         }

         return ☃x;
      }

      public JsonElement serialize(ServerStatusResponse var1, Type var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();
         if (☃.func_151317_a() != null) {
            ☃.add("description", ☃.serialize(☃.func_151317_a()));
         }

         if (☃.func_151318_b() != null) {
            ☃.add("players", ☃.serialize(☃.func_151318_b()));
         }

         if (☃.func_151322_c() != null) {
            ☃.add("version", ☃.serialize(☃.func_151322_c()));
         }

         if (☃.func_151316_d() != null) {
            ☃.addProperty("favicon", ☃.func_151316_d());
         }

         return ☃;
      }
   }

   public static class Version {
      private final String field_151306_a;
      private final int field_151305_b;

      public Version(String var1, int var2) {
         this.field_151306_a = ☃;
         this.field_151305_b = ☃;
      }

      public String func_151303_a() {
         return this.field_151306_a;
      }

      public int func_151304_b() {
         return this.field_151305_b;
      }

      public static class Serializer implements JsonDeserializer<ServerStatusResponse.Version>, JsonSerializer<ServerStatusResponse.Version> {
         public ServerStatusResponse.Version deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject ☃ = JsonUtils.func_151210_l(☃, "version");
            return new ServerStatusResponse.Version(JsonUtils.func_151200_h(☃, "name"), JsonUtils.func_151203_m(☃, "protocol"));
         }

         public JsonElement serialize(ServerStatusResponse.Version var1, Type var2, JsonSerializationContext var3) {
            JsonObject ☃ = new JsonObject();
            ☃.addProperty("name", ☃.func_151303_a());
            ☃.addProperty("protocol", ☃.func_151304_b());
            return ☃;
         }
      }
   }
}
