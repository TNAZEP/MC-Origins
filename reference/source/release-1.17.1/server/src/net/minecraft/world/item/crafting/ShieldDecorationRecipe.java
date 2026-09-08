package net.minecraft.world.item.crafting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ShieldDecorationRecipe extends CustomRecipe {
   public ShieldDecorationRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      ItemStack â˜ƒx = ItemStack.EMPTY;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxxx.getItem() instanceof BannerItem) {
               if (!â˜ƒx.isEmpty()) {
                  return false;
               }

               â˜ƒx = â˜ƒxxx;
            } else {
               if (!â˜ƒxxx.is(Items.SHIELD)) {
                  return false;
               }

               if (!â˜ƒ.isEmpty()) {
                  return false;
               }

               if (â˜ƒxxx.getTagElement("BlockEntityTag") != null) {
                  return false;
               }

               â˜ƒ = â˜ƒxxx;
            }
         }
      }

      return !â˜ƒ.isEmpty() && !â˜ƒx.isEmpty();
   }

   public ItemStack assemble(CraftingContainer var1) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      ItemStack â˜ƒx = ItemStack.EMPTY;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxxx.getItem() instanceof BannerItem) {
               â˜ƒ = â˜ƒxxx;
            } else if (â˜ƒxxx.is(Items.SHIELD)) {
               â˜ƒx = â˜ƒxxx.copy();
            }
         }
      }

      if (â˜ƒx.isEmpty()) {
         return â˜ƒx;
      } else {
         CompoundTag â˜ƒxx = â˜ƒ.getTagElement("BlockEntityTag");
         CompoundTag â˜ƒxxx = â˜ƒxx == null ? new CompoundTag() : â˜ƒxx.copy();
         â˜ƒxxx.putInt("Base", ((BannerItem)â˜ƒ.getItem()).getColor().getId());
         â˜ƒx.addTagElement("BlockEntityTag", â˜ƒxxx);
         return â˜ƒx;
      }
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHIELD_DECORATION;
   }
}
