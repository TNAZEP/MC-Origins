package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.inventory.ContainerRecipeBook;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.util.ResourceLocation;

public class GuiRecipeBook extends Gui implements IGuiEventListener, IRecipeUpdateListener, IRecipePlacer<Ingredient> {
   protected static final ResourceLocation field_191894_a = new ResourceLocation("textures/gui/recipe_book.png");
   private int field_191903_n;
   private int field_191904_o;
   private int field_191905_p;
   protected final GhostRecipe field_191915_z = new GhostRecipe();
   private final List<GuiButtonRecipeTab> field_193018_j = Lists.<GuiButtonRecipeTab>newArrayList();
   private GuiButtonRecipeTab field_191913_x;
   protected GuiButtonToggle field_193960_m;
   protected ContainerRecipeBook field_201522_g;
   protected Minecraft field_191888_F;
   private GuiTextField field_193962_q;
   private String field_193963_r = "";
   protected RecipeBookClient field_193964_s;
   protected final RecipeBookPage field_193022_s = new RecipeBookPage();
   protected final RecipeItemHelper field_193965_u = new RecipeItemHelper();
   private int field_193966_v;
   private boolean field_199738_u;

   public void func_201520_a(int var1, int var2, Minecraft var3, boolean var4, ContainerRecipeBook var5) {
      this.field_191888_F = ☃;
      this.field_191904_o = ☃;
      this.field_191905_p = ☃;
      this.field_201522_g = ☃;
      ☃.field_71439_g.field_71070_bA = ☃;
      this.field_193964_s = ☃.field_71439_g.func_199507_B();
      this.field_193966_v = ☃.field_71439_g.field_71071_by.func_194015_p();
      if (this.func_191878_b()) {
         this.func_201518_a(☃);
      }

      ☃.field_195559_v.func_197967_a(true);
   }

   public void func_201518_a(boolean var1) {
      this.field_191903_n = ☃ ? 0 : 86;
      int ☃ = (this.field_191904_o - 147) / 2 - this.field_191903_n;
      int ☃x = (this.field_191905_p - 166) / 2;
      this.field_193965_u.func_194119_a();
      this.field_191888_F.field_71439_g.field_71071_by.func_201571_a(this.field_193965_u);
      this.field_201522_g.func_201771_a(this.field_193965_u);
      String ☃xx = this.field_193962_q != null ? this.field_193962_q.func_146179_b() : "";
      this.field_193962_q = new GuiTextField(0, this.field_191888_F.field_71466_p, ☃ + 25, ☃x + 14, 80, this.field_191888_F.field_71466_p.field_78288_b + 5);
      this.field_193962_q.func_146203_f(50);
      this.field_193962_q.func_146185_a(false);
      this.field_193962_q.func_146189_e(true);
      this.field_193962_q.func_146193_g(16777215);
      this.field_193962_q.func_146180_a(☃xx);
      this.field_193022_s.func_194194_a(this.field_191888_F, ☃, ☃x);
      this.field_193022_s.func_193732_a(this);
      this.field_193960_m = new GuiButtonToggle(0, ☃ + 110, ☃x + 12, 26, 16, this.field_193964_s.func_203432_a(this.field_201522_g));
      this.func_205702_a();
      this.field_193018_j.clear();

      for(RecipeBookCategories ☃xxx : RecipeBookClient.func_202888_a(this.field_201522_g)) {
         this.field_193018_j.add(new GuiButtonRecipeTab(0, ☃xxx));
      }

      if (this.field_191913_x != null) {
         this.field_191913_x = (GuiButtonRecipeTab)this.field_193018_j
            .stream()
            .filter(var1x -> var1x.func_201503_d().equals(this.field_191913_x.func_201503_d()))
            .findFirst()
            .orElse(null);
      }

      if (this.field_191913_x == null) {
         this.field_191913_x = (GuiButtonRecipeTab)this.field_193018_j.get(0);
      }

      this.field_191913_x.func_191753_b(true);
      this.func_193003_g(false);
      this.func_193949_f();
   }

   protected void func_205702_a() {
      this.field_193960_m.func_191751_a(152, 41, 28, 18, field_191894_a);
   }

   public void func_191871_c() {
      this.field_193962_q = null;
      this.field_191913_x = null;
      this.field_191888_F.field_195559_v.func_197967_a(false);
   }

