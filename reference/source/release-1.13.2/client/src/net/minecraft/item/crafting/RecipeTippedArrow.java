package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeTippedArrow extends IRecipeHidden {
   public RecipeTippedArrow(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (☃.func_174922_i() == 3 && ☃.func_174923_h() == 3) {
         for(int ☃ = 0; ☃ < ☃.func_174922_i(); ++☃) {
            for(int ☃x = 0; ☃x < ☃.func_174923_h(); ++☃x) {
               ItemStack ☃xx = ☃.func_70301_a(☃ + ☃x * ☃.func_174922_i());
               if (☃xx.func_190926_b()) {
                  return false;
               }

               Item ☃xx = ☃xx.func_77973_b();
               if (☃ == 1 && ☃x == 1) {
                  if (☃xx != Items.field_185156_bI) {
                     return false;
                  }
               } else if (☃xx != Items.field_151032_g) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      ItemStack ☃ = ☃.func_70301_a(1 + ☃.func_174922_i());
      if (☃.func_77973_b() != Items.field_185156_bI) {
         return ItemStack.field_190927_a;
      } else {
         ItemStack ☃ = new ItemStack(Items.field_185167_i, 8);
         PotionUtils.func_185188_a(☃, PotionUtils.func_185191_c(☃));
         PotionUtils.func_185184_a(☃, PotionUtils.func_185190_b(☃));
         return ☃;
      }
   }

   @Override
   public boolean func_194133_a(int var1, int var2) {
      return ☃ >= 2 && ☃ >= 2;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199585_k;
   }
}
