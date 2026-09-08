package net.minecraft.world.level.newbiome.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer2;
import net.minecraft.world.level.newbiome.layer.traits.DimensionOffset1Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum RegionHillsLayer implements AreaTransformer2, DimensionOffset1Transformer {
   INSTANCE;

   private static final Logger LOGGER = LogManager.getLogger();
   private static final Int2IntMap MUTATIONS = Util.make(new Int2IntOpenHashMap(), var0 -> {
      var0.put(1, 129);
      var0.put(2, 130);
      var0.put(3, 131);
      var0.put(4, 132);
      var0.put(5, 133);
      var0.put(6, 134);
      var0.put(12, 140);
      var0.put(21, 149);
      var0.put(23, 151);
      var0.put(27, 155);
      var0.put(28, 156);
      var0.put(29, 157);
      var0.put(30, 158);
      var0.put(32, 160);
      var0.put(33, 161);
      var0.put(34, 162);
      var0.put(35, 163);
      var0.put(36, 164);
      var0.put(37, 165);
      var0.put(38, 166);
      var0.put(39, 167);
   });

   @Override
   public int applyPixel(Context var1, Area var2, Area var3, int var4, int var5) {
      int â˜ƒ = â˜ƒ.get(this.getParentX(â˜ƒ + 1), this.getParentY(â˜ƒ + 1));
      int â˜ƒx = â˜ƒ.get(this.getParentX(â˜ƒ + 1), this.getParentY(â˜ƒ + 1));
      if (â˜ƒ > 255) {
         LOGGER.debug("old! {}", â˜ƒ);
      }

      int â˜ƒ = (â˜ƒx - 2) % 29;
      if (!Layers.isShallowOcean(â˜ƒ) && â˜ƒx >= 2 && â˜ƒ == 1) {
         return MUTATIONS.getOrDefault(â˜ƒ, â˜ƒ);
      } else {
         if (â˜ƒ.nextRandom(3) == 0 || â˜ƒ == 0) {
            int â˜ƒ = â˜ƒ;
            if (â˜ƒ == 2) {
               â˜ƒ = 17;
            } else if (â˜ƒ == 4) {
               â˜ƒ = 18;
            } else if (â˜ƒ == 27) {
               â˜ƒ = 28;
            } else if (â˜ƒ == 29) {
               â˜ƒ = 1;
            } else if (â˜ƒ == 5) {
               â˜ƒ = 19;
            } else if (â˜ƒ == 32) {
               â˜ƒ = 33;
            } else if (â˜ƒ == 30) {
               â˜ƒ = 31;
            } else if (â˜ƒ == 1) {
               â˜ƒ = â˜ƒ.nextRandom(3) == 0 ? 18 : 4;
            } else if (â˜ƒ == 12) {
               â˜ƒ = 13;
            } else if (â˜ƒ == 21) {
               â˜ƒ = 22;
            } else if (â˜ƒ == 168) {
               â˜ƒ = 169;
            } else if (â˜ƒ == 0) {
               â˜ƒ = 24;
            } else if (â˜ƒ == 45) {
               â˜ƒ = 48;
            } else if (â˜ƒ == 46) {
               â˜ƒ = 49;
            } else if (â˜ƒ == 10) {
               â˜ƒ = 50;
            } else if (â˜ƒ == 3) {
               â˜ƒ = 34;
            } else if (â˜ƒ == 35) {
               â˜ƒ = 36;
            } else if (Layers.isSame(â˜ƒ, 38)) {
               â˜ƒ = 37;
            } else if ((â˜ƒ == 24 || â˜ƒ == 48 || â˜ƒ == 49 || â˜ƒ == 50) && â˜ƒ.nextRandom(3) == 0) {
               â˜ƒ = â˜ƒ.nextRandom(2) == 0 ? 1 : 4;
            }

            if (â˜ƒ == 0 && â˜ƒ != â˜ƒ) {
               â˜ƒ = MUTATIONS.getOrDefault(â˜ƒ, â˜ƒ);
            }

            if (â˜ƒ != â˜ƒ) {
               int â˜ƒ = 0;
               if (Layers.isSame(â˜ƒ.get(this.getParentX(â˜ƒ + 1), this.getParentY(â˜ƒ + 0)), â˜ƒ)) {
                  ++â˜ƒ;
               }

               if (Layers.isSame(â˜ƒ.get(this.getParentX(â˜ƒ + 2), this.getParentY(â˜ƒ + 1)), â˜ƒ)) {
                  ++â˜ƒ;
               }

               if (Layers.isSame(â˜ƒ.get(this.getParentX(â˜ƒ + 0), this.getParentY(â˜ƒ + 1)), â˜ƒ)) {
                  ++â˜ƒ;
               }

               if (Layers.isSame(â˜ƒ.get(this.getParentX(â˜ƒ + 1), this.getParentY(â˜ƒ + 2)), â˜ƒ)) {
                  ++â˜ƒ;
               }

               if (â˜ƒ >= 3) {
                  return â˜ƒ;
               }
            }
         }

         return â˜ƒ;
      }
   }
}
