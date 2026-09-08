package net.minecraft.client.gui.recipebook;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;

public class GuiFurnaceRecipeBook extends GuiRecipeBook {
   private Iterator<Item> field_201525_l;
   private Set<Item> field_201526_m;
   private Slot field_201527_n;
   private Item field_201528_o;
   private float field_201524_p;

   @Override
   protected boolean func_201521_f() {
      boolean ☃ = !this.field_193964_s.func_202884_d();
      this.field_193964_s.func_202882_d(☃);
      return ☃;
   }

   @Override
   public boolean func_191878_b() {
      return this.field_193964_s.func_202883_c();
   }

   @Override
   protected void func_193006_a(boolean var1) {
      this.field_193964_s.func_202881_c(☃);
      if (!☃) {
         this.field_193022_s.func_194200_c();
      }

      this.func_193956_j();
   }

   @Override
   protected void func_205702_a() {
      this.field_193960_m.func_191751_a(152, 182, 28, 18, field_191894_a);
   }

   @Override
   protected String func_205703_f() {
      return I18n.func_135052_a(this.field_193960_m.func_191754_c() ? "gui.recipebook.toggleRecipes.smeltable" : "gui.recipebook.toggleRecipes.all");
   }

   @Override
   public void func_191874_a(@Nullable Slot var1) {
      super.func_191874_a(☃);
      if (☃ != null && ☃.field_75222_d < this.field_201522_g.func_203721_h()) {
         this.field_201527_n = null;
      }
   }

   @Override
   public void func_193951_a(IRecipe var1, List<Slot> var2) {
      ItemStack ☃ = ☃.func_77571_b();
      this.field_191915_z.func_192685_a(☃);
      this.field_191915_z.func_194187_a(Ingredient.func_193369_a(☃), ((Slot)☃.get(2)).field_75223_e, ((Slot)☃.get(2)).field_75221_f);
      NonNullList<Ingredient> ☃x = ☃.func_192400_c();
      this.field_201527_n = (Slot)☃.get(1);
      if (this.field_201526_m == null) {
         this.field_201526_m = TileEntityFurnace.func_201564_p().keySet();
      }

      this.field_201525_l = this.field_201526_m.iterator();
      this.field_201528_o = null;
      Iterator<Ingredient> ☃ = ☃x.iterator();

      for(int ☃x = 0; ☃x < 2; ++☃x) {
         if (!☃.hasNext()) {
            return;
         }

         Ingredient ☃xx = (Ingredient)☃.next();
         if (!☃xx.func_203189_d()) {
            Slot ☃xxx = (Slot)☃.get(☃x);
            this.field_191915_z.func_194187_a(☃xx, ☃xxx.field_75223_e, ☃xxx.field_75221_f);
         }
      }
   }

   @Override
   public void func_191864_a(int var1, int var2, boolean var3, float var4) {
      super.func_191864_a(☃, ☃, ☃, ☃);
      if (this.field_201527_n != null) {
         if (!GuiScreen.func_146271_m()) {
            this.field_201524_p += ☃;
         }

         RenderHelper.func_74520_c();
         GlStateManager.func_179140_f();
         int ☃ = this.field_201527_n.field_75223_e + ☃;
         int ☃x = this.field_201527_n.field_75221_f + ☃;
         Gui.func_73734_a(☃, ☃x, ☃ + 16, ☃x + 16, 822018048);
         this.field_191888_F.func_175599_af().func_184391_a(this.field_191888_F.field_71439_g, this.func_201523_i().func_190903_i(), ☃, ☃x);
         GlStateManager.func_179143_c(516);
         Gui.func_73734_a(☃, ☃x, ☃ + 16, ☃x + 16, 822083583);
         GlStateManager.func_179143_c(515);
         GlStateManager.func_179145_e();
         RenderHelper.func_74518_a();
      }
   }

   private Item func_201523_i() {
      if (this.field_201528_o == null || this.field_201524_p > 30.0F) {
         this.field_201524_p = 0.0F;
         if (this.field_201525_l == null || !this.field_201525_l.hasNext()) {
            if (this.field_201526_m == null) {
               this.field_201526_m = TileEntityFurnace.func_201564_p().keySet();
            }

            this.field_201525_l = this.field_201526_m.iterator();
         }

         this.field_201528_o = (Item)this.field_201525_l.next();
      }

      return this.field_201528_o;
   }
}
