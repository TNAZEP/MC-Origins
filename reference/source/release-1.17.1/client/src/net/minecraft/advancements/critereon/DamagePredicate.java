package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;

public class DamagePredicate {
   public static final DamagePredicate ANY = DamagePredicate.Builder.damageInstance().build();
   private final MinMaxBounds.Doubles dealtDamage;
   private final MinMaxBounds.Doubles takenDamage;
   private final EntityPredicate sourceEntity;
   private final Boolean blocked;
   private final DamageSourcePredicate type;

   public DamagePredicate() {
      this.dealtDamage = MinMaxBounds.Doubles.ANY;
      this.takenDamage = MinMaxBounds.Doubles.ANY;
      this.sourceEntity = EntityPredicate.ANY;
      this.blocked = null;
      this.type = DamageSourcePredicate.ANY;
   }

   public DamagePredicate(MinMaxBounds.Doubles var1, MinMaxBounds.Doubles var2, EntityPredicate var3, @Nullable Boolean var4, DamageSourcePredicate var5) {
      this.dealtDamage = â˜ƒ;
      this.takenDamage = â˜ƒ;
      this.sourceEntity = â˜ƒ;
      this.blocked = â˜ƒ;
      this.type = â˜ƒ;
   }

   public boolean matches(ServerPlayer var1, DamageSource var2, float var3, float var4, boolean var5) {
      if (this == ANY) {
         return true;
      } else if (!this.dealtDamage.matches((double)â˜ƒ)) {
         return false;
      } else if (!this.takenDamage.matches((double)â˜ƒ)) {
         return false;
      } else if (!this.sourceEntity.matches(â˜ƒ, â˜ƒ.getEntity())) {
         return false;
      } else if (this.blocked != null && this.blocked != â˜ƒ) {
         return false;
      } else {
         return this.type.matches(â˜ƒ, â˜ƒ);
      }
   }

   public static DamagePredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "damage");
         MinMaxBounds.Doubles â˜ƒx = MinMaxBounds.Doubles.fromJson(â˜ƒ.get("dealt"));
         MinMaxBounds.Doubles â˜ƒxx = MinMaxBounds.Doubles.fromJson(â˜ƒ.get("taken"));
         Boolean â˜ƒxxx = â˜ƒ.has("blocked") ? GsonHelper.getAsBoolean(â˜ƒ, "blocked") : null;
         EntityPredicate â˜ƒxxxx = EntityPredicate.fromJson(â˜ƒ.get("source_entity"));
         DamageSourcePredicate â˜ƒxxxxx = DamageSourcePredicate.fromJson(â˜ƒ.get("type"));
         return new DamagePredicate(â˜ƒx, â˜ƒxx, â˜ƒxxxx, â˜ƒxxx, â˜ƒxxxxx);
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("dealt", this.dealtDamage.serializeToJson());
         â˜ƒ.add("taken", this.takenDamage.serializeToJson());
         â˜ƒ.add("source_entity", this.sourceEntity.serializeToJson());
         â˜ƒ.add("type", this.type.serializeToJson());
         if (this.blocked != null) {
            â˜ƒ.addProperty("blocked", this.blocked);
         }

         return â˜ƒ;
      }
   }

   public static class Builder {
      private MinMaxBounds.Doubles dealtDamage = MinMaxBounds.Doubles.ANY;
      private MinMaxBounds.Doubles takenDamage = MinMaxBounds.Doubles.ANY;
      private EntityPredicate sourceEntity = EntityPredicate.ANY;
      private Boolean blocked;
      private DamageSourcePredicate type = DamageSourcePredicate.ANY;

      public static DamagePredicate.Builder damageInstance() {
         return new DamagePredicate.Builder();
      }

      public DamagePredicate.Builder dealtDamage(MinMaxBounds.Doubles var1) {
         this.dealtDamage = â˜ƒ;
         return this;
      }

      public DamagePredicate.Builder takenDamage(MinMaxBounds.Doubles var1) {
         this.takenDamage = â˜ƒ;
         return this;
      }

      public DamagePredicate.Builder sourceEntity(EntityPredicate var1) {
         this.sourceEntity = â˜ƒ;
         return this;
      }

      public DamagePredicate.Builder blocked(Boolean var1) {
         this.blocked = â˜ƒ;
         return this;
      }

      public DamagePredicate.Builder type(DamageSourcePredicate var1) {
         this.type = â˜ƒ;
         return this;
      }

      public DamagePredicate.Builder type(DamageSourcePredicate.Builder var1) {
         this.type = â˜ƒ.build();
         return this;
      }

      public DamagePredicate build() {
         return new DamagePredicate(this.dealtDamage, this.takenDamage, this.sourceEntity, this.blocked, this.type);
      }
   }
}
