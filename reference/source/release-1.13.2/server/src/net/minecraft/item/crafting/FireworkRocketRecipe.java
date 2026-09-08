package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FireworkRocketRecipe extends IRecipeHidden {
   private static final Ingredient field_196209_a = Ingredient.func_199804_a(Items.field_151121_aF);
   private static final Ingredient field_196210_b = Ingredient.func_199804_a(Items.field_151016_H);
   private static final Ingredient field_196211_c = Ingredient.func_199804_a(Items.field_196153_dF);

   public FireworkRocketRecipe(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         boolean ☃ = false;
         int ☃x = 0;

         for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (!☃xxx.func_190926_b()) {
               if (field_196209_a.test(☃xxx)) {
                  if (☃) {
                     return false;
                  }

                  ☃ = true;
               } else if (field_196210_b.test(☃xxx)) {
                  if (++☃x > 3) {
                     return false;
                  }
               } else if (!field_196211_c.test(☃xxx)) {
                  return false;
               }
            }
         }

         return ☃ && ☃x >= 1;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      ItemStack ☃ = new ItemStack(Items.field_196152_dE, 3);
      NBTTagCompound ☃x = ☃.func_190925_c("Fireworks");
      NBTTagList ☃xx = new NBTTagList();
      int ☃xxx = 0;

      for(int ☃xxxx = 0; ☃xxxx < ☃.func_70302_i_(); ++☃xxxx) {
         ItemStack ☃xxxxx = ☃.func_70301_a(☃xxxx);
         if (!☃xxxxx.func_190926_b()) {
            if (field_196210_b.test(☃xxxxx)) {
               ++☃xxx;
            } else if (field_196211_c.test(☃xxxxx)) {
               NBTTagCompound ☃xxxxxx = ☃xxxxx.func_179543_a("Explosion");
               if (☃xxxxxx != null) {
                  ☃xx.add((INBTBase)☃xxxxxx);
               }
            }
         }
      }

      ☃x.func_74774_a("Flight", (byte)☃xxx);
      if (!☃xx.isEmpty()) {
         ☃x.func_74782_a("Explosions", ☃xx);
      }

      return ☃;
   }

   @Override
   public ItemStack func_77571_b() {
      return new ItemStack(Items.field_196152_dE);
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199581_g;
   }
}
