package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

public class TippedArrowRecipe extends CustomRecipe {
   public TippedArrowRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      if (â˜ƒ.getWidth() == 3 && â˜ƒ.getHeight() == 3) {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getWidth(); ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getHeight(); ++â˜ƒx) {
               ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒ + â˜ƒx * â˜ƒ.getWidth());
               if (â˜ƒxx.isEmpty()) {
                  return false;
               }

               if (â˜ƒ == 1 && â˜ƒx == 1) {
                  if (!â˜ƒxx.is(Items.LINGERING_POTION)) {
                     return false;
                  }
               } else if (!â˜ƒxx.is(Items.ARROW)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack assemble(CraftingContainer var1) {
      ItemStack â˜ƒ = â˜ƒ.getItem(1 + â˜ƒ.getWidth());
      if (!â˜ƒ.is(Items.LINGERING_POTION)) {
         return ItemStack.EMPTY;
      } else {
         ItemStack â˜ƒ = new ItemStack(Items.TIPPED_ARROW, 8);
         PotionUtils.setPotion(â˜ƒ, PotionUtils.getPotion(â˜ƒ));
         PotionUtils.setCustomEffects(â˜ƒ, PotionUtils.getCustomEffects(â˜ƒ));
         return â˜ƒ;
      }
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ >= 2 && â˜ƒ >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.TIPPED_ARROW;
   }
}
