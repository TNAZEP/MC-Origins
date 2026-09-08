package net.minecraft.realms;

import java.util.List;
import net.minecraft.util.text.ITextComponent;

public class DisconnectedRealmsScreen extends RealmsScreen {
   private final String title;
   private final ITextComponent reason;
   private List<String> lines;
   private final RealmsScreen parent;
   private int textHeight;

   public DisconnectedRealmsScreen(RealmsScreen var1, String var2, ITextComponent var3) {
      this.parent = ☃;
      this.title = getLocalizedString(☃);
      this.reason = ☃;
   }

   @Override
   public void init() {
      Realms.setConnectedToRealms(false);
      Realms.clearResourcePack();
      this.lines = this.fontSplit(this.reason.func_150254_d(), this.width() - 50);
      this.textHeight = this.lines.size() * this.fontLineHeight();
      this.buttonsAdd(
         new RealmsButton(0, this.width() / 2 - 100, this.height() / 2 + this.textHeight / 2 + this.fontLineHeight(), getLocalizedString("gui.back")) {
            @Override
            public void onClick(double var1, double var3) {
               Realms.setScreen(DisconnectedRealmsScreen.this.parent);
            }
         }
      );
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (☃ == 256) {
         Realms.setScreen(this.parent);
         return true;
      } else {
         return super.keyPressed(☃, ☃, ☃);
      }
   }

   @Override
   public void render(int var1, int var2, float var3) {
      this.renderBackground();
      this.drawCenteredString(this.title, this.width() / 2, this.height() / 2 - this.textHeight / 2 - this.fontLineHeight() * 2, 11184810);
      int ☃ = this.height() / 2 - this.textHeight / 2;
      if (this.lines != null) {
         for(String ☃x : this.lines) {
            this.drawCenteredString(☃x, this.width() / 2, ☃, 16777215);
            ☃ += this.fontLineHeight();
         }
      }

      super.render(☃, ☃, ☃);
   }
}
