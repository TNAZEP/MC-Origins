package net.minecraft.client.gui;

import com.google.common.base.Predicates;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.MathHelper;

public class GuiTextField extends Gui implements IGuiEventListener {
   private final int field_175208_g;
   private final FontRenderer field_146211_a;
   public int field_146209_f;
   public int field_146210_g;
   private final int field_146218_h;
   private final int field_146219_i;
   private String field_146216_j = "";
   private int field_146217_k = 32;
   private int field_146214_l;
   private boolean field_146215_m = true;
   private boolean field_146212_n = true;
   private boolean field_146213_o;
   private boolean field_146226_p = true;
   private int field_146225_q;
   private int field_146224_r;
   private int field_146223_s;
   private int field_146222_t = 14737632;
   private int field_146221_u = 7368816;
   private boolean field_146220_v = true;
   private String field_195614_x;
   private BiConsumer<Integer, String> field_175210_x;
   private Predicate<String> field_175209_y = Predicates.alwaysTrue();
   private BiFunction<String, Integer, String> field_195613_A = (var0, var1x) -> var0;

   public GuiTextField(int var1, FontRenderer var2, int var3, int var4, int var5, int var6) {
      this(☃, ☃, ☃, ☃, ☃, ☃, null);
   }

   public GuiTextField(int var1, FontRenderer var2, int var3, int var4, int var5, int var6, @Nullable GuiTextField var7) {
      this.field_175208_g = ☃;
      this.field_146211_a = ☃;
      this.field_146209_f = ☃;
      this.field_146210_g = ☃;
      this.field_146218_h = ☃;
      this.field_146219_i = ☃;
      if (☃ != null) {
         this.func_146180_a(☃.func_146179_b());
      }
   }

   public void func_195609_a(BiConsumer<Integer, String> var1) {
      this.field_175210_x = ☃;
   }

   public void func_195607_a(BiFunction<String, Integer, String> var1) {
      this.field_195613_A = ☃;
   }

   public void func_146178_a() {
      ++this.field_146214_l;
   }

   public void func_146180_a(String var1) {
      if (this.field_175209_y.test(☃)) {
         if (☃.length() > this.field_146217_k) {
            this.field_146216_j = ☃.substring(0, this.field_146217_k);
         } else {
            this.field_146216_j = ☃;
         }

         this.func_190516_a(this.field_175208_g, ☃);
         this.func_146202_e();
      }
   }

   public String func_146179_b() {
      return this.field_146216_j;
   }

   public String func_146207_c() {
      int ☃ = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
      int ☃x = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
      return this.field_146216_j.substring(☃, ☃x);
   }

   public void func_200675_a(Predicate<String> var1) {
      this.field_175209_y = ☃;
   }

   public void func_146191_b(String var1) {
      String ☃ = "";
      String ☃x = SharedConstants.func_71565_a(☃);
      int ☃xx = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
      int ☃xxx = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
      int ☃xxxx = this.field_146217_k - this.field_146216_j.length() - (☃xx - ☃xxx);
      if (!this.field_146216_j.isEmpty()) {
         ☃ = ☃ + this.field_146216_j.substring(0, ☃xx);
      }

      int ☃;
      if (☃xxxx < ☃x.length()) {
         ☃ = ☃ + ☃x.substring(0, ☃xxxx);
         ☃ = ☃xxxx;
      } else {
         ☃ = ☃ + ☃x;
         ☃ = ☃x.length();
      }

      if (!this.field_146216_j.isEmpty() && ☃xxx < this.field_146216_j.length()) {
         ☃ = ☃ + this.field_146216_j.substring(☃xxx);
      }

      if (this.field_175209_y.test(☃)) {
         this.field_146216_j = ☃;
         this.func_146182_d(☃xx - this.field_146223_s + ☃);
         this.func_190516_a(this.field_175208_g, this.field_146216_j);
      }
   }

   public void func_190516_a(int var1, String var2) {
      if (this.field_175210_x != null) {
         this.field_175210_x.accept(☃, ☃);
      }
   }

   public void func_146177_a(int var1) {
      if (!this.field_146216_j.isEmpty()) {
         if (this.field_146223_s != this.field_146224_r) {
            this.func_146191_b("");
         } else {
            this.func_146175_b(this.func_146187_c(☃) - this.field_146224_r);
         }
      }
   }

   public void func_146175_b(int var1) {
      if (!this.field_146216_j.isEmpty()) {
         if (this.field_146223_s != this.field_146224_r) {
            this.func_146191_b("");
         } else {
            boolean ☃ = ☃ < 0;
            int ☃x = ☃ ? this.field_146224_r + ☃ : this.field_146224_r;
            int ☃xx = ☃ ? this.field_146224_r : this.field_146224_r + ☃;
            String ☃xxx = "";
            if (☃x >= 0) {
               ☃xxx = this.field_146216_j.substring(0, ☃x);
            }

            if (☃xx < this.field_146216_j.length()) {
               ☃xxx = ☃xxx + this.field_146216_j.substring(☃xx);
            }

            if (this.field_175209_y.test(☃xxx)) {
               this.field_146216_j = ☃xxx;
               if (☃) {
                  this.func_146182_d(☃);
               }

               this.func_190516_a(this.field_175208_g, this.field_146216_j);
            }
         }
      }
   }

