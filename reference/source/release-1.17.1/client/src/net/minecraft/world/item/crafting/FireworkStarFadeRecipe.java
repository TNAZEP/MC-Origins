package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FireworkStarFadeRecipe extends CustomRecipe {
   private static final Ingredient STAR_INGREDIENT = Ingredient.of(Items.FIREWORK_STAR);

   public FireworkStarFadeRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      boolean â˜ƒ = false;
      boolean â˜ƒx = false;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxxx.getItem() instanceof DyeItem) {
               â˜ƒ = true;
            } else {
               if (!STAR_INGREDIENT.test(â˜ƒxxx)) {
                  return false;
               }

               if (â˜ƒx) {
                  return false;
               }

               â˜ƒx = true;
            }
         }
      }

      return â˜ƒx && â˜ƒ;
   }

   public ItemStack assemble(CraftingContainer var1) {
      List<Integer> â˜ƒ = Lists.newArrayList();
      ItemStack â˜ƒx = null;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         Item â˜ƒxxxx = â˜ƒxxx.getItem();
         if (â˜ƒxxxx instanceof DyeItem) {
            â˜ƒ.add(((DyeItem)â˜ƒxxxx).getDyeColor().getFireworkColor());
         } else if (STAR_INGREDIENT.test(â˜ƒxxx)) {
            â˜ƒx = â˜ƒxxx.copy();
            â˜ƒx.setCount(1);
         }
      }

      if (â˜ƒx != null && !â˜ƒ.isEmpty()) {
         â˜ƒx.getOrCreateTagElement("Explosion").putIntArray("FadeColors", â˜ƒ);
         return â˜ƒx;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.FIREWORK_STAR_FADE;
   }
}
