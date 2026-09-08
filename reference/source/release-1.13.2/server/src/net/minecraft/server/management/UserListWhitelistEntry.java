package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserListWhitelistEntry extends UserListEntry<GameProfile> {
   public UserListWhitelistEntry(GameProfile var1) {
      super(☃);
   }

   public UserListWhitelistEntry(JsonObject var1) {
      super(func_152646_b(☃), ☃);
   }

   @Override
   protected void func_152641_a(JsonObject var1) {
      if (this.func_152640_f() != null) {
         ☃.addProperty("uuid", this.func_152640_f().getId() == null ? "" : this.func_152640_f().getId().toString());
         ☃.addProperty("name", this.func_152640_f().getName());
         super.func_152641_a(☃);
      }
   }

   private static GameProfile func_152646_b(JsonObject var0) {
      if (☃.has("uuid") && ☃.has("name")) {
         String ☃ = ☃.get("uuid").getAsString();

         UUID ☃;
         try {
            ☃ = UUID.fromString(☃);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(☃, ☃.get("name").getAsString());
      } else {
         return null;
      }
   }
}
