package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.area.AreaDimension;

public interface IDimOffset1Transformer extends IDimTransformer {
   @Override
   default AreaDimension func_202706_a(AreaDimension var1) {
      return new AreaDimension(☃.func_202690_a() - 1, ☃.func_202691_b() - 1, ☃.func_202688_c() + 2, ☃.func_202689_d() + 2);
   }
}
