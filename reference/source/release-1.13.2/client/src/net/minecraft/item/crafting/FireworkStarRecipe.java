package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class FireworkStarRecipe extends IRecipeHidden {
   private static final Ingredient field_196212_a = Ingredient.func_199804_a(
      Items.field_151059_bz,
      Items.field_151008_G,
      Items.field_151074_bl,
      Items.field_196182_dv,
      Items.field_196183_dw,
      Items.field_196185_dy,
      Items.field_196184_dx,
      Items.field_196151_dA,
      Items.field_196186_dz
   );
   private static final Ingredient field_196213_b = Ingredient.func_199804_a(Items.field_151045_i);
   private static final Ingredient field_196214_c = Ingredient.func_199804_a(Items.field_151114_aO);
   private static final Map<Item, ItemFireworkRocket.Shape> field_196215_d = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      var0.put(Items.field_151059_bz, ItemFireworkRocket.Shape.LARGE_BALL);
      var0.put(Items.field_151008_G, ItemFireworkRocket.Shape.BURST);
      var0.put(Items.field_151074_bl, ItemFireworkRocket.Shape.STAR);
      var0.put(Items.field_196182_dv, ItemFireworkRocket.Shape.CREEPER);
      var0.put(Items.field_196183_dw, ItemFireworkRocket.Shape.CREEPER);
      var0.put(Items.field_196185_dy, ItemFireworkRocket.Shape.CREEPER);
      var0.put(Items.field_196184_dx, ItemFireworkRocket.Shape.CREEPER);
      var0.put(Items.field_196151_dA, ItemFireworkRocket.Shape.CREEPER);
      var0.put(Items.field_196186_dz, ItemFireworkRocket.Shape.CREEPER);
   });
   private static final Ingredient field_196216_e = Ingredient.func_199804_a(Items.field_151016_H);

   public FireworkStarRecipe(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         boolean ☃ = false;
         boolean ☃x = false;
         boolean ☃xx = false;
         boolean ☃xxx = false;
         boolean ☃xxxx = false;

         for(int ☃xxxxx = 0; ☃xxxxx < ☃.func_70302_i_(); ++☃xxxxx) {
            ItemStack ☃xxxxxx = ☃.func_70301_a(☃xxxxx);
            if (!☃xxxxxx.func_190926_b()) {
               if (field_196212_a.test(☃xxxxxx)) {
                  if (☃xx) {
                     return false;
                  }

                  ☃xx = true;
               } else if (field_196214_c.test(☃xxxxxx)) {
                  if (☃xxxx) {
                     return false;
                  }

                  ☃xxxx = true;
               } else if (field_196213_b.test(☃xxxxxx)) {
                  if (☃xxx) {
                     return false;
                  }

                  ☃xxx = true;
               } else if (field_196216_e.test(☃xxxxxx)) {
                  if (☃) {
                     return false;
                  }

                  ☃ = true;
               } else {
                  if (!(☃xxxxxx.func_77973_b() instanceof ItemDye)) {
                     return false;
                  }

                  ☃x = true;
               }
            }
         }

         return ☃ && ☃x;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      ItemStack ☃ = new ItemStack(Items.field_196153_dF);
      NBTTagCompound ☃x = ☃.func_190925_c("Explosion");
      ItemFireworkRocket.Shape ☃xx = ItemFireworkRocket.Shape.SMALL_BALL;
      List<Integer> ☃xxx = Lists.newArrayList();

      for(int ☃xxxx = 0; ☃xxxx < ☃.func_70302_i_(); ++☃xxxx) {
         ItemStack ☃xxxxx = ☃.func_70301_a(☃xxxx);
         if (!☃xxxxx.func_190926_b()) {
            if (field_196212_a.test(☃xxxxx)) {
               ☃xx = (ItemFireworkRocket.Shape)field_196215_d.get(☃xxxxx.func_77973_b());
            } else if (field_196214_c.test(☃xxxxx)) {
               ☃x.func_74757_a("Flicker", true);
            } else if (field_196213_b.test(☃xxxxx)) {
               ☃x.func_74757_a("Trail", true);
            } else if (☃xxxxx.func_77973_b() instanceof ItemDye) {
               ☃xxx.add(((ItemDye)☃xxxxx.func_77973_b()).func_195962_g().func_196060_f());
            }
         }
      }

      ☃x.func_197646_b("Colors", ☃xxx);
      ☃x.func_74774_a("Type", (byte)☃xx.func_196071_a());
      return ☃;
   }

   @Override
   public boolean func_194133_a(int var1, int var2) {
      return ☃ * ☃ >= 2;
   }

   @Override
   public ItemStack func_77571_b() {
      return new ItemStack(Items.field_196153_dF);
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199582_h;
   }
}