   public int func_146187_c(int var1) {
      return this.func_146183_a(☃, this.func_146198_h());
   }

   public int func_146183_a(int var1, int var2) {
      return this.func_146197_a(☃, ☃, true);
   }

   public int func_146197_a(int var1, int var2, boolean var3) {
      int ☃ = ☃;
      boolean ☃x = ☃ < 0;
      int ☃xx = Math.abs(☃);

      for(int ☃xxx = 0; ☃xxx < ☃xx; ++☃xxx) {
         if (!☃x) {
            int ☃xxxx = this.field_146216_j.length();
            ☃ = this.field_146216_j.indexOf(32, ☃);
            if (☃ == -1) {
               ☃ = ☃xxxx;
            } else {
               while(☃ && ☃ < ☃xxxx && this.field_146216_j.charAt(☃) == ' ') {
                  ++☃;
               }
            }
         } else {
            while(☃ && ☃ > 0 && this.field_146216_j.charAt(☃ - 1) == ' ') {
               --☃;
            }

            while(☃ > 0 && this.field_146216_j.charAt(☃ - 1) != ' ') {
               --☃;
            }
         }
      }

      return ☃;
   }

   public void func_146182_d(int var1) {
      this.func_146190_e(this.field_146223_s + ☃);
   }

   public void func_146190_e(int var1) {
      this.func_212422_f(☃);
      this.func_146199_i(this.field_146224_r);
      this.func_190516_a(this.field_175208_g, this.field_146216_j);
   }

   public void func_212422_f(int var1) {
      this.field_146224_r = MathHelper.func_76125_a(☃, 0, this.field_146216_j.length());
   }

   public void func_146196_d() {
      this.func_146190_e(0);
   }

