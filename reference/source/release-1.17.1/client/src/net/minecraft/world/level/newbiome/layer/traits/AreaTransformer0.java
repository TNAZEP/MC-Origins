package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface AreaTransformer0 {
   default <R extends Area> AreaFactory<R> run(BigContext<R> var1) {
      return () -> â˜ƒ.createResult((var2, var3) -> {
            â˜ƒ.initRandom((long)var2, (long)var3);
            return this.applyPixel(â˜ƒ, var2, var3);
         });
   }

   int applyPixel(Context var1, int var2, int var3);
}
