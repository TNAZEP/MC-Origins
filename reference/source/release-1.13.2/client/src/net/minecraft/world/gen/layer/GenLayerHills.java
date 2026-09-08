package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum GenLayerHills implements IAreaTransformer2, IDimOffset1Transformer {
   INSTANCE;

   private static final Logger field_151629_c = LogManager.getLogger();
   private static final int field_202796_c = IRegistry.field_212624_m.func_148757_b(Biomes.field_150583_P);
   private static final int field_202797_d = IRegistry.field_212624_m.func_148757_b(Biomes.field_150582_Q);
   private static final int field_202799_f = IRegistry.field_212624_m.func_148757_b(Biomes.field_76769_d);
   private static final int field_202800_g = IRegistry.field_212624_m.func_148757_b(Biomes.field_76786_s);
   private static final int field_202801_h = IRegistry.field_212624_m.func_148757_b(Biomes.field_76770_e);
   private static final int field_202802_i = IRegistry.field_212624_m.func_148757_b(Biomes.field_150580_W);
   private static final int field_202803_j = IRegistry.field_212624_m.func_148757_b(Biomes.field_76767_f);
   private static final int field_202804_k = IRegistry.field_212624_m.func_148757_b(Biomes.field_76785_t);
   private static final int field_202805_l = IRegistry.field_212624_m.func_148757_b(Biomes.field_76774_n);
   private static final int field_202806_m = IRegistry.field_212624_m.func_148757_b(Biomes.field_76775_o);
   private static final int field_202807_n = IRegistry.field_212624_m.func_148757_b(Biomes.field_76782_w);
   private static final int field_202808_o = IRegistry.field_212624_m.func_148757_b(Biomes.field_76792_x);
   private static final int field_202809_p = IRegistry.field_212624_m.func_148757_b(Biomes.field_150589_Z);
   private static final int field_202810_q = IRegistry.field_212624_m.func_148757_b(Biomes.field_150607_aa);
   private static final int field_202812_s = IRegistry.field_212624_m.func_148757_b(Biomes.field_76772_c);
   private static final int field_202813_t = IRegistry.field_212624_m.func_148757_b(Biomes.field_150578_U);
   private static final int field_202814_u = IRegistry.field_212624_m.func_148757_b(Biomes.field_150581_V);
   private static final int field_202815_v = IRegistry.field_212624_m.func_148757_b(Biomes.field_150585_R);
   private static final int field_202816_w = IRegistry.field_212624_m.func_148757_b(Biomes.field_150588_X);
   private static final int field_202817_x = IRegistry.field_212624_m.func_148757_b(Biomes.field_150587_Y);
   private static final int field_202818_y = IRegistry.field_212624_m.func_148757_b(Biomes.field_76768_g);
   private static final int field_202819_z = IRegistry.field_212624_m.func_148757_b(Biomes.field_150584_S);
   private static final int field_202794_A = IRegistry.field_212624_m.func_148757_b(Biomes.field_150579_T);
   private static final int field_202795_B = IRegistry.field_212624_m.func_148757_b(Biomes.field_76784_u);

   @Override
   public int func_202709_a(IContext var1, AreaDimension var2, IArea var3, IArea var4, int var5, int var6) {
      int ☃ = ☃.func_202678_a(☃ + 1, ☃ + 1);
      int ☃x = ☃.func_202678_a(☃ + 1, ☃ + 1);
      if (☃ > 255) {
         field_151629_c.debug("old! {}", ☃);
      }

      int ☃ = (☃x - 2) % 29;
      if (!LayerUtil.func_203631_b(☃) && ☃x >= 2 && ☃ == 1) {
         Biome ☃x = IRegistry.field_212624_m.func_148754_a(☃);
         if (☃x == null || !☃x.func_185363_b()) {
            Biome ☃xx = Biome.func_185356_b(☃x);
            return ☃xx == null ? ☃ : IRegistry.field_212624_m.func_148757_b(☃xx);
         }
      }

      if (☃.func_202696_a(3) == 0 || ☃ == 0) {
         int ☃ = ☃;
         if (☃ == field_202799_f) {
            ☃ = field_202800_g;
         } else if (☃ == field_202803_j) {
            ☃ = field_202804_k;
         } else if (☃ == field_202796_c) {
            ☃ = field_202797_d;
         } else if (☃ == field_202815_v) {
            ☃ = field_202812_s;
         } else if (☃ == field_202818_y) {
            ☃ = field_202795_B;
         } else if (☃ == field_202813_t) {
            ☃ = field_202814_u;
         } else if (☃ == field_202819_z) {
            ☃ = field_202794_A;
         } else if (☃ == field_202812_s) {
            ☃ = ☃.func_202696_a(3) == 0 ? field_202804_k : field_202803_j;
         } else if (☃ == field_202805_l) {
            ☃ = field_202806_m;
         } else if (☃ == field_202807_n) {
            ☃ = field_202808_o;
         } else if (☃ == LayerUtil.field_202832_c) {
            ☃ = LayerUtil.field_202830_a;
         } else if (☃ == LayerUtil.field_203633_b) {
            ☃ = LayerUtil.field_203636_g;
         } else if (☃ == LayerUtil.field_203634_d) {
            ☃ = LayerUtil.field_203637_i;
         } else if (☃ == LayerUtil.field_202831_b) {
            ☃ = LayerUtil.field_203638_j;
         } else if (☃ == field_202801_h) {
            ☃ = field_202802_i;
         } else if (☃ == field_202816_w) {
            ☃ = field_202817_x;
         } else if (LayerUtil.func_202826_a(☃, field_202810_q)) {
            ☃ = field_202809_p;
         } else if ((☃ == LayerUtil.field_202830_a || ☃ == LayerUtil.field_203636_g || ☃ == LayerUtil.field_203637_i || ☃ == LayerUtil.field_203638_j)
            && ☃.func_202696_a(3) == 0) {
            ☃ = ☃.func_202696_a(2) == 0 ? field_202812_s : field_202803_j;
         }

         if (☃ == 0 && ☃ != ☃) {
            Biome ☃ = Biome.func_185356_b(IRegistry.field_212624_m.func_148754_a(☃));
            ☃ = ☃ == null ? ☃ : IRegistry.field_212624_m.func_148757_b(☃);
         }

         if (☃ != ☃) {
            int ☃ = 0;
            if (LayerUtil.func_202826_a(☃.func_202678_a(☃ + 1, ☃ + 0), ☃)) {
               ++☃;
            }

            if (LayerUtil.func_202826_a(☃.func_202678_a(☃ + 2, ☃ + 1), ☃)) {
               ++☃;
            }

            if (LayerUtil.func_202826_a(☃.func_202678_a(☃ + 0, ☃ + 1), ☃)) {
               ++☃;
            }

            if (LayerUtil.func_202826_a(☃.func_202678_a(☃ + 1, ☃ + 2), ☃)) {
               ++☃;
            }

            if (☃ >= 3) {
               return ☃;
            }
         }
      }

      return ☃;
   }
}
