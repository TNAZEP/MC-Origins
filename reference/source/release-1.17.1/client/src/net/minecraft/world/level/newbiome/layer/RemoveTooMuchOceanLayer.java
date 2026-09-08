package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public enum RemoveTooMuchOceanLayer implements CastleTransformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2, int var3, int var4, int var5, int var6) {
      return Layers.isShallowOcean(â˜ƒ)
            && Layers.isShallowOcean(â˜ƒ)
            && Layers.isShallowOcean(â˜ƒ)
            && Layers.isShallowOcean(â˜ƒ)
            && Layers.isShallowOcean(â˜ƒ)
            && â˜ƒ.nextRandom(2) == 0
         ? 1
         : â˜ƒ;
   }
}
