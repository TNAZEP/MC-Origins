package net.minecraft.world.level.storage.loot.functions;

import java.util.function.BiFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;

public class LootItemFunctions {
   public static final BiFunction<ItemStack, LootContext, ItemStack> IDENTITY = (var0, var1) -> var0;
   public static final LootItemFunctionType SET_COUNT = register("set_count", new SetItemCountFunction.Serializer());
   public static final LootItemFunctionType ENCHANT_WITH_LEVELS = register("enchant_with_levels", new EnchantWithLevelsFunction.Serializer());
   public static final LootItemFunctionType ENCHANT_RANDOMLY = register("enchant_randomly", new EnchantRandomlyFunction.Serializer());
   public static final LootItemFunctionType SET_ENCHANTMENTS = register("set_enchantments", new SetEnchantmentsFunction.Serializer());
   public static final LootItemFunctionType SET_NBT = register("set_nbt", new SetNbtFunction.Serializer());
   public static final LootItemFunctionType FURNACE_SMELT = register("furnace_smelt", new SmeltItemFunction.Serializer());
   public static final LootItemFunctionType LOOTING_ENCHANT = register("looting_enchant", new LootingEnchantFunction.Serializer());
   public static final LootItemFunctionType SET_DAMAGE = register("set_damage", new SetItemDamageFunction.Serializer());
   public static final LootItemFunctionType SET_ATTRIBUTES = register("set_attributes", new SetAttributesFunction.Serializer());
   public static final LootItemFunctionType SET_NAME = register("set_name", new SetNameFunction.Serializer());
   public static final LootItemFunctionType EXPLORATION_MAP = register("exploration_map", new ExplorationMapFunction.Serializer());
   public static final LootItemFunctionType SET_STEW_EFFECT = register("set_stew_effect", new SetStewEffectFunction.Serializer());
   public static final LootItemFunctionType COPY_NAME = register("copy_name", new CopyNameFunction.Serializer());
   public static final LootItemFunctionType SET_CONTENTS = register("set_contents", new SetContainerContents.Serializer());
   public static final LootItemFunctionType LIMIT_COUNT = register("limit_count", new LimitCount.Serializer());
   public static final LootItemFunctionType APPLY_BONUS = register("apply_bonus", new ApplyBonusCount.Serializer());
   public static final LootItemFunctionType SET_LOOT_TABLE = register("set_loot_table", new SetContainerLootTable.Serializer());
   public static final LootItemFunctionType EXPLOSION_DECAY = register("explosion_decay", new ApplyExplosionDecay.Serializer());
   public static final LootItemFunctionType SET_LORE = register("set_lore", new SetLoreFunction.Serializer());
   public static final LootItemFunctionType FILL_PLAYER_HEAD = register("fill_player_head", new FillPlayerHead.Serializer());
   public static final LootItemFunctionType COPY_NBT = register("copy_nbt", new CopyNbtFunction.Serializer());
   public static final LootItemFunctionType COPY_STATE = register("copy_state", new CopyBlockState.Serializer());
   public static final LootItemFunctionType SET_BANNER_PATTERN = register("set_banner_pattern", new SetBannerPatternFunction.Serializer());

   private static LootItemFunctionType register(String var0, Serializer<? extends LootItemFunction> var1) {
      return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(â˜ƒ), new LootItemFunctionType(â˜ƒ));
   }

   public static Object createGsonAdapter() {
      return GsonAdapterFactory.<LootItemFunction, LootItemFunctionType>builder(Registry.LOOT_FUNCTION_TYPE, "function", "function", LootItemFunction::getType)
         .build();
   }

   public static BiFunction<ItemStack, LootContext, ItemStack> compose(BiFunction<ItemStack, LootContext, ItemStack>[] var0) {
      switch(â˜ƒ.length) {
         case 0:
            return IDENTITY;
         case 1:
            return â˜ƒ[0];
         case 2:
            BiFunction<ItemStack, LootContext, ItemStack> â˜ƒ = â˜ƒ[0];
            BiFunction<ItemStack, LootContext, ItemStack> â˜ƒx = â˜ƒ[1];
            return (var2x, var3) -> (ItemStack)â˜ƒ.apply((ItemStack)â˜ƒ.apply(var2x, var3), var3);
         default:
            return (var1x, var2x) -> {
               for(BiFunction<ItemStack, LootContext, ItemStack> â˜ƒ : â˜ƒ) {
                  var1x = (ItemStack)â˜ƒ.apply(var1x, var2x);
               }

               return var1x;
            };
      }
   }
}
