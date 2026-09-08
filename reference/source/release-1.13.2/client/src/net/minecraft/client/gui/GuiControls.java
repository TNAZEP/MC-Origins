package net.minecraft.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.Util;

public class GuiControls extends GuiScreen {
   private static final GameSettings.Options[] field_146492_g = new GameSettings.Options[]{
      GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN, GameSettings.Options.AUTO_JUMP
   };
   private final GuiScreen field_146496_h;
   protected String field_146495_a = "Controls";
   private final GameSettings field_146497_i;
   public KeyBinding field_146491_f;
   public long field_152177_g;
   private GuiKeyBindingList field_146494_r;
   private GuiButton field_146493_s;

   public GuiControls(GuiScreen var1, GameSettings var2) {
      this.field_146496_h = ☃;
      this.field_146497_i = ☃;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146494_r = new GuiKeyBindingList(this, this.field_146297_k);
      this.field_195124_j.add(this.field_146494_r);
      this.func_195073_a(this.field_146494_r);
      this.func_189646_b(new GuiButton(200, this.field_146294_l / 2 - 155 + 160, this.field_146295_m - 29, 150, 20, I18n.func_135052_a("gui.done")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiControls.this.field_146297_k.func_147108_a(GuiControls.this.field_146496_h);
         }
      });
      this.field_146493_s = this.func_189646_b(
         new GuiButton(201, this.field_146294_l / 2 - 155, this.field_146295_m - 29, 150, 20, I18n.func_135052_a("controls.resetAll")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               for(KeyBinding ☃ : GuiControls.this.field_146297_k.field_71474_y.field_74324_K) {
                  ☃.func_197979_b(☃.func_197977_i());
               }
   
               KeyBinding.func_74508_b();
            }
         }
      );
      this.field_146495_a = I18n.func_135052_a("controls.title");
      int ☃ = 0;

      for(GameSettings.Options ☃x : field_146492_g) {
         if (☃x.func_74380_a()) {
            this.func_189646_b(new GuiOptionSlider(☃x.func_74381_c(), this.field_146294_l / 2 - 155 + ☃ % 2 * 160, 18 + 24 * (☃ >> 1), ☃x));
         } else {
            this.func_189646_b(
               new GuiOptionButton(☃x.func_74381_c(), this.field_146294_l / 2 - 155 + ☃ % 2 * 160, 18 + 24 * (☃ >> 1), ☃x, this.field_146497_i.func_74297_c(☃x)) {
                  @Override
                  public void func_194829_a(double var1, double var3) {
                     GuiControls.this.field_146497_i.func_74306_a(this.func_146136_c(), 1);
                     this.field_146126_j = GuiControls.this.field_146497_i.func_74297_c(GameSettings.Options.func_74379_a(this.field_146127_k));
                  }
               }
            );
         }

         ++☃;
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.field_146491_f != null) {
         this.field_146497_i.func_198014_a(this.field_146491_f, InputMappings.Type.MOUSE.func_197944_a(☃));
         this.field_146491_f = null;
         KeyBinding.func_74508_b();
         return true;
      } else if (☃ == 0 && this.field_146494_r.mouseClicked(☃, ☃, ☃)) {
         this.func_195072_d(true);
         this.func_195073_a(this.field_146494_r);
         return true;
      } else {
         return super.mouseClicked(☃, ☃, ☃);
      }
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      if (☃ == 0 && this.field_146494_r.mouseReleased(☃, ☃, ☃)) {
         this.func_195072_d(false);
         return true;
      } else {
         return super.mouseReleased(☃, ☃, ☃);
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.field_146491_f != null) {
         if (☃ == 256) {
            this.field_146497_i.func_198014_a(this.field_146491_f, InputMappings.field_197958_a);
         } else {
            this.field_146497_i.func_198014_a(this.field_146491_f, InputMappings.func_197954_a(☃, ☃));
         }

         this.field_146491_f = null;
         this.field_152177_g = Util.func_211177_b();
         KeyBinding.func_74508_b();
         return true;
      } else {
         return super.keyPressed(☃, ☃, ☃);
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.field_146494_r.func_148128_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, this.field_146495_a, this.field_146294_l / 2, 8, 16777215);
      boolean ☃ = false;

      for(KeyBinding ☃x : this.field_146497_i.field_74324_K) {
         if (!☃x.func_197985_l()) {
            ☃ = true;
            break;
         }
      }

      this.field_146493_s.field_146124_l = ☃;
      super.func_73863_a(☃, ☃, ☃);
   }
}