   public int func_193011_a(boolean var1, int var2, int var3) {
      int ☃;
      if (this.func_191878_b() && !☃) {
         ☃ = 177 + (☃ - ☃ - 200) / 2;
      } else {
         ☃ = (☃ - ☃) / 2;
      }

      return ☃;
   }

   public void func_191866_a() {
      this.func_193006_a(!this.func_191878_b());
   }

   public boolean func_191878_b() {
      return this.field_193964_s.func_192812_b();
   }

   protected void func_193006_a(boolean var1) {
      this.field_193964_s.func_192813_a(☃);
      if (!☃) {
         this.field_193022_s.func_194200_c();
      }

      this.func_193956_j();
   }

   public void func_191874_a(@Nullable Slot var1) {
      if (☃ != null && ☃.field_75222_d < this.field_201522_g.func_203721_h()) {
         this.field_191915_z.func_192682_a();
         if (this.func_191878_b()) {
            this.func_193942_g();
         }
      }
   }

   private void func_193003_g(boolean var1) {
      List<RecipeList> ☃ = this.field_193964_s.func_202891_a(this.field_191913_x.func_201503_d());
      ☃.forEach(
         var1x -> var1x.func_194210_a(this.field_193965_u, this.field_201522_g.func_201770_g(), this.field_201522_g.func_201772_h(), this.field_193964_s)
      );
      List<RecipeList> ☃x = Lists.<RecipeList>newArrayList(☃);
      ☃x.removeIf(var0 -> !var0.func_194209_a());
      ☃x.removeIf(var0 -> !var0.func_194212_c());
      String ☃xx = this.field_193962_q.func_146179_b();
      if (!☃xx.isEmpty()) {
         ObjectSet<RecipeList> ☃xxx = new ObjectLinkedOpenHashSet<>(
            this.field_191888_F.func_193987_a(SearchTreeManager.field_194012_b).func_194038_a(☃xx.toLowerCase(Locale.ROOT))
         );
         ☃x.removeIf(var1x -> !☃.contains(var1x));
      }

      if (this.field_193964_s.func_203432_a(this.field_201522_g)) {
         ☃x.removeIf(var0 -> !var0.func_192708_c());
      }

      this.field_193022_s.func_194192_a(☃x, ☃);
   }

   private void func_193949_f() {
      int ☃ = (this.field_191904_o - 147) / 2 - this.field_191903_n - 30;
      int ☃x = (this.field_191905_p - 166) / 2 + 3;
      int ☃xx = 27;
      int ☃xxx = 0;

      for(GuiButtonRecipeTab ☃xxxx : this.field_193018_j) {
         RecipeBookCategories ☃xxxxx = ☃xxxx.func_201503_d();
         if (☃xxxxx == RecipeBookCategories.SEARCH || ☃xxxxx == RecipeBookCategories.FURNACE_SEARCH) {
            ☃xxxx.field_146125_m = true;
            ☃xxxx.func_191752_c(☃, ☃x + 27 * ☃xxx++);
         } else if (☃xxxx.func_199500_a(this.field_193964_s)) {
            ☃xxxx.func_191752_c(☃, ☃x + 27 * ☃xxx++);
            ☃xxxx.func_193918_a(this.field_191888_F);
         }
      }
   }

   public void func_193957_d() {
      if (this.func_191878_b()) {
         if (this.field_193966_v != this.field_191888_F.field_71439_g.field_71071_by.func_194015_p()) {
            this.func_193942_g();
            this.field_193966_v = this.field_191888_F.field_71439_g.field_71071_by.func_194015_p();
         }
      }
   }

   private void func_193942_g() {
      this.field_193965_u.func_194119_a();
      this.field_191888_F.field_71439_g.field_71071_by.func_201571_a(this.field_193965_u);
      this.field_201522_g.func_201771_a(this.field_193965_u);
      this.func_193003_g(false);
   }

   public void func_191861_a(int var1, int var2, float var3) {
      if (this.func_191878_b()) {
         RenderHelper.func_74520_c();
         GlStateManager.func_179140_f();
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.0F, 100.0F);
         this.field_191888_F.func_110434_K().func_110577_a(field_191894_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         int ☃ = (this.field_191904_o - 147) / 2 - this.field_191903_n;
         int ☃x = (this.field_191905_p - 166) / 2;
         this.func_73729_b(☃, ☃x, 1, 1, 147, 166);
         this.field_193962_q.func_195608_a(☃, ☃, ☃);
         RenderHelper.func_74518_a();

         for(GuiButtonRecipeTab ☃xx : this.field_193018_j) {
            ☃xx.func_194828_a(☃, ☃, ☃);
         }

         this.field_193960_m.func_194828_a(☃, ☃, ☃);
         this.field_193022_s.func_194191_a(☃, ☃x, ☃, ☃, ☃);
         GlStateManager.func_179121_F();
      }
   }

