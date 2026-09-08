package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.client.GameSettings;
import net.minecraft.client.resources.I18n;

public class GuiSnooper extends GuiScreen {
   private final GuiScreen field_146608_a;
   private final GameSettings field_146603_f;
   private final java.util.List<String> field_146604_g = Lists.newArrayList();
   private final java.util.List<String> field_146609_h = Lists.newArrayList();
   private String field_146610_i;
   private String[] field_146607_r;
   private GuiSnooper.List field_146606_s;
   private GuiButton field_146605_t;

   public GuiSnooper(GuiScreen var1, GameSettings var2) {
      this.field_146608_a = ☃;
      this.field_146603_f = ☃;
   }

   @Override
   public IGuiEventListener getFocused() {
      return this.field_146606_s;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146610_i = I18n.func_135052_a("options.snooper.title");
      String ☃ = I18n.func_135052_a("options.snooper.desc");
      java.util.List<String> ☃x = Lists.newArrayList();

      for(String ☃xx : this.field_146289_q.func_78271_c(☃, this.field_146294_l - 30)) {
         ☃x.add(☃xx);
      }

      this.field_146607_r = (String[])☃x.toArray(new String[☃x.size()]);
      this.field_146604_g.clear();
      this.field_146609_h.clear();
      GuiButton ☃xx = new GuiButton(
         1, this.field_146294_l / 2 - 152, this.field_146295_m - 30, 150, 20, this.field_146603_f.func_74297_c(GameSettings.Options.SNOOPER_ENABLED)
      ) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiSnooper.this.field_146603_f.func_74306_a(GameSettings.Options.SNOOPER_ENABLED, 1);
            GuiSnooper.this.field_146605_t.field_146126_j = GuiSnooper.this.field_146603_f.func_74297_c(GameSettings.Options.SNOOPER_ENABLED);
         }
      };
      ☃xx.field_146124_l = false;
      this.field_146605_t = this.func_189646_b(☃xx);
      this.func_189646_b(new GuiButton(2, this.field_146294_l / 2 + 2, this.field_146295_m - 30, 150, 20, I18n.func_135052_a("gui.done")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiSnooper.this.field_146603_f.func_74303_b();
            GuiSnooper.this.field_146603_f.func_74303_b();
            GuiSnooper.this.field_146297_k.func_147108_a(GuiSnooper.this.field_146608_a);
         }
      });
      boolean ☃xxx = this.field_146297_k.func_71401_C() != null && this.field_146297_k.func_71401_C().func_80003_ah() != null;

      for(Entry<String, String> ☃xxxx : new TreeMap(this.field_146297_k.func_71378_E().func_76465_c()).entrySet()) {
         this.field_146604_g.add((☃xxx ? "C " : "") + (String)☃xxxx.getKey());
         this.field_146609_h.add(this.field_146289_q.func_78269_a((String)☃xxxx.getValue(), this.field_146294_l - 220));
      }

      if (☃xxx) {
         for(Entry<String, String> ☃xxxx : new TreeMap(this.field_146297_k.func_71401_C().func_80003_ah().func_76465_c()).entrySet()) {
            this.field_146604_g.add("S " + (String)☃xxxx.getKey());
            this.field_146609_h.add(this.field_146289_q.func_78269_a((String)☃xxxx.getValue(), this.field_146294_l - 220));
         }
      }

      this.field_146606_s = new GuiSnooper.List();
      this.field_195124_j.add(this.field_146606_s);
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.field_146606_s.func_148128_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, this.field_146610_i, this.field_146294_l / 2, 8, 16777215);
      int ☃ = 22;

      for(String ☃x : this.field_146607_r) {
         this.func_73732_a(this.field_146289_q, ☃x, this.field_146294_l / 2, ☃, 8421504);
         ☃ += this.field_146289_q.field_78288_b;
      }

      super.func_73863_a(☃, ☃, ☃);
   }

   class List extends GuiSlot {
      public List() {
         super(
            GuiSnooper.this.field_146297_k,
            GuiSnooper.this.field_146294_l,
            GuiSnooper.this.field_146295_m,
            80,
            GuiSnooper.this.field_146295_m - 40,
            GuiSnooper.this.field_146289_q.field_78288_b + 1
         );
      }

      @Override
      protected int func_148127_b() {
         return GuiSnooper.this.field_146604_g.size();
      }

      @Override
      protected boolean func_148131_a(int var1) {
         return false;
      }

      @Override
      protected void func_148123_a() {
      }

      @Override
      protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
         GuiSnooper.this.field_146289_q.func_211126_b((String)GuiSnooper.this.field_146604_g.get(☃), 10.0F, (float)☃, 16777215);
         GuiSnooper.this.field_146289_q.func_211126_b((String)GuiSnooper.this.field_146609_h.get(☃), 230.0F, (float)☃, 16777215);
      }

      @Override
      protected int func_148137_d() {
         return this.field_148155_a - 10;
      }
   }
}
