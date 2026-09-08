package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerBlockEntity;

public class BannerDuplicateRecipe extends CustomRecipe {
   public BannerDuplicateRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      DyeColor â˜ƒ = null;
      ItemStack â˜ƒx = null;
      ItemStack â˜ƒxx = null;

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ.getContainerSize(); ++â˜ƒxxx) {
         ItemStack â˜ƒxxxx = â˜ƒ.getItem(â˜ƒxxx);
         if (!â˜ƒxxxx.isEmpty()) {
            Item â˜ƒxxxxx = â˜ƒxxxx.getItem();
            if (!(â˜ƒxxxxx instanceof BannerItem)) {
               return false;
            }

            BannerItem â˜ƒxxxxx = (BannerItem)â˜ƒxxxxx;
            if (â˜ƒ == null) {
               â˜ƒ = â˜ƒxxxxx.getColor();
            } else if (â˜ƒ != â˜ƒxxxxx.getColor()) {
               return false;
            }

            int â˜ƒxxxxx = BannerBlockEntity.getPatternCount(â˜ƒxxxx);
            if (â˜ƒxxxxx > 6) {
               return false;
            }

            if (â˜ƒxxxxx > 0) {
               if (â˜ƒx != null) {
                  return false;
               }

               â˜ƒx = â˜ƒxxxx;
            } else {
               if (â˜ƒxx != null) {
                  return false;
               }

               â˜ƒxx = â˜ƒxxxx;
            }
         }
      }

      return â˜ƒx != null && â˜ƒxx != null;
   }

   public ItemStack assemble(CraftingContainer var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getContainerSize(); ++â˜ƒ) {
         ItemStack â˜ƒx = â˜ƒ.getItem(â˜ƒ);
         if (!â˜ƒx.isEmpty()) {
            int â˜ƒxx = BannerBlockEntity.getPatternCount(â˜ƒx);
            if (â˜ƒxx > 0 && â˜ƒxx <= 6) {
               ItemStack â˜ƒxxx = â˜ƒx.copy();
               â˜ƒxxx.setCount(1);
               return â˜ƒxxx;
            }
         }
      }

      return ItemStack.EMPTY;
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingContainer var1) {
      NonNullList<ItemStack> â˜ƒ = NonNullList.withSize(â˜ƒ.getContainerSize(), ItemStack.EMPTY);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            if (â˜ƒxx.getItem().hasCraftingRemainingItem()) {
               â˜ƒ.set(â˜ƒx, new ItemStack(â˜ƒxx.getItem().getCraftingRemainingItem()));
            } else if (â˜ƒxx.hasTag() && BannerBlockEntity.getPatternCount(â˜ƒxx) > 0) {
               ItemStack â˜ƒxxx = â˜ƒxx.copy();
               â˜ƒxxx.setCount(1);
               â˜ƒ.set(â˜ƒx, â˜ƒxxx);
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.BANNER_DUPLICATE;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }
}
