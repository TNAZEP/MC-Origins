package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.util.text.ITextComponent;

public abstract class UserListEntryBan<T> extends UserListEntry<T> {
   public static final SimpleDateFormat field_73698_a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   protected final Date field_73694_d;
   protected final String field_73695_e;
   protected final Date field_73692_f;
   protected final String field_73693_g;

   public UserListEntryBan(T var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(☃);
      this.field_73694_d = ☃ == null ? new Date() : ☃;
      this.field_73695_e = ☃ == null ? "(Unknown)" : ☃;
      this.field_73692_f = ☃;
      this.field_73693_g = ☃ == null ? "Banned by an operator." : ☃;
   }

   protected UserListEntryBan(T var1, JsonObject var2) {
      super(☃, ☃);

      Date ☃;
      try {
         ☃ = ☃.has("created") ? field_73698_a.parse(☃.get("created").getAsString()) : new Date();
      } catch (ParseException var7) {
         ☃ = new Date();
      }

      this.field_73694_d = ☃;
      this.field_73695_e = ☃.has("source") ? ☃.get("source").getAsString() : "(Unknown)";

      Date ☃;
      try {
         ☃ = ☃.has("expires") ? field_73698_a.parse(☃.get("expires").getAsString()) : null;
      } catch (ParseException var6) {
         ☃ = null;
      }

      this.field_73692_f = ☃;
      this.field_73693_g = ☃.has("reason") ? ☃.get("reason").getAsString() : "Banned by an operator.";
   }

   public String func_199040_b() {
      return this.field_73695_e;
   }

   public Date func_73680_d() {
      return this.field_73692_f;
   }

   public String func_73686_f() {
      return this.field_73693_g;
   }

   public abstract ITextComponent func_199041_e();

   @Override
   boolean func_73682_e() {
      return this.field_73692_f == null ? false : this.field_73692_f.before(new Date());
   }

   @Override
   protected void func_152641_a(JsonObject var1) {
      ☃.addProperty("created", field_73698_a.format(this.field_73694_d));
      ☃.addProperty("source", this.field_73695_e);
      ☃.addProperty("expires", this.field_73692_f == null ? "forever" : field_73698_a.format(this.field_73692_f));
      ☃.addProperty("reason", this.field_73693_g);
   }
}
