package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.NoiseGeneratorImproved;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum OceanLayer implements IAreaTransformer0 {
   INSTANCE;

   @Override
   public int func_202821_a(IContext var1, AreaDimension var2, int var3, int var4) {
      NoiseGeneratorImproved ☃ = ☃.func_205589_a();
      double ☃x = ☃.func_205562_a((double)(☃ + ☃.func_202690_a()) / 8.0, (double)(☃ + ☃.func_202691_b()) / 8.0);
      if (☃x > 0.4) {
         return LayerUtil.field_203632_a;
      } else if (☃x > 0.2) {
         return LayerUtil.field_203633_b;
      } else if (☃x < -0.4) {
         return LayerUtil.field_202831_b;
      } else {
         return ☃x < -0.2 ? LayerUtil.field_203634_d : LayerUtil.field_202832_c;
      }
   }
}
