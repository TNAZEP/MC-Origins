package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.MathHelper;

public class GhostRecipe {
   private IRecipe field_192687_a;
   private final List<GhostRecipe.GhostIngredient> field_192688_b = Lists.<GhostRecipe.GhostIngredient>newArrayList();
   private float field_194190_c;

   public void func_192682_a() {
      this.field_192687_a = null;
      this.field_192688_b.clear();
      this.field_194190_c = 0.0F;
   }

   public void func_194187_a(Ingredient var1, int var2, int var3) {
      this.field_192688_b.add(new GhostRecipe.GhostIngredient(☃, ☃, ☃));
   }

   public GhostRecipe.GhostIngredient func_192681_a(int var1) {
      return (GhostRecipe.GhostIngredient)this.field_192688_b.get(☃);
   }

   public int func_192684_b() {
      return this.field_192688_b.size();
   }

   @Nullable
   public IRecipe func_192686_c() {
      return this.field_192687_a;
   }

   public void func_192685_a(IRecipe var1) {
      this.field_192687_a = ☃;
   }

   public void func_194188_a(Minecraft var1, int var2, int var3, boolean var4, float var5) {
      if (!GuiScreen.func_146271_m()) {
         this.field_194190_c += ☃;
      }

      RenderHelper.func_74520_c();
      GlStateManager.func_179140_f();

      for(int ☃ = 0; ☃ < this.field_192688_b.size(); ++☃) {
         GhostRecipe.GhostIngredient ☃x = (GhostRecipe.GhostIngredient)this.field_192688_b.get(☃);
         int ☃xx = ☃x.func_193713_b() + ☃;
         int ☃xxx = ☃x.func_193712_c() + ☃;
         if (☃ == 0 && ☃) {
            Gui.func_73734_a(☃xx - 4, ☃xxx - 4, ☃xx + 20, ☃xxx + 20, 822018048);
         } else {
            Gui.func_73734_a(☃xx, ☃xxx, ☃xx + 16, ☃xxx + 16, 822018048);
         }

         ItemStack ☃x = ☃x.func_194184_c();
         ItemRenderer ☃xx = ☃.func_175599_af();
         ☃xx.func_184391_a(☃.field_71439_g, ☃x, ☃xx, ☃xxx);
         GlStateManager.func_179143_c(516);
         Gui.func_73734_a(☃xx, ☃xxx, ☃xx + 16, ☃xxx + 16, 822083583);
         GlStateManager.func_179143_c(515);
         if (☃ == 0) {
            ☃xx.func_175030_a(☃.field_71466_p, ☃x, ☃xx, ☃xxx);
         }

         GlStateManager.func_179145_e();
      }

      RenderHelper.func_74518_a();
   }

   public class GhostIngredient {
      private final Ingredient field_194186_b;
      private final int field_192678_b;
      private final int field_192679_c;

      public GhostIngredient(Ingredient var2, int var3, int var4) {
         this.field_194186_b = ☃;
         this.field_192678_b = ☃;
         this.field_192679_c = ☃;
      }

      public int func_193713_b() {
         return this.field_192678_b;
      }

      public int func_193712_c() {
         return this.field_192679_c;
      }

      public ItemStack func_194184_c() {
         ItemStack[] ☃ = this.field_194186_b.func_193365_a();
         return ☃[MathHelper.func_76141_d(GhostRecipe.this.field_194190_c / 30.0F) % ☃.length];
      }
   }
}
