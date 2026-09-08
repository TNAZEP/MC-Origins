package net.minecraft.client.gui.inventory;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public abstract class GuiContainer extends GuiScreen {
   public static final ResourceLocation field_147001_a = new ResourceLocation("textures/gui/container/inventory.png");
   protected int field_146999_f = 176;
   protected int field_147000_g = 166;
   public Container field_147002_h;
   protected int field_147003_i;
   protected int field_147009_r;
   protected Slot field_147006_u;
   private Slot field_147005_v;
   private boolean field_147004_w;
   private ItemStack field_147012_x = ItemStack.field_190927_a;
   private int field_147011_y;
   private int field_147010_z;
   private Slot field_146989_A;
   private long field_146990_B;
   private ItemStack field_146991_C = ItemStack.field_190927_a;
   private Slot field_146985_D;
   private long field_146986_E;
   protected final Set<Slot> field_147008_s = Sets.<Slot>newHashSet();
   protected boolean field_147007_t;
   private int field_146987_F;
   private int field_146988_G;
   private boolean field_146995_H;
   private int field_146996_I;
   private long field_146997_J;
   private Slot field_146998_K;
   private int field_146992_L;
   private boolean field_146993_M;
   private ItemStack field_146994_N = ItemStack.field_190927_a;

   public GuiContainer(Container var1) {
      this.field_147002_h = ☃;
      this.field_146995_H = true;
   }

   @Override
   protected void func_73866_w_() {
      super.func_73866_w_();
      this.field_146297_k.field_71439_g.field_71070_bA = this.field_147002_h;
      this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
      this.field_147009_r = (this.field_146295_m - this.field_147000_g) / 2;
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      int ☃ = this.field_147003_i;
      int ☃x = this.field_147009_r;
      this.func_146976_a(☃, ☃, ☃);
      GlStateManager.func_179101_C();
      RenderHelper.func_74518_a();
      GlStateManager.func_179140_f();
      GlStateManager.func_179097_i();
      super.func_73863_a(☃, ☃, ☃);
      RenderHelper.func_74520_c();
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)☃, (float)☃x, 0.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179091_B();
      this.field_147006_u = null;
      int ☃xx = 240;
      int ☃xxx = 240;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);

      for(int ☃xxxx = 0; ☃xxxx < this.field_147002_h.field_75151_b.size(); ++☃xxxx) {
         Slot ☃xxxxx = (Slot)this.field_147002_h.field_75151_b.get(☃xxxx);
         if (☃xxxxx.func_111238_b()) {
            this.func_146977_a(☃xxxxx);
         }

         if (this.func_195362_a(☃xxxxx, (double)☃, (double)☃) && ☃xxxxx.func_111238_b()) {
            this.field_147006_u = ☃xxxxx;
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            int ☃xxxxx = ☃xxxxx.field_75223_e;
            int ☃xxxxxx = ☃xxxxx.field_75221_f;
            GlStateManager.func_179135_a(true, true, true, false);
            this.func_73733_a(☃xxxxx, ☃xxxxxx, ☃xxxxx + 16, ☃xxxxxx + 16, -2130706433, -2130706433);
            GlStateManager.func_179135_a(true, true, true, true);
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
         }
      }

      RenderHelper.func_74518_a();
      this.func_146979_b(☃, ☃);
      RenderHelper.func_74520_c();
      InventoryPlayer ☃xxxx = this.field_146297_k.field_71439_g.field_71071_by;
      ItemStack ☃xxxxx = this.field_147012_x.func_190926_b() ? ☃xxxx.func_70445_o() : this.field_147012_x;
      if (!☃xxxxx.func_190926_b()) {
         int ☃xxxxxx = 8;
         int ☃xxxxxxx = this.field_147012_x.func_190926_b() ? 8 : 16;
         String ☃xxxxxxxx = null;
         if (!this.field_147012_x.func_190926_b() && this.field_147004_w) {
            ☃xxxxx = ☃xxxxx.func_77946_l();
            ☃xxxxx.func_190920_e(MathHelper.func_76123_f((float)☃xxxxx.func_190916_E() / 2.0F));
         } else if (this.field_147007_t && this.field_147008_s.size() > 1) {
            ☃xxxxx = ☃xxxxx.func_77946_l();
            ☃xxxxx.func_190920_e(this.field_146996_I);
            if (☃xxxxx.func_190926_b()) {
               ☃xxxxxxxx = "" + TextFormatting.YELLOW + "0";
            }
         }

         this.func_146982_a(☃xxxxx, ☃ - ☃ - 8, ☃ - ☃x - ☃xxxxxxx, ☃xxxxxxxx);
      }

      if (!this.field_146991_C.func_190926_b()) {
         float ☃xxxx = (float)(Util.func_211177_b() - this.field_146990_B) / 100.0F;
         if (☃xxxx >= 1.0F) {
            ☃xxxx = 1.0F;
            this.field_146991_C = ItemStack.field_190927_a;
         }

         int ☃xxxx = this.field_146989_A.field_75223_e - this.field_147011_y;
         int ☃xxxxx = this.field_146989_A.field_75221_f - this.field_147010_z;
         int ☃xxxxxx = this.field_147011_y + (int)((float)☃xxxx * ☃xxxx);
         int ☃xxxxxxx = this.field_147010_z + (int)((float)☃xxxxx * ☃xxxx);
         this.func_146982_a(this.field_146991_C, ☃xxxxxx, ☃xxxxxxx, null);
      }

      GlStateManager.func_179121_F();
      GlStateManager.func_179145_e();
      GlStateManager.func_179126_j();
      RenderHelper.func_74519_b();
   }

   protected void func_191948_b(int var1, int var2) {
      if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b() && this.field_147006_u != null && this.field_147006_u.func_75216_d()) {
         this.func_146285_a(this.field_147006_u.func_75211_c(), ☃, ☃);
      }
   }

   private void func_146982_a(ItemStack var1, int var2, int var3, String var4) {
      GlStateManager.func_179109_b(0.0F, 0.0F, 32.0F);
      this.field_73735_i = 200.0F;
      this.field_146296_j.field_77023_b = 200.0F;
      this.field_146296_j.func_180450_b(☃, ☃, ☃);
      this.field_146296_j.func_180453_a(this.field_146289_q, ☃, ☃, ☃ - (this.field_147012_x.func_190926_b() ? 0 : 8), ☃);
      this.field_73735_i = 0.0F;
      this.field_146296_j.field_77023_b = 0.0F;
   }

   protected void func_146979_b(int var1, int var2) {
   }

   protected abstract void func_146976_a(float var1, int var2, int var3);

   private void func_146977_a(Slot var1) {
      int ☃ = ☃.field_75223_e;
      int ☃x = ☃.field_75221_f;
      ItemStack ☃xx = ☃.func_75211_c();
      boolean ☃xxx = false;
      boolean ☃xxxx = ☃ == this.field_147005_v && !this.field_147012_x.func_190926_b() && !this.field_147004_w;
      ItemStack ☃xxxxx = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
      String ☃xxxxxx = null;
      if (☃ == this.field_147005_v && !this.field_147012_x.func_190926_b() && this.field_147004_w && !☃xx.func_190926_b()) {
         ☃xx = ☃xx.func_77946_l();
         ☃xx.func_190920_e(☃xx.func_190916_E() / 2);
      } else if (this.field_147007_t && this.field_147008_s.contains(☃) && !☃xxxxx.func_190926_b()) {
         if (this.field_147008_s.size() == 1) {
            return;
         }

         if (Container.func_94527_a(☃, ☃xxxxx, true) && this.field_147002_h.func_94531_b(☃)) {
            ☃xx = ☃xxxxx.func_77946_l();
            ☃xxx = true;
            Container.func_94525_a(this.field_147008_s, this.field_146987_F, ☃xx, ☃.func_75211_c().func_190926_b() ? 0 : ☃.func_75211_c().func_190916_E());
            int ☃ = Math.min(☃xx.func_77976_d(), ☃.func_178170_b(☃xx));
            if (☃xx.func_190916_E() > ☃) {
               ☃xxxxxx = TextFormatting.YELLOW.toString() + ☃;
               ☃xx.func_190920_e(☃);
            }
         } else {
            this.field_147008_s.remove(☃);
            this.func_146980_g();
         }
      }

      this.field_73735_i = 100.0F;
      this.field_146296_j.field_77023_b = 100.0F;
      if (☃xx.func_190926_b() && ☃.func_111238_b()) {
         String ☃ = ☃.func_178171_c();
         if (☃ != null) {
            TextureAtlasSprite ☃x = this.field_146297_k.func_147117_R().func_110572_b(☃);
            GlStateManager.func_179140_f();
            this.field_146297_k.func_110434_K().func_110577_a(TextureMap.field_110575_b);
            this.func_175175_a(☃, ☃x, ☃x, 16, 16);
            GlStateManager.func_179145_e();
            ☃xxxx = true;
         }
      }

      if (!☃xxxx) {
         if (☃xxx) {
            func_73734_a(☃, ☃x, ☃ + 16, ☃x + 16, -2130706433);
         }

         GlStateManager.func_179126_j();
         this.field_146296_j.func_184391_a(this.field_146297_k.field_71439_g, ☃xx, ☃, ☃x);
         this.field_146296_j.func_180453_a(this.field_146289_q, ☃xx, ☃, ☃x, ☃xxxxxx);
      }

      this.field_146296_j.field_77023_b = 0.0F;
      this.field_73735_i = 0.0F;
   }

   private void func_146980_g() {
      ItemStack ☃ = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
      if (!☃.func_190926_b() && this.field_147007_t) {
         if (this.field_146987_F == 2) {
            this.field_146996_I = ☃.func_77976_d();
         } else {
            this.field_146996_I = ☃.func_190916_E();

            for(Slot ☃x : this.field_147008_s) {
               ItemStack ☃xx = ☃.func_77946_l();
               ItemStack ☃xxx = ☃x.func_75211_c();
               int ☃xxxx = ☃xxx.func_190926_b() ? 0 : ☃xxx.func_190916_E();
               Container.func_94525_a(this.field_147008_s, this.field_146987_F, ☃xx, ☃xxxx);
               int ☃xxxxx = Math.min(☃xx.func_77976_d(), ☃x.func_178170_b(☃xx));
               if (☃xx.func_190916_E() > ☃xxxxx) {
                  ☃xx.func_190920_e(☃xxxxx);
               }

               this.field_146996_I -= ☃xx.func_190916_E() - ☃xxxx;
            }
         }
      }
   }

   private Slot func_195360_a(double var1, double var3) {
      for(int ☃ = 0; ☃ < this.field_147002_h.field_75151_b.size(); ++☃) {
         Slot ☃x = (Slot)this.field_147002_h.field_75151_b.get(☃);
         if (this.func_195362_a(☃x, ☃, ☃) && ☃x.func_111238_b()) {
            return ☃x;
         }
      }

      return null;
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (super.mouseClicked(☃, ☃, ☃)) {
         return true;
      } else {
         boolean ☃ = this.field_146297_k.field_71474_y.field_74322_I.func_197984_a(☃);
         Slot ☃x = this.func_195360_a(☃, ☃);
         long ☃xx = Util.func_211177_b();
         this.field_146993_M = this.field_146998_K == ☃x && ☃xx - this.field_146997_J < 250L && this.field_146992_L == ☃;
         this.field_146995_H = false;
         if (☃ == 0 || ☃ == 1 || ☃) {
            int ☃xxx = this.field_147003_i;
            int ☃xxxx = this.field_147009_r;
            boolean ☃xxxxx = this.func_195361_a(☃, ☃, ☃xxx, ☃xxxx, ☃);
            int ☃xxxxxx = -1;
            if (☃x != null) {
               ☃xxxxxx = ☃x.field_75222_d;
            }

            if (☃xxxxx) {
               ☃xxxxxx = -999;
            }

            if (this.field_146297_k.field_71474_y.field_85185_A && ☃xxxxx && this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
               this.field_146297_k.func_147108_a(null);
               return true;
            }

            if (☃xxxxxx != -1) {
               if (this.field_146297_k.field_71474_y.field_85185_A) {
                  if (☃x != null && ☃x.func_75216_d()) {
                     this.field_147005_v = ☃x;
                     this.field_147012_x = ItemStack.field_190927_a;
                     this.field_147004_w = ☃ == 1;
                  } else {
                     this.field_147005_v = null;
                  }
               } else if (!this.field_147007_t) {
                  if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
                     if (this.field_146297_k.field_71474_y.field_74322_I.func_197984_a(☃)) {
                        this.func_184098_a(☃x, ☃xxxxxx, ☃, ClickType.CLONE);
                     } else {
                        boolean ☃xxx = ☃xxxxxx != -999 && (InputMappings.func_197956_a(340) || InputMappings.func_197956_a(344));
                        ClickType ☃xxxx = ClickType.PICKUP;
                        if (☃xxx) {
                           this.field_146994_N = ☃x != null && ☃x.func_75216_d() ? ☃x.func_75211_c().func_77946_l() : ItemStack.field_190927_a;
                           ☃xxxx = ClickType.QUICK_MOVE;
                        } else if (☃xxxxxx == -999) {
                           ☃xxxx = ClickType.THROW;
                        }

                        this.func_184098_a(☃x, ☃xxxxxx, ☃, ☃xxxx);
                     }

                     this.field_146995_H = true;
                  } else {
                     this.field_147007_t = true;
                     this.field_146988_G = ☃;
                     this.field_147008_s.clear();
                     if (☃ == 0) {
                        this.field_146987_F = 0;
                     } else if (☃ == 1) {
                        this.field_146987_F = 1;
                     } else if (this.field_146297_k.field_71474_y.field_74322_I.func_197984_a(☃)) {
                        this.field_146987_F = 2;
                     }
                  }
               }
            }
         }

         this.field_146998_K = ☃x;
         this.field_146997_J = ☃xx;
         this.field_146992_L = ☃;
         return true;
      }
   }

   protected boolean func_195361_a(double var1, double var3, int var5, int var6, int var7) {
      return ☃ < (double)☃ || ☃ < (double)☃ || ☃ >= (double)(☃ + this.field_146999_f) || ☃ >= (double)(☃ + this.field_147000_g);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      Slot ☃ = this.func_195360_a(☃, ☃);
      ItemStack ☃x = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
      if (this.field_147005_v != null && this.field_146297_k.field_71474_y.field_85185_A) {
         if (☃ == 0 || ☃ == 1) {
            if (this.field_147012_x.func_190926_b()) {
               if (☃ != this.field_147005_v && !this.field_147005_v.func_75211_c().func_190926_b()) {
                  this.field_147012_x = this.field_147005_v.func_75211_c().func_77946_l();
               }
            } else if (this.field_147012_x.func_190916_E() > 1 && ☃ != null && Container.func_94527_a(☃, this.field_147012_x, false)) {
               long ☃xx = Util.func_211177_b();
               if (this.field_146985_D == ☃) {
                  if (☃xx - this.field_146986_E > 500L) {
                     this.func_184098_a(this.field_147005_v, this.field_147005_v.field_75222_d, 0, ClickType.PICKUP);
                     this.func_184098_a(☃, ☃.field_75222_d, 1, ClickType.PICKUP);
                     this.func_184098_a(this.field_147005_v, this.field_147005_v.field_75222_d, 0, ClickType.PICKUP);
                     this.field_146986_E = ☃xx + 750L;
                     this.field_147012_x.func_190918_g(1);
                  }
               } else {
                  this.field_146985_D = ☃;
                  this.field_146986_E = ☃xx;
               }
            }
         }
      } else if (this.field_147007_t
         && ☃ != null
         && !☃x.func_190926_b()
         && (☃x.func_190916_E() > this.field_147008_s.size() || this.field_146987_F == 2)
         && Container.func_94527_a(☃, ☃x, true)
         && ☃.func_75214_a(☃x)
         && this.field_147002_h.func_94531_b(☃)) {
         this.field_147008_s.add(☃);
         this.func_146980_g();
      }

      return true;
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      Slot ☃ = this.func_195360_a(☃, ☃);
      int ☃x = this.field_147003_i;
      int ☃xx = this.field_147009_r;
      boolean ☃xxx = this.func_195361_a(☃, ☃, ☃x, ☃xx, ☃);
      int ☃xxxx = -1;
      if (☃ != null) {
         ☃xxxx = ☃.field_75222_d;
      }

      if (☃xxx) {
         ☃xxxx = -999;
      }

      if (this.field_146993_M && ☃ != null && ☃ == 0 && this.field_147002_h.func_94530_a(ItemStack.field_190927_a, ☃)) {
         if (func_146272_n()) {
            if (!this.field_146994_N.func_190926_b()) {
               for(Slot ☃ : this.field_147002_h.field_75151_b) {
                  if (☃ != null
                     && ☃.func_82869_a(this.field_146297_k.field_71439_g)
                     && ☃.func_75216_d()
                     && ☃.field_75224_c == ☃.field_75224_c
                     && Container.func_94527_a(☃, this.field_146994_N, true)) {
                     this.func_184098_a(☃, ☃.field_75222_d, ☃, ClickType.QUICK_MOVE);
                  }
               }
            }
         } else {
            this.func_184098_a(☃, ☃xxxx, ☃, ClickType.PICKUP_ALL);
         }

         this.field_146993_M = false;
         this.field_146997_J = 0L;
      } else {
         if (this.field_147007_t && this.field_146988_G != ☃) {
            this.field_147007_t = false;
            this.field_147008_s.clear();
            this.field_146995_H = true;
            return true;
         }

         if (this.field_146995_H) {
            this.field_146995_H = false;
            return true;
         }

         if (this.field_147005_v != null && this.field_146297_k.field_71474_y.field_85185_A) {
            if (☃ == 0 || ☃ == 1) {
               if (this.field_147012_x.func_190926_b() && ☃ != this.field_147005_v) {
                  this.field_147012_x = this.field_147005_v.func_75211_c();
               }

               boolean ☃ = Container.func_94527_a(☃, this.field_147012_x, false);
               if (☃xxxx != -1 && !this.field_147012_x.func_190926_b() && ☃) {
                  this.func_184098_a(this.field_147005_v, this.field_147005_v.field_75222_d, ☃, ClickType.PICKUP);
                  this.func_184098_a(☃, ☃xxxx, 0, ClickType.PICKUP);
                  if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
                     this.field_146991_C = ItemStack.field_190927_a;
                  } else {
                     this.func_184098_a(this.field_147005_v, this.field_147005_v.field_75222_d, ☃, ClickType.PICKUP);
                     this.field_147011_y = MathHelper.func_76128_c(☃ - (double)☃x);
                     this.field_147010_z = MathHelper.func_76128_c(☃ - (double)☃xx);
                     this.field_146989_A = this.field_147005_v;
                     this.field_146991_C = this.field_147012_x;
                     this.field_146990_B = Util.func_211177_b();
                  }
               } else if (!this.field_147012_x.func_190926_b()) {
                  this.field_147011_y = MathHelper.func_76128_c(☃ - (double)☃x);
                  this.field_147010_z = MathHelper.func_76128_c(☃ - (double)☃xx);
                  this.field_146989_A = this.field_147005_v;
                  this.field_146991_C = this.field_147012_x;
                  this.field_146990_B = Util.func_211177_b();
               }

               this.field_147012_x = ItemStack.field_190927_a;
               this.field_147005_v = null;
            }
         } else if (this.field_147007_t && !this.field_147008_s.isEmpty()) {
            this.func_184098_a(null, -999, Container.func_94534_d(0, this.field_146987_F), ClickType.QUICK_CRAFT);

            for(Slot ☃ : this.field_147008_s) {
               this.func_184098_a(☃, ☃.field_75222_d, Container.func_94534_d(1, this.field_146987_F), ClickType.QUICK_CRAFT);
            }

            this.func_184098_a(null, -999, Container.func_94534_d(2, this.field_146987_F), ClickType.QUICK_CRAFT);
         } else if (!this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
            if (this.field_146297_k.field_71474_y.field_74322_I.func_197984_a(☃)) {
               this.func_184098_a(☃, ☃xxxx, ☃, ClickType.CLONE);
            } else {
               boolean ☃ = ☃xxxx != -999 && (InputMappings.func_197956_a(340) || InputMappings.func_197956_a(344));
               if (☃) {
                  this.field_146994_N = ☃ != null && ☃.func_75216_d() ? ☃.func_75211_c().func_77946_l() : ItemStack.field_190927_a;
               }

               this.func_184098_a(☃, ☃xxxx, ☃, ☃ ? ClickType.QUICK_MOVE : ClickType.PICKUP);
            }
         }
      }

      if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
         this.field_146997_J = 0L;
      }

      this.field_147007_t = false;
      return true;
   }

   private boolean func_195362_a(Slot var1, double var2, double var4) {
      return this.func_195359_a(☃.field_75223_e, ☃.field_75221_f, 16, 16, ☃, ☃);
   }

   protected boolean func_195359_a(int var1, int var2, int var3, int var4, double var5, double var7) {
      int ☃ = this.field_147003_i;
      int ☃x = this.field_147009_r;
      ☃ -= (double)☃;
      ☃ -= (double)☃x;
      return ☃ >= (double)(☃ - 1) && ☃ < (double)(☃ + ☃ + 1) && ☃ >= (double)(☃ - 1) && ☃ < (double)(☃ + ☃ + 1);
   }

   protected void func_184098_a(Slot var1, int var2, int var3, ClickType var4) {
      if (☃ != null) {
         ☃ = ☃.field_75222_d;
      }

      this.field_146297_k.field_71442_b.func_187098_a(this.field_147002_h.field_75152_c, ☃, ☃, ☃, this.field_146297_k.field_71439_g);
   }

   @Override
   public boolean func_195120_Y_() {
      return false;
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(☃, ☃, ☃)) {
         return true;
      } else {
         if (☃ == 256 || this.field_146297_k.field_71474_y.field_151445_Q.func_197976_a(☃, ☃)) {
            this.field_146297_k.field_71439_g.func_71053_j();
         }

         this.func_195363_d(☃, ☃);
         if (this.field_147006_u != null && this.field_147006_u.func_75216_d()) {
            if (this.field_146297_k.field_71474_y.field_74322_I.func_197976_a(☃, ☃)) {
               this.func_184098_a(this.field_147006_u, this.field_147006_u.field_75222_d, 0, ClickType.CLONE);
            } else if (this.field_146297_k.field_71474_y.field_74316_C.func_197976_a(☃, ☃)) {
               this.func_184098_a(this.field_147006_u, this.field_147006_u.field_75222_d, func_146271_m() ? 1 : 0, ClickType.THROW);
            }
         }

         return true;
      }
   }

   protected boolean func_195363_d(int var1, int var2) {
      if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b() && this.field_147006_u != null) {
         for(int ☃ = 0; ☃ < 9; ++☃) {
            if (this.field_146297_k.field_71474_y.field_151456_ac[☃].func_197976_a(☃, ☃)) {
               this.func_184098_a(this.field_147006_u, this.field_147006_u.field_75222_d, ☃, ClickType.SWAP);
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void func_146281_b() {
      if (this.field_146297_k.field_71439_g != null) {
         this.field_147002_h.func_75134_a(this.field_146297_k.field_71439_g);
      }
   }

   @Override
   public boolean func_73868_f() {
      return false;
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      if (!this.field_146297_k.field_71439_g.func_70089_S() || this.field_146297_k.field_71439_g.field_70128_L) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }
   }
}
