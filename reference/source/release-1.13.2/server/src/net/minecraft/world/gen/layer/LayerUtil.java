package net.minecraft.world.gen.layer;

import com.google.common.collect.ImmutableList;
import java.util.function.LongFunction;
import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IContextExtended;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public class LayerUtil {
   protected static final int field_203632_a = IRegistry.field_212624_m.func_148757_b(Biomes.field_203614_T);
   protected static final int field_203633_b = IRegistry.field_212624_m.func_148757_b(Biomes.field_203615_U);
   protected static final int field_202832_c = IRegistry.field_212624_m.func_148757_b(Biomes.field_76771_b);
   protected static final int field_203634_d = IRegistry.field_212624_m.func_148757_b(Biomes.field_203616_V);
   protected static final int field_202831_b = IRegistry.field_212624_m.func_148757_b(Biomes.field_76776_l);
   protected static final int field_203635_f = IRegistry.field_212624_m.func_148757_b(Biomes.field_203617_W);
   protected static final int field_203636_g = IRegistry.field_212624_m.func_148757_b(Biomes.field_203618_X);
   protected static final int field_202830_a = IRegistry.field_212624_m.func_148757_b(Biomes.field_150575_M);
   protected static final int field_203637_i = IRegistry.field_212624_m.func_148757_b(Biomes.field_203619_Y);
   protected static final int field_203638_j = IRegistry.field_212624_m.func_148757_b(Biomes.field_203620_Z);

   private static <T extends IArea, C extends IContextExtended<T>> IAreaFactory<T> func_202829_a(
      long var0, IAreaTransformer1 var2, IAreaFactory<T> var3, int var4, LongFunction<C> var5
   ) {
      IAreaFactory<T> ☃ = ☃;

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         ☃ = ☃.func_202713_a((IContextExtended<T>)☃.apply(☃ + (long)☃x), ☃);
      }

      return ☃;
   }

   public static <T extends IArea, C extends IContextExtended<T>> ImmutableList<IAreaFactory<T>> func_202828_a(
      WorldType var0, OverworldGenSettings var1, LongFunction<C> var2
   ) {
      IAreaFactory<T> ☃ = GenLayerIsland.INSTANCE.func_202823_a((IContextExtended<T>)☃.apply(1L));
      ☃ = GenLayerZoom.FUZZY.func_202713_a((IContextExtended<T>)☃.apply(2000L), ☃);
      ☃ = GenLayerAddIsland.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(1L), ☃);
      ☃ = GenLayerZoom.NORMAL.func_202713_a((IContextExtended<T>)☃.apply(2001L), ☃);
      ☃ = GenLayerAddIsland.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(2L), ☃);
      ☃ = GenLayerAddIsland.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(50L), ☃);
      ☃ = GenLayerAddIsland.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(70L), ☃);
      ☃ = GenLayerRemoveTooMuchOcean.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(2L), ☃);
      IAreaFactory<T> ☃x = OceanLayer.INSTANCE.func_202823_a((IContextExtended<T>)☃.apply(2L));
      ☃x = func_202829_a(2001L, GenLayerZoom.NORMAL, ☃x, 6, ☃);
      ☃ = GenLayerAddSnow.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(2L), ☃);
      ☃ = GenLayerAddIsland.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(3L), ☃);
      ☃ = GenLayerEdge.CoolWarm.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(2L), ☃);
      ☃ = GenLayerEdge.HeatIce.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(2L), ☃);
      ☃ = GenLayerEdge.Special.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(3L), ☃);
      ☃ = GenLayerZoom.NORMAL.func_202713_a((IContextExtended<T>)☃.apply(2002L), ☃);
      ☃ = GenLayerZoom.NORMAL.func_202713_a((IContextExtended<T>)☃.apply(2003L), ☃);
      ☃ = GenLayerAddIsland.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(4L), ☃);
      ☃ = GenLayerAddMushroomIsland.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(5L), ☃);
      ☃ = GenLayerDeepOcean.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(4L), ☃);
      ☃ = func_202829_a(1000L, GenLayerZoom.NORMAL, ☃, 0, ☃);
      int ☃xx = 4;
      int ☃xxx = ☃xx;
      if (☃ != null) {
         ☃xx = ☃.func_202200_j();
         ☃xxx = ☃.func_202198_k();
      }

      if (☃ == WorldType.field_77135_d) {
         ☃xx = 6;
      }

      IAreaFactory<T> var7 = func_202829_a(1000L, GenLayerZoom.NORMAL, ☃, 0, ☃);
      var7 = GenLayerRiverInit.INSTANCE.func_202713_a((IContextExtended)☃.apply(100L), var7);
      IAreaFactory<T> var8 = new GenLayerBiome(☃, ☃).func_202713_a((IContextExtended<T>)☃.apply(200L), ☃);
      var8 = func_202829_a(1000L, GenLayerZoom.NORMAL, var8, 2, ☃);
      var8 = GenLayerBiomeEdge.INSTANCE.func_202713_a((IContextExtended)☃.apply(1000L), var8);
      IAreaFactory<T> var9 = func_202829_a(1000L, GenLayerZoom.NORMAL, var7, 2, ☃);
      var8 = GenLayerHills.INSTANCE.func_202707_a((IContextExtended)☃.apply(1000L), var8, var9);
      var7 = func_202829_a(1000L, GenLayerZoom.NORMAL, var7, 2, ☃);
      var7 = func_202829_a(1000L, GenLayerZoom.NORMAL, var7, ☃xxx, ☃);
      var7 = GenLayerRiver.INSTANCE.func_202713_a((IContextExtended)☃.apply(1L), var7);
      var7 = GenLayerSmooth.INSTANCE.func_202713_a((IContextExtended)☃.apply(1000L), var7);
      var8 = GenLayerRareBiome.INSTANCE.func_202713_a((IContextExtended)☃.apply(1001L), var8);

      for(int ☃ = 0; ☃ < ☃xx; ++☃) {
         var8 = GenLayerZoom.NORMAL.func_202713_a((IContextExtended)☃.apply((long)(1000 + ☃)), var8);
         if (☃ == 0) {
            var8 = GenLayerAddIsland.INSTANCE.func_202713_a((IContextExtended)☃.apply(3L), var8);
         }

         if (☃ == 1 || ☃xx == 1) {
            var8 = GenLayerShore.INSTANCE.func_202713_a((IContextExtended)☃.apply(1000L), var8);
         }
      }

      var8 = GenLayerSmooth.INSTANCE.func_202713_a((IContextExtended)☃.apply(1000L), var8);
      var8 = GenLayerRiverMix.INSTANCE.func_202707_a((IContextExtended)☃.apply(100L), var8, var7);
      var8 = GenLayerMixOceans.INSTANCE.func_202707_a((IContextExtended<T>)☃.apply(100L), var8, ☃x);
      IAreaFactory<T> ☃ = GenLayerVoronoiZoom.INSTANCE.func_202713_a((IContextExtended<T>)☃.apply(10L), var8);
      return ImmutableList.of(var8, ☃, var8);
   }

   public static GenLayer[] func_202824_a(long var0, WorldType var2, OverworldGenSettings var3) {
      int ☃ = 1;
      int[] ☃x = new int[1];
      ImmutableList<IAreaFactory<LazyArea>> ☃xx = func_202828_a(☃, ☃, var3x -> {
         ☃[0]++;
         return new LazyAreaLayerContext(1, ☃[0], ☃, var3x);
      });
      GenLayer ☃xxx = new GenLayer((IAreaFactory<LazyArea>)☃xx.get(0));
      GenLayer ☃xxxx = new GenLayer((IAreaFactory<LazyArea>)☃xx.get(1));
      GenLayer ☃xxxxx = new GenLayer((IAreaFactory<LazyArea>)☃xx.get(2));
      return new GenLayer[]{☃xxx, ☃xxxx, ☃xxxxx};
   }

   public static boolean func_202826_a(int var0, int var1) {
      if (☃ == ☃) {
         return true;
      } else {
         Biome ☃ = IRegistry.field_212624_m.func_148754_a(☃);
         Biome ☃x = IRegistry.field_212624_m.func_148754_a(☃);
         if (☃ == null || ☃x == null) {
            return false;
         } else if (☃ != Biomes.field_150607_aa && ☃ != Biomes.field_150608_ab) {
            if (☃.func_201856_r() != Biome.Category.NONE && ☃x.func_201856_r() != Biome.Category.NONE && ☃.func_201856_r() == ☃x.func_201856_r()) {
               return true;
            } else {
               return ☃ == ☃x;
            }
         } else {
            return ☃x == Biomes.field_150607_aa || ☃x == Biomes.field_150608_ab;
         }
      }
   }

   protected static boolean func_202827_a(int var0) {
      return ☃ == field_203632_a
         || ☃ == field_203633_b
         || ☃ == field_202832_c
         || ☃ == field_203634_d
         || ☃ == field_202831_b
         || ☃ == field_203635_f
         || ☃ == field_203636_g
         || ☃ == field_202830_a
         || ☃ == field_203637_i
         || ☃ == field_203638_j;
   }

   protected static boolean func_203631_b(int var0) {
      return ☃ == field_203632_a || ☃ == field_203633_b || ☃ == field_202832_c || ☃ == field_203634_d || ☃ == field_202831_b;
   }
}
