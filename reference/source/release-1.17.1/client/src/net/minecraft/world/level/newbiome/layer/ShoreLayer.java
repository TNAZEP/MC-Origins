package net.minecraft.world.level.newbiome.layer;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public enum ShoreLayer implements CastleTransformer {
   INSTANCE;

   private static final IntSet SNOWY = new IntOpenHashSet(new int[]{26, 11, 12, 13, 140, 30, 31, 158, 10});
   private static final IntSet JUNGLES = new IntOpenHashSet(new int[]{168, 169, 21, 22, 23, 149, 151});

   @Override
   public int apply(Context var1, int var2, int var3, int var4, int var5, int var6) {
      if (â˜ƒ == 14) {
         if (Layers.isShallowOcean(â˜ƒ) || Layers.isShallowOcean(â˜ƒ) || Layers.isShallowOcean(â˜ƒ) || Layers.isShallowOcean(â˜ƒ)) {
            return 15;
         }
      } else if (JUNGLES.contains(â˜ƒ)) {
         if (!isJungleCompatible(â˜ƒ) || !isJungleCompatible(â˜ƒ) || !isJungleCompatible(â˜ƒ) || !isJungleCompatible(â˜ƒ)) {
            return 23;
         }

         if (Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ)) {
            return 16;
         }
      } else if (â˜ƒ != 3 && â˜ƒ != 34 && â˜ƒ != 20) {
         if (SNOWY.contains(â˜ƒ)) {
            if (!Layers.isOcean(â˜ƒ) && (Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ))) {
               return 26;
            }
         } else if (â˜ƒ != 37 && â˜ƒ != 38) {
            if (!Layers.isOcean(â˜ƒ) && â˜ƒ != 7 && â˜ƒ != 6 && (Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ))) {
               return 16;
            }
         } else if (!Layers.isOcean(â˜ƒ)
            && !Layers.isOcean(â˜ƒ)
            && !Layers.isOcean(â˜ƒ)
            && !Layers.isOcean(â˜ƒ)
            && (!this.isMesa(â˜ƒ) || !this.isMesa(â˜ƒ) || !this.isMesa(â˜ƒ) || !this.isMesa(â˜ƒ))) {
            return 2;
         }
      } else if (!Layers.isOcean(â˜ƒ) && (Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ) || Layers.isOcean(â˜ƒ))) {
         return 25;
      }

      return â˜ƒ;
   }

   private static boolean isJungleCompatible(int var0) {
      return JUNGLES.contains(â˜ƒ) || â˜ƒ == 4 || â˜ƒ == 5 || Layers.isOcean(â˜ƒ);
   }

   private boolean isMesa(int var1) {
      return â˜ƒ == 37 || â˜ƒ == 38 || â˜ƒ == 39 || â˜ƒ == 165 || â˜ƒ == 166 || â˜ƒ == 167;
   }
}
