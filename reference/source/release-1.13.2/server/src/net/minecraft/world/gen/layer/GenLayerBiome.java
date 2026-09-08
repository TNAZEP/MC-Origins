package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class GenLayerBiome implements IC0Transformer {
   private static final int field_202727_a = IRegistry.field_212624_m.func_148757_b(Biomes.field_150583_P);
   private static final int field_202728_b = IRegistry.field_212624_m.func_148757_b(Biomes.field_76769_d);
   private static final int field_202729_c = IRegistry.field_212624_m.func_148757_b(Biomes.field_76770_e);
   private static final int field_202730_d = IRegistry.field_212624_m.func_148757_b(Biomes.field_76767_f);
   private static final int field_202731_e = IRegistry.field_212624_m.func_148757_b(Biomes.field_76774_n);
   private static final int field_202732_f = IRegistry.field_212624_m.func_148757_b(Biomes.field_76782_w);
   private static final int field_202733_g = IRegistry.field_212624_m.func_148757_b(Biomes.field_150608_ab);
   private static final int field_202734_h = IRegistry.field_212624_m.func_148757_b(Biomes.field_150607_aa);
   private static final int field_202735_i = IRegistry.field_212624_m.func_148757_b(Biomes.field_76789_p);
   private static final int field_202736_j = IRegistry.field_212624_m.func_148757_b(Biomes.field_76772_c);
   private static final int field_202737_k = IRegistry.field_212624_m.func_148757_b(Biomes.field_150578_U);
   private static final int field_202738_l = IRegistry.field_212624_m.func_148757_b(Biomes.field_150585_R);
   private static final int field_202739_m = IRegistry.field_212624_m.func_148757_b(Biomes.field_150588_X);
   private static final int field_202740_n = IRegistry.field_212624_m.func_148757_b(Biomes.field_76780_h);
   private static final int field_202741_o = IRegistry.field_212624_m.func_148757_b(Biomes.field_76768_g);
   private static final int field_202742_p = IRegistry.field_212624_m.func_148757_b(Biomes.field_150584_S);
   private static final int[] field_202743_q = new int[]{field_202728_b, field_202730_d, field_202729_c, field_202740_n, field_202736_j, field_202741_o};
   private static final int[] field_202744_r = new int[]{field_202728_b, field_202728_b, field_202728_b, field_202739_m, field_202739_m, field_202736_j};
   private static final int[] field_202745_s = new int[]{field_202730_d, field_202738_l, field_202729_c, field_202736_j, field_202727_a, field_202740_n};
   private static final int[] field_202746_t = new int[]{field_202730_d, field_202729_c, field_202741_o, field_202736_j};
   private static final int[] field_202747_u = new int[]{field_202731_e, field_202731_e, field_202731_e, field_202742_p};
   private final OverworldGenSettings field_175973_g;
   private int[] field_151623_c = field_202744_r;

   public GenLayerBiome(WorldType var1, OverworldGenSettings var2) {
      if (☃ == WorldType.field_77136_e) {
         this.field_151623_c = field_202743_q;
         this.field_175973_g = null;
      } else {
         this.field_175973_g = ☃;
      }
   }

   @Override
   public int func_202726_a(IContext var1, int var2) {
      if (this.field_175973_g != null && this.field_175973_g.func_202199_l() >= 0) {
         return this.field_175973_g.func_202199_l();
      } else {
         int ☃ = (☃ & 3840) >> 8;
         ☃ &= -3841;
         if (!LayerUtil.func_202827_a(☃) && ☃ != field_202735_i) {
            switch(☃) {
               case 1:
                  if (☃ > 0) {
                     return ☃.func_202696_a(3) == 0 ? field_202733_g : field_202734_h;
                  }

                  return this.field_151623_c[☃.func_202696_a(this.field_151623_c.length)];
               case 2:
                  if (☃ > 0) {
                     return field_202732_f;
                  }

                  return field_202745_s[☃.func_202696_a(field_202745_s.length)];
               case 3:
                  if (☃ > 0) {
                     return field_202737_k;
                  }

                  return field_202746_t[☃.func_202696_a(field_202746_t.length)];
               case 4:
                  return field_202747_u[☃.func_202696_a(field_202747_u.length)];
               default:
                  return field_202735_i;
            }
         } else {
            return ☃;
         }
      }
   }
}
