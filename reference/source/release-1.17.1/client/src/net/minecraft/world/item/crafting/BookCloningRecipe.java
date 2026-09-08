package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;

public class BookCloningRecipe extends CustomRecipe {
   public BookCloningRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      int â˜ƒ = 0;
      ItemStack â˜ƒx = ItemStack.EMPTY;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxxx.is(Items.WRITTEN_BOOK)) {
               if (!â˜ƒx.isEmpty()) {
                  return false;
               }

               â˜ƒx = â˜ƒxxx;
            } else {
               if (!â˜ƒxxx.is(Items.WRITABLE_BOOK)) {
                  return false;
               }

               ++â˜ƒ;
            }
         }
      }

      return !â˜ƒx.isEmpty() && â˜ƒx.hasTag() && â˜ƒ > 0;
   }

   public ItemStack assemble(CraftingContainer var1) {
      int â˜ƒ = 0;
      ItemStack â˜ƒx = ItemStack.EMPTY;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxxx.is(Items.WRITTEN_BOOK)) {
               if (!â˜ƒx.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               â˜ƒx = â˜ƒxxx;
            } else {
               if (!â˜ƒxxx.is(Items.WRITABLE_BOOK)) {
                  return ItemStack.EMPTY;
               }

               ++â˜ƒ;
            }
         }
      }

      if (!â˜ƒx.isEmpty() && â˜ƒx.hasTag() && â˜ƒ >= 1 && WrittenBookItem.getGeneration(â˜ƒx) < 2) {
         ItemStack â˜ƒxx = new ItemStack(Items.WRITTEN_BOOK, â˜ƒ);
         CompoundTag â˜ƒxxx = â˜ƒx.getTag().copy();
         â˜ƒxxx.putInt("generation", WrittenBookItem.getGeneration(â˜ƒx) + 1);
         â˜ƒxx.setTag(â˜ƒxxx);
         return â˜ƒxx;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingContainer var1) {
      NonNullList<ItemStack> â˜ƒ = NonNullList.withSize(â˜ƒ.getContainerSize(), ItemStack.EMPTY);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
         if (â˜ƒxx.getItem().hasCraftingRemainingItem()) {
            â˜ƒ.set(â˜ƒx, new ItemStack(â˜ƒxx.getItem().getCraftingRemainingItem()));
         } else if (â˜ƒxx.getItem() instanceof WrittenBookItem) {
            ItemStack â˜ƒxx = â˜ƒxx.copy();
            â˜ƒxx.setCount(1);
            â˜ƒ.set(â˜ƒx, â˜ƒxx);
            break;
         }
      }

      return â˜ƒ;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.BOOK_CLONING;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ >= 3 && â˜ƒ >= 3;
   }
}
