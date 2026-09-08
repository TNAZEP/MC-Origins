package net.minecraft.client.gui.advancements;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiAdvancement extends Gui {
   private static final ResourceLocation field_191827_a = new ResourceLocation("textures/gui/advancements/widgets.png");
   private static final Pattern field_192996_f = Pattern.compile("(.+) \\S+");
   private final GuiAdvancementTab field_191828_f;
   private final Advancement field_191829_g;
   private final DisplayInfo field_191830_h;
   private final String field_191831_i;
   private final int field_191832_j;
   private final List<String> field_192997_l;
   private final Minecraft field_191833_k;
   private GuiAdvancement field_191834_l;
   private final List<GuiAdvancement> field_191835_m = Lists.<GuiAdvancement>newArrayList();
   private AdvancementProgress field_191836_n;
   private final int field_191837_o;
   private final int field_191826_p;

   public GuiAdvancement(GuiAdvancementTab var1, Minecraft var2, Advancement var3, DisplayInfo var4) {
      this.field_191828_f = ☃;
      this.field_191829_g = ☃;
      this.field_191830_h = ☃;
      this.field_191833_k = ☃;
      this.field_191831_i = ☃.field_71466_p.func_78269_a(☃.func_192297_a().func_150254_d(), 163);
      this.field_191837_o = MathHelper.func_76141_d(☃.func_192299_e() * 28.0F);
      this.field_191826_p = MathHelper.func_76141_d(☃.func_192296_f() * 27.0F);
      int ☃ = ☃.func_193124_g();
      int ☃x = String.valueOf(☃).length();
      int ☃xx = ☃ > 1 ? ☃.field_71466_p.func_78256_a("  ") + ☃.field_71466_p.func_78256_a("0") * ☃x * 2 + ☃.field_71466_p.func_78256_a("/") : 0;
      int ☃xxx = 29 + ☃.field_71466_p.func_78256_a(this.field_191831_i) + ☃xx;
      String ☃xxxx = ☃.func_193222_b().func_150254_d();
      this.field_192997_l = this.func_192995_a(☃xxxx, ☃xxx);

      for(String ☃xxxxx : this.field_192997_l) {
         ☃xxx = Math.max(☃xxx, ☃.field_71466_p.func_78256_a(☃xxxxx));
      }

      this.field_191832_j = ☃xxx + 3 + 5;
   }

   private List<String> func_192995_a(String var1, int var2) {
      if (☃.isEmpty()) {
         return Collections.emptyList();
      } else {
         List<String> ☃ = this.field_191833_k.field_71466_p.func_78271_c(☃, ☃);
         if (☃.size() < 2) {
            return ☃;
         } else {
            String ☃ = (String)☃.get(0);
            String ☃x = (String)☃.get(1);
            int ☃xx = this.field_191833_k.field_71466_p.func_78256_a(☃ + ' ' + ☃x.split(" ")[0]);
            if (☃xx - ☃ <= 10) {
               return this.field_191833_k.field_71466_p.func_78271_c(☃, ☃xx);
            } else {
               Matcher ☃ = field_192996_f.matcher(☃);
               if (☃.matches()) {
                  int ☃x = this.field_191833_k.field_71466_p.func_78256_a(☃.group(1));
                  if (☃ - ☃x <= 10) {
                     return this.field_191833_k.field_71466_p.func_78271_c(☃, ☃x);
                  }
               }

               return ☃;
            }
         }
      }
   }

   @Nullable
   private GuiAdvancement func_191818_a(Advancement var1) {
      do {
         ☃ = ☃.func_192070_b();
      } while(☃ != null && ☃.func_192068_c() == null);

      return ☃ != null && ☃.func_192068_c() != null ? this.field_191828_f.func_191794_b(☃) : null;
   }

   public void func_191819_a(int var1, int var2, boolean var3) {
      if (this.field_191834_l != null) {
         int ☃ = ☃ + this.field_191834_l.field_191837_o + 13;
         int ☃x = ☃ + this.field_191834_l.field_191837_o + 26 + 4;
         int ☃xx = ☃ + this.field_191834_l.field_191826_p + 13;
         int ☃xxx = ☃ + this.field_191837_o + 13;
         int ☃xxxx = ☃ + this.field_191826_p + 13;
         int ☃xxxxx = ☃ ? -16777216 : -1;
         if (☃) {
            this.func_73730_a(☃x, ☃, ☃xx - 1, ☃xxxxx);
            this.func_73730_a(☃x + 1, ☃, ☃xx, ☃xxxxx);
            this.func_73730_a(☃x, ☃, ☃xx + 1, ☃xxxxx);
            this.func_73730_a(☃xxx, ☃x - 1, ☃xxxx - 1, ☃xxxxx);
            this.func_73730_a(☃xxx, ☃x - 1, ☃xxxx, ☃xxxxx);
            this.func_73730_a(☃xxx, ☃x - 1, ☃xxxx + 1, ☃xxxxx);
            this.func_73728_b(☃x - 1, ☃xxxx, ☃xx, ☃xxxxx);
            this.func_73728_b(☃x + 1, ☃xxxx, ☃xx, ☃xxxxx);
         } else {
            this.func_73730_a(☃x, ☃, ☃xx, ☃xxxxx);
            this.func_73730_a(☃xxx, ☃x, ☃xxxx, ☃xxxxx);
            this.func_73728_b(☃x, ☃xxxx, ☃xx, ☃xxxxx);
         }
      }

      for(GuiAdvancement ☃ : this.field_191835_m) {
         ☃.func_191819_a(☃, ☃, ☃);
      }
   }

   public void func_191817_b(int var1, int var2) {
      if (!this.field_191830_h.func_193224_j() || this.field_191836_n != null && this.field_191836_n.func_192105_a()) {
         float ☃x = this.field_191836_n == null ? 0.0F : this.field_191836_n.func_192103_c();
         AdvancementState ☃;
         if (☃x >= 1.0F) {
            ☃ = AdvancementState.OBTAINED;
         } else {
            ☃ = AdvancementState.UNOBTAINED;
         }

         this.field_191833_k.func_110434_K().func_110577_a(field_191827_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179147_l();
         this.func_73729_b(
            ☃ + this.field_191837_o + 3, ☃ + this.field_191826_p, this.field_191830_h.func_192291_d().func_192309_b(), 128 + ☃.func_192667_a() * 26, 26, 26
         );
         RenderHelper.func_74520_c();
         this.field_191833_k
            .func_175599_af()
            .func_184391_a(null, this.field_191830_h.func_192298_b(), ☃ + this.field_191837_o + 8, ☃ + this.field_191826_p + 5);
      }

      for(GuiAdvancement ☃ : this.field_191835_m) {
         ☃.func_191817_b(☃, ☃);
      }
   }

   public void func_191824_a(AdvancementProgress var1) {
      this.field_191836_n = ☃;
   }

   public void func_191822_a(GuiAdvancement var1) {
      this.field_191835_m.add(☃);
   }

   public void func_191821_a(int var1, int var2, float var3, int var4, int var5) {
      boolean ☃xxx = ☃ + ☃ + this.field_191837_o + this.field_191832_j + 26 >= this.field_191828_f.func_193934_g().field_146294_l;
      String ☃xxxx = this.field_191836_n == null ? null : this.field_191836_n.func_193126_d();
      int ☃xxxxx = ☃xxxx == null ? 0 : this.field_191833_k.field_71466_p.func_78256_a(☃xxxx);
      boolean ☃xxxxxx = 113 - ☃ - this.field_191826_p - 26 <= 6 + this.field_192997_l.size() * this.field_191833_k.field_71466_p.field_78288_b;
      float ☃xxxxxxx = this.field_191836_n == null ? 0.0F : this.field_191836_n.func_192103_c();
      int ☃xxxxxxxx = MathHelper.func_76141_d(☃xxxxxxx * (float)this.field_191832_j);
      AdvancementState ☃;
      AdvancementState ☃x;
      AdvancementState ☃xx;
      if (☃xxxxxxx >= 1.0F) {
         ☃xxxxxxxx = this.field_191832_j / 2;
         ☃ = AdvancementState.OBTAINED;
         ☃x = AdvancementState.OBTAINED;
         ☃xx = AdvancementState.OBTAINED;
      } else if (☃xxxxxxxx < 2) {
         ☃xxxxxxxx = this.field_191832_j / 2;
         ☃ = AdvancementState.UNOBTAINED;
         ☃x = AdvancementState.UNOBTAINED;
         ☃xx = AdvancementState.UNOBTAINED;
      } else if (☃xxxxxxxx > this.field_191832_j - 2) {
         ☃xxxxxxxx = this.field_191832_j / 2;
         ☃ = AdvancementState.OBTAINED;
         ☃x = AdvancementState.OBTAINED;
         ☃xx = AdvancementState.UNOBTAINED;
      } else {
         ☃ = AdvancementState.OBTAINED;
         ☃x = AdvancementState.UNOBTAINED;
         ☃xx = AdvancementState.UNOBTAINED;
      }

      int ☃x = this.field_191832_j - ☃xxxxxxxx;
      this.field_191833_k.func_110434_K().func_110577_a(field_191827_a);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179147_l();
      int ☃xx = ☃ + this.field_191826_p;
      int ☃;
      if (☃xxx) {
         ☃ = ☃ + this.field_191837_o - this.field_191832_j + 26 + 6;
      } else {
         ☃ = ☃ + this.field_191837_o;
      }

      int ☃ = 32 + this.field_192997_l.size() * this.field_191833_k.field_71466_p.field_78288_b;
      if (!this.field_192997_l.isEmpty()) {
         if (☃xxxxxx) {
            this.func_192994_a(☃, ☃xx + 26 - ☃, this.field_191832_j, ☃, 10, 200, 26, 0, 52);
         } else {
            this.func_192994_a(☃, ☃xx, this.field_191832_j, ☃, 10, 200, 26, 0, 52);
         }
      }

      this.func_73729_b(☃, ☃xx, 0, ☃.func_192667_a() * 26, ☃xxxxxxxx, 26);
      this.func_73729_b(☃ + ☃xxxxxxxx, ☃xx, 200 - ☃x, ☃x.func_192667_a() * 26, ☃x, 26);
      this.func_73729_b(
         ☃ + this.field_191837_o + 3, ☃ + this.field_191826_p, this.field_191830_h.func_192291_d().func_192309_b(), 128 + ☃xx.func_192667_a() * 26, 26, 26
      );
      if (☃xxx) {
         this.field_191833_k.field_71466_p.func_175063_a(this.field_191831_i, (float)(☃ + 5), (float)(☃ + this.field_191826_p + 9), -1);
         if (☃xxxx != null) {
            this.field_191833_k.field_71466_p.func_175063_a(☃xxxx, (float)(☃ + this.field_191837_o - ☃xxxxx), (float)(☃ + this.field_191826_p + 9), -1);
         }
      } else {
         this.field_191833_k.field_71466_p.func_175063_a(this.field_191831_i, (float)(☃ + this.field_191837_o + 32), (float)(☃ + this.field_191826_p + 9), -1);
         if (☃xxxx != null) {
            this.field_191833_k
               .field_71466_p
               .func_175063_a(☃xxxx, (float)(☃ + this.field_191837_o + this.field_191832_j - ☃xxxxx - 5), (float)(☃ + this.field_191826_p + 9), -1);
         }
      }

      if (☃xxxxxx) {
         for(int ☃ = 0; ☃ < this.field_192997_l.size(); ++☃) {
            this.field_191833_k
               .field_71466_p
               .func_211126_b(
                  (String)this.field_192997_l.get(☃), (float)(☃ + 5), (float)(☃xx + 26 - ☃ + 7 + ☃ * this.field_191833_k.field_71466_p.field_78288_b), -5592406
               );
         }
      } else {
         for(int ☃ = 0; ☃ < this.field_192997_l.size(); ++☃) {
            this.field_191833_k
               .field_71466_p
               .func_211126_b(
                  (String)this.field_192997_l.get(☃),
                  (float)(☃ + 5),
                  (float)(☃ + this.field_191826_p + 9 + 17 + ☃ * this.field_191833_k.field_71466_p.field_78288_b),
                  -5592406
               );
         }
      }

      RenderHelper.func_74520_c();
      this.field_191833_k.func_175599_af().func_184391_a(null, this.field_191830_h.func_192298_b(), ☃ + this.field_191837_o + 8, ☃ + this.field_191826_p + 5);
   }

   protected void func_192994_a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      this.func_73729_b(☃, ☃, ☃, ☃, ☃, ☃);
      this.func_192993_a(☃ + ☃, ☃, ☃ - ☃ - ☃, ☃, ☃ + ☃, ☃, ☃ - ☃ - ☃, ☃);
      this.func_73729_b(☃ + ☃ - ☃, ☃, ☃ + ☃ - ☃, ☃, ☃, ☃);
      this.func_73729_b(☃, ☃ + ☃ - ☃, ☃, ☃ + ☃ - ☃, ☃, ☃);
      this.func_192993_a(☃ + ☃, ☃ + ☃ - ☃, ☃ - ☃ - ☃, ☃, ☃ + ☃, ☃ + ☃ - ☃, ☃ - ☃ - ☃, ☃);
      this.func_73729_b(☃ + ☃ - ☃, ☃ + ☃ - ☃, ☃ + ☃ - ☃, ☃ + ☃ - ☃, ☃, ☃);
      this.func_192993_a(☃, ☃ + ☃, ☃, ☃ - ☃ - ☃, ☃, ☃ + ☃, ☃, ☃ - ☃ - ☃);
      this.func_192993_a(☃ + ☃, ☃ + ☃, ☃ - ☃ - ☃, ☃ - ☃ - ☃, ☃ + ☃, ☃ + ☃, ☃ - ☃ - ☃, ☃ - ☃ - ☃);
      this.func_192993_a(☃ + ☃ - ☃, ☃ + ☃, ☃, ☃ - ☃ - ☃, ☃ + ☃ - ☃, ☃ + ☃, ☃, ☃ - ☃ - ☃);
   }

   protected void func_192993_a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      for(int ☃ = 0; ☃ < ☃; ☃ += ☃) {
         int ☃x = ☃ + ☃;
         int ☃xx = Math.min(☃, ☃ - ☃);

         for(int ☃xxx = 0; ☃xxx < ☃; ☃xxx += ☃) {
            int ☃xxxx = ☃ + ☃xxx;
            int ☃xxxxx = Math.min(☃, ☃ - ☃xxx);
            this.func_73729_b(☃x, ☃xxxx, ☃, ☃, ☃xx, ☃xxxxx);
         }
      }
   }

   public boolean func_191816_c(int var1, int var2, int var3, int var4) {
      if (!this.field_191830_h.func_193224_j() || this.field_191836_n != null && this.field_191836_n.func_192105_a()) {
         int ☃ = ☃ + this.field_191837_o;
         int ☃x = ☃ + 26;
         int ☃xx = ☃ + this.field_191826_p;
         int ☃xxx = ☃xx + 26;
         return ☃ >= ☃ && ☃ <= ☃x && ☃ >= ☃xx && ☃ <= ☃xxx;
      } else {
         return false;
      }
   }

   public void func_191825_b() {
      if (this.field_191834_l == null && this.field_191829_g.func_192070_b() != null) {
         this.field_191834_l = this.func_191818_a(this.field_191829_g);
         if (this.field_191834_l != null) {
            this.field_191834_l.func_191822_a(this);
         }
      }
   }

   public int func_191820_c() {
      return this.field_191826_p;
   }

   public int func_191823_d() {
      return this.field_191837_o;
   }
}
