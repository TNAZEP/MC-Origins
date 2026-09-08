package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Runnables;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL;

public class GuiMainMenu extends GuiScreen {
   private static final Random field_175374_h = new Random();
   private final float field_73974_b;
   private String field_73975_c;
   private GuiButton field_195209_i;
   private GuiButton field_73973_d;
   private final Object field_104025_t = new Object();
   public static final String field_96138_a = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
   private int field_92024_r;
   private int field_92023_s;
   private int field_92022_t;
   private int field_92021_u;
   private int field_92020_v;
   private int field_92019_w;
   private String field_92025_p;
   private String field_146972_A = field_96138_a;
   private String field_104024_v;
   private static final ResourceLocation field_110353_x = new ResourceLocation("texts/splashes.txt");
   private static final ResourceLocation field_110352_y = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation field_194400_H = new ResourceLocation("textures/gui/title/edition.png");
   private boolean field_183502_L;
   private GuiScreen field_183503_M;
   private int field_193978_M;
   private int field_193979_N;
   private final RenderSkybox field_209101_K = new RenderSkybox(new RenderSkyboxCube(new ResourceLocation("textures/gui/title/background/panorama")));

   public GuiMainMenu() {
      this.field_73975_c = "missingno";
      IResource ☃ = null;

      try {
         List<String> ☃x = Lists.newArrayList();
         ☃ = Minecraft.func_71410_x().func_195551_G().func_199002_a(field_110353_x);
         BufferedReader ☃xx = new BufferedReader(new InputStreamReader(☃.func_199027_b(), StandardCharsets.UTF_8));

         String ☃;
         while((☃ = ☃xx.readLine()) != null) {
            ☃ = ☃.trim();
            if (!☃.isEmpty()) {
               ☃x.add(☃);
            }
         }

         if (!☃x.isEmpty()) {
            do {
               this.field_73975_c = (String)☃x.get(field_175374_h.nextInt(☃x.size()));
            } while(this.field_73975_c.hashCode() == 125780783);
         }
      } catch (IOException var8) {
      } finally {
         IOUtils.closeQuietly(☃);
      }

      this.field_73974_b = field_175374_h.nextFloat();
      this.field_92025_p = "";
      if (!GL.getCapabilities().OpenGL20 && !OpenGlHelper.func_153193_b()) {
         this.field_92025_p = I18n.func_135052_a("title.oldgl1");
         this.field_146972_A = I18n.func_135052_a("title.oldgl2");
         this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
      }
   }

   private boolean func_183501_a() {
      return Minecraft.func_71410_x().field_71474_y.func_74308_b(GameSettings.Options.REALMS_NOTIFICATIONS) && this.field_183503_M != null;
   }

   @Override
   public void func_73876_c() {
      if (this.func_183501_a()) {
         this.field_183503_M.func_73876_c();
      }
   }

   @Override
   public boolean func_73868_f() {
      return false;
   }

   @Override
   public boolean func_195120_Y_() {
      return false;
   }

