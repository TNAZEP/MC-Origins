package net.minecraft.client.gui;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList extends GuiListExtended<GuiKeyBindingList.Entry> {
   private final GuiControls field_148191_k;
   private final Minecraft field_148189_l;
   private int field_148188_n;

   public GuiKeyBindingList(GuiControls var1, Minecraft var2) {
      super(☃, ☃.field_146294_l + 45, ☃.field_146295_m, 63, ☃.field_146295_m - 32, 20);
      this.field_148191_k = ☃;
      this.field_148189_l = ☃;
      KeyBinding[] ☃ = ArrayUtils.clone(☃.field_71474_y.field_74324_K);
      Arrays.sort(☃);
      String ☃x = null;

      for(KeyBinding ☃xx : ☃) {
         String ☃xxx = ☃xx.func_151466_e();
         if (!☃xxx.equals(☃x)) {
            ☃x = ☃xxx;
            this.func_195085_a(new GuiKeyBindingList.CategoryEntry(☃xxx));
         }

         int ☃xxx = ☃.field_71466_p.func_78256_a(I18n.func_135052_a(☃xx.func_151464_g()));
         if (☃xxx > this.field_148188_n) {
            this.field_148188_n = ☃xxx;
         }

         this.func_195085_a(new GuiKeyBindingList.KeyEntry(☃xx));
      }
   }

   @Override
   protected int func_148137_d() {
      return super.func_148137_d() + 15;
   }

   @Override
   public int func_148139_c() {
      return super.func_148139_c() + 32;
   }

   public class CategoryEntry extends GuiKeyBindingList.Entry {
      private final String field_148285_b;
      private final int field_148286_c;

      public CategoryEntry(String var2) {
         this.field_148285_b = I18n.func_135052_a(☃);
         this.field_148286_c = GuiKeyBindingList.this.field_148189_l.field_71466_p.func_78256_a(this.field_148285_b);
      }

      @Override
      public void func_194999_a(int var1, int var2, int var3, int var4, boolean var5, float var6) {
         GuiKeyBindingList.this.field_148189_l
            .field_71466_p
            .func_211126_b(
               this.field_148285_b,
               (float)(GuiKeyBindingList.this.field_148189_l.field_71462_r.field_146294_l / 2 - this.field_148286_c / 2),
               (float)(this.func_195001_c() + ☃ - GuiKeyBindingList.this.field_148189_l.field_71466_p.field_78288_b - 1),
               16777215
            );
      }
   }

   public abstract static class Entry extends GuiListExtended.IGuiListEntry<GuiKeyBindingList.Entry> {
   }

   public class KeyEntry extends GuiKeyBindingList.Entry {
      private final KeyBinding field_148282_b;
      private final String field_148283_c;
      private final GuiButton field_148280_d;
      private final GuiButton field_148281_e;

      private KeyEntry(final KeyBinding var2) {
         this.field_148282_b = ☃;
         this.field_148283_c = I18n.func_135052_a(☃.func_151464_g());
         this.field_148280_d = new GuiButton(0, 0, 0, 75, 20, I18n.func_135052_a(☃.func_151464_g())) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiKeyBindingList.this.field_148191_k.field_146491_f = ☃;
            }
         };
         this.field_148281_e = new GuiButton(0, 0, 0, 50, 20, I18n.func_135052_a("controls.reset")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiKeyBindingList.this.field_148189_l.field_71474_y.func_198014_a(☃, ☃.func_197977_i());
               KeyBinding.func_74508_b();
            }
         };
      }

      @Override
      public void func_194999_a(int var1, int var2, int var3, int var4, boolean var5, float var6) {
         int ☃ = this.func_195001_c();
         int ☃x = this.func_195002_d();
         boolean ☃xx = GuiKeyBindingList.this.field_148191_k.field_146491_f == this.field_148282_b;
         GuiKeyBindingList.this.field_148189_l
            .field_71466_p
            .func_211126_b(
               this.field_148283_c,
               (float)(☃x + 90 - GuiKeyBindingList.this.field_148188_n),
               (float)(☃ + ☃ / 2 - GuiKeyBindingList.this.field_148189_l.field_71466_p.field_78288_b / 2),
               16777215
            );
         this.field_148281_e.field_146128_h = ☃x + 190;
         this.field_148281_e.field_146129_i = ☃;
         this.field_148281_e.field_146124_l = !this.field_148282_b.func_197985_l();
         this.field_148281_e.func_194828_a(☃, ☃, ☃);
         this.field_148280_d.field_146128_h = ☃x + 105;
         this.field_148280_d.field_146129_i = ☃;
         this.field_148280_d.field_146126_j = this.field_148282_b.func_197978_k();
         boolean ☃xxx = false;
         if (!this.field_148282_b.func_197986_j()) {
            for(KeyBinding ☃xxxx : GuiKeyBindingList.this.field_148189_l.field_71474_y.field_74324_K) {
               if (☃xxxx != this.field_148282_b && this.field_148282_b.func_197983_b(☃xxxx)) {
                  ☃xxx = true;
                  break;
               }
            }
         }

         if (☃xx) {
            this.field_148280_d.field_146126_j = TextFormatting.WHITE
               + "> "
               + TextFormatting.YELLOW
               + this.field_148280_d.field_146126_j
               + TextFormatting.WHITE
               + " <";
         } else if (☃xxx) {
            this.field_148280_d.field_146126_j = TextFormatting.RED + this.field_148280_d.field_146126_j;
         }

         this.field_148280_d.func_194828_a(☃, ☃, ☃);
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (this.field_148280_d.mouseClicked(☃, ☃, ☃)) {
            return true;
         } else {
            return this.field_148281_e.mouseClicked(☃, ☃, ☃);
         }
      }

      @Override
      public boolean mouseReleased(double var1, double var3, int var5) {
         return this.field_148280_d.mouseReleased(☃, ☃, ☃) || this.field_148281_e.mouseReleased(☃, ☃, ☃);
      }
   }
}
