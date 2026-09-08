package net.minecraft.world.level.newbiome.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.function.LongFunction;
import net.minecraft.Util;
import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.area.LazyArea;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.LazyAreaContext;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;

public class Layers implements LayerBiomes {
   protected static final int WARM_ID = 1;
   protected static final int MEDIUM_ID = 2;
   protected static final int COLD_ID = 3;
   protected static final int ICE_ID = 4;
   protected static final int SPECIAL_MASK = 3840;
   protected static final int SPECIAL_SHIFT = 8;
   private static final Int2IntMap CATEGORIES = Util.make(new Int2IntOpenHashMap(), var0 -> {
      register(var0, Layers.Category.BEACH, 16);
      register(var0, Layers.Category.BEACH, 26);
      register(var0, Layers.Category.DESERT, 2);
      register(var0, Layers.Category.DESERT, 17);
      register(var0, Layers.Category.DESERT, 130);
      register(var0, Layers.Category.EXTREME_HILLS, 131);
      register(var0, Layers.Category.EXTREME_HILLS, 162);
      register(var0, Layers.Category.EXTREME_HILLS, 20);
      register(var0, Layers.Category.EXTREME_HILLS, 3);
      register(var0, Layers.Category.EXTREME_HILLS, 34);
      register(var0, Layers.Category.FOREST, 27);
      register(var0, Layers.Category.FOREST, 28);
      register(var0, Layers.Category.FOREST, 29);
      register(var0, Layers.Category.FOREST, 157);
      register(var0, Layers.Category.FOREST, 132);
      register(var0, Layers.Category.FOREST, 4);
      register(var0, Layers.Category.FOREST, 155);
      register(var0, Layers.Category.FOREST, 156);
      register(var0, Layers.Category.FOREST, 18);
      register(var0, Layers.Category.ICY, 140);
      register(var0, Layers.Category.ICY, 13);
      register(var0, Layers.Category.ICY, 12);
      register(var0, Layers.Category.JUNGLE, 168);
      register(var0, Layers.Category.JUNGLE, 169);
      register(var0, Layers.Category.JUNGLE, 21);
      register(var0, Layers.Category.JUNGLE, 23);
      register(var0, Layers.Category.JUNGLE, 22);
      register(var0, Layers.Category.JUNGLE, 149);
      register(var0, Layers.Category.JUNGLE, 151);
      register(var0, Layers.Category.MESA, 37);
      register(var0, Layers.Category.MESA, 165);
      register(var0, Layers.Category.MESA, 167);
      register(var0, Layers.Category.MESA, 166);
      register(var0, Layers.Category.BADLANDS_PLATEAU, 39);
      register(var0, Layers.Category.BADLANDS_PLATEAU, 38);
      register(var0, Layers.Category.MUSHROOM, 14);
      register(var0, Layers.Category.MUSHROOM, 15);
      register(var0, Layers.Category.NONE, 25);
      register(var0, Layers.Category.OCEAN, 46);
      register(var0, Layers.Category.OCEAN, 49);
      register(var0, Layers.Category.OCEAN, 50);
      register(var0, Layers.Category.OCEAN, 48);
      register(var0, Layers.Category.OCEAN, 24);
      register(var0, Layers.Category.OCEAN, 47);
      register(var0, Layers.Category.OCEAN, 10);
      register(var0, Layers.Category.OCEAN, 45);
      register(var0, Layers.Category.OCEAN, 0);
      register(var0, Layers.Category.OCEAN, 44);
      register(var0, Layers.Category.PLAINS, 1);
      register(var0, Layers.Category.PLAINS, 129);
      register(var0, Layers.Category.RIVER, 11);
      register(var0, Layers.Category.RIVER, 7);
      register(var0, Layers.Category.SAVANNA, 35);
      register(var0, Layers.Category.SAVANNA, 36);
      register(var0, Layers.Category.SAVANNA, 163);
      register(var0, Layers.Category.SAVANNA, 164);
      register(var0, Layers.Category.SWAMP, 6);
      register(var0, Layers.Category.SWAMP, 134);
      register(var0, Layers.Category.TAIGA, 160);
      register(var0, Layers.Category.TAIGA, 161);
      register(var0, Layers.Category.TAIGA, 32);
      register(var0, Layers.Category.TAIGA, 33);
      register(var0, Layers.Category.TAIGA, 30);
      register(var0, Layers.Category.TAIGA, 31);
      register(var0, Layers.Category.TAIGA, 158);
      register(var0, Layers.Category.TAIGA, 5);
      register(var0, Layers.Category.TAIGA, 19);
      register(var0, Layers.Category.TAIGA, 133);
   });

