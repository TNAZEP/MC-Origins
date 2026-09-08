package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketUpdateBeacon;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiBeacon extends GuiContainer {
   private static final Logger field_147026_u = LogManager.getLogger();
   private static final ResourceLocation field_147025_v = new ResourceLocation("textures/gui/container/beacon.png");
   private final IInventory field_147024_w;
   private GuiBeacon.ConfirmButton field_147028_x;
   private boolean field_147027_y;

   public GuiBeacon(InventoryPlayer var1, IInventory var2) {
      super(new ContainerBeacon(☃, ☃));
      this.field_147024_w = ☃;
      this.field_146999_f = 230;
      this.field_147000_g = 219;
   }

   @Override
   protected void func_73866_w_() {
      super.func_73866_w_();
      this.field_147028_x = new GuiBeacon.ConfirmButton(-1, this.field_147003_i + 164, this.field_147009_r + 107);
      this.func_189646_b(this.field_147028_x);
      this.func_189646_b(new GuiBeacon.CancelButton(-2, this.field_147003_i + 190, this.field_147009_r + 107));
      this.field_147027_y = true;
      this.field_147028_x.field_146124_l = false;
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      int ☃ = this.field_147024_w.func_174887_a_(0);
      Potion ☃x = Potion.func_188412_a(this.field_147024_w.func_174887_a_(1));
      Potion ☃xx = Potion.func_188412_a(this.field_147024_w.func_174887_a_(2));
      if (this.field_147027_y && ☃ >= 0) {
         this.field_147027_y = false;
         int ☃xxx = 100;

         for(int ☃xxxx = 0; ☃xxxx <= 2; ++☃xxxx) {
            int ☃xxxxx = TileEntityBeacon.field_146009_a[☃xxxx].length;
            int ☃xxxxxx = ☃xxxxx * 22 + (☃xxxxx - 1) * 2;

            for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxx; ++☃xxxxxxx) {
               Potion ☃xxxxxxxx = TileEntityBeacon.field_146009_a[☃xxxx][☃xxxxxxx];
               GuiBeacon.PowerButton ☃xxxxxxxxx = new GuiBeacon.PowerButton(
                  ☃xxx++, this.field_147003_i + 76 + ☃xxxxxxx * 24 - ☃xxxxxx / 2, this.field_147009_r + 22 + ☃xxxx * 25, ☃xxxxxxxx, ☃xxxx
               );
               this.func_189646_b(☃xxxxxxxxx);
               if (☃xxxx >= ☃) {
                  ☃xxxxxxxxx.field_146124_l = false;
               } else if (☃xxxxxxxx == ☃x) {
                  ☃xxxxxxxxx.func_146140_b(true);
               }
            }
         }

         int ☃xxxx = 3;
         int ☃xxxxx = TileEntityBeacon.field_146009_a[3].length + 1;
         int ☃xxxxxx = ☃xxxxx * 22 + (☃xxxxx - 1) * 2;

         for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxx - 1; ++☃xxxxxxx) {
            Potion ☃xxxxxxxx = TileEntityBeacon.field_146009_a[3][☃xxxxxxx];
            GuiBeacon.PowerButton ☃xxxxxxxxx = new GuiBeacon.PowerButton(
               ☃xxx++, this.field_147003_i + 167 + ☃xxxxxxx * 24 - ☃xxxxxx / 2, this.field_147009_r + 47, ☃xxxxxxxx, 3
            );
            this.func_189646_b(☃xxxxxxxxx);
            if (3 >= ☃) {
               ☃xxxxxxxxx.field_146124_l = false;
            } else if (☃xxxxxxxx == ☃xx) {
               ☃xxxxxxxxx.func_146140_b(true);
            }
         }

         if (☃x != null) {
            GuiBeacon.PowerButton ☃xxxxxxx = new GuiBeacon.PowerButton(
               ☃xxx++, this.field_147003_i + 167 + (☃xxxxx - 1) * 24 - ☃xxxxxx / 2, this.field_147009_r + 47, ☃x, 3
            );
            this.func_189646_b(☃xxxxxxx);
            if (3 >= ☃) {
               ☃xxxxxxx.field_146124_l = false;
            } else if (☃x == ☃xx) {
               ☃xxxxxxx.func_146140_b(true);
            }
         }
      }

      this.field_147028_x.field_146124_l = !this.field_147024_w.func_70301_a(0).func_190926_b() && ☃x != null;
   }

   @Override
   protected void func_146979_b(int var1, int var2) {
      RenderHelper.func_74518_a();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("block.minecraft.beacon.primary"), 62, 10, 14737632);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("block.minecraft.beacon.secondary"), 169, 10, 14737632);

      for(GuiButton ☃ : this.field_146292_n) {
         if (☃.func_146115_a()) {
            ☃.func_146111_b(☃ - this.field_147003_i, ☃ - this.field_147009_r);
            break;
         }
      }

      RenderHelper.func_74520_c();
   }

   @Override
   protected void func_146976_a(float var1, int var2, int var3) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147025_v);
      int ☃ = (this.field_146294_l - this.field_146999_f) / 2;
      int ☃x = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(☃, ☃x, 0, 0, this.field_146999_f, this.field_147000_g);
      this.field_146296_j.field_77023_b = 100.0F;
      this.field_146296_j.func_180450_b(new ItemStack(Items.field_151166_bC), ☃ + 42, ☃x + 109);
      this.field_146296_j.func_180450_b(new ItemStack(Items.field_151045_i), ☃ + 42 + 22, ☃x + 109);
      this.field_146296_j.func_180450_b(new ItemStack(Items.field_151043_k), ☃ + 42 + 44, ☃x + 109);
      this.field_146296_j.func_180450_b(new ItemStack(Items.field_151042_j), ☃ + 42 + 66, ☃x + 109);
      this.field_146296_j.field_77023_b = 0.0F;
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      super.func_73863_a(☃, ☃, ☃);
      this.func_191948_b(☃, ☃);
   }

   abstract static class Button extends GuiButton {
      private final ResourceLocation field_146145_o;
      private final int field_146144_p;
      private final int field_146143_q;
      private boolean field_146142_r;

      protected Button(int var1, int var2, int var3, ResourceLocation var4, int var5, int var6) {
         super(☃, ☃, ☃, 22, 22, "");
         this.field_146145_o = ☃;
         this.field_146144_p = ☃;
         this.field_146143_q = ☃;
      }

      @Override
      public void func_194828_a(int var1, int var2, float var3) {
         if (this.field_146125_m) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(GuiBeacon.field_147025_v);
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = ☃ >= this.field_146128_h
               && ☃ >= this.field_146129_i
               && ☃ < this.field_146128_h + this.field_146120_f
               && ☃ < this.field_146129_i + this.field_146121_g;
            int ☃ = 219;
            int ☃x = 0;
            if (!this.field_146124_l) {
               ☃x += this.field_146120_f * 2;
            } else if (this.field_146142_r) {
               ☃x += this.field_146120_f * 1;
            } else if (this.field_146123_n) {
               ☃x += this.field_146120_f * 3;
            }

            this.func_73729_b(this.field_146128_h, this.field_146129_i, ☃x, 219, this.field_146120_f, this.field_146121_g);
            if (!GuiBeacon.field_147025_v.equals(this.field_146145_o)) {
               Minecraft.func_71410_x().func_110434_K().func_110577_a(this.field_146145_o);
            }

            this.func_73729_b(this.field_146128_h + 2, this.field_146129_i + 2, this.field_146144_p, this.field_146143_q, 18, 18);
         }
      }

      public boolean func_146141_c() {
         return this.field_146142_r;
      }

      public void func_146140_b(boolean var1) {
         this.field_146142_r = ☃;
      }
   }

   class CancelButton extends GuiBeacon.Button {
      public CancelButton(int var2, int var3, int var4) {
         super(☃, ☃, ☃, GuiBeacon.field_147025_v, 112, 220);
      }

      @Override
      public void func_194829_a(double var1, double var3) {
         GuiBeacon.this.field_146297_k
            .field_71439_g
            .field_71174_a
            .func_147297_a(new CPacketCloseWindow(GuiBeacon.this.field_146297_k.field_71439_g.field_71070_bA.field_75152_c));
         GuiBeacon.this.field_146297_k.func_147108_a(null);
      }

      @Override
      public void func_146111_b(int var1, int var2) {
         GuiBeacon.this.func_146279_a(I18n.func_135052_a("gui.cancel"), ☃, ☃);
      }
   }

   class ConfirmButton extends GuiBeacon.Button {
      public ConfirmButton(int var2, int var3, int var4) {
         super(☃, ☃, ☃, GuiBeacon.field_147025_v, 90, 220);
      }

      @Override
      public void func_194829_a(double var1, double var3) {
         GuiBeacon.this.field_146297_k
            .func_147114_u()
            .func_147297_a(new CPacketUpdateBeacon(GuiBeacon.this.field_147024_w.func_174887_a_(1), GuiBeacon.this.field_147024_w.func_174887_a_(2)));
         GuiBeacon.this.field_146297_k
            .field_71439_g
            .field_71174_a
            .func_147297_a(new CPacketCloseWindow(GuiBeacon.this.field_146297_k.field_71439_g.field_71070_bA.field_75152_c));
         GuiBeacon.this.field_146297_k.func_147108_a(null);
      }

      @Override
      public void func_146111_b(int var1, int var2) {
         GuiBeacon.this.func_146279_a(I18n.func_135052_a("gui.done"), ☃, ☃);
      }
   }

   class PowerButton extends GuiBeacon.Button {
      private final Potion field_184066_p;
      private final int field_146148_q;

      public PowerButton(int var2, int var3, int var4, Potion var5, int var6) {
         super(☃, ☃, ☃, GuiContainer.field_147001_a, ☃.func_76392_e() % 12 * 18, 198 + ☃.func_76392_e() / 12 * 18);
         this.field_184066_p = ☃;
         this.field_146148_q = ☃;
      }

      @Override
      public void func_194829_a(double var1, double var3) {
         if (!this.func_146141_c()) {
            int ☃ = Potion.func_188409_a(this.field_184066_p);
            if (this.field_146148_q < 3) {
               GuiBeacon.this.field_147024_w.func_174885_b(1, ☃);
            } else {
               GuiBeacon.this.field_147024_w.func_174885_b(2, ☃);
            }

            GuiBeacon.this.field_146292_n.clear();
            GuiBeacon.this.field_195124_j.clear();
            GuiBeacon.this.func_73866_w_();
            GuiBeacon.this.func_73876_c();
         }
      }

      @Override
      public void func_146111_b(int var1, int var2) {
         String ☃ = I18n.func_135052_a(this.field_184066_p.func_76393_a());
         if (this.field_146148_q >= 3 && this.field_184066_p != MobEffects.field_76428_l) {
            ☃ = ☃ + " II";
         }

         GuiBeacon.this.func_146279_a(☃, ☃, ☃);
      }
   }
}
