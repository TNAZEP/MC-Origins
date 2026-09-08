package net.minecraft.advancements.criterion;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class MobEffectsPredicate {
   public static final MobEffectsPredicate field_193473_a = new MobEffectsPredicate(Collections.emptyMap());
   private final Map<Potion, MobEffectsPredicate.InstancePredicate> field_193474_b;

   public MobEffectsPredicate(Map<Potion, MobEffectsPredicate.InstancePredicate> var1) {
      this.field_193474_b = ☃;
   }

   public static MobEffectsPredicate func_204014_a() {
      return new MobEffectsPredicate(Maps.<Potion, MobEffectsPredicate.InstancePredicate>newHashMap());
   }

   public MobEffectsPredicate func_204015_a(Potion var1) {
      this.field_193474_b.put(☃, new MobEffectsPredicate.InstancePredicate());
      return this;
   }

   public boolean func_193469_a(Entity var1) {
      if (this == field_193473_a) {
         return true;
      } else {
         return ☃ instanceof EntityLivingBase ? this.func_193470_a(((EntityLivingBase)☃).func_193076_bZ()) : false;
      }
   }

   public boolean func_193472_a(EntityLivingBase var1) {
      return this == field_193473_a ? true : this.func_193470_a(☃.func_193076_bZ());
   }

   public boolean func_193470_a(Map<Potion, PotionEffect> var1) {
      if (this == field_193473_a) {
         return true;
      } else {
         for(Entry<Potion, MobEffectsPredicate.InstancePredicate> ☃ : this.field_193474_b.entrySet()) {
            PotionEffect ☃x = (PotionEffect)☃.get(☃.getKey());
            if (!((MobEffectsPredicate.InstancePredicate)☃.getValue()).func_193463_a(☃x)) {
               return false;
            }
         }

         return true;
      }
   }

   public static MobEffectsPredicate func_193471_a(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "effects");
         Map<Potion, MobEffectsPredicate.InstancePredicate> ☃x = Maps.<Potion, MobEffectsPredicate.InstancePredicate>newHashMap();

         for(Entry<String, JsonElement> ☃xx : ☃.entrySet()) {
            ResourceLocation ☃xxx = new ResourceLocation((String)☃xx.getKey());
            Potion ☃xxxx = IRegistry.field_212631_t.func_212608_b(☃xxx);
            if (☃xxxx == null) {
               throw new JsonSyntaxException("Unknown effect '" + ☃xxx + "'");
            }

            MobEffectsPredicate.InstancePredicate ☃xxx = MobEffectsPredicate.InstancePredicate.func_193464_a(
               JsonUtils.func_151210_l((JsonElement)☃xx.getValue(), (String)☃xx.getKey())
            );
            ☃x.put(☃xxxx, ☃xxx);
         }

         return new MobEffectsPredicate(☃x);
      } else {
         return field_193473_a;
      }
   }

   public JsonElement func_204013_b() {
      if (this == field_193473_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject ☃ = new JsonObject();

         for(Entry<Potion, MobEffectsPredicate.InstancePredicate> ☃x : this.field_193474_b.entrySet()) {
            ☃.add(
               IRegistry.field_212631_t.func_177774_c((Potion)☃x.getKey()).toString(), ((MobEffectsPredicate.InstancePredicate)☃x.getValue()).func_204012_a()
            );
         }

         return ☃;
      }
   }

   public static class InstancePredicate {
      private final MinMaxBounds.IntBound field_193465_a;
      private final MinMaxBounds.IntBound field_193466_b;
      @Nullable
      private final Boolean field_193467_c;
      @Nullable
      private final Boolean field_193468_d;

      public InstancePredicate(MinMaxBounds.IntBound var1, MinMaxBounds.IntBound var2, @Nullable Boolean var3, @Nullable Boolean var4) {
         this.field_193465_a = ☃;
         this.field_193466_b = ☃;
         this.field_193467_c = ☃;
         this.field_193468_d = ☃;
      }

      public InstancePredicate() {
         this(MinMaxBounds.IntBound.field_211347_e, MinMaxBounds.IntBound.field_211347_e, null, null);
      }

      public boolean func_193463_a(@Nullable PotionEffect var1) {
         if (☃ == null) {
            return false;
         } else if (!this.field_193465_a.func_211339_d(☃.func_76458_c())) {
            return false;
         } else if (!this.field_193466_b.func_211339_d(☃.func_76459_b())) {
            return false;
         } else if (this.field_193467_c != null && this.field_193467_c != ☃.func_82720_e()) {
            return false;
         } else {
            return this.field_193468_d == null || this.field_193468_d == ☃.func_188418_e();
         }
      }

      public JsonElement func_204012_a() {
         JsonObject ☃ = new JsonObject();
         ☃.add("amplifier", this.field_193465_a.func_200321_c());
         ☃.add("duration", this.field_193466_b.func_200321_c());
         ☃.addProperty("ambient", this.field_193467_c);
         ☃.addProperty("visible", this.field_193468_d);
         return ☃;
      }

      public static MobEffectsPredicate.InstancePredicate func_193464_a(JsonObject var0) {
         MinMaxBounds.IntBound ☃ = MinMaxBounds.IntBound.func_211344_a(☃.get("amplifier"));
         MinMaxBounds.IntBound ☃x = MinMaxBounds.IntBound.func_211344_a(☃.get("duration"));
         Boolean ☃xx = ☃.has("ambient") ? JsonUtils.func_151212_i(☃, "ambient") : null;
         Boolean ☃xxx = ☃.has("visible") ? JsonUtils.func_151212_i(☃, "visible") : null;
         return new MobEffectsPredicate.InstancePredicate(☃, ☃x, ☃xx, ☃xxx);
      }
   }
}
