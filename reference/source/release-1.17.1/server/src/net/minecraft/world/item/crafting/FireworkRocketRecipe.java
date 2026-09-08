package net.minecraft.world.item.crafting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FireworkRocketRecipe extends CustomRecipe {
   private static final Ingredient PAPER_INGREDIENT = Ingredient.of(Items.PAPER);
   private static final Ingredient GUNPOWDER_INGREDIENT = Ingredient.of(Items.GUNPOWDER);
   private static final Ingredient STAR_INGREDIENT = Ingredient.of(Items.FIREWORK_STAR);

   public FireworkRocketRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      boolean â˜ƒ = false;
      int â˜ƒx = 0;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            if (PAPER_INGREDIENT.test(â˜ƒxxx)) {
               if (â˜ƒ) {
                  return false;
               }

               â˜ƒ = true;
            } else if (GUNPOWDER_INGREDIENT.test(â˜ƒxxx)) {
               if (++â˜ƒx > 3) {
                  return false;
               }
            } else if (!STAR_INGREDIENT.test(â˜ƒxxx)) {
               return false;
            }
         }
      }

      return â˜ƒ && â˜ƒx >= 1;
   }

   public ItemStack assemble(CraftingContainer var1) {
      ItemStack â˜ƒ = new ItemStack(Items.FIREWORK_ROCKET, 3);
      CompoundTag â˜ƒx = â˜ƒ.getOrCreateTagElement("Fireworks");
      ListTag â˜ƒxx = new ListTag();
      int â˜ƒxxx = 0;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ.getContainerSize(); ++â˜ƒxxxx) {
         ItemStack â˜ƒxxxxx = â˜ƒ.getItem(â˜ƒxxxx);
         if (!â˜ƒxxxxx.isEmpty()) {
            if (GUNPOWDER_INGREDIENT.test(â˜ƒxxxxx)) {
               ++â˜ƒxxx;
            } else if (STAR_INGREDIENT.test(â˜ƒxxxxx)) {
               CompoundTag â˜ƒxxxxxx = â˜ƒxxxxx.getTagElement("Explosion");
               if (â˜ƒxxxxxx != null) {
                  â˜ƒxx.add(â˜ƒxxxxxx);
               }
            }
         }
      }

      â˜ƒx.putByte("Flight", (byte)â˜ƒxxx);
      if (!â˜ƒxx.isEmpty()) {
         â˜ƒx.put("Explosions", â˜ƒxx);
      }

      return â˜ƒ;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }

   @Override
   public ItemStack getResultItem() {
      return new ItemStack(Items.FIREWORK_ROCKET);
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.FIREWORK_ROCKET;
   }
}