   public void func_191876_c(int var1, int var2, int var3, int var4) {
      if (this.func_191878_b()) {
         this.field_193022_s.func_193721_a(☃, ☃);
         if (this.field_193960_m.func_146115_a()) {
            String ☃ = this.func_205703_f();
            if (this.field_191888_F.field_71462_r != null) {
               this.field_191888_F.field_71462_r.func_146279_a(☃, ☃, ☃);
            }
         }

         this.func_193015_d(☃, ☃, ☃, ☃);
      }
   }

   protected String func_205703_f() {
      return I18n.func_135052_a(this.field_193960_m.func_191754_c() ? "gui.recipebook.toggleRecipes.craftable" : "gui.recipebook.toggleRecipes.all");
   }

   private void func_193015_d(int var1, int var2, int var3, int var4) {
      ItemStack ☃ = null;

      for(int ☃x = 0; ☃x < this.field_191915_z.func_192684_b(); ++☃x) {
         GhostRecipe.GhostIngredient ☃xx = this.field_191915_z.func_192681_a(☃x);
         int ☃xxx = ☃xx.func_193713_b() + ☃;
         int ☃xxxx = ☃xx.func_193712_c() + ☃;
         if (☃ >= ☃xxx && ☃ >= ☃xxxx && ☃ < ☃xxx + 16 && ☃ < ☃xxxx + 16) {
            ☃ = ☃xx.func_194184_c();
         }
      }

      if (☃ != null && this.field_191888_F.field_71462_r != null) {
         this.field_191888_F.field_71462_r.func_146283_a(this.field_191888_F.field_71462_r.func_191927_a(☃), ☃, ☃);
      }
   }

