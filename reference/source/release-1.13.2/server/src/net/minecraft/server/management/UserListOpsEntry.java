package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserListOpsEntry extends UserListEntry<GameProfile> {
   private final int field_152645_a;
   private final boolean field_183025_b;

   public UserListOpsEntry(GameProfile var1, int var2, boolean var3) {
      super(☃);
      this.field_152645_a = ☃;
      this.field_183025_b = ☃;
   }

   public UserListOpsEntry(JsonObject var1) {
      super(func_152643_b(☃), ☃);
      this.field_152645_a = ☃.has("level") ? ☃.get("level").getAsInt() : 0;
      this.field_183025_b = ☃.has("bypassesPlayerLimit") && ☃.get("bypassesPlayerLimit").getAsBoolean();
   }

   public int func_152644_a() {
      return this.field_152645_a;
   }

   public boolean func_183024_b() {
      return this.field_183025_b;
   }

   @Override
   protected void func_152641_a(JsonObject var1) {
      if (this.func_152640_f() != null) {
         ☃.addProperty("uuid", this.func_152640_f().getId() == null ? "" : this.func_152640_f().getId().toString());
         ☃.addProperty("name", this.func_152640_f().getName());
         super.func_152641_a(☃);
         ☃.addProperty("level", this.field_152645_a);
         ☃.addProperty("bypassesPlayerLimit", this.field_183025_b);
      }
   }

   private static GameProfile func_152643_b(JsonObject var0) {
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
