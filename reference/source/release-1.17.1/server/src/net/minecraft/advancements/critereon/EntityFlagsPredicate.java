package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityFlagsPredicate {
   public static final EntityFlagsPredicate ANY = new EntityFlagsPredicate.Builder().build();
   @Nullable
   private final Boolean isOnFire;
   @Nullable
   private final Boolean isCrouching;
   @Nullable
   private final Boolean isSprinting;
   @Nullable
   private final Boolean isSwimming;
   @Nullable
   private final Boolean isBaby;

   public EntityFlagsPredicate(@Nullable Boolean var1, @Nullable Boolean var2, @Nullable Boolean var3, @Nullable Boolean var4, @Nullable Boolean var5) {
      this.isOnFire = â˜ƒ;
      this.isCrouching = â˜ƒ;
      this.isSprinting = â˜ƒ;
      this.isSwimming = â˜ƒ;
      this.isBaby = â˜ƒ;
   }

   public boolean matches(Entity var1) {
      if (this.isOnFire != null && â˜ƒ.isOnFire() != this.isOnFire) {
         return false;
      } else if (this.isCrouching != null && â˜ƒ.isCrouching() != this.isCrouching) {
         return false;
      } else if (this.isSprinting != null && â˜ƒ.isSprinting() != this.isSprinting) {
         return false;
      } else if (this.isSwimming != null && â˜ƒ.isSwimming() != this.isSwimming) {
         return false;
      } else {
         return this.isBaby == null || !(â˜ƒ instanceof LivingEntity) || ((LivingEntity)â˜ƒ).isBaby() == this.isBaby;
      }
   }

   @Nullable
   private static Boolean getOptionalBoolean(JsonObject var0, String var1) {
      return â˜ƒ.has(â˜ƒ) ? GsonHelper.getAsBoolean(â˜ƒ, â˜ƒ) : null;
   }

   public static EntityFlagsPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "entity flags");
         Boolean â˜ƒx = getOptionalBoolean(â˜ƒ, "is_on_fire");
         Boolean â˜ƒxx = getOptionalBoolean(â˜ƒ, "is_sneaking");
         Boolean â˜ƒxxx = getOptionalBoolean(â˜ƒ, "is_sprinting");
         Boolean â˜ƒxxxx = getOptionalBoolean(â˜ƒ, "is_swimming");
         Boolean â˜ƒxxxxx = getOptionalBoolean(â˜ƒ, "is_baby");
         return new EntityFlagsPredicate(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
      } else {
         return ANY;
      }
   }

   private void addOptionalBoolean(JsonObject var1, String var2, @Nullable Boolean var3) {
      if (â˜ƒ != null) {
         â˜ƒ.addProperty(â˜ƒ, â˜ƒ);
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         this.addOptionalBoolean(â˜ƒ, "is_on_fire", this.isOnFire);
         this.addOptionalBoolean(â˜ƒ, "is_sneaking", this.isCrouching);
         this.addOptionalBoolean(â˜ƒ, "is_sprinting", this.isSprinting);
         this.addOptionalBoolean(â˜ƒ, "is_swimming", this.isSwimming);
         this.addOptionalBoolean(â˜ƒ, "is_baby", this.isBaby);
         return â˜ƒ;
      }
   }

   public static class Builder {
      @Nullable
      private Boolean isOnFire;
      @Nullable
      private Boolean isCrouching;
      @Nullable
      private Boolean isSprinting;
      @Nullable
      private Boolean isSwimming;
      @Nullable
      private Boolean isBaby;

      public static EntityFlagsPredicate.Builder flags() {
         return new EntityFlagsPredicate.Builder();
      }

      public EntityFlagsPredicate.Builder setOnFire(@Nullable Boolean var1) {
         this.isOnFire = â˜ƒ;
         return this;
      }

      public EntityFlagsPredicate.Builder setCrouching(@Nullable Boolean var1) {
         this.isCrouching = â˜ƒ;
         return this;
      }

      public EntityFlagsPredicate.Builder setSprinting(@Nullable Boolean var1) {
         this.isSprinting = â˜ƒ;
         return this;
      }

      public EntityFlagsPredicate.Builder setSwimming(@Nullable Boolean var1) {
         this.isSwimming = â˜ƒ;
         return this;
      }

      public EntityFlagsPredicate.Builder setIsBaby(@Nullable Boolean var1) {
         this.isBaby = â˜ƒ;
         return this;
      }

      public EntityFlagsPredicate build() {
         return new EntityFlagsPredicate(this.isOnFire, this.isCrouching, this.isSprinting, this.isSwimming, this.isBaby);
      }
   }
}
