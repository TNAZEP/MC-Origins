package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.context.BigContext;

public interface AreaTransformer1 extends DimensionTransformer {
   default <R extends Area> AreaFactory<R> run(BigContext<R> var1, AreaFactory<R> var2) {
      return () -> {
         R â˜ƒ = â˜ƒ.make();
         return â˜ƒ.createResult((var3x, var4) -> {
            â˜ƒ.initRandom((long)var3x, (long)var4);
            return this.applyPixel(â˜ƒ, â˜ƒ, var3x, var4);
         }, â˜ƒ);
      };
   }

   int applyPixel(BigContext<?> var1, Area var2, int var3, int var4);
}