   public void func_146202_e() {
      this.func_146190_e(this.field_146216_j.length());
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (!this.func_146176_q() || !this.func_146206_l()) {
         return false;
      } else if (GuiScreen.func_175278_g(☃)) {
         this.func_146202_e();
         this.func_146199_i(0);
         return true;
      } else if (GuiScreen.func_175280_f(☃)) {
         Minecraft.func_71410_x().field_195559_v.func_197960_a(this.func_146207_c());
         return true;
      } else if (GuiScreen.func_175279_e(☃)) {
         if (this.field_146226_p) {
            this.func_146191_b(Minecraft.func_71410_x().field_195559_v.func_197965_a());
         }

         return true;
      } else if (GuiScreen.func_175277_d(☃)) {
         Minecraft.func_71410_x().field_195559_v.func_197960_a(this.func_146207_c());
         if (this.field_146226_p) {
            this.func_146191_b("");
         }

         return true;
      } else {
         switch(☃) {
            case 259:
               if (GuiScreen.func_146271_m()) {
                  if (this.field_146226_p) {
                     this.func_146177_a(-1);
                  }
               } else if (this.field_146226_p) {
                  this.func_146175_b(-1);
               }

               return true;
            case 260:
            case 264:
            case 265:
            case 266:
            case 267:
            default:
               return ☃ != 256;
            case 261:
               if (GuiScreen.func_146271_m()) {
                  if (this.field_146226_p) {
                     this.func_146177_a(1);
                  }
               } else if (this.field_146226_p) {
                  this.func_146175_b(1);
               }

               return true;
            case 262:
               if (GuiScreen.func_146272_n()) {
                  if (GuiScreen.func_146271_m()) {
                     this.func_146199_i(this.func_146183_a(1, this.func_146186_n()));
                  } else {
                     this.func_146199_i(this.func_146186_n() + 1);
                  }
               } else if (GuiScreen.func_146271_m()) {
                  this.func_146190_e(this.func_146187_c(1));
               } else {
                  this.func_146182_d(1);
               }

               return true;
            case 263:
               if (GuiScreen.func_146272_n()) {
                  if (GuiScreen.func_146271_m()) {
                     this.func_146199_i(this.func_146183_a(-1, this.func_146186_n()));
                  } else {
                     this.func_146199_i(this.func_146186_n() - 1);
                  }
               } else if (GuiScreen.func_146271_m()) {
                  this.func_146190_e(this.func_146187_c(-1));
               } else {
                  this.func_146182_d(-1);
               }

               return true;
            case 268:
               if (GuiScreen.func_146272_n()) {
                  this.func_146199_i(0);
               } else {
                  this.func_146196_d();
               }

               return true;
            case 269:
               if (GuiScreen.func_146272_n()) {
                  this.func_146199_i(this.field_146216_j.length());
               } else {
                  this.func_146202_e();
               }

               return true;
         }
      }
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (!this.func_146176_q() || !this.func_146206_l()) {
         return false;
      } else if (SharedConstants.func_71566_a(☃)) {
         if (this.field_146226_p) {
            this.func_146191_b(Character.toString(☃));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (!this.func_146176_q()) {
         return false;
      } else {
         boolean ☃ = ☃ >= (double)this.field_146209_f
            && ☃ < (double)(this.field_146209_f + this.field_146218_h)
            && ☃ >= (double)this.field_146210_g
            && ☃ < (double)(this.field_146210_g + this.field_146219_i);
         if (this.field_146212_n) {
            this.func_146195_b(☃);
         }

         if (this.field_146213_o && ☃ && ☃ == 0) {
            int ☃ = MathHelper.func_76128_c(☃) - this.field_146209_f;
            if (this.field_146215_m) {
               ☃ -= 4;
            }

            String ☃ = this.field_146211_a.func_78269_a(this.field_146216_j.substring(this.field_146225_q), this.func_146200_o());
            this.func_146190_e(this.field_146211_a.func_78269_a(☃, ☃).length() + this.field_146225_q);
            return true;
         } else {
            return false;
         }
      }
   }

   public void func_195608_a(int var1, int var2, float var3) {
      if (this.func_146176_q()) {
         if (this.func_146181_i()) {
            func_73734_a(
               this.field_146209_f - 1,
               this.field_146210_g - 1,
               this.field_146209_f + this.field_146218_h + 1,
               this.field_146210_g + this.field_146219_i + 1,
               -6250336
            );
            func_73734_a(
               this.field_146209_f, this.field_146210_g, this.field_146209_f + this.field_146218_h, this.field_146210_g + this.field_146219_i, -16777216
            );
         }

         int ☃ = this.field_146226_p ? this.field_146222_t : this.field_146221_u;
         int ☃x = this.field_146224_r - this.field_146225_q;
         int ☃xx = this.field_146223_s - this.field_146225_q;
         String ☃xxx = this.field_146211_a.func_78269_a(this.field_146216_j.substring(this.field_146225_q), this.func_146200_o());
         boolean ☃xxxx = ☃x >= 0 && ☃x <= ☃xxx.length();
         boolean ☃xxxxx = this.field_146213_o && this.field_146214_l / 6 % 2 == 0 && ☃xxxx;
         int ☃xxxxxx = this.field_146215_m ? this.field_146209_f + 4 : this.field_146209_f;
         int ☃xxxxxxx = this.field_146215_m ? this.field_146210_g + (this.field_146219_i - 8) / 2 : this.field_146210_g;
         int ☃xxxxxxxx = ☃xxxxxx;
         if (☃xx > ☃xxx.length()) {
            ☃xx = ☃xxx.length();
         }

         if (!☃xxx.isEmpty()) {
            String ☃ = ☃xxxx ? ☃xxx.substring(0, ☃x) : ☃xxx;
            ☃xxxxxxxx = this.field_146211_a.func_175063_a((String)this.field_195613_A.apply(☃, this.field_146225_q), (float)☃xxxxxx, (float)☃xxxxxxx, ☃);
         }

         boolean ☃ = this.field_146224_r < this.field_146216_j.length() || this.field_146216_j.length() >= this.func_146208_g();
         int ☃x = ☃xxxxxxxx;
         if (!☃xxxx) {
            ☃x = ☃x > 0 ? ☃xxxxxx + this.field_146218_h : ☃xxxxxx;
         } else if (☃) {
            ☃x = ☃xxxxxxxx - 1;
            --☃xxxxxxxx;
         }

         if (!☃xxx.isEmpty() && ☃xxxx && ☃x < ☃xxx.length()) {
            ☃xxxxxxxx = this.field_146211_a
               .func_175063_a((String)this.field_195613_A.apply(☃xxx.substring(☃x), this.field_146224_r), (float)☃xxxxxxxx, (float)☃xxxxxxx, ☃);
         }

         if (!☃ && this.field_195614_x != null) {
            this.field_146211_a.func_175063_a(this.field_195614_x, (float)(☃x - 1), (float)☃xxxxxxx, -8355712);
         }

         if (☃xxxxx) {
            if (☃) {
               Gui.func_73734_a(☃x, ☃xxxxxxx - 1, ☃x + 1, ☃xxxxxxx + 1 + this.field_146211_a.field_78288_b, -3092272);
            } else {
               this.field_146211_a.func_175063_a("_", (float)☃x, (float)☃xxxxxxx, ☃);
            }
         }

         if (☃xx != ☃x) {
            int ☃ = ☃xxxxxx + this.field_146211_a.func_78256_a(☃xxx.substring(0, ☃xx));
            this.func_146188_c(☃x, ☃xxxxxxx - 1, ☃ - 1, ☃xxxxxxx + 1 + this.field_146211_a.field_78288_b);
         }
      }
   }

   private void func_146188_c(int var1, int var2, int var3, int var4) {
      if (☃ < ☃) {
         int ☃ = ☃;
         ☃ = ☃;
         ☃ = ☃;
      }

      if (☃ < ☃) {
         int ☃ = ☃;
         ☃ = ☃;
         ☃ = ☃;
      }

      if (☃ > this.field_146209_f + this.field_146218_h) {
         ☃ = this.field_146209_f + this.field_146218_h;
      }

      if (☃ > this.field_146209_f + this.field_146218_h) {
         ☃ = this.field_146209_f + this.field_146218_h;
      }

      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      GlStateManager.func_179131_c(0.0F, 0.0F, 255.0F, 255.0F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179115_u();
      GlStateManager.func_187422_a(GlStateManager.LogicOp.OR_REVERSE);
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      ☃x.func_181662_b((double)☃, (double)☃, 0.0).func_181675_d();
      ☃x.func_181662_b((double)☃, (double)☃, 0.0).func_181675_d();
      ☃x.func_181662_b((double)☃, (double)☃, 0.0).func_181675_d();
      ☃x.func_181662_b((double)☃, (double)☃, 0.0).func_181675_d();
      ☃.func_78381_a();
      GlStateManager.func_179134_v();
      GlStateManager.func_179098_w();
   }

   public void func_146203_f(int var1) {
      this.field_146217_k = ☃;
      if (this.field_146216_j.length() > ☃) {
         this.field_146216_j = this.field_146216_j.substring(0, ☃);
         this.func_190516_a(this.field_175208_g, this.field_146216_j);
      }
   }

   public int func_146208_g() {
      return this.field_146217_k;
   }

   public int func_146198_h() {
      return this.field_146224_r;
   }

   public boolean func_146181_i() {
      return this.field_146215_m;
   }

   public void func_146185_a(boolean var1) {
      this.field_146215_m = ☃;
   }

   public void func_146193_g(int var1) {
      this.field_146222_t = ☃;
   }

   public void func_146204_h(int var1) {
      this.field_146221_u = ☃;
   }

   @Override
   public void func_205700_b(boolean var1) {
      this.func_146195_b(☃);
   }

   @Override
   public boolean func_207704_ae_() {
      return true;
   }

   public void func_146195_b(boolean var1) {
      if (☃ && !this.field_146213_o) {
         this.field_146214_l = 0;
      }

      this.field_146213_o = ☃;
   }

   public boolean func_146206_l() {
      return this.field_146213_o;
   }

   public void func_146184_c(boolean var1) {
      this.field_146226_p = ☃;
   }

   public int func_146186_n() {
      return this.field_146223_s;
   }

   public int func_146200_o() {
      return this.func_146181_i() ? this.field_146218_h - 8 : this.field_146218_h;
   }

   public void func_146199_i(int var1) {
      int ☃ = this.field_146216_j.length();
      if (☃ > ☃) {
         ☃ = ☃;
      }

      if (☃ < 0) {
         ☃ = 0;
      }

      this.field_146223_s = ☃;
      if (this.field_146211_a != null) {
         if (this.field_146225_q > ☃) {
            this.field_146225_q = ☃;
         }

         int ☃ = this.func_146200_o();
         String ☃x = this.field_146211_a.func_78269_a(this.field_146216_j.substring(this.field_146225_q), ☃);
         int ☃xx = ☃x.length() + this.field_146225_q;
         if (☃ == this.field_146225_q) {
            this.field_146225_q -= this.field_146211_a.func_78262_a(this.field_146216_j, ☃, true).length();
         }

         if (☃ > ☃xx) {
            this.field_146225_q += ☃ - ☃xx;
         } else if (☃ <= this.field_146225_q) {
            this.field_146225_q -= this.field_146225_q - ☃;
         }

         this.field_146225_q = MathHelper.func_76125_a(this.field_146225_q, 0, ☃);
      }
   }

   public void func_146205_d(boolean var1) {
      this.field_146212_n = ☃;
   }

   public boolean func_146176_q() {
      return this.field_146220_v;
   }

   public void func_146189_e(boolean var1) {
      this.field_146220_v = ☃;
   }

   public void func_195612_c(@Nullable String var1) {
      this.field_195614_x = ☃;
   }

   public int func_195611_j(int var1) {
      return ☃ > this.field_146216_j.length()
         ? this.field_146209_f
         : this.field_146209_f + this.field_146211_a.func_78256_a(this.field_146216_j.substring(0, ☃));
   }
}
