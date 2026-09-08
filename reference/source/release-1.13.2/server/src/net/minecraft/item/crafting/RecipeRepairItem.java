package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeRepairItem extends IRecipeHidden {
   public RecipeRepairItem(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         List<ItemStack> ☃ = Lists.<ItemStack>newArrayList();

         for(int ☃x = 0; ☃x < ☃.func_70302_i_(); ++☃x) {
            ItemStack ☃xx = ☃.func_70301_a(☃x);
            if (!☃xx.func_190926_b()) {
               ☃.add(☃xx);
               if (☃.size() > 1) {
                  ItemStack ☃xxx = (ItemStack)☃.get(0);
                  if (☃xx.func_77973_b() != ☃xxx.func_77973_b() || ☃xxx.func_190916_E() != 1 || ☃xx.func_190916_E() != 1 || !☃xxx.func_77973_b().func_77645_m()
                     )
                   {
                     return false;
                  }
               }
            }
         }

         return ☃.size() == 2;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      List<ItemStack> ☃ = Lists.<ItemStack>newArrayList();

      for(int ☃x = 0; ☃x < ☃.func_70302_i_(); ++☃x) {
         ItemStack ☃xx = ☃.func_70301_a(☃x);
         if (!☃xx.func_190926_b()) {
            ☃.add(☃xx);
            if (☃.size() > 1) {
               ItemStack ☃xxx = (ItemStack)☃.get(0);
               if (☃xx.func_77973_b() != ☃xxx.func_77973_b() || ☃xxx.func_190916_E() != 1 || ☃xx.func_190916_E() != 1 || !☃xxx.func_77973_b().func_77645_m()) {
                  return ItemStack.field_190927_a;
               }
            }
         }
      }

      if (☃.size() == 2) {
         ItemStack ☃x = (ItemStack)☃.get(0);
         ItemStack ☃xx = (ItemStack)☃.get(1);
         if (☃x.func_77973_b() == ☃xx.func_77973_b() && ☃x.func_190916_E() == 1 && ☃xx.func_190916_E() == 1 && ☃x.func_77973_b().func_77645_m()) {
            Item ☃xxx = ☃x.func_77973_b();
            int ☃xxxx = ☃xxx.func_77612_l() - ☃x.func_77952_i();
            int ☃xxxxx = ☃xxx.func_77612_l() - ☃xx.func_77952_i();
            int ☃xxxxxx = ☃xxxx + ☃xxxxx + ☃xxx.func_77612_l() * 5 / 100;
            int ☃xxxxxxx = ☃xxx.func_77612_l() - ☃xxxxxx;
            if (☃xxxxxxx < 0) {
               ☃xxxxxxx = 0;
            }

            ItemStack ☃xxx = new ItemStack(☃x.func_77973_b());
            ☃xxx.func_196085_b(☃xxxxxxx);
            return ☃xxx;
         }
      }

      return ItemStack.field_190927_a;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199584_j;
   }
}
