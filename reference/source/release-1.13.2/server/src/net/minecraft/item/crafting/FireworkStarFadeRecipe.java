package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FireworkStarFadeRecipe extends IRecipeHidden {
   private static final Ingredient field_196217_a = Ingredient.func_199804_a(Items.field_196153_dF);

   public FireworkStarFadeRecipe(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         boolean ☃ = false;
         boolean ☃x = false;

         for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (!☃xxx.func_190926_b()) {
               if (☃xxx.func_77973_b() instanceof ItemDye) {
                  ☃ = true;
               } else {
                  if (!field_196217_a.test(☃xxx)) {
                     return false;
                  }

                  if (☃x) {
                     return false;
                  }

                  ☃x = true;
               }
            }
         }

         return ☃x && ☃;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      List<Integer> ☃ = Lists.newArrayList();
      ItemStack ☃x = null;

      for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
         ItemStack ☃xxx = ☃.func_70301_a(☃xx);
         Item ☃xxxx = ☃xxx.func_77973_b();
         if (☃xxxx instanceof ItemDye) {
            ☃.add(((ItemDye)☃xxxx).func_195962_g().func_196060_f());
         } else if (field_196217_a.test(☃xxx)) {
            ☃x = ☃xxx.func_77946_l();
            ☃x.func_190920_e(1);
         }
      }

      if (☃x != null && !☃.isEmpty()) {
         ☃x.func_190925_c("Explosion").func_197646_b("FadeColors", ☃);
         return ☃x;
      } else {
         return ItemStack.field_190927_a;
      }
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199583_i;
   }
}
