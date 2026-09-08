package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.IBishopTransformer;

public enum GenLayerAddMushroomIsland implements IBishopTransformer {
   INSTANCE;

   private static final int field_202793_b = IRegistry.field_212624_m.func_148757_b(Biomes.field_76789_p);

   @Override
   public int func_202792_a(IContext var1, int var2, int var3, int var4, int var5, int var6) {
      return LayerUtil.func_203631_b(☃)
            && LayerUtil.func_203631_b(☃)
            && LayerUtil.func_203631_b(☃)
            && LayerUtil.func_203631_b(☃)
            && LayerUtil.func_203631_b(☃)
            && ☃.func_202696_a(100) == 0
         ? field_202793_b
         : ☃;
   }
}
