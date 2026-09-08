package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenLayerDeepOcean implements ICastleTransformer {
   INSTANCE;

   @Override
   public int func_202748_a(IContext var1, int var2, int var3, int var4, int var5, int var6) {
      if (LayerUtil.func_203631_b(☃)) {
         int ☃ = 0;
         if (LayerUtil.func_203631_b(☃)) {
            ++☃;
         }

         if (LayerUtil.func_203631_b(☃)) {
            ++☃;
         }

         if (LayerUtil.func_203631_b(☃)) {
            ++☃;
         }

         if (LayerUtil.func_203631_b(☃)) {
            ++☃;
         }

         if (☃ > 3) {
            if (☃ == LayerUtil.field_203632_a) {
               return LayerUtil.field_203635_f;
            }

            if (☃ == LayerUtil.field_203633_b) {
               return LayerUtil.field_203636_g;
            }

            if (☃ == LayerUtil.field_202832_c) {
               return LayerUtil.field_202830_a;
            }

            if (☃ == LayerUtil.field_203634_d) {
               return LayerUtil.field_203637_i;
            }

            if (☃ == LayerUtil.field_202831_b) {
               return LayerUtil.field_203638_j;
            }

            return LayerUtil.field_202830_a;
         }
      }

      return ☃;
   }
}
