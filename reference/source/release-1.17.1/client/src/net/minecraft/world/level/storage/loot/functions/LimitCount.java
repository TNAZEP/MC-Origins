package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LimitCount extends LootItemConditionalFunction {
   final IntRange limiter;

   LimitCount(LootItemCondition[] var1, IntRange var2) {
      super(â˜ƒ);
      this.limiter = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.LIMIT_COUNT;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.limiter.getReferencedContextParams();
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      int â˜ƒ = this.limiter.clamp(â˜ƒ, â˜ƒ.getCount());
      â˜ƒ.setCount(â˜ƒ);
      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> limitCount(IntRange var0) {
      return simpleBuilder(var1 -> new LimitCount(var1, â˜ƒ));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<LimitCount> {
      public void serialize(JsonObject var1, LimitCount var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.add("limit", â˜ƒ.serialize(â˜ƒ.limiter));
      }

      public LimitCount deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         IntRange â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "limit", â˜ƒ, IntRange.class);
         return new LimitCount(â˜ƒ, â˜ƒ);
      }
   }
}
