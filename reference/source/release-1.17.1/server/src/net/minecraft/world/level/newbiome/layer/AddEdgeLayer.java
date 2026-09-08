package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C0Transformer;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public class AddEdgeLayer {
   public static enum CoolWarm implements CastleTransformer {
      INSTANCE;

      @Override
      public int apply(Context var1, int var2, int var3, int var4, int var5, int var6) {
         return â˜ƒ != 1 || â˜ƒ != 3 && â˜ƒ != 3 && â˜ƒ != 3 && â˜ƒ != 3 && â˜ƒ != 4 && â˜ƒ != 4 && â˜ƒ != 4 && â˜ƒ != 4 ? â˜ƒ : 2;
      }
   }

   public static enum HeatIce implements CastleTransformer {
      INSTANCE;

      @Override
      public int apply(Context var1, int var2, int var3, int var4, int var5, int var6) {
         return â˜ƒ != 4 || â˜ƒ != 1 && â˜ƒ != 1 && â˜ƒ != 1 && â˜ƒ != 1 && â˜ƒ != 2 && â˜ƒ != 2 && â˜ƒ != 2 && â˜ƒ != 2 ? â˜ƒ : 3;
      }
   }

   public static enum IntroduceSpecial implements C0Transformer {
      INSTANCE;

      @Override
      public int apply(Context var1, int var2) {
         if (!Layers.isShallowOcean(â˜ƒ) && â˜ƒ.nextRandom(13) == 0) {
            â˜ƒ |= 1 + â˜ƒ.nextRandom(15) << 8 & 3840;
         }

         return â˜ƒ;
      }
   }
}
