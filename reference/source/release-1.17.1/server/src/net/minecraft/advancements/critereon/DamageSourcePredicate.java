package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;

public class DamageSourcePredicate {
   public static final DamageSourcePredicate ANY = DamageSourcePredicate.Builder.damageType().build();
   private final Boolean isProjectile;
   private final Boolean isExplosion;
   private final Boolean bypassesArmor;
   private final Boolean bypassesInvulnerability;
   private final Boolean bypassesMagic;
   private final Boolean isFire;
   private final Boolean isMagic;
   private final Boolean isLightning;
   private final EntityPredicate directEntity;
   private final EntityPredicate sourceEntity;

   public DamageSourcePredicate(
      @Nullable Boolean var1,
      @Nullable Boolean var2,
      @Nullable Boolean var3,
      @Nullable Boolean var4,
      @Nullable Boolean var5,
      @Nullable Boolean var6,
      @Nullable Boolean var7,
      @Nullable Boolean var8,
      EntityPredicate var9,
      EntityPredicate var10
   ) {
      this.isProjectile = â˜ƒ;
      this.isExplosion = â˜ƒ;
      this.bypassesArmor = â˜ƒ;
      this.bypassesInvulnerability = â˜ƒ;
      this.bypassesMagic = â˜ƒ;
      this.isFire = â˜ƒ;
      this.isMagic = â˜ƒ;
      this.isLightning = â˜ƒ;
      this.directEntity = â˜ƒ;
      this.sourceEntity = â˜ƒ;
   }

   public boolean matches(ServerPlayer var1, DamageSource var2) {
      return this.matches(â˜ƒ.getLevel(), â˜ƒ.position(), â˜ƒ);
   }

   public boolean matches(ServerLevel var1, Vec3 var2, DamageSource var3) {
      if (this == ANY) {
         return true;
      } else if (this.isProjectile != null && this.isProjectile != â˜ƒ.isProjectile()) {
         return false;
      } else if (this.isExplosion != null && this.isExplosion != â˜ƒ.isExplosion()) {
         return false;
      } else if (this.bypassesArmor != null && this.bypassesArmor != â˜ƒ.isBypassArmor()) {
         return false;
      } else if (this.bypassesInvulnerability != null && this.bypassesInvulnerability != â˜ƒ.isBypassInvul()) {
         return false;
      } else if (this.bypassesMagic != null && this.bypassesMagic != â˜ƒ.isBypassMagic()) {
         return false;
      } else if (this.isFire != null && this.isFire != â˜ƒ.isFire()) {
         return false;
      } else if (this.isMagic != null && this.isMagic != â˜ƒ.isMagic()) {
         return false;
      } else if (this.isLightning != null && this.isLightning != (â˜ƒ == DamageSource.LIGHTNING_BOLT)) {
         return false;
      } else if (!this.directEntity.matches(â˜ƒ, â˜ƒ, â˜ƒ.getDirectEntity())) {
         return false;
      } else {
         return this.sourceEntity.matches(â˜ƒ, â˜ƒ, â˜ƒ.getEntity());
      }
   }

   public static DamageSourcePredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "damage type");
         Boolean â˜ƒx = getOptionalBoolean(â˜ƒ, "is_projectile");
         Boolean â˜ƒxx = getOptionalBoolean(â˜ƒ, "is_explosion");
         Boolean â˜ƒxxx = getOptionalBoolean(â˜ƒ, "bypasses_armor");
         Boolean â˜ƒxxxx = getOptionalBoolean(â˜ƒ, "bypasses_invulnerability");
         Boolean â˜ƒxxxxx = getOptionalBoolean(â˜ƒ, "bypasses_magic");
         Boolean â˜ƒxxxxxx = getOptionalBoolean(â˜ƒ, "is_fire");
         Boolean â˜ƒxxxxxxx = getOptionalBoolean(â˜ƒ, "is_magic");
         Boolean â˜ƒxxxxxxxx = getOptionalBoolean(â˜ƒ, "is_lightning");
         EntityPredicate â˜ƒxxxxxxxxx = EntityPredicate.fromJson(â˜ƒ.get("direct_entity"));
         EntityPredicate â˜ƒxxxxxxxxxx = EntityPredicate.fromJson(â˜ƒ.get("source_entity"));
         return new DamageSourcePredicate(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
      } else {
         return ANY;
      }
   }

   @Nullable
   private static Boolean getOptionalBoolean(JsonObject var0, String var1) {
      return â˜ƒ.has(â˜ƒ) ? GsonHelper.getAsBoolean(â˜ƒ, â˜ƒ) : null;
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         this.addOptionally(â˜ƒ, "is_projectile", this.isProjectile);
         this.addOptionally(â˜ƒ, "is_explosion", this.isExplosion);
         this.addOptionally(â˜ƒ, "bypasses_armor", this.bypassesArmor);
         this.addOptionally(â˜ƒ, "bypasses_invulnerability", this.bypassesInvulnerability);
         this.addOptionally(â˜ƒ, "bypasses_magic", this.bypassesMagic);
         this.addOptionally(â˜ƒ, "is_fire", this.isFire);
         this.addOptionally(â˜ƒ, "is_magic", this.isMagic);
         this.addOptionally(â˜ƒ, "is_lightning", this.isLightning);
         â˜ƒ.add("direct_entity", this.directEntity.serializeToJson());
         â˜ƒ.add("source_entity", this.sourceEntity.serializeToJson());
         return â˜ƒ;
      }
   }

   private void addOptionally(JsonObject var1, String var2, @Nullable Boolean var3) {
      if (â˜ƒ != null) {
         â˜ƒ.addProperty(â˜ƒ, â˜ƒ);
      }
   }

   public static class Builder {
      private Boolean isProjectile;
      private Boolean isExplosion;
      private Boolean bypassesArmor;
      private Boolean bypassesInvulnerability;
      private Boolean bypassesMagic;
      private Boolean isFire;
      private Boolean isMagic;
      private Boolean isLightning;
      private EntityPredicate directEntity = EntityPredicate.ANY;
      private EntityPredicate sourceEntity = EntityPredicate.ANY;

      public static DamageSourcePredicate.Builder damageType() {
         return new DamageSourcePredicate.Builder();
      }

      public DamageSourcePredicate.Builder isProjectile(Boolean var1) {
         this.isProjectile = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder isExplosion(Boolean var1) {
         this.isExplosion = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder bypassesArmor(Boolean var1) {
         this.bypassesArmor = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder bypassesInvulnerability(Boolean var1) {
         this.bypassesInvulnerability = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder bypassesMagic(Boolean var1) {
         this.bypassesMagic = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder isFire(Boolean var1) {
         this.isFire = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder isMagic(Boolean var1) {
         this.isMagic = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder isLightning(Boolean var1) {
         this.isLightning = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder direct(EntityPredicate var1) {
         this.directEntity = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder direct(EntityPredicate.Builder var1) {
         this.directEntity = â˜ƒ.build();
         return this;
      }

      public DamageSourcePredicate.Builder source(EntityPredicate var1) {
         this.sourceEntity = â˜ƒ;
         return this;
      }

      public DamageSourcePredicate.Builder source(EntityPredicate.Builder var1) {
         this.sourceEntity = â˜ƒ.build();
         return this;
      }

      public DamageSourcePredicate build() {
         return new DamageSourcePredicate(
            this.isProjectile,
            this.isExplosion,
            this.bypassesArmor,
            this.bypassesInvulnerability,
            this.bypassesMagic,
            this.isFire,
            this.isMagic,
            this.isLightning,
            this.directEntity,
            this.sourceEntity
         );
      }
   }
}
