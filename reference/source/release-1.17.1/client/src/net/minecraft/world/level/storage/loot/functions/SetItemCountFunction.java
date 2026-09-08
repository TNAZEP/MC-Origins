package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetItemCountFunction extends LootItemConditionalFunction {
   final NumberProvider value;
   final boolean add;

   SetItemCountFunction(LootItemCondition[] var1, NumberProvider var2, boolean var3) {
      super(â˜ƒ);
      this.value = â˜ƒ;
      this.add = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_COUNT;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.value.getReferencedContextParams();
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      int â˜ƒ = this.add ? â˜ƒ.getCount() : 0;
      â˜ƒ.setCount(Mth.clamp(â˜ƒ + this.value.getInt(â˜ƒ), 0, â˜ƒ.getMaxStackSize()));
      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> setCount(NumberProvider var0) {
      return simpleBuilder(var1 -> new SetItemCountFunction(var1, â˜ƒ, false));
   }

   public static LootItemConditionalFunction.Builder<?> setCount(NumberProvider var0, boolean var1) {
      return simpleBuilder(var2 -> new SetItemCountFunction(var2, â˜ƒ, â˜ƒ));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetItemCountFunction> {
      public void serialize(JsonObject var1, SetItemCountFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.add("count", â˜ƒ.serialize(â˜ƒ.value));
         â˜ƒ.addProperty("add", â˜ƒ.add);
      }

      public SetItemCountFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         NumberProvider â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "count", â˜ƒ, NumberProvider.class);
         boolean â˜ƒx = GsonHelper.getAsBoolean(â˜ƒ, "add", false);
         return new SetItemCountFunction(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }
}
