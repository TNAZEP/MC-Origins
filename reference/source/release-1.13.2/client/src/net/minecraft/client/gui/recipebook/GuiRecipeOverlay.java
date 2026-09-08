package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerRecipeBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiRecipeOverlay extends Gui implements IGuiEventListener {
   private static final ResourceLocation field_191847_a = new ResourceLocation("textures/gui/recipe_book.png");
   private final List<GuiRecipeOverlay.Button> field_193972_f = Lists.<GuiRecipeOverlay.Button>newArrayList();
   private boolean field_191850_h;
   private int field_191851_i;
   private int field_191852_j;
   private Minecraft field_191853_k;
   private RecipeList field_191848_f;
   private IRecipe field_193973_l;
   private float field_193974_m;
   private boolean field_201704_n;

   public void func_201703_a(Minecraft var1, RecipeList var2, int var3, int var4, int var5, int var6, float var7) {
      this.field_191853_k = ☃;
      this.field_191848_f = ☃;
      if (☃.field_71439_g.field_71070_bA instanceof ContainerFurnace) {
         this.field_201704_n = true;
      }

      boolean ☃ = ☃.field_71439_g.func_199507_B().func_203432_a((ContainerRecipeBook)☃.field_71439_g.field_71070_bA);
      List<IRecipe> ☃x = ☃.func_194207_b(true);
      List<IRecipe> ☃xx = ☃ ? Collections.emptyList() : ☃.func_194207_b(false);
      int ☃xxx = ☃x.size();
      int ☃xxxx = ☃xxx + ☃xx.size();
      int ☃xxxxx = ☃xxxx <= 16 ? 4 : 5;
      int ☃xxxxxx = (int)Math.ceil((double)((float)☃xxxx / (float)☃xxxxx));
      this.field_191851_i = ☃;
      this.field_191852_j = ☃;
      int ☃xxxxxxx = 25;
      float ☃xxxxxxxx = (float)(this.field_191851_i + Math.min(☃xxxx, ☃xxxxx) * 25);
      float ☃xxxxxxxxx = (float)(☃ + 50);
      if (☃xxxxxxxx > ☃xxxxxxxxx) {
         this.field_191851_i = (int)((float)this.field_191851_i - ☃ * (float)((int)((☃xxxxxxxx - ☃xxxxxxxxx) / ☃)));
      }

      float ☃ = (float)(this.field_191852_j + ☃xxxxxx * 25);
      float ☃x = (float)(☃ + 50);
      if (☃ > ☃x) {
         this.field_191852_j = (int)((float)this.field_191852_j - ☃ * (float)MathHelper.func_76123_f((☃ - ☃x) / ☃));
      }

      float ☃ = (float)this.field_191852_j;
      float ☃x = (float)(☃ - 100);
      if (☃ < ☃x) {
         this.field_191852_j = (int)((float)this.field_191852_j - ☃ * (float)MathHelper.func_76123_f((☃ - ☃x) / ☃));
      }

      this.field_191850_h = true;
      this.field_193972_f.clear();

      for(int ☃ = 0; ☃ < ☃xxxx; ++☃) {
         boolean ☃x = ☃ < ☃xxx;
         IRecipe ☃xx = ☃x ? (IRecipe)☃x.get(☃) : (IRecipe)☃xx.get(☃ - ☃xxx);
         int ☃xxx = this.field_191851_i + 4 + 25 * (☃ % ☃xxxxx);
         int ☃xxxx = this.field_191852_j + 5 + 25 * (☃ / ☃xxxxx);
         if (this.field_201704_n) {
            this.field_193972_f.add(new GuiRecipeOverlay.FurnaceButton(☃xxx, ☃xxxx, ☃xx, ☃x));
         } else {
            this.field_193972_f.add(new GuiRecipeOverlay.Button(☃xxx, ☃xxxx, ☃xx, ☃x));
         }
      }

      this.field_193973_l = null;
   }

   public RecipeList func_193971_a() {
      return this.field_191848_f;
   }

   public IRecipe func_193967_b() {
      return this.field_193973_l;
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (☃ != 0) {
         return false;
      } else {
         for(GuiRecipeOverlay.Button ☃ : this.field_193972_f) {
            if (☃.mouseClicked(☃, ☃, ☃)) {
               this.field_193973_l = ☃.field_193924_p;
               return true;
            }
         }

         return false;
      }
   }

   public void func_191842_a(int var1, int var2, float var3) {
      if (this.field_191850_h) {
         this.field_193974_m += ☃;
         RenderHelper.func_74520_c();
         GlStateManager.func_179147_l();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_191853_k.func_110434_K().func_110577_a(field_191847_a);
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.0F, 170.0F);
         int ☃ = this.field_193972_f.size() <= 16 ? 4 : 5;
         int ☃x = Math.min(this.field_193972_f.size(), ☃);
         int ☃xx = MathHelper.func_76123_f((float)this.field_193972_f.size() / (float)☃);
         int ☃xxx = 24;
         int ☃xxxx = 4;
         int ☃xxxxx = 82;
         int ☃xxxxxx = 208;
         this.func_191846_c(☃x, ☃xx, 24, 4, 82, 208);
         GlStateManager.func_179084_k();
         RenderHelper.func_74518_a();

         for(GuiRecipeOverlay.Button ☃xxxxxxx : this.field_193972_f) {
            ☃xxxxxxx.func_194828_a(☃, ☃, ☃);
         }

         GlStateManager.func_179121_F();
      }
   }

   private void func_191846_c(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.func_73729_b(this.field_191851_i, this.field_191852_j, ☃, ☃, ☃, ☃);
      this.func_73729_b(this.field_191851_i + ☃ * 2 + ☃ * ☃, this.field_191852_j, ☃ + ☃ + ☃, ☃, ☃, ☃);
      this.func_73729_b(this.field_191851_i, this.field_191852_j + ☃ * 2 + ☃ * ☃, ☃, ☃ + ☃ + ☃, ☃, ☃);
      this.func_73729_b(this.field_191851_i + ☃ * 2 + ☃ * ☃, this.field_191852_j + ☃ * 2 + ☃ * ☃, ☃ + ☃ + ☃, ☃ + ☃ + ☃, ☃, ☃);

      for(int ☃ = 0; ☃ < ☃; ++☃) {
         this.func_73729_b(this.field_191851_i + ☃ + ☃ * ☃, this.field_191852_j, ☃ + ☃, ☃, ☃, ☃);
         this.func_73729_b(this.field_191851_i + ☃ + (☃ + 1) * ☃, this.field_191852_j, ☃ + ☃, ☃, ☃, ☃);

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            if (☃ == 0) {
               this.func_73729_b(this.field_191851_i, this.field_191852_j + ☃ + ☃x * ☃, ☃, ☃ + ☃, ☃, ☃);
               this.func_73729_b(this.field_191851_i, this.field_191852_j + ☃ + (☃x + 1) * ☃, ☃, ☃ + ☃, ☃, ☃);
            }

            this.func_73729_b(this.field_191851_i + ☃ + ☃ * ☃, this.field_191852_j + ☃ + ☃x * ☃, ☃ + ☃, ☃ + ☃, ☃, ☃);
            this.func_73729_b(this.field_191851_i + ☃ + (☃ + 1) * ☃, this.field_191852_j + ☃ + ☃x * ☃, ☃ + ☃, ☃ + ☃, ☃, ☃);
            this.func_73729_b(this.field_191851_i + ☃ + ☃ * ☃, this.field_191852_j + ☃ + (☃x + 1) * ☃, ☃ + ☃, ☃ + ☃, ☃, ☃);
            this.func_73729_b(this.field_191851_i + ☃ + (☃ + 1) * ☃ - 1, this.field_191852_j + ☃ + (☃x + 1) * ☃ - 1, ☃ + ☃, ☃ + ☃, ☃ + 1, ☃ + 1);
            if (☃ == ☃ - 1) {
               this.func_73729_b(this.field_191851_i + ☃ * 2 + ☃ * ☃, this.field_191852_j + ☃ + ☃x * ☃, ☃ + ☃ + ☃, ☃ + ☃, ☃, ☃);
               this.func_73729_b(this.field_191851_i + ☃ * 2 + ☃ * ☃, this.field_191852_j + ☃ + (☃x + 1) * ☃, ☃ + ☃ + ☃, ☃ + ☃, ☃, ☃);
            }
         }

         this.func_73729_b(this.field_191851_i + ☃ + ☃ * ☃, this.field_191852_j + ☃ * 2 + ☃ * ☃, ☃ + ☃, ☃ + ☃ + ☃, ☃, ☃);
         this.func_73729_b(this.field_191851_i + ☃ + (☃ + 1) * ☃, this.field_191852_j + ☃ * 2 + ☃ * ☃, ☃ + ☃, ☃ + ☃ + ☃, ☃, ☃);
      }
   }

   public void func_192999_a(boolean var1) {
      this.field_191850_h = ☃;
   }

   public boolean func_191839_a() {
      return this.field_191850_h;
   }

   class Button extends GuiButton implements IRecipePlacer<Ingredient> {
      private final IRecipe field_193924_p;
      private final boolean field_193925_q;
      protected final List<GuiRecipeOverlay.Button.Child> field_201506_o = Lists.<GuiRecipeOverlay.Button.Child>newArrayList();

      public Button(int var2, int var3, IRecipe var4, boolean var5) {
         super(0, ☃, ☃, "");
         this.field_146120_f = 24;
         this.field_146121_g = 24;
         this.field_193924_p = ☃;
         this.field_193925_q = ☃;
         this.func_201505_a(☃);
      }

      protected void func_201505_a(IRecipe var1) {
         this.func_201501_a(3, 3, -1, ☃, ☃.func_192400_c().iterator(), 0);
      }

      @Override
      public void func_201500_a(Iterator<Ingredient> var1, int var2, int var3, int var4, int var5) {
         ItemStack[] ☃ = ((Ingredient)☃.next()).func_193365_a();
         if (☃.length != 0) {
            this.field_201506_o.add(new GuiRecipeOverlay.Button.Child(3 + ☃ * 7, 3 + ☃ * 7, ☃));
         }
      }

      @Override
      public void func_194828_a(int var1, int var2, float var3) {
         RenderHelper.func_74520_c();
         GlStateManager.func_179141_d();
         GuiRecipeOverlay.this.field_191853_k.func_110434_K().func_110577_a(GuiRecipeOverlay.field_191847_a);
         this.field_146123_n = ☃ >= this.field_146128_h
            && ☃ >= this.field_146129_i
            && ☃ < this.field_146128_h + this.field_146120_f
            && ☃ < this.field_146129_i + this.field_146121_g;
         int ☃ = 152;
         if (!this.field_193925_q) {
            ☃ += 26;
         }

         int ☃ = GuiRecipeOverlay.this.field_201704_n ? 130 : 78;
         if (this.field_146123_n) {
            ☃ += 26;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, ☃, ☃, this.field_146120_f, this.field_146121_g);

         for(GuiRecipeOverlay.Button.Child ☃ : this.field_201506_o) {
            GlStateManager.func_179094_E();
            float ☃x = 0.42F;
            int ☃xx = (int)((float)(this.field_146128_h + ☃.field_201706_b) / 0.42F - 3.0F);
            int ☃xxx = (int)((float)(this.field_146129_i + ☃.field_201707_c) / 0.42F - 3.0F);
            GlStateManager.func_179152_a(0.42F, 0.42F, 1.0F);
            GlStateManager.func_179145_e();
            GuiRecipeOverlay.this.field_191853_k
               .func_175599_af()
               .func_180450_b(☃.field_201705_a[MathHelper.func_76141_d(GuiRecipeOverlay.this.field_193974_m / 30.0F) % ☃.field_201705_a.length], ☃xx, ☃xxx);
            GlStateManager.func_179140_f();
            GlStateManager.func_179121_F();
         }

         GlStateManager.func_179118_c();
         RenderHelper.func_74518_a();
      }

      public class Child {
         public ItemStack[] field_201705_a;
         public int field_201706_b;
         public int field_201707_c;

         public Child(int var2, int var3, ItemStack[] var4) {
            this.field_201706_b = ☃;
            this.field_201707_c = ☃;
            this.field_201705_a = ☃;
         }
      }
   }

   class FurnaceButton extends GuiRecipeOverlay.Button {
      public FurnaceButton(int var2, int var3, IRecipe var4, boolean var5) {
         super(☃, ☃, ☃, ☃);
      }

      @Override
      protected void func_201505_a(IRecipe var1) {
         ItemStack[] ☃ = ☃.func_192400_c().get(0).func_193365_a();
         this.field_201506_o.add(new GuiRecipeOverlay.Button.Child(10, 10, ☃));
      }
   }
}
