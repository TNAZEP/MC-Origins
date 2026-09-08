package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class UserListIPBansEntry extends UserListEntryBan<String> {
   public UserListIPBansEntry(String var1) {
      this(☃, null, null, null, null);
   }

   public UserListIPBansEntry(String var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public ITextComponent func_199041_e() {
      return new TextComponentString(this.func_152640_f());
   }

   public UserListIPBansEntry(JsonObject var1) {
      super(func_152647_b(☃), ☃);
   }

   private static String func_152647_b(JsonObject var0) {
      return ☃.has("ip") ? ☃.get("ip").getAsString() : null;
   }

   @Override
   protected void func_152641_a(JsonObject var1) {
      if (this.func_152640_f() != null) {
         ☃.addProperty("ip", this.func_152640_f());
         super.func_152641_a(☃);
      }
   }
}
