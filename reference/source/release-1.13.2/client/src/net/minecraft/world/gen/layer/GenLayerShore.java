package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenLayerShore implements ICastleTransformer {
   INSTANCE;

   private static final int field_202768_b = IRegistry.field_212624_m.func_148757_b(Biomes.field_76787_r);
   private static final int field_202769_c = IRegistry.field_212624_m.func_148757_b(Biomes.field_150577_O);
   private static final int field_202771_e = IRegistry.field_212624_m.func_148757_b(Biomes.field_76769_d);
   private static final int field_202772_f = IRegistry.field_212624_m.func_148757_b(Biomes.field_76770_e);
   private static final int field_202773_g = IRegistry.field_212624_m.func_148757_b(Biomes.field_150580_W);
   private static final int field_202774_h = IRegistry.field_212624_m.func_148757_b(Biomes.field_76767_f);
   private static final int field_202775_i = IRegistry.field_212624_m.func_148757_b(Biomes.field_76782_w);
   private static final int field_202776_j = IRegistry.field_212624_m.func_148757_b(Biomes.field_150574_L);
   private static final int field_202777_k = IRegistry.field_212624_m.func_148757_b(Biomes.field_76792_x);
   private static final int field_202778_l = IRegistry.field_212624_m.func_148757_b(Biomes.field_150589_Z);
   private static final int field_202779_m = IRegistry.field_212624_m.func_148757_b(Biomes.field_150607_aa);
   private static final int field_202780_n = IRegistry.field_212624_m.func_148757_b(Biomes.field_150608_ab);
   private static final int field_202781_o = IRegistry.field_212624_m.func_148757_b(Biomes.field_185437_ai);
   private static final int field_202782_p = IRegistry.field_212624_m.func_148757_b(Biomes.field_185438_aj);
   private static final int field_202783_q = IRegistry.field_212624_m.func_148757_b(Biomes.field_185439_ak);
   private static final int field_202784_r = IRegistry.field_212624_m.func_148757_b(Biomes.field_76789_p);
   private static final int field_202785_s = IRegistry.field_212624_m.func_148757_b(Biomes.field_76788_q);
   private static final int field_202787_u = IRegistry.field_212624_m.func_148757_b(Biomes.field_76781_i);
   private static final int field_202788_v = IRegistry.field_212624_m.func_148757_b(Biomes.field_76783_v);
   private static final int field_202789_w = IRegistry.field_212624_m.func_148757_b(Biomes.field_150576_N);
   private static final int field_202790_x = IRegistry.field_212624_m.func_148757_b(Biomes.field_76780_h);
   private static final int field_202791_y = IRegistry.field_212624_m.func_148757_b(Biomes.field_76768_g);

   @Override
   public int func_202748_a(IContext var1, int var2, int var3, int var4, int var5, int var6) {
      Biome ☃ = IRegistry.field_212624_m.func_148754_a(☃);
      if (☃ == field_202784_r) {
         if (LayerUtil.func_203631_b(☃) || LayerUtil.func_203631_b(☃) || LayerUtil.func_203631_b(☃) || LayerUtil.func_203631_b(☃)) {
            return field_202785_s;
         }
      } else if (☃ != null && ☃.func_201856_r() == Biome.Category.JUNGLE) {
         if (!func_151631_c(☃) || !func_151631_c(☃) || !func_151631_c(☃) || !func_151631_c(☃)) {
            return field_202776_j;
         }

         if (LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃)) {
            return field_202768_b;
         }
      } else if (☃ != field_202772_f && ☃ != field_202773_g && ☃ != field_202788_v) {
         if (☃ != null && ☃.func_201851_b() == Biome.RainType.SNOW) {
            if (!LayerUtil.func_202827_a(☃)
               && (LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃))) {
               return field_202769_c;
            }
         } else if (☃ != field_202778_l && ☃ != field_202779_m) {
            if (!LayerUtil.func_202827_a(☃)
               && ☃ != field_202787_u
               && ☃ != field_202790_x
               && (LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃))) {
               return field_202768_b;
            }
         } else if (!LayerUtil.func_202827_a(☃)
            && !LayerUtil.func_202827_a(☃)
            && !LayerUtil.func_202827_a(☃)
            && !LayerUtil.func_202827_a(☃)
            && (!this.func_151633_d(☃) || !this.func_151633_d(☃) || !this.func_151633_d(☃) || !this.func_151633_d(☃))) {
            return field_202771_e;
         }
      } else if (!LayerUtil.func_202827_a(☃)
         && (LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃) || LayerUtil.func_202827_a(☃))) {
         return field_202789_w;
      }

      return ☃;
   }

   private static boolean func_151631_c(int var0) {
      if (IRegistry.field_212624_m.func_148754_a(☃) != null && IRegistry.field_212624_m.func_148754_a(☃).func_201856_r() == Biome.Category.JUNGLE) {
         return true;
      } else {
         return ☃ == field_202776_j || ☃ == field_202775_i || ☃ == field_202777_k || ☃ == field_202774_h || ☃ == field_202791_y || LayerUtil.func_202827_a(☃);
      }
   }

   private boolean func_151633_d(int var1) {
      return ☃ == field_202778_l || ☃ == field_202779_m || ☃ == field_202780_n || ☃ == field_202781_o || ☃ == field_202782_p || ☃ == field_202783_q;
   }
}
