package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;

public class SuspiciousStewRecipe extends CustomRecipe {
   public SuspiciousStewRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      boolean â˜ƒ = false;
      boolean â˜ƒx = false;
      boolean â˜ƒxx = false;
      boolean â˜ƒxxx = false;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ.getContainerSize(); ++â˜ƒxxxx) {
         ItemStack â˜ƒxxxxx = â˜ƒ.getItem(â˜ƒxxxx);
         if (!â˜ƒxxxxx.isEmpty()) {
            if (â˜ƒxxxxx.is(Blocks.BROWN_MUSHROOM.asItem()) && !â˜ƒxx) {
               â˜ƒxx = true;
            } else if (â˜ƒxxxxx.is(Blocks.RED_MUSHROOM.asItem()) && !â˜ƒx) {
               â˜ƒx = true;
            } else if (â˜ƒxxxxx.is(ItemTags.SMALL_FLOWERS) && !â˜ƒ) {
               â˜ƒ = true;
            } else {
               if (!â˜ƒxxxxx.is(Items.BOWL) || â˜ƒxxx) {
                  return false;
               }

               â˜ƒxxx = true;
            }
         }
      }

      return â˜ƒ && â˜ƒxx && â˜ƒx && â˜ƒxxx;
   }

   public ItemStack assemble(CraftingContainer var1) {
      ItemStack â˜ƒ = ItemStack.EMPTY;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
         if (!â˜ƒxx.isEmpty() && â˜ƒxx.is(ItemTags.SMALL_FLOWERS)) {
            â˜ƒ = â˜ƒxx;
            break;
         }
      }

      ItemStack â˜ƒxx = new ItemStack(Items.SUSPICIOUS_STEW, 1);
      if (â˜ƒ.getItem() instanceof BlockItem && ((BlockItem)â˜ƒ.getItem()).getBlock() instanceof FlowerBlock â˜ƒx) {
         MobEffect â˜ƒxxx = â˜ƒx.getSuspiciousStewEffect();
         SuspiciousStewItem.saveMobEffect(â˜ƒxx, â˜ƒxxx, â˜ƒx.getEffectDuration());
      }

      return â˜ƒxx;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ >= 2 && â˜ƒ >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SUSPICIOUS_STEW;
   }
}
