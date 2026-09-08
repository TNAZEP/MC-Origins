package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipesMapCloning extends IRecipeHidden {
   public RecipesMapCloning(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         int ☃ = 0;
         ItemStack ☃x = ItemStack.field_190927_a;

         for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (!☃xxx.func_190926_b()) {
               if (☃xxx.func_77973_b() == Items.field_151098_aY) {
                  if (!☃x.func_190926_b()) {
                     return false;
                  }

                  ☃x = ☃xxx;
               } else {
                  if (☃xxx.func_77973_b() != Items.field_151148_bJ) {
                     return false;
                  }

                  ++☃;
               }
            }
         }

         return !☃x.func_190926_b() && ☃ > 0;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      int ☃ = 0;
      ItemStack ☃x = ItemStack.field_190927_a;

      for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
         ItemStack ☃xxx = ☃.func_70301_a(☃xx);
         if (!☃xxx.func_190926_b()) {
            if (☃xxx.func_77973_b() == Items.field_151098_aY) {
               if (!☃x.func_190926_b()) {
                  return ItemStack.field_190927_a;
               }

               ☃x = ☃xxx;
            } else {
               if (☃xxx.func_77973_b() != Items.field_151148_bJ) {
                  return ItemStack.field_190927_a;
               }

               ++☃;
            }
         }
      }

      if (!☃x.func_190926_b() && ☃ >= 1) {
         ItemStack ☃xx = ☃x.func_77946_l();
         ☃xx.func_190920_e(☃ + 1);
         return ☃xx;
      } else {
         return ItemStack.field_190927_a;
      }
   }

   @Override
   public boolean func_194133_a(int var1, int var2) {
      return ☃ >= 3 && ☃ >= 3;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199579_e;
   }
}
