package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;

public class ServerListEntryLanDetected extends ServerSelectionList.Entry {
   private final GuiMultiplayer field_148292_c;
   protected final Minecraft field_148293_a;
   protected final LanServerInfo field_148291_b;
   private long field_148290_d;

   protected ServerListEntryLanDetected(GuiMultiplayer var1, LanServerInfo var2) {
      this.field_148292_c = ☃;
      this.field_148291_b = ☃;
      this.field_148293_a = Minecraft.func_71410_x();
   }

   @Override
   public void func_194999_a(int var1, int var2, int var3, int var4, boolean var5, float var6) {
      int ☃ = this.func_195002_d();
      int ☃x = this.func_195001_c();
      this.field_148293_a.field_71466_p.func_211126_b(I18n.func_135052_a("lanServer.title"), (float)(☃ + 32 + 3), (float)(☃x + 1), 16777215);
      this.field_148293_a.field_71466_p.func_211126_b(this.field_148291_b.func_77487_a(), (float)(☃ + 32 + 3), (float)(☃x + 12), 8421504);
      if (this.field_148293_a.field_71474_y.field_80005_w) {
         this.field_148293_a.field_71466_p.func_211126_b(I18n.func_135052_a("selectServer.hiddenAddress"), (float)(☃ + 32 + 3), (float)(☃x + 12 + 11), 3158064);
      } else {
         this.field_148293_a.field_71466_p.func_211126_b(this.field_148291_b.func_77488_b(), (float)(☃ + 32 + 3), (float)(☃x + 12 + 11), 3158064);
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      this.field_148292_c.func_146790_a(this.func_195003_b());
      if (Util.func_211177_b() - this.field_148290_d < 250L) {
         this.field_148292_c.func_146796_h();
      }

      this.field_148290_d = Util.func_211177_b();
      return false;
   }

   public LanServerInfo func_189995_a() {
      return this.field_148291_b;
   }
}
