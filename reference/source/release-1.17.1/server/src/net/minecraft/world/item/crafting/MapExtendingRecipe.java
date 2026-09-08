package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapExtendingRecipe extends ShapedRecipe {
   public MapExtendingRecipe(ResourceLocation var1) {
      super(
         â˜ƒ,
         "",
         3,
         3,
         NonNullList.of(
            Ingredient.EMPTY,
            Ingredient.of(Items.PAPER),
            Ingredient.of(Items.PAPER),
            Ingredient.of(Items.PAPER),
            Ingredient.of(Items.PAPER),
            Ingredient.of(Items.FILLED_MAP),
            Ingredient.of(Items.PAPER),
            Ingredient.of(Items.PAPER),
            Ingredient.of(Items.PAPER),
            Ingredient.of(Items.PAPER)
         ),
         new ItemStack(Items.MAP)
      );
   }

   @Override
   public boolean matches(CraftingContainer var1, Level var2) {
      if (!super.matches(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         ItemStack â˜ƒ = ItemStack.EMPTY;

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize() && â˜ƒ.isEmpty(); ++â˜ƒx) {
            ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
            if (â˜ƒxx.is(Items.FILLED_MAP)) {
               â˜ƒ = â˜ƒxx;
            }
         }

         if (â˜ƒ.isEmpty()) {
            return false;
         } else {
            MapItemSavedData â˜ƒx = MapItem.getSavedData(â˜ƒ, â˜ƒ);
            if (â˜ƒx == null) {
               return false;
            } else if (â˜ƒx.isExplorationMap()) {
               return false;
            } else {
               return â˜ƒx.scale < 4;
            }
         }
      }
   }

   @Override
   public ItemStack assemble(CraftingContainer var1) {
      ItemStack â˜ƒ = ItemStack.EMPTY;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize() && â˜ƒ.isEmpty(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
         if (â˜ƒxx.is(Items.FILLED_MAP)) {
            â˜ƒ = â˜ƒxx;
         }
      }

      â˜ƒ = â˜ƒ.copy();
      â˜ƒ.setCount(1);
      â˜ƒ.getOrCreateTag().putInt("map_scale_direction", 1);
      return â˜ƒ;
   }

   @Override
   public boolean isSpecial() {
      return true;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.MAP_EXTENDING;
   }
}
