package net.minecraft.client.renderer;

import com.google.common.collect.Ordering;
import java.util.Collection;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtil;

public abstract class InventoryEffectRenderer extends GuiContainer {
   protected boolean field_147045_u;

   public InventoryEffectRenderer(Container var1) {
      super(☃);
   }

   @Override
   protected void func_73866_w_() {
      super.func_73866_w_();
      this.func_175378_g();
   }

   protected void func_175378_g() {
      if (this.field_146297_k.field_71439_g.func_70651_bq().isEmpty()) {
         this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
         this.field_147045_u = false;
      } else {
         this.field_147003_i = 160 + (this.field_146294_l - this.field_146999_f - 200) / 2;
         this.field_147045_u = true;
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      super.func_73863_a(☃, ☃, ☃);
      if (this.field_147045_u) {
         this.func_147044_g();
      }
   }

   private void func_147044_g() {
      int ☃ = this.field_147003_i - 124;
      int ☃x = this.field_147009_r;
      int ☃xx = 166;
      Collection<PotionEffect> ☃xxx = this.field_146297_k.field_71439_g.func_70651_bq();
      if (!☃xxx.isEmpty()) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179140_f();
         int ☃xxxx = 33;
         if (☃xxx.size() > 5) {
            ☃xxxx = 132 / (☃xxx.size() - 1);
         }

         for(PotionEffect ☃xxxx : Ordering.natural().sortedCopy(☃xxx)) {
            Potion ☃xxxxx = ☃xxxx.func_188419_a();
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146297_k.func_110434_K().func_110577_a(field_147001_a);
            this.func_73729_b(☃, ☃x, 0, 166, 140, 32);
            if (☃xxxxx.func_76400_d()) {
               int ☃xxxxxx = ☃xxxxx.func_76392_e();
               this.func_73729_b(☃ + 6, ☃x + 7, ☃xxxxxx % 12 * 18, 198 + ☃xxxxxx / 12 * 18, 18, 18);
            }

            String ☃xxxxx = I18n.func_135052_a(☃xxxxx.func_76393_a());
            if (☃xxxx.func_76458_c() == 1) {
               ☃xxxxx = ☃xxxxx + ' ' + I18n.func_135052_a("enchantment.level.2");
            } else if (☃xxxx.func_76458_c() == 2) {
               ☃xxxxx = ☃xxxxx + ' ' + I18n.func_135052_a("enchantment.level.3");
            } else if (☃xxxx.func_76458_c() == 3) {
               ☃xxxxx = ☃xxxxx + ' ' + I18n.func_135052_a("enchantment.level.4");
            }

            this.field_146289_q.func_175063_a(☃xxxxx, (float)(☃ + 10 + 18), (float)(☃x + 6), 16777215);
            String ☃xxxxx = PotionUtil.func_188410_a(☃xxxx, 1.0F);
            this.field_146289_q.func_175063_a(☃xxxxx, (float)(☃ + 10 + 18), (float)(☃x + 6 + 10), 8355711);
            ☃x += ☃xxxx;
         }
      }
   }
}
