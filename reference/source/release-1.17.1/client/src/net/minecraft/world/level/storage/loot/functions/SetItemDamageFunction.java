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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetItemDamageFunction extends LootItemConditionalFunction {
   private static final Logger LOGGER = LogManager.getLogger();
   final NumberProvider damage;
   final boolean add;

   SetItemDamageFunction(LootItemCondition[] var1, NumberProvider var2, boolean var3) {
      super(â˜ƒ);
      this.damage = â˜ƒ;
      this.add = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_DAMAGE;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.damage.getReferencedContextParams();
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      if (â˜ƒ.isDamageableItem()) {
         int â˜ƒ = â˜ƒ.getMaxDamage();
         float â˜ƒx = this.add ? 1.0F - (float)â˜ƒ.getDamageValue() / (float)â˜ƒ : 0.0F;
         float â˜ƒxx = 1.0F - Mth.clamp(this.damage.getFloat(â˜ƒ) + â˜ƒx, 0.0F, 1.0F);
         â˜ƒ.setDamageValue(Mth.floor(â˜ƒxx * (float)â˜ƒ));
      } else {
         LOGGER.warn("Couldn't set damage of loot item {}", â˜ƒ);
      }

      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> setDamage(NumberProvider var0) {
      return simpleBuilder(var1 -> new SetItemDamageFunction(var1, â˜ƒ, false));
   }

   public static LootItemConditionalFunction.Builder<?> setDamage(NumberProvider var0, boolean var1) {
      return simpleBuilder(var2 -> new SetItemDamageFunction(var2, â˜ƒ, â˜ƒ));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetItemDamageFunction> {
      public void serialize(JsonObject var1, SetItemDamageFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.add("damage", â˜ƒ.serialize(â˜ƒ.damage));
         â˜ƒ.addProperty("add", â˜ƒ.add);
      }

      public SetItemDamageFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         NumberProvider â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "damage", â˜ƒ, NumberProvider.class);
         boolean â˜ƒx = GsonHelper.getAsBoolean(â˜ƒ, "add", false);
         return new SetItemDamageFunction(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }
}
