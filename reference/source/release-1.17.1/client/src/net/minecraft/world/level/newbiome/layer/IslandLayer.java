package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer0;

public enum IslandLayer implements AreaTransformer0 {
   INSTANCE;

   @Override
   public int applyPixel(Context var1, int var2, int var3) {
      if (â˜ƒ == 0 && â˜ƒ == 0) {
         return 1;
      } else {
         return â˜ƒ.nextRandom(10) == 0 ? 1 : 0;
      }
   }
}
