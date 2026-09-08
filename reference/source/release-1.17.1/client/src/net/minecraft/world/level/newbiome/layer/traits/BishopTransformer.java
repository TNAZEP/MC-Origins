package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface BishopTransformer extends AreaTransformer1, DimensionOffset1Transformer {
   int apply(Context var1, int var2, int var3, int var4, int var5, int var6);

   @Override
   default int applyPixel(BigContext<?> var1, Area var2, int var3, int var4) {
      return this.apply(
         â˜ƒ,
         â˜ƒ.get(this.getParentX(â˜ƒ + 0), this.getParentY(â˜ƒ + 2)),
         â˜ƒ.get(this.getParentX(â˜ƒ + 2), this.getParentY(â˜ƒ + 2)),
         â˜ƒ.get(this.getParentX(â˜ƒ + 2), this.getParentY(â˜ƒ + 0)),
         â˜ƒ.get(this.getParentX(â˜ƒ + 0), this.getParentY(â˜ƒ + 0)),
         â˜ƒ.get(this.getParentX(â˜ƒ + 1), this.getParentY(â˜ƒ + 1))
      );
   }
}
