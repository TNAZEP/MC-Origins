package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;

public class GuiScreenAlert extends GuiScreen {
   private final Runnable field_201552_h;
   protected final ITextComponent field_201548_a;
   protected final ITextComponent field_201550_f;
   private final List<String> field_201553_i = Lists.newArrayList();
   protected String field_201551_g;
   private int field_201549_s;

   public GuiScreenAlert(Runnable var1, ITextComponent var2, ITextComponent var3) {
      this(☃, ☃, ☃, "gui.back");
   }

   public GuiScreenAlert(Runnable var1, ITextComponent var2, ITextComponent var3, String var4) {
      this.field_201552_h = ☃;
      this.field_201548_a = ☃;
      this.field_201550_f = ☃;
      this.field_201551_g = I18n.func_135052_a(☃);
   }

   @Override
   protected void func_73866_w_() {
      super.func_73866_w_();
      this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 6 + 168, this.field_201551_g) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiScreenAlert.this.field_201552_h.run();
         }
      });
      this.field_201553_i.clear();
      this.field_201553_i.addAll(this.field_146289_q.func_78271_c(this.field_201550_f.func_150254_d(), this.field_146294_l - 50));
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_201548_a.func_150254_d(), this.field_146294_l / 2, 70, 16777215);
      int ☃ = 90;

      for(String ☃x : this.field_201553_i) {
         this.func_73732_a(this.field_146289_q, ☃x, this.field_146294_l / 2, ☃, 16777215);
         ☃ += this.field_146289_q.field_78288_b;
      }

      super.func_73863_a(☃, ☃, ☃);
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      if (--this.field_201549_s == 0) {
         for(GuiButton ☃ : this.field_146292_n) {
            ☃.field_146124_l = true;
         }
      }
   }
}
