package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C1Transformer;

public enum AddSnowLayer implements C1Transformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2) {
      if (Layers.isShallowOcean(â˜ƒ)) {
         return â˜ƒ;
      } else {
         int â˜ƒ = â˜ƒ.nextRandom(6);
         if (â˜ƒ == 0) {
            return 4;
         } else {
            return â˜ƒ == 1 ? 3 : 1;
         }
      }
   }
}
