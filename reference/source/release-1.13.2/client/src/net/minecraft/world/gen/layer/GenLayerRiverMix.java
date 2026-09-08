package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum GenLayerRiverMix implements IAreaTransformer2, IDimOffset0Transformer {
   INSTANCE;

   private static final int field_202720_c = IRegistry.field_212624_m.func_148757_b(Biomes.field_76777_m);
   private static final int field_202721_d = IRegistry.field_212624_m.func_148757_b(Biomes.field_76774_n);
   private static final int field_202722_e = IRegistry.field_212624_m.func_148757_b(Biomes.field_76789_p);
   private static final int field_202723_f = IRegistry.field_212624_m.func_148757_b(Biomes.field_76788_q);
   private static final int field_202725_h = IRegistry.field_212624_m.func_148757_b(Biomes.field_76781_i);

   @Override
   public int func_202709_a(IContext var1, AreaDimension var2, IArea var3, IArea var4, int var5, int var6) {
      int ☃ = ☃.func_202678_a(☃, ☃);
      int ☃x = ☃.func_202678_a(☃, ☃);
      if (LayerUtil.func_202827_a(☃)) {
         return ☃;
      } else if (☃x == field_202725_h) {
         if (☃ == field_202721_d) {
            return field_202720_c;
         } else {
            return ☃ != field_202722_e && ☃ != field_202723_f ? ☃x & 0xFF : field_202723_f;
         }
      } else {
         return ☃;
      }
   }
}
