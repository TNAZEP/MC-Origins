package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.IContextExtended;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;

public interface IBishopTransformer extends IAreaTransformer1, IDimOffset1Transformer {
   int func_202792_a(IContext var1, int var2, int var3, int var4, int var5, int var6);

   @Override
   default int func_202712_a(IContextExtended<?> var1, AreaDimension var2, IArea var3, int var4, int var5) {
      return this.func_202792_a(
         ☃,
         ☃.func_202678_a(☃ + 0, ☃ + 2),
         ☃.func_202678_a(☃ + 2, ☃ + 2),
         ☃.func_202678_a(☃ + 2, ☃ + 0),
         ☃.func_202678_a(☃ + 0, ☃ + 0),
         ☃.func_202678_a(☃ + 1, ☃ + 1)
      );
   }
}
