package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum GenLayerAddSnow implements IC1Transformer {
   INSTANCE;

   @Override
   public int func_202716_a(IContext var1, int var2) {
      if (LayerUtil.func_203631_b(☃)) {
         return ☃;
      } else {
         int ☃ = ☃.func_202696_a(6);
         if (☃ == 0) {
            return 4;
         } else {
            return ☃ == 1 ? 3 : 1;
         }
      }
   }
}
