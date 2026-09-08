package net.minecraft.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BannerDuplicateRecipe extends IRecipeHidden {
   public BannerDuplicateRecipe(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         EnumDyeColor ☃ = null;
         ItemStack ☃x = null;
         ItemStack ☃xx = null;

         for(int ☃xxx = 0; ☃xxx < ☃.func_70302_i_(); ++☃xxx) {
            ItemStack ☃xxxx = ☃.func_70301_a(☃xxx);
            Item ☃xxxxx = ☃xxxx.func_77973_b();
            if (☃xxxxx instanceof ItemBanner) {
               ItemBanner ☃xxxxxx = (ItemBanner)☃xxxxx;
               if (☃ == null) {
                  ☃ = ☃xxxxxx.func_195948_b();
               } else if (☃ != ☃xxxxxx.func_195948_b()) {
                  return false;
               }

               boolean ☃xxxxxx = TileEntityBanner.func_175113_c(☃xxxx) > 0;
               if (☃xxxxxx) {
                  if (☃x != null) {
                     return false;
                  }

                  ☃x = ☃xxxx;
               } else {
                  if (☃xx != null) {
                     return false;
                  }

                  ☃xx = ☃xxxx;
               }
            }
         }

         return ☃x != null && ☃xx != null;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      for(int ☃ = 0; ☃ < ☃.func_70302_i_(); ++☃) {
         ItemStack ☃x = ☃.func_70301_a(☃);
         if (!☃x.func_190926_b() && TileEntityBanner.func_175113_c(☃x) > 0) {
            ItemStack ☃xx = ☃x.func_77946_l();
            ☃xx.func_190920_e(1);
            return ☃xx;
         }
      }

      return ItemStack.field_190927_a;
   }

   @Override
   public NonNullList<ItemStack> func_179532_b(IInventory var1) {
      NonNullList<ItemStack> ☃ = NonNullList.func_191197_a(☃.func_70302_i_(), ItemStack.field_190927_a);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         ItemStack ☃xx = ☃.func_70301_a(☃x);
         if (!☃xx.func_190926_b()) {
            if (☃xx.func_77973_b().func_77634_r()) {
               ☃.set(☃x, new ItemStack(☃xx.func_77973_b().func_77668_q()));
            } else if (☃xx.func_77942_o() && TileEntityBanner.func_175113_c(☃xx) > 0) {
               ItemStack ☃xxx = ☃xx.func_77946_l();
               ☃xxx.func_190920_e(1);
               ☃.set(☃x, ☃xxx);
            }
         }
      }

      return ☃;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199586_l;
   }
}
