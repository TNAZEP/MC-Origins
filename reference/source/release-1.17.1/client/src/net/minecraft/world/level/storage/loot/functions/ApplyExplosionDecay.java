package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Random;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ApplyExplosionDecay extends LootItemConditionalFunction {
   ApplyExplosionDecay(LootItemCondition[] var1) {
      super(â˜ƒ);
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.EXPLOSION_DECAY;
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      Float â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
      if (â˜ƒ != null) {
         Random â˜ƒx = â˜ƒ.getRandom();
         float â˜ƒxx = 1.0F / â˜ƒ;
         int â˜ƒxxx = â˜ƒ.getCount();
         int â˜ƒxxxx = 0;

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxx; ++â˜ƒxxxxx) {
            if (â˜ƒx.nextFloat() <= â˜ƒxx) {
               ++â˜ƒxxxx;
            }
         }

         â˜ƒ.setCount(â˜ƒxxxx);
      }

      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> explosionDecay() {
      return simpleBuilder(ApplyExplosionDecay::new);
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<ApplyExplosionDecay> {
      public ApplyExplosionDecay deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         return new ApplyExplosionDecay(â˜ƒ);
      }
   }
}
