package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectsPredicate {
   public static final MobEffectsPredicate ANY = new MobEffectsPredicate(Collections.emptyMap());
   private final Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> effects;

   public MobEffectsPredicate(Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> var1) {
      this.effects = â˜ƒ;
   }

   public static MobEffectsPredicate effects() {
      return new MobEffectsPredicate(Maps.<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate>newLinkedHashMap());
   }

   public MobEffectsPredicate and(MobEffect var1) {
      this.effects.put(â˜ƒ, new MobEffectsPredicate.MobEffectInstancePredicate());
      return this;
   }

   public MobEffectsPredicate and(MobEffect var1, MobEffectsPredicate.MobEffectInstancePredicate var2) {
      this.effects.put(â˜ƒ, â˜ƒ);
      return this;
   }

   public boolean matches(Entity var1) {
      if (this == ANY) {
         return true;
      } else {
         return â˜ƒ instanceof LivingEntity ? this.matches(((LivingEntity)â˜ƒ).getActiveEffectsMap()) : false;
      }
   }

   public boolean matches(LivingEntity var1) {
      return this == ANY ? true : this.matches(â˜ƒ.getActiveEffectsMap());
   }

   public boolean matches(Map<MobEffect, MobEffectInstance> var1) {
      if (this == ANY) {
         return true;
      } else {
         for(Entry<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> â˜ƒ : this.effects.entrySet()) {
            MobEffectInstance â˜ƒx = (MobEffectInstance)â˜ƒ.get(â˜ƒ.getKey());
            if (!((MobEffectsPredicate.MobEffectInstancePredicate)â˜ƒ.getValue()).matches(â˜ƒx)) {
               return false;
            }
         }

         return true;
      }
   }

   public static MobEffectsPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "effects");
         Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> â˜ƒx = Maps.<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate>newLinkedHashMap(
            
         );

         for(Entry<String, JsonElement> â˜ƒxx : â˜ƒ.entrySet()) {
            ResourceLocation â˜ƒxxx = new ResourceLocation((String)â˜ƒxx.getKey());
            MobEffect â˜ƒxxxx = (MobEffect)Registry.MOB_EFFECT.getOptional(â˜ƒxxx).orElseThrow(() -> new JsonSyntaxException("Unknown effect '" + â˜ƒ + "'"));
            MobEffectsPredicate.MobEffectInstancePredicate â˜ƒxxxxx = MobEffectsPredicate.MobEffectInstancePredicate.fromJson(
               GsonHelper.convertToJsonObject((JsonElement)â˜ƒxx.getValue(), (String)â˜ƒxx.getKey())
            );
            â˜ƒx.put(â˜ƒxxxx, â˜ƒxxxxx);
         }

         return new MobEffectsPredicate(â˜ƒx);
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();

         for(Entry<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> â˜ƒx : this.effects.entrySet()) {
            â˜ƒ.add(
               Registry.MOB_EFFECT.getKey((MobEffect)â˜ƒx.getKey()).toString(),
               ((MobEffectsPredicate.MobEffectInstancePredicate)â˜ƒx.getValue()).serializeToJson()
            );
         }

         return â˜ƒ;
      }
   }

   public static class MobEffectInstancePredicate {
      private final MinMaxBounds.Ints amplifier;
      private final MinMaxBounds.Ints duration;
      @Nullable
      private final Boolean ambient;
      @Nullable
      private final Boolean visible;

      public MobEffectInstancePredicate(MinMaxBounds.Ints var1, MinMaxBounds.Ints var2, @Nullable Boolean var3, @Nullable Boolean var4) {
         this.amplifier = â˜ƒ;
         this.duration = â˜ƒ;
         this.ambient = â˜ƒ;
         this.visible = â˜ƒ;
      }

      public MobEffectInstancePredicate() {
         this(MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, null, null);
      }

      public boolean matches(@Nullable MobEffectInstance var1) {
         if (â˜ƒ == null) {
            return false;
         } else if (!this.amplifier.matches(â˜ƒ.getAmplifier())) {
            return false;
         } else if (!this.duration.matches(â˜ƒ.getDuration())) {
            return false;
         } else if (this.ambient != null && this.ambient != â˜ƒ.isAmbient()) {
            return false;
         } else {
            return this.visible == null || this.visible == â˜ƒ.isVisible();
         }
      }

      public JsonElement serializeToJson() {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("amplifier", this.amplifier.serializeToJson());
         â˜ƒ.add("duration", this.duration.serializeToJson());
         â˜ƒ.addProperty("ambient", this.ambient);
         â˜ƒ.addProperty("visible", this.visible);
         return â˜ƒ;
      }

      public static MobEffectsPredicate.MobEffectInstancePredicate fromJson(JsonObject var0) {
         MinMaxBounds.Ints â˜ƒ = MinMaxBounds.Ints.fromJson(â˜ƒ.get("amplifier"));
         MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("duration"));
         Boolean â˜ƒxx = â˜ƒ.has("ambient") ? GsonHelper.getAsBoolean(â˜ƒ, "ambient") : null;
         Boolean â˜ƒxxx = â˜ƒ.has("visible") ? GsonHelper.getAsBoolean(â˜ƒ, "visible") : null;
         return new MobEffectsPredicate.MobEffectInstancePredicate(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }
}
