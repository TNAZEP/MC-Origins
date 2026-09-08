package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C0Transformer;

public enum RiverInitLayer implements C0Transformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2) {
      return Layers.isShallowOcean(â˜ƒ) ? â˜ƒ : â˜ƒ.nextRandom(299999) + 2;
   }
}
