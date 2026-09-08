package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum GenLayerMixOceans implements IAreaTransformer2, IDimOffset0Transformer {
   INSTANCE;

   @Override
   public int func_202709_a(IContext var1, AreaDimension var2, IArea var3, IArea var4, int var5, int var6) {
      int ☃ = ☃.func_202678_a(☃, ☃);
      int ☃x = ☃.func_202678_a(☃, ☃);
      if (!LayerUtil.func_202827_a(☃)) {
         return ☃;
      } else {
         int ☃ = 8;
         int ☃x = 4;

         for(int ☃xx = -8; ☃xx <= 8; ☃xx += 4) {
            for(int ☃xxx = -8; ☃xxx <= 8; ☃xxx += 4) {
               int ☃xxxx = ☃.func_202678_a(☃ + ☃xx, ☃ + ☃xxx);
               if (!LayerUtil.func_202827_a(☃xxxx)) {
                  if (☃x == LayerUtil.field_203632_a) {
                     return LayerUtil.field_203633_b;
                  }

                  if (☃x == LayerUtil.field_202831_b) {
                     return LayerUtil.field_203634_d;
                  }
               }
            }
         }

         if (☃ == LayerUtil.field_202830_a) {
            if (☃x == LayerUtil.field_203633_b) {
               return LayerUtil.field_203636_g;
            }

            if (☃x == LayerUtil.field_202832_c) {
               return LayerUtil.field_202830_a;
            }

            if (☃x == LayerUtil.field_203634_d) {
               return LayerUtil.field_203637_i;
            }

            if (☃x == LayerUtil.field_202831_b) {
               return LayerUtil.field_203638_j;
            }
         }

         return ☃x;
      }
   }
}
