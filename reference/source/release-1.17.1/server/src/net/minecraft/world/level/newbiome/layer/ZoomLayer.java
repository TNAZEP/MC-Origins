package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;

public enum ZoomLayer implements AreaTransformer1 {
   NORMAL,
   FUZZY {
      @Override
      protected int modeOrRandom(BigContext<?> var1, int var2, int var3, int var4, int var5) {
         return â˜ƒ.random(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   };

   private static final int ZOOM_BITS = 1;
   private static final int ZOOM_MASK = 1;

   @Override
   public int getParentX(int var1) {
      return â˜ƒ >> 1;
   }

   @Override
   public int getParentY(int var1) {
      return â˜ƒ >> 1;
   }

   @Override
   public int applyPixel(BigContext<?> var1, Area var2, int var3, int var4) {
      int â˜ƒ = â˜ƒ.get(this.getParentX(â˜ƒ), this.getParentY(â˜ƒ));
      â˜ƒ.initRandom((long)(â˜ƒ >> 1 << 1), (long)(â˜ƒ >> 1 << 1));
      int â˜ƒx = â˜ƒ & 1;
      int â˜ƒxx = â˜ƒ & 1;
      if (â˜ƒx == 0 && â˜ƒxx == 0) {
         return â˜ƒ;
      } else {
         int â˜ƒ = â˜ƒ.get(this.getParentX(â˜ƒ), this.getParentY(â˜ƒ + 1));
         int â˜ƒx = â˜ƒ.random(â˜ƒ, â˜ƒ);
         if (â˜ƒx == 0 && â˜ƒxx == 1) {
            return â˜ƒx;
         } else {
            int â˜ƒ = â˜ƒ.get(this.getParentX(â˜ƒ + 1), this.getParentY(â˜ƒ));
            int â˜ƒx = â˜ƒ.random(â˜ƒ, â˜ƒ);
            if (â˜ƒx == 1 && â˜ƒxx == 0) {
               return â˜ƒx;
            } else {
               int â˜ƒ = â˜ƒ.get(this.getParentX(â˜ƒ + 1), this.getParentY(â˜ƒ + 1));
               return this.modeOrRandom(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   protected int modeOrRandom(BigContext<?> var1, int var2, int var3, int var4, int var5) {
      if (â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ) {
         return â˜ƒ;
      } else if (â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ) {
         return â˜ƒ;
      } else if (â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ) {
         return â˜ƒ;
      } else if (â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ) {
         return â˜ƒ;
      } else if (â˜ƒ == â˜ƒ && â˜ƒ != â˜ƒ) {
         return â˜ƒ;
      } else if (â˜ƒ == â˜ƒ && â˜ƒ != â˜ƒ) {
         return â˜ƒ;
      } else if (â˜ƒ == â˜ƒ && â˜ƒ != â˜ƒ) {
         return â˜ƒ;
      } else if (â˜ƒ == â˜ƒ && â˜ƒ != â˜ƒ) {
         return â˜ƒ;
      } else if (â˜ƒ == â˜ƒ && â˜ƒ != â˜ƒ) {
         return â˜ƒ;
      } else {
         return â˜ƒ == â˜ƒ && â˜ƒ != â˜ƒ ? â˜ƒ : â˜ƒ.random(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
