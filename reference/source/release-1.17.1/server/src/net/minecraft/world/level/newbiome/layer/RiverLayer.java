package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public enum RiverLayer implements CastleTransformer {
   INSTANCE;

   @Override
   public int apply(Context var1, int var2, int var3, int var4, int var5, int var6) {
      int â˜ƒ = riverFilter(â˜ƒ);
      return â˜ƒ == riverFilter(â˜ƒ) && â˜ƒ == riverFilter(â˜ƒ) && â˜ƒ == riverFilter(â˜ƒ) && â˜ƒ == riverFilter(â˜ƒ) ? -1 : 7;
   }

   private static int riverFilter(int var0) {
      return â˜ƒ >= 2 ? 2 + (â˜ƒ & 1) : â˜ƒ;
   }
}
