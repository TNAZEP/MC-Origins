package net.minecraft.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class GuiScreenOptionsSounds extends GuiScreen {
   private final GuiScreen field_146505_f;
   private final GameSettings field_146506_g;
   protected String field_146507_a = "Options";
   private String field_146508_h;

   public GuiScreenOptionsSounds(GuiScreen var1, GameSettings var2) {
      this.field_146505_f = ☃;
      this.field_146506_g = ☃;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146507_a = I18n.func_135052_a("options.sounds.title");
      this.field_146508_h = I18n.func_135052_a("options.off");
      int ☃ = 0;
      this.func_189646_b(
         new GuiScreenOptionsSounds.Button(
            SoundCategory.MASTER.ordinal(),
            this.field_146294_l / 2 - 155 + ☃ % 2 * 160,
            this.field_146295_m / 6 - 12 + 24 * (☃ >> 1),
            SoundCategory.MASTER,
            true
         )
      );
      ☃ += 2;

      for(SoundCategory ☃x : SoundCategory.values()) {
         if (☃x != SoundCategory.MASTER) {
            this.func_189646_b(
               new GuiScreenOptionsSounds.Button(
                  ☃x.ordinal(), this.field_146294_l / 2 - 155 + ☃ % 2 * 160, this.field_146295_m / 6 - 12 + 24 * (☃ >> 1), ☃x, false
               )
            );
            ++☃;
         }
      }

      this.func_189646_b(
         new GuiOptionButton(
            201,
            this.field_146294_l / 2 - 75,
            this.field_146295_m / 6 - 12 + 24 * (++☃ >> 1),
            GameSettings.Options.SHOW_SUBTITLES,
            this.field_146506_g.func_74297_c(GameSettings.Options.SHOW_SUBTITLES)
         ) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiScreenOptionsSounds.this.field_146297_k.field_71474_y.func_74306_a(GameSettings.Options.SHOW_SUBTITLES, 1);
               this.field_146126_j = GuiScreenOptionsSounds.this.field_146297_k.field_71474_y.func_74297_c(GameSettings.Options.SHOW_SUBTITLES);
               GuiScreenOptionsSounds.this.field_146297_k.field_71474_y.func_74303_b();
            }
         }
      );
      this.func_189646_b(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m / 6 + 168, I18n.func_135052_a("gui.done")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiScreenOptionsSounds.this.field_146297_k.field_71474_y.func_74303_b();
            GuiScreenOptionsSounds.this.field_146297_k.func_147108_a(GuiScreenOptionsSounds.this.field_146505_f);
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
      this.func_73732_a(this.field_146289_q, this.field_146507_a, this.field_146294_l / 2, 15, 16777215);
      super.func_73863_a(☃, ☃, ☃);
   }

   protected String func_184097_a(SoundCategory var1) {
      float ☃ = this.field_146506_g.func_186711_a(☃);
      return ☃ == 0.0F ? this.field_146508_h : (int)(☃ * 100.0F) + "%";
   }

   class Button extends GuiButton {
      private final SoundCategory field_184063_r;
      private final String field_146152_s;
      public double field_146156_o;
      public boolean field_146155_p;

      public Button(int var2, int var3, int var4, SoundCategory var5, boolean var6) {
         super(☃, ☃, ☃, ☃ ? 310 : 150, 20, "");
         this.field_184063_r = ☃;
         this.field_146152_s = I18n.func_135052_a("soundCategory." + ☃.func_187948_a());
         this.field_146126_j = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_184097_a(☃);
         this.field_146156_o = (double)GuiScreenOptionsSounds.this.field_146506_g.func_186711_a(☃);
      }

      @Override
      protected int func_146114_a(boolean var1) {
         return 0;
      }

      @Override
      protected void func_146119_b(Minecraft var1, int var2, int var3) {
         if (this.field_146125_m) {
            if (this.field_146155_p) {
               this.field_146156_o = (double)((float)(☃ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8));
               this.field_146156_o = MathHelper.func_151237_a(this.field_146156_o, 0.0, 1.0);
               ☃.field_71474_y.func_186712_a(this.field_184063_r, (float)this.field_146156_o);
               ☃.field_71474_y.func_74303_b();
               this.field_146126_j = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_184097_a(this.field_184063_r);
            }

            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_73729_b(this.field_146128_h + (int)(this.field_146156_o * (double)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
            this.func_73729_b(this.field_146128_h + (int)(this.field_146156_o * (double)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
         }
      }

      @Override
      public void func_194829_a(double var1, double var3) {
         this.field_146156_o = (☃ - (double)(this.field_146128_h + 4)) / (double)(this.field_146120_f - 8);
         this.field_146156_o = MathHelper.func_151237_a(this.field_146156_o, 0.0, 1.0);
         GuiScreenOptionsSounds.this.field_146297_k.field_71474_y.func_186712_a(this.field_184063_r, (float)this.field_146156_o);
         GuiScreenOptionsSounds.this.field_146297_k.field_71474_y.func_74303_b();
         this.field_146126_j = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_184097_a(this.field_184063_r);
         this.field_146155_p = true;
      }

      @Override
      public void func_146113_a(SoundHandler var1) {
      }

      @Override
      public void func_194831_b(double var1, double var3) {
         if (this.field_146155_p) {
            GuiScreenOptionsSounds.this.field_146297_k.func_147118_V().func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
         }

         this.field_146155_p = false;
      }
   }
}