   public void func_191864_a(int var1, int var2, boolean var3, float var4) {
      this.field_191915_z.func_194188_a(this.field_191888_F, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.func_191878_b() && !this.field_191888_F.field_71439_g.func_175149_v()) {
         if (this.field_193022_s.func_198955_a(☃, ☃, ☃, (this.field_191904_o - 147) / 2 - this.field_191903_n, (this.field_191905_p - 166) / 2, 147, 166)) {
            IRecipe ☃ = this.field_193022_s.func_194193_a();
            RecipeList ☃x = this.field_193022_s.func_194199_b();
            if (☃ != null && ☃x != null) {
               if (!☃x.func_194213_a(☃) && this.field_191915_z.func_192686_c() == ☃) {
                  return false;
               }

               this.field_191915_z.func_192682_a();
               this.field_191888_F.field_71442_b.func_203413_a(this.field_191888_F.field_71439_g.field_71070_bA.field_75152_c, ☃, GuiScreen.func_146272_n());
               if (!this.func_191880_f()) {
                  this.func_193006_a(false);
               }
            }

            return true;
         } else if (this.field_193962_q.mouseClicked(☃, ☃, ☃)) {
            return true;
         } else if (this.field_193960_m.mouseClicked(☃, ☃, ☃)) {
            boolean ☃ = this.func_201521_f();
            this.field_193960_m.func_191753_b(☃);
            this.func_193956_j();
            this.func_193003_g(false);
            return true;
         } else {
            for(GuiButtonRecipeTab ☃ : this.field_193018_j) {
               if (☃.mouseClicked(☃, ☃, ☃)) {
                  if (this.field_191913_x != ☃) {
                     this.field_191913_x.func_191753_b(false);
                     this.field_191913_x = ☃;
                     this.field_191913_x.func_191753_b(true);
                     this.func_193003_g(true);
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean func_201521_f() {
      boolean ☃ = !this.field_193964_s.func_192815_c();
      this.field_193964_s.func_192810_b(☃);
      return ☃;
   }

   public boolean func_195604_a(double var1, double var3, int var5, int var6, int var7, int var8, int var9) {
      if (!this.func_191878_b()) {
         return true;
      } else {
         boolean ☃ = ☃ < (double)☃ || ☃ < (double)☃ || ☃ >= (double)(☃ + ☃) || ☃ >= (double)(☃ + ☃);
         boolean ☃x = (double)(☃ - 147) < ☃ && ☃ < (double)☃ && (double)☃ < ☃ && ☃ < (double)(☃ + ☃);
         return ☃ && !☃x && !this.field_191913_x.func_146115_a();
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      this.field_199738_u = false;
      if (!this.func_191878_b() || this.field_191888_F.field_71439_g.func_175149_v()) {
         return false;
      } else if (☃ == 256 && !this.func_191880_f()) {
         this.func_193006_a(false);
         return true;
      } else if (this.field_193962_q.keyPressed(☃, ☃, ☃)) {
         this.func_195603_h();
         return true;
      } else if (this.field_191888_F.field_71474_y.field_74310_D.func_197976_a(☃, ☃) && !this.field_193962_q.func_146206_l()) {
         this.field_199738_u = true;
         this.field_193962_q.func_146195_b(true);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyReleased(int var1, int var2, int var3) {
      this.field_199738_u = false;
      return IGuiEventListener.super.keyReleased(☃, ☃, ☃);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (this.field_199738_u) {
         return false;
      } else if (!this.func_191878_b() || this.field_191888_F.field_71439_g.func_175149_v()) {
         return false;
      } else if (this.field_193962_q.charTyped(☃, ☃)) {
         this.func_195603_h();
         return true;
      } else {
         return IGuiEventListener.super.charTyped(☃, ☃);
      }
   }

   private void func_195603_h() {
      String ☃ = this.field_193962_q.func_146179_b().toLowerCase(Locale.ROOT);
      this.func_193716_a(☃);
      if (!☃.equals(this.field_193963_r)) {
         this.func_193003_g(false);
         this.field_193963_r = ☃;
      }
   }

   private void func_193716_a(String var1) {
      if ("excitedze".equals(☃)) {
         LanguageManager ☃ = this.field_191888_F.func_135016_M();
         Language ☃x = ☃.func_191960_a("en_pt");
         if (☃.func_135041_c().compareTo(☃x) == 0) {
            return;
         }

         ☃.func_135045_a(☃x);
         this.field_191888_F.field_71474_y.field_74363_ab = ☃x.func_135034_a();
         this.field_191888_F.func_110436_a();
         this.field_191888_F.field_71466_p.func_78275_b(☃.func_135044_b());
         this.field_191888_F.field_71474_y.func_74303_b();
      }
   }

   private boolean func_191880_f() {
      return this.field_191903_n == 86;
   }

   public void func_193948_e() {
      this.func_193949_f();
      if (this.func_191878_b()) {
         this.func_193003_g(false);
      }
   }

   @Override
   public void func_193001_a(List<IRecipe> var1) {
      for(IRecipe ☃ : ☃) {
         this.field_191888_F.field_71439_g.func_193103_a(☃);
      }
   }

   public void func_193951_a(IRecipe var1, List<Slot> var2) {
      ItemStack ☃ = ☃.func_77571_b();
      this.field_191915_z.func_192685_a(☃);
      this.field_191915_z.func_194187_a(Ingredient.func_193369_a(☃), ((Slot)☃.get(0)).field_75223_e, ((Slot)☃.get(0)).field_75221_f);
      this.func_201501_a(
         this.field_201522_g.func_201770_g(), this.field_201522_g.func_201772_h(), this.field_201522_g.func_201767_f(), ☃, ☃.func_192400_c().iterator(), 0
      );
   }

   @Override
   public void func_201500_a(Iterator<Ingredient> var1, int var2, int var3, int var4, int var5) {
      Ingredient ☃ = (Ingredient)☃.next();
      if (!☃.func_203189_d()) {
         Slot ☃x = (Slot)this.field_201522_g.field_75151_b.get(☃);
         this.field_191915_z.func_194187_a(☃, ☃x.field_75223_e, ☃x.field_75221_f);
      }
   }

   protected void func_193956_j() {
      if (this.field_191888_F.func_147114_u() != null) {
         this.field_191888_F
            .func_147114_u()
            .func_147297_a(
               new CPacketRecipeInfo(
                  this.field_193964_s.func_192812_b(),
                  this.field_193964_s.func_192815_c(),
                  this.field_193964_s.func_202883_c(),
                  this.field_193964_s.func_202884_d()
               )
            );
      }
   }
}
