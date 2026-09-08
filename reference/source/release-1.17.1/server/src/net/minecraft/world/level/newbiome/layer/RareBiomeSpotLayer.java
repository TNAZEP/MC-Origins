package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C1Transformer;

public enum RareBiomeSpotLayer implements C1Transformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2) {
      return â˜ƒ.nextRandom(57) == 0 && â˜ƒ == 1 ? 129 : â˜ƒ;
   }
}
