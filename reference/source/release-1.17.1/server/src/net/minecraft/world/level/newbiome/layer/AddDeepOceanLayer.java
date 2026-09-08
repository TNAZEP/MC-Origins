package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public enum AddDeepOceanLayer implements CastleTransformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2, int var3, int var4, int var5, int var6) {
      if (Layers.isShallowOcean(â˜ƒ)) {
         int â˜ƒ = 0;
         if (Layers.isShallowOcean(â˜ƒ)) {
            ++â˜ƒ;
         }

         if (Layers.isShallowOcean(â˜ƒ)) {
            ++â˜ƒ;
         }

         if (Layers.isShallowOcean(â˜ƒ)) {
            ++â˜ƒ;
         }

         if (Layers.isShallowOcean(â˜ƒ)) {
            ++â˜ƒ;
         }

         if (â˜ƒ > 3) {
            if (â˜ƒ == 44) {
               return 47;
            }

            if (â˜ƒ == 45) {
               return 48;
            }

            if (â˜ƒ == 0) {
               return 24;
            }

            if (â˜ƒ == 46) {
               return 49;
            }

            if (â˜ƒ == 10) {
               return 50;
            }

            return 24;
         }
      }

      return â˜ƒ;
   }
}
