package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenLayerRemoveTooMuchOcean implements ICastleTransformer {
   INSTANCE;

   @Override
   public int func_202748_a(IContext var1, int var2, int var3, int var4, int var5, int var6) {
      return LayerUtil.func_203631_b(☃)
            && LayerUtil.func_203631_b(☃)
            && LayerUtil.func_203631_b(☃)
            && LayerUtil.func_203631_b(☃)
            && LayerUtil.func_203631_b(☃)
            && ☃.func_202696_a(2) == 0
         ? 1
         : ☃;
   }
}
