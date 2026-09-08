package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface C0Transformer extends AreaTransformer1, DimensionOffset0Transformer {
   int apply(Context var1, int var2);

   @Override
   default int applyPixel(BigContext<?> var1, Area var2, int var3, int var4) {
      return this.apply(â˜ƒ, â˜ƒ.get(this.getParentX(â˜ƒ), this.getParentY(â˜ƒ)));
   }
}