   private static <T extends Area, C extends BigContext<T>> AreaFactory<T> zoom(
      long var0, AreaTransformer1 var2, AreaFactory<T> var3, int var4, LongFunction<C> var5
   ) {
      AreaFactory<T> â˜ƒ = â˜ƒ;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         â˜ƒ = â˜ƒ.run((BigContext<T>)â˜ƒ.apply(â˜ƒ + (long)â˜ƒx), â˜ƒ);
      }

      return â˜ƒ;
   }

   private static <T extends Area, C extends BigContext<T>> AreaFactory<T> getDefaultLayer(boolean var0, int var1, int var2, LongFunction<C> var3) {
      AreaFactory<T> â˜ƒ = IslandLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(1L));
      â˜ƒ = ZoomLayer.FUZZY.run((BigContext<T>)â˜ƒ.apply(2000L), â˜ƒ);
      â˜ƒ = AddIslandLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(1L), â˜ƒ);
      â˜ƒ = ZoomLayer.NORMAL.run((BigContext<T>)â˜ƒ.apply(2001L), â˜ƒ);
      â˜ƒ = AddIslandLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(2L), â˜ƒ);
      â˜ƒ = AddIslandLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(50L), â˜ƒ);
      â˜ƒ = AddIslandLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(70L), â˜ƒ);
      â˜ƒ = RemoveTooMuchOceanLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(2L), â˜ƒ);
      AreaFactory<T> â˜ƒx = OceanLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(2L));
      â˜ƒx = zoom(2001L, ZoomLayer.NORMAL, â˜ƒx, 6, â˜ƒ);
      â˜ƒ = AddSnowLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(2L), â˜ƒ);
      â˜ƒ = AddIslandLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(3L), â˜ƒ);
      â˜ƒ = AddEdgeLayer.CoolWarm.INSTANCE.run((BigContext<T>)â˜ƒ.apply(2L), â˜ƒ);
      â˜ƒ = AddEdgeLayer.HeatIce.INSTANCE.run((BigContext<T>)â˜ƒ.apply(2L), â˜ƒ);
      â˜ƒ = AddEdgeLayer.IntroduceSpecial.INSTANCE.run((BigContext<T>)â˜ƒ.apply(3L), â˜ƒ);
      â˜ƒ = ZoomLayer.NORMAL.run((BigContext<T>)â˜ƒ.apply(2002L), â˜ƒ);
      â˜ƒ = ZoomLayer.NORMAL.run((BigContext<T>)â˜ƒ.apply(2003L), â˜ƒ);
      â˜ƒ = AddIslandLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(4L), â˜ƒ);
      â˜ƒ = AddMushroomIslandLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(5L), â˜ƒ);
      â˜ƒ = AddDeepOceanLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(4L), â˜ƒ);
      â˜ƒ = zoom(1000L, ZoomLayer.NORMAL, â˜ƒ, 0, â˜ƒ);
      AreaFactory<T> var6 = zoom(1000L, ZoomLayer.NORMAL, â˜ƒ, 0, â˜ƒ);
      var6 = RiverInitLayer.INSTANCE.run((BigContext)â˜ƒ.apply(100L), var6);
      AreaFactory<T> var7 = new BiomeInitLayer(â˜ƒ).run((BigContext<T>)â˜ƒ.apply(200L), â˜ƒ);
      var7 = RareBiomeLargeLayer.INSTANCE.run((BigContext)â˜ƒ.apply(1001L), var7);
      var7 = zoom(1000L, ZoomLayer.NORMAL, var7, 2, â˜ƒ);
      var7 = BiomeEdgeLayer.INSTANCE.run((BigContext)â˜ƒ.apply(1000L), var7);
      AreaFactory<T> var8 = zoom(1000L, ZoomLayer.NORMAL, var6, 2, â˜ƒ);
      var7 = RegionHillsLayer.INSTANCE.run((BigContext)â˜ƒ.apply(1000L), var7, var8);
      var6 = zoom(1000L, ZoomLayer.NORMAL, var6, 2, â˜ƒ);
      var6 = zoom(1000L, ZoomLayer.NORMAL, var6, â˜ƒ, â˜ƒ);
      var6 = RiverLayer.INSTANCE.run((BigContext)â˜ƒ.apply(1L), var6);
      var6 = SmoothLayer.INSTANCE.run((BigContext)â˜ƒ.apply(1000L), var6);
      var7 = RareBiomeSpotLayer.INSTANCE.run((BigContext)â˜ƒ.apply(1001L), var7);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         var7 = ZoomLayer.NORMAL.run((BigContext)â˜ƒ.apply((long)(1000 + â˜ƒxx)), var7);
         if (â˜ƒxx == 0) {
            var7 = AddIslandLayer.INSTANCE.run((BigContext)â˜ƒ.apply(3L), var7);
         }

         if (â˜ƒxx == 1 || â˜ƒ == 1) {
            var7 = ShoreLayer.INSTANCE.run((BigContext)â˜ƒ.apply(1000L), var7);
         }
      }

      var7 = SmoothLayer.INSTANCE.run((BigContext)â˜ƒ.apply(1000L), var7);
      var7 = RiverMixerLayer.INSTANCE.run((BigContext)â˜ƒ.apply(100L), var7, var6);
      return OceanMixerLayer.INSTANCE.run((BigContext<T>)â˜ƒ.apply(100L), var7, â˜ƒx);
   }

   public static Layer getDefaultLayer(long var0, boolean var2, int var3, int var4) {
      int â˜ƒ = 25;
      AreaFactory<LazyArea> â˜ƒx = getDefaultLayer(â˜ƒ, â˜ƒ, â˜ƒ, var2x -> new LazyAreaContext(25, â˜ƒ, var2x));
      return new Layer(â˜ƒx);
   }

   public static boolean isSame(int var0, int var1) {
      if (â˜ƒ == â˜ƒ) {
         return true;
      } else {
         return CATEGORIES.get(â˜ƒ) == CATEGORIES.get(â˜ƒ);
      }
   }

   private static void register(Int2IntOpenHashMap var0, Layers.Category var1, int var2) {
      â˜ƒ.put(â˜ƒ, â˜ƒ.ordinal());
   }

   protected static boolean isOcean(int var0) {
      return â˜ƒ == 44 || â˜ƒ == 45 || â˜ƒ == 0 || â˜ƒ == 46 || â˜ƒ == 10 || â˜ƒ == 47 || â˜ƒ == 48 || â˜ƒ == 24 || â˜ƒ == 49 || â˜ƒ == 50;
   }

   protected static boolean isShallowOcean(int var0) {
      return â˜ƒ == 44 || â˜ƒ == 45 || â˜ƒ == 0 || â˜ƒ == 46 || â˜ƒ == 10;
   }

   static enum Category {
      NONE,
      TAIGA,
      EXTREME_HILLS,
      JUNGLE,
      MESA,
      BADLANDS_PLATEAU,
      PLAINS,
      SAVANNA,
      ICY,
      BEACH,
      FOREST,
      OCEAN,
      DESERT,
      RIVER,
      SWAMP,
      MUSHROOM;
   }
}
