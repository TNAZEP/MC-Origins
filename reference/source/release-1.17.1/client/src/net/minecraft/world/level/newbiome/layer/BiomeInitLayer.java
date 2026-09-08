package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C0Transformer;

public class BiomeInitLayer implements C0Transformer {
   private static final int[] LEGACY_WARM_BIOMES = new int[]{2, 4, 3, 6, 1, 5};
   private static final int[] WARM_BIOMES = new int[]{2, 2, 2, 35, 35, 1};
   private static final int[] MEDIUM_BIOMES = new int[]{4, 29, 3, 1, 27, 6};
   private static final int[] COLD_BIOMES = new int[]{4, 3, 5, 1};
   private static final int[] ICE_BIOMES = new int[]{12, 12, 12, 30};
   private int[] warmBiomes = WARM_BIOMES;

   public BiomeInitLayer(boolean var1) {
      if (â˜ƒ) {
         this.warmBiomes = LEGACY_WARM_BIOMES;
      }
   }

   @Override
   public int apply(Context var1, int var2) {
      int â˜ƒ = (â˜ƒ & 3840) >> 8;
      â˜ƒ &= -3841;
      if (!Layers.isOcean(â˜ƒ) && â˜ƒ != 14) {
         switch(â˜ƒ) {
            case 1:
               if (â˜ƒ > 0) {
                  return â˜ƒ.nextRandom(3) == 0 ? 39 : 38;
               }

               return this.warmBiomes[â˜ƒ.nextRandom(this.warmBiomes.length)];
            case 2:
               if (â˜ƒ > 0) {
                  return 21;
               }

               return MEDIUM_BIOMES[â˜ƒ.nextRandom(MEDIUM_BIOMES.length)];
            case 3:
               if (â˜ƒ > 0) {
                  return 32;
               }

               return COLD_BIOMES[â˜ƒ.nextRandom(COLD_BIOMES.length)];
            case 4:
               return ICE_BIOMES[â˜ƒ.nextRandom(ICE_BIOMES.length)];
            default:
               return 14;
         }
      } else {
         return â˜ƒ;
      }
   }
}
