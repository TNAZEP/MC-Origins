package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiNewChat extends Gui {
   private static final Logger field_146249_a = LogManager.getLogger();
   private final Minecraft field_146247_f;
   private final List<String> field_146248_g = Lists.newArrayList();
   private final List<ChatLine> field_146252_h = Lists.<ChatLine>newArrayList();
   private final List<ChatLine> field_146253_i = Lists.<ChatLine>newArrayList();
   private int field_146250_j;
   private boolean field_146251_k;

   public GuiNewChat(Minecraft var1) {
      this.field_146247_f = ☃;
   }

   public void func_146230_a(int var1) {
      if (this.field_146247_f.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN) {
         int ☃ = this.func_146232_i();
         int ☃x = this.field_146253_i.size();
         double ☃xx = this.field_146247_f.field_71474_y.field_74357_r * 0.9F + 0.1F;
         if (☃x > 0) {
            boolean ☃xxx = false;
            if (this.func_146241_e()) {
               ☃xxx = true;
            }

            double ☃xxx = this.func_194815_g();
            int ☃xxxx = MathHelper.func_76143_f((double)this.func_146228_f() / ☃xxx);
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(2.0F, 8.0F, 0.0F);
            GlStateManager.func_179139_a(☃xxx, ☃xxx, 1.0);
            int ☃xxxxx = 0;

            for(int ☃xxxxxx = 0; ☃xxxxxx + this.field_146250_j < this.field_146253_i.size() && ☃xxxxxx < ☃; ++☃xxxxxx) {
               ChatLine ☃xxxxxxx = (ChatLine)this.field_146253_i.get(☃xxxxxx + this.field_146250_j);
               if (☃xxxxxxx != null) {
                  int ☃xxxxxxxx = ☃ - ☃xxxxxxx.func_74540_b();
                  if (☃xxxxxxxx < 200 || ☃xxx) {
                     double ☃xxxxxxxxx = (double)☃xxxxxxxx / 200.0;
                     ☃xxxxxxxxx = 1.0 - ☃xxxxxxxxx;
                     ☃xxxxxxxxx *= 10.0;
                     ☃xxxxxxxxx = MathHelper.func_151237_a(☃xxxxxxxxx, 0.0, 1.0);
                     ☃xxxxxxxxx *= ☃xxxxxxxxx;
                     int ☃xxxxxxxxxx = (int)(255.0 * ☃xxxxxxxxx);
                     if (☃xxx) {
                        ☃xxxxxxxxxx = 255;
                     }

                     ☃xxxxxxxxxx = (int)((double)☃xxxxxxxxxx * ☃xx);
                     ++☃xxxxx;
                     if (☃xxxxxxxxxx > 3) {
                        int ☃xxxxxxxxx = 0;
                        int ☃xxxxxxxxxx = -☃xxxxxx * 9;
                        func_73734_a(-2, ☃xxxxxxxxxx - 9, 0 + ☃xxxx + 4, ☃xxxxxxxxxx, ☃xxxxxxxxxx / 2 << 24);
                        String ☃xxxxxxxxxxx = ☃xxxxxxx.func_151461_a().func_150254_d();
                        GlStateManager.func_179147_l();
                        this.field_146247_f.field_71466_p.func_175063_a(☃xxxxxxxxxxx, 0.0F, (float)(☃xxxxxxxxxx - 8), 16777215 + (☃xxxxxxxxxx << 24));
                        GlStateManager.func_179118_c();
                        GlStateManager.func_179084_k();
                     }
                  }
               }
            }

            if (☃xxx) {
               int ☃xxxxxx = this.field_146247_f.field_71466_p.field_78288_b;
               GlStateManager.func_179109_b(-3.0F, 0.0F, 0.0F);
               int ☃xxxxxxx = ☃x * ☃xxxxxx + ☃x;
               int ☃xxxxxxxx = ☃xxxxx * ☃xxxxxx + ☃xxxxx;
               int ☃xxxxxxxxx = this.field_146250_j * ☃xxxxxxxx / ☃x;
               int ☃xxxxxxxxxx = ☃xxxxxxxx * ☃xxxxxxxx / ☃xxxxxxx;
               if (☃xxxxxxx != ☃xxxxxxxx) {
                  int ☃xxxxxxxxxxx = ☃xxxxxxxxx > 0 ? 170 : 96;
                  int ☃xxxxxxxxxxxx = this.field_146251_k ? 13382451 : 3355562;
                  func_73734_a(0, -☃xxxxxxxxx, 2, -☃xxxxxxxxx - ☃xxxxxxxxxx, ☃xxxxxxxxxxxx + (☃xxxxxxxxxxx << 24));
                  func_73734_a(2, -☃xxxxxxxxx, 1, -☃xxxxxxxxx - ☃xxxxxxxxxx, 13421772 + (☃xxxxxxxxxxx << 24));
               }
            }

            GlStateManager.func_179121_F();
         }
      }
   }

   public void func_146231_a(boolean var1) {
      this.field_146253_i.clear();
      this.field_146252_h.clear();
      if (☃) {
         this.field_146248_g.clear();
      }
   }

   public void func_146227_a(ITextComponent var1) {
      this.func_146234_a(☃, 0);
   }

   public void func_146234_a(ITextComponent var1, int var2) {
      this.func_146237_a(☃, ☃, this.field_146247_f.field_71456_v.func_73834_c(), false);
      field_146249_a.info("[CHAT] {}", ☃.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
   }

   private void func_146237_a(ITextComponent var1, int var2, int var3, boolean var4) {
      if (☃ != 0) {
         this.func_146242_c(☃);
      }

      int ☃ = MathHelper.func_76128_c((double)this.func_146228_f() / this.func_194815_g());
      List<ITextComponent> ☃x = GuiUtilRenderComponents.func_178908_a(☃, ☃, this.field_146247_f.field_71466_p, false, false);
      boolean ☃xx = this.func_146241_e();

      for(ITextComponent ☃xxx : ☃x) {
         if (☃xx && this.field_146250_j > 0) {
            this.field_146251_k = true;
            this.func_194813_a(1.0);
         }

         this.field_146253_i.add(0, new ChatLine(☃, ☃xxx, ☃));
      }

      while(this.field_146253_i.size() > 100) {
         this.field_146253_i.remove(this.field_146253_i.size() - 1);
      }

      if (!☃) {
         this.field_146252_h.add(0, new ChatLine(☃, ☃, ☃));

         while(this.field_146252_h.size() > 100) {
            this.field_146252_h.remove(this.field_146252_h.size() - 1);
         }
      }
   }

   public void func_146245_b() {
      this.field_146253_i.clear();
      this.func_146240_d();

      for(int ☃ = this.field_146252_h.size() - 1; ☃ >= 0; --☃) {
         ChatLine ☃x = (ChatLine)this.field_146252_h.get(☃);
         this.func_146237_a(☃x.func_151461_a(), ☃x.func_74539_c(), ☃x.func_74540_b(), true);
      }
   }

   public List<String> func_146238_c() {
      return this.field_146248_g;
   }

   public void func_146239_a(String var1) {
      if (this.field_146248_g.isEmpty() || !((String)this.field_146248_g.get(this.field_146248_g.size() - 1)).equals(☃)) {
         this.field_146248_g.add(☃);
      }
   }

   public void func_146240_d() {
      this.field_146250_j = 0;
      this.field_146251_k = false;
   }

   public void func_194813_a(double var1) {
      this.field_146250_j = (int)((double)this.field_146250_j + ☃);
      int ☃ = this.field_146253_i.size();
      if (this.field_146250_j > ☃ - this.func_146232_i()) {
         this.field_146250_j = ☃ - this.func_146232_i();
      }

      if (this.field_146250_j <= 0) {
         this.field_146250_j = 0;
         this.field_146251_k = false;
      }
   }

   @Nullable
   public ITextComponent func_194817_a(double var1, double var3) {
      if (!this.func_146241_e()) {
         return null;
      } else {
         double ☃ = this.func_194815_g();
         double ☃x = ☃ - 2.0;
         double ☃xx = (double)this.field_146247_f.field_195558_d.func_198087_p() - ☃ - 40.0;
         ☃x = (double)MathHelper.func_76128_c(☃x / ☃);
         ☃xx = (double)MathHelper.func_76128_c(☃xx / ☃);
         if (!(☃x < 0.0) && !(☃xx < 0.0)) {
            int ☃xxx = Math.min(this.func_146232_i(), this.field_146253_i.size());
            if (☃x <= (double)MathHelper.func_76128_c((double)this.func_146228_f() / this.func_194815_g())
               && ☃xx < (double)(this.field_146247_f.field_71466_p.field_78288_b * ☃xxx + ☃xxx)) {
               int ☃xxxx = (int)(☃xx / (double)this.field_146247_f.field_71466_p.field_78288_b + (double)this.field_146250_j);
               if (☃xxxx >= 0 && ☃xxxx < this.field_146253_i.size()) {
                  ChatLine ☃xxxxx = (ChatLine)this.field_146253_i.get(☃xxxx);
                  int ☃xxxxxx = 0;

                  for(ITextComponent ☃xxxxxxx : ☃xxxxx.func_151461_a()) {
                     if (☃xxxxxxx instanceof TextComponentString) {
                        ☃xxxxxx += this.field_146247_f
                           .field_71466_p
                           .func_78256_a(GuiUtilRenderComponents.func_178909_a(((TextComponentString)☃xxxxxxx).func_150265_g(), false));
                        if ((double)☃xxxxxx > ☃x) {
                           return ☃xxxxxxx;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public boolean func_146241_e() {
      return this.field_146247_f.field_71462_r instanceof GuiChat;
   }

   public void func_146242_c(int var1) {
      Iterator<ChatLine> ☃ = this.field_146253_i.iterator();

      while(☃.hasNext()) {
         ChatLine ☃x = (ChatLine)☃.next();
         if (☃x.func_74539_c() == ☃) {
            ☃.remove();
         }
      }

      ☃ = this.field_146252_h.iterator();

      while(☃.hasNext()) {
         ChatLine ☃x = (ChatLine)☃.next();
         if (☃x.func_74539_c() == ☃) {
            ☃.remove();
            break;
         }
      }
   }

   public int func_146228_f() {
      return func_194814_b(this.field_146247_f.field_71474_y.field_96692_F);
   }

   public int func_146246_g() {
      return func_194816_c(this.func_146241_e() ? this.field_146247_f.field_71474_y.field_96694_H : this.field_146247_f.field_71474_y.field_96693_G);
   }

   public double func_194815_g() {
      return this.field_146247_f.field_71474_y.field_96691_E;
   }

   public static int func_194814_b(double var0) {
      int ☃ = 320;
      int ☃x = 40;
      return MathHelper.func_76128_c(☃ * 280.0 + 40.0);
   }

   public static int func_194816_c(double var0) {
      int ☃ = 180;
      int ☃x = 20;
      return MathHelper.func_76128_c(☃ * 160.0 + 20.0);
   }

   public int func_146232_i() {
      return this.func_146246_g() / 9;
   }
}
