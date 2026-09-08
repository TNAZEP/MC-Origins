package net.minecraft.client.gui.recipebook;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.inventory.ContainerRecipeBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class GuiButtonRecipeTab extends GuiButtonToggle {
   private final RecipeBookCategories field_193921_u;
   private float field_193922_v;

   public GuiButtonRecipeTab(int var1, RecipeBookCategories var2) {
      super(☃, 0, 0, 35, 27, false);
      this.field_193921_u = ☃;
      this.func_191751_a(153, 2, 35, 0, GuiRecipeBook.field_191894_a);
   }

   public void func_193918_a(Minecraft var1) {
      RecipeBookClient ☃ = ☃.field_71439_g.func_199507_B();
      List<RecipeList> ☃x = ☃.func_202891_a(this.field_193921_u);
      if (☃.field_71439_g.field_71070_bA instanceof ContainerRecipeBook) {
         for(RecipeList ☃xx : ☃x) {
            for(IRecipe ☃xxx : ☃xx.func_194208_a(☃.func_203432_a((ContainerRecipeBook)☃.field_71439_g.field_71070_bA))) {
               if (☃.func_194076_e(☃xxx)) {
                  this.field_193922_v = 15.0F;
                  return;
               }
            }
         }
      }
   }

   @Override
   public void func_194828_a(int var1, int var2, float var3) {
      if (this.field_146125_m) {
         if (this.field_193922_v > 0.0F) {
            float ☃ = 1.0F + 0.1F * (float)Math.sin((double)(this.field_193922_v / 15.0F * (float) Math.PI));
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)(this.field_146128_h + 8), (float)(this.field_146129_i + 12), 0.0F);
            GlStateManager.func_179152_a(1.0F, ☃, 1.0F);
            GlStateManager.func_179109_b((float)(-(this.field_146128_h + 8)), (float)(-(this.field_146129_i + 12)), 0.0F);
         }

         this.field_146123_n = ☃ >= this.field_146128_h
            && ☃ >= this.field_146129_i
            && ☃ < this.field_146128_h + this.field_146120_f
            && ☃ < this.field_146129_i + this.field_146121_g;
         Minecraft ☃ = Minecraft.func_71410_x();
         ☃.func_110434_K().func_110577_a(this.field_191760_o);
         GlStateManager.func_179097_i();
         int ☃x = this.field_191756_q;
         int ☃xx = this.field_191757_r;
         if (this.field_191755_p) {
            ☃x += this.field_191758_s;
         }

         if (this.field_146123_n) {
            ☃xx += this.field_191759_t;
         }

         int ☃ = this.field_146128_h;
         if (this.field_191755_p) {
            ☃ -= 2;
         }

         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(☃, this.field_146129_i, ☃x, ☃xx, this.field_146120_f, this.field_146121_g);
         GlStateManager.func_179126_j();
         RenderHelper.func_74520_c();
         GlStateManager.func_179140_f();
         this.func_193920_a(☃.func_175599_af());
         GlStateManager.func_179145_e();
         RenderHelper.func_74518_a();
         if (this.field_193922_v > 0.0F) {
            GlStateManager.func_179121_F();
            this.field_193922_v -= ☃;
         }
      }
   }

   private void func_193920_a(ItemRenderer var1) {
      List<ItemStack> ☃ = this.field_193921_u.func_202903_a();
      int ☃x = this.field_191755_p ? -2 : 0;
      if (☃.size() == 1) {
         ☃.func_180450_b((ItemStack)☃.get(0), this.field_146128_h + 9 + ☃x, this.field_146129_i + 5);
      } else if (☃.size() == 2) {
         ☃.func_180450_b((ItemStack)☃.get(0), this.field_146128_h + 3 + ☃x, this.field_146129_i + 5);
         ☃.func_180450_b((ItemStack)☃.get(1), this.field_146128_h + 14 + ☃x, this.field_146129_i + 5);
      }
   }

   public RecipeBookCategories func_201503_d() {
      return this.field_193921_u;
   }

   public boolean func_199500_a(RecipeBookClient var1) {
      List<RecipeList> ☃ = ☃.func_202891_a(this.field_193921_u);
      this.field_146125_m = false;
      if (☃ != null) {
         for(RecipeList ☃x : ☃) {
            if (☃x.func_194209_a() && ☃x.func_194212_c()) {
               this.field_146125_m = true;
               break;
            }
         }
      }

      return this.field_146125_m;
   }
}
