package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.BishopTransformer;

public enum AddIslandLayer implements BishopTransformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2, int var3, int var4, int var5, int var6) {
      if (!Layers.isShallowOcean(â˜ƒ) || Layers.isShallowOcean(â˜ƒ) && Layers.isShallowOcean(â˜ƒ) && Layers.isShallowOcean(â˜ƒ) && Layers.isShallowOcean(â˜ƒ)) {
         if (!Layers.isShallowOcean(â˜ƒ)
            && (Layers.isShallowOcean(â˜ƒ) || Layers.isShallowOcean(â˜ƒ) || Layers.isShallowOcean(â˜ƒ) || Layers.isShallowOcean(â˜ƒ))
            && â˜ƒ.nextRandom(5) == 0) {
            if (Layers.isShallowOcean(â˜ƒ)) {
               return â˜ƒ == 4 ? 4 : â˜ƒ;
            }

            if (Layers.isShallowOcean(â˜ƒ)) {
               return â˜ƒ == 4 ? 4 : â˜ƒ;
            }

            if (Layers.isShallowOcean(â˜ƒ)) {
               return â˜ƒ == 4 ? 4 : â˜ƒ;
            }

            if (Layers.isShallowOcean(â˜ƒ)) {
               return â˜ƒ == 4 ? 4 : â˜ƒ;
            }
         }

         return â˜ƒ;
      } else {
         int â˜ƒ = 1;
         int â˜ƒx = 1;
         if (!Layers.isShallowOcean(â˜ƒ) && â˜ƒ.nextRandom(â˜ƒ++) == 0) {
            â˜ƒx = â˜ƒ;
         }

         if (!Layers.isShallowOcean(â˜ƒ) && â˜ƒ.nextRandom(â˜ƒ++) == 0) {
            â˜ƒx = â˜ƒ;
         }

         if (!Layers.isShallowOcean(â˜ƒ) && â˜ƒ.nextRandom(â˜ƒ++) == 0) {
            â˜ƒx = â˜ƒ;
         }

         if (!Layers.isShallowOcean(â˜ƒ) && â˜ƒ.nextRandom(â˜ƒ++) == 0) {
            â˜ƒx = â˜ƒ;
         }

         if (â˜ƒ.nextRandom(3) == 0) {
            return â˜ƒx;
         } else {
            return â˜ƒx == 4 ? 4 : â˜ƒ;
         }
      }
   }
}
