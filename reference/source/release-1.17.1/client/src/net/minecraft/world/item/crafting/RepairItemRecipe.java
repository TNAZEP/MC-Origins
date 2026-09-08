package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class RepairItemRecipe extends CustomRecipe {
   public RepairItemRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayList();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒ.add(â˜ƒxx);
            if (â˜ƒ.size() > 1) {
               ItemStack â˜ƒxxx = (ItemStack)â˜ƒ.get(0);
               if (!â˜ƒxx.is(â˜ƒxxx.getItem()) || â˜ƒxxx.getCount() != 1 || â˜ƒxx.getCount() != 1 || !â˜ƒxxx.getItem().canBeDepleted()) {
                  return false;
               }
            }
         }
      }

      return â˜ƒ.size() == 2;
   }

   public ItemStack assemble(CraftingContainer var1) {
      List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayList();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒ.add(â˜ƒxx);
            if (â˜ƒ.size() > 1) {
               ItemStack â˜ƒxxx = (ItemStack)â˜ƒ.get(0);
               if (!â˜ƒxx.is(â˜ƒxxx.getItem()) || â˜ƒxxx.getCount() != 1 || â˜ƒxx.getCount() != 1 || !â˜ƒxxx.getItem().canBeDepleted()) {
                  return ItemStack.EMPTY;
               }
            }
         }
      }

      if (â˜ƒ.size() == 2) {
         ItemStack â˜ƒx = (ItemStack)â˜ƒ.get(0);
         ItemStack â˜ƒxx = (ItemStack)â˜ƒ.get(1);
         if (â˜ƒx.is(â˜ƒxx.getItem()) && â˜ƒx.getCount() == 1 && â˜ƒxx.getCount() == 1 && â˜ƒx.getItem().canBeDepleted()) {
            Item â˜ƒxxx = â˜ƒx.getItem();
            int â˜ƒxxxx = â˜ƒxxx.getMaxDamage() - â˜ƒx.getDamageValue();
            int â˜ƒxxxxx = â˜ƒxxx.getMaxDamage() - â˜ƒxx.getDamageValue();
            int â˜ƒxxxxxx = â˜ƒxxxx + â˜ƒxxxxx + â˜ƒxxx.getMaxDamage() * 5 / 100;
            int â˜ƒxxxxxxx = â˜ƒxxx.getMaxDamage() - â˜ƒxxxxxx;
            if (â˜ƒxxxxxxx < 0) {
               â˜ƒxxxxxxx = 0;
            }

            ItemStack â˜ƒxxx = new ItemStack(â˜ƒx.getItem());
            â˜ƒxxx.setDamageValue(â˜ƒxxxxxxx);
            Map<Enchantment, Integer> â˜ƒxxxx = Maps.newHashMap();
            Map<Enchantment, Integer> â˜ƒxxxxx = EnchantmentHelper.getEnchantments(â˜ƒx);
            Map<Enchantment, Integer> â˜ƒxxxxxx = EnchantmentHelper.getEnchantments(â˜ƒxx);
            Registry.ENCHANTMENT.stream().filter(Enchantment::isCurse).forEach(var3x -> {
               int â˜ƒ = Math.max(â˜ƒ.getOrDefault(var3x, 0), â˜ƒ.getOrDefault(var3x, 0));
               if (â˜ƒ > 0) {
                  â˜ƒ.put(var3x, â˜ƒ);
               }
            });
            if (!â˜ƒxxxx.isEmpty()) {
               EnchantmentHelper.setEnchantments(â˜ƒxxxx, â˜ƒxxx);
            }

            return â˜ƒxxx;
         }
      }

      return ItemStack.EMPTY;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.REPAIR_ITEM;
   }
}
