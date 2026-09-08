package net.minecraft.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.util.MouseSmoother;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class MouseHelper {
   private final Minecraft field_198036_a;
   private boolean field_198037_b;
   private boolean field_198038_c;
   private boolean field_198039_d;
   private double field_198040_e;
   private double field_198041_f;
   private int field_212148_g;
   private int field_198042_g = -1;
   private boolean field_198043_h = true;
   private int field_198044_i;
   private double field_198045_j;
   private final MouseSmoother field_198046_k = new MouseSmoother();
   private final MouseSmoother field_198047_l = new MouseSmoother();
   private double field_198048_m;
   private double field_198049_n;
   private double field_200542_o;
   private double field_198050_o = Double.MIN_VALUE;
   private boolean field_198051_p;

   public MouseHelper(Minecraft var1) {
      this.field_198036_a = ☃;
   }

   private void func_198023_a(long var1, int var3, int var4, int var5) {
      if (☃ == this.field_198036_a.field_195558_d.func_198092_i()) {
         boolean ☃ = ☃ == 1;
         if (Minecraft.field_142025_a && ☃ == 0) {
            if (☃) {
               if ((☃ & 2) == 2) {
                  ☃ = 1;
                  ++this.field_212148_g;
               }
            } else if (this.field_212148_g > 0) {
               ☃ = 1;
               --this.field_212148_g;
            }
         }

         int ☃ = ☃;
         if (☃) {
            if (this.field_198036_a.field_71474_y.field_85185_A && this.field_198044_i++ > 0) {
               return;
            }

            this.field_198042_g = ☃;
            this.field_198045_j = GLFW.glfwGetTime();
         } else if (this.field_198042_g != -1) {
            if (this.field_198036_a.field_71474_y.field_85185_A && --this.field_198044_i > 0) {
               return;
            }

            this.field_198042_g = -1;
         }

         boolean[] ☃ = new boolean[]{false};
         if (this.field_198036_a.field_71462_r == null) {
            if (!this.field_198051_p && ☃) {
               this.func_198034_i();
            }
         } else {
            double ☃ = this.field_198040_e
               * (double)this.field_198036_a.field_195558_d.func_198107_o()
               / (double)this.field_198036_a.field_195558_d.func_198105_m();
            double ☃x = this.field_198041_f
               * (double)this.field_198036_a.field_195558_d.func_198087_p()
               / (double)this.field_198036_a.field_195558_d.func_198083_n();
            if (☃) {
               GuiScreen.func_195121_a(
                  () -> ☃[0] = this.field_198036_a.field_71462_r.mouseClicked(☃, ☃, ☃),
                  "mouseClicked event handler",
                  this.field_198036_a.field_71462_r.getClass().getCanonicalName()
               );
            } else {
               GuiScreen.func_195121_a(
                  () -> ☃[0] = this.field_198036_a.field_71462_r.mouseReleased(☃, ☃, ☃),
                  "mouseReleased event handler",
                  this.field_198036_a.field_71462_r.getClass().getCanonicalName()
               );
            }
         }

         if (!☃[0] && (this.field_198036_a.field_71462_r == null || this.field_198036_a.field_71462_r.field_146291_p)) {
            if (☃ == 0) {
               this.field_198037_b = ☃;
            } else if (☃ == 2) {
               this.field_198038_c = ☃;
            } else if (☃ == 1) {
               this.field_198039_d = ☃;
            }

            KeyBinding.func_197980_a(InputMappings.Type.MOUSE.func_197944_a(☃), ☃);
            if (☃) {
               if (this.field_198036_a.field_71439_g.func_175149_v() && ☃ == 2) {
                  this.field_198036_a.field_71456_v.func_175187_g().func_175261_b();
               } else {
                  KeyBinding.func_197981_a(InputMappings.Type.MOUSE.func_197944_a(☃));
               }
            }
         }
      }
   }

   private void func_198020_a(long var1, double var3, double var5) {
      if (☃ == Minecraft.func_71410_x().field_195558_d.func_198092_i()) {
         double ☃ = ☃ * this.field_198036_a.field_71474_y.field_208033_V;
         if (this.field_198036_a.field_71462_r != null) {
            this.field_198036_a.field_71462_r.mouseScrolled(☃);
         } else if (this.field_198036_a.field_71439_g != null) {
            if (this.field_200542_o != 0.0 && Math.signum(☃) != Math.signum(this.field_200542_o)) {
               this.field_200542_o = 0.0;
            }

            this.field_200542_o += ☃;
            double ☃ = (double)((int)this.field_200542_o);
            if (☃ == 0.0) {
               return;
            }

            this.field_200542_o -= ☃;
            if (this.field_198036_a.field_71439_g.func_175149_v()) {
               if (this.field_198036_a.field_71456_v.func_175187_g().func_175262_a()) {
                  this.field_198036_a.field_71456_v.func_175187_g().func_195621_a(-☃);
               } else {
                  double ☃ = MathHelper.func_151237_a((double)this.field_198036_a.field_71439_g.field_71075_bZ.func_75093_a() + ☃ * 0.005F, 0.0, 0.2F);
                  this.field_198036_a.field_71439_g.field_71075_bZ.func_195931_a(☃);
               }
            } else {
               this.field_198036_a.field_71439_g.field_71071_by.func_195409_a(☃);
            }
         }
      }
   }

   public void func_198029_a(long var1) {
      GLFW.glfwSetCursorPosCallback(☃, this::func_198022_b);
      GLFW.glfwSetMouseButtonCallback(☃, this::func_198023_a);
      GLFW.glfwSetScrollCallback(☃, this::func_198020_a);
   }

   private void func_198022_b(long var1, double var3, double var5) {
      if (☃ == Minecraft.func_71410_x().field_195558_d.func_198092_i()) {
         if (this.field_198043_h) {
            this.field_198040_e = ☃;
            this.field_198041_f = ☃;
            this.field_198043_h = false;
         }

         IGuiEventListener ☃ = this.field_198036_a.field_71462_r;
         if (this.field_198042_g != -1 && this.field_198045_j > 0.0 && ☃ != null) {
            double ☃x = ☃ * (double)this.field_198036_a.field_195558_d.func_198107_o() / (double)this.field_198036_a.field_195558_d.func_198105_m();
            double ☃xx = ☃ * (double)this.field_198036_a.field_195558_d.func_198087_p() / (double)this.field_198036_a.field_195558_d.func_198083_n();
            double ☃xxx = (☃ - this.field_198040_e)
               * (double)this.field_198036_a.field_195558_d.func_198107_o()
               / (double)this.field_198036_a.field_195558_d.func_198105_m();
            double ☃xxxx = (☃ - this.field_198041_f)
               * (double)this.field_198036_a.field_195558_d.func_198087_p()
               / (double)this.field_198036_a.field_195558_d.func_198083_n();
            GuiScreen.func_195121_a(() -> ☃.mouseDragged(☃, ☃, this.field_198042_g, ☃, ☃), "mouseDragged event handler", ☃.getClass().getCanonicalName());
         }

         this.field_198036_a.field_71424_I.func_76320_a("mouse");
         if (this.func_198035_h() && this.field_198036_a.func_195544_aj()) {
            this.field_198048_m += ☃ - this.field_198040_e;
            this.field_198049_n += ☃ - this.field_198041_f;
         }

         this.func_198028_a();
         this.field_198040_e = ☃;
         this.field_198041_f = ☃;
         this.field_198036_a.field_71424_I.func_76319_b();
      }
   }

   public void func_198028_a() {
      double ☃ = GLFW.glfwGetTime();
      double ☃x = ☃ - this.field_198050_o;
      this.field_198050_o = ☃;
      if (this.func_198035_h() && this.field_198036_a.func_195544_aj()) {
         double ☃xxxx = this.field_198036_a.field_71474_y.field_74341_c * 0.6F + 0.2F;
         double ☃xxxxx = ☃xxxx * ☃xxxx * ☃xxxx * 8.0;
         double ☃xx;
         double ☃xxx;
         if (this.field_198036_a.field_71474_y.field_74326_T) {
            double ☃xxxxxx = this.field_198046_k.func_199102_a(this.field_198048_m * ☃xxxxx, ☃x * ☃xxxxx);
            double ☃xxxxxxx = this.field_198047_l.func_199102_a(this.field_198049_n * ☃xxxxx, ☃x * ☃xxxxx);
            ☃xx = ☃xxxxxx;
            ☃xxx = ☃xxxxxxx;
         } else {
            this.field_198046_k.func_199101_a();
            this.field_198047_l.func_199101_a();
            ☃xx = this.field_198048_m * ☃xxxxx;
            ☃xxx = this.field_198049_n * ☃xxxxx;
         }

         this.field_198048_m = 0.0;
         this.field_198049_n = 0.0;
         int ☃xx = 1;
         if (this.field_198036_a.field_71474_y.field_74338_d) {
            ☃xx = -1;
         }

         this.field_198036_a.func_193032_ao().func_195872_a(☃xx, ☃xxx);
         if (this.field_198036_a.field_71439_g != null) {
            this.field_198036_a.field_71439_g.func_195049_a(☃xx, ☃xxx * (double)☃xx);
         }
      } else {
         this.field_198048_m = 0.0;
         this.field_198049_n = 0.0;
      }
   }

   public boolean func_198030_b() {
      return this.field_198037_b;
   }

   public boolean func_198031_d() {
      return this.field_198039_d;
   }

   public double func_198024_e() {
      return this.field_198040_e;
   }

   public double func_198026_f() {
      return this.field_198041_f;
   }

   public void func_198021_g() {
      this.field_198043_h = true;
   }

   public boolean func_198035_h() {
      return this.field_198051_p;
   }

   public void func_198034_i() {
      if (this.field_198036_a.func_195544_aj()) {
         if (!this.field_198051_p) {
            if (!Minecraft.field_142025_a) {
               KeyBinding.func_186704_a();
            }

            this.field_198051_p = true;
            this.field_198040_e = (double)(this.field_198036_a.field_195558_d.func_198105_m() / 2);
            this.field_198041_f = (double)(this.field_198036_a.field_195558_d.func_198083_n() / 2);
            GLFW.glfwSetCursorPos(this.field_198036_a.field_195558_d.func_198092_i(), this.field_198040_e, this.field_198041_f);
            GLFW.glfwSetInputMode(this.field_198036_a.field_195558_d.func_198092_i(), 208897, 212995);
            this.field_198036_a.func_147108_a(null);
            this.field_198036_a.field_71429_W = 10000;
         }
      }
   }

   public void func_198032_j() {
      if (this.field_198051_p) {
         this.field_198051_p = false;
         GLFW.glfwSetInputMode(this.field_198036_a.field_195558_d.func_198092_i(), 208897, 212993);
         this.field_198040_e = (double)(this.field_198036_a.field_195558_d.func_198105_m() / 2);
         this.field_198041_f = (double)(this.field_198036_a.field_195558_d.func_198083_n() / 2);
         GLFW.glfwSetCursorPos(this.field_198036_a.field_195558_d.func_198092_i(), this.field_198040_e, this.field_198041_f);
      }
   }
}
