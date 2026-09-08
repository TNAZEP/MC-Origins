package net.minecraft.client.gui;

import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;

public class GuiVideoSettings extends GuiScreen {
   private final GuiScreen field_146498_f;
   protected String field_146500_a = "Video Settings";
   private final GameSettings field_146499_g;
   private GuiOptionsRowList field_146501_h;
   private static final GameSettings.Options[] field_146502_i = new GameSettings.Options[]{
      GameSettings.Options.GRAPHICS,
      GameSettings.Options.RENDER_DISTANCE,
      GameSettings.Options.AMBIENT_OCCLUSION,
      GameSettings.Options.FRAMERATE_LIMIT,
      GameSettings.Options.ENABLE_VSYNC,
      GameSettings.Options.VIEW_BOBBING,
      GameSettings.Options.GUI_SCALE,
      GameSettings.Options.ATTACK_INDICATOR,
      GameSettings.Options.GAMMA,
      GameSettings.Options.RENDER_CLOUDS,
      GameSettings.Options.USE_FULLSCREEN,
      GameSettings.Options.PARTICLES,
      GameSettings.Options.MIPMAP_LEVELS,
      GameSettings.Options.USE_VBO,
      GameSettings.Options.ENTITY_SHADOWS,
      GameSettings.Options.BIOME_BLEND_RADIUS
   };

   public GuiVideoSettings(GuiScreen var1, GameSettings var2) {
      this.field_146498_f = ☃;
      this.field_146499_g = ☃;
   }

   @Nullable
   @Override
   public IGuiEventListener getFocused() {
      return this.field_146501_h;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146500_a = I18n.func_135052_a("options.videoTitle");
      this.func_189646_b(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m - 27, I18n.func_135052_a("gui.done")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiVideoSettings.this.field_146297_k.field_71474_y.func_74303_b();
            GuiVideoSettings.this.field_146297_k.field_195558_d.func_198097_f();
            GuiVideoSettings.this.field_146297_k.func_147108_a(GuiVideoSettings.this.field_146498_f);
         }
      });
      if (OpenGlHelper.field_176083_O) {
         this.field_146501_h = new GuiOptionsRowList(
            this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 32, 25, field_146502_i
         );
      } else {
         GameSettings.Options[] ☃ = new GameSettings.Options[field_146502_i.length - 1];
         int ☃x = 0;

         for(GameSettings.Options ☃xx : field_146502_i) {
            if (☃xx == GameSettings.Options.USE_VBO) {
               break;
            }

            ☃[☃x] = ☃xx;
            ++☃x;
         }

         this.field_146501_h = new GuiOptionsRowList(this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 32, 25, ☃);
      }

      this.field_195124_j.add(this.field_146501_h);
   }

   @Override
   public void func_195122_V_() {
      this.field_146297_k.field_71474_y.func_74303_b();
      super.func_195122_V_();
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      int ☃ = this.field_146499_g.field_74335_Z;
      if (super.mouseClicked(☃, ☃, ☃)) {
         if (this.field_146499_g.field_74335_Z != ☃) {
            this.field_146297_k.field_195558_d.func_198098_h();
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      int ☃ = this.field_146499_g.field_74335_Z;
      if (super.mouseReleased(☃, ☃, ☃)) {
         return true;
      } else if (this.field_146501_h.mouseReleased(☃, ☃, ☃)) {
         if (this.field_146499_g.field_74335_Z != ☃) {
            this.field_146297_k.field_195558_d.func_198098_h();
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.field_146501_h.func_148128_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, this.field_146500_a, this.field_146294_l / 2, 5, 16777215);
      super.func_73863_a(☃, ☃, ☃);
   }
}
