package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiMultiplayer extends GuiScreen {
   private static final Logger field_146802_a = LogManager.getLogger();
   private final ServerPinger field_146797_f = new ServerPinger();
   private final GuiScreen field_146798_g;
   private ServerSelectionList field_146803_h;
   private ServerList field_146804_i;
   private GuiButton field_146810_r;
   private GuiButton field_146809_s;
   private GuiButton field_146808_t;
   private boolean field_146807_u;
   private boolean field_146806_v;
   private boolean field_146805_w;
   private boolean field_146813_x;
   private String field_146812_y;
   private ServerData field_146811_z;
   private LanServerDetector.LanServerList field_146799_A;
   private LanServerDetector.ThreadLanServerFind field_146800_B;
   private boolean field_146801_C;

   public GuiMultiplayer(GuiScreen var1) {
      this.field_146798_g = ☃;
   }

   @Override
   public IGuiEventListener getFocused() {
      return this.field_146803_h;
   }

   @Override
   protected void func_73866_w_() {
      super.func_73866_w_();
      this.field_146297_k.field_195559_v.func_197967_a(true);
      if (this.field_146801_C) {
         this.field_146803_h.func_148122_a(this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 64);
      } else {
         this.field_146801_C = true;
         this.field_146804_i = new ServerList(this.field_146297_k);
         this.field_146804_i.func_78853_a();
         this.field_146799_A = new LanServerDetector.LanServerList();

         try {
            this.field_146800_B = new LanServerDetector.ThreadLanServerFind(this.field_146799_A);
            this.field_146800_B.start();
         } catch (Exception var2) {
            field_146802_a.warn("Unable to start LAN server detection: {}", var2.getMessage());
         }

         this.field_146803_h = new ServerSelectionList(this, this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 64, 36);
         this.field_146803_h.func_148195_a(this.field_146804_i);
      }

      this.func_146794_g();
   }

   public void func_146794_g() {
      this.field_146810_r = this.func_189646_b(
         new GuiButton(7, this.field_146294_l / 2 - 154, this.field_146295_m - 28, 70, 20, I18n.func_135052_a("selectServer.edit")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiListExtended.IGuiListEntry<?> ☃ = GuiMultiplayer.this.field_146803_h.func_148193_k() < 0
                  ? null
                  : (GuiListExtended.IGuiListEntry)GuiMultiplayer.this.field_146803_h.func_195074_b().get(GuiMultiplayer.this.field_146803_h.func_148193_k());
               GuiMultiplayer.this.field_146805_w = true;
               if (☃ instanceof ServerListEntryNormal) {
                  ServerData ☃x = ((ServerListEntryNormal)☃).func_148296_a();
                  GuiMultiplayer.this.field_146811_z = new ServerData(☃x.field_78847_a, ☃x.field_78845_b, false);
                  GuiMultiplayer.this.field_146811_z.func_152583_a(☃x);
                  GuiMultiplayer.this.field_146297_k.func_147108_a(new GuiScreenAddServer(GuiMultiplayer.this, GuiMultiplayer.this.field_146811_z));
               }
            }
         }
      );
      this.field_146808_t = this.func_189646_b(
         new GuiButton(2, this.field_146294_l / 2 - 74, this.field_146295_m - 28, 70, 20, I18n.func_135052_a("selectServer.delete")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiListExtended.IGuiListEntry<?> ☃ = GuiMultiplayer.this.field_146803_h.func_148193_k() < 0
                  ? null
                  : (GuiListExtended.IGuiListEntry)GuiMultiplayer.this.field_146803_h.func_195074_b().get(GuiMultiplayer.this.field_146803_h.func_148193_k());
               if (☃ instanceof ServerListEntryNormal) {
                  String ☃x = ((ServerListEntryNormal)☃).func_148296_a().field_78847_a;
                  if (☃x != null) {
                     GuiMultiplayer.this.field_146807_u = true;
                     String ☃xx = I18n.func_135052_a("selectServer.deleteQuestion");
                     String ☃xxx = I18n.func_135052_a("selectServer.deleteWarning", ☃x);
                     String ☃xxxx = I18n.func_135052_a("selectServer.deleteButton");
                     String ☃xxxxx = I18n.func_135052_a("gui.cancel");
                     GuiYesNo ☃xxxxxx = new GuiYesNo(GuiMultiplayer.this, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, GuiMultiplayer.this.field_146803_h.func_148193_k());
                     GuiMultiplayer.this.field_146297_k.func_147108_a(☃xxxxxx);
                  }
               }
            }
         }
      );
      this.field_146809_s = this.func_189646_b(
         new GuiButton(1, this.field_146294_l / 2 - 154, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("selectServer.select")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiMultiplayer.this.func_146796_h();
            }
         }
      );
      this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 - 50, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("selectServer.direct")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMultiplayer.this.field_146813_x = true;
            GuiMultiplayer.this.field_146811_z = new ServerData(I18n.func_135052_a("selectServer.defaultName"), "", false);
            GuiMultiplayer.this.field_146297_k.func_147108_a(new GuiScreenServerList(GuiMultiplayer.this, GuiMultiplayer.this.field_146811_z));
         }
      });
      this.func_189646_b(new GuiButton(3, this.field_146294_l / 2 + 4 + 50, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("selectServer.add")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMultiplayer.this.field_146806_v = true;
            GuiMultiplayer.this.field_146811_z = new ServerData(I18n.func_135052_a("selectServer.defaultName"), "", false);
            GuiMultiplayer.this.field_146297_k.func_147108_a(new GuiScreenAddServer(GuiMultiplayer.this, GuiMultiplayer.this.field_146811_z));
         }
      });
      this.func_189646_b(new GuiButton(8, this.field_146294_l / 2 + 4, this.field_146295_m - 28, 70, 20, I18n.func_135052_a("selectServer.refresh")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMultiplayer.this.func_146792_q();
         }
      });
      this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 + 4 + 76, this.field_146295_m - 28, 75, 20, I18n.func_135052_a("gui.cancel")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMultiplayer.this.field_146297_k.func_147108_a(GuiMultiplayer.this.field_146798_g);
         }
      });
      this.field_195124_j.add(this.field_146803_h);
      this.func_146790_a(this.field_146803_h.func_148193_k());
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      if (this.field_146799_A.func_77553_a()) {
         List<LanServerInfo> ☃ = this.field_146799_A.func_77554_c();
         this.field_146799_A.func_77552_b();
         this.field_146803_h.func_148194_a(☃);
      }

      this.field_146797_f.func_147223_a();
   }

   @Override
   public void func_146281_b() {
      this.field_146297_k.field_195559_v.func_197967_a(false);
      if (this.field_146800_B != null) {
         this.field_146800_B.interrupt();
         this.field_146800_B = null;
      }

      this.field_146797_f.func_147226_b();
   }

   private void func_146792_q() {
      this.field_146297_k.func_147108_a(new GuiMultiplayer(this.field_146798_g));
   }

   @Override
   public void confirmResult(boolean var1, int var2) {
      GuiListExtended.IGuiListEntry<?> ☃ = this.field_146803_h.func_148193_k() < 0
         ? null
         : (GuiListExtended.IGuiListEntry)this.field_146803_h.func_195074_b().get(this.field_146803_h.func_148193_k());
      if (this.field_146807_u) {
         this.field_146807_u = false;
         if (☃ && ☃ instanceof ServerListEntryNormal) {
            this.field_146804_i.func_78851_b(this.field_146803_h.func_148193_k());
            this.field_146804_i.func_78855_b();
            this.field_146803_h.func_148192_c(-1);
            this.field_146803_h.func_148195_a(this.field_146804_i);
         }

         this.field_146297_k.func_147108_a(this);
      } else if (this.field_146813_x) {
         this.field_146813_x = false;
         if (☃) {
            this.func_146791_a(this.field_146811_z);
         } else {
            this.field_146297_k.func_147108_a(this);
         }
      } else if (this.field_146806_v) {
         this.field_146806_v = false;
         if (☃) {
            this.field_146804_i.func_78849_a(this.field_146811_z);
            this.field_146804_i.func_78855_b();
            this.field_146803_h.func_148192_c(-1);
            this.field_146803_h.func_148195_a(this.field_146804_i);
         }

         this.field_146297_k.func_147108_a(this);
      } else if (this.field_146805_w) {
         this.field_146805_w = false;
         if (☃ && ☃ instanceof ServerListEntryNormal) {
            ServerData ☃ = ((ServerListEntryNormal)☃).func_148296_a();
            ☃.field_78847_a = this.field_146811_z.field_78847_a;
            ☃.field_78845_b = this.field_146811_z.field_78845_b;
            ☃.func_152583_a(this.field_146811_z);
            this.field_146804_i.func_78855_b();
            this.field_146803_h.func_148195_a(this.field_146804_i);
         }

         this.field_146297_k.func_147108_a(this);
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      int ☃ = this.field_146803_h.func_148193_k();
      GuiListExtended.IGuiListEntry<?> ☃x = ☃ < 0 ? null : (GuiListExtended.IGuiListEntry)this.field_146803_h.func_195074_b().get(☃);
      if (☃ == 294) {
         this.func_146792_q();
         return true;
      } else {
         if (☃ >= 0) {
            if (☃ == 265) {
               if (func_146272_n()) {
                  if (☃ > 0 && ☃x instanceof ServerListEntryNormal) {
                     this.field_146804_i.func_78857_a(☃, ☃ - 1);
                     this.func_146790_a(this.field_146803_h.func_148193_k() - 1);
                     this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
                     this.field_146803_h.func_148195_a(this.field_146804_i);
                  }
               } else if (☃ > 0) {
                  this.func_146790_a(this.field_146803_h.func_148193_k() - 1);
                  this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
                  if (this.field_146803_h.func_195074_b().get(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan) {
                     if (this.field_146803_h.func_148193_k() > 0) {
                        this.func_146790_a(this.field_146803_h.func_195074_b().size() - 1);
                        this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
                     } else {
                        this.func_146790_a(-1);
                     }
                  }
               } else {
                  this.func_146790_a(-1);
               }

               return true;
            }

            if (☃ == 264) {
               if (func_146272_n()) {
                  if (☃ < this.field_146804_i.func_78856_c() - 1) {
                     this.field_146804_i.func_78857_a(☃, ☃ + 1);
                     this.func_146790_a(☃ + 1);
                     this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                     this.field_146803_h.func_148195_a(this.field_146804_i);
                  }
               } else if (☃ < this.field_146803_h.func_195074_b().size()) {
                  this.func_146790_a(this.field_146803_h.func_148193_k() + 1);
                  this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                  if (this.field_146803_h.func_195074_b().get(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan) {
                     if (this.field_146803_h.func_148193_k() < this.field_146803_h.func_195074_b().size() - 1) {
                        this.func_146790_a(this.field_146803_h.func_195074_b().size() + 1);
                        this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                     } else {
                        this.func_146790_a(-1);
                     }
                  }
               } else {
                  this.func_146790_a(-1);
               }

               return true;
            }

            if (☃ == 257 || ☃ == 335) {
               this.func_146796_h();
               return true;
            }
         }

         return super.keyPressed(☃, ☃, ☃);
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.field_146812_y = null;
      this.func_146276_q_();
      this.field_146803_h.func_148128_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("multiplayer.title"), this.field_146294_l / 2, 20, 16777215);
      super.func_73863_a(☃, ☃, ☃);
      if (this.field_146812_y != null) {
         this.func_146283_a(Lists.newArrayList(Splitter.on("\n").split(this.field_146812_y)), ☃, ☃);
      }
   }

   public void func_146796_h() {
      GuiListExtended.IGuiListEntry<?> ☃ = this.field_146803_h.func_148193_k() < 0
         ? null
         : (GuiListExtended.IGuiListEntry)this.field_146803_h.func_195074_b().get(this.field_146803_h.func_148193_k());
      if (☃ instanceof ServerListEntryNormal) {
         this.func_146791_a(((ServerListEntryNormal)☃).func_148296_a());
      } else if (☃ instanceof ServerListEntryLanDetected) {
         LanServerInfo ☃ = ((ServerListEntryLanDetected)☃).func_189995_a();
         this.func_146791_a(new ServerData(☃.func_77487_a(), ☃.func_77488_b(), true));
      }
   }

   private void func_146791_a(ServerData var1) {
      this.field_146297_k.func_147108_a(new GuiConnecting(this, this.field_146297_k, ☃));
   }

   public void func_146790_a(int var1) {
      this.field_146803_h.func_148192_c(☃);
      GuiListExtended.IGuiListEntry<?> ☃ = ☃ < 0 ? null : (GuiListExtended.IGuiListEntry)this.field_146803_h.func_195074_b().get(☃);
      this.field_146809_s.field_146124_l = false;
      this.field_146810_r.field_146124_l = false;
      this.field_146808_t.field_146124_l = false;
      if (☃ != null && !(☃ instanceof ServerListEntryLanScan)) {
         this.field_146809_s.field_146124_l = true;
         if (☃ instanceof ServerListEntryNormal) {
            this.field_146810_r.field_146124_l = true;
            this.field_146808_t.field_146124_l = true;
         }
      }
   }

   public ServerPinger func_146789_i() {
      return this.field_146797_f;
   }

   public void func_146793_a(String var1) {
      this.field_146812_y = ☃;
   }

   public ServerList func_146795_p() {
      return this.field_146804_i;
   }

   public boolean func_175392_a(ServerListEntryNormal var1, int var2) {
      return ☃ > 0;
   }

   public boolean func_175394_b(ServerListEntryNormal var1, int var2) {
      return ☃ < this.field_146804_i.func_78856_c() - 1;
   }

   public void func_175391_a(ServerListEntryNormal var1, int var2, boolean var3) {
      int ☃ = ☃ ? 0 : ☃ - 1;
      this.field_146804_i.func_78857_a(☃, ☃);
      if (this.field_146803_h.func_148193_k() == ☃) {
         this.func_146790_a(☃);
      }

      this.field_146803_h.func_148195_a(this.field_146804_i);
   }

   public void func_175393_b(ServerListEntryNormal var1, int var2, boolean var3) {
      int ☃ = ☃ ? this.field_146804_i.func_78856_c() - 1 : ☃ + 1;
      this.field_146804_i.func_78857_a(☃, ☃);
      if (this.field_146803_h.func_148193_k() == ☃) {
         this.func_146790_a(☃);
      }

      this.field_146803_h.func_148195_a(this.field_146804_i);
   }
}
