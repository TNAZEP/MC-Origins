package net.minecraft.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen {
   private final GuiScreen field_175361_a;
   private String field_175360_f;

   public GuiCustomizeSkin(GuiScreen var1) {
      this.field_175361_a = ☃;
   }

   @Override
   protected void func_73866_w_() {
      int ☃ = 0;
      this.field_175360_f = I18n.func_135052_a("options.skinCustomisation.title");

      for(EnumPlayerModelParts ☃x : EnumPlayerModelParts.values()) {
         this.func_189646_b(
            new GuiCustomizeSkin.ButtonPart(
               ☃x.func_179328_b(), this.field_146294_l / 2 - 155 + ☃ % 2 * 160, this.field_146295_m / 6 + 24 * (☃ >> 1), 150, 20, ☃x
            )
         );
         ++☃;
      }

      this.func_189646_b(
         new GuiOptionButton(
            199,
            this.field_146294_l / 2 - 155 + ☃ % 2 * 160,
            this.field_146295_m / 6 + 24 * (☃ >> 1),
            GameSettings.Options.MAIN_HAND,
            this.field_146297_k.field_71474_y.func_74297_c(GameSettings.Options.MAIN_HAND)
         ) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiCustomizeSkin.this.field_146297_k.field_71474_y.func_74306_a(GameSettings.Options.MAIN_HAND, 1);
               this.field_146126_j = GuiCustomizeSkin.this.field_146297_k.field_71474_y.func_74297_c(GameSettings.Options.MAIN_HAND);
               GuiCustomizeSkin.this.field_146297_k.field_71474_y.func_82879_c();
            }
         }
      );
      if (++☃ % 2 == 1) {
         ++☃;
      }

      this.func_189646_b(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m / 6 + 24 * (☃ >> 1), I18n.func_135052_a("gui.done")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiCustomizeSkin.this.field_146297_k.field_71474_y.func_74303_b();
            GuiCustomizeSkin.this.field_146297_k.func_147108_a(GuiCustomizeSkin.this.field_175361_a);
         }
      });
   }

   @Override
   public void func_195122_V_() {
      this.field_146297_k.field_71474_y.func_74303_b();
      super.func_195122_V_();
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_175360_f, this.field_146294_l / 2, 20, 16777215);
      super.func_73863_a(☃, ☃, ☃);
   }

   private String func_175358_a(EnumPlayerModelParts var1) {
      String ☃;
      if (this.field_146297_k.field_71474_y.func_178876_d().contains(☃)) {
         ☃ = I18n.func_135052_a("options.on");
      } else {
         ☃ = I18n.func_135052_a("options.off");
      }

      return ☃.func_179326_d().func_150254_d() + ": " + ☃;
   }

   class ButtonPart extends GuiButton {
      private final EnumPlayerModelParts field_175234_p;

      private ButtonPart(int var2, int var3, int var4, int var5, int var6, EnumPlayerModelParts var7) {
         super(☃, ☃, ☃, ☃, ☃, GuiCustomizeSkin.this.func_175358_a(☃));
         this.field_175234_p = ☃;
      }

      @Override
      public void func_194829_a(double var1, double var3) {
         GuiCustomizeSkin.this.field_146297_k.field_71474_y.func_178877_a(this.field_175234_p);
         this.field_146126_j = GuiCustomizeSkin.this.func_175358_a(this.field_175234_p);
      }
   }
}
