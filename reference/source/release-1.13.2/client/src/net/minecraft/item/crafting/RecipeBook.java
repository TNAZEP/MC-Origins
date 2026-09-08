package net.minecraft.item.crafting;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerRecipeBook;
import net.minecraft.util.ResourceLocation;

public class RecipeBook {
   protected final Set<ResourceLocation> field_194077_a = Sets.<ResourceLocation>newHashSet();
   protected final Set<ResourceLocation> field_194078_b = Sets.<ResourceLocation>newHashSet();
   protected boolean field_192818_b;
   protected boolean field_192819_c;
   protected boolean field_202885_e;
   protected boolean field_202886_f;

   public void func_193824_a(RecipeBook var1) {
      this.field_194077_a.clear();
      this.field_194078_b.clear();
      this.field_194077_a.addAll(☃.field_194077_a);
      this.field_194078_b.addAll(☃.field_194078_b);
   }

   public void func_194073_a(IRecipe var1) {
      if (!☃.func_192399_d()) {
         this.func_209118_a(☃.func_199560_c());
      }
   }

   protected void func_209118_a(ResourceLocation var1) {
      this.field_194077_a.add(☃);
   }

   public boolean func_193830_f(@Nullable IRecipe var1) {
      return ☃ == null ? false : this.field_194077_a.contains(☃.func_199560_c());
   }

   public void func_193831_b(IRecipe var1) {
      this.func_209119_b(☃.func_199560_c());
   }

   protected void func_209119_b(ResourceLocation var1) {
      this.field_194077_a.remove(☃);
      this.field_194078_b.remove(☃);
   }

   public boolean func_194076_e(IRecipe var1) {
      return this.field_194078_b.contains(☃.func_199560_c());
   }

   public void func_194074_f(IRecipe var1) {
      this.field_194078_b.remove(☃.func_199560_c());
   }

   public void func_193825_e(IRecipe var1) {
      this.func_209120_c(☃.func_199560_c());
   }

   protected void func_209120_c(ResourceLocation var1) {
      this.field_194078_b.add(☃);
   }

   public boolean func_192812_b() {
      return this.field_192818_b;
   }

   public void func_192813_a(boolean var1) {
      this.field_192818_b = ☃;
   }

   public boolean func_203432_a(ContainerRecipeBook var1) {
      return ☃ instanceof ContainerFurnace ? this.field_202886_f : this.field_192819_c;
   }

   public boolean func_192815_c() {
      return this.field_192819_c;
   }

   public void func_192810_b(boolean var1) {
      this.field_192819_c = ☃;
   }

   public boolean func_202883_c() {
      return this.field_202885_e;
   }

   public void func_202881_c(boolean var1) {
      this.field_202885_e = ☃;
   }

   public boolean func_202884_d() {
      return this.field_202886_f;
   }

   public void func_202882_d(boolean var1) {
      this.field_202886_f = ☃;
   }
}
