package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.CompositeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.structure.DesertPyramidConfig;
import net.minecraft.world.gen.feature.structure.EndCityConfig;
import net.minecraft.world.gen.feature.structure.FortressConfig;
import net.minecraft.world.gen.feature.structure.IglooConfig;
import net.minecraft.world.gen.feature.structure.JunglePyramidConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.OceanMonumentConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.feature.structure.SwampHutConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillagePieces;
import net.minecraft.world.gen.feature.structure.WoodlandMansionConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlatGenSettings extends ChunkGenSettings {
   private static final Logger field_211404_q = LogManager.getLogger();
   private static final CompositeFeature<MineshaftConfig, NoPlacementConfig> field_202250_m = Biome.func_201864_a(
      Feature.field_202329_g, new MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<VillageConfig, NoPlacementConfig> field_202251_n = Biome.func_201864_a(
      Feature.field_202328_f, new VillageConfig(0, VillagePieces.Type.OAK), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<StrongholdConfig, NoPlacementConfig> field_202252_o = Biome.func_201864_a(
      Feature.field_202335_m, new StrongholdConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<SwampHutConfig, NoPlacementConfig> field_202253_p = Biome.func_201864_a(
      Feature.field_202334_l, new SwampHutConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<DesertPyramidConfig, NoPlacementConfig> field_202254_q = Biome.func_201864_a(
      Feature.field_202332_j, new DesertPyramidConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<JunglePyramidConfig, NoPlacementConfig> field_202255_r = Biome.func_201864_a(
      Feature.field_202331_i, new JunglePyramidConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<IglooConfig, NoPlacementConfig> field_202256_s = Biome.func_201864_a(
      Feature.field_202333_k, new IglooConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<ShipwreckConfig, NoPlacementConfig> field_204750_v = Biome.func_201864_a(
      Feature.field_204751_l, new ShipwreckConfig(false), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<OceanMonumentConfig, NoPlacementConfig> field_202257_t = Biome.func_201864_a(
      Feature.field_202336_n, new OceanMonumentConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<LakesConfig, LakeChanceConfig> field_202258_u = Biome.func_201864_a(
      Feature.field_202289_ai, new LakesConfig(Blocks.field_150355_j), Biome.field_201884_D, new LakeChanceConfig(4)
   );
   private static final CompositeFeature<LakesConfig, LakeChanceConfig> field_202259_v = Biome.func_201864_a(
      Feature.field_202289_ai, new LakesConfig(Blocks.field_150353_l), Biome.field_201883_C, new LakeChanceConfig(80)
   );
   private static final CompositeFeature<EndCityConfig, NoPlacementConfig> field_202260_w = Biome.func_201864_a(
      Feature.field_202338_p, new EndCityConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<WoodlandMansionConfig, NoPlacementConfig> field_202261_x = Biome.func_201864_a(
      Feature.field_202330_h, new WoodlandMansionConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<FortressConfig, NoPlacementConfig> field_202262_y = Biome.func_201864_a(
      Feature.field_202337_o, new FortressConfig(), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   private static final CompositeFeature<OceanRuinConfig, NoPlacementConfig> field_204028_A = Biome.func_201864_a(
      Feature.field_204029_o, new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.1F), Biome.field_201917_l, IPlacementConfig.field_202468_e
   );
   public static final Map<CompositeFeature<?, ?>, GenerationStage.Decoration> field_202248_k = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      var0.put(field_202250_m, GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
      var0.put(field_202251_n, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_202252_o, GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
      var0.put(field_202253_p, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_202254_q, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_202255_r, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_202256_s, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_204750_v, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_204028_A, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_202258_u, GenerationStage.Decoration.LOCAL_MODIFICATIONS);
      var0.put(field_202259_v, GenerationStage.Decoration.LOCAL_MODIFICATIONS);
      var0.put(field_202260_w, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_202261_x, GenerationStage.Decoration.SURFACE_STRUCTURES);
      var0.put(field_202262_y, GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
      var0.put(field_202257_t, GenerationStage.Decoration.SURFACE_STRUCTURES);
   });
   public static final Map<String, CompositeFeature<?, ?>[]> field_202247_j = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      var0.put("mineshaft", new CompositeFeature[]{field_202250_m});
      var0.put("village", new CompositeFeature[]{field_202251_n});
      var0.put("stronghold", new CompositeFeature[]{field_202252_o});
      var0.put("biome_1", new CompositeFeature[]{field_202253_p, field_202254_q, field_202255_r, field_202256_s, field_204028_A, field_204750_v});
      var0.put("oceanmonument", new CompositeFeature[]{field_202257_t});
      var0.put("lake", new CompositeFeature[]{field_202258_u});
      var0.put("lava_lake", new CompositeFeature[]{field_202259_v});
      var0.put("endcity", new CompositeFeature[]{field_202260_w});
      var0.put("mansion", new CompositeFeature[]{field_202261_x});
      var0.put("fortress", new CompositeFeature[]{field_202262_y});
   });
   public static final Map<CompositeFeature<?, ?>, IFeatureConfig> field_202249_l = Util.func_200696_a(
      Maps.<CompositeFeature<?, ?>, IFeatureConfig>newHashMap(), var0 -> {
         var0.put(field_202250_m, new MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL));
         var0.put(field_202251_n, new VillageConfig(0, VillagePieces.Type.OAK));
         var0.put(field_202252_o, new StrongholdConfig());
         var0.put(field_202253_p, new SwampHutConfig());
         var0.put(field_202254_q, new DesertPyramidConfig());
         var0.put(field_202255_r, new JunglePyramidConfig());
         var0.put(field_202256_s, new IglooConfig());
         var0.put(field_204028_A, new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F));
         var0.put(field_204750_v, new ShipwreckConfig(false));
         var0.put(field_202257_t, new OceanMonumentConfig());
         var0.put(field_202260_w, new EndCityConfig());
         var0.put(field_202261_x, new WoodlandMansionConfig());
         var0.put(field_202262_y, new FortressConfig());
      }
   );
   private final List<FlatLayerInfo> field_82655_a = Lists.<FlatLayerInfo>newArrayList();
   private final Map<String, Map<String, String>> field_82653_b = Maps.newHashMap();
   private Biome field_82654_c;
   private final IBlockState[] field_202244_C = new IBlockState[256];
   private boolean field_202245_D;
   private int field_202246_E;

   @Nullable
   public static Block func_212683_a(String var0) {
      try {
         ResourceLocation ☃ = new ResourceLocation(☃);
         if (IRegistry.field_212618_g.func_212607_c(☃)) {
            return IRegistry.field_212618_g.func_82594_a(☃);
         }
      } catch (IllegalArgumentException var2) {
         field_211404_q.warn("Invalid blockstate: {}", ☃, var2);
      }

      return null;
   }

   public Biome func_82648_a() {
      return this.field_82654_c;
   }

   public void func_82647_a(Biome var1) {
      this.field_82654_c = ☃;
   }

   public Map<String, Map<String, String>> func_82644_b() {
      return this.field_82653_b;
   }

   public List<FlatLayerInfo> func_82650_c() {
      return this.field_82655_a;
   }

   public void func_82645_d() {
      int ☃ = 0;

      for(FlatLayerInfo ☃x : this.field_82655_a) {
         ☃x.func_82660_d(☃);
         ☃ += ☃x.func_82657_a();
      }

      this.field_202246_E = 0;
      this.field_202245_D = true;
      ☃ = 0;

      for(FlatLayerInfo ☃x : this.field_82655_a) {
         for(int ☃xx = ☃x.func_82656_d(); ☃xx < ☃x.func_82656_d() + ☃x.func_82657_a(); ++☃xx) {
            IBlockState ☃xxx = ☃x.func_175900_c();
            if (☃xxx.func_177230_c() != Blocks.field_150350_a) {
               this.field_202245_D = false;
               this.field_202244_C[☃xx] = ☃xxx;
            }
         }

         if (☃x.func_175900_c().func_177230_c() == Blocks.field_150350_a) {
            ☃ += ☃x.func_82657_a();
         } else {
            this.field_202246_E += ☃x.func_82657_a() + ☃;
            ☃ = 0;
         }
      }
   }

   public String toString() {
      StringBuilder ☃ = new StringBuilder();

      for(int ☃x = 0; ☃x < this.field_82655_a.size(); ++☃x) {
         if (☃x > 0) {
            ☃.append(",");
         }

         ☃.append(this.field_82655_a.get(☃x));
      }

      ☃.append(";");
      ☃.append(IRegistry.field_212624_m.func_177774_c(this.field_82654_c));
      ☃.append(";");
      if (!this.field_82653_b.isEmpty()) {
         int ☃x = 0;

         for(Entry<String, Map<String, String>> ☃xx : this.field_82653_b.entrySet()) {
            if (☃x++ > 0) {
               ☃.append(",");
            }

            ☃.append(((String)☃xx.getKey()).toLowerCase(Locale.ROOT));
            Map<String, String> ☃xxx = (Map)☃xx.getValue();
            if (!☃xxx.isEmpty()) {
               ☃.append("(");
               int ☃xxxx = 0;

               for(Entry<String, String> ☃xxxxx : ☃xxx.entrySet()) {
                  if (☃xxxx++ > 0) {
                     ☃.append(" ");
                  }

                  ☃.append((String)☃xxxxx.getKey());
                  ☃.append("=");
                  ☃.append((String)☃xxxxx.getValue());
               }

               ☃.append(")");
            }
         }
      }

      return ☃.toString();
   }

   public static FlatGenSettings func_210835_a(Dynamic<?> var0) {
      FlatGenSettings ☃ = ChunkGeneratorType.field_205489_f.func_205483_a();
      List<Pair<Integer, Block>> ☃x = (List)((Stream)☃.get("layers").flatMap(Dynamic::getStream).orElse(Stream.empty()))
         .map(var0x -> Pair.of(var0x.getInt("height", 1), func_212683_a(var0x.getString("block"))))
         .collect(Collectors.toList());
      if (☃x.stream().anyMatch(var0x -> var0x.getSecond() == null)) {
         return func_82649_e();
      } else {
         List<FlatLayerInfo> ☃ = (List)☃x.stream().map(var0x -> new FlatLayerInfo(var0x.getFirst(), (Block)var0x.getSecond())).collect(Collectors.toList());
         if (☃.isEmpty()) {
            return func_82649_e();
         } else {
            ☃.func_82650_c().addAll(☃);
            ☃.func_82645_d();
            ☃.func_82647_a(IRegistry.field_212624_m.func_212608_b(new ResourceLocation(☃.getString("biome"))));
            ☃.get("structures")
               .flatMap(Dynamic::getMapValues)
               .ifPresent(
                  var1x -> var1x.keySet().forEach(var1xx -> var1xx.getStringValue().map(var1xxx -> (Map)☃.func_82644_b().put(var1xxx, Maps.newHashMap())))
               );
            return ☃;
         }
      }
   }

   public static FlatGenSettings func_82649_e() {
      FlatGenSettings ☃ = ChunkGeneratorType.field_205489_f.func_205483_a();
      ☃.func_82647_a(Biomes.field_76772_c);
      ☃.func_82650_c().add(new FlatLayerInfo(1, Blocks.field_150357_h));
      ☃.func_82650_c().add(new FlatLayerInfo(2, Blocks.field_150346_d));
      ☃.func_82650_c().add(new FlatLayerInfo(1, Blocks.field_196658_i));
      ☃.func_82645_d();
      ☃.func_82644_b().put("village", Maps.newHashMap());
      return ☃;
   }

   public boolean func_202238_o() {
      return this.field_202245_D;
   }

   public IBlockState[] func_202233_q() {
      return this.field_202244_C;
   }
}
