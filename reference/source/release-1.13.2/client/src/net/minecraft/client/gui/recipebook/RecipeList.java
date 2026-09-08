package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.item.crafting.RecipeItemHelper;

public class RecipeList {
   private final List<IRecipe> field_192713_b = Lists.<IRecipe>newArrayList();
   private final Set<IRecipe> field_194215_b = Sets.<IRecipe>newHashSet();
   private final Set<IRecipe> field_194216_c = Sets.<IRecipe>newHashSet();
   private final Set<IRecipe> field_194217_d = Sets.<IRecipe>newHashSet();
   private boolean field_194218_e = true;

   public boolean func_194209_a() {
      return !this.field_194217_d.isEmpty();
   }

   public void func_194214_a(RecipeBook var1) {
      for(IRecipe ☃ : this.field_192713_b) {
         if (☃.func_193830_f(☃)) {
            this.field_194217_d.add(☃);
         }
      }
   }

   public void func_194210_a(RecipeItemHelper var1, int var2, int var3, RecipeBook var4) {
      for(int ☃ = 0; ☃ < this.field_192713_b.size(); ++☃) {
         IRecipe ☃x = (IRecipe)this.field_192713_b.get(☃);
         boolean ☃xx = ☃x.func_194133_a(☃, ☃) && ☃.func_193830_f(☃x);
         if (☃xx) {
            this.field_194216_c.add(☃x);
         } else {
            this.field_194216_c.remove(☃x);
         }

         if (☃xx && ☃.func_194116_a(☃x, null)) {
            this.field_194215_b.add(☃x);
         } else {
            this.field_194215_b.remove(☃x);
         }
      }
   }

   public boolean func_194213_a(IRecipe var1) {
      return this.field_194215_b.contains(☃);
   }

   public boolean func_192708_c() {
      return !this.field_194215_b.isEmpty();
   }

   public boolean func_194212_c() {
      return !this.field_194216_c.isEmpty();
   }

   public List<IRecipe> func_192711_b() {
      return this.field_192713_b;
   }

   public List<IRecipe> func_194208_a(boolean var1) {
      List<IRecipe> ☃ = Lists.<IRecipe>newArrayList();
      Set<IRecipe> ☃x = ☃ ? this.field_194215_b : this.field_194216_c;

      for(IRecipe ☃xx : this.field_192713_b) {
         if (☃x.contains(☃xx)) {
            ☃.add(☃xx);
         }
      }

      return ☃;
   }

   public List<IRecipe> func_194207_b(boolean var1) {
      List<IRecipe> ☃ = Lists.<IRecipe>newArrayList();

      for(IRecipe ☃x : this.field_192713_b) {
         if (this.field_194216_c.contains(☃x) && this.field_194215_b.contains(☃x) == ☃) {
            ☃.add(☃x);
         }
      }

      return ☃;
   }

   public void func_192709_a(IRecipe var1) {
      this.field_192713_b.add(☃);
      if (this.field_194218_e) {
         ItemStack ☃ = ((IRecipe)this.field_192713_b.get(0)).func_77571_b();
         ItemStack ☃x = ☃.func_77571_b();
         this.field_194218_e = ItemStack.func_179545_c(☃, ☃x) && ItemStack.func_77970_a(☃, ☃x);
      }
   }

   public boolean func_194211_e() {
      return this.field_194218_e;
   }
}
