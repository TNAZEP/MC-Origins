package net.minecraft.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class GuiOptions extends GuiScreen {
   private static final GameSettings.Options[] field_146440_f = new GameSettings.Options[]{GameSettings.Options.FOV};
   private final GuiScreen field_146441_g;
   private final GameSettings field_146443_h;
   private GuiButton field_175357_i;
   private GuiLockIconButton field_175356_r;
   protected String field_146442_a = "Options";

   public GuiOptions(GuiScreen var1, GameSettings var2) {
      this.field_146441_g = ☃;
      this.field_146443_h = ☃;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146442_a = I18n.func_135052_a("options.title");
      int ☃ = 0;

      for(GameSettings.Options ☃x : field_146440_f) {
         if (☃x.func_74380_a()) {
            this.func_189646_b(
               new GuiOptionSlider(☃x.func_74381_c(), this.field_146294_l / 2 - 155 + ☃ % 2 * 160, this.field_146295_m / 6 - 12 + 24 * (☃ >> 1), ☃x)
            );
         } else {
            GuiOptionButton ☃xx = new GuiOptionButton(
               ☃x.func_74381_c(),
               this.field_146294_l / 2 - 155 + ☃ % 2 * 160,
               this.field_146295_m / 6 - 12 + 24 * (☃ >> 1),
               ☃x,
               this.field_146443_h.func_74297_c(☃x)
            ) {
               @Override
               public void func_194829_a(double var1, double var3) {
                  GuiOptions.this.field_146443_h.func_74306_a(this.func_146136_c(), 1);
                  this.field_146126_j = GuiOptions.this.field_146443_h.func_74297_c(GameSettings.Options.func_74379_a(this.field_146127_k));
               }
            };
            this.func_189646_b(☃xx);
         }

         ++☃;
      }

      if (this.field_146297_k.field_71441_e != null) {
         EnumDifficulty ☃x = this.field_146297_k.field_71441_e.func_175659_aa();
         this.field_175357_i = new GuiButton(
            108, this.field_146294_l / 2 - 155 + ☃ % 2 * 160, this.field_146295_m / 6 - 12 + 24 * (☃ >> 1), 150, 20, this.func_175355_a(☃x)
         ) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiOptions.this.field_146297_k
                  .field_71441_e
                  .func_72912_H()
                  .func_176144_a(EnumDifficulty.func_151523_a(GuiOptions.this.field_146297_k.field_71441_e.func_175659_aa().func_151525_a() + 1));
               GuiOptions.this.field_175357_i.field_146126_j = GuiOptions.this.func_175355_a(GuiOptions.this.field_146297_k.field_71441_e.func_175659_aa());
            }
         };
         this.func_189646_b(this.field_175357_i);
         if (this.field_146297_k.func_71356_B() && !this.field_146297_k.field_71441_e.func_72912_H().func_76093_s()) {
            this.field_175357_i.func_175211_a(this.field_175357_i.func_146117_b() - 20);
            this.field_175356_r = new GuiLockIconButton(
               109, this.field_175357_i.field_146128_h + this.field_175357_i.func_146117_b(), this.field_175357_i.field_146129_i
            ) {
               @Override
               public void func_194829_a(double var1, double var3) {
                  GuiOptions.this.field_146297_k
                     .func_147108_a(
                        new GuiYesNo(
                           GuiOptions.this,
                           new TextComponentTranslation("difficulty.lock.title").func_150254_d(),
                           new TextComponentTranslation(
                                 "difficulty.lock.question",
                                 new TextComponentTranslation(GuiOptions.this.field_146297_k.field_71441_e.func_72912_H().func_176130_y().func_151526_b())
                              )
                              .func_150254_d(),
                           109
                        )
                     );
               }
            };
            this.func_189646_b(this.field_175356_r);
            this.field_175356_r.func_175229_b(this.field_146297_k.field_71441_e.func_72912_H().func_176123_z());
            this.field_175356_r.field_146124_l = !this.field_175356_r.func_175230_c();
            this.field_175357_i.field_146124_l = !this.field_175356_r.func_175230_c();
         } else {
            this.field_175357_i.field_146124_l = false;
         }
      } else {
         this.func_189646_b(
            new GuiOptionButton(
               GameSettings.Options.REALMS_NOTIFICATIONS.func_74381_c(),
               this.field_146294_l / 2 - 155 + ☃ % 2 * 160,
               this.field_146295_m / 6 - 12 + 24 * (☃ >> 1),
               GameSettings.Options.REALMS_NOTIFICATIONS,
               this.field_146443_h.func_74297_c(GameSettings.Options.REALMS_NOTIFICATIONS)
            ) {
               @Override
               public void func_194829_a(double var1, double var3) {
                  GuiOptions.this.field_146443_h.func_74306_a(this.func_146136_c(), 1);
                  this.field_146126_j = GuiOptions.this.field_146443_h.func_74297_c(GameSettings.Options.func_74379_a(this.field_146127_k));
               }
            }
         );
      }

      this.func_189646_b(
         new GuiButton(110, this.field_146294_l / 2 - 155, this.field_146295_m / 6 + 48 - 6, 150, 20, I18n.func_135052_a("options.skinCustomisation")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
               GuiOptions.this.field_146297_k.func_147108_a(new GuiCustomizeSkin(GuiOptions.this));
            }
         }
      );
      this.func_189646_b(new GuiButton(106, this.field_146294_l / 2 + 5, this.field_146295_m / 6 + 48 - 6, 150, 20, I18n.func_135052_a("options.sounds")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
            GuiOptions.this.field_146297_k.func_147108_a(new GuiScreenOptionsSounds(GuiOptions.this, GuiOptions.this.field_146443_h));
         }
      });
      this.func_189646_b(new GuiButton(101, this.field_146294_l / 2 - 155, this.field_146295_m / 6 + 72 - 6, 150, 20, I18n.func_135052_a("options.video")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
            GuiOptions.this.field_146297_k.func_147108_a(new GuiVideoSettings(GuiOptions.this, GuiOptions.this.field_146443_h));
         }
      });
      this.func_189646_b(new GuiButton(100, this.field_146294_l / 2 + 5, this.field_146295_m / 6 + 72 - 6, 150, 20, I18n.func_135052_a("options.controls")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
            GuiOptions.this.field_146297_k.func_147108_a(new GuiControls(GuiOptions.this, GuiOptions.this.field_146443_h));
         }
      });
      this.func_189646_b(
         new GuiButton(102, this.field_146294_l / 2 - 155, this.field_146295_m / 6 + 96 - 6, 150, 20, I18n.func_135052_a("options.language")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
               GuiOptions.this.field_146297_k
                  .func_147108_a(new GuiLanguage(GuiOptions.this, GuiOptions.this.field_146443_h, GuiOptions.this.field_146297_k.func_135016_M()));
            }
         }
      );
      this.func_189646_b(new GuiButton(103, this.field_146294_l / 2 + 5, this.field_146295_m / 6 + 96 - 6, 150, 20, I18n.func_135052_a("options.chat.title")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
            GuiOptions.this.field_146297_k.func_147108_a(new ScreenChatOptions(GuiOptions.this, GuiOptions.this.field_146443_h));
         }
      });
      this.func_189646_b(
         new GuiButton(105, this.field_146294_l / 2 - 155, this.field_146295_m / 6 + 120 - 6, 150, 20, I18n.func_135052_a("options.resourcepack")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
               GuiOptions.this.field_146297_k.func_147108_a(new GuiScreenResourcePacks(GuiOptions.this));
            }
         }
      );
      this.func_189646_b(
         new GuiButton(104, this.field_146294_l / 2 + 5, this.field_146295_m / 6 + 120 - 6, 150, 20, I18n.func_135052_a("options.snooper.view")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
               GuiOptions.this.field_146297_k.func_147108_a(new GuiSnooper(GuiOptions.this, GuiOptions.this.field_146443_h));
            }
         }
      );
      this.func_189646_b(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m / 6 + 168, I18n.func_135052_a("gui.done")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiOptions.this.field_146297_k.field_71474_y.func_74303_b();
            GuiOptions.this.field_146297_k.func_147108_a(GuiOptions.this.field_146441_g);
         }
      });
   }

   public String func_175355_a(EnumDifficulty var1) {
      return new TextComponentTranslation("options.difficulty").func_150258_a(": ").func_150257_a(☃.func_199285_b()).func_150254_d();
   }

   @Override
   public void confirmResult(boolean var1, int var2) {
      this.field_146297_k.func_147108_a(this);
      if (☃ == 109 && ☃ && this.field_146297_k.field_71441_e != null) {
         this.field_146297_k.field_71441_e.func_72912_H().func_180783_e(true);
         this.field_175356_r.func_175229_b(true);
         this.field_175356_r.field_146124_l = false;
         this.field_175357_i.field_146124_l = false;
      }
   }

   @Override
   public void func_195122_V_() {
      this.field_146297_k.field_71474_y.func_74303_b();
      super.func_195122_V_();
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_146442_a, this.field_146294_l / 2, 15, 16777215);
      super.func_73863_a(☃, ☃, ☃);
   }
}
