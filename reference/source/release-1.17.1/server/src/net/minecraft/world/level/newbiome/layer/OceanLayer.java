package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer0;

public enum OceanLayer implements AreaTransformer0 {
   INSTANCE;

   @Override
   public int applyPixel(Context var1, int var2, int var3) {
      ImprovedNoise â˜ƒ = â˜ƒ.getBiomeNoise();
      double â˜ƒx = â˜ƒ.noise((double)â˜ƒ / 8.0, (double)â˜ƒ / 8.0, 0.0);
      if (â˜ƒx > 0.4) {
         return 44;
      } else if (â˜ƒx > 0.2) {
         return 45;
      } else if (â˜ƒx < -0.4) {
         return 10;
      } else {
         return â˜ƒx < -0.2 ? 46 : 0;
      }
   }
}
