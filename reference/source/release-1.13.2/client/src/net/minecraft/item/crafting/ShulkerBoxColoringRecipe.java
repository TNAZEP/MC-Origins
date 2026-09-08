package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShulkerBoxColoringRecipe extends IRecipeHidden {
   public ShulkerBoxColoringRecipe(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         int ☃ = 0;
         int ☃x = 0;

         for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (!☃xxx.func_190926_b()) {
               if (Block.func_149634_a(☃xxx.func_77973_b()) instanceof BlockShulkerBox) {
                  ++☃;
               } else {
                  if (!(☃xxx.func_77973_b() instanceof ItemDye)) {
                     return false;
                  }

                  ++☃x;
               }

               if (☃x > 1 || ☃ > 1) {
                  return false;
               }
            }
         }

         return ☃ == 1 && ☃x == 1;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      ItemStack ☃ = ItemStack.field_190927_a;
      ItemDye ☃x = (ItemDye)Items.field_196106_bc;

      for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
         ItemStack ☃xxx = ☃.func_70301_a(☃xx);
         if (!☃xxx.func_190926_b()) {
            Item ☃xxxx = ☃xxx.func_77973_b();
            if (Block.func_149634_a(☃xxxx) instanceof BlockShulkerBox) {
               ☃ = ☃xxx;
            } else if (☃xxxx instanceof ItemDye) {
               ☃x = (ItemDye)☃xxxx;
            }
         }
      }

      ItemStack ☃xx = BlockShulkerBox.func_190953_b(☃x.func_195962_g());
      if (☃.func_77942_o()) {
         ☃xx.func_77982_d(☃.func_77978_p().func_74737_b());
      }

      return ☃xx;
   }

   @Override
   public boolean func_194133_a(int var1, int var2) {
      return ☃ * ☃ >= 2;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199589_o;
   }
}
