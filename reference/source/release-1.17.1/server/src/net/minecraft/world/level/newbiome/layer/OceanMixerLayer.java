package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer2;
import net.minecraft.world.level.newbiome.layer.traits.DimensionOffset0Transformer;

public enum OceanMixerLayer implements AreaTransformer2, DimensionOffset0Transformer {
   INSTANCE;

   @Override
   public int applyPixel(Context var1, Area var2, Area var3, int var4, int var5) {
      int â˜ƒ = â˜ƒ.get(this.getParentX(â˜ƒ), this.getParentY(â˜ƒ));
      int â˜ƒx = â˜ƒ.get(this.getParentX(â˜ƒ), this.getParentY(â˜ƒ));
      if (!Layers.isOcean(â˜ƒ)) {
         return â˜ƒ;
      } else {
         int â˜ƒ = 8;
         int â˜ƒx = 4;

         for(int â˜ƒxx = -8; â˜ƒxx <= 8; â˜ƒxx += 4) {
            for(int â˜ƒxxx = -8; â˜ƒxxx <= 8; â˜ƒxxx += 4) {
               int â˜ƒxxxx = â˜ƒ.get(this.getParentX(â˜ƒ + â˜ƒxx), this.getParentY(â˜ƒ + â˜ƒxxx));
               if (!Layers.isOcean(â˜ƒxxxx)) {
                  if (â˜ƒx == 44) {
                     return 45;
                  }

                  if (â˜ƒx == 10) {
                     return 46;
                  }
               }
            }
         }

         if (â˜ƒ == 24) {
            if (â˜ƒx == 45) {
               return 48;
            }

            if (â˜ƒx == 0) {
               return 24;
            }

            if (â˜ƒx == 46) {
               return 49;
            }

            if (â˜ƒx == 10) {
               return 50;
            }
         }

         return â˜ƒx;
      }
   }
}
