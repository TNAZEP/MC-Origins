package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class MapCloningRecipe extends CustomRecipe {
   public MapCloningRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      int â˜ƒ = 0;
      ItemStack â˜ƒx = ItemStack.EMPTY;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxxx.is(Items.FILLED_MAP)) {
               if (!â˜ƒx.isEmpty()) {
                  return false;
               }

               â˜ƒx = â˜ƒxxx;
            } else {
               if (!â˜ƒxxx.is(Items.MAP)) {
                  return false;
               }

               ++â˜ƒ;
            }
         }
      }

      return !â˜ƒx.isEmpty() && â˜ƒ > 0;
   }

   public ItemStack assemble(CraftingContainer var1) {
      int â˜ƒ = 0;
      ItemStack â˜ƒx = ItemStack.EMPTY;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxxx.is(Items.FILLED_MAP)) {
               if (!â˜ƒx.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               â˜ƒx = â˜ƒxxx;
            } else {
               if (!â˜ƒxxx.is(Items.MAP)) {
                  return ItemStack.EMPTY;
               }

               ++â˜ƒ;
            }
         }
      }

      if (!â˜ƒx.isEmpty() && â˜ƒ >= 1) {
         ItemStack â˜ƒxx = â˜ƒx.copy();
         â˜ƒxx.setCount(â˜ƒ + 1);
         return â˜ƒxx;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ >= 3 && â˜ƒ >= 3;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.MAP_CLONING;
   }
}
