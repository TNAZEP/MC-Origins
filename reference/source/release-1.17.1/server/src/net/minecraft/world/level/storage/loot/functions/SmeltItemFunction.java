package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Optional;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmeltItemFunction extends LootItemConditionalFunction {
   private static final Logger LOGGER = LogManager.getLogger();

   SmeltItemFunction(LootItemCondition[] var1) {
      super(â˜ƒ);
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.FURNACE_SMELT;
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      if (â˜ƒ.isEmpty()) {
         return â˜ƒ;
      } else {
         Optional<SmeltingRecipe> â˜ƒ = â˜ƒ.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(â˜ƒ), â˜ƒ.getLevel());
         if (â˜ƒ.isPresent()) {
            ItemStack â˜ƒx = ((SmeltingRecipe)â˜ƒ.get()).getResultItem();
            if (!â˜ƒx.isEmpty()) {
               ItemStack â˜ƒxx = â˜ƒx.copy();
               â˜ƒxx.setCount(â˜ƒ.getCount());
               return â˜ƒxx;
            }
         }

         LOGGER.warn("Couldn't smelt {} because there is no smelting recipe", â˜ƒ);
         return â˜ƒ;
      }
   }

   public static LootItemConditionalFunction.Builder<?> smelted() {
      return simpleBuilder(SmeltItemFunction::new);
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SmeltItemFunction> {
      public SmeltItemFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         return new SmeltItemFunction(â˜ƒ);
      }
   }
}
