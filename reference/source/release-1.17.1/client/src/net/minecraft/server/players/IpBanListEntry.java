package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class IpBanListEntry extends BanListEntry<String> {
   public IpBanListEntry(String var1) {
      this(â˜ƒ, null, null, null, null);
   }

   public IpBanListEntry(String var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public Component getDisplayName() {
      return new TextComponent(this.getUser());
   }

   public IpBanListEntry(JsonObject var1) {
      super(createIpInfo(â˜ƒ), â˜ƒ);
   }

   private static String createIpInfo(JsonObject var0) {
      return â˜ƒ.has("ip") ? â˜ƒ.get("ip").getAsString() : null;
   }

   @Override
   protected void serialize(JsonObject var1) {
      if (this.getUser() != null) {
         â˜ƒ.addProperty("ip", this.getUser());
         super.serialize(â˜ƒ);
      }
   }
}
