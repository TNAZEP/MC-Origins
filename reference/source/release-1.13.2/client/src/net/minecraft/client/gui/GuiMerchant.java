package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketSelectTrade;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiMerchant extends GuiContainer {
   private static final Logger field_147039_u = LogManager.getLogger();
   private static final ResourceLocation field_147038_v = new ResourceLocation("textures/gui/container/villager.png");
   private final IMerchant field_147037_w;
   private GuiMerchant.MerchantButton field_147043_x;
   private GuiMerchant.MerchantButton field_147042_y;
   private int field_147041_z;
   private final ITextComponent field_147040_A;
   private final InventoryPlayer field_212355_D;

   public GuiMerchant(InventoryPlayer var1, IMerchant var2, World var3) {
      super(new ContainerMerchant(☃, ☃, ☃));
      this.field_147037_w = ☃;
      this.field_147040_A = ☃.func_145748_c_();
      this.field_212355_D = ☃;
   }

   private void func_195391_j() {
      ((ContainerMerchant)this.field_147002_h).func_75175_c(this.field_147041_z);
      this.field_146297_k.func_147114_u().func_147297_a(new CPacketSelectTrade(this.field_147041_z));
   }

   @Override
   protected void func_73866_w_() {
      super.func_73866_w_();
      int ☃ = (this.field_146294_l - this.field_146999_f) / 2;
      int ☃x = (this.field_146295_m - this.field_147000_g) / 2;
      this.field_147043_x = this.func_189646_b(new GuiMerchant.MerchantButton(1, ☃ + 120 + 27, ☃x + 24 - 1, true) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMerchant.this.field_147041_z++;
            MerchantRecipeList ☃ = GuiMerchant.this.field_147037_w.func_70934_b(GuiMerchant.this.field_146297_k.field_71439_g);
            if (☃ != null && GuiMerchant.this.field_147041_z >= ☃.size()) {
               GuiMerchant.this.field_147041_z = ☃.size() - 1;
            }

            GuiMerchant.this.func_195391_j();
         }
      });
      this.field_147042_y = this.func_189646_b(new GuiMerchant.MerchantButton(2, ☃ + 36 - 19, ☃x + 24 - 1, false) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMerchant.this.field_147041_z--;
            if (GuiMerchant.this.field_147041_z < 0) {
               GuiMerchant.this.field_147041_z = 0;
            }

            GuiMerchant.this.func_195391_j();
         }
      });
      this.field_147043_x.field_146124_l = false;
      this.field_147042_y.field_146124_l = false;
   }

   @Override
   protected void func_146979_b(int var1, int var2) {
      String ☃ = this.field_147040_A.func_150254_d();
      this.field_146289_q.func_211126_b(☃, (float)(this.field_146999_f / 2 - this.field_146289_q.func_78256_a(☃) / 2), 6.0F, 4210752);
      this.field_146289_q.func_211126_b(this.field_212355_D.func_145748_c_().func_150254_d(), 8.0F, (float)(this.field_147000_g - 96 + 2), 4210752);
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      MerchantRecipeList ☃ = this.field_147037_w.func_70934_b(this.field_146297_k.field_71439_g);
      if (☃ != null) {
         this.field_147043_x.field_146124_l = this.field_147041_z < ☃.size() - 1;
         this.field_147042_y.field_146124_l = this.field_147041_z > 0;
      }
   }

   @Override
   protected void func_146976_a(float var1, int var2, int var3) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147038_v);
      int ☃ = (this.field_146294_l - this.field_146999_f) / 2;
      int ☃x = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(☃, ☃x, 0, 0, this.field_146999_f, this.field_147000_g);
      MerchantRecipeList ☃xx = this.field_147037_w.func_70934_b(this.field_146297_k.field_71439_g);
      if (☃xx != null && !☃xx.isEmpty()) {
         int ☃xxx = this.field_147041_z;
         if (☃xxx < 0 || ☃xxx >= ☃xx.size()) {
            return;
         }

         MerchantRecipe ☃xxx = (MerchantRecipe)☃xx.get(☃xxx);
         if (☃xxx.func_82784_g()) {
            this.field_146297_k.func_110434_K().func_110577_a(field_147038_v);
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.func_179140_f();
            this.func_73729_b(this.field_147003_i + 83, this.field_147009_r + 21, 212, 0, 28, 21);
            this.func_73729_b(this.field_147003_i + 83, this.field_147009_r + 51, 212, 0, 28, 21);
         }
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      super.func_73863_a(☃, ☃, ☃);
      MerchantRecipeList ☃ = this.field_147037_w.func_70934_b(this.field_146297_k.field_71439_g);
      if (☃ != null && !☃.isEmpty()) {
         int ☃x = (this.field_146294_l - this.field_146999_f) / 2;
         int ☃xx = (this.field_146295_m - this.field_147000_g) / 2;
         int ☃xxx = this.field_147041_z;
         MerchantRecipe ☃xxxx = (MerchantRecipe)☃.get(☃xxx);
         ItemStack ☃xxxxx = ☃xxxx.func_77394_a();
         ItemStack ☃xxxxxx = ☃xxxx.func_77396_b();
         ItemStack ☃xxxxxxx = ☃xxxx.func_77397_d();
         GlStateManager.func_179094_E();
         RenderHelper.func_74520_c();
         GlStateManager.func_179140_f();
         GlStateManager.func_179091_B();
         GlStateManager.func_179142_g();
         GlStateManager.func_179145_e();
         this.field_146296_j.field_77023_b = 100.0F;
         this.field_146296_j.func_180450_b(☃xxxxx, ☃x + 36, ☃xx + 24);
         this.field_146296_j.func_175030_a(this.field_146289_q, ☃xxxxx, ☃x + 36, ☃xx + 24);
         if (!☃xxxxxx.func_190926_b()) {
            this.field_146296_j.func_180450_b(☃xxxxxx, ☃x + 62, ☃xx + 24);
            this.field_146296_j.func_175030_a(this.field_146289_q, ☃xxxxxx, ☃x + 62, ☃xx + 24);
         }

         this.field_146296_j.func_180450_b(☃xxxxxxx, ☃x + 120, ☃xx + 24);
         this.field_146296_j.func_175030_a(this.field_146289_q, ☃xxxxxxx, ☃x + 120, ☃xx + 24);
         this.field_146296_j.field_77023_b = 0.0F;
         GlStateManager.func_179140_f();
         if (this.func_195359_a(36, 24, 16, 16, (double)☃, (double)☃) && !☃xxxxx.func_190926_b()) {
            this.func_146285_a(☃xxxxx, ☃, ☃);
         } else if (!☃xxxxxx.func_190926_b() && this.func_195359_a(62, 24, 16, 16, (double)☃, (double)☃) && !☃xxxxxx.func_190926_b()) {
            this.func_146285_a(☃xxxxxx, ☃, ☃);
         } else if (!☃xxxxxxx.func_190926_b() && this.func_195359_a(120, 24, 16, 16, (double)☃, (double)☃) && !☃xxxxxxx.func_190926_b()) {
            this.func_146285_a(☃xxxxxxx, ☃, ☃);
         } else if (☃xxxx.func_82784_g()
            && (this.func_195359_a(83, 21, 28, 21, (double)☃, (double)☃) || this.func_195359_a(83, 51, 28, 21, (double)☃, (double)☃))) {
            this.func_146279_a(I18n.func_135052_a("merchant.deprecated"), ☃, ☃);
         }

         GlStateManager.func_179121_F();
         GlStateManager.func_179145_e();
         GlStateManager.func_179126_j();
         RenderHelper.func_74519_b();
      }

      this.func_191948_b(☃, ☃);
   }

   public IMerchant func_147035_g() {
      return this.field_147037_w;
   }

   abstract static class MerchantButton extends GuiButton {
      private final boolean field_146157_o;

      public MerchantButton(int var1, int var2, int var3, boolean var4) {
         super(☃, ☃, ☃, 12, 19, "");
         this.field_146157_o = ☃;
      }

      @Override
      public void func_194828_a(int var1, int var2, float var3) {
         if (this.field_146125_m) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(GuiMerchant.field_147038_v);
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            boolean ☃ = ☃ >= this.field_146128_h
               && ☃ >= this.field_146129_i
               && ☃ < this.field_146128_h + this.field_146120_f
               && ☃ < this.field_146129_i + this.field_146121_g;
            int ☃x = 0;
            int ☃xx = 176;
            if (!this.field_146124_l) {
               ☃xx += this.field_146120_f * 2;
            } else if (☃) {
               ☃xx += this.field_146120_f;
            }

            if (!this.field_146157_o) {
               ☃x += this.field_146121_g;
            }

            this.func_73729_b(this.field_146128_h, this.field_146129_i, ☃xx, ☃x, this.field_146120_f, this.field_146121_g);
         }
      }
   }
}
