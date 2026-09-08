package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShieldRecipes extends IRecipeHidden {
   public ShieldRecipes(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         ItemStack ☃ = ItemStack.field_190927_a;
         ItemStack ☃x = ItemStack.field_190927_a;

         for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (!☃xxx.func_190926_b()) {
               if (☃xxx.func_77973_b() instanceof ItemBanner) {
                  if (!☃x.func_190926_b()) {
                     return false;
                  }

                  ☃x = ☃xxx;
               } else {
                  if (☃xxx.func_77973_b() != Items.field_185159_cQ) {
                     return false;
                  }

                  if (!☃.func_190926_b()) {
                     return false;
                  }

                  if (☃xxx.func_179543_a("BlockEntityTag") != null) {
                     return false;
                  }

                  ☃ = ☃xxx;
               }
            }
         }

         return !☃.func_190926_b() && !☃x.func_190926_b();
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      ItemStack ☃ = ItemStack.field_190927_a;
      ItemStack ☃x = ItemStack.field_190927_a;

      for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
         ItemStack ☃xxx = ☃.func_70301_a(☃xx);
         if (!☃xxx.func_190926_b()) {
            if (☃xxx.func_77973_b() instanceof ItemBanner) {
               ☃ = ☃xxx;
            } else if (☃xxx.func_77973_b() == Items.field_185159_cQ) {
               ☃x = ☃xxx.func_77946_l();
            }
         }
      }

      if (☃x.func_190926_b()) {
         return ☃x;
      } else {
         NBTTagCompound ☃xx = ☃.func_179543_a("BlockEntityTag");
         NBTTagCompound ☃xxx = ☃xx == null ? new NBTTagCompound() : ☃xx.func_74737_b();
         ☃xxx.func_74768_a("Base", ((ItemBanner)☃.func_77973_b()).func_195948_b().func_196059_a());
         ☃x.func_77983_a("BlockEntityTag", ☃xxx);
         return ☃x;
      }
   }

   @Override
   public boolean func_194133_a(int var1, int var2) {
      return ☃ * ☃ >= 2;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199588_n;
   }
}
