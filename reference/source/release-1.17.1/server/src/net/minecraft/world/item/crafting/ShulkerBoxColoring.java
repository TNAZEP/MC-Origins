package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class ShulkerBoxColoring extends CustomRecipe {
   public ShulkerBoxColoring(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      int â˜ƒ = 0;
      int â˜ƒx = 0;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (Block.byItem(â˜ƒxxx.getItem()) instanceof ShulkerBoxBlock) {
               ++â˜ƒ;
            } else {
               if (!(â˜ƒxxx.getItem() instanceof DyeItem)) {
                  return false;
               }

               ++â˜ƒx;
            }

            if (â˜ƒx > 1 || â˜ƒ > 1) {
               return false;
            }
         }
      }

      return â˜ƒ == 1 && â˜ƒx == 1;
   }

   public ItemStack assemble(CraftingContainer var1) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      DyeItem â˜ƒx = (DyeItem)Items.WHITE_DYE;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            Item â˜ƒxxxx = â˜ƒxxx.getItem();
            if (Block.byItem(â˜ƒxxxx) instanceof ShulkerBoxBlock) {
               â˜ƒ = â˜ƒxxx;
            } else if (â˜ƒxxxx instanceof DyeItem) {
               â˜ƒx = (DyeItem)â˜ƒxxxx;
            }
         }
      }

      ItemStack â˜ƒxx = ShulkerBoxBlock.getColoredItemStack(â˜ƒx.getDyeColor());
      if (â˜ƒ.hasTag()) {
         â˜ƒxx.setTag(â˜ƒ.getTag().copy());
      }

      return â˜ƒxx;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHULKER_BOX_COLORING;
   }
}
