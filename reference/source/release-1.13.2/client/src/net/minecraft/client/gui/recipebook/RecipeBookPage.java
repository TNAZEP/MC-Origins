package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;

public class RecipeBookPage {
   private final List<GuiButtonRecipe> field_193743_h = Lists.<GuiButtonRecipe>newArrayListWithCapacity(20);
   private GuiButtonRecipe field_194201_b;
   private final GuiRecipeOverlay field_194202_c = new GuiRecipeOverlay();
   private Minecraft field_193754_s;
   private final List<IRecipeUpdateListener> field_193757_v = Lists.<IRecipeUpdateListener>newArrayList();
   private List<RecipeList> field_194203_f;
   private GuiButtonToggle field_193740_e;
   private GuiButtonToggle field_193741_f;
   private int field_193737_b;
   private int field_193738_c;
   private RecipeBook field_194204_k;
   private IRecipe field_194205_l;
   private RecipeList field_194206_m;

   public RecipeBookPage() {
      for(int ☃ = 0; ☃ < 20; ++☃) {
         this.field_193743_h.add(new GuiButtonRecipe());
      }
   }

   public void func_194194_a(Minecraft var1, int var2, int var3) {
      this.field_193754_s = ☃;
      this.field_194204_k = ☃.field_71439_g.func_199507_B();

      for(int ☃ = 0; ☃ < this.field_193743_h.size(); ++☃) {
         ((GuiButtonRecipe)this.field_193743_h.get(☃)).func_191770_c(☃ + 11 + 25 * (☃ % 5), ☃ + 31 + 25 * (☃ / 5));
      }

      this.field_193740_e = new GuiButtonToggle(0, ☃ + 93, ☃ + 137, 12, 17, false);
      this.field_193740_e.func_191751_a(1, 208, 13, 18, GuiRecipeBook.field_191894_a);
      this.field_193741_f = new GuiButtonToggle(0, ☃ + 38, ☃ + 137, 12, 17, true);
      this.field_193741_f.func_191751_a(1, 208, 13, 18, GuiRecipeBook.field_191894_a);
   }

   public void func_193732_a(GuiRecipeBook var1) {
      this.field_193757_v.remove(☃);
      this.field_193757_v.add(☃);
   }

   public void func_194192_a(List<RecipeList> var1, boolean var2) {
      this.field_194203_f = ☃;
      this.field_193737_b = (int)Math.ceil((double)☃.size() / 20.0);
      if (this.field_193737_b <= this.field_193738_c || ☃) {
         this.field_193738_c = 0;
      }

      this.func_194198_d();
   }

   private void func_194198_d() {
      int ☃ = 20 * this.field_193738_c;

      for(int ☃x = 0; ☃x < this.field_193743_h.size(); ++☃x) {
         GuiButtonRecipe ☃xx = (GuiButtonRecipe)this.field_193743_h.get(☃x);
         if (☃ + ☃x < this.field_194203_f.size()) {
            RecipeList ☃xxx = (RecipeList)this.field_194203_f.get(☃ + ☃x);
            ☃xx.func_203400_a(☃xxx, this);
            ☃xx.field_146125_m = true;
         } else {
            ☃xx.field_146125_m = false;
         }
      }

      this.func_194197_e();
   }

   private void func_194197_e() {
      this.field_193740_e.field_146125_m = this.field_193737_b > 1 && this.field_193738_c < this.field_193737_b - 1;
      this.field_193741_f.field_146125_m = this.field_193737_b > 1 && this.field_193738_c > 0;
   }

   public void func_194191_a(int var1, int var2, int var3, int var4, float var5) {
      if (this.field_193737_b > 1) {
         String ☃ = this.field_193738_c + 1 + "/" + this.field_193737_b;
         int ☃x = this.field_193754_s.field_71466_p.func_78256_a(☃);
         this.field_193754_s.field_71466_p.func_211126_b(☃, (float)(☃ - ☃x / 2 + 73), (float)(☃ + 141), -1);
      }

      RenderHelper.func_74518_a();
      this.field_194201_b = null;

      for(GuiButtonRecipe ☃ : this.field_193743_h) {
         ☃.func_194828_a(☃, ☃, ☃);
         if (☃.field_146125_m && ☃.func_146115_a()) {
            this.field_194201_b = ☃;
         }
      }

      this.field_193741_f.func_194828_a(☃, ☃, ☃);
      this.field_193740_e.func_194828_a(☃, ☃, ☃);
      this.field_194202_c.func_191842_a(☃, ☃, ☃);
   }

   public void func_193721_a(int var1, int var2) {
      if (this.field_193754_s.field_71462_r != null && this.field_194201_b != null && !this.field_194202_c.func_191839_a()) {
         this.field_193754_s.field_71462_r.func_146283_a(this.field_194201_b.func_191772_a(this.field_193754_s.field_71462_r), ☃, ☃);
      }
   }

   @Nullable
   public IRecipe func_194193_a() {
      return this.field_194205_l;
   }

   @Nullable
   public RecipeList func_194199_b() {
      return this.field_194206_m;
   }

   public void func_194200_c() {
      this.field_194202_c.func_192999_a(false);
   }

   public boolean func_198955_a(double var1, double var3, int var5, int var6, int var7, int var8, int var9) {
      this.field_194205_l = null;
      this.field_194206_m = null;
      if (this.field_194202_c.func_191839_a()) {
         if (this.field_194202_c.mouseClicked(☃, ☃, ☃)) {
            this.field_194205_l = this.field_194202_c.func_193967_b();
            this.field_194206_m = this.field_194202_c.func_193971_a();
         } else {
            this.field_194202_c.func_192999_a(false);
         }

         return true;
      } else if (this.field_193740_e.mouseClicked(☃, ☃, ☃)) {
         ++this.field_193738_c;
         this.func_194198_d();
         return true;
      } else if (this.field_193741_f.mouseClicked(☃, ☃, ☃)) {
         --this.field_193738_c;
         this.func_194198_d();
         return true;
      } else {
         for(GuiButtonRecipe ☃ : this.field_193743_h) {
            if (☃.mouseClicked(☃, ☃, ☃)) {
               if (☃ == 0) {
                  this.field_194205_l = ☃.func_193760_e();
                  this.field_194206_m = ☃.func_191771_c();
               } else if (☃ == 1 && !this.field_194202_c.func_191839_a() && !☃.func_193929_d()) {
                  this.field_194202_c
                     .func_201703_a(
                        this.field_193754_s, ☃.func_191771_c(), ☃.field_146128_h, ☃.field_146129_i, ☃ + ☃ / 2, ☃ + 13 + ☃ / 2, (float)☃.func_146117_b()
                     );
               }

               return true;
            }
         }

         return false;
      }
   }

   public void func_194195_a(List<IRecipe> var1) {
      for(IRecipeUpdateListener ☃ : this.field_193757_v) {
         ☃.func_193001_a(☃);
      }
   }

   public Minecraft func_203411_d() {
      return this.field_193754_s;
   }

   public RecipeBook func_203412_e() {
      return this.field_194204_k;
   }
}
