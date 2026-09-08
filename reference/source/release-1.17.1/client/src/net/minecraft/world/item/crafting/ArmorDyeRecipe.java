package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArmorDyeRecipe extends CustomRecipe {
   public ArmorDyeRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      List<ItemStack> â˜ƒx = Lists.<ItemStack>newArrayList();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxxx.getItem() instanceof DyeableLeatherItem) {
               if (!â˜ƒ.isEmpty()) {
                  return false;
               }

               â˜ƒ = â˜ƒxxx;
            } else {
               if (!(â˜ƒxxx.getItem() instanceof DyeItem)) {
                  return false;
               }

               â˜ƒx.add(â˜ƒxxx);
            }
         }
      }

      return !â˜ƒ.isEmpty() && !â˜ƒx.isEmpty();
   }

   public ItemStack assemble(CraftingContainer var1) {
      List<DyeItem> â˜ƒ = Lists.<DyeItem>newArrayList();
      ItemStack â˜ƒx = ItemStack.EMPTY;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            Item â˜ƒxxxx = â˜ƒxxx.getItem();
            if (â˜ƒxxxx instanceof DyeableLeatherItem) {
               if (!â˜ƒx.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               â˜ƒx = â˜ƒxxx.copy();
            } else {
               if (!(â˜ƒxxxx instanceof DyeItem)) {
                  return ItemStack.EMPTY;
               }

               â˜ƒ.add((DyeItem)â˜ƒxxxx);
            }
         }
      }

      return !â˜ƒx.isEmpty() && !â˜ƒ.isEmpty() ? DyeableLeatherItem.dyeArmor(â˜ƒx, â˜ƒ) : ItemStack.EMPTY;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.ARMOR_DYE;
   }
}
