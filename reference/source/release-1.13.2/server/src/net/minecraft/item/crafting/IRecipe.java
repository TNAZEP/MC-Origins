package net.minecraft.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IRecipe {
   boolean func_77569_a(IInventory var1, World var2);

   ItemStack func_77572_b(IInventory var1);

   ItemStack func_77571_b();

   default NonNullList<ItemStack> func_179532_b(IInventory var1) {
      NonNullList<ItemStack> ☃ = NonNullList.func_191197_a(☃.func_70302_i_(), ItemStack.field_190927_a);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         Item ☃xx = ☃.func_70301_a(☃x).func_77973_b();
         if (☃xx.func_77634_r()) {
            ☃.set(☃x, new ItemStack(☃xx.func_77668_q()));
         }
      }

      return ☃;
   }

   default NonNullList<Ingredient> func_192400_c() {
      return NonNullList.func_191196_a();
   }

   default boolean func_192399_d() {
      return false;
   }

   ResourceLocation func_199560_c();

   IRecipeSerializer<?> func_199559_b();
}
