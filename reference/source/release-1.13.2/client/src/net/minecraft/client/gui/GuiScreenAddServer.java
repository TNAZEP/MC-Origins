package net.minecraft.client.gui;

import java.net.IDN;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringUtils;

public class GuiScreenAddServer extends GuiScreen {
   private GuiButton field_195179_a;
   private final GuiScreen field_146310_a;
   private final ServerData field_146311_h;
   private GuiTextField field_146308_f;
   private GuiTextField field_146309_g;
   private GuiButton field_152176_i;
   private final Predicate<String> field_181032_r = var0 -> {
      if (StringUtils.func_151246_b(var0)) {
         return true;
      } else {
         String[] ☃ = var0.split(":");
         if (☃.length == 0) {
            return true;
         } else {
            try {
               String ☃ = IDN.toASCII(☃[0]);
               return true;
            } catch (IllegalArgumentException var3) {
               return false;
            }
         }
      }
   };

   public GuiScreenAddServer(GuiScreen var1, ServerData var2) {
      this.field_146310_a = ☃;
      this.field_146311_h = ☃;
   }

   @Override
   public void func_73876_c() {
      this.field_146309_g.func_146178_a();
      this.field_146308_f.func_146178_a();
   }

   @Override
   public IGuiEventListener getFocused() {
      return this.field_146308_f.func_146206_l() ? this.field_146308_f : this.field_146309_g;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146297_k.field_195559_v.func_197967_a(true);
      this.field_195179_a = this.func_189646_b(
         new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96 + 18, I18n.func_135052_a("addServer.add")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiScreenAddServer.this.func_195172_h();
            }
         }
      );
      this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 18, I18n.func_135052_a("gui.cancel")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiScreenAddServer.this.field_146310_a.confirmResult(false, 0);
         }
      });
      this.field_152176_i = this.func_189646_b(
         new GuiButton(
            2,
            this.field_146294_l / 2 - 100,
            this.field_146295_m / 4 + 72,
            I18n.func_135052_a("addServer.resourcePack") + ": " + this.field_146311_h.func_152586_b().func_152589_a().func_150254_d()
         ) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiScreenAddServer.this.field_146311_h
                  .func_152584_a(
                     ServerData.ServerResourceMode.values()[(GuiScreenAddServer.this.field_146311_h.func_152586_b().ordinal() + 1)
                        % ServerData.ServerResourceMode.values().length]
                  );
               GuiScreenAddServer.this.field_152176_i.field_146126_j = I18n.func_135052_a("addServer.resourcePack")
                  + ": "
                  + GuiScreenAddServer.this.field_146311_h.func_152586_b().func_152589_a().func_150254_d();
            }
         }
      );
      this.field_146308_f = new GuiTextField(1, this.field_146289_q, this.field_146294_l / 2 - 100, 106, 200, 20) {
         @Override
         public void func_146195_b(boolean var1) {
            super.func_146195_b(☃);
            if (☃) {
               GuiScreenAddServer.this.field_146309_g.func_146195_b(false);
            }
         }
      };
      this.field_146308_f.func_146203_f(128);
      this.field_146308_f.func_146180_a(this.field_146311_h.field_78845_b);
      this.field_146308_f.func_200675_a(this.field_181032_r);
      this.field_146308_f.func_195609_a(this::func_195171_a);
      this.field_195124_j.add(this.field_146308_f);
      this.field_146309_g = new GuiTextField(0, this.field_146289_q, this.field_146294_l / 2 - 100, 66, 200, 20) {
         @Override
         public void func_146195_b(boolean var1) {
            super.func_146195_b(☃);
            if (☃) {
               GuiScreenAddServer.this.field_146308_f.func_146195_b(false);
            }
         }
      };
      this.field_146309_g.func_146195_b(true);
      this.field_146309_g.func_146180_a(this.field_146311_h.field_78847_a);
      this.field_146309_g.func_195609_a(this::func_195171_a);
      this.field_195124_j.add(this.field_146309_g);
      this.func_195122_V_();
   }

   @Override
   public void func_175273_b(Minecraft var1, int var2, int var3) {
      String ☃ = this.field_146308_f.func_146179_b();
      String ☃x = this.field_146309_g.func_146179_b();
      this.func_146280_a(☃, ☃, ☃);
      this.field_146308_f.func_146180_a(☃);
      this.field_146309_g.func_146180_a(☃x);
   }

   private void func_195171_a(int var1, String var2) {
      this.func_195122_V_();
   }

   @Override
   public void func_146281_b() {
      this.field_146297_k.field_195559_v.func_197967_a(false);
   }

   private void func_195172_h() {
      this.field_146311_h.field_78847_a = this.field_146309_g.func_146179_b();
      this.field_146311_h.field_78845_b = this.field_146308_f.func_146179_b();
      this.field_146310_a.confirmResult(true, 0);
   }

   @Override
   public void func_195122_V_() {
      this.field_195179_a.field_146124_l = !this.field_146308_f.func_146179_b().isEmpty()
         && this.field_146308_f.func_146179_b().split(":").length > 0
         && !this.field_146309_g.func_146179_b().isEmpty();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (☃ == 258) {
         if (this.field_146309_g.func_146206_l()) {
            this.field_146308_f.func_146195_b(true);
         } else {
            this.field_146309_g.func_146195_b(true);
         }

         return true;
      } else if ((☃ == 257 || ☃ == 335) && this.field_195179_a.field_146124_l) {
         this.func_195172_h();
         return true;
      } else {
         return super.keyPressed(☃, ☃, ☃);
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("addServer.title"), this.field_146294_l / 2, 17, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("addServer.enterName"), this.field_146294_l / 2 - 100, 53, 10526880);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("addServer.enterIp"), this.field_146294_l / 2 - 100, 94, 10526880);
      this.field_146309_g.func_195608_a(☃, ☃, ☃);
      this.field_146308_f.func_195608_a(☃, ☃, ☃);
      super.func_73863_a(☃, ☃, ☃);
   }
}
