package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;

public class GuiLanguage extends GuiScreen {
   protected GuiScreen field_146453_a;
   private GuiLanguage.List field_146450_f;
   private final GameSettings field_146451_g;
   private final LanguageManager field_146454_h;
   private GuiOptionButton field_211832_i;
   private GuiOptionButton field_146452_r;

   public GuiLanguage(GuiScreen var1, GameSettings var2, LanguageManager var3) {
      this.field_146453_a = ☃;
      this.field_146451_g = ☃;
      this.field_146454_h = ☃;
   }

   @Override
   public IGuiEventListener getFocused() {
      return this.field_146450_f;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146450_f = new GuiLanguage.List(this.field_146297_k);
      this.field_195124_j.add(this.field_146450_f);
      this.field_211832_i = this.func_189646_b(
         new GuiOptionButton(
            100,
            this.field_146294_l / 2 - 155,
            this.field_146295_m - 38,
            GameSettings.Options.FORCE_UNICODE_FONT,
            this.field_146451_g.func_74297_c(GameSettings.Options.FORCE_UNICODE_FONT)
         ) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiLanguage.this.field_146451_g.func_74306_a(this.func_146136_c(), 1);
               this.field_146126_j = GuiLanguage.this.field_146451_g.func_74297_c(GameSettings.Options.FORCE_UNICODE_FONT);
               GuiLanguage.this.func_195181_h();
            }
         }
      );
      this.field_146452_r = this.func_189646_b(
         new GuiOptionButton(6, this.field_146294_l / 2 - 155 + 160, this.field_146295_m - 38, I18n.func_135052_a("gui.done")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiLanguage.this.field_146297_k.func_147108_a(GuiLanguage.this.field_146453_a);
            }
         }
      );
      super.func_73866_w_();
   }

   private void func_195181_h() {
      this.field_146297_k.field_195558_d.func_198098_h();
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.field_146450_f.func_148128_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("options.language"), this.field_146294_l / 2, 16, 16777215);
      this.func_73732_a(
         this.field_146289_q, "(" + I18n.func_135052_a("options.languageWarning") + ")", this.field_146294_l / 2, this.field_146295_m - 56, 8421504
      );
      super.func_73863_a(☃, ☃, ☃);
   }

   class List extends GuiSlot {
      private final java.util.List<String> field_148176_l = Lists.newArrayList();
      private final Map<String, Language> field_148177_m = Maps.newHashMap();

      public List(Minecraft var2) {
         super(☃, GuiLanguage.this.field_146294_l, GuiLanguage.this.field_146295_m, 32, GuiLanguage.this.field_146295_m - 65 + 4, 18);

         for(Language ☃ : GuiLanguage.this.field_146454_h.func_135040_d()) {
            this.field_148177_m.put(☃.func_135034_a(), ☃);
            this.field_148176_l.add(☃.func_135034_a());
         }
      }

      @Override
      protected int func_148127_b() {
         return this.field_148176_l.size();
      }

      @Override
      protected boolean func_195078_a(int var1, int var2, double var3, double var5) {
         Language ☃ = (Language)this.field_148177_m.get(this.field_148176_l.get(☃));
         GuiLanguage.this.field_146454_h.func_135045_a(☃);
         GuiLanguage.this.field_146451_g.field_74363_ab = ☃.func_135034_a();
         this.field_148161_k.func_110436_a();
         GuiLanguage.this.field_146289_q.func_78275_b(GuiLanguage.this.field_146454_h.func_135044_b());
         GuiLanguage.this.field_146452_r.field_146126_j = I18n.func_135052_a("gui.done");
         GuiLanguage.this.field_211832_i.field_146126_j = GuiLanguage.this.field_146451_g.func_74297_c(GameSettings.Options.FORCE_UNICODE_FONT);
         GuiLanguage.this.field_146451_g.func_74303_b();
         GuiLanguage.this.func_195181_h();
         return true;
      }

      @Override
      protected boolean func_148131_a(int var1) {
         return ((String)this.field_148176_l.get(☃)).equals(GuiLanguage.this.field_146454_h.func_135041_c().func_135034_a());
      }

      @Override
      protected int func_148138_e() {
         return this.func_148127_b() * 18;
      }

      @Override
      protected void func_148123_a() {
         GuiLanguage.this.func_146276_q_();
      }

      @Override
      protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
         GuiLanguage.this.field_146289_q.func_78275_b(true);
         this.func_73732_a(
            GuiLanguage.this.field_146289_q,
            ((Language)this.field_148177_m.get(this.field_148176_l.get(☃))).toString(),
            this.field_148155_a / 2,
            ☃ + 1,
            16777215
         );
         GuiLanguage.this.field_146289_q.func_78275_b(GuiLanguage.this.field_146454_h.func_135041_c().func_135035_b());
      }
   }
}
