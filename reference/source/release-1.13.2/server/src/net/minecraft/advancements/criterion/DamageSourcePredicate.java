package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JsonUtils;

public class DamageSourcePredicate {
   public static final DamageSourcePredicate field_192449_a = DamageSourcePredicate.Builder.func_203981_a().func_203979_b();
   private final Boolean field_192450_b;
   private final Boolean field_192451_c;
   private final Boolean field_192452_d;
   private final Boolean field_192453_e;
   private final Boolean field_192454_f;
   private final Boolean field_192455_g;
   private final Boolean field_192456_h;
   private final EntityPredicate field_193419_i;
   private final EntityPredicate field_193420_j;

   public DamageSourcePredicate(
      @Nullable Boolean var1,
      @Nullable Boolean var2,
      @Nullable Boolean var3,
      @Nullable Boolean var4,
      @Nullable Boolean var5,
      @Nullable Boolean var6,
      @Nullable Boolean var7,
      EntityPredicate var8,
      EntityPredicate var9
   ) {
      this.field_192450_b = ☃;
      this.field_192451_c = ☃;
      this.field_192452_d = ☃;
      this.field_192453_e = ☃;
      this.field_192454_f = ☃;
      this.field_192455_g = ☃;
      this.field_192456_h = ☃;
      this.field_193419_i = ☃;
      this.field_193420_j = ☃;
   }

   public boolean func_193418_a(EntityPlayerMP var1, DamageSource var2) {
      if (this == field_192449_a) {
         return true;
      } else if (this.field_192450_b != null && this.field_192450_b != ☃.func_76352_a()) {
         return false;
      } else if (this.field_192451_c != null && this.field_192451_c != ☃.func_94541_c()) {
         return false;
      } else if (this.field_192452_d != null && this.field_192452_d != ☃.func_76363_c()) {
         return false;
      } else if (this.field_192453_e != null && this.field_192453_e != ☃.func_76357_e()) {
         return false;
      } else if (this.field_192454_f != null && this.field_192454_f != ☃.func_151517_h()) {
         return false;
      } else if (this.field_192455_g != null && this.field_192455_g != ☃.func_76347_k()) {
         return false;
      } else if (this.field_192456_h != null && this.field_192456_h != ☃.func_82725_o()) {
         return false;
      } else if (!this.field_193419_i.func_192482_a(☃, ☃.func_76364_f())) {
         return false;
      } else {
         return this.field_193420_j.func_192482_a(☃, ☃.func_76346_g());
      }
   }

   public static DamageSourcePredicate func_192447_a(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "damage type");
         Boolean ☃x = func_192448_a(☃, "is_projectile");
         Boolean ☃xx = func_192448_a(☃, "is_explosion");
         Boolean ☃xxx = func_192448_a(☃, "bypasses_armor");
         Boolean ☃xxxx = func_192448_a(☃, "bypasses_invulnerability");
         Boolean ☃xxxxx = func_192448_a(☃, "bypasses_magic");
         Boolean ☃xxxxxx = func_192448_a(☃, "is_fire");
         Boolean ☃xxxxxxx = func_192448_a(☃, "is_magic");
         EntityPredicate ☃xxxxxxxx = EntityPredicate.func_192481_a(☃.get("direct_entity"));
         EntityPredicate ☃xxxxxxxxx = EntityPredicate.func_192481_a(☃.get("source_entity"));
         return new DamageSourcePredicate(☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx);
      } else {
         return field_192449_a;
      }
   }

   @Nullable
   private static Boolean func_192448_a(JsonObject var0, String var1) {
      return ☃.has(☃) ? JsonUtils.func_151212_i(☃, ☃) : null;
   }

   public JsonElement func_203991_a() {
      if (this == field_192449_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject ☃ = new JsonObject();
         this.func_203992_a(☃, "is_projectile", this.field_192450_b);
         this.func_203992_a(☃, "is_explosion", this.field_192451_c);
         this.func_203992_a(☃, "bypasses_armor", this.field_192452_d);
         this.func_203992_a(☃, "bypasses_invulnerability", this.field_192453_e);
         this.func_203992_a(☃, "bypasses_magic", this.field_192454_f);
         this.func_203992_a(☃, "is_fire", this.field_192455_g);
         this.func_203992_a(☃, "is_magic", this.field_192456_h);
         ☃.add("direct_entity", this.field_193419_i.func_204006_a());
         ☃.add("source_entity", this.field_193420_j.func_204006_a());
         return ☃;
      }
   }

   private void func_203992_a(JsonObject var1, String var2, @Nullable Boolean var3) {
      if (☃ != null) {
         ☃.addProperty(☃, ☃);
      }
   }

   public static class Builder {
      private Boolean field_203982_a;
      private Boolean field_203983_b;
      private Boolean field_203984_c;
      private Boolean field_203985_d;
      private Boolean field_203986_e;
      private Boolean field_203987_f;
      private Boolean field_203988_g;
      private EntityPredicate field_203989_h = EntityPredicate.field_192483_a;
      private EntityPredicate field_203990_i = EntityPredicate.field_192483_a;

      public static DamageSourcePredicate.Builder func_203981_a() {
         return new DamageSourcePredicate.Builder();
      }

      public DamageSourcePredicate.Builder func_203978_a(Boolean var1) {
         this.field_203982_a = ☃;
         return this;
      }

      public DamageSourcePredicate.Builder func_203980_a(EntityPredicate.Builder var1) {
         this.field_203989_h = ☃.func_204000_b();
         return this;
      }

      public DamageSourcePredicate func_203979_b() {
         return new DamageSourcePredicate(
            this.field_203982_a,
            this.field_203983_b,
            this.field_203984_c,
            this.field_203985_d,
            this.field_203986_e,
            this.field_203987_f,
            this.field_203988_g,
            this.field_203989_h,
            this.field_203990_i
         );
      }
   }
}
