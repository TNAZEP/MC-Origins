package net.minecraft.client.gui;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class GuiGameOver extends GuiScreen {
   private int field_146347_a;
   private final ITextComponent field_184871_f;

   public GuiGameOver(@Nullable ITextComponent var1) {
      this.field_184871_f = ☃;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146347_a = 0;
      String ☃;
      String ☃x;
      if (this.field_146297_k.field_71441_e.func_72912_H().func_76093_s()) {
         ☃ = I18n.func_135052_a("deathScreen.spectate");
         ☃x = I18n.func_135052_a("deathScreen." + (this.field_146297_k.func_71387_A() ? "deleteWorld" : "leaveServer"));
      } else {
         ☃ = I18n.func_135052_a("deathScreen.respawn");
         ☃x = I18n.func_135052_a("deathScreen.titleScreen");
      }

      this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72, ☃) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiGameOver.this.field_146297_k.field_71439_g.func_71004_bE();
            GuiGameOver.this.field_146297_k.func_147108_a(null);
         }
      });
      GuiButton ☃ = this.func_189646_b(
         new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, ☃x) {
            @Override
            public void func_194829_a(double var1, double var3) {
               if (GuiGameOver.this.field_146297_k.field_71441_e.func_72912_H().func_76093_s()) {
                  GuiGameOver.this.field_146297_k.func_147108_a(new GuiMainMenu());
               } else {
                  GuiYesNo ☃ = new GuiYesNo(
                     GuiGameOver.this,
                     I18n.func_135052_a("deathScreen.quit.confirm"),
                     "",
                     I18n.func_135052_a("deathScreen.titleScreen"),
                     I18n.func_135052_a("deathScreen.respawn"),
                     0
                  );
                  GuiGameOver.this.field_146297_k.func_147108_a(☃);
                  ☃.func_146350_a(20);
               }
            }
         }
      );
      if (!this.field_146297_k.field_71441_e.func_72912_H().func_76093_s() && this.field_146297_k.func_110432_I() == null) {
         ☃.field_146124_l = false;
      }

      for(GuiButton ☃ : this.field_146292_n) {
         ☃.field_146124_l = false;
      }
   }

   @Override
   public boolean func_195120_Y_() {
      return false;
   }

   @Override
   public void confirmResult(boolean var1, int var2) {
      if (☃ == 31102009) {
         super.confirmResult(☃, ☃);
      } else if (☃) {
         if (this.field_146297_k.field_71441_e != null) {
            this.field_146297_k.field_71441_e.func_72882_A();
         }

         this.field_146297_k.func_205055_a(null, new GuiDirtMessageScreen(I18n.func_135052_a("menu.savingLevel")));
         this.field_146297_k.func_147108_a(new GuiMainMenu());
      } else {
         this.field_146297_k.field_71439_g.func_71004_bE();
         this.field_146297_k.func_147108_a(null);
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      boolean ☃ = this.field_146297_k.field_71441_e.func_72912_H().func_76093_s();
      this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, 1615855616, -1602211792);
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      this.func_73732_a(
         this.field_146289_q, I18n.func_135052_a(☃ ? "deathScreen.title.hardcore" : "deathScreen.title"), this.field_146294_l / 2 / 2, 30, 16777215
      );
      GlStateManager.func_179121_F();
      if (this.field_184871_f != null) {
         this.func_73732_a(this.field_146289_q, this.field_184871_f.func_150254_d(), this.field_146294_l / 2, 85, 16777215);
      }

      this.func_73732_a(
         this.field_146289_q,
         I18n.func_135052_a("deathScreen.score") + ": " + TextFormatting.YELLOW + this.field_146297_k.field_71439_g.func_71037_bA(),
         this.field_146294_l / 2,
         100,
         16777215
      );
      if (this.field_184871_f != null && ☃ > 85 && ☃ < 85 + this.field_146289_q.field_78288_b) {
         ITextComponent ☃ = this.func_184870_b(☃);
         if (☃ != null && ☃.func_150256_b().func_150210_i() != null) {
            this.func_175272_a(☃, ☃, ☃);
         }
      }

      super.func_73863_a(☃, ☃, ☃);
   }

   @Nullable
   public ITextComponent func_184870_b(int var1) {
      if (this.field_184871_f == null) {
         return null;
      } else {
         int ☃ = this.field_146297_k.field_71466_p.func_78256_a(this.field_184871_f.func_150254_d());
         int ☃x = this.field_146294_l / 2 - ☃ / 2;
         int ☃xx = this.field_146294_l / 2 + ☃ / 2;
         int ☃xxx = ☃x;
         if (☃ >= ☃x && ☃ <= ☃xx) {
            for(ITextComponent ☃xxxx : this.field_184871_f) {
               ☃xxx += this.field_146297_k.field_71466_p.func_78256_a(GuiUtilRenderComponents.func_178909_a(☃xxxx.func_150261_e(), false));
               if (☃xxx > ☃) {
                  return ☃xxxx;
               }
            }

            return null;
         } else {
            return null;
         }
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.field_184871_f != null && ☃ > 85.0 && ☃ < (double)(85 + this.field_146289_q.field_78288_b)) {
         ITextComponent ☃ = this.func_184870_b((int)☃);
         if (☃ != null && ☃.func_150256_b().func_150235_h() != null && ☃.func_150256_b().func_150235_h().func_150669_a() == ClickEvent.Action.OPEN_URL) {
            this.func_175276_a(☃);
            return false;
         }
      }

      return super.mouseClicked(☃, ☃, ☃);
   }

   @Override
   public boolean func_73868_f() {
      return false;
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      ++this.field_146347_a;
      if (this.field_146347_a == 20) {
         for(GuiButton ☃ : this.field_146292_n) {
            ☃.field_146124_l = true;
         }
      }
   }
}