   @Override
   protected void func_73866_w_() {
      this.field_193978_M = this.field_146289_q.func_78256_a("Copyright Mojang AB. Do not distribute!");
      this.field_193979_N = this.field_146294_l - this.field_193978_M - 2;
      Calendar ☃ = Calendar.getInstance();
      ☃.setTime(new Date());
      if (☃.get(2) + 1 == 12 && ☃.get(5) == 24) {
         this.field_73975_c = "Merry X-mas!";
      } else if (☃.get(2) + 1 == 1 && ☃.get(5) == 1) {
         this.field_73975_c = "Happy new year!";
      } else if (☃.get(2) + 1 == 10 && ☃.get(5) == 31) {
         this.field_73975_c = "OOoooOOOoooo! Spooky!";
      }

      int ☃ = 24;
      int ☃x = this.field_146295_m / 4 + 48;
      if (this.field_146297_k.func_71355_q()) {
         this.func_73972_b(☃x, 24);
      } else {
         this.func_73969_a(☃x, 24);
      }

      this.field_195209_i = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 100, ☃x + 72 + 12, 98, 20, I18n.func_135052_a("menu.options")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMainMenu.this.field_146297_k.func_147108_a(new GuiOptions(GuiMainMenu.this, GuiMainMenu.this.field_146297_k.field_71474_y));
         }
      });
      this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 + 2, ☃x + 72 + 12, 98, 20, I18n.func_135052_a("menu.quit")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMainMenu.this.field_146297_k.func_71400_g();
         }
      });
      this.func_189646_b(
         new GuiButtonLanguage(5, this.field_146294_l / 2 - 124, ☃x + 72 + 12) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiMainMenu.this.field_146297_k
                  .func_147108_a(
                     new GuiLanguage(GuiMainMenu.this, GuiMainMenu.this.field_146297_k.field_71474_y, GuiMainMenu.this.field_146297_k.func_135016_M())
                  );
            }
         }
      );
      synchronized(this.field_104025_t) {
         this.field_92023_s = this.field_146289_q.func_78256_a(this.field_92025_p);
         this.field_92024_r = this.field_146289_q.func_78256_a(this.field_146972_A);
         int ☃ = Math.max(this.field_92023_s, this.field_92024_r);
         this.field_92022_t = (this.field_146294_l - ☃) / 2;
         this.field_92021_u = ☃x - 24;
         this.field_92020_v = this.field_92022_t + ☃;
         this.field_92019_w = this.field_92021_u + 24;
      }

      this.field_146297_k.func_181537_a(false);
      if (Minecraft.func_71410_x().field_71474_y.func_74308_b(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.field_183502_L) {
         RealmsBridge ☃ = new RealmsBridge();
         this.field_183503_M = ☃.getNotificationScreen(this);
         this.field_183502_L = true;
      }

      if (this.func_183501_a()) {
         this.field_183503_M.func_146280_a(this.field_146297_k, this.field_146294_l, this.field_146295_m);
      }
   }

   private void func_73969_a(int var1, int var2) {
      this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 - 100, ☃, I18n.func_135052_a("menu.singleplayer")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMainMenu.this.field_146297_k.func_147108_a(new GuiWorldSelection(GuiMainMenu.this));
         }
      });
      this.func_189646_b(new GuiButton(2, this.field_146294_l / 2 - 100, ☃ + ☃ * 1, I18n.func_135052_a("menu.multiplayer")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMainMenu.this.field_146297_k.func_147108_a(new GuiMultiplayer(GuiMainMenu.this));
         }
      });
      this.func_189646_b(new GuiButton(14, this.field_146294_l / 2 - 100, ☃ + ☃ * 2, I18n.func_135052_a("menu.online")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMainMenu.this.func_140005_i();
         }
      });
   }

   private void func_73972_b(int var1, int var2) {
      this.func_189646_b(new GuiButton(11, this.field_146294_l / 2 - 100, ☃, I18n.func_135052_a("menu.playdemo")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiMainMenu.this.field_146297_k.func_71371_a("Demo_World", "Demo_World", WorldServerDemo.field_73071_a);
         }
      });
      this.field_73973_d = this.func_189646_b(
         new GuiButton(12, this.field_146294_l / 2 - 100, ☃ + ☃ * 1, I18n.func_135052_a("menu.resetdemo")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               ISaveFormat ☃ = GuiMainMenu.this.field_146297_k.func_71359_d();
               WorldInfo ☃x = ☃.func_75803_c("Demo_World");
               if (☃x != null) {
                  GuiMainMenu.this.field_146297_k
                     .func_147108_a(
                        new GuiYesNo(
                           GuiMainMenu.this,
                           I18n.func_135052_a("selectWorld.deleteQuestion"),
                           I18n.func_135052_a("selectWorld.deleteWarning", ☃x.func_76065_j()),
                           I18n.func_135052_a("selectWorld.deleteButton"),
                           I18n.func_135052_a("gui.cancel"),
                           12
                        )
                     );
               }
            }
         }
      );
      ISaveFormat ☃ = this.field_146297_k.func_71359_d();
      WorldInfo ☃x = ☃.func_75803_c("Demo_World");
      if (☃x == null) {
         this.field_73973_d.field_146124_l = false;
      }
   }

   private void func_140005_i() {
      RealmsBridge ☃ = new RealmsBridge();
      ☃.switchToRealms(this);
   }

   @Override
   public void confirmResult(boolean var1, int var2) {
      if (☃ && ☃ == 12) {
         ISaveFormat ☃ = this.field_146297_k.func_71359_d();
         ☃.func_75800_d();
         ☃.func_75802_e("Demo_World");
         this.field_146297_k.func_147108_a(this);
      } else if (☃ == 12) {
         this.field_146297_k.func_147108_a(this);
      } else if (☃ == 13) {
         if (☃) {
            Util.func_110647_a().func_195640_a(this.field_104024_v);
         }

         this.field_146297_k.func_147108_a(this);
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.field_209101_K.func_209144_a(☃);
      int ☃ = 274;
      int ☃x = this.field_146294_l / 2 - 137;
      int ☃xx = 30;
      this.field_146297_k.func_110434_K().func_110577_a(new ResourceLocation("textures/gui/title/background/panorama_overlay.png"));
      func_152125_a(0, 0, 0.0F, 0.0F, 16, 128, this.field_146294_l, this.field_146295_m, 16.0F, 128.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_110352_y);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      if ((double)this.field_73974_b < 1.0E-4) {
         this.func_73729_b(☃x + 0, 30, 0, 0, 99, 44);
         this.func_73729_b(☃x + 99, 30, 129, 0, 27, 44);
         this.func_73729_b(☃x + 99 + 26, 30, 126, 0, 3, 44);
         this.func_73729_b(☃x + 99 + 26 + 3, 30, 99, 0, 26, 44);
         this.func_73729_b(☃x + 155, 30, 0, 45, 155, 44);
      } else {
         this.func_73729_b(☃x + 0, 30, 0, 0, 155, 44);
         this.func_73729_b(☃x + 155, 30, 0, 45, 155, 44);
      }

      this.field_146297_k.func_110434_K().func_110577_a(field_194400_H);
      func_146110_a(☃x + 88, 67, 0.0F, 0.0F, 98, 14, 128.0F, 16.0F);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)(this.field_146294_l / 2 + 90), 70.0F, 0.0F);
      GlStateManager.func_179114_b(-20.0F, 0.0F, 0.0F, 1.0F);
      float ☃ = 1.8F - MathHelper.func_76135_e(MathHelper.func_76126_a((float)(Util.func_211177_b() % 1000L) / 1000.0F * (float) (Math.PI * 2)) * 0.1F);
      ☃ = ☃ * 100.0F / (float)(this.field_146289_q.func_78256_a(this.field_73975_c) + 32);
      GlStateManager.func_179152_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, this.field_73975_c, 0, -8, -256);
      GlStateManager.func_179121_F();
      String ☃x = "Minecraft 1.13.2";
      if (this.field_146297_k.func_71355_q()) {
         ☃x = ☃x + " Demo";
      } else {
         ☃x = ☃x + ("release".equalsIgnoreCase(this.field_146297_k.func_184123_d()) ? "" : "/" + this.field_146297_k.func_184123_d());
      }

      this.func_73731_b(this.field_146289_q, ☃x, 2, this.field_146295_m - 10, -1);
      this.func_73731_b(this.field_146289_q, "Copyright Mojang AB. Do not distribute!", this.field_193979_N, this.field_146295_m - 10, -1);
      if (☃ > this.field_193979_N && ☃ < this.field_193979_N + this.field_193978_M && ☃ > this.field_146295_m - 10 && ☃ < this.field_146295_m) {
         func_73734_a(this.field_193979_N, this.field_146295_m - 1, this.field_193979_N + this.field_193978_M, this.field_146295_m, -1);
      }

      if (this.field_92025_p != null && !this.field_92025_p.isEmpty()) {
         func_73734_a(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
         this.func_73731_b(this.field_146289_q, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
         this.func_73731_b(this.field_146289_q, this.field_146972_A, (this.field_146294_l - this.field_92024_r) / 2, this.field_92021_u + 12, -1);
      }

      super.func_73863_a(☃, ☃, ☃);
      if (this.func_183501_a()) {
         this.field_183503_M.func_73863_a(☃, ☃, ☃);
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (super.mouseClicked(☃, ☃, ☃)) {
         return true;
      } else {
         synchronized(this.field_104025_t) {
            if (!this.field_92025_p.isEmpty()
               && !StringUtils.func_151246_b(this.field_104024_v)
               && ☃ >= (double)this.field_92022_t
               && ☃ <= (double)this.field_92020_v
               && ☃ >= (double)this.field_92021_u
               && ☃ <= (double)this.field_92019_w) {
               GuiConfirmOpenLink ☃ = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
               ☃.func_146358_g();
               this.field_146297_k.func_147108_a(☃);
               return true;
            }
         }

         if (this.func_183501_a() && this.field_183503_M.mouseClicked(☃, ☃, ☃)) {
            return true;
         } else {
            if (☃ > (double)this.field_193979_N
               && ☃ < (double)(this.field_193979_N + this.field_193978_M)
               && ☃ > (double)(this.field_146295_m - 10)
               && ☃ < (double)this.field_146295_m) {
               this.field_146297_k.func_147108_a(new GuiWinGame(false, Runnables.doNothing()));
            }

            return false;
         }
      }
   }

   @Override
   public void func_146281_b() {
      if (this.field_183503_M != null) {
         this.field_183503_M.func_146281_b();
      }
   }
}
