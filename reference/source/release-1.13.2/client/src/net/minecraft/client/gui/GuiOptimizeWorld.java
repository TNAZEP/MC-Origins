package net.minecraft.client.gui;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.WorldOptimizer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.ISaveFormat;

public class GuiOptimizeWorld extends GuiScreen {
   private static final Object2IntMap<DimensionType> field_212348_a = Util.func_200696_a(new Object2IntOpenCustomHashMap<>(Util.func_212443_g()), var0 -> {
      var0.put(DimensionType.OVERWORLD, -13408734);
      var0.put(DimensionType.NETHER, -10075085);
      var0.put(DimensionType.THE_END, -8943531);
      var0.defaultReturnValue(-2236963);
   });
   private final GuiYesNoCallback field_212134_f;
   private final WorldOptimizer field_212203_f;

   public GuiOptimizeWorld(GuiYesNoCallback var1, String var2, ISaveFormat var3) {
      this.field_212134_f = ☃;
      this.field_212203_f = new WorldOptimizer(☃, ☃, ☃.func_75803_c(☃));
   }

   @Override
   protected void func_73866_w_() {
      super.func_73866_w_();
      this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 150, I18n.func_135052_a("gui.cancel")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiOptimizeWorld.this.field_212203_f.func_212217_a();
            GuiOptimizeWorld.this.field_212134_f.confirmResult(false, 0);
         }
      });
   }

   @Override
   public void func_73876_c() {
      if (this.field_212203_f.func_212218_b()) {
         this.field_212134_f.confirmResult(true, 0);
      }
   }

   @Override
   public void func_146281_b() {
      this.field_212203_f.func_212217_a();
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.func_73732_a(
         this.field_146289_q, I18n.func_135052_a("optimizeWorld.title", this.field_212203_f.func_212214_n()), this.field_146294_l / 2, 20, 16777215
      );
      int ☃ = this.field_146294_l / 2 - 150;
      int ☃x = this.field_146294_l / 2 + 150;
      int ☃xx = this.field_146295_m / 4 + 100;
      int ☃xxx = ☃xx + 10;
      this.func_73732_a(
         this.field_146289_q,
         this.field_212203_f.func_212215_m().func_150254_d(),
         this.field_146294_l / 2,
         ☃xx - this.field_146289_q.field_78288_b - 2,
         10526880
      );
      if (this.field_212203_f.func_212211_j() > 0) {
         func_73734_a(☃ - 1, ☃xx - 1, ☃x + 1, ☃xxx + 1, -16777216);
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("optimizeWorld.info.converted", this.field_212203_f.func_212208_k()), ☃, 40, 10526880);
         this.func_73731_b(
            this.field_146289_q,
            I18n.func_135052_a("optimizeWorld.info.skipped", this.field_212203_f.func_212209_l()),
            ☃,
            40 + this.field_146289_q.field_78288_b + 3,
            10526880
         );
         this.func_73731_b(
            this.field_146289_q,
            I18n.func_135052_a("optimizeWorld.info.total", this.field_212203_f.func_212211_j()),
            ☃,
            40 + (this.field_146289_q.field_78288_b + 3) * 2,
            10526880
         );
         int ☃xxxx = 0;

         for(DimensionType ☃xxxxx : DimensionType.func_212681_b()) {
            int ☃xxxxxx = MathHelper.func_76141_d(this.field_212203_f.func_212543_a(☃xxxxx) * (float)(☃x - ☃));
            func_73734_a(☃ + ☃xxxx, ☃xx, ☃ + ☃xxxx + ☃xxxxxx, ☃xxx, field_212348_a.getInt(☃xxxxx));
            ☃xxxx += ☃xxxxxx;
         }

         int ☃xxxxx = this.field_212203_f.func_212208_k() + this.field_212203_f.func_212209_l();
         this.func_73732_a(
            this.field_146289_q,
            ☃xxxxx + " / " + this.field_212203_f.func_212211_j(),
            this.field_146294_l / 2,
            ☃xx + 2 * this.field_146289_q.field_78288_b + 2,
            10526880
         );
         this.func_73732_a(
            this.field_146289_q,
            MathHelper.func_76141_d(this.field_212203_f.func_212207_i() * 100.0F) + "%",
            this.field_146294_l / 2,
            ☃xx + ((☃xxx - ☃xx) / 2 - this.field_146289_q.field_78288_b / 2),
            10526880
         );
      }

      super.func_73863_a(☃, ☃, ☃);
   }
}
