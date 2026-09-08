package net.minecraft.client.gui.toasts;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;

public class RecipeToast implements IToast {
   private final List<IRecipe> field_202906_c = Lists.<IRecipe>newArrayList();
   private long field_193667_d;
   private boolean field_193668_e;

   public RecipeToast(IRecipe var1) {
      this.field_202906_c.add(☃);
   }

   @Override
   public IToast.Visibility func_193653_a(GuiToast var1, long var2) {
      if (this.field_193668_e) {
         this.field_193667_d = ☃;
         this.field_193668_e = false;
      }

      if (this.field_202906_c.isEmpty()) {
         return IToast.Visibility.HIDE;
      } else {
         ☃.func_192989_b().func_110434_K().func_110577_a(field_193654_a);
         GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
         ☃.func_73729_b(0, 0, 0, 32, 160, 32);
         ☃.func_192989_b().field_71466_p.func_211126_b(I18n.func_135052_a("recipe.toast.title"), 30.0F, 7.0F, -11534256);
         ☃.func_192989_b().field_71466_p.func_211126_b(I18n.func_135052_a("recipe.toast.description"), 30.0F, 18.0F, -16777216);
         RenderHelper.func_74520_c();
         IRecipe ☃x = (IRecipe)this.field_202906_c.get((int)(☃ / (5000L / (long)this.field_202906_c.size()) % (long)this.field_202906_c.size()));
         ItemStack ☃;
         if (☃x instanceof FurnaceRecipe) {
            ☃ = new ItemStack(Blocks.field_150460_al);
         } else {
            ☃ = new ItemStack(Blocks.field_150462_ai);
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.6F, 0.6F, 1.0F);
         ☃.func_192989_b().func_175599_af().func_184391_a(null, ☃, 3, 3);
         GlStateManager.func_179121_F();
         ☃.func_192989_b().func_175599_af().func_184391_a(null, ☃x.func_77571_b(), 8, 8);
         return ☃ - this.field_193667_d >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
      }
   }

   public void func_202905_a(IRecipe var1) {
      if (this.field_202906_c.add(☃)) {
         this.field_193668_e = true;
      }
   }

   public static void func_193665_a(GuiToast var0, IRecipe var1) {
      RecipeToast ☃ = ☃.func_192990_a(RecipeToast.class, field_193655_b);
      if (☃ == null) {
         ☃.func_192988_a(new RecipeToast(☃));
      } else {
         ☃.func_202905_a(☃);
      }
   }
}
