package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmorDyeable;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipesArmorDyes extends IRecipeHidden {
   public RecipesArmorDyes(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         ItemStack ☃ = ItemStack.field_190927_a;
         List<ItemStack> ☃x = Lists.<ItemStack>newArrayList();

         for(int ☃xx = 0; ☃xx < ☃.func_70302_i_(); ++☃xx) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (!☃xxx.func_190926_b()) {
               if (☃xxx.func_77973_b() instanceof ItemArmorDyeable) {
                  if (!☃.func_190926_b()) {
                     return false;
                  }

                  ☃ = ☃xxx;
               } else {
                  if (!(☃xxx.func_77973_b() instanceof ItemDye)) {
                     return false;
                  }

                  ☃x.add(☃xxx);
               }
            }
         }

         return !☃.func_190926_b() && !☃x.isEmpty();
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      ItemStack ☃ = ItemStack.field_190927_a;
      int[] ☃x = new int[3];
      int ☃xx = 0;
      int ☃xxx = 0;
      ItemArmorDyeable ☃xxxx = null;

      for(int ☃xxxxx = 0; ☃xxxxx < ☃.func_70302_i_(); ++☃xxxxx) {
         ItemStack ☃xxxxxx = ☃.func_70301_a(☃xxxxx);
         if (!☃xxxxxx.func_190926_b()) {
            Item ☃xxxxxxx = ☃xxxxxx.func_77973_b();
            if (☃xxxxxxx instanceof ItemArmorDyeable) {
               ☃xxxx = (ItemArmorDyeable)☃xxxxxxx;
               if (!☃.func_190926_b()) {
                  return ItemStack.field_190927_a;
               }

               ☃ = ☃xxxxxx.func_77946_l();
               ☃.func_190920_e(1);
               if (☃xxxx.func_200883_f_(☃xxxxxx)) {
                  int ☃xxxxxxxx = ☃xxxx.func_200886_f(☃);
                  float ☃xxxxxxxxx = (float)(☃xxxxxxxx >> 16 & 0xFF) / 255.0F;
                  float ☃xxxxxxxxxx = (float)(☃xxxxxxxx >> 8 & 0xFF) / 255.0F;
                  float ☃xxxxxxxxxxx = (float)(☃xxxxxxxx & 0xFF) / 255.0F;
                  ☃xx = (int)((float)☃xx + Math.max(☃xxxxxxxxx, Math.max(☃xxxxxxxxxx, ☃xxxxxxxxxxx)) * 255.0F);
                  ☃x[0] = (int)((float)☃x[0] + ☃xxxxxxxxx * 255.0F);
                  ☃x[1] = (int)((float)☃x[1] + ☃xxxxxxxxxx * 255.0F);
                  ☃x[2] = (int)((float)☃x[2] + ☃xxxxxxxxxxx * 255.0F);
                  ++☃xxx;
               }
            } else {
               if (!(☃xxxxxxx instanceof ItemDye)) {
                  return ItemStack.field_190927_a;
               }

               float[] ☃xxxxxxx = ((ItemDye)☃xxxxxxx).func_195962_g().func_193349_f();
               int ☃xxxxxxxx = (int)(☃xxxxxxx[0] * 255.0F);
               int ☃xxxxxxxxx = (int)(☃xxxxxxx[1] * 255.0F);
               int ☃xxxxxxxxxx = (int)(☃xxxxxxx[2] * 255.0F);
               ☃xx += Math.max(☃xxxxxxxx, Math.max(☃xxxxxxxxx, ☃xxxxxxxxxx));
               ☃x[0] += ☃xxxxxxxx;
               ☃x[1] += ☃xxxxxxxxx;
               ☃x[2] += ☃xxxxxxxxxx;
               ++☃xxx;
            }
         }
      }

      if (☃xxxx == null) {
         return ItemStack.field_190927_a;
      } else {
         int ☃xxxxx = ☃x[0] / ☃xxx;
         int ☃xxxxxx = ☃x[1] / ☃xxx;
         int ☃xxxxxxx = ☃x[2] / ☃xxx;
         float ☃xxxxxxxx = (float)☃xx / (float)☃xxx;
         float ☃xxxxxxxxx = (float)Math.max(☃xxxxx, Math.max(☃xxxxxx, ☃xxxxxxx));
         ☃xxxxx = (int)((float)☃xxxxx * ☃xxxxxxxx / ☃xxxxxxxxx);
         ☃xxxxxx = (int)((float)☃xxxxxx * ☃xxxxxxxx / ☃xxxxxxxxx);
         ☃xxxxxxx = (int)((float)☃xxxxxxx * ☃xxxxxxxx / ☃xxxxxxxxx);
         int var25 = (☃xxxxx << 8) + ☃xxxxxx;
         var25 = (var25 << 8) + ☃xxxxxxx;
         ☃xxxx.func_200885_a(☃, var25);
         return ☃;
      }
   }

   @Override
   public boolean func_194133_a(int var1, int var2) {
      return ☃ * ☃ >= 2;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199577_c;
   }
}
