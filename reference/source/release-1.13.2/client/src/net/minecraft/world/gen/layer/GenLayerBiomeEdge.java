package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenLayerBiomeEdge implements ICastleTransformer {
   INSTANCE;

   private static final int field_202752_b = IRegistry.field_212624_m.func_148757_b(Biomes.field_76769_d);
   private static final int field_202753_c = IRegistry.field_212624_m.func_148757_b(Biomes.field_76770_e);
   private static final int field_202754_d = IRegistry.field_212624_m.func_148757_b(Biomes.field_150580_W);
   private static final int field_202755_e = IRegistry.field_212624_m.func_148757_b(Biomes.field_76774_n);
   private static final int field_202756_f = IRegistry.field_212624_m.func_148757_b(Biomes.field_76782_w);
   private static final int field_202757_g = IRegistry.field_212624_m.func_148757_b(Biomes.field_150574_L);
   private static final int field_202758_h = IRegistry.field_212624_m.func_148757_b(Biomes.field_150589_Z);
   private static final int field_202759_i = IRegistry.field_212624_m.func_148757_b(Biomes.field_150608_ab);
   private static final int field_202760_j = IRegistry.field_212624_m.func_148757_b(Biomes.field_150607_aa);
   private static final int field_202761_k = IRegistry.field_212624_m.func_148757_b(Biomes.field_76772_c);
   private static final int field_202762_l = IRegistry.field_212624_m.func_148757_b(Biomes.field_150578_U);
   private static final int field_202763_m = IRegistry.field_212624_m.func_148757_b(Biomes.field_76783_v);
   private static final int field_202764_n = IRegistry.field_212624_m.func_148757_b(Biomes.field_76780_h);
   private static final int field_202765_o = IRegistry.field_212624_m.func_148757_b(Biomes.field_76768_g);
   private static final int field_202766_p = IRegistry.field_212624_m.func_148757_b(Biomes.field_150584_S);

   @Override
   public int func_202748_a(IContext var1, int var2, int var3, int var4, int var5, int var6) {
      int[] ☃ = new int[1];
      if (!this.func_202751_a(☃, ☃, ☃, ☃, ☃, ☃, field_202753_c, field_202763_m)
         && !this.func_151635_b(☃, ☃, ☃, ☃, ☃, ☃, field_202760_j, field_202758_h)
         && !this.func_151635_b(☃, ☃, ☃, ☃, ☃, ☃, field_202759_i, field_202758_h)
         && !this.func_151635_b(☃, ☃, ☃, ☃, ☃, ☃, field_202762_l, field_202765_o)) {
         if (☃ != field_202752_b || ☃ != field_202755_e && ☃ != field_202755_e && ☃ != field_202755_e && ☃ != field_202755_e) {
            if (☃ == field_202764_n) {
               if (☃ == field_202752_b
                  || ☃ == field_202752_b
                  || ☃ == field_202752_b
                  || ☃ == field_202752_b
                  || ☃ == field_202766_p
                  || ☃ == field_202766_p
                  || ☃ == field_202766_p
                  || ☃ == field_202766_p
                  || ☃ == field_202755_e
                  || ☃ == field_202755_e
                  || ☃ == field_202755_e
                  || ☃ == field_202755_e) {
                  return field_202761_k;
               }

               if (☃ == field_202756_f || ☃ == field_202756_f || ☃ == field_202756_f || ☃ == field_202756_f) {
                  return field_202757_g;
               }
            }

            return ☃;
         } else {
            return field_202754_d;
         }
      } else {
         return ☃[0];
      }
   }

   private boolean func_202751_a(int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (!LayerUtil.func_202826_a(☃, ☃)) {
         return false;
      } else {
         if (this.func_151634_b(☃, ☃) && this.func_151634_b(☃, ☃) && this.func_151634_b(☃, ☃) && this.func_151634_b(☃, ☃)) {
            ☃[0] = ☃;
         } else {
            ☃[0] = ☃;
         }

         return true;
      }
   }

   private boolean func_151635_b(int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (☃ != ☃) {
         return false;
      } else {
         if (LayerUtil.func_202826_a(☃, ☃) && LayerUtil.func_202826_a(☃, ☃) && LayerUtil.func_202826_a(☃, ☃) && LayerUtil.func_202826_a(☃, ☃)) {
            ☃[0] = ☃;
         } else {
            ☃[0] = ☃;
         }

         return true;
      }
   }

   private boolean func_151634_b(int var1, int var2) {
      if (LayerUtil.func_202826_a(☃, ☃)) {
         return true;
      } else {
         Biome ☃ = IRegistry.field_212624_m.func_148754_a(☃);
         Biome ☃x = IRegistry.field_212624_m.func_148754_a(☃);
         if (☃ != null && ☃x != null) {
            Biome.TempCategory ☃xx = ☃.func_150561_m();
            Biome.TempCategory ☃xxx = ☃x.func_150561_m();
            return ☃xx == ☃xxx || ☃xx == Biome.TempCategory.MEDIUM || ☃xxx == Biome.TempCategory.MEDIUM;
         } else {
            return false;
         }
      }
   }
}
