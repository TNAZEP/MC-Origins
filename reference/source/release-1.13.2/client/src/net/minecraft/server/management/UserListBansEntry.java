package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class UserListBansEntry extends UserListEntryBan<GameProfile> {
   public UserListBansEntry(GameProfile var1) {
      this(☃, null, null, null, null);
   }

   public UserListBansEntry(GameProfile var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(☃, ☃, ☃, ☃, ☃);
   }

   public UserListBansEntry(JsonObject var1) {
      super(func_152648_b(☃), ☃);
   }

   @Override
   protected void func_152641_a(JsonObject var1) {
      if (this.func_152640_f() != null) {
         ☃.addProperty("uuid", this.func_152640_f().getId() == null ? "" : this.func_152640_f().getId().toString());
         ☃.addProperty("name", this.func_152640_f().getName());
         super.func_152641_a(☃);
      }
   }

   @Override
   public ITextComponent func_199041_e() {
      GameProfile ☃ = this.func_152640_f();
      return new TextComponentString(☃.getName() != null ? ☃.getName() : Objects.toString(☃.getId(), "(Unknown)"));
   }

   private static GameProfile func_152648_b(JsonObject var0) {
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
