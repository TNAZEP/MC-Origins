package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public enum BiomeEdgeLayer implements CastleTransformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2, int var3, int var4, int var5, int var6) {
      int[] â˜ƒ = new int[1];
      if (!this.checkEdge(â˜ƒ, â˜ƒ)
         && !this.checkEdgeStrict(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 38, 37)
         && !this.checkEdgeStrict(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 39, 37)
         && !this.checkEdgeStrict(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 32, 5)) {
         if (â˜ƒ != 2 || â˜ƒ != 12 && â˜ƒ != 12 && â˜ƒ != 12 && â˜ƒ != 12) {
            if (â˜ƒ == 6) {
               if (â˜ƒ == 2
                  || â˜ƒ == 2
                  || â˜ƒ == 2
                  || â˜ƒ == 2
                  || â˜ƒ == 30
                  || â˜ƒ == 30
                  || â˜ƒ == 30
                  || â˜ƒ == 30
                  || â˜ƒ == 12
                  || â˜ƒ == 12
                  || â˜ƒ == 12
                  || â˜ƒ == 12) {
                  return 1;
               }

               if (â˜ƒ == 21 || â˜ƒ == 21 || â˜ƒ == 21 || â˜ƒ == 21 || â˜ƒ == 168 || â˜ƒ == 168 || â˜ƒ == 168 || â˜ƒ == 168) {
                  return 23;
               }
            }

            return â˜ƒ;
         } else {
            return 34;
         }
      } else {
         return â˜ƒ[0];
      }
   }

   private boolean checkEdge(int[] var1, int var2) {
      if (!Layers.isSame(â˜ƒ, 3)) {
         return false;
      } else {
         â˜ƒ[0] = â˜ƒ;
         return true;
      }
   }

   private boolean checkEdgeStrict(int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (â˜ƒ != â˜ƒ) {
         return false;
      } else {
         if (Layers.isSame(â˜ƒ, â˜ƒ) && Layers.isSame(â˜ƒ, â˜ƒ) && Layers.isSame(â˜ƒ, â˜ƒ) && Layers.isSame(â˜ƒ, â˜ƒ)) {
            â˜ƒ[0] = â˜ƒ;
         } else {
            â˜ƒ[0] = â˜ƒ;
         }

         return true;
      }
   }
}
