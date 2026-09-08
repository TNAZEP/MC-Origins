package net.minecraft.client.gui;

import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;

public class ServerListEntryNormal extends ServerSelectionList.Entry {
   private static final Logger field_148304_a = LogManager.getLogger();
   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(
      5,
      new ThreadFactoryBuilder()
         .setNameFormat("Server Pinger #%d")
         .setDaemon(true)
         .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_148304_a))
         .build()
   );
   private static final ResourceLocation field_178015_c = new ResourceLocation("textures/misc/unknown_server.png");
   private static final ResourceLocation field_178014_d = new ResourceLocation("textures/gui/server_selection.png");
   private final GuiMultiplayer field_148303_c;
   private final Minecraft field_148300_d;
   private final ServerData field_148301_e;
   private final ResourceLocation field_148306_i;
   private String field_148299_g;
   private DynamicTexture field_148305_h;
   private long field_148298_f;

   protected ServerListEntryNormal(GuiMultiplayer var1, ServerData var2) {
      this.field_148303_c = ☃;
      this.field_148301_e = ☃;
      this.field_148300_d = Minecraft.func_71410_x();
      this.field_148306_i = new ResourceLocation("servers/" + Hashing.sha1().hashUnencodedChars(☃.field_78845_b) + "/icon");
      this.field_148305_h = (DynamicTexture)this.field_148300_d.func_110434_K().func_110581_b(this.field_148306_i);
   }

   @Override
   public void func_194999_a(int var1, int var2, int var3, int var4, boolean var5, float var6) {
      int ☃ = this.func_195001_c();
      int ☃x = this.func_195002_d();
      if (!this.field_148301_e.field_78841_f) {
         this.field_148301_e.field_78841_f = true;
         this.field_148301_e.field_78844_e = -2L;
         this.field_148301_e.field_78843_d = "";
         this.field_148301_e.field_78846_c = "";
         field_148302_b.submit(() -> {
            try {
               this.field_148303_c.func_146789_i().func_147224_a(this.field_148301_e);
            } catch (UnknownHostException var2xx) {
               this.field_148301_e.field_78844_e = -1L;
               this.field_148301_e.field_78843_d = TextFormatting.DARK_RED + I18n.func_135052_a("multiplayer.status.cannot_resolve");
            } catch (Exception var3xx) {
               this.field_148301_e.field_78844_e = -1L;
               this.field_148301_e.field_78843_d = TextFormatting.DARK_RED + I18n.func_135052_a("multiplayer.status.cannot_connect");
            }
         });
      }

      boolean ☃ = this.field_148301_e.field_82821_f > 404;
      boolean ☃x = this.field_148301_e.field_82821_f < 404;
      boolean ☃xx = ☃ || ☃x;
      this.field_148300_d.field_71466_p.func_211126_b(this.field_148301_e.field_78847_a, (float)(☃x + 32 + 3), (float)(☃ + 1), 16777215);
      List<String> ☃xxx = this.field_148300_d.field_71466_p.func_78271_c(this.field_148301_e.field_78843_d, ☃ - 32 - 2);

      for(int ☃xxxx = 0; ☃xxxx < Math.min(☃xxx.size(), 2); ++☃xxxx) {
         this.field_148300_d
            .field_71466_p
            .func_211126_b((String)☃xxx.get(☃xxxx), (float)(☃x + 32 + 3), (float)(☃ + 12 + this.field_148300_d.field_71466_p.field_78288_b * ☃xxxx), 8421504);
      }

      String ☃xxxxxx = ☃xx ? TextFormatting.DARK_RED + this.field_148301_e.field_82822_g : this.field_148301_e.field_78846_c;
      int ☃xxxxxxx = this.field_148300_d.field_71466_p.func_78256_a(☃xxxxxx);
      this.field_148300_d.field_71466_p.func_211126_b(☃xxxxxx, (float)(☃x + ☃ - ☃xxxxxxx - 15 - 2), (float)(☃ + 1), 8421504);
      int ☃xxxxxxxx = 0;
      String ☃xxxxxxxxx = null;
      int ☃xxxx;
      String ☃xxxxx;
      if (☃xx) {
         ☃xxxx = 5;
         ☃xxxxx = I18n.func_135052_a(☃ ? "multiplayer.status.client_out_of_date" : "multiplayer.status.server_out_of_date");
         ☃xxxxxxxxx = this.field_148301_e.field_147412_i;
      } else if (this.field_148301_e.field_78841_f && this.field_148301_e.field_78844_e != -2L) {
         if (this.field_148301_e.field_78844_e < 0L) {
            ☃xxxx = 5;
         } else if (this.field_148301_e.field_78844_e < 150L) {
            ☃xxxx = 0;
         } else if (this.field_148301_e.field_78844_e < 300L) {
            ☃xxxx = 1;
         } else if (this.field_148301_e.field_78844_e < 600L) {
            ☃xxxx = 2;
         } else if (this.field_148301_e.field_78844_e < 1000L) {
            ☃xxxx = 3;
         } else {
            ☃xxxx = 4;
         }

         if (this.field_148301_e.field_78844_e < 0L) {
            ☃xxxxx = I18n.func_135052_a("multiplayer.status.no_connection");
         } else {
            ☃xxxxx = this.field_148301_e.field_78844_e + "ms";
            ☃xxxxxxxxx = this.field_148301_e.field_147412_i;
         }
      } else {
         ☃xxxxxxxx = 1;
         ☃xxxx = (int)(Util.func_211177_b() / 100L + (long)(this.func_195003_b() * 2) & 7L);
         if (☃xxxx > 4) {
            ☃xxxx = 8 - ☃xxxx;
         }

         ☃xxxxx = I18n.func_135052_a("multiplayer.status.pinging");
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_148300_d.func_110434_K().func_110577_a(Gui.field_110324_m);
      Gui.func_146110_a(☃x + ☃ - 15, ☃, (float)(☃xxxxxxxx * 10), (float)(176 + ☃xxxx * 8), 10, 8, 256.0F, 256.0F);
      if (this.field_148301_e.func_147409_e() != null && !this.field_148301_e.func_147409_e().equals(this.field_148299_g)) {
         this.field_148299_g = this.field_148301_e.func_147409_e();
         this.func_148297_b();
         this.field_148303_c.func_146795_p().func_78855_b();
      }

      if (this.field_148305_h != null) {
         this.func_178012_a(☃x, ☃, this.field_148306_i);
      } else {
         this.func_178012_a(☃x, ☃, field_178015_c);
      }

      int ☃xxxx = ☃ - ☃x;
      int ☃xxxxx = ☃ - ☃;
      if (☃xxxx >= ☃ - 15 && ☃xxxx <= ☃ - 5 && ☃xxxxx >= 0 && ☃xxxxx <= 8) {
         this.field_148303_c.func_146793_a(☃xxxxx);
      } else if (☃xxxx >= ☃ - ☃xxxxxxx - 15 - 2 && ☃xxxx <= ☃ - 15 - 2 && ☃xxxxx >= 0 && ☃xxxxx <= 8) {
         this.field_148303_c.func_146793_a(☃xxxxxxxxx);
      }

      if (this.field_148300_d.field_71474_y.field_85185_A || ☃) {
         this.field_148300_d.func_110434_K().func_110577_a(field_178014_d);
         Gui.func_73734_a(☃x, ☃, ☃x + 32, ☃ + 32, -1601138544);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         int ☃xxxx = ☃ - ☃x;
         int ☃xxxxx = ☃ - ☃;
         if (this.func_178013_b()) {
            if (☃xxxx < 32 && ☃xxxx > 16) {
               Gui.func_146110_a(☃x, ☃, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(☃x, ☃, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.field_148303_c.func_175392_a(this, this.func_195003_b())) {
            if (☃xxxx < 16 && ☃xxxxx < 16) {
               Gui.func_146110_a(☃x, ☃, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(☃x, ☃, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.field_148303_c.func_175394_b(this, this.func_195003_b())) {
            if (☃xxxx < 16 && ☃xxxxx > 16) {
               Gui.func_146110_a(☃x, ☃, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(☃x, ☃, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }
      }
   }

   protected void func_178012_a(int var1, int var2, ResourceLocation var3) {
      this.field_148300_d.func_110434_K().func_110577_a(☃);
      GlStateManager.func_179147_l();
      Gui.func_146110_a(☃, ☃, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      GlStateManager.func_179084_k();
   }

   private boolean func_178013_b() {
      return true;
   }

   private void func_148297_b() {
      if (this.field_148301_e.func_147409_e() == null) {
         this.field_148300_d.func_110434_K().func_147645_c(this.field_148306_i);
         this.field_148305_h.func_195414_e().close();
         this.field_148305_h = null;
      } else {
         try (MemoryStack ☃ = MemoryStack.stackPush()) {
            ByteBuffer ☃x = ☃.UTF8(this.field_148301_e.func_147409_e(), false);
            ByteBuffer ☃xx = Base64.getDecoder().decode(☃x);
            ByteBuffer ☃xxx = ☃.malloc(☃xx.remaining());
            ☃xxx.put(☃xx);
            ☃xxx.rewind();
            NativeImage ☃xxxx = NativeImage.func_195704_a(☃xxx);
            Validate.validState(☃xxxx.func_195702_a() == 64, "Must be 64 pixels wide");
            Validate.validState(☃xxxx.func_195714_b() == 64, "Must be 64 pixels high");
            if (this.field_148305_h == null) {
               this.field_148305_h = new DynamicTexture(☃xxxx);
            } else {
               this.field_148305_h.func_195415_a(☃xxxx);
               this.field_148305_h.func_110564_a();
            }

            this.field_148300_d.func_110434_K().func_110579_a(this.field_148306_i, this.field_148305_h);
         } catch (Throwable var17) {
            field_148304_a.error("Invalid icon for server {} ({})", this.field_148301_e.field_78847_a, this.field_148301_e.field_78845_b, var17);
            this.field_148301_e.func_147407_a(null);
         }
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      double ☃ = ☃ - (double)this.func_195002_d();
      double ☃x = ☃ - (double)this.func_195001_c();
      if (☃ <= 32.0) {
         if (☃ < 32.0 && ☃ > 16.0 && this.func_178013_b()) {
            this.field_148303_c.func_146790_a(this.func_195003_b());
            this.field_148303_c.func_146796_h();
            return true;
         }

         if (☃ < 16.0 && ☃x < 16.0 && this.field_148303_c.func_175392_a(this, this.func_195003_b())) {
            this.field_148303_c.func_175391_a(this, this.func_195003_b(), GuiScreen.func_146272_n());
            return true;
         }

         if (☃ < 16.0 && ☃x > 16.0 && this.field_148303_c.func_175394_b(this, this.func_195003_b())) {
            this.field_148303_c.func_175393_b(this, this.func_195003_b(), GuiScreen.func_146272_n());
            return true;
         }
      }

      this.field_148303_c.func_146790_a(this.func_195003_b());
      if (Util.func_211177_b() - this.field_148298_f < 250L) {
         this.field_148303_c.func_146796_h();
      }

      this.field_148298_f = Util.func_211177_b();
      return false;
   }

   public ServerData func_148296_a() {
      return this.field_148301_e;
   }
}
