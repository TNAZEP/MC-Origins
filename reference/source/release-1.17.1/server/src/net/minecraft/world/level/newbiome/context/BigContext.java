package net.minecraft.world.level.newbiome.context;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.layer.traits.PixelTransformer;

public interface BigContext<R extends Area> extends Context {
   void initRandom(long var1, long var3);

   R createResult(PixelTransformer var1);

   default R createResult(PixelTransformer var1, R var2) {
      return this.createResult(â˜ƒ);
   }

   default R createResult(PixelTransformer var1, R var2, R var3) {
      return this.createResult(â˜ƒ);
   }

   default int random(int var1, int var2) {
      return this.nextRandom(2) == 0 ? â˜ƒ : â˜ƒ;
   }

   default int random(int var1, int var2, int var3, int var4) {
      int â˜ƒ = this.nextRandom(4);
      if (â˜ƒ == 0) {
         return â˜ƒ;
      } else if (â˜ƒ == 1) {
         return â˜ƒ;
      } else {
         return â˜ƒ == 2 ? â˜ƒ : â˜ƒ;
      }
   }
}
