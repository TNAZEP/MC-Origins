package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C1Transformer;

public enum RareBiomeLargeLayer implements C1Transformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2) {
      return â˜ƒ.nextRandom(10) == 0 && â˜ƒ == 21 ? 168 : â˜ƒ;
   }
}
