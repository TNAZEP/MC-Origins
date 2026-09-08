package net.minecraft.util;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class Session {
   private final String field_74286_b;
   private final String field_148257_b;
   private final String field_148258_c;
   private final Session.Type field_152429_d;

   public Session(String var1, String var2, String var3, String var4) {
      this.field_74286_b = ☃;
      this.field_148257_b = ☃;
      this.field_148258_c = ☃;
      this.field_152429_d = Session.Type.func_152421_a(☃);
   }

   public String func_111286_b() {
      return "token:" + this.field_148258_c + ":" + this.field_148257_b;
   }

   public String func_148255_b() {
      return this.field_148257_b;
   }

   public String func_111285_a() {
      return this.field_74286_b;
   }

   public String func_148254_d() {
      return this.field_148258_c;
   }

   public GameProfile func_148256_e() {
      try {
         UUID ☃ = UUIDTypeAdapter.fromString(this.func_148255_b());
         return new GameProfile(☃, this.func_111285_a());
      } catch (IllegalArgumentException var2) {
         return new GameProfile(null, this.func_111285_a());
      }
   }

   public static enum Type {
      LEGACY("legacy"),
      MOJANG("mojang");

      private static final Map<String, Session.Type> field_152425_c = (Map<String, Session.Type>)Arrays.stream(values())
         .collect(Collectors.toMap(var0 -> var0.field_152426_d, Function.identity()));
      private final String field_152426_d;

      private Type(String var3) {
         this.field_152426_d = ☃;
      }

      @Nullable
      public static Session.Type func_152421_a(String var0) {
         return (Session.Type)field_152425_c.get(☃.toLowerCase(Locale.ROOT));
      }
   }
}
