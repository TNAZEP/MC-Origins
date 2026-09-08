package net.minecraft.client.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.item.crafting.RecipeManager;

public class RecipeBookClient extends RecipeBook {
   private final RecipeManager field_199645_e;
   private final Map<RecipeBookCategories, List<RecipeList>> field_197931_e = Maps.newHashMap();
   private final List<RecipeList> field_197932_f = Lists.<RecipeList>newArrayList();

   public RecipeBookClient(RecipeManager var1) {
      this.field_199645_e = ☃;
   }

   public void func_199644_c() {
      this.field_197932_f.clear();
      this.field_197931_e.clear();
      Table<RecipeBookCategories, String, RecipeList> ☃ = HashBasedTable.create();

      for(IRecipe ☃x : this.field_199645_e.func_199510_b()) {
         if (!☃x.func_192399_d()) {
            RecipeBookCategories ☃xxx = func_202887_g(☃x);
            String ☃xxxx = ☃x.func_193358_e();
            RecipeList ☃xx;
            if (☃xxxx.isEmpty()) {
               ☃xx = this.func_202889_b(☃xxx);
            } else {
               ☃xx = ☃.get(☃xxx, ☃xxxx);
               if (☃xx == null) {
                  ☃xx = this.func_202889_b(☃xxx);
                  ☃.put(☃xxx, ☃xxxx, ☃xx);
               }
            }

            ☃xx.func_192709_a(☃x);
         }
      }
   }

   private RecipeList func_202889_b(RecipeBookCategories var1) {
      RecipeList ☃ = new RecipeList();
      this.field_197932_f.add(☃);
      ((List)this.field_197931_e.computeIfAbsent(☃, var0 -> Lists.newArrayList())).add(☃);
      if (☃ != RecipeBookCategories.FURNACE_BLOCKS && ☃ != RecipeBookCategories.FURNACE_FOOD && ☃ != RecipeBookCategories.FURNACE_MISC) {
         ((List)this.field_197931_e.computeIfAbsent(RecipeBookCategories.SEARCH, var0 -> Lists.newArrayList())).add(☃);
      } else {
         ((List)this.field_197931_e.computeIfAbsent(RecipeBookCategories.FURNACE_SEARCH, var0 -> Lists.newArrayList())).add(☃);
      }

      return ☃;
   }

   private static RecipeBookCategories func_202887_g(IRecipe var0) {
      if (☃ instanceof FurnaceRecipe) {
         if (☃.func_77571_b().func_77973_b() instanceof ItemFood) {
            return RecipeBookCategories.FURNACE_FOOD;
         } else {
            return ☃.func_77571_b().func_77973_b() instanceof ItemBlock ? RecipeBookCategories.FURNACE_BLOCKS : RecipeBookCategories.FURNACE_MISC;
         }
      } else {
         ItemStack ☃ = ☃.func_77571_b();
         ItemGroup ☃x = ☃.func_77973_b().func_77640_w();
         if (☃x == ItemGroup.field_78030_b) {
            return RecipeBookCategories.BUILDING_BLOCKS;
         } else if (☃x == ItemGroup.field_78040_i || ☃x == ItemGroup.field_78037_j) {
            return RecipeBookCategories.EQUIPMENT;
         } else {
            return ☃x == ItemGroup.field_78028_d ? RecipeBookCategories.REDSTONE : RecipeBookCategories.MISC;
         }
      }
   }

   public static List<RecipeBookCategories> func_202888_a(Container var0) {
      if (☃ instanceof ContainerWorkbench || ☃ instanceof ContainerPlayer) {
         return Lists.newArrayList(
            RecipeBookCategories.SEARCH,
            RecipeBookCategories.EQUIPMENT,
            RecipeBookCategories.BUILDING_BLOCKS,
            RecipeBookCategories.MISC,
            RecipeBookCategories.REDSTONE
         );
      } else {
         return ☃ instanceof ContainerFurnace
            ? Lists.newArrayList(
               RecipeBookCategories.FURNACE_SEARCH, RecipeBookCategories.FURNACE_FOOD, RecipeBookCategories.FURNACE_BLOCKS, RecipeBookCategories.FURNACE_MISC
            )
            : Lists.newArrayList();
      }
   }

   public List<RecipeList> func_199642_d() {
      return this.field_197932_f;
   }

   public List<RecipeList> func_202891_a(RecipeBookCategories var1) {
      return (List<RecipeList>)this.field_197931_e.getOrDefault(☃, Collections.emptyList());
   }
}
