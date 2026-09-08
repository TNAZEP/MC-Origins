package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.IContextExtended;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;

public interface IAreaTransformer2 extends IDimTransformer {
   default <R extends IArea> IAreaFactory<R> func_202707_a(IContextExtended<R> var1, IAreaFactory<R> var2, IAreaFactory<R> var3) {
      return var4 -> {
         R ☃ = ☃.make(this.func_202706_a(var4));
         R ☃x = ☃.make(this.func_202706_a(var4));
         return ☃.func_201488_a_(var4, (var5x, var6x) -> {
            ☃.func_202698_a((long)(var5x + var4.func_202690_a()), (long)(var6x + var4.func_202691_b()));
            return this.func_202709_a(☃, var4, ☃, ☃, var5x, var6x);
         }, ☃, ☃x);
      };
   }

   int func_202709_a(IContext var1, AreaDimension var2, IArea var3, IArea var4, int var5, int var6);
}
