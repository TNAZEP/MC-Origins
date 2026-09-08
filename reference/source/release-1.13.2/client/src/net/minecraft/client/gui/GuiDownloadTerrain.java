package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

public class GuiDownloadTerrain extends GuiScreen {
   @Override
   public boolean func_195120_Y_() {
      return false;
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146278_c(0);
      this.func_73732_a(
         this.field_146289_q, I18n.func_135052_a("multiplayer.downloadingTerrain"), this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16777215
      );
      super.func_73863_a(☃, ☃, ☃);
   }

   @Override
   public boolean func_73868_f() {
      return false;
   }
}
