package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetStewEffectFunction extends LootItemConditionalFunction {
   final Map<MobEffect, NumberProvider> effectDurationMap;

   SetStewEffectFunction(LootItemCondition[] var1, Map<MobEffect, NumberProvider> var2) {
      super(â˜ƒ);
      this.effectDurationMap = ImmutableMap.copyOf(â˜ƒ);
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_STEW_EFFECT;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return (Set<LootContextParam<?>>)this.effectDurationMap
         .values()
         .stream()
         .flatMap(var0 -> var0.getReferencedContextParams().stream())
         .collect(ImmutableSet.toImmutableSet());
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      if (â˜ƒ.is(Items.SUSPICIOUS_STEW) && !this.effectDurationMap.isEmpty()) {
         Random â˜ƒ = â˜ƒ.getRandom();
         int â˜ƒx = â˜ƒ.nextInt(this.effectDurationMap.size());
         Entry<MobEffect, NumberProvider> â˜ƒxx = Iterables.get(this.effectDurationMap.entrySet(), â˜ƒx);
         MobEffect â˜ƒxxx = (MobEffect)â˜ƒxx.getKey();
         int â˜ƒxxxx = ((NumberProvider)â˜ƒxx.getValue()).getInt(â˜ƒ);
         if (!â˜ƒxxx.isInstantenous()) {
            â˜ƒxxxx *= 20;
         }

         SuspiciousStewItem.saveMobEffect(â˜ƒ, â˜ƒxxx, â˜ƒxxxx);
         return â˜ƒ;
      } else {
         return â˜ƒ;
      }
   }

   public static SetStewEffectFunction.Builder stewEffect() {
      return new SetStewEffectFunction.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetStewEffectFunction.Builder> {
      private final Map<MobEffect, NumberProvider> effectDurationMap = Maps.<MobEffect, NumberProvider>newHashMap();

      protected SetStewEffectFunction.Builder getThis() {
         return this;
      }

      public SetStewEffectFunction.Builder withEffect(MobEffect var1, NumberProvider var2) {
         this.effectDurationMap.put(â˜ƒ, â˜ƒ);
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new SetStewEffectFunction(this.getConditions(), this.effectDurationMap);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetStewEffectFunction> {
      public void serialize(JsonObject var1, SetStewEffectFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         if (!â˜ƒ.effectDurationMap.isEmpty()) {
            JsonArray â˜ƒ = new JsonArray();

            for(MobEffect â˜ƒx : â˜ƒ.effectDurationMap.keySet()) {
               JsonObject â˜ƒxx = new JsonObject();
               ResourceLocation â˜ƒxxx = Registry.MOB_EFFECT.getKey(â˜ƒx);
               if (â˜ƒxxx == null) {
                  throw new IllegalArgumentException("Don't know how to serialize mob effect " + â˜ƒx);
               }

               â˜ƒxx.add("type", new JsonPrimitive(â˜ƒxxx.toString()));
               â˜ƒxx.add("duration", â˜ƒ.serialize(â˜ƒ.effectDurationMap.get(â˜ƒx)));
               â˜ƒ.add(â˜ƒxx);
            }

            â˜ƒ.add("effects", â˜ƒ);
         }
      }

      public SetStewEffectFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         Map<MobEffect, NumberProvider> â˜ƒ = Maps.<MobEffect, NumberProvider>newHashMap();
         if (â˜ƒ.has("effects")) {
            for(JsonElement â˜ƒx : GsonHelper.getAsJsonArray(â˜ƒ, "effects")) {
               String â˜ƒxx = GsonHelper.getAsString(â˜ƒx.getAsJsonObject(), "type");
               MobEffect â˜ƒxxx = (MobEffect)Registry.MOB_EFFECT
                  .getOptional(new ResourceLocation(â˜ƒxx))
                  .orElseThrow(() -> new JsonSyntaxException("Unknown mob effect '" + â˜ƒ + "'"));
               NumberProvider â˜ƒxxxx = GsonHelper.getAsObject(â˜ƒx.getAsJsonObject(), "duration", â˜ƒ, NumberProvider.class);
               â˜ƒ.put(â˜ƒxxx, â˜ƒxxxx);
            }
         }

         return new SetStewEffectFunction(â˜ƒ, â˜ƒ);
      }
   }
}
