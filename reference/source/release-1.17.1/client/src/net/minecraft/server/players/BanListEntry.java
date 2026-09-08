package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public abstract class BanListEntry<T> extends StoredUserEntry<T> {
   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   public static final String EXPIRES_NEVER = "forever";
   protected final Date created;
   protected final String source;
   protected final Date expires;
   protected final String reason;

   public BanListEntry(T var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(â˜ƒ);
      this.created = â˜ƒ == null ? new Date() : â˜ƒ;
      this.source = â˜ƒ == null ? "(Unknown)" : â˜ƒ;
      this.expires = â˜ƒ;
      this.reason = â˜ƒ == null ? "Banned by an operator." : â˜ƒ;
   }

   protected BanListEntry(T var1, JsonObject var2) {
      super(â˜ƒ);

      Date â˜ƒ;
      try {
         â˜ƒ = â˜ƒ.has("created") ? DATE_FORMAT.parse(â˜ƒ.get("created").getAsString()) : new Date();
      } catch (ParseException var7) {
         â˜ƒ = new Date();
      }

      this.created = â˜ƒ;
      this.source = â˜ƒ.has("source") ? â˜ƒ.get("source").getAsString() : "(Unknown)";

      Date â˜ƒ;
      try {
         â˜ƒ = â˜ƒ.has("expires") ? DATE_FORMAT.parse(â˜ƒ.get("expires").getAsString()) : null;
      } catch (ParseException var6) {
         â˜ƒ = null;
      }

      this.expires = â˜ƒ;
      this.reason = â˜ƒ.has("reason") ? â˜ƒ.get("reason").getAsString() : "Banned by an operator.";
   }

   public Date getCreated() {
      return this.created;
   }

   public String getSource() {
      return this.source;
   }

   public Date getExpires() {
      return this.expires;
   }

   public String getReason() {
      return this.reason;
   }

   public abstract Component getDisplayName();

   @Override
   boolean hasExpired() {
      return this.expires == null ? false : this.expires.before(new Date());
   }

   @Override
   protected void serialize(JsonObject var1) {
      â˜ƒ.addProperty("created", DATE_FORMAT.format(this.created));
      â˜ƒ.addProperty("source", this.source);
      â˜ƒ.addProperty("expires", this.expires == null ? "forever" : DATE_FORMAT.format(this.expires));
      â˜ƒ.addProperty("reason", this.reason);
   }
}
