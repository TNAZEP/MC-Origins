package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface AreaTransformer2 extends DimensionTransformer {
   default <R extends Area> AreaFactory<R> run(BigContext<R> var1, AreaFactory<R> var2, AreaFactory<R> var3) {
      return () -> {
         R â˜ƒ = â˜ƒ.make();
         R â˜ƒx = â˜ƒ.make();
         return â˜ƒ.createResult((var4x, var5x) -> {
            â˜ƒ.initRandom((long)var4x, (long)var5x);
            return this.applyPixel(â˜ƒ, â˜ƒ, â˜ƒ, var4x, var5x);
         }, â˜ƒ, â˜ƒx);
      };
   }

   int applyPixel(Context var1, Area var2, Area var3, int var4, int var5);
}
