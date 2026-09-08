package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeBookCloning extends IRecipeHidden {
   public RecipeBookCloning(ResourceLocation var1) {
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
               if (☃xxx.func_77973_b() == Items.field_151164_bB) {
                  if (!☃x.func_190926_b()) {
                     return false;
                  }

                  ☃x = ☃xxx;
               } else {
                  if (☃xxx.func_77973_b() != Items.field_151099_bA) {
                     return false;
                  }

                  ++☃;
               }
            }
         }

         return !☃x.func_190926_b() && ☃x.func_77942_o() && ☃ > 0;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      int ☃ = 0;
      ItemStack ☃x = ItemStack.field_190927_a;

      for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
         ItemStack ☃xxx = ☃.func_70301_a(☃xx);
         if (!☃xxx.func_190926_b()) {
            if (☃xxx.func_77973_b() == Items.field_151164_bB) {
               if (!☃x.func_190926_b()) {
                  return ItemStack.field_190927_a;
               }

               ☃x = ☃xxx;
            } else {
               if (☃xxx.func_77973_b() != Items.field_151099_bA) {
                  return ItemStack.field_190927_a;
               }

               ++☃;
            }
         }
      }

      if (!☃x.func_190926_b() && ☃x.func_77942_o() && ☃ >= 1 && ItemWrittenBook.func_179230_h(☃x) < 2) {
         ItemStack ☃xx = new ItemStack(Items.field_151164_bB, ☃);
         NBTTagCompound ☃xxx = ☃x.func_77978_p().func_74737_b();
         ☃xxx.func_74768_a("generation", ItemWrittenBook.func_179230_h(☃x) + 1);
         ☃xx.func_77982_d(☃xxx);
         return ☃xx;
      } else {
         return ItemStack.field_190927_a;
      }
   }

   @Override
   public NonNullList<ItemStack> func_179532_b(IInventory var1) {
      NonNullList<ItemStack> ☃ = NonNullList.func_191197_a(☃.func_70302_i_(), ItemStack.field_190927_a);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         ItemStack ☃xx = ☃.func_70301_a(☃x);
         if (☃xx.func_77973_b().func_77634_r()) {
            ☃.set(☃x, new ItemStack(☃xx.func_77973_b().func_77668_q()));
         } else if (☃xx.func_77973_b() instanceof ItemWrittenBook) {
            ItemStack ☃xx = ☃xx.func_77946_l();
            ☃xx.func_190920_e(1);
            ☃.set(☃x, ☃xx);
            break;
         }
      }

      return ☃;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199578_d;
   }

   @Override
   public boolean func_194133_a(int var1, int var2) {
      return ☃ >= 3 && ☃ >= 3;
   }
}
