package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.recipebook.GuiFurnaceRecipeBook;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerRecipeBook;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiContainer implements IRecipeShownListener {
   private static final ResourceLocation field_147087_u = new ResourceLocation("textures/gui/container/furnace.png");
   private static final ResourceLocation field_201558_x = new ResourceLocation("textures/gui/recipe_button.png");
   private final InventoryPlayer field_175383_v;
   private final IInventory field_147086_v;
   public final GuiFurnaceRecipeBook field_201557_v = new GuiFurnaceRecipeBook();
   private boolean field_201556_A;

   public GuiFurnace(InventoryPlayer var1, IInventory var2) {
      super(new ContainerFurnace(☃, ☃));
      this.field_175383_v = ☃;
      this.field_147086_v = ☃;
   }

   @Override
   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_201556_A = this.field_146294_l < 379;
      this.field_201557_v
         .func_201520_a(this.field_146294_l, this.field_146295_m, this.field_146297_k, this.field_201556_A, (ContainerRecipeBook)this.field_147002_h);
      this.field_147003_i = this.field_201557_v.func_193011_a(this.field_201556_A, this.field_146294_l, this.field_146999_f);
      this.func_189646_b(
         new GuiButtonImage(10, this.field_147003_i + 20, this.field_146295_m / 2 - 49, 20, 18, 0, 0, 19, field_201558_x) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiFurnace.this.field_201557_v.func_201518_a(GuiFurnace.this.field_201556_A);
               GuiFurnace.this.field_201557_v.func_191866_a();
               GuiFurnace.this.field_147003_i = GuiFurnace.this.field_201557_v
                  .func_193011_a(GuiFurnace.this.field_201556_A, GuiFurnace.this.field_146294_l, GuiFurnace.this.field_146999_f);
               this.func_191746_c(GuiFurnace.this.field_147003_i + 20, GuiFurnace.this.field_146295_m / 2 - 49);
            }
         }
      );
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      this.field_201557_v.func_193957_d();
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      if (this.field_201557_v.func_191878_b() && this.field_201556_A) {
         this.func_146976_a(☃, ☃, ☃);
         this.field_201557_v.func_191861_a(☃, ☃, ☃);
      } else {
         this.field_201557_v.func_191861_a(☃, ☃, ☃);
         super.func_73863_a(☃, ☃, ☃);
         this.field_201557_v.func_191864_a(this.field_147003_i, this.field_147009_r, true, ☃);
      }

      this.func_191948_b(☃, ☃);
      this.field_201557_v.func_191876_c(this.field_147003_i, this.field_147009_r, ☃, ☃);
   }

   @Override
   protected void func_146979_b(int var1, int var2) {
      String ☃ = this.field_147086_v.func_145748_c_().func_150254_d();
      this.field_146289_q.func_211126_b(☃, (float)(this.field_146999_f / 2 - this.field_146289_q.func_78256_a(☃) / 2), 6.0F, 4210752);
      this.field_146289_q.func_211126_b(this.field_175383_v.func_145748_c_().func_150254_d(), 8.0F, (float)(this.field_147000_g - 96 + 2), 4210752);
   }

   @Override
   protected void func_146976_a(float var1, int var2, int var3) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147087_u);
      int ☃ = this.field_147003_i;
      int ☃x = this.field_147009_r;
      this.func_73729_b(☃, ☃x, 0, 0, this.field_146999_f, this.field_147000_g);
      if (TileEntityFurnace.func_174903_a(this.field_147086_v)) {
         int ☃xx = this.func_175382_i(13);
         this.func_73729_b(☃ + 56, ☃x + 36 + 12 - ☃xx, 176, 12 - ☃xx, 14, ☃xx + 1);
      }

      int ☃ = this.func_175381_h(24);
      this.func_73729_b(☃ + 79, ☃x + 34, 176, 14, ☃ + 1, 16);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.field_201557_v.mouseClicked(☃, ☃, ☃)) {
         return true;
      } else {
         return this.field_201556_A && this.field_201557_v.func_191878_b() ? true : super.mouseClicked(☃, ☃, ☃);
      }
   }

   @Override
   protected void func_184098_a(Slot var1, int var2, int var3, ClickType var4) {
      super.func_184098_a(☃, ☃, ☃, ☃);
      this.field_201557_v.func_191874_a(☃);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      return this.field_201557_v.keyPressed(☃, ☃, ☃) ? false : super.keyPressed(☃, ☃, ☃);
   }

   @Override
   protected boolean func_195361_a(double var1, double var3, int var5, int var6, int var7) {
      boolean ☃ = ☃ < (double)☃ || ☃ < (double)☃ || ☃ >= (double)(☃ + this.field_146999_f) || ☃ >= (double)(☃ + this.field_147000_g);
      return this.field_201557_v.func_195604_a(☃, ☃, this.field_147003_i, this.field_147009_r, this.field_146999_f, this.field_147000_g, ☃) && ☃;
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      return this.field_201557_v.charTyped(☃, ☃) ? true : super.charTyped(☃, ☃);
   }

   @Override
   public void func_192043_J_() {
      this.field_201557_v.func_193948_e();
   }

   @Override
   public GuiRecipeBook func_194310_f() {
      return this.field_201557_v;
   }

   @Override
   public void func_146281_b() {
      this.field_201557_v.func_191871_c();
      super.func_146281_b();
   }

   private int func_175381_h(int var1) {
      int ☃ = this.field_147086_v.func_174887_a_(2);
      int ☃x = this.field_147086_v.func_174887_a_(3);
      return ☃x != 0 && ☃ != 0 ? ☃ * ☃ / ☃x : 0;
   }

   private int func_175382_i(int var1) {
      int ☃ = this.field_147086_v.func_174887_a_(1);
      if (☃ == 0) {
         ☃ = 200;
      }

      return this.field_147086_v.func_174887_a_(0) * ☃ / ☃;
   }
}
