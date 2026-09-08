package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiWinGame extends GuiScreen {
   private static final Logger field_146580_a = LogManager.getLogger();
   private static final ResourceLocation field_146576_f = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation field_194401_g = new ResourceLocation("textures/gui/title/edition.png");
   private static final ResourceLocation field_146577_g = new ResourceLocation("textures/misc/vignette.png");
   private final boolean field_193980_h;
   private final Runnable field_193981_i;
   private float field_146581_h;
   private List<String> field_146582_i;
   private int field_146579_r;
   private float field_146578_s = 0.5F;

   public GuiWinGame(boolean var1, Runnable var2) {
      this.field_193980_h = ☃;
      this.field_193981_i = ☃;
      if (!☃) {
         this.field_146578_s = 0.75F;
      }
   }

   @Override
   public void func_73876_c() {
      this.field_146297_k.func_181535_r().func_73660_a();
      this.field_146297_k.func_147118_V().func_73660_a();
      float ☃ = (float)(this.field_146579_r + this.field_146295_m + this.field_146295_m + 24) / this.field_146578_s;
      if (this.field_146581_h > ☃) {
         this.func_146574_g();
      }
   }

   @Override
   public void func_195122_V_() {
      this.func_146574_g();
   }

   private void func_146574_g() {
      this.field_193981_i.run();
      this.field_146297_k.func_147108_a(null);
   }

   @Override
   protected void func_73866_w_() {
      if (this.field_146582_i == null) {
         this.field_146582_i = Lists.newArrayList();
         IResource ☃ = null;

         try {
            String ☃x = "" + TextFormatting.WHITE + TextFormatting.OBFUSCATED + TextFormatting.GREEN + TextFormatting.AQUA;
            int ☃xx = 274;
            if (this.field_193980_h) {
               ☃ = this.field_146297_k.func_195551_G().func_199002_a(new ResourceLocation("texts/end.txt"));
               InputStream ☃xxx = ☃.func_199027_b();
               BufferedReader ☃xxxx = new BufferedReader(new InputStreamReader(☃xxx, StandardCharsets.UTF_8));
               Random ☃xxxxx = new Random(8124371L);

               String ☃;
               while((☃ = ☃xxxx.readLine()) != null) {
                  String ☃;
                  String ☃;
                  for(☃ = ☃.replaceAll("PLAYERNAME", this.field_146297_k.func_110432_I().func_111285_a());
                     ☃.contains(☃x);
                     ☃ = ☃ + TextFormatting.WHITE + TextFormatting.OBFUSCATED + "XXXXXXXX".substring(0, ☃xxxxx.nextInt(4) + 3) + ☃
                  ) {
                     int ☃xxxxxx = ☃.indexOf(☃x);
                     ☃ = ☃.substring(0, ☃xxxxxx);
                     ☃ = ☃.substring(☃xxxxxx + ☃x.length());
                  }

                  this.field_146582_i.addAll(this.field_146297_k.field_71466_p.func_78271_c(☃, 274));
                  this.field_146582_i.add("");
               }

               ☃xxx.close();

               for(int ☃xxxxxx = 0; ☃xxxxxx < 8; ++☃xxxxxx) {
                  this.field_146582_i.add("");
               }
            }

            InputStream ☃x = this.field_146297_k.func_195551_G().func_199002_a(new ResourceLocation("texts/credits.txt")).func_199027_b();
            BufferedReader ☃xx = new BufferedReader(new InputStreamReader(☃x, StandardCharsets.UTF_8));

            String ☃;
            while((☃ = ☃xx.readLine()) != null) {
               ☃ = ☃.replaceAll("PLAYERNAME", this.field_146297_k.func_110432_I().func_111285_a());
               ☃ = ☃.replaceAll("\t", "    ");
               this.field_146582_i.addAll(this.field_146297_k.field_71466_p.func_78271_c(☃, 274));
               this.field_146582_i.add("");
            }

            ☃x.close();
            this.field_146579_r = this.field_146582_i.size() * 12;
         } catch (Exception var14) {
            field_146580_a.error("Couldn't load credits", var14);
         } finally {
            IOUtils.closeQuietly(☃);
         }
      }
   }

   private void func_146575_b(int var1, int var2, float var3) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      this.field_146297_k.func_110434_K().func_110577_a(Gui.field_110325_k);
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      int ☃xx = this.field_146294_l;
      float ☃xxx = -this.field_146581_h * 0.5F * this.field_146578_s;
      float ☃xxxx = (float)this.field_146295_m - this.field_146581_h * 0.5F * this.field_146578_s;
      float ☃xxxxx = 0.015625F;
      float ☃xxxxxx = this.field_146581_h * 0.02F;
      float ☃xxxxxxx = (float)(this.field_146579_r + this.field_146295_m + this.field_146295_m + 24) / this.field_146578_s;
      float ☃xxxxxxxx = (☃xxxxxxx - 20.0F - this.field_146581_h) * 0.005F;
      if (☃xxxxxxxx < ☃xxxxxx) {
         ☃xxxxxx = ☃xxxxxxxx;
      }

      if (☃xxxxxx > 1.0F) {
         ☃xxxxxx = 1.0F;
      }

      ☃xxxxxx *= ☃xxxxxx;
      ☃xxxxxx = ☃xxxxxx * 96.0F / 255.0F;
      ☃x.func_181662_b(0.0, (double)this.field_146295_m, (double)this.field_73735_i)
         .func_187315_a(0.0, (double)(☃xxx * 0.015625F))
         .func_181666_a(☃xxxxxx, ☃xxxxxx, ☃xxxxxx, 1.0F)
         .func_181675_d();
      ☃x.func_181662_b((double)☃xx, (double)this.field_146295_m, (double)this.field_73735_i)
         .func_187315_a((double)((float)☃xx * 0.015625F), (double)(☃xxx * 0.015625F))
         .func_181666_a(☃xxxxxx, ☃xxxxxx, ☃xxxxxx, 1.0F)
         .func_181675_d();
      ☃x.func_181662_b((double)☃xx, 0.0, (double)this.field_73735_i)
         .func_187315_a((double)((float)☃xx * 0.015625F), (double)(☃xxxx * 0.015625F))
         .func_181666_a(☃xxxxxx, ☃xxxxxx, ☃xxxxxx, 1.0F)
         .func_181675_d();
      ☃x.func_181662_b(0.0, 0.0, (double)this.field_73735_i)
         .func_187315_a(0.0, (double)(☃xxxx * 0.015625F))
         .func_181666_a(☃xxxxxx, ☃xxxxxx, ☃xxxxxx, 1.0F)
         .func_181675_d();
      ☃.func_78381_a();
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146575_b(☃, ☃, ☃);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      int ☃xx = 274;
      int ☃xxx = this.field_146294_l / 2 - 137;
      int ☃xxxx = this.field_146295_m + 50;
      this.field_146581_h += ☃;
      float ☃xxxxx = -this.field_146581_h * this.field_146578_s;
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, ☃xxxxx, 0.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_146576_f);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179141_d();
      this.func_73729_b(☃xxx, ☃xxxx, 0, 0, 155, 44);
      this.func_73729_b(☃xxx + 155, ☃xxxx, 0, 45, 155, 44);
      this.field_146297_k.func_110434_K().func_110577_a(field_194401_g);
      func_146110_a(☃xxx + 88, ☃xxxx + 37, 0.0F, 0.0F, 98, 14, 128.0F, 16.0F);
      GlStateManager.func_179118_c();
      int ☃xxxxxx = ☃xxxx + 100;

      for(int ☃xxxxxxx = 0; ☃xxxxxxx < this.field_146582_i.size(); ++☃xxxxxxx) {
         if (☃xxxxxxx == this.field_146582_i.size() - 1) {
            float ☃xxxxxxxx = (float)☃xxxxxx + ☃xxxxx - (float)(this.field_146295_m / 2 - 6);
            if (☃xxxxxxxx < 0.0F) {
               GlStateManager.func_179109_b(0.0F, -☃xxxxxxxx, 0.0F);
            }
         }

         if ((float)☃xxxxxx + ☃xxxxx + 12.0F + 8.0F > 0.0F && (float)☃xxxxxx + ☃xxxxx < (float)this.field_146295_m) {
            String ☃xxxxxxxx = (String)this.field_146582_i.get(☃xxxxxxx);
            if (☃xxxxxxxx.startsWith("[C]")) {
               this.field_146289_q
                  .func_175063_a(
                     ☃xxxxxxxx.substring(3), (float)(☃xxx + (274 - this.field_146289_q.func_78256_a(☃xxxxxxxx.substring(3))) / 2), (float)☃xxxxxx, 16777215
                  );
            } else {
               this.field_146289_q.field_78289_c.setSeed((long)((float)((long)☃xxxxxxx * 4238972211L) + this.field_146581_h / 4.0F));
               this.field_146289_q.func_175063_a(☃xxxxxxxx, (float)☃xxx, (float)☃xxxxxx, 16777215);
            }
         }

         ☃xxxxxx += 12;
      }

      GlStateManager.func_179121_F();
      this.field_146297_k.func_110434_K().func_110577_a(field_146577_g);
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
      int ☃xxxxxxx = this.field_146294_l;
      int ☃xxxxxxxx = this.field_146295_m;
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      ☃x.func_181662_b(0.0, (double)☃xxxxxxxx, (double)this.field_73735_i).func_187315_a(0.0, 1.0).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).func_181675_d();
      ☃x.func_181662_b((double)☃xxxxxxx, (double)☃xxxxxxxx, (double)this.field_73735_i)
         .func_187315_a(1.0, 1.0)
         .func_181666_a(1.0F, 1.0F, 1.0F, 1.0F)
         .func_181675_d();
      ☃x.func_181662_b((double)☃xxxxxxx, 0.0, (double)this.field_73735_i).func_187315_a(1.0, 0.0).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).func_181675_d();
      ☃x.func_181662_b(0.0, 0.0, (double)this.field_73735_i).func_187315_a(0.0, 0.0).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).func_181675_d();
      ☃.func_78381_a();
      GlStateManager.func_179084_k();
      super.func_73863_a(☃, ☃, ☃);
   }
}
