package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer2;
import net.minecraft.world.level.newbiome.layer.traits.DimensionOffset0Transformer;

public enum RiverMixerLayer implements AreaTransformer2, DimensionOffset0Transformer {
   INSTANCE;

   @Override
   public int applyPixel(Context var1, Area var2, Area var3, int var4, int var5) {
      int â˜ƒ = â˜ƒ.get(this.getParentX(â˜ƒ), this.getParentY(â˜ƒ));
      int â˜ƒx = â˜ƒ.get(this.getParentX(â˜ƒ), this.getParentY(â˜ƒ));
      if (Layers.isOcean(â˜ƒ)) {
         return â˜ƒ;
      } else if (â˜ƒx == 7) {
         if (â˜ƒ == 12) {
            return 11;
         } else {
            return â˜ƒ != 14 && â˜ƒ != 15 ? â˜ƒx & 0xFF : 15;
         }
      } else {
         return â˜ƒ;
      }
   }
}
