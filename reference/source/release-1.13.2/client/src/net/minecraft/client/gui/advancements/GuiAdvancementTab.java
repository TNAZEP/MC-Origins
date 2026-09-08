package net.minecraft.client.gui.advancements;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiAdvancementTab extends Gui {
   private final Minecraft field_191802_a;
   private final GuiScreenAdvancements field_193938_f;
   private final AdvancementTabType field_191803_f;
   private final int field_191804_g;
   private final Advancement field_191805_h;
   private final DisplayInfo field_191806_i;
   private final ItemStack field_191807_j;
   private final String field_191808_k;
   private final GuiAdvancement field_191809_l;
   private final Map<Advancement, GuiAdvancement> field_191810_m = Maps.<Advancement, GuiAdvancement>newLinkedHashMap();
   private double field_191811_n;
   private double field_191812_o;
   private int field_193939_q = Integer.MAX_VALUE;
   private int field_193940_r = Integer.MAX_VALUE;
   private int field_191813_p = Integer.MIN_VALUE;
   private int field_191814_q = Integer.MIN_VALUE;
   private float field_191815_r;
   private boolean field_192992_s;

   public GuiAdvancementTab(Minecraft var1, GuiScreenAdvancements var2, AdvancementTabType var3, int var4, Advancement var5, DisplayInfo var6) {
      this.field_191802_a = ☃;
      this.field_193938_f = ☃;
      this.field_191803_f = ☃;
      this.field_191804_g = ☃;
      this.field_191805_h = ☃;
      this.field_191806_i = ☃;
      this.field_191807_j = ☃.func_192298_b();
      this.field_191808_k = ☃.func_192297_a().func_150254_d();
      this.field_191809_l = new GuiAdvancement(this, ☃, ☃, ☃);
      this.func_193937_a(this.field_191809_l, ☃);
   }

   public Advancement func_193935_c() {
      return this.field_191805_h;
   }

   public String func_191795_d() {
      return this.field_191808_k;
   }

   public void func_191798_a(int var1, int var2, boolean var3) {
      this.field_191803_f.func_192651_a(this, ☃, ☃, ☃, this.field_191804_g);
   }

   public void func_191796_a(int var1, int var2, ItemRenderer var3) {
      this.field_191803_f.func_192652_a(☃, ☃, this.field_191804_g, ☃, this.field_191807_j);
   }

   public void func_191799_a() {
      if (!this.field_192992_s) {
         this.field_191811_n = (double)(117 - (this.field_191813_p + this.field_193939_q) / 2);
         this.field_191812_o = (double)(56 - (this.field_191814_q + this.field_193940_r) / 2);
         this.field_192992_s = true;
      }

      GlStateManager.func_179143_c(518);
      func_73734_a(0, 0, 234, 113, -16777216);
      GlStateManager.func_179143_c(515);
      ResourceLocation ☃ = this.field_191806_i.func_192293_c();
      if (☃ != null) {
         this.field_191802_a.func_110434_K().func_110577_a(☃);
      } else {
         this.field_191802_a.func_110434_K().func_110577_a(TextureManager.field_194008_a);
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      int ☃ = MathHelper.func_76128_c(this.field_191811_n);
      int ☃x = MathHelper.func_76128_c(this.field_191812_o);
      int ☃xx = ☃ % 16;
      int ☃xxx = ☃x % 16;

      for(int ☃xxxx = -1; ☃xxxx <= 15; ++☃xxxx) {
         for(int ☃xxxxx = -1; ☃xxxxx <= 8; ++☃xxxxx) {
            func_146110_a(☃xx + 16 * ☃xxxx, ☃xxx + 16 * ☃xxxxx, 0.0F, 0.0F, 16, 16, 16.0F, 16.0F);
         }
      }

      this.field_191809_l.func_191819_a(☃, ☃x, true);
      this.field_191809_l.func_191819_a(☃, ☃x, false);
      this.field_191809_l.func_191817_b(☃, ☃x);
   }

   public void func_192991_a(int var1, int var2, int var3, int var4) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, 0.0F, 200.0F);
      func_73734_a(0, 0, 234, 113, MathHelper.func_76141_d(this.field_191815_r * 255.0F) << 24);
      boolean ☃ = false;
      int ☃x = MathHelper.func_76128_c(this.field_191811_n);
      int ☃xx = MathHelper.func_76128_c(this.field_191812_o);
      if (☃ > 0 && ☃ < 234 && ☃ > 0 && ☃ < 113) {
         for(GuiAdvancement ☃xxx : this.field_191810_m.values()) {
            if (☃xxx.func_191816_c(☃x, ☃xx, ☃, ☃)) {
               ☃ = true;
               ☃xxx.func_191821_a(☃x, ☃xx, this.field_191815_r, ☃, ☃);
               break;
            }
         }
      }

      GlStateManager.func_179121_F();
      if (☃) {
         this.field_191815_r = MathHelper.func_76131_a(this.field_191815_r + 0.02F, 0.0F, 0.3F);
      } else {
         this.field_191815_r = MathHelper.func_76131_a(this.field_191815_r - 0.04F, 0.0F, 1.0F);
      }
   }

   public boolean func_195627_a(int var1, int var2, double var3, double var5) {
      return this.field_191803_f.func_198891_a(☃, ☃, this.field_191804_g, ☃, ☃);
   }

   @Nullable
   public static GuiAdvancementTab func_193936_a(Minecraft var0, GuiScreenAdvancements var1, int var2, Advancement var3) {
      if (☃.func_192068_c() == null) {
         return null;
      } else {
         for(AdvancementTabType ☃ : AdvancementTabType.values()) {
            if (☃ < ☃.func_192650_a()) {
               return new GuiAdvancementTab(☃, ☃, ☃, ☃, ☃, ☃.func_192068_c());
            }

            ☃ -= ☃.func_192650_a();
         }

         return null;
      }
   }

   public void func_195626_a(double var1, double var3) {
      if (this.field_191813_p - this.field_193939_q > 234) {
         this.field_191811_n = MathHelper.func_151237_a(this.field_191811_n + ☃, (double)(-(this.field_191813_p - 234)), 0.0);
      }

      if (this.field_191814_q - this.field_193940_r > 113) {
         this.field_191812_o = MathHelper.func_151237_a(this.field_191812_o + ☃, (double)(-(this.field_191814_q - 113)), 0.0);
      }
   }

   public void func_191800_a(Advancement var1) {
      if (☃.func_192068_c() != null) {
         GuiAdvancement ☃ = new GuiAdvancement(this, this.field_191802_a, ☃, ☃.func_192068_c());
         this.func_193937_a(☃, ☃);
      }
   }

   private void func_193937_a(GuiAdvancement var1, Advancement var2) {
      this.field_191810_m.put(☃, ☃);
      int ☃ = ☃.func_191823_d();
      int ☃x = ☃ + 28;
      int ☃xx = ☃.func_191820_c();
      int ☃xxx = ☃xx + 27;
      this.field_193939_q = Math.min(this.field_193939_q, ☃);
      this.field_191813_p = Math.max(this.field_191813_p, ☃x);
      this.field_193940_r = Math.min(this.field_193940_r, ☃xx);
      this.field_191814_q = Math.max(this.field_191814_q, ☃xxx);

      for(GuiAdvancement ☃xxxx : this.field_191810_m.values()) {
         ☃xxxx.func_191825_b();
      }
   }

   @Nullable
   public GuiAdvancement func_191794_b(Advancement var1) {
      return (GuiAdvancement)this.field_191810_m.get(☃);
   }

   public GuiScreenAdvancements func_193934_g() {
      return this.field_193938_f;
   }
}
